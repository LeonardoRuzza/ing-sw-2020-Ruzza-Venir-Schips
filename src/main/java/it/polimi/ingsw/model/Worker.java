package it.polimi.ingsw.model;

import java.io.Serializable;

public class Worker implements Cloneable, Serializable {

    public enum Gender implements Serializable{Male,Female}
    public enum Color implements Serializable{RED("\u001B[31m"),GREEN("\u001B[32m"),YELLOW("\u001B[33m"),PURPLE("\u001B[35m");

        private String colorString;
        private String ANSI_RESET = "\u001B[0m";
        Color(String s) {
            colorString = s;
        }
        public String getANSI_RESET() {
            return ANSI_RESET;
        }
        public String getColorString() {
            return colorString;
        }
    }  //colori da valutare in seguito

    private final Gender gender;
    private final Color color;
    private Cell cell, oldLocation;


// Builder
    public Worker(Gender gender,Color color) {
        cell = null;
        oldLocation = null;
        this.gender = gender;
        this.color = color;
    }


// Getter

    /**
     * Getter for worker old location
     * @return previous Cell of worker
     */
    public Cell getOldLocation() {
        return oldLocation;
    }
    /**
     * Getter for worker gender
     * @return gender of worker
     */
    public Gender getGender() { return gender; }
    /**
     * Getter for worker color
     * @return color of worker
     */
    public Color getColor(){return color;}
    /**
     * Getter for worker location
     * @return actuale Cell of worker
     */
    public Cell getCell() { return cell;}


// Generic methods

    /**Move the worker in the cell passed as parameter. The function does not apply any "controls" on the move.
     * <p>
     * @param newCell The cell destination where the selected worker will be moved
     * @return oldPosition(a Cell) of the worker just moved
     */
    protected Cell move(Cell newCell) {
        oldLocation = this.cell;
        this.cell = newCell;
        return oldLocation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Worker) {
            Worker w = (Worker) obj;
            return this == w;
        }
        else
            return false;
    }

    @Override
    protected Worker clone() {
        return new Worker(this.gender, this.color);
    }
}
