FROM openjdk:11-jdk-slim

ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} myapp.jar
EXPOSE 8080

java -jar myapp.jar -Dspring.profiles.active=secret
