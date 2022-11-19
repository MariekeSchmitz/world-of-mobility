package de.hsrm.mi.swt_project.demo.api.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swt_project.demo.api.game.GameRestController;

@RestController
@RequestMapping("/api/game")
public class GameRestController{
   
    Logger logger = LoggerFactory.getLogger(GameRestController.class);

    @GetMapping(value="/gameTest", produces=MediaType.TEXT_PLAIN_VALUE)
    public String get_Game_Test(){
        logger.info("GET Request for '/api/game/gameTest'");

        return "Game Test";
    }
}