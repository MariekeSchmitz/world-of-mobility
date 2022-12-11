package de.hsrm.mi.swt_project.demo.instancehandling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;


@SpringBootTest 
class GameInstanceTest {

    private GameInstance gameInstance;
    private Passenger passenger;
    private MotorizedObject car;


    @BeforeEach
    void setup() {
        GameMap map = new GameMap();

        passenger = new Passenger(Orientation.SOUTH, 0.5f, 0.5f, 1);
        car = new MotorizedObject(Orientation.NORTH, 1.5f, 1.5f, 1);

        Tile street = Tiletype.STREET_CROSS.createTile();
        Tile sideway = Tiletype.SIDEWAY.createTile();

        map.addNpc(passenger);
        map.addNpc(car);
        
        map.getTiles()[0][0] = sideway;
        map.getTiles()[0][1] = sideway;
        map.getTiles()[1][0] = street;
        map.getTiles()[1][1] = street;

        gameInstance = new GameInstance(map, "TestGame", 1);

    }

    @Test
    void testValidateAndMove() {

        float currentPosXPassenger = passenger.getXPos();
        float currentPosYPassenger = passenger.getYPos();
        float currentPosXCar = car.getXPos();
        float currentPosYCar = car.getYPos();

        passenger.setCurrentVelocity(1);
        car.setCurrentVelocity(1);
        
        gameInstance.update();

        assertEquals(currentPosXPassenger, passenger.getXPos());
        assertEquals(currentPosYPassenger, passenger.getYPos());
        assertEquals(currentPosXCar, car.getXPos());
        assertEquals(currentPosYCar, car.getYPos());
    }
    
}
