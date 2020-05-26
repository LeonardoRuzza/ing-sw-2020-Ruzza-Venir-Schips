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

        private final String ip;
        private final int port;
        private Player player;
        private boolean active = true;
        private boolean isLobbyPhase = true;
        protected Queue<String> outcomeGUI = new LinkedList<>();
        private SantoriniGUI santoriniGUI;
        private MessageToGUI summary = new MessageToGUI(StateOfGUI.STARTNORMALGAME);
        private int playersSummaries = 0;

    /**
     * This is the intermediary between Server and the Client. Here we receive input from Server, converting it in order to show to the User's GUI
     * <p>
     *  Here are implemented methods to write and read from Socket
     *  <p>
     *  It works like the normal {@code Client} for CLI, but also need to convert from String to relative element in GUI. This is made in {@code SantoriniGUI}, here we only set the {@code CurrentStateOfGUI}
     * @param ip   Ip of the Server we want to connect to
     * @param port  Port on which the Client and Server will speak
     * @see it.polimi.ingsw.server.Server
     */
    public ClientGUI(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }


    /**
      * @return {@code true} if active, {@code false} otherwise
     */
    public synchronized boolean isActive() { return active; }

    /**
     * @param active {@code true} to set active, {@code false} otherwise
     */
    public synchronized void setActive(boolean active) {this.active = active; }

    /**
     * Check input sent to CLI, and decode it setting the {@code StateOfGUI}; Then send an Update to {@code SantoriniGUI}
      * @param s String received from the CLI
     */
    private void decoderGUI(String s) {
        if (isLobbyPhase) {
            switch (s) {
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
                        scanner.close();
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
                case GameMessage.quitCloseConnection:
                    santoriniGUI.updatePanelForQuiteAndLose(GameMessage.quitCloseConnection);
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
                    if (s.contains(GameMessage.turnMessageTurnEnd) || s.contains(GameMessage.turnMessageWaitAfterFirstAllocation)) {
                        santoriniGUI.updateMatchMessage(GameMessage.GUIWaitOtherPlayersTurn);
                        break;
                    }
                    if(s.contains(GameMessage.turnMessageFailBuildChangeDestination)){
                        santoriniGUI.updateMatchMessage(GameMessage.turnMessageFailBuildChangeDestination);
                        break;
                    }
                    if(s.contains(GameMessage.turnMessageWin)){
                        santoriniGUI.updateWin(true);
                        break;
                    }
                    if(s.contains(GameMessage.turnMessageLose)){
                        santoriniGUI.updateWin(false);
                        break;
                    }
                    if(s.contains(GameMessage.turnMessagePlayerLose)){
                        santoriniGUI.updatePanelForQuiteAndLose(s);
                        break;
                    }

                    if (s.contains(GameMessage.aresTurnMessageAskRemoveBlok)) {
                        santoriniGUI.updateSuperPlayer(new MessageToGUI(StateOfGUI.ARES));
                        break;
                    }
                    if (s.contains(GameMessage.aresTurnMessageFailRemoveBlokWNewCell)) {
                        santoriniGUI.updateSuperPlayer(new MessageToGUI(StateOfGUI.ARESFAIL));
                        break;
                    }
                    if (s.contains(GameMessage.atlasTurnMessageAskBuildDorse)){
                        santoriniGUI.updateSuperPlayer(new MessageToGUI(StateOfGUI.ATLAS));
                        break;
                    }
                    if (s.contains(GameMessage.atlasTurnMessageFailBuildDorse)){
                        santoriniGUI.updateSuperPlayer(new MessageToGUI(StateOfGUI.ATLASFAIL));
                        break;
                    }
                    if(s.contains(GameMessage.prometheusTurnMessageAskBuildBefore)){
                        if(s.contains(GameMessage.prometheusTurnMessageFailOptionalBuild)) {
                            santoriniGUI.updateMatchMessage(GameMessage.turnMessageFailBuildChangeDestination);
                        }
                        santoriniGUI.updateSuperPlayer(new MessageToGUI(StateOfGUI.PROMETHEUS));
                        break;
                    }
                    if(s.contains(GameMessage.hephaesthusTurnMessageAskBuild)){
                        if(s.contains(GameMessage.hephaesthusTurnMessageFailOptionalBuild)){
                            santoriniGUI.updateMatchMessage(GameMessage.turnMessageFailBuildChangeDestination);
                        }
                        santoriniGUI.updateSuperPlayer(new MessageToGUI((StateOfGUI.HEPHAESTUS)));
                        break;
                    }
                    if(s.contains(GameMessage.hestiaDemeterTurnMessageAskTwoBuild)){
                        santoriniGUI.updateSuperPlayer(new MessageToGUI(StateOfGUI.HESTIADEMETER));
                        break;
                    }
                    if(s.contains(GameMessage.hestiaDemeterTurnMessageFailOptionalBuildWNewCell)){
                        santoriniGUI.updateSuperPlayer(new MessageToGUI(StateOfGUI.HESTIADEMETERFAIL));
                        break;
                    }
            }
        }
    }

    /**
     * A dedicated thread created to receive Objects from the Server
     * <p>
     * Call the {@link #decoderGUI(String)} if socketIn is a String
     * <p>
     * Call the {@link #santoriniGUI} update method, to show changes in Board
     * <p>
     * <p>
     *     <b>If an execption occur this thread will be killed</b>
     * </p>
     * @param socketIn An {@code ObjectInputStream} to receive from the Socket
     * @return New thread waiting for input <b>from the Server</b>
     */
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if(inputObject instanceof String){
                            if (((String) inputObject).contains("Connection closed!")) {
                                setActive(false);
                            }
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

    /**
     * A dedicated thread created to receive the converted input from the GUI( a String). This is added to a queque, and then flush when queque is not empty
     * <p>
     * <b>If an execption occur this thread will be killed</b>
     * <p>
     * @param socketOut a PrintWriter, where the output received from the GUI will be send
     * @return New thread waiting for input <b>from the GUI</b>
     */
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


    /**
     * The main thread associated to the ClientGUI
     * @throws IOException Should be never used, since Threads that read or write to Socket are killed if an exception occurs
     * @see #asyncReadFromSocket(ObjectInputStream)
     * @see #asyncWriteToSocket(PrintWriter)
     */
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
            socketIn.close();
            socketOut.close();
            socket.close();
            System.out.println("Closing the application...");
        }
    }
}

