package de.hsrm.mi.swt_project.demo.userManagement;

public record SendUserDTO(String username, String error) {

    public static SendUserDTO from(String username, String error) {
        return new SendUserDTO(username, error);
    }
}