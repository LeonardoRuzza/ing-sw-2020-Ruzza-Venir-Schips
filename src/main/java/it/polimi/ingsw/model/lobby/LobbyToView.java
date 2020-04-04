package it.polimi.ingsw.model.lobby;

public class LobbyToView {

    private Lobby lobby;
    private LobbyPlayer lobbyPlayer;
    //private String information;

    public LobbyToView(Lobby lobby, LobbyPlayer lobbyPlayer/*, String information*/) {
        this.lobby = lobby;
        this.lobbyPlayer = lobbyPlayer;
        //this.information = information;
    }

    /*public String getInformation() {return information; }*/
    public Lobby getLobby() { return lobby; }
    public LobbyPlayer getLobbyPlayer() {
        return lobbyPlayer;
    }
}
