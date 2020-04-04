package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.ObservableLobby;
import it.polimi.ingsw.utils.InputConversion;
import java.util.Arrays;
import java.util.List;

public class Lobby extends ObservableLobby<LobbyToView> implements Cloneable {

    private StateOfTurn stateOfTurn = StateOfTurn.COLOR; // Fase 1 = scelta dei colori
    private List<LobbyPlayer> lobbyPlayers;
    private int numberOfLobbyPlayer;
    private LobbyPlayer lobbyActualPlayer;
    private List<Worker.Color> availableColors;
    private final int totalColors = Worker.Color.values().length;


    public Lobby(String name1, String name2) {
        addLobbyPlayer(new LobbyPlayer(name1));
        lobbyActualPlayer = lobbyPlayers.get(0);
        addLobbyPlayer(new LobbyPlayer(name2));
        numberOfLobbyPlayer = 2;
        availableColors.addAll(Arrays.asList(Worker.Color.values()));
    }

    public Lobby(String name1, String name2, String name3) {
        this(name1, name2);
        addLobbyPlayer(new LobbyPlayer(name3));
        numberOfLobbyPlayer = 3;
    }

    public boolean chooseColor(String info){
        Worker.Color chosenColor = InputConversion.conversion(info.toUpperCase());
        if (availableColors.contains(chosenColor)){
            lobbyActualPlayer.color = chosenColor;
            updateAvailableColors(chosenColor);
            if (availableColors.size() == totalColors - numberOfLobbyPlayer) // Finita la fase di scelta dei colori
                stateOfTurn = StateOfTurn.CARD;
            nextLobbyPlayer();
            notifyLobby(new LobbyToView(this.clone(), lobbyActualPlayer));
            return true;
        }
        return false;
    }


    private void nextLobbyPlayer(){
        if (lobbyActualPlayer.equals(lobbyPlayers.get(lobbyPlayers.size() - 1))){ // Se l'actualPlayer è l'ultimo della lista il nuovo actual player è il primo della lista
            lobbyActualPlayer = lobbyPlayers.get(0);
        }
        else
            lobbyActualPlayer = lobbyPlayers.get(lobbyPlayers.indexOf(lobbyActualPlayer) + 1);

    }

    public int getNumberOfAvailableColors() { return availableColors.size(); }


    public void updateAvailableColors(Worker.Color c) { this.availableColors.remove(c); }
    public StateOfTurn getStateOfTurn() { return stateOfTurn; }
    public boolean isLobbyPlayerTurn(LobbyPlayer lobbyPlayer){ return (lobbyActualPlayer.equals(lobbyPlayer)); }
    public List<LobbyPlayer> getLobbyPlayers() { return lobbyPlayers; }
    public void addLobbyPlayer(LobbyPlayer lobbyPlayer) { this.lobbyPlayers.add(lobbyPlayer); }


    @Override
    public Lobby clone() {
        try {
            Lobby l = (Lobby) super.clone();
            l.lobbyPlayers.addAll(this.lobbyPlayers);
            l.lobbyActualPlayer = lobbyActualPlayer.clone();
            l.availableColors.addAll(this.availableColors);
            return l;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
