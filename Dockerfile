# Use the Maven 3.8.2 with JDK 8 image as the base image
FROM maven:3.8.2-jdk-8 AS build

# Set the working directory inside the container
WORKDIR /GestionDesStages_V_0

# Copy the pom.xml file to the container
COPY pom.xml .

# Download the dependencies specified in the pom.xml file
RUN mvn dependency:go-offline

# Copy the rest of the application code to the container
COPY src/ src/

# Build the application using Maven
RUN mvn package

# Use an OpenJDK image as the base image for running the application
FROM openjdk:8-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Ensure that the /app directory exists
RUN mkdir -p /app

# Copy the JAR file built in the previous stage to the container
COPY --from=build /GestionDesStages_V_0/target/GestionDesStages-0.0.1-SNAPSHOT.jar /app/GestionDesStages-0.0.1-SNAPSHOT.jar

# Expose the port that your application runs on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "/app/GestionDesStages-0.0.1-SNAPSHOT.jar"]
