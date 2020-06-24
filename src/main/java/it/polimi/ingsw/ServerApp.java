package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) {
        Server server;
        int PORT = 12345;
        try {
            System.out.println("Starting server...\n\n");
            if(args.length>0){
                try {
                    PORT = Integer.parseInt(args[0]);
                    if(PORT < 1026 || PORT > 65535){
                        PORT = 12345;
                        System.out.println("Wrong server's port, opening on standard server's port: 12345 - Accepted ports are between 1026 - 65535");
                    }
                }catch (Exception e){
                    System.out.println("Wrong server's port, opening on standard server's port: 12345");
                }
            }
            server = new Server(PORT);
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
