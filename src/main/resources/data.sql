-- Sample data for Employee Directory
-- Insert Departments firs
INSERT INTO DEPARTMENT (DEPT_NUM, DEPT_NAME, DEPT_MANAGER_ID) VALUES 
(1, 'Engineering', NULL),
(2, 'Human Resources', NULL),
(3, 'Sales', NULL),
(4, 'Marketing', NULL);

-- Insert Employees
INSERT INTO EMPLOYEE (EMP_NUM, FIRST_NAME, LAST_NAME, TITLE, PHONE_NUMBER, EMAIL, HIRE_DATE, MANAGER_ID, DEPT_NUM) VALUES 
-- Engineering Department
(1, 'John', 'Smith', 'Engineering Director', '555-0101', 'john.smith@company.com', '2020-01-15', NULL, 1),
(2, 'Sarah', 'Johnson', 'Senior Developer', '555-0102', 'sarah.johnson@company.com', '2020-03-20', 1, 1),
(3, 'Mike', 'Davis', 'Developer', '555-0103', 'mike.davis@company.com', '2021-06-10', 2, 1),
(4, 'Emily', 'Brown', 'Junior Developer', '555-0104', 'emily.brown@company.com', '2022-01-05', 2, 1),

-- HR Department
(5, 'Lisa', 'Wilson', 'HR Director', '555-0201', 'lisa.wilson@company.com', '2019-11-01', NULL, 2),
(6, 'David', 'Miller', 'HR Specialist', '555-0202', 'david.miller@company.com', '2021-02-15', 5, 2),

-- Sales Department
(7, 'Jennifer', 'Garcia', 'Sales Director', '555-0301', 'jennifer.garcia@company.com', '2020-05-10', NULL, 3),
(8, 'Robert', 'Martinez', 'Senior Sales Rep', '555-0302', 'robert.martinez@company.com', '2020-08-20', 7, 3),
(9, 'Amanda', 'Taylor', 'Sales Rep', '555-0303', 'amanda.taylor@company.com', '2021-09-15', 8, 3),

-- Marketing Department
(10, 'Chris', 'Anderson', 'Marketing Director', '555-0401', 'chris.anderson@company.com', '2020-02-01', NULL, 4),
(11, 'Jessica', 'Thomas', 'Marketing Specialist', '555-0402', 'jessica.thomas@company.com', '2021-04-12', 10, 4);

-- Update departments to set their managers
UPDATE DEPARTMENT SET DEPT_MANAGER_ID = 1 WHERE DEPT_NUM = 1; -- Engineering
UPDATE DEPARTMENT SET DEPT_MANAGER_ID = 5 WHERE DEPT_NUM = 2; -- HR
UPDATE DEPARTMENT SET DEPT_MANAGER_ID = 7 WHERE DEPT_NUM = 3; -- Sales
UPDATE DEPARTMENT SET DEPT_MANAGER_ID = 10 WHERE DEPT_NUM = 4; -- Marketing

-- Update the sequence to continue from where we left off
ALTER SEQUENCE hibernate_sequence RESTART WITH 12;