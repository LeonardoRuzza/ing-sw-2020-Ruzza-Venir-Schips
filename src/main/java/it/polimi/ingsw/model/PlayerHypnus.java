package it.polimi.ingsw.model;

public class PlayerHypnus extends Player {

    public PlayerHypnus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    protected boolean setSelectedWorker(Worker.Gender selectedGender){
        Worker tempWorker=null;
        switch (selectedGender){
            case Male:
                tempWorker=workers[0];
                break;
            case Female:
                tempWorker=workers[1];
                break;
            default:
                break;
        }
        if(isHigherWorker(tempWorker)){
            selectedWorker=null;
            return false;
        }
        else{
            selectedWorker=tempWorker;
            return true;
        }
    }

    @Override
    protected boolean setSelectedWorker(Worker worker){
        if(!isHigherWorker(worker)){
            selectedWorker=worker;
            return selectedWorker!=null;
        }
        else return false;
    }

    private boolean isHigherWorker(Worker w){
        int zW, zOtherW;
        zW=w.getCell().getzCoord();
        if(w.getCell().getBlock() != null){
            zW++;
        }
        if(w.getGender() == Worker.Gender.Male){
            zOtherW=workers[1].getCell().getzCoord();
            if(workers[1].getCell().getBlock() != null){
                zOtherW++;
            }
        }
        else {
            zOtherW=workers[0].getCell().getzCoord();
            if(workers[0].getCell().getBlock() != null){
                zOtherW++;
            }
        }
        return zW>zOtherW;



/*        int zMax=0;
        for(int i=0; i<match.players.length;i++){
            for(int k=0; i<match.players[i].workers.length; k++){
                if(w != match.players[i].workers[k]){                         //controlla di non verificare sullo stesso worker ricevuto come parametro
                    if(match.players[i].workers[k].getCell().getzCoord()>zMax){
                        zMax=match.players[i].workers[k].getCell().getzCoord();
                    }
                }
            }
        }
        return w.getCell().getzCoord()>zMax;*/
    }
}
