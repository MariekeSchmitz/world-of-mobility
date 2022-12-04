package de.hsrm.mi.swt_project.demo.api.game;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;
import de.hsrm.mi.swt_project.demo.messaging.GameUserListDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetAllMapsOverviewDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetGameCommandDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetGameConfigDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetMapOverviewDataDTO;
import de.hsrm.mi.swt_project.demo.messaging.SendGameUpdateDTO;
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

    Logger logger = LoggerFactory.getLogger(GameRestController.class);

    /**
     * Moves a player in the game.
     * 
     * @param gameCommand the command to move the player
     * @param id the id of the game
     */
    @PostMapping(value="/{id}/game-command", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getGameCommand(@RequestBody GetGameCommandDTO gameCommand, @PathVariable long id) {
        logger.info("POST Request for '/api/game/" + id + "/game-command' with body: " + gameCommand);

        instanceHandler.getGameInstanceById(id).moveMovable(gameCommand.user(), gameCommand.control());
    }

    /**
     * Returns the current state of the game.
     * 
     * @param id the id of the game
     * @return the current state of the game
     */
    @GetMapping(value="/{id}/game-update", produces = MediaType.APPLICATION_JSON_VALUE)
    public SendGameUpdateDTO getGameUpdate(@PathVariable long id) {
        logger.info("GET Request for '/api/game/" + id + "/game-update'");

        return SendGameUpdateDTO.from(instanceHandler.getGameInstanceById(id).getMoveableObjects());
    }

    @GetMapping(value="/{id}/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameUserListDTO getGamePlayers(@PathVariable long id) {
        logger.info("GET Request for '/api/game/" + id + "/players'");
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
        logger.info("POST Request for '/api/game/create-game' with body: " + gameConfig.mapName() + " and " + gameConfig.sessionName());

        return instanceHandler.createGameInstance(gameConfig.mapName(), gameConfig.sessionName());
    }

    /**
     * Joins a player into an existing game.
     * 
     * @param user the user to join
     * @param type the type of the movableObject
     * @param id the id of the game
     */
    @PostMapping(value="/{id}/join-game", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void joinGame(@RequestBody String user, @RequestBody String type, @PathVariable long id) {
        logger.info("POST Request for '/api/game/" + id + "/join-game' with body: " + user, " and " + type);

        instanceHandler.getGameInstanceById(id).addPlayer(user, MoveableType.valueOf(type).createMovable());
    }

    /**
     * Returns a list of all maps.
     * 
     * @return a list of all maps
     */
    @GetMapping(value="/get-all-maps", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetAllMapsOverviewDTO getMaps() {
        logger.info("GET Request for '/api/game/get-all-maps'");

        LinkedList<GetMapOverviewDataDTO> maps = new LinkedList<>();
        maps.add(new GetMapOverviewDataDTO("Map1"));
        maps.add(new GetMapOverviewDataDTO("Map2"));
        maps.add(new GetMapOverviewDataDTO("Map3"));

        // TO DO : unterhalb reinkommentieren + händisches adden (obendrüber) in maps-Liste rausschmeissen (aktuell noch keine richtigen Maps zum testen vorhanden)
        // for (String mapName : instanceHandler.getMaps()) {
        //     maps.add(new GetMapOverviewDataDTO("mapName"));
        // }

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

        logger.info("GET Request for '/api/game/game-config' with body: " + gameConfig.mapName() + " and " + gameConfig.sessionName());

        Boolean validationSuccess = instanceHandler.checkSessionNameAvailable(gameConfig.sessionName());

        return new ValidationDTO(validationSuccess);
        // return new ValidationDTO(false);
    }


}
