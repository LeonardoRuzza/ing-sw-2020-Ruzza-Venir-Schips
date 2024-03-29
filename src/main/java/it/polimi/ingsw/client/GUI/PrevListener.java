package it.polimi.ingsw.client.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PrevListener implements MouseListener {
    private final MasterCardPanel masterCardPanel;
    private CardListener cardListener;

    /**Create a new PrevListener.
     * <p>
     * @param masterCardPanel the masterCardPanel to associate to this
     * @param cardListener the cardListener to associate to this
     */
    public PrevListener(MasterCardPanel masterCardPanel,CardListener cardListener) {
        this.masterCardPanel = masterCardPanel;
        this.cardListener = cardListener;
    }

    /**If there are other cards to show, change the currentStatePanel (decrementing of 1) of MasterCardPanel and call setjPanelCards to refresh the window.
     * It operates only if the selection phase is not finished (and currentStatePanel is {@literal >}0).
     * <p>
     * @param e the MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(cardListener.isStopSelection()){return;}
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
