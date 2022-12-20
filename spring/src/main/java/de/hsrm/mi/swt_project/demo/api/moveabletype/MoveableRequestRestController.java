package de.hsrm.mi.swt_project.demo.api.moveabletype;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swt_project.demo.movables.MoveableType;

@RestController
@RequestMapping("/api")
public class MoveableRequestRestController {

    List<String> types = new ArrayList<>();

    public MoveableRequestRestController(){
        for(MoveableType type: MoveableType.values()){
            types.add(type.name());
        }
    }
 
    /**
     * Get for VehicleType Request
     * 
     * @param 
     * @author Kevin Emunds
     */
    @GetMapping(value = "/MovableTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getMoveableTypeRequest(){
        return types;
    }
}
