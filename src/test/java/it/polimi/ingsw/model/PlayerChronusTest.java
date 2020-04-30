package it.polimi.ingsw.model;

import org.junit.*;

public class PlayerChronusTest {
    final Card cardChronus = new Card(10);
    final int numOfPlayers = 3;
    private Player testPlayer1;
    private Match match = new Match(1, numOfPlayers);
    private Worker testWorker;
    private int[] location = new int[]{0,0};
    private int[] build = new int[]{1,1};
    @Before
    public void setUp() throws Exception {
        testPlayer1 = new PlayerChronus("player1", 1, cardChronus, match, Worker.Color.GREEN);
        Player player2 = new Player("player2", 2, match);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, player2, player3};
        testWorker = testPlayer1.workers[0];
        match.forceMove(location[0],location[1],testWorker);
        match.nextPlayer();
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 4; y++){
                match.forceBuild(x, build[1], testWorker);
            }
        }
    }

    @Test
    public void testSuperWin() {
        Assert.assertTrue("Error Check Win", match.checkWin(testPlayer1.workers[0]));
    }
}
