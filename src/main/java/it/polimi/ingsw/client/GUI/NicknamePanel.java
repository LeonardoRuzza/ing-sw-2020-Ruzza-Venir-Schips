package it.polimi.ingsw.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NicknamePanel extends JPanel{

    BufferedImage imageBtnPlay;
    BufferedImage imageLabelSys;
    BufferedImage imageTxtField;
    JLabel playButton;
    PlayButtonListener listner;
    JLabel imageTextBox;
    JLabel sysLabel;
    JTextField nicknameTxtField;

    ClientGUI clientGUI;

    /**
     * This Class implements the panel which will ask the username
     * @param gui Class that will receive the user name
     * @throws IOException Exception
     */
    protected NicknamePanel(ClientGUI gui) throws IOException {
        this.clientGUI = gui;
        imageBtnPlay = ImageIO.read(new File("src/main/resources/btn_play.png"));
        imageLabelSys = ImageIO.read(new File("src/main/resources/sys_label_nickname.png"));
        imageTxtField = ImageIO.read(new File("src/main/resources/nickname_txtField.png"));
        this.setSize(new Dimension(1920,1080));
        this.setLayout(null);
        playButton = new JLabel(new ImageIcon(imageBtnPlay));
        imageTextBox = new JLabel(new ImageIcon(imageTxtField));
        sysLabel = new JLabel(new ImageIcon(imageLabelSys));
        nicknameTxtField = new JTextField("nickname");

        sysLabel.setBounds(651,188,619,207);

        imageTextBox.setBounds(689,437,525,118);
        nicknameTxtField.setBounds(715,449,470,70);
        nicknameTxtField.setBackground(new Color(0,0,0,0));
        nicknameTxtField.setOpaque(false);
        nicknameTxtField.setBorder(null);
        nicknameTxtField.setFont(new Font("ComicSansMS",Font.BOLD,30));
        nicknameTxtField.setForeground(Color.WHITE);
        nicknameTxtField.setHorizontalAlignment(JTextField.CENTER);

        playButton.setBounds(1597,684,323,396);
        listner = new PlayButtonListener(null,nicknameTxtField, playButton, clientGUI, listner);
        playButton.addMouseListener(listner);

        this.setOpaque(true);
        this.setBackground(new Color(0,0,0,0));
        this.add(nicknameTxtField);
        this.add(imageTextBox);
        this.add(sysLabel);
        this.add(playButton);
    }

    /**
     * This method create and show a panel which will notify to user that the name he choose isn't available
     * @param f frame which will sho the panel
     */
    public void showNameAlreadyTokenDialog(JFrame f){
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(null);
        screenPanel.setBounds(0,0,1920,1080);
        screenPanel.setOpaque(true);
        screenPanel.setBackground(new java.awt.Color(0,0,0,0));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setOpaque(true);
        infoPanel.setBackground(new java.awt.Color(0,0,0,0));

        String path = "src/main/resources/dialog_unavailable_name.png";
        BufferedImage backgroundImg = null;
        try {
            backgroundImg = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert backgroundImg != null;
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImg));
        backgroundLabel.setBounds(0,0,775,380);

        JLabel buttonOK = new JLabel("");
        buttonOK.setBounds(180,260,416,105);
        buttonOK.addMouseListener(new PlayButtonListener(f,null, playButton, null, listner));

        infoPanel.setBounds(671,295,775,380);
        infoPanel.add(buttonOK);
        infoPanel.add(backgroundLabel);
        infoPanel.setBounds(671,295,775,380);
        screenPanel.add(infoPanel);

        screenPanel.setBounds(0,0,1920,1080);
        f.getContentPane().add(screenPanel,0);
        f.getContentPane().revalidate();
        f.getContentPane().repaint();
    }
}
