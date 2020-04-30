package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

public class WorkerTest {

    Worker w1 = new Worker(Worker.Gender.Male, Worker.Color.GREEN);
    Cell c1 = new Cell(2,2,1);
    Cell c2 = new Cell(1,1,0);


    @Test
    public void testBuilderAndGetter() {

        Assert.assertNull(w1.getCell());
        Assert.assertNull(w1.getOldLocation());
        Assert.assertEquals(w1.getColor(), Worker.Color.GREEN);
        Assert.assertEquals(w1.getGender(), Worker.Gender.Male);
    }

    @Test
    public void testMove() {

        w1.move(c1);
        Assert.assertEquals(w1.getCell().getxCoord(), 2);
        Assert.assertEquals(w1.getCell().getyCoord(), 2);
        Assert.assertEquals(w1.getCell().getzCoord(), 1);
        Assert.assertNull(w1.getOldLocation());

        w1.move(c2);
        Assert.assertEquals(w1.getCell().getxCoord(), 1);
        Assert.assertEquals(w1.getCell().getyCoord(), 1);
        Assert.assertEquals(w1.getCell().getzCoord(), 0);
        Assert.assertEquals(w1.getOldLocation().getxCoord(), 2);
        Assert.assertEquals(w1.getOldLocation().getyCoord(), 2);
        Assert.assertEquals(w1.getOldLocation().getzCoord(), 1);
    }
}
