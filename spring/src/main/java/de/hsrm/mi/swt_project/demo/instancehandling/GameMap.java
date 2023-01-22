package de.hsrm.mi.swt_project.demo.instancehandling;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import de.hsrm.mi.swt_project.demo.editor.placeableobjects.GasStation;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Pig;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.PlaceableObject;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Sheep;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.TrafficLight;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Tree;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.CanHoldNatureObject;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.CanHoldStreetObject;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByCar;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.Walkable;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;
import de.hsrm.mi.swt_project.demo.util.ArrayHelpers;

/**
 * Represents a 2d field of tiles and its npcs
 * 
 * @author Tom Gouthier
 * @author Sascha Scheid
 */
public class GameMap {

    public static final int DEFAULT_SIZE = 8;
    public static final int MAP_EXPANSION_PER_SITE = 2;

    private Tile[][] tiles = new Tile[DEFAULT_SIZE][DEFAULT_SIZE];
    private String name;
    private List<MoveableObject> npcs = new ArrayList<>();

    private transient Logger logger = LoggerFactory.getLogger(getClass());

    public GameMap() {

        fillMapWithDefaultTiles(tiles, DEFAULT_SIZE);

    }

    public GameMap(int customSize) {
        tiles = new Tile[customSize][customSize];
        fillMapWithDefaultTiles(tiles, customSize);
    }

    /**
     * adds a moveable Object to the map. e.g. a scripted car
     * 
     * @param moveable
     * 
     */
    public void addNpc(MoveableObject moveable) {

        if (moveable.getScript() == null || moveable.getScript().isEmpty()) {
            try {
                Resource resource = new ClassPathResource("defaultNPCScript.py");
                File scriptfile = resource.getFile();
                String script = Files.readString(scriptfile.toPath());
                moveable.loadScript(script);
            } catch (Exception e) {
                logger.error("LoadDefaultScript Error");
            }
        }
        this.npcs.add(moveable);
    }

    /**
     * deletes npc if found in npc-list
     * 
     * @param x
     * @param y
     * @author Tom Gouthier, Marie Bohnert
     */
    public void deleteNPC(float x, float y) throws NoNpcExistsOnCoordinates {

        logger.info("trying to remove npc with coordinates: {} {}", x, y);

        for (MoveableObject npc : npcs) {
            if (npc.getXPos() == x && npc.getYPos() == y) {
                npcs.remove(npc);
                logger.info("removed following npc: {}", npc);
                return;
            }
        }
        logger.info("npc doesn't exist");
        throw new NoNpcExistsOnCoordinates();

    }

    /**
     * Adds a Tile to the game map at a certain position.
     * 
     * If the tile is placed on the edge of the current map,
     * it is assumed, that there is not enough space for further building.
     * Therefore the game map size will be expanded with the currently placed
     * tiles staying centered.
     * 
     * @param tile Tile to place
     * @param xPos x-position of the tile
     * @param yPos y-position of the tile
     * 
     */
    public void addTile(Tile tile, int xPos, int yPos) {

        if (tile == null) {
            throw new IllegalArgumentException("tile is not allowed to be null.");
        }

        if (yPos < 0 || xPos < 0 || yPos >= this.tiles.length || xPos >= this.tiles[yPos].length) {
            throw new IllegalArgumentException("tile must be placed from position (0, 0) to (%d, %d), was (%d, %d)"
                    .formatted(this.tiles.length - 1, this.tiles[0].length - 1, xPos, yPos));
        }

        this.tiles[yPos][xPos] = tile;

        if (isExpansionNeeded(xPos, yPos)) {
            this.expandMap();
        }

    }

    /**
     * 
     * @param xPos
     * @param yPos
     */
    public void removeTile(int xPos, int yPos) {
        this.tiles[yPos][xPos] = Tiletype.GRASSTILE.createTile();
    }

