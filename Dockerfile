# Stage 1: Build stage (using Maven)
FROM maven:3.8.2-jdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Create final image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/GestionDesStages-0.0.1.jar /app/
EXPOSE 8000
CMD ["java", "-jar", "GestionDesStages-0.0.1.jar"]
