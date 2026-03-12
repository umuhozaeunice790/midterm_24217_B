# 🏢 Building Permit & Inspection Management System

## 📌 Project Overview
A Spring Boot backend application for digitizing construction permit approval and inspection processes.

## ✨ Features Implemented

### Core Features
- ✅ Register Applicant
- ✅ Submit Building Permit
- ✅ Upload Building Plan
- ✅ Assign Inspectors
- ✅ Record Inspection Results
- ✅ Retrieve Applicants by Province (Name/Code)
- ✅ Sorting APIs
- ✅ Pagination APIs

### Database Relationships
- **One-to-One**: BuildingPermit ↔ BuildingPlan
- **One-to-Many**: Province → Applicant, Applicant → BuildingPermit, BuildingPermit → Inspection
- **Many-to-Many**: BuildingPermit ↔ Inspector

### Technologies
- Spring Boot 4.0.3
- Spring Data JPA
- Hibernate ORM
- PostgreSQL
- Java 21

## 🚀 Build & Run Instructions

### Prerequisites
- Java 21
- PostgreSQL 12+
- Maven 3.6+

### Database Setup
```sql
CREATE DATABASE building_permit_db;
```

Update `application.properties` with your PostgreSQL credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Build the Project
```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Endpoints

### 1. Province Management

#### Create Province
```http
POST /api/provinces
Content-Type: application/json

{
  "code": "NCR",
  "name": "National Capital Region"
}
```

#### Get All Provinces
```http
GET /api/provinces
```

---

### 2. Applicant Management

#### Register Applicant
```http
POST /api/applicants/register
Content-Type: application/json

{
  "name": "Juan Dela Cruz",
  "email": "juan@example.com",
  "phone": "09171234567",
  "provinceId": 1
}
```

#### Get Applicants by Province Name
```http
GET /api/applicants/province/name/National Capital Region
```

#### Get Applicants by Province Code
```http
GET /api/applicants/province/code/NCR
```

#### Get All Applicants
```http
GET /api/applicants
```

#### Get Applicants with Pagination
```http
GET /api/applicants/paginated?page=0&size=10
```

#### Get Applicants with Sorting
```http
GET /api/applicants/sorted?page=0&size=10&sortBy=name&direction=ASC
```

---

### 3. Building Permit Management

#### Submit Building Permit
```http
POST /api/permits/submit
Content-Type: application/json

{
  "projectName": "Residential Building Construction",
  "location": "Quezon City",
  "applicantId": 1
}
```

#### Assign Inspectors to Permit
```http
POST /api/permits/1/assign-inspectors
Content-Type: application/json

{
  "inspectorIds": [1, 2]
}
```

#### Get Permit by ID
```http
GET /api/permits/1
```

#### Get All Permits
```http
GET /api/permits
```

#### Get Permits with Pagination
```http
GET /api/permits/paginated?page=0&size=10
```

#### Get Permits with Sorting
```http
GET /api/permits/sorted?page=0&size=10&sortBy=submissionDate&direction=DESC
```

---

### 4. Building Plan Management

#### Upload Building Plan
```http
POST /api/plans/upload
Content-Type: application/json

{
  "fileName": "building_plan_v1.pdf",
  "fileUrl": "/uploads/plans/building_plan_v1.pdf",
  "permitId": 1
}
```

---

### 5. Inspector Management

#### Create Inspector
```http
POST /api/inspectors
Content-Type: application/json

{
  "name": "Maria Santos",
  "licenseNumber": "INS-2024-001",
  "specialization": "Structural Engineering"
}
```

#### Get All Inspectors
```http
GET /api/inspectors
```

---

### 6. Inspection Management

#### Record Inspection Result
```http
POST /api/inspections/record
Content-Type: application/json

