package de.hsrm.mi.swt_project.demo.movables;

import de.hsrm.mi.swt_project.demo.Direction;
import de.hsrm.mi.swt_project.demo.Orientation;

public class MotorizedObject extends MoveableObject {

    public MotorizedObject() {
        this(0, 0, 100);
    }

    /**
     * Creates an instance of motorized object.
     * 
     * @param xPos x-position of the object on the map
     * @param yPos y-position of the object on the map
     * @param maxVelocity Maximum velocity of the object.
     */
    public MotorizedObject(float xPos, float yPos, float maxVelocity) {
        this(Orientation.NORTH, xPos, yPos, maxVelocity);
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

            case NORTH: // Fall through
            case EAST:  // Fall through
            case SOUTH: // Fall through
            case WEST:  // Fall through

                // in case of valid axis-aligned orientation
                // nothing has to be adjusted
                return orientation;

            // invalid orientations

            case NORTH_EAST:    // Fall through
            case SOUTH_EAST:    // Fall through
            case SOUTH_WEST:    // Fall through
            case NORTH_WEST:    // Fall through

                // in case of invalid diagonal orientation
                // adjust to next valid orientation
                return orientation.next();
            
            default:

                // NORTH is always the default orientation
                return Orientation.NORTH;
        }
    }
    
}
