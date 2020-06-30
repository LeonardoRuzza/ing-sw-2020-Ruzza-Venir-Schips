package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.utils.GameMessage.turnMessageBUILDBEFORE;

public class PlayerPrometheusTest {
    final Card cardProm = new Card(8);
    final int numOfPlayers = 3;
    private Player testPlayer1;
    private Match match = new Match(1, numOfPlayers);
    private Worker testWorkerProm;
    private int[] location = new int[]{0,0};
    private int[] build = new int[]{1,1};

    /**
     * Setup a match with three players
     */
    @Before
    public void setUp() {
        testPlayer1 = new PlayerPrometheus("player1", 1, cardProm, match, Worker.Color.RED);
        Player player2 = new Player("player2", 2, match);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, player2, player3};
        testWorkerProm = testPlayer1.workers[0];
        testPlayer1.setSelectedWorker(testWorkerProm);
        match.nextPlayer(); //round p1;
    }

    /**
     * Make two build testing Prometheus super power
     */
    @Test
    public void buildTwoTimeTest() {
        Assert.assertTrue("Error First Locate", testPlayer1.selectedWorkerMove(location[0],location[1]));
        location[0] = 1;
        location[1] = 0;
        Assert.assertTrue("Error Build Before Move", testPlayer1.selectedWorkerBuild(build[0],build[1]));
        Assert.assertTrue("Error First Locate", testPlayer1.selectedWorkerMove(location[0],location[1]));
        Assert.assertTrue("Error Build After Move", testPlayer1.selectedWorkerBuild(build[0],build[1]));
        build[0] = 1;
        build[1] = 1;
    }

    /**
     * Test selectedWorkerMove does not allow Prometheus to go up after he choose to build two times
     */
    @Test
    public void tryGoUpTest() {
        Assert.assertTrue("Error First Locate", testPlayer1.selectedWorkerMove(location[0],location[1]));
        location[0] = 1;
        location[1] = 1;
        Assert.assertTrue("Error Build Before Move", testPlayer1.selectedWorkerBuild(build[0],build[1]));
        Assert.assertFalse("Error First Locate", testPlayer1.selectedWorkerMove(location[0],location[1]));
    }

    /**
     * Test override manage turn allow Prometheus to make a standard round.
     * It is not needed to test if he can move up because of the called method to check move is the same of the previous test
     */
    @Test
    public void testManageTurnBuildOneTime() {
        testPlayer1.setSelectedWorker(testPlayer1.workers[0]);
        testPlayer1.selectedWorkerMove(0,0);
        Assert.assertEquals("Errore Selezione worker", testPlayer1.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.prometheusTurnMessageAskBuildBefore);
        Assert.assertEquals("Errore Movimento worker", testPlayer1.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Errore Costruzione Singola worker", testPlayer1.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.turnMessageTurnEnd);
    }

    /**
     * Test override manage turn allow Prometheus to build two times
     */
    @Test
    public void testManageTurnBuildTwoTimeSuccess() {
        match.nextPlayer();
        match.nextPlayer();
        testPlayer1.setSelectedWorker(testPlayer1.workers[0]);
        testPlayer1.selectedWorkerMove(0,0);
        Assert.assertEquals("Errore Selezione worker", testPlayer1.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.prometheusTurnMessageAskBuildBefore);
        Assert.assertEquals("Errore Prima Costruzione worker", testPlayer1.manageTurn(1,1, Worker.Gender.Male, GameMessage.turnMessageBUILDBEFORE).getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Errore Movimento worker", testPlayer1.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Errore Prima Costruzione worker", testPlayer1.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.turnMessageTurnEnd);
    }

}
