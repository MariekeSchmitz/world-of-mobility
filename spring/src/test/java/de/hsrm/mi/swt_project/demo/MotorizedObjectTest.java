package de.hsrm.mi.swt_project.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;

@SpringBootTest
public class MotorizedObjectTest {
    
    @Test
    protected void testOrientationAdjustment() {
        assertEquals(Orientation.NORTH, new MotorizedObject().getOrientation());
        assertEquals(Orientation.EAST, new MotorizedObject(Orientation.NORTH_EAST, 0, 0, 100).getOrientation());
    }

    @Test
    protected void testTurn() {

        Orientation[] expectedOrientations = {
            Orientation.EAST,
            Orientation.SOUTH,
            Orientation.WEST,
            Orientation.NORTH
        };

        MotorizedObject vehicle = new MotorizedObject();

        for (Orientation orientation: expectedOrientations) {
            vehicle.turn(Direction.RIGHT);
            assertEquals(orientation, vehicle.getOrientation());
        }

    }

    @Test
    protected void testMove() {

        MotorizedObject vehicle = new MotorizedObject(Orientation.EAST, 50, 50, 2);
        vehicle.setCurrentVelocity(0.5f);

        for (int i = 0; i < 10; i++) {
            vehicle.move();
            if (i % 2 == 0) {
                vehicle.turn(Direction.LEFT);
            } else {
                vehicle.turn(Direction.RIGHT);
            }
        }

        assertEquals(55, vehicle.getXPos());
        assertEquals(55, vehicle.getYPos());
    }

}
