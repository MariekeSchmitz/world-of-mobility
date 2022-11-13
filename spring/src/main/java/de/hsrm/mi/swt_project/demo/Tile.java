package de.hsrm.mi.swt_project.demo;

import java.util.List;

public abstract class Tile implements Turnable{
    
    protected Orientation orientation;
    protected List <Placeable> placedObjects;
    protected TileType type;


    public void addPlaceable(Placeable placeable) {

    }


    @Override
    public void turn(Direction direction) {

    }


}
