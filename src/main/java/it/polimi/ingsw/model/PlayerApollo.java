package it.polimi.ingsw.model;

public class PlayerApollo extends Player {

    public PlayerApollo(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){
        Worker tempWorker;
        if(!super.selectedWorkerMove(x, y)){
            tempWorker=match.checkMove(x,y,selectedWorker);
            if (tempWorker == null) return false;
            if(!tempWorker.equals(selectedWorker)){
                return match.forceMove(x, y, selectedWorker) && match.forceMove(selectedWorker.getOldLocation().getxCoord(), selectedWorker.getOldLocation().getyCoord(), tempWorker);
            }
        }
        return false;

    }
}
