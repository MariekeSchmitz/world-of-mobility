package de.hsrm.mi.swt_project.demo.usermanagement;

/**
 * Exception that occurs, when a User is already present. Users must be unique
 * 
 * @author Tom Gouthier
 */
public class UserNotUniqueException extends Exception {

    public UserNotUniqueException(String message) {
        super(message);
    }
}
