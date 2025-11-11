# Rental API - Student Project (OpenClassrooms)

![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen?logo=springboot)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-Completed-success)

This project is a RESTful API built with **Spring Boot 3.5.7** and **Java 21**, developed as part of a student assignment at **OpenClassrooms**.

The goal was to replace a simulated backend (previously powered by Mockoon) with a fully functional and secure API capable of handling user authentication (tenants and owners), rental listings, and data management.

The Angular frontend was already in place using mock data. This API now powers it with real data, following clean architecture principles, secured with **OAuth2** and **JWT**, backed by a **MySQL** database, and fully documented using **Swagger**.

✅ The project is now **completed**, tested, documented, and ready for use.

## 🧱 Technologies

- Java 21
- Spring Boot 3.5.7
- MySQL

## 🛠️ Tools

- **Visual Studio Code** – Main IDE used for coding, debugging, and managing the project structure
- **Postman** – Tool for manually testing API endpoints, inspecting HTTP requests/responses, and managing collections
- **HeidiSQL** – GUI tool for managing and querying the MySQL database
- **Mockoon** – Used initially to simulate external APIs and test routing before backend implementation
- **Swagger UI** – Automatically generated interface for exploring and testing API endpoints directly from the browser

## 📦 Dependencies

- **Spring Web** : Enables building RESTful web services and controllers
- **Spring Data JPA** : Simplifies database access and ORM with Hibernate
- **Spring Security** : Provides authentication and authorization mechanisms
- **OAuth2 Resource Server** : Supports JWT-based OAuth2 authentication
- **Lombok** : Reduces boilerplate code (getters, setters, constructors, etc.)
- **MySQL Driver** : Connects the application to a MySQL database
- **Validation** : Adds bean validation via annotations (e.g., @NotNull, @Email)
- **Swagger** : Generates interactive API documentation
- **MapStruct** : Generates interactive API documentation


## 🔧 Prerequisites for this project  : Rental API
Before getting started, make sure the following tools are installed on your machine:

- **Java Jdk 21**

    ➤ [Download Jdk 21](https://adoptium.net/fr/temurin/releases?version=21)

- **MySql 8 (Oracle)**

    ➤ [Download MySql 8](https://dev.mysql.com/downloads/installer/)

## 🚀 Project Setup : Rental Api (this repository)
- Clone the repository from the following address :
```bash
> git clone https://github.com/ZeckLab/OC-P3-rental-api.git
> cd OC-P3-rental-api
```

- Open the directory in Visual Studio Code

## ⚙️ Environnement Configuration (.env file)
Before running the application, you must configure your environment variables.
To do so:
- Duplicate the .env.example file and rename it to .env
- Fill in the values according to your local setup, following the comments provided in the file

```env
# Database configuration
DB_URL=jdbc:mysql://localhost:3306/rental_db
DB_USERNAME=your_user
DB_PASSWORD=your_password

# JWT configuration
JWT_SECRET=your_very_long_random_secret_key_here
JWT_EXPIRATION_MS=86400000

# Server port
SERVER_PORT=3001

# Directory to store uploaded pictures
UPLOAD_DIR=uploads/pictures

# Enable CORS and specify allowed origins
CORS_ALLOWED_ORIGINS=http://localhost:4200
```

⚠️ Important: Do not remove the comments from .env.example. They guide you through each variable’s purpose and expected format.

## 🖼️ Sample Image for Testing

The project includes a sample image that can be used to test image upload functionality.

- **Location**: `src/main/resources/Online-House-Rental-Sites.jpg`
- **Usage in Swagger**: Use this file when testing the image upload endpoint.
- **Usage in the frontend**: Use this file when testing the creation of a rental

### ⚠️ Upload Directory Configuration

By default, the system will automatically create the upload folder at the root of the project.

You can choose any folder name you prefer. Just make sure to declare it in your `.env` file:

```env
# Examples
UPLOAD_DIR=uploads/pictures
UPLOAD_DIR=pouet
```

If the folder does not exist, image uploads will fail.

## 🗄️ MySQL Setup Instructions

Before running the API, make sure your MySQL database is properly installed and configured.

### Install MySQL

Download and install MySQL 8 from the official website:
➡️ [Download MySQL](https://dev.mysql.com/downloads/installer/)

During installation, note your root password.

### Create the database with MySql Command Line Client

You can either create the database manually or use the provided SQL script.

```sql
CREATE DATABASE rental_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Create a dedicated user

```sql
CREATE USER 'your_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON rental_db.* TO 'your_user'@'localhost';
FLUSH PRIVILEGES;

USE rental_db;
```

Replace 'your_user' and 'your_password' with your own credentials and put them in .env

### Populate the database
Instead of running the full script, copy and execute each CREATE TABLE block manually.
- Open the file:
    src/main/resources/script.sql
- Copy each SQL block (one table at a time) and paste it into your MySQL client

This manual approach ensures compatibility and gives you full control over the setup.


## 🏗️ Build the project :
```bash
> ./mvnw clean install
```

## ▶️ Run the application : Rental Api
```bash
> ./mvnw spring-boot:run
```

## 🔐 Authentication

This API uses **OAuth2** and **JWT** for secure access.

- Protected endpoints require a valid JWT token in the `Authorization` header:

```
Authorization: Bearer <your_token>
```

## 🧭 How to explore the API

Once the backend is running, you have **two alternatives** to interact with the API:

1. **Swagger (recommended)** – A built-in interface to test endpoints directly in the browser
2. **Angular frontend** – A separate client application provided by OpenClassrooms

Choose the method that best suits your workflow. Both are explained below.

## 📘 API Documentation (Swagger)

Access Swagger at:

```
http://localhost:3001/swagger-ui/index.html
```

To access protected routes via Swagger:
- Use the /login or /register endpoint directly in Swagger by clicking Try it out and submitting valid credentials.
- Copy the JWT token from the response.
- Click the Authorize button at the top right of the Swagger UI.
- Paste the token
- Once authorized, you’ll be able to access all secured endpoints directly from Swagger.

## 🖥️ Optional Frontend (OpenClassrooms Angular App)

If you prefer a graphical interface instead of Swagger, use the Angular frontend:

### Prerequisites

- **Node.js**
    ➤ [Download Node](https://nodejs.org/en/download)

- **Angular**
    Install the Angular CLI globally:

    > npm install -g @angular/cli

To check the version :

    > node -v
    > ng version

### Setup Instructions

- Clone the repository from the following address :
```bash
> git clone https://github.com/OpenClassrooms-Student-Center/P3-Full-Stack-portail-locataire
> cd P3-Full-Stack-portail-locataire
```

- Open the directory in Visual Studio Code

- Install your node_modules before starting :
```bash
> npm install
```

### Run the application

Start the development server :
```bash
> npm start
```

Access the app at:

```
http://localhost:4200
```

Make sure the backend is running on port **3001** for proper communication.
