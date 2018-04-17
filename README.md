Cinemagic
=========

## Building

Running `mvn package` will build, verify, and package the frontend and backend.

To run the application in development mode from the command line:

```bash
mvn compile spring-boot:run '-Dspring.profiles.active=development'
```

This will automatically spin up an H2 database.

The frontend will now be visible at `http://localhost:8080/booking` and the admin site at 
`http://localhost:8080/admin`.  The default username and password for the admin site is admin/admin. 

### IntelliJ

To run in development mode, create a new run configuration of type `Spring Boot` with main class 
`org.softwire.training.cinemagic.Application`, JRE 1.8, and set the active profile to be `development`.
Under `before launch` add a task of type `run npm script`, which runs `build`.  Move this to happen
before the build stage.

To run the tests, right click on the `test/java` folder and then `run all tests`.  Then you can save 
this configuration for later.  This will not automatically rebuild the frontend, so you will need to 
add this to the test configuration as in the paragraph above.

## Production

You will need to inject or otherwise override the database URL, database username and password, and 
admin passwords in `application-production.properties`, then run:

```bash
mvn compile spring-boot:run '-Dspring.profiles.active=production'
```

## Architecture

        +------------------+
        |                  |
        | Frontend (React) |
        |                  |
        +--------+---------+
                 |
                 |
                 |
        +--------v---------+
        |                  |
        | REST API (Spring |
        |           Boot)  |
        +--------+---------+
                 |
                 |
                 |
        +--------v---------+
        |                  |
        | MySQL            |
        |                  |
        +------------------+

The application is a fairly standard Spring Boot application backed by a MySQL database, or an H2 
database in development and testing.  Communication with the database is via Hibernate and Hibernate 
Validator is used to verify API requests.

The application exposes a REST API at `/api` with functions to books seats in various cinemas, and
also an admin API at `/api/admin` which has functions to create cinemas, films, showings, etc.  
The admin API is authenticated - the user must first log in at `/api/admin/login` and then present
the auth cookie on subsequent requests.  This backend application also serves the frontend at the 
URLs `/booking` and `/admin`.

The frontend is a React application packed with Webpack.  Stylesheets are compiled with Dart SASS.

There are integration tests written with Selenium webdriver.

Copyright © 2018 Softwire - All Rights Reserved
