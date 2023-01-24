package de.hsrm.mi.swt_project.demo.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.collision.Collidable;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

@SpringBootTest
class CollisionValidatorTest {
    
    @Test
    void testWithoutOtherCollidables() {

        Collidable toValidate = new MotorizedObject();
        List<Collidable> collidables = new ArrayList<>();

        Validator validator = new CollisionValidator(toValidate, collidables);
        assertTrue(validator.validate());
    }

    @Test
    void testWithValidatingObjectInCollidableCollection() {

        Collidable toValidate = new MotorizedObject();

        List<Collidable> collidables = new ArrayList<>();
        collidables.add(toValidate);

        Validator validator = new CollisionValidator(toValidate, collidables);
        assertTrue(validator.validate());
    }

    @Test
    void testWithCollidablesThatShouldNotCollide() {

        MoveableObject toValidate = new MotorizedObject(Orientation.NORTH, 0, 0, 1);
        toValidate.setCurrentVelocity(1);

        List<Collidable> collidables = new ArrayList<>();

        collidables.add(new MotorizedObject(1, 1));
        collidables.add(new MotorizedObject(1, 2));

        Validator validator = new CollisionValidator(toValidate, collidables);
        assertTrue(validator.validate());
    }

    @Test
    void testWithCollidableInFront() {

        MoveableObject toValidate = new MotorizedObject(Orientation.NORTH, 0, 0, 1);
        toValidate.setCurrentVelocity(1);

        List<Collidable> collidables = new ArrayList<>();
        collidables.add(new MotorizedObject(0, 1));

        Validator validator = new CollisionValidator(toValidate, collidables);
        assertFalse(validator.validate());
    }

}
