package it.polimi.ingsw.model;

public class PlayerMinotaur extends Player {

    public PlayerMinotaur(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){
        Worker tempWorker;
        int x0, y0, xFinal, yFinal;
        if(super.selectedWorkerMove(x, y)){return true;}
        tempWorker=match.checkMove(x, y, selectedWorker);
        if(tempWorker!=null && !tempWorker.equals(selectedWorker)){                                                          //ora serve capire la direzione dello spostamento per poter coerentemente spostarci il worker avversario
            x0=selectedWorker.getCell().getxCoord();
            y0=selectedWorker.getCell().getyCoord();
            xFinal=x-x0;
            yFinal=y-y0;
            if(match.checkBuild(x+xFinal, y+yFinal, tempWorker)){                                                           //ritorna true se non c'è sopra una cupola o un worker! Perfetto!
                if(match.forceMove(x+xFinal, y+yFinal , tempWorker) && match.forceMove(x, y, selectedWorker)){return true;}
            }
        }
        return false;
    }
}
