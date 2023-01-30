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

        @Override
        public int xSign() {
            return 0;
        }

        @Override
        public int ySign() {
            return 1;
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

        @Override
        public int xSign() {
            return 1;
        }

        @Override
        public int ySign() {
            return 1;
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

        @Override
        public int xSign() {
            return 1;
        }

        @Override
        public int ySign() {
            return 0;
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

        @Override
        public int xSign() {
            return 1;
        }

        @Override
        public int ySign() {
            return -1;
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

        @Override
        public int xSign() {
            return 0;
        }

        @Override
        public int ySign() {
            return -1;
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

        @Override
        public int xSign() {
            return -1;
        }

        @Override
        public int ySign() {
            return -1;
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

        @Override
        public int xSign() {
            return -1;
        }

        @Override
        public int ySign() {
            return 0;
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

        @Override
        public int xSign() {
            return -1;
        }

        @Override
        public int ySign() {
            return 1;
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

    /**
     * Indicats if movement with this orientation
     * increases, decresses or does not effect the
     * x-coordinate.
     * 
     * @return +1 if increasing
     *          0 if not affecting
     *         -1 if decreasing
     */
    public abstract int xSign();

    /**
     * Indicats if movement with this orientation
     * increases, decresses or does not effect the
     * y-coordinate.
     * 
     * @return +1 if increasing
     *          0 if not affecting
     *         -1 if decreasing
     */
    public abstract int ySign();

}
