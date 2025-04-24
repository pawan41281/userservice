# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add the application's jar to the image
COPY target/userservice.jar userservice.jar

# Command to execute the application
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "userservice.jar"]