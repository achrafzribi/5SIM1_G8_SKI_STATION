# Utiliser une image de base avec OpenJDK 8, compatible avec  projet
FROM openjdk:8-jdk-alpine

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copie le fichier JAR généré par Maven dans le conteneur
# Le fichier JAR se trouve dans le dossier target et doit correspondre à votre artefact
COPY target/gestion-station-ski-1.0.jar /app/gestion-station-ski-1.0.jar

# Expose le port 8089 (configuration du port dans application.properties)
EXPOSE 8089

# Commande pour démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app/gestion-station-ski-1.0.jar"]