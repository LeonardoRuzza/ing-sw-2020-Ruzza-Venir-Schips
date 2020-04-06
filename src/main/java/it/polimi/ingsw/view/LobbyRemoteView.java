package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.lobby.*;
import it.polimi.ingsw.observer.ObservableLobby;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverLobby;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.GameMessage;


public class LobbyRemoteView extends ObservableLobby<ViewToController> implements ObserverLobby<LobbyToView> {

    private ClientConnection clientConnection;
    private LobbyPlayer lobbyPlayer;

    public LobbyRemoteView(ClientConnection clientConnection, LobbyPlayer lobbyPlayer) {
        this.clientConnection = clientConnection;
        this.lobbyPlayer = lobbyPlayer;
        clientConnection.addObserver(new MessageReceiver());
        // TODO Da aggiungere i riferimenti agli avversari...
    }

    private class MessageReceiver implements Observer<String> {
        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            if (!message.isEmpty())
                notifyInputToController(message);
            else
                clientConnection.asyncSend("Empty message!");
        }
    }

    private void notifyInputToController(String message) {
        notifyLobby(new ViewToController(message, lobbyPlayer, this));
    }

    public void reportError(String message) {showMessage(message);}
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }
    protected void showMessageSync(Object message) {
        clientConnection.send(message);
    }


    @Override
    public void updateLobby(LobbyToView message) {
        final Lobby lobby = message.getLobby();
        String resultMsg;

        switch (lobby.getStateOfTurn()) {
            case COLOR:  // Fase di scelta dei colori
                if (lobby.isLobbyPlayerTurn(lobbyPlayer)) { // Se è il turno del player
                    showMessageSync(GameMessage.availableColors);
                    for (String s :lobby.getAvailableColors()) {
                       resultMsg = s + ", ";
                       showMessageSync(resultMsg);
                    }
                    showMessageSync(GameMessage.chooseColor);
                }
                else{  // Altrimenti se non è il suo turno
                    showMessage(GameMessage.waitMessageForColor);
                }
                break;
            case CARD:
                if (lobby.getSwitchState()){  // Finita fase di scelta dei colori e inizia quella delle carte
                    showMessageSync(GameMessage.cardPhase);
                    showMessageSync(Card.drawAll());
                    if (lobby.getLobbyPlayers().get(0).equals(lobbyPlayer)){
                        if (lobby.getNumberOfLobbyPlayer() == 2)
                            showMessageSync(GameMessage.playerMasterChoseCard2);  // Mex per il master player
                        else
                            showMessageSync(GameMessage.playerMasterChoseCard3);  // Mex per il master player
                        }
                    else
                        showMessageSync(GameMessage.waitMasterChoseOfCard); // Mex per gli altri player
                }
                else{
                    if (lobby.isDeckChosen()) {  // Se il deck è stato scelto
                        if (lobby.isLobbyPlayerTurn(lobbyPlayer)){    // Stampa le carte disponibili rimanenti
                            showMessageSync(GameMessage.availableCards);
                            for (Card c :lobby.getChosenDeck()) {
                                resultMsg = c.getName() + ", ";
                                showMessageSync(resultMsg);
                            }
                        }
                        else  // Altrimenti se non è il suo turno
                            showMessageSync(GameMessage.waitMessageForCard);
                    }
                    else {  // Altrimenti chiedi al master player di inserire una nuova carta
                        showMessage(GameMessage.playerMasterChoseAnotherCard);
                    }

                }
                break;
            case READYTOSTART:
                LobbyPlayer l = lobby.getLobbyPlayers().get(0);
                resultMsg = GameMessage.player1is + l.getNickname() + "\n" + GameMessage.hisColor + l.getColor().toString() + "\n" + GameMessage.hisCard + l.getCard().getName() + "\n\n";
                showMessageSync(resultMsg);
                l = lobby.getLobbyPlayers().get(1);
                resultMsg = GameMessage.player2is + l.getNickname() + "\n" + GameMessage.hisColor + l.getColor().toString() + "\n" + GameMessage.hisCard + l.getCard().getName() + "\n\n";
                showMessageSync(resultMsg);
                if (lobby.getLobbyPlayers().size() == 3){
                    l = lobby.getLobbyPlayers().get(2);
                    resultMsg = GameMessage.player3is + l.getNickname() + "\n" + GameMessage.hisColor + l.getColor().toString() + "\n" + GameMessage.hisCard + l.getCard().getName() + "\n\n";
                    showMessageSync(resultMsg);

                showMessageSync(GameMessage.startNormalGame);  // Notifica l'inizio della partita normale
                }
                break;
        }
    }
}
