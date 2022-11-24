FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY . .
RUN ./mvnw clean install -DskipTests

EXPOSE 8081

CMD ["java", "-Dserver.port=8081", "-jar", "target/BackEndSource-1.0-SNAPSHOT.jar"]