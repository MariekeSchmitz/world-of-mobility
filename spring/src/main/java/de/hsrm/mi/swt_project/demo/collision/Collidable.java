package de.hsrm.mi.swt_project.demo.collision;

/**
 * Classes implementing this interface provide
 * methods to get the information needed to
 * calculate if they collide.
 * 
 * As our collision model is based on overlapping
 * circles, the must provide x- and y-coordinate
 * to calculate the distance between two collidable
 * objects as well as the radius to check if they
 * overlap.
 * 
 * @author Sascha Scheid
 */
public interface Collidable {
    
    /**
     * Returns x-coordinate of the collidable object.
     * 
     * @return x-coordinate
     */
    float getXPos();

    /**
     * Returns y-coordinate of the collidable object.
     * 
     * @return y-coordinate
     */
    float getYPos();

    /**
     * Returns radius of the collidable object.
     * 
     * @return radius
     */
    float getRadius();

}
