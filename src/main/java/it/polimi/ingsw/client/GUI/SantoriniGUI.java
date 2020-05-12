package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.utils.GameMessage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class SantoriniGUI {
    private  ClientGUI clientGUI;
    private StateOfGUI currentStateOfGUI;
    private JFrame frame = new JFrame("Santorini");
    private JPanel currentPanel;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    public Clip soundThread;
    private VolumeButtonListner volumeListner;
    private JPanel settingsPanel;
    private JLabel quitButton;

    public SantoriniGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
        soundThread = playSound("background.wav");
        settingsPanel = createSettingsPanel();
        createAndStartGUI();
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
        setCursor();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addBackgroundImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/resources/background_lobby.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel background = new JPanel();
        background.setLayout(null);
        background.setBounds(0,0,1920,1080);
        assert image != null;
        JLabel backGrIm = new JLabel(new ImageIcon(image));
        backGrIm.setBounds(0,0,1920,1080);
        background.add(backGrIm);
        frame.getContentPane().add(background,1);
    }

    private Clip playSound(final String fileName) {
        Clip sound = null;
        try {
            Clip clip = AudioSystem.getClip();
            InputStream audioSrc = new FileInputStream("src/main/resources/sound/" + fileName);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip.open(audioStream);
            if(volumeListner != null){
                if(volumeListner.getActive()){
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }else{
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            sound = clip;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return sound;
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
        removeSettingsPanel();
        frame.getContentPane().remove(0);
        frame.getContentPane().invalidate();
        frame.getContentPane().add(panel,0);
        addSettingsPanel();
        frame.getContentPane().validate();
        frame.repaint();
    }

    private JPanel createSettingsPanel(){
        JPanel volumePanel = new JPanel();
        volumePanel.setLayout(null);
        volumePanel.setOpaque(true);
        volumePanel.setBackground(new Color(0,0,0,0));

        BufferedImage volumeImage = null;
        try {
            volumeImage = ImageIO.read(new File("src/main/resources/volume.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage muteImage = null;
        try {
            muteImage = ImageIO.read(new File("src/main/resources/mute_volume.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage quitImage = null;
        try {
            quitImage = ImageIO.read(new File("src/main/resources/btn_quit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        quitButton = new JLabel(new ImageIcon(quitImage));
        JLabel volume = new JLabel(new ImageIcon(volumeImage));
        JLabel mute = new JLabel(new ImageIcon(muteImage));
        volumeListner = new VolumeButtonListner(mute,volume,soundThread, frame);

        mute.addMouseListener(volumeListner);
        volume.addMouseListener(volumeListner);
        quitButton.addMouseListener(new QuitListner(this));
        volume.setBounds(1773,81,101,89);
        volumePanel.add(volume,0);
        mute.setBounds(1773,81,101,89);
        volumePanel.add(mute,1);
        quitButton.setBounds(1773,170,101,26);
        volumePanel.add(quitButton);
        mute.setVisible(false);
        quitButton.setVisible(false);
        volumePanel.setBounds(0,0,1920,1080);
        return volumePanel;
    }
    public void addSettingsPanel(){
        settingsPanel.setBounds(0,0,1920,1080);
        frame.getContentPane().add(settingsPanel,0);
    }
    public void removeSettingsPanel(){
        frame.getContentPane().remove(settingsPanel);
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
                    addBackgroundImage();
                    addSettingsPanel();
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
                quitButton.setVisible(true);
                switchPanel(gamePanel);
                for (MessageToGUI.PlayerSummary p: message.getPlayerSummaries()){
                    gamePanel.updateCardPanel((p.getPlayerNumber()), p.getNickname(), p.getCardName(), p.getColor());
                }
                soundThread.stop();
                soundThread = playSound("match_background.wav");
                volumeListner.setSound(soundThread);
                break;
        }
    }

    protected  void updatePanelForQuiteAndLose(String message){
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.askUseGeneralDialog(message);
    }

    protected void updateMatchMessage(String message) {
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.updateServerMessage(message);
    }

    protected void updateBoard(Board board){
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.updateGrid(board);
    }

    public void updateWin(boolean winORlose){
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.showEndGameDialog(winORlose);
        if(winORlose){
            soundThread.stop();
            soundThread = playSound("winner_sound.wav");
            volumeListner.setSound(soundThread);
            soundThread.start();
        }else{
            soundThread.stop();
            soundThread = playSound("loser_sound.wav");
            volumeListner.setSound(soundThread);
            soundThread.start();
        }
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
            case HESTIADEMETER:
                currentStateOfGUI = message.getStateOfGUI();
                gamePanel.askUseSuperPower(GameMessage.hestiaDemeterTurnMessageAskTwoBuildGUI);
                break;
            case HESTIADEMETERFAIL:
                gamePanel.askUseSuperPower(GameMessage.hestiaDemeterTurnMessageFailOptionalBuildWNewCellGUI);
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
                    updateMatchMessage(GameMessage.hephaesthusBuildTwiceGUI);  // Caso particolare in cui HEPHAESTUS vuole costruire 2 volte sulla stessa cella
                else
                    updateMatchMessage(GameMessage.GUIChooseweretobuild);  // Caso normale
                break;
            case HESTIADEMETER:
                if (response)
                    updateMatchMessage(GameMessage.GUIChooseweretobuild);   // Caso in cui HESTIA o DEMETER vogliono costruire una seconda volta
                break;
        }
    }

}
