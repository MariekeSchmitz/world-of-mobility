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
public record GetInstanceInfoDTO(long id, String gamename, String worldname, int playeramount, String command) {

    /**
     * create a DTO
     * 
     * @param instance
     * @return GetInstanceInfoDTO
     */
    public static GetInstanceInfoDTO from(Instance instance, String command) {
        if (instance instanceof GameInstance gameinstance) {
            return new GetInstanceInfoDTO(
                gameinstance.getId(),
                gameinstance.getName(),
                gameinstance.getMap().getName(),
                GameUserListDTO.from(gameinstance.getMoveableObjects()).users().size(),
                command
                );
        } else {
            return new GetInstanceInfoDTO(
                instance.getId(),
                "EditorGameName " + instance.getId(),
                instance.getMap().getName(),
                ((EditorInstance) instance).getUsers().size(),
                command
                );
        }

    }
    
}
