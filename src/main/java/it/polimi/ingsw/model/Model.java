package it.polimi.ingsw.model;


import it.polimi.ingsw.observer.Observable;

public class Model extends Observable<ChoiceResponseMessage>{
    private Match match;

    public Match getMatch() {
        return match;
    }

    public boolean isPlayerTurn(Player player) {
        return match.getPlayingNow().equals(player);
    }

    public Board getBoardCopy(){
        return match.getBoard().clone();
    }

    public boolean firstAllocation(int x, int y, Worker.Gender gender){
        match.getPlayingNow().setSelectedWorker(gender);
        return match.getPlayingNow().selectedWorkerMove(x,y);
    }

    public void performPlay(int x, int y, Worker.Gender gender, String optional){
        ChoiceResponseMessage resp=match.getPlayingNow().manageTurn(x, y,gender, optional);
        //controllo vittoria,reset partita
        notify(resp);
    }

}
