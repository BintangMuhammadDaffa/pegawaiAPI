# Pegawai API

A RESTful API for managing employees (Pegawai) and divisions (Divisi) built with Spring Boot. This project provides CRUD operations for employees, search functionality, and a view that combines employee and division data.

## Features

- **Employee Management**: Create, read, update, and delete employee records
- **Division Integration**: Employees are linked to divisions with budget information
- **Search Functionality**: Search employees by name (case-insensitive substring search)
- **Data View**: Retrieve combined employee and division data through a custom view
- **RESTful API**: Standard HTTP methods with JSON responses
- **API Documentation**: Integrated Swagger UI for API exploration
- **Comprehensive Testing**: Integration tests with Testcontainers

## Technologies Used

- **Java 17**
- **Spring Boot 3.5.7**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring Boot Test
- **PostgreSQL** (Production database)
- **H2 Database** (Testing)
- **Lombok** (Code generation)
- **Maven** (Build tool)
- **Testcontainers** (Integration testing)
- **SpringDoc OpenAPI** (API documentation)
- **Spring REST Docs** (Documentation generation)

## Project Structure

```
pegawaiAPI/
├── src/
│   ├── main/
│   │   ├── java/com/pegawai/pegawaiAPI/
│   │   │   ├── PegawaiApiApplication.java          # Main application class
│   │   │   ├── controller/
│   │   │   │   └── PegawaiController.java          # REST controller
│   │   │   ├── dto/
│   │   │   │   └── ApiResponse.java                # Response DTO
│   │   │   ├── entity/
│   │   │   │   ├── Divisi.java                     # Division entity
│   │   │   │   └── Pegawai.java                    # Employee entity
│   │   │   ├── repository/
│   │   │   │   ├── DivisiPegawaiViewRepo.java      # Custom view repository
│   │   │   │   ├── DivisiRepo.java                 # Division repository
│   │   │   │   └── PegawaiRepo.java                # Employee repository
│   │   │   ├── repository/projection/
│   │   │   │   └── DivisiPegawaiView.java          # View projection interface
│   │   │   └── service/
│   │   │       └── PegawaiService.java             # Business logic service
│   │   └── resources/
│   │       └── application.yml                      # Application configuration
│   └── test/
│       ├── java/com/pegawai/pegawaiAPI/
│       │   ├── PegawaiApiApplicationTests.java     # Unit tests
│       │   └── PegawaiControllerIntegrationTest.java # Integration tests
│       └── resources/
│           └── application-test.yml                # Test configuration
├── pom.xml                                          # Maven configuration
├── mvnw                                             # Maven wrapper (Unix)
├── mvnw.cmd                                         # Maven wrapper (Windows)
└── README.md                                        # This file
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **PostgreSQL** (for production)
- **Docker** (for Testcontainers in tests)

## Installation

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd pegawaiAPI
   ```

2. **Set up PostgreSQL database:**

   - Create a database named `company_db`
   - Update `src/main/resources/application.yml` with your PostgreSQL credentials:
     ```yaml
     spring:
       datasource:
         url: jdbc:postgresql://localhost:5432/company_db
         username: your_username
         password: your_password
     ```

3. **Build the project:**

   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## Usage

### API Documentation

Once the application is running, you can access the Swagger UI for API documentation at:
`http://localhost:8080/swagger-ui.html`

### API Endpoints

All endpoints are prefixed with `/api/pegawai` and return responses in the following format:

```json
{
  "success": true,
  "message": "Operation message",
  "data": { ... } // or array for list operations
}
```

#### 1. Get All Employees with Division Info

**GET** `/api/pegawai/view`

Retrieves all employees joined with their division information.

**Response:**

```json
{
  "success": true,
  "message": "Data retrieved successfully",
  "data": [
    {
      "idPeg": 1,
      "nama": "John Doe",
      "email": "john@example.com",
      "namaDiv": "IT Department",
      "anggaran": 1000000.0,
      "idDiv": 1
    }
  ]
}
```

#### 2. Search Employees by Name

**GET** `/api/pegawai/search?nama={search_term}`

Searches for employees whose names contain the search term (case-insensitive).

**Parameters:**

- `nama` (query parameter): Search term

**Example:** `/api/pegawai/search?nama=john`

**Response:**

```json
{
  "success": true,
  "message": "Search completed successfully",
  "data": [
    {
      "idPeg": 1,
      "nama": "John Doe",
      "email": "john@example.com",
      "idDiv": 1
    }
  ]
}
```

#### 3. Create New Employee

**POST** `/api/pegawai`

Creates a new employee record.

**Request Body:**

```json
{
  "idPeg": 1,
  "nama": "John Doe",
  "email": "john@example.com",
  "idDiv": 1
}
```

**Response (201 Created):**

```json
{
  "success": true,
  "message": "Pegawai created successfully",
  "data": {
    "idPeg": 1,
    "nama": "John Doe",
    "email": "john@example.com",
    "idDiv": 1
  }
}
```

#### 4. Update Employee

**PUT** `/api/pegawai/{id}`

Updates an existing employee record.

**Path Parameters:**

- `id`: Employee ID

**Request Body:** (same as create, but ID is in path)

**Response:**

```json
{
  "success": true,
  "message": "Pegawai updated successfully",
  "data": {
    "idPeg": 1,
    "nama": "John Updated",
    "email": "john.updated@example.com",
    "idDiv": 1
  }
}
```

#### 5. Delete Employee

**DELETE** `/api/pegawai/{id}`

Deletes an employee record.

**Path Parameters:**

- `id`: Employee ID

**Response:**

```json
{
  "success": true,
  "message": "Pegawai deleted successfully",
  "data": null
}
```

### Running Tests

Execute the tests using Maven:

```bash
mvn test
```

This will run both unit tests and integration tests. Integration tests use Testcontainers to spin up a PostgreSQL container for testing.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
