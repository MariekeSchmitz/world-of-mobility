package de.hsrm.mi.swt_project.demo.messaging;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.objecthandling.TrafficLightState;

/**
 * DTO that bundles updates of all moveable objects into one DTO
 * @author Marieke Schmitz 
 */
public record SendGameUpdateDTO(List<SendMoveableUpdateDTO> moveableUpdates, TrafficLightState trafficLightState) {
    
    public static SendGameUpdateDTO from(Map<String,MoveableObject> moveableObjects, TrafficLightState trafficLightState) {

        List<SendMoveableUpdateDTO> moveableUpdates = new LinkedList<>();

        for (Entry<String, MoveableObject> entry: moveableObjects.entrySet()) {

            String name = entry.getKey();
            MoveableObject moveable = entry.getValue();
            
            SendMoveableUpdateDTO sendMoveableUpdateDTO = SendMoveableUpdateDTO.from(name, moveable);
            moveableUpdates.add(sendMoveableUpdateDTO);

        }

        return new SendGameUpdateDTO(moveableUpdates, trafficLightState);

    }




}
