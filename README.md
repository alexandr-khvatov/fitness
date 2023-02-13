# Fitness API
###### Fitness club simple content management app
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
To build and run this application locally, you'll need Git and JDK installed on your computer.


Docker instructions:

```
# Clone this repository
$ git clone https://github.com/alexandr-khvatov/fitness.git

# Go into the repository
$ cd fitness

# Build and run container
$ docker-compose --env-file .env up

```

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