package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Worker;

public class InputConversion {

    public static Worker.Color colorConversion(String color) {
        switch (color) {
            case "RED":
                return Worker.Color.RED;
            case "GREEN":
                return Worker.Color.GREEN;
            case "YELLOW":
                return Worker.Color.YELLOW;
            default:
                return null;
        }
    }
}
