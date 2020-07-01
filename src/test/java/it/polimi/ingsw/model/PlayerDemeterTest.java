package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerDemeterTest {
    private  static Match match;
    private static PlayerDemeter demeter;
    private static Player player2Generic;

    /**
     * Set up a match with 2 player where one of these is a PlayerDemeter.
     */
    @Before
    public void setUp() {
        match=new Match(1,2);
        demeter=new PlayerDemeter("leo",2,new Card(4),match, Worker.Color.RED);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.GREEN);
        match.players[1] = demeter;
        match.players[0] = player2Generic;
    }

    /**
     * Make null the precedent assignments.
     */
    @AfterClass
    public static void afterClass() {
        match=null;
        demeter=null;
        player2Generic=null;
    }

    /**
     * Test the correct working of override methods selectedWorkerBuild and  resetTurn. These are the various tested cases:
     * a normal building;
     * trying to build two times but on the same space;
     * trying to build two times correctly;
     * trying to build only one time and use resetTurn checking the correct working in the next phase.
     */
    @Test
    public void testSelectedWorkerBuildAndResetTurn() {

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,1));

        Assert.assertTrue(demeter.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(demeter.selectedWorkerMove(1,3));
        Assert.assertTrue(demeter.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(demeter.selectedWorkerMove(2,0));

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,2));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,3));

        Assert.assertTrue(demeter.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(demeter.selectedWorkerMove(1,4));
        Assert.assertTrue(demeter.selectedWorkerBuild(2,3));             //build first time with super.
        Assert.assertFalse(demeter.selectedWorkerBuild(2,3));            //trying building on same space
        Assert.assertTrue(demeter.selectedWorkerBuild(1,3));             //building the second time correctly
        //demeter.resetTurn(); //not necessary

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(0,1));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(0,0));

        Assert.assertTrue(demeter.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(demeter.selectedWorkerMove(3,0));
        Assert.assertTrue(demeter.selectedWorkerBuild(3,1));             //building only one time
        demeter.resetTurn();                                                 // explicit resetTurn necessary

        Assert.assertTrue(demeter.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(demeter.selectedWorkerMove(4,0));
        Assert.assertTrue(demeter.selectedWorkerBuild(4,1));                //building two times
        Assert.assertTrue(demeter.selectedWorkerBuild(3,0));

    }

    /**
     * Test override method manageTurn in case of single building.
     */
    @Test
    public void testManageTurnBuildOneTime() {
        match.nextPlayer();
        match.nextPlayer();
        demeter.setSelectedWorker(demeter.workers[0]);
        demeter.selectedWorkerMove(0,0);
        Assert.assertEquals("Error selection worker", demeter.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement worker", demeter.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Error single building worker", demeter.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Error building end turn", demeter.manageTurn(1,0, Worker.Gender.Male, GameMessage.turnMessageNO).getNextInstruction(),  GameMessage.turnMessageTurnEnd);
    }

    /**
     * Test override method manageTurn building two times.
     */
    @Test
    public void testManageTurnBuildTwoTimeSuccess() {
        match.nextPlayer();
        match.nextPlayer();
        demeter.setSelectedWorker(demeter.workers[0]);
        demeter.selectedWorkerMove(0,0);
        Assert.assertEquals("Error selection worker", demeter.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement worker", demeter.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Error single building worker", demeter.manageTurn(2,2, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkBuild + GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Error building end turn", demeter.manageTurn(0,0, Worker.Gender.Male, GameMessage.turnMessageBUILDTWOTIMES).getNextInstruction(), GameMessage.turnMessageOkBuild+GameMessage.turnMessageTurnEnd);
    }

    /**
     * Test override method manageTurn failing to build two times.
     */
    @Test
    public void testManageTurnBuildTwoTimeFail() {
        match.nextPlayer();
        match.nextPlayer();
        demeter.setSelectedWorker(demeter.workers[0]);
        demeter.selectedWorkerMove(0,0);
        Assert.assertEquals("Error selection worker", demeter.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection+GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement worker", demeter.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Error single building worker", demeter.manageTurn(2,2, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkBuild + GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Error building end turn (1)", demeter.manageTurn(2,2, Worker.Gender.Male, GameMessage.turnMessageBUILDTWOTIMES).getNextInstruction(), GameMessage.hestiaDemeterTurnMessageFailOptionalBuildWNewCell);
        Assert.assertEquals("Error building end turn (2)", demeter.manageTurn(2,2, Worker.Gender.Male, GameMessage.turnMessageNO).getNextInstruction(), GameMessage.turnMessageTurnEnd);
    }
}
