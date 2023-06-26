FROM openjdk:17-jdk-alpine
LABEL authors="Faouzi <me@mfaouzi.com>"
MAINTAINER mfaouzi

COPY target/Trakz-server-0.0.1-SNAPSHOT.jar Trakz-server-0.0.1-SNAPSHOT.jar
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","/Trakz-server-0.0.1-SNAPSHOT.jar"]