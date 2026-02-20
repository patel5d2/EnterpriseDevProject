# =========================================
# Dockerfile - Tri-State Student Directory
# Multi-stage build: Maven build → lightweight JRE runtime
# =========================================

# --- Stage 1: Build ---
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper and pom.xml first for dependency caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cached layer unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Copy source code and build the JAR (skip tests during image build)
COPY src ./src
RUN ./mvnw package -DskipTests -B

# --- Stage 2: Runtime ---
FROM eclipse-temurin:21-jre-alpine AS runtime

# Security: run as non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Change ownership to the non-root user
RUN chown appuser:appgroup app.jar

USER appuser

# Expose the default Spring Boot port
EXPOSE 8080

# Health check: pings the application every 30s, allows 60s for startup
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/login || exit 1

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
