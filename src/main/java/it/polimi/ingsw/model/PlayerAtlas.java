package it.polimi.ingsw.model;

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
                return manageStateSelection(x, y, gender);
            case 2:
                tempResponse = manageStateMove(x, y);
                if(tempResponse.getNextInstruction().equals(turnMessageOkMovement)){
                    return new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + atlasTurnMessageAskBuildDorse);
                }
                return tempResponse;
            case 3:
                if(optional.equals(turnMessageDORSE)){
                        if(selectedWorkerBuildDorse(x,y)){
                            stateOfTurn = 1;
                            tempResponse = new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageOkBuild+turnMessageTurnEnd);
                            match.nextPlayer();
                            return tempResponse;
                        }
                        else return new ChoiceResponseMessage(match.getBoard().clone(), this, atlasTurnMessageFailBuildDorse);
                }
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(turnMessageOkBuild)){
                    tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ turnMessageTurnEnd);
                    match.nextPlayer();
                    return tempResponse;
                }
                return tempResponse;
            default: return new ChoiceResponseMessage(match.getBoard().clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }
}
