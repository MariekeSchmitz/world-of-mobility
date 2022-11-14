package de.hsrm.mi.swt_project.demo;

import java.util.List;

/**
 * @author Tom Gouthier
 * Tiles that inherit this class are made for Traffic (cars,trains,.. excluding People) and are limited in their turnable directions
 */
public abstract class TrafficTile extends Tile {

    protected List <Orientation> allowedDirections;
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
