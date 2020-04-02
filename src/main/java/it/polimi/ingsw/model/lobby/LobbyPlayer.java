package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;

public class LobbyPlayer {
    protected String nickname;
    protected Worker.Color color;
    protected Card card;

    public LobbyPlayer(String nickname) {
        this.nickname = nickname;
    }
}
