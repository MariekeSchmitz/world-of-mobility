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

}
