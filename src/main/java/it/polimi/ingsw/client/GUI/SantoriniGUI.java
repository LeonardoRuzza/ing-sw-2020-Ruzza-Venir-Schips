package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;

public class SantoriniGUI {
    private  ClientGUI clientGUI;
    private JFrame frame = new JFrame("Santorini");

    public SantoriniGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
    }

/*    public ClientGUI getClientGUI(){
        return clientGUI;
    }*/

    public void sendNotification(String s){
        clientGUI.outcomeGUI.add(s);
    }

    public void createAndStartGUI() {
        frame.setLayout(new BorderLayout());
        //TODO creazione schermata scelta nickname
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void updateLobbyMessage() {

    }

    public void updateMatchMessage() {

    }

}
