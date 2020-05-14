package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Worker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ColorLabel extends JLabel {
    private final Worker.Color color;

    /**Create a new ColorLabel.
     * <p>
     * @param color the color associated to the label
     */
    public ColorLabel(Worker.Color color) {
        Image buttonIcon = null;
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

    /**Getter for the color.
     * <p>
     * @return {@code Worker.Color}
     */
    public Worker.Color getColor() {
        return color;
    }
}
