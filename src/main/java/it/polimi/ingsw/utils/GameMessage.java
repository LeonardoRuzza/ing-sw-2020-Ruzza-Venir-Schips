package it.polimi.ingsw.utils;

public class GameMessage {
    // Lobby message
    public static final String chooseColor = "Choose your color. ";
    public static final String waitMessageForColor = "Wait for the other player's choose of color. ";
    public static final String wrongColorInput = "Your input for color is not correct. ";
    public static final String notAvailableColor = "The selected color has been already choosen. ";

    public static final String wrongTurnMessage = "It is not your turn! ";

    public static final String turnMessage

    public static final String turnMessageDORSE = "DORSE";  //Needs Coord
    public static final String turnMessageBUILDTWOTIMES = "BUILDTWOTIMES";
    public static final String turnMessageNO = "NO";
    public static final String turnMessageBUILDBEFORE = "BUILDBEFORE";
    public static final String turnMessageFIRSTALLOCATION = "FIRSTALLOCATION";
    public static final String turnMessageErrorFIRSTALLOCATION = "Errore il posizionamento non è avvenuto correttamente";

    public static final String turnMessageLose = "You Lose. ";
    public static final String turnMessageWin = "You Win. ";

    public static final String turnMessageUnselectableWorkerSwitch = "Worker was not selectable, the other was chosen automatically. ";
    public static final String turnMessageLoserNoWorker = "You lost, neither worker is selectable! ";
    public static final String turnMessageOkWorkerSelection = "Worker successfully selected. ";
    public static final String turnMessageChooseCellMove = "Choose where to move. ";
    public static final String turnMessageOkMovement = "You moved correctly. ";
    public static final String turnMessageFailMovementChangeDestination = "It is not possible to perform the required move, change box. ";
    public static final String turnMessageChooseCellBuild = "Choose where to build. ";
    public static final String turnMessageOkBuild = "Construction completed. ";
    public static final String turnMessageFailBuildChangeDestination = "Construction cannot be performed, change box. ";
    public static final String turnMessageTurnEnd = "Your turn is over. ";

    public static final String aresTurnMessageAskRemoveBlok = "If you want to remove a block adjacent to the worker that you have not moved, write the coordinates otherwise write NO. ";
    public static final String aresTurnMessageSuccessRemoveBlokWEnd = "The block was successfully removed. Your turn is over. ";
    public static final String aresTurnMessageFailRemoveBlokWNewCell = "Unable to remove a block here, change position or cancel the option by writing NO. ";

    public static final String atlasTurnMessageAskBuildDorse = "Choose where to build and if you want to build a dome add DORSE after the box. ";
    public static final String atlasTurnMessageFailBuildDorse = "Impossible to build a dome here, change position or give up the DORSE option. ";

    public static final String prometheusTurnMessageAskBuildBefore = "Choose where to move or if you want to build before moving, write BUILDBEFORE after the box where you want to build. ";

    public static final String hephaesthusTurnMessageAskBuild = "Choose where to build and if you want to build twice write BUILDTWOTIMES after the box. ";
    public static final String hephaesthusTurnMessageFailOptionalBuildWEnd = "You can't build twice here. Your turn is over. ";

    public static final String hestiaDemeterTurnMessageAskTwoBuild = "If you want to build again, indicate the coordinates and write BUILDTWOTIMES otherwise write NO. ";
    public static final String hestiaDemeterTurnMessageFailOptionalBuildWEnd = "You can't build here twice. Your turn is over. ";
    public static final String hestiaDemeterTurnMessageFailOptionalBuildWNewCell = "You can't build here. Enter new coordinates or write NO. ";
}
