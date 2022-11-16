package de.hsrm.mi.swt_project.demo;

import java.util.List;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

public record SendMapDTO (Tile [][] tiles, List <MoveableObject> npcs ){
    
    public static SendMapDTO from(GameMap map) {
        return new SendMapDTO(map.getTiles(), map.getNpcs());
    }


}
