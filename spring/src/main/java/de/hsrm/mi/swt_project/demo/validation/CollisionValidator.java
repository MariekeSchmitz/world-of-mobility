package de.hsrm.mi.swt_project.demo.validation;

import java.util.Map;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

public class CollisionValidator implements Validator{

    MoveableObject moveableObject;
    MoveableObject[] moveableObjects;

    public CollisionValidator(Map<String, MoveableObject> moveableObjects) {

        this.moveableObjects = new MoveableObject[moveableObjects.size()];

        MoveableObject[] moveableObjectValues = new MoveableObject[moveableObjects.size()];
        moveableObjects.values().toArray(moveableObjectValues);
        
        for(int i = 0; i < moveableObjectValues.length; i++) {
            this.moveableObjects[i] = moveableObjectValues[i].copy();
            this.moveableObjects[i].move();
        }
    }

    @Override
    public boolean validate() {
        
        // for(int i = 0; i < moveableObjects.length; i++) {
        //     for(int j = i+1; j < moveableObjects.length; i++) {
        //         doObjectsHit(moveableObjects[i], moveableObjects[j]);
        //     }
        // }
        for(int i = 0; i < moveableObjects.length; i++) {
            if(doObjectsHit(moveableObject, moveableObjects[i])) {
                return false;
            }
        }
        
        return true;
    }

    public boolean doObjectsHit(MoveableObject object1, MoveableObject object2) {

        double distance = Math.sqrt(Math.pow(object1.getXPos() - object2.getXPos(), 2) + Math.pow(object1.getYPos() - object2.getYPos(), 2));
        double maxDistance = object1.getHitboxRaidius() + object2.getHitboxRaidius();
        if(distance >= maxDistance) {
            return true;
        }

        return false;
    }

    public void setMoveableObject(MoveableObject moveableObject) {
        this.moveableObject = moveableObject.copy();
        moveableObject.move();
    }
    
}
