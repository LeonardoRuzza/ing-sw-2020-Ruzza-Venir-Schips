package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.utils.GameMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SantoriniGUI {
    private  ClientGUI clientGUI;
    private StateOfGUI currentStateOfGUI;
    private JFrame frame = new JFrame("Santorini");
    private JPanel currentPanel;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();

    public SantoriniGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
        createAndStartGUI();
    }

    public StateOfGUI getCurrentStateOfGUI() {
        return currentStateOfGUI;
    }

    public void sendNotification(String s){
        clientGUI.outcomeGUI.add(s);
    }

    public void createAndStartGUI() {
        frame.setResizable(false);
        frame.setSize(1920,1080);
        frame.setMinimumSize(new Dimension(1920,1080));
        frame.setMaximumSize(new Dimension(1920,1080));
        frame.setPreferredSize(new Dimension(1920,1080));
        frame.setUndecorated(true);
        frame.setExtendedState(Frame.MAXIMIZED_VERT);
        //gd.setFullScreenWindow(frame);
        frame.setLocation((((int)(ge.getMaximumWindowBounds().getWidth()/2)-960)),0);
        frame.pack();
        frame.setVisible(true);
        //setCursor();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addBackgroungImage() {
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

    private void setCursor(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/resources/mouse_pointer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "customCursor");
        frame.setCursor(customCursor);
    }

    private void switchPanel(JPanel panel){
        frame.getContentPane().remove(0);
        frame.getContentPane().invalidate();
        frame.getContentPane().add(panel,0);
        frame.getContentPane().validate();
        frame.repaint();
    }

    private void addLabel(String pathname){
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

    protected void updateMatchMessage(String message) {
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.updateServerMessage(message);
    }

    protected void updateBoard(Board board){
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.updateGrid(board);
    }

    protected void updateSuperPlayer(MessageToGUI message){
        GamePanel gamePanel = (GamePanel) currentPanel;
        switch (message.getStateOfGUI()) {
            case ARES:
               currentStateOfGUI = message.getStateOfGUI();
               gamePanel.askUseSuperPower(GameMessage.aresTurnMessageAskRemoveBlokGUI);
               break;
            case ARESFAIL:
                gamePanel.askUseSuperPower(GameMessage.aresTurnMessageFailRemoveBlokWNewCellGUI);
                break;
            case ATLAS:
                currentStateOfGUI = message.getStateOfGUI();
                gamePanel.askUseSuperPower(GameMessage.atlasTurnMessageAskBuildDorseGUI);
                break;
            case ATLASFAIL:
                gamePanel.askUseSuperPower(GameMessage.atlasTurnMessageFailBuildDorseGUI);
                break;
            case PROMETHEUS:
                currentStateOfGUI = message.getStateOfGUI();
                gamePanel.askUseSuperPower(GameMessage.prometheusTurnMessageAskBuildBeforeGUI);
                break;
            case HEPHAESTUS:
                currentStateOfGUI = message.getStateOfGUI();
                gamePanel.askUseSuperPower(GameMessage.hephaesthusTurnMessageAskBuildGUI);
                break;
        }
    }

    protected void superPlayerChoose(boolean response){
        switch (currentStateOfGUI){
            case ARES:
                if (response)
                    updateMatchMessage(GameMessage.aresRemoveBlockGUI);   // Caso in cui Ares vuole rimuovere un blocco
                break;
            case ATLAS:
                if (response)
                    updateMatchMessage(GameMessage.atlasBuildDomeGUI);  // Caso particolare in cui ATLAS vuole costruire una cupola
                else
                    updateMatchMessage(GameMessage.atlasBuildNormalGUI);  // Caso normale
                break;
            case PROMETHEUS:
                if (response)
                    updateMatchMessage(GameMessage.GUIChooseweretobuild);  // Caso particolare in cui PROMETHEUS vuole costruire prima di muoversi
                else
                    updateMatchMessage(GameMessage.GUIChooseweretomove);   // Caso normale
                break;
            case HEPHAESTUS:
                if (response)
                    updateMatchMessage(GameMessage.hephaesthusBuildTwiceGUI);  // Caso particolare in cui HEPHAESTUS vuole costruire 2 volte
                else
                    updateMatchMessage(GameMessage.GUIChooseweretobuild);  // Caso normale
                break;
        }
    }

}
