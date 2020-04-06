package it.polimi.ingsw.utils;

import java.util.ArrayList;
import java.util.List;

public class GameMessage {
    // Lobby message
    public static String chooseColor = "Choose your color. ";
    public static String waitMessageForColor = "Wait for the other player's choose of color. ";
    public static String wrongColorInput = "Your input for color is not correct. ";
    public static String notAvailableColor = "The selected color has been already choosen. ";
    public static String availableColors = "Available colors: ";

    public static String cardPhase = "";
    public static String notAvailableCard = "Card not available";
    public static String availableCards = "Available cards: ";
    public static String playerMasterChoseCard2 = "Choose 2 card from the list; Insert one per time and then press Enter";
    public static String playerMasterChoseCard3 = "Choose 3 card from the list; Insert one per time and then press Enter";
    public static String playerMasterChoseAnotherCard = "OK! Choose another card. ";
    public static String waitMessageForCard = "Another player is choosing a card...";
    public static String waitMasterChoseOfCard = "Waiting the master player choose of deck ";

    public static String player1is = "Player1 is: ";
    public static String player2is = "Player2 is: ";
    public static String player3is = "Player3 is: ";
    public static String hisColor = "Chosen color: ";
    public static String hisCard = "Chosen card: ";
    public static String startNormalGame = "Game is starting...";






    // Normal Game Message
    public static String wrongTurnMessage = "It is not your turn!";
    public static String turnMessageDORSE = "DORSE";
    public static String turnMessageBUILDTWOTIMES = "BUILDTWOTIMES";
    public static String turnMessageNO = "NO";
    public static String turnMessageBUILDBEFORE = "BUILDBEFORE";
    public static String turnMessageFIRSTALLOCATION =  "FIRSTALLOCATION";

    public static String turnMessageErrorFIRSTALLOCATION = "Error locating your Worker";

    public static String turnMessageLose = "You Lose. ";
    public static String turnMessageWin = "You Win. ";

    public static String turnMessageSelectYourWorker = "Select a worker specifying Male or Female";
    public static String turnMessageUnselectableWorkerSwitch = "Worker was not selectable, the other was chosen automatically. ";
    public static String turnMessageLoserNoWorker = "You lost, neither worker is selectable! ";
    public static String turnMessageOkWorkerSelection = "Worker successfully selected. ";
    public static String turnMessageChooseCellMove = "Choose where to move. ";
    public static String turnMessageOkMovement = "You moved correctly. ";
    public static String turnMessageFailMovementChangeDestination = "It is not possible to perform the required move, change box. ";
    public static String turnMessageChooseCellBuild = "Choose where to build. ";
    public static String turnMessageOkBuild = "Construction completed. ";
    public static String turnMessageFailBuildChangeDestination = "Construction cannot be performed, change box. ";
    public static String turnMessageTurnEnd = "Your turn is over. ";

    public static String aresTurnMessageAskRemoveBlok = "If you want to remove a block adjacent to the worker that you have not moved, write the coordinates otherwise write NO. ";
    public static String aresTurnMessageSuccessRemoveBlokWEnd = "The block was successfully removed. Your turn is over. ";
    public static String aresTurnMessageFailRemoveBlokWNewCell = "Unable to remove a block here, change position or cancel the option by writing NO. ";

    public static String atlasTurnMessageAskBuildDorse = "Choose where to build and if you want to build a dome add DORSE after the box. ";
    public static String atlasTurnMessageFailBuildDorse = "Impossible to build a dome here, change position or give up the DORSE option. ";

    public static String prometheusTurnMessageAskBuildBefore = "Choose where to move or if you want to build before moving, write BUILDBEFORE after the box where you want to build. ";

    public static String hephaesthusTurnMessageAskBuild = "Choose where to build and if you want to build twice write BUILDTWOTIMES after the box. ";
    public static String hephaesthusTurnMessageFailOptionalBuildWEnd = "You can't build twice here. Your turn is over. ";

    public static String hestiaDemeterTurnMessageAskTwoBuild = "If you want to build again, indicate the coordinates and write BUILDTWOTIMES otherwise write NO. ";
    public static String hestiaDemeterTurnMessageFailOptionalBuildWEnd = "You can't build here twice. Your turn is over. ";
    public static String hestiaDemeterTurnMessageFailOptionalBuildWNewCell = "You can't build here. Enter new coordinates or write NO. ";

    public static boolean isValidOptional(String optional){
        List<String> availableOptional = new ArrayList<>();
        availableOptional.add(turnMessageDORSE);
        availableOptional.add(turnMessageBUILDTWOTIMES);
        availableOptional.add(turnMessageNO);
        availableOptional.add(turnMessageBUILDBEFORE);
        availableOptional.add(turnMessageFIRSTALLOCATION);
        return availableOptional.contains(optional);
    }
}
