package de.hsrm.mi.swt_project.demo.movables;

import java.util.Locale;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;

public class MotorizedObject extends MoveableObject {

    public MotorizedObject() {
        this(0, 0);
    }

    /**
     * Creates an instance of motorized object.
     * 
     * @param xPos x-position of the object on the map
     * @param yPos y-position of the object on the map
     * @param maxVelocity Maximum velocity of the object.
     */
    public MotorizedObject(float xPos, float yPos) {
        this(Orientation.NORTH, xPos, yPos, 1);
    }

    /**
     * Creates an instance of motorized object.
     * 
     * @param orientation   Direction the object is looking at. Only N, E, S, W are valid direction for motorized objects.
     *                      Invalid directions will be adjusted: NE -> E, SE -> S, SW -> W, NW -> N
     * @param xPos x-position of the object on the map
     * @param yPos y-position of the object on the map
     * @param maxVelocity Maximum velocity of the object.
     */
    public MotorizedObject(Orientation orientation, float xPos, float yPos, float maxVelocity) {
        this.setXPos(xPos);
        this.setYPos(yPos);
        this.maxVelocity = maxVelocity;
        this.orientation = adjustOrientation(orientation);
    }

    @Override
    public MotorizedObject copy() {
        MotorizedObject copy = new MotorizedObject();

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

        float movement = this.currentVelocity * this.maxVelocity;

        switch (this.orientation) {
            
            case NORTH:
                this.yPos += movement;
                break;

            case EAST:
                this.xPos += movement;
                break;

            case SOUTH:
                this.yPos -= movement;
                break;

            case WEST:
                this.xPos -= movement;
                break;

            default:
                break;
                       
        }
    }

    @Override
    public void turn(Direction direction) {

        switch (direction) {
            case LEFT:
                this.orientation = this.orientation.prev().prev();  // can only turn 90 degrees
                break;
            case RIGHT:
                this.orientation = this.orientation.next().next();  // can only turn 90 degrees
                break;
            default:
                break;
            
        }

    }

    /**
     * Since motorized objects should only move along axis and never diagonally,
     * this method will adjust a given orientation to N-E-S-W orientation. 
     * 
     * Invalid directions will be adjusted: NE -> E, SE -> S, SW -> W, NW -> N
     * 
     * @param orientation orientation that eventually needs to be adjusted
     * @return orientation that is axis-aligned
     */
    protected Orientation adjustOrientation(Orientation orientation) {

        switch (orientation) {

            // valid orientations

            // in case of valid axis-aligned orientation
            // nothing has to be adjusted
            case NORTH, EAST, SOUTH, WEST: 
                return orientation;


            // invalid orientations

            // in case of invalid diagonal orientation
            // adjust to next valid orientation

            case NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST: 
                return orientation.next();

                
            // NORTH is always the default orientation
            default:
                return Orientation.NORTH;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof MotorizedObject)) {
            return false;
        }

        MotorizedObject mo = (MotorizedObject) o;
        return this.xPos == mo.xPos
            && this.yPos == mo.yPos
            && this.maxVelocity == mo.maxVelocity
            && this.currentVelocity == mo.currentVelocity
            && this.capacity == mo.capacity
            && this.orientation.equals(mo.orientation)
            && this.script.equals(mo.script);
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
        return String.format(Locale.ENGLISH, "MotorizedObject[xPos=%.2f,yPos=%.2f,curV=%.2f,maxV=%.2f,cap=%.2f,orientation=%s]", this.xPos, this.yPos, this.currentVelocity, this.maxVelocity, this.capacity, this.orientation);
    }

}
