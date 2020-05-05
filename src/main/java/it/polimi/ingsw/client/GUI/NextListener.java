package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.Math;
import java.awt.event.ActionEvent;

public class NextListener implements MouseListener {
    private MasterCardPanel masterCardPanel;
    private CardListener cardListener;

    public NextListener(MasterCardPanel masterCardPanel, CardListener cardListener) {
        this.masterCardPanel = masterCardPanel;
        this.cardListener = cardListener;
    }

    /**If there are other cards to show, change the currentStatePanel (incrementing of 1) of MasterCardPanel and call setjPanelCards to refresh the window.
     * It operates only if the selection phase is not finished.
     * <p>
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(cardListener.isStopSelection()){return;}
        int numberOfPanel = masterCardPanel.getCurrentStatePanel();
        if(numberOfPanel < Math.floor(masterCardPanel.getNumbOfAvailableCards()/4.0)){
            numberOfPanel++;
            masterCardPanel.setCurrentStatePanel(numberOfPanel);
            masterCardPanel.setjPanelCards(numberOfPanel+3+3*(numberOfPanel-1));
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
