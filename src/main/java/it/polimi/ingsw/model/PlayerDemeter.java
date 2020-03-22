package it.polimi.ingsw.model;

public class PlayerDemeter extends Player {
    private boolean firstBuild=true;
    private int firstX=-1, firstY=-1; //inferiscono valori diversi da x e y per forza (qui non è necesario ma alla fine di selectedWorker Move nell'ultimo if lo è

    public PlayerDemeter(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    protected void resetTurn(){         //reset esplicito necessario nel caso venga eseguita una sola costruzione da Demeter
        firstBuild=true;
        firstX=-1;
        firstY=-1;
    }

    @Override
    public boolean selectedWorkerBuild(int x, int y){  //questo metodo funzionerebbe solo se venisse chiamata sempre due volte la costruzione... funziona sempre solo se viene chiamato resetTurn quando Demeter costruisce una volta sola
        if(firstBuild){
            if(super.selectedWorkerBuild(x,y)){
                firstBuild=false;
                firstX=x;
                firstY=y;
                return true;
            }
            else return false;
        }
        else if(x!=firstX && y!=firstY){
             if(super.selectedWorkerBuild(x,y)){
                resetTurn();
                return true;
             }
             else return false;
        }
        return false;
    }
}
