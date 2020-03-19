package it.polimi.ingsw.model;

public class PlayerHephaesthus extends Player {

    public PlayerHephaesthus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerBuild(int x, int y){

    }
}
