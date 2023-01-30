package de.hsrm.mi.swt_project.demo.usermanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swt_project.demo.validation.LoginValidator;

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
    public String addUser(String username)
            throws UserNotUniqueException, UsernameTooShortException, UsernameTooLongException {

        LoginValidator loginValidator = new LoginValidator(username, userList.getUserList());

        loginValidator.validate();
        userList.getUserList().add(username);
        logger.info("User {} added to List of Users. Now logged in.", username);
        return username;
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
