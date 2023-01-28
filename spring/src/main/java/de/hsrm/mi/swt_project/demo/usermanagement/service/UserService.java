package de.hsrm.mi.swt_project.demo.usermanagement.service;

/**
 * interface with service methods for the userList
 * 
 * @author Tom Gouthier, Marie Bohnert
 */
public interface UserService {

    /**
     * Adds user to the list of users
     * Throws Exception when User is not unique or too short/long
     * 
     * @author Tom Gouthier, Marie Bohnert
     */
    String addUser(String username) throws UserNotUniqueException, UsernameTooShortException, UsernameTooLongException;

    /**
     * removes User from the list of users
     * 
     * @author Tom Gouthier, Marie Bohnert
     */
    void removeUser(String username);

}
