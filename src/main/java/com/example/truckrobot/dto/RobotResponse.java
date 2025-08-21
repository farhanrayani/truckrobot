package com.example.truckrobot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard response from robot operations")
public class RobotResponse {

    @Schema(
        description = "Response message describing the result of the operation",
        example = "Robot placed at 0,0 facing NORTH"
    )
    private String message;

    @Schema(
        description = "Status of the operation",
        example = "SUCCESS",
        allowableValues = {"SUCCESS", "ERROR"}
    )
    private String status;

    public RobotResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}