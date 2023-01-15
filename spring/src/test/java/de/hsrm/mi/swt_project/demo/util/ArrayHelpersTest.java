package de.hsrm.mi.swt_project.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArrayHelpersTest {
    
    @Test
    void testCopy() {

        String[] src = { "Hallo", "Welt"};
        String[] actual = new String[8];

        String[] expected = { "Hallo", "Welt", null, null, null, null, null, null };

        ArrayHelpers.copy(src, actual);

        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i]);
        }

    }

    @Test
    void testCopy2D() {

        String[][] src = {
            { "Hallo", "Aloha" },
            { "Ahoi", "Gude" },
        };

        String[][] actual = new String[4][2];

        String[][] expected = {
            { "Hallo", "Aloha" },
            { "Ahoi", "Gude" },
            { null, null },
            { null, null },
        };

        ArrayHelpers.copy2D(src, actual);

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[i].length; j++) {
                assertEquals(expected[i][j], actual[i][j]);
            }
        }
    }

    @Test
    void testExpand() {
        String[] src = { "Aloha" };
        String[] actual = new String[5];
        String[] expected = { null, "Aloha", null, null, null };

        ArrayHelpers.expand(src, actual, 1);

        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    void testExpand2D() {

        String[][] src = {
            { "A", "B" },
            { "C", "D" }
        };

        String[][] actual = new String[6][6];

        String[][] expected = {
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, "A", "B", null },
            { null, null, null, "C", "D", null },
            { null, null, null, null, null, null },
        };

        ArrayHelpers.expand2D(src, actual, 3);

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual.length; j++) {
                assertEquals(expected[i][j], actual[i][j]);
            }
        }
    }

    @Test
    void testShrink() {

        String[] src = { "ene", "mene", "mu" };
        String[] actual = new String[2];

        String[] expected = { "mene", "mu" };

        ArrayHelpers.shrink(src, actual, 1);

        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    void testShrink2D() {

        String[][] src = { 
            { "A", "B", "C", "D", "E" },
            { "F", "G", "H", "I", "J" },
            { "K", "L", "M", "N", "O" },
            { "P", "Q", "R", "S", "T" },
            { "U", "V", "W", "X", "Y" },
        };

        String[][] actual = new String[3][3];

        String[][] expected = {
            { "M", "N", "O" },
            { "R", "S", "T" },
            { "W", "X", "Y" },
        };

        ArrayHelpers.shrink2D(src, actual, 2);

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[i].length; j++) {
                assertEquals(expected[i][j], actual[i][j]);
            }
        }
    }

    @Test
    void testTransfer() {

        String[] src = { "Hallo", "liebe", "Welt" };

        String[] actual1 = new String[1];
        String[] actual2 = new String[3];
        String[] actual3 = new String[5];

        String[] expected1 = { "liebe" };
        String[] expected2 = { "Hallo", "liebe", "Welt" };
        String[] expected3 = { null, "Hallo", "liebe", "Welt", null };

        ArrayHelpers.transfer(src, actual1);
        ArrayHelpers.transfer(src, actual2);
        ArrayHelpers.transfer(src, actual3);

        for (int i = 1; i < actual1.length; i++) {
            assertEquals(expected1[i], actual1[i]);
        }

        for (int i = 1; i < actual1.length; i++) {
            assertEquals(expected2[i], actual2[i]);
        }

        for (int i = 1; i < actual1.length; i++) {
            assertEquals(expected3[i], actual3[i]);
        }

    }

    @Test
    void testTransfer2D() {

        String[][] src = {
            { "A", "B", "C", "D" },
            { "E", "F", "G", "H" },
            { "I", "J", "K", "L" },
            { "M", "N", "O", "P" },
        };

        String[][] actual1 = new String[2][2];
        String[][] actual2 = new String[4][4];
        String[][] actual3 = new String[6][6];

        String[][] expected1 = {
            { "F", "G" },
            { "J", "K" },
        };

        String[][] expected2 = {
            { "A", "B", "C", "D" },
            { "E", "F", "G", "H" },
            { "I", "J", "K", "L" },
            { "M", "N", "O", "P" },
        };

        String[][] expected3 = {
            { null, null, null, null, null, null },
            { null, "A", "B", "C", "D", null },
            { null, "E", "F", "G", "H", null },
            { null, "I", "J", "K", "L", null },
            { null, "M", "N", "O", "P", null },
            { null, null, null, null, null, null },
        };

        ArrayHelpers.transfer2D(src, actual1);
        ArrayHelpers.transfer2D(src, actual2);
        ArrayHelpers.transfer2D(src, actual3);

        for (int i = 0; i < actual1.length; i++) {
            for (int j = 0; j < actual1[i].length; j++) {
                assertEquals(expected1[i][j], actual1[i][j]);
            }
        }
        
        for (int i = 0; i < actual2.length; i++) {
            for (int j = 0; j < actual2[i].length; j++) {
                assertEquals(expected2[i][j], actual2[i][j]);
            }
        }

        for (int i = 0; i < actual3.length; i++) {
            for (int j = 0; j < actual3[i].length; j++) {
                assertEquals(expected3[i][j], actual3[i][j]);
            }
        }
    }

    @Test
    void testRotate90CCW() {

        String[][] src = {
            { "A", "B" },
            { "C", "D" }
        };

        String[][] expected = {
            { "B", "D" },
            { "A", "C" }
        };

        String[][] actual = new String[2][2];

        ArrayHelpers.rotate90CCW(src, actual);

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                assertEquals(expected[i][j], actual[i][j]);
            }
        }
    }

    @Test
    void testRotate90CW() {

        String[][] src = {
            { "A", "B" },
            { "C", "D" }
        };

        String[][] expected = {
            { "C", "A" },
            { "D", "B" }
        };

        String[][] actual = new String[2][2];

        ArrayHelpers.rotate90CW(src, actual);

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                assertEquals(expected[i][j], actual[i][j]);
            }
        }
    }

}
