# Java Project

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

## Overview

This is a Spring Boot application designed following the principles of Hexagonal Architecture. This architecture ensures a clear separation of concerns and promotes maintainability, testability, and scalability. This application provides a RESTful API for fetching price information.

## Project Structure

```bash
hexagonal/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │   └── com/
│ │   └── example/
│ │   └── hexagonal/
│ │   │ ├── application/
│ │   │ │ ├── configuration/
│ │   │ │ │ └── MetricsConfiguration.java
│ │   │ │ ├── dto/
│ │   │ │ │ ├── FindPriceRequest.java
│ │   │ │ │ └── PriceResponse.java
│ │   │ │ ├── exception/
│ │   │ │ │ ├── InvalidPriceRequestException.java
│ │   │ │ │ └── PriceNotFoundException.java
│ │   │ │ ├── mapper/
│ │   │ │ │ └── PriceDtoMapper.java
│ │   │ │ ├── service/
│ │   │ │ │ └── PriceService.java
│ │   │ ├── domain/
│ │   │ │ ├── model/
│ │   │ │ │ ├── Brand.java
│ │   │ │ │ ├── Currency.java
│ │   │ │ │ ├── Fee.java
│ │   │ │ │ ├── Product.java
│ │   │ │ │ └── Price.java
│ │   │ │ ├── repository/
│ │   │ │   └── PriceRepository.java
│ │   │ ├── infrastructure/
│ │   │   ├── configuration/
│ │   │   │ └── BeanConfiguration.java
│ │   │   ├── controller/
│ │   │   │ └── exception/
│ │   │   │   └── GlobalExceptionHandler.java
│ │   │   │ └── PriceController.java
│ │   │   ├── jdbc/
│ │   │   │ ├── entity/
│ │   │   │ │ └── JdbcPrice.java
│ │   │   │ ├── mapper/
│ │   │   │ │ ├── JdbcPriceMapper.java
│ │   │   │ │ ├── PricePreparedStatementSetter.java
│ │   │   │ │ └── PriceRowMapper.java
│ │   │   │ ├── adapter/
│ │   │   │   └── JdbcPriceRepository.java
│ │   │   └── jpa/
│ │   │     ├── entity/
│ │   │     │ └── JpaPrice.java
│ │   │     ├── mapper/
│ │   │     │ └── JpaPriceMapper.java
│ │   │     └── adapter/
│ │   │       ├── JpaPriceRepository.java
│ │   │       └── JpaPriceRepositoryImpl.java
│ │   └── resources/
│ │     └── application.properties
│ │     └── schema.sql
│ │     └── data.sql
│ ├── test/
│   ├── java/
│   │ └── com/
│   │ └── example/
│   │ └── hexagonal/
│   │   ├── application/
│   │   │ └── service/
│   │   │   ├── PriceServiceIT.java
│   │   │   └── PriceServiceTest.java
│   │   ├── domain/
│   │   │ └── model/
│   │   │   └── PriceResponseTestEntity.java
│   │   ├── infrastructure/
│   │     └── controller/
│   │       ├── PriceControllerIT.java
│   │       ├── PriceControllerTest.java
│   │       └── PriceControllerUAT.java
│   └── resources/
│   └── application-test.properties
│
├── pom.xml
└── README.md
```

## Design Principles and Methodologies

### Hexagonal Architecture

Hexagonal Architecture is an architectural pattern used to create loosely coupled application components. This architecture promotes separation of concerns, allowing the business logic to be isolated from external systems like databases, UI or other services.

### SOLID Principles

The application adheres to the SOLID principles:

#### Single Responsibility Principle (SRP)

**Principle:** A class should have only one reason to change, meaning it should have only one responsibility.

**Application:**

- `PriceService` handles business logic related to prices.
- `PriceController` handles HTTP requests and responses.
- `PriceRepository` interface (and implementations like `JdbcPriceRepository` or `JpaPriceRepository`) handle data persistence.
- `GlobalExceptionHandler` handles exception management across the application.

Each class has a clearly defined responsibility, ensuring that changes in one aspect of the application (e.g., business logic, data persistence, request handling) do not affect other aspects.

#### Open/Closed Principle (OCP)

**Principle:** Software entities should be open for extension but closed for modification.

**Application:**

- Interfaces like `PriceRepository` allow new implementations (e.g., switching from JDBC to JPA) without modifying the existing interface.
- Adding new features like new DTOs or new validation logic can be done by extending existing classes or adding new ones without modifying the existing code.

This approach allows the application to be extended with new functionality without modifying existing, tested, and working code, reducing the risk of introducing bugs.

#### Liskov Substitution Principle (LSP)

**Principle:** Objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program.

**Application:**

- The `PriceRepository` interface can have multiple implementations (e.g., `JdbcPriceRepository`, `JpaPriceRepository`...), and these can be used interchangeably without altering the correct functioning of the application.
- The design ensures that different implementations of an interface can be substituted without changing the behavior expected by the interface.

#### Interface Segregation Principle (ISP)

