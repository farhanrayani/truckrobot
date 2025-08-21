package com.example.truckrobot.controller;

import com.example.truckrobot.dto.PlaceRequest;
import com.example.truckrobot.dto.RobotResponse;
import com.example.truckrobot.service.RobotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/robot")
@Tag(name = "Robot Control", description = "APIs for controlling the truck robot on a 5x5 table")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @Operation(
        summary = "Place robot on table",
        description = """
                Places the truck robot on the table at the specified position (x, y) facing the given direction.
                The robot can be placed anywhere on the 5x5 table (coordinates 0-4).
                If robot is already placed, this command will move it to the new position.
                """,
        tags = {"Robot Control"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Robot placed successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RobotResponse.class),
                examples = @ExampleObject(
                    name = "Successful placement",
                    value = """
                            {
                              "message": "Robot placed at 0,0 facing NORTH",
                              "status": "SUCCESS"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid position - outside table boundaries",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RobotResponse.class),
                examples = @ExampleObject(
                    name = "Invalid position",
                    value = """
                            {
                              "message": "Invalid position. Robot cannot be placed outside the 5x5 table.",
                              "status": "ERROR"
                            }
                            """
                )
            )
        )
    })
    @PostMapping("/place")
    public ResponseEntity<RobotResponse> place(
        @Parameter(
            description = "Robot placement request with x, y coordinates (0-4) and facing direction",
            required = true,
            schema = @Schema(implementation = PlaceRequest.class)
        )
        @Valid @RequestBody PlaceRequest request) {
        try {
            boolean success = robotService.place(request.getX(), request.getY(), request.getFacing());
            if (success) {
                return ResponseEntity.ok(new RobotResponse(
                    "Robot placed at " + request.getX() + "," + request.getY() + " facing " + request.getFacing(),
                    "SUCCESS"
                ));
            } else {
                return ResponseEntity.badRequest().body(new RobotResponse(
                    "Invalid position. Robot cannot be placed outside the 5x5 table.",
                    "ERROR"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RobotResponse(
                "Invalid request: " + e.getMessage(),
                "ERROR"
            ));
        }
    }

    @Operation(
        summary = "Move robot forward",
        description = """
                Moves the robot one unit forward in the direction it is currently facing.
                The robot will ignore this command if it would result in falling off the table.
                Robot must be placed on the table before this command can be executed.
                """,
        tags = {"Robot Control"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Robot moved successfully (or move was safely ignored)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                              "message": "Robot moved",
                              "status": "SUCCESS"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Robot not placed on table",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                              "message": "Robot is not placed on the table. Use PLACE command first.",
                              "status": "ERROR"
                            }
                            """
                )
            )
        )
    })
    @PostMapping("/move")
    public ResponseEntity<RobotResponse> move() {
        if (!robotService.isRobotPlaced()) {
            return ResponseEntity.badRequest().body(new RobotResponse(
                "Robot is not placed on the table. Use PLACE command first.",
                "ERROR"
            ));
        }

        robotService.move();
        return ResponseEntity.ok(new RobotResponse("Robot moved", "SUCCESS"));
    }

    @Operation(
        summary = "Turn robot left",
        description = """
                Rotates the robot 90 degrees to the left (counter-clockwise) without changing its position.
                Direction changes: NORTH → WEST → SOUTH → EAST → NORTH
                Robot must be placed on the table before this command can be executed.
                """,
        tags = {"Robot Control"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Robot turned left successfully"),
        @ApiResponse(responseCode = "400", description = "Robot not placed on table")
    })
    @PostMapping("/left")
    public ResponseEntity<RobotResponse> turnLeft() {
        if (!robotService.isRobotPlaced()) {
            return ResponseEntity.badRequest().body(new RobotResponse(
                "Robot is not placed on the table. Use PLACE command first.",
                "ERROR"
            ));
        }

        robotService.turnLeft();
        return ResponseEntity.ok(new RobotResponse("Robot turned left", "SUCCESS"));
    }

    @Operation(
        summary = "Turn robot right",
        description = """
                Rotates the robot 90 degrees to the right (clockwise) without changing its position.
                Direction changes: NORTH → EAST → SOUTH → WEST → NORTH
                Robot must be placed on the table before this command can be executed.
                """,
        tags = {"Robot Control"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Robot turned right successfully"),
        @ApiResponse(responseCode = "400", description = "Robot not placed on table")
    })
    @PostMapping("/right")
    public ResponseEntity<RobotResponse> turnRight() {
        if (!robotService.isRobotPlaced()) {
            return ResponseEntity.badRequest().body(new RobotResponse(
                "Robot is not placed on the table. Use PLACE command first.",
                "ERROR"
            ));
        }

        robotService.turnRight();
        return ResponseEntity.ok(new RobotResponse("Robot turned right", "SUCCESS"));
    }

    @Operation(
        summary = "Get robot position and direction",
        description = """
                Returns the current X, Y position and facing direction of the robot.
                Format: "X,Y,DIRECTION" (e.g., "0,1,NORTH")
                Returns "ROBOT MISSING" if robot has not been placed on the table.
                """,
        tags = {"Robot Status"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Robot status retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @ExampleObject(
                        name = "Robot positioned",
                        value = """
                                {
                                  "message": "2,3,NORTH",
                                  "status": "SUCCESS"
                                }
                                """
                    ),
                    @ExampleObject(
                        name = "Robot not placed",
                        value = """
                                {
                                  "message": "ROBOT MISSING",
                                  "status": "SUCCESS"
                                }
                                """
                    )
                }
            )
        )
    })
    @GetMapping("/report")
    public ResponseEntity<RobotResponse> report() {
        String report = robotService.report();
        return ResponseEntity.ok(new RobotResponse(report, "SUCCESS"));
    }

    @Operation(
        summary = "Reset robot",
        description = """
                Removes the robot from the table and resets its state.
                After reset, robot must be placed again before other commands can be used.
                This is useful for starting a new simulation sequence.
                """,
        tags = {"Robot Control"}
    )
    @ApiResponse(responseCode = "200", description = "Robot reset successfully")
    @PostMapping("/reset")
    public ResponseEntity<RobotResponse> reset() {
        robotService.reset();
        return ResponseEntity.ok(new RobotResponse("Robot reset", "SUCCESS"));
    }
}