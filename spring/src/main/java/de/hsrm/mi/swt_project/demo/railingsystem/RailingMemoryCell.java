package de.hsrm.mi.swt_project.demo.railingsystem;

import de.hsrm.mi.swt_project.demo.controls.Direction;

public class RailingMemoryCell {
    private int xPos;
    private int yPos;
    private boolean alreadyTurned;
    private Direction leftRight5050;

    public RailingMemoryCell(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        leftRight5050 = (int)(Math.random()*100) < 50 ? Direction.LEFT : Direction.RIGHT;
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

    public boolean leftTile(int x, int y){
        if(x != xPos || y != yPos){
            xPos = x; 
            yPos = y;
            leftRight5050 = (int)(Math.random()*100) < 50 ? Direction.LEFT : Direction.RIGHT;
            return true;
        } else {
            return false;
        }
    }

    public Direction getRandomDirection(){
        return leftRight5050;
    }
    
}
