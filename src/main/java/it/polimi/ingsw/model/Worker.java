package it.polimi.ingsw.model;

public class Worker {

    //private enum Gender{Male,Female};   creare enumerazioni gender e color
    //private enum Color{};
    private Cell oldLocation;
    private Cell cell;

    public Worker() {
        cell=null;
        oldLocation=null;
    }

    public Cell getOldLocation() {
        return oldLocation;
    }

    Cell move(Cell newCell) {
        //mettere cell in oldLocation prima di aggiornala
        oldLocation = this.cell;
        this.cell = newCell;
        return oldLocation;

    }
}
