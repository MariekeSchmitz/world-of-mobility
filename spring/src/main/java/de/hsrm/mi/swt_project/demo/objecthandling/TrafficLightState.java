package de.hsrm.mi.swt_project.demo.objecthandling;

 /**
  * Enum for communticating global traffic light state.
  *  @author Timothy Doukhin, Finn Schindel
  */
public enum TrafficLightState {
    NORTHSOUTH{
        public TrafficLightState next() {
            return NORTHSOUTHEASTWEST;
        }
    },
    EASTWEST {
        public TrafficLightState next() {
            return EASTWESTNORHSOUTH;
        }
    },
    EASTWESTNORHSOUTH{
        public TrafficLightState next() {
            return NORTHSOUTH;
        }
    },
    NORTHSOUTHEASTWEST{
        public TrafficLightState next() {
            return EASTWEST;
        }
    }; 

    public abstract TrafficLightState next();
}
