package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CardInfoListner extends JFrame implements MouseListener {
    private GamePanel.PlayerCardPanel panelCard;
    private boolean active = false;

    public CardInfoListner(GamePanel.PlayerCardPanel panel){
        this.panelCard = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(active){
            active = false;
            this.panelCard.closeDialogPanelCardInfo();
        }else{
            active = true;
            this.panelCard.showDialogPanelCardInfo();
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
