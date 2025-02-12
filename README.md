﻿# Personal Finance Manager

A comprehensive Spring Boot application for managing personal finances, tracking expenses, setting savings goals, and generating financial reports.

## Features

### User Management
- Secure user registration and authentication using JWT
- User profile management
- Password encryption using BCrypt

### Transaction Management
- Track income and expenses
- Categorize transactions
- View transaction history
- Update and delete transactions
- Real-time balance tracking

### Savings Goals
- Set personalized savings targets
- Track progress towards financial goals
- Monitor goal achievements
- Category-specific goal tracking
- Progress visualization

### Financial Reports
- Monthly spending analysis
- Category-wise expense breakdown
- Visual representations using charts
- Daily spending trends
- Income vs. expense comparisons

## Technical Stack

| Component | Technology |
|-----------|------------|
| Backend | Spring Boot 3.4.1 |
| Security | Spring Security, JWT |
| Database | H2 Database |
| Build Tool | Maven |
| Charts | JFreeChart |
| API Documentation | Swagger/OpenAPI |

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/yourusername/personal-finance-manager.git
```

### Navigate to the Project Directory
```bash
cd personal-finance-manager
```

### Build the Project
```bash
./mvnw clean install
```

### Run the Application
```bash
./mvnw spring-boot:run
```

The application will start on: http://localhost:8080

## API Endpoints

### User Management
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login
- `GET /api/users/profile` - Get user profile

### Transactions
- `POST /api/transactions/{userId}` - Add new transaction
- `GET /api/transactions/{userId}` - Get user transactions
- `PUT /api/transactions/{transactionId}` - Update transaction
- `DELETE /api/transactions/{transactionId}` - Delete transaction

### Savings Goals
- `POST /api/savings-goals/{userId}` - Create savings goal
- `GET /api/savings-goals/{userId}` - Get user's savings goals
- `GET /api/savings-goals/{goalId}/progress` - Get goal progress

### Reports
- `GET /api/reports/monthly/{userId}/charts/spending-category` - Get category-wise spending chart
- `GET /api/reports/monthly/{userId}/charts/spending-trend` - Get spending trend chart

## Security

The application implements comprehensive security measures:

- JWT-based authentication
- Password encryption
- Secure endpoints
- CORS configuration
- Session management

## Development

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA recommended)

### Configuration
Modify application properties in `src/main/resources/application.properties`
