package de.hsrm.mi.swt_project.demo.editor.tiles;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.CanHoldStreetObject;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByBike;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByCar;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Placeable;


/**
 * @author Tom Gouthier
 * Tiles that represent a Street
 */
public class Streetile extends TrafficTile implements DriveableByBike, DriveableByCar, CanHoldStreetObject{

    @Override
    public Orientation getOrientation() {
        return super.getOrientation();
    }

    public Placeable getPlacedObject() {
        return super.getPlacedObject();
    }


}
