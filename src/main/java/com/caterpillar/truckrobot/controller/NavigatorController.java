package com.caterpillar.truckrobot.controller;

import com.caterpillar.truckrobot.dto.PlaceDto;
import com.caterpillar.truckrobot.dto.ResponseDto;
import com.caterpillar.truckrobot.component.NavigatorComponent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nav")
public class NavigatorController {

    @Autowired
    private NavigatorComponent navigatorComponent;

    @Operation( summary = "Place robot on table",description = "Places on the table at the specified location.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Robot placed successfully"),
        @ApiResponse(responseCode = "400",description = "Invalid location")})
    @PostMapping("/place")
    public ResponseEntity<ResponseDto> doPlace(@RequestBody(required = true) PlaceDto placeDto) {
        try {
            boolean isValid = navigatorComponent.place(placeDto.getX(), placeDto.getY(), placeDto.getFacing());
            if (isValid) {
                ResponseDto responseDto =new ResponseDto(
                    "Coordinates" + placeDto.getX() + "," + placeDto.getY() + " facing " + placeDto.getFacing(),
                    "SUCCESS");
                return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.badRequest().body(new ResponseDto(
                    "Invalid location.",
                    "ERROR"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid placeDto: ","ERROR"));
        }
    }

    @Operation(summary = "Move robot forward",description = "Moves the robot one unit forward.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Robot moved successfully"),
        @ApiResponse(responseCode = "400",description = "Robot not placed on table")
    })
    @PostMapping("/move")
    public ResponseEntity<ResponseDto> move() {
        if (!navigatorComponent.isRobotPlaced()) {
            return ResponseEntity.badRequest().body(new ResponseDto(
                "Robot is not placed on the table.","ERROR" ));
        }
        navigatorComponent.move();
        return ResponseEntity.ok(new ResponseDto("Robot moved", "SUCCESS"));
    }

    @Operation(summary = "Turn robot left",description = "Rotates the robot 90 degrees to the left.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Robot turned left successfully"),
    @ApiResponse(responseCode = "400", description = "Robot not placed on table")})
    @PostMapping("/left")
    public ResponseEntity<ResponseDto> toLeft() {
        if (!navigatorComponent.isRobotPlaced()) {
            return ResponseEntity.badRequest().body(new ResponseDto("Robot is not placed.","ERROR"));
        }
        navigatorComponent.turnLeft();
        return ResponseEntity.ok(new ResponseDto("Robot turned left", "SUCCESS"));
    }

    @Operation(summary = "Turn robot right",description = "Rotates the robot 90 degrees to the right without changing its location.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Robot turned right successfully"),
        @ApiResponse(responseCode = "400", description = "Robot not placed on table")})
    @PostMapping("/right")
    public ResponseEntity<ResponseDto> toRight() {
        if (!navigatorComponent.isRobotPlaced()) {
            return ResponseEntity.badRequest().body(new ResponseDto("Robot is not placed on the table.","ERROR"));
        }
        navigatorComponent.turnRight();
        return ResponseEntity.ok(new ResponseDto("Robot turned right", "SUCCESS"));
    }

    @Operation(summary = "Get robot location and turn",
        description = "Returns the current X,Y location and facing turn of the robot.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Robot status retrieved successfully")})

    @GetMapping("/report")
    public ResponseEntity<ResponseDto> doReport() {
        String report = navigatorComponent.report();
        return ResponseEntity.ok(new ResponseDto(report, "SUCCESS"));
    }

    @Operation(summary = "Reset robot",description = "Removes the robot from the table and resets its state.")
    @ApiResponse(responseCode = "200", description = "Robot reset successfully")
    @PostMapping("/reset")
    public ResponseEntity<ResponseDto> performReset() {
        navigatorComponent.reset();
        return ResponseEntity.ok(new ResponseDto("Robot reset", "SUCCESS"));
    }
}