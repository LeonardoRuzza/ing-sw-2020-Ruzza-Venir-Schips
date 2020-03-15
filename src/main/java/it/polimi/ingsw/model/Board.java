package it.polimi.ingsw.model;

public class Board {

    final int boardSide = 5;
    private Cell[][][] cell;


    public Board() {
       for(int z = 0; z < 5; z++){
           for(int y = 0; y < 5; y++){
               for(int x = 0; x < 5; x++){
                    cell[x][y][z] = new Cell(x, y, z);
               }
           }
       }
    }

    public Block blockInCell(int x, int y) {

    }

    public Worker workerInCell(int x, int y) {

    }
}
