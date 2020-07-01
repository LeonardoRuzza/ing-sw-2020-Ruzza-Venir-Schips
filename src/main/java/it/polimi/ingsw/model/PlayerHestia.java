package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerHestia extends Player {

    private boolean firstBuild=true;

    protected PlayerHestia(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**Reset parameters of the turn of the Player with Hestia's power.
     *
     */
    @Override
    protected void resetTurn(){
        firstBuild=true;
    }

    /**Integrate selectedWorkerBuild of Player with the power of Hestia which expect to build two times but not on a perimeter cell the second time (respecting other standard building rules).
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if was possible to build and performed; {@code false} otherwise
     */
    @Override
    protected boolean selectedWorkerBuild(int x, int y){
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

    /**Integrate manageTurn of Player with the state of turn of choose to build two times expected by Hestia's power.
     *<p>
     * @param x first coordinate, when its value is relevant
     * @param y second coordinate, when its value is relevant
     * @param gender of the worker to select, when its value is needed
     * @param optional a particular choice of the player, when its value is needed
     * @return ChoiceResponseMessage the message to notify to RemoteView
     */
    @Override
    protected ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional){
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
                    return new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.turnMessageChooseCellBuild);
                }
                return tempResponse;
            case 3:
                tempResponse = super.manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    stateOfTurn = 4;
                    tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
                    return tempResponse;
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
                }else if(optional.equals(GameMessage.turnMessageNO)){
                    stateOfTurn = 1;
                    resetTurn();
                    match.nextPlayer();
                    return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageTurnEnd);
                }
                return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageErrorInSyntax+GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
            default:
                throw new RuntimeException(); //not possible case
        }
    }

    /**Integrate manageStateBuild with specific messages for the player with Hestia's power.
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return ChoiceResponseMessage to return to manageTurn and (modified or not) then to RemoteView. Specify the result of the tried build.
     */
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
