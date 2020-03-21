package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

public class Match {

    final int maxPlayer = 3;
    private int ID;
    private int numberOfPlayers;
    private enum stateOfMatch{};
    private Player playingNow;
    protected Player[] players;
    private Board board;

    public Match(int ID, int numberOfPlayers) {  // Da valutare quali parametri passare, per creazione partita
        this.ID = ID;
        this.numberOfPlayers = numberOfPlayers;
        this.board = new Board();
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }


    public boolean checkWin(@NotNull Worker w) {
        if (w.getOldLocation() == null || w.getCell() == null){
            return checkSuperWin(playingNow);
        }
        if (w.getCell().getzCoord() == 3 && !(w.getCell().equals(w.getOldLocation())) && w.getOldLocation().getzCoord() != 3){
            return checkSuperWin(playingNow);
        }
        return checkSuperWin(playingNow);
    }

    public boolean checkSuperWin(Player p){
        return false;
    }

    public boolean checkLimitWin(Player p){
        return false;
    }

    //va fatta a inizio turno
    public Player checkLoserMove(@NotNull Worker w) {
        for(int x = w.getCell().getxCoord()-1; x < w.getCell().getxCoord()+2; x++){
            if(x > 4 || x < 0){
                continue;
            }
            for(int y = w.getCell().getyCoord()-1; y < w.getCell().getyCoord()+2; y++){
                if(y > 4 || y < 0 || x == y){
                    continue;
                }
                if(board.getLastBusyCell(x, y).getBlock() != Block.DORSE){
                    if(board.getDistance(w.getCell(), board.getLastBusyCell(x, y))[2] < 2){
                        if((board.getLastBusyCell(x, y).getWorker()) == null){ // da modificare per carta speciale
                            return null;
                        }
                    }
                }
            }
        }
        return playingNow;
    }

    //va fatta sul turno dopo lo spostamento
    public Player checkLoserBuild(@NotNull Worker w) {
        for(int x = w.getCell().getxCoord()-1; x < w.getCell().getxCoord()+2; x++){
            if(x > 4 || x < 0){
                continue;
            }
            for(int y = w.getCell().getyCoord()-1; y < w.getCell().getyCoord()+2; y++){
                if(y > 4 || y < 0 || x == y){
                    continue;
                }
                if(board.getLastBusyCell(x, y).getBlock() != Block.DORSE){
                    if((board.getLastBusyCell(x, y).getWorker()) == null){ // da modificare per carta speciale
                        return null;
                    }
                }
            }
        }
        return playingNow;
    }

    //\result == null ==> error
    //\result == playerWorker ==> done
    //\result == foeWorker ==> do something
    protected Worker checkMove (int x, int y, Worker w) { //controlli regole generali validi per
        Cell moveToCell = board.getLastBusyCell(x,y);
        if(moveToCell.getWorker() != null){
            return moveToCell.getWorker();
        }else if (moveToCell.getBlock() == Block.DORSE){
            return null;
        }
        if(w.getCell() != null){
            if(w.getCell().equals(moveToCell)){
                return null;
            }
            int[] distance = board.getDistance(w.getCell(), moveToCell);
            if(distance[0] > 1 || distance[1] > 1 || distance[2] > 1){
                return null;
            }
        }
        return w;
    }

    protected boolean checkBuild (int x, int y, @NotNull Worker w) {//ricordati che nel caso di costruzione andari una cella sopra a getlastbusycell
        Cell buildToCell = board.getLastBusyCell(x, y);
        if(w.getCell().equals(buildToCell)){
            return false;
        }
        if(buildToCell.getWorker() != null){
            return false;
        }else if (buildToCell.getBlock() == Block.DORSE){
            return false;
        }
        int[] distance = board.getDistance(w.getCell(), buildToCell);
        //cambiare zeta
        return distance[0] <= 1 && distance[1] <= 1;
    }

    protected Player nextPlayer(){
        if(players[numberOfPlayers-1].equals(playingNow) || playingNow == null){ // caso in cui il giocatore è l'ultimo caso in cui si inizia la partita
            playingNow = players[0];
        }else{
            for (int x = 0; x < numberOfPlayers; x++){
            if (players[x].equals(playingNow)) {
                    playingNow = players[x+1];
                    break;
                }
            }
        }
        return playingNow;
    }

    //imposto da vincoli esterni in caso di esito positivo x e y saranno gli stessi di checkmove e checkbuild
    protected boolean forceMove(int x,int y, Worker w){
        return board.forceMove(board.getLastBusyCell(x, y), w);
    }

    protected boolean forceBuild(int x, int y, Worker w){
        return ((board.forceBuild(board.getFirstBuildableCell(x, y))) != null);
    }

    protected boolean removeBlock(int x, int y){
        return false;
    }
    protected boolean removeWorker(Worker w){
        return false;
    }
}
