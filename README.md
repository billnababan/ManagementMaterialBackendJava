# Material Request Management System - Backend

## Introduction

The backend of the Material Request Management System is built using Java Spring Boot. It provides the necessary APIs for managing material requests in the oil and gas and renewable energy sectors.

## Features

- **User Authentication**: Secure login using JWT (JSON Web Token).
- **Role-Based Access Control (RBAC)**: Different functionalities for Production and Warehouse departments.
- **CRUD Operations**: Create, Read, Update, and Delete material requests.

## Technologies Used

- **Backend**: Java Spring Boot 3
- **Database**: PostgreSQL
- **Authentication**: JWT (JSON Web Token)

## Installation

To set up the backend on your local machine, follow these steps:

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- PostgreSQL database
- Git

### Step 1: Clone the Repository

Clone the repository to your local machine using the following command:

### Step 2: Set Up the Backend

1. Navigate to the backend directory:

   ```bash
   cd MaterialManagement
   ```

2. Configure the database connection in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/material_request_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   ```

3. Run the Spring Boot application:

   ```bash
   ./mvnw spring-boot:run
   ```

## User Roles

1. **Production Department**:

   - Create, update, or delete material requests.
   - Check the status of material requests (pending approval, approved, or rejected).

2. **Warehouse Department**:
   - View incoming material requests.
   - Approve or reject material requests, providing reasons if necessary.
   - Modify the quantity requested by the Production Department.

## API Documentation

### User Management

- **Create User**

  - **Endpoint**: `POST /api/users`
  - **Request Body**:
    ```json
    {
      "username": "new_user",
      "password": "password123",
      "department": "PRODUCTION" // or "WAREHOUSE"
    }
    ```

- **Get All Users**
  - **Endpoint**: `GET /api/users`
  - **Request Header**: `Authorization: Bearer <token>`

### Authentication

- **Login**

  - **Endpoint**: `POST /api/auth/login`
  - **Request Body**:
    ```json
    {
      "username": "your_username",
      "password": "your_password"
    }
    ```

- **Logout**
  - **Endpoint**: `POST /api/auth/logout`
  - **Request Header**: `Authorization: Bearer <token>`

### Material Requests

- **Create Material Request**

  - **Endpoint**: `POST /api/material-requests`
  - **Request Body**:
    ```json
    {
      "items": [
        {
          "materialName": "Besi",
          "requestedQuantity": 10,
          "unit": "KG",
          "usageDescription": "Untuk produksi mesin A"
        }
      ]
    }
    ```

- **Get My Requests**

  - **Endpoint**: `GET /api/material-requests/my-requests`
  - **Request Header**: `Authorization: Bearer <token>`

- **Get Pending Requests**

  - **Endpoint**: `GET /api/material-requests/pending`
  - **Request Header**: `Authorization: Bearer <token>`

- **Approve Material Request**

  - **Endpoint**: `PUT /api/material-requests/{id}/approve`
  - **Request Body**:
    ```json
    {
      "approvedQuantity": 5
    }
    ```

- **Reject Material Request**
  - **Endpoint**: `PUT /api/material-requests/{id}/reject`
  - **Request Body**:
    ```json
    {
      "reason": "Not needed anymore"
    }
    ```

## Testing with Postman

### Step-by-Step API Testing

1. **Create User**:

   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/users`
   - **Body**:
     ```json
     {
       "username": "new_user",
       "password": "password123",
       "department": "PRODUCTION"
     }
     ```

2. **Login**:

   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/auth/login`
   - **Body**:
     ```json
     {
       "username": "your_username",
       "password": "your_password"
     }
     ```

3. **Create Material Request**:

   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/material-requests`
   - **Header**: `Authorization: Bearer <token>`
   - **Body**:
     ```json
     {
       "items": [
         {
           "materialName": "Besi",
           "requestedQuantity": 10,
           "unit": "KG",
           "usageDescription": "Untuk produksi mesin A"
         }
       ]
     }
     ```

4. **Get My Requests**:

   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/material-requests/my-requests`
   - **Header**: `Authorization: Bearer <token>`

5. **Approve Material Request**:

   - **Method**: `PUT`
   - **URL**: `http://localhost:8080/api/material-requests/{id}/approve`
   - **Header**: `Authorization: Bearer <token>`
   - **Body**:
     ```json
     {
       "approvedQuantity": 5
     }
     ```

6. **Reject Material Request**:

   - **Method**: `PUT`
   - **URL**: `http://localhost:8080/api/material-requests/{id}/reject`
   - **Header**: `Authorization: Bearer <token>`
   - **Body**:
     ```json
     {
       "reason": "Not needed anymore"
     }
     ```

## Notes

I am very open to feedback and suggestions from everyone. Your insights will help improve this project and make it more effective for users. Thank you for your support!
