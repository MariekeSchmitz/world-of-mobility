package de.hsrm.mi.swt_project.demo.instancehandling;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoNpcToRemoveException extends RuntimeException {
    
}
