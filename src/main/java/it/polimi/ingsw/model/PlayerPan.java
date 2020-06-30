package it.polimi.ingsw.model;

public class PlayerPan extends Player {

    protected PlayerPan(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**Implement the power of Pan which expect that the Player with this power can win also if his worker moves down two or more levels.
     * <p>
     * @return {@code true} in case of win; {@code false} otherwise
     */
    @Override
    protected boolean checkSuperWin(){
        int zOld,zFinal,groundFinal=-1;
        Block tempB;
        zOld=selectedWorker.getOldLocation().getzCoord();
        zFinal=selectedWorker.getCell().getzCoord();
        tempB=selectedWorker.getCell().getBlock();
        if(tempB == Block.B1){groundFinal++;}
        return (zFinal + groundFinal) - zOld <= -2;
    }
}
