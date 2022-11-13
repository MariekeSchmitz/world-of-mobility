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
        
    }
    
}
