
package de.hsrm.mi.swt_project.demo.editor.tiles;

import de.hsrm.mi.swt_project.demo.controls.Orientation;

public enum Tiletype {

    STREET_STRAIGHT() {
        @Override
        public Tile createTile() {
            TrafficTile tile = new Streetile();
            tile.allowedDirections.add(Orientation.NORTH);
            tile.allowedDirections.add(Orientation.SOUTH);
            tile.type = this;
            return tile;
        }
    },
    STREET_CURVE() {
        @Override
        public Tile createTile() {
            TrafficTile tile = new Streetile();
            tile.allowedDirections.add(Orientation.SOUTH);
            tile.allowedDirections.add(Orientation.WEST);
            tile.type = this;
            return tile;
        }
    }, 
    STREET_CROSS() {
        @Override
        public Tile createTile() {
            TrafficTile tile = new Streetile();
            tile.allowedDirections.add(Orientation.NORTH);
            tile.allowedDirections.add(Orientation.EAST);
            tile.allowedDirections.add(Orientation.SOUTH);
            tile.allowedDirections.add(Orientation.WEST);
            tile.type = this;
            return tile;
        }
    },
    STREET_T_CROSS() {
        @Override
        public Tile createTile() {
            TrafficTile tile = new Streetile();
            tile.allowedDirections.add(Orientation.EAST);
            tile.allowedDirections.add(Orientation.SOUTH);
            tile.allowedDirections.add(Orientation.WEST);
            tile.type = this;
            return tile;
        }
    },
    RAIL_STRAIGHT() {
        @Override
        public Tile createTile() {
            TrafficTile tile = new Railtile();
            tile.allowedDirections.add(Orientation.NORTH);
            tile.allowedDirections.add(Orientation.SOUTH);
            tile.type = this;
            return tile;
        }
    },
    RAIL_CURVE() {
        @Override
        public Tile createTile() {
            TrafficTile tile = new Railtile();
            tile.allowedDirections.add(Orientation.SOUTH);
            tile.allowedDirections.add(Orientation.WEST);
            tile.type = this;
            return tile;
        }
    },
    SIDEWAY() {
        @Override
        public Tile createTile() {
            Tile tile = new PassengerTile();
            tile.type = this;
            return tile;
        }
    },
    SIDEWAY_BICYCLE_CURVE() {
        @Override
        public Tile createTile() {
            Tile tile = new PassengerTile();
            tile.type = this;
            return tile;
        }
    },
    SIDEWAY_PASSENGER_CURVE() {
        @Override
        public Tile createTile() {
            Tile tile = new PassengerTile();
            tile.type = this;
            return tile;
        }
    },
    FARM() {
        @Override
        public Tile createTile() {
            Tile tile = new FarmTile();
            tile.type = this;
            return tile;
        }
    },
    GRASSTILE() {
        @Override
        public Tile createTile() {
            Tile tile = new GrassTile();
            tile.type = this;
            return tile;
        }
    };

    public abstract Tile createTile();
}
