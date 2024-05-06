# Stage 1: Build with Maven
FROM maven:3.8.2-openjdk-17 AS build
WORKDIR /GestionDesStages_V_0
COPY . .
RUN mvn clean install

# Stage 2: Create final image
FROM maven:3.8.2-openjdk-17 AS build
WORKDIR /app
COPY --from=build /GestionDesStages_V_0/target/GestionDesStages-0.0.1.jar /app/
EXPOSE 8000
CMD ["java", "-jar", "GestionDesStages-0.0.1.jar"]
