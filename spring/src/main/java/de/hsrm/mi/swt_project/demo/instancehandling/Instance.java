package de.hsrm.mi.swt_project.demo.instancehandling;

import de.hsrm.mi.swt_project.demo.updates.Updateable;

/**
 * This abstract class represents a single instance of the game.
 * 
 * @author Alexandra Müller
 */
public abstract class Instance implements Updateable {
    protected GameMap map;
    protected long id;

    /**
     * Creates a new instance.
     * 
     * @param map the map to use for the instance
     */
    public Instance(GameMap map, long id) {
        this.map = map;
        this.id = id;
    }

    /**
     * Pushes updates of the instance.
     */
    public abstract void update();

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