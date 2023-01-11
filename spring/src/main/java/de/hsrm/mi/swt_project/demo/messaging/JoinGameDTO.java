package de.hsrm.mi.swt_project.demo.messaging;

/**
 * @author Marieke Schmitz 
 * Data Transfer Object that the server receives from the client to request validation of sent gamename
 */
public record JoinGameDTO(String user, String type, int xPos, int yPos) {
    
}
