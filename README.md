# TaskFlow – Task Management System

A simple, production-ready Spring Boot REST API designed as the application artifact for a multi-environment CI/CD pipeline using Docker, Amazon ECR, Amazon ECS Fargate, and GitHub Actions.

---

## Project Overview

TaskFlow is an in-memory task management REST API. Its primary purpose is to serve as a reliable, environment-agnostic application artifact that can be built once and deployed across Dev, Staging, and Production environments by changing only environment variables — not the image.

---

## Technology Stack

| Technology | Version |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Maven | 3.x |
| Spring Web | Included in Boot |
| Spring Boot Actuator | Included in Boot |
| Jakarta Bean Validation | Included in Boot |
| Storage | In-memory (ConcurrentHashMap) |

---

## Application Architecture

```
src/main/java/com/example/taskflow/
├── TaskFlowApplication.java        # Entry point
├── controller/
│   ├── ApplicationController.java  # GET /, /version, /health
│   └── TaskController.java         # CRUD + status endpoints
├── model/
│   ├── Task.java                   # Task entity
│   ├── TaskStatus.java             # TODO | IN_PROGRESS | DONE
│   └── TaskPriority.java           # LOW | MEDIUM | HIGH
├── service/
│   └── TaskService.java            # Business logic + in-memory store
├── exception/
│   ├── TaskNotFoundException.java
│   └── GlobalExceptionHandler.java # @RestControllerAdvice
└── config/
    └── ApplicationConfig.java      # Reads APP_ENV, APP_VERSION, APP_MESSAGE
```

---

## API Endpoints

| Method | Path | Description | Status Code |
|---|---|---|---|
| GET | `/` | Application info | 200 |
| GET | `/version` | Version info | 200 |
| GET | `/health` | Health check | 200 |
| GET | `/actuator/health` | Actuator health | 200 |
| POST | `/api/tasks` | Create task | 201 |
| GET | `/api/tasks` | Get all tasks | 200 |
| GET | `/api/tasks/{id}` | Get task by ID | 200 / 404 |
| PUT | `/api/tasks/{id}` | Update task | 200 / 404 |
| DELETE | `/api/tasks/{id}` | Delete task | 204 / 404 |
| PATCH | `/api/tasks/{id}/status` | Update task status | 200 / 404 |

### Create Task – Example Request

```json
POST /api/tasks
{
  "title": "Learn Docker",
  "description": "Practice Docker commands",
  "status": "TODO",
  "priority": "HIGH"
}
```

### Update Status – Example Request

```json
PATCH /api/tasks/{id}/status
{
  "status": "DONE"
}
```

---

## Environment Variables

| Variable | Description | Example |
|---|---|---|
| `APP_ENV` | Environment name | `DEV`, `STAGING`, `PRODUCTION` |
| `APP_VERSION` | Application version / Git SHA | `abc123` |
| `APP_MESSAGE` | Welcome message | `Welcome to Development` |

Defaults (local development only):

| Variable | Default |
|---|---|
| `APP_ENV` | `LOCAL` |
| `APP_VERSION` | `local-dev` |
| `APP_MESSAGE` | `Welcome to Local Development` |

---

## Local Setup

### Prerequisites

- Java 17
- Maven 3.x
- Docker (optional, for container testing)

### Run Locally with Maven

```bash
mvn spring-boot:run
```

Or with custom environment:

```bash
APP_ENV=DEV APP_VERSION=abc123 APP_MESSAGE="Welcome to Development" mvn spring-boot:run
```

---

## Maven Build Commands

```bash
# Compile and run tests
mvn clean test

# Build JAR (skip tests)
mvn clean package -DskipTests

# Build JAR and run tests
mvn clean package
```

The JAR is produced at: `target/taskflow-0.0.1-SNAPSHOT.jar`

---

## Docker Build Command

```bash
docker build -t taskflow:local .
```

---

## Docker Run Command

```bash
docker run -p 8080:8080 \
  -e APP_ENV=DEV \
  -e APP_VERSION=local \
  -e APP_MESSAGE="Welcome to Development" \
  taskflow:local
```

---

## Build Once, Deploy Many

This is the core DevOps principle behind this project.

**The same Docker image is built once and promoted through all environments.**  
Only the environment variables change — the image does not.

### Dev

```bash
docker run -p 8080:8080 \
  -e APP_ENV=DEV \
  -e APP_VERSION=abc123 \
  -e APP_MESSAGE="Welcome to Development" \
  <ecr-repo>/taskflow:abc123
```

### Staging

```bash
docker run -p 8080:8080 \
  -e APP_ENV=STAGING \
  -e APP_VERSION=abc123 \
  -e APP_MESSAGE="Welcome to Staging" \
  <ecr-repo>/taskflow:abc123
```

### Production

```bash
docker run -p 8080:8080 \
  -e APP_ENV=PRODUCTION \
  -e APP_VERSION=abc123 \
  -e APP_MESSAGE="Welcome to Production" \
  <ecr-repo>/taskflow:abc123
```

The `/version` endpoint proves the same image is running in each environment:

```json
{
  "application": "TaskFlow",
  "environment": "STAGING",
  "version": "abc123"
}
```

---

## Health Check

```bash
curl http://localhost:8080/health
```

Response:

```json
{ "status": "UP" }
```

Also available via Spring Boot Actuator:

```bash
curl http://localhost:8080/actuator/health
```

The `/health` endpoint is used as the smoke test in the CI/CD pipeline after each deployment.

---

## Version Endpoint

```bash
curl http://localhost:8080/version
```

Response:

```json
{
  "application": "TaskFlow",
  "environment": "DEV",
  "version": "abc123"
}
```

This endpoint is used in the pipeline to verify that the correct image version has been deployed to each environment.

---

## Pipeline Overview

```
GitHub Push
    │
    ▼
Build & Test (mvn clean package)
    │
    ▼
Docker Build & Push → Amazon ECR
    │
    ▼
Deploy to ECS Fargate (DEV)
    │
    ▼
Smoke Test: GET /health → 200 OK
    │
    ▼
Deploy to ECS Fargate (STAGING)
    │
    ▼
Smoke Test: GET /health → 200 OK
    │
    ▼
Deploy to ECS Fargate (PRODUCTION)
```

The same ECR image tag is used across all three stages. Only the ECS task definition environment variables differ.
