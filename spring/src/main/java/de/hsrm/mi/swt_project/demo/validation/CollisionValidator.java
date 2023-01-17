package de.hsrm.mi.swt_project.demo.validation;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * This class validates wheather two moveable objects collide.
 * 
 * @author Felix Ruf
 * @author Alexandra Müller
 */
public class CollisionValidator implements Validator{

    Logger logger = LoggerFactory.getLogger(CollisionValidator.class);

    private MoveableObject moveableObject;
    private MoveableObject[] moveableObjects;

    /**
     * Creates a new CollisionValidator.
     * 
     * @param moveableObjects Map of moveable objects which will be checked for collisions.
     */
    public CollisionValidator(Map<String, MoveableObject> moveableObjects) {

        this.moveableObjects = new MoveableObject[moveableObjects.size()];

        MoveableObject[] moveableObjectValues = new MoveableObject[moveableObjects.size()];
        moveableObjects.values().toArray(moveableObjectValues);
        
        for(int i = 0; i < moveableObjectValues.length; i++) {
            this.moveableObjects[i] = moveableObjectValues[i].copy();
            this.moveableObjects[i].move();
        }
    }

    /**
     * Checks if the moveable object collides with any other moveable object.
     * 
     * @return true if the moveable object does not collide with any other moveable object
     */
    @Override
    public boolean validate() {
        
        for (MoveableObject moveableObject : moveableObjects) {
            if (doObjectsHit(this.moveableObject, moveableObject)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Checks if two moveable objects collide.
     * 
     * @param object1 first moveable object
     * @param object2 second moveable object
     * @return true if the moveable objects collide, else false
     */
    public boolean doObjectsHit(MoveableObject object1, MoveableObject object2) {
        float x = object1.getxPos() - object2.getxPos();
        float y = object1.getyPos() - object2.getyPos();

        double distanceSqrt = x * x + y * y;
        double maxDistanceSqrt = (object1.getHitboxRadius() + object2.getHitboxRadius()) * (object1.getHitboxRadius() + object2.getHitboxRadius());

        if(distanceSqrt > 0 && distanceSqrt <= maxDistanceSqrt) {
            return true;
        }

        return false;
    }

    /**
     * Sets the moveable object to be validated.
     * 
     * @param moveableObject moveable object to be validated
     */
    public void setMoveableObject(MoveableObject moveableObject) {
        this.moveableObject = moveableObject.copy();
        this.moveableObject.move();
    }
    
}
