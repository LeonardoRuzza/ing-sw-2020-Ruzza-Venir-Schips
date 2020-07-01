package it.polimi.ingsw.model;

import org.junit.*;

import java.util.Arrays;

public class PlayerMinotaurTest {
    private static Match match;
    private static Player player1, Minotaur;

    /**
     * Instantiate a match wit 2 players where one of these is a PlayerMinotaur.
     */
    @BeforeClass
    public static void beforeClass() {
        match = new Match(1,2);
        player1 = new Player("edo",1,new Card(2),match,Worker.Color.RED);
        Minotaur = new PlayerMinotaur("Minotaur",2,new Card(1),match, Worker.Color.GREEN);
        match.players[0] = player1;
        match.players[1] = Minotaur;
    }

    /**
     * Test the override method selectedWorkerMove of PlayerMinotaur in these cases:
     * trying to move on an occupied space in a possible case (same levels);
     * trying to move on an occupied space in a possible case (different levels);
     * normal movements;
     * trying to move on an occupied space where is other PlayerMinotaur's worker (not possible);
     */
    @Test
    public void testSelectedWorkerMove() {

        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(2,2));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1,1));


        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Minotaur.selectedWorkerMove(2,3));
        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Minotaur.selectedWorkerMove(3,0));


        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Minotaur.selectedWorkerMove(2,2));
        Assert.assertEquals(Minotaur.workers[0].getCell(), player1.workers[0].getOldLocation());  // Checking the current positions
        Assert.assertTrue(Minotaur.selectedWorkerBuild(2, 3));


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1,0));
        Assert.assertTrue(player1.selectedWorkerBuild(2,0));       // Build B1


        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Minotaur.selectedWorkerMove(3,1));
        Assert.assertTrue(Minotaur.selectedWorkerBuild(2, 0));     // Build B2


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(0,0));
        Assert.assertTrue(player1.selectedWorkerBuild(0,1));


        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Minotaur.selectedWorkerMove(2,1));
        Assert.assertEquals(Minotaur.workers[0].getCell(), player1.workers[0].getOldLocation());  // Checking actual position

        Assert.assertFalse(Minotaur.selectedWorkerMove(3,1)); //not possible movement on other proper worker

        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(0,1));
        Assert.assertTrue(player1.selectedWorkerBuild(0,2));

        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Minotaur.selectedWorkerMove(1,0));
        Assert.assertTrue(Minotaur.selectedWorkerMove(0,0));
        Assert.assertTrue(Minotaur.selectedWorkerMove(0,1));  //trying to move on a different level space occupied by and opponent's worker
        Assert.assertFalse(Minotaur.selectedWorkerBuild(0,2)); //checking using a selectedWorkerBuild that can not build because there are a worker in the space
    }
}
