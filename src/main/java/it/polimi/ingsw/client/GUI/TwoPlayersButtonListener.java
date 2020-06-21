package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TwoPlayersButtonListener extends JFrame implements MouseListener {

    private final int nPlayers = 2;
    private SantoriniGUI clientGUI;
    private JLabel btnThree;
    private JLabel btnTwo;
    private MouseListener listenerThree;

    public TwoPlayersButtonListener(SantoriniGUI gui, JLabel btnThree, JLabel btnTwo, MouseListener listenerThree){
        this.btnThree = btnThree;
        this.clientGUI = gui;
        this.btnTwo = btnTwo;
        this.listenerThree = listenerThree;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(clientGUI != null){
            clientGUI.sendNotification(Integer.toString(nPlayers));
            btnTwo.removeMouseListener(this);
            btnThree.removeMouseListener(listenerThree);
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
