package it.polimi.ingsw.model;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.GameMessage;
import org.jetbrains.annotations.NotNull;

public class Match extends Observable<ChoiceResponseMessage> implements Cloneable {

    final int maxPlayer = 3;
    private int ID;
    private int numberOfPlayers;

    //private enum stateOfMatch{};
    private Player playingNow;
    protected Player[] players;
    private Board board;

    public Match(int ID, int numberOfPlayers) {  // Da valutare quali parametri passare, per creazione partita
        this.ID = ID;
        this.numberOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
        this.board = new Board();
    }

    public void initializeGame(){
        nextPlayer();
        notify(new ChoiceResponseMessage(this.clone(), this.playingNow.clone(), GameMessage.turnMessageFIRSTALLOCATION));
    }

    public Board getBoard() { return board; }
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
    public Player getPlayingNow() { return playingNow; }
    public Player[] getPlayers() {
        return players;
    }

    public void addPlayer(Player p){
        for(int x = 0; x<numberOfPlayers; x++){
            if(players[x] == null){
                players[x] = p;
                return;
            }
        }
    }

    public boolean checkWin(@NotNull Worker w) {
        if (w.getOldLocation() == null || w.getCell() == null){
            return checkSuperWin(false);
        }
        if (w.getCell().getzCoord() == 2 && !(w.getCell().equals(w.getOldLocation())) && w.getOldLocation().getzCoord() != 2){
            return checkLimitWin(true);
        }
        return checkSuperWin(false);
    }

    public boolean checkSuperWin(boolean standardWin){
        boolean tempWin = false;
        if(playingNow.card == null){
            return false;
        }
        tempWin = playingNow.checkSuperWin();
        return standardWin|| tempWin;
    }

    public boolean checkLimitWin(boolean standardWin){
        boolean tempWin = standardWin;
        for(int x = 0; x < numberOfPlayers; x++) {
            Player p = players[x];
            if(p.card == null){
                continue;
            }
            if (p.card.getActivationPeriod() == Card.activationPeriod.LIMITWINCOND) {
                tempWin = p.checkLimitWin(playingNow);
            }
        }
        return standardWin && tempWin;
    }

