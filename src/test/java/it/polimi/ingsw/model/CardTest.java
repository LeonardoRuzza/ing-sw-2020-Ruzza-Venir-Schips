package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CardTest {
    /**
     * Test the correct working of the Builder and Getters and allow to check the correct association of Gods to God's power uncommenting the println raw.
     */
    @Test
    public void testBuilderAndGetter() {
        Card[] cards = new Card[14];
        for(int i=0; i<= 13;i++) {
        cards[i]= new Card(i);
        }
        for(Card i: cards){
            i.getName();
            i.getDesc();
            i.getActivationPeriod();
            //System.out.println("\n\nName:"+i.getName()+"\nDescription:"+i.getDesc()+"\nActivationPeriod:"+i.getActivationPeriod());
        }
        Card card=new Card("Apollo");
        Assert.assertEquals("APOLLO", card.getName());
    }

    /**
     * Test the method drawAll (uncomment the print raw to see the result).
     */
    @Test
    public void testDrawAll(){
        Card.drawAll();
        //System.out.print(Card.drawAll());
    }

    /**
     * Test the method draw (uncomment the print raw to see the result).
     */
    @Test
    public void testDraw(){
        Card card=new Card(1);
        card.draw();
        //System.out.print(card.draw());
    }
}
