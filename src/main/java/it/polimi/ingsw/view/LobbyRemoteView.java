package it.polimi.ingsw.view;

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
            /*try{
            }catch(IllegalArgumentException e){
                clientConnection.asyncSend("Error!");
            }*/
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
        String resultMsg = "";

        if (lobby.getStateOfTurn().equals(StateOfTurn.COLOR)) {  // Fase di scelta dei colori
            if (lobby.isLobbyPlayerTurn(lobbyPlayer)) { // Se è il turno del player

            }
            else{  // Altrimenti se non è il suo turno

            }
        }
        else{

        }
    }
}
