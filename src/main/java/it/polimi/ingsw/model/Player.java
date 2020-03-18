package it.polimi.ingsw.model;

public class Player {

    private String nickname;
    private int number;
    private Card card;

    private Match match;
    private Worker[] workers;
    private Worker selectedWorker;


    public Player(String nickname, int number) {
        this.nickname = nickname;
        this.number = number;
    }


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
