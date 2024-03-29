package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.lobby.Lobby;
import it.polimi.ingsw.model.lobby.LobbyPlayer;
import it.polimi.ingsw.model.lobby.StateOfTurn;
import it.polimi.ingsw.model.lobby.ViewToController;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverLobby;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.GameMessage;
import it.polimi.ingsw.utils.InputConversion;
import it.polimi.ingsw.view.LobbyRemoteView;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;


public class Controller implements Observer<PlayerChoiceMessage>, ObserverLobby<ViewToController> {

    private Match match;
    private Lobby lobby;
    private LobbyRemoteView lobbyView1, lobbyView2, lobbyView3;


    public Controller(Match match){
        this.match = match;
    }

    public Controller(Lobby lobby, LobbyRemoteView view1, LobbyRemoteView view2, LobbyRemoteView view3){
        this.lobby = lobby;
        this.lobbyView1 = view1;
        this.lobbyView2 = view2;
        this.lobbyView3 = view3;
    }


    /////////////////    Lobby     ////////////////////////////////////////////////
    /**
     * Function to handle the player input during the game
     *  @param choice   Contains the information about the player: RemoteView, SelectedGender, xCoord, yCoord, OptionalString
     */
    private synchronized void performMoveAndBuild(PlayerChoiceMessage choice){
        if (match.getPlayingNow().getNickname().equals(choice.getPlayer().getNickname())) {
            if (match.getNumOfTurn()==0){
                if(!match.checkAllPlayerLocated()){
                    match.execFirstAllocation(choice.getX(), choice.getY(), choice.getGender());
                    return;
                }
            }
            match.performPlay(choice.getX(), choice.getY(), choice.getGender(), choice.getOptional());
        }else
            choice.getView().reportError(GameMessage.wrongTurnMessage);
    }


    @Override
    public void update(PlayerChoiceMessage message) {
        if (message.getOptional().equals(GameMessage.removeObserver)){
            removeMe(message.getView());
            return;
        }
        performMoveAndBuild(message);
    }



    /////////////////    Lobby     ////////////////////////////////////////////////
    /**
     * Function to handle the player input in the Lobby phase
     *  @param message   Contains the information about the player: RemoteView, choice
     */
    private synchronized void handleLobbyInput(ViewToController message){

        if (!lobby.isLobbyPlayerTurn(message.getLobbyPlayer())){
            message.getLobbyRemoteView().reportError(GameMessage.wrongTurnMessage);
            return;
        }
        switch (lobby.getStateOfTurn()) {
            case COLOR:
                if ((InputConversion.colorConversion(message.getInformation().toUpperCase())) == null) {
                    message.getLobbyRemoteView().reportError(GameMessage.wrongColorInput);
                    return;
                }
                if (!lobby.chooseColor(message.getInformation())) {
                    message.getLobbyRemoteView().reportError(GameMessage.notAvailableColor);
                }
                break;
            case CARD:
                if(!lobby.chooseCard(message.getInformation())){
                    message.getLobbyRemoteView().reportError(GameMessage.notAvailableCard);
                }
                break;
            case CHOOSESTARTPLAYER:
                if(!lobby.chooseStartPlayer(message.getInformation())){
                    message.getLobbyRemoteView().reportError(GameMessage.chooseStartPlayerBadInput);
                    return;
                }
                if (lobby.getStateOfTurn().equals(StateOfTurn.READYTOSTART)){
                    createNormalGame();
                }
                break;
            }
    }

    /** When all interactions in lobby are finished, this method create the various components of the normal MVC, adding relative
     * observers, and then call a method in {@code match} to get prepared to start the real game.
     */
    private void createNormalGame(){
        Match match = new Match(0, lobby.getLobbyPlayers().size());
        Controller controller = new Controller(match);
        LobbyPlayer l1 = null;
        LobbyPlayer l2 = null;
        LobbyPlayer l3 = null;
        ClientConnection c1 = null;
        ClientConnection c2 = null;
        ClientConnection c3 = null;
        if(lobby.getNumberOfLobbyPlayer() == 3 && lobby.getPlayingNow() == 2){
            l1 = lobby.getLobbyPlayers().get(2);
            l2 = lobby.getLobbyPlayers().get(0);
            l3 = lobby.getLobbyPlayers().get(1);
            c1 = lobbyView3.getClientConnection();
            c2 = lobbyView1.getClientConnection();
            c3 = lobbyView2.getClientConnection();
        }else{
            if(lobby.getPlayingNow() == 0){
                l1 = lobby.getLobbyPlayers().get(0);
                l2 = lobby.getLobbyPlayers().get(1);
                c1 = lobbyView1.getClientConnection();
                c2 = lobbyView2.getClientConnection();
            }else{
                if(lobby.getPlayingNow() == 1){
                    if (lobby.getNumberOfLobbyPlayer() == 3){
                        l1 = lobby.getLobbyPlayers().get(1);
                        l2 = lobby.getLobbyPlayers().get(2);
                        l3 = lobby.getLobbyPlayers().get(0);
                        c1 = lobbyView2.getClientConnection();
                        c2 = lobbyView3.getClientConnection();
                        c3 = lobbyView1.getClientConnection();
                    }else{
                        l1 = lobby.getLobbyPlayers().get(1);
                        l2 = lobby.getLobbyPlayers().get(0);
                        c1 = lobbyView2.getClientConnection();
                        c2 = lobbyView1.getClientConnection();
                    }
                }
            }
        }
        assert l1 != null;
        Player p1 = FactoryPlayer.getPlayer(l1.getNickname(), 1, match, l1.getColor(), l1.getCard().getNumber());
        Player p2 = FactoryPlayer.getPlayer(l2.getNickname(), 2, match, l2.getColor(), l2.getCard().getNumber());
        match.addPlayer(p1);
        match.addPlayer(p2);
        RemoteView p1RemoteView = new RemoteView(c1, p1);
        RemoteView p2RemoteView = new RemoteView(c2, p2);
        match.addObserver(p1RemoteView);
        match.addObserver(p2RemoteView);
        p1RemoteView.addObserver(controller);
        p2RemoteView.addObserver(controller);
        lobbyView1.getClientConnection().removeObserver(lobbyView1.getMessageReceiver());
        lobbyView2.getClientConnection().removeObserver(lobbyView2.getMessageReceiver());
        if (lobby.getLobbyPlayers().size() == 3){
            if(l3 == null){
                l3 = lobby.getLobbyPlayers().get(2);
                c3 = lobbyView3.getClientConnection();
            }
            Player p3 = FactoryPlayer.getPlayer(l3.getNickname(), 3, match, l3.getColor(), l3.getCard().getNumber());
            match.addPlayer(p3);
            RemoteView p3RemoteView = new RemoteView(c3, p3);
            match.addObserver(p3RemoteView);
            p3RemoteView.addObserver(controller);
            lobbyView3.getClientConnection().removeObserver(lobbyView3.getMessageReceiver());
        }
        match.initializeGame();
    }



    @Override
    public void updateLobby(ViewToController message) { handleLobbyInput(message);}

    private void removeMe(View rv){
        match.removeObserver(rv);
    }
}
