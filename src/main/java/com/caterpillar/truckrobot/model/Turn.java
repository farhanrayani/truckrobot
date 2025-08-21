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
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }

    public Turn turnRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }
}