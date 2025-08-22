package com.caterpillar.truckrobot.controller;

import com.caterpillar.truckrobot.dto.PlaceDto;
import com.caterpillar.truckrobot.model.Turn;
import com.caterpillar.truckrobot.component.NavigatorComponent;
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

@WebMvcTest(NavigatorController.class)
class NavigatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NavigatorComponent navigatorComponent;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(navigatorComponent);
    }

    @Test
    void testPlace_ValidPosition_Success() throws Exception {
        PlaceDto request = new PlaceDto(0, 0, Turn.NORTH);
        when(navigatorComponent.doPlacing(anyInt(), anyInt(), any(Turn.class))).thenReturn(true);

        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
             .andExpect(jsonPath("$.status").value("SUCCESS"));
        verify(navigatorComponent).doPlacing(0, 0, Turn.NORTH);
    }

    @Test
    void testPlace_InvalidPosition_BadRequest() throws Exception {
        PlaceDto request = new PlaceDto(5, 5, Turn.NORTH);
        when(navigatorComponent.doPlacing(anyInt(), anyInt(), any(Turn.class))).thenReturn(false);

        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }


    @Test
    void testMove_RobotPlaced_Success() throws Exception {
        when(navigatorComponent.isRobotPlaced()).thenReturn(true);

        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isOk());
        verify(navigatorComponent).move();
    }

    @Test
    void testMove_RobotNotPlaced_BadRequest() throws Exception {
        when(navigatorComponent.isRobotPlaced()).thenReturn(false);

        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isBadRequest());

    verify(navigatorComponent, never()).move();
    }

    @Test
    void testTurnLeft_RobotPlaced_Success() throws Exception {
        when(navigatorComponent.isRobotPlaced()).thenReturn(true);

        mockMvc.perform(post("/api/v1/nav/left"))
            .andExpect(status().isOk());

        verify(navigatorComponent).turnLeft();
    }

    @Test
    void testTurnLeft_RobotNotPlaced_BadRequest() throws Exception {
        when(navigatorComponent.isRobotPlaced()).thenReturn(false);

        mockMvc.perform(post("/api/v1/nav/left"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testTurnRight_RobotPlaced_Success() throws Exception {
        when(navigatorComponent.isRobotPlaced()).thenReturn(true);

        mockMvc.perform(post("/api/v1/nav/right"))
            .andExpect(status().isOk());

        verify(navigatorComponent).turnRight();
    }

    @Test
    void testTurnRight_RobotNotPlaced_BadRequest() throws Exception {
        when(navigatorComponent.isRobotPlaced()).thenReturn(false);

        mockMvc.perform(post("/api/v1/nav/right"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testReport_Success() throws Exception {
        when(navigatorComponent.report()).thenReturn("2,3,NORTH");

        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk());

        verify(navigatorComponent).report();
    }

    @Test
    void testReport_RobotMissing() throws Exception {
        when(navigatorComponent.report()).thenReturn("ROBOT MISSING");

        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk());
    }

    @Test
    void testReset_Success() throws Exception {
        mockMvc.perform(post("/api/v1/nav/reset"))
            .andExpect(status().isOk());

        verify(navigatorComponent).reset();
    }
}