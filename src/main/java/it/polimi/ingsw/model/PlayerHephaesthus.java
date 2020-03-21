package it.polimi.ingsw.model;

public class PlayerHephaesthus extends Player {
    private boolean firstBuild=true;
    public PlayerHephaesthus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    protected void resetTurn(){         //reset esplicito necessario nel caso venga eseguita una sola costruzione da Hephaesthus
        firstBuild=true;
    }

    @Override
    public boolean selectedWorkerBuild(int x, int y){
        if(firstBuild){
            if(super.selectedWorkerMove(x,y)){
                firstBuild=false;
                return true;
            }
            else return false;
        }
        else if(super.selectedWorkerBuild(x,y)){              //costruisco indipendentemente se è una cupola perchè in questo caso la checkBuild seguente ritorna false e allora applicherei la removeBlock
            if(!(match.checkBuild(x,y,selectedWorker))){
                match.removeBlock(x,y);
                resetTurn();
                return false;
            }
            else{
                resetTurn();
                return true;
            }
        }
        return false;
    }
}
