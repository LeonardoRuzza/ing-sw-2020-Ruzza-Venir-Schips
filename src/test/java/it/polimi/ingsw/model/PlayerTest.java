package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameMessage;
import org.junit.*;

public class PlayerTest {

    private static Player player1,player2, player3;
    private static Match match;
    private static Card card1, card2, card3;

    @Before
    public void setUp() throws Exception {
        match=new Match(1,3);
        player1 = new Player("leo",1, card1 = new Card(1), match, Worker.Color.RED);
        player2 = new Player("valerio", 2, card2  = new Card(5), match, Worker.Color.BLACK);
        player3 = new Player("edo", 3, card3  = new Card(3), match, Worker.Color.WHITE);
        match.players[0] = player1;
        match.players[1] = player2;
        match.players[2] = player3;
    }

    @After
    public void tearDown() throws Exception {

    }

    @BeforeClass
    public static void beforeClass() throws Exception {

    }

    @AfterClass
    public static void afterClass() throws Exception {

    }

    @Test
    public void testBuildersAndSetterAndGetter() {
        Assert.assertEquals("Errore1", "leo", player1.getNickname());
        Assert.assertEquals("Errore2",1,player1.getNumber());
        player1.setNickname("Edoardo");
        player1.setNumber(2);
        Assert.assertEquals("Errore3", "Edoardo", player1.getNickname());
        Assert.assertEquals("Errore4",2 ,player1.getNumber());
        Assert.assertEquals("Errore5", "valerio", player2.getNickname());
        Assert.assertEquals("Errore6",2 ,player2.getNumber());
        Assert.assertEquals("Errore7",card2,player2.card);
        Assert.assertEquals("Errore8",match,player2.match);
        Assert.assertEquals("Errore9",Worker.Color.BLACK,player2.workers[0].getColor());
        Assert.assertEquals("Errore10",Worker.Color.BLACK,player2.workers[1].getColor());
        Assert.assertEquals("Errore11",Worker.Gender.Male,player2.workers[0].getGender());
        Assert.assertEquals("Errore12",Worker.Gender.Female,player2.workers[1].getGender());
        //player1=null;
        //player2=null;
    }


    @Test
    public void testSetSelectedWorker() {
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
    }

    @Test
    public void testSelectedWorkerMove() {
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(1, 2));
        Assert.assertTrue(player1.selectedWorkerMove(2, 2));
        Assert.assertFalse(player1.selectedWorkerMove(4, 4));
    }

    @Test
    public void testSelectedWorkerBuild() {
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(1, 2));
        Assert.assertTrue(player1.selectedWorkerMove(2, 2));
        Assert.assertTrue(player1.selectedWorkerBuild(1, 2));  // Costruito B1

        Assert.assertTrue(player1.selectedWorkerMove(1, 2));
    }

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


   // Turno 1 -------------------------------------------------------------
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(1, 1));
        Assert.assertTrue(player1.selectedWorkerBuild(1, 2));  // Costruito B1

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2.selectedWorkerMove(3, 4));
        Assert.assertTrue(player2.selectedWorkerBuild(3, 3));  // Costruito B1


        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player3.selectedWorkerMove(1, 2));
        Assert.assertTrue(player3.selectedWorkerBuild(0, 2));  // Costruito B1

// Turno 2 ------------------------------------------------------------------------
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1, 0));
        Assert.assertTrue(player1.selectedWorkerBuild(2, 1));  // Costruito B1

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2.selectedWorkerMove(4, 2));
        Assert.assertTrue(player2.selectedWorkerBuild(3, 2));  // Costruito B1

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player3.selectedWorkerMove(2, 1));
        Assert.assertTrue(player3.selectedWorkerBuild(3, 2));  // Costruito B2

 // Turno 3 ------------------------------------------------------------------------
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(0, 2));
        Assert.assertTrue(player1.selectedWorkerBuild(1, 3));  // Costruito B1

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2.selectedWorkerMove(3, 3));
        Assert.assertTrue(player2.selectedWorkerBuild(2, 3));  // Costruito B1

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player3.selectedWorkerMove(3, 2));
        Assert.assertTrue(player3.selectedWorkerBuild(3, 1));  // Costruito B1

 // Turno 4 ------------------------------------------------------------------------
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(2, 0));
        Assert.assertTrue(player1.selectedWorkerBuild(3, 1));  // Costruito B2

        Assert.assertTrue(player2.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2.selectedWorkerMove(4, 1));
        Assert.assertTrue(player2.selectedWorkerBuild(3, 1));  // Costruito B3

        Assert.assertTrue(player3.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player3.selectedWorkerMove(3, 1));
    }

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
        Assert.assertEquals("errore", GameMessage.turnMessageOkMovement, player1.manageStateMove(1,2).getNextInstruction());
        Assert.assertEquals("errore", GameMessage.turnMessageFailMovementChangeDestination, player1.manageStateMove(1,2).getNextInstruction());
        player1.match.forceBuild(1,1,player1.selectedWorker);
        player1.match.forceBuild(1,1,player1.selectedWorker);
        player1.match.forceBuild(1,1,player1.selectedWorker);
        player1.match.forceBuild(0,2,player1.selectedWorker);
        player1.match.forceBuild(0,1,player1.selectedWorker);
        player1.match.forceBuild(0,1,player1.selectedWorker);
        player1.match.forceMove(0,2,player1.selectedWorker);
        player1.match.forceMove(0,1,player1.selectedWorker);
        Assert.assertEquals("errore", GameMessage.turnMessageWin, player1.manageStateMove(1,1).getNextInstruction());
    }

    @Test
    public void testManageStateBuild(){

    }
}
