# Use OpenJDK 17 as the base image
FROM eclipse-temurin:17-jdk AS maven_build

# Set the working directory in the container
WORKDIR /app/

# Copy the Maven wrapper and project descriptor
COPY mvnw /app/
COPY .mvn /app/.mvn/
COPY pom.xml /app/

# Copy the entire project
COPY src /app/src/

# Build the application
RUN ./mvnw package -Dmaven.test.skip=true

# Production stage
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file from the build stage to the production stage
COPY --from=maven_build /app/target/*.jar /app.jar

# Expose port 8080
EXPOSE 8080

ENV JAVA_OPTS="-Xms256m -Xmx2048m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
