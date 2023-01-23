package de.hsrm.mi.swt_project.demo.messaging;

import java.util.List;

/**
 * DTO that bundles all Users from the Editor
 * @author Astrid Klemmer & Marieke Schmitz
 */
public record EditorUserListDTO(List<String> users) {
    
    public static EditorUserListDTO from(List<String> users) {
        return new EditorUserListDTO(users);
    }
}
