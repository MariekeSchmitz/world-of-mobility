package de.hsrm.mi.swt_project.demo.api.editor;

public record GetNewWorldDTO(String name) {

    public GetNewWorldDTO from(String name){
        return new GetNewWorldDTO(name);

    }
    
}