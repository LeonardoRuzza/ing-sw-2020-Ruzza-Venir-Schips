package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerHestiaTest {
    private  static Match match;
    private static PlayerHestia hestia;
    private static Player player2Generic;

    /**
     * Set up a match with 2 players where one of these is a PlayerHestia.
     */
    @Before
    public void setUp() {
        match=new Match(1,2);
        hestia=new PlayerHestia("leo",2,new Card(12),match, Worker.Color.GREEN);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.RED);
        match.players[1] = hestia;
        match.players[0] = player2Generic;

    }

    /**
     * Make null some properties of this test class.
     */
    @AfterClass
    public static void afterClass() {
        match=null;
        hestia=null;
        player2Generic=null;

    }

    /**
     * Test the override methods selectedWorkerBuild and resetTurn in these cases:
     * build one time;
     * trying to build the second time on the same space (possible);
     * trying to build with and without perimeter space one/two times;
     * trying to build too far(not possible).
     */
    @Test
    public void testSelectedWorkerBuildAndResetTurn() {
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,1));

        Assert.assertTrue(hestia.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(hestia.selectedWorkerMove(1,3));
        Assert.assertTrue(hestia.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(hestia.selectedWorkerMove(2,0));

        Assert.assertTrue(hestia.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(hestia.selectedWorkerMove(3,0));
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));             //building first time with super.
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));            //building the second time on the same space

        Assert.assertTrue(hestia.selectedWorkerBuild(4,0));             //build the first time on a perimeter cell
        //hestia.resetTurn();
        //Assert.assertTrue(hestia.selectedWorkerBuild(4,0));          //to check correct working of resetTurn
        Assert.assertFalse(hestia.selectedWorkerBuild(4,0));            //trying to build the second time o a perimeter space (not possible)
        hestia.resetTurn();

        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));     //building two times correctly
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));

        Assert.assertFalse(hestia.selectedWorkerBuild(3,2));    //trying to build on not reachable space (not possible)
        Assert.assertTrue(hestia.selectedWorkerBuild(2,1));
        Assert.assertFalse(hestia.selectedWorkerBuild(3,2));

    }

    @Test
    public void testManageTurnBuildOneTime() {
        match.nextPlayer();
        match.nextPlayer();
        hestia.setSelectedWorker(hestia.workers[0]);
        hestia.selectedWorkerMove(0,0);
        Assert.assertEquals("Errore Selezione worker", hestia.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Errore Movimento worker", hestia.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Errore Costruzione Singola worker", hestia.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Errore Costruzione Termina Turno", hestia.manageTurn(1,0, Worker.Gender.Male, GameMessage.turnMessageNO).getNextInstruction(),  GameMessage.turnMessageTurnEnd);
    }
    @Test
    public void testManageTurnBuildTwoTimeSuccess() {
        match.nextPlayer();
        match.nextPlayer();
        hestia.setSelectedWorker(hestia.workers[0]);
        hestia.selectedWorkerMove(0,0);
        Assert.assertEquals("Errore Selezione worker", hestia.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Errore Movimento worker", hestia.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Errore Costruzione Singola worker", hestia.manageTurn(2,2, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkBuild + GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Errore Costruzione Termina Turno", hestia.manageTurn(2,2, Worker.Gender.Male, GameMessage.turnMessageBUILDTWOTIMES).getNextInstruction(), GameMessage.turnMessageOkBuild+GameMessage.turnMessageTurnEnd);
    }
    @Test
    public void testManageTurnBuildTwoTimeFail() {
        match.nextPlayer();
        match.nextPlayer();
        hestia.setSelectedWorker(hestia.workers[0]);
        hestia.selectedWorkerMove(0,0);
        Assert.assertEquals("Errore Selezione worker", hestia.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Errore Movimento worker", hestia.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Errore Costruzione Singola worker", hestia.manageTurn(2,2, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkBuild + GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Errore Costruzione Termina Turno", hestia.manageTurn(0,1, Worker.Gender.Male, GameMessage.turnMessageBUILDTWOTIMES).getNextInstruction(), GameMessage.hestiaDemeterTurnMessageFailOptionalBuildWNewCell);
        Assert.assertEquals("Errore Costruzione Termina Turno", hestia.manageTurn(-1,-1, Worker.Gender.Male, GameMessage.turnMessageNO).getNextInstruction(), GameMessage.turnMessageTurnEnd);
    }
}
