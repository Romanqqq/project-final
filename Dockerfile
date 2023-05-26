FROM openjdk:17.0.1-jdk
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE jira-1.0.jar
COPY resources ./resources


ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/jira-1.0.jar"]