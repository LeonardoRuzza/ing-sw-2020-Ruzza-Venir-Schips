package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ChoiceResponseMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerChoiceMessage;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;


public abstract class View extends Observable<PlayerChoiceMessage> implements Observer<ChoiceResponseMessage> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    public Player getPlayer() { return player;}

    protected abstract void showMessage(Object message);

    public void manageChoice(Player player, int x, int y,String optional, Worker.Gender gender){
        System.out.println(x + " " + y+ "" + optional + "" + gender.toString());
        notify(new PlayerChoiceMessage(player, x, y, this, optional, gender));
    }

    public void reportError(String message){
        showMessage(message);
    }
}
