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

    /**
     * Store information taken from the {@link ClientGUI} decoderGUI, to show player only Available Colors and Card
     * <p>
     * Quite similar to the {@link it.polimi.ingsw.model.lobby.LobbyToView}
     * @param stateOfGUI to let the GUI know in which phase we are in order to choose panel to show
     * @param setCompleteDeck {@code true} for the Master Player, {@code false} otherwise
     * @see it.polimi.ingsw.model.lobby.Lobby
     */
    public MessageToGUI(StateOfGUI stateOfGUI, boolean setCompleteDeck) {
        this.stateOfGUI = stateOfGUI;
        if (setCompleteDeck){
            for (int i=0; i<Card.getCardNumb(); i++)
                completeDeck.add(new Card(i));
        }
    }

    /**
     * Set the number of cards Master player have to choose
     * @param cardToChoose Can be only 2 or 3
     */
    protected void setCardToChoose(int cardToChoose) { this.cardToChoose = cardToChoose; }

    /**
     * @return Number of cards Master Player can choose
     */
    public int getCardToChoose() { return cardToChoose; }

    /**
     * To initialize the {@link GamePanel}
     * @return List with all choices of players in a game, such as name, color and card
     */
    public List<PlayerSummary> getPlayerSummaries() { return playerSummaries; }

    /**
     * @return List of all cards supported by the Game
     */
    public List<Card> getCompleteDeck() { return completeDeck; }

    /**
     * @return the {@link StateOfGUI} associated to the game phase
     */
    public StateOfGUI getStateOfGUI() { return stateOfGUI; }


    /**
     * @return List of color that a player can choose
     */
    public List<Worker.Color> getAvailableColors() {
        return availableColors;
    }

    /**
     * Eliminate color passed as parameter from the list of available Color
     * @param color Color just chosen by a player
     */
    protected void updateAvailableColors(Worker.Color color){
        availableColors.add(color);
    }

    /**
     * @return List of card that a player can choose
     */
    public List<Card> getAvailableCards() { return availableCards; }

    /**
     * Elimante card passed as parameter from the list of available Color
     * @param card Card just chosen by a player
     */
    protected void updateAvailableCards(Card card){
        availableCards.add(card);
    }

    /**
     * Add a new PlayerSummary to the List of PlayerSummaries
     * @return PlayerSummary just added
     */
    protected PlayerSummary addPlayerSummary(){
        PlayerSummary temp = new PlayerSummary();
        playerSummaries.add(temp);
        return temp;
    }

    /**
     * A class to store info about player choose: number, name, color, card
     */
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
