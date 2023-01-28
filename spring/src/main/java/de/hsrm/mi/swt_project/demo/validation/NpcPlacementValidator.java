package de.hsrm.mi.swt_project.demo.validation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByCar;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.Walkable;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;

/**
     * Validates if npc can be placed.
     * Checks if the types of tile and moveable fit together.
     * Also checks if there is already a npc on the position.
     * 
     * 
     * @author Tom Gouthier, Marie Bohnert
     */
public class NpcPlacementValidator implements Validator {

    protected MoveableObject npcToValidate;
    protected List<MoveableObject> npcs;
    protected Tile tileToPlaceOn;
    Logger logger = LoggerFactory.getLogger(getClass());
    
    

    
    public NpcPlacementValidator(MoveableObject npcToValidate, List<MoveableObject> npcs, Tile tileToPlaceOn) {
        this.npcToValidate = npcToValidate;
        this.npcs = npcs;
        this.tileToPlaceOn = tileToPlaceOn;
    }
    
    @Override
    public boolean validate() {
        int xPos = (int) npcToValidate.getXPos();
        int yPos = (int) npcToValidate.getYPos();

        if (npcToValidate instanceof Passenger) {

            if (!(tileToPlaceOn instanceof Walkable)) {
                logger.error("npc cannot be placed. Passenger npc cant be put on non walkable tile.");
                return false;
            }

        } else if (npcToValidate instanceof MotorizedObject) {

            if (!(tileToPlaceOn instanceof DriveableByCar)) {
                logger.error("npc cannot be placed. Motorized npc cant be put on non driveable tile.");
                return false;
            }

        }

        for (MoveableObject npc : npcs) {

            if (xPos == npc.getXPos() && yPos == npc.getYPos()) {
                logger.error("npc cannot be placed. Already another npc on position {} {}.",npcToValidate.getXPos(), npcToValidate.getYPos());
                return false;
            }
        }

        logger.info("npc placement is valid");
        return true;
    }
}
