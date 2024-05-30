FROM openjdk:17-jdk-slim

LABEL authors="Kamil Wodowski, Paweł Wiński"

WORKDIR /app

COPY target/steam-twitch-integration-0.0.1-SNAPSHOT.jar app.jar

# coping files with initial data (csv's)
COPY src/main/resources/data ./data

ENTRYPOINT ["java", "-jar", "app.jar"]