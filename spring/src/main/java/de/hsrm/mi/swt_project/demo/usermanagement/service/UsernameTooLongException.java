package de.hsrm.mi.swt_project.demo.usermanagement.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that is thrown if Username is too long
 * 
 * @author Tom Gouthier, Marie Bohnert
 * 
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Username ist zu lang. Höchstlänge sind zehn Zeichen")
public class UsernameTooLongException extends RuntimeException{
    
}
