FROM openjdk:17
ARG JAR_FILE = build/libs/*.jar
COPY ${JAR_FILE} squadmania.jar
ENTRYPOINT [ "java", "-jar", "/squadmania.jar" ]