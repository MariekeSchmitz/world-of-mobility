package de.hsrm.mi.swt_project.demo.userManagement;

/**
 * Data Transfer Object with user data sent by the client
 * 
 * @author Tom Gouthier
 */
public record GetUserDTO(String name) {

    /**
     * creates and returns DTO
     * 
     * @author Tom Gouthier
     */
    public GetUserDTO from(String name) {
        return new GetUserDTO(name);
    }
}
