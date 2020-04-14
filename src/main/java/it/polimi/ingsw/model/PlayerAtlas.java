package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerAtlas extends Player {

    protected PlayerAtlas(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    public boolean selectedWorkerBuildDorse(int x, int y){
        if(match.checkBuild(x, y, selectedWorker)){
            return match.forceBuildDorse(x, y, selectedWorker);
        }
        return false;
    }

    @Override
    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional){
        ChoiceResponseMessage tempResponse;
        switch(stateOfTurn){
            case 1:
                tempResponse = manageStateSelection(x, y, gender);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkWorkerSelection)){
                    tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.turnMessageChooseCellMove);
                    return tempResponse;
                }
                return tempResponse;
            case 2:
                tempResponse = manageStateMove(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkMovement)){
                    return new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.atlasTurnMessageAskBuildDorse);
                }
                return tempResponse;
            case 3:
                if(optional.equals(GameMessage.turnMessageDORSE)){
                        if(selectedWorkerBuildDorse(x,y)){
                            stateOfTurn = 1;
                            match.nextPlayer();
                            tempResponse = new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageOkBuild+GameMessage.turnMessageTurnEnd);
                            return tempResponse;
                        }
                        else return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.atlasTurnMessageFailBuildDorse);
                }
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    match.nextPlayer();
                    tempResponse = new ChoiceResponseMessage(match.clone(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.turnMessageTurnEnd);
                    return tempResponse;
                }
                return tempResponse;
            default: return new ChoiceResponseMessage(match.clone(), this.clone(), "Errore nello stato del turno!"); //da valutare questo default
        }
    }
}
