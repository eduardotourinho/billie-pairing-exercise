Pair Programming Exercise for Billie
=============

This holds the implementation of the [Billie's pair-programming](docs/README.md) exercise.
Although the main use-case was to implement the `Notify shipment`, the `Create Order` use-case was implemented as support to the main use-case.

### Disclaimer

This is my first project written in Kotlin. I'm not 100% sure of Kotlin best practices and mostly followed Java practices. So please, let me know if anything is better done in a different way in Kotlin.

## Code Architecture

The code is organized based on the [Hexagonal architecture](https://reflectoring.io/spring-hexagonal/):

- `adapters` are the concrete implementation for the external dependencies of the application (i.e. Database, Rest request)
  - `in`: Adapters that initiate the request **TO** the application.
  - `out`: Adapter that are responsible for handling the request **FROM** the application;
- `app` contains the business logic and the `domain` for the application. The communication with external dependencies (`adapters`) are done through the defined `ports`.

### Simplified Design Overview 

![Billie hexagonal design.jpg](docs%2FBillie%20hexagonal%20design.jpg)

## API spec

Docs at:
http://localhost:8080/swagger-ui/index.html

### Future improvements

- Better error handling;
- More tests;


