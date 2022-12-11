package de.hsrm.mi.swt_project.demo.movables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;

@SpringBootTest
class MotorizedObjectTest {
    
    @Test
    void testOrientationAdjustment() {
        assertEquals(Orientation.NORTH, new MotorizedObject().getOrientation());
        assertEquals(Orientation.EAST, new MotorizedObject(Orientation.NORTH_EAST, 0, 0, 100).getOrientation());
    }

    @Test
    void testTurn() {

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
    void testCopy() {

        MotorizedObject vehicle = new MotorizedObject(Orientation.EAST, 50, 50, 20);
        vehicle.setCurrentVelocity(0.5f);

        MotorizedObject copy = vehicle.copy();

        assertNotSame(vehicle, copy);
        assertEquals(vehicle, copy);

    }
    
    @Test
    void testEquals() {
        MotorizedObject mo1 = new MotorizedObject(50, 50, 100);
        MotorizedObject mo2 = new MotorizedObject(50, 50, 100);

        assertNotSame(mo1, mo2);
        assertEquals(mo1, mo2);
        assertEquals(mo2, mo1);
    }

    @Test
    void testHashcode() {
        MotorizedObject mo1 = new MotorizedObject(10, 20, 30);
        MotorizedObject mo2 = new MotorizedObject(10, 20, 30);

        assertNotSame(mo1, mo2);
        assertEquals(mo1.hashCode(), mo2.hashCode());
    }

    @Test
    void testToString() {
        MotorizedObject p = new MotorizedObject(1, 2, 3);
        assertEquals("MotorizedObject[xPos=1.00,yPos=2.00,curV=0.00,maxV=3.00,cap=1.00,orientation=NORTH]", p.toString());
    }

}
