package de.hsrm.mi.swt_project.demo.instancehandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;


@SpringBootTest 
class GameInstanceTest {

    private GameInstance gameInstance;
    private Passenger passenger;
    private MotorizedObject car;


    /**
     * Sets up following scenario
     * 
     *  ------------- -------------
     * |   SIDEWAY   |   SIDEWAY   |
     * | Passenger v |             |
     *  ------------- -------------
     * |    STREET   |   STREET    |
     * |             |    Car ^    |
     *  ------------- -------------
     * 
     * IMPORTANT: To test position change of MoveableObjects,
     *            you need to add them manually to the GameInstance
     *            at the start of the test, e.g. via the addPlayer method.
     */
    @BeforeEach
    void setup() {

        GameMap map = new GameMap();

        passenger = new Passenger(Orientation.SOUTH, 0.5f, 0.5f, 1);
        car = new MotorizedObject(Orientation.NORTH, 1.5f, 1.5f, 1);

        Tile street = Tiletype.STREET_CROSS.createTile();
        Tile sideway = Tiletype.SIDEWAY.createTile();

        map.getTiles()[0][0] = sideway;
        map.getTiles()[0][1] = sideway;
        map.getTiles()[1][0] = street;
        map.getTiles()[1][1] = street;

        gameInstance = new GameInstance(map, "TestGame", 1);

    }

    @Test
    void testDeepCopyOfMapNPCsInConstructor() {

        GameMap map = new GameMap();
        MoveableObject original = new Passenger();

        map.addNpc(original);

        GameInstance instance = new GameInstance(map, "Test", 1);

        MoveableObject copy = instance.getMoveableObjects().get("NPC0");

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    void testValidateAndMove() {

        gameInstance.addPlayer("Test1", passenger);
        gameInstance.addPlayer("Test2", car);

        float startPosXPassenger = passenger.getXPos();
        float startPosYPassenger = passenger.getYPos();

        float startPosXCar = car.getXPos();
        float startPosYCar = car.getYPos();

        // Moving forward in default setup should result
        // in invalid moves for passenger and car
        passenger.setCurrentVelocity(1);
        car.setCurrentVelocity(1);
        
        gameInstance.update();

        // passenger should not be moved and velocity should be reset
        assertEquals(startPosXPassenger, passenger.getXPos());
        assertEquals(startPosYPassenger, passenger.getYPos());
        assertEquals(0, passenger.getCurrentVelocity());

        // car should not be moved and velocity should be reset
        assertEquals(startPosXCar, car.getXPos());
        assertEquals(startPosYCar, car.getYPos());
        assertEquals(0, passenger.getCurrentVelocity());

        passenger.turn(Direction.LEFT);
        passenger.turn(Direction.LEFT);
        passenger.setCurrentVelocity(1);

        car.turn(Direction.LEFT);
        car.setCurrentVelocity(1);        

        gameInstance.update();

        // car and passenger should have changed position after valid move
        assertFalse(startPosXPassenger == passenger.getXPos() && startPosYPassenger == passenger.getYPos());
        assertFalse(startPosXCar == car.getXPos() && startPosYCar == car.getYPos());
    }
    
    @Test
    void testUpdate() {

        gameInstance.addPlayer("Test1", passenger);

        float startX = passenger.getYPos();
        float startY = passenger.getXPos();

        passenger.setCurrentVelocity(0.2f);

        // Run a circle
        for (int i = 0; i < Orientation.values().length; i++) {

            passenger.turn(Direction.LEFT);
            this.gameInstance.update();

            // Always away from start position before last step
            if (i < Orientation.values().length - 1) {
                assertFalse(startX == passenger.getXPos() && startY == passenger.getYPos());
            }
        }

        // Be at start position after full circle
        assertEquals(startX, passenger.getXPos());
        assertEquals(startY, passenger.getYPos());
    }
}
