package de.hsrm.mi.swt_project.demo.messaging;

/**
 * @author Marieke Schmitz 
 * Data Transfer Object that the server receives from the client to request validation of sent gamename
 */
public record GetGameConfigDTO(String mapName, String sessionName, int maximumPlayerCount, boolean npcsActivated) {
    
}
