package com.caterpillar.truckrobot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Truck Robot Simulation API")
                .description("""
                                A REST API for simulating a truck robot moving on a 5x5 table.
                                
                                ## Features
                                - Place robot on table with location and turn
                                - Move robot one unit forward
                                - Rotate robot left or right (90 degrees)
                                - Get current robot location and turn
                                - Prevent robot from falling off the table
                                
                                ## Table Specification
                                - Size: 5x5 units
                                - Origin (0,0): South-West corner
                                - Valid positions: (0,0) to (4,4)
                                - Valid turns: NORTH, SOUTH, EAST, WEST
                                """)
                .version("v1.0.0")
                .contact(new Contact()
                    .name("Truck Robot API Team")
                    .email("support@truckrobot.com")
                    .url("https://github.com/example/truck-robot"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080" + contextPath)
                    .description("Local development server"),
                new Server()
                    .url("https://api.truckrobot.com" + contextPath)
                    .description("Production server")))
            .tags(List.of(
                new Tag()
                    .name("Robot Control")
                    .description("Operations for controlling the truck robot"),
                new Tag()
                    .name("Robot Status")
                    .description("Operations for checking robot status")));
    }
}