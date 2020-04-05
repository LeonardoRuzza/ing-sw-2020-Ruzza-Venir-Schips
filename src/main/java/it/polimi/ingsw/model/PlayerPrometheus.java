package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerPrometheus extends Player {
    private boolean justBuild=false; // mi dice se ha costruito o meno prima di muoversi

    protected PlayerPrometheus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    protected void resetTurn(){   //reset esplicito per USO SOLO INTERNO a dispetto dei metodi simili nelle altre classi..il giocatore dovrà per forza muoversi per non perdere e quindi il reset prima o poi viene fatto
        justBuild=false;
    }

    @Override
    public boolean selectedWorkerBuild(int x, int y){
        justBuild = true;
        return super.selectedWorkerBuild(x, y);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){  //NON BASTA ridefinire questo metodo...l'ordine di chiamata è del model/controller che dovranno chiamare nel caso per costruire prima di muoversi
        if(!justBuild){
            return super.selectedWorkerMove(x,y);
        }
        else{
            Worker tempWorker=new Worker(selectedWorker.getGender(),selectedWorker.getColor());  //copio i valori di selected worker in un worker temporaneo
            tempWorker.move(selectedWorker.getCell());
            if(match.forceMove(x, y,tempWorker)){
                int zNow = tempWorker.getCell().getzCoord();
                int zOld = selectedWorker.getCell().getzCoord();
                if(this.selectedWorker.getCell().getBlock() != null){
                    zOld++;
                }
                if(tempWorker.getCell().getBlock() != null){
                    zNow++;
                }
                if((zNow - zOld) > 0){
                    match.removeWorker(tempWorker);
                    return false;
                }else{
                    match.removeWorker(tempWorker);
                    if(super.selectedWorkerMove(x,y)){
                        resetTurn();
                        return true;
                    }else{
                        return false;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional){
        ChoiceResponseMessage tempResponse;
        if(!optional.equals(GameMessage.turnMessageBUILDBEFORE) && stateOfTurn == 2){
            stateOfTurn++;
        }
        switch(stateOfTurn){
            case 1:
                tempResponse = manageStateSelection(x, y, gender);
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
                return tempResponse;
            case 3:
                tempResponse = manageStateMove(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkMovement)){
                    stateOfTurn = 4;
                    return new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.turnMessageChooseCellBuild);
                }
                return tempResponse;
            case 4:
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                    match.nextPlayer();
                    tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.turnMessageTurnEnd);
                    return tempResponse;
                }
                return tempResponse;
            default: return new ChoiceResponseMessage(match.clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }

}
