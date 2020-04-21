package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.ClosingConnectionParameter;

public interface ClientConnection{

    void closeConnection();

    void close(ClosingConnectionParameter closingParameter);

    void addObserver(Observer<String> observer);

    void removeObserver(Observer<String> observer);

    void asyncSend(Object message);

    void send(Object message);

    void setReadyToPlay(boolean ready);

    void release();
}
