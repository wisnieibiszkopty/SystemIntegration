FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-alpine

LABEL authors="Kamil Wodowski, Paweł Wiński"

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

# coping files with initial data (csv's)
COPY src/main/resources/data ./data

CMD ["java", "-jar", "app.jar"]
