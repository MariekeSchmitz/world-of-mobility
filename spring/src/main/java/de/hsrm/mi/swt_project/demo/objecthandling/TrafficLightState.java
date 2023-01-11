package de.hsrm.mi.swt_project.demo.objecthandling;

 /**
  * Enum for communticating global traffic light state.
  *  @author Timothy Doukhin, Finn Schindel
  */
public enum TrafficLightState {
    NORTHSOUTH{
        public TrafficLightState next() {
            return EASTWEST;
        }
    },
    EASTWEST {
        public TrafficLightState next() {
            return NORTHSOUTH;
        }
    };

    public abstract TrafficLightState next();
}
