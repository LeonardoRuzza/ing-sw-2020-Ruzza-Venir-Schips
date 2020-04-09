package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;

public class LobbyPlayer implements Cloneable{

    protected String nickname;
    protected Worker.Color color;
    protected Card card;


    public LobbyPlayer(String nickname) {
        this.nickname = nickname;
        this.color = null;
        this.card = null;
    }

    public String getNickname() { return nickname; }
    public Worker.Color getColor() { return color; }
    public Card getCard() { return card; }

    @Override
    public LobbyPlayer clone() throws CloneNotSupportedException {
        //return (LobbyPlayer) super.clone();

        LobbyPlayer temp = new LobbyPlayer(this.nickname);
        temp.color = this.color;
        if (card != null) {
            temp.card = new Card(this.card.getNumber());
        } else {
            temp.card = null;
        }
        return temp;
    }
}
