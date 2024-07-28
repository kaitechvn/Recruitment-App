# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS maven_build
WORKDIR /tmp/

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ src/

RUN ./mvnw package -Dmaven.test.skip=true

# Stage 2: Package the application
FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=maven_build /tmp/target/recruitment-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
