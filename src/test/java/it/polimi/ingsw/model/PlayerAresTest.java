package it.polimi.ingsw.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerAresTest {
    private  static Match match;
    private static PlayerAres ares;
    private static Player player2Generic;


    @BeforeClass
    public static void beforeClass() throws Exception {
        match=new Match(1,2);
        ares=new PlayerAres("ruzzolino",1,new Card(9),match, Worker.Color.BLACK);
        player2Generic=new Player("edo",2,new Card(1),match,Worker.Color.WHITE);
        match.players[0] = ares;
        match.players[1] = player2Generic;

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








    }
}
