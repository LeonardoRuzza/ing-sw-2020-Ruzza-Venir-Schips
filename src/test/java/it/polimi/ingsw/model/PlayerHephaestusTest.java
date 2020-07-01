package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerHephaestusTest {

    private  static Match match;
    private static PlayerHephaestus hephaestus;
    private static Player player2Generic;

    /**
     * Set up a match with 2 players where one of these is a PlayerHephaestus.
     */
    @Before
    public void setUp() {
        match=new Match(1,2);
        hephaestus =new PlayerHephaestus("leo",2,new Card(5),match, Worker.Color.GREEN);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.RED);
        match.players[1] = hephaestus;
        match.players[0] = player2Generic;
    }

    /**
     * Make null some parameters of the test class.
     */
    @AfterClass
    public static void afterClass() {
        match=null;
        hephaestus =null;
        player2Generic=null;
    }

    /**
     * Test the override methods selectedWorkerBuild and resetTurn. Tested cases are the following:
     * build one time;
     * trying to build two times but on different cells (not possible);
     * build two times on the same cell (possible);
     * trying to build two times but the second is a couple (not possible);
     * trying the precedent case after a resetTurn;
     * trying to build on a not reachable space.
     */
    @Test
    public void testSelectedWorkerBuildAndResetTurn() {
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,1));

        Assert.assertTrue(hephaestus.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(hephaestus.selectedWorkerMove(1,3));
        Assert.assertTrue(hephaestus.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(hephaestus.selectedWorkerMove(2,0));

        Assert.assertTrue(hephaestus.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(hephaestus.selectedWorkerBuild(2,1));                //building first time
        Assert.assertFalse(hephaestus.selectedWorkerBuild(3,0));                //trying to build a second time but on a different cell (ko)
        Assert.assertTrue(hephaestus.selectedWorkerBuild(2,1));                 //build the second time on the same cell (ok)

        Assert.assertTrue(hephaestus.selectedWorkerBuild(2,1));
        Assert.assertFalse(hephaestus.selectedWorkerBuild(2,1));              //trying to build second time but a dorse (ko)

        hephaestus.resetTurn();   //resetTurn and now can build the dorse
        Assert.assertTrue(hephaestus.selectedWorkerBuild(2,1));

        hephaestus.resetTurn();
        Assert.assertTrue(hephaestus.selectedWorkerBuild(3,0));   //after resetTurn build on a different space
        hephaestus.resetTurn();
        Assert.assertFalse(hephaestus.selectedWorkerBuild(0,0));   //trying to build on a not reachable space
        Assert.assertTrue(hephaestus.selectedWorkerBuild(1,0));    //building the first block
        Assert.assertTrue(hephaestus.selectedWorkerBuild(1,0));    //building the second block
    }

    /**
     * Test the override method manageTurn in case of single building.
     */
    @Test
    public void testManageTurnBuildOneTime() {
        match.nextPlayer();
        match.nextPlayer();
        hephaestus.setSelectedWorker(hephaestus.workers[0]);
        hephaestus.selectedWorkerMove(0,0);
        Assert.assertEquals("Error selection worker", hephaestus.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection+GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement worker", hephaestus.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkMovement+GameMessage.hephaesthusTurnMessageAskBuild);
        Assert.assertEquals("Error single building worker", hephaestus.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkBuild + GameMessage.turnMessageTurnEnd);
    }

    /**
     * Test the override method manageTurn in case of building two times with success.
     */
    @Test
    public void testManageTurnBuildTwoTimeSuccess() {
        match.nextPlayer();
        match.nextPlayer();
        hephaestus.setSelectedWorker(hephaestus.workers[0]);
        hephaestus.selectedWorkerMove(0,0);
        Assert.assertEquals("Error selection worker", hephaestus.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement worker", hephaestus.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkMovement+GameMessage.hephaesthusTurnMessageAskBuild);
        Assert.assertEquals("Error double building worker", hephaestus.manageTurn(1,0, Worker.Gender.Male, GameMessage.turnMessageBUILDTWOTIMES).getNextInstruction(), GameMessage.turnMessageOkBuild + GameMessage.turnMessageTurnEnd);
    }

    /**
     * Test the override method manageTurn in case of building two times failing.
     */
    @Test
    public void testManageTurnBuildTwoTimeFail() {
        match.nextPlayer();
        match.nextPlayer();
        hephaestus.setSelectedWorker(hephaestus.workers[0]);
        hephaestus.selectedWorkerMove(0,0);
        match.forceBuild(1,0, hephaestus.workers[0]);
        match.forceBuild(1,0, hephaestus.workers[0]);
        Assert.assertEquals("Error selection worker", hephaestus.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement worker", hephaestus.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkMovement+GameMessage.hephaesthusTurnMessageAskBuild);
        Assert.assertEquals("Error double building worker", hephaestus.manageTurn(1,0, Worker.Gender.Male, GameMessage.turnMessageBUILDTWOTIMES).getNextInstruction(), GameMessage.hephaesthusTurnMessageFailOptionalBuildWEnd);
    }
}
