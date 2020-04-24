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

    /**
     * Return void, set internal elements to start the game and ask first player to allocate his first worker
     */
    public void initializeGame(){
        nextPlayer();
        notify(new ChoiceResponseMessage(this.clone(), this.playingNow.clone(), GameMessage.turnMessageFIRSTALLOCATION));
    }

    /**
     * Getter for board associated with this match
     * @return Board
     */
    public Board getBoard() { return board; }
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * Getter for the player which is playing now
     * @return Player
     */
    public Player getPlayingNow() { return playingNow; }

    /**
     * Getter for players associated with this match
     * @return Player[]
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Setter of players in match
     *
     * @param p Player to be added in match
     * @return Player
     */
    public void addPlayer(Player p){
        for(int x = 0; x<numberOfPlayers; x++){
            if(players[x] == null){
                players[x] = p;
                return;
            }
        }
    }

    /**
     * Returns true if the selected worker wins the match
     *
     * @param w Worker you want check if is winner
     * @return True if the Worker is winner; False otherwise
     * */
    public boolean checkWin(@NotNull Worker w) {
        if (w.getOldLocation() == null || w.getCell() == null){
            return checkSuperWin(false);
        }
        if (w.getCell().getzCoord() == 2 && !(w.getCell().equals(w.getOldLocation())) && w.getOldLocation().getzCoord() != 2){
            return checkLimitWin(true);
        }
        return checkSuperWin(false);
    }

    /**
     * Check if the player has a card that expand the win condition
     *
     * @param standardWin Says if worker wins with standard rules
     * @return True if the Worker is winner; False otherwise
     * */
    public boolean checkSuperWin(boolean standardWin){
        boolean tempWin = false;
        if(playingNow.card == null){
            return false;
        }
        tempWin = playingNow.checkSuperWin();
        return standardWin|| tempWin;
    }

    /**
     * Check if there are card in match that limit the win condition
     *
     * @param standardWin Say if worker wins with standard rules
     * @return True if the Worker is winner; False otherwise
     * */
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

    /**
     * Returns true if the selected worker can no longer move
     *
     * @param w Worker you want check if is loser
     * @return True if the Worker is loser; False otherwise
     *
     * */
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

    /**
     * Returns true if the selected worker can no longer build
     *
     * @param w Worker you want check if is loser
     * @return True if the Worker is loser; False otherwise
     *
     * */
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

    /**
     * Check player worker can move into a selected cell
     * @param x coordinate of the cell in which to move
     * @param y coordinate of the cell in which to move
     * @param w Worker you want move
     * @return The worker you gave as param if you can move; Worker which occupies the cell if the cell you want move into is busy; null otherwise
     */
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

    /**
     * Check player worker can build into a selected cell
     * @param x coordinate of the cell in which to build
     * @param y coordinate of the cell in which to build
     * @param w Worker you want build with
     * @return True if you can build; null otherwise
     */
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

    /**
     * Select the next turn player and set parameters of match.
     * @return Player selected as actual playing
     */
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

    /**
     * Perform a move of a worker into a selected cell, also checking cards move limit
     * @param x coordinate of the cell in which to move
     * @param y coordinate of the cell in which to move
     * @param w worker you want move
     * @return True if moved; False otherwise
     */
    protected boolean forceMove(int x,int y, Worker w){
        if (forceMoveLimit(board.getLastBusyCell(x, y))) {
            return board.forceMove(board.getLastBusyCell(x, y), w);
        }
        return false;
    }

    /**
     * Check if there are cards that limit move
     * @param nextCell cell you want move into
     * @return True if you can move; False otherwise
     */
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

    /**
     * Perform build into a selected cell
     * @param x coordinate of the cell in which to build
     * @param y coordinate of the cell in which to build
     * @param w worker you want build with
     * @return True if builded; False otherwise
     */
    protected boolean forceBuild(int x, int y, Worker w){
        return ((board.forceBuild(board.getFirstBuildableCell(x, y))) != null);
    }
    /**
     * Perform build of a dorse into a selected cell
     * @param x coordinate of the cell in which to build
     * @param y coordinate of the cell in which to build
     * @param w worker you want build with
     * @return True if builded; False otherwise
     */
    protected boolean forceBuildDorse(int x, int y, Worker w){
        return (board.forcebuildDorse(board.getFirstBuildableCell(x,y)));
    }
    /**
     * Collapse block into a selected cell
     * @param x coordinate of the cell in which collapse block
     * @param y coordinate of the cell in which collapse block
     */
    protected void removeBlock(int x, int y){
        board.removeBlock(x,y);
    }
    /**
     * Remove worker from board
     * @param w worker to be removed
     */
    protected void removeWorker(Worker w){
        board.removeWorker(w);
    }
    /**
     * Remove player from match
     * @param p player to be removed
     */
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

    /**
     * Counts the number of ended tower on the board
     * @return number of ended tower
     */
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

    /**
     * This method perform the first allocation of a worker by gender
     * @param x coordinate where you want to put the worker
     * @param y coordinate where you want to put the worker
     * @param gender of worker you want put into cell
     */
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
