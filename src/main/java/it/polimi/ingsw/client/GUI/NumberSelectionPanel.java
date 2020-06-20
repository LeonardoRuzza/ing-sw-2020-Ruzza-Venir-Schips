package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;

public class NumberSelectionPanel extends JPanel {
    JLabel button2;
    JLabel button3;
    JLabel sysLabel;

    Image imageBtnPlay;
    Image imageLabelSys;
    Image imageBtn2;
    Image imageBtn3;

    MouseListener listenerTwo;
    MouseListener listenerThree;

    SantoriniGUI gui;

    /**
     * This Class implements the panel which will ask the number of players in the match
     * @param gui Class that will receive the number
     * @throws IOException Exception
     */
    protected NumberSelectionPanel(SantoriniGUI gui) throws IOException {
        this.gui = gui;
        imageBtnPlay = gui.loadImage("btn_play");
        imageLabelSys = gui.loadImage("sys_label_player_num");
        imageBtn2 = gui.loadImage("btn_numofplayer2");
        imageBtn3 = gui.loadImage("btn_numofplayer3");
        this.setSize(new Dimension(1920,1080));
        this.setLayout(null);
        button2 = new JLabel(new ImageIcon(imageBtn2));
        button3 = new JLabel(new ImageIcon(imageBtn3));
        sysLabel = new JLabel(new ImageIcon(imageLabelSys));

        sysLabel.setBounds(651,188,619,207);

        button2.setBounds(752,503,208,105);
        listenerTwo = new TwoPlayersButtonListener(gui,button3,button2,listenerThree);
        button2.addMouseListener(listenerTwo);

        button3.setBounds(960,503,208,104);
        listenerThree = new ThreePlayersButtonListener(gui,button3,button2,listenerTwo);
        button3.addMouseListener(listenerThree);

        this.setOpaque(true);
        this.setBackground(new Color(0,0,0,0));
        this.add(button2);
        this.add(button3);
        this.add(sysLabel);

    }
}
