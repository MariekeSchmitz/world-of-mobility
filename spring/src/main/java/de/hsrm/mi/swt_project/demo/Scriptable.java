package de.hsrm.mi.swt_project.demo;

/**
 * Classes that implement this interface represent objects whose behaviour can be customized by a script. 
 * @author Marieke Schmitz
 */

public interface Scriptable {
   
    /**
     * Load script that customizes behaviour and safe it to object
     * @author Marieke Schmitz
     */
    public void loadScript();

     /**
     * Execute script of the object to use it as behaviour 
     * @author Marieke Schmitz
     */
    public void executeScript();
    
}
