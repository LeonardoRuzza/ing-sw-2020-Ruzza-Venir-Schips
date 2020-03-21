package it.polimi.ingsw.model;

public class PlayerArtemis extends Player {

    public PlayerArtemis(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){ //si può chiedere al giocatore di scegliere direttamente la posizione finale e poi verificare con delle check move se era possibile raggiungere le x,y finali con un'altra casella intermedia (doppio ciclo for)
        Worker tempWorker1;
        Worker tempWorker2;
        Cell tempCell;
        if( x != selectedWorker.getCell().getxCoord() && y != selectedWorker.getCell().getyCoord()){  //non si può retrocedere sulla stessa casella
            if(super.selectedWorkerMove(x,y)){return true;}                                           //caso in cui si sposta solo una volta! specificalo! richiama il super!
            for(int i=-1; i <= 1 ; i++){                                                              //scorro le possibili x del primo movimento
                for(int j=-1; j<=1  ; j++){                                                           //scorro le possibili y del primo movimento
                    if(i==0 && j==0){ continue; }
                    tempWorker1=null;
                    tempWorker2=null;
                    tempCell=null;
                    tempWorker1=match.checkMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,selectedWorker); //valuto se è possibile spostarsi sulla casella intermedia individuata
                    if(tempWorker1.equals(selectedWorker)){                                                                                     //nel caso sia possibile allora costruisco un worker temporaneo per vedere se si può raggiungere la casella finale
                        tempWorker2=new Worker(selectedWorker.getGender(),selectedWorker.getColor());                                           //copio i valori di selected worker in un worker temporaneo
                        tempWorker2.move(selectedWorker.getCell());
                        if(match.forceMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,tempWorker2)){
                            tempWorker1=match.checkMove(x, y, tempWorker2);
                            if(tempWorker1.equals(tempWorker2)){  //adesso cancello il tempWorker2 e lo riutilizzo come prima per simulare il secondo spostamento (elimino con la remove la traccia lasciata nella cella). la checkMove non bastava come verifica perchè potrebbero esserci limitaizoni avversaria nella forceMove e non si potrebbe più tornare indietro fatta la prima mossa col vero worker.
                                tempCell=tempWorker2.getCell();
                                match.removeWorker(tempWorker2);
                                tempWorker2=new Worker(selectedWorker.getGender(),selectedWorker.getColor());
                                tempWorker2.move(tempCell);
                                if(match.forceMove(x, y, tempWorker2)){
                                    match.removeWorker(tempWorker2);
                                    match.forceMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,selectedWorker); //su queste moveForce non serve fare i controlli perchè per forza ok dalla precedente simulazione su tempworker2
                                    match.forceMove(x, y, selectedWorker);
                                    return true;
                                }
                            }
                            match.removeWorker(tempWorker2);
                        }
                    }
                }
            }
        }
        return false;
    }
}
