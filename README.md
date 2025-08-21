# Truck Robot Simulation API

A REST API for simulating a truck robot moving on a 5×5 table surface with boundary protection.

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.8+

### Setup
```bash
git clone https://github.com/your-username/truck-robot-api.git
cd truck-robot-api
mvn spring-boot:run
```

**Verify**: `curl http://localhost:8080/truckrobot/actuator/health`

## API Endpoints

**Robot Control:**
- `POST /api/v1/robot/place` - Place robot on table
- `POST /api/v1/robot/move` - Move robot forward
- `POST /api/v1/robot/left` - Turn robot left
- `POST /api/v1/robot/right` - Turn robot right
- `GET /api/v1/robot/report` - Get robot status
- `POST /api/v1/robot/reset` - Reset robot

**Interactive Docs**: `http://localhost:8080/truckrobot/swagger-ui.html`

## Usage Examples

```bash
# Place robot
curl -X POST http://localhost:8080/truckrobot/api/v1/robot/place \
  -H "Content-Type: application/json" \
  -d '{"x": 0, "y": 0, "facing": "NORTH"}'

# Move and check position
curl -X POST http://localhost:8080/truckrobot/api/v1/robot/move
curl -X GET http://localhost:8080/truckrobot/api/v1/robot/report
# Response: {"message":"0,1,NORTH","status":"SUCCESS"}
```

## Testing & Quality

```bash
# Run tests
mvn test

# Check coverage (target: >95%)
mvn test jacoco:report

# Check code style
mvn checkstyle:check

# Build production JAR
mvn clean package
```

## Development

**Key Features:**
- 5×5 table boundary protection
- Cardinal direction movement (NORTH, SOUTH, EAST, WEST)
- Input validation with detailed error messages
- High test coverage (>95%)
- Checkstyle code quality enforcement
