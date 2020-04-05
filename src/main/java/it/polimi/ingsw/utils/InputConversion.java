package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Worker;

public class InputConversion {

    public static Worker.Color colorConversion(String color) {
        switch (color) {
            case "BLACK":
                return Worker.Color.BLACK;
            case "WHITE":
                return Worker.Color.WHITE;
            case "RED":
                return Worker.Color.RED;
            case "BLUE":
                return Worker.Color.BLUE;
            case "GREEN":
                return Worker.Color.GREEN;
            case "YELLOW":
                return Worker.Color.YELLOW;
            default:
                return null;
        }
    }
}
