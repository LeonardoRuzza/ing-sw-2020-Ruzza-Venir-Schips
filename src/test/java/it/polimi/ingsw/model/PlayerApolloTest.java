package it.polimi.ingsw.model;

import org.junit.*;

public class PlayerApolloTest {

    private static Match match;
    private static Player player1, Apollo;

    /**
     *This method instantiate a match with 2 players where one of these is PlayerApollo.
     */
    @BeforeClass
    public static void beforeClass() {
        match = new Match(1,2);
        player1 = new Player("edo",1,new Card(2),match,Worker.Color.GREEN);
        Apollo = new PlayerApollo("Apollo",2,new Card(1),match, Worker.Color.RED);
        match.players[0] = player1;
        match.players[1] = Apollo;
    }

    /**
     * This method check the correct working of selectedWorkerMove override in PlayerApollo in various cases:
     * the first allocation of workers;
     * a possible switch with an opponent's worker;
     * a normal movement;
     * a not possible switching with other himself worker;
     */
    @Test
    public void testSelectedWorkerMove() {

        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(2,2));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1,1));

        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Apollo.selectedWorkerMove(1,3));   // First allocation (Male)
        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Apollo.selectedWorkerMove(2,0));   // First allocation (Female)


        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Apollo.selectedWorkerMove(2,2));   // Apollo switch with an opponent's worker
        Assert.assertEquals(Apollo.workers[0].getCell(), player1.workers[0].getOldLocation());
        Assert.assertEquals(player1.workers[0].getCell(), Apollo.workers[0].getOldLocation());


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male)); // A movement possible only if the previously switching worked.
        Assert.assertTrue(player1.selectedWorkerMove(0,3));
        Assert.assertTrue(player1.selectedWorkerBuild(1,2));


        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Apollo.selectedWorkerMove(2,1));   // Apollo normal movement
        Assert.assertTrue(Apollo.selectedWorkerBuild(3, 1));


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(1,2));
        Assert.assertTrue(player1.selectedWorkerBuild(0,3));


        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Apollo.selectedWorkerMove(1,2));   //Another Apollo's special movement

        Assert.assertFalse(Apollo.selectedWorkerMove(2,1)); //A not possible movement of Apollo (switching two himself workers)
    }
}
