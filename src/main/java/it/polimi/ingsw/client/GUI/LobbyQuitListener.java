package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

public class LobbyQuitListener implements MouseListener {
    SantoriniGUI gui;

    public LobbyQuitListener(SantoriniGUI gui) {
        this.gui = gui;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(e.getComponent());
        topFrame.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));
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
