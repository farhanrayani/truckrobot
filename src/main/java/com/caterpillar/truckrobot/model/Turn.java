package com.caterpillar.truckrobot.model;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Turn {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public Turn turnLeft() {
        switch (this) {
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            default:
                throw new IllegalStateException("Invalid turn direction: " + this);
        }
    }

    public Turn turnRight() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalStateException("Invalid turn direction: " + this);
        }
    }
}