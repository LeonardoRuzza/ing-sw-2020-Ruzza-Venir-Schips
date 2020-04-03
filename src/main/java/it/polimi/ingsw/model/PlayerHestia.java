package it.polimi.ingsw.model;

public class PlayerHestia extends Player {
    private boolean firstBuild=true;
    private int firstX=-1, firstY=-1;

    protected PlayerHestia(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    protected void resetTurn(){         //reset esplicito necessario nel caso venga eseguita una sola costruzione da Hestia
        firstBuild=true;
    }

    @Override
    public boolean selectedWorkerBuild(int x, int y){
        if(firstBuild){
            if(super.selectedWorkerBuild(x,y)){
                firstBuild=false;
                return true;
            }
            else return false;
        }
        else if(!(x==0 || x==4 || y==0 || y==4)){
            if(super.selectedWorkerBuild(x,y)){
                resetTurn();
                return true;
            }
            else return false;
        }
        return false;
    }

    @Override
    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional){
        ChoiceResponseMessage tempResponse;
        switch(stateOfTurn){
            case 1:
                return manageStateSelection(x, y, gender);
            case 2:
                tempResponse = manageStateMove(x, y);
                if(tempResponse.getNextInstruction().equals("Ti sei mosso correttamente")){
                    return new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + "Scegli dove crostruire e nel caso tu voglia costruire due volte scrivi BUILDTWOTIMES dopo la casella");
                }
                return tempResponse;
            case 3:
                tempResponse = super.manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                    stateOfTurn = 4;
                    tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ " e il tuo turno è terminato");
                    return tempResponse;
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
                return new ChoiceResponseMessage(match.getBoard().clone(), this, "Fine del tuo turno!");
            default: return new ChoiceResponseMessage(match.getBoard().clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }

    @Override
    protected ChoiceResponseMessage manageStateBuild(int x, int y){
        if(selectedWorkerBuild(x,y)) {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.getBoard().clone(), this, "Costruzione eseguita");
        }else {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.getBoard().clone(), this, "Non puoi costruire qui. Fine del turno.");
        }
    }

}
