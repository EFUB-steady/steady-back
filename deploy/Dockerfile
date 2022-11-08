FROM openjdk:11-jdk-slim

ARG JAR_FILE=*-SNAPSHOT.jar
COPY ${JAR_FILE} myapp.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","myapp.jar", \
"--spring.config.location=/config/application-secret.properties"]
