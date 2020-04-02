package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;

public class PlayerChoiceMessage {
    private final int x;
    private final int y;
    private final Worker.Gender gender;
    private final Player player;
    private final View view;
    private final String optional;

    public PlayerChoiceMessage(Player player, int x, int y, View view, String optional, Worker.Gender gender) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.view = view;
        this.optional = optional;
        this.gender = gender;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }

    public String getOptional() {
        return optional;
    }

    public Worker.Gender getGender() {
        return gender;
    }
}
