package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * <b>Method in this class are used in other classes like board and match. See these classes for more specific tests</b>
 */
public class WorkerTest {

    Worker w1 = new Worker(Worker.Gender.Male, Worker.Color.GREEN);
    Cell c1 = new Cell(2,2,1);
    Cell c2 = new Cell(1,1,0);

    /**
     * This test only check that:
     * <p>
     * - Builder and getter works fine.
     */
    @Test
    public void testBuilderAndGetter() {
        Assert.assertNull(w1.getCell());
        Assert.assertNull(w1.getOldLocation());
        Assert.assertEquals(w1.getColor(), Worker.Color.GREEN);
        Assert.assertEquals(w1.getGender(), Worker.Gender.Male);
    }

    /**
     * This test mainly check :
     * <p>
     * - Method {@code move}
     * <p>
     * This test if focused on the Worker's movement
     * <p>
     * It tries to make a worker perform some movements, checking his getter return correct values after the move
     */
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
