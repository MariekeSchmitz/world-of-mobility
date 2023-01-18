package de.hsrm.mi.swt_project.demo.usermanagement;

/**
 * Data Transfer Object with user data sent by the client
 * 
 * @author Tom Gouthier, Marie Bohnert
 */
public record GetUserDTO(String name) {

    /**
     * creates and returns DTO
     * 
     * @author Tom Gouthier, Marie Bohnert
     */
    public GetUserDTO from(String name) {
        return new GetUserDTO(name);
    }
}
