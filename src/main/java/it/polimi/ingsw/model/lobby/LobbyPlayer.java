package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;

public class LobbyPlayer implements Cloneable{
    protected String nickname;
    protected Worker.Color color;
    protected Card card;

    public LobbyPlayer(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public LobbyPlayer clone() throws CloneNotSupportedException {
        return (LobbyPlayer) super.clone();
    }
}
