package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class CardListener implements MouseListener {
    private SantoriniGUI santoriniGUI;
    private static List<Card> selectedCards = new ArrayList<>();
    private int numberOfSelectableCards;
    private static List<CardLabel> connectedLabels = new ArrayList<>();

    public CardListener(SantoriniGUI santoriniGUI, int numberOfSelectableCards, CardLabel cardLabel) {
        this.santoriniGUI = santoriniGUI;
        this.numberOfSelectableCards = numberOfSelectableCards;
        connectedLabels.add(cardLabel);
    }

    public static List<Integer> getNumbersOfSelectedCards(){
        List<Integer> result= new ArrayList<>();
        for(Card c: selectedCards){
            result.add(c.getNumber());
        }
        return result;
    }

    public static List<Card> getSelectedCards(){
        return new ArrayList<>(selectedCards);
    }


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
                selectedCards.add(card);
                cardLabel.selectCard();
                for(Card c:selectedCards){
                    santoriniGUI.sendNotification(c.getName());
                }
                selectedCards.clear();
                for(CardLabel cl: connectedLabels){
                    if(cl!=null) {
                        cl.removeMouseListener(cl.getMouseListeners()[0]);
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

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
