# Social Media Backend

The backend is a **Spring Boot REST API** that provides the business logic and data access layer for the Social Media Web Application.

It handles authentication, users, posts, comments, follows, feeds, search, image uploads, and database access.

## Technology Stack

* **Java 21**
* **Spring Boot 3.3.5**
* **Spring Security**
* **Spring Data JPA**
* **PostgreSQL 18**
* **Maven**
* **JWT**
* **JUnit 5**
* **Mockito**
* **Testcontainers**

## Project Structure

The backend is organized by application domain rather than by technical layer.

```text
src/
├── main/
│   └── java/com/steph/
│       ├── auth/          # Authentication and login
│       ├── comment/       # Comment management
│       ├── config/        # Security and application configuration
│       ├── exceptions/    # Exception handling
│       ├── feed/          # User feed generation
│       ├── follows/       # Follow relationships
│       ├── post/          # Post management
│       ├── search/        # User and content search
│       ├── upload/        # Image upload handling
│       └── user/          # User and profile management
│
└── test/
    └── java/com/steph/
        └── ...             # Unit and integration tests
```

Each domain generally contains the components needed to handle its functionality, such as controllers, services, repositories, entities, DTOs, and projections.

## API

All application endpoints are versioned under:

```text
/api/v1
```

### Authentication

| Method | Endpoint                    | Description                         |
| ------ | --------------------------- | ----------------------------------- |
| `POST` | `/api/v1/auth/register`     | Register a new user                 |
| `POST` | `/api/v1/auth/authenticate` | Authenticate with email or username |

### Users

| Method   | Endpoint                          | Description                     |
| -------- | --------------------------------- | ------------------------------- |
| `GET`    | `/api/v1/users`                   | Retrieve users                  |
| `GET`    | `/api/v1/users/{userId}`          | Retrieve a user                 |
| `GET`    | `/api/v1/users/me`                | Retrieve the authenticated user |
| `PUT`    | `/api/v1/users/{userId}`          | Update a user                   |
| `PUT`    | `/api/v1/users/{userId}/password` | Change a user's password        |
| `DELETE` | `/api/v1/users/{userId}`          | Delete a user                   |

### Posts

| Method   | Endpoint                      | Description                        |
| -------- | ----------------------------- | ---------------------------------- |
| `GET`    | `/api/v1/posts/{id}`          | Retrieve a post                    |
| `GET`    | `/api/v1/posts/user/{userId}` | Retrieve posts belonging to a user |
| `POST`   | `/api/v1/posts`               | Create a post                      |
| `PUT`    | `/api/v1/posts/{postId}`      | Update a post                      |
| `DELETE` | `/api/v1/posts/{id}`          | Delete a post                      |

### Comments

| Method   | Endpoint                         | Description                  |
| -------- | -------------------------------- | ---------------------------- |
| `GET`    | `/api/v1/comments/post/{postId}` | Retrieve comments for a post |
| `POST`   | `/api/v1/comments/post/{postId}` | Add a comment                |
| `PUT`    | `/api/v1/comments/{commentId}`   | Update a comment             |
| `DELETE` | `/api/v1/comments/{commentId}`   | Delete a comment             |

### Follows

| Method   | Endpoint                             | Description                   |
| -------- | ------------------------------------ | ----------------------------- |
| `GET`    | `/api/v1/follows/followers/{userId}` | Retrieve a user's followers   |
| `GET`    | `/api/v1/follows/following/{userId}` | Retrieve users being followed |
| `GET`    | `/api/v1/follows/suggestions`        | Retrieve follow suggestions   |
| `GET`    | `/api/v1/follows/recent`             | Retrieve recent follows       |
| `POST`   | `/api/v1/follows/{followedId}`       | Follow a user                 |
| `DELETE` | `/api/v1/follows/{followedId}`       | Unfollow a user               |

### Feed

| Method | Endpoint       | Description                            |
| ------ | -------------- | -------------------------------------- |
| `GET`  | `/api/v1/feed` | Retrieve the authenticated user's feed |

### Search

| Method | Endpoint         | Description                |
| ------ | ---------------- | -------------------------- |
| `GET`  | `/api/v1/search` | Search application content |

