package de.hsrm.mi.swt_project.demo.scripting;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;

@SpringBootTest
class ScriptContextCacheTest {

    @Test
    void testGetContextFor() {
        
        ScriptContextCache cache = new ScriptContextCache();

        MoveableObject moveable = new Passenger();
        Tile[][] gameMap = new Tile[10][10];
        List<MoveableObject> allMoveables = new ArrayList<>();

        ScriptContext context = cache.getContextFor(moveable, gameMap, allMoveables);

        // check that context is not null
        assertNotNull(context);

        // check that context is stored in cache
        assertSame(context, cache.contextCache.get(moveable));

        // check that calling getContextFor with the same moveable returns the same context
        ScriptContext context2 = cache.getContextFor(moveable, gameMap, allMoveables);
        assertSame(context, context2);
    }
    
}
