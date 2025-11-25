# Task Manager API
A Spring Boot 4 / Java 17 REST API for managing tasks, their dependencies, and user assignments with JWT-based authentication and role-aware authorization.

## Features
- JWT login and stateless security; all endpoints (except `/auth/login`) require a Bearer token.
- Role-aware access: role `1` is treated as admin; other roles are standard users.
- Task CRUD operations with filtering by assignee, status, and due date.
- Task dependencies with validation that all dependencies are completed before a task can be marked done.
- User management for admins, including creating users with encoded passwords.

## Tech Stack
- Spring Boot 4.0 (web, security, data JPA, OAuth2 resource server)
- MySQL with Spring Data JPA
- JWT (jjwt 0.11.5) for auth tokens
- Lombok for boilerplate reduction

## Configuration
- Database settings live in `src/main/resources/application.properties` (`spring.datasource.url/username/password`). Default URL points to a local MySQL database named `tasks`.
- The JWT signing key is currently defined in `src/main/java/com/softxpert/taskManager/Services/JwtService.java` (`SECRET`). Change it before any production use and keep it out of version control.
- Default port is Spring Boot’s standard `8080`; override with `server.port` if needed.

## Running Locally
1) Ensure prerequisites: Java 17, MySQL running with a database (default `tasks`), and optionally Maven if you prefer not to use the wrapper.
2) Update `application.properties` with your DB credentials.
3) Build: `./mvnw clean package` (or `mvnw.cmd` on Windows).
4) Run: `./mvnw spring-boot:run`.
5) Test: `./mvnw test`.

## API Overview
All endpoints expect `Authorization: Bearer <token>` except `/auth/login`.

| Method | Path | Description | Access |
| --- | --- | --- | --- |
| POST | `/auth/login` | Authenticate with email/password; returns JWT and role. | Public |
| POST | `/users/add` | Create a new user. Body: `RegisterRequest`. | Admin (ROLE\_1) |
| POST | `/tasks/add` | Create a task. Body: `RegisterTask`. | Admin (ROLE\_1) |
| PUT | `/tasks/updateAssignee/{taskId}/{assignee}` | Reassign a task. | Admin (ROLE\_1) |
| GET | `/tasks` | List tasks with optional filters: `assignee`, `status`, `dueFrom`, `dueTo` (ISO date). | Admin (ROLE\_1) |
| GET | `/tasks/{id}` | List tasks for a specific assignee. Non-admins can only access their own ID. | Admin or self |
| PUT | `/tasks/updateTaskStatus/{taskId}/{status}` | Update task status; only admin or the assigned user. If `status=1`, all dependencies must already be completed. | Admin or assignee |
| PUT | `/tasks/updateTask/{taskId}` | Partial task update. Body: `UpdateTask`. | Admin (ROLE\_1) |
| POST | `/taskDependency/add?taskId=&dependencyId=` | Add a dependency between tasks. | Admin (ROLE\_1) |
| PUT | `/taskDependency/edit?taskId=&oldDependencyId=&newDependencyId=` | Replace a task dependency. | Admin (ROLE\_1) |

## DTO Notes
- `RegisterTask` includes: `title`, `description`, `dueDate`, `assignee`, `statusId`.
- `UpdateTask` supports partial updates (non-null fields are applied).
- `RegisterRequest` includes user `firstName`, `lastName`, `email`, `password`, `roleId`.
- `LoginResponse` returns `token` and `role`.

## Behavior Highlights
- Dependency enforcement: when updating a task’s status to `1` (completed), the service verifies all dependent tasks are already completed; otherwise it rejects the request.
- Authorization: method-level `@PreAuthorize` guards admin-only endpoints; task status changes are limited to admins or the task’s assignee.

## Useful Paths
- Main application entry: `src/main/java/com/softxpert/taskManager/TaskManagerApplication.java`
- Security setup: `src/main/java/com/softxpert/taskManager/Security/SecurityConfig.java`
- JWT handling: `src/main/java/com/softxpert/taskManager/Services/JwtService.java`
- Controllers: `src/main/java/com/softxpert/taskManager/Controllers/`
