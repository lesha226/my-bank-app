FROM eclipse-temurin:21-jre-jammy

WORKDIR /front-ui

COPY target/my-bank-front-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]