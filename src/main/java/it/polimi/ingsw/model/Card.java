package it.polimi.ingsw.model;

public class Card {


    public enum activationPeriod{YOURMOVE, YOURBUILD, YOURTURN, FOETURN, ENDYOURTURN, STARTFOETURN, SUPERWINCOND, LIMITWINCOND}
    //final int cardNumb = 14;
    private int number;
    static final String[] names = {"Apollo","Artemis","Athena","Atlas","Demeter","Hephaestus","Minotaur","Pan","Prometheus","Ares","Chronus","Hera","Hestia","Hypnus"};
    static final String[] descriptions = {"Your Move: Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.",
            "Your Move: Your Worker may move one additional time, but not back to its initial space.",
            "Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.",
            "Your Build: Your Worker may build a dome at any level.",
            "Your Build: Your Worker may build one additional time, but not on the same space.",
            "Your Build: Your Worker may build one additional block (not dome) on top of your first block.",
            "Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.",
            "Win Condition: You also win if your Worker moves down two or more levels.",
            "Your Turn: If your Worker does not move up, it may build both before and after moving.",
            "End of Your Turn: You may remove an unoccupied block (not dome) neighboring your unmoved Worker. You also remove any Tokens on the block.",
            "Win Condition: You also win when there are at least five Complete Towers on the board.",
            "Opponent’s Turn: An opponent cannot win by moving into a perimeter space.",
            "Your Build: Your Worker may build one additional time, but this cannot be on a perimeter space.",
            "Start of Opponent’s Turn: If one of your opponent’s Workers is higher than all of their others, it cannot move."};
    static final activationPeriod[] actPeriods = {activationPeriod.YOURMOVE, activationPeriod.YOURMOVE, activationPeriod.FOETURN,
            activationPeriod.YOURBUILD, activationPeriod.YOURBUILD, activationPeriod.YOURBUILD,
            activationPeriod.YOURMOVE, activationPeriod.SUPERWINCOND, activationPeriod.YOURTURN,
            activationPeriod.ENDYOURTURN, activationPeriod.SUPERWINCOND, activationPeriod.LIMITWINCOND,
            activationPeriod.YOURBUILD, activationPeriod.STARTFOETURN};



    public Card(int cardNum){
        this.number = cardNum;
    }

    public String getName(){
        return names[this.number];
    }
    public String getDesc(){
        return descriptions[this.number];
    }
    public activationPeriod getActivationPeriod(){ return actPeriods[this.number]; }


}
