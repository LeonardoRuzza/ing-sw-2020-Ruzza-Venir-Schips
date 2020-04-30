package it.polimi.ingsw.model;

import org.junit.*;

public class PlayerApolloTest {

    private static Match match;
    private static Player player1, Apollo;


    @BeforeClass
    public static void beforeClass() throws Exception {
        match = new Match(1,2);
        player1 = new Player("edo",1,new Card(2),match,Worker.Color.GREEN);
        Apollo = new PlayerApollo("Apollo",2,new Card(1),match, Worker.Color.RED);
        match.players[0] = player1;
        match.players[1] = Apollo;
    }

    @Test
    public void testselectedWorkerMove() {

        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(2,2));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1,1));

        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Apollo.selectedWorkerMove(1,3));   // Apollo fa il posiz iniziale, quindi una mossa normale
        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Apollo.selectedWorkerMove(2,0));   // Uguale a sopra


        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Apollo.selectedWorkerMove(2,2));   // Apollo si muove in una cella dell'avversario
        Assert.assertEquals(Apollo.workers[0].getCell(), player1.workers[0].getOldLocation());  // Controllo che i worker si siano effettivamente scambiati di posizione
        Assert.assertEquals(player1.workers[0].getCell(), Apollo.workers[0].getOldLocation());


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male)); // Il worker del player 1 può effettivamente spostarsi in una cella che senza lo scamnbio non avrebbe potuto raggiungere
        Assert.assertTrue(player1.selectedWorkerMove(0,3));
        Assert.assertTrue(player1.selectedWorkerBuild(1,2));


        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Apollo.selectedWorkerMove(2,1));   // Apollo fa una mossa normale
        Assert.assertTrue(Apollo.selectedWorkerBuild(3, 1));


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(1,2));
        Assert.assertTrue(player1.selectedWorkerBuild(0,3));


        Assert.assertTrue(Apollo.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Apollo.selectedWorkerMove(1,2));   // Apollo fa una mossa speciale
    }
}
