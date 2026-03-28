![[Introduction to Databases and Structures#1. Views]]


---
### Creating Views

**Syntax:**
```sql
CREATE VIEW view_name AS
SELECT column1, column2, ...
FROM table_name
WHERE condition;
```

This can be thought of as:

```MySQL
CREATE VIEW view_name AS <select Queries>
```

### Sample Database Schema

```sql
-- Base Tables
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    dept_id INT,
    manager_id INT,
    hire_date DATE
);

CREATE TABLE DEPARTMENT (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50),
    location VARCHAR(100)
);

CREATE TABLE CUSTOMER (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    city VARCHAR(50),
    credit_score INT
);

CREATE TABLE LOAN (
    loan_number VARCHAR(20) PRIMARY KEY,
    branch_name VARCHAR(50),
    amount DECIMAL(12,2),
    loan_type VARCHAR(20)
);

CREATE TABLE BORROWER (
    customer_id INT,
    loan_number VARCHAR(20),
    PRIMARY KEY (customer_id, loan_number),
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id),
    FOREIGN KEY (loan_number) REFERENCES LOAN(loan_number)
);

CREATE TABLE ACCOUNT (
    account_number VARCHAR(20) PRIMARY KEY,
    branch_name VARCHAR(50),
    balance DECIMAL(12,2)
);

CREATE TABLE DEPOSITOR (
    customer_id INT,
    account_number VARCHAR(20),
    PRIMARY KEY (customer_id, account_number),
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id),
    FOREIGN KEY (account_number) REFERENCES ACCOUNT(account_number)
);

-- Sample Data
INSERT INTO EMPLOYEE VALUES
    (101, 'John Smith', 75000, 1, NULL, '2020-01-15'),
    (102, 'Jane Doe', 65000, 1, 101, '2020-03-20'),
    (103, 'Bob Wilson', 85000, 2, NULL, '2019-06-10'),
    (104, 'Alice Brown', 55000, 2, 103, '2021-02-01'),
    (105, 'Charlie Davis', 95000, 1, 101, '2018-11-05');

INSERT INTO DEPARTMENT VALUES
    (1, 'Engineering', 'Building A'),
    (2, 'Sales', 'Building B'),
    (3, 'HR', 'Building C');

INSERT INTO CUSTOMER VALUES
    (201, 'Michael Johnson', 'New York', 720),
    (202, 'Sarah Williams', 'Boston', 680),
    (203, 'David Miller', 'Chicago', 750);

INSERT INTO LOAN VALUES
    ('L-101', 'Downtown', 50000, 'Personal'),
    ('L-102', 'Uptown', 100000, 'Business'),
    ('L-103', 'Downtown', 75000, 'Home');

INSERT INTO BORROWER VALUES
    (201, 'L-101'),
    (202, 'L-102'),
    (203, 'L-103');

INSERT INTO ACCOUNT VALUES
    ('A-101', 'Downtown', 5000),
    ('A-102', 'Uptown', 8000),
    ('A-103', 'Downtown', 1200);

INSERT INTO DEPOSITOR VALUES
    (201, 'A-101'),
    (202, 'A-102'),
    (203, 'A-103');
```

---

### Example 1: Simple View (Hide Sensitive Data)

**Requirement:** Show employee information without salary details

```sql
-- Create view without salary
CREATE VIEW EMPLOYEE_PUBLIC AS
SELECT emp_id, name, dept_id, hire_date
FROM EMPLOYEE;

-- Query the view
SELECT * FROM EMPLOYEE_PUBLIC;
```

**Result:**
```
┌────────┬───────────────┬─────────┬────────────┐
│ emp_id │ name          │ dept_id │ hire_date  │
├────────┼───────────────┼─────────┼────────────┤
│ 101    │ John Smith    │ 1       │ 2020-01-15 │
│ 102    │ Jane Doe      │ 1       │ 2020-03-20 │
│ 103    │ Bob Wilson    │ 2       │ 2019-06-10 │
│ 104    │ Alice Brown   │ 2       │ 2021-02-01 │
│ 105    │ Charlie Davis │ 1       │ 2018-11-05 │
└────────┴───────────────┴─────────┴────────────┘
```

**Benefit:** Users querying this view cannot see salary information.

---

### Example 2: View with JOIN (Simplification)

**Requirement:** Simplify employee-department query

```sql
-- Create view joining tables
CREATE VIEW EMPLOYEE_DEPT_INFO AS
SELECT 
    e.emp_id,
    e.name AS employee_name,
    e.salary,
    d.dept_name,
    d.location
FROM EMPLOYEE e
JOIN DEPARTMENT d ON e.dept_id = d.dept_id;

-- Query the view
SELECT * FROM EMPLOYEE_DEPT_INFO WHERE dept_name = 'Engineering';
```

**Result:**
```
┌────────┬───────────────┬─────────┬─────────────┬───────────┐
│ emp_id │ employee_name │ salary  │ dept_name   │ location  │
├────────┼───────────────┼─────────┼─────────────┼───────────┤
│ 101    │ John Smith    │ 75000   │ Engineering │ Building A│
│ 102    │ Jane Doe      │ 65000   │ Engineering │ Building A│
│ 105    │ Charlie Davis │ 95000   │ Engineering │ Building A│
└────────┴───────────────┴─────────┴─────────────┴───────────┘
```

**Benefit:** Users don't need to know the JOIN syntax; they query a simple view.

---
### Example 3: View with Aggregation

**Requirement:** Show department-wise average salary

```sql
-- Create aggregate view
CREATE VIEW DEPT_SALARY_STATS AS
SELECT 
    d.dept_name,
    COUNT(e.emp_id) AS num_employees,
    AVG(e.salary) AS avg_salary,
    MIN(e.salary) AS min_salary,
    MAX(e.salary) AS max_salary
FROM DEPARTMENT d
LEFT JOIN EMPLOYEE e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name;

-- Query the view
SELECT * FROM DEPT_SALARY_STATS ORDER BY avg_salary DESC;
```

**Result:**
```
┌─────────────┬───────────────┬────────────┬────────────┬────────────┐
│ dept_name   │ num_employees │ avg_salary │ min_salary │ max_salary │
├─────────────┼───────────────┼────────────┼────────────┼────────────┤
│ Engineering │ 3             │ 78333.33   │ 65000      │ 95000      │
│ Sales       │ 2             │ 70000.00   │ 55000      │ 85000      │
│ HR          │ 0             │ NULL       │ NULL       │ NULL       │
└─────────────┴───────────────┴────────────┴────────────┴────────────┘
```

---

### Example 4: View with Computed Columns

**Requirement:** Show employee tenure

```sql
-- Create view with calculated column
CREATE VIEW EMPLOYEE_TENURE AS
SELECT 
    emp_id,
    name,
    hire_date,
    TIMESTAMPDIFF(YEAR, hire_date, CURDATE()) AS years_of_service,
    salary,
    CASE 
        WHEN TIMESTAMPDIFF(YEAR, hire_date, CURDATE()) >= 5 THEN 'Senior'
        WHEN TIMESTAMPDIFF(YEAR, hire_date, CURDATE()) >= 2 THEN 'Mid-Level'
        ELSE 'Junior'
    END AS seniority_level
FROM EMPLOYEE;

-- Query the view
SELECT * FROM EMPLOYEE_TENURE ORDER BY years_of_service DESC;
```

**Result:**
```
┌────────┬───────────────┬────────────┬──────────────────┬────────┬─────────────────┐
│ emp_id │ name          │ hire_date  │ years_of_service │ salary │ seniority_level │
├────────┼───────────────┼────────────┼──────────────────┼────────┼─────────────────┤
│ 105    │ Charlie Davis │ 2018-11-05 │ 7                │ 95000  │ Senior          │
│ 103    │ Bob Wilson    │ 2019-06-10 │ 6                │ 85000  │ Senior          │
│ 101    │ John Smith    │ 2020-01-15 │ 6                │ 75000  │ Senior          │
│ 102    │ Jane Doe      │ 2020-03-20 │ 5                │ 65000  │ Senior          │
│ 104    │ Alice Brown   │ 2021-02-01 │ 4                │ 55000  │ Mid-Level       │
└────────┴───────────────┴────────────┴──────────────────┴────────┴─────────────────┘
```

---

### Example 5: Parameterized View (Using WHERE)

**Requirement:** View for high-performing customers

```sql
-- Create view with condition
CREATE VIEW HIGH_CREDIT_CUSTOMERS AS
SELECT 
    customer_id,
    customer_name,
    city,
    credit_score
FROM CUSTOMER
WHERE credit_score >= 700;

-- Query the view
SELECT * FROM HIGH_CREDIT_CUSTOMERS;
```

**Result:**
```
┌─────────────┬─────────────────┬─────────┬──────────────┐
│ customer_id │ customer_name   │ city    │ credit_score │
├─────────────┼─────────────────┼─────────┼──────────────┤
│ 201         │ Michael Johnson │ New York│ 720          │
│ 203         │ David Miller    │ Chicago │ 750          │
└─────────────┴─────────────────┴─────────┴──────────────┘
```

---

# View Operations

## 1. Querying Views

Views can be queried like regular tables:

```sql
-- Simple SELECT
SELECT * FROM EMPLOYEE_PUBLIC;

-- With WHERE clause
SELECT * FROM EMPLOYEE_PUBLIC WHERE dept_id = 1;

-- With JOIN (view joining with table)
SELECT 
    ep.name,
    d.dept_name
FROM EMPLOYEE_PUBLIC ep
JOIN DEPARTMENT d ON ep.dept_id = d.dept_id;

-- Nested view query
SELECT dept_name, avg_salary
FROM DEPT_SALARY_STATS
WHERE avg_salary > 70000;
```

---

## 2. Updating Views

**View updates** are allowed under certain conditions but have restrictions.

### Updatable View Requirements:

A view is **updatable** if:
1. ✓ Defined on a **single base table**
2. ✓ Contains **no aggregates** (SUM, AVG, COUNT, etc.)
3. ✓ Contains **no GROUP BY** or **HAVING**
4. ✓ Contains **no DISTINCT**
5. ✓ Contains **no subqueries** in SELECT clause
6. ✓ Contains **no UNION** operations

### 1) Updatable View

```sql
-- Create updatable view
CREATE VIEW ENGINEERING_EMPLOYEES AS
SELECT emp_id, name, salary, dept_id
FROM EMPLOYEE
WHERE dept_id = 1;

-- UPDATE through view (✓ Allowed)
UPDATE ENGINEERING_EMPLOYEES
SET salary = salary * 1.10
WHERE emp_id = 102;

-- Verify update
SELECT * FROM EMPLOYEE WHERE emp_id = 102;
```

**Result:** Jane Doe's salary updated to 71,500 (65,000 * 1.10)

### 2) INSERT through View

```sql
-- INSERT through view (✓ Allowed)
INSERT INTO ENGINEERING_EMPLOYEES (emp_id, name, salary, dept_id)
VALUES (106, 'Tom Anderson', 70000, 1);

-- Verify insertion
SELECT * FROM EMPLOYEE WHERE emp_id = 106;
```

**Result:** New employee added to EMPLOYEE table

A few questions that may arise are:
#### **Question 1: "Do we need PRIMARY KEY in the view to insert?"**

**Answer:** NOT exactly. You need:

1. **The PRIMARY KEY in the base table** (it's required for that)
2. **Either have a DEFAULT value OR let AUTO_INCREMENT handle it**
3. **Include it in the view** (optional but recommended)

SQL

```
-- ❌ WRONG: View without primary key, but base table needs it
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT title, genre, rental_price
FROM movies
WHERE rental_price > 5.00;

-- ✅ BETTER: Include primary key in view
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT movie_id, title, genre, rental_price
FROM movies
WHERE rental_price > 5.00
WITH CHECK OPTION;
```

---

#### **Question 2: "Do we need all fields where NULL or DEFAULT is not defined?"**

**Answer:** YES, exactly right!

**Rule:** When inserting through a view, you must provide values for:

- ✓ Columns without DEFAULT value
- ✓ Columns that are NOT NULL
- ✓ PRIMARY KEY columns (unless AUTO_INCREMENT)

```MySQL
-- If movies table is:
CREATE TABLE movies (
    movie_id INT PRIMARY KEY AUTO_INCREMENT,  ← AUTO_INCREMENT, optional
    title VARCHAR(100) NOT NULL,               ← Required
    genre VARCHAR(50) DEFAULT 'Unknown',       ← Optional (has default)
    rental_price DECIMAL(5,2) NOT NULL,        ← Required
    release_year INT                           ← Optional (allows NULL)
);

-- Then INSERT must include:
INSERT INTO view_name (title, rental_price, ...)
VALUES ('Movie', 5.99, ...);
-- title and rental_price are required
-- Everything else is optional
```

---
#### **Question 3: "Should it follow the WHERE condition of the view?"**

**Answer:** MUST follow if you use `WITH CHECK OPTION`

**Without WITH CHECK OPTION:**

```mySQL
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT title, genre, rental_price
FROM movies
WHERE rental_price > 5.00;
-- No WITH CHECK OPTION

INSERT INTO EXPENSIVE_MOVIES VALUES ("Budget Movie", "Action", 3.99);
-- ✓ SUCCEEDS but...
-- The row IS inserted into movies table
-- BUT it WON'T appear in the view (WHERE excludes it)
-- Confusing! ❌
```

**With WITH CHECK OPTION:**

```mySQL
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT title, genre, rental_price
FROM movies
WHERE rental_price > 5.00
WITH CHECK OPTION;  ← Add this!

INSERT INTO EXPENSIVE_MOVIES VALUES ("Budget Movie", "Action", 3.99);
-- ❌ FAILS immediately
-- ERROR: Check option failed
-- Clear and safe! ✓
```

### 3) DELETE through View

```sql
-- DELETE through view (✓ Allowed)
DELETE FROM ENGINEERING_EMPLOYEES
WHERE emp_id = 106;

-- Verify deletion
SELECT * FROM EMPLOYEE WHERE emp_id = 106;
```

**Result:** Employee removed from EMPLOYEE table

---

### 4) Non-Updatable View (Aggregate)

```sql
-- This view is NOT updatable (has aggregation)
CREATE VIEW DEPT_AVG_SALARY AS
SELECT dept_id, AVG(salary) AS avg_salary
FROM EMPLOYEE
GROUP BY dept_id;

-- Try to UPDATE (❌ Will FAIL)
UPDATE DEPT_AVG_SALARY
SET avg_salary = 80000
WHERE dept_id = 1;
```

**Error:** Cannot update aggregate view

**Why?** How would the system translate "set average to 80000" into updates on individual employee salaries?

---

### 5) View with NULL Challenge

```sql
-- View missing some columns
CREATE VIEW EMPLOYEE_BASIC AS
SELECT emp_id, name, dept_id
FROM EMPLOYEE;

-- Try to INSERT (⚠️ Problem: missing salary)
INSERT INTO EMPLOYEE_BASIC (emp_id, name, dept_id)
VALUES (107, 'Lisa Green', 2);

-- What happens in base table?
SELECT * FROM EMPLOYEE WHERE emp_id = 107;
```

**Result:**
```
┌────────┬────────────┬─────────┬─────────┬────────────┬───────────┐
│ emp_id │ name       │ salary  │ dept_id │ manager_id │ hire_date │
├────────┼────────────┼─────────┼─────────┼────────────┼───────────┤
│ 107    │ Lisa Green │ NULL    │ 2       │ NULL       │ NULL      │
└────────┴────────────┴─────────┴─────────┴────────────┴───────────┘
```

**Issue:** Missing columns get NULL values, which may violate constraints.

---

### [[WITH CHECK OPTION in Views in MySQL]]

Prevents updates/inserts that would make rows disappear from the view.

```mysql
-- Create view with check option
CREATE VIEW ENGINEERING_EMPLOYEES_SAFE AS
SELECT emp_id, name, salary, dept_id
FROM EMPLOYEE
WHERE dept_id = 1
WITH CHECK OPTION;

-- Try to change dept_id (❌ Will FAIL)
UPDATE ENGINEERING_EMPLOYEES_SAFE
SET dept_id = 2
WHERE emp_id = 102;
```

**Error:** CHECK OPTION violated

**Reason:** Changing dept_id to 2 would remove the row from the view (which shows only dept_id = 1), so it's blocked.

---

## 3. Dropping Views

```sql
-- Drop a view
DROP VIEW IF EXISTS EMPLOYEE_PUBLIC;

-- Drop multiple views
DROP VIEW IF EXISTS EMPLOYEE_PUBLIC, DEPT_SALARY_STATS;
```

---

## 4. Altering Views

MySQL doesn't have `ALTER VIEW`, so you must `CREATE OR REPLACE`:

```mysql
-- Replace existing view
CREATE OR REPLACE VIEW EMPLOYEE_PUBLIC AS
SELECT emp_id, name, dept_id, hire_date, salary  -- Added salary
FROM EMPLOYEE;
```

---

# View Performance Considerations

#### How Views Work Internally

**When you query a view:**

```sql
SELECT * FROM EMPLOYEE_DEPT_INFO WHERE dept_name = 'Engineering';
```

**MySQL internally rewrites it as:**

```sql
SELECT 
    e.emp_id,
    e.name AS employee_name,
    e.salary,
    d.dept_name,
    d.location
FROM EMPLOYEE e
JOIN DEPARTMENT d ON e.dept_id = d.dept_id
WHERE d.dept_name = 'Engineering';  -- Condition pushed down
```

**Key Points:**
- View definition is **substituted** into the query
- No intermediate result stored (unless materialized)
- Conditions are **pushed down** to base tables for efficiency

---

### View Materialization

Some complex views benefit from **materialization** (storing results).

**Standard View (Virtual):**
```sql
CREATE VIEW COMPLEX_REPORT AS
SELECT ...
FROM table1 t1
JOIN table2 t2 ON ...
JOIN table3 t3 ON ...
WHERE ...;
```

**Problem:** Query runs every time you access the view (slow for complex queries)

**Solution:** Materialized View (covered later in this guide)

---

### Practical View Examples

#### Example 6: Customer Loan Summary

```sql
-- Create comprehensive customer view
CREATE VIEW CUSTOMER_LOAN_SUMMARY AS
SELECT 
    c.customer_id,
    c.customer_name,
    c.city,
    c.credit_score,
    COUNT(l.loan_number) AS num_loans,
    COALESCE(SUM(l.amount), 0) AS total_loan_amount
FROM CUSTOMER c
LEFT JOIN BORROWER b ON c.customer_id = b.customer_id
LEFT JOIN LOAN l ON b.loan_number = l.loan_number
GROUP BY c.customer_id, c.customer_name, c.city, c.credit_score;

-- Query the view
SELECT * FROM CUSTOMER_LOAN_SUMMARY
WHERE total_loan_amount > 50000;
```

**Result:**
```
┌─────────────┬──────────��──────┬─────────┬──────────────┬───────────┬───────────────────┐
│ customer_id │ customer_name   │ city    │ credit_score │ num_loans │ total_loan_amount │
├─────────────┼─────────────────┼─────────┼──────────────┼───────────┼───────────────────┤
│ 202         │ Sarah Williams  │ Boston  │ 680          │ 1         │ 100000            │
│ 203         │ David Miller    │ Chicago │ 750          │ 1         │ 75000             │
└─────────────┴─────────────────┴─────────┴──────────────┴───────────┴───────────────────┘
```

---

#### Example 7: View on View (Nested Views)

```sql
-- First-level view
CREATE VIEW HIGH_SALARY_EMPLOYEES AS
SELECT emp_id, name, salary, dept_id
FROM EMPLOYEE
WHERE salary > 70000;

-- Second-level view (based on first view)
CREATE VIEW HIGH_SALARY_ENGINEERING AS
SELECT hse.emp_id, hse.name, hse.salary, d.dept_name
FROM HIGH_SALARY_EMPLOYEES hse
JOIN DEPARTMENT d ON hse.dept_id = d.dept_id
WHERE d.dept_name = 'Engineering';

-- Query nested view
SELECT * FROM HIGH_SALARY_ENGINEERING;
```

**Result:**
```
┌────────┬──────────���────┬────────┬─────────────┐
│ emp_id │ name          │ salary │ dept_name   │
├────────┼───────────────┼────────┼─────────────┤
│ 101    │ John Smith    │ 75000  │ Engineering │
│ 105    │ Charlie Davis │ 95000  │ Engineering │
└────────┴───────────────┴────────┴─────────────┘
```

---

### View Metadata

Check existing views:

```sql
-- Show all views in database
SHOW FULL TABLES WHERE Table_type = 'VIEW';

-- Show view definition
SHOW CREATE VIEW EMPLOYEE_PUBLIC;

-- Query information schema
SELECT 
    TABLE_NAME,
    VIEW_DEFINITION
FROM INFORMATION_SCHEMA.VIEWS
WHERE TABLE_SCHEMA = 'your_database_name';
```
