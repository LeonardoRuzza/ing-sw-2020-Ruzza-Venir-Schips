package it.polimi.ingsw.model;

public class PlayerApollo extends Player {

    protected PlayerApollo(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**This method implement the power of Apollo allowing the movement of the selectedWorker of the Player on a cell occupied by an opponent's worker switching them (respecting the other standard rules of movement).
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if the movement was possible and performed; {@code false} otherwise
     */
    @Override
    protected boolean selectedWorkerMove(int x, int y){
        Worker tempWorker;
        if(!super.selectedWorkerMove(x, y)){
            tempWorker=match.checkMove(x,y,selectedWorker);
            if (tempWorker == null || selectedWorker.getCell() == null) return false; //the second condition is for the initial allocation
            if(!tempWorker.getColor().equals(selectedWorker.getColor())){
                int xOld = selectedWorker.getCell().getxCoord();
                int yOld = selectedWorker.getCell().getyCoord();
                int zOld = selectedWorker.getCell().getzCoord();
                Cell oldCell = match.getBoard().cells[xOld][yOld][zOld];
                if(match.forceMove(x, y, selectedWorker)){
                    tempWorker.move(oldCell);
                    oldCell.setWorkerInCell(tempWorker);
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
