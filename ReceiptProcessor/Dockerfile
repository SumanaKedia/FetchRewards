# Use a Maven image to build the application
FROM maven:3.9.0 AS build

# Set a working directory
WORKDIR /app

# Copy the pom.xml and any other necessary files for dependency resolution
COPY pom.xml ./
COPY src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Use a slim JDK image to run the application
FROM openjdk:17-jdk-slim

# Set a working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/ReceiptProcessor-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
