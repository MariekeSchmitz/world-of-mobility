package de.hsrm.mi.swt_project.demo.userManagement;

/**
 * interface with service methods for the userList
 * 
 * @author Tom Gouthier
 */
public interface UserService {

    /**
     * Adds user to the list of users
     * Throws Exception when User is not unique
     * 
     * @author Tom Gouthier
     */
    String addUser(String username) throws UserNotUniqueException;

    /**
     * removes User from the list of users
     * 
     * @author Tom Gouthier
     */
    void removeUser(String username);
}
