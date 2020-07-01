package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
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

    /**
     * Setup a match with three players
     */
    @Before
    public void setUp() {
        testPlayer1 = new PlayerAtlas("player1", 1, cardAtlas, match, Worker.Color.GREEN);
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

    /**
     * Test simply the correct working of the method to build a dorse of PlayerAtlas.
     */
    @Test
    public void testSuperWin() {
        testPlayer1.selectedWorker = testWorker;
        Assert.assertTrue("Error Build Dorse", testPlayer1.selectedWorkerBuildDorse(0,1));
        Assert.assertEquals("No Dorse built", testWorker.getOldLocation().getBlock(), Block.DORSE);
    }

    /**
     * Test the override manage turn permit to atlas player to build a dorse instead of a block.
     */
    @Test
    public void testManageTurnBuildDorse() {
        testPlayer1.setSelectedWorker(testPlayer1.workers[0]);
        testPlayer1.selectedWorkerMove(0,0);
        Assert.assertEquals("Error selection worker", testPlayer1.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement worker", testPlayer1.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement + GameMessage.atlasTurnMessageAskBuildDorse);
        Assert.assertEquals("Error single building worker", testPlayer1.manageTurn(1,0, Worker.Gender.Male, GameMessage.turnMessageDORSE).getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.turnMessageTurnEnd);
    }

    /**
     * Test the override manage turn permit to atlas player also to build a normal block.
     */
    @Test
    public void testManageTurnBuildNormal() {
        testPlayer1.setSelectedWorker(testPlayer1.workers[0]);
        testPlayer1.selectedWorkerMove(0,0);
        Assert.assertEquals("Error selection worker", testPlayer1.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement worker", testPlayer1.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement + GameMessage.atlasTurnMessageAskBuildDorse);
        Assert.assertEquals("Error single building worker", testPlayer1.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.turnMessageTurnEnd);
    }
}
