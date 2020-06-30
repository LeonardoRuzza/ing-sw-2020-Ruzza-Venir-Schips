package it.polimi.ingsw.model;

public class PlayerHypnus extends Player {

    protected PlayerHypnus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**Implement the power of Hypnus which specify that if one of opponent’s workers is higher than all of their others, it cannot move.
     * <p>
     * @param opponent actual Player
     * @param w worker of the actual Player which he want to select
     * @return {@code true} if the selection is possible; {@code false} otherwise
     */
    @Override
    protected boolean checkLimitSelection(Player opponent, Worker w){
        if(opponent.equals(this)) return true;
        if(opponent.workers[0].getCell() == null || opponent.workers[1].getCell() == null ) {return true;}
        return !isHigherWorker(opponent, w);
    }


    private boolean isHigherWorker(Player opponent, Worker w){
        int zW, zOtherW;
        zW=w.getCell().getzCoord();
        if(w.getCell().getBlock() != null){
            zW++;
        }
        if(w.getGender() == Worker.Gender.Male){
            zOtherW=opponent.workers[1].getCell().getzCoord();
            if(opponent.workers[1].getCell().getBlock() != null){
                zOtherW++;
            }
        }
        else {
            zOtherW=opponent.workers[0].getCell().getzCoord();
            if(opponent.workers[0].getCell().getBlock() != null){
                zOtherW++;
            }
        }
        return zW>zOtherW;
    }
}
