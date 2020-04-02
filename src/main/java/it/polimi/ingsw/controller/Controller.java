package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.PlayerChoiceMessage;
import it.polimi.ingsw.model.lobby.Lobby;
import it.polimi.ingsw.model.lobby.ViewToController;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverLobby;
import it.polimi.ingsw.utils.GameMessage;


public class Controller implements Observer<PlayerChoiceMessage>, ObserverLobby<ViewToController> {

    private Match match;
    private Lobby lobby;

    public Controller(Match match){
        this.match = match;
    }
    public Controller(Lobby lobby){
        this.lobby = lobby;
    }

    private synchronized void performMoveAndBuild(PlayerChoiceMessage choice){
        if (match.getPlayingNow().equals(choice.getPlayer())) {
            if (choice.getOptional().equals("FirstAllocation")) {
                match.firstAllocation(choice.getX(), choice.getY(), choice.getGender());
            }
            else {
                match.performPlay(choice.getX(), choice.getY(), choice.getGender(), choice.getOptional());
            }
        }
        else
            choice.getView().reportError(GameMessage.wrongTurnMessage);
    }


    //Player player1 = FactoryPlayer.getPlayer(getKeyByValue(waitingConnectionForTwo, c1), 1, model.getMatch(), playerColorTwo[0], deckTwo[0]);
    //Player player2 = FactoryPlayer.getPlayer(getKeyByValue(waitingConnectionForTwo, c2), 2, model.getMatch(), playerColorTwo[1], deckTwo[1]);

    @Override
    public void update(PlayerChoiceMessage message) {
        performMoveAndBuild(message);
    }

    @Override
    public void updateLobby(ViewToController message) {

    }
}
