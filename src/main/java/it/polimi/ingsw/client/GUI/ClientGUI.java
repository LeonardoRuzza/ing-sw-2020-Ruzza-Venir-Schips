package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.GameMessage;
import it.polimi.ingsw.utils.InputConversion;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

public class ClientGUI {

        private String ip;
        private int port;
        private Player player;
        private boolean active = true;
        private boolean isLobbyPhase = true;
        protected Queue<String> outcomeGUI = new LinkedList<>();
        private SantoriniGUI santoriniGUI;
        private MessageToGUI summary = new MessageToGUI(StateOfGUI.STARTNORMALGAME);
        int playersSummaries = 0;

        public ClientGUI(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }



        public synchronized boolean isActive() { return active; }
        public synchronized void setActive(boolean active) {
            this.active = active;
        }


    private void decoderGUI(String s) {
        if (isLobbyPhase) {
            switch (s) {
                // MEX PER TUTTI
                case (GameMessage.insertName):
                    santoriniGUI = new SantoriniGUI(this);
                    santoriniGUI.updateGUILobby(new MessageToGUI(StateOfGUI.INSERTNAME));
                    break;
                case (GameMessage.changeName):
                    santoriniGUI.updateGUILobby(new MessageToGUI(StateOfGUI.CHANGENAME));
                    break;
                case (GameMessage.loadingMatch):
                    santoriniGUI.updateGUILobby(new MessageToGUI(StateOfGUI.LOADINGMATCH));
                    break;
                case (GameMessage.waitingPlayers):
                case (GameMessage.waitMessageForColorBegin):
                case (GameMessage.waitMessageForColor):
                case (GameMessage.waitMessageForCard):
                case (GameMessage.waitMasterChoseOfCard):
                    santoriniGUI.updateGUILobby(new MessageToGUI(StateOfGUI.WAITINGOTHERPLAYERSCHOOSE));
                    break;
                case (GameMessage.startNormalGame):
                    isLobbyPhase = false;
                    santoriniGUI.updateGUILobby(summary);
                    break;


                // MEX PER IL MASTERPLAYER
                case (GameMessage.masterPlayerSelectNumberofPlayers):
                    santoriniGUI.updateGUILobby(new MessageToGUI(StateOfGUI.MASTERSELECTNUMBEROFPLAYERS));
                    break;
                case (GameMessage.chooseColorBegin):
                    MessageToGUI m1 = new MessageToGUI(StateOfGUI.CHOOSECOLOR);
                    for (Worker.Color c : Worker.Color.values()) {
                        m1.updateAvailableColors(c);
                    }
                    santoriniGUI.updateGUILobby(m1);
                    break;


                default:
                    // MEX PER GLI ALTRI GIOCATORI
                    if (s.contains(GameMessage.chooseColor)) {
                        MessageToGUI mexForPlayer = new MessageToGUI(StateOfGUI.CHOOSECOLOR);
                        for (Worker.Color c : Worker.Color.values()) {
                            if (s.contains(c.toString())) {
                                mexForPlayer.updateAvailableColors(c);
                            }
                        }
                        santoriniGUI.updateGUILobby(mexForPlayer);
                        break;
                    }
                    if (s.contains(GameMessage.availableCards)) {
                        MessageToGUI mexForPlayer2 = new MessageToGUI(StateOfGUI.PLAYERCHOOSECARD, true);
                        for (Card c : mexForPlayer2.getCompleteDeck()) {
                            if (s.contains(c.getName())) {
                                mexForPlayer2.updateAvailableCards(c);
                            }
                        }
                        santoriniGUI.updateGUILobby(mexForPlayer2);
                        break;
                    }
                    // MEX PER IL MASTER PLAYER
                    if (s.contains(GameMessage.cardPhase)) {
                        MessageToGUI masterChooseCard = new MessageToGUI(StateOfGUI.MASTERCHOOSECARD, true);
                        if (s.contains(GameMessage.playerMasterChoseCard2)) {
                            masterChooseCard.setCardToChoose(2);
                        } else if (s.contains(GameMessage.playerMasterChoseCard3)) {
                            masterChooseCard.setCardToChoose(3);
                        }
                        santoriniGUI.updateGUILobby(masterChooseCard);
                        break;
                    }

                    // MEX PER TUTTI di Riepilogo
                    if (s.contains(GameMessage.player1is) || s.contains(GameMessage.player2is) || s.contains(GameMessage.player3is)) {
                        MessageToGUI.PlayerSummary temp = summary.addPlayerSummary();
                        playersSummaries++;
                        temp.setPlayerNumber(playersSummaries);
                        Scanner scanner = new Scanner(s);
                        scanner.next();
                        scanner.next();
                        temp.setNickname(scanner.nextLine().substring(1));
                        scanner.next();
                        scanner.next();
                        temp.setColor(InputConversion.colorConversion(scanner.nextLine().toUpperCase().substring(1)));
                        scanner.next();
                        scanner.next();
                        temp.setCardName(scanner.nextLine().substring(1));
                        break;
                    }
            }
        } else {
            switch (s) {
                case GameMessage.turnMessageSelectFirstAllocation:
                    santoriniGUI.updateMatchMessage(GameMessage.GUIFirstallocation);
                    break;
                case GameMessage.turnMessageWaitFirstAllocation:
                    santoriniGUI.updateMatchMessage(s);
                    break;
                default:
                    if (s.contains(GameMessage.turnMessageSelectYourWorker)) {
                        santoriniGUI.updateMatchMessage(GameMessage.GUISelectWorker);
                        break;
                    }
                    if (s.contains(GameMessage.turnMessageChooseCellMove)) {
                        santoriniGUI.updateMatchMessage(GameMessage.GUIChooseweretomove);
                        break;
                    }
                    if (s.contains(GameMessage.turnMessageChooseCellBuild)) {
                        santoriniGUI.updateMatchMessage(GameMessage.GUIChooseweretobuild);
                        break;
                    }
                    if (s.contains(GameMessage.turnMessageTurnEnd)) {
                        santoriniGUI.updateMatchMessage(GameMessage.GUIWaitOtherPlayersTurn);
                        break;
                    }
            }
        }
    }


    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if(inputObject instanceof String){
                            decoderGUI((String) inputObject);
                        } else if (inputObject instanceof Board){
                            santoriniGUI.updateBoard((Board) inputObject);
                        } else if (inputObject instanceof Player){
                            player=((Player)inputObject);
                        }else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final PrintWriter socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        if (!outcomeGUI.isEmpty()) {
                            socketOut.println(outcomeGUI.poll());
                            socketOut.flush();
                        }
                    }
                }catch(Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }


    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            System.out.println("Closing Connection...");
            socketIn.close();
            socketOut.close();
            socket.close();
            System.out.println("Connection closed!");
        }
    }
}

