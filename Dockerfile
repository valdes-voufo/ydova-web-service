
# Use a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy application JAR file into the container
COPY mail.service-1.0.0.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
