package de.hsrm.mi.swt_project.demo.api.editor;

/**
 * DTO that the server recieves by cerating a new world
 * 
 * @author Marie Bohnert, Tom Gouthier, Victoria Thee
 */
public record SendNewWorldDTO(long id, String error) {

    public static SendNewWorldDTO from(long id, String error) {
        return new SendNewWorldDTO(id, error);
    }

}
