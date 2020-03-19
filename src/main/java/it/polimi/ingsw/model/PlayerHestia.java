package it.polimi.ingsw.model;

public class PlayerHestia extends Player {

    public PlayerHestia(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){

    }
}
