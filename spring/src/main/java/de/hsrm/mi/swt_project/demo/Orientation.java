package de.hsrm.mi.swt_project.demo;

/**
 * This enum represents orientations something can look at.
 * 
 * @author Sascha Scheid
 */
public enum Orientation {

    NORTH {
        @Override
        public Orientation next() {
            return EAST;
        }

        @Override
        public Orientation prev() {
            return WEST;
        }
    },
    EAST {
        @Override
        public Orientation next() {
            return SOUTH;
        }

        @Override
        public Orientation prev() {
            return NORTH;
        }
    },
    SOUTH {
        @Override
        public Orientation next() {
            return WEST;
        }

        @Override
        public Orientation prev() {
            return EAST;
        }
    },
    WEST {
        @Override
        public Orientation next() {
            return NORTH;
        }

        @Override
        public Orientation prev() {
            return SOUTH;
        }
    };
    
    /**
     * Gets next orientation given that N-E-S-W are cyclic.
     * 
     * @return Next orientation in N-E-S-W cycle.
     */
    public abstract Orientation next();

    /**
     * Gets previous orientation given that N-E-S-W are cyclic.
     * 
     * @return Previous orientation in N-E-S-W cycle.
     */
    public abstract Orientation prev();

}
