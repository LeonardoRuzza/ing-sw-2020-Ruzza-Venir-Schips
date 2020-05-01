package it.polimi.ingsw.client.GUI;

import java.lang.Math;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextListener implements ActionListener {
    private MasterCardPanel masterCardPanel;

    public NextListener(MasterCardPanel masterCardPanel) {
        this.masterCardPanel = masterCardPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int numberOfPanel = masterCardPanel.getCurrentStatePanel();
        if(numberOfPanel < Math.floor(masterCardPanel.getNumbOfAvailableCards()/4.0)){
            numberOfPanel++;
            masterCardPanel.setCurrentStatePanel(numberOfPanel);
            masterCardPanel.setjPanelCards(numberOfPanel+3+3*(numberOfPanel-1));
        }
    }
}
