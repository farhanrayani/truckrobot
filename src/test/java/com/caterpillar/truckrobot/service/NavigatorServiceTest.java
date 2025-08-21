package com.caterpillar.truckrobot.service;

import com.caterpillar.truckrobot.model.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class NavigatorServiceTest {

    private NavigatorService navigatorService;

    @BeforeEach
    void setUp() {
        navigatorService = new NavigatorService();
    }

    @Test
    void testPlace_ValidPosition_ReturnsTrue() {
        boolean result = navigatorService.place(0, 0, Turn.NORTH);
        assertTrue(result);
        assertTrue(navigatorService.isRobotPlaced());
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
        boolean result = navigatorService.place(x, y, turn);
        assertFalse(result);
        assertFalse(navigatorService.isRobotPlaced());
    }

    @Test
    void testMove_WhenPlaced() {
        navigatorService.place(1, 1, Turn.NORTH);
        navigatorService.move();
        assertEquals("1,2,NORTH", navigatorService.report());
    }

    @Test
    void testMove_WhenNotPlaced() {
        navigatorService.move(); // Should be ignored
        assertEquals("ROBOT MISSING", navigatorService.report());
    }

    @Test
    void testTurnLeft_WhenPlaced() {
        navigatorService.place(1, 1, Turn.NORTH);
        navigatorService.turnLeft();
        assertEquals("1,1,WEST", navigatorService.report());
    }

    @Test
    void testTurnLeft_WhenNotPlaced() {
        navigatorService.turnLeft(); // Should be ignored
        assertEquals("ROBOT MISSING", navigatorService.report());
    }

    @Test
    void testTurnRight_WhenPlaced() {
        navigatorService.place(1, 1, Turn.NORTH);
        navigatorService.turnRight();
        assertEquals("1,1,EAST", navigatorService.report());
    }

    @Test
    void testTurnRight_WhenNotPlaced() {
        navigatorService.turnRight(); // Should be ignored
        assertEquals("ROBOT MISSING", navigatorService.report());
    }

    @Test
    void testReport_InitialState() {
        assertEquals("ROBOT MISSING", navigatorService.report());
    }

    @Test
    void testReport_AfterPlacement() {
        navigatorService.place(2, 3, Turn.SOUTH);
        assertEquals("2,3,SOUTH", navigatorService.report());
    }

    @Test
    void testReset() {
        navigatorService.place(2, 3, Turn.EAST);
        assertTrue(navigatorService.isRobotPlaced());

        navigatorService.reset();

        assertFalse(navigatorService.isRobotPlaced());
        assertEquals("ROBOT MISSING", navigatorService.report());
    }

    @Test
    void testBoundaryValidation() {
        // Valid boundaries
        assertTrue(navigatorService.place(0, 0, Turn.NORTH));
        navigatorService.reset();
        assertTrue(navigatorService.place(4, 4, Turn.NORTH));

        // Invalid boundaries
        assertFalse(navigatorService.place(-1, 0, Turn.NORTH));
        assertFalse(navigatorService.place(0, -1, Turn.NORTH));
        assertFalse(navigatorService.place(5, 0, Turn.NORTH));
        assertFalse(navigatorService.place(0, 5, Turn.NORTH));
    }
}
