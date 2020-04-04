package it.polimi.ingsw.model;

import org.junit.*;

public class PlayerAresTest {
    private  static Match match;
    private static PlayerAres ares;
    private static Player player2Generic;


    @Before
    public void setUp() throws Exception {
        match=new Match(1,2);
        ares=new PlayerAres("leo",2,new Card(1),match, Worker.Color.RED);
        player2Generic=new Player("edo",1,new Card(2),match,Worker.Color.WHITE);
        match.players[1] = ares;
        match.players[0] = player2Generic;

    }

    @AfterClass
    public static void afterClass() throws Exception {
    }

    @Test
    public void testNotSelectedWorkerRemoveBlock() {
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,1));

        Assert.assertTrue(ares.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(ares.selectedWorkerMove(1,3));
        Assert.assertTrue(ares.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(ares.selectedWorkerMove(2,0));

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,2));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,3));

        Assert.assertTrue(ares.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(ares.selectedWorkerMove(1,0));
        Assert.assertTrue(ares.selectedWorkerBuild(0,1));

        Assert.assertTrue(ares.notSelectedWorkerRemoveBlock(2,3));              //effettuo una rimozione possibile

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,3));
        Assert.assertNull(player2Generic.selectedWorker.getCell().getBlock());        //verifico che il blocco sia stato rimosso effettivamente
        Assert.assertTrue(player2Generic.selectedWorkerBuild(1,4));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(1,4));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(1,4));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(1,4));

        Assert.assertTrue(ares.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(ares.selectedWorkerMove(0,1));
        Assert.assertTrue(ares.selectedWorkerBuild(0,0));

        Assert.assertFalse(ares.notSelectedWorkerRemoveBlock(1,4));             //verifico che non rimuova una cupola
        Assert.assertFalse(ares.notSelectedWorkerRemoveBlock(0,0));             //verifico non faccia rimuovere vicino al worker con cui si è mosso
    }

    @Test
    public void testManageTurnRemoveWithSuccess() {
        match.nextPlayer();
        match.nextPlayer();
        ares.setSelectedWorker(ares.workers[0]);
        ares.selectedWorkerMove(0,0);
        ares.setSelectedWorker(ares.workers[1]);
        ares.selectedWorkerMove(2,0);
        ares.setSelectedWorker(ares.workers[0]);
        Assert.assertEquals("Errore Selezione worker", ares.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), Player.turnMessageOkWorkerSelection);
        Assert.assertEquals("Errore Movimento worker", ares.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  Player.turnMessageOkMovement);
        Assert.assertEquals("Errore Costruzione Singola worker", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  Player.turnMessageOkBuild +  Player.aresTurnMessageAskRemoveBlok);
        Assert.assertEquals("Errore Costruzione Termina Turno", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  Player.aresTurnMessageSuccessRemoveBlokWEnd);
    }

    @Test
    public void testManageTurnNotRemoveDorse() {
        match.nextPlayer();
        match.nextPlayer();
        ares.setSelectedWorker(ares.workers[0]);
        ares.selectedWorkerMove(0,0);
        ares.setSelectedWorker(ares.workers[1]);
        ares.selectedWorkerMove(2,0);
        ares.setSelectedWorker(ares.workers[0]);
        match.forceBuild(1,0,ares.selectedWorker);
        match.forceBuild(1,0,ares.selectedWorker);
        match.forceBuild(1,0,ares.selectedWorker);
        Assert.assertEquals("Errore Selezione worker", ares.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), Player.turnMessageOkWorkerSelection);
        Assert.assertEquals("Errore Movimento worker", ares.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  Player.turnMessageOkMovement);
        Assert.assertEquals("Errore Costruzione Singola worker", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  Player.turnMessageOkBuild +  Player.aresTurnMessageAskRemoveBlok);
        Assert.assertEquals("Errore Rimuovi Blocco", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  Player.aresTurnMessageFailRemoveBlokWNewCell);
        Assert.assertEquals("Errore Costruzione Termina Turno", ares.manageTurn(1,0, Worker.Gender.Male, Player.turnMessageNO).getNextInstruction(),  Player.turnMessageTurnEnd);
        match.getBoard().draw(ares);
    }

    @Test
    public void testManageTurnNotWantRemove() {
        match.nextPlayer();
        match.nextPlayer();
        ares.setSelectedWorker(ares.workers[0]);
        ares.selectedWorkerMove(0,0);
        ares.setSelectedWorker(ares.workers[1]);
        ares.selectedWorkerMove(2,0);
        ares.setSelectedWorker(ares.workers[0]);
        match.forceBuild(1,0,ares.selectedWorker);
        match.forceBuild(1,0,ares.selectedWorker);
        match.forceBuild(1,0,ares.selectedWorker);
        Assert.assertEquals("Errore Selezione worker", ares.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), Player.turnMessageOkWorkerSelection);
        Assert.assertEquals("Errore Movimento worker", ares.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  Player.turnMessageOkMovement);
        Assert.assertEquals("Errore Costruzione Singola worker", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  Player.turnMessageOkBuild +  Player.aresTurnMessageAskRemoveBlok);
        Assert.assertEquals("Errore Fine turno", ares.manageTurn(1,0, Worker.Gender.Male, Player.turnMessageNO).getNextInstruction(),  Player.turnMessageTurnEnd);
        match.getBoard().draw(ares);
    }

}
