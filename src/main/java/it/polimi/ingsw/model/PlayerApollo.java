package it.polimi.ingsw.model;

public class PlayerApollo extends Player {

    public PlayerApollo(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){   //da controllare se le forceMove vanno a buon fine anche se limitazioni di carte avversarie che in reltà non dovrebbero valere in questo caso....inoltre modellizare lo spostamento forzato da un avversario!
        Worker tempWorker;
        if(!super.selectedWorkerMove(x, y)){
            tempWorker=match.checkMove(x,y,selectedWorker);
            if(!tempWorker.equals(selectedWorker) && tempWorker!=null){
               if( match.forceMove(x, y, selectedWorker) && match.forceMove(selectedWorker.getOldLocation().getxCoord(), selectedWorker.getOldLocation().getyCoord(), tempWorker){ return true;}
            }
        }
        return false;

    }
}
