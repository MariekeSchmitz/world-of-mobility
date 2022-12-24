package de.hsrm.mi.swt_project.demo.instancehandling;

import de.hsrm.mi.swt_project.demo.controls.Updateable;

/**
 * This abstract class represents a single instance of the game.
 * 
 * @author Alexandra Müller
 */
public abstract class Instance implements Updateable {

    protected long lifetime = 1200;
    protected long remainingLifetime = lifetime;

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

    public long getRemainingLifetime() {
        return remainingLifetime;
    }

    public void setLifetime(long loopCount) {
        this.lifetime = loopCount;
        this.remainingLifetime = loopCount;
    }

    public void resetRemainingLifetime() {
        this.remainingLifetime = this.lifetime;
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

    @Override
    public void update() {
        this.remainingLifetime--;
    }
}