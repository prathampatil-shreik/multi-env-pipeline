# ============================================================
# TaskFlow - Task Management System
# ============================================================
# Step 1 - Build the JAR:
#   mvn clean package -DskipTests
#
# Step 2 - Build the image:
#   docker build -t taskflow:local .
#
# Step 3 - Run the container:
#   docker run -p 8080:8080 \
#     -e APP_ENV=DEV \
#     -e APP_VERSION=local \
#     -e APP_MESSAGE="Welcome to Development" \
#     taskflow:local
#
# Build once, deploy many:
# The same image runs in Dev, Staging, and Production.
# Only environment variables change — the image does not.
# ============================================================

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Non-root user for security
RUN addgroup -S taskflow && adduser -S taskflow -G taskflow

# Copy the pre-built JAR (run: mvn clean package -DskipTests first)
COPY target/taskflow-0.0.1-SNAPSHOT.jar app.jar

RUN chown taskflow:taskflow app.jar

USER taskflow

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
