package it.polimi.ingsw.model;

public class Board {

    final int levelHeight = 4;
    final int boardSide = 5;
    private Cell[][][] cells;


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

    public Block blockInCell(int x, int y) {
        Block lastBlock = null;
        for(int z = 0; z < levelHeight; z++){
            switch (cells[x][y][z].getBlock()) {
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
            if (lastBlock == null){
                break;
            }
        }
        return lastBlock;
    }

    public Worker workerInCell(int x, int y) {
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


    public Cell getLastBusyCell(int x, int y){
        int z = 0;
        blockInCell(x,y);
        return null;
    }

    public int[] getDistance(Cell c1, Cell c2){
        return null;
    }

 /* protected boolean forceMove(int x, int y, Worker worker){

    }

    protected boolean forceBuild(int x, int y, Worker worker){

    }*/
}
