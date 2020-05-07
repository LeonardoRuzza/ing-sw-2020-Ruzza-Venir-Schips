package it.polimi.ingsw.model;

public class PlayerAthena extends Player {
    private int zOldAthena;
    private int zNowAthena;
    protected PlayerAthena(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**This method allow to implement the power of Athena (with checkLimitMove) saving actual and the previous z coordinates of the selectedWorker of the Player.
     *<p>
     * @param x first coordinate
     * @param y second coordinate
     * @return {@code true} if the movement was possible and performed; {@code false} otherwise
     */
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

    /**This method check if the movement specified by the Player (who is an opponent of the Player with Athena's power) is possible or not implementing the rule imposed by Athena power.
     * <p>
     * @param nextCell where the opponent's selectedWorker want to move
     * @param opponent the opponent Player who is playing actually
     * @return {@code true} if the opponent's movement is possible; {@code false} otherwise
     */
    @Override
    public boolean checkLimitMove(Cell nextCell, Player opponent, Worker opponentWorker){
        if(this.selectedWorker==null || opponentWorker ==null){ return true; }
        if(this.selectedWorker.getOldLocation() == null || this.selectedWorker.getCell() == null){
            return true;
        }
        if(zNowAthena-zOldAthena>=1){
            if(opponentWorker.getCell() == null){
                return true;
            }
            int zOldOpponent= opponentWorker.getCell().getzCoord();
            if(opponentWorker.getCell().getBlock() != null){
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
