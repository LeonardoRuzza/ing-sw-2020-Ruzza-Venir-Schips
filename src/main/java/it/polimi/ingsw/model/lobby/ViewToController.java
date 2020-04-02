package it.polimi.ingsw.model.lobby;
import it.polimi.ingsw.view.View;

public class ViewToController {

    private String information;
    private LobbyPlayer lobbyPlayer;
    private View LobbyRemoteView;


    public ViewToController(String information, LobbyPlayer lobbyPlayer, View lobbyRemoteView) {
        this.information = information;
        this.lobbyPlayer = lobbyPlayer;
        this.LobbyRemoteView = lobbyRemoteView;
    }

    public String getInformation() {
        return information;
    }
    public LobbyPlayer getLobbyPlayer() {
        return lobbyPlayer;
    }
    public View getLobbyRemoteView() {
        return LobbyRemoteView;
    }
}
