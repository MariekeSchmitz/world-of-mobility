package de.hsrm.mi.swt_project.demo.api.game;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swt_project.demo.instancehandling.GameInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.Instance;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;
import de.hsrm.mi.swt_project.demo.instancehandling.UpdateloopInstanceInfo;
import de.hsrm.mi.swt_project.demo.messaging.GameUserListDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetAllMapsOverviewDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetGameCommandDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetListInstanceDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetGameConfigDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetMapOverviewDataDTO;
import de.hsrm.mi.swt_project.demo.messaging.JoinGameDTO;
import de.hsrm.mi.swt_project.demo.messaging.SendGameUpdateDTO;
import de.hsrm.mi.swt_project.demo.messaging.SendMapDTO;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableType;
import de.hsrm.mi.swt_project.demo.messaging.ValidationDTO;

/**
 * This class handles all requests to the game instance.
 * 
 * @author Alexandra Müller
 */
@RestController
@RequestMapping("/api/game")
public class GameRestController{
    @Autowired
    InstanceHandler instanceHandler;

    @Autowired
    private UpdateloopInstanceInfo loopInstanceInfo;

    Logger logger = LoggerFactory.getLogger(GameRestController.class);

    /**
     * Moves a player in the game.
     * 
     * @param gameCommand the command to move the player
     * @param id the id of the game
     */
    @PostMapping(value="/{id}/game-command", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getGameCommand(@RequestBody GetGameCommandDTO gameCommand, @PathVariable long id) {
        logger.info("POST Request for '/api/game/{}/game-command' with body: {}", id, gameCommand);

        instanceHandler.getGameInstanceById(id).moveMoveable(gameCommand.user(), gameCommand.control());
    }

    /**
     * Returns the current state of the game.
     * 
     * @param id the id of the game
     * @return the current state of the game
     */
    @GetMapping(value="/{id}/game-update", produces = MediaType.APPLICATION_JSON_VALUE)
    public SendGameUpdateDTO getGameUpdate(@PathVariable long id) {
        logger.info("GET Request for '/api/game/{}/game-update'", id);

        return SendGameUpdateDTO.from(instanceHandler.getGameInstanceById(id).getMoveableObjects());
    }

    /**
     * Post for Getting GameInstanceList
     * @param getListInstanceDTO
     * @author Finn Schindel, Astrid Klemmer
     */
    @GetMapping(value = "/instancelist")
    public GetListInstanceDTO postGameList() {
        logger.info("Post Request for List form all GameList");
        List<Instance> gamelist = instanceHandler.getGameInstances();
        return GetListInstanceDTO.from(gamelist);
    } 


    @GetMapping(value="/{id}/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameUserListDTO getGamePlayers(@PathVariable long id) {
        logger.info("GET Request for '/api/game/{}/players'", id);
        return GameUserListDTO.from(instanceHandler.getGameInstanceById(id).getMoveableObjects());
    }

    /**
     * Creates a new game instance.
     * 
     * @param mapName the name of the map to use for the instance
     * @param sessionName the name of the session to use for the instance
     * @return the id of the new instance
     */
    
    @PostMapping(value="/create-game", consumes = MediaType.APPLICATION_JSON_VALUE)
    public long createGame(@RequestBody GetGameConfigDTO gameConfig) {
        
        String mapName = gameConfig.mapName();
        String sessionName = gameConfig.sessionName();
        int maximumPlayerCount = gameConfig.maximumPlayerCount();
        boolean npcsActivated = gameConfig.npcsActivated();

        logger.info("POST Request for '/api/game/create-game' with body: {} and {}", mapName, sessionName);

        return instanceHandler.createGameInstance(mapName, sessionName, maximumPlayerCount, npcsActivated);
    }

    /**
     * Joins a player into an existing game.
     * 
     * @param user the user to join
     * @param type the type of the movableObject
     * @param id the id of the game
     * @param xPos the x position the player wants to spawn at
     * @param yPos the y position the player wants to spawn at
     */
    @PostMapping(value="/{id}/join-game", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean joinGame(@RequestBody JoinGameDTO joinGameRequest , @PathVariable long id) {

        String user = joinGameRequest.user();
        String type = joinGameRequest.type();

        float xPos = joinGameRequest.xPos();
        float yPos = joinGameRequest.yPos();

        logger.info("POST Request for '/api/game/{}/join-game' with body: {} and {} and {} and {}", id, user, type, xPos, yPos);

        GameInstance instance = instanceHandler.getGameInstanceById(id);
        boolean playerSlotAvailable = instance.playerSlotAvailable();
        if (instance != null && playerSlotAvailable) {
            MoveableObject moveable = MoveableType.valueOf(type).createMovable(xPos, yPos);
            instance.addPlayer(user, moveable);
            loopInstanceInfo.publishInstanceInfoState(instanceHandler.getGameInstanceById(id), "CREATE");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a list of all maps.
     * 
     * @return a list of all maps
     */
    @GetMapping(value="/get-all-maps", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetAllMapsOverviewDTO getMaps() {
        logger.info("GET Request for '/api/game/get-all-maps'");

        List<GetMapOverviewDataDTO> maps = new LinkedList<>();

        for (String mapName : instanceHandler.getMaps()) {
            maps.add(new GetMapOverviewDataDTO(mapName));
        }

        return new GetAllMapsOverviewDTO(maps);
    }

    /**
     * Validates sessionName
     * 
     * @param gameConfig a DTO that contains mapName and sessionName
     * @return if validation was successful
     */
    @PostMapping(value="/game-config", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationDTO getGameConfig(@RequestBody GetGameConfigDTO gameConfig) {

        String mapName = gameConfig.mapName();
        String sessionName = gameConfig.sessionName();
        int maximumPlayerCount = gameConfig.maximumPlayerCount();

        logger.info("GET Request for '/api/game/game-config' with body: {} and {}", mapName, sessionName);

        boolean validationSuccess = instanceHandler.checkSessionNameAvailable(sessionName);
        if(maximumPlayerCount < 1) validationSuccess = false;

        return new ValidationDTO(validationSuccess);
    }

    /**
     * Post for getting the map from Editor Instance
     * 
     * @param getMapDTO
     * @author Fabio Bertels
     */
    @GetMapping(value = "/getmap/{instanceID}")
    public SendMapDTO getMapEditor(@PathVariable Long instanceID) {
        GameInstance gameInstance = instanceHandler.getGameInstanceById(instanceID);
        return SendMapDTO.from(gameInstance.getMap());
    }

    /**
     * Removes a player from an existing game.
     * 
     * @param user the user to leave
     * @param type the type of the movableObject
     * @param id the id of the game
     * @author Astrid Klemmer
     */
    @PostMapping(value="/{id}/leave-game", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void leaveGame(@RequestBody JoinGameDTO leaveGameRequest , @PathVariable long id) {
        logger.info("Post-Req leave-game - delete user ", leaveGameRequest.user());
        instanceHandler.getGameInstanceById(id).removePlayer(leaveGameRequest.user());
        loopInstanceInfo.publishInstanceInfoState(instanceHandler.getGameInstanceById(id), "CREATE");
    }

    /**
     * Cheacks wether the MoveableObject can spawn at the given location
     * 
     * @param moveableObject String of the Moveable Object to check for
     * @param xPos x-Position the Object will be spawned at
     * @param yPos y-Position the Object will be spawned at
     * @param id ID of the GameInstance the Object will be spawned in
     * @return boolean value that indicates wether the Object can Spawn at the given location
     */
    @GetMapping(value = "/{id}/validate-spawnpoint")
    public String validateSpawnpoint(@RequestParam String moveableObject, @RequestParam int xPos, @RequestParam int yPos, @PathVariable long id) {
        GameInstance gameInstance = instanceHandler.getGameInstanceById(id);
        MoveableObject objectToValidate = MoveableType.valueOf(moveableObject).createMovable();
        String stringifiedBoolean = String.valueOf(gameInstance.validateSpawnpoint(objectToValidate, xPos, yPos));
        return stringifiedBoolean;
    }
}
