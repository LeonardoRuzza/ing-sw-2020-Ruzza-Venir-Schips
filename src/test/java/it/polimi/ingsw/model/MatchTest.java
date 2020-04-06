package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatchTest {
    final int numOfPlayers = 3;
    private Player testPlayer1;
    Player player2;
    Player player3;
    private Match match = new Match(1, numOfPlayers);


    @Before
    public void setUp(){
        testPlayer1 = new Player("player1", 1, match);
        player2 = new Player("player2", 2, match);
        player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, player2, player3};
        match.nextPlayer();
    }

    @Test
    public void testGetNumberOfPlayers(){
        Assert.assertEquals("error number of player", match.getNumberOfPlayers(), numOfPlayers);
    }

    @Test
    public void testCheckWinFalse() {
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
        Assert.assertTrue("Error Check Loser Move", match.checkLoserMove(testWorkerOne));
    }
    @Test
    public void testCheckLoserBuild() {
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
        Assert.assertTrue("Error Check Loser Move", match.checkLoserBuild(testWorkerOne));
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
        Assert.assertTrue("Error nextPlayerAct1", match.nextPlayer().equals(player2));
        Assert.assertTrue("Error nextPlayerAct2", match.nextPlayer().equals(player3));
        Assert.assertTrue("Error nextPlayerAct3", match.nextPlayer().equals(testPlayer1));
    }

    @Test
    public void testRemovePlayer() {
        match.removePlayer(testPlayer1);
        Assert.assertTrue("Error numb of players:" + match.getNumberOfPlayers(), match.getNumberOfPlayers() == 2);
        Assert.assertTrue("Error players:" + match.players[0]+match.players[1], match.players.length == 2);
        match.removePlayer(player2);
        Assert.assertTrue("Error numb of players:" + match.getNumberOfPlayers(), match.getNumberOfPlayers() == 1);
        Assert.assertTrue("Error players:" + match.players[0], match.players.length == 1);
    }

    @Test
    public void testTowerCount() {
        for(int x=0; x<match.getBoard().boardSide; x++){
            for(int y=0; y<match.getBoard().boardSide; y++){
                match.forceBuild(x,y,null);
                match.forceBuild(x,y,null);
                match.forceBuild(x,y,null);
                match.forceBuild(x,y,null);
            }
        }
        Assert.assertEquals(match.towerCount(), 5*5);
        match.getBoard().draw();
    }
}
