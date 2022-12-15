package de.hsrm.mi.swt_project.demo.messaging;

import de.hsrm.mi.swt_project.demo.controls.TileControl;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.controls.PlaceableControl;
import de.hsrm.mi.swt_project.demo.editor.placeableObjects.PlaceableObjectType;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;

/**
 * Data Transfer Object that the server receives by the client when a placeable object is added or removed
 * @author Marieke Schmitz
 */
public record GetPlaceableObjectUpdateDTO (
    PlaceableObjectType type,
    PlaceableControl control,
    int xPos, 
    int yPos){}
