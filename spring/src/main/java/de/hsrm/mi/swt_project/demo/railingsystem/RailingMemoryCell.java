package de.hsrm.mi.swt_project.demo.railingsystem;

public class RailingMemoryCell {
    private int xPos;
    private int yPos;
    private boolean alreadyTurned;

    public RailingMemoryCell(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
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
            return true;
        } else {
            return false;
        }
    }
    
}
