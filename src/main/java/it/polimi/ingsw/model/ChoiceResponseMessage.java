package it.polimi.ingsw.model;

public class ChoiceResponseMessage {
    private final Player player;
    private final Match match;
    private final String nextInstruction;

    /**Create a ChoiceResponseMessage.
     * <p>
     * @param match the match associated
     * @param player the player who caused the interaction with the model
     * @param instruction the Sting which contains info of the result derived by the model
     */
    ChoiceResponseMessage(Match match, Player player, String instruction) {
        this.player = player;
        this.match = match;
        this.nextInstruction = instruction;
    }

    /**Getter for the Player of ChoiceResponseMessage.
     * <p>
     * @return {@code Player}
     */
    public Player getPlayer() {
        return player;
    }

    /**Getter for the Match of ChoiceResponseMessage.
     * <p>
     * @return {@code Match}
     */
    public Match getMatch() {
        return match;
    }

    /**Getter for the nextInstruction of ChoiceResponseMessage.
     * <p>
     * @return {@code String}
     */
    public String getNextInstruction() {
        return nextInstruction;
    }
}
