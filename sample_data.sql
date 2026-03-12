-- Sample Data for Building Permit Management System

-- Insert Provinces
INSERT INTO provinces (code, name) VALUES 
('NCR', 'National Capital Region'),
('CAR', 'Cordillera Administrative Region'),
('R01', 'Ilocos Region'),
('R03', 'Central Luzon'),
('R04A', 'CALABARZON');

-- Insert Applicants
INSERT INTO applicants (name, email, phone, province_id) VALUES 
('Juan Dela Cruz', 'juan@example.com', '09171234567', 1),
('Maria Santos', 'maria@example.com', '09181234567', 1),
('Pedro Reyes', 'pedro@example.com', '09191234567', 2);

-- Insert Inspectors
INSERT INTO inspectors (name, license_number, specialization) VALUES 
('Engr. Jose Garcia', 'INS-2024-001', 'Structural Engineering'),
('Engr. Ana Lopez', 'INS-2024-002', 'Electrical Engineering'),
('Engr. Carlos Mendoza', 'INS-2024-003', 'Civil Engineering');

-- Insert Building Permits
INSERT INTO building_permits (project_name, location, status, submission_date, applicant_id) VALUES 
('Residential Building Construction', 'Quezon City', 'PENDING', CURRENT_DATE, 1),
('Commercial Complex', 'Makati City', 'UNDER_INSPECTION', CURRENT_DATE, 2),
('Warehouse Facility', 'Baguio City', 'APPROVED', CURRENT_DATE, 3);

-- Insert Building Plans
INSERT INTO building_plans (file_name, file_url, upload_date, permit_id) VALUES 
('residential_plan_v1.pdf', '/uploads/plans/residential_plan_v1.pdf', CURRENT_TIMESTAMP, 1),
('commercial_plan_v1.pdf', '/uploads/plans/commercial_plan_v1.pdf', CURRENT_TIMESTAMP, 2);

-- Assign Inspectors to Permits (Many-to-Many)
INSERT INTO permit_inspectors (permit_id, inspector_id) VALUES 
(2, 1),
(2, 2),
(3, 3);

-- Insert Inspections
INSERT INTO inspections (inspection_date, result, remarks, permit_id, inspector_id) VALUES 
(CURRENT_DATE, 'PASSED', 'All structural requirements met', 3, 3),
(CURRENT_DATE, 'PENDING', 'Awaiting final electrical inspection', 2, 2);
