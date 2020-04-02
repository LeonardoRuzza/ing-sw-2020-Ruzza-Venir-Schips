package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.observer.ObservableLobby;
import java.util.List;

public class Lobby extends ObservableLobby<LobbyToView> {

    private List<LobbyPlayer> lobbyPlayers;
    private int numberOfLobbyPlayer;
    private LobbyPlayer lobbyActualPlayer;

    public Lobby(String name1, String name2) {
        addLobbyPlayer(new LobbyPlayer(name1));
        lobbyActualPlayer = lobbyPlayers.get(0);
        addLobbyPlayer(new LobbyPlayer(name2));
        numberOfLobbyPlayer = 2;
    }

    public Lobby(String name1, String name2, String name3) {
        this(name1, name2);
        addLobbyPlayer(new LobbyPlayer(name3));
        numberOfLobbyPlayer = 3;
    }

    public boolean isLobbyPlayerTurn(LobbyPlayer lobbyPlayer){
        return (lobbyActualPlayer == lobbyPlayer);
    }

    public void nextLobbyPlayer(){
        switch (numberOfLobbyPlayer){
            case 2:
            case 3:
        }
        lobbyActualPlayer = lobbyPlayers.get(lobbyPlayers.indexOf(lobbyActualPlayer) + 1);

    };

    public List<LobbyPlayer> getLobbyPlayers() {
        return lobbyPlayers;
    }
    public void addLobbyPlayer(LobbyPlayer lobbyPlayer) {
        this.lobbyPlayers.add(lobbyPlayer);
    }
}
