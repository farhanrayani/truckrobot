package com.example.truckrobot.controller;

import com.example.truckrobot.dto.PlaceRequest;
import com.example.truckrobot.dto.RobotResponse;
import com.example.truckrobot.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/robot")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @PostMapping("/place")
    public ResponseEntity<RobotResponse> place(@RequestBody PlaceRequest request) {
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

    @GetMapping("/report")
    public ResponseEntity<RobotResponse> report() {
        String report = robotService.report();
        return ResponseEntity.ok(new RobotResponse(report, "SUCCESS"));
    }

    @PostMapping("/reset")
    public ResponseEntity<RobotResponse> reset() {
        robotService.reset();
        return ResponseEntity.ok(new RobotResponse("Robot reset", "SUCCESS"));
    }
}