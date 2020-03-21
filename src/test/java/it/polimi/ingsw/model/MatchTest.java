package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

public class MatchTest {
    final int numOfPlayers = 3;
    private Player testPlayer1;
    private Match match = new Match(1, numOfPlayers);

    @Test
    public void testGetNumberOfPlayers(){
        Assert.assertEquals("error number of player", match.getNumberOfPlayers(), numOfPlayers);
    }

    @Test
    public void testCheckWinFalse() {
        testNextPlayer();
        testPlayer1.workers[0] = testWorkerOne;
        int[] location = new int[]{0,0};
        //testPosizionamento inizio partita
        Assert.assertTrue("Error checkMove first locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        //test spostamento prima casella
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        int[] build = new int[]{1,1};
        //test checkCostruzione
        Assert.assertTrue("Error checkBuild on locate", match.checkBuild(build[0],build[1],testWorkerOne));
        //test costruzione
        Assert.assertTrue("Error forceBuild on locate", match.forceBuild(build[0],build[1],testWorkerOne));
        Assert.assertFalse("Error Check Win", match.checkWin(testPlayer1.workers[0]));
    }

    @Test
    public void testCheckWinTrue() {
        testNextPlayer();
        testPlayer1.workers[0] = testWorkerOne;
        int[] location = new int[]{0,0};
        //testPosizionamento inizio partita
        Assert.assertTrue("Error checkMove first locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        //test spostamento prima casella
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        int[] build = new int[]{1,1};
        //test checkCostruzione
        Assert.assertTrue("Error checkBuild on locate", match.checkBuild(build[0],build[1],testWorkerOne));
        //test costruzione
        Assert.assertTrue("Error forceBuild on locate", match.forceBuild(build[0],build[1],testWorkerOne));
        //movimento
        location[0] = 1;
        location[1] = 1;
        Assert.assertTrue("Error checkMove first locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        //costruzione
        build[0] = 0;
        build[1] = 0;
        Assert.assertTrue("Error checkBuild on locate", match.checkBuild(build[0],build[1],testWorkerOne));
        Assert.assertTrue("Error forceBuild on locate", match.forceBuild(build[0],build[1],testWorkerOne));
        Assert.assertTrue("Error checkBuild on locate", match.checkBuild(build[0],build[1],testWorkerOne));
        Assert.assertTrue("Error forceBuild on locate", match.forceBuild(build[0],build[1],testWorkerOne));
        //movimento
        location[0] = 0;
        location[1] = 0;
        Assert.assertTrue("Error checkMove first locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        //costruzione
        build[0] = 1;
        build[1] = 1;
        Assert.assertTrue("Error checkBuild on locate", match.checkBuild(build[0],build[1],testWorkerOne));
        Assert.assertTrue("Error forceBuild on locate", match.forceBuild(build[0],build[1],testWorkerOne));
        Assert.assertTrue("Error checkBuild on locate", match.checkBuild(build[0],build[1],testWorkerOne));
        Assert.assertTrue("Error forceBuild on locate", match.forceBuild(build[0],build[1],testWorkerOne));
        //movimento al 3 piano
        location[0] = 1;
        location[1] = 1;
        Assert.assertTrue("Error checkMove first locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));

        Assert.assertTrue("Error Check Win", match.checkWin(testPlayer1.workers[0]));
    }

    final Worker testWorkerOne = new Worker(Worker.Gender.Male, Worker.Color.BLACK);

    @Test
    public void testCheckLoserMove() {
        testNextPlayer();
        testPlayer1.workers[0] = testWorkerOne;
        int[] location = new int[]{1,1};
        int[] build = new int[]{1,1};
        Assert.assertTrue("Error checkMove first locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        int lastBuild = 0;
        while(lastBuild != 4){
            location[0] = 0;
            location[1] = 0;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
            match.forceBuild(build[0],build[1], testWorkerOne);
            lastBuild++;
            location[0] = 1;
            location[1] = 1;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        }
        location[0] = 1;
        location[1] = 0;
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        lastBuild = 0;
        while(lastBuild != 4){
            location[0] = 0;
            location[1] = 0;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
            build[0] = 1;
            build[1] = 0;
            match.forceBuild(build[0],build[1], testWorkerOne);
            lastBuild++;
            location[0] = 1;
            location[1] = 0;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        }
        location[0] = 0;
        location[1] = 1;
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        lastBuild = 0;
        while(lastBuild != 4){
            location[0] = 0;
            location[1] = 0;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
            build[0] = 0;
            build[1] = 1;
            match.forceBuild(build[0],build[1], testWorkerOne);
            lastBuild++;
            location[0] = 0;
            location[1] = 1;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        }
        location[0] = 0;
        location[1] = 0;
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        Assert.assertTrue("Error Check Loser Move", match.checkLoserMove(testWorkerOne).equals(testPlayer1));
    }
    @Test
    public void testCheckLoserBuild() {
        testNextPlayer();
        testPlayer1.workers[0] = testWorkerOne;
        int[] location = new int[]{1,1};
        int[] build = new int[]{1,1};
        Assert.assertTrue("Error checkMove first locate", match.checkMove(location[0],location[1],testWorkerOne).equals(testWorkerOne));
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        int lastBuild = 0;
        while(lastBuild != 4){
            location[0] = 0;
            location[1] = 0;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
            match.forceBuild(build[0],build[1], testWorkerOne);
            lastBuild++;
            location[0] = 1;
            location[1] = 1;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        }
        location[0] = 1;
        location[1] = 0;
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        lastBuild = 0;
        while(lastBuild != 4){
            location[0] = 0;
            location[1] = 0;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
            build[0] = 1;
            build[1] = 0;
            match.forceBuild(build[0],build[1], testWorkerOne);
            lastBuild++;
            location[0] = 1;
            location[1] = 0;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        }
        location[0] = 0;
        location[1] = 1;
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        lastBuild = 0;
        while(lastBuild != 4){
            location[0] = 0;
            location[1] = 0;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
            build[0] = 0;
            build[1] = 1;
            match.forceBuild(build[0],build[1], testWorkerOne);
            lastBuild++;
            location[0] = 0;
            location[1] = 1;
            Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        }
        location[0] = 0;
        location[1] = 0;
        Assert.assertTrue("Error forceMove first locate", match.forceMove(location[0],location[1],testWorkerOne));
        Assert.assertTrue("Error Check Loser Move", match.checkLoserBuild(testWorkerOne).equals(testPlayer1));
    }
    @Test
    public void testMovementLocateMovement() {
        testNextPlayer();
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
        //test checkCostruzione
        Assert.assertTrue("Error checkBuild on locate", match.checkBuild(build[0],build[1],testWorkerOne));
        //test costruzione
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
        testPlayer1 = new Player("player1", 1, match);
        Player player2 = new Player("player2", 2, match);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, player2, player3};
        Assert.assertTrue("Error nextPlayerActNull", match.nextPlayer().equals(testPlayer1));
        Assert.assertTrue("Error nextPlayerAct1", match.nextPlayer().equals(player2));
        Assert.assertTrue("Error nextPlayerAct2", match.nextPlayer().equals(player3));
        Assert.assertTrue("Error nextPlayerAct3", match.nextPlayer().equals(testPlayer1));
    }

}
