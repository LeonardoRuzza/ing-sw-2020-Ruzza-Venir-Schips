package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.PlayerChoiceMessage;
import it.polimi.ingsw.model.lobby.Lobby;
import it.polimi.ingsw.model.lobby.StateOfTurn;
import it.polimi.ingsw.model.lobby.ViewToController;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverLobby;
import it.polimi.ingsw.utils.GameMessage;
import it.polimi.ingsw.utils.InputConversion;


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


    @Override
    public void update(PlayerChoiceMessage message) {
        performMoveAndBuild(message);
    }



    /////////////////    Lobby     ////////////////////////////////////////////////
    private synchronized void handleLobbyInput(ViewToController message){
        // Controllo se è il turno del player che ha mandato il messaggio di Input
        if (!lobby.isLobbyPlayerTurn(message.getLobbyPlayer())){
            message.getLobbyRemoteView().reportError(GameMessage.wrongTurnMessage);
            return;
        }
        if (lobby.getStateOfTurn().equals(StateOfTurn.COLOR)) {
            if ((InputConversion.conversion(message.getInformation().toUpperCase())) == null) {
                message.getLobbyRemoteView().reportError(GameMessage.wrongColorInput);
                return;
            }
            if(!lobby.chooseColor(message.getInformation())){
                message.getLobbyRemoteView().reportError(GameMessage.notAvailableColor);
            }
        }
        else{

        }

    }

    @Override
    public void updateLobby(ViewToController message) { handleLobbyInput(message);}
}
