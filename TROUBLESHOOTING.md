# 🔧 Troubleshooting Guide

## Common Issues and Solutions

### 1. Database Connection Issues

**Error**: `Connection refused` or `Database does not exist`

**Solution**:
```bash
# Create the database
psql -U postgres
CREATE DATABASE building_permit_db;
\q
```

Update `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/building_permit_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

---

### 2. Port Already in Use

**Error**: `Port 8080 is already in use`

**Solution**:
Change port in `application.properties`:
```properties
server.port=8081
```

---

### 3. Duplicate Entry Errors

**Error**: `409 Conflict - Email already exists`

**Terminal Output**:
```
❌ DUPLICATE RESOURCE: Email 'test@example.com' already exists
📍 Request Path: /api/applicants/register
```

**Solution**: Use a different email address or check existing records first.

---

### 4. Resource Not Found

**Error**: `404 Not Found - Province with ID 1 not found`

**Terminal Output**:
```
❌ RESOURCE NOT FOUND: Province with ID 1 not found
📍 Request Path: /api/applicants/register
```

**Solution**: Create the province first before creating applicants.

---

### 5. Invalid JSON Format

**Error**: `400 Bad Request - Invalid JSON format`

**Terminal Output**:
```
❌ INVALID JSON FORMAT
📍 Request Path: /api/provinces
🔍 Error: JSON parse error...
```

**Solution**: Check your JSON syntax in Postman. Ensure:
- All strings are in double quotes
- No trailing commas
- Proper brackets and braces

---

### 6. Foreign Key Constraint Violation

**Error**: `400 Bad Request - Invalid reference`

**Terminal Output**:
```
❌ DATABASE CONSTRAINT VIOLATION
📍 Request Path: /api/permits/submit
🔍 Root Cause: foreign key constraint violation
💡 Full Error: ...
```

**Solution**: Ensure the referenced entity exists (e.g., applicant must exist before creating permit).

---

## Understanding Terminal Logs

### Successful Request
```
📥 POST /api/provinces - Creating province: NCR
🔄 Creating province with code: NCR and name: National Capital Region
✅ Province created successfully with ID: 1
```

### Failed Request
```
📥 POST /api/applicants/register - Registering applicant: test@example.com
🔄 Registering applicant: John Doe with email: test@example.com
❌ Province with ID 999 not found
```

---

## HTTP Status Codes Used

| Status Code | Meaning | When Used |
|-------------|---------|-----------|
| 200 OK | Success | GET requests, successful updates |
| 201 CREATED | Resource created | POST requests (create operations) |
| 400 BAD REQUEST | Invalid input | Missing fields, invalid JSON, constraint violations |
| 404 NOT FOUND | Resource not found | Entity with given ID doesn't exist |
| 409 CONFLICT | Duplicate resource | Email, code, or license number already exists |
| 500 INTERNAL SERVER ERROR | Server error | Unexpected errors |

---

## Testing Workflow

### Step 1: Create Province
```http
POST http://localhost:8080/api/provinces
Content-Type: application/json

{
  "code": "NCR",
  "name": "National Capital Region"
}
```

**Expected Response**: `201 CREATED`

---

### Step 2: Register Applicant
```http
POST http://localhost:8080/api/applicants/register
Content-Type: application/json

{
  "name": "Juan Dela Cruz",
  "email": "juan@example.com",
  "phone": "09171234567",
  "provinceId": 1
}
```

**Expected Response**: `201 CREATED`

---

### Step 3: Create Inspector
```http
POST http://localhost:8080/api/inspectors
Content-Type: application/json

{
  "name": "Engr. Maria Santos",
  "licenseNumber": "INS-2024-001",
  "specialization": "Structural Engineering"
}
```

**Expected Response**: `201 CREATED`

---

### Step 4: Submit Building Permit
```http
POST http://localhost:8080/api/permits/submit
Content-Type: application/json

{
  "projectName": "Residential Building",
  "location": "Quezon City",
  "applicantId": 1
}
```

**Expected Response**: `201 CREATED`

---

### Step 5: Upload Building Plan
```http
POST http://localhost:8080/api/plans/upload
Content-Type: application/json

{
  "fileName": "plan_v1.pdf",
  "fileUrl": "/uploads/plan_v1.pdf",
  "permitId": 1
}
```

**Expected Response**: `201 CREATED`

---

### Step 6: Assign Inspectors
```http
POST http://localhost:8080/api/permits/1/assign-inspectors
Content-Type: application/json

{
  "inspectorIds": [1]
}
```

**Expected Response**: `200 OK`

---

### Step 7: Record Inspection
```http
POST http://localhost:8080/api/inspections/record
Content-Type: application/json

{
  "permitId": 1,
  "inspectorId": 1,
  "result": "PASSED",
  "remarks": "All requirements met"
}
```

**Expected Response**: `201 CREATED`

---

## Checking Logs

### Enable Debug Logging
In `application.properties`:
```properties
logging.level.com.example.BuildingPermitMs=DEBUG
```

### View SQL Queries
Already enabled in `application.properties`:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## Common Postman Issues

### Issue: Getting HTML error page instead of JSON

**Solution**: Ensure you set the header:
```
Content-Type: application/json
```

### Issue: Request body is empty

**Solution**: In Postman:
1. Select "Body" tab
2. Choose "raw"
3. Select "JSON" from dropdown
4. Paste your JSON

---

## Database Verification

### Check if tables exist
```sql
\dt
```

### View province data
```sql
SELECT * FROM provinces;
```

### View applicants with province
```sql
SELECT a.id, a.name, a.email, p.name as province_name 
FROM applicants a 
JOIN provinces p ON a.province_id = p.id;
```

---

## Error Response Format

All errors return this JSON structure:
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Province with ID 999 not found",
  "path": "/api/applicants/register",
  "timestamp": "2024-03-12T23:45:00"
}
```

---

## Need More Help?

Check the terminal output for detailed error messages with emojis:
- 📥 = Incoming request
- 🔄 = Processing
- ✅ = Success
- ❌ = Error
- 📍 = Request path
- 🔍 = Error details
- 💡 = Additional info
