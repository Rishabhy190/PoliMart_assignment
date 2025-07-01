# Polimart: Media Upload and Management Platform

A full-stack web application for user registration, authentication, and secure media (file) upload, listing, and download. Built with Java Spring Boot (backend) and Angular (frontend).

---

## Features

- **User Registration & Login** (JWT-based authentication)
- **Media Upload** (with description, supports large files)
- **Media Listing** (view all your uploaded files, filter by type)
- **Media Download** (download your files securely)
- **Media Delete** (remove your files)
- **Responsive UI** (Bootstrap 5)
- **In-memory H2 Database** (for easy setup and testing)

---

## Quick Start

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd Polimart
```

---

### 2. Backend Setup (Spring Boot)

#### Requirements

- Java 21+
- Maven (or use the provided Maven Wrapper)

#### Run the Backend

```bash
cd backend-Springboot
./mvnw spring-boot:run
```
or on Windows:
```cmd
cd backend-Springboot
mvnw.cmd spring-boot:run
```

- The backend will start at: **http://localhost:8081**
- H2 Console: **http://localhost:8081/h2-console** (JDBC URL: `jdbc:h2:mem:testdb`, user: `sa`, password: _empty_)

#### Default User Credentials

- **Email:** `test@example.com`
- **Password:** `test123`

---

### 3. Frontend Setup (Angular)

#### Requirements

- Node.js (v18+ recommended)
- npm

#### Install Dependencies

```bash
cd ../frontend-angular
npm install
```

#### Run the Frontend

```bash
npm start
```

- The frontend will start at: **http://localhost:4200**

---

## Usage

1. **Register** a new user or use the default credentials above.
2. **Login** to receive a JWT token (handled automatically by the frontend).
3. **Upload** files (images, PDFs, etc.) with an optional description.
4. **View** your uploaded files, download, or delete them.

---

## API Endpoints

### Auth

- `POST /login` — Login (returns JWT)
- `POST /register` — Register a new user

### Media

- `POST /api/media/upload` — Upload a file
- `GET /api/media/list` — List all your files
- `GET /api/media/list/{fileType}` — List your files by type
- `GET /api/media/download/{mediaId}` — Download a file
- `DELETE /api/media/{mediaId}` — Delete a file

---

## Configuration

- **Backend Port:** `8081` (see `backend-Springboot/src/main/resources/application.properties`)
- **Frontend Port:** `4200` (default Angular)
- **File Upload Directory:** `backend-Springboot/uploads`
- **JWT Secret:** Set in `application.properties` (`jwt.secret`)
- **Database:** In-memory H2 (no setup needed)

---

## Sample Files

- Example files are in `backend-Springboot/uploads/` for testing.

---

## Tech Stack

- **Backend:** Java 21, Spring Boot, Spring Security, Spring Data JPA, H2, JWT, Lombok
- **Frontend:** Angular 18, Bootstrap 5, RxJS

---

## Notes

- For production, change the JWT secret and switch to a persistent database.
- The frontend expects the backend at `http://localhost:8081` (change in `frontend-angular/src/app/services/*.ts` if needed). 