package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CardTest {
    private Card cards[];

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBuilderAndGetter() {
        cards = new Card[14];
        for(int i=0; i<= 13;i++) {
        cards[i]= new Card(i);
        }
        for(Card i: cards){
            System.out.println("\n\nNome:"+i.getName()+"\nDescrizione:"+i.getDesc()+"\nActivationPeriod:"+i.getActivationPeriod());
        }
        cards=null;
        Card card=new Card("Apollo");
        Assert.assertEquals("APOLLO", card.getName());
    }

    @Test
    public void testDrawAll(){
        System.out.print(Card.drawAll());
    }

    @Test
    public void testDraw(){
        Card card=new Card(1);
        System.out.print(card.draw());
    }
}
