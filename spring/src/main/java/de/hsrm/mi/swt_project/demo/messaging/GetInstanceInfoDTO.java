package de.hsrm.mi.swt_project.demo.messaging;

import de.hsrm.mi.swt_project.demo.instancehandling.EditorInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.GameInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.Instance;
/**
 * @param id = InstanceId
 * @param gamename = name from the instance
 * @param worldname = name from the worldmap
 * @param playeramount = amount from playing Users
 * @author Astrid Klemmer, Finn Schindel
 */
public record GetInstanceInfoDTO(long id, String gamename, String worldname, int playeramount) {

    /**
     * create a DTO
     * 
     * @param instance
     * @return GetInstanceInfoDTO
     */
    public static GetInstanceInfoDTO from(Instance instance) {
        if (instance instanceof GameInstance) {
            return new GetInstanceInfoDTO(
                instance.getId(),
                "GameName" + instance.getId(),
                instance.getMap().getName(),
                GameUserListDTO.from(((GameInstance)instance).getMoveableObjects()).users().size()
                );
        } else {
            return new GetInstanceInfoDTO(
                instance.getId(),
                "GameName" + instance.getId(),
                instance.getMap().getName(),
                ((EditorInstance) instance).getUsers().size()
                );
        }

    }
    
}
