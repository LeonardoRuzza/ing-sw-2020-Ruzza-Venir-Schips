package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;


public class BoardTest {

    /*@BeforeClass
    public static void beforeClass() throws Exception {
        Board board = new Board();
    }

    @AfterClass
    public static void afterClass() throws Exception {

    }*/

    @Test
    public void testBuilderAndDistanceFromVariousCells() {

        Board board = new Board();
        int[] temp = new int[3];
        temp[0] = 2;
        temp[1] = 1;
        temp[2] = 0;
        Assert.assertArrayEquals("Error", temp, board.getDistance(board.cells[2][2][0], board.cells[4][1][0]));
    }
}
