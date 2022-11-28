package de.hsrm.mi.swt_project.demo.userManagement;

/**
 * @author Tom Gouthier
 *         Exception that is thrown if Username is not long enough
 */
public class UsernameTooShortException extends Exception {

    public UsernameTooShortException(String message) {
        super(message);
    }
}
