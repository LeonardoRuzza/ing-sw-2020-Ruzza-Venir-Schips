package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

public class MatchTest {
    final int numOfPlayers = 3;
    private Match match = new Match(1, numOfPlayers);

    @Test
    public void testGetNumberOfPlayers(){
        Assert.assertTrue("error number of player", match.getNumberOfPlayers() == numOfPlayers);
    }


    @Test
    public void testCheckWin() {
       //per test server player
    }

    final Worker testWorkerOne = new Worker(Worker.Gender.Male, Worker.Color.BLACK);

    @Test
    public void testCheckSuperWin() {

        //per test server player
    }

    @Test
    public void testCheckLoserMove() {
        //per test serve player
    }
    @Test
    public void testCheckLoserBuild() {
        //per test serve player
    }
    @Test
    public void testMovementLocateMovement() {
        int[] location = new int[]{0,0};
        //testPosizionamento inizio partita
        Assert.assertTrue("Error checkMove first locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        //test spostamento prima casella
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        location[0] = 1;
        location[1] = 0;
        //test Movimento
        Assert.assertTrue("Error checkMove locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        //test spostamento nuova casella
        Assert.assertTrue("Error forceMove locate", match.forceMove(location[0],location[1],testWorkerOne));
    }
    @Test
    public void testBuildMoveOnBuild(){
        testMovementLocateMovement();
        int[] build = new int[]{1,1};
        //testPosizionamento inizio partita
        Assert.assertTrue("Error checkBuild on locate", match.checkBuild(build[0],build[1],testWorkerOne));
        //test spostamento prima casella
        Assert.assertTrue("Error forceBuild on locate", match.forceBuild(build[0],build[1],testWorkerOne));
        int[] location = new int[]{1,1};
        location[0] = 1;
        location[1] = 1;
        //test Movimento
        Assert.assertTrue("Error checkMove locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        //test spostamento nuova casella
        Assert.assertTrue("Error forceMove locate", match.forceMove(location[0],location[1],testWorkerOne));
        Assert.assertEquals(testWorkerOne.getCell().getBlock(), Block.B1);
    }
    @Test
    public void testNextPlayer() {
        Player player1 = new Player("player1", 1, match);
        Player player2 = new Player("player2", 2, match);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{player1, player2, player3};
        Assert.assertTrue("Error nextPlayerActNull", match.nextPlayer().equals(player1));
        Assert.assertTrue("Error nextPlayerAct1", match.nextPlayer().equals(player2));
        Assert.assertTrue("Error nextPlayerAct2", match.nextPlayer().equals(player3));
        Assert.assertTrue("Error nextPlayerAct3", match.nextPlayer().equals(player1));
    }

}
