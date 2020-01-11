[![Build Status](https://travis-ci.org/akolata/Training-Tracker-Backend.svg?branch=master)](https://travis-ci.org/akolata/Training-Tracker-Backend)
# Training Tracker - backend

Spring Boot application written in Java, API for the Training Tracker project. Features:
* sign user up/in
* save user's favourites gyms and exercise
* keep track of user's activity - register their training progress

The main goal of this project is to practice software development techniques.

## Table of contents

* [Technologies](#technologies)
* [How to run](#how-to-run)
* [Application profiles](#application-profiles)
* [Embedded H2 database](#embedded-h2-database)
* [Application configurations](#application-configurations)

## Technologies

This is a simple CRUD monolithic application written with Spring Boot.  
Languages, frameworks tools and libraries used:

* Java 11
* Groovy
* Spring Boot 2
* Hibernate
* Maven
* MapsStruct
* Lombok
* Spock
* Docker

## How to run

You can run this application locally in two ways:

* [Local installation](#a-running-jar-locally)
* [Docker](#b-docker)

### A. Running .jar locally
You can run this application locally, but You have to set up and configure:
* Java 11
* Maven 3.6

After that, from project's directory build a .jar file by running a command:

```console 
mvn clean package
```

JAR will be saved in */target* directory. After that run the below command:

```console
java -jar ./target/training-tracker-0.0.1-SNAPSHOT.jar --spring.profiles.active=local\
```

After that application should be working locally on port 8080 by default. Please notice that:
* application version (*-0.0.1-SNAPSHOT*) will vary with the time
* profile 'local' was activated from the command line. Application profiles will be listed later 

### B. Docker

### Building application image
Build an image by running below command from the console:
```console
mvn jib:dockerBuild
```

After that image will be present in Your local Docker's registry.
 
### Running a container

* [Running container from the console](#a-running-container-from-the-console)
* [Docker-compose](#b-docker-compose)

#### A. Running container from the console
In order to run application using *local* profile use this command:
```console
docker run -d -p8080:8080 --name="training-tracker-backend" training-tracker-backend --spring.profiles.active=local
```

#### B. Docker-compose
Go to ./docker directory, and run:
```console
docker-compose up -d
```

to start the application, or:

```console
docker-compose down
```
to stop it

## Application profiles

1. default - common application settings
2. sql-logger - activate this profiles in order to see a lot information about SQL queries, parameters etc.
3. jpa-debug - activate this profiles in order to see DEBUG logs from JPA, like transaction lifecycle details
4. jpa-stats - activate this profile in order to see statistics about executed SQL queries
5. local - profile for running application locally. Uses an embedded H2 database

### Profiles usage examples

* --spring.profiles.active=local - runs on port 8080, with embedded H2 database
* --spring.profiles.active=local,sql-logger - runs on port 8080, with embedded H2 database, logs SQL queries to the console
* --spring.profiles.active=local,sql-logger,sql-stats - runs on port 8080, with embedded H2 database, 
logs SQL queries to the console and displays SQL statistics

### Embedded H2 database
Go to http://localhost:8080/h2 to see a login form to H2 database web console. Username, password and
JDBC URL can be found as properties in 'local' application profile. Use:  
username: sa  
password: (empty password input)  
JDBC URL: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;

to log into embedded H2 database console

## Application configurations
Below You can see an example IntelliJ configuration goals
 
 * Maven
    * training-tracker \[build-app\] ```clean package -f pom.xml``` builds application
    * training-tracker \[unit-tests\] ```clean test -f pom.xml``` runs unit tests
    * training-tracker \[integration-tests\] ```clean verify -f pom.xml``` runs integration tests
    * training-tracker \[build-docker-image\] ```jib:dockerBuild -f pom.xml``` build Docker's image in local registry
 * Spring Boot
    * training-tracker \[local\] ```active profiles: local``` runs main class *pl.akolata.trainingtracker.TrainingTrackerApplication* with local profile