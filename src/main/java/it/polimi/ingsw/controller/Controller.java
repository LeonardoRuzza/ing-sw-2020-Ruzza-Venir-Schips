package it.polimi.ingsw.controller;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.model.Model;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model=model;
        this.view=view;

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
