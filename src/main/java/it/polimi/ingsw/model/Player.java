package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

public class Player {

    protected String nickname;
    protected int number;
    protected Card card;
    protected static Card[] deck;


    protected Match match;
    protected Worker[] workers = new Worker[2];
    protected Worker selectedWorker;

//Builder
    public Player(String nickname, int number, Match match) {
        this.nickname = nickname;
        this.number = number;
        this.match = match;
    }

    public Player(String nickname, int number, Card card, Match match, Worker.Color color){ //secondo costruttore che sarà utilizzato dalle sottoclassi per la loro medesima istanziazione.
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
    protected boolean setSelectedWorker(Worker worker){ selectedWorker=worker; return selectedWorker!= null;}

    //per la CLI
    protected boolean setSelectedWorker(@NotNull Worker.Gender selectedGender){
        switch (selectedGender){
            case Male:
                selectedWorker=workers[0];
                break;
            case Female:
                selectedWorker=workers[1];
                break;
            default:
                selectedWorker=null;
                break;
        }
        return selectedWorker != null;
    }

//Other Methods
    private void addToDeck(int[] numbersOfCards){  // Chiamato quando tutti i giocatori hanno loggato la partita e sono stati creati. Prima dell'inizio della partita vera e propria in pratica
        deck = new Card[match.getNumberOfPlayers()];
        for(int i=0; i< match.getNumberOfPlayers(); i++){
            deck[i] = new Card(numbersOfCards[i]);
        }
    }


    public boolean selectedWorkerMove(int x, int y){           //Memo: serve anche per allocare inizialmente i worker a inizio partita
        Worker tempWorker;
        tempWorker=match.checkMove(x,y,selectedWorker);
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
}
