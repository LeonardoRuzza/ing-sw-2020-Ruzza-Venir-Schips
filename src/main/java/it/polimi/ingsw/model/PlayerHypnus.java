package it.polimi.ingsw.model;

public class PlayerHypnus extends Player {

    public PlayerHypnus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    @Override
    public boolean checkLimitSelection(Player opponent, Worker w){  //ritorna true se la selezione è fattibile sennò false se la limita
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



/*        int zMax=0;
        for(int i=0; i<match.players.length;i++){
            for(int k=0; i<match.players[i].workers.length; k++){
                if(w != match.players[i].workers[k]){                         //controlla di non verificare sullo stesso worker ricevuto come parametro
                    if(match.players[i].workers[k].getCell().getzCoord()>zMax){
                        zMax=match.players[i].workers[k].getCell().getzCoord();
                    }
                }
            }
        }
        return w.getCell().getzCoord()>zMax;*/
    }
}
