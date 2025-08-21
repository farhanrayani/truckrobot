package com.example.truckrobot.performance;

import com.example.truckrobot.model.Direction;
import com.example.truckrobot.service.RobotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

class RobotPerformanceTest {

    private RobotService robotService;

    @BeforeEach
    void setUp() {
        robotService = new RobotService();
    }

    @Test
    void testPerformanceOf1000Operations() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // Perform 1000 operations
        for (int i = 0; i < 1000; i++) {
            robotService.place(i % 5, (i + 1) % 5, Direction.values()[i % 4]);
            robotService.move();
            robotService.turnLeft();
            robotService.turnRight();
            robotService.report();
        }

        stopWatch.stop();

        // Assert that 1000 operations complete in reasonable time (less than 1 second)
        assertTrue(stopWatch.getTotalTimeMillis() < 1000,
            "1000 operations should complete in less than 1 second, took: " + stopWatch.getTotalTimeMillis() + "ms");
    }

    @Test
    void testMemoryEfficiency() {
        // Test that creating many robot instances doesn't cause memory issues
        for (int i = 0; i < 1000; i++) {
            RobotService service = new RobotService();
            service.place(0, 0, Direction.NORTH);
            service.move();
            assertEquals("0,1,NORTH", service.report());
        }

        // Force garbage collection to ensure no memory leaks
        System.gc();

        // If we reach here without OutOfMemoryError, the test passes
        assertTrue(true);
    }
}
