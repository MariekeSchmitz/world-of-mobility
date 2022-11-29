package de.hsrm.mi.swt_project.demo.instancehandling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.hsrm.mi.swt_project.demo.controls.Updateable;

/**
 * This class maintains all instances of the game.
 * 
 * @author Alexandra Müller
 * @author Sascha Scheid
 */
 public class InstanceHandler implements Updateable {

    @Autowired
    protected UpdateloopService loopservice;

    protected List<Instance> instances;

    // TODO think of another solution because long can reach limit
    protected long idCounter = 1;


    /**
     * Creates a new instance handler.
     */
    public InstanceHandler() {
        instances = new ArrayList<Instance>();
    }

    /**
     * Adds a new game instance to the handler.
     * 
     * @param map the map to use for the instance
     */
    public void createGameInstance(GameMap map, String name) {
        instances.add(new GameInstance(map, name, idCounter));
        idCounter++;
    }

    /**
     * Adds a new editor instance to the handler.
     * 
     * @param map the map to use for the instance
     */
    public void createEditorInstance(GameMap map) {
        instances.add(new EditorInstance(map, idCounter));
        idCounter++;
    }

    /**
     * Removes an instance from the handler.
     * 
     * @param instance the instance to remove
     */
    public void removeInstance(Instance instance) {
        instances.remove(instance);
    }

    /**
     * This method triggers all instances to update
     * their state. After an update of the state, 
     * the new state of an instance will be published.
     */
    public void update() {
        for (Instance instance : instances) {
            instance.update();
            loopservice.publishInstanceState(instance);
        }
    }

    /**
     * Returns the instance with the given id.
     * 
     * @param id the id of the instance
     * @return the instance with the given id
     */
    public List<Instance> getInstances() {
        return instances;
    }

    /**
     * Returns the game instance with the given id.
     * 
     * @param id the id of the instance
     * @return the instance with the given id
     */
    public GameInstance getGameInstanceById(long id) {
        for (Instance instance : instances) {
            if (instance.getId() == id) {
                if(instance instanceof GameInstance) {
                    return (GameInstance) instance;
                }
            }
        }
        return null;
    }

    /**
     * Returns the instance with the given id.
     * 
     * @param id the id of the instance
     * @return the instance with the given id
     */
    public EditorInstance getEditorInstanceById(long id) {
        for (Instance instance : instances) {
            if (instance.getId() == id) {
                if(instance instanceof EditorInstance) {
                    return (EditorInstance) instance;
                }
            }
        }
        return null;
    }
}
