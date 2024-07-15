# Project Management Web Application

## Overview

This project aims to deploy a web application for project management, enabling users to create and manage projects efficiently. It provides features for creating projects, inviting users to join, managing issues, project-based communication through chats, and subscription plans.

## Key Features

- **Project Creation**: Users can create projects and invite team members to collaborate.
- **Issue Management**: Track and manage issues within each project.
- **Real-Time Chat**: Facilitate project-based communication through integrated chat functionality.
- **Subscription Plans**: Offer various plans (free, monthly, annual) with differing project limits. Payment integration with Razorpay for plan upgrades.

## Technologies Used

- **Frontend**: React, Bootstrap
- **Backend**: Spring Boot
- **Database**: MySQL
- **Security**: Spring Security, JWT
- **Payment Integration**: Razorpay

## Highlights

### Project Setup and Initializer
- Set up the project using Spring Initializer.
- Configured Spring Security for secure access.

### JWT Token Authentication
- Added JWT dependencies for secure communication.
- Configured security filters, web security, and CORS for backend access.

### Entity and Service Implementation
- Created entities for users, projects, issues, comments, and chats with appropriate relationships and annotations.
- Developed services for managing users, projects, issues, and chats.

### Subscription and Payment Integration
- Implemented subscription plans with Razorpay payment gateway.
- Created controllers for managing subscriptions and processing payments.

### Real-Time Chat
- Integrated real-time chat functionality for project-based communication.
- Developed a chat service with message handling and storage.

### Issue and Task Management
- Implemented CRUD operations for issues and tasks.
- Enabled task assignment, status updates, and comment functionalities.

### API Development and Testing
- Created various APIs for signup, login, project management, issue tracking, comments, and subscriptions.
- Planned API testing using Postman for end-to-end verification.

## Getting Started

### Prerequisites
- Java 8 or above
- MySQL

### Installation
1. Clone the repository.
   ```bash
   git clone https://github.com/your-username/project-management-app.git
   ```
2. Navigate to the backend directory and build the Spring Boot application.
   ```bash
   cd backend
   mvn clean install
   ```
3. Set up the MySQL database and update `application.properties` with your database credentials.
4. Run the Spring Boot application.
   ```bash
   mvn spring-boot:run
   ```
5. Navigate to the frontend directory and install dependencies.
   ```bash
   cd ../frontend
   npm install
   ```
6. Start the React application.
   ```bash
   npm start
   ```

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License.

## Acknowledgements
- [Spring Boot](https://spring.io/projects/spring-boot)
- [React](https://reactjs.org/)
- [Bootstrap](https://getbootstrap.com/)
- [Razorpay](https://razorpay.com/)

For more details and to follow the project's progress, watch the [video series](#) and check the code on [GitHub](#).
