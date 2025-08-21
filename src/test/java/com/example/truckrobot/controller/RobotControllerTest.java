package com.example.truckrobot.controller;

import com.example.truckrobot.dto.PlaceRequest;
import com.example.truckrobot.model.Direction;
import com.example.truckrobot.service.RobotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RobotController.class)
class RobotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RobotService robotService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(robotService);
    }

    @Test
    void testPlace_ValidPosition_Success() throws Exception {
        PlaceRequest request = new PlaceRequest(0, 0, Direction.NORTH);
        when(robotService.place(anyInt(), anyInt(), any(Direction.class))).thenReturn(true);

        mockMvc.perform(post("/api/v1/robot/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Robot placed at 0,0 facing NORTH"))
            .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(robotService).place(0, 0, Direction.NORTH);
    }

    @Test
    void testPlace_InvalidPosition_BadRequest() throws Exception {
        PlaceRequest request = new PlaceRequest(5, 5, Direction.NORTH);
        when(robotService.place(anyInt(), anyInt(), any(Direction.class))).thenReturn(false);

        mockMvc.perform(post("/api/v1/robot/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Invalid position. Robot cannot be placed outside the 5x5 table."))
            .andExpect(jsonPath("$.status").value("ERROR"));

        verify(robotService).place(5, 5, Direction.NORTH);
    }

    @Test
    void testPlace_InvalidJson_BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/robot/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"invalid\": \"json\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("ERROR"));
    }

    @Test
    void testMove_RobotPlaced_Success() throws Exception {
        when(robotService.isRobotPlaced()).thenReturn(true);

        mockMvc.perform(post("/api/v1/robot/move"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Robot moved"))
            .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(robotService).move();
    }

    @Test
    void testMove_RobotNotPlaced_BadRequest() throws Exception {
        when(robotService.isRobotPlaced()).thenReturn(false);

        mockMvc.perform(post("/api/v1/robot/move"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Robot is not placed on the table. Use PLACE command first."))
            .andExpect(jsonPath("$.status").value("ERROR"));

        verify(robotService, never()).move();
    }

    @Test
    void testTurnLeft_RobotPlaced_Success() throws Exception {
        when(robotService.isRobotPlaced()).thenReturn(true);

        mockMvc.perform(post("/api/v1/robot/left"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Robot turned left"))
            .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(robotService).turnLeft();
    }

    @Test
    void testTurnLeft_RobotNotPlaced_BadRequest() throws Exception {
        when(robotService.isRobotPlaced()).thenReturn(false);

        mockMvc.perform(post("/api/v1/robot/left"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("ERROR"));
    }

    @Test
    void testTurnRight_RobotPlaced_Success() throws Exception {
        when(robotService.isRobotPlaced()).thenReturn(true);

        mockMvc.perform(post("/api/v1/robot/right"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Robot turned right"))
            .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(robotService).turnRight();
    }

    @Test
    void testTurnRight_RobotNotPlaced_BadRequest() throws Exception {
        when(robotService.isRobotPlaced()).thenReturn(false);

        mockMvc.perform(post("/api/v1/robot/right"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("ERROR"));
    }

    @Test
    void testReport_Success() throws Exception {
        when(robotService.report()).thenReturn("2,3,NORTH");

        mockMvc.perform(get("/api/v1/robot/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("2,3,NORTH"))
            .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(robotService).report();
    }

    @Test
    void testReport_RobotMissing() throws Exception {
        when(robotService.report()).thenReturn("ROBOT MISSING");

        mockMvc.perform(get("/api/v1/robot/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("ROBOT MISSING"))
            .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void testReset_Success() throws Exception {
        mockMvc.perform(post("/api/v1/robot/reset"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Robot reset"))
            .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(robotService).reset();
    }
}