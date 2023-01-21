package de.hsrm.mi.swt_project.demo.instancehandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.editor.tiles.Streetile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

@SpringBootTest
class GameMapTest {
    
    @Test
    void testAddTile() {

        GameMap gameMap = new GameMap();
        MoveableObject moveable = new MotorizedObject();
        gameMap.getNpcs().add(moveable);
    
        Tile tile = new Streetile();

        int startSize = gameMap.getTiles().length;
        int currentSize = startSize;

        assertEquals(GameMap.DEFAULT_SIZE, startSize);

        assertThrows(IllegalArgumentException.class, () -> gameMap.addTile(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> gameMap.addTile(tile, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> gameMap.addTile(tile, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> gameMap.addTile(tile, GameMap.DEFAULT_SIZE, 0));
        assertThrows(IllegalArgumentException.class, () -> gameMap.addTile(tile, 0, GameMap.DEFAULT_SIZE));

        // Adding a tile to the middle should not trigger expansion
        gameMap.addTile(tile, currentSize / 2, currentSize / 2);
        currentSize = gameMap.getTiles().length;
        assertEquals(startSize, currentSize);
        assertEquals(0, moveable.getxPos());
        assertEquals(0, moveable.getyPos());

        // Adding a tile to the left edge should trigger expansion
        gameMap.addTile(tile, 1, 0);
        currentSize = gameMap.getTiles().length;
        assertEquals(startSize + GameMap.MAP_EXPANSION_STEP, currentSize);
        assertEquals(GameMap.MAP_EXPANSION_STEP, moveable.getxPos());
        assertEquals(GameMap.MAP_EXPANSION_STEP, moveable.getyPos());

        // Adding a tile to the top edge should trigger expansion
        gameMap.addTile(tile, 0, 1);
        currentSize = gameMap.getTiles().length;
        assertEquals(startSize + GameMap.MAP_EXPANSION_STEP * 2, currentSize);
        assertEquals(GameMap.MAP_EXPANSION_STEP * 2, moveable.getxPos());
        assertEquals(GameMap.MAP_EXPANSION_STEP * 2, moveable.getyPos());

        // Adding a tile to the right edge should trigger expansion
        gameMap.addTile(tile, currentSize - 1, 1);
        currentSize = gameMap.getTiles().length;
        assertEquals(startSize + GameMap.MAP_EXPANSION_STEP * 3, currentSize);
        assertEquals(GameMap.MAP_EXPANSION_STEP * 3, moveable.getxPos());
        assertEquals(GameMap.MAP_EXPANSION_STEP * 3, moveable.getyPos());

        // Adding a tile to the bottom edge should trigger expansion
        gameMap.addTile(tile, 1, currentSize - 1);
        currentSize = gameMap.getTiles().length;
        assertEquals(startSize + GameMap.MAP_EXPANSION_STEP * 4, currentSize);
        assertEquals(GameMap.MAP_EXPANSION_STEP * 4, moveable.getxPos());
        assertEquals(GameMap.MAP_EXPANSION_STEP * 4, moveable.getyPos());

    }

}
