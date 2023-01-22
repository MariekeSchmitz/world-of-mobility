package de.hsrm.mi.swt_project.demo.instancehandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that occurs when an npc is placed on a position its not supposed to
 * be placed on
 * 
 * @author Marie Bohnert, Tom Gouthier
 */
@ResponseStatus(code=HttpStatus.FORBIDDEN, reason="NPC darf hier nicht platziert werden.")
public class NpcNotPlaceableException extends RuntimeException {

}
