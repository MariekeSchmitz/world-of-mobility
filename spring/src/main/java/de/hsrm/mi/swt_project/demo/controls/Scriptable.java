package de.hsrm.mi.swt_project.demo.controls;

/**
 * Classes that implement this interface represent objects whose behaviour can be customized by a script. 
 * @author Marieke Schmitz
 */

public interface Scriptable {
   
    /**
     * Load script that customizes behaviour and safe it to object
     * @author Marieke Schmitz
     */
    public void loadScript(String script);

     /**
     * Returns the script that was loaded for the scriptable object.
     * @author Marieke Schmitz
     */
    public String getScript();
    
}
