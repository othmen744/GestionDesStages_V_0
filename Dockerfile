# Use a base image with Java and Maven installed
FROM maven:3.8.2-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to install dependencies
COPY pom.xml .

# Install project dependencies
RUN mvn dependency:go-offline

# Copy the rest of the application files
COPY src ./src

# Compile the application
RUN mvn clean package -DskipTests

# Create a new stage for the final image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/GestionDesStages-0.0.1.jar /app/

# Expose the port the application listens on
EXPOSE 8000

# Define the command to run the application when the container starts
CMD ["java", "-jar", "GestionDesStages-0.0.1.jar"]
