package it.polimi.ingsw.model;

import org.junit.*;

public class CellTest {

    private Cell cell;


    @Test
    public void testBuilder() {

        cell = new Cell(3,4,5);
        Assert.assertTrue("Error", cell.getxCoord() == 3 && cell.getyCoord() == 4 && cell.getzCoord() == 5);

    }

    @BeforeClass
    public static void beforeClass() throws Exception {

    }

    @AfterClass
    public static void afterClass() throws Exception {

    }
}
