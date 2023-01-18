package de.hsrm.mi.swt_project.demo.usermanagement;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

/**
 * Exception that occurs, when a User is already present. Users must be unique
 * 
 * @author Tom Gouthier, Marie Bohnert
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User is not unique.")
public class UserNotUniqueException extends RuntimeException {

}
