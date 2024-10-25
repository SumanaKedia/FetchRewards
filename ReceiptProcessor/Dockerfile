# Use OpenJDK as base image
FROM openjdk:17-jdk-slim

# Set a working directory
WORKDIR /app

# Copy the built JAR file from the target directory
COPY target/ReceiptProcessor-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
