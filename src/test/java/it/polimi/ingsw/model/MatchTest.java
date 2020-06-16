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
        match = new Match(1, numOfPlayers);
        testPlayer1 = new PlayerApollo("player1", 1, new Card(0), match, Worker.Color.RED);
        player2 = new PlayerAthena("player2", 2,  new Card(2),  match, Worker.Color.GREEN);
        match.addPlayer(testPlayer1);
        match.addPlayer(player2);
        if(match.getNumberOfPlayers() == 3){
            player3 = new Player("player3", 3,  new Card(11),  match, Worker.Color.YELLOW);
            match.addPlayer(player3);
        }
        match.nextPlayer();
    }

    @Test
    public void testInitializeGame() {
        match.initializeGame();
    }

    @Test
    public void testGetNumberOfPlayers(){
        Assert.assertEquals("error number of player", match.getNumberOfPlayers(), numOfPlayers);
    }

    @Test
    public void testAddPlayer(){
        match.players = new Player[]{null, null, null};
        match.addPlayer(testPlayer1);
        Assert.assertEquals("Error add Player1", match.players[0], testPlayer1);
        match.addPlayer(player2);
        Assert.assertEquals("Error add Player2", match.players[1], player2);
        if(match.getNumberOfPlayers() == 3){
            match.addPlayer(player3);
            Assert.assertEquals("Error add Player3", match.players[2], player3);
        }

    }

    @Test
    public void testCheckWin(){
        testPlayer1.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,0, testPlayer1.selectedWorker);
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        match.forceMove(1,0, testPlayer1.selectedWorker);
        Assert.assertFalse("Error Check Win False", match.checkWin(testPlayer1.selectedWorker));
        match.forceBuild(0,0,testPlayer1.selectedWorker);
        match.forceBuild(0,0,testPlayer1.selectedWorker);
        match.forceBuild(0,0,testPlayer1.selectedWorker);
        match.forceMove(0,0, testPlayer1.selectedWorker);
        Assert.assertTrue("Error Check Win True", match.checkWin(testPlayer1.selectedWorker));
    }

    @Test
    public void testCheckLoserMove(){
        testPlayer1.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,0, testPlayer1.selectedWorker);
        player2.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,1, player2.selectedWorker);
        match.forceBuildDorse(1,1,player2.selectedWorker);
        match.forceBuild(1,0,player2.selectedWorker);
        match.forceBuild(1,0,player2.selectedWorker);
        Assert.assertFalse("Error Player1 is loser for move", match.checkLoserMove(testPlayer1.selectedWorker));
        match.removeBlock(1,0);
        Assert.assertFalse("Error Player1 not loser for move", match.checkLoserMove(testPlayer1.selectedWorker));
    }

    @Test
    public void testCheckLoserBuild(){
        testPlayer1.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,0, testPlayer1.selectedWorker);
        player2.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,1, player2.selectedWorker);
        match.forceBuildDorse(1,1,player2.selectedWorker);
        match.forceBuild(1,0,player2.selectedWorker);
        match.forceBuild(1,0,player2.selectedWorker);
        match.forceBuild(1,0,player2.selectedWorker);
        match.forceBuild(1,0,player2.selectedWorker);
        Assert.assertTrue("Error Player1 is loser for build Fail", match.checkLoserBuild(testPlayer1.selectedWorker));
        match.removeBlock(1,0);
        Assert.assertFalse("Error Player1 not loser for build Success", match.checkLoserBuild(testPlayer1.selectedWorker));
    }

    @Test
    public void testCheckMove(){
        testPlayer1.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,0, testPlayer1.selectedWorker);
        player2.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,1, player2.selectedWorker);
        Assert.assertEquals("Error checkMove on other worker", match.checkMove(0, 1, testPlayer1.selectedWorker), player2.selectedWorker);
        Assert.assertNull("Error checkMove out of the board", match.checkMove(-1,3, testPlayer1.selectedWorker));
        Assert.assertNull("Error checkMove worker move on same cell", match.checkMove(0,0, testPlayer1.selectedWorker));
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        Assert.assertNull("Error checkMove worker move too up", match.checkMove(1,0, testPlayer1.selectedWorker));
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        Assert.assertNull("Error checkMove worker move on dorse", match.checkMove(1,0, testPlayer1.selectedWorker));
        Assert.assertEquals("Error checkMove Success", match.checkMove(1, 1, testPlayer1.selectedWorker), testPlayer1.selectedWorker);
    }

    @Test
    public void testCheckBuild(){
        testPlayer1.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,0, testPlayer1.selectedWorker);
        player2.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,1, player2.selectedWorker);
        Assert.assertFalse("Error checkBuild on other worker", match.checkBuild(0,1,testPlayer1.selectedWorker));
        Assert.assertFalse("Error checkBuild out of the board", match.checkBuild(-1,3, testPlayer1.selectedWorker));
        Assert.assertFalse("Error checkBuild worker move on same cell", match.checkBuild(0,0, testPlayer1.selectedWorker));
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        match.forceBuild(1,0,testPlayer1.selectedWorker);
        Assert.assertFalse("Error checkBuild worker move on dorse", match.checkBuild(1,0, testPlayer1.selectedWorker));
        Assert.assertTrue("Error checkBuild Success", match.checkBuild(1,1, testPlayer1.selectedWorker));
    }

    @Test
    public void testForceMoveAndForceMoveLimit(){
        testPlayer1.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,0, testPlayer1.selectedWorker);
        match.nextPlayer();
        player2.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,1, player2.selectedWorker);
        match.forceBuild(0,2, player2.selectedWorker);
        Assert.assertTrue("Error Athen(P2) go up 1LV Success", match.getPlayingNow().selectedWorkerMove(0,2));
        match.nextPlayer();
        match.nextPlayer();
        match.forceBuild(1,0, testPlayer1.selectedWorker);
        Assert.assertFalse("Error Player1 try go up 1LV Fail", match.forceMove(1,0, testPlayer1.selectedWorker));
        Assert.assertTrue("Error Player1 try to move Success", match.forceMove(1,1, testPlayer1.selectedWorker));
    }

    @Test
    public void testForceBuild (){
        match.nextPlayer();
        player2.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,1, player2.selectedWorker);
        Assert.assertTrue("Error Player2 try to build Success", match.forceBuild(0,2, player2.selectedWorker));
    }
    @Test
    public void testForceBuildDorse (){
        match.nextPlayer();
        player2.setSelectedWorker(Worker.Gender.Male);
        match.forceMove(0,1, player2.selectedWorker);
        Assert.assertTrue("Error Player2 try to build Success", match.forceBuildDorse(0,2, player2.selectedWorker));
    }

    @Test
    public void testNextPlayer() {
        Assert.assertEquals("Error nextPlayerAct1", match.nextPlayer(), player2);
        Assert.assertEquals("Error nextPlayerAct2", match.nextPlayer(), player3);
        Assert.assertEquals("Error nextPlayerAct3", match.nextPlayer(), testPlayer1);
    }

    @Test
    public void removePlayer(){
        match.removePlayer(match.players[0]);
        match.removePlayer(match.players[0]);
        Assert.assertEquals("Error remove Player", match.players[0], player3);

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
        //match.getBoard().draw(player2); //manda player 2 solo perchè è necessario mandare un player come parametro
    }

    @Test
    public void testFirstAllocation() {
        testPlayer1.setSelectedWorker(Worker.Gender.Male);
        Assert.assertTrue("Error First allocation worker", match.firstAllocation(3,3,testPlayer1.selectedWorker.getGender()));
    }

    @Test
    public void testExecFirstAllocation() {
        match.execFirstAllocation(0,0, Worker.Gender.Male);
        match.execFirstAllocation(0,0, Worker.Gender.Male);
        match.execFirstAllocation(0,0, Worker.Gender.Female);
        match.execFirstAllocation(0,1, Worker.Gender.Female);
        match.execFirstAllocation(1,0, Worker.Gender.Male);
        match.execFirstAllocation(1,1, Worker.Gender.Female);
        match.execFirstAllocation(2,0, Worker.Gender.Male);
        match.execFirstAllocation(2,1, Worker.Gender.Female);
    }
}
