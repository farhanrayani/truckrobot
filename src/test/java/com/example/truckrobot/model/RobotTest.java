package com.example.truckrobot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    private Robot robot;

    @BeforeEach
    void setUp() {
        robot = new Robot();
    }

    @Test
    void testInitialState() {
        assertFalse(robot.isPlaced());
        assertEquals("ROBOT MISSING", robot.report());
        assertNull(robot.getPosition());
        assertNull(robot.getFacing());
    }

    @Test
    void testPlace_ValidPosition() {
        robot.place(1, 2, Direction.NORTH);

        assertTrue(robot.isPlaced());
        assertEquals(1, robot.getPosition().getX());
        assertEquals(2, robot.getPosition().getY());
        assertEquals(Direction.NORTH, robot.getFacing());
        assertEquals("1,2,NORTH", robot.report());
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, NORTH",
        "4, 4, SOUTH",
        "2, 3, EAST",
        "1, 1, WEST"
    })
    void testPlace_BoundaryPositions(int x, int y, Direction direction) {
        robot.place(x, y, direction);

        assertTrue(robot.isPlaced());
        assertEquals(x, robot.getPosition().getX());
        assertEquals(y, robot.getPosition().getY());
        assertEquals(direction, robot.getFacing());
    }

    @Test
    void testMove_ValidMovement() {
        robot.place(1, 1, Direction.NORTH);
        robot.move();

        assertEquals("1,2,NORTH", robot.report());
    }

    @Test
    void testMove_PreventsFallingOffTable() {
        // Test north boundary
        robot.place(2, 4, Direction.NORTH);
        robot.move(); // Should not move off table
        assertEquals("2,4,NORTH", robot.report());

        // Test south boundary
        robot.place(2, 0, Direction.SOUTH);
        robot.move(); // Should not move off table
        assertEquals("2,0,SOUTH", robot.report());

        // Test east boundary
        robot.place(4, 2, Direction.EAST);
        robot.move(); // Should not move off table
        assertEquals("4,2,EAST", robot.report());

        // Test west boundary
        robot.place(0, 2, Direction.WEST);
        robot.move(); // Should not move off table
        assertEquals("0,2,WEST", robot.report());
    }

    @Test
    void testMove_WithoutPlacement() {
        robot.move(); // Should be ignored
        assertFalse(robot.isPlaced());
        assertEquals("ROBOT MISSING", robot.report());
    }

    @Test
    void testTurnLeft() {
        robot.place(1, 1, Direction.NORTH);
        robot.turnLeft();

        assertEquals("1,1,WEST", robot.report());
    }

    @Test
    void testTurnLeft_WithoutPlacement() {
        robot.turnLeft(); // Should be ignored
        assertFalse(robot.isPlaced());
    }

    @Test
    void testTurnRight() {
        robot.place(1, 1, Direction.NORTH);
        robot.turnRight();

        assertEquals("1,1,EAST", robot.report());
    }

    @Test
    void testTurnRight_WithoutPlacement() {
        robot.turnRight(); // Should be ignored
        assertFalse(robot.isPlaced());
    }

    @Test
    void testReset() {
        robot.place(2, 3, Direction.EAST);
        assertTrue(robot.isPlaced());

        robot.reset();

        assertFalse(robot.isPlaced());
        assertEquals("ROBOT MISSING", robot.report());
        assertNull(robot.getPosition());
        assertNull(robot.getFacing());
    }

    @Test
    void testComplexMovementSequence() {
        // Test sequence: PLACE 1,2,EAST MOVE MOVE LEFT MOVE REPORT
        robot.place(1, 2, Direction.EAST);
        robot.move(); // 2,2,EAST
        robot.move(); // 3,2,EAST
        robot.turnLeft(); // 3,2,NORTH
        robot.move(); // 3,3,NORTH

        assertEquals("3,3,NORTH", robot.report());
    }
}