    public boolean validateAndAddPlaceableObject(Tile tile, int xPos, int yPos, PlaceableObject placeableObject) {

        boolean validate = false;

        if (tile instanceof CanHoldNatureObject) {
            if (placeableObject instanceof Tree || placeableObject instanceof Pig || placeableObject instanceof Sheep) {
                validate = true;
            }
        } else if (tile instanceof CanHoldStreetObject) {
            if ((!(tile.getType().equals(Tiletype.STREET_CURVE)) && placeableObject instanceof TrafficLight) ||
                    (tile.getType().equals(Tiletype.STREET_STRAIGHT) && placeableObject instanceof GasStation)) {
                validate = true;
            }

        }

        if (validate) {
            tile.addPlaceable(placeableObject);

            if (isExpansionNeeded(xPos, yPos)) {
                this.expandMap();
            }

            return true;
        }

        return false;
    }

    private boolean isExpansionNeeded(int xPos, int yPos) {

        boolean placedOnLeftEdge = (xPos == 0);
        boolean placedOnTopEdge = (yPos == 0);
        boolean placedOnRightEdge = (xPos == this.tiles[yPos].length - 1);
        boolean placedOnBottomEdge = (yPos == this.tiles.length - 1);

        return (placedOnLeftEdge || placedOnTopEdge || placedOnRightEdge || placedOnBottomEdge);

    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public void setTile(Tile tile, int xPos, int yPos) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException("tile is not allowed to be null.");
        }

        if (yPos < 0 || xPos < 0 || yPos >= this.tiles.length || xPos >= this.tiles[yPos].length) {
            throw new IllegalArgumentException("tile must be placed from position (0, 0) to (%d, %d), was (%d, %d)"
                    .formatted(this.tiles.length - 1, this.tiles[0].length - 1, xPos, yPos));
        }

        this.tiles[yPos][xPos] = tile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MoveableObject> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<MoveableObject> npcs) {
        this.npcs = npcs;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("GameMap [tiles=");

        for (Tile[] tArr : tiles) {
            for (Tile t : tArr) {
                String toAppend = (t == null) ? "Null" : t.toString();
                builder.append(toAppend);
                builder.append(";");
            }
        }

        builder.append("name=");
        builder.append(name);
        builder.append("npcs=");
        builder.append(npcs);
        builder.append("]");

        return builder.toString();
    }

    /**
     * Creates a new 2D tile array of extended size and
     * centers values from current tile array inside the
     * created one.
     * 
     * Replaces current tile array with the new one.
     * 
     * Also adjusts position of npcs that are currently
     * placed on the map.
     */
    private void expandMap() {

        int size = this.tiles.length + (MAP_EXPANSION_PER_SITE * 2);
        Tile[][] newTiles = new Tile[size][size];

        fillMapWithDefaultTiles(newTiles, size);
        ArrayHelpers.transfer2D(this.tiles, newTiles);

        this.npcs = this.npcs
            .stream()
            .map(npc -> {

                float xPos = npc.getxPos() + MAP_EXPANSION_PER_SITE;
                float yPos = npc.getyPos() + MAP_EXPANSION_PER_SITE;

                npc.setXPos(xPos);
                npc.setYPos(yPos);

                return npc;

            }).toList();

        this.tiles = newTiles;
    }

    /**
     * Fills 2D tile array with default tiles.
     * 
     * @param newTiles
     * @param size
     */
    private void fillMapWithDefaultTiles(Tile[][] newTiles, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newTiles[i][j] = Tiletype.GRASSTILE.createTile();
            }
        }
    }

    /**
     * validates if npc can be placed
     * 
     * @param moveableObject
     * @return boolean can be placed or not
     * @author Tom Gouthier, Marie Bohnert
     */
    public boolean validateNpcPlacement(MoveableObject moveableObject) {

        int xPos = (int) moveableObject.getXPos();
        int yPos = (int) moveableObject.getYPos();

        if (moveableObject instanceof Passenger) {

            if (!(tiles[yPos][xPos] instanceof Walkable)) {
                return false;
            }

        } else if (moveableObject instanceof MotorizedObject) {

            if (!(tiles[yPos][xPos] instanceof DriveableByCar)) {
                return false;
            }

        }

        for (MoveableObject npc : npcs) {

            if (xPos == npc.getXPos() && yPos == npc.getYPos()) {
                return false;
            }
        }
        return true;
    }

}