package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerAthenaTest {
    final Card cardAthena = new Card(2);
    final Card cardAtlas = new Card(3);
    final int numOfPlayers = 3;
    private Player testPlayer1;
    private Player testPlayer2;
    private Match match = new Match(1, numOfPlayers);
    private Worker testWorkerAtlsas;
    private Worker testWorkerAthena;
    private int[] location = new int[]{4,4};
    private int[] build = new int[]{4,3};
    @Before
    public void setUp() throws Exception {
        testPlayer1 = new PlayerAtlas("player1", 1, cardAtlas, match, Worker.Color.RED);
        testPlayer2 = new PlayerAthena("player2", 2, cardAthena, match, Worker.Color.GREEN);
        Player player3 = new Player("player3", 3, match);
        match.players = new Player[]{testPlayer1, testPlayer2, player3};
        testWorkerAtlsas = testPlayer1.workers[0];
        testWorkerAthena = testPlayer2.workers[0];
        testPlayer2.setSelectedWorker(testWorkerAthena);
        testPlayer1.setSelectedWorker(testWorkerAtlsas);
        match.nextPlayer(); //turno p1;
        match.nextPlayer(); //turno p2;
    }

    @Test
    public void testAthenaGoUp() {
        match.forceBuild(build[0], build[1], testWorkerAthena);
        testPlayer2.selectedWorkerMove(location[0],location[1]);
        location[0] = 4;
        location[1] = 3;
        testPlayer2.selectedWorkerMove(location[0],location[1]);
        match.nextPlayer();
        match.nextPlayer();
        build[0] = 1;
        build[1] = 1;
        match.forceBuild(build[0], build[1], testWorkerAtlsas);
        location[0] = 0;
        location[1] = 0;
        match.forceMove(location[0],location[1],testWorkerAtlsas);
        location[0] = 1;
        location[1] = 1;
        match.checkMove(location[0],location[1],testWorkerAtlsas);
        Assert.assertFalse("Error Check Limit Move", match.forceMove(location[0],location[1],testWorkerAtlsas));
    }
}
