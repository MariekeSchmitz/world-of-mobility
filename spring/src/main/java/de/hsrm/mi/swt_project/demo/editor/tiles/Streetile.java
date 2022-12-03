package de.hsrm.mi.swt_project.demo.editor.tiles;

import java.util.List;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.placeable_objects.Placeable;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByBike;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByCar;

/**
 * @author Tom Gouthier
 * Tiles that represent a Street
 */
public class Streetile extends TrafficTile implements DriveableByBike, DriveableByCar{

    public Orientation getOrientation() {
        return super.getOrientation();
    }

    public List<Placeable> getPlacedObjects() {
        return super.getPlacedObjects();
    }

    public Tiletype getType() {
        return this.type;
    }

}
