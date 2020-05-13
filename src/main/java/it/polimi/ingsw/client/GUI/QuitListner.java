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
    @Override
    public void mouseClicked(MouseEvent e) {
        gui.sendNotification(quit);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
