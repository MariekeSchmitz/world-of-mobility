package de.hsrm.mi.swt_project.demo.api.editor;

public record SendNewWorldDTO(long id, String error) {

    public static SendNewWorldDTO from(long id, String error){
        return new SendNewWorldDTO(id, error);
    }
    
}
