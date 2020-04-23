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

    /**This method set the selectedWorker of the player if it is not limited by some opponent's power.
     *<p>
     * @param selectedGender the gender of the worker which the player want to select
     * @return {@code true} if the selection was possible and performed; {@code false} otherwise
     */
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

    /**If the move is possible checking only basic rules of movement with match.checkMove, this method try  to perform it with match.forceMove (which consider also others limitation caused by ,for example, opponent's power). This method is also used to allocate a worker for the first time.
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if the movement was possible and performed; {@code false} otherwise
     */
    public boolean selectedWorkerMove(int x, int y){
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

    /**If it is possible to build checking only basic rules of building with match.checkBuild, this method try  to perform it with match.forceBuild (which consider also others limitation caused by ,for example, opponent's power).
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if was possible to build and performed; {@code false} otherwise
     */
    public boolean selectedWorkerBuild(int x, int y){
        if(match.checkBuild(x,y,selectedWorker)){
            if(match.forceBuild(x,y,selectedWorker)){
                return true;
            }
        }
        return false;
    }

    /**This method invoke the method checkLimitSelection for all player who can limit the selection of others player (which has a card "STARTFOETURN") to check if is possible for the current player select his worker.
     * <p>
     * @param worker this is who the method must check if is possible to select
     * @return {@code true} if is possible to select the worker; {@code false} otherwise
     */
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

    /**This method manage the state of the turn of a player (not of all the match). Allow to do the right checking and operations receiving parameter and choice of the player. It operates like an FSM. The 3 basic operations to do in order in all the turn are: manageStateSelection, manageStateMove, manageStateBuild.
     * <p>
     * @param x first coordinate, when its value is relevant
     * @param y second coordinate, when its value is relevant
     * @param gender of the worker to select, when its value is needed
     * @param optional a particular choice of the player, when its value is needed
     * @return ChoiceResponseMessage the message to notify to RemoteView
     */
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
                    tempResponse = new ChoiceResponseMessage(tempResponse.getMatch(), tempResponse.getPlayer(), tempResponse.getNextInstruction() + GameMessage.turnMessageChooseCellBuild);
                    return tempResponse;
                }
                return tempResponse;
            case 3:
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

    /**Manage the operation of selection of the worker of the player in its turn. Check losing condition (checkLoserMove of Match) and if possible select the worker of the specified gender, otherwise try with the other worker (setSelectedWorker).
     *<p>
     * @param gender the gender of the worker to select
     * @return ChoiceResponseMessage the message to return to manageTurn and (modified or not) then to RemoteView. Specify the result of the tried operation.
     */
    protected ChoiceResponseMessage manageStateSelection(Worker.Gender gender){ //i vari if else servono perchè nelle setSelectedWorker vengono verificati altri limiti rispetto alla checkLoserMove
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
                if(!match.checkLoserMove(gender == Worker.Gender.Male ? workers[1] : workers[0]) && setSelectedWorker(gender == Worker.Gender.Male ? workers[1].getGender() : workers[0].getGender())){ //aggiunta prima condizione per verificare possa spostarsi una volta selezionato
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

    /**Manage the state of the turn of the movement of the selectedWorker. Check also winning conditions after movement if it was correctly executed (match.checkWin).
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return ChoiceResponseMessage to return to manageTurn and (modified or not) then to RemoteView. Specify the result of the tried movement.
     */
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

    /**Manage the state of the turn of build  of the selectedWorker. Check losing conditions (match.checkLoserBuild) before try to build with selectedWorkerBuild.
     * <p>
     * @param x first coordinate
     * @param y second coordinate
     * @return ChoiceResponseMessage to return to manageTurn and (modified or not) then to RemoteView. Specify the result of the tried build.
     */
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
    public Player clone() {
        final Player result;
        if (this.card == null || this.workers[0] == null || this.workers[1] == null){
            result = new Player(this.getNickname(), this.getNumber(), null);
        }else{
            result = FactoryPlayer.getPlayer(this.getNickname(), this.getNumber(), null, this.workers[0].getColor(), this.card.getNumber());
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
