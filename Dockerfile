FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/movie-booking-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", \
    "--spring.datasource.url=${DB_URL}", \
    "--spring.datasource.username=${DB_USERNAME}", \
    "--spring.datasource.password=${DB_PASSWORD}"]

