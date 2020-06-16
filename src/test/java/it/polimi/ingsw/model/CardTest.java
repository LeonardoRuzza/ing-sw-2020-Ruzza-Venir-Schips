package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CardTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

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
            //System.out.println("\n\nNome:"+i.getName()+"\nDescrizione:"+i.getDesc()+"\nActivationPeriod:"+i.getActivationPeriod());
        }
        Card card=new Card("Apollo");
        Assert.assertEquals("APOLLO", card.getName());
    }

    @Test
    public void testDrawAll(){
        Card.drawAll();
        //System.out.print(Card.drawAll());
    }

    @Test
    public void testDraw(){
        Card card=new Card(1);
        card.draw();
        //System.out.print(card.draw());
    }
}
