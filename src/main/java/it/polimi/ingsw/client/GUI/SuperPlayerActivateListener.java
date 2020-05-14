package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.utils.GameMessage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SuperPlayerActivateListener extends JFrame implements MouseListener {
    boolean activate;
    String superPower;
    GamePanel boardPanel;
    SantoriniGUI gui;

    public SuperPlayerActivateListener(SantoriniGUI gui, GamePanel boardPanel, boolean activate, String superPower){
        this.boardPanel = boardPanel;
        this.activate = activate;
        this.superPower = superPower;
        this.gui = gui;
    }

    /**
     * On Click this method will set the string which will be sent to gui if click on cancel send NO
     * @param e Mouse Event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(activate){
            boardPanel.setSuperPower(superPower);
            boardPanel.hideSuperPowerDialog();
            gui.superPlayerChoose(true);
        }else{
            if(superPower.equals(GameMessage.turnMessageNO)){
                boardPanel.setSuperPower("");
                boardPanel.hideSuperPowerDialog();
                gui.sendNotification(superPower);
                return;
            }
            boardPanel.setSuperPower("");
            boardPanel.hideSuperPowerDialog();
            gui.superPlayerChoose(false);
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
