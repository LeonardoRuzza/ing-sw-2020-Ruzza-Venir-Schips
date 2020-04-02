package it.polimi.ingsw.model;

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
                if(tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                    stateOfTurn = 4; //sovrascrive quello che mette di default l'altro medoto maageStateBuild
                    tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ " selezione la casella dalla quale vuoi rimuovere un blocco vicino all'operaio che non hai spostato sennò scrivi NO"); //da valutare remoteview
                    return tempResponse;
                }
                return tempResponse;
            case 4:
                if(optional.equals("NO")){
                    stateOfTurn = 1;
                    match.nextPlayer();
                    return new ChoiceResponseMessage(match.getBoard().clone(), this, "Fine del tuo turno!");
                }
                if(notSelectedWorkerRemoveBlock(x, y)){
                    tempResponse = new ChoiceResponseMessage(match.getBoard().clone(), this, "Il blocco è stato rimosso ed è finito il turno!");
                    match.nextPlayer();
                    stateOfTurn = 1;
                    return tempResponse;
                }
                stateOfTurn = 4;
                return new ChoiceResponseMessage(match.getBoard().clone(), this, "Il blocco NON è stato rimosso, inserire altri valori oppure scrivere NO per rinunciare alla rimozione del blocco");

            default:
                return new ChoiceResponseMessage(match.getBoard().clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }
}
