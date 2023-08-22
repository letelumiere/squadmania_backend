FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} squadmania_auth-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "/squadmania.jar" ]    