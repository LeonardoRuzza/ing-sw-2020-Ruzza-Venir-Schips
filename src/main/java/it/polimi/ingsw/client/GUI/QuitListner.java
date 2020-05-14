package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

public class QuitListner extends JFrame  implements MouseListener {
    String quit = "quit";
    SantoriniGUI gui;
    JFrame frame;

    public QuitListner(SantoriniGUI gui, JFrame frame){
        this.gui = gui;
        this.frame = frame;
    }

    /**
     * On Click this method will send QUIT message to server and close the frame
     * @param e Mouse Event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        gui.sendNotification(quit);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
