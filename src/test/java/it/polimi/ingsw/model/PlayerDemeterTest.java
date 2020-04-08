package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerDemeterTest {
    private  static Match match;
    private static PlayerDemeter demeter;
    private static Player player2Generic;

    @Before
    public void setUp() throws Exception {
        match=new Match(1,2);
        demeter=new PlayerDemeter("leo",2,new Card(4),match, Worker.Color.BLACK);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.WHITE);
        match.players[1] = demeter;
        match.players[0] = player2Generic;
    }

    @AfterClass
    public static void afterClass() throws Exception {
        match=null;
        demeter=null;
        player2Generic=null;
    }

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
        Assert.assertTrue(demeter.selectedWorkerBuild(2,3));             //costruisce la prima volta con la super
        Assert.assertFalse(demeter.selectedWorkerBuild(2,3));            //prova a costruire due volte sulla stessa casella
        Assert.assertTrue(demeter.selectedWorkerBuild(1,3));             //costruisce la seconda volta correttamente
        //demeter.resetTurn(); //ovviamente qua non era necessario, perchè svolge stesse operazioni fatte per giungere qua (era solo un controllo in più)

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(0,1));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(0,0));

        Assert.assertTrue(demeter.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(demeter.selectedWorkerMove(3,0));
        Assert.assertTrue(demeter.selectedWorkerBuild(3,1));             //costruisce una sola volta perciò servirà fare la resetTurn esplicita
        demeter.resetTurn();

        Assert.assertTrue(demeter.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(demeter.selectedWorkerMove(4,0));
        Assert.assertTrue(demeter.selectedWorkerBuild(4,1));                //costruisce due volte verificando che la resetTurn abbia funzionanto bene
        Assert.assertTrue(demeter.selectedWorkerBuild(3,0));

    }

    @Test
    public void testManageTurnBuildOneTime() {
        match.nextPlayer();
        match.nextPlayer();
        demeter.setSelectedWorker(demeter.workers[0]);
        demeter.selectedWorkerMove(0,0);
        Assert.assertEquals("Errore Selezione worker", demeter.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Errore Movimento worker", demeter.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Errore Costruzione Singola worker", demeter.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Errore Costruzione Termina Turno", demeter.manageTurn(1,0, Worker.Gender.Male, GameMessage.turnMessageNO).getNextInstruction(),  GameMessage.turnMessageTurnEnd);
    }
    @Test
    public void testManageTurnBuildTwoTimeSuccess() {
        match.nextPlayer();
        match.nextPlayer();
        demeter.setSelectedWorker(demeter.workers[0]);
        demeter.selectedWorkerMove(0,0);
        Assert.assertEquals("Errore Selezione worker", demeter.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Errore Movimento worker", demeter.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Errore Costruzione Singola worker", demeter.manageTurn(2,2, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkBuild + GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Errore Costruzione Termina Turno", demeter.manageTurn(0,0, Worker.Gender.Male, GameMessage.turnMessageBUILDTWOTIMES).getNextInstruction(), GameMessage.turnMessageOkBuild+GameMessage.turnMessageTurnEnd);
    }
    @Test
    public void testManageTurnBuildTwoTimeFail() {
        match.nextPlayer();
        match.nextPlayer();
        demeter.setSelectedWorker(demeter.workers[0]);
        demeter.selectedWorkerMove(0,0);
        Assert.assertEquals("Errore Selezione worker", demeter.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection+GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Errore Movimento worker", demeter.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement +  GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Errore Costruzione Singola worker", demeter.manageTurn(2,2, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkBuild + GameMessage.hestiaDemeterTurnMessageAskTwoBuild);
        Assert.assertEquals("Errore Costruzione Termina Turno", demeter.manageTurn(2,2, Worker.Gender.Male, GameMessage.turnMessageBUILDTWOTIMES).getNextInstruction(), GameMessage.hestiaDemeterTurnMessageFailOptionalBuildWNewCell);
        Assert.assertEquals("Errore Costruzione Termina Turno", demeter.manageTurn(2,2, Worker.Gender.Male, GameMessage.turnMessageNO).getNextInstruction(), GameMessage.turnMessageTurnEnd);
    }
}
