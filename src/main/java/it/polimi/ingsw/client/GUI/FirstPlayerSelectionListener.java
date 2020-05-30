package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

public class FirstPlayerSelectionListener implements MouseListener {
    String pName;
    SantoriniGUI gui;

    public FirstPlayerSelectionListener(SantoriniGUI gui, String pName){
        this.gui = gui;
        this.pName = pName;
    }

    /**
     * On Click this method will send QUIT message to server and close the frame
     * @param e Mouse Event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(pName != null){
            gui.sendNotification(pName);
        }
    }

    /**
     * Mouse Pressed Event
     * @param e Mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Mouse Released Event
     * @param e Mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    /**
     * Mouse Entered Event
     * @param e Mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    /**
     * Mouse Exited Event
     * @param e Mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
