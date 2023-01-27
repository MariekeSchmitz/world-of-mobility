package de.hsrm.mi.swt_project.demo.usermanagement.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that is thrown if Username is not long enough
 * 
 * @author Tom Gouthier, Marie Bohnert
 * 
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Username ist zu kurz. Mindestlänge sind drei Zeichen")
public class UsernameTooShortException extends RuntimeException {

}