    //va fatta a inizio turno
    public boolean checkLoserMove(@NotNull Worker w) {
        for(int x = w.getCell().getxCoord()-1; x < w.getCell().getxCoord()+2; x++){
            if(x > 4 || x < 0){
                continue;
            }
            for(int y = w.getCell().getyCoord()-1; y < w.getCell().getyCoord()+2; y++){
                if(y > 4 || y < 0){
                    continue;
                }
                if(w.getCell().getxCoord() == x && w.getCell().getyCoord() == y){
                    continue;
                }
                if(board.getLastBusyCell(x, y).getBlock() != Block.DORSE){
                    if(board.getDistance(board.getFirstBuildableCell(x, y),board.getFirstBuildableCell(w.getCell().getxCoord(),w.getCell().getyCoord()))[2] < 2){
                        if((board.getLastBusyCell(x, y).getWorker()) == null){
                            if(!forceMoveLimit(board.getLastBusyCell(x, y))){
                                continue;
                            }
                            return false;
                        }
                        else{
                            if(playingNow.card.getActivationPeriod() == Card.activationPeriod.YOURMOVE){
                                Player playingNowCopy = playingNow.clone();
                                playingNowCopy.match = this.clone();
                                if(w.getGender() == Worker.Gender.Male){
                                    playingNowCopy.workers[0] = playingNowCopy.match.checkMove(w.getCell().getxCoord(),w.getCell().getyCoord(),playingNowCopy.workers[0]);
                                    playingNowCopy.selectedWorker = playingNowCopy.workers[0];
                                }
                                else {
                                    playingNowCopy.workers[1]= playingNowCopy.match.checkMove(w.getCell().getxCoord(),w.getCell().getyCoord(),playingNowCopy.workers[1]);
                                    playingNowCopy.selectedWorker = playingNowCopy.workers[1];
                                }
                                if(playingNowCopy.selectedWorkerMove(x, y)){
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    //va fatta sul turno dopo lo spostamento
    public boolean checkLoserBuild(@NotNull Worker w) {
        for(int x = w.getCell().getxCoord()-1; x < w.getCell().getxCoord()+2; x++){
            if(x > 4 || x < 0){
                continue;
            }
            for(int y = w.getCell().getyCoord()-1; y < w.getCell().getyCoord()+2; y++){
                if(y > 4 || y < 0){
                    continue;
                }
                if(w.getCell().getxCoord() == x && w.getCell().getyCoord() == y){
                    continue;
                }
                if(board.getLastBusyCell(x, y).getBlock() != Block.DORSE){
                    if((board.getLastBusyCell(x, y).getWorker()) == null){ // da modificare per carta speciale
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //\result == null ==> error
    //\result == playerWorker ==> done
    //\result == foeWorker ==> do something
    protected Worker checkMove (int x, int y, Worker w) {
        if(x < 0 || x > 4 || y < 0 || y > 4){
            return null;
        }
        Cell moveToCell = board.getLastBusyCell(x,y);
        if(moveToCell.getWorker() != null && !moveToCell.getWorker().equals(w)){
            return moveToCell.getWorker();
        }else if (moveToCell.getBlock() == Block.DORSE){
            return null;
        }
        if(w.getCell() != null){
            if(w.getCell().equals(moveToCell)){
                return null;
            }
            moveToCell = board.getFirstBuildableCell(x,y);
            int[] distance = board.getDistance(moveToCell, board.getFirstBuildableCell(w.getCell().getxCoord(),w.getCell().getyCoord()));
            if(distance[0] > 1 || distance[1] > 1 || distance[2] > 1){
                return null;
            }
        }
        return w;
    }

    protected boolean checkBuild (int x, int y, @NotNull Worker w) {
        if(x < 0 || x > 4 || y < 0 || y > 4){
            return false;
        }
        Cell buildToCell = board.getLastBusyCell(x, y);
        if(w.getCell().equals(buildToCell)){
            return false;
        }
        if(buildToCell.getWorker() != null){
            return false;
        }else if (buildToCell.getBlock() == Block.DORSE){
            return false;
        }
        int[] distance = board.getDistance(buildToCell, w.getCell());
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
        board.setPlayingNow(playingNow);
        return playingNow;
    }

    //imposto da vincoli esterni in caso di esito positivo x e y saranno gli stessi di checkmove e checkbuild
    protected boolean forceMove(int x,int y, Worker w){
        if (forceMoveLimit(board.getLastBusyCell(x, y))) {
            return board.forceMove(board.getLastBusyCell(x, y), w);
        }
        return false;
    }

    protected  boolean forceMoveLimit(Cell nextCell){
        boolean returnLimit = true;
        for(int x = 0; x < numberOfPlayers; x++) {
            Player p = players[x];
            if (p.card == null) {
                continue;
            }
            if (p.card.getActivationPeriod() == Card.activationPeriod.FOETURN && p != playingNow) {
                returnLimit = returnLimit && p.checkLimitMove(nextCell, playingNow);
            }
        }
        return returnLimit;
    }

    protected boolean forceBuild(int x, int y, Worker w){
        return ((board.forceBuild(board.getFirstBuildableCell(x, y))) != null);
    }

    protected boolean forceBuildDorse(int x, int y, Worker w){
        return (board.forcebuildDorse(board.getFirstBuildableCell(x,y)));
    }

    protected void removeBlock(int x, int y){
        board.removeBlock(x,y);
    }
    protected void removeWorker(Worker w){
        board.removeWorker(w);
    }
    protected void removePlayer(Player p){
        if(numberOfPlayers == 3){
            Player[] temp = new Player[numberOfPlayers-1];
            nextPlayer();
            int i = 0;
            for (int x = 0; x < numberOfPlayers; x++){
                if(players[x] != null){
                    if(players[x].equals(p)){
                        players[x] = null;
                    }else{
                        temp[i] = players[x];
                        i++;
                    }
                }
            }
            numberOfPlayers = 2;
            this.players = temp;
            return;
        }else{
            Player[] temp = new Player[numberOfPlayers-1];
            nextPlayer();
            for (int x = 0; x < numberOfPlayers; x++){
                if(players[x] != null){
                    if(players[x].equals(p)){
                        players[x] = null;
                    }else{
                        temp[0] = players[x];
                    }
                }
            }
            numberOfPlayers = 1;
            this.players = temp;
            return;
        }
    }

    protected int towerCount(){
        int counter = 0;
        for(int x = 0; x < board.boardSide; x++){
            for(int y = 0; y < board.boardSide; y++){
                if (board.getLastBusyCell(x, y).getzCoord() == 3 && board.getLastBusyCell(x,y).getBlock() == Block.DORSE){
                    counter++;
                }
            }
        }
        return counter;
    }

    public void execFirstAllocation(int x, int y, Worker.Gender gender){
        this.playingNow.setSelectedWorker(gender);
        if(this.playingNow.selectedWorker.getCell() != null){
            notify(new ChoiceResponseMessage(this.clone(),playingNow.clone(), GameMessage.turnMessageErrorFIRSTALLOCATION));
            return;
        }
        if(!firstAllocation(x,y,gender)){
            notify(new ChoiceResponseMessage(this.clone(),playingNow.clone(), GameMessage.turnMessageErrorFIRSTALLOCATION));
            return;
        }
        this.playingNow.stateOfTurn++;
        if(this.playingNow.stateOfTurn == 3 && this.playingNow.getNickname().equals(players[0].getNickname())){
            this.playingNow.stateOfTurn = 1;
            this.nextPlayer();
            notify(new ChoiceResponseMessage(this.clone(),playingNow.clone(), GameMessage.turnMessageFIRSTALLOCATION));
            return;
        }else if (this.playingNow.stateOfTurn == 3 && this.playingNow.getNickname().equals(players[1].getNickname()) && numberOfPlayers == 2){
            this.playingNow.stateOfTurn = 1;
            this.nextPlayer();
            notify(new ChoiceResponseMessage(this.clone(),playingNow.clone(), GameMessage.turnMessageFIRSTALLOCATIONEnded));
            return;
        }else if (this.playingNow.stateOfTurn == 3 && this.playingNow.getNickname().equals(players[1].getNickname()) && numberOfPlayers == 3){
            this.playingNow.stateOfTurn = 1;
            this.nextPlayer();
            notify(new ChoiceResponseMessage(this.clone(),playingNow.clone(), GameMessage.turnMessageFIRSTALLOCATION));
            return;
        }else if (this.playingNow.stateOfTurn == 3 && this.playingNow.getNickname().equals(players[2].getNickname()) && numberOfPlayers == 3){
            this.playingNow.stateOfTurn = 1;
            this.nextPlayer();
            notify(new ChoiceResponseMessage(this.clone(),playingNow.clone(), GameMessage.turnMessageFIRSTALLOCATIONEnded));
            return;
        }
        notify(new ChoiceResponseMessage(this.clone(),playingNow.clone(), GameMessage.turnMessageFIRSTALLOCATION));
        return;
    }

    public boolean firstAllocation(int x, int y, Worker.Gender gender){
       return playingNow.selectedWorkerMove(x,y);
    }

    public void performPlay(int x, int y, Worker.Gender gender, String optional){
        ChoiceResponseMessage resp = playingNow.manageTurn(x, y,gender, optional);
        //controllo vittoria,reset partita
        notify(resp);
    }

    @Override
    protected Match clone() {
        final Match result = new Match(this.ID, this.numberOfPlayers);
        result.playingNow = this.playingNow.clone();
        for (int x = 0; x < numberOfPlayers; x++){
            if(this.players[x] != null){
                result.players[x] = this.players[x].clone();
            }
        }
        result.board = this.getBoard().clone();
        return result;
    }

}
