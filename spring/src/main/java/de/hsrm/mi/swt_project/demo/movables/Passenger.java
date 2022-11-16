package de.hsrm.mi.swt_project.demo.movables;

import de.hsrm.mi.swt_project.demo.Direction;
import de.hsrm.mi.swt_project.demo.Orientation;

public class Passenger extends MoveableObject {

    /**
     * Creates an instance of Passenger
     */
    public Passenger() {
        this(0, 0, 100);
    }

    /**
     * Creates an instance of Passenger.
     * 
     * @param xPos x-position of the object on the map
     * @param yPos y-position of the object on the map
     * @param maxVelocity Maximum velocity of the object.
     */
    public Passenger(float xPos, float yPos, float maxVelocity) {
        this(Orientation.NORTH, xPos, yPos, maxVelocity);
    }

    /**
     * Creates an instance of Passenger.
     * 
     * @param orientation   Direction the object is looking at.
     * @param xPos x-position of the object on the map
     * @param yPos y-position of the object on the map
     * @param maxVelocity Maximum velocity of the object.
     */
    public Passenger(Orientation orientation, float xPos, float yPos, float maxVelocity) {
        this.setXPos(xPos);
        this.setYPos(yPos);
        this.maxVelocity = maxVelocity;
        this.orientation = orientation;
    }

    @Override
    public Passenger copy() {
  
        Passenger copy = new Passenger();

        copy.xPos = this.xPos;
        copy.yPos = this.yPos;
        copy.maxVelocity = this.maxVelocity;
        copy.currentVelocity = this.currentVelocity;
        copy.capacity = this.capacity;
        copy.script = this.script;
        copy.orientation = this.orientation;

        return copy;
    }

    @Override
    public void move() {

        float straightMovement = this.currentVelocity * this.maxVelocity;
        float diagonalMovement = (float) (straightMovement / Math.sqrt(2));
        
        switch (this.orientation) {

            case NORTH:
                this.yPos += straightMovement;
                break;

            case NORTH_EAST:
                this.xPos += diagonalMovement;
                this.yPos += diagonalMovement;
                break;

            case EAST:
                this.xPos += straightMovement;
                break;

            case SOUTH_EAST:
                this.xPos += diagonalMovement;
                this.setYPos(this.yPos - diagonalMovement);
                break;

            case SOUTH:
                this.setYPos(this.yPos - straightMovement);
                break;

            case SOUTH_WEST:
                this.setXPos(this.xPos - diagonalMovement);
                this.setYPos(this.yPos - diagonalMovement);
                break;

            case WEST:
                this.setXPos(this.xPos - straightMovement);
                break;

            case NORTH_WEST:
                this.setXPos(this.xPos - diagonalMovement);
                this.yPos += diagonalMovement;
                break;

            default:
                break;

        }
    }

    @Override
    public void turn(Direction direction) {
        switch (direction) {
            case LEFT:
                this.orientation = this.orientation.prev();
                break;
            case RIGHT:
                this.orientation = this.orientation.next();
                break;
            default:
                break;
            
        }
    }
    
}
