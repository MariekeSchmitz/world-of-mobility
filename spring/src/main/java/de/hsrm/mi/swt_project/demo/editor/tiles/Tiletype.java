
package de.hsrm.mi.swt_project.demo.editor;

import java.util.Arrays;
import java.util.List;

import de.hsrm.mi.swt_project.demo.common.Orientation;

public enum Tiletype {

    STREET_STRAIGHT(Orientation.NORTH, Orientation.SOUTH),
    STREET_CURVE(Orientation.SOUTH, Orientation.WEST), 
    STREET_CROSS(Orientation.NORTH, Orientation.EAST, Orientation.SOUTH, Orientation.WEST),
    STREET_T_CROSS(Orientation.EAST, Orientation.SOUTH, Orientation.WEST),
    RAIL_STRAIGHT(Orientation.NORTH, Orientation.SOUTH),
    RAIL_CURVE(Orientation.SOUTH, Orientation.WEST),
    SIDEWAY();

    List<Orientation> allowedOrientations;

    private Tiletype(Orientation... o){
        allowedOrientations.addAll(Arrays.asList(o));
    }
}
