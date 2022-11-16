package de.hsrm.mi.swt_project.demo.updates;
/**
 * Classes that implement this interface represent instances with objects who can change their state (e.g. position) and hence can cause updates. 
 * @author Marieke Schmitz
 */
public interface Updateable {

     /**
     * Update the state of all objects of instance (e.g. NPCs)
     * @author Marieke Schmitz
     */
    public void update();
}
