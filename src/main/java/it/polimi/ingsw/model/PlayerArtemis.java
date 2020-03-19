package it.polimi.ingsw.model;

public class PlayerArtemis extends Player {

    public PlayerArtemis(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){ //si può chiedere al giocatore di scegliere direttamente la posizione finale e poi verificar con delle check move se era possibile raggiungere le x,y finali con un'altra casella intermedia (doppio ciclo for)
        Worker tempWorker1;
        Worker tempWorker2;
        if( x != selectedWorker.getCell().getxCoord() && y != selectedWorker.getCell().getyCoord()){  //non si può retrocedere sulla stessa casella
            if(super.selectedWorkerMove(x,y)){return true;}                                           //caso in cui si sposta solo una volta! specificalo! richiama il super!
            for(int i=-1; i <= 1 ; i++){                                                              //scorro le possibili x del primo movimento
                for(int j=-1; j<=1  ; j++){                                                           //scorro le possibili y del primo movimento
                    if(i==0 && j==0){ continue; }
                    tempWorker1=null;
                    tempWorker2=null;
                    tempWorker1=match.checkMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,selectedWorker); //valuto se è possibile spostarsi sulla casella intermedia individuata
                    if(tempWorker1.equals(selectedWorker)){                                                                                     //nel caso sia possibile allora costruisco un worker temporaneo per vedere se si può raggiungere la casella finale
                        tempWorker2=new Worker(selectedWorker.getGender(),selectedWorker.getColor());                                           //copio i valori di selected worker in un worker temporaneo
                        tempWorker2.setCell(selectedWorker.getCell());
                        match.forceMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,tempWorker2);
                        tempWorker1=match.checkMove(x, y, tempWorker2);
                        if(tempWorker1.equals(tempWorker2)){
                            match.forceMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,selectedWorker);
                            match.forceMOve(x, y, selectedWorker);
                            //match.removeWorker(Worker);                                                                                       //deve rimuovere il worker dalla cella ovunque esso si trovi
                            return true;
                        }
                        //match.removeWorker();
                    }
                }
            }
        }
        return false;
    }
}
