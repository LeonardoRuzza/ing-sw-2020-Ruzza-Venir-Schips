package it.polimi.ingsw.model;

public class PlayerHera extends Player {

    protected PlayerHera(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean checkLimitWin(Player opponent){              //in match questo metodo dovrà essere chiamato mandando il player attuale (non se è Hera stessa)
        int x=opponent.selectedWorker.getCell().getxCoord();
        int y=opponent.selectedWorker.getCell().getyCoord();
        return (x != 0 && x != 4 && y != 0 && y != 4);             //ritorna false se il giocatore non può vincere
    }
}



