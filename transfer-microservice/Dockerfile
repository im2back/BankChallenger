FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn package -DskipTests

FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY --from=build /usr/src/app/${JAR_FILE} app.jar

ARG MOCK_AUTHORIZE=param
ARG USER_MS_URL=param
ARG RABBIT_MQ=param

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "/app.jar"]