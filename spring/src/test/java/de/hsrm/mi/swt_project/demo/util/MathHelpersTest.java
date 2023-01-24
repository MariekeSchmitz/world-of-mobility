package de.hsrm.mi.swt_project.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MathHelpersTest {

    @Test
    void testSquaredEuclideanDistance() {

        float startXPos = 3.0f;
        float startYPos = 5.0f;

        float endXPos = 0.0f;
        float endYPos = 9.0f;

        float expected = 25.0f;
        float actual = MathHelpers.squaredEuclideanDistance(startXPos, startYPos, endXPos, endYPos);

        assertEquals(expected, actual);
    }

    @Test
    void testEuclideanDistance() {

        float startXPos = 6.0f;
        float startYPos = 4.0f;

        float endXPos = 9.0f;
        float endYPos = 0.0f;

        float expected = 5.0f;
        float actual = MathHelpers.euclideanDistance(startXPos, startYPos, endXPos, endYPos);

        assertEquals(expected, actual);

    }

    
}
