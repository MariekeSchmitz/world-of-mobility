package de.hsrm.mi.swt_project.demo.usermanagement;

/**
 * Data Transfer Object with userdata and potential error message sent to client
 * 
 * @author Tom Gouthier, Marie Bohnert
 */
public record SendUserDTO(String username) {

    /**
     * creates DTO instance
     * 
     * @author Tom Gouthier, Marie Bohnert
     */
    public static SendUserDTO from(String username) {
        return new SendUserDTO(username);
    }
}