package com.caterpillar.truckrobot.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void testTurnLeft_AllDirections() {
        assertEquals(Turn.WEST, Turn.NORTH.turnLeft());
        assertEquals(Turn.SOUTH, Turn.WEST.turnLeft());
        assertEquals(Turn.EAST, Turn.SOUTH.turnLeft());
        assertEquals(Turn.NORTH, Turn.EAST.turnLeft());
    }

    @Test
    void testTurnRight_AllDirections() {
        assertEquals(Turn.EAST, Turn.NORTH.turnRight());
        assertEquals(Turn.SOUTH, Turn.EAST.turnRight());
        assertEquals(Turn.WEST, Turn.SOUTH.turnRight());
        assertEquals(Turn.NORTH, Turn.WEST.turnRight());
    }

    @ParameterizedTest
    @EnumSource(Turn.class)
    void testTurnLeftThenRight_ReturnsOriginal(Turn turn) {
        Turn result = turn.turnLeft().turnRight();
        assertEquals(turn, result);
    }

    @ParameterizedTest
    @EnumSource(Turn.class)
    void testTurnRightThenLeft_ReturnsOriginal(Turn turn) {
        Turn result = turn.turnRight().turnLeft();
        assertEquals(turn, result);
    }

    @Test
    void testFourLeftTurns_ReturnsOriginal() {
        Turn start = Turn.NORTH;
        Turn result = start.turnLeft().turnLeft().turnLeft().turnLeft();
        assertEquals(start, result);
    }

    @Test
    void testFourRightTurns_ReturnsOriginal() {
        Turn start = Turn.NORTH;
        Turn result = start.turnRight().turnRight().turnRight().turnRight();
        assertEquals(start, result);
    }
}