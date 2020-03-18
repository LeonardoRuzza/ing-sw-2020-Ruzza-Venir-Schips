package it.polimi.ingsw.model;

public class Player {

    private String nickname;
    private int number;
    private Card card;


    private Match match;
    private Worker[] workers = new Worker[2];
    private Worker selectedWorker;

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
    protected void setSelectedWorker(Worker worker){ selectedWorker=worker;}

    //per la CLI
    protected void setSelectedWorker(Worker.Gender selectedGender){
        switch (selectedGender){
            case Male:
                selectedWorker=workers[0];
                break;
            case Female:
                selectedWorker=workers[1];
                break;
            default:
                break;
        }
    }

//Other Methods
    private void selectDeck(){  // Chiamato dal costruttore del primo giocatore

    }

    private void selectCard(){

    }

    public void locateWorker(int x, int y, Worker w){

    }

    public void SelectedWorkerMove(int x, int y, Worker w){

    }

    public void SelectedWorkerBuild(int x, int y, Worker w){

    }
}
