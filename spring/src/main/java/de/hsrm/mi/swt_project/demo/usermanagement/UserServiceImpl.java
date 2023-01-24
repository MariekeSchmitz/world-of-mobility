package de.hsrm.mi.swt_project.demo.usermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of a UserService. Handles all actions on the List of users
 * 
 * @author Tom Gouthier, Marie Bohnert
 */
@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    UserList userList;

    @Autowired
    public UserServiceImpl(UserList userList) {
        this.userList = userList;
    }

    @Override
    public String addUser(String username) throws UserNotUniqueException, UsernameTooShortException {

        if (username.length() < 3) {
            logger.error("Username {} not long enough. Has to be 3 or above letters.", username);
            throw new UsernameTooShortException();

        } else if (userList.getUserList().contains(username)) {
            logger.error("Username {} is not unique", username);
            throw new UserNotUniqueException();
        } else {
            logger.info("User {} added to List of Users. Now logged in.", username);
            userList.getUserList().add(username);
            return username;
        }

    }

    @Override
    public void removeUser(String username) {

        if (userList.getUserList().contains(username)) {
            logger.info("User {} removed", username);
            userList.getUserList().remove(username);
        } else {
            logger.info("User {} is not logged in and could not be logged out", username);
        }
    }

}
