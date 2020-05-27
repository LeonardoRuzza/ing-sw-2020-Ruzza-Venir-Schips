package it.polimi.ingsw.client.GUI;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class NicknamePanel extends JPanel{

    Image imageBtnPlay;
    Image imageLabelSys;
    Image imageTxtField;
    JLabel playButton;
    PlayButtonListener listner;
    JLabel imageTextBox;
    JLabel sysLabel;
    JTextField nicknameTxtField;

    SantoriniGUI gui;

    /**
     * This Class implements the panel which will ask the username
     * @param gui Class that will receive the user name
     * @throws IOException Exception
     */
    protected NicknamePanel( SantoriniGUI gui) throws IOException {
        this.gui = gui;
        imageBtnPlay = gui.loadImage("btn_play");
        imageLabelSys = gui.loadImage("sys_label_nickname");
        imageTxtField = gui.loadImage("nickname_txtField");
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
        listner = new PlayButtonListener(null,nicknameTxtField, playButton, gui, listner);
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
        Image backgroundImg = gui.loadImage("dialog_unavailable_name");
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
