package de.hsrm.mi.swt_project.demo.messaging;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * DTO that bundles all necessary information on one moveable object
 * @author Marieke Schmitz
 */

public record SendMoveableUpdateDTO (
    String user,
    float xPos,
    float yPos,
    String classname,
    float capacity,
    float velocity,
    Orientation orientation
) {

    public static SendMoveableUpdateDTO from(String username, MoveableObject moveableObject) {
        return new SendMoveableUpdateDTO(
            username, 
            moveableObject.getXPos(), 
            moveableObject.getYPos(),
            moveableObject.getType().toString(),
            moveableObject.getCapacity(),
            moveableObject.getCurrentVelocity(),
            moveableObject.getOrientation());
    }
} 