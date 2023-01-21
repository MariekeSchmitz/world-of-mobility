package de.hsrm.mi.swt_project.demo.railingsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.swt_project.demo.api.game.GameRestController;
import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

public class RailingBehaviour {

    Logger logger = LoggerFactory.getLogger(GameRestController.class);
    public void railCoordinates(MoveableObject moveable, Tile tile, Direction dir){
        float movement = moveable.getCurrentVelocity() * moveable.getMaxVelocity();
        if(tile.getType().equals(Tiletype.STREET_STRAIGHT)){
            straightBehavier(moveable, tile, movement);
        } else if(tile.getType().equals(Tiletype.STREET_CURVE)){
            curveBehavier(moveable, tile, movement);
        }
    }

    public void straightBehavier(MoveableObject moveable, Tile tile, float movement){
        float xPos = moveable.getxPos();
        float yPos = moveable.getyPos();
        Orientation movOr = moveable.getOrientation();
        if(tile.getOrientation() == Orientation.NORTH || tile.getOrientation() == Orientation.SOUTH){
            if(movOr == Orientation.NORTH){
                moveable.setXPos(((int)xPos)+0.7f);
                moveable.setYPos(yPos+movement);
            } else if(movOr == Orientation.SOUTH){
                moveable.setXPos(((int)xPos)+0.3f);
                moveable.setYPos(yPos-movement);
            }
        } else {
            if(movOr == Orientation.EAST){
                moveable.setYPos(((int)yPos)+0.3f);
                moveable.setXPos(xPos+movement);
            } else if(movOr == Orientation.WEST){
                moveable.setYPos(((int)yPos)+0.7f);
                moveable.setXPos(xPos-movement);
            }
        }
    }  

    public void curveBehavier(MoveableObject moveable, Tile tile, float movement){
        float xPos = moveable.getxPos();
        float yPos = moveable.getyPos();
        Orientation movOr = moveable.getOrientation();
        if(tile.getOrientation() == Orientation.NORTH){
            if(movOr == Orientation.NORTH){
                moveable.setXPos(((int)xPos)+0.7f);
                if(yPos+movement > ((int)yPos)+0.7f){
                    moveable.setYPos(((int)yPos)+0.7f);
                    moveable.turn(Direction.LEFT);
                    curveBehavier(moveable, tile, 0.002f);
                } else moveable.setYPos(yPos+movement);
            } else if (movOr == Orientation.EAST) {
                moveable.setXPos(((int)xPos)+0.3f);
                if(yPos+movement > ((int)yPos)+0.3f){
                    moveable.setYPos(((int)yPos)+0.3f);
                    moveable.turn(Direction.RIGHT);
                    curveBehavier(moveable, tile, 0.002f);
                } else moveable.setYPos(yPos+movement);
            } else if (movOr == Orientation.SOUTH) {
                moveable.setXPos(((int)xPos)+0.3f);
                moveable.setYPos(yPos-movement);
            } else if (movOr == Orientation.WEST) {
                moveable.setYPos(((int)yPos)+0.7f);
                moveable.setXPos(xPos-movement);
            }
        } else if(tile.getOrientation() == Orientation.EAST){
            if(movOr == Orientation.NORTH){
                moveable.setXPos(((int)xPos)+0.7f);
                moveable.setYPos(yPos+movement);
            } else if (movOr == Orientation.EAST) {
                moveable.setYPos(((int)yPos)+0.3f);
                if(xPos+movement > ((int)xPos)+0.7f){
                    moveable.setXPos(((int)xPos)+0.7f);
                    moveable.turn(Direction.LEFT);
                    curveBehavier(moveable, tile, 0.002f);
                } else moveable.setXPos(xPos+movement);
            } else if (movOr == Orientation.SOUTH) {
                moveable.setXPos(((int)xPos)+0.3f);
                if(yPos+movement > ((int)yPos)+0.3f){
                    moveable.setXPos(((int)xPos)+0.3f);
                    moveable.turn(Direction.RIGHT);
                    curveBehavier(moveable, tile, 0.002f);
                } else moveable.setYPos(yPos-movement);
            } else if (movOr == Orientation.WEST) {
                moveable.setYPos(((int)yPos)+0.7f);
                moveable.setXPos(xPos-movement);
            }
        } else if(tile.getOrientation() == Orientation.SOUTH){
            if(movOr == Orientation.NORTH){
                moveable.setXPos(((int)xPos)+0.7f);
                moveable.setYPos(yPos+movement);
            } else if (movOr == Orientation.EAST) {
                moveable.setYPos(((int)yPos)+0.3f);
                moveable.setXPos(xPos+movement);
            } else if (movOr == Orientation.SOUTH) {
                moveable.setXPos(((int)xPos)+0.3f);
                if(yPos-movement < ((int)yPos)+0.3f){
                    moveable.setYPos(((int)yPos)+0.3f);
                    moveable.turn(Direction.LEFT);
                    curveBehavier(moveable, tile, 0.002f);
                } else moveable.setYPos(yPos-movement);
            } else if (movOr == Orientation.WEST) {
                moveable.setYPos(((int)yPos)+0.7f);
                if(xPos-movement < ((int)xPos)+0.7f){
                    moveable.setYPos(((int)yPos)+0.7f);
                    moveable.turn(Direction.RIGHT);
                    curveBehavier(moveable, tile, 0.002f);
                } else moveable.setXPos(xPos-movement);
            }
        } else if(tile.getOrientation() == Orientation.WEST){
            if(movOr == Orientation.NORTH){
                moveable.setXPos(((int)xPos)+0.7f);
                if(yPos+movement > ((int)yPos)+0.3f){
                    moveable.setYPos(((int)yPos)+0.3f);
                    moveable.turn(Direction.RIGHT);
                    curveBehavier(moveable, tile, 0.002f);
                } else moveable.setYPos(yPos+movement);
            } else if (movOr == Orientation.EAST) {
                moveable.setYPos(((int)yPos)+0.3f);
                moveable.setXPos(xPos+movement);
            } else if (movOr == Orientation.SOUTH) {
                moveable.setXPos(((int)xPos)+0.3f);
                moveable.setYPos(yPos-movement);
            } else if (movOr == Orientation.WEST) {
                moveable.setYPos(((int)yPos)+0.7f);
                if(xPos-movement < ((int)xPos)+0.3f){
                    moveable.setYPos(((int)yPos)+0.7f);
                    moveable.turn(Direction.LEFT);
                    curveBehavier(moveable, tile, 0.002f);
                } else moveable.setXPos(xPos-movement);
            }
        }
    }  



}
