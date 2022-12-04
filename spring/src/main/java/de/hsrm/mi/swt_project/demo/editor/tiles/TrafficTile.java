package de.hsrm.mi.swt_project.demo.editor.tiles;

import java.util.List;
import java.util.ArrayList;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;

/**
 * @author Tom Gouthier
 * Tiles that inherit this class are made for Traffic (cars,trains,.. excluding People) and are limited in their turnable directions
 */
public abstract class TrafficTile extends Tile {

    protected List<Orientation> allowedDirections = new ArrayList<>();

    @Override
    public void turn(Direction direction) {
        if (direction == Direction.LEFT) {
            this.orientation = orientation.prev();
             for (Orientation orientation : allowedDirections) {
                orientation = orientation.prev();
             }
        } else {
            this.orientation = orientation.next();
            for (Orientation orientation : allowedDirections) {
                orientation = orientation.next();
             }
        }

        
    }
    
}
