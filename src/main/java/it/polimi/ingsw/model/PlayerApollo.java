package it.polimi.ingsw.model;

public class PlayerApollo extends Player {

    protected PlayerApollo(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){
        Worker tempWorker;
        if(!super.selectedWorkerMove(x, y)){
            tempWorker=match.checkMove(x,y,selectedWorker);
            if (tempWorker == null || selectedWorker.getCell() == null) return false; //la seconda condizione per locate iniziale
            if(!tempWorker.equals(selectedWorker)){
                int xOld = selectedWorker.getCell().getxCoord();
                int yOld = selectedWorker.getCell().getyCoord();
                boolean retValue1 = match.forceMove(x, y, selectedWorker) && match.forceMove(xOld, yOld, tempWorker);
                if(retValue1){
                    match.getBoard().cells[x][y][selectedWorker.getCell().getzCoord()].setWorkerInCell(selectedWorker);
                }
                return retValue1;
            }
        }
        return true;
    }
}
