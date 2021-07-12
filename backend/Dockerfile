FROM openjdk:8-jdk-alpine
ADD build/libs/backend-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]
