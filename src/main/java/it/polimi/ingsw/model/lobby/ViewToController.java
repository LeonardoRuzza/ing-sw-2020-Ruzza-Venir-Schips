package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.view.LobbyRemoteView;


public class ViewToController {

    private String information;
    private LobbyPlayer lobbyPlayer;
    private LobbyRemoteView lobbyRemoteView;


    public ViewToController(String information, LobbyPlayer lobbyPlayer, LobbyRemoteView lobbyRemoteView) {
        this.information = information;
        this.lobbyPlayer = lobbyPlayer;
        this.lobbyRemoteView = lobbyRemoteView;
    }

    public String getInformation() {
        return information;
    }
    public LobbyPlayer getLobbyPlayer() {
        return lobbyPlayer;
    }
    public LobbyRemoteView getLobbyRemoteView() {
        return lobbyRemoteView;
    }
}
