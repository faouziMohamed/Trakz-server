FROM openjdk:17-jdk-alpine
LABEL authors="Faouzi <me@mfaouzi.com>"
MAINTAINER mfaouzi

COPY ./target/trakz-server-1.0.0.jar trakz-server-1.0.0.jar
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","/trakz-server-1.0.0.jar"]
