package de.hsrm.mi.swt_project.demo.validation;
	
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
	
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
	
import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.PassengerTile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Streetile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;
	
	
@SpringBootTest
class MovementValidatorTest {
	
    private Tile[][] map;
	
    @BeforeEach
    void setup() {

        map = new Tile[5][5];
	
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Tiletype.STREET_STRAIGHT.createTile();
            }
        }
    }
	
	
    @Test
    void testValidateWithValidMovement() {
	
        MoveableObject moveable = new MotorizedObject();
	
        MovementValidator validator = new MovementValidator(map, moveable);
	
        boolean result = validator.validate();
        assertTrue(result);
    }
	
	
    @Test
    void testValidateWithInvalidMovementOffMap() {
	
        MoveableObject moveable = new MotorizedObject();
        moveable.setXPos(5);
	
        MovementValidator validator = new MovementValidator(map, moveable);
	
        boolean result = validator.validate();
        assertFalse(result);
    }
	
	
    @Test
    void testValidateWithInvalidMovementOnNonDriveableTile() {
	
        MoveableObject moveable = new MotorizedObject(Orientation.NORTH, 2, 1, 1);
        moveable.setCurrentVelocity(1);
	
        MovementValidator validator = new MovementValidator(map, moveable);
	
        map[2][2] = new PassengerTile();
	
        boolean result = validator.validate();
        assertFalse(result);
    }
	
	
    @Test
    void testValidateWithInvalidMovementOnNonWalkableTile() {
	
        MoveableObject moveable = new Passenger(Orientation.NORTH, 2, 1, 1);
        moveable.setCurrentVelocity(1);
	
        MovementValidator validator = new MovementValidator(map, moveable);
	
        map[2][2] = new Streetile();
	
        boolean result = validator.validate();
        assertFalse(result);
    }
	
	
    @Test
    void testValidateWithMovementAcrossInvalidTile() {
	
        MoveableObject moveable = new MotorizedObject(Orientation.NORTH, 0, 0, 2);
        moveable.setCurrentVelocity(1.0f);
	
        MovementValidator validator = new MovementValidator(map, moveable);
	
        map[1][0] = new PassengerTile();
	
        boolean result = validator.validate();
        assertFalse(result);
    }
	
	
    @Test
    void testValidateWithMovementFromInvalidDirection() {
	
        MoveableObject moveable = new MotorizedObject(Orientation.NORTH, 0, 0, 1);
	
        moveable.setCurrentVelocity(1.0f);
        moveable.turn(Direction.RIGHT);
	
        MovementValidator validator = new MovementValidator(map, moveable);
	
        boolean result = validator.validate();
        assertFalse(result);
	
    }
}