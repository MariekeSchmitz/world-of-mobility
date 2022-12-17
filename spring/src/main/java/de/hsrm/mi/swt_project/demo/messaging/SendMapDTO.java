package de.hsrm.mi.swt_project.demo.messaging;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.instancehandling.GameMap;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * Data Transfer Object that the Server sends to the Client with the current Map
 * state
 * 
 * @author Tom Gouthier
 */
public record SendMapDTO(Tile[][] tiles, List<MoveableObject> npcs) {

    public static SendMapDTO from(GameMap map) {
        return new SendMapDTO(map.getTiles(), map.getNpcs());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(tiles);
        result = prime * result + ((npcs == null) ? 0 : npcs.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SendMapDTO other = (SendMapDTO) obj;
        if (!Arrays.deepEquals(tiles, other.tiles))
            return false;
        if (npcs == null) {
            if (other.npcs != null)
                return false;
        } else if (!npcs.equals(other.npcs))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SendMapDTO{" +
                "tiles=" + Arrays.toString(tiles) +
                ", npcs=" + npcs.toString() + "}";
    }

}
