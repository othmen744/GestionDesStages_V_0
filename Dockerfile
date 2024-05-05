FROM maven as build
WORKDIR /GestionDesStages_V_0
COPY . .
RUN mvn install

FROM maven:3.8.2-jdk-8
WORKDIR /GestionDesStages_V_0
COPY --from=build /GestionDesStages_V_0/target/GestionDesStages-0.0.1.jar /GestionDesStages_V_0/
EXPOSE 8000
CMD ["java", "-jar", "GestionDesStages-0.0.1.jar"]
