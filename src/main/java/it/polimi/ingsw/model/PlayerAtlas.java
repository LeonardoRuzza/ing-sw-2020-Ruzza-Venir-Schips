package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerAtlas extends Player {

    protected PlayerAtlas(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**This method implement the power of Atlas of build dorse at any level (respecting the others rules).
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if build dorse was possible and performed; {@code false} otherwise
     */
    public boolean selectedWorkerBuildDorse(int x, int y){
        if(match.checkBuild(x, y, selectedWorker)){
            return match.forceBuildDorse(x, y, selectedWorker);
        }
        return false;
    }

    /**This method allow to integrate manageTurn with the power of Atlas, allowing to the Player to choose what type of building do.
     * <p>
     * @param x first coordinate, when its value is relevant
     * @param y second coordinate, when its value is relevant
     * @param gender of the worker to select, when its value is needed
     * @param optional a particular choice of the player, when its value is needed. In this case it's used eventually to choose to build a "normal" block or a dorse.
     * @return ChoiceResponseMessage the message to notify to RemoteView
     */
    @Override
    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional){
        ChoiceResponseMessage tempResponse;
        switch(stateOfTurn){
            case 1:
                tempResponse = manageStateSelection(gender,x,y);
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
