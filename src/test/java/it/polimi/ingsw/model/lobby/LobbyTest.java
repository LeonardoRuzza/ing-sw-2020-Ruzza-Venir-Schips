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

    @Test
    public void builder() {
        setUp();
        Assert.assertEquals(l.getLobbyPlayers().get(0).getNickname(), edo);
        Assert.assertEquals(l.getLobbyPlayers().get(1).getNickname(), leo);
        Assert.assertEquals(l.getLobbyPlayers().get(2).getNickname(), vale);

        Assert.assertEquals(l.getNumberOfLobbyPlayer(), l.getLobbyPlayers().size());
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(0)));

        Assert.assertTrue(l.getChosenDeck().isEmpty());
    }

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


    @Test
    public void chooseColor() {
        // Turno del player 1
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


    @Test
    public void chooseCard() {
        setUp();
        chooseColor();

        //Fase di scelta del deck da parte del master Player
        Assert.assertTrue(l.chooseCard("Apollo"));
        Assert.assertFalse(l.isDeckChosen());
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(0)));

        Assert.assertTrue(l.chooseCard("Hera"));
        Assert.assertFalse(l.isDeckChosen());
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(0)));

        Assert.assertTrue(l.chooseCard("Minotaur"));
        Assert.assertTrue(l.isDeckChosen());
        Assert.assertTrue(l.isLobbyPlayerTurn(l.getLobbyPlayers().get(1)));

        // Fase di scelta delle carte da parte dei giocatori
        Assert.assertFalse(l.chooseCard("Pan"));
        Assert.assertTrue(l.chooseCard("Apollo"));
        Assert.assertEquals(l.getStateOfTurn(), StateOfTurn.CARD);
        Assert.assertEquals(l.getLobbyPlayers().get(1).getCard().getName(), "Apollo".toUpperCase());

        Assert.assertFalse(l.chooseCard("Apollo"));
        Assert.assertTrue(l.chooseCard("Minotaur"));
        Assert.assertEquals(l.getStateOfTurn(), StateOfTurn.READYTOSTART);
        Assert.assertEquals(l.getLobbyPlayers().get(2).getCard().getName(), "Minotaur".toUpperCase());
        Assert.assertEquals(l.getLobbyPlayers().get(0).getCard().getName(), "Hera".toUpperCase());

    }
}
