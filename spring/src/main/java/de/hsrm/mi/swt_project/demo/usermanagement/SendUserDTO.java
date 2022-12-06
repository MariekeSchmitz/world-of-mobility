package de.hsrm.mi.swt_project.demo.usermanagement;

/**
 * Data Transfer Object with userdata and potential error message sent to client
 * 
 * @author Tom Gouthier
 */
public record SendUserDTO(String username, String error) {

    /**
     * creates DTO instance
     * 
     * @author Tom Gouthier
     */
    public static SendUserDTO from(String username, String error) {
        return new SendUserDTO(username, error);
    }
}