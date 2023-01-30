package de.hsrm.mi.swt_project.demo.railingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.Car;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

@SpringBootTest
class StraightBehaviorTest {
    
    static final float UP_ALIGNMENT_OFFSET = 0.7f;
    static final float RIGHT_ALIGNMENT_OFFSET = 0.7f;
    static final float DOWN_ALIGNMENT_OFFSET = 0.3f;
    static final float LEFT_ALIGNMENT_OFFSET = 0.3f;


    RailingBehaviour railingSystem;
    Tile straightTile;
    MoveableObject moveable;
    String moveableKey;

    @BeforeEach
    void setup() {
        railingSystem = new RailingBehaviour();
        straightTile = Tiletype.STREET_STRAIGHT.createTile();
        moveable = new Car();
        moveableKey= "testMoveable";
    }

    @Test
    void testNorthTileDrivingNorth(){
        assertEquals(Orientation.NORTH, straightTile.getOrientation());
        assertEquals(Orientation.NORTH, moveable.getOrientation());
        railingSystem.railCoordinates(moveableKey, moveable, straightTile, null);
        assertEquals(RIGHT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertEquals(0, moveable.getYPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, straightTile, null);
        assertEquals(RIGHT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertTrue(moveable.getYPos() > 0);
    }

    @Test
    void testNorthTileDrivingSouth(){
        moveable.turn(Direction.LEFT);
        moveable.turn(Direction.LEFT);
        moveable.setYPos(1);
        assertEquals(Orientation.NORTH, straightTile.getOrientation());
        assertEquals(Orientation.SOUTH, moveable.getOrientation());
        railingSystem.railCoordinates(moveableKey, moveable, straightTile, null);
        assertEquals(LEFT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertEquals(1, moveable.getYPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, straightTile, null);
        assertEquals(LEFT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertTrue(moveable.getYPos() < 1);
    }

    @Test
    void testEastTileDrivingEast(){
        moveable.turn(Direction.RIGHT);
        straightTile.turn(Direction.RIGHT);
        assertEquals(Orientation.EAST, straightTile.getOrientation());
        assertEquals(Orientation.EAST, moveable.getOrientation());
        railingSystem.railCoordinates(moveableKey, moveable, straightTile, null);
        assertEquals(DOWN_ALIGNMENT_OFFSET, moveable.getYPos());
        assertEquals(0, moveable.getXPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, straightTile, null);
        assertEquals(DOWN_ALIGNMENT_OFFSET, moveable.getYPos());
        assertTrue(moveable.getXPos() > 0);
    }

    @Test
    void testEastTileDrivingWest(){
        moveable.turn(Direction.LEFT);
        straightTile.turn(Direction.RIGHT);
        moveable.setXPos(1);
        assertEquals(Orientation.EAST, straightTile.getOrientation());
        assertEquals(Orientation.WEST, moveable.getOrientation());
        railingSystem.railCoordinates(moveableKey, moveable, straightTile, null);
        assertEquals(UP_ALIGNMENT_OFFSET, moveable.getYPos());
        assertEquals(1, moveable.getXPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, straightTile, null);
        assertEquals(UP_ALIGNMENT_OFFSET, moveable.getYPos());
        assertTrue(moveable.getXPos() < 1);
    }
}
