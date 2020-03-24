package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerPrometheusTest {
    final Card cardProm = new Card(8);
    final int numOfPlayers = 3;
    private Player testPlayer1;
    private Match match = new Match(1, numOfPlayers);
    private Worker testWorkerProm;
    private int[] location = new int[]{0,0};
    private int[] build = new int[]{1,1};
    @Before
    public void setUp() throws Exception {
        testPlayer1 = new PlayerPrometheus("player1", 1, cardProm, match, Worker.Color.RED);
        Player player2 = new Player("player2", 2, match);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, player2, player3};
        testWorkerProm = testPlayer1.workers[0];
        testPlayer1.setSelectedWorker(testWorkerProm);
        match.nextPlayer(); //turno p1;
    }

    @Test
    public void buildTwoTimeTest() {
        Assert.assertTrue("Error First Locate", testPlayer1.selectedWorkerMove(location[0],location[1]));
        location[0] = 1;
        location[1] = 0;
        Assert.assertTrue("Error Build Before Move", testPlayer1.selectedWorkerBuild(build[0],build[1]));
        Assert.assertTrue("Error First Locate", testPlayer1.selectedWorkerMove(location[0],location[1]));
        Assert.assertTrue("Error Build After Move", testPlayer1.selectedWorkerBuild(build[0],build[1]));
        build[0] = 1;
        build[1] = 1;
    }

    @Test
    public void tryGoUpTest() {
        Assert.assertTrue("Error First Locate", testPlayer1.selectedWorkerMove(location[0],location[1]));
        location[0] = 1;
        location[1] = 1;
        Assert.assertTrue("Error Build Before Move", testPlayer1.selectedWorkerBuild(build[0],build[1]));
        Assert.assertFalse("Error First Locate", testPlayer1.selectedWorkerMove(location[0],location[1]));
    }
}
