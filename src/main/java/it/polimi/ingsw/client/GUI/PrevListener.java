package it.polimi.ingsw.client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrevListener implements ActionListener {
    private MasterCardPanel masterCardPanel;

    public PrevListener(MasterCardPanel masterCardPanel) {
        this.masterCardPanel = masterCardPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int numberOfPanel = masterCardPanel.getCurrentStatePanel();
        if(numberOfPanel <= Math.floor(masterCardPanel.getNumbOfAvailableCards()/4.0) && numberOfPanel>0){
            numberOfPanel++;
            masterCardPanel.setCurrentStatePanel(numberOfPanel);
            masterCardPanel.setjPanelCards(numberOfPanel+3+3*(numberOfPanel-1));
        }
    }
}
