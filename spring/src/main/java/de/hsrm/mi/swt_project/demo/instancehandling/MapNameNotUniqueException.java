package de.hsrm.mi.swt_project.demo.instancehandling;

/**
 * Exception for checking if a name is unique
 * 
 * @author Marie Bohnert, Tom Gouthier, Victoria Thee
 */
public class MapNameNotUniqueException extends Exception {

    public MapNameNotUniqueException(String message) {
        super(message);
    }
}
