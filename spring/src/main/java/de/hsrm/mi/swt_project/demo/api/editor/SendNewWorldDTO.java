package de.hsrm.mi.swt_project.demo.api.editor;

public record SendNewWorldDTO(String name, String error) {

    public static SendNewWorldDTO from(String name, String error){
        return new SendNewWorldDTO(name, error);
    }
    
}
