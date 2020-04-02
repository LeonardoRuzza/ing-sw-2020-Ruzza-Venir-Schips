package it.polimi.ingsw.view;

import it.polimi.ingsw.model.lobby.LobbyPlayer;
import it.polimi.ingsw.model.lobby.LobbyToView;
import it.polimi.ingsw.model.lobby.ViewToController;
import it.polimi.ingsw.observer.ObservableLobby;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverLobby;
import it.polimi.ingsw.server.ClientConnection;

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
            try{
                // TODO Leggere input
                String[] inputs = message.split(",");
            }catch(IllegalArgumentException e){
                clientConnection.asyncSend("Error!");
            }
        }
    }

    @Override
    public void updateLobby(LobbyToView message) {

    }
}
