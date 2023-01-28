package de.hsrm.mi.swt_project.demo.scripting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.crypto.encodings.OAEPEncoding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.editor.tiles.Streetile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;

@SpringBootTest
class MoveableFacadeTest {

    MoveableObject moveable;
    MoveableFacade facade;

    @BeforeEach
    void setup() {
        moveable = new MotorizedObject();
        ScriptContext context = new ScriptContext(moveable, null, null);

        facade = MoveableFacade.createFor(moveable, context);
    }

    @Test
    void testAccelerate() {
        float expected = moveable.getCurrentVelocity() + MoveableFacade.ACCELERATION_DELTA;
        facade.accelerate();
        assertEquals(expected, moveable.getCurrentVelocity());
    }

    @Test
    void brake() {
        float expected = moveable.getCurrentVelocity() - MoveableFacade.ACCELERATION_DELTA;
        facade.brake();
        assertEquals(expected, moveable.getCurrentVelocity());
    }

    @Test
    public void testEmergencyBrake() {
        moveable.setCurrentVelocity(50);
        facade.emergencyBrake();
        assertEquals(0.0f, moveable.getCurrentVelocity());
    }

    @Test
    void testSurroundingTiles() {

        moveable.setXPos(5);
        moveable.setYPos(5);

        Tile[][] gameMap = new Tile[10][10];
        for (int i = 0; i < gameMap.length; i++) {
            for (int j = 0; j < gameMap[i].length; j++) {
                gameMap[i][j] = new Streetile();
            }
        }

        ScriptContext context = new ScriptContext(moveable, gameMap, null);

        facade = MoveableFacade.createFor(moveable, context);

        TileProxy[][] surroundingTiles = facade.surroundingTiles();

        for (int i = 0; i < surroundingTiles.length; i++) {
            for (int j = 0; j < surroundingTiles[i].length; j++) {
                assertSame(gameMap[i+4][j+4], surroundingTiles[i][j].tile);
            }
        }
    }

    @Test
    void testNearbyRoadUsers() {

        moveable.setXPos(5);
        moveable.setYPos(5);

        List<MoveableObject> allMoveables = new ArrayList<>();

        // place moveable on every tile.
        // moveable xy will be positioned on tile [x][y], e.g.
        // moveable number 11 will be placed on tile [1][1]
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                MoveableObject obj = new MotorizedObject();
                obj.setXPos(i);
                obj.setYPos(j);
                allMoveables.add(obj);
            }
        }

        ScriptContext context = new ScriptContext(moveable, null, allMoveables);
        MoveableFacade facade = MoveableFacade.createFor(moveable, context);

        List<MoveableObject> nearbyRoadUsers = facade.nearbyRoadUsers();

        assertEquals(9, nearbyRoadUsers.size());
        assertSame(allMoveables.get(44), nearbyRoadUsers.get(0));
        assertSame(allMoveables.get(45), nearbyRoadUsers.get(1));
        assertSame(allMoveables.get(46), nearbyRoadUsers.get(2));
        assertSame(allMoveables.get(54), nearbyRoadUsers.get(3));
        assertSame(allMoveables.get(55), nearbyRoadUsers.get(4));
        assertSame(allMoveables.get(56), nearbyRoadUsers.get(5));
        assertSame(allMoveables.get(64), nearbyRoadUsers.get(6));
        assertSame(allMoveables.get(65), nearbyRoadUsers.get(7));
        assertSame(allMoveables.get(66), nearbyRoadUsers.get(8));
    }

    @Test
    void testDistanceTo() {

        moveable.setXPos(0);
        moveable.setYPos(0);

        MoveableObject other = new Passenger();
        other.setXPos(3);
        other.setYPos(4);

        assertEquals(5, facade.distanceTo(other));
    }

    @Test 	
    void testTurnLeft() {
        Passenger passenger = new Passenger(Orientation.NORTH, 50, 50, 1); 	
        ScriptContext context = new ScriptContext(passenger, null, null);
        facade = MoveableFacade.createFor(passenger, context);

        facade.turnLeft();
        assertEquals(Orientation.NORTH_WEST, passenger.getOrientation()); 	
    } 	
	
    @Test 	
    void testTurnRight() { 	
        Passenger passenger = new Passenger(Orientation.NORTH, 50, 50, 1); 	
        ScriptContext context = new ScriptContext(passenger, null, null);
        facade = MoveableFacade.createFor(passenger, context);

        facade.turnRight();
        assertEquals(Orientation.NORTH_EAST, passenger.getOrientation()); 
    }
}
