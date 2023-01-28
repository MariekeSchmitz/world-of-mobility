package de.hsrm.mi.swt_project.demo.railingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.Car;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

public class CurveBehaviorTest {
    static final float UP_ALIGNMENT_OFFSET = 0.7f;
    static final float RIGHT_ALIGNMENT_OFFSET = 0.7f;
    static final float DOWN_ALIGNMENT_OFFSET = 0.3f;
    static final float LEFT_ALIGNMENT_OFFSET = 0.3f;


    RailingBehaviour railingSystem;
    Tile curveTile;
    MoveableObject moveable;
    String moveableKey;

    @BeforeEach
    void setup() {
        railingSystem = new RailingBehaviour();
        curveTile = Tiletype.STREET_CURVE.createTile();
        moveable = new Car();
        moveable.setCurrentVelocity(0);
        moveableKey= "testMoveable";
    }

    @Test
    void testNorthTileDrivingNorth(){
        assertEquals(Orientation.NORTH, curveTile.getOrientation());
        assertEquals(Orientation.NORTH, moveable.getOrientation());
        moveable.setYPos(UP_ALIGNMENT_OFFSET);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(RIGHT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertEquals(UP_ALIGNMENT_OFFSET, moveable.getYPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(UP_ALIGNMENT_OFFSET, moveable.getYPos());
        assertTrue(moveable.getXPos() < RIGHT_ALIGNMENT_OFFSET);
        assertEquals(Orientation.WEST, moveable.getOrientation());
    }

    @Test
    void testNorthTileDrivingEast(){
        moveable.turn(Direction.RIGHT);
        assertEquals(Orientation.NORTH, curveTile.getOrientation());
        assertEquals(Orientation.EAST, moveable.getOrientation());
        moveable.setXPos(LEFT_ALIGNMENT_OFFSET);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(DOWN_ALIGNMENT_OFFSET, moveable.getXPos());
        assertEquals(LEFT_ALIGNMENT_OFFSET, moveable.getYPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(LEFT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertTrue(moveable.getYPos() < DOWN_ALIGNMENT_OFFSET);
        assertEquals(Orientation.SOUTH, moveable.getOrientation());
    }

    @Test
    void testEastTileDrivingEast(){
        curveTile.turn(Direction.RIGHT);
        moveable.turn(Direction.RIGHT);
        assertEquals(Orientation.EAST, curveTile.getOrientation());
        assertEquals(Orientation.EAST, moveable.getOrientation());
        moveable.setXPos(RIGHT_ALIGNMENT_OFFSET);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(RIGHT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertEquals(DOWN_ALIGNMENT_OFFSET, moveable.getYPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(RIGHT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertTrue(moveable.getYPos() > DOWN_ALIGNMENT_OFFSET);
        assertEquals(Orientation.NORTH, moveable.getOrientation());
    }

    @Test
    void testEastTileDrivingSouth(){
        curveTile.turn(Direction.RIGHT);
        moveable.turn(Direction.RIGHT);
        moveable.turn(Direction.RIGHT);
        assertEquals(Orientation.EAST, curveTile.getOrientation());
        assertEquals(Orientation.SOUTH, moveable.getOrientation());
        moveable.setYPos(UP_ALIGNMENT_OFFSET);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(UP_ALIGNMENT_OFFSET, moveable.getYPos());
        assertEquals(LEFT_ALIGNMENT_OFFSET, moveable.getXPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(UP_ALIGNMENT_OFFSET, moveable.getYPos());
        assertTrue(moveable.getXPos() < LEFT_ALIGNMENT_OFFSET);
        assertEquals(Orientation.WEST, moveable.getOrientation());
    }

    @Test
    void testSouthTileDrivingSouth(){
        curveTile.turn(Direction.RIGHT);
        curveTile.turn(Direction.RIGHT);
        moveable.turn(Direction.RIGHT);
        moveable.turn(Direction.RIGHT);
        assertEquals(Orientation.SOUTH, curveTile.getOrientation());
        assertEquals(Orientation.SOUTH, moveable.getOrientation());
        moveable.setYPos(DOWN_ALIGNMENT_OFFSET);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(DOWN_ALIGNMENT_OFFSET, moveable.getYPos());
        assertEquals(LEFT_ALIGNMENT_OFFSET, moveable.getXPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(DOWN_ALIGNMENT_OFFSET, moveable.getYPos());
        assertTrue(moveable.getXPos() > LEFT_ALIGNMENT_OFFSET);
        assertEquals(Orientation.EAST, moveable.getOrientation());
    }

    @Test
    void testSouthTileDrivingWEST(){
        curveTile.turn(Direction.RIGHT);
        curveTile.turn(Direction.RIGHT);
        moveable.turn(Direction.LEFT);
        assertEquals(Orientation.SOUTH, curveTile.getOrientation());
        assertEquals(Orientation.WEST, moveable.getOrientation());
        moveable.setXPos(RIGHT_ALIGNMENT_OFFSET);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(RIGHT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertEquals(UP_ALIGNMENT_OFFSET, moveable.getYPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(RIGHT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertTrue(moveable.getYPos() > UP_ALIGNMENT_OFFSET);
        assertEquals(Orientation.NORTH, moveable.getOrientation());
    }

    @Test
    void testWestTileDrivingWest(){
        curveTile.turn(Direction.LEFT);
        moveable.turn(Direction.LEFT);
        assertEquals(Orientation.WEST, curveTile.getOrientation());
        assertEquals(Orientation.WEST, moveable.getOrientation());
        moveable.setXPos(LEFT_ALIGNMENT_OFFSET);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(LEFT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertEquals(UP_ALIGNMENT_OFFSET, moveable.getYPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(LEFT_ALIGNMENT_OFFSET, moveable.getXPos());
        assertTrue(moveable.getYPos() < UP_ALIGNMENT_OFFSET);
        assertEquals(Orientation.SOUTH, moveable.getOrientation());
    }

    @Test
    void testWestTileDrivingNorth(){
        curveTile.turn(Direction.LEFT);
        assertEquals(Orientation.WEST, curveTile.getOrientation());
        assertEquals(Orientation.NORTH, moveable.getOrientation());
        moveable.setYPos(DOWN_ALIGNMENT_OFFSET);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(DOWN_ALIGNMENT_OFFSET, moveable.getYPos());
        assertEquals(RIGHT_ALIGNMENT_OFFSET, moveable.getXPos());
        moveable.setCurrentVelocity(1);
        railingSystem.railCoordinates(moveableKey, moveable, curveTile, null);
        assertEquals(DOWN_ALIGNMENT_OFFSET, moveable.getYPos());
        assertTrue(moveable.getXPos() > RIGHT_ALIGNMENT_OFFSET);
        assertEquals(Orientation.EAST, moveable.getOrientation());
    }
}
