package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;

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
    PlayButtonListner listner;
    JLabel imageTextBox;
    JLabel sysLabel;
    JTextField nicknameTxtField;

    ClientGUI clientGUI;

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
        listner = new PlayButtonListner(nicknameTxtField, playButton, clientGUI);
        playButton.addMouseListener(listner);

        this.setOpaque(true);
        this.setBackground(new Color(0,0,0,0));
        this.add(nicknameTxtField);
        this.add(imageTextBox);
        this.add(sysLabel);
        this.add(playButton);
    }

    public void showNameAlreadyTokenDialog(JFrame f){
        int dialogResult = JOptionPane.showOptionDialog(f, "This name is already taken", "Nickname Error", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,"Cancel");
        if(dialogResult == JOptionPane.ERROR_MESSAGE || dialogResult == JOptionPane.CLOSED_OPTION){
            nicknameTxtField.setText("nickname");
            playButton.addMouseListener(listner);
        }
    }
}
