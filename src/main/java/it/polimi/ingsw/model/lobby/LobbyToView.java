package it.polimi.ingsw.model.lobby;

public class LobbyToView {

    private Lobby lobby;
    //private LobbyPlayer lobbyPlayer;
    //private String information;

    public LobbyToView(Lobby lobby) {
        this.lobby = lobby;
        //this.lobbyPlayer = lobbyPlayer;
    }

    /*public String getInformation() {return information; }*/
    public Lobby getLobby() { return lobby; }
   // public LobbyPlayer getLobbyPlayer() { return lobbyPlayer; }
}
