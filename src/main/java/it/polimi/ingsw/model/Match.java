package it.polimi.ingsw.model;

public class Match {

    private int ID;
    private int numberOfPlayers;
    private enum stateOfMatch{};
    private Player playingNow;
    private Player[] players;
    private Board board;


    public Match(int ID, int numberOfPlayers) {  // Da valutare quali parametri passare, per creazione partita
        this.ID = ID;
        this.numberOfPlayers = numberOfPlayers;
    }
    /*public Player checkWin() {

    }

    public Player checkLoser() {

    }

    protected Worker checkMove (int x, int y, Worker w) { //controlli regole generali validi per tutti

    }

    protected Block checkBuild (int x, int y, Worker w) {

    }


    private Player nextPlayer(){

    }

    protected boolean forceMove(int x,int y, Worker w){   //controlli influenzati da limitazioni avversarie
    }

    protected boolean forceBuild(int x, int y, Worker w){
    }
    */
}
