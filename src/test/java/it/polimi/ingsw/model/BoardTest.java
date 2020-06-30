package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class BoardTest {

    private Board board = new Board();
    private Worker w1 = new Worker(Worker.Gender.Male, Worker.Color.RED);
    private Worker w2 = new Worker(Worker.Gender.Female, Worker.Color.RED);

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

        Assert.assertNull(board.blockInCell(0,0)); // Controlla non ci sia nessun blocco

        board.cells[0][0][0].addBlock();                             // Costruisce blocco B1
        Assert.assertEquals(board.cells[0][0][0].getBlock(), Block.B1);
        Assert.assertEquals(board.blockInCell(0,0), Block.B1);

        board.cells[0][0][1].addBlock();                           // Costruisce blocco B2
        Assert.assertEquals(board.cells[0][0][1].getBlock(), Block.B2);
        Assert.assertEquals(board.blockInCell(0,0), Block.B2);

        board.cells[0][0][2].addBlock();                            // Costruisce blocco B3
        Assert.assertEquals(board.cells[0][0][2].getBlock(), Block.B3);
        Assert.assertEquals(board.blockInCell(0,0), Block.B3);

        board.cells[0][0][3].addBlock();                             // Costruisce cupola
        Assert.assertEquals(board.cells[0][0][3].getBlock(), Block.DORSE);
        Assert.assertEquals(board.blockInCell(0,0), Block.DORSE);
    }

    @Test
    public void testWorkerInCell() {

        Assert.assertNull(board.workerInCell(0,0)); // Controlla non ci sia nessun worker

        Assert.assertTrue(board.cells[0][0][0].moveWorkerInto(w1));   // Sposta in w1 nella cella[0][0][0]
        Assert.assertEquals(board.cells[0][0][0].getWorker(), w1);    // Controllo sia andato a buon fine
        Assert.assertEquals(board.workerInCell(0,0), w1);       // Controlla che l'ultimo worker nella cella [0][0] sia w1


        Assert.assertTrue(board.cells[3][3][0].moveWorkerInto(w1));          // Sposta in w1 nella cella[3][3][0]
        Assert.assertEquals(board.cells[3][3][0].getWorker(), w1);           // Controllo sia andato a buon fine
        Assert.assertEquals(w1.getOldLocation(),board.cells[0][0][0] );      // Controllo che w1 abbia come old location [0][0][0]
        Assert.assertNull(board.cells[0][0][0].getWorker());                 // Controlla che nella vecchia cella non ci sia più il worker


        Assert.assertEquals(board.cells[0][0][0].addBlock(), board.cells[0][0][0].getBlock());   // Aggiungo blocco B1 in [0][0]
        Assert.assertEquals(board.cells[0][0][1].addBlock(), board.cells[0][0][1].getBlock());   // Aggiungo blocco B2 in [0][0]


        Assert.assertTrue(board.cells[0][0][1].moveWorkerInto(w1));           // Sposta in w1 nella cella[0][0][1]
        Assert.assertEquals(board.cells[0][0][1].getWorker(), w1);            // Controllo sia andato a buon fine
        Assert.assertEquals(w1.getOldLocation(), board.cells[3][3][0]);       // Controllo che w1 abbia come old location [0][0][0]
        Assert.assertNull(board.cells[3][3][0].getWorker());                  // Controlla che nella vecchia cella non ci sia più il worker
        Assert.assertEquals(board.workerInCell(0,0), w1);               // Controlla che l'ultimo worker nella cella [0][0] sia w1



        Assert.assertTrue(board.cells[4][4][0].moveWorkerInto(w1));                              // Sposta in w1 nella cella[4][4][0]
        Assert.assertEquals(board.cells[4][4][0].getWorker(), w1);                              // Controllo sia andato a buon fine
        Assert.assertEquals(board.cells[0][0][2].addBlock(), board.cells[0][0][2].getBlock());  // Aggiungo blocco B3 in [0][0]

        Assert.assertTrue(board.cells[0][0][2].moveWorkerInto(w2));          // Sposta in w2 nella cella[0][0][2]
        Assert.assertEquals(board.cells[0][0][2].getWorker(), w2);           // Controllo sia andato a buon fine
        Assert.assertEquals(board.workerInCell(0,0), w2);               // Controlla che l'ultimo worker nella cella [0][0] sia w2
        Assert.assertEquals(board.workerInCell(4,4), w1);               // Controlla che l'ultimo worker nella cella [4][4] sia w1
    }

    @Test
    public void testGetLastBusyCell() {   // TEST SOLO IN PRESENZA DI BLOCCHI

        Assert.assertEquals(board.cells[0][0][0], board.getLastBusyCell(0,0));             // Controllo ultima cella occupata in altezza sia [0][0][0]
        Assert.assertEquals(board.cells[0][0][0], board.getFirstBuildableCell(0,0));       // Controllo prima cella costruibile sia [0][0][0]


        Assert.assertEquals(board.cells[0][0][0].addBlock(), board.cells[0][0][0].getBlock());   // Aggiungo blocco B1 in [0][0]
        Assert.assertEquals(board.cells[0][0][0], board.getLastBusyCell(0,0));             // Controllo ultima cella occupata in altezza sia [0][0][0]
        Assert.assertEquals(board.cells[0][0][1], board.getFirstBuildableCell(0,0));       // Controllo prima cella costruibile sia [0][0][1]

        Assert.assertEquals(board.cells[0][0][1].addBlock(), board.cells[0][0][1].getBlock());   // Aggiungo blocco B2 in [0][0]
        Assert.assertEquals(board.cells[0][0][1], board.getLastBusyCell(0,0));             // Controllo ultima cella occupata in altezza sia [0][0][1]
        Assert.assertEquals(board.cells[0][0][2], board.getFirstBuildableCell(0,0));       // Controllo prima cella costruibile sia [0][0][2]

        Assert.assertEquals(board.cells[0][0][2].addBlock(), board.cells[0][0][2].getBlock());   // Aggiungo blocco B3 in [0][0]
        Assert.assertEquals(board.cells[0][0][2], board.getLastBusyCell(0,0));             // Controllo ultima cella occupata in altezza sia [0][0][2]
        Assert.assertEquals(board.cells[0][0][3], board.getFirstBuildableCell(0,0));       // Controllo prima cella costruibile sia [0][0][3]

        Assert.assertEquals(board.cells[0][0][3].addBlock(), board.cells[0][0][3].getBlock());   // Aggiungo blocco Cupola in [0][0]
        Assert.assertEquals(board.cells[0][0][3], board.getLastBusyCell(0,0));             // Controllo ultima cella occupata in altezza sia [0][0][3]
        Assert.assertNull(board.getFirstBuildableCell(0,0));                               // Controllo non ci sia una cella libera su cui costruire
    }

    @Test
    public void testGetLastBusyCell2() {   // TEST IN PRESENZA DI BLOCCHI E CELLE
        board.cells[0][0][0].addBlock();
        board.cells[0][0][1].addBlock();
        board.cells[0][0][2].addBlock();
        board.cells[0][0][2].moveWorkerInto(w1);
        Assert.assertEquals(board.cells[0][0][2], board.getLastBusyCell(0,0));
        Assert.assertEquals(board.cells[0][0][3], board.getFirstBuildableCell(0,0));

        board.cells[2][2][0].moveWorkerInto(w2);
        Assert.assertEquals(board.cells[2][2][0], board.getLastBusyCell(2,2));

        /*board.cells[0][0][0].addBlock();
        board.cells[0][0][1].addBlock();
        board.cells[0][0][2].addBlock();
        board.cells[0][0][3].moveWorkerInto(w1);
        Assert.assertEquals(board.cells[0][0][3], board.getLastBusyCell(0,0));*/
    }


    @Test
    public void testRemoveWorker() {

        Assert.assertTrue(board.cells[0][0][0].moveWorkerInto(w1));   // Sposta in w1 nella cella [0][0][0]
        Assert.assertEquals(board.cells[0][0][0].getWorker(), w1);    // Controllo sia andato a buon fine
        board.removeWorker(w1);                                       // Rimuove w1 dalla cella in cui si trova ( [0][0][0] in questo caso)
        Assert.assertNull(board.cells[0][0][0].getWorker());          // Controllo che in [0][0][0] non ci sia più w1
    }

    @Test
    public void testRemoveBlock() {

        board.cells[0][0][0].addBlock();  // Aggiunge B1
        board.cells[0][0][1].addBlock();  // Aggiunge B2
        board.cells[0][0][2].addBlock();  // Aggiunge B3

        board.removeBlock(0,0); // Rimuove B3
        Assert.assertEquals(board.cells[0][0][0].getBlock(), Block.B1);   // Controlla che B1 e B2 siano ancora presenti
        Assert.assertEquals(board.cells[0][0][1].getBlock(), Block.B2);
        Assert.assertNull(board.cells[0][0][2].getBlock());

        board.removeBlock(0,0); // Rimuove B2
        Assert.assertEquals(board.cells[0][0][0].getBlock(), Block.B1);
        Assert.assertNull(board.cells[0][0][1].getBlock());

        board.removeBlock(0,0); // Rimuove B1
        Assert.assertNull(board.cells[0][0][0].getBlock());
        Assert.assertNull(board.cells[0][0][1].getBlock());
        Assert.assertNull(board.cells[0][0][2].getBlock());

        board.cells[2][2][0].addBlock();  // Aggiunge B1
        board.cells[2][2][1].addBlock();  // Aggiunge B2
        board.cells[2][2][2].addBlock();  // Aggiunge B3
        board.cells[2][2][3].addBlock();  // Aggiunge Cupola
        board.removeBlock(2,2); // Rimuove Cupola
        board.removeBlock(2,2); // Rimuove B3
        board.removeBlock(2,2); // Rimuove B2
        board.removeBlock(2,2); // Rimuove B1
        Assert.assertNull(board.cells[2][2][0].getBlock());   // Controlla che rimozione sia andata a buon fine
        Assert.assertNull(board.cells[2][2][1].getBlock());
        Assert.assertNull(board.cells[2][2][2].getBlock());
        Assert.assertNull(board.cells[2][2][3].getBlock());
    }

    @Test
    public void testRemoveUnrealBlock() {   // TEST PER VEDERE SE REMOVEBLOCK FUNZIONA ANCHE SE NON CI SONO BLOCCHI DA RIMUOVERE SULLA CELLA CONSIDERATA: INFATTI IN TAL CASO NON FA NULLA

        board.removeBlock(2,2);
        board.removeBlock(2,2);
        board.removeBlock(2,2);
    }

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
