package it.polimi.ingsw.model;

public class PlayerHypnus extends Player {

    protected PlayerHypnus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean checkLimitSelection(Player opponent, Worker w){  //ritorna true se la selezione è fattibile sennò false se la limita
        if(opponent.equals(this)) return true;
        if(w.getCell() == null) {return true;}
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
