package de.hsrm.mi.swt_project.demo.api.game;

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
import de.hsrm.mi.swt_project.demo.messaging.GetGameCommandDTO;
import de.hsrm.mi.swt_project.demo.messaging.SendGameUpdateDTO;
import de.hsrm.mi.swt_project.demo.movables.MoveableType;

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

    /**
     * Creates a new game instance.
     * 
     * @param mapName the name of the map to use for the instance
     * @param sessionName the name of the session to use for the instance
     * @return the id of the new instance
     */
    /*
    @PostMapping(value="/create-game", consumes = MediaType.APPLICATION_JSON_VALUE)
    public long createGame(@RequestBody GetGameConfigDTO gameconfig) {
        logger.info("POST Request for '/api/game/create-game' with body: " + gameConfig.mapName() + " and " + gameConfig.sessionName());

        return instanceHandler.createGameInstance(gameConfig.mapName(), gameConfig.sessionName());
    }*/

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
    public List<String> getMaps() {
        logger.info("GET Request for '/api/game/get-all-maps'");

        return instanceHandler.getMaps();
    }
}
