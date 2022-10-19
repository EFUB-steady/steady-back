FROM openjdk:11-jre-slim

VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} myapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dspring.config.location=classpath:/application.properties,classpath:/application-secret.properties", "-jar", "/myapp.jar"]
