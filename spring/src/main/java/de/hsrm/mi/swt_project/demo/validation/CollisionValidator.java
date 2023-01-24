package de.hsrm.mi.swt_project.demo.validation;

import java.util.Collection;
import java.util.List;

import de.hsrm.mi.swt_project.demo.collision.Collidable;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.util.MathHelpers;

/**
 * This class validates wheather a collidable object
 * collides with any of the other .
 * 
 * @author Felix Ruf
 * @author Alexandra Müller
 * @author Sascha Scheid
 */
public class CollisionValidator implements Validator {

    protected Collidable collidable;
    protected List<? extends Collidable> otherCollidables;

    /**
     * Creates a new CollisionValidator.
     *
     * @param collidableToValidate Collidable that will be checked for collision
     * @param allCollidables Collection containing all collidables that will be checked.
     */
    public CollisionValidator(Collidable collidableToValidate, Collection<? extends Collidable> allCollidables) {

        if (collidableToValidate instanceof MoveableObject moveable) {
            MoveableObject copy = moveable.copy();
            copy.move();
            this.collidable = copy;
        } else {
            this.collidable = collidableToValidate;
        }

        // No need to check collision with itself, so
        // remove object to check from all collidables.
        this.otherCollidables = allCollidables
            .stream()
            .filter(
                aCollidable -> aCollidable != collidableToValidate
            ).toList();
    }

    /**
     * Checks if the collidable object that will be validated
     * collides with any of the other collidable objects.
     * 
     * @return True if the collidable object does not collide with 
     *         any other moveable object, else false
     */
    @Override
    public boolean validate() {
        return this.otherCollidables
            .stream()
            .noneMatch(
                aCollidable -> this.collidesWith(aCollidable)
            );
    }

    /**
     * Checks if the collidable to validate collides with
     * anouther collidable.
     * 
     * @param other Collidable to check 
     * @return true if the moveable objects collide, else false
     */
    protected boolean collidesWith(Collidable other) {

        float thisXPos = this.collidable.getXPos();
        float thisYPos = this.collidable.getYPos();
        float otherXPos = other.getXPos();
        float otherYPos = other.getYPos();

        float minDistance = this.collidable.getRadius() + other.getRadius();

        float squaredDistance = MathHelpers.squaredEuclideanDistance(thisXPos, thisYPos, otherXPos, otherYPos);
        float squaredMinDistance = minDistance * minDistance;

        return squaredDistance <= squaredMinDistance;
    }    
}
