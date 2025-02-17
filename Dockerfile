FROM openjdk:17

WORKDIR /app

COPY target/*.jar backend.jar

EXPOSE 8080

CMD ["java", "-jar", "backend.jar"]
