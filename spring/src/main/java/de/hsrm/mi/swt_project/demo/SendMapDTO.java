package de.hsrm.mi.swt_project.demo;

import java.util.List;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;


/**
 * Data Transfer Object that the Server sends to the Client with the current Map state
 * @author Tom Gouthier
 */
public record SendMapDTO (Tile [][] tiles, List <MoveableObject> npcs ){
    
    public static SendMapDTO from(GameMap map) {
        return new SendMapDTO(map.getTiles(), map.getNpcs());
    }


}
