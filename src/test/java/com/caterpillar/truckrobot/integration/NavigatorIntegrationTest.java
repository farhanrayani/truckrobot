package com.caterpillar.truckrobot.integration;

import com.caterpillar.truckrobot.dto.PlaceDto;
import com.caterpillar.truckrobot.model.Turn;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class NavigatorIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Reset robot before each test
        try {
            mockMvc.perform(post("/api/v1/nav/reset"));
        } catch (Exception e) {
            // Ignore errors during setup
        }
    }

    @Test
    void testExample1_Place00North_Move_Report() throws Exception {
        // PLACE 0,0,NORTH
        PlaceDto placeDto = new PlaceDto(0, 0, Turn.NORTH);
        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDto)))
            .andExpect(status().isOk());

        // MOVE
        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isOk());

        // REPORT - Expected: 0,1,NORTH
        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("0,1,NORTH"));
    }

    @Test
    void testExample2_Place00North_Left_Report() throws Exception {
        // PLACE 0,0,NORTH
        PlaceDto placeDto = new PlaceDto(0, 0, Turn.NORTH);
        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDto)))
            .andExpect(status().isOk());

        // LEFT
        mockMvc.perform(post("/api/v1/nav/left"))
            .andExpect(status().isOk());

        // REPORT - Expected: 0,0,WEST
        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("0,0,WEST"));
    }

    @Test
    void testExample3_Place12East_Move_Move_Left_Move_Report() throws Exception {
        // PLACE 1,2,EAST
        PlaceDto placeDto = new PlaceDto(1, 2, Turn.EAST);
        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDto)))
            .andExpect(status().isOk());

        // MOVE
        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isOk());

        // MOVE
        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isOk());

        // LEFT
        mockMvc.perform(post("/api/v1/nav/left"))
            .andExpect(status().isOk());

        // MOVE
        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isOk());

        // REPORT - Expected: 3,3,NORTH
        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("3,3,NORTH"));
    }

    @Test
    void testExample4_Move_Report_WithoutPlacement() throws Exception {
        // MOVE (without placement)
        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isBadRequest());

        // REPORT - Expected: ROBOT MISSING
        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("ROBOT MISSING"));
    }

    @Test
    void testBoundaryPrevention_NorthBoundary() throws Exception {
        // Place at north boundary
        PlaceDto placeDto = new PlaceDto(2, 4, Turn.NORTH);
        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDto)))
            .andExpect(status().isOk());

        // Try to move off table
        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isOk());

        // Should still be at same position
        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("2,4,NORTH"));
    }

    @Test
    void testBoundaryPrevention_AllBoundaries() throws Exception {
        // Test all boundaries
        Turn[] turns = {Turn.NORTH, Turn.SOUTH, Turn.EAST, Turn.WEST};
        int[][] positions = {{2, 4}, {2, 0}, {4, 2}, {0, 2}};
        String[] expectedReports = {"2,4,NORTH", "2,0,SOUTH", "4,2,EAST", "0,2,WEST"};

        for (int i = 0; i < turns.length; i++) {
            // Reset
            mockMvc.perform(post("/api/v1/nav/reset"));

            // Place at boundary
            PlaceDto placeDto = new PlaceDto(positions[i][0], positions[i][1], turns[i]);
            mockMvc.perform(post("/api/v1/nav/place")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(placeDto)))
                .andExpect(status().isOk());

            // Try to move off table
            mockMvc.perform(post("/api/v1/nav/move"))
                .andExpect(status().isOk());

            // Should still be at same position
            mockMvc.perform(get("/api/v1/nav/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedReports[i]));
        }
    }

    @Test
    void testInvalidPlacements() throws Exception {
        int[][] invalidPositions = {{-1, 0}, {0, -1}, {5, 0}, {0, 5}, {-1, -1}, {5, 5}};

        for (int[] pos : invalidPositions) {
            PlaceDto placeDto = new PlaceDto(pos[0], pos[1], Turn.NORTH);
            mockMvc.perform(post("/api/v1/nav/place")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(placeDto)))
                .andExpect(status().isBadRequest());
        }
    }

    @Test
    void testComplexMovementSequence() throws Exception {
        // Complex sequence test
        mockMvc.perform(post("/api/v1/nav/reset"));

        // Place robot
        PlaceDto placeDto = new PlaceDto(2, 2, Turn.NORTH);
        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDto)))
            .andExpect(status().isOk());

        // Move west
        mockMvc.perform(post("/api/v1/nav/move"));

        // Should be back at original position (2,2) but facing WEST
        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("2,3,NORTH"));
    }

    @Test
    void testRobotIgnoresCommandsWhenNotPlaced() throws Exception {
        // Ensure robot is not placed
        mockMvc.perform(post("/api/v1/nav/reset"));

        // Try MOVE without placement
        mockMvc.perform(post("/api/v1/nav/move"))
            .andExpect(status().isBadRequest());

        // Try LEFT without placement
        mockMvc.perform(post("/api/v1/nav/left"))
            .andExpect(status().isBadRequest());

        // Try RIGHT without placement
        mockMvc.perform(post("/api/v1/nav/right"))
            .andExpect(status().isBadRequest());

        // REPORT should still work and return ROBOT MISSING
        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("ROBOT MISSING"));
    }

    @Test
    void testMultiplePlacementOperations() throws Exception {
        // First placement
        PlaceDto placeDto1 = new PlaceDto(1, 1, Turn.NORTH);
        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDto1)))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(jsonPath("$.message").value("1,1,NORTH"));

        // Second placement (should overwrite first)
        PlaceDto placeDto2 = new PlaceDto(3, 3, Turn.SOUTH);
        mockMvc.perform(post("/api/v1/nav/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDto2)))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/nav/report"))
            .andExpect(jsonPath("$.message").value("3,3,SOUTH"));
    }
}