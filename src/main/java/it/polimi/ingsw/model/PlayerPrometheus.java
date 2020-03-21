package it.polimi.ingsw.model;

public class PlayerPrometheus extends Player {
    private boolean justBuild=false;

    public PlayerPrometheus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    protected void resetTurn(){   //reset esplicito per USO SOLO INTERNO a dispetto dei metodi simili nelle altre classi..il giocatore dovrà per forza muoversi per non perdere e quindi il reset prima o poi viene fatto
        justBuild=false;
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){  //NON BASTA ridefinire questo metodo...l'ordine di chiamata è del model/controller che dovranno chiamare nel caso per costruire prima di muoversi
    if(!justBuild){
        return super.selectedWorkerMove(x,y);
    }
    else{
        Worker tempWorker=new Worker(selectedWorker.getGender(),selectedWorker.getColor());  //copio i valori di selected worker in un worker temporaneo
        tempWorker.move(selectedWorker.getCell());
        if(match.forceMove(x, y,tempWorker)){
            if(tempWorker.getCell().getzCoord() != selectedWorker.getCell().getzCoord()){
                match.removeWorker(tempWorker);
                return false;
            }
            else{
                match.removeWorker(tempWorker);
                if(super.selectedWorkerMove(x,y)){
                    resetTurn();
                    return true;
                }
                else return false;
            }
        }
        return false;
    }
    }
}
