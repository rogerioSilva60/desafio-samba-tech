FROM openjdk
WORKDIR /app
COPY target/desafio-samba-tech-0.0.1-SNAPSHOT.jar /app/desafio-samba-tech-app.jar
ENTRYPOINT ["java", "-jar", "desafio-samba-tech-app.jar"]