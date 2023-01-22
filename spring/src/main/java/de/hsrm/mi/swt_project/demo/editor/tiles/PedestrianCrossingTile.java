package de.hsrm.mi.swt_project.demo.editor.tiles;

import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByBike;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByCar;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.Walkable;

/**
 * @author Astrid Klemmer
 * tile that represents a pedestrian crossing
 */
public class PedestrianCrossingTile extends TrafficTile implements Walkable, DriveableByBike, DriveableByCar {

}
