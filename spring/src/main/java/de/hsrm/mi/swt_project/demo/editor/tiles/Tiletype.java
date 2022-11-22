
package de.hsrm.mi.swt_project.demo.editor.tiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hsrm.mi.swt_project.demo.controls.Orientation;

public enum Tiletype {

    STREET_STRAIGHT(Orientation.NORTH, Orientation.SOUTH) {
        @Override
        public Tile createTile() {
            return new Streetile();
        }
    },
    STREET_CURVE(Orientation.SOUTH, Orientation.WEST) {
        @Override
        public Tile createTile() {
            return new Streetile();
        }
    }, 
    STREET_CROSS(Orientation.NORTH, Orientation.EAST, Orientation.SOUTH, Orientation.WEST) {
        @Override
        public Tile createTile() {
            return new Streetile();
        }
    },
    STREET_T_CROSS(Orientation.EAST, Orientation.SOUTH, Orientation.WEST) {
        @Override
        public Tile createTile() {
            return new Streetile();
        }
    },
    RAIL_STRAIGHT(Orientation.NORTH, Orientation.SOUTH) {
        @Override
        public Tile createTile() {
            return new Railtile();
        }
    },
    RAIL_CURVE(Orientation.SOUTH, Orientation.WEST) {
        @Override
        public Tile createTile() {
            return new Railtile();
        }
    },
    SIDEWAY() {
        @Override
        public Tile createTile() {
            return new PassengerTile();
        }
    };

    List<Orientation> allowedOrientations = new ArrayList<>();

    private Tiletype(Orientation... o){
        allowedOrientations.addAll(Arrays.asList(o));
    }

    public abstract Tile createTile();
}
