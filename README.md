# Social Media Backend API
A RESTful backend service for a social media platform that supports user authentication, profiles, and follow relationships.

## Overview
This project provides the backend infrastructure for a social media application. It handles:

- User registration and authentication
- Profile management
- Follow/unfollow functionality
- Data persistence with a relational database
- REST API endpoints for frontend integration

## Tech Stack
- Language: Java
- Framework: Spring Boot
- Database: PostgreSQL
- Build Tool: Maven
- Authentication: JSON Web Token
- Testing: JUnit + Mockito
<!-- 
## Project Structure (need to refactor)
```
src/
 ├── main/
 │   ├── java/com/yourapp/
 │   │   ├── config/        # Security & app configuration
 │   │   ├── controller/    # REST controllers (API endpoints)
 │   │   ├── service/       # Business logic
 │   │   ├── repository/    # Database access (JPA/DAO)
 │   │   ├── model/         # Entity classes
 │   │   ├── dto/           # Data Transfer Objects
 │   │   └── exception/     # Custom exceptions
 │   └── resources/
 │       ├── application.properties
```
-->

## Features
### User Management
- Create account
- Login / authentication
- Check for existing email/username
### Follow System
- Follow/unfollow users
- Retrieve followers/following lists
### API Design
- RESTful endpoints
- DTO-based responses avoiding direct entity exposure
- Proper HTTP status codes

## Setup & Installation
### 1. Clone the repo
- git clone https://github.com/yourusername/social-media-backend.git
- cd social-media-backend

### 2. Configure database <br>
Update application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
spring.datasource.username=youruser
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the application
```mvn spring-boot:run```

## API Endpoints
### Auth
```
  POST /api/v1/auth/register
  POST /api/v1/auth/authenticate
```
### Users
```
GET /users/
GET /users/{userId}
PUT /users/{userId}
DELETE /users/{userId}
```
### Posts
```
GET /posts/{postId}
GET /posts/user/{userId}
POST /posts
PUT  /posts/{postId}
DELETE  /posts{postId}
```
### Comments
```
GET  /comments/post/{postId}
POST /comments/post/{postId}
PUT  /comments/{commentId}
DELETE  /comments/{commentId}
```
### Follow
```
GET /follows/followers/{userId}
GET /follows/following/{userId}
POST /follows/{followedUserId}
DELETE /follows/{followedUserId}
```
### Feed
```
GET /feed
```

## Testing

Run tests with:

```mvn test```

- Unit tests use Mockito for mocking repositories/services
- Focus on service layer logic and edge cases

## Error Handling

The API uses consistent error responses:
```
{
  "timestamp": "2026-01-01T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Username already exists",
  "path": "/api/auth/register"
}
```
Common error codes:

- 400 – Validation errors
- 401 – Authentication failed
- 404 – Resource not found
- 500 – Internal server error


