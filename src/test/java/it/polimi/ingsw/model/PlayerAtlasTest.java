package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class PlayerAtlasTest {
    final Card cardAtlas = new Card(3);
    final int numOfPlayers = 3;
    private Player testPlayer1;
    private Match match = new Match(1, numOfPlayers);
    private Worker testWorker;
    private int[] location = new int[]{0,1};
    private int[] build = new int[]{1,1};
    @Before
    public void setUp() throws Exception {
        testPlayer1 = new PlayerAtlas("player1", 1, cardAtlas, match, Worker.Color.BLACK);
        Player player2 = new Player("player2", 2, match);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, player2, player3};
        testWorker = testPlayer1.workers[0];
        match.forceMove(location[0],location[1],testWorker);
        location[0] = 0;
        location[1] = 0;
        match.forceMove(location[0],location[1],testWorker);
        match.nextPlayer();
    }

    @Test
    public void testSuperWin() {
        testPlayer1.selectedWorker = testWorker;
        Assert.assertTrue("Error Build Dorse", testPlayer1.selectedWorkerBuildDorse(0,1));
        Assert.assertEquals("No Dorse Builded", testWorker.getOldLocation().getBlock(), Block.DORSE);
    }
}
