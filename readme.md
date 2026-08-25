# Bookserver

A Spring Boot REST API for managing books and authors.

## Demo
- Production demo URL available on Canvas
- Login with `vera/vera`, `marie/password`, or `admin/admin`
- Or sign up to register as a new user

## Requirements

- **Server**: Java 25 (on PATH)
- **Frontend**: Node (v25.6.0) and npm (11.8.0) (on PATH)
- Import this repo in IntelliJ (as Maven project)
- Repo contains server (in `.`) and client (in `./src/main/frontend`)

## JDK 25 Features Used

This project uses modern Java features:

| Feature                          | Usage                                            |
|----------------------------------|--------------------------------------------------|
| **Records (JDK 16+)**            | All DTOs are immutable records                   |
| **Stream.toList() (JDK 16+)**    | Replaces `Collectors.toList()`                   |
| **Text Blocks (JDK 15+)**        | Multi-line strings in OpenAPI descriptions       |
| **String.formatted() (JDK 15+)** | Instance method for string formatting            |
| **Constructor Injection**        | Spring best practice (no `@Autowired` on fields) |

### DTO Architecture (Modern Approach)

All DTOs use **Java Records** (immutable data carriers):

```java
public record BookDTO(int id, String title, List<AuthorDTO> authors) {}
public record BookDetailedDTO(int id, String title, String description,
                               List<AuthorDTO> authors,
                               List<BookDTO> booksSameAuthor) {}
```

**Key principles:**

- Records are immutable - all data set at construction time
- No ModelMapper - manual mapping in converters (explicit, fast)
- Service layer gathers all data before creating DTOs

## Server

### Start server

* In IntelliJ: Run `BookserverApplication`
* Or via command line:
  ```bash
  ./mvnw package
  java --enable-preview -jar target/bookserver-0.0.1-SNAPSHOT.jar
  ```

### Try out

* API GET request in browser: **GET http://localhost:8080/api/books**
* API documentation: http://localhost:8080/swagger-ui/index.html
* H2 in-memory database: http://localhost:8080/h2-console/
    * JDBC URL: `jdbc:h2:mem:books`
    * Username: `sa`, no password
* Postman:
    * Import collection `Books.postman_collection.json`
    * Or import swagger-doc: http://localhost:8080/v2/api-docs
    * POST/PUT/DELETE require CSRF protection:
        * Copy value of `XSRF-TOKEN` Cookie to Header `X-XSRF-TOKEN`
        * This is done automatically in the collection - just create an environment first (e.g., dev)
    * Important: always keep Postman collection up-to-date in the repo
* Client served by server:
    * On index.html: http://localhost:8080/
    * Requires at least a "maven compile" first

## Client

### Start client (development)

* In IntelliJ
* Or via command line in `bookserver/src/main/frontend`:
  ```bash
  npm run start
  ```

### Try out

* http://localhost:3000
* Login with vera/vera, marie/password, admin/admin
* ... or signup to register as a new user

## Application Architecture

### Core Entities and Relationships
- **Book**: Central entity with title, price, and many-to-many relationship with Authors
- **Author**: Linked to multiple books
- **Genre**: Book categorization
- **User**: Authentication and authorization with role-based access

### Layer Structure
- **Controllers**: REST endpoints (`/api/*`) with Swagger documentation
- **Services**: Business logic layer
- **Repositories**: Data access layer using Spring Data JPA
- **DTOs**: Immutable Java Records with manual converters
- **Models**: JPA entities with Lombok annotations and validation

### Security Configuration
- CSRF protection enabled with cookie-based tokens
- Session-based authentication with database user store
- Role-based authorization (admin/user roles)
- GET requests on `/api/**` are public, POST/PUT/DELETE require authentication
- H2 console accessible in development mode

### Database Profiles
- **dev** (default): H2 in-memory database with `data.sql` initialization
- **test**: Separate H2 instance for testing, no data initialization
- **prod**: PostgreSQL with Flyway migrations (database URL required)

## Development URLs

| Service | URL | Notes |
|---------|-----|-------|
| **Full Application** | http://localhost:8080 | Complete app (after build) |
| **Frontend Dev Server** | http://localhost:3000 | Hot reload during development |
| **API Documentation** | http://localhost:8080/swagger-ui/index.html | Swagger UI |
| **H2 Console** | http://localhost:8080/h2-console | Dev mode only |

## Authentication

### Default Users
Defined in `data.sql`:
- `vera/vera` - Regular user
- `marie/password` - Regular user
- `admin/admin` - Admin user

Users can also register new accounts via the signup endpoint.

## Testing

- Integration tests inherit from `AbstractIntegrationTest`
- Tests use `@ActiveProfiles("test")` for test database configuration
- MockMvc for controller testing with automatic CSRF token handling
- Test classes organized by controller and operation

### Running Tests from Command Line

To run unit tests quickly without rebuilding the frontend:
```bash
mvn test "-Dskip.installnodedeps=true" "-Dskip.npm=true" "-Dmaven.antrun.skip=true"
```



## Common Development Tasks

### Adding a New Entity

1. Create model class with JPA annotations
2. Create repository interface
3. Create DTOs (detailed and simplified)
4. Create DTO converter
5. Create service interface and implementation
6. Create controller with REST endpoints
7. Add test classes
8. Update `data.sql` if needed

### Running with Different Profiles

```bash
# Development (H2, default)
./mvnw spring-boot:run

# Test profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=test

# Production (PostgreSQL)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## Troubleshooting

### Frontend not loading at localhost:8080
- Ensure you've run `./mvnw compile` or `./mvnw package` at least once
- Check that frontend build files are in `target/classes/public`

### CSRF errors in Postman
- Ensure you've created a Postman environment
- The collection automatically handles tokens
- For manual requests: copy XSRF-TOKEN cookie to X-XSRF-TOKEN header

### Database issues
- Check H2 console at http://localhost:8080/h2-console
- Verify JDBC URL: `jdbc:h2:mem:books`
- Ensure `data.sql` is being loaded (dev profile only)

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)