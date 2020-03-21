package it.polimi.ingsw.model;

public class PlayerPan extends Player {

    public PlayerPan(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean checkSuperWin(){                    //attenzione bisogna che questa funzione non vega chiamata se la cella su cui si è spostato Pan è perimetrale e gioca Hera! sennò mettere qua una condizione in più
        int zOld,zFinal,groundFinal=-1;
        Block tempB;
        zOld=selectedWorker.getOldLocation().getzCoord();
        zFinal=selectedWorker.getCell().getzCoord();
        tempB=selectedWorker.getCell().getBlock();
        if(tempB == Block.B1){groundFinal++;}
        // testare se condizione corretta!
        return (zFinal + groundFinal) - zOld <= -2;
    }
}
