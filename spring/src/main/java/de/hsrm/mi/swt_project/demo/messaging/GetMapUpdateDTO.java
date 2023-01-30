package de.hsrm.mi.swt_project.demo.messaging;

import de.hsrm.mi.swt_project.demo.controls.TileControl;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;

/**
 * Data Transfer Object that the server receives by the client on every map change on editor mode
 * @author Tom Gouthier
 */
public record GetMapUpdateDTO (
    Tiletype type,
    Orientation orientation, 
    TileControl control,
    int xPos, 
    int yPos){}
