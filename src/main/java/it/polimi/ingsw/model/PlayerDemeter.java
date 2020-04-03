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
        }else{
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
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(turnMessageOkBuild)){
                    stateOfTurn = 4;
                    return new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ hestiaDemeterTurnMessageAskTwoBuild);
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
                }
                stateOfTurn = 1;
                resetTurn();
                match.nextPlayer();
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
