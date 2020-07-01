package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class BoardTest {

    private Board board = new Board();
    private Worker w1 = new Worker(Worker.Gender.Male, Worker.Color.RED);
    private Worker w2 = new Worker(Worker.Gender.Female, Worker.Color.RED);


    /**
     * This test check that:
     * <p>
     * - Builder works fine, creating correctly the various cells.
     * <p>
     * - Method {@code getDistance} returns correct value
     *
     */
    @Test
    public void testBuilderAndDistanceFromCells() {

        int levelHeight = 4;
        int boardSide = 5;
        for(int z = 0; z < levelHeight; z++){
            for(int y = 0; y < boardSide; y++){
                for(int x = 0; x < boardSide; x++){
                    Assert.assertNotNull(board.cells[x][y][z]);  // Check that cells were correctly created
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


    /**
     * This test mainly check :
     * <p> Method {@code addBlock}
     * -
     * <p>
     * - Method {@code blockInCell}
     * <p>
     * This test if focused on <b>Building block</b>
     * <p>
     * It tries to build some block form B1 to DORSE on a cell and then check that they are on the board for real
     */
    @Test
    public void testBlockInCell() {

        Assert.assertNull(board.blockInCell(0,0)); // Check that no block is present at the begin

        board.cells[0][0][0].addBlock();                             // Build B1
        Assert.assertEquals(board.cells[0][0][0].getBlock(), Block.B1);
        Assert.assertEquals(board.blockInCell(0,0), Block.B1);

        board.cells[0][0][1].addBlock();                           // Build B2
        Assert.assertEquals(board.cells[0][0][1].getBlock(), Block.B2);
        Assert.assertEquals(board.blockInCell(0,0), Block.B2);

        board.cells[0][0][2].addBlock();                            // Build B3
        Assert.assertEquals(board.cells[0][0][2].getBlock(), Block.B3);
        Assert.assertEquals(board.blockInCell(0,0), Block.B3);

        board.cells[0][0][3].addBlock();                             // Build DORSE
        Assert.assertEquals(board.cells[0][0][3].getBlock(), Block.DORSE);
        Assert.assertEquals(board.blockInCell(0,0), Block.DORSE);
    }


    /**
     * This test mainly check :
     * <p>
     * - Method {@code workerInCell}
     * <p>
     * - Method {@code moveWorkerInto}
     * <p>
     * - Method {@code getOldLocation}
     * <p>
     * This test if focused on the Worker's movement
     * <p>
     * It tries to make a worker perform various movements in the board; at the same time builds some blocks and check if worker can effectively move respecting game rules.
     * It also checks that oldLocation is the correct one, that the oldLocation is now empty and that the worker has effectively move.
     */
    @Test
    public void testWorkerInCell() {

        Assert.assertNull(board.workerInCell(0,0)); // Check that no worker is present on the cell

        Assert.assertTrue(board.cells[0][0][0].moveWorkerInto(w1));   // Move w1 in cell[0][0][0]
        Assert.assertEquals(board.cells[0][0][0].getWorker(), w1);    // Check move was succesfull
        Assert.assertEquals(board.workerInCell(0,0), w1);       // Check that only worker in cell[0][0] is w1


        Assert.assertTrue(board.cells[3][3][0].moveWorkerInto(w1));
        Assert.assertEquals(board.cells[3][3][0].getWorker(), w1);
        Assert.assertEquals(w1.getOldLocation(),board.cells[0][0][0] );      // Check that w1 old location is cell[0][0][0]
        Assert.assertNull(board.cells[0][0][0].getWorker());                 // Check that in oldLocation there is no worker


        Assert.assertEquals(board.cells[0][0][0].addBlock(), board.cells[0][0][0].getBlock());   // Add 2 blocks in cell[0][0]
        Assert.assertEquals(board.cells[0][0][1].addBlock(), board.cells[0][0][1].getBlock());


        Assert.assertTrue(board.cells[0][0][1].moveWorkerInto(w1));
        Assert.assertEquals(board.cells[0][0][1].getWorker(), w1);
        Assert.assertEquals(w1.getOldLocation(), board.cells[3][3][0]);
        Assert.assertNull(board.cells[3][3][0].getWorker());
        Assert.assertEquals(board.workerInCell(0,0), w1);



        Assert.assertTrue(board.cells[4][4][0].moveWorkerInto(w1));
        Assert.assertEquals(board.cells[4][4][0].getWorker(), w1);
        Assert.assertEquals(board.cells[0][0][2].addBlock(), board.cells[0][0][2].getBlock());  // Add 1 more block  in cell[0][0]

        Assert.assertTrue(board.cells[0][0][2].moveWorkerInto(w2));
        Assert.assertEquals(board.cells[0][0][2].getWorker(), w2);
        Assert.assertEquals(board.workerInCell(0,0), w2);
        Assert.assertEquals(board.workerInCell(4,4), w1);
    }


    /**
     * This test mainly check :
     * <p>
     * - Method {@code getLastBusyCell}
     * <p>
     * - Method {@code getFirstBuildableCell}
     * <p>
     * This test if focused on <b>Check when is possible to build on a cell only in presence of Blocks</b>
     * <p>
     * It check that after building a block the level is 'marked' as busy, and which level is the first 'available' one
     */
    @Test
    public void testGetLastBusyCell() {

        Assert.assertEquals(board.cells[0][0][0], board.getLastBusyCell(0,0));
        Assert.assertEquals(board.cells[0][0][0], board.getFirstBuildableCell(0,0));


        Assert.assertEquals(board.cells[0][0][0].addBlock(), board.cells[0][0][0].getBlock());
        Assert.assertEquals(board.cells[0][0][0], board.getLastBusyCell(0,0));
        Assert.assertEquals(board.cells[0][0][1], board.getFirstBuildableCell(0,0));

        Assert.assertEquals(board.cells[0][0][1].addBlock(), board.cells[0][0][1].getBlock());
        Assert.assertEquals(board.cells[0][0][1], board.getLastBusyCell(0,0));
        Assert.assertEquals(board.cells[0][0][2], board.getFirstBuildableCell(0,0));

        Assert.assertEquals(board.cells[0][0][2].addBlock(), board.cells[0][0][2].getBlock());
        Assert.assertEquals(board.cells[0][0][2], board.getLastBusyCell(0,0));
        Assert.assertEquals(board.cells[0][0][3], board.getFirstBuildableCell(0,0));

        Assert.assertEquals(board.cells[0][0][3].addBlock(), board.cells[0][0][3].getBlock());
        Assert.assertEquals(board.cells[0][0][3], board.getLastBusyCell(0,0));
        Assert.assertNull(board.getFirstBuildableCell(0,0));                               // Check that is no longer possible to build in cell[0][0]
    }


    /**
     * This test mainly check :
     * <p>
     * - Method {@code getLastBusyCell}
     * <p>
     * - Method {@code getFirstBuildableCell}
     * <p>
     * This test if focused on <b>Check when is possible to build on a cell in presence of Blocks and workers</b>
     * <p>
     * It check that after building a block or move a worker in a cell, the level is 'marked' as busy, and which level is the first 'available' one.
     */
    @Test
    public void testGetLastBusyCell2() {
        board.cells[0][0][0].addBlock();
        board.cells[0][0][1].addBlock();
        board.cells[0][0][2].addBlock();
        board.cells[0][0][2].moveWorkerInto(w1);
        Assert.assertEquals(board.cells[0][0][2], board.getLastBusyCell(0,0));
        Assert.assertEquals(board.cells[0][0][3], board.getFirstBuildableCell(0,0));

        board.cells[2][2][0].moveWorkerInto(w2);
        Assert.assertEquals(board.cells[2][2][0], board.getLastBusyCell(2,2));
    }


    /**
     * This test mainly check :
     * <p>
     * - Method {@code removeWorker}
     * <p>
     * This test if focused on <b>Removing workers from the Board</b>
     * <p>
     * It check that the cell is effectively empty after the worker has been removed.
     */
    @Test
    public void testRemoveWorker() {

        Assert.assertTrue(board.cells[0][0][0].moveWorkerInto(w1));
        Assert.assertEquals(board.cells[0][0][0].getWorker(), w1);
        board.removeWorker(w1);
        Assert.assertNull(board.cells[0][0][0].getWorker());
    }


    /**
     * This test mainly check :
     * <p>
     * - Method {@code removeBlock}
     * <p>
     * This test if focused on <b>Removing blocks from the Board</b>
     * <p>
     * It check that the highest block(and only that block) is effectively removed from a cell after calling the method removeBlock
     */
    @Test
    public void testRemoveBlock() {

        board.cells[0][0][0].addBlock();
        board.cells[0][0][1].addBlock();
        board.cells[0][0][2].addBlock();

        board.removeBlock(0,0);
        Assert.assertEquals(board.cells[0][0][0].getBlock(), Block.B1);
        Assert.assertEquals(board.cells[0][0][1].getBlock(), Block.B2);
        Assert.assertNull(board.cells[0][0][2].getBlock());

        board.removeBlock(0,0);
        Assert.assertEquals(board.cells[0][0][0].getBlock(), Block.B1);
        Assert.assertNull(board.cells[0][0][1].getBlock());

        board.removeBlock(0,0);
        Assert.assertNull(board.cells[0][0][0].getBlock());
        Assert.assertNull(board.cells[0][0][1].getBlock());
        Assert.assertNull(board.cells[0][0][2].getBlock());

        board.cells[2][2][0].addBlock();
        board.cells[2][2][1].addBlock();
        board.cells[2][2][2].addBlock();
        board.cells[2][2][3].addBlock();
        board.removeBlock(2,2);
        board.removeBlock(2,2);
        board.removeBlock(2,2);
        board.removeBlock(2,2);
        Assert.assertNull(board.cells[2][2][0].getBlock());
        Assert.assertNull(board.cells[2][2][1].getBlock());
        Assert.assertNull(board.cells[2][2][2].getBlock());
        Assert.assertNull(board.cells[2][2][3].getBlock());
    }


    /**
     * This test mainly check :
     * <p>
     * - Method {@code removeBlock}
     * <p>
     * This test if focused on <b>Removing a Block from the Board when no Block is present</b>
     * <p>
     * It check that removeBlock does nothing if no Block is present on a cell
     */
    @Test
    public void testRemoveUnrealBlock() {

        board.removeBlock(2,2);
        board.removeBlock(2,2);
        board.removeBlock(2,2);
    }

    /**
     * This test mainly check :
     * <p>
     * - Method {@code draw}
     * <p>
     * It check that the method print the real current status of the board.
     */
    @Test
    public void testDraw(){
        ByteArrayOutputStream printedBoard = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(printedBoard);
        PrintStream old = System.out;
        System.setOut(ps);

        w2 = new Worker(Worker.Gender.Male, Worker.Color.GREEN);
        Worker w3 = new Worker(Worker.Gender.Female, Worker.Color.GREEN);
        Player p1 = new Player("ptest", 3, null, null, Worker.Color.RED);
        p1.workers[0] = w2;
        p1.workers[1] = w3;
        board.setPlayingNow(p1);
        board.cells[0][0][0].addBlock();
        board.cells[0][0][1].addBlock();
        board.cells[0][0][2].addBlock();
        board.cells[0][0][3].addBlock();
        board.cells[2][2][0].addBlock();
        board.cells[2][2][1].addBlock();
        board.cells[2][2][0].moveWorkerInto(p1.workers[0]);
        board.cells[0][4][0].addBlock();
        board.cells[0][4][0].moveWorkerInto(p1.workers[1]);
        board.cells[3][3][0].moveWorkerInto(w1);
        board.draw(p1);

        System.out.flush();
        System.setOut(old);
    }
}
