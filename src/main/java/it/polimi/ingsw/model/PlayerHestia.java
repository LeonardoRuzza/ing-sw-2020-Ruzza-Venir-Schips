package it.polimi.ingsw.model;

public class PlayerHestia extends Player {
    private boolean firstBuild=true;
    private int firstX=-1, firstY=-1;

    public PlayerHestia(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    protected void resetTurn(){         //reset esplicito necessario nel caso venga eseguita una sola costruzione da Hestia
        firstBuild=true;
        firstX=-1;
        firstY=-1;
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){
        if(firstBuild){
            if(super.selectedWorkerBuild(x,y)){
                firstBuild=false;
                firstX=x;
                firstY=y;
                return true;
            }
            else return false;
        }
        else if(!(x==0 || x==4 || y==0 || y==4)){             //condizione corrette per indicare caselle perimetrali?
            if(super.selectedWorkerBuild(x,y)){
                resetTurn();
                return true;
            }
            else return false;
        }
        return false;
    }

    }
}
