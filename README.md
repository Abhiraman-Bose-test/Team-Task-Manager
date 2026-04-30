# Team-Task-Manager
A full-stack Team Task Management Web Application where users can create projects, add team members, assign tasks, track progress, and view project dashboards.

The application is built using a microservice-based backend architecture with a React TypeScript frontend.


## Live URLs
https://frontend-task-manager-production-b004.up.railway.app/login


Frontend: https://github.com/Abhiraman-Bose-test/Frontend-Task-Manager
User Auth Service: https://github.com/Abhiraman-Bose-test/User-AuthService
Project Task Service: https://github.com/Abhiraman-Bose-test/Team-Task-Manager

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

Admin:

- Manage project members
- Create and assign tasks
- View all project tasks
- Update any task status
- Delete tasks
- View full project dashboard

Member:

- View assigned projects
- View assigned tasks
- Update only assigned task status
- View task information relevant to their assignment

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
## Notes

- The creator of a project automatically becomes `ADMIN`.
- Project roles are stored in the `project_members` table.
- User identity is managed by `UserAuthService`.
- Project and task permissions are managed by `ProjectTaskService`.
- JWT secret must be identical in both backend services.
- Vite environment variables must start with `VITE_`.
- Railway backend services can both run internally on port `8080` because they are separate services.

---

## Author

```text
Abhiraman Bose
