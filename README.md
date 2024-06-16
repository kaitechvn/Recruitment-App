# Recruitment Backend API Project
[![GitHub Stars](https://img.shields.io/github/stars/kaitechvn/Recruitment-App.svg)](https://github.com/kaitechvn/Recruitment-App/stargazers)
[![Current Version](https://img.shields.io/badge/version-0.0.1-green.svg)](https://github.com/kaitechvn/Recruitment-App)
[![Live Demo](https://img.shields.io/badge/status-active-blue.svg)](https://github.com/kaitechvn/Recruitment-App)
## Table of Contents

- [Introduction](#introduction)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Features](#features)
- [Installation](#installation)
- [Example usage](#example usage)
- [Link and screenshot](#link and screenshot)

## Introduction
This project focuses on the recruitment domain, aiming to design and implement a comprehensive API system for managing various recruitment-related operations.
It serves as a mock project intended to enhance backend programming skills and provide hands-on experience in developing RESTful API using modern technologies.

## Technologies
[![Java Badge](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot Badge](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security Badge](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)](https://spring.io/projects/spring-security)
[![MySQL Badge](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Redis Badge](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io/)

- **Java** - version 22 
- **Spring Boot** - version 3.2.5
- **Spring Securiy** - version 3.2.5 
- **MySQL** - version 8.0.36
- **Redis** - version 7.2.1

## Architecture
![Project Flow](./img/architecture.png)

## Features
- Perform CRUD operations
- Provide analytical APIs for insights
- Secure APIs with authorization mechanisms using JWT
- Implement caching service to improve performance
- Create log for tracking and debugging
- Monitor application system using Prothemeus, Grafana, Sentry

## Installation
Follow these steps to get the project up and running on your local machine. Use your command line for setting up

### Prerequisites
- Java 22
- MySQL 
- Mongosh - for MongoDB
- Maven 
- Docker 

### Setup
**1. Clone the repository**
   ```bash
   $ https://github.com/kaitechvn/Recruitment-App.git
   cd Recruitment-App
   ```
**2. Run virtual machine**
   ```bash
   $ docker compose up -d 
   ```
**3. Add data to MySQL and MongoDB**
*MySQL*
   ```bash
   $ docker exec -i recruitment-app-mysql-db-1 sh -c 'exec mysql -uroot -p"Khai2604@" -e "CREATE DATABASE job_db;"'
   $ docker exec -i recruitment-app-mysql-db-1 sh -c 'exec mysql -uroot -p"Khai2604@" job_db' < recruitment.sql
   ```
*MongoDB*
   ```bash
   $ docker exec -it recruitment-app-mongodb-1 mongosh --username root --password Mongo@123 --authenticationDatabase admin
   use sample_db
   ```
   ```bash
   db.createUser({
   user: 'user',
   pwd: 'User123',
   roles: [{ role: 'readWrite', db: 'sample_db' }]
   });
   ```

   
**4. Run the application**
   ```bash
   $ mvn clean install
   $ java -jar target/recruitment-0.0.1-SNAPSHOT.jar
   ```

**5. Example usage**


     
