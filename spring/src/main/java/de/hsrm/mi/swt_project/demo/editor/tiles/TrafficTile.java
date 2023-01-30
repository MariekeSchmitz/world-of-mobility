package de.hsrm.mi.swt_project.demo.editor.tiles;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;

/**
 * @author Tom Gouthier
 * Tiles that inherit this class are made for Traffic (cars,trains,.. excluding People) and are limited in their turnable directions
 */
public abstract class TrafficTile extends Tile {

    protected List<Orientation> allowedDirections = new ArrayList<>();

    /**
     * Gets directions from which something is
     * able to move onto the tile.
     * 
     * @return List containing allowed direction
     */
    public List<Orientation> getAllowedDirections() {
        return this.allowedDirections;
    }

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

    /**
     * Adds allowed ori
     * @param orientation
     */
    public void setAllowedDirections(List<Orientation> list) {
        allowedDirections = list;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((allowedDirections == null) ? 0 : allowedDirections.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TrafficTile other = (TrafficTile) obj;
        if (allowedDirections == null) {
            if (other.allowedDirections != null)
                return false;
        } else if (!allowedDirections.equals(other.allowedDirections))
            return false;
        return true;
    }
    
    
}
