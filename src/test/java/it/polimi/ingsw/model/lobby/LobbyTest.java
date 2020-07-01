package it.polimi.ingsw.model.lobby;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.utils.InputConversion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LobbyTest {
    Lobby l;
    final String edo = "Edo";
    final String leo = "Leo";
    final String vale = "Vale";

    @Before
    public void setUp() {
        l = new Lobby(edo, leo, vale);
    }

    /**
     * This test check that:
     * <p>
     * - Builder works fine, creating correctly the Lobbyplayers passed as parameter.
     * <p>
     * - Getter works fine.
     * <p>
     * - Variables are correctly initialized.
     */
    @Test
    public void builderAndGetter() {
        setUp();
        Assert.assertEquals(l.getLobbyPlayers().get(0).getNickname(), edo);
        Assert.assertEquals(l.getLobbyPlayers().get(1).getNickname(), leo);
        Assert.assertEquals(l.getLobbyPlayers().get(2).getNickname(), vale);

        Assert.assertEquals(l.getNumberOfLobbyPlayer(), l.getLobbyPlayers().size());
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(0)));

        Assert.assertTrue(l.getChosenDeck().isEmpty());
    }

    /**
     * This test check that:
     * <p>
     * - Method {@code nextLobbyPlayer} pass turn to the next player.
     */
    @Test
    public void nextTurn() {
        setUp();
        l.nextLobbyPlayer();
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(1)));
        l.nextLobbyPlayer();
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(2)));
        l.nextLobbyPlayer();
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(0)));
    }

    /**
     * This test check:
     * <p>
     * - Method {@code updateChosenDeck}
     * <p>
     * - Method {@code getChosenDeck}
     * <p>
     * This test if focused on <b>Insert and Remove Cards</b> from deck.
     * <p>
     * It tries to add and remove cards and then check with the getter if the deck has been updated.
     */
    @Test
    public void updateChosenDeck() {
        setUp();
        Card c1 = new Card(0);
        Card c2 = new Card(1);
        Card c3 = new Card(2);
        l.updateChosenDeck(c1, Lobby.Insert.ADD);
        l.updateChosenDeck(c2, Lobby.Insert.ADD);
        l.updateChosenDeck(c3, Lobby.Insert.ADD);
        Assert.assertTrue(l.getChosenDeck().contains(c1));
        Assert.assertTrue(l.getChosenDeck().contains(c2));
        Assert.assertTrue(l.getChosenDeck().contains(c3));
        l.updateChosenDeck(c1, Lobby.Insert.REMOVE);
        l.updateChosenDeck(c2, Lobby.Insert.REMOVE);
        Assert.assertFalse(l.getChosenDeck().contains(c1));
        Assert.assertFalse(l.getChosenDeck().contains(c2));
        Assert.assertTrue(l.getChosenDeck().contains(c3));
    }

    /**
     * This test mainly check:
     * <p>
     * - Method {@code chooseColor}
     * <p>
     * - Method {@code getAvailableColors}
     * <p>
     * - Method {@code getStateOfTurn}
     * <p>
     * This test if focused on <b>Player's choose of color</b>.
     * <p>
     * It tries to simulate the phase of color's choose in the lobby. It checks that list of available colors is correctly updated.
     * Moreover it checks that after every player has choose his color the Lobby's {@code StateOfTurn} pass to {@code StateOfTurn.CARD}.
     */
    @Test
    public void chooseColor() {
        // Player's 1 turn
        String color = "green";
        Assert.assertTrue(l.chooseColor(color));
        Assert.assertFalse(l.getAvailableColors().contains(color));
        Assert.assertEquals(l.getLobbyPlayers().get(0).getColor(), InputConversion.colorConversion(color.toUpperCase()));
        Assert.assertEquals(l.getStateOfTurn(), StateOfTurn.COLOR);

        color = "red";
        Assert.assertFalse(l.chooseColor("green"));
        Assert.assertTrue(l.chooseColor(color));
        Assert.assertFalse(l.getAvailableColors().contains(color));
        Assert.assertEquals(l.getLobbyPlayers().get(1).getColor(), InputConversion.colorConversion(color.toUpperCase()));
        Assert.assertEquals(l.getStateOfTurn(), StateOfTurn.COLOR);

        color = "yellow";
        Assert.assertFalse(l.chooseColor("green"));
        Assert.assertFalse(l.chooseColor("red"));
        Assert.assertTrue(l.chooseColor(color));
        Assert.assertFalse(l.getAvailableColors().contains(color));
        Assert.assertEquals(l.getLobbyPlayers().get(2).getColor(), InputConversion.colorConversion(color.toUpperCase()));
        Assert.assertEquals(l.getStateOfTurn(), StateOfTurn.CARD);
    }


    /**
     * This test mainly check:
     * <p>
     * - Method {@code chooseCard}
     * <p>
     * - Method {@code isDeckChosen}
     * <p>
     * - Method {@code getStateOfTurn}
     * <p>
     * This test if focused on <b>Player's choose of card</b>.
     * <p>
     * It tries to simulate the phase of cards's choose in the lobby. It checks that list of available cards is correctly updated.
     * Moreover it checks that after every player has choose his card the Lobby's {@code StateOfTurn} pass to {@code StateOfTurn.CHOOSESTARTPLAYER}.
     */
    @Test
    public void chooseCard() {
        setUp();
        chooseColor();

        //Master player is choosing the deck
        Assert.assertTrue(l.chooseCard("Apollo"));
        Assert.assertFalse(l.isDeckChosen());
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(0)));

        Assert.assertTrue(l.chooseCard("Hera"));
        Assert.assertFalse(l.isDeckChosen());
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(0)));

        Assert.assertTrue(l.chooseCard("Minotaur"));
        Assert.assertTrue(l.isDeckChosen());
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(1)));

        // Player's are choosing their cards
        Assert.assertFalse(l.chooseCard("Pan"));
        Assert.assertTrue(l.chooseCard("Apollo"));
        Assert.assertEquals(l.getStateOfTurn(), StateOfTurn.CARD);
        Assert.assertEquals(l.getLobbyPlayers().get(1).getCard().getName(), "Apollo".toUpperCase());

        Assert.assertFalse(l.chooseCard("Apollo"));
        Assert.assertTrue(l.chooseCard("Minotaur"));
        Assert.assertEquals(l.getStateOfTurn(), StateOfTurn.CHOOSESTARTPLAYER);
        Assert.assertEquals(l.getLobbyPlayers().get(2).getCard().getName(), "Minotaur".toUpperCase());
        Assert.assertEquals(l.getLobbyPlayers().get(0).getCard().getName(), "Hera".toUpperCase());

    }

    /**
     * This test check:
     * <p>
     * - Method {@code chooseStartPlayer}
     * <p>
     * - Method {@code getPlayingNow}
     * <p>
     * This test if focused on <b>Master Player choose of starting player</b>.
     * <p>
     * Check that player's chosen by the Master is the one who is marked as playing now.
     */
    @Test
    public void chooseStartPlayer() {
        setUp();
        Assert.assertTrue(l.chooseStartPlayer("edo"));
        Assert.assertEquals(l.getPlayingNow(), 0);
        l.chooseStartPlayer("leo");
        Assert.assertEquals(l.getPlayingNow(), 1);
        l.chooseStartPlayer("vale");
        Assert.assertEquals(l.getPlayingNow(), 2);

        Assert.assertFalse(l.chooseStartPlayer("pippo"));
    }
}
