# 📋 API Testing Guide

## Quick Start Testing

### 1️⃣ Create Province (REQUIRED FIRST)
```http
POST http://localhost:8080/api/provinces
Content-Type: application/json

{
  "code": "NCR",
  "name": "National Capital Region"
}
```

**Expected**: `201 CREATED`
**Terminal Log**:
```
📥 POST /api/provinces - Creating province: NCR
🔄 Creating province with code: NCR and name: National Capital Region
✅ Province created successfully with ID: 1
```

---

### 2️⃣ Register Applicant
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

**Expected**: `201 CREATED`
**Terminal Log**:
```
📥 POST /api/applicants/register - Registering applicant: juan@example.com
🔄 Registering applicant: Juan Dela Cruz with email: juan@example.com
✅ Applicant registered successfully with ID: 1
```

---

### 3️⃣ Create Inspector
```http
POST http://localhost:8080/api/inspectors
Content-Type: application/json

{
  "name": "Engr. Maria Santos",
  "licenseNumber": "INS-2024-001",
  "specialization": "Structural Engineering"
}
```

**Expected**: `201 CREATED`

---

### 4️⃣ Submit Building Permit
```http
POST http://localhost:8080/api/permits/submit
Content-Type: application/json

{
  "projectName": "Residential Building Construction",
  "location": "Quezon City",
  "applicantId": 1
}
```

**Expected**: `201 CREATED`

---

### 5️⃣ Upload Building Plan
```http
POST http://localhost:8080/api/plans/upload
Content-Type: application/json

{
  "fileName": "building_plan_v1.pdf",
  "fileUrl": "/uploads/plans/building_plan_v1.pdf",
  "permitId": 1
}
```

**Expected**: `201 CREATED`

---

### 6️⃣ Assign Inspectors to Permit
```http
POST http://localhost:8080/api/permits/1/assign-inspectors
Content-Type: application/json

{
  "inspectorIds": [1]
}
```

**Expected**: `200 OK`

---

### 7️⃣ Record Inspection
```http
POST http://localhost:8080/api/inspections/record
Content-Type: application/json

{
  "permitId": 1,
  "inspectorId": 1,
  "result": "PASSED",
  "remarks": "All structural requirements met"
}
```

**Expected**: `201 CREATED`
**Note**: When result is "PASSED", permit status automatically changes to "APPROVED"

---

## Query APIs

### Get Applicants by Province Name
```http
GET http://localhost:8080/api/applicants/province/name/National Capital Region
```

### Get Applicants by Province Code
```http
GET http://localhost:8080/api/applicants/province/code/NCR
```

### Get All Applicants (Paginated)
```http
GET http://localhost:8080/api/applicants/paginated?page=0&size=10
```

### Get All Applicants (Sorted)
```http
GET http://localhost:8080/api/applicants/sorted?page=0&size=10&sortBy=name&direction=ASC
```

### Get All Permits (Sorted by Date)
```http
GET http://localhost:8080/api/permits/sorted?page=0&size=10&sortBy=submissionDate&direction=DESC
```

### Get All Inspections (Paginated)
```http
GET http://localhost:8080/api/inspections/paginated?page=0&size=10
```

---

## Error Testing

### Test Duplicate Email
```http
POST http://localhost:8080/api/applicants/register
Content-Type: application/json

{
  "name": "Pedro Reyes",
  "email": "juan@example.com",
  "phone": "09181234567",
  "provinceId": 1
}
```

**Expected**: `409 CONFLICT`
**Response**:
```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Email 'juan@example.com' already exists",
  "path": "/api/applicants/register",
  "timestamp": "2024-03-12T23:45:00"
}
```

**Terminal Log**:
```
❌ DUPLICATE RESOURCE: Email 'juan@example.com' already exists
📍 Request Path: /api/applicants/register
```

---

### Test Invalid Province ID
```http
POST http://localhost:8080/api/applicants/register
Content-Type: application/json

{
  "name": "Maria Garcia",
  "email": "maria@example.com",
  "phone": "09191234567",
  "provinceId": 999
}
```

**Expected**: `404 NOT FOUND`
**Response**:
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Province with ID 999 not found",
  "path": "/api/applicants/register",
  "timestamp": "2024-03-12T23:45:00"
}
```

**Terminal Log**:
```
❌ RESOURCE NOT FOUND: Province with ID 999 not found
📍 Request Path: /api/applicants/register
```

---

### Test Invalid JSON
```http
POST http://localhost:8080/api/provinces
Content-Type: application/json

{
  "code": "NCR"
  "name": "National Capital Region"
}
```

**Expected**: `400 BAD REQUEST`
**Terminal Log**:
```
❌ INVALID JSON FORMAT
📍 Request Path: /api/provinces
🔍 Error: JSON parse error...
```

---

## Inspection Result Values

Valid values for `result` field:
- `PASSED` - Inspection passed (automatically approves permit)
- `FAILED` - Inspection failed
- `PENDING` - Inspection pending

---

## Permit Status Values

- `PENDING` - Initial status
- `UNDER_INSPECTION` - After inspectors assigned
- `APPROVED` - After inspection passes
- `REJECTED` - Manually set

---

## Response Status Codes

| Code | Status | Meaning |
|------|--------|---------|
| 200 | OK | Successful GET/PUT request |
| 201 | CREATED | Resource successfully created |
| 400 | BAD REQUEST | Invalid input or constraint violation |
| 404 | NOT FOUND | Resource doesn't exist |
| 409 | CONFLICT | Duplicate entry (email, code, license) |
| 500 | INTERNAL SERVER ERROR | Unexpected server error |

---

## Pagination Parameters

- `page` - Page number (starts at 0)
- `size` - Number of items per page
- `sortBy` - Field name to sort by
- `direction` - Sort direction (ASC or DESC)

Example:
```
/api/applicants/sorted?page=0&size=5&sortBy=name&direction=ASC
```

---

## Terminal Log Symbols

- 📥 = Incoming HTTP request
- 🔄 = Processing operation
- ✅ = Operation successful
- ❌ = Error occurred
- 📍 = Request path/endpoint
- 🔍 = Detailed error information
- 💡 = Additional context
- 📋 = Fetching data
- 📚 = Stack trace

---

## Complete Test Sequence

Run these in order:

1. Create 2-3 provinces
2. Create 3-5 applicants (different provinces)
3. Create 2-3 inspectors
4. Submit 2-3 building permits
5. Upload building plans for each permit
6. Assign inspectors to permits
7. Record inspections
8. Test query endpoints
9. Test pagination
10. Test sorting

---

## Verification Queries

After testing, verify data:

```sql
-- Check all provinces
SELECT * FROM provinces;

-- Check applicants with provinces
SELECT a.name, a.email, p.name as province 
FROM applicants a 
JOIN provinces p ON a.province_id = p.id;

-- Check permits with status
SELECT id, project_name, status, submission_date 
FROM building_permits;

-- Check inspections with results
SELECT i.id, bp.project_name, ins.name as inspector, i.result 
FROM inspections i
JOIN building_permits bp ON i.permit_id = bp.id
JOIN inspectors ins ON i.inspector_id = ins.id;
```
