# Truck Robot Simulation API 🤖

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Coverage](https://img.shields.io/badge/Coverage-95%25+-success.svg)](#test-coverage)

A REST API simulation of a truck robot moving on a 5×5 table surface. The robot can be placed, moved, and rotated while being prevented from falling off the table.

## 📋 Table of Contents
- [Features](#-features)
- [Quick Start](#-quick-start)
- [API Documentation](#-api-documentation)
- [Testing](#-testing)
- [Code Quality](#-code-quality)
- [Project Structure](#-project-structure)
- [API Reference](#-api-reference)
- [Development](#-development)
- [Contributing](#-contributing)

## ✨ Features

### Core Functionality
- **Robot Placement**: Place robot at any valid position (0-4, 0-4) facing any cardinal direction
- **Movement Control**: Move robot forward one unit in current facing direction
- **Rotation Control**: Turn robot left or right (90-degree increments)
- **Position Reporting**: Get current robot position and facing direction
- **Boundary Protection**: Prevent robot from falling off the 5×5 table
- **State Management**: Track robot placement and ignore invalid commands

### Technical Features
- **RESTful API**: Clean, intuitive endpoint design
- **Input Validation**: Comprehensive request validation with detailed error messages
- **Interactive Documentation**: Swagger UI for API exploration and testing
- **High Test Coverage**: 95%+ line coverage, 90%+ branch coverage
- **Code Quality**: Checkstyle enforcement with industry standards
- **Spring Boot**: Production-ready framework with actuator endpoints

## 🚀 Quick Start

### Prerequisites
- **Java 21** or higher
- **Maven 3.8+**
- **Git**

### Installation & Setup
```bash
# Clone the repository
git clone https://github.com/your-username/truck-robot-api.git
cd truck-robot-api

# Build the project
mvn clean compile

# Run tests
mvn test

# Start the application
mvn spring-boot:run
```

### Verify Installation
```bash
# Check application health
curl http://localhost:8080/truckrobot/actuator/health

# Expected response: {"status":"UP"}
```

## 📚 API Documentation

### Swagger UI (Interactive Documentation)
Access the interactive API documentation at:
```
http://localhost:8080/truckrobot/swagger-ui.html
```

### OpenAPI Specification
Get the machine-readable API specification:
```
http://localhost:8080/truckrobot/api-docs
```

### Key Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/robot/place` | Place robot on table |
| `POST` | `/api/v1/robot/move` | Move robot forward |
| `POST` | `/api/v1/robot/left` | Turn robot left |
| `POST` | `/api/v1/robot/right` | Turn robot right |
| `GET` | `/api/v1/robot/report` | Get robot status |
| `POST` | `/api/v1/robot/reset` | Reset robot |

## 🧪 Testing

### Running Tests

#### All Tests
```bash
mvn test
```

#### Specific Test Categories
```bash
# Unit tests only
mvn test -Dtest="*Test"

# Integration tests only
mvn test -Dtest="*IntegrationTest"

# Controller tests only
mvn test -Dtest="*ControllerTest"
```

### API Testing Examples

#### Using cURL
```bash
# Place robot
curl -X POST http://localhost:8080/truckrobot/api/v1/robot/place \
  -H "Content-Type: application/json" \
  -d '{"x": 0, "y": 0, "facing": "NORTH"}'

# Move robot
curl -X POST http://localhost:8080/truckrobot/api/v1/robot/move

# Get position
curl -X GET http://localhost:8080/truckrobot/api/v1/robot/report

# Expected: {"message":"0,1,NORTH","status":"SUCCESS"}
```

#### Example Test Scenarios

##### Scenario 1: Basic Movement
```bash
# PLACE 0,0,NORTH → MOVE → REPORT
curl -X POST localhost:8080/truckrobot/api/v1/robot/place -H "Content-Type: application/json" -d '{"x":0,"y":0,"facing":"NORTH"}'
curl -X POST localhost:8080/truckrobot/api/v1/robot/move
curl -X GET localhost:8080/truckrobot/api/v1/robot/report
# Result: 0,1,NORTH
```

##### Scenario 2: Rotation
```bash
# PLACE 0,0,NORTH → LEFT → REPORT
curl -X POST localhost:8080/truckrobot/api/v1/robot/place -H "Content-Type: application/json" -d '{"x":0,"y":0,"facing":"NORTH"}'
curl -X POST localhost:8080/truckrobot/api/v1/robot/left
curl -X GET localhost:8080/truckrobot/api/v1/robot/report
# Result: 0,0,WEST
```

##### Scenario 3: Complex Movement
```bash
# PLACE 1,2,EAST → MOVE → MOVE → LEFT → MOVE → REPORT
curl -X POST localhost:8080/truckrobot/api/v1/robot/place -H "Content-Type: application/json" -d '{"x":1,"y":2,"facing":"EAST"}'
curl -X POST localhost:8080/truckrobot/api/v1/robot/move
curl -X POST localhost:8080/truckrobot/api/v1/robot/move
curl -X POST localhost:8080/truckrobot/api/v1/robot/left
curl -X POST localhost:8080/truckrobot/api/v1/robot/move
curl -X GET localhost:8080/truckrobot/api/v1/robot/report
# Result: 3,3,NORTH
```

### Test Coverage

#### Generate Coverage Report
```bash
# Run tests with coverage
mvn clean test jacoco:report

# Open coverage report
open target/site/jacoco/index.html
```

#### Coverage Goals & Results
- ✅ **Line Coverage**: >95% (Target: 95%)
- ✅ **Branch Coverage**: >90% (Target: 90%)
- ✅ **Method Coverage**: 100% (Target: 100%)

#### Coverage Report Structure
```
target/site/jacoco/
├── index.html              # Main coverage dashboard
├── com.example.truckrobot/
│   ├── controller/         # Controller coverage details
│   ├── service/           # Service layer coverage
│   ├── model/             # Domain model coverage
│   └── dto/               # Data transfer objects
└── jacoco.xml             # XML report for CI/CD
```

## 🔍 Code Quality

### Checkstyle

#### Run Checkstyle Check
```bash
# Check code style compliance
mvn checkstyle:check

# Generate checkstyle report
mvn checkstyle:checkstyle

# Open checkstyle report
open target/site/checkstyle.html
```

#### Code Style Standards
- **Line Length**: Maximum 120 characters
- **Indentation**: 4 spaces (no tabs)
- **Naming Conventions**:
    - Classes: PascalCase (e.g., `RobotController`)
    - Methods/Variables: camelCase (e.g., `moveRobot`)
    - Constants: UPPER_SNAKE_CASE (e.g., `TABLE_SIZE`)
- **Import Organization**: No star imports, grouped by package
- **Braces**: Required for all control statements
- **Magic Numbers**: Prohibited (except common values: -1, 0, 1, 2, 3, 4, 5)

#### Skip Checkstyle (Development Only)
```bash
mvn compile -Dcheckstyle.skip=true
```

### Full Quality Check
```bash
# Run all quality checks together
mvn clean test jacoco:report checkstyle:checkstyle

# This will:
# 1. Clean previous builds
# 2. Run all tests
# 3. Generate coverage report
# 4. Run checkstyle analysis
# 5. Generate code quality reports
```

## 📁 Project Structure

```
truck-robot-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/truckrobot/
│   │   │   ├── TruckRobotApplication.java     # Main Spring Boot application
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java         # Swagger/OpenAPI configuration
│   │   │   ├── controller/
│   │   │   │   └── RobotController.java       # REST API endpoints
│   │   │   ├── service/
│   │   │   │   └── RobotService.java          # Business logic
│   │   │   ├── model/
│   │   │   │   ├── Direction.java             # Direction enum
│   │   │   │   ├── Position.java              # Position value object
│   │   │   │   └── Robot.java                 # Robot domain model
│   │   │   └── dto/
│   │   │       ├── PlaceRequest.java          # Place robot request
│   │   │       └── RobotResponse.java         # Standard API response
│   │   └── resources/
│   │       ├── application.yml                # Main configuration
│   │       └── application-test.yml           # Test configuration
│   └── test/
│       ├── java/com/example/truckrobot/
│       │   ├── controller/                    # Controller tests
│       │   ├── service/                       # Service tests
│       │   ├── model/                         # Model tests
│       │   ├── integration/                   # Integration tests
│       │   └── dto/                           # DTO tests
│       └── resources/
│           └── application-test.yml           # Test environment config
├── checkstyle.xml                             # Checkstyle configuration
├── checkstyle-suppressions.xml                # Checkstyle suppressions
├── .editorconfig                              # IDE formatting rules
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

## 🔌 API Reference

### Robot Placement
```http
POST /api/v1/robot/place
Content-Type: application/json

{
  "x": 0,          // X coordinate (0-4)
  "y": 0,          // Y coordinate (0-4)  
  "facing": "NORTH" // Direction: NORTH, SOUTH, EAST, WEST
}
```

**Response:**
```json
{
  "message": "Robot placed at 0,0 facing NORTH",
  "status": "SUCCESS"
}
```

### Robot Movement
```http
POST /api/v1/robot/move
```

### Robot Rotation
```http
POST /api/v1/robot/left   # Turn 90° counter-clockwise
POST /api/v1/robot/right  # Turn 90° clockwise
```

### Robot Status
```http
GET /api/v1/robot/report
```

**Response:**
```json
{
  "message": "2,3,NORTH",  // Format: "X,Y,DIRECTION"
  "status": "SUCCESS"
}
```

### Error Responses
```json
{
  "message": "Invalid position. Robot cannot be placed outside the 5x5 table.",
  "status": "ERROR"
}
```

## 🛠 Development

### IDE Setup

#### IntelliJ IDEA
1. Import as Maven project
2. Install plugins:
    - Checkstyle-IDEA
    - SonarLint (optional)
3. Configure code style:
    - Import `.idea/codeStyles/Project.xml`
    - Enable Checkstyle real-time checking

#### VS Code
1. Install extensions:
    - Extension Pack for Java
    - Checkstyle for Java
    - Spring Boot Extension Pack
2. Configure settings:
   ```json
   {
     "java.checkstyle.configuration": "checkstyle.xml",
     "java.format.settings.url": ".editorconfig"
   }
   ```

### Building for Production

#### Create JAR
```bash
mvn clean package

# JAR location: target/truck-robot-0.0.1-SNAPSHOT.jar
```

#### Run JAR
```bash
java -jar target/truck-robot-0.0.1-SNAPSHOT.jar
```

#### Docker (Optional)
```dockerfile
FROM openjdk:21-jre-slim
COPY target/truck-robot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Environment Configuration

#### Development
```yaml
# application-dev.yml
logging:
  level:
    com.example.truckrobot: DEBUG
springdoc:
  swagger-ui:
    enabled: true
```

#### Production
```yaml
# application-prod.yml
logging:
  level:
    com.example.truckrobot: INFO
springdoc:
  swagger-ui:
    enabled: false
```

## 🤝 Contributing

### Development Workflow
1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Make changes with tests
4. Run quality checks: `mvn clean test jacoco:check checkstyle:check`
5. Commit changes: `git commit -m 'Add amazing feature'`
6. Push branch: `git push origin feature/amazing-feature`
7. Create Pull Request

### Quality Requirements
- ✅ All tests must pass
- ✅ Code coverage ≥95% line coverage
- ✅ No Checkstyle violations
- ✅ All public methods documented
- ✅ Integration tests for new endpoints

### Coding Standards
- Follow existing code style (enforced by Checkstyle)
- Write unit tests for all new functionality
- Add integration tests for API changes
- Update Swagger documentation for API changes
- Use meaningful commit messages

## 📊 Monitoring & Health Checks

### Health Endpoints
```bash
# Application health
curl http://localhost:8080/truckrobot/actuator/health

# Application info
curl http://localhost:8080/truckrobot/actuator/info

# Metrics
curl http://localhost:8080/truckrobot/actuator/metrics
```

## 🔧 Troubleshooting

### Common Issues

#### Port Already in Use
```bash
# Find process using port 8080
lsof -i :8080

# Kill process
kill -9 <PID>

# Or use different port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

#### Tests Failing
```bash
# Run tests with debug output
mvn test -X

# Run specific test
mvn test -Dtest=RobotControllerTest

# Skip tests temporarily
mvn spring-boot:run -DskipTests
```

#### Coverage Below Threshold
```bash
# Check uncovered lines
open target/site/jacoco/index.html

# Run coverage with debug
mvn clean test jacoco:report -X
```


## 📞 Support

- **Documentation**: [Swagger UI](http://localhost:8080/truckrobot/swagger-ui.html)
- **Issues**: [GitHub Issues](https://github.com/your-username/truck-robot-api/issues)
- **Wiki**: [Project Wiki](https://github.com/your-username/truck-robot-api/wiki)

## 🏆 Acknowledgments

- Spring Boot team for the excellent framework
- OpenAPI/Swagger community for documentation tools
- JaCoCo team for coverage reporting
- Checkstyle community for code quality tools

---
