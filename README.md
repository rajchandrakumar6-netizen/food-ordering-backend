# 🍕 Food Ordering System - Backend

A RESTful backend API for an Online Food Ordering platform built with Spring Boot, Spring Security, and MySQL.

## 📋 Project Info

- **Course:** CMJD - Comprehensive Master Java Developer
- **Batch:** 112/113
- **Assignment:** Backend Development with Spring Boot and MySQL

## 🛠️ Tech Stack

- Java 24
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Security + JWT
- MySQL 8.0
- Lombok
- Maven

## 🚀 Getting Started

### Setup

1. Clone the repository
2. Create MySQL database: `CREATE DATABASE food_ordering_db;`
3. Update `application.properties` with your MySQL password
4. Run: `./mvnw spring-boot:run`

Server starts on `http://localhost:8080`

## 🔐 Authentication

Uses JWT tokens. Include in requests:
`Authorization: Bearer YOUR_TOKEN`

## 📡 Key Endpoints

- POST /api/auth/register
- POST /api/auth/login
- GET /api/foods
- GET /api/categories
- POST /api/cart/add
- POST /api/orders
- POST /api/payments