package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.view.LobbyRemoteView;


public class ViewToController {

    private String information;
    private LobbyPlayer lobbyPlayer;
    private LobbyRemoteView lobbyRemoteView;

    /**Create the message passed from the LobbyRemoteView to Controller.
     * <p>
     * @param information a string with info received
     * @param lobbyPlayer the LobbyPlayer who has sent the info
     * @param lobbyRemoteView the LobbyRemoteView associated to the LobbyPlayer
     */
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
