package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class CardListener implements MouseListener {
    private final SantoriniGUI santoriniGUI;
    private List<Card> selectedCards = new ArrayList<>();
    private final int numberOfSelectableCards;
    private List<CardLabel> connectedLabels = new ArrayList<>();
    private JPanel cardInfo;
    private boolean stopSelection = false;

    /**Create a new CardListener.
     * <p>
     * @param santoriniGUI the santoriniGUI to associate to this
     * @param numberOfSelectableCards the number of selectable cards
     */
    public CardListener(SantoriniGUI santoriniGUI, int numberOfSelectableCards){
        this.santoriniGUI = santoriniGUI;
        this.numberOfSelectableCards = numberOfSelectableCards;
    }

    /**Add one CardLabel to the list connectedLabels.
     * <p>
     * @param cardLabel the cardLabel which must be added to connectedLabels
     */
    public void addConnectedLabel(CardLabel cardLabel){
        connectedLabels.add(cardLabel);
    }

    /**This method convert the selectedCards List in a correspondent List which contains the number of the Card contained in selectedCards.
     * <p>
     * @return {@code List<Integer>}
     */
    public List<Integer> getNumbersOfSelectedCards(){
        List<Integer> result= new ArrayList<>();
        for(Card c: selectedCards){
            result.add(c.getNumber());
        }
        return result;
    }

    public List<Card> getSelectedCards(){
        return new ArrayList<>(selectedCards);
    }

    /**Add the pressed CardLabel to selectedCard List and call the method selectCard of CardLabel but if it is just contained remove it calling deselectCard.
     * Also if the number of selected cards reach the number of the player, it use sendNotification of SantoriniGUI to communicate with the server-side and remove or block the others listeners of the elements of the current window.
     * <p>
     * @param e the MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        CardLabel cardLabel = (CardLabel) e.getSource();
        Card card = cardLabel.getCard();
        if(selectedCards.contains(card)){
            cardLabel.deselectCard();
            selectedCards.remove(card);
        }
        else {
            if(selectedCards.size() == numberOfSelectableCards -1){
                stopSelection = true;
                selectedCards.add(card);
                cardLabel.selectCard();
                for(Card c:selectedCards){
                    santoriniGUI.sendNotification(c.getName());
                }
                selectedCards.clear();
                for(CardLabel cl: connectedLabels){
                    if(cl!=null) {
                        cl.removeMouseListener(this);
                    }
                }
                connectedLabels.clear();
            }
            else{
                if(selectedCards.size() >= numberOfSelectableCards) return; //maybe not possible case
                selectedCards.add(card);
                cardLabel.selectCard();
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**Show the description of the power of the cards on mouseEntered event on the correspondent cardLabel.
     * <p>
     * @param e the MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        CardLabel cardLabel = (CardLabel) e.getSource();
        Card card = cardLabel.getCard();
        Container cardContainer = cardLabel.getParent().getParent();
        String cardDescription = card.getDesc();
        JTextArea jTextArea= new JTextArea(cardDescription);
        jTextArea.setFont(new Font("ComicSansMS",Font.BOLD,20));
        jTextArea.setForeground(Color.BLACK);
        jTextArea.setEditable(false);
        jTextArea.setHighlighter(null);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setOpaque(true);
        jTextArea.setBackground(new Color(236,228,212,255));
        cardInfo = new JPanel();
        cardInfo.setLayout(null);
        cardInfo.add(jTextArea,0);
        cardInfo.setBounds(cardLabel.getBounds().x+100,cardLabel.getParent().getBounds().y+405,290,175);
        jTextArea.setBounds(0,0,cardInfo.getBounds().width,cardInfo.getBounds().height);
        cardContainer.add(cardInfo,0);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(cardLabel);
        topFrame.getContentPane().revalidate();
        topFrame.getContentPane().repaint();
    }

    /**Remove the description of the power of the cards on mouseExited event on the correspondent cardLabel.
     * <p>
     * @param e the MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        CardLabel cardLabel = (CardLabel) e.getSource();
        if(cardInfo!=null){
            cardInfo.setVisible(false);
            cardInfo.getParent().remove(cardInfo);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(cardLabel);
            topFrame.getContentPane().revalidate();
            topFrame.getContentPane().repaint();
        }
    }

    public boolean isStopSelection() {
        return stopSelection;
    }
}
