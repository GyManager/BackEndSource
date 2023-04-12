FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY . .
RUN wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
RUN tar -xvf apache-maven-3.6.3-bin.tar.gz
RUN mv apache-maven-3.6.3 /opt/
RUN /opt/apache-maven-3.6.3/bin/mvn clean install -DskipTests

EXPOSE 8081

CMD ["java", "-Dserver.port=8081", "-jar", "target/BackEndSource-1.0-SNAPSHOT.jar"]