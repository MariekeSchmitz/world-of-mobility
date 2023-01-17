package de.hsrm.mi.swt_project.demo.controls;

/**
 * This enum represents orientations something can look at.
 * 
 * @author Sascha Scheid
 */
public enum Orientation {

    NORTH {
        @Override
        public Orientation next() {
            return NORTH_EAST;
        }

        @Override
        public Orientation prev() {
            return NORTH_WEST;
        }

        @Override
        public Orientation opposite() {
            return SOUTH;
        }
    },

    NORTH_EAST {
        @Override
        public Orientation next() {
            return EAST;
        }

        @Override
        public Orientation prev() {
            return NORTH;
        }

        @Override
        public Orientation opposite() {
            return SOUTH_WEST;
        }
    },

    EAST {
        @Override
        public Orientation next() {
            return SOUTH_EAST;
        }

        @Override
        public Orientation prev() {
            return NORTH_EAST;
        }

        @Override
        public Orientation opposite() {
            return WEST;
        }
    },

    SOUTH_EAST {
        @Override
        public Orientation next() {
            return SOUTH;
        }
    
        @Override 
        public Orientation prev() {
            return EAST;
        }

        @Override
        public Orientation opposite() {
            return NORTH_WEST;
        }
    },

    SOUTH {
        @Override
        public Orientation next() {
            return SOUTH_WEST;
        }

        @Override
        public Orientation prev() {
            return SOUTH_EAST;
        }

        @Override
        public Orientation opposite() {
            return NORTH;
        }
    },

    SOUTH_WEST {
        @Override
        public Orientation next() {
            return WEST;
        }

        @Override
        public Orientation prev() {
            return SOUTH;
        }

        @Override
        public Orientation opposite() {
            return NORTH_EAST;
        }
    },

    WEST {
        @Override
        public Orientation next() {
            return NORTH_WEST;
        }

        @Override
        public Orientation prev() {
            return SOUTH_WEST;
        }

        @Override
        public Orientation opposite() {
            return EAST;
        }
    },
    
    NORTH_WEST {
        @Override
        public Orientation next() {
            return NORTH;
        }

        @Override
        public Orientation prev() {
            return WEST;
        }

        @Override
        public Orientation opposite() {
            return SOUTH_EAST;
        }
    };
    
    /**
     * Gets next orientation given that N-NE-E-SE-S-SW-W-NW are cyclic.
     * 
     * @return Next orientation in N-NE-E-SE-S-SW-W-NW cycle.
     */
    public abstract Orientation next();

    /**
     * Gets previous orientation given that N-NE-E-SE-S-SW-W-NW are cyclic.
     * 
     * @return Previous orientation in N-NE-E-SE-S-SW-W-NW cycle.
     */
    public abstract Orientation prev();

    /**
     * Gets the opposite orientation of pairs
     * (N, S), (NE, SW), (E, W), (SE, NW).
     * 
     * @return Opposite orientation.
     */
    public abstract Orientation opposite();

}
