package it.polimi.ingsw.client.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PrevListener implements MouseListener {
    private MasterCardPanel masterCardPanel;

    public PrevListener(MasterCardPanel masterCardPanel) {
        this.masterCardPanel = masterCardPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int numberOfPanel = masterCardPanel.getCurrentStatePanel();
        if(numberOfPanel <= Math.floor(masterCardPanel.getNumbOfAvailableCards()/4.0) && numberOfPanel>0){
            numberOfPanel--;
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
