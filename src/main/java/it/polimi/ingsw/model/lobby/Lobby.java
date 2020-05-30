package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.ObservableLobby;
import it.polimi.ingsw.utils.InputConversion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lobby extends ObservableLobby<LobbyToView> implements Cloneable {

    protected StateOfTurn stateOfTurn = StateOfTurn.COLOR;
    private boolean switchState = false;
    private boolean isDeckChosen = false;
    protected enum Insert {ADD,REMOVE}

    private List<Card> chosenDeck = new ArrayList<>();
    private List<Card> completeDeck = new ArrayList<>();
    private List<LobbyPlayer> lobbyPlayers = new ArrayList<>();
    private int numberOfLobbyPlayer;
    private LobbyPlayer lobbyActualPlayer;
    private List<Worker.Color> availableColors = new ArrayList<>();
    private final int totalColors = Worker.Color.values().length;
    private int playingNow;


    /**
     * <b>2 PLAYERS GAME</b>
     * <p>
     * Lobby is where players go after the choose of name in {@code Server};
     * <p>
     * It's created by the Server to allocate players passed as parameters in a game
     * <p>
     * Here we have all the logic to support Choose of Colors and Cards by players; After that we are ready to start a Real Game, and so the <b>Lobby Phase</b> is over
     * @param name1 Name of <b>first</b> player
     * @param name2 Name of <b>second</b> player
     * @see it.polimi.ingsw.server.Server
     */
    public Lobby(String name1, String name2) {
        addLobbyPlayer(new LobbyPlayer(name1));
        lobbyActualPlayer = lobbyPlayers.get(0);
        addLobbyPlayer(new LobbyPlayer(name2));
        numberOfLobbyPlayer = 2;
        availableColors.addAll(Arrays.asList(Worker.Color.values()));
        for (int i=0; i<Card.getCardNumb(); i++)
            completeDeck.add(new Card(i));
    }

    /**
     * Same as for 2 players, but this create a game for 3 players
     * @param name1 Name of <b>first</b> player
     * @param name2 Name of <b>second</b> player
     * @param name3 Name of <b>third</b> player
     * @see #Lobby(String, String)
     */
    public Lobby(String name1, String name2, String name3) {
        this(name1, name2);
        addLobbyPlayer(new LobbyPlayer(name3));
        numberOfLobbyPlayer = 3;
    }

    /** Gets from the controller the color choosen by the player. It checks if the color is still available, and then save the player choice.
     * <p>
     * After that it checks if all player have choose their color; If this is true, it goes to the choose of Card phase.
     * <p>
     * At the end the method notify to the views the changes.
     * <p>
     * <b>The method take one choice at time, so it need to be called multiple times to perform all the choices</b>
     * @param info   The color choosen by the player
     * @return   {@code true} if the color is available, {@code false} otherwise
     */
    public boolean chooseColor(String info){
        Worker.Color chosenColor = InputConversion.colorConversion(info.toUpperCase());
        if (availableColors.contains(chosenColor)){
            lobbyActualPlayer.color = chosenColor;  // Assegna il colore al giocatore
            updateAvailableColors(chosenColor);
            nextLobbyPlayer();
            if (availableColors.size() == totalColors - numberOfLobbyPlayer) { // Finita la fase di scelta dei colori
                stateOfTurn = StateOfTurn.CARD;
                lobbyActualPlayer = lobbyPlayers.get(0);
                switchState = true;
            }
            notifyLobby(new LobbyToView(this.clone()));
            switchState = false;
            return true;
        }
        return false;
    }

    /**Gets from the MasterPlayer the cards with which the players will play.
     * <p>
     * After that it gets the card selected by the Otherplayers and save their choices.
     * <p>
     * At the end the method notify to the views the changes.
     * <p>
     * <b>The method take one choice at time, so it need to be called multiple times to perform all the choices</b>
     * @param chosenCard  The card choosen by the player
     * @return  {@code true} if the card is available, {@code false} otherwise
     */
    public boolean chooseCard(String chosenCard){
        if (!isDeckChosen){
            for (Card c: completeDeck) {
                if (chosenCard.toUpperCase().equals(c.getName())){
                    completeDeck.remove(c);                        // Rimuovi la carta dal deck completo
                    updateChosenDeck(c, Insert.ADD);               // Aggiungi la carta a quelle selezionate
                    if (chosenDeck.size() == lobbyPlayers.size()) {
                        isDeckChosen = true;                       // Finita fase di scelta delle carte
                        nextLobbyPlayer();
                    }
                    notifyLobby(new LobbyToView(this.clone()));
                    return true;
                }
            }
            return false;
        }
        else
            for (Card c: chosenDeck){
                if (chosenCard.toUpperCase().equals(c.getName())){
                    updateChosenDeck(c, Insert.REMOVE);
                    lobbyActualPlayer.card = c;
                    nextLobbyPlayer();
                    if (chosenDeck.size() == 1){
                        lobbyActualPlayer.card = chosenDeck.get(0);
                        stateOfTurn = StateOfTurn.CHOOSESTARTPLAYER;
                    }
                    notifyLobby(new LobbyToView(this.clone()));
                    return true;
                }
            }
            return false;
    }

    public boolean chooseStartPlayer(String playersName){
        for (LobbyPlayer l: lobbyPlayers) {
            if(l.getNickname().toUpperCase().equals(playersName.toUpperCase())){
                playingNow = lobbyPlayers.indexOf(l);
                stateOfTurn = StateOfTurn.READYTOSTART;
                notifyLobby(new LobbyToView(this.clone()));
                return true;
            }
        }
        return false;
    }

    protected void updateChosenDeck(Card chosenCard, Insert insert){
        if (insert == Insert.ADD) {
            chosenDeck.add(chosenCard);
        }
        else {
            chosenDeck.remove(chosenCard);
        }
    }

    /**Update the {@code LobbyActualPlayer}, who will be the next in order after the one playing now
     */
    protected void nextLobbyPlayer(){
        if (lobbyActualPlayer.equals(lobbyPlayers.get(lobbyPlayers.size() - 1))){ // Se l'actualPlayer è l'ultimo della lista il nuovo actual player è il primo della lista
            lobbyActualPlayer = lobbyPlayers.get(0);
        }
        else
            lobbyActualPlayer = lobbyPlayers.get(lobbyPlayers.indexOf(lobbyActualPlayer) + 1);

    }

    /**The method check in which phase of the Lobby we are. Possible phase are choose of color or choose of card.
     * @return  {@code StateofTurn}
     */
    public StateOfTurn getStateOfTurn() { return stateOfTurn; }

    /**
     * @return Player choose by the master, who will start the game
     */
    public int getPlayingNow() { return playingNow; }

    public List<String> getAvailableColors() {
        List<String> temp = new ArrayList<>();
        for (Worker.Color c: availableColors)
            temp.add(c.toString());
        return temp;
    }
    public List<Card> getChosenDeck() {
        return chosenDeck;
    }
    public boolean isDeckChosen() { return isDeckChosen; }
    public int getNumberOfLobbyPlayer() { return numberOfLobbyPlayer; }
    public boolean getSwitchState() { return switchState; }
    private void updateAvailableColors(Worker.Color c) { this.availableColors.remove(c); }
    public boolean isLobbyPlayerTurn(LobbyPlayer lobbyPlayer){ return (lobbyActualPlayer.getNickname().equals(lobbyPlayer.getNickname())); }
    public List<LobbyPlayer> getLobbyPlayers() { return lobbyPlayers; }
    private void addLobbyPlayer(LobbyPlayer lobbyPlayer) { this.lobbyPlayers.add(lobbyPlayer); }


    @Override
    public Lobby clone() {
        try {
            final Lobby l;
            if (getNumberOfLobbyPlayer() == 2) {
                l = new Lobby(this.lobbyPlayers.get(0).getNickname(), this.lobbyPlayers.get(1).getNickname());
                l.lobbyPlayers.set(0, this.lobbyPlayers.get(0).clone());
                l.lobbyPlayers.set(1, this.lobbyPlayers.get(1).clone());
            } else {
                l = new Lobby(this.lobbyPlayers.get(0).getNickname(), this.lobbyPlayers.get(1).getNickname(), this.lobbyPlayers.get(2).getNickname());
                l.lobbyPlayers.set(0, this.lobbyPlayers.get(0).clone());
                l.lobbyPlayers.set(1, this.lobbyPlayers.get(1).clone());
                l.lobbyPlayers.set(2, this.lobbyPlayers.get(2).clone());
            }
            l.lobbyActualPlayer = this.lobbyActualPlayer.clone();
            l.switchState = this.switchState;
            l.isDeckChosen = this.isDeckChosen;
            l.stateOfTurn = this.stateOfTurn;
            l.chosenDeck.addAll(this.chosenDeck);
            l.availableColors.clear();
            l.availableColors.addAll(this.availableColors);

            /*final Lobby l = (Lobby) super.clone();
            l.lobbyActualPlayer = lobbyActualPlayer.clone();*/

            return l;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