### Uploads

| Method | Endpoint              | Description                 |
| ------ | --------------------- | --------------------------- |
| `POST` | `/api/v1/upload/user` | Upload a user profile image |
| `POST` | `/api/v1/upload/post` | Upload an image for a post  |

## Authentication and Security

The backend uses **Spring Security with stateless JWT authentication**.

### Login Flow

`POST /api/v1/auth/authenticate` accepts either an email address or username through the `login` field.

Example:

```json
{
  "login": "user@example.com",
  "password": "secret"
}
```

The authentication process is:

1. `AuthenticationController` receives the login request.
2. `AuthenticationService` delegates authentication to Spring Security.
3. `DaoAuthenticationProvider` loads the user through the configured `UserDetailsService`.
4. The user is located using either their email or username.
5. Spring Security verifies the submitted password against the stored BCrypt hash.
6. A JWT is generated after successful authentication.
7. The JWT is returned to the client.

### JWT

The JWT contains:

* `userId` as a custom claim
* The user's `username` as the JWT subject

Authenticated requests must include:

```http
Authorization: Bearer <token>
```

The `JwtAuthenticationFilter` validates the token and establishes the authenticated user in Spring Security's `SecurityContext`.

The JWT subject intentionally remains the username even though login accepts either email or username. This provides a single canonical identity for token validation.

## Database

The application uses **PostgreSQL 18** with Spring Data JPA.

The primary application data includes:

* Users
* Posts
* Comments
* Follow relationships

When running with Docker Compose, the backend connects to PostgreSQL through the Compose service name:

```text
db:5432
```

The database uses a persistent Docker volume so application data survives container recreation.

Database configuration is provided through environment variables rather than being hard-coded into the application.

## File Uploads

User and post images are handled by the upload components in:

```text
com.steph.upload
```

Uploaded files are stored outside the application JAR.

When running with Docker Compose, the upload directory is mounted to a persistent Docker volume:

```text
/app/uploads
```

This allows uploaded images to persist independently of the backend container.

## Error Handling

Application exceptions are handled centrally through the exception handling components in:

```text
com.steph.exceptions
```

`GlobalExceptionHandler` converts application exceptions into structured HTTP error responses.

This keeps controller code focused on handling requests while providing consistent error responses to the frontend.

## Testing

The backend uses **JUnit 5** and **Mockito** for automated testing.

Tests cover application services and controllers, with both isolated unit tests and integration testing.

The application context integration test uses **Testcontainers** to start a temporary PostgreSQL 18 container during testing.

The current test suite contains **70 automated tests**.

Run the complete backend test suite with:

```bash
./mvnw clean test
```

Tests are also executed automatically by GitHub Actions when changes are pushed or pull requests are opened against `main`.

## Running the Backend Without Docker

The backend can be run independently of Docker when a PostgreSQL instance is available.

From the `back-end` directory:

```bash
./mvnw spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

The backend requires the appropriate database and JWT environment variables to be configured before starting.

## Configuration

The backend reads its environment-specific configuration from environment variables.

The primary variables are:

| Variable                 | Purpose                       |
| ------------------------ | ----------------------------- |
| `DB_HOST`                | PostgreSQL hostname           |
| `DB_PORT`                | PostgreSQL port               |
| `DB_NAME`                | PostgreSQL database name      |
| `DB_USER`                | PostgreSQL username           |
| `DB_PASSWORD`            | PostgreSQL password           |
| `JWT_SECRET`             | Secret used to sign JWTs      |
| `JWT_EXPIRATION`         | JWT expiration time           |
| `JWT_REFRESH_EXPIRATION` | Refresh token expiration time |

For local Docker Compose development, these values are supplied through the project's root `.env` file.

Do not commit `.env` or production secrets to version control.

## Development Notes

The backend follows a domain-oriented structure to keep related functionality together.

The main request flow is generally:

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
PostgreSQL
```

DTOs are used where appropriate to separate API request/response models from persistence entities.

Security-related functionality is centralized in the `config` package, while application-specific exceptions are handled through the `exceptions` package.
