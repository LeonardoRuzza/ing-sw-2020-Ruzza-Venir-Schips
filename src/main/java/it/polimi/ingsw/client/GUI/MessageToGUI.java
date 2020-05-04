package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class MessageToGUI {

    private StateOfGUI stateOfGUI;
    private int cardToChoose;
    private List<Worker.Color> availableColors = new ArrayList<>();
    private List<Card> completeDeck = new ArrayList<>();
    private List<Card> availableCards = new ArrayList<>();
    private List<PlayerSummary> playerSummaries = new ArrayList<>();


    public MessageToGUI(StateOfGUI stateOfGUI) {
        this.stateOfGUI = stateOfGUI;
    }

    public MessageToGUI(StateOfGUI stateOfGUI, boolean setCompleteDeck) {
        this.stateOfGUI = stateOfGUI;
        if (setCompleteDeck){
            for (int i=0; i<Card.getCardNumb(); i++)
                completeDeck.add(new Card(i));
        }
    }


    protected void setCardToChoose(int cardToChoose) { this.cardToChoose = cardToChoose; }
    public int getCardToChoose() { return cardToChoose; }
    public List<PlayerSummary> getPlayerSummaries() { return playerSummaries; }
    public List<Card> getCompleteDeck() { return completeDeck; }
    public StateOfGUI getStateOfGUI() { return stateOfGUI; }


    public List<Worker.Color> getAvailableColors() {
        return availableColors;
    }

    protected void updateAvailableColors(Worker.Color color){
        availableColors.add(color);
    }

    public List<Card> getAvailableCards() { return availableCards; }

    protected void updateAvailableCards(Card card){
        availableCards.add(card);
    }

    protected PlayerSummary addPlayerSummary(){
        PlayerSummary temp = new PlayerSummary();
        playerSummaries.add(temp);
        return temp;
    }

    public class PlayerSummary{
        private int playerNumber;
        private String cardName;
        private Worker.Color color;
        private String nickname;

        public void setPlayerNumber(int playerNumber) {
            this.playerNumber = playerNumber;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public void setColor(Worker.Color color) {
            this.color = color;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getPlayerNumber() {
            return playerNumber;
        }

        public String getCardName() {
            return cardName;
        }

        public Worker.Color getColor() {
            return color;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
