package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Worker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ColorPanel extends JPanel {

    public ColorPanel(SantoriniGUI santoriniGUI, List<Worker.Color> availableColors) {
        int x=0;
        int buttonHeight = 312;
        int buttonWidth = 188;
        int y = 435;
        setSize(1920,1080);
        setLayout(null);
        add(createColorLabel());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(146, y, 1628, buttonHeight);
        for(Worker.Color temp: availableColors){
            ColorButton colorButton= new ColorButton(temp, santoriniGUI);
            jPanel.add(colorButton);
            colorButton.setBounds(x, 0, buttonWidth, buttonHeight);
            colorButton.setBorder(null);
            x+=480;
        }
        jPanel.setOpaque(true);
        jPanel.setBackground(new Color(0,0,0,0));
        add(jPanel);
        setOpaque(true);
        setBackground(new Color(0,0,0,0));
    }

    private JLabel createColorLabel(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/sys_label_select_color.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open color label image");
            return new JLabel("Error: image not found");
        }
        if(buttonIcon == null) return new JLabel("Choose your color:");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(651,188,619,207);
        return jLabel;
    }

    public static JLabel createWaitingColorLabel(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/sys_label_wait_players.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open color waiting label image");
            return new JLabel("Error: image not found");
        }
        if(buttonIcon == null) return new JLabel("Waiting others players choose of color");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return jLabel;
    }
}
