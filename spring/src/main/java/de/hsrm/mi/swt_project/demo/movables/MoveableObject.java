package de.hsrm.mi.swt_project.demo.movables;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Moveable;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.controls.Scriptable;
import de.hsrm.mi.swt_project.demo.controls.Turnable;

/**
 * This class represents objects that can change their position
 * and therefore move around the map.
 * 
 * @author Sascha Scheid
 */
public abstract class MoveableObject implements Moveable, Scriptable, Turnable {
    
    protected static final float MIN_VELOCITY = 0.0f;
    protected static final float MAX_VELOCITY = 1.0f;

    protected static final float MIN_CAPACITY = 0.0f;
    protected static final float MAX_CAPACITY = 1.0f;

    protected Orientation orientation;

    protected float xPos;
    protected float yPos;
    protected float maxVelocity;

    protected float capacity = 1;
    protected float currentVelocity = 0;
    protected String script = "";


    /**
     * Gets orientation of the movable object.
     * 
     * @return orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Gets x-position of the movable object in the map.
     * 
     * @return x-Position
     */
    public float getXPos() {
        return xPos;
    }

    /**
     * Gets y-position of the movable object in the map.
     * 
     * @return x-Position
     */
    public float getYPos() {
        return yPos;
    }

    /**
     * Gets capacity of the object. Capacity indicates
     * currently available resource for performing movements.
     * 
     * E.g. a capacity of 0.23 means that there are 23% of resources left.
     * 
     * @return Value in interval [0, 1]
     */
    public float getCapacity() {
        return capacity;
    }

    /**
     * Gets currentVelocity in relation to
     * maxVelocity.
     * 
     * @return Value in interval [0, 1]
     */
    public float getCurrentVelocity() {
        return currentVelocity;
    }

    /**
     * Sets new x-position of the movable object.
     * @param xPos New x-position
     */
    public void setXPos(float xPos) {
        this.xPos = Float.max(0.0f, xPos);
    }

    /**
     * Sets new y-position of the movable object.
     * @param yPos New y-position
     */
    public void setYPos(float yPos) {
        this.yPos = Float.max(0.0f, yPos);
    }

    /**
     * Sets capacity to given value.
     * 
     * Since capacity is relative to maxVelocity,
     * given value will be clipped to interval [0, 1].
     *  
     * @param capacity New capacity.
     */
    public void setCapacity(float capacity) {
        capacity = Float.max(MIN_CAPACITY, Float.min(MAX_CAPACITY, capacity));
        this.capacity = capacity;
    }

    /**
     * Sets currentVelocity to given value.
     * 
     * Since currentVelocity is relative to maxVelocity,
     * given value will be clipped to interval [0, 1].
     *  
     * @param velocity New velocity.
     */
    public void setCurrentVelocity(float velocity) {
        velocity = Float.max(MIN_VELOCITY, Float.min(MAX_VELOCITY, velocity));
        this.currentVelocity = velocity;
    }

    @Override
    public void loadScript(String script) {
        this.script = script;        
    }

    @Override
    public void executeScript() {
        if (this.script != null && !this.script.isEmpty()) {
            // perform script execution
        }
    }

    /**
     * Creates a copy of the instance.
     * 
     * @return Copy of the object.
     */
    public abstract MoveableObject copy();

    @Override
    public abstract void move();

    @Override
    public abstract void turn(Direction direction);

}
