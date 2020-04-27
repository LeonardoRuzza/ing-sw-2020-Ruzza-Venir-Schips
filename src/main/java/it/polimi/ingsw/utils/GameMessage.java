package it.polimi.ingsw.utils;

import java.util.ArrayList;
import java.util.List;

public class GameMessage {
    // Lobby message
    public static final String chooseColorBegin = "Opponent(s) found.\n\nChoose your color.\nAvailable colors:\nBLACK\nWHITE\nRED\nBLUE\nGREEN\nYELLOW";
    public static final String waitMessageForColorBegin = "Opponent(s) found\n\nWait for another player's choose of color. ";
    public static final String chooseColor = "Choose your color. ";
    public static final String waitMessageForColor = "Wait for another player's choose of color. ";
    public static final String wrongColorInput = "Your input for color is not correct. ";
    public static final String notAvailableColor = "The selected color has been already chosen. ";
    public static final String availableColors = "Available colors: ";

    public static final String cardPhase = "\n\nList of existing cards:";
    public static final String notAvailableCard = "Card not available";
    public static final String availableCards = "Available cards: ";
    public static final String playerMasterChoseCard2 = "Choose 2 card from the list; Insert one per time and then press Enter";
    public static final String playerMasterChoseCard3 = "Choose 3 card from the list; Insert one per time and then press Enter";
    public static final String playerMasterChoseAnotherCard = "OK! Choose another card. ";
    public static final String waitMessageForCard = "Another player is choosing a card...";
    public static final String waitMasterChoseOfCard = "Waiting the master player choose of deck ";

    public static final String player1is = "\n\nPlayer1 is: ";
    public static final String player2is = "Player2 is: ";
    public static final String player3is = "Player3 is: ";
    public static final String hisColor = "Chosen color: ";
    public static final String hisCard = "Chosen card: ";
    public static final String startNormalGame = "Game is starting...";






    // Normal Game Message
    public static final String messageGAMESTARTING = "The game is starting... ";
    public static final String wrongTurnMessage = "It is not your turn!";
    public static final String turnMessageDORSE = "DORSE";
    public static final String turnMessageBUILDTWOTIMES = "BUILDTWOTIMES";
    public static final String turnMessageNO = "NO";
    public static final String turnMessageBUILDBEFORE = "BUILDBEFORE";
    public static final String turnMessageFIRSTALLOCATION =  "FIRSTALLOCATION";

    public static final String turnMessageLose = "You Lose. ";
    public static final String turnMessageWin = "You Win. ";

    public static final String turnMessageSelectFirstAllocation = "Please select worker to allocate. With syntax X,Y or if you want to specify gender position X,Y,GENDER. ";
    public static final String turnMessageWaitFirstAllocation = "Please allocation of other player. ";
    public static final String turnMessageAlreadyLocatedWorkerFirstAllocation = "You have already located this worker please select the other one. ";
    public static final String turnMessageErrorFIRSTALLOCATION = "Error locating your Worker. ";
    public static final String turnMessageFIRSTALLOCATIONEnded = "Workers of all players have been allocated. ";

    public static final String turnMessageSelectYourWorker = "Select a worker specifying Male or Female";
    public static final String turnMessageLoserNoWorker = "You lost, neither worker is selectable! ";
    public static final String turnMessageOkWorkerSelection = "Worker successfully selected. ";
    public static final String turnMessageChooseCellMove = "Choose where to move. ";
    public static final String turnMessageUnselectableWorkerSwitch = "Worker was not selectable, the other was chosen automatically. " + turnMessageChooseCellMove;
    public static final String turnMessageOkMovement = "You moved correctly. ";
    public static final String turnMessageFailMovementChangeDestination = "It is not possible to perform the required move, change box. ";
    public static final String turnMessageChooseCellBuild = "Choose where to build. ";
    public static final String turnMessageOkBuild = "Construction completed. ";
    public static final String turnMessageFailBuildChangeDestination = "Construction cannot be performed, change box. ";
    public static final String turnMessageTurnEnd = "Your turn is over. ";

    public static final String turnMessageErrorInSyntax = "You wrote a wrong instruction, please check syntax. ";

    public static final String aresTurnMessageAskRemoveBlok = "If you want to remove a block adjacent to the worker that you have not moved, write the coordinates otherwise write NO. ";
    public static final String aresTurnMessageSuccessRemoveBlokWEnd = "The block was successfully removed. "+ turnMessageTurnEnd;
    public static final String aresTurnMessageFailRemoveBlokWNewCell = "Unable to remove a block here, change position or cancel the option by writing NO. ";

    public static final String atlasTurnMessageAskBuildDorse = "Choose where to build and if you want to build a dome add DORSE after the box. ";
    public static final String atlasTurnMessageFailBuildDorse = "Impossible to build a dome here, change position or give up the DORSE option. ";

    public static final String prometheusTurnMessageAskBuildBefore = "Choose where to move or if you want to build before moving, write BUILDBEFORE after the box where you want to build. ";

    public static final String hephaesthusTurnMessageAskBuild = "Choose where to build and if you want to build twice write BUILDTWOTIMES after the box. ";
    public static final String hephaesthusTurnMessageFailOptionalBuildWEnd = "You can't build twice here. " + turnMessageTurnEnd;

    public static final String hestiaDemeterTurnMessageAskTwoBuild = "If you want to build again, indicate the coordinates and write BUILDTWOTIMES otherwise write NO. ";
    public static final String hestiaDemeterTurnMessageFailOptionalBuildWEnd = "You can't build here twice. " + turnMessageTurnEnd;
    public static final String hestiaDemeterTurnMessageFailOptionalBuildWNewCell = "You can't build here. Enter new coordinates or write NO. ";

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
