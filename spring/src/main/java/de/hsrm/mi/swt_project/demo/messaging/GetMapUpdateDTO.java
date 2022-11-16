package de.hsrm.mi.swt_project.demo.messaging;

import de.hsrm.mi.swt_project.demo.common.Orientation;
import de.hsrm.mi.swt_project.demo.editor.Placeable;
import de.hsrm.mi.swt_project.demo.editor.Tiletype;

/**
 * Data Transfer Object that the server receives by the client on every map change on editor mode
 * @author Tom Gouthier
 */
public record GetMapUpdateDTO (
    Tiletype type,
    Orientation orientation, 
    int prevXPos, 
    int prevYpos, 
    int newXPos, 
    int newYPos, 
    Placeable [] placedObjects){}
