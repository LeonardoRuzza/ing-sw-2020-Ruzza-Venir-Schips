package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.utils.GameMessage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SuperPlayeAcitvateListner extends JFrame implements MouseListener {
    boolean activate;
    String superPower = "";
    GamePanel boardPanel;
    SantoriniGUI gui;

    public SuperPlayeAcitvateListner(SantoriniGUI gui, GamePanel boardPanel, boolean activate, String superPower){
        this.boardPanel = boardPanel;
        this.activate = activate;
        this.superPower = superPower;
        this.gui = gui;
    }

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
