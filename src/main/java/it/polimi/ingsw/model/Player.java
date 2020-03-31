package it.polimi.ingsw.model;
import org.jetbrains.annotations.NotNull;

public class Player {

    protected String nickname;
    protected int number;
    protected Card card;
    protected int stateOfTurn;


    protected Match match;
    protected Worker[] workers = new Worker[2];
    protected Worker selectedWorker;

    protected Player(String nickname, int number, Card card, Match match, Worker.Color color){ //secondo costruttore che sarà utilizzato dalle sottoclassi per la loro medesima istanziazione.
        this.nickname = nickname;
        this.number = number;
        this.match = match;
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

    protected ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional) {
        switch(stateOfTurn){
            case 1:
                if(match.checkLoserMove(gender == Worker.Gender.Male ? workers[0] : workers[1])){                         //se non può muovere il worker selezionato
                    if(match.checkLoserMove(gender == Worker.Gender.Male ? workers[1] : workers[0])){
                        match.removeWorker(workers[0]);
                        match.removeWorker(workers[1]);
                        return new ChoiceResponseMessage(match.getBoard().clone(), this, "Hai perso");
                    }
                    stateOfTurn++;
                    setSelectedWorker(gender == Worker.Gender.Male ? workers[1].getGender() : workers[0].getGender());
                    return new ChoiceResponseMessage(match.getBoard().clone(), this, "Non era selezionabile il worker è stato scelto automaticamente l'altro");
                }
                setSelectedWorker(gender);
                stateOfTurn++;
                return new ChoiceResponseMessage(match.getBoard().clone(), this, "Hai selezionato bene");
            case 2:
                selectedWorkerMove(x, y);
                if(match.checkWin(selectedWorker)){
                    stateOfTurn = 1;
                    return new ChoiceResponseMessage(match.getBoard().clone(), this, "Hai vinto");
                }
                stateOfTurn++;
               return new ChoiceResponseMessage(match.getBoard().clone(), this, "Ti sei mosso correttamente");
            case 3:
                if(match.checkLoserBuild(selectedWorker)){
                    stateOfTurn = 1;
                    match.removeWorker(workers[0]);
                    match.removeWorker(workers[1]);
                    return new ChoiceResponseMessage(match.getBoard().clone(), this, "Hai perso,non puoi costruire");
                }
                selectedWorkerBuild(x,y);
                stateOfTurn++;
                return new ChoiceResponseMessage(match.getBoard().clone(), this, "Costruzione eseguita");
            default:
                match.nextPlayer();
                return new ChoiceResponseMessage(match.getBoard().clone(), this, "Fine del tuo turno");
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
