package it.polimi.ingsw.model;

import org.junit.*;

public class PlayerTest {

    private static Player player1,player2;
    private static Match match=new Match(1,2);
    private static Card card=new Card(2);

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
        
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        player1 =new Player("ruzzolino",1, match);
        player2 = new Player("valerio", 2, card, match, Worker.Color.BLACK);
    }

    @AfterClass
    public static void afterClass() throws Exception {

    }

    @Test
    public void testBuildersAndSetterAndGetter() {
        Assert.assertEquals("Errore1", "ruzzolino", player1.getNickname());
        Assert.assertEquals("Errore2",1,player1.getNumber());
        player1.setNickname("Edoardo");
        player1.setNumber(2);
        Assert.assertEquals("Errore3", "Edoardo", player1.getNickname());
        Assert.assertEquals("Errore4",2 ,player1.getNumber());
        Assert.assertEquals("Errore5", "valerio", player2.getNickname());
        Assert.assertEquals("Errore6",2 ,player2.getNumber());
        Assert.assertEquals("Errore7",card,player2.card);
        Assert.assertEquals("Errore8",match,player2.match);
        Assert.assertEquals("Errore9",Worker.Color.BLACK,player2.workers[0].getColor());
        player1=null;
        player2=null;
    }

    @Test
    public void testAddToDeck() {
        int[] numberOfCards={3,11,8};
        player1.addToDeck(numberOfCards);
    }

    @Test
    public void testSetSelectedWorker() {
    }

    @Test
    public void testSelectedWorkerMove() {

    }

    @Test
    public void testSelectedWorkerBuild() {

    }
}
