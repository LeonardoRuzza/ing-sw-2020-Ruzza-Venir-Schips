package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.FactoryPlayer;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnectionForTwo = new HashMap<>();
    private Map<String, ClientConnection> waitingConnectionForThree = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnectionForTwo = new HashMap<>();
    private Map<ClientConnection, Map<ClientConnection,ClientConnection>> playingConnectionForThree = new HashMap<>();

    private int[] deckTwo = new int[2];
    private int[] deckThree = new int[3];
    private Worker.Color[] playerColorTwo = new Worker.Color[2];
    private Worker.Color[] playerColorThree = new Worker.Color[3];
    private FactoryPlayer factoryClass = new FactoryPlayer();

    public int[] getDeckTwo() {
        return deckTwo;
    }

    public int[] getDeckThree() {
        return deckThree;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection c) {
        List<ClientConnection> clientConnections = new ArrayList<>(waitingConnectionForTwo.values());
        for(ClientConnection x: clientConnections){
            if(x.equals(c)){
                ClientConnection opponent = playingConnectionForTwo.get(c);
                if(opponent != null) {
                    opponent.closeConnection();
                }
                playingConnectionForTwo.remove(c);
                playingConnectionForTwo.remove(opponent);
                Iterator<String> iterator = waitingConnectionForTwo.keySet().iterator();
                while(iterator.hasNext()){
                    if(waitingConnectionForTwo.get(iterator.next())==c){
                        iterator.remove();
                    }
                }
                return;
            }
        }
        List<ClientConnection> opponents = new ArrayList<>(playingConnectionForThree.get(c).keySet());
        ClientConnection opponent1 = opponents.get(0);
        ClientConnection opponent2 = opponents.get(1);
        if(opponent1 != null){
            opponent1.closeConnection();
            if(opponent2 != null){
                opponent2.closeConnection();
            }
        }else if(opponent2 != null){
                opponent2.closeConnection();
        }
        playingConnectionForThree.remove(c);
        Iterator<String> iterator = waitingConnectionForThree.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnectionForThree.get(iterator.next())==c){
                iterator.remove();
            }
        }
        return;
    }

    //Wait for another player
    public synchronized void lobby(ClientConnection c, String name, int playersNum, Worker.Color workerColor, int[] deck){
        if(playersNum == 2){
            if(waitingConnectionForTwo.isEmpty()){
                waitingConnectionForTwo.put(name, c);
                deckTwo = deck;
                playerColorTwo[0] = workerColor;
            }else{
                waitingConnectionForTwo.put(name, c);
                deckTwo = deck;
                playerColorTwo[1] = workerColor;
                List<String> keys = new ArrayList<>(waitingConnectionForTwo.keySet());
                ClientConnection c1 = waitingConnectionForTwo.get(keys.get(0));
                ClientConnection c2 = waitingConnectionForTwo.get(keys.get(1));
                Model model = new Model();
                Player player1 = FactoryPlayer.getPlayer(getKeyByValue(waitingConnectionForTwo, c1), 1, model.getMatch(), playerColorTwo[0], deckTwo[0]);
                Player player2 = FactoryPlayer.getPlayer(getKeyByValue(waitingConnectionForTwo, c2), 2, model.getMatch(), playerColorTwo[1], deckTwo[1]);
                //View player1View = new RemoteView();
                //View player2View = new RemoteView();

                Controller controller = new Controller(model);
                model.addObserver(player1View);
                model.addObserver(player2View);
                player1View.addObserver(controller);
                player2View.addObserver(controller);
                playingConnectionForTwo.put(c1, c2);
                playingConnectionForTwo.put(c2, c1);
                waitingConnectionForTwo.clear();

                c1.asyncSend(model.getBoardCopy());
                c2.asyncSend(model.getBoardCopy());
                if(model.isPlayerTurn(player1)){
                    c1.asyncSend(gameMessage.moveMessage);
                    c2.asyncSend(gameMessage.waitMessage);
                } else {
                    c2.asyncSend(gameMessage.moveMessage);
                    c1.asyncSend(gameMessage.waitMessage);
                }
            }
        }else{
            if(waitingConnectionForThree.isEmpty()){
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
                }
                if(waitingConnectionForThree.size() == 3){
                    List<String> keys = new ArrayList<>(waitingConnectionForThree.keySet());
                    ClientConnection c1 = waitingConnectionForThree.get(keys.get(0));
                    ClientConnection c2 = waitingConnectionForThree.get(keys.get(1));
                    ClientConnection c3 = waitingConnectionForThree.get(keys.get(2));
                    Model model = new Model();
                    Player player1 = FactoryPlayer.getPlayer(getKeyByValue(waitingConnectionForThree, c1), 1, model.getMatch(), playerColorThree[0], deckThree[0]);
                    Player player2 = FactoryPlayer.getPlayer(getKeyByValue(waitingConnectionForThree, c2), 2, model.getMatch(), playerColorThree[1], deckThree[1]);
                    Player player3 = FactoryPlayer.getPlayer(getKeyByValue(waitingConnectionForThree, c3), 3, model.getMatch(), playerColorThree[2], deckThree[2]);
                    //View player1View = new RemoteView();
                    //View player2View = new RemoteView();
                    //View player3View = new RemoteView();

                    Controller controller = new Controller(model);
                    model.addObserver(player1View);
                    model.addObserver(player2View);
                    model.addObserver(player3View);
                    player1View.addObserver(controller);
                    player2View.addObserver(controller);
                    player3View.addObserver(controller);
                    Map<ClientConnection, ClientConnection> tmpMap = new HashMap<>();
                    tmpMap.put(c2,c3);
                    playingConnectionForThree.put(c1,tmpMap);
                    tmpMap.clear();
                    tmpMap.put(c1,c3);
                    playingConnectionForThree.put(c2, tmpMap);
                    tmpMap.clear();
                    tmpMap.put(c1,c2);
                    playingConnectionForThree.put(c3, tmpMap);
                    waitingConnectionForThree.clear();

                    c1.asyncSend(model.getBoardCopy());
                    c2.asyncSend(model.getBoardCopy());
                    c3.asyncSend(model.getBoardCopy());

                    if(model.isPlayerTurn(player1)){
                        c1.asyncSend(gameMessage.moveMessage);
                        c2.asyncSend(gameMessage.waitMessage);
                        c3.asyncSend(gameMessage.waitMessage);
                    } else {
                        c2.asyncSend(gameMessage.moveMessage);
                        c1.asyncSend(gameMessage.waitMessage);
                        c3.asyncSend(gameMessage.waitMessage);
                    }
                }
            }
        }
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
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
