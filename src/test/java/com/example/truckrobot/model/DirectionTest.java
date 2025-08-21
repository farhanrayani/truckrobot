package com.example.truckrobot.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testTurnLeft_AllDirections() {
        assertEquals(Direction.WEST, Direction.NORTH.turnLeft());
        assertEquals(Direction.SOUTH, Direction.WEST.turnLeft());
        assertEquals(Direction.EAST, Direction.SOUTH.turnLeft());
        assertEquals(Direction.NORTH, Direction.EAST.turnLeft());
    }

    @Test
    void testTurnRight_AllDirections() {
        assertEquals(Direction.EAST, Direction.NORTH.turnRight());
        assertEquals(Direction.SOUTH, Direction.EAST.turnRight());
        assertEquals(Direction.WEST, Direction.SOUTH.turnRight());
        assertEquals(Direction.NORTH, Direction.WEST.turnRight());
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTurnLeftThenRight_ReturnsOriginal(Direction direction) {
        Direction result = direction.turnLeft().turnRight();
        assertEquals(direction, result);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTurnRightThenLeft_ReturnsOriginal(Direction direction) {
        Direction result = direction.turnRight().turnLeft();
        assertEquals(direction, result);
    }

    @Test
    void testFourLeftTurns_ReturnsOriginal() {
        Direction start = Direction.NORTH;
        Direction result = start.turnLeft().turnLeft().turnLeft().turnLeft();
        assertEquals(start, result);
    }

    @Test
    void testFourRightTurns_ReturnsOriginal() {
        Direction start = Direction.NORTH;
        Direction result = start.turnRight().turnRight().turnRight().turnRight();
        assertEquals(start, result);
    }
}