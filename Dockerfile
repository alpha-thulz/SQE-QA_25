FROM maven:3.9.8 AS api-backend
LABEL authors="SQE/QA HUB"

COPY backend/pom.xml pom.xml
COPY backend/src src

RUN mvn clean package -DskipTests

FROM openjdk:21

COPY --from=api-backend target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]