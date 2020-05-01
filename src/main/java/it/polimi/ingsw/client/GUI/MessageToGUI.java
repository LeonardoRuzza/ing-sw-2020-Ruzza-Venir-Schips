package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class MessageToGUI {

    private StateOfGUI stateOfGUI;
    private List<Worker.Color> availableColors = new ArrayList<>();
    private List<Card> completeDeck = new ArrayList<>();
    private List<Card> availableCards = new ArrayList<>();


    public MessageToGUI(StateOfGUI stateOfGUI) {
        this.stateOfGUI = stateOfGUI;
    }

    public MessageToGUI(StateOfGUI stateOfGUI, boolean setCompleteDeck) {
        this.stateOfGUI = stateOfGUI;
        for (int i=0; i<Card.getCardNumb(); i++)
            completeDeck.add(new Card(i));
    }

    public List<Card> getCompleteDeck() {
        return completeDeck;
    }


    public StateOfGUI getStateOfGUI() {
        return stateOfGUI;
    }

    public List<Worker.Color> getAvailableColors() {
        return availableColors;
    }

    protected void updateAvailableColors(Worker.Color color){
        availableColors.add(color);
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    protected void updateAvailableCards(Card card){
        availableCards.add(card);
    }
}
