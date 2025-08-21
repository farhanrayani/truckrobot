package com.example.truckrobot.service;

import com.example.truckrobot.model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RobotServiceTest {

    private RobotService robotService;

    @BeforeEach
    void setUp() {
        robotService = new RobotService();
    }

    @Test
    void testPlace_ValidPosition_ReturnsTrue() {
        boolean result = robotService.place(0, 0, Direction.NORTH);
        assertTrue(result);
        assertTrue(robotService.isRobotPlaced());
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
    void testPlace_InvalidPosition_ReturnsFalse(int x, int y, Direction direction) {
        boolean result = robotService.place(x, y, direction);
        assertFalse(result);
        assertFalse(robotService.isRobotPlaced());
    }

    @Test
    void testMove_WhenPlaced() {
        robotService.place(1, 1, Direction.NORTH);
        robotService.move();
        assertEquals("1,2,NORTH", robotService.report());
    }

    @Test
    void testMove_WhenNotPlaced() {
        robotService.move(); // Should be ignored
        assertEquals("ROBOT MISSING", robotService.report());
    }

    @Test
    void testTurnLeft_WhenPlaced() {
        robotService.place(1, 1, Direction.NORTH);
        robotService.turnLeft();
        assertEquals("1,1,WEST", robotService.report());
    }

    @Test
    void testTurnLeft_WhenNotPlaced() {
        robotService.turnLeft(); // Should be ignored
        assertEquals("ROBOT MISSING", robotService.report());
    }

    @Test
    void testTurnRight_WhenPlaced() {
        robotService.place(1, 1, Direction.NORTH);
        robotService.turnRight();
        assertEquals("1,1,EAST", robotService.report());
    }

    @Test
    void testTurnRight_WhenNotPlaced() {
        robotService.turnRight(); // Should be ignored
        assertEquals("ROBOT MISSING", robotService.report());
    }

    @Test
    void testReport_InitialState() {
        assertEquals("ROBOT MISSING", robotService.report());
    }

    @Test
    void testReport_AfterPlacement() {
        robotService.place(2, 3, Direction.SOUTH);
        assertEquals("2,3,SOUTH", robotService.report());
    }

    @Test
    void testReset() {
        robotService.place(2, 3, Direction.EAST);
        assertTrue(robotService.isRobotPlaced());

        robotService.reset();

        assertFalse(robotService.isRobotPlaced());
        assertEquals("ROBOT MISSING", robotService.report());
    }

    @Test
    void testBoundaryValidation() {
        // Valid boundaries
        assertTrue(robotService.place(0, 0, Direction.NORTH));
        robotService.reset();
        assertTrue(robotService.place(4, 4, Direction.NORTH));

        // Invalid boundaries
        assertFalse(robotService.place(-1, 0, Direction.NORTH));
        assertFalse(robotService.place(0, -1, Direction.NORTH));
        assertFalse(robotService.place(5, 0, Direction.NORTH));
        assertFalse(robotService.place(0, 5, Direction.NORTH));
    }
}
