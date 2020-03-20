package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;


public class BoardTest {

    private Board board = new Board();
    //private Worker w1 = new Worker(Worker.Gender.Male, Worker.Color.RED);

    /*@BeforeClass
    public static void beforeClass() throws Exception {
        Board board = new Board();
    }

    @AfterClass
    public static void afterClass() throws Exception {

    }*/

    @Test
    public void testBuilderAndDistanceFromCells() {

        int levelHeight = 4;
        int boardSide = 5;
        for(int z = 0; z < levelHeight; z++){
            for(int y = 0; y < boardSide; y++){
                for(int x = 0; x < boardSide; x++){
                    Assert.assertNotNull(board.cells[x][y][z]);  // Controlla che siano state create le celle
                }
            }
        }

        Assert.assertEquals(board.cells[0][0][0].getxCoord(), 0);
        Assert.assertEquals(board.cells[0][0][0].getyCoord(), 0);
        Assert.assertEquals(board.cells[0][0][0].getzCoord(), 0);

        int[] temp = new int[3];
        temp[0] = 2;
        temp[1] = 1;
        //temp[2] = 0;
        Assert.assertArrayEquals("Error", temp, board.getDistance(board.cells[2][2][0], board.cells[4][1][0]));

        temp[0] = 1;
        temp[1] = 1;
        temp[2] = -1;
        Assert.assertArrayEquals("Error", temp, board.getDistance(board.cells[2][2][2], board.cells[3][3][3]));
    }

    @Test
    public void testBlockInCell() {

        //Assert.assertNull(board.blockInCell(0,0));
        board.cells[0][0][0].addBlock();                             // Costruisce blocco B1
        Assert.assertEquals(board.cells[0][0][0].getBlock(), Block.B1);
        Assert.assertEquals(board.blockInCell(0,0), Block.B1);


        /*board.cells[0][0][1].addBlock(); // Costruisce blocco B2
        board.cells[0][0][2].addBlock(); // Costruisce blocco B3
        board.cells[0][0][3].addBlock(); // Costruisce cupola*/
    }

    @Test
    public void testWorkerInCell() {


    }
}
