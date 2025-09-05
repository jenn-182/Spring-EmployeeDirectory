# Employee Directory API Documentation

## Overview
A Spring Boot REST API for managing employees and departments with hierarchical relationships.

## Base URL
```
http://localhost:8081
```

## Endpoints

### Employees

#### Get All Employees
```http
GET /api/employees
```
Returns a list of all employees.

**Response:**
```json
[
  {
    "employeeNumber": 1,
    "firstName": "John",
    "lastName": "Smith",
    "title": "Engineering Director",
    "phoneNumber": "555-0101",
    "email": "john.smith@company.com",
    "hireDate": "2020-01-15"
  }
]
```

#### Get Employee by ID
```http
GET /api/employees/{id}
```
Returns a specific employee by ID.

#### Get Employee's Direct Reports
```http
GET /api/employees/{id}/reports
```
Returns all direct reports for a manager.

#### Get Employee's Full Report Chain
```http
GET /api/employees/{id}/all-reports
```
Returns all employees under a manager (including indirect reports).

#### Get Employee's Manager
```http
GET /api/employees/{id}/manager
```
Returns the direct manager of an employee.

#### Get Employee's Manager Chain
```http
GET /api/employees/{id}/manager-chain
```
Returns the full chain of managers up to the top level.

#### Get Top Level Managers
```http
GET /api/employees/top-managers
```
Returns all employees who don't have a manager.

#### Search Employee by Email
```http
GET /api/employees/search?email={email}
```
Find an employee by their email address.

#### Get Team Size
```http
GET /api/employees/{managerId}/team-size
```
Returns the total number of people under a manager.

#### Create Employee
```http
POST /api/employees
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Doe",
  "title": "Developer",
  "phoneNumber": "555-0199",
  "email": "jane.doe@company.com",
  "hireDate": "2023-09-05"
}
```

#### Update Employee
```http
PUT /api/employees/{id}
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Smith",
  "title": "Senior Developer"
}
```

#### Delete Employee
```http
DELETE /api/employees/{id}
```

### Departments

#### Get All Departments
```http
GET /api/departments
```

#### Get Department by ID
```http
GET /api/departments/{id}
```

#### Get Department Employees
```http
GET /api/departments/{id}/employees
```

#### Get Department Size
```http
GET /api/departments/{id}/size
```

#### Search Department by Name
```http
GET /api/departments/search?name={name}
```

#### Create Department
```http
POST /api/departments
Content-Type: application/json

{
  "departmentName": "Quality Assurance"
}
```

#### Update Department
```http
PUT /api/departments/{id}
```

#### Delete Department
```http
DELETE /api/departments/{id}
```

## Database Console
Access H2 database console at: `http://localhost:8081/h2-console`

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

## Sample Data
The application starts with sample data including:
- 4 departments (Engineering, HR, Sales, Marketing)
- 11 employees with various hierarchical relationships

## Error Responses
All endpoints return appropriate HTTP status codes:
- `200 OK` - Success
- `201 Created` - Resource created
- `404 Not Found` - Resource not found
- `400 Bad Request` - Invalid request data
- `500 Internal Server Error` - Server error