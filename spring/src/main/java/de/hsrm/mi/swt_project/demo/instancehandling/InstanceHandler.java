package de.hsrm.mi.swt_project.demo.instancehandling;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt_project.demo.controls.Updateable;

/**
 * This class maintains all instances of the game.
 * 
 * @author Alexandra Müller
 */
public class InstanceHandler implements Updateable {
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
     * Updates all instances.
     */
    public void update() {
        for (Instance instance : instances) {
            instance.update();
        }
    }
}
