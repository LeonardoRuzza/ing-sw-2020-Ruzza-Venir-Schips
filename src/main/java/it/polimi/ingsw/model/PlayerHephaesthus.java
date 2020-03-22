package it.polimi.ingsw.model;

public class PlayerHephaesthus extends Player {
    private boolean firstBuild=true;
    private int x0=-1, y0=-1;
    public PlayerHephaesthus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    protected void resetTurn(){         //reset esplicito necessario nel caso venga eseguita una sola costruzione da Hephaesthus
        firstBuild=true;
        x0=-1;
        y0=-1;

    }

    @Override
    public boolean selectedWorkerBuild(int x, int y){
        if(firstBuild){
            if(super.selectedWorkerBuild(x,y)){
                firstBuild=false;
                x0=x;
                y0=y;
                return true;
            }
            else return false;
        }
        else {
            if(x0 ==x && y0==y){                                 //verifico che vogli costruire sulla stessa cella precedente
                if(super.selectedWorkerBuild(x,y)){              //costruisco indipendentemente se è una cupola perchè in questo caso la checkBuild seguente ritorna false e allora applicherei la removeBlock
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
            }                   //nel caso salti il primo if ritorna poi false per farsi dare un'ltra casella adeguata
        }
        return false;
    }
}
