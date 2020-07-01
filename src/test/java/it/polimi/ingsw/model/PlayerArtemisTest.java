package it.polimi.ingsw.model;

import org.junit.*;

public class PlayerArtemisTest {
    private  static Match match;
    private static PlayerArtemis artemis;
    private static Player player2Generic;

    /**
     *Instantiate a match whit 2 players where one of these is a PlayerArtemis.
     */
    @BeforeClass
    public static void beforeClass() {
        match=new Match(1,2);
        artemis=new PlayerArtemis("leo",2,new Card(9),match, Worker.Color.RED);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.GREEN);
        match.players[1] = artemis;
        match.players[0] = player2Generic;
    }

    /**
     * Test the correct working of the override method selectedWorkerMove in these cases:
     * trying to move on the actual position (also not possible for none);
     * trying to move on a not reachable position;
     * trying some possible double movement.
     *
     */
    @Test
    public void testSelectedWorkerMove() {
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,1));

        Assert.assertTrue(artemis.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(artemis.selectedWorkerMove(1,3));
        Assert.assertTrue(artemis.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(artemis.selectedWorkerMove(2,0));

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,2));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,3));

        Assert.assertTrue(artemis.setSelectedWorker(Worker.Gender.Male));
        Assert.assertFalse(artemis.selectedWorkerMove(1,3));              //trying to stay on same position
        Assert.assertFalse(artemis.selectedWorkerMove(4,3));              //trying to move on a not reachable position
        Assert.assertTrue(artemis.selectedWorkerMove(3,3));               //trying a possible double movement
        Assert.assertTrue(artemis.selectedWorkerMove(2,1));               //trying another possible double movement
        Assert.assertFalse(artemis.selectedWorkerMove(0,2));              //trying to move but there are 2 workers that make it not possible
        Assert.assertTrue(artemis.selectedWorkerMove(0,1));               //trying like before but now is possible with a way

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,2));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,1));

        Assert.assertTrue(artemis.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(artemis.selectedWorkerMove(2,2));               //trying to go up using a way with different levels


    }

}
