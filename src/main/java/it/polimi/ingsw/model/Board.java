package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

public class Board {

    final int levelHeight = 4;
    final int boardSide = 5;
    protected Cell[][][] cells;


// Builder
    public Board() {
       cells = new Cell[boardSide][boardSide][levelHeight];
       for(int z = 0; z < levelHeight; z++){
           for(int y = 0; y < boardSide; y++){
               for(int x = 0; x < boardSide; x++){
                    cells[x][y][z] = new Cell(x, y, z);
               }
           }
       }
    }


// Observers
    public Block blockInCell(int x, int y) {  // Ritorna il blocco più in alto costruito nella cella
        Block lastBlock = null;
        for(int z = 0; z < levelHeight; z++){
            if (this.cells[x][y][z].getBlock() == null) {break;}
            switch (this.cells[x][y][z].getBlock()) {
                case B1:
                    lastBlock = Block.B1;
                    break;
                case B2:
                    lastBlock = Block.B2;
                    break;
                case B3:
                    lastBlock = Block.B3;
                    break;
                case DORSE:
                    lastBlock = Block.DORSE;
                    break;
                default:
                    break;
            }
        }
        return lastBlock;
    }

    public Worker workerInCell(int x, int y) {  // Ritorna il worker presente sulla cella
        Worker foundWorker = null;
        for(int z = 0; z < levelHeight; z++) {
            foundWorker = cells[x][y][z].getWorker();
            if(foundWorker == null){
                if(cells[x][y][z].getBlock() == null){
                    break;
                }
            }
        }
        return foundWorker;
    }


// Getters
    public Cell getLastBusyCell(int x, int y){
        Block b = blockInCell(x,y);
        Worker w = workerInCell(x,y);
        for(int z = 0; z < levelHeight; z++){
            if (cells[x][y][z].getBlock().equals(b) || cells[x][y][z].getWorker().equals(w))
                return cells[x][y][z];
        }
        return null;
    }

    public int[] getDistance(Cell c1, Cell c2){  // Modulo per x,y, per z cell1 - cell2
        int[] temp = new int[3];
        if (c1 != null && c2 != null) {
            temp[0] = Math.abs(c1.getxCoord() - c2.getxCoord());
            temp[1] = Math.abs(c1.getyCoord() - c2.getyCoord());
            temp[2] = c1.getzCoord() - c2.getzCoord();
            return temp;
        }
        else
            return null;
    }


//Generic Methods
    protected boolean forceMove(@NotNull Cell c, Worker w){
        //Cell c = getLastFreeCell(x,y);
        return c.moveWorkerInto(w);
    }

    protected Block forceBuild(@NotNull Cell c /*,Worker w*/){
        // Cell c = getLastFreeCell(x,y);
        return c.addBlock();
    }



     public Cell getLastFreeCell(int x, int y){
        Cell busyCell = getLastBusyCell(x,y);
        return cells[busyCell.getxCoord()][busyCell.getyCoord()][busyCell.getzCoord() + 1];
    }
}
