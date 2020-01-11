# Training Tracker - backend

Backend application written in Java with Spring Boot, which purpose is to expose the API, which will enable to
* sign users up/in
* save their favourites gyms and exercise
* keep track of their trainings activity

... and the main goal of this project is to practice software development techniques :)

## Technologies

This is a simple CRUD monolithic application written with Spring Boot.
Languages, frameworks and tools used:

* Java 11
* Groovy
* Spring Boot 2
* Hibernate
* Maven
* Mapstruct, Lombok
* Spock

## Getting Started

In order to be able to run this application, please choose how You want to run it:

* [Local installation](#a-local-installation)
* [Docker](#b-docker)

### A. Local Installation
You can run this application locally, but You have to set up and configure:
* Java 11
* Maven 3.6

After that, build a .jar file by running command:

```console 
mvn clean package
```

File will be saved in */target* directory. After that run the bellow command:

```console
java -jar ./target/training-tracker-0.0.1-SNAPSHOT.jar --spring.profiles.active=local\
```

After that application should be working locally on port 8080 by default. Please notice that:
* application version (*-0.0.1-SNAPSHOT*) will vary with the time
* profile 'local' was activated from the command line. Application profiles will be listed later 

### B. Docker
Docker is going to be supported in the future :)

## Application profiles

1. default - common application settings
2. sql-logger - activate this profiles in order to see a lot information about SQL queries, parameters etc.
3. jpa-debug - activate this profiles in order to see DEBUG logs from JPA, like transaction lifecycle details
4. jpa-stats - activate this profile in order to see statistics about executed SQL queries
5. local - profile for running application locally. Uses an embedded H2 database

## Example of use

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
Bellow You can see an example IntelliJ configuration goals
 
 * Maven
    * training-tracker \[build-app\] builds application ```clean package -f pom.xml```
    * training-tracker \[unit-tests\] runs unit tests  ```clean test -f pom.xml```
    * training-tracker \[integration-tests\] runs integration tests```clean verify -f pom.xml```
 * Spring Boot
    * training-tracker \[local\] ```active profiles: local``` runs main class *pl.akolata.trainingtracker.TrainingTrackerApplication* with local profile