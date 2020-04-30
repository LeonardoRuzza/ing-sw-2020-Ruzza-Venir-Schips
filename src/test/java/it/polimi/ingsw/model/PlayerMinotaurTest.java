package it.polimi.ingsw.model;

import org.junit.*;

import java.util.Arrays;

public class PlayerMinotaurTest {
    private static Match match;
    private static Player player1, Minotaur;


    @BeforeClass
    public static void beforeClass() throws Exception {
        match = new Match(1,2);
        player1 = new Player("edo",1,new Card(2),match,Worker.Color.RED);
        Minotaur = new PlayerMinotaur("Minotaur",2,new Card(1),match, Worker.Color.GREEN);
        match.players[0] = player1;
        match.players[1] = Minotaur;
    }

    @Test
    public void testselectedWorkerMove() {

        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player1.selectedWorkerMove(2,2));
        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1,1));


        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Minotaur.selectedWorkerMove(2,3));
        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Minotaur.selectedWorkerMove(3,0));


        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Minotaur.selectedWorkerMove(2,2));
        Assert.assertEquals(Minotaur.workers[0].getCell(), player1.workers[0].getOldLocation());  // Controllo che il workerMale di Minotaur sia nella old location del workerMale di player1
        /*System.out.println(player1.workers[0].getCell().getxCoord());                           // Controllo che il workerMale di player1 sia stato spostato nella giusta cella
        System.out.println(player1.workers[0].getCell().getyCoord());
        System.out.println(player1.workers[0].getCell().getzCoord());*/
        Assert.assertTrue(Minotaur.selectedWorkerBuild(2, 3));


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(1,0));
        Assert.assertTrue(player1.selectedWorkerBuild(2,0));       // Costruisce B1


        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(Minotaur.selectedWorkerMove(3,1));
        Assert.assertTrue(Minotaur.selectedWorkerBuild(2, 0));     // Costruisce B2


        Assert.assertTrue(player1.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player1.selectedWorkerMove(0,0));
        Assert.assertTrue(player1.selectedWorkerBuild(0,1));


        Assert.assertTrue(Minotaur.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(Minotaur.selectedWorkerMove(2,1));
        Assert.assertEquals(Minotaur.workers[0].getCell(), player1.workers[0].getOldLocation());  // Controlla che venga spostato indietro in una cella anche se deve salire a livello2
        /*System.out.println(player1.workers[0].getCell().getxCoord());
        System.out.println(player1.workers[0].getCell().getyCoord());
        System.out.println(player1.workers[0].getCell().getzCoord());*/
    }
}
