package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;
import java.io.Serializable;

public class Board implements Cloneable, Serializable {

    final int levelHeight = 4;
    final int boardSide = 5;
    protected Cell[][][] cells;

    private Player playingNow;


// Builder

    /**A board consist in <b>a tri-dimensional array </b> of cells; Dimensions of a cell are x,y,z.
     * <p>
     * The {@code zCoord}represent the level of the cell.
     * <p>
     * It's possible to build a block also in cell at {@code zCoord} = 0
     * On a cell at a certain level we can have both a worker and a block(of course following game's rules).
     * <p>
     * So, for example, if we have a worker and a block at (x,y,0), it means there is a worker at level1,
     * because there is a Block B1 present.
     * <p>
     *     <b>IMPORTANT: At (x,y,0), it's possible to have a worker without a block, and so the worker is standing at level0. It's not like this
     *        for cells at (x,y,z) with z>0; in this case it's only possible to have a block, or a worker and a block, since a worker cannot stand
     *        in an higher level if there is not a block under his feet.</b>
     */
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

    public void setPlayingNow(Player playingNow) {
        this.playingNow = playingNow;
    }

// Observers
    /**The function return the highest block built in the cell at (x,y)
     * <p>
     * @param x  {@code xCoord} of the cell that need to be checked
     * @param y  {@code yCoord} of the cell that need to be checked
     * @return  <b>The last Block built in the cell at (x,y)</b>; {@code null} if no block is detected
     */
    public Block blockInCell(int x, int y) {
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

    /** The function return the only worker present in the cell at (x,y), checking every level.
     * <p>
     * @param x    {@code xCoord} of the cell that need to be checked
     * @param y    {@code yCoord} of the cell that need to be checked
     * @return     <b>The Worker who stand on the cell at (x,y)</b>; {@code null} if no worker is detected
     */
    public Worker workerInCell(int x, int y) {
        Worker foundWorker;
        for(int z = 0; z < levelHeight; z++) {
            foundWorker = cells[x][y][z].getWorker();
            if (foundWorker != null)
                return foundWorker;
            else
                if(cells[x][y][z].getBlock() == null){
                    break;
                }
        }
        return null;
    }


// Getters
    /** Check the last "busy" level for the cell at (x,y).
     * <p>
     * A level is busy when is occupied by: a block, a worker, or both of them
     * <p>
     * @param x   {@code xCoord} of the cell that need to be checked
     * @param y   {@code yCoord} of the cell that need to be checked
     * @return     <b>The last busy cell at (x,y)</b>;   {@code null} if no worker or block is detected
     * @see      #Board()
     */
    public Cell getLastBusyCell(int x, int y){
        Block b = blockInCell(x,y);
        Worker w = workerInCell(x,y);

        if (b == null && w == null)
            return cells[x][y][0];

        for(int z = 0; z < levelHeight; z++){
            Block tempBlock = cells[x][y][z].getBlock();
            Worker tempWorker = cells[x][y][z].getWorker();
            if (tempBlock != null) {
                if (tempBlock.equals(b))
                    return cells[x][y][z];
            }
            if (tempWorker != null) {
                if (tempWorker.equals(w))
                    return cells[x][y][z];
            }
        }
        return null;
    }

    /** Return the first level at (x,y), where is possible to build a new block. Typically returns {@code  getLastBusyCell} +1
     * <p>
     * If there is only a worker at level0, so no block is present at(x,y) at any level, it returns cell[x][y][0].
     * <p>
     * @param x    {@code xCoord} of the cell that need to be checked
     * @param y    {@code yCoord} of the cell that need to be checked
     * @return      <b>The first buildable cell at (x,y)</b>;
     *              <p>
     *             {@code null} if there is a block(DORSE) built at level3, and
     *              so it's not possible build anymore at (x,y)
     * @see      #getLastBusyCell(int x, int y)
     */
    public Cell getFirstBuildableCell(int x, int y) {
        Cell busyCell = getLastBusyCell(x, y);

        if (busyCell.getBlock() != null) {
            if (busyCell.getzCoord() == 3)
                return null;
            return cells[busyCell.getxCoord()][busyCell.getyCoord()][busyCell.getzCoord() + 1];
        }
        else
            return cells[busyCell.getxCoord()][busyCell.getyCoord()][busyCell.getzCoord()];
    }


    /** Return the distance of various coord between 2 cells.
     * <p>
     * For the {@code xCoord} and {@code yCoord} it return the absolute value
     * of distance, <b>that can only be >0</b>
     * <p>
     * For the {@code zCoord} it return the simple value of distance, <b>that can be <0 or >0</b>
     * <p>
     * @param c1    First cell
     * @param c2    Second cell
     * @return      An array with 3 position:
     *              <p>
     *              <b>array[0]</b> = <p> |{@code c1.xCoord} - {@code c2.xCoord}|  note the absolute value </p>
     *              <p>
     *              <b>array[1]</b> = <p> |{@code c1.yCoord} - {@code c2.yCoord}|  note the absolute value </p>
     *              <p>
     *              <b>array[2]</b> = <p>{@code c1.zCoord} - {@code c2.zCoord}</p>
     *
     */
    public int[] getDistance(Cell c1, Cell c2){
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
    /**The "force" means that the function doesn't check if the move is allowed by the rules or not. These controls are made in match
     * @param c   Destination cell
     * @param w   Worker that need to be moved
     * @return   {@code true} if the move was correctly performed; {@code false} otherwise
     * @see Match
     */
    protected boolean forceMove(@NotNull Cell c, Worker w){
        return c.moveWorkerInto(w);
    }

    /** The "force" means that the function doesn't check if the move is allowed by the rules or not. These controls are made in match
     * @param c  Cell where a new block will be built
     * @return   The block just added
     * @see Match
     */
    protected Block forceBuild(@NotNull Cell c /*,Worker w*/){
        return c.addBlock();
    }

    /** It build a a DORSE, no matter what the level of cell is
     * @see #forceBuild(Cell)
     * @see PlayerAtlas
     */
    protected boolean forcebuildDorse(@NotNull Cell c){
        c.addDorse();
        return true;
    }

    protected void removeWorker(@NotNull Worker w) {
        Cell c = w.getCell();
        c.setWorkerNull();
    }

    /**Remove the highest block built at (x,y)
     * @param x   {@code xCoord} of the cell
     * @param y   {@code yCoord} of the cell
     * @see PlayerAres
     */
    protected void removeBlock(int x, int y){
        Block blockToRemove = blockInCell(x, y);
        if (blockToRemove == null)
            return;
        for(int z = 0; z < levelHeight; z++){
            Block tempBlock = cells[x][y][z].getBlock();
            if (tempBlock.equals(blockToRemove)) {
                cells[x][y][z].setBlockNull();
                return;
            }
        }
    }

    @Override
    protected Board clone() {
        final Board result = new Board();
        for(int z = 0; z < levelHeight; z++) {
            for (int y = 0; y < boardSide; y++) {
                for (int x = 0; x < boardSide; x++) {
                    result.cells[x][y][z] = this.cells[x][y][z].clone();
                }
            }
        }
        result.playingNow = this.playingNow.clone();
        return result;
    }

    /**Function to print a "screenshot" of the board to the player in the CLI
     * <p>
     * @param myPlayer  The player to whom the board will be printed
     */
    public void draw(Player myPlayer){
        boolean linePrint = true;
        System.out.print("It's " + playingNow.workers[0].getColor().getColorString() + playingNow.getNickname() + Worker.Color.RED.getANSI_RESET() + " turn\n");
        for(int y = 0; y < boardSide*2+1; y++){
            int realY = y/2;
            if(y == 0){
                System.out.print("    ");
                for(int x = 0; x < boardSide; x++){
                    System.out.print("  " + x + "   ");
                }
                System.out.println("");
            }
            for(int x = 0; x < boardSide; x++){

                int realX = x;
                if(linePrint){
                    if(x == 0){
                        System.out.print("    _____");
                    }else {
                        System.out.print("_____");
                    }
                }else{
                    if(x==0){
                        System.out.print(" "+ realY +" | ");
                    }else{
                        System.out.print("| ");
                    }
                    if (getLastBusyCell(realX,realY).getBlock()!=null){
                        if(getLastBusyCell(realX,realY).getBlock() == Block.DORSE){
                            System.out.print("\u001B[34m" + "D" + Worker.Color.RED.getANSI_RESET());
                        }else{
                            System.out.print(getLastBusyCell(realX,realY).getzCoord()+1);
                        }
                    }else{
                        System.out.print(" ");
                    }
                    System.out.print(" ");
                    Worker actW = getLastBusyCell(realX,realY).getWorker();
                    if(actW!=null){
                        if(actW.getGender() == myPlayer.workers[0].getGender() && actW.getColor() == myPlayer.workers[0].getColor()){
                            System.out.print(actW.getColor().getColorString() + "M"+" " + Worker.Color.RED.getANSI_RESET());
                        }else if(actW.getGender() == myPlayer.workers[1].getGender() && actW.getColor() == myPlayer.workers[1].getColor()){
                            System.out.print(actW.getColor().getColorString() + "F"+" " + Worker.Color.RED.getANSI_RESET());
                        }else{
                            System.out.print(actW.getColor().getColorString() + "w"+" " + Worker.Color.RED.getANSI_RESET());
                        }
                    }else{
                        System.out.print("  ");
                    }
                }
            }
            if(!linePrint){
                System.out.print("|");
            }else{
                System.out.print("____");
            }
            System.out.println("");
            linePrint = !linePrint;
        }
        System.out.println("");
    }
}
