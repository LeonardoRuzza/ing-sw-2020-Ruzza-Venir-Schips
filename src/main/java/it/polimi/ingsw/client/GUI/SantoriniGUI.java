package it.polimi.ingsw.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SantoriniGUI {
    private  ClientGUI clientGUI;
    private JFrame frame = new JFrame("Santorini");
    private JPanel currentPanel;
    private final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];


    public SantoriniGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
        createAndStartGUI();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void sendNotification(String s){
        clientGUI.outcomeGUI.add(s);
    }

    public void createAndStartGUI() {
        frame.setResizable(true);
        frame.setSize(1920,1080);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        device.setFullScreenWindow(frame);
    }

    public void addBackgroungImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/resources/background_lobby.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel background = new JPanel();
        assert image != null;
        JLabel backGrIm = new JLabel(new ImageIcon(image));
        background.add(backGrIm);
        frame.getContentPane().add(background,1);
    }

    private void switchPanel(JPanel panel){
        frame.getContentPane().remove(0);
        frame.getContentPane().invalidate();
        frame.getContentPane().add(panel,0);
        frame.getContentPane().validate();
        frame.repaint();
    }

    public void addLabel(String pathname){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel temp = new JPanel();
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(651,188,619,207);
        temp.setSize(new Dimension(1920,1080));
        temp.setLayout(null);
        temp.setOpaque(true);
        temp.setBackground(new Color(0,0,0,0));
        temp.add(label);
        switchPanel(temp);
    }


    public void updateGUILobby(MessageToGUI message) {
        switch (message.getStateOfGUI()){
            case INSERTNAME:
                try {
                    currentPanel = new NicknamePanel(clientGUI);
                    frame.getContentPane().add(currentPanel,0);
                    addBackgroungImage();
                    frame.validate();
                    frame.repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CHANGENAME:
                NicknamePanel nicknamePanel = (NicknamePanel) currentPanel;
                nicknamePanel.showNameAlreadyTokenDialog(frame);
                break;
            case LOADINGMATCH:
                addLabel("src/main/resources/sys_label_loading.png");
                break;
            case MASTERSELECTNUMBEROFPLAYERS:
                try {
                    currentPanel = new NumberSelectionPanel(clientGUI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switchPanel(currentPanel);
                break;
            case CHOOSECOLOR:
                currentPanel = new ColorPanel(this, message.getAvailableColors());
                switchPanel(currentPanel);
                break;
            case MASTERCHOOSECARD:
                currentPanel = new MasterCardPanel(this, message.getCompleteDeck(), message.getCardToChoose());
                switchPanel(currentPanel);
                break;
            case PLAYERCHOOSECARD:
                currentPanel = new NotMasterCardPanel(this, message.getAvailableCards());
                switchPanel(currentPanel);
                break;
            case WAITINGOTHERPLAYERSCHOOSE:
                addLabel("src/main/resources/sys_label_wait_players.png");
                break;
            case STARTNORMALGAME:
                currentPanel = new GamePanel(this);
                GamePanel gamePanel = (GamePanel) currentPanel;
                switchPanel(gamePanel);
                for (MessageToGUI.PlayerSummary p: message.getPlayerSummaries()){
                    gamePanel.updateCardPanel((p.getPlayerNumber()), p.getNickname(), p.getCardName(), p.getColor());
                }
                break;
        }
    }

    public void updateMatchMessage() {

    }

}
