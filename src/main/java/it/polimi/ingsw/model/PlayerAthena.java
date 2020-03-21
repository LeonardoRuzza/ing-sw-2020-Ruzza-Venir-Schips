package it.polimi.ingsw.model;

public class PlayerAthena extends Player {

    public PlayerAthena(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    protected boolean checkLimitMove(Cell nextCell, Player opponent){   //ritorna true se la mossa è fattibile sennò false se la limita
        if(selectedWorker==null){ return true; }
        int zOldAthena=this.selectedWorker.getOldLocation().getzCoord();  //il selectedWorker è unico per ogni player perciò non essendo sovrascritto rappresenta sicuramente quello del turno precedente
        int zNowAthena=this.selectedWorker.getCell().getzCoord();
        if(zNowAthena-zOldAthena>=1){
            int zOldOpponent=opponent.selectedWorker.getCell().getzCoord();
            if(nextCell.getzCoord()-zOldOpponent>=1){
                return false;
            }
        }
        return true;
    }
}
