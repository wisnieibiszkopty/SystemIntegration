#FROM openjdk:17-jdk-slim AS build

## installing maven
#RUN apt-get update && apt-get install -y maven

#WORKDIR /app

#COPY pom.xml .
#COPY src ./src

## takes a lot of time
#RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

LABEL authors="Kamil Wodowski, Paweł Wiński"

WORKDIR /app

#COPY --from=build /app/target/steam-twitch-integration-0.0.1-SNAPSHOT.jar app.jar
COPY target/steam-twitch-integration-0.0.1-SNAPSHOT.jar app.jar

# coping files with initial data (csv's)
COPY src/main/resources/data ./data

ENTRYPOINT ["java", "-jar", "app.jar"]