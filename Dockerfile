FROM openjdk:17-jdk-alpine as builder
MAINTAINER kh.com
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/*.jar
RUN mkdir -p files/image
VOLUME /files/image
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/*.jar"]