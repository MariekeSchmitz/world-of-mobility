package de.hsrm.mi.swt_project.demo.validation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.swt_project.demo.usermanagement.service.UserNotUniqueException;
import de.hsrm.mi.swt_project.demo.usermanagement.service.UsernameTooLongException;
import de.hsrm.mi.swt_project.demo.usermanagement.service.UsernameTooShortException;

/**
 * This class validates a user.
 * 
 * This validation includes the min and max length of a user.
 * It also validates the uniqueness of the username.
 * @author Tom Gouthier, Marie Bohnert
 */
public class LoginValidator implements Validator {
    private String username;
    private List<String> userList;

    private final int MAXLENGTH = 10;
    private final int MINLENGTH = 3;
    Logger logger = LoggerFactory.getLogger(getClass());

    public LoginValidator(String username, List<String> userList) {
        this.username = username;
        this.userList = userList;
    }

    @Override
    public boolean validate() throws UserNotUniqueException, UsernameTooShortException, UsernameTooLongException {
        if (username.length() < MINLENGTH) {
            logger.error("Username {} not long enough. Has to be {} or above letters.", username, MINLENGTH);
            throw new UsernameTooShortException();

        } else if (username.length() > MAXLENGTH) {
            logger.error("Username {} too long. Has to be {} or below letters.", username, MAXLENGTH);
            throw new UsernameTooLongException();
        } else if (userList.contains(username)) {
            logger.error("Username {} is not unique", username);
            throw new UserNotUniqueException();
        }
        logger.info("Username {} is valid", username);
        return true;
    }

}
