# Stage 1: Build with Maven
FROM maven:3.8.2-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

# Stage 2: Create final image
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/GestionDesStages-0.0.1.jar /app/
EXPOSE 8000
CMD ["java", "-jar", "GestionDesStages-0.0.1.jar"]
