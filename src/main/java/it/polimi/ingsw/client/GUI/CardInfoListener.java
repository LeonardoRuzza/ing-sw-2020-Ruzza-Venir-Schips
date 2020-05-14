package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CardInfoListener extends JFrame implements MouseListener {
    private GamePanel.PlayerCardPanel panelCard;
    private boolean active = false;

    public CardInfoListener(GamePanel.PlayerCardPanel panel){
        this.panelCard = panel;
    }

    /**
     * On event this method show or hide the Panel Info Card
     * @param e Mouse event
     */
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
