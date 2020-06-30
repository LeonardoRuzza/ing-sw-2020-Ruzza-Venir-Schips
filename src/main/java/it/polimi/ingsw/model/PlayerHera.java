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
    protected boolean checkLimitWin(Player opponent){
        if(opponent.equals(this)) return true;
        int x=opponent.selectedWorker.getCell().getxCoord();
        int y=opponent.selectedWorker.getCell().getyCoord();
        return (x != 0 && x != 4 && y != 0 && y != 4);
    }
}



