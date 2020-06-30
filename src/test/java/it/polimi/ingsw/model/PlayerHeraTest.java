package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerHeraTest {
    final Card cardHera = new Card(11);
    final Card cardAtlas = new Card(3);
    final int numOfPlayers = 3;
    private Player testPlayer1;
    private Match match = new Match(1, numOfPlayers);
    private Worker testWorker;
    private int[] location = new int[]{0,0};
    private int[] build = new int[]{1,1};

    /**
     * Setup a match with three players
     */
    @Before
    public void setUp() {
        testPlayer1 = new PlayerAtlas("player1", 2, cardAtlas, match, Worker.Color.RED);
        Player testPlayer2 = new PlayerHera("player2", 1, cardHera, match, Worker.Color.GREEN);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, testPlayer2, player3};
        testWorker = testPlayer1.workers[0];
        testPlayer1.selectedWorker = testWorker;
        match.nextPlayer();
    }

    /**
     * Check if checkWin limit the win of testPlayer1 because of Hera(testPlayer2) super power
     */
    @Test
    public void testLimitWinFalse() {
        for(int x = 0; x < 2; x++){
            match.forceBuild(build[0], build[1], testWorker);
        }
        match.forceMove(location[0],location[1],testWorker);
        build[0] = 0;
        build[1] = 0;
        for(int x = 0; x < 3; x++){
            match.forceBuild(build[0], build[1], testWorker);
        }
        location[0] = 0;
        location[1] = 0;
        match.forceMove(location[0],location[1],testWorker);
        Assert.assertFalse("Error Check Win", match.checkWin(testWorker));
    }

    /**
     * Check if checkWin does not limit the win of testPlayer1 because of Hera(testPlayer2) super power
     */
    @Test
    public void testLimitWinTrue() {
        for(int x = 0; x < 3; x++){
            match.forceBuild(build[0], build[1], testWorker);
        }
        build[0] = 0;
        build[1] = 0;
        for(int x = 0; x < 2; x++){
            match.forceBuild(build[0], build[1], testWorker);
        }
        match.forceMove(location[0],location[1],testWorker);
        location[0] = 1;
        location[1] = 1;
        match.forceMove(location[0],location[1],testWorker);
        Assert.assertTrue("Error Check Win", match.checkWin(testWorker));
    }
}
