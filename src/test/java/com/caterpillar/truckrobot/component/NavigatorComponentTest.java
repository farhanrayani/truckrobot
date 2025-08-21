package com.caterpillar.truckrobot.component;

import com.caterpillar.truckrobot.model.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class NavigatorComponentTest {

    private NavigatorComponent navigatorComponent;

    @BeforeEach
    void setUp() {
        navigatorComponent = new NavigatorComponent();
    }

    @Test
    void testPlace_ValidPosition_ReturnsTrue() {
        boolean result = navigatorComponent.place(0, 0, Turn.NORTH);
        assertTrue(result);
        assertTrue(navigatorComponent.isRobotPlaced());
    }

    @ParameterizedTest
    @CsvSource({
        "-1, 0, NORTH",
        "0, -1, NORTH",
        "5, 0, NORTH",
        "0, 5, NORTH",
        "-1, -1, NORTH",
        "5, 5, NORTH"
    })
    void testPlace_InvalidPosition_ReturnsFalse(int x, int y, Turn turn) {
        boolean result = navigatorComponent.place(x, y, turn);
        assertFalse(result);
        assertFalse(navigatorComponent.isRobotPlaced());
    }

    @Test
    void testMove_WhenPlaced() {
        navigatorComponent.place(1, 1, Turn.NORTH);
        navigatorComponent.move();
        assertEquals("1,2,NORTH", navigatorComponent.report());
    }

    @Test
    void testMove_WhenNotPlaced() {
        navigatorComponent.move(); // Should be ignored
        assertEquals("ROBOT MISSING", navigatorComponent.report());
    }

    @Test
    void testTurnLeft_WhenPlaced() {
        navigatorComponent.place(1, 1, Turn.NORTH);
        navigatorComponent.turnLeft();
        assertEquals("1,1,WEST", navigatorComponent.report());
    }

    @Test
    void testTurnLeft_WhenNotPlaced() {
        navigatorComponent.turnLeft(); // Should be ignored
        assertEquals("ROBOT MISSING", navigatorComponent.report());
    }

    @Test
    void testTurnRight_WhenPlaced() {
        navigatorComponent.place(1, 1, Turn.NORTH);
        navigatorComponent.turnRight();
        assertEquals("1,1,EAST", navigatorComponent.report());
    }

    @Test
    void testTurnRight_WhenNotPlaced() {
        navigatorComponent.turnRight(); // Should be ignored
        assertEquals("ROBOT MISSING", navigatorComponent.report());
    }

    @Test
    void testReport_InitialState() {
        assertEquals("ROBOT MISSING", navigatorComponent.report());
    }

    @Test
    void testReport_AfterPlacement() {
        navigatorComponent.place(2, 3, Turn.SOUTH);
        assertEquals("2,3,SOUTH", navigatorComponent.report());
    }

    @Test
    void testReset() {
        navigatorComponent.place(2, 3, Turn.EAST);
        assertTrue(navigatorComponent.isRobotPlaced());

        navigatorComponent.reset();

        assertFalse(navigatorComponent.isRobotPlaced());
        assertEquals("ROBOT MISSING", navigatorComponent.report());
    }

    @Test
    void testBoundaryValidation() {
        // Valid boundaries
        assertTrue(navigatorComponent.place(0, 0, Turn.NORTH));
        navigatorComponent.reset();
        assertTrue(navigatorComponent.place(4, 4, Turn.NORTH));

        // Invalid boundaries
        assertFalse(navigatorComponent.place(-1, 0, Turn.NORTH));
        assertFalse(navigatorComponent.place(0, -1, Turn.NORTH));
        assertFalse(navigatorComponent.place(5, 0, Turn.NORTH));
        assertFalse(navigatorComponent.place(0, 5, Turn.NORTH));
    }
}
