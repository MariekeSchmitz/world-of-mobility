package de.hsrm.mi.swt_project.demo.movables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;

@SpringBootTest
class PassengerTest {

    @Test
    void testTurn() {

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
    void testMove() {

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

    @Test
    void testCopy() {

        Passenger passenger = new Passenger(Orientation.EAST, 50, 50, 20);
        passenger.setCurrentVelocity(0.5f);

        Passenger copy = passenger.copy();

        assertNotSame(passenger, copy);
        assertEquals(passenger, copy);

    }
    
    @Test
    void testEquals() {
        Passenger p1 = new Passenger(50, 50);
        Passenger p2 = new Passenger(50, 50);

        assertNotSame(p1, p2);
        assertEquals(p1, p2);
        assertEquals(p2, p1);
    }

    @Test
    void testHashcode() {
        Passenger p1 = new Passenger(10, 20);
        Passenger p2 = new Passenger(10, 20);

        assertNotSame(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Passenger p = new Passenger(1, 2);
        assertEquals("Passenger[xPos=1.00,yPos=2.00,curV=0.00,maxV=1.00,cap=1.00,orientation=NORTH]", p.toString());
    }
    
}
