package de.hsrm.mi.swt_project.demo.instancehandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import de.hsrm.mi.swt_project.demo.controls.PlaceableControl;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Placeable;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.PlaceableObjectType;
import de.hsrm.mi.swt_project.demo.editor.tiles.GrassTile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;

@SpringBootTest
public class EditorInstanceTest {

    EditorInstance editorInstance;
    long editorId;


    @BeforeEach
    void setUp() {
        
        Tile[][] gameMap = new Tile[3][3];

        for (int i = 0; i < gameMap.length; i++){
            for (int j = 0; j < gameMap.length; j++){
                gameMap[i][j] = new GrassTile();
            }
        }

        GameMap map = new GameMap();
        map.setTiles(gameMap);
        editorInstance = new EditorInstance(map, 1, "/test");

    }
  
    @Test 
    void testEditPlaceablesOnMapAddControlSuccess() throws Exception {
        boolean validationSuccess = editorInstance.editPlaceablesOnMap(1, 1, PlaceableControl.ADD, PlaceableObjectType.TREE);
        assertTrue(validationSuccess);

        Tile editedTile = editorInstance.getMap().getTiles()[1][1];
        Placeable placedObject = editedTile.getPlacedObject();

        assertEquals(PlaceableObjectType.TREE, placedObject.getType());        
    }

    @Test 
    void testEditPlaceablesOnMapAddControlFail() throws Exception {
        boolean validationSuccess = editorInstance.editPlaceablesOnMap(1, 1, PlaceableControl.ADD, PlaceableObjectType.TRAFFIC_LIGHT);
        assertFalse(validationSuccess);

        Tile editedTile = editorInstance.getMap().getTiles()[1][1];
        Placeable placedObject = editedTile.getPlacedObject();

        assertNull(placedObject);        
    }


    @Test 
    void testEditPlaceablesOnMapRemoveControlSuccess() throws Exception {
        
        editorInstance.editPlaceablesOnMap(1, 1, PlaceableControl.ADD, PlaceableObjectType.TREE);
        boolean validationSuccess = editorInstance.editPlaceablesOnMap(1, 1, PlaceableControl.REMOVE, PlaceableObjectType.TREE);
        assertTrue(validationSuccess);

        Tile editedTile = editorInstance.getMap().getTiles()[1][1];
        Placeable placedObject = editedTile.getPlacedObject();
        assertNull(placedObject);        
    }



}
