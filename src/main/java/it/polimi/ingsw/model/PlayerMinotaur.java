package it.polimi.ingsw.model;

public class PlayerMinotaur extends Player {

    protected PlayerMinotaur(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**Implement the power of Minotaur which allow that the Player's selectedWorker may move into an opponent worker’s space, if his worker can be forced one space straight backwards to an unoccupied space at any level.
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if the movement was possible and performed; {@code false} otherwise
     */
    @Override
    public boolean selectedWorkerMove(int x, int y){
        Worker tempWorker;
        int x0, y0, xFinal, yFinal;
        if(super.selectedWorkerMove(x, y)){return true;}
        if(selectedWorker.getCell() == null) return false;
        tempWorker=match.checkMove(x, y, selectedWorker);
        if(tempWorker!=null && !tempWorker.getColor().equals(selectedWorker.getColor())){
            x0=selectedWorker.getCell().getxCoord();
            y0=selectedWorker.getCell().getyCoord();
            xFinal=x-x0;
            yFinal=y-y0;
            if(match.checkBuild(x+xFinal, y+yFinal, tempWorker)){     //return true if there isn't a dome or worker, what we need to know
                return match.forceMove(x + xFinal, y + yFinal, tempWorker) && match.forceMove(x, y, selectedWorker);
            }
        }
        return false;
    }
}
