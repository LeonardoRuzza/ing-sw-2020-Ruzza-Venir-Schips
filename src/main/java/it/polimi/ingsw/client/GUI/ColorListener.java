package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ColorListener implements ActionListener {
    private SantoriniGUI santoriniGUI;
    private static List<ColorButton> connectedButtons = new ArrayList<>();

    public ColorListener(SantoriniGUI santoriniGUI, ColorButton colorButton) {
        this.santoriniGUI = santoriniGUI;
        connectedButtons.add(colorButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ColorButton colorButton = (ColorButton) e.getSource();
        String s = colorButton.getColor().toString();
        santoriniGUI.sendNotification(s);
        for(ColorButton cb:connectedButtons) {
            if(cb!=null) {
                cb.removeActionListener(this);
            }
        }
    }
}
