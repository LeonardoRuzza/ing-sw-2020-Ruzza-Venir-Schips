package it.polimi.ingsw.model;

import org.junit.*;

public class CellTest {

    private Cell cell = new Cell(3, 4, 3);
    private Cell c1 = new Cell(1, 1, 0);
    private Cell c2 = new Cell(1, 1, 1);
    private Cell c3 = new Cell(1, 1, 2);
    private Cell c4 = new Cell(1, 1, 3);


    @Test
    public void testBuilder() {

        Assert.assertTrue("Error", cell.getxCoord() == 3 && cell.getyCoord() == 4 && cell.getzCoord() == 3);
        Assert.assertNull(cell.getBlock());
        Assert.assertNull(cell.getWorker());
    }

    @Test
    public void testAddBlock() { // Verifico che venga costruito il blocco giusto in base all'altezza della cella
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

        Assert.assertEquals(cell.addBlock(), Block.DORSE);  // Provo a ricostruire sulla stessa cella
    }

    @Test
    public void testMoveWorkerInto() {

    }
}
