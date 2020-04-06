package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;

public interface ClientConnection{

    void closeConnection();

    void close(Boolean singleClientClose);

    void addObserver(Observer<String> observer);

    void removeObserver(Observer<String> observer);

    void asyncSend(Object message);

    void send(Object message);

}
