package it.polimi.ingsw.model;

public class Cell {

    private int xCoord;
    private int yCoord;
    private int zCoord;
    private Worker workerInCell;
    private Block blockInCell;

    public Cell(int x, int y, int z){
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
        workerInCell = null;
        blockInCell = null;
    }
    public Worker getWorker(){
        return workerInCell;
    }

    public Block getBlock(){
        return blockInCell;
    }

    public Block addBlock(){


    }

    public Boolean moveWorkerInto(Worker w){
        if(workerInCell==null) {workerInCell=w;}
        else {return false;}
        return true;

    }
}
