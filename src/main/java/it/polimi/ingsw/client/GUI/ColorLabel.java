package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Worker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColorLabel extends JLabel {
    private final Worker.Color color;

    public ColorLabel(Worker.Color color, SantoriniGUI santoriniGUI) {
        Image buttonIcon = null; //oppure usare BufferedImage?
        this.color=color;
        try {
            switch (color) {
                case RED:
                    buttonIcon = ImageIO.read(new File("src/main/resources/wrk_red_big.png"));
                    break;
                case YELLOW:
                    buttonIcon = ImageIO.read(new File("src/main/resources/wrk_yellow_big.png"));
                    break;
                case GREEN:
                    buttonIcon = ImageIO.read(new File("src/main/resources/wrk_green_big.png"));
                    break;
                case PURPLE:
                    buttonIcon = ImageIO.read(new File("src/main/resources/wrk_purple_big.png"));
                    break;
                default:
                    break;
            }
        }
        catch (IOException e){
            System.out.println("Error trying to open worker image");
            setText(color.toString());
            setBackground(Color.getColor(color.toString()));
            return;
        }
        if(buttonIcon == null){
            setText(color.toString());
            setBackground(Color.getColor(color.toString()));
            return;
        }
        setIcon(new ImageIcon(buttonIcon));
        setFocusable(false);
    }

    public Worker.Color getColor() {
        return color;
    }
}
