package it.polimi.ingsw.model;

public class Cell {
    
    private int xCoord;
    private int yCoord;
    private int zCoord;
    private Worker workerInCell = null;
    private Block blockInCell = null;

    
    public Cell(int x, int y, int z){
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    public int getxCoord() {
        return xCoord;
    }
    public int getyCoord() {
        return yCoord;
    }
    public int getzCoord() {
        return zCoord;
    }
    
    public Worker getWorker(){
        return workerInCell;
    }

    public Block getBlock(){
        return blockInCell;
    }

    public Block addBlock(){
        switch (this.zCoord) {
            case 0:
                this.blockInCell = Block.B1;
                break;
            case 1:
                this.blockInCell = Block.B2;
                break;
            case 2:
                this.blockInCell = Block.B3;
                break;
            case 3:
                this.blockInCell = Block.DORSE;
                break;
            default:
                break;
        }
        return this.blockInCell;
    }

    public Boolean moveWorkerInto(Worker w){
        if(workerInCell==null) {
            workerInCell=w;
        }else {
            return false;
        }
        return true;
    }
}
