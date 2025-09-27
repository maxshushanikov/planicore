# chronyqor — REST API for Task Management (To-Do)

This project is a modern Spring Boot application that implements a RESTful API for managing tasks (To-Do). It is built using best practices and includes automated CI/CD pipelines, containerization, and comprehensive testing tools.

---

## 📋 Overview

**`chronyqor`** is the backend component of a task management application. It provides full CRUD operations (Create, Read, Update, Delete) for the `Task` entity, along with standard monitoring endpoints via Spring Boot Actuator.

### 🔧 Features
- Create a new task (`POST /api/v1/tasks`)
- Retrieve all tasks (`GET /api/v1/tasks`)
- Get a task by ID (`GET /api/v1/tasks/{id}`)
- Update a task (`PUT /api/v1/tasks/{id}`)
- Delete a task (`DELETE /api/v1/tasks/{id}`)
- Health and readiness checks (`GET /actuator/health`, `GET /ready`)

Each task includes:
- Title (`title`)
- Description (`description`)
- Status: `TODO`, `IN_PROGRESS`, `DONE`
- Due date (`dueDate`)
- Creation and update timestamps

---

## 🛠️ Technologies

| Category | Technologies Used |
|--------|-------------------|
| **Language & Platform** | Java 21, Spring Boot 3.5.5 |
| **Build Tool** | Gradle 8.14.3 |
| **ORM & Database** | Spring Data JPA, Hibernate, PostgreSQL (prod), H2 (dev/test) |
| **Database Migrations** | Flyway |
| **DTO Mapping** | MapStruct 1.5.5 |
| **Testing** | JUnit 5, Testcontainers, Mockito |
| **Containerization** | Docker, Docker Compose |
| **CI/CD** | GitHub Actions |
| **API Documentation** | Postman Collection |

---

## 🚀 Quick Start

### Prerequisites
- JDK 21
- Docker and Docker Compose
- Gradle (optional, wrapper included)

### Running with Docker Compose
```bash
# Build and run the full stack (backend + database)
docker-compose up --build

# The application will be available at:
# - API: http://localhost:8080
# - PostgreSQL: port 5432
```

### Local Build and Run
```bash
# Build the project
./gradlew build

# Run the application (dev profile)
./gradlew bootRun

# Or run the JAR directly
java -jar build/libs/planicore-1.0.0.jar
```

The app will start on `http://localhost:8080`.

---

## 🌐 Configuration Profiles

The project supports multiple profiles:

| Profile | Database | Purpose |
|--------|---------|--------|
| `default` | — | General settings |
| `dev` | H2 in-memory | Development, H2 console available at `/h2-console` |
| `test` | Testcontainers + PostgreSQL | Integration tests |
| `prod` | PostgreSQL | Production environment with enhanced logging and security |

Activate a profile:
```bash
# Using environment variable
SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun

# Or set in docker-compose.yml
environment:
  SPRING_PROFILES_ACTIVE: prod
```

---

## 🧪 Testing

Run tests:
```bash
./gradlew test
```

- **Unit Tests**: Verify service logic.
- **Integration Tests**: Use `Testcontainers` to spin up a real PostgreSQL instance during testing.

Test results are available at:
```
build/reports/tests/test/index.html
```

All tests passed successfully.

---

## 🤖 CI/CD Pipeline

An automated pipeline is configured using GitHub Actions:

1. **Build & Test** — Runs on push or PR to `master`.
2. **Build Docker Image** — Builds and pushes image to Docker Hub.
3. **Deploy to Production** — Deploys to production server on push to `main`.

> ⚠️ For CI/CD to work, configure the following secrets in GitHub:
> - `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`
> - `PROD_SERVER_HOST`, `PROD_SERVER_USER`, `PROD_SSH_KEY`

---

## 📦 Project Structure

```
src/main/java/org/planicore/
├── server/
│   ├── controller/     # REST controllers
│   ├── domain/         # JPA entities
│   ├── dto/            # Data Transfer Objects
│   ├── mapper/         # MapStruct mappers
│   ├── repository/     # Spring Data JPA repositories
│   ├── service/        # Business logic
│   └── HealthController.java
└── PlanicoreApplication.java
```

---

## 🔄 API Endpoints (Postman)

Ready-to-use Postman collections are located in the `postmann/` folder:
- `todo-api.postman_collection.json` — main API requests
- `todo-api-dev.postman_environment.json` — dev environment variables
- `todo-api-prod.postman_environment.json` — prod environment variables

Example request to create a task:
```json
POST {{baseUrl}}/api/v1/tasks
Content-Type: application/json

{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs",
  "status": "TODO",
  "dueDate": "2025-09-20T18:00:00"
}
```

---

## 📂 Configuration Files

Key configuration files:
- `src/main/resources/application.yml` — main config with profiles
- `src/main/resources/application.properties` — general properties
- `src/main/resources/db/migration/V1__create_tasks_table.sql` — SQL migration script

---

## 🐳 Docker

- **Backend**: Multi-stage `Dockerfile` in the root directory.
- **Database**: PostgreSQL 16 defined in `docker-compose.yml`.
- **Health Check**: Implemented via `/actuator/health`.

---

## 📊 Monitoring

Available endpoints:
- `GET /actuator/health` — application status
- `GET /actuator/info` — version info
- `GET /actuator/metrics` — Prometheus-compatible metrics
- `GET /ready` — simple readiness check

---

## 📄 License

No license specified (all rights reserved by default).

---

## 🙋 Support

For questions or suggestions, please use GitHub Issues.

---

> 💡 **Tip**: Before first launch, ensure all dependencies are installed and environment variables are properly configured.