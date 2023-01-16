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

            this.orientation = orientation.prev().prev();
            allowedDirections = allowedDirections.stream().map(current -> current.prev().prev()).toList();

        } else {

            this.orientation = orientation.next().next();
            allowedDirections = allowedDirections.stream().map(current -> current.next().next()).toList();
            
        }

    }
    
}
