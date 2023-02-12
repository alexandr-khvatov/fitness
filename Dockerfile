FROM openjdk:17-alpine as builder
MAINTAINER kh.com
WORKDIR /app
COPY .gradle/ .gradle
COPY . .
RUN ./gradlew bootJar

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/*.jar
RUN mkdir -p files/image
VOLUME /files/image
EXPOSE 8080
ENTRYPOINT ["java","-jar","/*.jar"]