package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;

public class PlayerChronus extends Player {
    final int numOfWinTower = 5;
    protected PlayerChronus(String nickname, int number, Card card, Match match, Worker.Color color) {
        super(nickname, number, card, match, color);
    }

    /**This method implement the power of Chronus. It allow the Player with this power to win if the number of complete tower is more than 5 (over the others win conditions).
     * <p>
     * @return {@code true} in case of win; {@code false} otherwise
     */
    @Override
    public boolean checkSuperWin(){
        if(match.towerCount() >= numOfWinTower ){
            return true;
        }
        return false;
    }

    @Override
    public ChoiceResponseMessage manageTurn(int x, int y, Worker.Gender gender, String optional) {
        if(checkSuperWin()){ return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageWin);}
        ChoiceResponseMessage response = super.manageTurn(x,y,gender,optional);
        if(checkSuperWin()){ return new ChoiceResponseMessage(match.clone(), this.clone(), GameMessage.turnMessageWin);}
        return response;
    }
}
