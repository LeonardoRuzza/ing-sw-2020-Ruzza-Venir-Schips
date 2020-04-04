package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Board implements Cloneable, Serializable {

    final int levelHeight = 4;
    final int boardSide = 5;
    protected Cell[][][] cells;

    private Player playingNow;


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

    public void setPlayingNow(Player playingNow) {
        this.playingNow = playingNow;
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


    public Cell getFirstBuildableCell(int x, int y) {
        Cell busyCell = getLastBusyCell(x, y);  // Ultima cella occupata in altezza, o da un worker o da un blocco

        if (busyCell.getBlock() != null) {
            if (busyCell.getzCoord() == 3)
                return null;
            return cells[busyCell.getxCoord()][busyCell.getyCoord()][busyCell.getzCoord() + 1];  // Se c'è un blocco ritorna la cella superiore
        }
        else
            return cells[busyCell.getxCoord()][busyCell.getyCoord()][busyCell.getzCoord()];       // Se non c'è un blocco ritorna cella stessa
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
        return c.moveWorkerInto(w);
    }
    protected Block forceBuild(@NotNull Cell c /*,Worker w*/){
        return c.addBlock();
    }
    protected boolean forcebuildDorse(@NotNull Cell c){
        c.addDorse();
        return true;
    }

    protected void removeWorker(@NotNull Worker w) {
        Cell c = w.getCell();
        c.setWorkerNull();
    }

    protected void removeBlock(int x, int y){
        Block blockToRemove = blockInCell(x, y);           // Ottengo ultimo blocco costruito sulla cella e lo salvo in blockToRemove
        if (blockToRemove == null)  // Se non ci sono blocchi da rimuovere non fa nulla
            return;
        for(int z = 0; z < levelHeight; z++){
            Block tempBlock = cells[x][y][z].getBlock();   // Analizzo i blocchi a partire dal basso, appena trovo corrispondenza con blockToRemove lo rimuovo
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
        return result;
    }

    public void draw(){
        boolean linePrint = true;
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
                            System.out.print(Worker.Color.BLUE.getColorString() + "D" + Worker.Color.BLACK.getANSI_RESET());
                        }else{
                            System.out.print(getLastBusyCell(realX,realY).getzCoord()+1);
                        }
                    }else{
                        System.out.print(" ");
                    }
                    System.out.print(" ");
                    Worker actW = getLastBusyCell(realX,realY).getWorker();
                    if(actW!=null){
                        if(actW.equals(playingNow.workers[0])){
                            System.out.print(actW.getColor().getColorString() + "M"+" " + Worker.Color.BLACK.getANSI_RESET());
                        }else if(actW.equals(playingNow.workers[1])){
                            System.out.print(actW.getColor().getColorString() + "F"+" " + Worker.Color.BLACK.getANSI_RESET());
                        }else{
                            System.out.print(actW.getColor().getColorString() + "w"+" " + Worker.Color.BLACK.getANSI_RESET());
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
