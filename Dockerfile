# Etapa 1: Construir o JAR na arquitetura nativa
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Usar OpenJDK para execução
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ARG EUREKA_SERVER=api-est-discovery
ARG KEYCLOAK_SERVER=keycloak-prod
ARG KEYCLOAK_SERVER_PORT=8080

EXPOSE 9004
ENTRYPOINT ["java", "-jar", "app.jar"]
