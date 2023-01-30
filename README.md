# Fitness API
###### Fitness club management app
Requirements
=======
* Java 17
* Spring Boot 2
* Tests
* Gradle Build
* Docker container

Usage/Docs API
=======
endpoints api:
http://localhost:8080/swagger-ui/index.html

Build
=======
To build and run this application locally, you'll need Git, Gradle and JDK installed on your computer.

From your command line:

```
# Clone this repository
$ git clone https://github.com/alexandr-khvatov/fitness.git

# Go into the repository
$ cd fitness

# Build
$ ./gradlew build

# Run the app
$ java -jar build/libs/*-SNAPSHOT.jar
```

Docker instructions:

```
# Clone this repository
$ git clone https://github.com/alexandr-khvatov/fitness.git

# Build
$ ./gradlew build

# Build Docker Image
$ docker build --build-arg JAR_FILE=build/libs/*.jar -t fitness-backend .

# Run Docker Container
$ docker run -p 8080:8080 fitness-backend

```