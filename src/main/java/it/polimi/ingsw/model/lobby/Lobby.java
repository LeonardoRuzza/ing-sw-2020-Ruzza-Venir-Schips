package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.ObservableLobby;
import it.polimi.ingsw.utils.InputConversion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lobby extends ObservableLobby<LobbyToView> implements Cloneable {

    private StateOfTurn stateOfTurn = StateOfTurn.COLOR; // Fase 1 = scelta dei colori
    private boolean switchState = false;
    private boolean isDeckChosen = false;
    private enum Insert {ADD,REMOVE}

    private List<Card> chosenDeck = new ArrayList<>();
    private List<Card> completeDeck = new ArrayList<>();
    private List<LobbyPlayer> lobbyPlayers = new ArrayList<>();
    private int numberOfLobbyPlayer;
    private LobbyPlayer lobbyActualPlayer;
    private List<Worker.Color> availableColors = new ArrayList<>();
    private final int totalColors = Worker.Color.values().length;


    public Lobby(String name1, String name2) {
        addLobbyPlayer(new LobbyPlayer(name1));
        lobbyActualPlayer = lobbyPlayers.get(0);
        addLobbyPlayer(new LobbyPlayer(name2));
        numberOfLobbyPlayer = 2;
        availableColors.addAll(Arrays.asList(Worker.Color.values()));
        for (int i=0; i<Card.getCardNumb(); i++)
            completeDeck.add(new Card(i));
    }

    public Lobby(String name1, String name2, String name3) {
        this(name1, name2);
        addLobbyPlayer(new LobbyPlayer(name3));
        numberOfLobbyPlayer = 3;
    }

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
                        lobbyActualPlayer.card = chosenDeck.get(0); // Assegna l'unica carta rimanente all'ultimo giocatore
                        stateOfTurn = StateOfTurn.READYTOSTART;
                    }
                    notifyLobby(new LobbyToView(this.clone()));
                    return true;
                }
            }
            return false;
    }

    public void updateChosenDeck(Card chosenCard, Insert insert){
        if (insert == Insert.ADD) {
            chosenDeck.add(chosenCard);
        }
        else {
            chosenDeck.remove(chosenCard);
        }
    }

    private void nextLobbyPlayer(){
        if (lobbyActualPlayer.equals(lobbyPlayers.get(lobbyPlayers.size() - 1))){ // Se l'actualPlayer è l'ultimo della lista il nuovo actual player è il primo della lista
            lobbyActualPlayer = lobbyPlayers.get(0);
        }
        else
            lobbyActualPlayer = lobbyPlayers.get(lobbyPlayers.indexOf(lobbyActualPlayer) + 1);

    }


    public List<String> getAvailableColors() {  // Ritorna lista con i colori rimanenti disponibili
        List<String> temp = new ArrayList<>();
        for (Worker.Color c: availableColors)
            temp.add(c.toString());
        return temp;
    }
    public List<Card> getChosenDeck() {   // Ritorna lista con le carte rimanenti disponibili
        return chosenDeck;
    }
    public boolean isDeckChosen() { return isDeckChosen; }
    public int getNumberOfLobbyPlayer() { return numberOfLobbyPlayer; }
    public boolean getSwitchState() { return switchState; }
    public void updateAvailableColors(Worker.Color c) { this.availableColors.remove(c); }
    public StateOfTurn getStateOfTurn() { return stateOfTurn; }
    public boolean isLobbyPlayerTurn(LobbyPlayer lobbyPlayer){ return (lobbyActualPlayer.getNickname().equals(lobbyPlayer.getNickname())); }
    public List<LobbyPlayer> getLobbyPlayers() { return lobbyPlayers; }
    public void addLobbyPlayer(LobbyPlayer lobbyPlayer) { this.lobbyPlayers.add(lobbyPlayer); }


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

            // TODO modificare clone
            /*final Lobby l = (Lobby) super.clone();
            l.lobbyActualPlayer = lobbyActualPlayer.clone();*/

            return l;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
