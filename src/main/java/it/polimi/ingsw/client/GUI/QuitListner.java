package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class QuitListner extends JFrame  implements MouseListener {
    String quit = "quit";
    SantoriniGUI gui;

    public QuitListner(SantoriniGUI gui){
        this.gui = gui;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        gui.sendNotification(quit);
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
