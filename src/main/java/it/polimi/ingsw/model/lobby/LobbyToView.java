package it.polimi.ingsw.model.lobby;

public class LobbyToView {

    private String information;
    private LobbyPlayer lobbyPlayer;


    public LobbyToView(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public LobbyPlayer getLobbyPlayer() {
        return lobbyPlayer;
    }
}
