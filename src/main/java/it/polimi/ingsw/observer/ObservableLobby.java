package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

public class ObservableLobby<T> {

    private final List<ObserverLobby<T>> observers = new ArrayList<>();

    public void addObserverLobby(ObserverLobby<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserverLobby(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notifyLobby(T message){
        synchronized (observers) {
            for(ObserverLobby<T> observer : observers){
                observer.updateLobby(message);
            }
        }
    }
}
