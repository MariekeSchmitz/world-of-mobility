package de.hsrm.mi.swt_project.demo.movables;

import java.util.Locale;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;

public class Passenger extends MoveableObject {

    protected static final float HITBOX_RADIUS = 0.04f;

    /**
     * Creates an instance of Passenger
     */
    public Passenger() {
        this(0, 0);
    }

    /**
     * Creates an instance of Passenger.
     * 
     * @param xPos x-position of the object on the map
     * @param yPos y-position of the object on the map
     * @param maxVelocity Maximum velocity of the object.
     */
    public Passenger(float xPos, float yPos) {
        this(Orientation.NORTH, xPos, yPos, 0.1f);
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
        copy.type = this.type;

        return copy;
    }

    @Override
    public void move() {

        float straightMovement = this.currentVelocity * this.maxVelocity;
        float diagonalMovement = (float) (straightMovement / Math.sqrt(2));
        
        switch (this.orientation) {

            case NORTH, EAST, SOUTH, WEST:
                this.xPos += this.orientation.xSign() * straightMovement;
                this.yPos += this.orientation.ySign() * straightMovement;
                break;

            case NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST:
                this.xPos += this.orientation.xSign() * diagonalMovement;
                this.yPos += this.orientation.ySign() * diagonalMovement;
                break;

            default:
                break;

        }
    }

    /**
     * Turns passenger object to the given direction.
     * Inverts the turn if the object is moving backwards.
     */
    @Override
    public void turn(Direction direction) {
        switch (direction) {
            case LEFT:
                if(this.currentVelocity < 0) 
                    this.orientation = this.orientation.next();
                else
                    this.orientation = this.orientation.prev();
                break;
            case RIGHT:
            if (this.currentVelocity < 0)
                    this.orientation = this.orientation.prev();
                else
                    this.orientation = this.orientation.next();
                break;
            default:
                break;
            
        }
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Passenger)) {
            return false;
        }

        Passenger p = (Passenger) o;
        return this.xPos == p.xPos
            && this.yPos == p.yPos
            && this.maxVelocity == p.maxVelocity
            && this.currentVelocity == p.currentVelocity
            && this.capacity == p.capacity
            && this.orientation.equals(p.orientation)
            && this.script.equals(p.script);
    }

    @Override
    public int hashCode() {
        int result = Float.hashCode(this.xPos);
        result = 31 * result + Float.hashCode(this.yPos);
        result = 31 * result + Float.hashCode(this.maxVelocity);
        result = 31 * result + Float.hashCode(this.currentVelocity);
        result = 31 * result + Float.hashCode(this.capacity);
        result = 31 * result + this.orientation.hashCode();
        result = 31 * result + this.script.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "Passenger[xPos=%.2f,yPos=%.2f,curV=%.2f,maxV=%.2f,cap=%.2f,orientation=%s]", this.xPos, this.yPos, this.currentVelocity, this.maxVelocity, this.capacity, this.orientation);
    }

    @Override
    public float getRadius() {
        return HITBOX_RADIUS;
    }
    
}
