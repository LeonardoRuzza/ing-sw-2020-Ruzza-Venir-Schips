package it.polimi.ingsw.model;

public class PlayerHephaesthus extends Player {
    private boolean firstBuild=true;
    private int x0=-1, y0=-1;
    protected PlayerHephaesthus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    protected void resetTurn(){         //reset esplicito necessario nel caso venga eseguita una sola costruzione da Hephaesthus
        firstBuild=true;
        x0=-1;
        y0=-1;

    }

    @Override
    public boolean selectedWorkerBuild(int x, int y){
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
            if(x0 ==x && y0==y){                                 //verifico che vogli costruire sulla stessa cella precedente
                if(super.selectedWorkerBuild(x,y)){              //costruisco indipendentemente se è una cupola perchè in questo caso la checkBuild seguente ritorna false e allora applicherei la removeBlock
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
            }                   //nel caso salti il primo if ritorna poi false per farsi dare un'ltra casella adeguata
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
                if(tempResponse.getNextInstruction().equals("Ti sei mosso correttamente")){
                    return new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + "Scegli dove crostruire e nel caso tu voglia costruire due volte scrivi BUILDTWOTIMES dopo la casella");
                }
                return tempResponse;
            case 3:
                if(!optional.equals("BUILDTWOTIMES")){
                    tempResponse = super.manageStateBuild(x, y);
                    if(tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                        tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ " e il tuo turno è terminato");
                        match.nextPlayer();
                        return tempResponse;
                    }
                    return tempResponse;
                }
                tempResponse = super.manageStateBuild(x, y);
                if(!tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                    return tempResponse;
                }
                tempResponse = manageStateBuild(x, y);
                if(tempResponse.getNextInstruction().equals("Costruzione eseguita")){
                    tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ " e il tuo turno è terminato");
                }else{
                    tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction());
                }
                match.nextPlayer();
                return tempResponse;
            default: return new ChoiceResponseMessage(match.getBoard().clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }

    @Override
    protected ChoiceResponseMessage manageStateBuild(int x, int y){
        if(selectedWorkerBuild(x,y)) {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.getBoard().clone(), this, "Costruzione eseguita");
        }else {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.getBoard().clone(), this, "Non puoi costruire due volte qui. Fine del turno.");
        }
    }
}