{
  "permitId": 1,
  "inspectorId": 1,
  "result": "PASSED",
  "remarks": "All structural requirements met"
}
```

**Inspection Results**: `PASSED`, `FAILED`, `PENDING`

#### Get All Inspections
```http
GET /api/inspections
```

#### Get Inspections with Pagination
```http
GET /api/inspections/paginated?page=0&size=10
```

#### Get Inspections with Sorting
```http
GET /api/inspections/sorted?page=0&size=10&sortBy=inspectionDate&direction=DESC
```

---

## 🗂️ Database Schema

### Tables
- `provinces` - Province information
- `applicants` - Applicant details
- `building_permits` - Permit applications
- `building_plans` - Building plan files
- `inspectors` - Inspector information
- `inspections` - Inspection records
- `permit_inspectors` - Many-to-Many join table

## 🔍 Key Features Demonstrated

### 1. existsBy() Method
- `existsByEmail()` in ApplicantRepository
- `existsByLicenseNumber()` in InspectorRepository
- `existsByCode()` and `existsByName()` in ProvinceRepository

### 2. Custom Query Methods
- `findByProvinceName()` - Retrieve applicants by province name
- `findByProvinceCode()` - Retrieve applicants by province code

### 3. Pagination
- All list endpoints support pagination via `Pageable`
- Example: `/api/applicants/paginated?page=0&size=10`

### 4. Sorting
- Dynamic sorting on multiple fields
- Example: `/api/permits/sorted?sortBy=submissionDate&direction=DESC`

## 📊 Testing Workflow

1. **Create Province**
   ```bash
   POST /api/provinces
   ```

2. **Register Applicant**
   ```bash
   POST /api/applicants/register
   ```

3. **Submit Building Permit**
   ```bash
   POST /api/permits/submit
   ```

4. **Upload Building Plan**
   ```bash
   POST /api/plans/upload
   ```

5. **Create Inspector**
   ```bash
   POST /api/inspectors
   ```

6. **Assign Inspectors**
   ```bash
   POST /api/permits/1/assign-inspectors
   ```

7. **Record Inspection**
   ```bash
   POST /api/inspections/record
   ```

8. **Retrieve Applicants by Province**
   ```bash
   GET /api/applicants/province/name/NCR
   ```

## 🎯 Project Structure
```
src/main/java/com/example/BuildingPermitMs/
├── entity/          # JPA Entities
├── repository/      # Spring Data JPA Repositories
├── service/         # Business Logic Layer
├── controller/      # REST Controllers
└── dto/            # Data Transfer Objects
```

## 📝 Notes
- All dates are automatically set to current date/time
- Permit status automatically updates to APPROVED when inspection PASSED
- Email and license numbers must be unique
- Province codes must be unique
# 🏢 Building Permit & Inspection Management System

## 📌 Project Overview
The **Building Permit & Inspection Management System** is a Spring Boot backend application designed to digitize construction permit application, approval, and inspection processes.

Government construction offices often manage permits manually, causing delays, data loss, and poor tracking of inspections. This system provides a centralized digital platform where applicants submit building permits, inspectors perform inspections, and administrators manage approvals based on locations and provinces.

This project demonstrates database relationships, Spring Data JPA features, and backend system design as required for the practical assessment.

---

## 🎯 Project Objectives
- Manage building permit applications digitally.
- Track inspection activities and results.
- Store construction locations using provinces.
- Demonstrate database relationships:
  - One-to-One
  - One-to-Many
  - Many-to-Many
- Implement sorting and pagination using Spring Data JPA.
- Prevent duplicate records using `existsBy()` method.
- Retrieve applicants based on province name or province code.

---

## 🛠️ Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate ORM
- MySQL Database
- Maven
- REST APIs
- Lombok
- Postman (API Testing)

---

## 🏗️ System Architecture

The system follows a layered architecture:

### Architecture Layers
- **Controller Layer** – Handles HTTP requests.
- **Service Layer** – Contains business logic.
- **Repository Layer** – Performs database operations.
- **Entity Layer** – Maps database tables using JPA annotations.

---

## 🗄️ Database Design (ERD)

### Main Entities (Tables)
1. Applicant  
2. Permit  
3. Inspector  
4. Inspection  
5. Location  
6. Province  
7. BuildingPlan  

The database contains more than five tables as required by the assessment rubric.

---

## 🔗 Entity Relationships

### ✅ One-to-Many Relationship
- One Applicant submits many Permits.
- One Province contains many Locations.


**Logic:**  
Multiple inspectors can inspect a single permit, and inspectors can work on multiple permits.  
This relationship is implemented using a join table called `permit_inspector`.

---

### ✅ One-to-One Relationship
- Permit ↔ BuildingPlan

**Logic:**  
Each permit submission contains exactly one building plan document.

---

## 📍 Location Management

Location information is stored hierarchically:

**Logic:**  
An applicant may apply for multiple construction permits, while each permit belongs to only one applicant.

---

### ✅ Many-to-Many Relationship
- Permit ↔ Inspector


**Logic:**  
Each permit submission contains exactly one building plan document.

---

## 📍 Location Management

Location information is stored hierarchically:


**Logic:**  
Each permit submission contains exactly one building plan document.

---

## 📍 Location Management

Location information is stored hierarchically:



Each location references a province using a foreign key to maintain normalized data storage.

---

## 🔎 Sorting Implementation

Sorting is implemented using Spring Data JPA `Sort` and `Pageable`.

Example use cases:
- Sort permits by submission date.
- Sort inspectors by experience.

```java
PageRequest.of(page, size, Sort.by("submissionDate").descending());
