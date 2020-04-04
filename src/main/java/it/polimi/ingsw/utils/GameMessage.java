package it.polimi.ingsw.utils;

public class GameMessage {
    // Lobby message
    public static String chooseColor = "Choose your color. ";
    public static String waitMessageForColor = "Wait for the other player's choose of color. ";
    public static String wrongColorInput = "Your input for color is not correct. ";
    public static String notAvailableColor = "The selected color has been already choosen. ";


    public static String wrongTurnMessage = "It is not your turn!";
    public static String turnMessageDORSE = "DORSE";
    public static String turnMessageBUILDTWOTIMES = "BUILDTWOTIMES";
    public static String turnMessageNO = "NO";
    public static String TurnMessageBUILDBEFORE = "BUILDBEFORE";
    public static String turnMessageLose = "You Lose. ";
    public static String turnMessageWin = "You Win. ";

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
}
