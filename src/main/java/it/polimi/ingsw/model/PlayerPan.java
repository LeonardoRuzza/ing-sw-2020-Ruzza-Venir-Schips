package it.polimi.ingsw.model;

public class PlayerPan extends Player {

    public PlayerPan(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean checkSuperWin(){
        int zOld,zFinal,groundFinal=-1;
        Block tempB;
        zOld=selectedWorker.getOldLocation().getzCoord();
        zFinal=selectedWorker.getCell().getzCoord();
        tempB=selectedWorker.getCell().getBlock();
        if(tempB == Block.B1){groundFinal++;}
        return (zFinal + groundFinal) - zOld <= -2;
    }
}
