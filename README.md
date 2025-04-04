# Spring Boot Microservices Project

## API Endpoints

### 🏷 User Operations
| Method | Endpoint         | Description                 |
|--------|-----------------|------------------------------|
| `POST` | `/register`      | Register new user           |
| `POST` | `/auth/login`    | Authenticate user (JWT)     |
| `GET`  | `/users/{id}`    | Get user by ID              |
| `GET`  | `/users`         | Get all users               |
| `PUT`  | `/users/{id}`    | Update user details         |
| `DELETE` | `/users/{id}`  | Delete user                 |

### 🗄 Journal Operations
| Method  | Endpoint                          | Description                  |
|---------|----------------------------------|-------------------------------|
| `GET`   | `/journal/all-audit-logs`       | Get all audit logs             |
| `GET`   | `/journal/audit-logs/{id}`      | Get logs for a user by ID      |
| `GET`   | `/journal/audit-logs/?email=`   | Get logs for a user by email   |

---

## 🗃 Database Tables

### **1️⃣ Users Table**
Stores user details.

| Column  | Type         | Description          |
|---------|--------------|----------------------|
| `id`    | `Long`       | Unique user ID       |
| `name`  | `String`     | User's full name     |
| `email` | `String`     | User's email         |
| `password` | `String`  | Hashed password      |
| `role`  | `String`     | User role            |

---

### **2️⃣ Audit Table**
Stores audit logs for user activities.

| Column      | Type              | Description                             |
|-------------|-------------------|-----------------------------------------|
| `id`        | `Long`            | Unique log ID                           |
| `action`    | `String`          | Action performed (e.g., LOGIN, DELETE)  |
| `email`     | `String`          | Email of user performing action         |
| `timestamp` | `LocalDateTime`   | Time of the action                      |
| `details`   | `String`          | Additional information                  |

