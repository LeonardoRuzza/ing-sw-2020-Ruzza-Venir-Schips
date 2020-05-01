package it.polimi.ingsw.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SantoriniGUI {
    private  ClientGUI clientGUI;
    private JFrame frame = new JFrame("Santorini");
    private JPanel currentPanel;


    public SantoriniGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
        createAndStartGUI();
    }

/*    public ClientGUI getClientGUI(){
        return clientGUI;
    }*/

    public void sendNotification(String s){
        clientGUI.outcomeGUI.add(s);
    }

    public void createAndStartGUI() {
        //frame.setLayout(null);
        frame.setResizable(true);
        frame.setSize(1920,1080);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addBackgroungImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/resources/background_lobby.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel background = new JPanel();
        JLabel backGrIm = new JLabel(new ImageIcon(image));
        background.add(backGrIm);
        frame.add(background);
    }


    public void updateGUILobby(MessageToGUI message) {
        switch (message.getStateOfGUI()){
            case INSERTNAME:
                try {
                    currentPanel = new NicknamePanel(clientGUI);
                    frame.add(currentPanel);
                    addBackgroungImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CHANGENAME:
                NicknamePanel temp = (NicknamePanel) currentPanel;
                temp.showNameAlreadyTokenDialog(frame);
                break;

        }
    }

    public void updateMatchMessage() {

    }

}
