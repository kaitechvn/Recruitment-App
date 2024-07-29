# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS maven_build
WORKDIR /tmp/

COPY .mvn/ /tmp/.mvn/
COPY mvnw /tmp/
COPY pom.xml /tmp/
COPY src /tmp/src/

RUN ./mvnw package -Dmaven.test.skip=true

# Stage 2: Package the application
FROM openjdk:17-jdk-slim

EXPOSE 8080
ENV JAVA_OPTS="-Xms256m -Xmx2048m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]

COPY --from=maven_build /tmp/target/*.jar /app.jar

