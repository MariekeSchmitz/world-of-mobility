package de.hsrm.mi.swt_project.demo.usermanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that is thrown if Username is not long enough
 * 
 * @author Tom Gouthier
 * 
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Username is too short. Three or more letters needed.")
public class UsernameTooShortException extends RuntimeException {

}
