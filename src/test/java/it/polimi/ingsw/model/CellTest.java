package it.polimi.ingsw.model;

import org.junit.*;

/**
 * <b>Method in this class are used in other classes like board and match. See these classes for more specific tests</b>
 */
public class CellTest {

    private Cell cell = new Cell(3, 4, 3);
    private Cell c1 = new Cell(1, 1, 0);
    private Cell c2 = new Cell(1, 1, 1);
    private Cell c3 = new Cell(1, 1, 2);
    private Cell c4 = new Cell(1, 1, 3);
    private Worker w1 = new Worker(Worker.Gender.Male, Worker.Color.RED);
    /*private Worker w2 = new Worker(Worker.Gender.Female, Worker.Color.RED);
    private Worker w3 = new Worker(Worker.Gender.Male, Worker.Color.WHITE);
    private Worker w4 = new Worker(Worker.Gender.Female, Worker.Color.WHITE);*/


    /**
     * This test mainly check that:
     * <p>
     * - Builder works fine, creating a cell with right coordinates and with no blocks or worker present.
     */
    @Test
    public void testBuilder() {

        Assert.assertTrue("Error", cell.getxCoord() == 3 && cell.getyCoord() == 4 && cell.getzCoord() == 3);
        Assert.assertNull(cell.getBlock());
        Assert.assertNull(cell.getWorker());
    }

    /**
     * This test mainly check :
     * <p> Method {@code addBlock}
     * -
     * <p>
     * - Method {@code getBlock}
     * <p>
     * This test if focused on <b>Building block</b>>
     * <p>
     * It tries to build some blocks checking that the correct block is associate to the right cell's level
     */
    @Test
    public void testAddBlock() {

        Assert.assertNull(c1.getBlock());
        c1.addBlock();
        c2.addBlock();
        c3.addBlock();
        c4.addBlock();
        Assert.assertEquals(c1.getBlock(), Block.B1);
        Assert.assertEquals(c2.getBlock(), Block.B2);
        Assert.assertEquals(c3.getBlock(), Block.B3);
        Assert.assertEquals(c4.getBlock(), Block.DORSE);

        cell.addBlock();
        Assert.assertNotEquals(cell.getBlock(), Block.B1);
        Assert.assertNotEquals(cell.getBlock(), Block.B2);
        Assert.assertNotEquals(cell.getBlock(), Block.B3);
        Assert.assertEquals(cell.getBlock(), Block.DORSE);

        Assert.assertEquals(cell.addBlock(), Block.DORSE);
    }

    /**
     * This test only checks the method {@code addDorse}, to see if it's possible to build a DORSE at any level
     */
    @Test
    public void testAddDorse() {
        c1.addDorse();
        Assert.assertEquals(c1.getBlock(), Block.DORSE);

        c2.addDorse();
        Assert.assertEquals(c1.getBlock(), Block.DORSE);
    }

    /**
     * This test mainly check :
     * <p>
     * - Method {@code moveWorkerInto}
     * <p>
     * - Method {@code getOldLocation}
     * <p>
     * This test if focused on the Worker's movement
     * <p>
     * It tries to make a worker perform some movements, also not respecting game rules.
     */
    @Test
    public void testMoveWorkerInto() {

        Assert.assertTrue(cell.moveWorkerInto(w1));
        Assert.assertEquals(cell.getWorker(), w1);
        Assert.assertEquals(w1.getCell(), cell);

        Assert.assertTrue(c1.moveWorkerInto(w1));
        Assert.assertEquals(c1.getWorker(), w1);
        Assert.assertEquals(w1.getCell(), c1);
        Assert.assertEquals(w1.getOldLocation(), cell);
        Assert.assertNull(cell.getWorker());
    }

    /**
     * This test mainly check :
     * <p>
     * - Method {@code removeBlock}
     * <p>
     * This test if focused on <b>Removing blocks from the cells</b>
     * <p>
     * It check that the highest block(and only that block) is effectively removed from a cell after calling the method removeBlock
     */
    @Test
    public void testRemoveBlock() {

        c1.addBlock();
        c2.addBlock();
        c3.addBlock();

        c3.setBlockNull();
        Assert.assertEquals(c1.getBlock(), Block.B1);
        Assert.assertEquals(c2.getBlock(), Block.B2);
        Assert.assertNull(c3.getBlock());
        Assert.assertNull(c4.getBlock());

        c2.setBlockNull();
        Assert.assertNull(c2.getBlock());

        c1.setBlockNull();
        Assert.assertNull(c1.getBlock());
    }
}
