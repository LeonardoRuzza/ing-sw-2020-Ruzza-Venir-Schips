package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;
    private static final Object lock = new Object();

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    @Override
    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    public void close(Boolean singleClientClose) {
        closeConnection();                          // Chiude connessione per il client
        System.out.println("Deregistering client...");
        if (singleClientClose){
            server.deregisterConnectionSingleClient(this);
        }else{
            server.deregisterConnection(this);
        }
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        int numberOfPlayers = -1;
        Scanner in;
        String name;
        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            name = read;
            while(!server.addClient(this, name)){
                send("\nPleaseChangeName");
                read = in.nextLine();
                name = read;
            }
            send("\nSearchingForExistingmatch");
            synchronized (lock){
                if(server.isFirstPlayer(this)){
                    boolean temp = false;
                    while (!temp) {
                        send("\nCreatingNewGame");
                        send("\nChoose number of players");
                        read = in.nextLine();
                        try {
                            numberOfPlayers = (Integer.parseInt(read));
                            if (numberOfPlayers == 2 || numberOfPlayers == 3)
                               temp = true;
                            else
                                this.send("Inserire un numero corretto");
                        } catch (NumberFormatException e) {
                            this.send("Inserire un numero corretto");
                        }
                    }
                }
            }
            server.manageLobby(numberOfPlayers);
            this.send("Loading Match");
            while (isActive()) {
                read = in.nextLine();
                if (read.toUpperCase().equals("QUIT")){
                    break;
                }
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        } finally {
            close(false);
        }
    }
}
