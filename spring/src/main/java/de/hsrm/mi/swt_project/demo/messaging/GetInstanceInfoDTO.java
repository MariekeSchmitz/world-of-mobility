package de.hsrm.mi.swt_project.demo.messaging;

import java.util.List;

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
public record GetInstanceInfoDTO(long id, String gamename, String worldname, int playeramount, int maxPlayerCount, List<String> users, String command) {

    /**
     * create a DTO
     * 
     * @param instance
     * @return GetInstanceInfoDTO
     */
    public static GetInstanceInfoDTO from(Instance instance, String command) {
        if (instance instanceof GameInstance gameinstance) {
            List<String> gameUsers = GameUserListDTO.from(gameinstance.getMoveableObjects()).users();
            return new GetInstanceInfoDTO(
                gameinstance.getId(),
                gameinstance.getName(),
                gameinstance.getMap().getName(),
                gameUsers.size(),
                gameinstance.getMaximumPlayerCount(),
                gameUsers,
                command
                );
        } else {
            List<String> editorUsers = ((EditorInstance) instance).getUsers();
            return new GetInstanceInfoDTO(
                instance.getId(),
                "EditorGameName " + instance.getId(),
                instance.getMap().getName(),
                ((EditorInstance) instance).getUsers().size(),
                0,
                editorUsers,
                command
                );
        }

    }
    
}
