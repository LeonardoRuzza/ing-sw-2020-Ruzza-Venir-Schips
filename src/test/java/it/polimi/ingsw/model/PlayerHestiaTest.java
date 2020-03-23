package it.polimi.ingsw.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerHestiaTest {
    private  static Match match;
    private static PlayerHestia hestia;
    private static Player player2Generic;

    @BeforeClass
    public static void beforeClass() throws Exception {
        match=new Match(1,2);
        hestia=new PlayerHestia("leo",2,new Card(12),match, Worker.Color.BLACK);
        player2Generic=new Player("edo",1,new Card(1),match,Worker.Color.WHITE);
        match.players[1] = hestia;
        match.players[0] = player2Generic;

    }

    @AfterClass
    public static void afterClass() throws Exception {
        match=null;
        hestia=null;
        player2Generic=null;

    }


    @Test
    public void testSelectedWorkerBuildAndResetTurn() {
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(player2Generic.selectedWorkerMove(2,2));
        Assert.assertTrue(player2Generic.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(player2Generic.selectedWorkerMove(1,1));

        Assert.assertTrue(hestia.setSelectedWorker(Worker.Gender.Male));
        Assert.assertTrue(hestia.selectedWorkerMove(1,3));
        Assert.assertTrue(hestia.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(hestia.selectedWorkerMove(2,0));

        Assert.assertTrue(hestia.setSelectedWorker(Worker.Gender.Female));
        Assert.assertTrue(hestia.selectedWorkerMove(3,0));
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));             //costruisce la prima volta con la super
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));            //costruisce la seconda volta sulla stessa casella

        Assert.assertTrue(hestia.selectedWorkerBuild(4,0));             //costruisce la prima volta con la super su una casella permimetrale
        //hestia.resetTurn();
        //Assert.assertTrue(hestia.selectedWorkerBuild(4,0));          //per verificare che resetTurn funzioni
        Assert.assertFalse(hestia.selectedWorkerBuild(4,0));            //prova a costruire la seconda volta sulla stessa casella ma è perimetrale
        hestia.resetTurn();

        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));     //costruisce 2 volte senza caselle perimetrali di mezzo
        Assert.assertTrue(hestia.selectedWorkerBuild(3,1));

        Assert.assertFalse(hestia.selectedWorkerBuild(3,2));    //verifico che non possa costruire troppo lontano sia alla prima costruzione che alla seconda
        Assert.assertTrue(hestia.selectedWorkerBuild(2,1));
        Assert.assertFalse(hestia.selectedWorkerBuild(3,2));

    }
}
