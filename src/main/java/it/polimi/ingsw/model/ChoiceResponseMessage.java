package it.polimi.ingsw.model;

public class ChoiceResponseMessage {
    private final Player player;
    private final Match match;
    private String nextInstruction;

    ChoiceResponseMessage(Match match, Player player, String instruction) {
        this.player = player;
        this.match = match;
        this.nextInstruction = instruction;
    }

    public Player getPlayer() {
        return player;
    }

    public Match getMatch() {
        return match;
    }
    public String getNextInstruction() {
        return nextInstruction;
    }
}
