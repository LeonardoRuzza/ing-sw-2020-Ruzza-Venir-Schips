package it.polimi.ingsw.model;

import org.junit.*;

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


    @Test
    public void testBuilder() {

        Assert.assertTrue("Error", cell.getxCoord() == 3 && cell.getyCoord() == 4 && cell.getzCoord() == 3);
        Assert.assertNull(cell.getBlock());
        Assert.assertNull(cell.getWorker());
    }

    @Test
    public void testAddBlock() { // Verifico che venga costruito il blocco giusto in base all'altezza della cella

        Assert.assertNull(c1.getBlock()); // Non è presente ancora nessun blocco sulla cella c1
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
    public void testAddDorse() {
        c1.addDorse();
        Assert.assertEquals(c1.getBlock(), Block.DORSE);
    }

    @Test
    public void testMoveWorkerInto() {

        // w1 si sposta in cell
        Assert.assertTrue(cell.moveWorkerInto(w1));              // Conferma che ho spostato w1 in cell
        Assert.assertEquals(cell.getWorker(), w1);               // Controlla che in cell ci sia effetivamente w1
        Assert.assertEquals(w1.getCell(), cell);                 // Controlla che w1 sia effettivamente in cell


        // w1 si sposta in c1 da cell
        Assert.assertTrue(c1.moveWorkerInto(w1));                     // Conferma che ho spostato w1 in c1
        Assert.assertEquals(c1.getWorker(), w1);                      // Controlla che in c1 ci sia effetivamente w1
        Assert.assertEquals(w1.getCell(), c1);                        // Controlla che w1 sia effettivamente in c1
        Assert.assertEquals(w1.getOldLocation(), cell);               // Controlla che la old location di w1 sia cell
        Assert.assertNull(cell.getWorker());                          // Controlla che la vecchia cella occupata(cell) sia ora libera
    }

    @Test
    public void testRemoveBlock() {

        c1.addBlock(); // Aggiungo dei blocchi
        c2.addBlock();
        c3.addBlock();

        c3.setBlockNull(); // Rimuovo B3
        Assert.assertEquals(c1.getBlock(), Block.B1);
        Assert.assertEquals(c2.getBlock(), Block.B2);
        Assert.assertNull(c3.getBlock());
        Assert.assertNull(c4.getBlock());

        c2.setBlockNull(); // Rimuovo B2
        Assert.assertNull(c2.getBlock());

        c1.setBlockNull(); // Rimuovo B1
        Assert.assertNull(c1.getBlock());
    }
}
