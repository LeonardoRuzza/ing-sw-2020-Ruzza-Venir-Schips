package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;

public class PlayerChoiceMessage {
    private final int x;
    private final int y;
    private final Worker.Gender gender;
    private final Player player;
    private final View view;
    private final String optional;

    /**Create a new PlayerChoiceMessage.
     * <p>
     * @param player the player who requires the interaction
     * @param x coordinate x
     * @param y coordinate y
     * @param view the View of the player
     * @param optional the optional message attached from the request coming from client-side (eventually empty)
     * @param gender the gender of the worker (can be null)
     */
    public PlayerChoiceMessage(Player player, int x, int y, View view, String optional, Worker.Gender gender) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.view = view;
        this.optional = optional;
        this.gender = gender;
    }

    /**Getter for coordinate x.
     * <p>
     * @return {@code int}
     */
    public int getX() {
        return x;
    }

    /**Getter for coordinate y.
     * <p>
     * @return {@code int}
     */
    public int getY() {
        return y;
    }

    /**Getter for the player.
     * <p>
     * @return {@code Player}
     */
    public Player getPlayer() {
        return player;
    }

    /**Getter for the view.
     * <p>
     * @return {@code View}
     */
    public View getView() {
        return view;
    }

    /**Getter for the optional.
     * <p>
     * @return {@code String}
     */
    public String getOptional() {
        return optional;
    }

    /**Getter for the gender (Worker.Gender).
     * <p>
     * @return {@code Worker.Gender}
     */
    public Worker.Gender getGender() {
        return gender;
    }
}
