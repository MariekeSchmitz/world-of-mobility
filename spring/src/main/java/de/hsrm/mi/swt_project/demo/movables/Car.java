package de.hsrm.mi.swt_project.demo.movables;

import de.hsrm.mi.swt_project.demo.controls.Orientation;

public class Car extends MotorizedObject {
    public Car() {
        super(0, 0);
    }

    /**
     * Creates an instance of motorized object.
     * 
     * @param xPos x-position of the object on the map
     * @param yPos y-position of the object on the map
     * @param maxVelocity Maximum velocity of the object.
     */
    public Car(float xPos, float yPos) {
        super(Orientation.NORTH, xPos, yPos, 0.5f);
    }
}
