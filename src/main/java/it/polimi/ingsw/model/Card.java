package it.polimi.ingsw.model;

import java.io.Serializable;

public class Card implements Serializable {


    public enum activationPeriod{YOURMOVE, YOURBUILD, YOURTURN, FOETURN, ENDYOURTURN, STARTFOETURN, SUPERWINCOND, LIMITWINCOND}
    private static final int cardNumb = 14;
    private int number;
    static final String[] names = {"APOLLO","ARTEMIS","ATHENA","ATLAS","DEMETER","HEPHAESTUS","MINOTAUR","PAN","PROMETHEUS","ARES","CHRONUS","HERA","HESTIA","HYPNUS"};

    static final String[] descriptions = {
            "Your Move: Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.",
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
    public Card(String cardName){
        for(int x=0; x<names.length; x++){
            if(names[x].equals(cardName.toUpperCase())){
                this.number = x;
            }
        }
    }

    /**
     * Getter for the total number of cards
     * @return number of cards
     */
    public static int getCardNumb() { return cardNumb; }
    /**
     * Getter for the number associated at the card
     * @return number of the card
     */
    public int getNumber() { return number; }
    /**
     * Getter for the name associated at the card
     * @return name of the card
     */
    public String getName(){
        return names[this.number];
    }
    /**
     * Getter for the description associated at the card
     * @return description of the card
     */
    public String getDesc(){
        return descriptions[this.number];
    }
    /**
     * Getter for the activation time associated at the card
     * @return activation time of the card
     */
    public activationPeriod getActivationPeriod(){ return actPeriods[this.number]; }

    /**
     * Create a String with all card info already formatted for print
     * @return String contains all card info
     */
    public static String drawAll(){
        StringBuilder result = new StringBuilder();
        for(int i=0; i<cardNumb; i++ ){
            result.append("\n").append(Worker.Color.RED.getColorString()).append(names[i]).append(Worker.Color.RED.getANSI_RESET()).append("\n").append(descriptions[i]).append("\n");
        }
        return result.toString();
    }

    /**
     * Create a string formatted for print with card info (utility for testing only)
     * @return String contains card info
     */
    public String draw(){
        return "\n" + Worker.Color.RED.getColorString() + names[number] + Worker.Color.RED.getANSI_RESET() + "\n" + descriptions[number] + "\n";
    }
}
