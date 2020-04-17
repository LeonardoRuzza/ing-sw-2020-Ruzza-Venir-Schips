package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

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
                tempResponse = manageStateSelection(gender);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkWorkerSelection)){
                    tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.turnMessageChooseCellMove);
                    return tempResponse;
                }
                return tempResponse;
            case 2:
                tempResponse = manageStateMove(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkMovement)){
                    return new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.turnMessageChooseCellBuild);
                }
                return tempResponse;
            case 3:
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    stateOfTurn = 4;
                    return new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
                }
                return tempResponse;
            case 4:
                if(optional.equals(GameMessage.turnMessageBUILDTWOTIMES)){
                    tempResponse = manageStateBuild(x, y);
                    if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                        match.nextPlayer();
                        tempResponse = new ChoiceResponseMessage(match.clone(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.turnMessageTurnEnd);
                        return tempResponse;
                    }
                    return tempResponse;
                }
                stateOfTurn = 1;
                resetTurn();
                match.nextPlayer();
                return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageTurnEnd);
            default: return new ChoiceResponseMessage(match.clone(), this.clone(), "Errore nello stato del turno!"); //da valutare questo default
        }
    }

    @Override
    protected ChoiceResponseMessage manageStateBuild(int x, int y){
        if(match.checkLoserBuild(selectedWorker)){
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.hestiaDemeterTurnMessageFailOptionalBuildWEnd);
        }
        if(selectedWorkerBuild(x,y)) {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageOkBuild);
        }else {
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.hestiaDemeterTurnMessageFailOptionalBuildWNewCell);
        }
    }
}
