# Authentication Flow

This backend uses Spring Security with JWT-based stateless authentication.

## Login

`POST /api/v1/auth/authenticate` is a public endpoint configured in `SecurityConfig`.

The login flow is:

1. `AuthenticationController.authenticate(...)` receives the request body.
2. `AuthenticationService.authenticate(...)` calls `authenticationManager.authenticate(...)`.
3. Spring Security delegates to the configured `DaoAuthenticationProvider`.
4. The provider calls `UserDetailsService` from `ApplicationConfig`.
5. `UserDetailsService` loads the user with `userRepository.findByEmailOrUsername(login, login)`.
6. Spring compares the submitted password with the stored BCrypt hash.
7. If authentication succeeds, `AuthenticationService` loads the user and generates a JWT.

The login request accepts either email or username through the `login` field.

Example request body:

```json
{
  "login": "user@example.com",
  "password": "secret"
}
```

or

```json
{
  "login": "someUsername",
  "password": "secret"
}
```

## JWT Contents

After a successful login, the backend returns a JWT.

- The token includes `userId` as a claim.
- The JWT subject is the user's `username`.

Keeping `username` as the subject means token validation continues to use the username-based identity already implemented in `JwtService` and `JwtAuthenticationFilter`.

## Authenticated Requests

Protected endpoints require:

```http
Authorization: Bearer <token>
```

For authenticated requests, the flow is:

1. `JwtAuthenticationFilter` reads the `Authorization` header.
2. The filter extracts the JWT and reads its subject.
3. The subject is treated as the username.
4. `UserDetailsService` loads the user from the database.
5. `JwtService` validates the token.
6. If valid, Spring stores the authenticated user in the `SecurityContext`.
7. The request proceeds to the controller.

## Important Detail

Login accepts either email or username, but the JWT subject remains `username`.

That is intentional:

- login is flexible for the user
- token validation stays consistent internally
- the app uses one canonical subject value inside JWTs
