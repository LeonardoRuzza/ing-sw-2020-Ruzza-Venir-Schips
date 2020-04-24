package it.polimi.ingsw.model;

import java.io.Serializable;

public class Cell implements Cloneable, Serializable {
    
    private int xCoord;
    private int yCoord;
    private int zCoord;
    private Worker workerInCell = null;
    private Block blockInCell = null;


// Builder

    /**
     * @see Board builder
     */
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
    public Block getBlock(){ return blockInCell; }


// Setter
    protected void setWorkerInCell(Worker workerInCell) {
        this.workerInCell = workerInCell;
    }
    protected void setWorkerNull() {
        this.workerInCell = null;
    }
    protected void setBlockNull() {
        this.blockInCell = null;
    }


// Generic Methods

    /** Add a DORSE in this cell, no matter what the {@code zCoord} of {@code this} cell is.
     * <p>
     *     This is intended to perform the effect of the <b>Card Atlas<b>
     * @see PlayerAtlas
     */
    protected void addDorse(){
        this.blockInCell = Block.DORSE;
    }

    /**Add a new block in {@code this} cell; this is only based on the {@code zCoord} of the cell;
     * <p>
     * {@code zCoord} = 0 ----> {@code Block} B1
     * <p>
     * {@code zCoord} = 1 ----> {@code Block} B2
     * <p>
     * {@code zCoord} = 2 ----> {@code Block} B3
     * <p>
     * {@code zCoord} = 3 ----> {@code Block} DORSE
     * <p>
     * @return  The Block just added in this cell
     */
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

    /**Move the worker passed as parameter in {@code this} cell.
     * Also mark the old Cell of the worker as free.
     * <p>
     *  <b>The function doesn't check if {@code this} cell is empty or not</b>
     * <p>
     * @param w  The worker you want to move in {@code this} cell
     * @return   {@code true} if {@code w} != null ; {@code false} otherwise
     */
    public Boolean moveWorkerInto(Worker w){
        if(w != null){
            Cell c = w.move(this);    // In c salvo la oldLocation del Worker, sposto il worker nella nuova cella
            if (c != null) {
                c.setWorkerNull();   // Libero la cella precedentemente occupata dal worker
            }
            workerInCell = w;   // Salvo riferimento a nuovo worker
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Cell) {
            Cell c = (Cell) obj;
            return this == c;
        }
        else
            return false;
    }

    @Override
    protected Cell clone() {
        Cell clonedCell = new Cell(xCoord, yCoord, zCoord);
        if(this.workerInCell != null){
            clonedCell.workerInCell = this.workerInCell.clone();
            if(this.workerInCell.getOldLocation() != null) {
                clonedCell.workerInCell.move(new Cell(this.workerInCell.getOldLocation().getxCoord(), this.workerInCell.getOldLocation().getyCoord(), this.workerInCell.getOldLocation().getzCoord()));
            }
            clonedCell.workerInCell.move(clonedCell);
        }else{
            clonedCell.workerInCell = null;
        }
        clonedCell.blockInCell = this.blockInCell;
        return clonedCell;
    }
}
