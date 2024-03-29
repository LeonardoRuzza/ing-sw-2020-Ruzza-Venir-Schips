package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerHephaestus extends Player {
    private boolean firstBuild=true;
    private int x0=-1, y0=-1;
    private boolean usedSuperPower = true;
    protected PlayerHephaestus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**Reset parameters of the turn of the Player with Hephaestus's power.
     *
     */
    @Override
    protected void resetTurn(){
        firstBuild=true;
        x0=-1;
        y0=-1;

    }

    /**Implement the power of Hephaestus which allow to build two times on the same cell (respecting the others building's rules).
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
                x0=x;
                y0=y;
                return true;
            }
            else return false;
        }
        else {
            if(x0 ==x && y0==y){
                if(super.selectedWorkerBuild(x,y)){              //independent if is going to build a dome because the next checkBuild in this case will return false and then we'll call removeBlock
                    if(!(match.checkBuild(x,y,selectedWorker))){
                        match.removeBlock(x,y);
                        resetTurn();
                        return false;
                    }
                    else{
                        resetTurn();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**Integrate manageTurn with the state of turn expected by Hephaestus' power to choose to build two times.
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
                    return new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.hephaesthusTurnMessageAskBuild);
                }
                return tempResponse;
            case 3:
                if(!optional.equals(GameMessage.turnMessageBUILDTWOTIMES) || !usedSuperPower){
                    tempResponse = super.manageStateBuild(x, y);
                    if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                        resetTurn();
                        match.nextPlayer();
                        tempResponse = new ChoiceResponseMessage(match.clone(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.turnMessageTurnEnd);
                        usedSuperPower = true;
                        return tempResponse;
                    }
                    usedSuperPower = false;
                    return tempResponse;
                }
                tempResponse = super.manageStateBuild(x, y);
                if(!tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    return new ChoiceResponseMessage(match.clone(), tempResponse.getPlayer(), GameMessage.hephaesthusTurnMessageFailOptionalBuild + GameMessage.hephaesthusTurnMessageAskBuild);
                }
                tempResponse = manageStateBuild(x, y);
                resetTurn();
                match.nextPlayer();
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    tempResponse = new ChoiceResponseMessage(match.clone(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.turnMessageTurnEnd);
                }else{
                    tempResponse = new ChoiceResponseMessage(match.clone(), tempResponse.getPlayer(), tempResponse.getNextInstruction());
                }
                return tempResponse;
            default:
                throw new RuntimeException(); //not possible case
        }
    }

    /**Integrate manageStateBuild with specific messages for the Player with Hephaestus' power.
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return ChoiceResponseMessage to return to manageTurn and (modified or not) then to RemoteView. Specify the result of the tried build.
     */
    @Override
    protected ChoiceResponseMessage manageStateBuild(int x, int y){
        if(selectedWorkerBuild(x,y)) {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageOkBuild);
        }else {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.hephaesthusTurnMessageFailOptionalBuildWEnd);
        }
    }
}
