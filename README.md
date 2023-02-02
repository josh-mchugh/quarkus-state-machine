# Simple Statemachine With Quarkus

This project is an experiment for designing a simple statemachine. I have used more complex statemachines such as [Spring Statemachine](https://spring.io/projects/spring-statemachine). I wanted to make a simple Java based statemachine pattern without the use of a framework. I used Quarkus as a RESTful API framework for demostration and practice purposes.

I based it off an article from Baeldung: [https://www.baeldung.com/java-enum-simple-state-machine](https://www.baeldung.com/java-enum-simple-state-machine)

Instead of using the LeaveRequestState for manging approval states of somones leave. I decided to make a small statemachine around Git pull requests.

## State Diagram



## Demostration



## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
