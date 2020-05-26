package it.polimi.ingsw.model.lobby;

import org.junit.Assert;
import org.junit.Test;

public class ViewToControllerTest {
    ViewToController viewToController;
    LobbyPlayer lobbyPlayer = new LobbyPlayer("leo");

    @Test
    public void testBuilderAndGetters(){
        viewToController = new ViewToController("info",lobbyPlayer,null);
        Assert.assertEquals(viewToController.getInformation(), "info");
        Assert.assertNull(viewToController.getLobbyRemoteView());
        Assert.assertEquals(viewToController.getLobbyPlayer(),lobbyPlayer);

    }
}
