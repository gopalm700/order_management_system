FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/notification-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} notfication.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/notfication.jar"]
