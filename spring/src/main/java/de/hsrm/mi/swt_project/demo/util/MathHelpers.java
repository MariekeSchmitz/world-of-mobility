package de.hsrm.mi.swt_project.demo.util;

/**
 * This class provides methods for common mathematical
 * problems that do not belong to specific classes.
 * 
 * @author Sascha Scheid
 */
public class MathHelpers {

    /**
     * Private constructor to avoid instanciation.
     */
    private MathHelpers() {
        // do nothing
    }

    /**
     * Calculates euclidean distance between two positions in a plane.
     * 
     * @param aXPos First x-coordinate
     * @param aYPos First y-coordinate
     * @param otherXPos Second x-coordinate
     * @param otherYPos Second y-coordinate
     * @return Distance between (x1, y1) and (x2, y2)
     */
    public static float euclideanDistance(float aXPos, float aYPos, float otherXPos, float otherYPos) {
        float squaredDistance = squaredEuclideanDistance(aXPos, aYPos, otherXPos, otherYPos);
        return (float) Math.sqrt(squaredDistance);
    }

    /**
     * Calculates squared euclidean distance between two positions in a plane.
     * 
     * @param aXPos First x-coordinate
     * @param aYPos First y-coordinate
     * @param otherXPos Second x-coordinate
     * @param otherYPos Second y-coordinate
     * @return Squared distance between (x1, y1) and (x2, y2)
     */
    public static float squaredEuclideanDistance(float aXPos, float aYPos, float otherXPos, float otherYPos) {

        float xDist = otherXPos - aXPos;
        float yDist = otherYPos - aYPos;

        float xDistSquared = xDist * xDist;
        float yDistSquared = yDist * yDist;

        return xDistSquared + yDistSquared;
    }
    
}
