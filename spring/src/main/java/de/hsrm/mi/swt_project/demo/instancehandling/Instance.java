package de.hsrm.mi.swt_project.demo.instancehandling;

import de.hsrm.mi.swt_project.demo.controls.Updateable;

/**
 * This abstract class represents a single instance of the game.
 * 
 * @author Alexandra Müller
 */
public abstract class Instance implements Updateable {
    // @Value("${map.savedir:maps}")
    protected String mapSavePath = "maps";

    protected GameMap map;
    protected long id;

    /**
     * Creates a new instance.
     * 
     * @param map the map to use for the instance
     */
    protected Instance(GameMap map, long id) {
        this.map = map;
        this.id = id;
    }

    /**
     * Returns the map of the instance.
     * 
     * @return the map of the instance
     */
    public GameMap getMap() {
        return map;
    }

    /**
     * Returns the id of the instance.
     * 
     * @return the id of the instance
     */
    public long getId() {
        return id;
    }
}