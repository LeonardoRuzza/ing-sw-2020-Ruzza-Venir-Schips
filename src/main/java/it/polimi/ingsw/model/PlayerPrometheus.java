package it.polimi.ingsw.model;

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
        switch(stateOfTurn){
            case 1:
                if(!optional.equals("BUILDBEFORE")){
                    stateOfTurn++;
                }
                return manageStateSelection(x, y, gender);
            case 2:
                tempResponse = super.manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                    stateOfTurn = 3;
                    tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ ". Scegli dove muoverti");
                    return tempResponse;
                }
                return tempResponse;
            case 3:
                tempResponse = manageStateMove(x, y);
                if(tempResponse.getNextInstruction().equals("Ti sei mosso correttamente")){
                    return new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + ". Scegli dove crostruire");
                }
                return tempResponse;
            case 4:
                tempResponse = super.manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                    tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ " e il tuo turno è terminato");
                    match.nextPlayer();
                    return tempResponse;
                }
                return tempResponse;
            default: return new ChoiceResponseMessage(match.getBoard().clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }

}
