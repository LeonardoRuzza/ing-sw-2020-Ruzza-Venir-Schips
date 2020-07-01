package it.polimi.ingsw.model;

import org.junit.*;
//Win B3(2) to B1(0) || B3(2) to G(0) || B2(1) to G(0)
public class PlayerPanTest {
    final Card cardPan = new Card(7);
    final int numOfPlayers = 3;
    private Player testPlayer1;
    private Match match = new Match(1, numOfPlayers);
    private Worker testWorker;
    private int[] location = new int[]{0,0};
    private int[] build = new int[]{1,1};

    /**
     * Setup a match with three players where one of these is a PlayerPan.
     */
    @Before
    public void setUp() {
        testPlayer1 = new PlayerPan("player1", 1, cardPan, match, Worker.Color.GREEN);
        Player player2 = new Player("player2", 2, match);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, player2, player3};
        testWorker = testPlayer1.workers[0];
        testPlayer1.selectedWorker = testWorker;
        match.forceMove(location[0],location[1],testWorker);
        match.nextPlayer();
    }

    /**
     * Test check win allow pan to win in standard condition.
     */
    @Test
    public void winThreeToGround(){
        for(int x = 0; x < 3; x++){
            match.forceBuild(build[0], build[1], testWorker);
        }
        location[0] = 1;
        location[1] = 1;
        match.forceMove(location[0],location[1],testWorker);
        location[0] = 0;
        location[1] = 0;
        match.forceMove(location[0],location[1],testWorker);
        Assert.assertTrue("Error Check Win", match.checkWin(testPlayer1.workers[0]));
    }

    /**
     * Test check win allow pan to win using his super power (from 3rd level to 1st level).
     */
    @Test
    public void winThreeToBlock(){
        for(int x = 0; x < 3; x++){
            match.forceBuild(build[0], build[1], testWorker);
        }
        location[0] = 1;
        location[1] = 1;
        match.forceMove(location[0],location[1],testWorker);
        build[0] = 0;
        build[1] = 0;
        match.forceBuild(build[0], build[1], testWorker);
        location[0] = 0;
        location[1] = 0;
        match.forceMove(location[0],location[1],testWorker);
        Assert.assertTrue("Error Check Win", match.checkWin(testPlayer1.workers[0]));
    }
}
