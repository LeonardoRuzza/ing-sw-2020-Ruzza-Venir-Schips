package it.polimi.ingsw.model;

public class Worker {

    public enum Gender{Male,Female};
    public enum Color{BLACK,WHITE,RED,BLUE,GREEN,YELLOW};  //colori da valutare in seguito
    private Gender gender;
    private Color color;
    private Cell oldLocation;
    private Cell cell;

    public Worker(Gender gender,Color color) {
        cell=null;
        oldLocation=null;
        this.gender=gender;
        this.color=color;
    }

    public Cell getOldLocation() {
        return oldLocation;
    }
    public Color getColor(){return color;}

    Cell move(Cell newCell) {
        //mettere cell in oldLocation prima di aggiornala
        oldLocation = this.cell;
        this.cell = newCell;
        return oldLocation;

    }
}
