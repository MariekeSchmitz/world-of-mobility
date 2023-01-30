package de.hsrm.mi.swt_project.demo.scripting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.GrassTile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Streetile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;

class ScriptContextTest {
    
    @Test
    void testProvideMapContext() {

        MoveableObject moveable = new Passenger();
        moveable.setXPos(5);
        moveable.setYPos(5);

        Tile[][] gameMap = new Tile[10][10];
        for (int i = 0; i < gameMap.length; i++) {
            for (int j = 0; j < gameMap[i].length; j++) {
                gameMap[i][j] = (i < 5) ? new GrassTile() : new Streetile();
            }
        }

        ScriptContext context = new ScriptContext(moveable, gameMap, null);

        TileProxy[][] mapContext = context.provideMapContext();

        // check size of map context
        assertEquals(ScriptContext.LOOK_AHEAD * 2 + 1, mapContext.length);
        assertEquals(ScriptContext.LOOK_AHEAD * 2 + 1, mapContext[0].length);

        // check that center of map context is the tile where the moveable is positioned
        int middle = ScriptContext.LOOK_AHEAD;
        assertSame(gameMap[5][5], mapContext[middle][middle].tile);

        int row = (int) moveable.getYPos();
        int col = (int) moveable.getXPos();

        int contextSize = mapContext.length;

        // check that map context belongs to right tiles on full map
        assertSame(gameMap[row - ScriptContext.LOOK_AHEAD][col - ScriptContext.LOOK_AHEAD], mapContext[0][0].tile);
        assertSame(gameMap[row - ScriptContext.LOOK_AHEAD][col + ScriptContext.LOOK_AHEAD], mapContext[0][contextSize - 1].tile);
        assertSame(gameMap[row + ScriptContext.LOOK_AHEAD][col - ScriptContext.LOOK_AHEAD], mapContext[contextSize - 1][0].tile);
        assertSame(gameMap[row + ScriptContext.LOOK_AHEAD][col + ScriptContext.LOOK_AHEAD], mapContext[contextSize - 1][contextSize -1].tile);
    }

    @Test
    void testProvideMapContextForTurnedMoveable() {

        MoveableObject moveable = new Passenger(Orientation.EAST, 5, 5, 1);

        Tile[][] gameMap = new Tile[10][10];
        for (int i = 0; i < gameMap.length; i++) {
            for (int j = 0; j < gameMap[i].length; j++) {
                gameMap[i][j] = (i < 5) ? new GrassTile() : new Streetile();
            }
        }

        ScriptContext context = new ScriptContext(moveable, gameMap, null);

        TileProxy[][] mapContext = context.provideMapContext();

        int row = (int) moveable.getYPos();
        int col = (int) moveable.getXPos();

        int contextSize = mapContext.length;

        // check that map context belongs to right tiles on full map
        assertSame(gameMap[row + ScriptContext.LOOK_AHEAD][col - ScriptContext.LOOK_AHEAD], mapContext[0][0].tile);
        assertSame(gameMap[row - ScriptContext.LOOK_AHEAD][col - ScriptContext.LOOK_AHEAD], mapContext[0][contextSize - 1].tile);
        assertSame(gameMap[row + ScriptContext.LOOK_AHEAD][col + ScriptContext.LOOK_AHEAD], mapContext[contextSize - 1][0].tile);
        assertSame(gameMap[row - ScriptContext.LOOK_AHEAD][col + ScriptContext.LOOK_AHEAD], mapContext[contextSize - 1][contextSize -1].tile);
    }

    @Test
    void testProvideOtherMoveablesContext() {

        MoveableObject moveable = new Passenger();
        moveable.setXPos(5);
        moveable.setYPos(5);

        MoveableObject mov1 = new Passenger();
        mov1.setXPos(5);
        mov1.setYPos(4);

        MoveableObject mov2 = new Passenger();
        mov2.setXPos(4);
        mov2.setYPos(5);

        float outOfRangeX = moveable.getXPos() + ScriptContext.LOOK_AHEAD + 1;
        float outOfRangeY = moveable.getXPos() + ScriptContext.LOOK_AHEAD + 1;

        MoveableObject mov3 = new Passenger();
        mov3.setXPos(outOfRangeX);
        mov3.setYPos(outOfRangeY);

        List<MoveableObject> allMoveables = new ArrayList<>();
        allMoveables.add(mov1);
        allMoveables.add(mov2);
        allMoveables.add(mov3);

        ScriptContext context = new ScriptContext(moveable, null, allMoveables);
        List<MoveableObject> otherMoveablesContext = context.provideOtherMoveablesContext();

        // check that only moveable objects that are in a tile-radius of LOOK_AHEAD around the moveable are returned
        assertEquals(2, otherMoveablesContext.size());
        assertSame(mov1, otherMoveablesContext.get(0));
        assertSame(mov2, otherMoveablesContext.get(1));
    }
}
