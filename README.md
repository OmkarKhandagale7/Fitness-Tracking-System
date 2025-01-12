# Fitness Tracking API

This is a backend API for a fitness tracking system that allows users to track their activities, manage workout plans, and monitor progress. The API is built with Spring Boot and offers various endpoints to manage user data, activities, and workout plans.

## Table of Contents
- [Features](#features)
- [Setup Instructions](#setup-instructions)
- [API Usage Guide](#api-usage-guide)
  - [Authentication](#authentication)
  - [Endpoints](#endpoints)
    - [User Endpoints](#user-endpoints)
    - [Activity Log Endpoints](#activity-log-endpoints)
    - [Workout Plan Endpoints](#workout-plan-endpoints)
- [Technologies Used](#technologies-used)
- [Test Coverage](#test-coverage)
- [Contact Information](#contact)

# Features
- Fully Functional Spring Boot Application
- User Registration and Roll based access to User
- Create, Update, Delete, and Fetch Users
- Create, Update, Delete, and Fetch Acticity Logs
- Create, Update, Delete, and Fetch Workout Plans
- Store all data into Mysql Database
- Done code coverage of 82.70% for test cases
- API Documentation available via Swagger UI

## Setup Instructions

Follow these steps to set up the Fitness Tracking API locally:

### Prerequisites

- Java 17 or above
- Maven for dependency management
- MySQL database for storing data
- IDE like IntelliJ IDEA or Eclips

### 1. Clone the Repository

All code is available inside master branch

    -git clone https://github.com/OmkarKhandagale7/Fitness-Tracking-System.git


### 2. Setup Mysql Database

    Create Mysql database with name fitness

### 3. Build and Run the Application
       Run the application with the help of any prefereable IDE (Eclips etc..) 
    
## Api Usage Guide 
### Authentication 
        http://localhost:8080/api/users  this can be access by Admin only User cannot access this endpoint. 
        Credentials for Admin - 
                    username - admin
                    password - admin123
        hhtp://localhost:8080/workout_plans and hhtp://localhost:8080/activity_logs can be access by both the users 
        Credentials for User 
                    username - user 
                    password - user123
        
### Endpoints

###1 User Endpoints
    
    GET /api/users
    Fetch all users.
    
    GET /api/users/{id}
    Fetch a user by their ID.
    
    POST /api/users
    Create a new user.
    
    PUT /api/users/{id}
    Update an existing user.

    DELETE /api/users/{id}
    Delete a user by their ID.

###2 Activity Log Endpoints
    
    GET /api/activity-logs
    Fetch all activity logs.
    
    GET /api/activity-logs/{id}
    Fetch an activity log by its ID.
    
    POST /api/activity-logs/user/{userId}
    Create a new activity log for a specific user.
    
    PUT /api/activity-logs/{id}
    Update an activity log by its ID.
    
    DELETE /api/activity-logs/{id}
    Delete an activity log by its ID.

###3 Workout Plan Endpoints
    
    GET /api/workout-plans
    Fetch all workout plans.
    
    GET /api/workout-plans/{id}
    Fetch a workout plan by its ID.
    
    POST /api/workout-plans/user/{userId}
    Create a new workout plan for a specific user.
    
    
    PUT /api/workout-plans/{id}
    Update a workout plan by its ID.
    
    DELETE /api/workout-plans/{id}
    Delete a workout plan by its ID.

###4 Swagger Endpoint for Documentation

    http://localhost:8080/swagger-ui/index.html



##Technologies Used
Spring Boot - Java framework for building web applications
MySQL - Relational database for storing data
Hibernate - ORM framework for database interactions
Spring Security - For basic authenticatio and Role bases access 
Swagger - API documentation and testing tool
Maven - Build tools for managing dependencies


##Test Coverage 

-The test coverage of the project currently stands at an impressive 82.70%, ensuring robust functionality and reliability

##Contact Information

If you have any questions, feel free to reach out to me:

- Contact - 8625955442
- Email: omikhandagale862@gmail.com
- GitHub: [OmkarKhandagale7](https://github.com/OmkarKhandagale7)

