package de.hsrm.mi.swt_project.demo.messaging;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * DTO that bundles updates of all moveable objects into one DTO
 * @author Marieke Schmitz 
 */
public record SendGameUpdateDTO(List<SendMoveableUpdateDTO> moveableUpdates) {
    
    public static SendGameUpdateDTO from(HashMap<String,MoveableObject> moveableObjects) {

        List<SendMoveableUpdateDTO> moveableUpdates = new LinkedList<>();

        for (String key: moveableObjects.keySet()) {
            
            SendMoveableUpdateDTO sendMoveableUpdateDTO = SendMoveableUpdateDTO.from(key,moveableObjects.get(key));
            moveableUpdates.add(sendMoveableUpdateDTO);

        }

        return new SendGameUpdateDTO(moveableUpdates);

    }




}
