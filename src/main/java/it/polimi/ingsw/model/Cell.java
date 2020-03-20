package it.polimi.ingsw.model;

public class Cell {
    
    private int xCoord;
    private int yCoord;
    private int zCoord;
    private Worker workerInCell = null;
    private Block blockInCell = null;


// Builder
    public Cell(int x, int y, int z){
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }


// Getter
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


// Setter
    private void setWorkerInCell() {
        this.workerInCell = null;
    }


// Generic Methods
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
        if(w != null){
            Cell c = w.move(this);    // In c salvo la oldLocation del Worker, sposto il worker nella nuova cella
            if (c != null) {
                c.setWorkerInCell();   // Libero la cella precedentemente occupata dal worker
            }
            workerInCell = w;   // Salvo riferimento a nuovo worker
            return true;
        }
        return false;
    }
}
