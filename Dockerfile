FROM openjdk:17
RUN ./gradlew clean build
COPY build/libs/*.jar squadmania.jar
ENTRYPOINT [ "java", "-jar", "squadmania.jar" ]
