package it.polimi.ingsw.model;

public class Worker {

    public enum Gender{Male,Female}
    public enum Color{BLACK,WHITE,RED,BLUE,GREEN,YELLOW}  //colori da valutare in seguito
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
        Worker w = (Worker) obj;
        return this == w;
    }
}
