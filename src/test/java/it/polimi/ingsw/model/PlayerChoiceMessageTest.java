package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

public class PlayerChoiceMessageTest {
    Player player= new Player("leo",1,new Match(1,2));
    PlayerChoiceMessage playerChoiceMessage = new PlayerChoiceMessage(player,1,1,null,"",Worker.Gender.Male);

    @Test
    public void testGetters(){
        Assert.assertEquals(playerChoiceMessage.getPlayer(),player);
        Assert.assertEquals(playerChoiceMessage.getX(),1);
        Assert.assertEquals(playerChoiceMessage.getY(),1);
        Assert.assertEquals(playerChoiceMessage.getGender(),Worker.Gender.Male);
        Assert.assertEquals(playerChoiceMessage.getOptional(),"");
    }
}