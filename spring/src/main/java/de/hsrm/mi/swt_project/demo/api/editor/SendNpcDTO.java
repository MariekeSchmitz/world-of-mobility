package de.hsrm.mi.swt_project.demo.api.editor;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableType;

public record SendNpcDTO(Orientation orientation, float xPos, float yPos, float maxVelocity, float capacity,
        float currentVelocity, String script, MoveableType type) {

    public static SendNpcDTO from(MoveableObject moveableObject) {
        return new SendNpcDTO(moveableObject.getOrientation(), moveableObject.getxPos(), moveableObject.getyPos(),
                moveableObject.getMaxVelocity(),
                moveableObject.getCapacity(), moveableObject.getCurrentVelocity(), moveableObject.getScript(),
                moveableObject.getType());
    }
}