package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CardListener implements ActionListener {
    private SantoriniGUI santoriniGUI;
    private static List<Card> selectedCards;
    private int numberOfSelectableCards;
    private static List<CardButton> connectedButtons = new ArrayList<>();

    public CardListener(SantoriniGUI santoriniGUI, int numberOfSelectableCards, CardButton cardButton) {
        this.santoriniGUI = santoriniGUI;
        this.numberOfSelectableCards = numberOfSelectableCards;
        connectedButtons.add(cardButton);
        selectedCards = new ArrayList<>();
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
    public void actionPerformed(ActionEvent e) {
        CardButton cardButton = (CardButton) e.getSource();
        Card card = cardButton.getCard();
        if(selectedCards.contains(card)){
            cardButton.deselectCard();
            selectedCards.remove(card);
        }
        else {
            if(selectedCards.size() == numberOfSelectableCards -1){
                selectedCards.add(card);
                cardButton.selectCard();
                for(Card c:selectedCards){
                    santoriniGUI.sendNotification(c.getName());
                }
                selectedCards.clear();
                for(CardButton cb:connectedButtons){
                    if(cb!=null) {
                        cb.removeActionListener(this);
                    }
                }
            }
            else{
                if(selectedCards.size() >= numberOfSelectableCards) return; //maybe not possible case
                selectedCards.add(card);
                cardButton.selectCard();
            }

        }
    }
}
