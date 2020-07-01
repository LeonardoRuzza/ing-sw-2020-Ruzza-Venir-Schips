package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerHypnusTest {

    private static Match match;
    private static Player player1, player2, Hypnus;

    /**
     * Set up a match with 3 players where one of these is a PlayerHypnus.
     */
    @BeforeClass
    public static void beforeClass() {
        match = new Match(1, 2);
        player1 = new Player("edo", 1, new Card(2), match, Worker.Color.GREEN);
        player2 = new Player("leo", 1, new Card(2), match, Worker.Color.RED);
        Hypnus = new PlayerHypnus("Hypnus", 2,  new Card(13), match, Worker.Color.PURPLE);
        match.players[0] = player1;
        match.players[1] = Hypnus;
    }

    /**
     * Test the correct implementation of the Hypnus' power in various situations:
     * normal turn phase of opponents who select one workers at the same level of the other or are in a more down level;
     * selection of a worker who is the highest of the two workers (not allowed by Hypnus).
     */
    @Test
    public void testHypnus() {
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(2, 2));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1, 1));


        Assert.assertTrue(Hypnus.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Hypnus.selectedWorkerMove(2, 3));
        Assert.assertTrue(Hypnus.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Hypnus.selectedWorkerMove(3, 0));


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(3, 2));
        Assert.assertTrue(player1.selectedWorkerBuild(4,2));      // Build B1


        Assert.assertTrue(Hypnus.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Hypnus.selectedWorkerMove(4, 0));
        Assert.assertTrue(Hypnus.selectedWorkerBuild(4,1));


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(4, 2));
        Assert.assertTrue(player1.selectedWorkerBuild(4,3));  // Build B1


        Assert.assertTrue(Hypnus.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Hypnus.selectedWorkerMove(3, 3));
        Assert.assertTrue(Hypnus.selectedWorkerBuild(4,3)); // Build B2


        Assert.assertFalse(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(0, 0));

    }
}
