package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerDemeter extends Player {
    private boolean firstBuild=true;
    private int firstX=-1, firstY=-1; //inferiscono valori diversi da x e y per forza (qui non è necesario ma alla fine di selectedWorker Move nell'ultimo if lo è

    protected PlayerDemeter(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**Reset parameters of the turn of the Player with Demeter's power.
     *
     */
    @Override
    protected void resetTurn(){
        firstBuild=true;
        firstX=-1;
        firstY=-1;
    }

    /**This method implement the power of Demeter which allow the Player to build two times but on different cells (respecting the others building rules).
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if was possible to build and performed; {@code false} otherwise
     */
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

    /**This method allow to integrate manageTurn with the state of turn of the possible double build expected by the power of Demeter.
     * <p>
     * @param x first coordinate, when its value is relevant
     * @param y second coordinate, when its value is relevant
     * @param gender of the worker to select, when its value is needed
     * @param optional a particular choice of the player, when its value is needed. In this case it can specify if the Player want to build two times or not.
     * @return ChoiceResponseMessage the message to notify to RemoteView
     */
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

    /**This method integrate the manageStateBuild with specific message for the Player with Demeter's power.
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
