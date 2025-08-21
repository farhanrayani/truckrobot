package com.caterpillar.truckrobot.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Cardinal directions the robot can face")
public enum Turn {
    @Schema(description = "Facing towards positive Y axis")
    NORTH,

    @Schema(description = "Facing towards negative Y axis")
    SOUTH,

    @Schema(description = "Facing towards positive X axis")
    EAST,

    @Schema(description = "Facing towards negative X axis")
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