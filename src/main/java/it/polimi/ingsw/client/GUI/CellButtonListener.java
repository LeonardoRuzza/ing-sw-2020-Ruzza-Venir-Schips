package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.utils.GameMessage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CellButtonListener extends JFrame implements MouseListener {
    SantoriniGUI gui;
    String coordinates;
    GamePanel gamePanel;
    public CellButtonListener(SantoriniGUI gui, GamePanel gamePanel, GamePanel.CellButton button){
        this.gui = gui;
        this.gamePanel = gamePanel;
        coordinates = String.valueOf(button.getxCoord())+","+String.valueOf(button.getyCoord());
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(coordinates!=null){
            if(gui!=null){
                String result;
                if(!gamePanel.getSuperPower().equals("")){
                    result = coordinates + "," + gamePanel.getSuperPower();
                    gamePanel.setSuperPower("");
                    gui.sendNotification(result);
                    return;
                }
                gui.sendNotification(coordinates);
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
