package it.polimi.ingsw.model;
import org.jetbrains.annotations.NotNull;

public class Player {

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


    public static String turnMessageDORSE = "DORSE";
    public static String turnMessageBUILDTWOTIMES = "BUILDTWOTIMES";
    public static String turnMessageNO = "NO";
    public static String TurnMessageBUILDBEFORE = "BUILDBEFORE";
    public static String turnMessageLose = "You Lose. ";
    public static String turnMessageWin = "You Win. ";

    public static String turnMessageUnselectableWorkerSwitch = "Worker was not selectable, the other was chosen automatically. ";
    public static String turnMessageLoserNoWorker = "You lost, neither worker is selectable! ";
    public static String turnMessageOkWorkerSelection = "Worker successfully selected. ";
    public static String turnMessageChooseCellMove = "Choose where to move. ";
    public static String turnMessageOkMovement = "You moved correctly. ";
    public static String turnMessageFailMovementChangeDestination = "It is not possible to perform the required move, change box. ";
    public static String turnMessageChooseCellBuild = "Choose where to build. ";
    public static String turnMessageOkBuild = "Construction completed. ";
    public static String turnMessageFailBuildChangeDestination = "Construction cannot be performed, change box. ";
    public static String turnMessageTurnEnd = "Your turn is over. ";

    public static String aresTurnMessageAskRemoveBlok = "If you want to remove a block adjacent to the worker that you have not moved, write the coordinates otherwise write NO. ";
    public static String aresTurnMessageSuccessRemoveBlokWEnd = "The block was successfully removed. Your turn is over. ";
    public static String aresTurnMessageFailRemoveBlokWNewCell = "Unable to remove a block here, change position or cancel the option by writing NO. ";

    public static String atlasTurnMessageAskBuildDorse = "Choose where to build and if you want to build a dome add DORSE after the box. ";
    public static String atlasTurnMessageFailBuildDorse = "Impossible to build a dome here, change position or give up the DORSE option. ";

    public static String prometheusTurnMessageAskBuildBefore = "Choose where to move or if you want to build before moving, write BUILDBEFORE after the box where you want to build. ";

    public static String hephaesthusTurnMessageAskBuild = "Choose where to build and if you want to build twice write BUILDTWOTIMES after the box. ";
    public static String hephaesthusTurnMessageFailOptionalBuildWEnd = "You can't build twice here. Your turn is over. ";

    public static String hestiaDemeterTurnMessageAskTwoBuild = "If you want to build again, indicate the coordinates and write BUILDTWOTIMES otherwise write NO. ";
    public static String hestiaDemeterTurnMessageFailOptionalBuildWEnd = "You can't build here twice. Your turn is over. ";
    public static String hestiaDemeterTurnMessageFailOptionalBuildWNewCell = "You can't build here. Enter new coordinates or write NO. ";

    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional) {
        switch(stateOfTurn){
            case 1:
                return manageStateSelection(x, y, gender);
            case 2:
                return manageStateMove(x, y);
            case 3:
                ChoiceResponseMessage tempResponse = manageStateBuild(x, y);
                    if(tempResponse.getNextInstruction().equals(turnMessageOkBuild)){
                        tempResponse = new ChoiceResponseMessage(tempResponse.getBoard(), tempResponse.getPlayer(), tempResponse.getNextInstruction()+ turnMessageTurnEnd);
                        match.nextPlayer();
                        return tempResponse;
                    }
                    return tempResponse;
            default: return new ChoiceResponseMessage(match.getBoard().clone(), this, "Errore nello stato del turno!"); //da valutare questo default
        }
    }

    protected ChoiceResponseMessage manageStateSelection( int x, int y, Worker.Gender gender){ //i vari if else servono perchè nelle setSelectedWorker vengono verificati altri limiti rispetto alla checkLoserMove
        if(match.checkLoserMove(gender == Worker.Gender.Male ? workers[0] : workers[1])){                         //se non può muovere il worker selezionato
            if(match.checkLoserMove(gender == Worker.Gender.Male ? workers[1] : workers[0])){
                match.removeWorker(workers[0]);
                match.removeWorker(workers[1]);
                return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageLose);
            }
            if(setSelectedWorker(gender == Worker.Gender.Male ? workers[1].getGender() : workers[0].getGender())){
                stateOfTurn++;
                return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageUnselectableWorkerSwitch);
            }
            else
                return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageLoserNoWorker);
        }
        else {
            if(setSelectedWorker(gender)){
                stateOfTurn++;
                return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageOkWorkerSelection);
            }
            else {
                if(setSelectedWorker(gender == Worker.Gender.Male ? workers[1].getGender() : workers[0].getGender())){
                    stateOfTurn++;
                    return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageUnselectableWorkerSwitch);
                }
                else
                    return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageLoserNoWorker);
            }
        }
    }


    protected ChoiceResponseMessage manageStateMove(int x, int y){
        if(selectedWorkerMove(x, y)) {
            if (match.checkWin(selectedWorker)) {
                stateOfTurn = 1;
                return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageWin);
            }
            stateOfTurn++;
            return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageOkMovement);
        }
        else {
            return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageFailMovementChangeDestination);
        }
    }


    protected ChoiceResponseMessage manageStateBuild(int x, int y){
        if(match.checkLoserBuild(selectedWorker)){
            stateOfTurn = 1;
            match.removeWorker(workers[0]);
            match.removeWorker(workers[1]);
            return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageLose);
        }
        if(selectedWorkerBuild(x,y)) {
            stateOfTurn = 1;
            return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageOkBuild);
        }
        else {
            return new ChoiceResponseMessage(match.getBoard().clone(), this, turnMessageFailBuildChangeDestination);
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
