package it.polimi.ingsw.model;

public class PlayerMinotaur extends Player {

    public PlayerMinotaur(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){
        if(super.selectedWorkerMove(x, y)){return true;}


    }
}
