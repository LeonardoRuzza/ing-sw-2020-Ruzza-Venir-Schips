package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ThreePlayersButtonListner extends JFrame implements MouseListener {

    private final int nPlayers = 3;
    SantoriniGUI clientGUI;
    JLabel btnThree;
    JLabel btnTwo;
    MouseListener listenerTwo;

    public ThreePlayersButtonListner(SantoriniGUI gui, JLabel btnThree, JLabel btnTwo, MouseListener listenerTwo){
        this.btnThree = btnThree;
        this.clientGUI = gui;
        this.btnTwo = btnTwo;
        this.listenerTwo = listenerTwo;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(clientGUI != null){
            clientGUI.sendNotification(Integer.toString(nPlayers));
            btnThree.removeMouseListener(this);
            btnTwo.removeMouseListener(listenerTwo);
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
