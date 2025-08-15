FROM openjdk:21

WORKDIR /app
COPY build/libs/*.jar app.jar
COPY src/main/resources/keystore.p12 /app/resources/keystore.p12

ENTRYPOINT ["java", "-jar", "app.jar"]

