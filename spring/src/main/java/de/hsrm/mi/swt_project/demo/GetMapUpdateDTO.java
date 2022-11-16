package de.hsrm.mi.swt_project.demo;

public record GetMapUpdateDTO (
    Tiletype type, 
    Orientation orientation, 
    int prevXPos, 
    int prevYpos, 
    int newXPos, 
    int newYPos, 
    Placeable [] placedObjects){}
