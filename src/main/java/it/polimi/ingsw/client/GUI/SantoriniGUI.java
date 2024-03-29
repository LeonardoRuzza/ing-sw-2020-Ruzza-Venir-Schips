package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.utils.GameMessage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class SantoriniGUI {
    private ClientGUI clientGUI;
    private StateOfGUI currentStateOfGUI;
    private JFrame frame = new JFrame("Santorini");
    private JPanel currentPanel;
    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private Clip soundThread;
    private VolumeButtonListener volumeListener;
    private JPanel settingsPanel;
    private JLabel quitButton;

    /**
     * Simply call the createAndStartGUI, to associate a GUI to the Client
     * <p>
     * <b>For the lobby phase</b>GUI consist of a main frame, with various panels that will be switched to support user interactions ( choose of name, color, card)
     * <p>
     * <b>For the normal game</b> GUI consist in a main frame, with various element, such as buttons, to support user interaction showing an Image of Current state of the Board
     * @param clientGUI the Client associated to the GUI
     * @see #createAndStartGUI()
     */
    public SantoriniGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
        soundThread = playSound("background2");
        settingsPanel = createSettingsPanel();
        createAndStartGUI();
    }

    /**
     * Send the player input from the GUI, converted in String, to the asyncWriteToSocket
     * @param s  String to send
     * @see ClientGUI
     */
    public void sendNotification(String s){
        clientGUI.outcomeGUI.add(s);
    }

    /**
     * Sett the various setting for the main framework
     * <b>Resolution</b>: 1920x1080
     */
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

    /**
     * Add a background image; This will be shown only in the <b>Lobby phase</b>
     */
    private void addBackgroundImage() {
        Image image = loadImage("background_lobby");
        JPanel background = new JPanel();
        background.setLayout(null);
        background.setBounds(0,0,1920,1080);
        assert image != null;
        JLabel backGrIm = new JLabel(new ImageIcon(image));
        backGrIm.setBounds(0,0,1920,1080);
        background.add(backGrIm);
        frame.getContentPane().add(background,1);
    }

    /**
     * Add a background sound, which is played in loop
     * @param fileName Name of the sound file
     * @return Clip associated to the sound specified in the parameter
     */
    private Clip playSound(final String fileName) {
        Clip sound = null;
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioStream = loadSound(fileName);
            if(audioStream==null){
                return clip;
            }
            clip.open(audioStream);
            if(volumeListener != null){
                if(volumeListener.getActive()){
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

    /**
     * Set a customized cursor to the replace the standard SO cursor
     */
    private void setCursor(){
        Image image = loadImage("mouse_pointer");
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "customCursor");
        frame.setCursor(customCursor);
    }

    /**
     * Method to remove the current panel, and show a new one passed as parameter; This also add the settings panel to the new panel
     *<p>
     * This is meant for the <b>Lobby phase</b> to switch from various panels (choose of card, color...)
     * @param panel The Jpanel that need to be shown
     */
    private void switchPanel(JPanel panel){
        removeSettingsPanel();
        frame.getContentPane().remove(0);
        frame.getContentPane().invalidate();
        frame.getContentPane().add(panel,0);
        addSettingsPanel();
        frame.getContentPane().validate();
        frame.repaint();
    }

    /**
     * Add a setting panel in the top right of frame, to allow user quit the game, sett or disable sounds
     * @return The panel just created
     */
    private JPanel createSettingsPanel(){
        JPanel volumePanel = new JPanel();
        volumePanel.setLayout(null);
        volumePanel.setOpaque(true);
        volumePanel.setBackground(new Color(0,0,0,0));

        Image volumeImage = loadImage("volume");
        Image muteImage = loadImage("mute_volume");
        Image quitImage = loadImage("btn_quit");
        assert quitImage != null;
        quitButton = new JLabel(new ImageIcon(quitImage));
        assert volumeImage != null;
        JLabel volume = new JLabel(new ImageIcon(volumeImage));
        assert muteImage != null;
        JLabel mute = new JLabel(new ImageIcon(muteImage));
        volumeListener = new VolumeButtonListener(mute,volume,soundThread, frame);

        mute.addMouseListener(volumeListener);
        volume.addMouseListener(volumeListener);
        quitButton.addMouseListener(new QuitListener(this, frame));
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

    /**
     * Add the setting panel to the main frame
     */
    private void addSettingsPanel(){
        settingsPanel.setBounds(0,0,1920,1080);
        frame.getContentPane().add(settingsPanel,0);
    }

    /**
     * Remove the setting panel from the framework
     */
    private void removeSettingsPanel(){
        frame.getContentPane().remove(settingsPanel);
    }

    /**
     * Create a {@code JPanel} and add a label to it. Also set the JPanel to Opaque to not cover the main Panel
     * @param pathname Relative path of the image to be added in the {@code JLabel}
     */
    private void addLabel(String pathname){
        Image image = loadImage(pathname);
        JPanel temp = new JPanel();
        assert image != null;
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(651,188,619,207);
        temp.setSize(new Dimension(1920,1080));
        temp.setLayout(null);
        temp.setOpaque(true);
        temp.setBackground(new Color(0,0,0,0));
        temp.add(label);
        switchPanel(temp);
    }

    /**
     * <b>For the Lobby Phase</b>:
     * <p>
     *    It create and remove various panels, associated to the current StateOfGUI, that need to be shown to the User. For example show the panel for InsertName, ChoseColor
     * </p>
     * @param message Contains the current {@code StateOfGui} plus various info, to show available colors,card...
     * @see MessageToGUI
     * @see StateOfGUI
     */
    public void updateGUILobby(MessageToGUI message) {
        switch (message.getStateOfGUI()){
            case INSERTNAME:
                try {
                    currentPanel = new NicknamePanel(this);
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
                addLabel("sys_label_loading");
                break;
            case MASTERSELECTNUMBEROFPLAYERS:
                try {
                    currentPanel = new NumberSelectionPanel(this);
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
                addLabel("sys_label_wait_players");
                break;
            case CHOOSESTARTPLAYER:
                currentPanel = new FirstPlayerSelectionPanel(this, message.getPlayerSummaries());
                switchPanel(currentPanel);
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
                soundThread = playSound("match_background");
                volumeListener.setSound(soundThread);
                break;
        }
    }

    /**
     * Show a panel in case a player Lose or Quit the Game
     * @param message Message to show
     */
    protected  void updatePanelForQuiteAndLose(String message){
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.askUseGeneralDialog(false, message);
    }

    /**
     * Update the message show in the bottom of the {@code GamePanel}, to let the User know what to do
     * @param message Message that need to be shown
     * @see GamePanel
     */
    protected void updateMatchMessage(String message) {
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.updateServerMessage(message);
    }

    protected void updatePanelForQuitLobby(){
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(null);
        screenPanel.setBounds(0,0,1920,1080);
        screenPanel.setOpaque(true);
        screenPanel.setBackground(new java.awt.Color(0,0,0,0));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setOpaque(true);
        infoPanel.setBackground(new java.awt.Color(0,0,0,0));
        Image backgroundImg = loadImage("general_dialog");
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImg));
        backgroundLabel.setBounds(0,0,775,380);

        JLabel buttonOK = new JLabel("");
        buttonOK.setBounds(180,260,416,105);
        buttonOK.addMouseListener(new LobbyQuitListener(this));

        JTextArea jTextArea = new JTextArea(GameMessage.quitCloseConnection);
        jTextArea.setFont(new Font("ComicSansMS",Font.BOLD,40));
        jTextArea.setForeground(java.awt.Color.white);
        jTextArea.setEditable(false);
        jTextArea.setSelectionColor(new java.awt.Color(0,0,0,0));
        jTextArea.setHighlighter(null);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setOpaque(true);
        jTextArea.setBackground(new java.awt.Color(0,0,0,0));
        jTextArea.setBounds(0,9,775,260);

        infoPanel.setBounds(671,295,775,380);
        infoPanel.add(buttonOK);
        infoPanel.add(jTextArea);
        infoPanel.add(backgroundLabel);
        infoPanel.setBounds(671,295,775,380);
        screenPanel.add(infoPanel);

        screenPanel.setBounds(0,0,1920,1080);
        frame.getContentPane().add(screenPanel,0);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    /**
     * Update the current image of the Board to show to the User effect of his and other player's moves
     * @param board A cloned Board, with all relative informations
     * @see Board
     */
    protected void updateBoard(Board board){
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.updateGrid(board);
    }

    /**
     * Show a Panel, with relative sound effect, to User in case he Win or Lose the Game
     * @param winORlose {@code true} if a Player win; {@code false} if a player Lose
     */
    public void updateWin(boolean winORlose){
        GamePanel gamePanel = (GamePanel) currentPanel;
        gamePanel.showEndGameDialog(winORlose);
        if(winORlose){
            soundThread.stop();
            soundThread = playSound("winner_sound");
            volumeListener.setSound(soundThread);
            soundThread.start();
        }else{
            soundThread.stop();
            soundThread = playSound("loser_sound");
            volumeListener.setSound(soundThread);
            soundThread.start();
        }
    }

    /**
     * Show the various custom panels associated to the Cards, to let the User choose if use his card's Super Power or Not
     * @param message Contains info to know card chosen by User in order to show the associated Panel
     */
    protected void updateSuperPlayer(MessageToGUI message){
        GamePanel gamePanel = (GamePanel) currentPanel;
        switch (message.getStateOfGUI()) {
            case ARES:
               currentStateOfGUI = message.getStateOfGUI();
               gamePanel.askUseGeneralDialog(true, GameMessage.aresTurnMessageAskRemoveBlokGUI);
               break;
            case ARESFAIL:
                gamePanel.askUseGeneralDialog(true, GameMessage.aresTurnMessageFailRemoveBlokWNewCellGUI);
                break;
            case ATLAS:
                currentStateOfGUI = message.getStateOfGUI();
                gamePanel.askUseGeneralDialog(true, GameMessage.atlasTurnMessageAskBuildDorseGUI);
                break;
            case ATLASFAIL:
                gamePanel.askUseGeneralDialog(true, GameMessage.atlasTurnMessageFailBuildDorseGUI);
                break;
            case PROMETHEUS:
                currentStateOfGUI = message.getStateOfGUI();
                gamePanel.askUseGeneralDialog(true, GameMessage.prometheusTurnMessageAskBuildBeforeGUI);
                break;
            case HEPHAESTUS:
                currentStateOfGUI = message.getStateOfGUI();
                gamePanel.askUseGeneralDialog(true, GameMessage.hephaesthusTurnMessageAskBuildGUI);
                break;
            case HESTIADEMETER:
                currentStateOfGUI = message.getStateOfGUI();
                gamePanel.askUseGeneralDialog(true, GameMessage.hestiaDemeterTurnMessageAskTwoBuildGUI);
                break;
            case HESTIADEMETERFAIL:
                gamePanel.askUseGeneralDialog(true, GameMessage.hestiaDemeterTurnMessageFailOptionalBuildWNewCellGUI);
                break;
        }
    }

    /**
     * Call the {@code updateMatchMessage} to let User know after his choice of use his Card's Superpower or Not
     * @param response {@code true} if User choose to use his Card's Superpower; {@code} false otherwise
     */
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
    /**
     * load image
     * @param imageName name of image to without extension
     * @return loaded image
     */
    protected Image loadImage(String imageName){
        Image img = null;
        String pathString = Objects.requireNonNull(getClass().getClassLoader().getResource(imageName + ".png")).getFile();
        try {
            img = ImageIO.read(new File(pathString));
        } catch (IOException e) {
            InputStream pathString1 = getClass().getClassLoader().getResourceAsStream(imageName + ".png");
            try {
                assert pathString1 != null;
                img = ImageIO.read(pathString1);
            } catch (IOException eInt) {
                eInt.printStackTrace();
            }
        }
        return img;
    }
    protected AudioInputStream loadSound(String soundName){
        InputStream audioSrc = null;
        InputStream bufferedIn = null;
        AudioInputStream audioStream = null;
        String pathString = Objects.requireNonNull(getClass().getClassLoader().getResource("sound/" + soundName + ".wav")).getFile();
        try {
            audioSrc = new FileInputStream(pathString);
            bufferedIn = new BufferedInputStream(audioSrc);
            audioStream = AudioSystem.getAudioInputStream(bufferedIn);
        } catch (IOException | UnsupportedAudioFileException e) {

            try {
                InputStream pathString1 = getClass().getClassLoader().getResourceAsStream("sound/"+soundName + ".wav");
                assert pathString1 != null;
                bufferedIn = new BufferedInputStream(pathString1);
                audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            } catch (IOException | UnsupportedAudioFileException eInt) {
                eInt.printStackTrace();
            }
        }
        return audioStream;
    }
}
