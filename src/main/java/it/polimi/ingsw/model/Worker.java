package it.polimi.ingsw.model;

public class Worker {
    //private enum Gender{Male,Female};   creare enumerazioni gender e color
    //private enum Color{};
    private Cell[] oldLocation;
    private Cell cell;

    public Worker() {
        cell=null;
        oldLocation=null;
    }

    void move(Cell cell) {
        //mettere cell in oldLocation prima di aggiornala
        this.cell = cell;

    }
}
