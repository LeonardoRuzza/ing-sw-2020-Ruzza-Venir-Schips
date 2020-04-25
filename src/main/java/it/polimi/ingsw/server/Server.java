package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.lobby.Lobby;
import it.polimi.ingsw.utils.ClosingConnectionParameter;
import it.polimi.ingsw.utils.GameMessage;
import it.polimi.ingsw.view.LobbyRemoteView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.io.IOException;
import java.lang.reflect.Array;
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

    /*private int[] deckTwo = new int[2];
    private int[] deckThree = new int[3];
    private Worker.Color[] playerColorTwo = new Worker.Color[2];
    private Worker.Color[] playerColorThree = new Worker.Color[3];
    public int[] getDeckTwo() { return deckTwo; }
    public int[] getDeckThree() { return deckThree; }*/


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
     * For example, if a player win, all the connections must be closed for that game.
     * @param c   {@code Clientconnection} of the client who need to be deregistered
     * @param closingParameter   To distinguish various possibilities that lead a client to be deregistered
     */
    public synchronized void deregisterConnection(ClientConnection c, ClosingConnectionParameter closingParameter) {
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        registerOrder.remove(c);
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
                    if(closingParameter == ClosingConnectionParameter.FORWIN) opponent.send(GameMessage.turnMessageLose);
                    else opponent.send("Someone else quit the game. Closing your connection too.");
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
        if(c != null){
            playingConnectionForThree.remove(c);
        }
        if(opponent1!=null){
            if(closingParameter == ClosingConnectionParameter.FORWIN) opponent1.send(GameMessage.turnMessageLose);
            else opponent1.send("Someone else quit the game. Closing your connection too.");
            playingConnectionForThree.remove(opponent1);
            opponent1.closeConnection();
        }
        if(opponent2 != null){
            if(closingParameter == ClosingConnectionParameter.FORWIN) opponent2.send(GameMessage.turnMessageLose);
            else opponent2.send("Someone else quit the game. Closing your connection too.");
            playingConnectionForThree.remove(opponent2);
            opponent2.closeConnection();
        }
    }

    /**
     * <b>This is called only when a player lose the game and so we need to deregister him. For all other cases
     * </b> see {@code deregisterConnection}
     * <p>
     * The function deregister a single client, and check if he was playing a 2 or 3 playerGame
     * <p>
     * <b>Case 2</b> Since there is only 1 player left, close his connecion too
     * <p>
     * <b>Case 3</b> Since there are 2 players left, move the 2 left players in a game for 2
     * @param c  {@code Clientconnection} of the client who need to be deregistered
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
        Map<ClientConnection, ClientConnection> opponents = new HashMap<>(playingConnectionForThree.get(c));
        ClientConnection[] temp =  opponents.values().toArray(new ClientConnection[0]);
        ClientConnection opponent1 = temp[0];
        ClientConnection opponent2 = getKeyByValue(opponents, opponent1);
        if(c != null){
            playingConnectionForThree.remove(c);
        }
        if(opponent1!= null && opponent2 != null){
            playingConnectionForThree.remove(opponent1);
            playingConnectionForThree.remove(opponent2);
            playingConnectionForTwo.put(opponent1, opponent2);
            playingConnectionForTwo.put(opponent2, opponent1);
        }else if(opponent1 == null && opponent2 != null) {      //questo ramo else non viene mai raggiunto perchè questo metodo è invocato solo quando ci sono ancora altri due giocatori attivi
            opponent2.closeConnection();
        }else if(opponent1 != null){
            opponent1.closeConnection();
        }

    }

    /**
     * Add a new client in the waiting List, checking previously if the name was already taken, by a client registered before
     * @param c      {@code Clientconnection} of the client
     * @param name   Name chosen by the client
     * @return       {@code true} if the client was succesfully added to the waiting list, {@code false} otherwise
     */
    public synchronized boolean addClient(ClientConnection c, String name){
        if (waitingConnection.containsKey(name)){
            return false;
        }
        registerOrder.add(c);
        waitingConnection.put(name, c);
        return true;
    }

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
