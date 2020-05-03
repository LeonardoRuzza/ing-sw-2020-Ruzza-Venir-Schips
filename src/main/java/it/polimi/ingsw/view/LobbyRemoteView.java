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
    private MessageReceiver messageReceiver;

    public LobbyRemoteView(ClientConnection clientConnection, LobbyPlayer lobbyPlayer) {
        this.clientConnection = clientConnection;
        this.lobbyPlayer = lobbyPlayer;
        clientConnection.addObserver(messageReceiver = new MessageReceiver());
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

    public MessageReceiver getMessageReceiver() { return messageReceiver; }
    public ClientConnection getClientConnection() { return clientConnection; }
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
        String resultMsg = "";

        switch (lobby.getStateOfTurn()) {
            case COLOR:  // Fase di scelta dei colori
                if (lobby.isLobbyPlayerTurn(lobbyPlayer)) { // Se è il turno del player
                    for (String s :lobby.getAvailableColors()) {
                       resultMsg = resultMsg.concat(s);
                       resultMsg = resultMsg.concat("\n");
                    }
                    showMessageSync(GameMessage.availableColors + "\n" + resultMsg + GameMessage.chooseColor);
                }
                else{  // Altrimenti se non è il suo turno
                    showMessage("\n" +GameMessage.waitMessageForColor);
                }
                break;
            case CARD:
                if (lobby.getSwitchState()){  // Finita fase di scelta dei colori e inizia quella delle carte
                    if (lobby.getLobbyPlayers().get(0).getNickname().equals(lobbyPlayer.getNickname())){
                        if (lobby.getNumberOfLobbyPlayer() == 2)
                            showMessageSync(GameMessage.cardPhase + Card.drawAll() + "\n" + GameMessage.playerMasterChoseCard2);  // Mex per il master player
                        else
                            showMessageSync(GameMessage.cardPhase + Card.drawAll() + "\n" +GameMessage.playerMasterChoseCard3);  // Mex per il master player
                        }
                    else
                        showMessageSync("\n" + GameMessage.waitMasterChoseOfCard); // Mex per gli altri player
                }
                else{
                    if (lobby.isDeckChosen()) {  // Se il deck è stato scelto
                        if (lobby.isLobbyPlayerTurn(lobbyPlayer)){    // Stampa le carte disponibili rimanenti
                            for (Card c :lobby.getChosenDeck()) {
                                resultMsg = resultMsg.concat(c.getName());
                                resultMsg = resultMsg.concat("\n");
                            }
                            showMessageSync(GameMessage.availableCards + "\n" + resultMsg);
                        }
                        else  // Altrimenti se non è il suo turno
                            showMessageSync("\n" + GameMessage.waitMessageForCard);
                    }
                    else {  // Altrimenti chiedi al master player di inserire una nuova carta
                        if (lobby.isLobbyPlayerTurn(lobbyPlayer)) {
                            showMessage(GameMessage.playerMasterChoseAnotherCard);
                        }
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

                }
                showMessageSync(GameMessage.startNormalGame);  // Notifica l'inizio della partita normale
                break;
        }
    }
}
