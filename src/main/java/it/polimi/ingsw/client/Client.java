package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private final String ip;
    private final int port;
    private Player player;

    /**
     * This is the intermediary between Server and the Client. Here we receive input from Server, converting it in order to show to the User's GUI
     * <p>
     *  Here are implemented methods to write and read from Socket
     *  <p>
     * @param ip   Ip of the Server we want to connect to
     * @param port  Port on which the Client and Server will speak
     * @see it.polimi.ingsw.server.Server
     */
    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    private boolean active = true;

    /**
     * @return {@code true} if active, {@code false} otherwise
     */
    public synchronized boolean isActive(){
        return active;
    }

    /**
     * @param active {@code true} to set active, {@code false} otherwise
     */
    public synchronized void setActive(boolean active){
        this.active = active;
    }

    /**
     * A dedicated thread created to receive Objects from the Server
     * <p>
     * Simply print in the Standard Output, if socketIn is a String
     * <p>
     * Call the Board Draw method, to show changes in Board, if socketIn is a Board
     * <p>
     * <p>
     *     <b>If an execption occur this thread will be killed</b>
     * </p>
     * @param socketIn An {@code ObjectInputStream} to receive from the Socket
     * @return New thread waiting for input <b>from the Server</b>
     * @see Board
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
                            System.out.println((String) inputObject);
                        } else if (inputObject instanceof Board){
                            ((Board)inputObject).draw(player);
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
     * A dedicated thread created to receive Strings from CLI. This is flushed when a <b>\n</b> character is found
     * @param stdin Input received from Standard Input( User keyboard) in CLI
     * @param socketOut a PrintWriter, where the Input received from CLI will be send
     * @return New thread waiting for input <b>from the CLI</b>
     */
    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        String inputLine = stdin.nextLine();
                        socketOut.println(inputLine);
                        socketOut.flush();
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
     * The main thread associated to the Client
     * @throws IOException Should be never used, since Threads that read or write to Socket are killed if an exception occurs
     * @see #asyncReadFromSocket(ObjectInputStream)
     * @see #asyncWriteToSocket(Scanner, PrintWriter)
     */
    public void run() throws IOException {
        Socket socket;
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println("Unable to open socket");
            return;
        }
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
            System.out.println("Closing the application...");
        }
    }
}
