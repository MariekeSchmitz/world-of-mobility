package de.hsrm.mi.swt_project.demo.messaging;

import de.hsrm.mi.swt_project.demo.instancehandling.EditorInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.GameInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.Instance;

public record GetInstanceInfoDTO(long id, String gamename, String worldname, int playeramount) {

    public static GetInstanceInfoDTO from(Instance instance) {
        if (instance instanceof GameInstance) {
            return new GetInstanceInfoDTO(
                instance.getId(),
                "Platzhalter - GameName" + instance.getId(),
                instance.getMap().getName(),
                0
                );
        } else {
            return new GetInstanceInfoDTO(
                instance.getId(),
                "Platzhalter - GameName" + instance.getId(),
                instance.getMap().getName(),
                ((EditorInstance) instance).getUsers().size()
                );
        }

    }
    
}
