package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.GUI.ClientGUI;

import java.io.IOException;

public class ClientApp
{
    public static void main(String[] args){

        String IP = "127.0.0.1";
        int PORT = 12345;

        if(args.length>0 && args[0].equals("cli")){
            if(args.length > 2){
                if(args[1].split("\\.").length == 4){
                    try {
                        PORT = Integer.parseInt(args[2]);
                        IP = args[1];
                        if(PORT < 1026 || PORT > 65535){
                            IP = "127.0.0.1";
                            PORT = 12345;
                            System.out.println("Wrong server address opening on standard server");
                        }
                    }catch (Exception e){
                        System.out.println("Wrong server address opening on standard server");
                    }
                }else{
                    System.out.println("Wrong server address opening on standard server");
                }

            }
        }else if(args.length>1){
            if(args[0].split("\\.").length == 4){
                try {
                    PORT = Integer.parseInt(args[1]);
                    IP = args[0];
                    if(PORT < 1026 || PORT > 65535){
                        IP = "127.0.0.1";
                        PORT = 12345;
                        System.out.println("Wrong server address opening on standard server");
                    }
                }catch (Exception e){
                    System.out.println("Wrong server address opening on standard server");
                }
            }else{
                System.out.println("Wrong server address opening on standard server");
            }
        }else if(args.length > 0){
            System.out.println("Incorrect parameters starting with default connection's options");
        }

        if(args.length>0 && args[0].equals("cli")){
            System.out.print( "\n\n" + "\u001B[31m"+"_____/\\\\\\\\\\\\\\\\\\\\\\_______/\\\\\\\\\\\\\\\\\\______/\\\\\\\\\\_____/\\\\\\___/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\__________/\\\\\\\\\\\\\\\\\\______/\\\\\\\\\\\\\\\\\\\\\\___/\\\\\\\\\\_____/\\\\\\___/\\\\\\\\\\\\\\\\\\\\\\_        \n" +
                    " ___/\\\\\\/////////\\\\\\___/\\\\\\\\\\\\\\\\\\\\\\\\\\___\\/\\\\\\\\\\\\___\\/\\\\\\__\\///////\\\\\\/////____/\\\\\\///\\\\\\______/\\\\\\///////\\\\\\___\\/////\\\\\\///___\\/\\\\\\\\\\\\___\\/\\\\\\__\\/////\\\\\\///__       \n" +
                    "  __\\//\\\\\\______\\///___/\\\\\\/////////\\\\\\__\\/\\\\\\/\\\\\\__\\/\\\\\\________\\/\\\\\\_______/\\\\\\/__\\///\\\\\\___\\/\\\\\\_____\\/\\\\\\_______\\/\\\\\\______\\/\\\\\\/\\\\\\__\\/\\\\\\______\\/\\\\\\_____      \n" +
                    "   ___\\////\\\\\\_________\\/\\\\\\_______\\/\\\\\\__\\/\\\\\\//\\\\\\_\\/\\\\\\________\\/\\\\\\______/\\\\\\______\\//\\\\\\__\\/\\\\\\\\\\\\\\\\\\\\\\/________\\/\\\\\\______\\/\\\\\\//\\\\\\_\\/\\\\\\______\\/\\\\\\_____     \n" +
                    "    ______\\////\\\\\\______\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\__\\/\\\\\\\\//\\\\\\\\/\\\\\\________\\/\\\\\\_____\\/\\\\\\_______\\/\\\\\\__\\/\\\\\\//////\\\\\\________\\/\\\\\\______\\/\\\\\\\\//\\\\\\\\/\\\\\\______\\/\\\\\\_____    \n" +
                    "     _________\\////\\\\\\___\\/\\\\\\/////////\\\\\\__\\/\\\\\\_\\//\\\\\\/\\\\\\________\\/\\\\\\_____\\//\\\\\\______/\\\\\\___\\/\\\\\\____\\//\\\\\\_______\\/\\\\\\______\\/\\\\\\_\\//\\\\\\/\\\\\\______\\/\\\\\\_____   \n" +
                    "      __/\\\\\\______\\//\\\\\\__\\/\\\\\\_______\\/\\\\\\__\\/\\\\\\__\\//\\\\\\\\\\\\________\\/\\\\\\______\\///\\\\\\__/\\\\\\_____\\/\\\\\\_____\\//\\\\\\______\\/\\\\\\______\\/\\\\\\__\\//\\\\\\\\\\\\______\\/\\\\\\_____  \n" +
                    "       _\\///\\\\\\\\\\\\\\\\\\\\\\/___\\/\\\\\\_______\\/\\\\\\__\\/\\\\\\___\\//\\\\\\\\\\________\\/\\\\\\________\\///\\\\\\\\\\/______\\/\\\\\\______\\//\\\\\\__/\\\\\\\\\\\\\\\\\\\\\\__\\/\\\\\\___\\//\\\\\\\\\\___/\\\\\\\\\\\\\\\\\\\\\\_ \n" +
                    "        ___\\///////////_____\\///________\\///___\\///_____\\/////_________\\///___________\\/////________\\///________\\///__\\///////////___\\///_____\\/////___\\///////////__\n" +
                    "\u001B[0m" + "        Progetto Ingegneria del Software Ruzza, Schips, Venir\n\n\n" );

            Client client = new Client(IP, PORT);
            try{
                client.run();
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }else{
            ClientGUI clientGUI = new ClientGUI(IP, PORT);
            try{
                clientGUI.run();
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }
}

