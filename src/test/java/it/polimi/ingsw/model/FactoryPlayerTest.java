package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Test;

public class FactoryPlayerTest {

    @Test
    public void getPlayer() throws Exception {
        Match match = new Match(1, 2);
        FactoryPlayer factory = new FactoryPlayer();
        for(int x = 0; x < Card.getCardNumb(); x++ ){
            Player finalPlayer = FactoryPlayer.getPlayer("edo", 1, match, Worker.Color.RED, x);
            switch(x){
                case 0:
                    Assert.assertTrue(finalPlayer instanceof PlayerApollo);
                    break;
                case 1:
                    Assert.assertTrue(finalPlayer instanceof PlayerArtemis);
                    break;
                case 2:
                    Assert.assertTrue(finalPlayer instanceof PlayerAthena);
                    break;
                case 3:
                    Assert.assertTrue(finalPlayer instanceof PlayerAtlas);
                    break;
                case 4:
                    Assert.assertTrue(finalPlayer instanceof PlayerDemeter);
                    break;
                case 5:
                    Assert.assertTrue(finalPlayer instanceof PlayerHephaestus);
                    break;
                case 6:
                    Assert.assertTrue(finalPlayer instanceof PlayerMinotaur);
                    break;
                case 7:
                    Assert.assertTrue(finalPlayer instanceof PlayerPan);
                    break;
                case 8:
                    Assert.assertTrue(finalPlayer instanceof PlayerPrometheus);
                    break;
                case 9:
                    Assert.assertTrue(finalPlayer instanceof PlayerAres);
                    break;
                case 10:
                    Assert.assertTrue(finalPlayer instanceof PlayerChronus);
                    break;
                case 11:
                    Assert.assertTrue(finalPlayer instanceof PlayerHera);
                    break;
                case 12:
                    Assert.assertTrue(finalPlayer instanceof PlayerHestia);
                    break;
                case 13:
                    Assert.assertTrue(finalPlayer instanceof PlayerHypnus);
                    break;
                default:
                    break;
            }
        }

    }


}
