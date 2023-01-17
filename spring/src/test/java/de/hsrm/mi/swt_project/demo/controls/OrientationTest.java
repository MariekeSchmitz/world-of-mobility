package de.hsrm.mi.swt_project.demo.controls;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrientationTest {
    
    @Test
    void testNext() {

        Orientation[] expectedOrder = {
            Orientation.NORTH_EAST,
            Orientation.EAST,
            Orientation.SOUTH_EAST,
            Orientation.SOUTH,
            Orientation.SOUTH_WEST,
            Orientation.WEST,
            Orientation.NORTH_WEST,
            Orientation.NORTH
        };

        Orientation orientation = Orientation.NORTH;

        for (Orientation currentExpectedOrientation: expectedOrder) {
            assertEquals(currentExpectedOrientation, orientation.next());
            orientation = orientation.next();
        }
    }

    @Test
    void testPrev() {

        Orientation[] expectedOrder = {
            Orientation.NORTH_WEST,
            Orientation.WEST,
            Orientation.SOUTH_WEST,
            Orientation.SOUTH,
            Orientation.SOUTH_EAST,
            Orientation.EAST,
            Orientation.NORTH_EAST,
            Orientation.NORTH
        };

        Orientation orientation = Orientation.NORTH;

        for (Orientation currentExpectedOrientation: expectedOrder) {
            assertEquals(currentExpectedOrientation, orientation.prev());
            orientation = orientation.prev();
        }
    }

    @Test
    void testOpposite() {

        Orientation[] orientations = {
            Orientation.NORTH,
            Orientation.NORTH_EAST,
            Orientation.EAST,
            Orientation.SOUTH_EAST,
            Orientation.NORTH_WEST,
            Orientation.WEST,
            Orientation.SOUTH_WEST,
            Orientation.SOUTH,
        };

        for (int i = 0; i < orientations.length; i++) {
            int oppositeIndex = orientations.length - 1 - i;
            assertEquals(orientations[oppositeIndex], orientations[i].opposite());
        }
    }
}
