package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerAresTest {
    private  static Match match;
    private static PlayerAres ares;
    private static Player player2Generic;

    /**
     * Set up a match with 2 players where one of these is of type PlayerAres.
     */
    @Before
    public void setUp() {
        match=new Match(1,2);
        ares=new PlayerAres("leo",2,new Card(1),match, Worker.Color.RED);
        player2Generic=new Player("edo",1,new Card(2),match,Worker.Color.GREEN);
        match.players[1] = ares;
        match.players[0] = player2Generic;

    }

    /**
     * Test the method notSelectedWorkerRemoveBlock of PlayerAres checking the correct implementation of his power in these situations:
     * removing correctly a block;
     * try to remove a dorse (not possible);
     * try to remove with the not the not selectedWorker (not possible);
     */
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

        Assert.assertTrue(ares.notSelectedWorkerRemoveBlock(2,3));              //possible removing

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,3));
        Assert.assertNull(player2Generic.selectedWorker.getCell().getBlock());        //verifying the remove
        Assert.assertTrue(player2Generic.selectedWorkerBuild(1,4));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(1,4));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(1,4));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(1,4));

        Assert.assertTrue(ares.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(ares.selectedWorkerMove(0,1));
        Assert.assertTrue(ares.selectedWorkerBuild(0,0));

        Assert.assertFalse(ares.notSelectedWorkerRemoveBlock(1,4));             //try to remove a dorse (not possible)
        Assert.assertFalse(ares.notSelectedWorkerRemoveBlock(0,0));             //trying to remove near the selectedWorker (not possible)
    }

    /**
     * Test the override of manageTurn and the correct removing phase added with a possible case.
     */
    @Test
    public void testManageTurnRemoveWithSuccess() {
        match.nextPlayer();
        ares.setSelectedWorker(ares.workers[0]);
        ares.selectedWorkerMove(0,0);
        ares.setSelectedWorker(ares.workers[1]);
        ares.selectedWorkerMove(2,0);
        ares.setSelectedWorker(ares.workers[0]);
        Assert.assertEquals("Error selection", ares.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement", ares.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement + GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Error building (1)", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.aresTurnMessageAskRemoveBlok);
        Assert.assertEquals("Error building (2)", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.aresTurnMessageSuccessRemoveBlokWEnd);
    }

    /**
     * Test the override of manageTurn with a not possible case of removing block.
     */
    @Test
    public void testManageTurnNotRemoveDorse() {
        match.nextPlayer();
        ares.setSelectedWorker(ares.workers[0]);
        ares.selectedWorkerMove(0,0);
        ares.setSelectedWorker(ares.workers[1]);
        ares.selectedWorkerMove(2,0);
        ares.setSelectedWorker(ares.workers[0]);
        match.forceBuild(1,0,ares.selectedWorker);
        match.forceBuild(1,0,ares.selectedWorker);
        match.forceBuild(1,0,ares.selectedWorker);
        Assert.assertEquals("Error selection", ares.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement", ares.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement + GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Error building", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.aresTurnMessageAskRemoveBlok);
        Assert.assertEquals("Error removing", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.aresTurnMessageFailRemoveBlokWNewCell);
        Assert.assertEquals("Error end turn", ares.manageTurn(1,0, Worker.Gender.Male, GameMessage.turnMessageNO).getNextInstruction(),  GameMessage.turnMessageTurnEnd);
    }

    /**
     * Test the override method manageTurn to check the correct working in case of not want to remove a block.
     */
    @Test
    public void testManageTurnNotWantRemove() {
        match.nextPlayer();
        ares.setSelectedWorker(ares.workers[0]);
        ares.selectedWorkerMove(0,0);
        ares.setSelectedWorker(ares.workers[1]);
        ares.selectedWorkerMove(2,0);
        ares.setSelectedWorker(ares.workers[0]);
        match.forceBuild(1,0,ares.selectedWorker);
        match.forceBuild(1,0,ares.selectedWorker);
        match.forceBuild(1,0,ares.selectedWorker);
        Assert.assertEquals("Error selection", ares.manageTurn(0,0, Worker.Gender.Male, "").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals("Error movement", ares.manageTurn(1,1, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkMovement + GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals("Error building", ares.manageTurn(1,0, Worker.Gender.Male, "").getNextInstruction(),  GameMessage.turnMessageOkBuild +  GameMessage.aresTurnMessageAskRemoveBlok);
        Assert.assertEquals("Error end turn", ares.manageTurn(1,0, Worker.Gender.Male, GameMessage.turnMessageNO).getNextInstruction(),  GameMessage.turnMessageTurnEnd);
    }

}
