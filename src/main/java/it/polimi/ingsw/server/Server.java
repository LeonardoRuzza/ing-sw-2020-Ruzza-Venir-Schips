package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.lobby.Lobby;
import it.polimi.ingsw.utils.GameMessage;
import it.polimi.ingsw.view.LobbyRemoteView;
import org.jetbrains.annotations.NotNull;


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
    private List<ClientConnection> registerOrder = new ArrayList<>();
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnectionForTwo = new HashMap<>();
    private Map<ClientConnection, Map<ClientConnection,ClientConnection>> playingConnectionForThree = new HashMap<>();

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public static <T, E> T getKeyByValue(@NotNull Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * The function deregister a {@code Clientconnection} from the {@code Server}. It checks if it's necessary to close connections
     * also for other clients in the game.
     * <p>
     * <b>This is called only when a player QUIT/DISCONNECT the game</b>
     * @param c   {@code Clientconnection} of the client who need to be deregister
     */
    public synchronized void deregisterConnection(ClientConnection c) {
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
                if(isFirstPlayer(c)){
                    numberOfPlayers = 0;
                    registerOrder.remove(c);
                    if(registerOrder.get(0)!=null){
                        registerOrder.get(0).release();
                    }
                    return;
                }
                registerOrder.remove(c);
                return;
            }
        }
        registerOrder.remove(c);
        List<ClientConnection> clientConnections = new ArrayList<>(playingConnectionForTwo.values());
        for(ClientConnection x: clientConnections){
            if(x.equals(c)){
                ClientConnection opponent = playingConnectionForTwo.get(c);
                if(opponent != null) {
                    opponent.send(GameMessage.quitCloseConnection);
                    opponent.closeConnection();
                }
                playingConnectionForTwo.remove(c);
                playingConnectionForTwo.remove(opponent);
                return;
            }
        }
        Map<ClientConnection, ClientConnection> opponents = new HashMap<>(playingConnectionForThree.get(c));
        ClientConnection[] temp =  opponents.values().toArray(new ClientConnection[0]);
        ClientConnection opponent1 = temp[0];
        ClientConnection opponent2 = getKeyByValue(opponents, opponent1);
        playingConnectionForThree.remove(c);

        if(opponent1!=null){
            opponent1.send(GameMessage.quitCloseConnection);
            playingConnectionForThree.remove(opponent1);
            opponent1.closeConnection();
        }
        if(opponent2 != null){
            opponent2.send(GameMessage.quitCloseConnection);
            playingConnectionForThree.remove(opponent2);
            opponent2.closeConnection();
        }
    }

    /**
     * <b>This is called when we need to deregister a player. For the <b>QUIT</b> case
     * </b> see {@code deregisterConnection}
     * <p>
     * The function deregister a single client, and check if he was playing a 2 or 3 playerGame
     * <p>
     * <b>Case 2</b> Remove from Map for 2 player {@code c}
     * <p>
     * <b>Case 3</b> Same as for Case 2. In addition since there are 2 players left, move the 2 left players, from Map for 3 to Map for 2
     * @param c  {@code ClientConnection} of the client who need to be deregister
     * @see     #deregisterConnection
     */
    public synchronized void deregisterConnectionSingleClient(ClientConnection c) {
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
                return;
            }
        }

        List<ClientConnection> clientConnections = new ArrayList<>(playingConnectionForTwo.keySet());
        for(ClientConnection x: clientConnections){
            if(x.equals(c)){
                playingConnectionForTwo.remove(c);
                return;
            }
        }
        Map<ClientConnection, ClientConnection> opponents = new HashMap<>(playingConnectionForThree.get(c));
        ClientConnection[] temp =  opponents.values().toArray(new ClientConnection[0]);
        ClientConnection opponent1 = temp[0];
        ClientConnection opponent2 = getKeyByValue(opponents, opponent1);
        playingConnectionForThree.remove(c);

        if(opponent1!= null && opponent2 != null){
            playingConnectionForThree.remove(opponent1);
            playingConnectionForThree.remove(opponent2);
            playingConnectionForTwo.put(opponent1, opponent2);
            playingConnectionForTwo.put(opponent2, opponent1);
        }
    }

    /**
     * Add a new client in the waiting List, checking previously if the name was already taken, by a client registered before
     * @param c      {@code ClientConnection} of the client
     * @param name   Name chosen by the client
     * @return       {@code true} if the client was successfully added to the waiting list, {@code false} otherwise
     */
    public synchronized boolean addClient(ClientConnection c, String name){
        if (waitingConnection.containsKey(name)){
            return false;
        }
        registerOrder.add(c);
        waitingConnection.put(name, c);
        return true;
    }

    /**Check if the client is the first in the registerOrder List.
     * <p>
     * @param c {@code ClientConnection}
     * @return {@code true} if the client is first in registerOrder, {@code false} otherwise
     */
    public synchronized boolean isFirstPlayer(ClientConnection c){
        if(registerOrder.size() == 0){
            return false;
        }
        return (registerOrder.get(0)).equals(c);
    }

    /**Check if there are enough clients in the waiting List to start a new game.
     * <p>
     * @param numberOfPlayers  Parameter received by the master player
     * @return   {@code true} if there are enough player to start a game, {@code false} otherwise
     */
    public synchronized boolean manageLobby(int numberOfPlayers){
        if(numberOfPlayers == 2 || numberOfPlayers == 3){
            if (waitingConnection.size() >= numberOfPlayers) {
                createGame(numberOfPlayers);
                return true;
            }else {
                this.numberOfPlayers = numberOfPlayers;
                return false;
            }
        }
        if (this.numberOfPlayers == 2 || this.numberOfPlayers == 3){
            if (waitingConnection.size() >= this.numberOfPlayers) {
                createGame(this.numberOfPlayers);
                return true;
            }
        }
        return false;
    }


    //Wait for another player
    /**The function create a new Lobby for the players, creating the MVC components and starting the interaction with the players
     * <p>
     * @param numberOfPlayers   who will play this game. The number is choosen by the master player
     */
    public void createGame(int numberOfPlayers){
        if(numberOfPlayers == 2){
            ClientConnection c1 = registerOrder.get(0);
            ClientConnection c2 = registerOrder.get(1);
            ClientConnection cNext = null;
            if(registerOrder.size() > 2){
                cNext = registerOrder.get(2);
            }
            String nameC1 = getKeyByValue(waitingConnection, c1);
            String nameC2 = getKeyByValue(waitingConnection, c2);
            Lobby lobby = new Lobby(nameC1, nameC2);

            LobbyRemoteView player1LobbyView = new LobbyRemoteView(c1, lobby.getLobbyPlayers().get(0));
            LobbyRemoteView player2LobbyView = new LobbyRemoteView(c2, lobby.getLobbyPlayers().get(1));
            Controller controller = new Controller(lobby, player1LobbyView, player2LobbyView, null);

            lobby.addObserverLobby(player1LobbyView);
            lobby.addObserverLobby(player2LobbyView);
            player1LobbyView.addObserverLobby(controller);
            player2LobbyView.addObserverLobby(controller);

            playingConnectionForTwo.put(c1, c2);
            playingConnectionForTwo.put(c2, c1);
            waitingConnection.remove(nameC1);
            waitingConnection.remove(nameC2);
            registerOrder.remove(c2);
            registerOrder.remove(c1);
            c1.asyncSend(GameMessage.chooseColorBegin);
            c2.asyncSend(GameMessage.waitMessageForColorBegin);
            c2.setReadyToPlay(true);
            c2.release();
            if(cNext!=null){
                cNext.release();
            }
            this.numberOfPlayers = 0;
        }
        else{
            ClientConnection c1 = registerOrder.get(0);
            ClientConnection c2 = registerOrder.get(1);
            ClientConnection c3 = registerOrder.get(2);
            ClientConnection cNext = null;
            if(registerOrder.size() > 3){
                cNext = registerOrder.get(3);
            }
            String nameC1 = getKeyByValue(waitingConnection, c1);
            String nameC2 = getKeyByValue(waitingConnection, c2);
            String nameC3 = getKeyByValue(waitingConnection, c3);
            Lobby lobby = new Lobby(nameC1, nameC2, nameC3);
            LobbyRemoteView player1LobbyView = new LobbyRemoteView(c1, lobby.getLobbyPlayers().get(0));
            LobbyRemoteView player2LobbyView = new LobbyRemoteView(c2, lobby.getLobbyPlayers().get(1));
            LobbyRemoteView player3LobbyView = new LobbyRemoteView(c3, lobby.getLobbyPlayers().get(2));
            Controller controller = new Controller(lobby, player1LobbyView, player2LobbyView, player3LobbyView);

            lobby.addObserverLobby(player1LobbyView);
            lobby.addObserverLobby(player2LobbyView);
            lobby.addObserverLobby(player3LobbyView);
            player1LobbyView.addObserverLobby(controller);
            player2LobbyView.addObserverLobby(controller);
            player3LobbyView.addObserverLobby(controller);
            Map<ClientConnection, ClientConnection> tmpMap = new HashMap<>();
            tmpMap.put(c2,c3);
            playingConnectionForThree.put(c1,tmpMap);
            tmpMap = new HashMap<>();
            tmpMap.put(c1,c3);
            playingConnectionForThree.put(c2, tmpMap);
            tmpMap = new HashMap<>();
            tmpMap.put(c1,c2);
            playingConnectionForThree.put(c3, tmpMap);
            waitingConnection.remove(nameC1);
            waitingConnection.remove(nameC2);
            waitingConnection.remove(nameC3);
            registerOrder.remove(c1);
            registerOrder.remove(c2);
            registerOrder.remove(c3);
            c1.asyncSend(GameMessage.chooseColorBegin);
            c2.asyncSend(GameMessage.waitMessageForColorBegin);
            c3.asyncSend(GameMessage.waitMessageForColorBegin);
            c2.setReadyToPlay(true);
            c3.setReadyToPlay(true);
            c2.release();
            c3.release();
            if(cNext!=null){
                cNext.release();
            }
            this.numberOfPlayers = 0;
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
