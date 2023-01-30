FROM openjdk:17-jdk-alpine
MAINTAINER kh.com
ARG JAR_FILE=target/libs/fitness-*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p files/image
VOLUME /files/image
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080