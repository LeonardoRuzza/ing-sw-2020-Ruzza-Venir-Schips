package it.polimi.ingsw.model;

public class PlayerDemeter extends Player {
    private boolean firstBuild=true;
    private int firstX=-1, firstY=-1; //inferiscono valori diversi da x e y per forza (qui non è necesario ma alla fine di selectedWorker Move nell'ultimo if lo è

    protected PlayerDemeter(String nickname, int number, Card card, Match match, Worker.Color color) {
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
        else{
            if(x!=firstX || y!=firstY){
                if(super.selectedWorkerBuild(x,y)){
                    resetTurn();
                    return true;
                }
                else return false;
            }
            return false;
        }
    }

    @Override
    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional) {
        switch(stateOfTurn){
            case 1:
                return manageStateSelection(x, y, gender);
            case 2:
                return manageStateMove(x, y);
            case 3:
                ChoiceResponseMessage tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                    stateOfTurn = 4;
                    return new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ " scegli dove effettuare la seconda costruzione sennò scrivi NO");
                }
                return tempResponse;
            case 4:
                if(optional.equals("BUILDTWOTIMES")){
                    tempResponse = manageStateBuild(x, y);
                    if(tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                        tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ " e il tuo turno è terminato");
                        match.nextPlayer();
                        return tempResponse;
                    }
                    return tempResponse;
                }else if(optional.equals("NO")){
                    stateOfTurn = 1;
                    resetTurn();
                    match.nextPlayer();
                    return new ChoiceResponseMessage(match.getBoard().clone(), this, "Fine del tuo turno!");
                }
                return new ChoiceResponseMessage(match.getBoard().clone(), this, "Fine del tuo turno!"); //questa riga è una specie di default se non vengono passati parametri corretti?

            default: return new ChoiceResponseMessage(match.getBoard().clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }
}
