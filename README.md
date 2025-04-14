# FocusBuddy 🧠✨

**FocusBuddy** is a full-stack productivity application designed to help users stay organized, manage tasks efficiently, and receive timely reminders. Built with Spring Boot on the backend and (optional) React on the frontend, FocusBuddy empowers users to stay on track with their daily goals.

---

## 🚀 Features

### ✅ Authentication & User Management
- **JWT-based login system** with username/email + password.
- **Secure user registration** with validation and email notification.
- **User profile access** (`/api/auth/me`).
- **Password change** with success email.

### 📅 To-Do Management
- Create, read, update, and delete your daily tasks.
- Filter by status: **Pending** or **Completed**.
- RESTful endpoints available for seamless frontend integration.

### ⏰ Reminders System
- Set reminders with a message and schedule.
- Get real-time **browser notifications** using **Server-Sent Events (SSE)**.
- View reminders per user and test reminder functionality with a built-in endpoint.

---

## 🧩 API Endpoints

### Auth Controller (`/api/auth`)
| Method | Endpoint             | Description                          |
|--------|----------------------|--------------------------------------|
| POST   | `/register`          | Register a new user                  |
| POST   | `/login`             | Login and receive JWT token          |
| GET    | `/me`                | Get current user profile from token  |
| POST   | `/change-password`   | Change password for logged-in user   |

### ToDo Controller (`/api/todos`)
| Method | Endpoint             | Description                          |
|--------|----------------------|--------------------------------------|
| GET    | `/`                  | Fetch all todos                      |
| GET    | `/{id}`              | Get specific todo by ID              |
| POST   | `/add`               | Create new todo                      |
| PUT    | `/update`            | Update an existing todo              |
| DELETE | `/delete/{id}`       | Delete a todo                        |
| GET    | `/completed`         | Fetch completed todos                |
| GET    | `/pending`           | Fetch pending todos                  |

### Reminder Controller (`/api/reminders`)
| Method | Endpoint                   | Description                                      |
|--------|----------------------------|--------------------------------------------------|
| GET    | `/`                        | Get all reminders                               |
| POST   | `/add`                     | Add a new reminder                              |
| GET    | `/user/{userId}`           | Get reminders for a specific user               |
| PUT    | `/{id}`                    | Update a specific reminder                      |
| DELETE | `/delete/{id}`             | Delete reminder by ID                           |
| GET    | `/stream/{userId}`         | Stream real-time reminders (SSE)                |
| GET    | `/test/send/{userId}`      | Trigger test reminder for user (SSE test)       |

---

## 🛠️ Tech Stack

- **Backend**: Java, Spring Boot, Spring Web, Spring Security, JWT, H2 Database
- **Frontend**: (Optional) React (planned), REST APIs ready for integration
- **Email Service**: JavaMailSender for welcome and password change emails
- **Notifications**: Server-Sent Events (SSE)

---

## 📂 Project Structure (Backend)

```
com.focusbuddy
│
├── controller       # REST controllers
├── dto              # Data Transfer Objects
├── model            # Entity classes
├── repository       # JPA Repositories
├── service          # Business logic layer
├── config           # JWT and WebSecurity configs
└── utils            # Utility classes (e.g. EmailService)
```

---

## 🔐 Authentication Flow
1. **Register** a user with `/api/auth/register`.
2. On **successful login** via `/api/auth/login`, a **JWT token** is issued.
3. Use `Authorization: Bearer <token>` header for protected endpoints.
4. Access your profile with `/api/auth/me`.
5. Change your password via `/api/auth/change-password`.

---

## 📢 Real-Time Notifications
- When reminders are due, FocusBuddy pushes real-time messages to users' browsers using **SSE**.
- To test, visit `/api/reminders/test/send/{userId}` and ensure the frontend is subscribed to `/stream/{userId}`.

---

## ✅ Future Enhancements
- React-based frontend with polished UI/UX.
- Push Notifications with service workers.
- Google Calendar integration.
- Multi-device sync.

---

## 🙌 Author
**Shiva Kumar**  
🔗 [GitHub](https://github.com/ShivaCherukuCodes)  
📩 Let's build something amazing!

---

## 📄 License
This project is open-source and available under the MIT License.

---

> Stay productive. Stay focused. **FocusBuddy's got your back!** 💪

