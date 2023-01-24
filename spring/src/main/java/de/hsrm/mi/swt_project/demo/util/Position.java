package de.hsrm.mi.swt_project.demo.util;

/**
 * This class stores a x- and y-coordinate
 * to bundle them as position.
 * 
 * It can be used in scenarios where you 
 * need to check for equality of two
 * positions or want to store a position
 * inside a collection.
 * 
 * @author Sascha Scheid
 */
public class Position {
    
    protected int xPos;
    protected int yPos;

    /**
     * Creates new Position instance.
     * 
     * @param xPos x-coordinate of the position
     * @param yPos y-coordinate of the position
     */
    public Position(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Getter for x-coordinate.
     * 
     * @return x-coordinate
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Getter for y-coordiante.
     * 
     * @return y-coordinate
     */
    public int getYPos() {
        return yPos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + xPos;
        result = prime * result + yPos;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (xPos != other.xPos)
            return false;
        if (yPos != other.yPos)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Position [xPos=" + xPos + ", yPos=" + yPos + "]";
    }
}
