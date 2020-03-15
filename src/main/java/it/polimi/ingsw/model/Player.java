package it.polimi.ingsw.model;

public class Player {

    private String nickname;
    private int number;
    private Card card;
    private Match match;
    private Worker[] workers;


    public Player(String nickname, int number) {
        this.nickname = nickname;
        this.number = number;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    private void selectDeck(){  // Chiamato dal costruttore del primo giocatore

    }

    private void selectCard(){

    }

    public void locateWorker(int x, int y, Worker w){

    }

    public void selectWorkerToMove(int x, int y, Worker w){

    }

    public void selectWorkerToBuild(int x, int y, Worker w){

    }
}
