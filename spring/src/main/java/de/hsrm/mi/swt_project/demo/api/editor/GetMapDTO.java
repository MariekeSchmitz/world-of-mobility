package de.hsrm.mi.swt_project.demo.api.editor;

/**
 * Data Transfer Object that the server receives from the client to request a specific map to be send
 * @author Felix Ruf
 */
public record GetMapDTO(String mapName, long mapId) {
    
}
