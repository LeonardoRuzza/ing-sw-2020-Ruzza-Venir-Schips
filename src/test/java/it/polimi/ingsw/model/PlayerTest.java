package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerTest {

    private static Player player1,player2, player3;
    private static Match match;
    private static Card card1, card2, card3;

    /**
     * Set up a match with 3 players.
     */
    @Before
    public void setUp() {
        match=new Match(1,3);
        player1 = new Player("leo",1, card1 = new Card(1), match, Worker.Color.RED);
        player2 = new Player("valerio", 2, card2  = new Card(5), match, Worker.Color.GREEN);
        player3 = new Player("edo", 3, card3  = new Card(3), match, Worker.Color.YELLOW);
        match.players[0] = player1;
        match.players[1] = player2;
        match.players[2] = player3;
    }

    /**
     * Test the build and getters.
     */
    @Test
    public void testBuildersAndSetterAndGetter() {
        Assert.assertEquals("Error1", "leo", player1.getNickname());
        Assert.assertEquals("Error2",1,player1.getNumber());
        player1.setNickname("Edoardo");
        player1.setNumber(2);
        Assert.assertEquals("Error3", "Edoardo", player1.getNickname());
        Assert.assertEquals("Error4",2 ,player1.getNumber());
        Assert.assertEquals("Error5", "valerio", player2.getNickname());
        Assert.assertEquals("Error6",2 ,player2.getNumber());
        Assert.assertEquals("Error7",card2,player2.card);
        Assert.assertEquals("Error8",match,player2.match);
        Assert.assertEquals("Error9",Worker.Color.GREEN,player2.workers[0].getColor());
        Assert.assertEquals("Error10",Worker.Color.GREEN,player2.workers[1].getColor());
        Assert.assertEquals("Error11",Worker.Gender.Male,player2.workers[0].getGender());
        Assert.assertEquals("Error12",Worker.Gender.Female,player2.workers[1].getGender());
        player2.setNickname("Tester");
        player2.setNumber(3);
        Assert.assertEquals("Error13", "Tester", player2.getNickname());
        Assert.assertEquals("Error14",3 ,player2.getNumber());
    }

    /**
     * Test the method setSelectedWorker (normal working).
     */
    @Test
    public void testSetSelectedWorker() {
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
    }

    /**
     * Test the method selectedWorkerMove (normal movement).
     */
    @Test
    public void testSelectedWorkerMove() {
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(1, 2));
        Assert.assertTrue(player1.selectedWorkerMove(2, 2));
        Assert.assertFalse(player1.selectedWorkerMove(4, 4));
    }

    /**
     * Test the method selectedWorkerBuild (normal working).
     */
    @Test
    public void testSelectedWorkerBuild() {
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(1, 2));
        Assert.assertTrue(player1.selectedWorkerMove(2, 2));
        Assert.assertTrue(player1.selectedWorkerBuild(1, 2));  // Build B1

        Assert.assertTrue(player1.selectedWorkerMove(1, 2));
    }

    /**
     * Test the initial locating and then a "match-like" sequence of movement and build of the players.
     */
    @Test
    public void testLocate() {
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(0, 0));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(0, 1));

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2.selectedWorkerMove(4, 4));
        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Female));
        //Assert.assertFalse(player2.selectedWorkerMove(0, 0));
        Assert.assertTrue(player2.selectedWorkerMove(4, 3));

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player3.selectedWorkerMove(2, 2));
        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player3.selectedWorkerMove(2, 3));


   // Turn 1 -------------------------------------------------------------
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(1, 1));
        Assert.assertTrue(player1.selectedWorkerBuild(1, 2));  // Build B1

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2.selectedWorkerMove(3, 4));
        Assert.assertTrue(player2.selectedWorkerBuild(3, 3));  // Build B1


        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player3.selectedWorkerMove(1, 2));
        Assert.assertTrue(player3.selectedWorkerBuild(0, 2));  // Build B1

