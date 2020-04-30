package it.polimi.ingsw.model;

import org.junit.*;

public class PlayerArtemisTest {
    private  static Match match;
    private static PlayerArtemis artemis;
    private static Player player2Generic;

    @BeforeClass
    public static void beforeClass() throws Exception {
        match=new Match(1,2);
        artemis=new PlayerArtemis("ruzzolino",2,new Card(9),match, Worker.Color.RED);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.GREEN);
        match.players[1] = artemis;
        match.players[0] = player2Generic;
    }

    @AfterClass
    public static void afterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSelectedWorkerMove() {
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,1));

        Assert.assertTrue(artemis.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(artemis.selectedWorkerMove(1,3));
        Assert.assertTrue(artemis.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(artemis.selectedWorkerMove(2,0));

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,2));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,3));

        Assert.assertTrue(artemis.setSelectedWorker(Worker.Gender.Male));
        Assert.assertFalse(artemis.selectedWorkerMove(1,3));              //non deve poter rimanere sulla stessa casella facendo avanti e indietro nemmeno (regola generale in reltà)
        Assert.assertFalse(artemis.selectedWorkerMove(4,3));              //non deve raggiungerla perchè troppo lontana
        Assert.assertTrue(artemis.selectedWorkerMove(3,3));               //si sposta effettivamente due volte
        Assert.assertTrue(artemis.selectedWorkerMove(2,1));               //si sposta effettivamente due volte di nuovo
        Assert.assertFalse(artemis.selectedWorkerMove(0,2));              //provo a spostarla a ci sono 2 worker che impediscono in movimento
        Assert.assertTrue(artemis.selectedWorkerMove(0,1));               //simile a prima ma ora ci riesce tramite un solo percorso possibile

        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,2));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerBuild(2,1));

        Assert.assertTrue(artemis.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(artemis.selectedWorkerMove(2,2));               //si sposta di 2 salendo gradualmente da liv 0 a liv2 errore: nelle iterazioni si esce dal tabellone se i for iterano attorno fuori dal tabellone stesso!


    }

}
