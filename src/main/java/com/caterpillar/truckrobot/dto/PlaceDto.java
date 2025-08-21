package com.caterpillar.truckrobot.dto;

import com.caterpillar.truckrobot.model.Turn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to place robot on the table")
public class PlaceDto {

    @Schema(
        description = "X coordinate on the table",
        example = "0",
        minimum = "0",
        maximum = "4"
    )
    @NotNull(message = "X coordinate is required")
    @Min(value = 0, message = "X coordinate must be between 0 and 4")
    @Max(value = 4, message = "X coordinate must be between 0 and 4")
    private int x;

    @Schema(
        description = "Y coordinate on the table",
        example = "0",
        minimum = "0",
        maximum = "4"
    )
    @NotNull(message = "Y coordinate is required")
    @Min(value = 0, message = "Y coordinate must be between 0 and 4")
    @Max(value = 4, message = "Y coordinate must be between 0 and 4")
    private int y;

    @Schema(
        description = "Direction the robot should face",
        example = "NORTH",
        allowableValues = {"NORTH", "SOUTH", "EAST", "WEST"}
    )
    @NotNull(message = "Facing direction is required")
    private Turn facing;

    public PlaceDto() {}

    public PlaceDto(int x, int y, Turn facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Turn getFacing() {
        return facing;
    }

    public void setFacing(Turn facing) {
        this.facing = facing;
    }
}