// Turn 2 ------------------------------------------------------------------------
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1, 0));
        Assert.assertTrue(player1.selectedWorkerBuild(2, 1));  // Build B1

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2.selectedWorkerMove(4, 2));
        Assert.assertTrue(player2.selectedWorkerBuild(3, 2));  // Build B1

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player3.selectedWorkerMove(2, 1));
        Assert.assertTrue(player3.selectedWorkerBuild(3, 2));  // Build B2

 // Turn 3 ------------------------------------------------------------------------
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(0, 2));
        Assert.assertTrue(player1.selectedWorkerBuild(1, 3));  // Build B1

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2.selectedWorkerMove(3, 3));
        Assert.assertTrue(player2.selectedWorkerBuild(2, 3));  // Build B1

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player3.selectedWorkerMove(3, 2));
        Assert.assertTrue(player3.selectedWorkerBuild(3, 1));  // Build B1

 // Turn 4 ------------------------------------------------------------------------
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(2, 0));
        Assert.assertTrue(player1.selectedWorkerBuild(3, 1));  // Build B2

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2.selectedWorkerMove(4, 1));
        Assert.assertTrue(player2.selectedWorkerBuild(3, 1));  // Build B3

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player3.selectedWorkerMove(3, 1));
    }

    /**
     * Test the method checkAllLimitSelection introducing ad hoc a PlayerHypnus. Various case of possible and not possible selection are tested.
     */
    @Test
    public void testCheckAllLimitSelection(){
        player1= new PlayerHypnus("leo",1, card1 = new Card(13), match, Worker.Color.RED);
        match.players[0] = player1;

        player1.setSelectedWorker(Worker.Gender.Male);
        player1.selectedWorkerMove(1,1);
        player1.setSelectedWorker(Worker.Gender.Female);
        player1.selectedWorkerMove(2,2);

        player2.setSelectedWorker(Worker.Gender.Male);
        player2.selectedWorkerMove(3,3);
        player2.setSelectedWorker(Worker.Gender.Female);
        player2.selectedWorkerMove(4,4);

        player3.setSelectedWorker(Worker.Gender.Male);
        player3.selectedWorkerMove(4,0);
        player3.setSelectedWorker(Worker.Gender.Female);
        player3.selectedWorkerMove(3,0);

        player1.match.forceBuild(3,1,player1.selectedWorker);
        player1.match.forceBuild(2,1,player1.selectedWorker);
        player1.match.forceBuild(2,1,player1.selectedWorker);

        player3.setSelectedWorker(Worker.Gender.Male);
        player3.match.forceMove(3,1,player3.selectedWorker);
        Assert.assertFalse(player3.setSelectedWorker(Worker.Gender.Male));

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Female));
        player3.match.forceBuild(2,0,player3.selectedWorker);
        player3.match.forceMove(2,0,player3.selectedWorker);

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Male));
        player3.match.forceMove(2,1,player3.selectedWorker);
        Assert.assertFalse(player3.setSelectedWorker(Worker.Gender.Male));

        player1.match.forceBuild(1,2,player1.selectedWorker);
        player1.match.forceMove(1,2,player1.selectedWorker);
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
    }

    /**
     * Test the method manageStateMove checking the correct exchange of information (returned messages). The principle cases are:
     * a correct movement;
     * an impossible movement;
     * a movement which implicate win.
     */
    @Test
    public void testManageStateMove(){
        player1.setSelectedWorker(Worker.Gender.Male);
        player1.selectedWorkerMove(1,1);
        player1.setSelectedWorker(Worker.Gender.Female);
        player1.selectedWorkerMove(2,2);

        player2.setSelectedWorker(Worker.Gender.Male);
        player2.selectedWorkerMove(3,3);
        player2.setSelectedWorker(Worker.Gender.Female);
        player2.selectedWorkerMove(4,4);

        player3.setSelectedWorker(Worker.Gender.Male);
        player3.selectedWorkerMove(4,0);
        player3.setSelectedWorker(Worker.Gender.Female);
        player3.selectedWorkerMove(3,0);

        match.nextPlayer();
        player1.setSelectedWorker(Worker.Gender.Male);
        Assert.assertEquals("error1", GameMessage.turnMessageOkMovement, player1.manageStateMove(1,2).getNextInstruction());
        Assert.assertEquals("error2", GameMessage.turnMessageFailMovementChangeDestination, player1.manageStateMove(1,2).getNextInstruction());
        player1.match.forceBuild(1,1,player1.selectedWorker);
        player1.match.forceBuild(1,1,player1.selectedWorker);
        player1.match.forceBuild(1,1,player1.selectedWorker);
        player1.match.forceBuild(0,2,player1.selectedWorker);
        player1.match.forceBuild(0,1,player1.selectedWorker);
        player1.match.forceBuild(0,1,player1.selectedWorker);
        player1.match.forceMove(0,2,player1.selectedWorker);
        player1.match.forceMove(0,1,player1.selectedWorker);
        Assert.assertEquals("error3", GameMessage.turnMessageWin, player1.manageStateMove(1,1).getNextInstruction());
    }

    /**
     * Test the method manageStateBuild in these cases:
     * correct build;
     * failed build;
     * a not possible build because the last movement make it impossible and so the Player lose;
     */
    @Test
    public void testManageStateBuild(){
        player1.setSelectedWorker(Worker.Gender.Male);
        player1.selectedWorkerMove(1,0);
        player1.setSelectedWorker(Worker.Gender.Female);
        player1.selectedWorkerMove(2,1);

        player2.setSelectedWorker(Worker.Gender.Male);
        player2.selectedWorkerMove(2,0);
        player2.setSelectedWorker(Worker.Gender.Female);
        player2.selectedWorkerMove(0,1);

        player3.setSelectedWorker(Worker.Gender.Male);
        player3.selectedWorkerMove(0,0);
        player3.setSelectedWorker(Worker.Gender.Female);
        player3.selectedWorkerMove(1,2);

        match.nextPlayer();
        player1.setSelectedWorker(Worker.Gender.Male);
        Assert.assertEquals(player1.manageStateBuild(1, 1).getNextInstruction(), GameMessage.turnMessageOkBuild);
        Assert.assertEquals(player1.manageStateBuild(2, 1).getNextInstruction(), GameMessage.turnMessageFailBuildChangeDestination);

        player3.setSelectedWorker(Worker.Gender.Female);
        player3.match.forceMove(1,1,player3.selectedWorker);
        Assert.assertEquals(player1.manageStateBuild(1, 1).getNextInstruction(), GameMessage.turnMessageLose);
    }

    /**
     * Test the method manageStateSelection in these cases:
     * correct selection (with coordinates or with gender);
     * incorrect selection (with coordinates);
     * none workers selectable.
     *
     */
    @Test
    public void testManageStateSelection1(){
        player1.setSelectedWorker(Worker.Gender.Male);
        player1.selectedWorkerMove(1,0);
        player1.setSelectedWorker(Worker.Gender.Female);
        player1.selectedWorkerMove(0,0);

        player2.setSelectedWorker(Worker.Gender.Male);
        player2.selectedWorkerMove(2,0);
        player2.setSelectedWorker(Worker.Gender.Female);
        player2.selectedWorkerMove(0,1);

        player3.setSelectedWorker(Worker.Gender.Male);
        player3.selectedWorkerMove(2,1);
        player3.setSelectedWorker(Worker.Gender.Female);
        player3.selectedWorkerMove(1,2);

        match.nextPlayer();
        Assert.assertEquals(player1.manageStateSelection(Worker.Gender.Male,-1,-1).getNextInstruction(), GameMessage.turnMessageOkWorkerSelection);
        Assert.assertEquals(player1.manageStateSelection(null,0,0).getNextInstruction(),GameMessage.turnMessageOkWorkerSelection);
        Assert.assertEquals(player1.manageStateSelection(null,2,3).getNextInstruction(),GameMessage.turnMessageSelectYourWorker);
        Assert.assertEquals(player1.manageStateSelection(null,1,0).getNextInstruction(),GameMessage.turnMessageOkWorkerSelection);

        player3.setSelectedWorker(Worker.Gender.Female);
        player3.match.forceMove(1,1,player3.selectedWorker);
        Assert.assertEquals(player1.manageStateSelection(Worker.Gender.Male,-1,-1).getNextInstruction(), GameMessage.turnMessageLoserNoWorker);
    }

    /**
     * Test the method manageStateSelection introducing a PlayerHypnus forcing a selection limit caused by his power, in these cases:
     * automatic switch because only other player's worker is selectable (caused by checkAllLimitSelection);
     * none workers selectable (mix of limits caused by checkLoserMove and setSelectedWorker).
     */
    @Test
    public void testManageStateSelection2(){
        player3 = new PlayerHypnus(player3.nickname,player3.number,new Card(13),player3.match,player3.workers[0].getColor());
        match.players[2] = player3;
        player1.setSelectedWorker(Worker.Gender.Male);
        player1.selectedWorkerMove(1,0);
        player1.setSelectedWorker(Worker.Gender.Female);
        player1.selectedWorkerMove(2,1);

        player2.setSelectedWorker(Worker.Gender.Male);
        player2.selectedWorkerMove(2,0);
        player2.setSelectedWorker(Worker.Gender.Female);
        player2.selectedWorkerMove(0,1);

        player3.setSelectedWorker(Worker.Gender.Male);
        player3.selectedWorkerMove(0,0);
        player3.setSelectedWorker(Worker.Gender.Female);
        player3.selectedWorkerMove(1,2);

        match.nextPlayer();

        player3.setSelectedWorker(Worker.Gender.Female);
        player3.match.forceMove(1,1,player3.selectedWorker);
        Assert.assertEquals(player1.manageStateSelection(Worker.Gender.Male,-1,-1).getNextInstruction(), GameMessage.turnMessageUnselectableWorkerSwitch);

        player3.match.forceMove(1,2,player3.selectedWorker);
        player1.match.forceBuild(3,1,player1.selectedWorker);
        player1.match.forceMove(3,1,player1.selectedWorker);
        Assert.assertEquals(player1.manageStateSelection(Worker.Gender.Female,-1,-1).getNextInstruction(), GameMessage.turnMessageUnselectableWorkerSwitch);

        player3.match.forceMove(1,1,player3.selectedWorker);
        player3.match.forceBuild(2,1,player3.selectedWorker);
        player3.match.forceBuild(2,1,player3.selectedWorker);
        Assert.assertEquals(player1.manageStateSelection(Worker.Gender.Male,-1,-1).getNextInstruction(), GameMessage.turnMessageLoserNoWorker);
    }

    /**
     * Test the method manageStateSelection introducing a PlayerHypnus forcing a selection limit caused by his power, in these cases:
     * none workers selectable (another mix of limits caused by checkLoserMove and setSelectedWorker, please run the test with coverage to see the difference between this and the precedent test).
     */
    @Test
    public void testManageStateSelection3(){
        player3 = new PlayerHypnus(player3.nickname,player3.number,new Card(13),player3.match,player3.workers[0].getColor());
        match.players[2] = player3;
        //Simulating the first allocation
        player1.setSelectedWorker(Worker.Gender.Male);
        player1.selectedWorkerMove(1,0);
        player1.setSelectedWorker(Worker.Gender.Female);
        player1.selectedWorkerMove(2,1);

        player2.setSelectedWorker(Worker.Gender.Male);
        player2.selectedWorkerMove(2,0);
        player2.setSelectedWorker(Worker.Gender.Female);
        player2.selectedWorkerMove(0,1);

        player3.setSelectedWorker(Worker.Gender.Male);
        player3.selectedWorkerMove(0,0);
        player3.setSelectedWorker(Worker.Gender.Female);
        player3.selectedWorkerMove(1,2);

        match.nextPlayer();

        player3.setSelectedWorker(Worker.Gender.Female);
        player3.match.forceMove(1,1,player3.selectedWorker);
        player1.setSelectedWorker(Worker.Gender.Female);
        player1.match.forceMove(3,1,player1.selectedWorker);
        player1.match.forceBuild(2,1,player1.selectedWorker);
        player1.match.forceMove(2,1,player1.selectedWorker);
        Assert.assertEquals(player1.manageStateSelection(Worker.Gender.Female,-1,-1).getNextInstruction(), GameMessage.turnMessageLoserNoWorker);
    }

    /**
     * Test the manageTurn, in particular these cases:
     * correct selection phase;
     * correct movement phase;
     * correct building phase;
     * impossible selection and losing player;
     * trying incorrect movement;
     * trying incorrect building;
     */
    @Test
    public void testManageTurn(){
        //Simulating the first allocation
        player1.setSelectedWorker(Worker.Gender.Male);
        player1.selectedWorkerMove(1,0);
        player1.setSelectedWorker(Worker.Gender.Female);
        player1.selectedWorkerMove(0,0);

        player2.setSelectedWorker(Worker.Gender.Male);
        player2.selectedWorkerMove(2,0);
        player2.setSelectedWorker(Worker.Gender.Female);
        player2.selectedWorkerMove(2,1);

        player3.setSelectedWorker(Worker.Gender.Male);
        player3.selectedWorkerMove(0,1);
        player3.setSelectedWorker(Worker.Gender.Female);
        player3.selectedWorkerMove(1,2);

        match.nextPlayer();
        //Correct selection
        Assert.assertEquals(player1.manageTurn(-1,-1, Worker.Gender.Male,"").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        //Lose selection
        player3.match.forceMove(1,1,player3.selectedWorker);
        player1.stateOfTurn = 1;
        Assert.assertEquals(player1.manageTurn(-1,-1, Worker.Gender.Male,"").getNextInstruction(), GameMessage.turnMessageLoserNoWorker);
        //All Correct (selection,move,build)
        Assert.assertEquals(match.getPlayingNow(),player2);
        Assert.assertEquals(player2.manageTurn(-1,-1, Worker.Gender.Female,"").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals(player2.manageTurn(2,2, Worker.Gender.Female,"").getNextInstruction(),GameMessage.turnMessageOkMovement + GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals(player2.manageTurn(3,2, Worker.Gender.Female,"").getNextInstruction(),GameMessage.turnMessageOkBuild + GameMessage.turnMessageTurnEnd);
        //Incorrect Move and incorrect build
        Assert.assertEquals(match.getPlayingNow(),player3);
        Assert.assertEquals(player3.manageTurn(-1,-1, Worker.Gender.Female,"").getNextInstruction(), GameMessage.turnMessageOkWorkerSelection + GameMessage.turnMessageChooseCellMove);
        Assert.assertEquals(player3.manageTurn(2,2, Worker.Gender.Female,"").getNextInstruction(),GameMessage.turnMessageFailMovementChangeDestination);
        Assert.assertEquals(player3.manageTurn(1,2, Worker.Gender.Female,"").getNextInstruction(),GameMessage.turnMessageOkMovement + GameMessage.turnMessageChooseCellBuild);
        Assert.assertEquals(player3.manageTurn(1,4, Worker.Gender.Female,"").getNextInstruction(),GameMessage.turnMessageFailBuildChangeDestination);
        Assert.assertEquals(player3.manageTurn(1,3, Worker.Gender.Female,"").getNextInstruction(),GameMessage.turnMessageOkBuild + GameMessage.turnMessageTurnEnd);
        Assert.assertEquals(match.getPlayingNow(),player2);
    }
}
