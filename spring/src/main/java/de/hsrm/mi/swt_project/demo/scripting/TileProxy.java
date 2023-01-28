package de.hsrm.mi.swt_project.demo.scripting;

import java.util.Arrays;
import java.util.List;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Placeable;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.editor.tiles.TrafficTile;

/**
 * This class restricts access to the Tile class,
 * so that users are able to access but not change
 * the tile properties.
 * 
 * @author Sascha Scheid
 * @author Finn Schindel
 */
public class TileProxy {
    
    protected Tile tile;

    /**
     * Creates new TileProxy.
     * 
     * @param tile Tile to restrict access to.
     */
    public TileProxy(Tile tile) {
        this.tile = tile;
    }

    /**
     * Gets type of the tile.
     * 
     * @return Type of the tile.
     */
    public Tiletype getType() {
        return this.tile.getType();
    }

    /**
     * Gets restricted proxy of the placeable
     * object that is placed on the tile.
     * 
     * @return Proxy for the placeable on the tile.
     */
    public PlaceableProxy getPlaceable() {
        Placeable placeable = this.tile.getPlacedObject();
        return new PlaceableProxy(placeable);
    }

    /**
     * Gets all orientations from which movement is allowed
     * to the tile.
     * 
     * @return Specified directions for traffic tiles, all
     *         existing directions for any other tile.
     */
    public List<Orientation> getAllowedOrientations() {

        if (tile instanceof TrafficTile trafficTile) {
            return trafficTile.getAllowedDirections();
        }

        // Movement on non-traffic tiles is allowed 
        // from any direction
        return Arrays.asList(Orientation.values());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tile == null) ? 0 : tile.hashCode());
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
        TileProxy other = (TileProxy) obj;
        if (tile == null) {
            if (other.tile != null)
                return false;
        } else if (!tile.equals(other.tile))
            return false;
        return true;
    }

    
}
