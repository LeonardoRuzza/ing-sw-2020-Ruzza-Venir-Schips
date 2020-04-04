package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.lobby.Lobby;
import it.polimi.ingsw.utils.GameMessage;
import it.polimi.ingsw.view.LobbyRemoteView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 12345;
    private int numberOfPlayers = 0;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnectionForTwo = new HashMap<>();
    private Map<ClientConnection, Map<ClientConnection,ClientConnection>> playingConnectionForThree = new HashMap<>();

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /*private int[] deckTwo = new int[2];
    private int[] deckThree = new int[3];
    private Worker.Color[] playerColorTwo = new Worker.Color[2];
    private Worker.Color[] playerColorThree = new Worker.Color[3];
    public int[] getDeckTwo() { return deckTwo; }
    public int[] getDeckThree() { return deckThree; }*/

    /*
    public static <T, E> T getKeyByValue(@NotNull Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }*/


    public synchronized void deregisterConnection(ClientConnection c) {
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
                return;
            }
        }

        List<ClientConnection> clientConnections = new ArrayList<>(playingConnectionForTwo.values());
        for(ClientConnection x: clientConnections){
            if(x.equals(c)){
                ClientConnection opponent = playingConnectionForTwo.get(c);
                if(opponent != null) {
                    opponent.closeConnection();
                }
                playingConnectionForTwo.remove(c);
                playingConnectionForTwo.remove(opponent);
                return;
            }
        }
        List<ClientConnection> opponents = new ArrayList<>(playingConnectionForThree.get(c).keySet());
        ClientConnection opponent1 = opponents.get(0);
        ClientConnection opponent2 = opponents.get(1);
        playingConnectionForThree.remove(c);
        playingConnectionForThree.remove(opponent1);
        playingConnectionForThree.remove(opponent2);
        if(opponent1 == null || opponent2 == null) return;
        opponent1.closeConnection();
        opponent2.closeConnection();
    }

    public synchronized boolean addClient(ClientConnection c, String name){
        if (waitingConnection.containsKey(name)){
            return false;
        }
        waitingConnection.put(name, c);
        return true;
    }


    public synchronized boolean isFirstPlayer(ClientConnection c){
        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        return waitingConnection.get(keys.get(0)).equals(c);
    }

    public synchronized void manageLobby(int numberOfPlayers){
        if (numberOfPlayers == 2 || numberOfPlayers == 3 || this.numberOfPlayers == 2 || this.numberOfPlayers == 3){
            if (waitingConnection.size() >= numberOfPlayers) {
                createGame(numberOfPlayers);
            }
            else {
                this.numberOfPlayers = numberOfPlayers;
            }
        }
    }



    //Wait for another player
    public void createGame(int numberOfPlayers){
        if(numberOfPlayers == 2){
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Lobby lobby = new Lobby(keys.get(0), keys.get(1));
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            Controller controller = new Controller(lobby);
            LobbyRemoteView player1LobbyView = new LobbyRemoteView(c1, lobby.getLobbyPlayers().get(0));
            LobbyRemoteView player2LobbyView = new LobbyRemoteView(c2, lobby.getLobbyPlayers().get(1));


            lobby.addObserverLobby(player1LobbyView);
            lobby.addObserverLobby(player2LobbyView);
            player1LobbyView.addObserverLobby(controller);
            player2LobbyView.addObserverLobby(controller);

            playingConnectionForTwo.put(c1, c2);
            playingConnectionForTwo.put(c2, c1);
            waitingConnection.remove(keys.get(0));
            waitingConnection.remove(keys.get(1));
            c1.asyncSend(GameMessage.chooseColor);
            c2.asyncSend(GameMessage.waitMessageForColor);
        }
        else{
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Lobby lobby = new Lobby(keys.get(0), keys.get(1), keys.get(2));
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            ClientConnection c3 = waitingConnection.get(keys.get(2));
            Controller controller = new Controller(lobby);
            LobbyRemoteView player1LobbyView = new LobbyRemoteView(c1, lobby.getLobbyPlayers().get(0));
            LobbyRemoteView player2LobbyView = new LobbyRemoteView(c2, lobby.getLobbyPlayers().get(1));
            LobbyRemoteView player3LobbyView = new LobbyRemoteView(c3, lobby.getLobbyPlayers().get(2));

            lobby.addObserverLobby(player1LobbyView);
            lobby.addObserverLobby(player2LobbyView);
            lobby.addObserverLobby(player3LobbyView);
            player1LobbyView.addObserverLobby(controller);
            player2LobbyView.addObserverLobby(controller);
            player3LobbyView.addObserverLobby(controller);

            Map<ClientConnection, ClientConnection> tmpMap = new HashMap<>();
            tmpMap.put(c2,c3);
            playingConnectionForThree.put(c1,tmpMap);
            tmpMap.clear();
            tmpMap.put(c1,c3);
            playingConnectionForThree.put(c2, tmpMap);
            tmpMap.clear();
            tmpMap.put(c1,c2);
            playingConnectionForThree.put(c3, tmpMap);
            waitingConnection.remove(keys.get(0));
            waitingConnection.remove(keys.get(1));
            waitingConnection.remove(keys.get(2));
            c1.asyncSend(GameMessage.waitMessageForColor);
            c2.asyncSend(GameMessage.waitMessageForColor);
            c3.asyncSend(GameMessage.waitMessageForColor);

            /*if(waitingConnectionForThree.isEmpty()){
                waitingConnectionForThree.put(name, c);
                deckThree = deck;
                playerColorThree[0] = workerColor;
            }else{
                waitingConnectionForThree.put(name, c);
                deckThree = deck;
                if(playerColorThree[1] == null){
                    playerColorThree[1] = workerColor;
                }else{
                    playerColorThree[2] = workerColor;
                }*/
        }
    }


    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}
