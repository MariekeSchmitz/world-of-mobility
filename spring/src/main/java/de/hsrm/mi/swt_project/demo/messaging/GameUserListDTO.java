package de.hsrm.mi.swt_project.demo.messaging;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * DTO that bundles all Users in a Game from a MovableObjects HashMap
 * filtering out all MoveableObjects with 'NPC' in their Key
 * @author Fabio Bertels
 */
public record GameUserListDTO(List<String> users) {
    
    public static GameUserListDTO from(Map<String,MoveableObject> moveableObjects) {

        List<String> users = new LinkedList<>();

        for (String key: moveableObjects.keySet()) {
            if(!key.contains("NPC")){
                users.add(key);
            }
        }
        return new GameUserListDTO(users);
    }
}