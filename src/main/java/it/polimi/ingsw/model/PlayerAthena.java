package it.polimi.ingsw.model;

public class PlayerAthena extends Player {
    private int zOldAthena;
    private int zNowAthena;
    protected PlayerAthena(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean selectedWorkerMove(int x, int y){
        boolean returnValue;
        if(selectedWorker.getCell() == null){
            return super.selectedWorkerMove(x, y);
        }else{
            returnValue = super.selectedWorkerMove(x, y);
            zNowAthena = this.selectedWorker.getCell().getzCoord();
            if(this.selectedWorker.getCell().getBlock() != null){
                zNowAthena++;
            }
            zOldAthena = this.selectedWorker.getOldLocation().getzCoord();
            if(this.selectedWorker.getOldLocation().getBlock() != null){
                zOldAthena++;
            }
        }
        return returnValue;
    }

    @Override
    public boolean checkLimitMove(Cell nextCell, Player opponent){   //ritorna true se la mossa è fattibile sennò false se la limita
        if(selectedWorker==null){ return true; }
        if(this.selectedWorker.getOldLocation() == null || this.selectedWorker.getCell() == null){
            return true;
        }
        if(zNowAthena-zOldAthena>=1){
            if(opponent.selectedWorker.getCell() == null){
                return true;
            }
            int zOldOpponent=opponent.selectedWorker.getCell().getzCoord();
            if(opponent.selectedWorker.getCell().getBlock() != null){
                zOldOpponent++;
            }
            int zNewOpponent = nextCell.getzCoord();
            if(nextCell.getBlock() != null){
                zNewOpponent++;
            }
            if(zNewOpponent-zOldOpponent>=1){
                return false;
            }
        }
        return true;
    }
}
