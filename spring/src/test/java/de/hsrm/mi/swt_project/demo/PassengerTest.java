package de.hsrm.mi.swt_project.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.movables.Passenger;

@SpringBootTest
public class PassengerTest {

    @Test
    protected void testTurn() {

        Orientation[] expectedOrientations = {
            Orientation.NORTH_EAST,
            Orientation.EAST,
            Orientation.SOUTH_EAST,
            Orientation.SOUTH,
            Orientation.SOUTH_WEST,
            Orientation.WEST,
            Orientation.NORTH_WEST,
            Orientation.NORTH
        };

        Passenger passenger = new Passenger();

        for (Orientation orientation: expectedOrientations) {
            passenger.turn(Direction.RIGHT);
            assertEquals(orientation, passenger.getOrientation());
        }
    }

    @Test
    protected void testMove() {

        float startX = 50;
        float startY = 50;

        Passenger passenger = new Passenger(Orientation.EAST, startX, startY, 2);
        passenger.setCurrentVelocity(0.5f);


        // Run a circle
        for (int i = 0; i < Orientation.values().length; i++) {

            passenger.move();
            passenger.turn(Direction.LEFT);

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
