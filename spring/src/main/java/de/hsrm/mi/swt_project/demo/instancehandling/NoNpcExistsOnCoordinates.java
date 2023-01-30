package de.hsrm.mi.swt_project.demo.instancehandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that occurs when no Npc exists on given position
 * 
 * @author Marie Bohnert, Tom Gouthier
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Der NPC existiert hier nicht.")
public class NoNpcExistsOnCoordinates extends RuntimeException {

}
