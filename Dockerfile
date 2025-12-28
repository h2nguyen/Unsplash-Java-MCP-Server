# Multi-stage Dockerfile for Unsplash Java MCP Server
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy gradle files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Give execution permission to gradlew
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build the application
RUN ./gradlew bootJar --no-daemon

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/unsplash-java-mcp-server-*.jar app.jar

# Expose port for SSE transport
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
