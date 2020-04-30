package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerHestiaTest {
    private  static Match match;
    private static PlayerHestia hestia;
    private static Player player2Generic;

    @Before
    public void setUp() throws Exception {
        match=new Match(1,2);
        hestia=new PlayerHestia("leo",2,new Card(12),match, Worker.Color.GREEN);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.RED);
        match.players[1] = hestia;
        match.players[0] = player2Generic;

    }

    @AfterClass
    public static void afterClass() throws Exception {
        match=null;
        hestia=null;
        player2Generic=null;

    }


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
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));             //costruisce la prima volta con la super
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));            //costruisce la seconda volta sulla stessa casella

        Assert.assertTrue(hestia.selectedWorkerBuild(4,0));             //costruisce la prima volta con la super su una casella permimetrale
        //hestia.resetTurn();
        //Assert.assertTrue(hestia.selectedWorkerBuild(4,0));          //per verificare che resetTurn funzioni
        Assert.assertFalse(hestia.selectedWorkerBuild(4,0));            //prova a costruire la seconda volta sulla stessa casella ma è perimetrale
        hestia.resetTurn();

        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));     //costruisce 2 volte senza caselle perimetrali di mezzo
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));

        Assert.assertFalse(hestia.selectedWorkerBuild(3,2));    //verifico che non possa costruire troppo lontano sia alla prima costruzione che alla seconda
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
