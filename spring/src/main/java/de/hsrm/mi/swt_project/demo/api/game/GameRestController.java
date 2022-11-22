package de.hsrm.mi.swt_project.demo.api.game;

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

@RestController
@RequestMapping("/api/game")
public class GameRestController{
    @Autowired
    InstanceHandler instanceHandler;

    Logger logger = LoggerFactory.getLogger(GameRestController.class);

    @PostMapping(value="/{id}/game-command", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getGameCommand(@RequestBody GetGameCommandDTO gameCommand, @PathVariable long id) {
        logger.info("GET Request for '/api/game/" + id + "/game-command' with body: " + gameCommand);

        instanceHandler.getGameInstanceById(id).moveMovable(gameCommand.user(), gameCommand.control());
    }

    @GetMapping(value="/{id}/game-update", produces = MediaType.APPLICATION_JSON_VALUE)
    public SendGameUpdateDTO getGameUpdate(@PathVariable long id) {
        logger.info("GET Request for '/api/game/" + id + "/game-update'");

        return SendGameUpdateDTO.from(instanceHandler.getGameInstanceById(id).getMoveableObjects());
    }
}
