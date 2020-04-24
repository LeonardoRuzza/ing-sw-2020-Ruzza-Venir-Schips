package it.polimi.ingsw.model;

public class PlayerHera extends Player {

    protected PlayerHera(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**Implement the power of Hera which expect that an opponent cannot win by moving into a perimeter space.
     * <p>
     * @param opponent the actual Player
     * @return {@code true} if the normal winning conditions is possible; {@code false} otherwise
     */
    @Override
    public boolean checkLimitWin(Player opponent){              //in match questo metodo dovrà essere chiamato mandando il player attuale (non se è Hera stessa)
        int x=opponent.selectedWorker.getCell().getxCoord();
        int y=opponent.selectedWorker.getCell().getyCoord();
        return (x != 0 && x != 4 && y != 0 && y != 4);             //ritorna false se il giocatore non può vincere
    }
}



