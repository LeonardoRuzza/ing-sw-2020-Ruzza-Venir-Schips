package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NumberSelectionPanel extends JPanel {
    JLabel button2;
    JLabel button3;
    JLabel sysLabel;

    BufferedImage imageBtnPlay;
    BufferedImage imageLabelSys;
    BufferedImage imageBtn2;
    BufferedImage imageBtn3;

    MouseListener listenerTwo;
    MouseListener listenerThree;

    ClientGUI clientGUI;

    protected NumberSelectionPanel(ClientGUI gui) throws IOException {
        clientGUI = gui;
        imageBtnPlay = ImageIO.read(new File("src/main/resources/btn_play.png"));
        imageLabelSys = ImageIO.read(new File("src/main/resources/sys_label_player_num.png"));
        imageBtn2 = ImageIO.read(new File("src/main/resources/btn_numofplayer2.png"));
        imageBtn3 = ImageIO.read(new File("src/main/resources/btn_numofplayer3.png"));
        this.setSize(new Dimension(1920,1080));
        this.setLayout(null);
        button2 = new JLabel(new ImageIcon(imageBtn2));
        button3 = new JLabel(new ImageIcon(imageBtn3));
        sysLabel = new JLabel(new ImageIcon(imageLabelSys));

        sysLabel.setBounds(651,188,619,207);

        button2.setBounds(752,503,208,105);
        listenerTwo = new TwoPlayersButtonListner(clientGUI,button3,button2,listenerThree);
        button2.addMouseListener(listenerTwo);

        button3.setBounds(960,503,208,104);
        listenerThree = new ThreePlayersButtonListner(clientGUI,button3,button2,listenerTwo);
        button3.addMouseListener(listenerThree);

        this.setOpaque(true);
        this.setBackground(new Color(0,0,0,0));
        this.add(button2);
        this.add(button3);
        this.add(sysLabel);

    }
}
