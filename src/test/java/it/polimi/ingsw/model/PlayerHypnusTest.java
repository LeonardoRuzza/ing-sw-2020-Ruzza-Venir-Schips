package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerHypnusTest {

    private static Match match;
    private static Player player1, player2, Hypnus;

    @BeforeClass
    public static void beforeClass() throws Exception {
        match = new Match(1, 2);
        player1 = new Player("edo", 1, new Card(2), match, Worker.Color.GREEN);
        player2 = new Player("leo", 1, new Card(2), match, Worker.Color.RED);
        Hypnus = new PlayerHypnus("Hypnus", 2,  new Card(13), match, Worker.Color.PURPLE);
        match.players[0] = player1;
        match.players[1] = Hypnus;
    }


    @Test
    public void testHypnus() {

        /*System.out.println(Hypnus.card.getName());
        System.out.println(Hypnus.card.getActivationPeriod());
        System.out.println(Hypnus.card.getDesc());*/


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
        Assert.assertTrue(player1.selectedWorkerBuild(4,2));      // Costruisce B1


        Assert.assertTrue(Hypnus.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Hypnus.selectedWorkerMove(4, 0));
        Assert.assertTrue(Hypnus.selectedWorkerBuild(4,1));


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(4, 2));
        Assert.assertTrue(player1.selectedWorkerBuild(4,3));  // Costruisce B1


        Assert.assertTrue(Hypnus.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Hypnus.selectedWorkerMove(3, 3));
        Assert.assertTrue(Hypnus.selectedWorkerBuild(4,3)); // Costruisce B2


        Assert.assertFalse(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(0, 0));

    }
}
