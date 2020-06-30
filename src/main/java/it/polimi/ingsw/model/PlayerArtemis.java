package it.polimi.ingsw.model;

public class PlayerArtemis extends Player {

    protected PlayerArtemis(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**This method implement the power of Artemis allowing the double movement of the selectedWorker of the Player (not to the same initial cell).
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if the movement was possible and performed; {@code false} otherwise
     */
    @Override
    protected boolean selectedWorkerMove(int x, int y){
        Worker tempWorker1;
        Worker tempWorker2;
        Cell tempCell;
        if(selectedWorker.getCell() == null) {return super.selectedWorkerMove(x,y);}                  //first locate case
        if( x != selectedWorker.getCell().getxCoord() || y != selectedWorker.getCell().getyCoord()){  //not possible to come back to the initial cell
            if(super.selectedWorkerMove(x,y)){return true;}
            for(int i=-1; i <= 1 ; i++){
                for(int j=-1; j<=1  ; j++){
                    if(i==0 && j==0){ continue; }
                    tempWorker1=null;
                    tempWorker2=null;
                    tempCell=null;
                    tempWorker1=match.checkMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,selectedWorker); //evaluate the intermediate cell
                    if(tempWorker1 == null){ continue;}
                    if(tempWorker1.equals(selectedWorker)){                                                                                     //in true case use a temporary worker to check the possible second movement
                        tempWorker2=new Worker(selectedWorker.getGender(),selectedWorker.getColor());
                        tempWorker2.move(selectedWorker.getCell());
                        if(match.forceMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,tempWorker2)){
                            tempWorker1=match.checkMove(x, y, tempWorker2);
                            if(tempWorker1 == null){ match.removeWorker(tempWorker2); continue;}
                            if(tempWorker1.equals(tempWorker2)){  //tempWorker2 is reused like before to simulate the second movement (remove is used to cancel the previously "trace" ). checkMove was not sufficient because can be others opponent's limitation  in the forceMove and then we can't turn back from the first movement with the true worker
                                tempCell=tempWorker2.getCell();
                                match.removeWorker(tempWorker2);
                                tempWorker2=new Worker(selectedWorker.getGender(),selectedWorker.getColor());
                                tempWorker2.move(tempCell);
                                if(match.forceMove(x, y, tempWorker2)){
                                    match.removeWorker(tempWorker2);
                                    match.forceMove(selectedWorker.getCell().getxCoord()+i, selectedWorker.getCell().getyCoord()+j,selectedWorker); //not necessary to do other controls now for the forceMove, just verified before
                                    match.forceMove(x, y, selectedWorker);
                                    return true;
                                }
                            }
                            match.removeWorker(tempWorker2);
                        }
                    }
                }
            }
            selectedWorker.getCell().setWorkerInCell(selectedWorker);
        }
        return false;
    }
}
