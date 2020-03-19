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
        System.out.println("Primo costruttore e modifica");
        System.out.println("Nickname:"+player1.getNickname()+"\nNumero del player:"+player1.getNumber());
        player1.setNickname("Edoardo");
        player1.setNumber(2);
        System.out.println("New nickname:"+player1.getNickname()+"\nNew numero del player:"+player1.getNumber());
        System.out.println("Secondo costruttore");
        System.out.println("Nickname:"+player2.getNickname()+"\nNumero del player:"+player2.getNumber()+"\nNome della carta:"+card.getName());
        player1=null;
        player2=null;
    }

    @Test
    public void testAddToDeck() {
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
