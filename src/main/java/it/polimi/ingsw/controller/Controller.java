package it.polimi.ingsw.controller;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.FactoryPlayer;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerChoiceMessage;
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
            if (choice.getOptional().equals(GameMessage.turnMessageFIRSTALLOCATION)) {
                if(!match.firstAllocation(choice.getX(), choice.getY(), choice.getGender())){
                    choice.getView().reportError(GameMessage.turnMessageErrorFIRSTALLOCATION);
                }
            }else {

                match.performPlay(choice.getX(), choice.getY(), choice.getGender(), choice.getOptional());
            }
        }else
            choice.getView().reportError(GameMessage.wrongTurnMessage);
    }


    @Override
    public void update(PlayerChoiceMessage message) {
        performMoveAndBuild(message);
    }



    /////////////////    Lobby     ////////////////////////////////////////////////
    private synchronized void handleLobbyInput(ViewToController message){

        if (!lobby.isLobbyPlayerTurn(message.getLobbyPlayer())){          // Controllo se è il turno del player che ha mandato il messaggio di Input
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
                    if (lobby.getStateOfTurn().equals(StateOfTurn.READYTOSTART)){

                        //TODO crea partita normale
                    }
                }
                break;
            }
    }

    private void createNormalGame(){
        Match match = new Match(0, lobby.getLobbyPlayers().size());
        Controller controller = new Controller(match);
        if (lobby.getLobbyPlayers().size() == 3){
            LobbyPlayer l1 = lobby.getLobbyPlayers().get(0);
            LobbyPlayer l2 = lobby.getLobbyPlayers().get(1);
            LobbyPlayer l3 = lobby.getLobbyPlayers().get(2);
            Player p1 = FactoryPlayer.getPlayer(l1.getNickname(), 1, match, l1.getColor(), l1.getCard().getNumber());
            Player p2 = FactoryPlayer.getPlayer(l2.getNickname(), 2, match, l2.getColor(), l2.getCard().getNumber());
            Player p3 = FactoryPlayer.getPlayer(l3.getNickname(), 3, match, l3.getColor(), l3.getCard().getNumber());
            ClientConnection c1 = null;
            ClientConnection c2 = null;
            ClientConnection c3 = null;
            RemoteView p1RemoteView = new RemoteView(c1, p1);
            RemoteView p2RemoteView = new RemoteView(c2, p2);
            RemoteView p3RemoteView = new RemoteView(c3, p3);
            match.addObserver(p1RemoteView);
            match.addObserver(p2RemoteView);
            match.addObserver(p3RemoteView);
            p1RemoteView.addObserver(controller);
            p2RemoteView.addObserver(controller);
            p3RemoteView.addObserver(controller);
        }else{
            LobbyPlayer l1 = lobby.getLobbyPlayers().get(0);
            LobbyPlayer l2 = lobby.getLobbyPlayers().get(1);
            Player p1 = FactoryPlayer.getPlayer(l1.getNickname(), 1, match, l1.getColor(), l1.getCard().getNumber());
            Player p2 = FactoryPlayer.getPlayer(l2.getNickname(), 2, match, l2.getColor(), l2.getCard().getNumber());
            ClientConnection c1 = null;
            ClientConnection c2 = null;
            RemoteView p1RemoteView = new RemoteView(c1, p1);
            RemoteView p2RemoteView = new RemoteView(c2, p2);
            match.addObserver(p1RemoteView);
            match.addObserver(p2RemoteView);
            p1RemoteView.addObserver(controller);
            p2RemoteView.addObserver(controller);
        }
    }

    @Override
    public void updateLobby(ViewToController message) { handleLobbyInput(message);}
}
