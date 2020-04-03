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
                if(tempResponse.getNextInstruction().equals(turnMessageOkMovement)){
                    return new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + turnMessageChooseCellBuild);
                }
                return tempResponse;
            case 3:
                tempResponse = super.manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(turnMessageOkBuild)){
                    stateOfTurn = 4;
                    tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ hestiaDemeterTurnMessageAskTwoBuild);
                    return tempResponse;
                }
                return tempResponse;
            case 4:
                if(optional.equals(turnMessageBUILDTWOTIMES)){
                    tempResponse = manageStateBuild(x, y);
                    if(tempResponse.getNextInstruction().equals(turnMessageOkBuild)){
                        tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ turnMessageTurnEnd);
                        match.nextPlayer();
                        return tempResponse;
                    }
                    return tempResponse;
                }else if(optional.equals(turnMessageNO)){
                    stateOfTurn = 1;
                    resetTurn();
                    match.nextPlayer();
                    return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageTurnEnd);
                }
                return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageTurnEnd);
            default: return new ChoiceResponseMessage(match.getBoard().clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }

    @Override
    protected ChoiceResponseMessage manageStateBuild(int x, int y){
        if(match.checkLoserBuild(selectedWorker)){
            return new ChoiceResponseMessage(match.getBoard().clone(), this, hestiaDemeterTurnMessageFailOptionalBuildWEnd);
        }
        if(selectedWorkerBuild(x,y)) {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageOkBuild);
        }else {
            return new ChoiceResponseMessage(match.getBoard().clone(), this, hestiaDemeterTurnMessageFailOptionalBuildWNewCell);
        }
    }

}
