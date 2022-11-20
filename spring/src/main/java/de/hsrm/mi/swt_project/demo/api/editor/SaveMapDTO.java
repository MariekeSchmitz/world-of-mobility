package de.hsrm.mi.swt_project.demo.api.editor;

/**
 * Data Transfer Object that the server receives from the client on map saving
 * @author Felix Ruf
 */
public record SaveMapDTO(String mapName, long id) {
    
}
