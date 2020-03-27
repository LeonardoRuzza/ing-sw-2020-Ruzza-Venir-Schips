package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.PlayerChoiceMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.model.Model;



public class Controller implements Observer<PlayerChoiceMessage> {

    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model=model;
        this.view=view;

    }

    @Override
    public void update(PlayerChoiceMessage message) {

    }
}
