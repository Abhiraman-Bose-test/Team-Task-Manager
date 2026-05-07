# Team Task Manager

A full-stack Team Task Management Web Application where users can create projects, add team members, assign tasks, track progress, and view project dashboards.

The application is built using a microservice-based backend architecture with a React TypeScript frontend.

---


## GitHub Repositories

| Module | Repository |
|---|---|
| Frontend | [Frontend Task Manager](https://github.com/Abhiraman-Bose-test/Frontend-Task-Manager) |
| User Auth Service | [User Auth Service](https://github.com/Abhiraman-Bose-test/User-AuthService) |
| Project Task Service | [Team Task Manager](https://github.com/Abhiraman-Bose-test/Team-Task-Manager) |

---

## Features

### Authentication

- User signup with name, email, and password
- Secure login using JWT
- Password encryption using BCrypt
- JWT-based authentication between frontend and backend services

### Project Management

- Create projects
- Project creator automatically becomes Admin
- Admin can add members to a project
- Admin can remove project members
- Users can view projects they are part of

### Task Management

- Admin can create tasks inside a project
- Admin can assign tasks to project members
- Tasks include title, description, due date, priority, and status
- Members can view assigned tasks
- Members can update the status of their assigned tasks

### Dashboard

- Total tasks
- Tasks by status
- Tasks per user
- Overdue tasks

### Role-Based Access

#### Admin

- Manage project members
- Create and assign tasks
- View all project tasks
- Update any task status
- Delete tasks
- View full project dashboard

#### Member

- View assigned projects
- View assigned tasks
- Update only assigned task status
- View task information relevant to their assignment

---

## Tech Stack

### Frontend

- React
- TypeScript
- Vite
- Axios
- React Router DOM

### Backend

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL


---

## Architecture

```text
Team Task Manager
│
├── UserAuthService
│   ├── Signup
│   ├── Login
│   ├── Password encryption
│   ├── JWT generation
│   └── User identity management
│
├── ProjectTaskService
│   ├── Project management
│   ├── Project members
│   ├── Task management
│   ├── Dashboard
│   └── Project-level role-based access
│
└── Frontend
    ├── Signup/Login
    ├── Projects
    ├── Project details
    ├── My tasks
    └── Dashboard
```

---

## Microservice Design

This project uses two backend services:

### UserAuthService

Responsible for:

- User registration
- User login
- Password encryption
- JWT token generation
- User identity management

### ProjectTaskService

Responsible for:

- Project creation
- Project member management
- Role-based access control
- Task creation and assignment
- Task status updates
- Dashboard statistics

---

## Database Design

The application uses separate databases for different services.

### UserAuthService Database

```text
users
- id
- name
- email
- password
- created_at
- updated_at
```

### ProjectTaskService Database

```text
projects
- id
- name
- description
- created_by_user_id
- created_at
- updated_at
```

```text
project_members
- id
- project_id
- user_id
- role
- created_at
- updated_at
```

```text
tasks
- id
- title
- description
- due_date
- priority
- status
- project_id
- assigned_to_user_id
- created_by_user_id
- created_at
- updated_at
```

---

## Important Notes

- The creator of a project automatically becomes `ADMIN`.
- Project roles are stored in the `project_members` table.
- User identity is managed by `UserAuthService`.
- Project and task permissions are managed by `ProjectTaskService`.
- JWT secret must be identical in both backend services.
- Vite environment variables must start with `VITE_`.

---

## Author

**Abhiraman Bose**
