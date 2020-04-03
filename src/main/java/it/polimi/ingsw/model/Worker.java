package it.polimi.ingsw.model;

public class Worker implements Cloneable{

    public enum Gender{Male,Female}
    public enum Color{BLACK("\u001B[30m"),WHITE("\u001B[37m"),RED("\u001B[31m"),BLUE("\u001B[34m"),GREEN("\u001B[32m"),YELLOW("\u001B[33m");

        private String colorString;
        private String ANSI_RESET = "\u001B[0m";
        private Color(String s) {
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
    public Cell getOldLocation() {
        return oldLocation;
    }
    public Gender getGender() { return gender; }
    public Color getColor(){return color;}
    public Cell getCell() { return cell;}


// Generic methods
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
