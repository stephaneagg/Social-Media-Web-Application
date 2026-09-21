# Social Media Frontend

The frontend is a **React application built with Vite** that provides the user interface for the Social Media Web Application.

It communicates with the Spring Boot backend through the `/api/v1` API and is served in production by **Nginx**.

## Technology Stack

* **React**
* **Vite**
* **JavaScript**
* **Sass**
* **Nginx**
* **npm**

## Project Structure

```text
src/
├── components/
│   ├── comments/       # Comment-related components
│   ├── feed/           # Feed components
│   ├── forms/          # Reusable forms
│   ├── header/         # Application header
│   ├── leftBar/        # Left navigation/sidebar
│   ├── post/           # Post components
│   ├── profile/        # Profile components
│   └── rightBar/       # Right navigation/sidebar
│
├── context/
│   ├── authContext/    # Authentication state
│   └── themeContext/   # Application theme state
│
├── pages/
│   ├── followers/      # Followers page
│   ├── home/           # Home/feed page
│   ├── login/          # Login page
│   ├── post/           # Post page
│   ├── profile/        # Profile page
│   ├── register/       # Registration page
│   ├── search/         # Search page
│   └── settings/       # User settings
│
├── services/
│   ├── auth/           # Authentication API requests
│   ├── comment/        # Comment API requests
│   ├── feed/           # Feed API requests
│   ├── follow/         # Follow API requests
│   ├── post/           # Post API requests
│   ├── search/         # Search API requests
│   ├── upload/         # Upload API requests
│   └── user/           # User API requests
│
├── utils/
│   └── formatDate/     # Shared utility functions
│
├── App.jsx             # Application component and routing
├── config.js           # API configuration
├── main.jsx            # Application entry point
└── style.scss          # Global styles
```

The application separates reusable UI components, pages, application state, API services, and utility functions.

## API Communication

The frontend uses:

```text
/api/v1
```

as the base URL for backend API requests.

This is configured in:

```text
src/config.js
```

For example, authentication requests are sent to:

```text
/api/v1/auth/authenticate
```

When running the full application with Docker Compose, the browser communicates with Nginx, which proxies `/api/` requests to the Spring Boot backend.

```text
Browser
   │
   ▼
Nginx
   │
   ├── React application
   │
   └── /api/ ──────► Spring Boot Backend
```

Using a relative API path allows the frontend to work without hard-coding the backend container's hostname or port into the browser application.

## Authentication

Authentication state is managed through the authentication context in:

```text
src/context/authContext
```

After successful authentication, the backend returns a JWT that the frontend uses when making authenticated API requests.

Authenticated requests include the token using the HTTP `Authorization` header:

```http
Authorization: Bearer <token>
```

The authentication context also manages the currently authenticated user.

## Styling

The application uses **Sass** for styling.

Global styles are defined in:

```text
src/style.scss
```

Individual components and pages may also contain their own Sass styles.

## Running the Frontend Without Docker

Install the frontend dependencies from the `front-end` directory:

```bash
npm install
```

Start the Vite development server:

```bash
npm run dev
```

The development server will normally be available at:

```text
http://localhost:5173
```

Because the frontend uses `/api/v1` for API requests, local development requires the backend to be running and accessible through the configured development setup.

## Building for Production

Create a production build with:

```bash
npm run build
```

The compiled application is written to:

```text
dist/
```

The production Docker image uses a multi-stage build:

1. A Node.js image installs dependencies and builds the React application.
2. An Nginx image serves the compiled files.
3. Nginx proxies API requests to the backend container.

This keeps Node.js and the development tooling out of the final runtime image.

## Running with Docker Compose

From the project root:

```bash
docker compose up -d --build
```

The frontend is available at:

```text
http://localhost:3000
```

Check the service status with:

```bash
docker compose ps
```

Stop the application with:

```bash
docker compose down
```

The frontend container is part of the complete application stack:

```text
Frontend (React + Nginx)
        │
        ▼
Backend (Spring Boot)
        │
        ▼
Database (PostgreSQL)
```

Docker Compose manages the service network and startup dependencies between the containers.

## Development vs. Production

The frontend uses different servers depending on the environment.

### Development

Vite provides the development server:

```text
React source
     ↓
Vite
     ↓
localhost:5173
```

### Production

The React application is compiled and served by Nginx:

```text
React source
     ↓
Vite build
     ↓
Static files
     ↓
Nginx
     ↓
localhost:3000
```

Nginx also handles API proxying so that the browser can communicate with the backend through the same frontend origin.

## Error Handling

API requests are handled through the service modules under:

```text
src/services/
```

These services centralize communication with the backend and allow components and pages to interact with the API without duplicating request logic.

Authentication failures and other API errors are handled by the corresponding components and application context.

## Development Notes

The frontend is designed to run as part of the full Docker Compose application, but it can also be developed independently using Vite.

The recommended development workflow is:

```text
Edit React/Sass code
        ↓
Run Vite development server
        ↓
Test application in browser
        ↓
Build production bundle
        ↓
Test through Docker Compose
```

The production container does not require Node.js at runtime because only the compiled static files are copied into the Nginx image.
