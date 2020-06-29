package it.polimi.ingsw.server;


import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.ClosingConnectionParameter;
import it.polimi.ingsw.utils.GameMessage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;
    private boolean readyToPlay = false;
    private final Object lock = new Object();


    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    @Override
    public void setReadyToPlay(boolean readyToPlay){
        this.readyToPlay = readyToPlay;
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
        send(GameMessage.closingConnection);
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    @Override
    public void close(ClosingConnectionParameter closingParameter) {
        closeConnection();
        if (closingParameter == ClosingConnectionParameter.SINGLE){
            System.out.println("Deregistering 1 client...");
            server.deregisterConnectionSingleClient(this);
        }else{
            System.out.println("Deregistering all clients of a game...");
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
    public void release(){
        synchronized (lock){
            lock.notify();
        }
    }

    @Override
    public void run() {
        int numberOfPlayers = -1;
        Scanner in;
        String name;
        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send(GameMessage.insertName);
            String read = in.nextLine();
            name = read;
            while(!server.addClient(this, name) || name.isEmpty()){   //try to register the new client/player with an available name
                send(GameMessage.changeName);
                read = in.nextLine();
                name = read;
            }
            send(GameMessage.loadingMatch);
            while(!readyToPlay){      //this management with multiple threads is used to instantiate correctly more matches with the correct Master Player by the Server
                synchronized (lock) {
                    if (server.isFirstPlayer(this)) {
                        boolean temp = false;
                        while (!temp) {
                            send("\nNo existing match found.");
                            send("\nCreating new game...");
                            send(GameMessage.masterPlayerSelectNumberofPlayers);
                            read = in.nextLine();
                            try {
                                numberOfPlayers = (Integer.parseInt(read));
                                if (numberOfPlayers == 2 || numberOfPlayers == 3) {
                                    temp = true;
                                    this.send(GameMessage.waitingPlayers);
                                }
                                else
                                    this.send("Insert a correct number");
                                this.setReadyToPlay(true);
                            } catch (NumberFormatException e) {
                                this.send("Insert a correct number");
                            }
                        }
                    } else {
                        try {
                            if(!server.manageLobby(-1)){
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            server.manageLobby(numberOfPlayers);
            while (isActive()) {
                read = in.nextLine();
                if (read.toUpperCase().equals("QUIT")){
                    close(ClosingConnectionParameter.ALL);
                    break;
                }
                notify(read);
            }
        } catch (IOException | RuntimeException e) {
            if(isActive()){
                System.err.println("Error!" + e.getMessage());
                close(ClosingConnectionParameter.ALL);
            }
        } finally {
            if(isActive()) closeConnection();
        }
    }
}
