package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerPrometheus extends Player {
    private boolean justBuild=false; // mi dice se ha costruito o meno prima di muoversi

    protected PlayerPrometheus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**Reset parameters of the Player with Prometheus' power.
     * <p>
     */
    @Override
    protected void resetTurn(){   //reset esplicito per USO SOLO INTERNO a dispetto dei metodi simili nelle altre classi..il giocatore dovrà per forza muoversi per non perdere e quindi il reset prima o poi viene fatto
        justBuild=false;
    }

    /**Integrate selectedWorkerBuild updating only the parameter justBuild.
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return  {@code true} if was possible to build and performed; {@code false} otherwise
     */
    @Override
    public boolean selectedWorkerBuild(int x, int y){
        justBuild = true;
        return super.selectedWorkerBuild(x, y);
    }

    /**Implement the power of Prometheus which expect that if the selectedWorker does not move up, it may build both before and after moving.
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if the movement was possible and performed; {@code false} otherwise
     */
    @Override
    public boolean selectedWorkerMove(int x, int y){
        if(!justBuild){
            return super.selectedWorkerMove(x,y);
        }
        else{
            Worker tempWorker=new Worker(selectedWorker.getGender(),selectedWorker.getColor());  //copio i valori di selected worker in un worker temporaneo
            tempWorker.move(selectedWorker.getCell());
            if(match.checkMove(x,y,tempWorker)!=null){
                if(match.checkMove(x,y,tempWorker).equals(tempWorker)) {
                    if (match.forceMove(x, y, tempWorker)) {
                        int zNow = tempWorker.getCell().getzCoord();
                        int zOld = selectedWorker.getCell().getzCoord();
                        if (this.selectedWorker.getCell().getBlock() != null) {
                            zOld++;
                        }
                        if (tempWorker.getCell().getBlock() != null) {
                            zNow++;
                        }
                        if ((zNow - zOld) > 0) {
                            match.removeWorker(tempWorker);
                            selectedWorker.getCell().setWorkerInCell(selectedWorker);
                            return false;
                        } else {
                            match.removeWorker(tempWorker);
                            if (super.selectedWorkerMove(x, y)) {
                                resetTurn();
                                return true;
                            } else {
                                selectedWorker.getCell().setWorkerInCell(selectedWorker);
                                return false;
                            }
                        }
                    }
                    selectedWorker.getCell().setWorkerInCell(selectedWorker);
                }
            }
            return false;
        }
    }

    /**Integrate manageTurn of Player with the state of the turn that expect the possibility to build before move respecting conditions of Prometheus' power.
     * <p>
     * @param x first coordinate, when its value is relevant
     * @param y second coordinate, when its value is relevant
     * @param gender of the worker to select, when its value is needed
     * @param optional a particular choice of the player, when its value is needed. In this case it specify if the player want to build before move.
     * @return ChoiceResponseMessage the message to notify to RemoteView
     */
    @Override
    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional){
        ChoiceResponseMessage tempResponse;
        if(!optional.equals(GameMessage.turnMessageBUILDBEFORE) && stateOfTurn == 2 && optional.equals("")){
            stateOfTurn++;
        }
        if(!optional.equals(GameMessage.turnMessageBUILDBEFORE)&& !optional.equals("")){
            return new ChoiceResponseMessage(this.match.clone(), this.clone(), GameMessage.prometheusTurnMessageFailOptionalBuild + GameMessage.prometheusTurnMessageAskBuildBefore);
        }
        switch(stateOfTurn){
            case 1:
                tempResponse = manageStateSelection(gender,x,y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkWorkerSelection)) {
                    tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.prometheusTurnMessageAskBuildBefore);
                }

                return tempResponse;
            case 2:
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    stateOfTurn = 3;
                    tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.turnMessageChooseCellMove);
                    return tempResponse;
                }
                return new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), GameMessage.prometheusTurnMessageFailOptionalBuild + GameMessage.prometheusTurnMessageAskBuildBefore);
            case 3:
                if(match.checkLoserMove(selectedWorker)){
                    match.removeWorker(workers[0]);
                    match.removeWorker(workers[1]);
                    match.removePlayer(this);
                    return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageLose);
                }
                tempResponse = manageStateMove(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkMovement)){
                    stateOfTurn = 4;
                    return new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.turnMessageChooseCellBuild);
                }
                return tempResponse;
            case 4:
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    resetTurn();
                    match.nextPlayer();
                    tempResponse = new ChoiceResponseMessage(match.clone(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.turnMessageTurnEnd);
                    return tempResponse;
                }
                return tempResponse;
            default: return new ChoiceResponseMessage(match.clone(), this.clone(), "Errore nello stato del turno!"); //da valutare questo default
        }
    }

}
