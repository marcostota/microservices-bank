FROM openjdk:26-ea-25-jdk-slim

LABEL "org.opencontainers.image.authors"="totakai"

COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]
