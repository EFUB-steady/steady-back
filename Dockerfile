FROM openjdk:11-jdk-slim

ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} myapp.jar
EXPOSE 8080
#ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dspring.config.location=classpath:/application.properties,classpath:/application-secret.properties", "-jar", "/myapp.jar"]
ENTRYPOINT exec java -jar redistest.jar