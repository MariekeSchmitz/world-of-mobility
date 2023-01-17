package de.hsrm.mi.swt_project.demo.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.editor.tiles.TrafficTile;

@SpringBootTest
class TrafficTileTest {

    @Test
    void testTurn() {

        TrafficTile tile = (TrafficTile) Tiletype.STREET_CURVE.createTile();
        List<Orientation> allowedDirections = tile.getAllowedDirections();

        assertEquals(Orientation.NORTH, tile.getOrientation());
        assertEquals(Orientation.SOUTH, allowedDirections.get(0));
        assertEquals(Orientation.WEST, allowedDirections.get(1));

        tile.turn(Direction.RIGHT);
        allowedDirections = tile.getAllowedDirections();

        // Check if tile and allowed directions have 
        // turned clockwise
        assertEquals(Orientation.EAST, tile.getOrientation());
        assertEquals(Orientation.WEST, allowedDirections.get(0));
        assertEquals(Orientation.NORTH, allowedDirections.get(1));

        tile.turn(Direction.LEFT);
        allowedDirections = tile.getAllowedDirections();

        // Check if tile and allowed directions have
        // turned counter-clockwise
        assertEquals(Orientation.NORTH, tile.getOrientation());
        assertEquals(Orientation.SOUTH, allowedDirections.get(0));
        assertEquals(Orientation.WEST, allowedDirections.get(1));

    }

}
