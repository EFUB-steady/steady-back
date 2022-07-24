FROM openjdk:11-jdk

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} myapp.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dspring.config.location=classpath:/application.properties,classpath:/application-secret.properties", "-jar", "/myapp.jar"]
