package it.polimi.ingsw.client.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ColorListener implements MouseListener {
    private SantoriniGUI santoriniGUI;
    private List<ColorLabel> connectedLabels = new ArrayList<>();

    public ColorListener(SantoriniGUI santoriniGUI) {
        this.santoriniGUI = santoriniGUI;
    }

    public void addColorLabel(ColorLabel colorLabel){
        connectedLabels.add(colorLabel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ColorLabel colorLabel = (ColorLabel) e.getSource();
        String s = colorLabel.getColor().toString();
        santoriniGUI.sendNotification(s);
        for(ColorLabel cl: connectedLabels) {
            if(cl!=null) {
                cl.removeMouseListener(this);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
