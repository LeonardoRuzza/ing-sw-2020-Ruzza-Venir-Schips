package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Worker;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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

    /**Send to server-side the selection of the correspondent color of the clicked colorLabel (calling sendNotification of SantoriniGUI).
     * <p>
     * @param e the MouseEvent
     */
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

    /**Show borders of the mouseEntered colorLabel.
     * <p>
     * @param e the MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        ColorLabel label = (ColorLabel) e.getSource();
        Border border = BorderFactory.createLineBorder(convertToColor(label.getColor()), 3);
        label.setBorder(border);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(label);
        topFrame.getContentPane().revalidate();
        topFrame.getContentPane().repaint();
    }

    /**Remove borders of the mouseExited colorLabel.
     * <p>
     * @param e the MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        ColorLabel label = (ColorLabel) e.getSource();
        label.setBorder(null);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(label);
        topFrame.getContentPane().revalidate();
        topFrame.getContentPane().repaint();
    }

    private Color convertToColor(Worker.Color workerColor){
        switch (workerColor){
            case RED: return Color.RED;
            case GREEN: return Color.GREEN;
            case YELLOW: return Color.YELLOW;
            case PURPLE: return new Color(128,0,128);
            default: return Color.BLACK;
        }
    }
}
