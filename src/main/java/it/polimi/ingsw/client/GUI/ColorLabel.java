package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Worker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ColorLabel extends JLabel {
    private final Worker.Color color;
    private final SantoriniGUI santoriniGUI;

    /**Create a new ColorLabel.
     * <p>
     * @param color the color associated to the label
     * @param santoriniGUI the associated santoriniGUI
     */
    public ColorLabel(Worker.Color color, SantoriniGUI santoriniGUI) {
        this.santoriniGUI = santoriniGUI;
        Image buttonIcon = null;
        this.color=color;
        switch (color) {
            case RED:
                buttonIcon =  santoriniGUI.loadImage("wrk_red_big");
                break;
            case YELLOW:
                buttonIcon = santoriniGUI.loadImage("wrk_yellow_big");
                break;
            case GREEN:
                buttonIcon = santoriniGUI.loadImage("wrk_green_big");
                break;
            case PURPLE:
                buttonIcon = santoriniGUI.loadImage("wrk_purple_big");
                break;
            default:
                break;
        }
        if(buttonIcon == null){
            setText(color.toString());
            setBackground(Color.getColor(color.toString()));
            return;
        }
        setIcon(new ImageIcon(buttonIcon));
        setFocusable(false);
    }

    /**Getter for the color.
     * <p>
     * @return {@code Worker.Color}
     */
    public Worker.Color getColor() {
        return color;
    }
}
