package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Worker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ColorPanel extends JPanel {
    private ColorListener  colorListener;
    private final SantoriniGUI santoriniGUI;

    public ColorPanel(SantoriniGUI santoriniGUI, List<Worker.Color> availableColors) {
        int x=0;
        int colorLabelHeight = 312;
        int colorLabelWidth = 188;
        int y = 435;
        this.santoriniGUI = santoriniGUI;
        colorListener = new ColorListener(santoriniGUI);
        setSize(1920,1080);
        setLayout(null);
        add(createColorMessageLabel());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(146, y, 1628, colorLabelHeight);
        for(Worker.Color temp: availableColors){
            ColorLabel colorLabel = new ColorLabel(temp,santoriniGUI);
            colorLabel.addMouseListener(colorListener);
            colorListener.addColorLabel(colorLabel);
            jPanel.add(colorLabel);
            colorLabel.setBounds(x, 0, colorLabelWidth, colorLabelHeight);
            colorLabel.setBorder(null);
            x+=480;
        }
        jPanel.setOpaque(true);
        jPanel.setBackground(new Color(0,0,0,0));
        add(jPanel);
        setOpaque(true);
        setBackground(new Color(0,0,0,0));
    }

    private JLabel createColorMessageLabel(){
        Image buttonIcon;
        buttonIcon = santoriniGUI.loadImage("sys_label_select_color");
        if(buttonIcon == null) return new JLabel("Choose your color:");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(651,188,619,207);
        return jLabel;
    }
}
