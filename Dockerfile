# Stage 1: Build with Maven
FROM maven as build
WORKDIR /GestionDesStages_V_0
COPY . .
RUN mvn clean install

# Stage 2: Create final image
FROM maven:3.8.2-jdk-8
WORKDIR /app
COPY --from=build /GestionDesStages_V_0/target/GestionDesStages-0.0.1.jar /app/
EXPOSE 8000
CMD ["java", "-jar", "GestionDesStages-0.0.1.jar"]
