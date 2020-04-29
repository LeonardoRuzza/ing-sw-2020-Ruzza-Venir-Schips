package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.GameMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

public class ClientGUI {

        private String ip;
        private int port;
        private Player player;
        protected Queue<String> outcomeGUI = new LinkedList<>();

        public ClientGUI(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        private boolean active = true;

        public synchronized boolean isActive() {
            return active;
        }

        public synchronized void setActive(boolean active) {
            this.active = active;
        }


    private void decoderGUI(String s){
        String temp = GameMessage.searchStartingGameMessage(s);
        if (temp != null){
            return;
        }
        else{
            if (GameMessage.isMatchMessage(s)){
                return;
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

