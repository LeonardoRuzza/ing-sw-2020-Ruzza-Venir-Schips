package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.utils.GameMessage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CellButtonListener extends JFrame implements MouseListener {
    private SantoriniGUI gui;
    private String coordinates;
    private GamePanel gamePanel;
    public CellButtonListener(SantoriniGUI gui, GamePanel gamePanel, GamePanel.CellButton button){
        this.gui = gui;
        this.gamePanel = gamePanel;
        coordinates = String.valueOf(button.getxCoord())+","+String.valueOf(button.getyCoord());
    }

    /**
     * On click this listener send to gui the string needed to use super power
     * @param e Mouse Event
     */
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
