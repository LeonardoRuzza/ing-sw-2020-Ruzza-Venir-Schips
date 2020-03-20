package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

public class MatchTest {
    final int numOfPlayers = 3;
    private Match match = new Match(1, numOfPlayers);

    @Test
    public void testGetNumberOfPlayers(){
        Assert.assertTrue("error number of player", match.getNumberOfPlayers() == numOfPlayers);
    }


    @Test
    public void testCheckWin() {
       //per test server player
    }

    final Worker testWorkerOne = new Worker(Worker.Gender.Male, Worker.Color.BLACK);

    @Test
    public void testCheckSuperWin() {

        //per test server player
    }

    @Test
    public void testCheckLoserMove() {
        //per test serve player
    }
    @Test
    public void testCheckLoserBuild() {
        //per test serve player
    }

    final int[] startLocation = new int[]{0,0};

    @Test
    public void testCheckMove() {
        //testPosizionamento inizio partita
        Assert.assertTrue("Error checkMove first locate", match.checkMove(startLocation[0],startLocation[1],testWorkerOne).equals(testWorkerOne));
    }
    @Test
    public void testCheckBuild() {

    }
    @Test
    public void testNextPlayer() {

    }
    @Test
    public void testForceMove() {

    }
    @Test
    public void testForceBuild() {

    }

}
