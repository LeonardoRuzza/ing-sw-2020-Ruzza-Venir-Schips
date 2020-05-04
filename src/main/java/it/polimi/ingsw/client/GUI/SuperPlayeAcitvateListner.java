package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SuperPlayeAcitvateListner extends JFrame implements MouseListener {
    boolean activate;
    String superPower = "";
    GamePanel boardPanel;

    public SuperPlayeAcitvateListner(GamePanel boardPanel, boolean activate, String superPower){
        this.boardPanel = boardPanel;
        this.activate = activate;
        this.superPower = superPower;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(activate){
            boardPanel.setSuperPower(superPower);
            boardPanel.hideSuperPowerDialog();
        }else{
            boardPanel.setSuperPower("");
            boardPanel.hideSuperPowerDialog();
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
