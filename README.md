# java-dining-review-api :curry:

## :page_facing_up: Description

This project is the final project of the [Codecademy](https://codecademy.com) course
*Create REST APIs with Spring and Java*. The Dining Review API allows users to create accounts,
add restaurants, and submit reviews focused on how well the restaurants accommodate dairy,
peanut, and egg allergies. Users can provide feedback on restaurants' allergy-friendly practices,
while admins have the authority to accept or reject these reviews.

## :arrow_forward: Getting Started

To manage the project's build I've used **Maven**.

* To run the application: `./mvnw spring-boot:run`
* To run the automatic tests: `./mvnw test`
* To create the jar file, run: `./mvnw package`

## :factory: Business

Here is a basic diagram of how the API works. Right now, both registered and unregistered users
can add new restaurants.
![dining-review-flow](dining-review.png)

## :woman_technologist: Tech Stack

The dining review app is built using Java and Spring Boot framework.

**Additional libraries**:

- **Lombok**: to reduce boilerplate code by using annotations.
- **JUnit**: to write and run tests.
- **Mockito**: to create mock objects in unit tests to isolate the behavior of the class being tested from its
  dependencies.
- **AssertJ**: to make the tests more readable by writing assertions that resemble natural language.
- **H2Database**: to set up an H2 database.