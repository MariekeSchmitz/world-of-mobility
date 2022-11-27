package de.hsrm.mi.swt_project.demo.userManagement;

public record GetUserDTO(String name) {

    public GetUserDTO from(String name) {
        return new GetUserDTO(name);
    }
}
