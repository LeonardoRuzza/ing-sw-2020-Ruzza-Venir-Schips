package it.polimi.ingsw.model;
import it.polimi.ingsw.utils.GameMessage;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


public class Player implements Serializable {

    protected String nickname;
    protected int number;
    protected Card card;
    protected int stateOfTurn = 1;

    protected Match match;
    protected Worker[] workers = new Worker[2];
    protected Worker selectedWorker;

    protected Player(String nickname, int number, Match match){
        this.nickname = nickname;
        this.number = number;
        this.match = match;
    }

    protected Player(String nickname, int number, Card card, Match match, Worker.Color color){ //secondo costruttore che sarà utilizzato dalle sottoclassi per la loro medesima istanziazione.
        this(nickname, number, match);
        this.card = card;
        workers[0] = new Worker(Worker.Gender.Male, color);
        workers[1] = new Worker(Worker.Gender.Female, color);
    }

//Getter and Setter
    public String getNickname() {
        return nickname;
    }

    protected void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNumber() {
        return number;
    }

    protected void setNumber(int number) {
        this.number = number;
    }

    //per la GUI
    protected boolean setSelectedWorker(Worker worker){
        if(checkAllLimitSelection(worker)) {
            selectedWorker = worker;
        }
        return selectedWorker!= null;
    }

    //per la CLI
    protected boolean setSelectedWorker(@NotNull Worker.Gender selectedGender){
        Worker tempWorker;
        switch (selectedGender){
            case Male:
                tempWorker=workers[0];
                break;
            case Female:
                tempWorker=workers[1];
                break;
            default:
                tempWorker=null;
                break;
        }
        if(checkAllLimitSelection(tempWorker)){
            selectedWorker = tempWorker;
        }
        else
            selectedWorker = null;
        return selectedWorker != null;
    }

    public boolean selectedWorkerMove(int x, int y){           //Memo: serve anche per allocare inizialmente i worker a inizio partita
        Worker tempWorker;
        tempWorker=match.checkMove(x,y,selectedWorker);
        if (tempWorker == null) return false;
        if(tempWorker.equals(selectedWorker)){
            if(match.forceMove(x,y,selectedWorker)){
                return true;
            }
        }
        return false;
    }

    public boolean selectedWorkerBuild(int x, int y){
        if(match.checkBuild(x,y,selectedWorker)){
            if(match.forceBuild(x,y,selectedWorker)){
                return true;
            }
        }
        return false;
    }

    private boolean checkAllLimitSelection(Worker worker){
        boolean selectionOk=true;
        for(int x = 0; x < match.getNumberOfPlayers(); x++) {
            Player p = match.players[x];
            if (p.card == null) {
                continue;
            }
            if (p.card.getActivationPeriod() == Card.activationPeriod.STARTFOETURN) {
                selectionOk= selectionOk && p.checkLimitSelection(this, worker);
            }
        }
        return selectionOk;
    }

    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional) {
        switch(stateOfTurn){
            case 1:
                return manageStateSelection(x, y, gender);
            case 2:
                return manageStateMove(x, y);
            case 3:
                ChoiceResponseMessage tempResponse = manageStateBuild(x, y);
                    if(tempResponse.getNextInstruction().equals(GameMessage.turnMessageOkBuild)){
                        match.nextPlayer();
                        tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ GameMessage.turnMessageTurnEnd);
                        return tempResponse;
                    }
                    return tempResponse;
            default: return new ChoiceResponseMessage(match.clone(), this.clone(), "Errore nello stato del turno!"); //da valutare questo default
        }
    }

    protected ChoiceResponseMessage manageStateSelection( int x, int y, Worker.Gender gender){ //i vari if else servono perchè nelle setSelectedWorker vengono verificati altri limiti rispetto alla checkLoserMove
        if(match.checkLoserMove(gender == Worker.Gender.Male ? workers[0] : workers[1])){                         //se non può muovere il worker selezionato
            if(match.checkLoserMove(gender == Worker.Gender.Male ? workers[1] : workers[0])){
                match.removeWorker(workers[0]);
                match.removeWorker(workers[1]);
                match.removePlayer(this);
                return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageLose);
            }
            if(setSelectedWorker(gender == Worker.Gender.Male ? workers[1].getGender() : workers[0].getGender())){
                stateOfTurn++;
                return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageUnselectableWorkerSwitch);
            }
            else
                return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageLoserNoWorker);
        }
        else {
            if(setSelectedWorker(gender)){
                stateOfTurn++;
                return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageOkWorkerSelection);
            }
            else {
                if(setSelectedWorker(gender == Worker.Gender.Male ? workers[1].getGender() : workers[0].getGender())){
                    stateOfTurn++;
                    return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageUnselectableWorkerSwitch);
                }
                else{
                    match.removeWorker(workers[0]);
                    match.removeWorker(workers[1]);
                    match.removePlayer(this);
                    return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageLoserNoWorker);
                }
            }
        }
    }


    protected ChoiceResponseMessage manageStateMove(int x, int y){
        if(selectedWorkerMove(x, y)) {
            if (match.checkWin(selectedWorker)) {
                stateOfTurn = 1;
                return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageWin);
            }
            stateOfTurn++;
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageOkMovement);
        }
        else {
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageFailMovementChangeDestination);
        }
    }


    protected ChoiceResponseMessage manageStateBuild(int x, int y){
        if(match.checkLoserBuild(selectedWorker)){
            stateOfTurn = 1;
            match.removeWorker(workers[0]);
            match.removeWorker(workers[1]);
            match.removePlayer(this);
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageLose);
        }
        if(selectedWorkerBuild(x,y)) {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageOkBuild);
        }
        else {
            return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageFailBuildChangeDestination);
        }
    }


    public boolean selectedWorkerBuildDorse(int x, int y){
        return false;
    }
    public boolean checkLimitWin(Player opponent){
        return true;
    }
    public boolean checkSuperWin(){
        return false;
    }
    public boolean checkLimitMove(Cell nextCell, Player opponent){
        return true;
    }
    protected void resetTurn(){}
    public boolean checkLimitSelection(Player actualPlayer, Worker w){return true;}
    protected boolean notSelectedWorkerRemoveBlock(int x, int y){return false;}

    @Override
    protected Player clone() {
        final Player result;
        if (this.card == null || this.workers[0] == null || this.workers[1] == null){
            result = new Player(this.getNickname(), this.getNumber(), null);
        }else{
            result = new Player(this.getNickname(), this.getNumber(), new Card(this.card.getNumber()), null, this.workers[0].getColor());
            result.workers[0] = this.workers[0].clone();
            result.workers[1] = this.workers[1].clone();
        }
        result.stateOfTurn = this.stateOfTurn;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return this == p;
        }
        else
            return false;
    }
}