**Principle:** Clients should not be forced to depend on methods they do not use.

**Application:**

- The `PriceRepository` interface defines only methods related to price data operations.
- Separate interfaces could be created for different entities if needed in the future, ensuring that clients depend only on the relevant methods.

This keeps the interfaces focused and relevant to the specific use cases, reducing unnecessary dependencies and promoting decoupling.

#### Dependency Inversion Principle (DIP)

**Principle:** High-level modules should not depend on low-level modules. Both should depend on abstractions (e.g., interfaces).

**Application:**

- `PriceService` depends on the `PriceRepository` interface, not on a specific implementation.
- Dependency Injection (DI) is used to inject the appropriate `PriceRepository` implementation into the `PriceService`.

This decouples the high-level business logic from the low-level data access logic, making the system more flexible and easier to maintain.

### Summary

By adhering to SOLID principles, the application design ensures the following benefits:

- **Maintainability:** Each class has a single responsibility, making it easier to understand, test, and maintain.
- **Extensibility:** The application can be extended with new features without modifying existing code, reducing the risk of introducing bugs.
- **Testability:** Clear separation of concerns and dependency injection make unit testing easier.
- **Flexibility:** The use of interfaces and dependency injection allows for easy replacement of components (e.g., changing from JPA to JDBC persistence mechanism) without significant changes to the codebase.
- **Decoupling:** High-level business logic is decoupled from low-level data access logic, promoting a clean and modular architecture.

The current project structure and codebase adhere to these principles, ensuring a robust, maintainable, and scalable application design.

## Design Patterns

### Dependency Injection

Used extensively for injecting dependencies, enhancing testability and modularity.

### Repository Pattern

Mediates between the domain and data mapping layers, using a collection-like interface for accessing domain objects.

### DTO Pattern

The DTO (Data Transfer Object) pattern is used to transfer data between different layers of an application without exposing the underlying domain models. It ensures data integrity, security and decoupling of the presentation layer from the domain layer.

## Design Decisions

### Layered Architecture

- **Application Layer:** Contains services (`PriceService`), business custom exceptions, metrics configuration, DTOs and its mappers.
- **Domain Layer:** Contains domain models (`Price`) and ports (`PriceRepository`).
- **Infrastructure Layer:** Contains JDBC/JPA implementations, API controllers, GlobalExceptionHandler, data persistence mappers and data persistence entities.

### DTOs and Mappers

DTOs (`FindPriceRequest` and `PriceResponse`) have been created to transfer data between the application and external clients. Mappers (`PriceDtoMapper`) convert domain - persistence model. Furthermore, on infrastructure layer, a jdbc/jpa entity was created to interact with the DB and some necessary mappers were added along with this entity to keep on the same layer all the jdbc/jpa stuff.

### Entities

Brand, Fee, Price and Product were created as separated entities as I think each one has its own responsibility. Each entity repository was created but not implemented as it was out of the exercise scope.

## Installation

Clone the repository:

```bash
git clone https://github.com/deluciaDL/JavaTest.git
```
Build the project:

```bash
mvn clean install
```
Run the application:

```bash
mvn spring-boot:run
```

## H2 Database

H2 database has been added to the project. It loads specific data when starting the application. You can check the following files to see DB data:

**schema.sql** and **data.sql** files contain DB data structure along with initial data.

In order to access H2 Database interface (application must be running):

```bash
http://localhost:8080/h2-console/
```
Then the credentials are:

- **user:** sa
- **pass:**

Anyway, you can check this credentials on the `application.properties` file

## Testing
**Unit Tests**

Located under `src/test/java/com/example/hexagonal/application/service` and `src/test/java/com/example/hexagonal/infrastructure/controller`.

**Integration Tests**

Located under `src/test/java/com/example/hexagonal/application/service` and `src/test/java/com/example/hexagonal/infrastructure/controller`.

### Running Tests
Use the following Maven command to run unit / integration, UAT tests:

```bash
mvn test
```
```bash
mvn verify
```
## API Documentation
The API documentation is generated using **OpenAPI (Swagger)**. Once the application is running, you can access the Swagger UI at:

```bash
http://localhost:8080/swagger-ui/index.html
```

## Logging

**SLF4J** is used for logging.

## Error Handling

A global exception handler `(GlobalExceptionHandler)` manages application-wide exceptions, custom exceptions (`InvalidPriceRequestException` and `PriceNotFoundException`) and provides meaningful error messages to the clients.

## Application Monitoring

Jacoco has been added to monitor test coverage on the application. In order to use it, please execute the following commands:

```bash
mvn test
mvn jacoco:report
```

Then you could open existing `index.html` under path:

```bash
*/target/site/jacoco/
```

## Metrics

The application has been configured to use MicroMeter for metrics. The following endpoints are available after starting the application:

*Displays the available endpoints*
```bash
http://localhost:8080/actuator
```

*Displays the application status*
```bash
http://localhost:8080/actuator/health
```

*Displays the metrics*
```bash
http://localhost:8080/actuator/prometheus
```
