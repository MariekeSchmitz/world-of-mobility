package de.hsrm.mi.swt_project.demo.instancehandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that occurs when an invalid script is trying to be loaded into npc
 * 
 * @author Marie Bohnert, Tom Gouthier
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Script ist nicht erlaubt")
public class ScriptNotValidException extends RuntimeException {

}
