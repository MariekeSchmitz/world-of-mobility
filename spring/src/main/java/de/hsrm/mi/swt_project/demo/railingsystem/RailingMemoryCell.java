package de.hsrm.mi.swt_project.demo.railingsystem;

import java.util.Random;

import de.hsrm.mi.swt_project.demo.controls.Direction;

/**
 * This class is a memory cell to remember a x and y pos 
 * of a moveable and if its already turned 
 * it also provides a method to check if the moveable left a tile 
 * that resets the already turned flag 
 * 
 * @author Fabio Bertels
 */
public class RailingMemoryCell {
    private int xPos;
    private int yPos;
    private boolean alreadyTurned;
    private Direction leftRight5050;
    Random random;

    /**
     * 
     * @param xPos x pos of the moveable
     * @param yPos y pos of the moveable
     */
    public RailingMemoryCell(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        this.random = new Random();
        leftRight5050 = random.nextInt(100) < 50 ? Direction.LEFT : Direction.RIGHT;
        this.alreadyTurned = false;
    }

    public int getXPos() {
        return xPos;
    }
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    public int getYPos() {
        return yPos;
    }
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
    public boolean isAlreadyTurned() {
        return alreadyTurned;
    }
    public void setAlreadyTurned(boolean alreadyTurned) {
        this.alreadyTurned = alreadyTurned;
    }

    /**
     * detects if the given x and y value are different to the current values 
     * also resets the random direction if they changed 
     * 
     * @param x new x position
     * @param y new y position
     * @return true if moveable left the tile (x or y changed) false if not 
     */
    public boolean leftTile(int x, int y){
        if(x != xPos || y != yPos){
            xPos = x; 
            yPos = y;
            leftRight5050 = random.nextInt(100) < 50 ? Direction.LEFT : Direction.RIGHT;
            return true;
        } else {
            return false;
        }
    }

    public Direction getRandomDirection(){
        return leftRight5050;
    }
    
}
