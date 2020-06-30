package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerChronusTest {
    final Card cardChronus = new Card(10);
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
        testPlayer1 = new PlayerChronus("player1", 1, cardChronus, match, Worker.Color.GREEN);
        Player player2 = new Player("player2", 2, match);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, player2, player3};
        testWorker = testPlayer1.workers[0];
        match.forceMove(location[0],location[1],testWorker);
        match.nextPlayer();

    }

    /**
     * Check if match check win return false for Chronus player with less than five tower on the board
     */
    @Test
    public void testNoWin() {
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                match.forceBuild(x, build[1], testWorker);
            }
            Assert.assertFalse("Error Check Win", match.checkWin(testPlayer1.workers[0]));
        }
    }

    /**
     * Check if manage turn does not return you win message for Chronus player with less than 5 complete tower on the board
     */
    @Test
    public void testManageTurnNoWin(){
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                match.forceBuild(x, build[1], testWorker);
            }
        }
        Assert.assertEquals(testPlayer1.manageTurn(-1, -1, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection+GameMessage.turnMessageChooseCellMove);
    }

    /**
     * Check if match check win return true for Chronus player with 5 complete tower on the board
     */
    @Test
    public void testSuperWin() {
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 4; y++){
                match.forceBuild(x, build[1], testWorker);
            }
        }
        Assert.assertTrue("Error Check Win", match.checkWin(testPlayer1.workers[0]));
    }

    /**
     * Check if manage turn return you win message for Chronus player with 5 complete tower on the board
     */
    @Test
    public void testManageTurn(){
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 4; y++){
                match.forceBuild(x, build[1], testWorker);
            }
        }
        Assert.assertEquals(testPlayer1.manageTurn(-1, -1, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageWin);
    }
}
