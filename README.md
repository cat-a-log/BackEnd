# 📦 Cat-a-log Platform - Backend

This repository houses the backend infrastructure for a personalized box organization application. The backend is responsible for managing and storing data related to user-created boxes and their contents. It provides the necessary APIs to support the application's core functionalities.

## Backend Functionalities

This backend provides the following functionalities to support the visual box organization application:

* **User Management:**
    * User registration 📝 and authentication ✅.
    * Secure storage of user credentials 🔒.
* **Box Management:**
    * Creation of new boxes with user-defined names ➕📦.
    * Retrieval of box details 🔍📦.
    * Updating existing box information ✏️📦.
    * Deletion of boxes 🗑️📦.
* **Item Management within Boxes:**
    * Adding items to specific boxes, including name, quantity 🔢, and image uploads 📤🖼️.
    * Viewing the contents of a box 👀📦.
    * Editing item details within a box ✏️.
    * Deleting items from a box 🗑️.
<!-- * **QR Code Generation:**
    * Automatic generation of unique QR codes 🏷️ for each created box.
    * Storage and association of QR codes with their corresponding boxes 🔗📦🏷️. -->
* **API Endpoints:**
    * Provides a set of API endpoints 🌐 for communication with the frontend application.
    * Supports standard HTTP methods (GET ➡️, POST 📤, PUT 💾, DELETE 🗑️) for data manipulation.
* **Scalability and Adaptability:**
    * Designed to handle varying levels of data and user load 📈, suitable for both individual home organization 🏠 and small business inventory management 🏢.
* **Security:**
    * Implements security best practices 🛡️ to protect user data and prevent unauthorized access 🚫.


## 🛠️ Technologies and Tools

- **Programming Language:** Java 21
- **Backend Framework:** Spring Boot 3.4.4
- **Spring Data:** JPA
- **Spring Security:** JWT
- **Database:** PostgreSQL / H2
- **IDE:** Visual Studio Code
- **Project Management:** Trello
- **Version Control:** Git - GitHub
- **API Client:** Postman/Swagger

## ⚙️ Environment Setup and Running the Application

- pgSQL (docker container, port: **54321**)
- pgAdmin (docker container) available on <http://localhost:8080>
- Spring boot

## Set up

1. Make .env and fill in values to your liking:

```sh
cp .env.example .env
```

2. Run docker container

```sh
docker compose up -d
```

3. Build project with maven

```sh
mvn clean install -DskipTests

```

4. Run spring-boot and visit <http://localhost:8080>

```sh
mvn spring-boot:run
```

## Swagger

API documentation is provided through swagger and can be found at <http://localhost:8080/swagger-ui/>

## 👥 Author

* **Mariuxi Olaya:**
    * GitHub: [catmaluci](https://github.com/catmaluci/)
    * LinkedIn: [Mariuxi Olaya](https://www.linkedin.com/in/molaya)
