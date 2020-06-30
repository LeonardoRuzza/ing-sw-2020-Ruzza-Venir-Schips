package it.polimi.ingsw.model;

import java.io.Serializable;

public class Cell implements Cloneable, Serializable {
    
    private final int xCoord;
    private final int yCoord;
    private final int zCoord;
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

    /**
     * Getter for x coordinate of cell
     * @return x coordinate
     */
    public int getxCoord() {
        return xCoord;
    }
    /**
     * Getter for y coordinate of cell
     * @return y coordinate
     */
    public int getyCoord() {
        return yCoord;
    }
    /**
     * Getter for z coordinate of cell
     * @return z coordinate
     */
    public int getzCoord() {
        return zCoord;
    }
    /**
     * Getter for worker of cell
     * @return worker in cell if present null otherwise
     */
    public Worker getWorker(){
        return workerInCell;
    }
    /**
     * Getter for block of cell
     * @return block in cell if present null otherwise
     */
    public Block getBlock(){ return blockInCell; }


// Setter

    /**
     * Setter for worker of cell
     * @param workerInCell worker to add in cell
     */
    protected void setWorkerInCell(Worker workerInCell) {
        this.workerInCell = workerInCell;
    }

    /**
     * Set no worker in cell
     */
    protected void setWorkerNull() {
        this.workerInCell = null;
    }

    /**
     * Set no block in cell
     */
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
    protected Block addBlock(){
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
    protected Boolean moveWorkerInto(Worker w){
        if(w != null){
            Cell c = w.move(this);
            if (c != null) {
                c.setWorkerNull();
            }
            workerInCell = w;
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
