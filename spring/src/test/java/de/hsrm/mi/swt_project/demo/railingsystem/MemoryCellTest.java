package de.hsrm.mi.swt_project.demo.railingsystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemoryCellTest {
    
    RailingMemoryCell memoryCell;

    @BeforeEach
    void setup() {
        memoryCell = new RailingMemoryCell(0, 0);
    }

    @Test 
    void testLeftTile(){
        assertFalse(memoryCell.leftTile(0, 0));
        assertTrue(memoryCell.leftTile(1, 0));
    }
}
