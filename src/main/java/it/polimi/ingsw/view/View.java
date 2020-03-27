package it.polimi.ingsw.view;


import java.util.Observable;
import java.util.Observer;

public abstract class View extends Observable implements Observer {

    public View(){
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
