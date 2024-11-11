FROM openjdk:17-jdk as build
RUN mkdir -p /app
WORKDIR /app
COPY target/gestion-station-ski-1.0.jar /app/gestion-station-ski-1.0.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "gestion-station-ski-1.0.jar"]
