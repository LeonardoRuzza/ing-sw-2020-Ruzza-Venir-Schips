package it.polimi.ingsw.model;

public class ChoiceResponseMessage {
    private final Player player;
    private final Board board;
    private String nextInstruction;

    ChoiceResponseMessage(Board board, Player player, String instruction) {
        this.player = player;
        this.board = board;
        this.nextInstruction = instruction;
    }

    public Player getPlayer() {
        return player;
    }

    public Board getBoard() {
        return board;
    }
}
