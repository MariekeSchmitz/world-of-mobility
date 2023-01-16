package de.hsrm.mi.swt_project.demo.scripting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * Caches context of scripted moveables.
 * 
 * This caches only the ScriptContext object,
 * not its content!
 * 
 * @author Sascha Scheid
 */
public class ScriptContextCache {
    
    protected Map<MoveableObject, ScriptContext> contextCache = new HashMap<>();

    /**
     * If a context was already created for the moveable, return that
     * context, else create the context and store it in the cache.
     * 
     * @param moveable Moveable object that is controlled by a script.
     * @param gameMap 2D array of tiles representing the whole map
     * @param allMoveables All moveable objects of an instance
     * 
     * @return Context for an moveable object
     */
    public ScriptContext getContextFor(MoveableObject moveable, Tile[][] gameMap, List<MoveableObject> allMoveables) {

        ScriptContext context = contextCache.get(moveable);

        if (context != null) {
            return context;
        }

        context = new ScriptContext(moveable, gameMap, allMoveables);
        contextCache.put(moveable, context);

        return context;
    }
}
