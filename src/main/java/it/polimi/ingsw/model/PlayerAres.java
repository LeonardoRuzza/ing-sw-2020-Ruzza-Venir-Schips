package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerAres extends Player {

    protected PlayerAres(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

   @Override
   protected boolean notSelectedWorkerRemoveBlock(int x, int y){
        Worker tempWorker;
        if(selectedWorker.equals(workers[0])){
            tempWorker=workers[1];
        }
        else tempWorker=workers[0];
        if(match.checkBuild(x, y,tempWorker)){
            match.removeBlock(x,y);
            return true;
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
                return manageStateMove( x, y);
            case 3:
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    stateOfTurn = 4; //sovrascrive quello che mette di default l'altro medoto maageStateBuild
                    tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.aresTurnMessageAskRemoveBlok); //da valutare remoteview
                    return tempResponse;
                }
                return tempResponse;
            case 4:
                if(optional.equals(GameMessage.turnMessageNO)){
                    stateOfTurn = 1;
                    match.nextPlayer();
                    return new ChoiceResponseMessage(match.clone(), this, GameMessage.turnMessageTurnEnd);
                }
                if(notSelectedWorkerRemoveBlock(x, y)){
                    match.nextPlayer();
                    stateOfTurn = 1;
                    tempResponse = new ChoiceResponseMessage(match.clone(), this, GameMessage.aresTurnMessageSuccessRemoveBlokWEnd);
                    return tempResponse;
                }
                stateOfTurn = 4;
                return new ChoiceResponseMessage(match.clone(), this, GameMessage.aresTurnMessageFailRemoveBlokWNewCell);

            default:
                return new ChoiceResponseMessage(match.clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }
}
