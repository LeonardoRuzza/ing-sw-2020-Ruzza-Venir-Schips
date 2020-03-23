package it.polimi.ingsw.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerHephaesthusTest {

    private  static Match match;
    private static PlayerHephaesthus efesto;
    private static Player player2Generic;

    @BeforeClass
    public static void beforeClass() throws Exception {
        match=new Match(1,2);
        efesto=new PlayerHephaesthus("leo",2,new Card(5),match, Worker.Color.BLACK);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.WHITE);
        match.players[1] = efesto;
        match.players[0] = player2Generic;
    }

    @AfterClass
    public static void afterClass() throws Exception {
        match=null;
        efesto=null;
        player2Generic=null;
    }

    @Test
    public void testSelectedWorkerBuildAndResetTurn() {
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,1));

        Assert.assertTrue(efesto.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(efesto.selectedWorkerMove(1,3));
        Assert.assertTrue(efesto.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(efesto.selectedWorkerMove(2,0));

        Assert.assertTrue(efesto.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(efesto.selectedWorkerBuild(2,1));                //costruisce una volta
        Assert.assertFalse(efesto.selectedWorkerBuild(3,0));                //prova a costruire la seconda volta ma su una casella divers
        Assert.assertTrue(efesto.selectedWorkerBuild(2,1));                 //costruisce la seconda volta sulla stessa casella di prima ok

        Assert.assertTrue(efesto.selectedWorkerBuild(2,1));
        Assert.assertFalse(efesto.selectedWorkerBuild(2,1));              //prova a costruire la seconda volta ma è una cupola,non può

        efesto.resetTurn();   //resetTurn e ora dovrebbe poter costruire la cupola
        Assert.assertTrue(efesto.selectedWorkerBuild(2,1));

        efesto.resetTurn();
        Assert.assertTrue(efesto.selectedWorkerBuild(3,0));   //dopo la resetTurn costruisce su altra casella rispetto a prima

        efesto.resetTurn();
        Assert.assertFalse(efesto.selectedWorkerBuild(0,0));   //prova a costruire troppo lontano
        Assert.assertTrue(efesto.selectedWorkerBuild(1,0));    //costruisce il primo blocco
        Assert.assertTrue(efesto.selectedWorkerBuild(1,0));    //costruisce il secondo blocco sulla stesa casella di prima





    }
}
