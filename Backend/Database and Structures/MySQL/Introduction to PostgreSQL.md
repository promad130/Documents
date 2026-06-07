**(Keep in mind and refer to [[Introduction to MySQL]] as well as [[Introduction to Databases and Structures]], as these coverts the theory, here we just cover the syntax)**
***(Check out [[Introduction to Database Migrations]], then [[Introduction to ORMs]] after this.)***

## 1. What is PostgreSQL?

PostgreSQL (often called **Postgres**) is a powerful, open-source **object-relational database management system (ORDBMS)**. It has been actively developed for over 35 years and is known for its reliability, feature robustness, and standards compliance.

Unlike simple relational databases, PostgreSQL supports advanced data types, full ACID compliance, complex queries, foreign keys, triggers, views, transactional integrity, and multiversion concurrency control (MVCC).

---

## 2. PostgreSQL vs MySQL — Why Choose Postgres?

|Feature|PostgreSQL|MySQL|
|---|---|---|
|SQL Standards Compliance|Excellent|Partial|
|ACID Compliance|Full (always)|Full (InnoDB only)|
|JSON Support|Advanced (`jsonb`, indexable)|Basic|
|Full-Text Search|Built-in, powerful|Limited|
|Custom Data Types|Yes|No|
|Window Functions|Full support|Partial (v8+)|
|CTEs (WITH queries)|Full + writable|Read-only (older)|
|Concurrency Model|MVCC (no read locks)|Lock-based|
|Extensibility|High (PostGIS, pg_vector…)|Low|
|Replication|Logical + Streaming|Binary log|
|License|PostgreSQL License (liberal)|GPL / Commercial|

**Key reasons to pick PostgreSQL:**

- You need complex queries, analytics, or reporting.
- Your data has irregular or semi-structured shapes (JSON, arrays, hstore).
- You want strict data integrity without workarounds.
- You plan to use extensions like **PostGIS** (geospatial), **pgvector** (AI embeddings), or **TimescaleDB** (time-series).
- You need better handling of concurrent reads and writes.

---

## 3. Installation on Linux

### Ubuntu / Debian

```bash
# Update package index
sudo apt update

# Install PostgreSQL (installs the latest stable from apt)
sudo apt install -y postgresql postgresql-contrib

# Check the service status
sudo systemctl status postgresql

# Enable PostgreSQL to start on boot
sudo systemctl enable postgresql

# Start if not already running
sudo systemctl start postgresql
```

### RHEL / Fedora / CentOS

```bash
# Install from the official PostgreSQL repo (example: PG 16 on RHEL 9)
sudo dnf install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-9-x86_64/pgdg-redhat-repo-latest.noarch.rpm
sudo dnf -qy module disable postgresql
sudo dnf install -y postgresql16-server postgresql16-contrib

# Initialize the database cluster
sudo /usr/pgsql-16/bin/postgresql-16-setup initdb

# Enable and start
sudo systemctl enable postgresql-16
sudo systemctl start postgresql-16
```

### Post-Install: First Login

PostgreSQL creates a default OS user called `postgres`. Switch to it to access the database shell (`psql`):

```bash
sudo -i -u postgres
psql
```

Or directly:

```bash
sudo -u postgres psql
```

### Create a Database and User

```sql
-- Inside psql
CREATE USER myuser WITH PASSWORD 'strongpassword';
CREATE DATABASE mydb OWNER myuser;
GRANT ALL PRIVILEGES ON DATABASE mydb TO myuser;
```

Exit `psql` with `\q`.

### Useful psql Meta-Commands

|Command|Description|
|---|---|
|`\l`|List all databases|
|`\c dbname`|Connect to a database|
|`\dt`|List tables in current schema|
|`\d tablename`|Describe a table|
|`\du`|List users/roles|
|`\q`|Quit|
|`\i file.sql`|Execute a SQL file|

---

## 4. Connecting with DBeaver

**DBeaver** is a free, cross-platform database GUI that works beautifully with PostgreSQL.

### Install DBeaver (Ubuntu)

```bash
# Download and install the .deb package
sudo apt install -y wget
wget https://dbeaver.io/files/dbeaver-ce_latest_amd64.deb
sudo dpkg -i dbeaver-ce_latest_amd64.deb
sudo apt --fix-broken install   # fix any dependency issues
```

Or via Snap:

```bash
sudo snap install dbeaver-ce
```

### Connect DBeaver to PostgreSQL

1. Open DBeaver → click **New Database Connection** (plug icon).
2. Select **PostgreSQL** → click Next.
3. Fill in the connection details:

|Field|Value|
|---|---|
|Host|`localhost`|
|Port|`5432`|
|Database|`mydb`|
|Username|`myuser`|
|Password|`strongpassword`|

4. Click **Test Connection** — DBeaver will prompt to download the JDBC driver automatically.
5. Click **Finish**.

> **Tip:** If connecting to a remote server, make sure `postgresql.conf` has `listen_addresses = '*'` and `pg_hba.conf` allows your IP with md5 or scram-sha-256 authentication. Restart PostgreSQL after changes.

---

## 5. SQL Fundamentals in PostgreSQL

All examples below use a sample schema:

```sql
-- Create tables for examples
CREATE TABLE departments (
    dept_id   SERIAL PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    emp_id    SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) UNIQUE,
    salary     NUMERIC(10, 2),
    dept_id    INT REFERENCES departments(dept_id),
    hire_date  DATE DEFAULT CURRENT_DATE,
    is_active  BOOLEAN DEFAULT TRUE
);

CREATE TABLE projects (
    project_id   SERIAL PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    budget       NUMERIC(12, 2),
    dept_id      INT REFERENCES departments(dept_id)
);
```

---

## 6. SELECT

### Basic Syntax

```sql
SELECT column1, column2, ...
FROM   table_name
WHERE  condition
ORDER  BY column [ASC | DESC]
LIMIT  n
OFFSET n;
```

### Examples

```sql
-- Select all columns
SELECT * FROM employees;

-- Select specific columns
SELECT first_name, last_name, salary
FROM   employees;

-- Filter with WHERE
SELECT first_name, salary
FROM   employees
WHERE  salary > 60000;

-- Multiple conditions
SELECT *
FROM   employees
WHERE  salary BETWEEN 50000 AND 90000
  AND  is_active = TRUE;

-- Pattern matching with LIKE
SELECT * FROM employees
WHERE  email LIKE '%@gmail.com';

-- IN operator
SELECT * FROM employees
WHERE  dept_id IN (1, 3, 5);

-- IS NULL check
SELECT * FROM employees
WHERE  dept_id IS NULL;

-- ORDER BY and LIMIT
SELECT first_name, salary
FROM   employees
ORDER  BY salary DESC
LIMIT  5;

-- DISTINCT values
SELECT DISTINCT dept_id FROM employees;

-- Column alias
SELECT
    first_name || ' ' || last_name AS full_name,
    salary * 12                    AS annual_salary
FROM employees;

-- Aggregate functions
SELECT
    COUNT(*)        AS total_employees,
    AVG(salary)     AS avg_salary,
    MAX(salary)     AS highest_salary,
    MIN(salary)     AS lowest_salary,
    SUM(salary)     AS payroll
FROM employees
WHERE is_active = TRUE;

-- GROUP BY
SELECT   dept_id, COUNT(*) AS headcount, AVG(salary) AS avg_sal
FROM     employees
GROUP BY dept_id
ORDER BY avg_sal DESC;

-- HAVING (filter on aggregates)
SELECT   dept_id, COUNT(*) AS headcount
FROM     employees
GROUP BY dept_id
HAVING   COUNT(*) > 5;
```

### Subquery

```sql
-- Employees earning above the company average
SELECT first_name, salary
FROM   employees
WHERE  salary > (SELECT AVG(salary) FROM employees);
```

### CTE (Common Table Expression)

```sql
WITH high_earners AS (
    SELECT * FROM employees WHERE salary > 80000
)
SELECT first_name, last_name, salary
FROM   high_earners
ORDER  BY salary DESC;
```

### Window Functions

```sql
-- Rank employees by salary within each department
SELECT
    first_name,
    dept_id,
    salary,
    RANK() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS dept_rank
FROM employees;

-- Running total of salary
SELECT
    emp_id,
    first_name,
    salary,
    SUM(salary) OVER (ORDER BY emp_id) AS running_total
FROM employees;
```

---

## 7. INSERT

### Basic Syntax

```sql
INSERT INTO table_name (column1, column2, ...)
VALUES (value1, value2, ...);
```

### Examples

```sql
-- Insert a single row
INSERT INTO departments (dept_name)
VALUES ('Engineering');

-- Insert multiple rows at once
INSERT INTO departments (dept_name)
VALUES
    ('Marketing'),
    ('Human Resources'),
    ('Finance');

-- Insert and return the generated ID
INSERT INTO employees (first_name, last_name, email, salary, dept_id)
VALUES ('Alice', 'Kumar', 'alice@example.com', 75000, 1)
RETURNING emp_id, first_name;

-- Insert from a SELECT (copy rows)
INSERT INTO employees (first_name, last_name, email, salary, dept_id)
SELECT first_name, last_name, email, salary * 1.10, dept_id
FROM   employees
WHERE  dept_id = 2;

-- Upsert: INSERT ... ON CONFLICT
-- If the email already exists, update the salary instead of failing
INSERT INTO employees (first_name, last_name, email, salary, dept_id)
VALUES ('Alice', 'Kumar', 'alice@example.com', 82000, 1)
ON CONFLICT (email)
DO UPDATE SET salary = EXCLUDED.salary;

-- ON CONFLICT DO NOTHING (silently ignore duplicates)
INSERT INTO departments (dept_name)
VALUES ('Engineering')
ON CONFLICT DO NOTHING;
```

---

## 8. UPDATE

### Basic Syntax

```sql
UPDATE table_name
SET    column1 = value1,
       column2 = value2, ...
WHERE  condition;
```

> ⚠️ **Always include a WHERE clause** unless you intentionally want to update every row.

### Examples

```sql
-- Update a single column
UPDATE employees
SET    salary = 80000
WHERE  emp_id = 3;

-- Update multiple columns
UPDATE employees
SET    salary    = 90000,
       is_active = TRUE
WHERE  email = 'alice@example.com';

-- Update using an expression
UPDATE employees
SET    salary = salary * 1.05   -- 5% raise for all
WHERE  dept_id = 1;

-- Update with RETURNING (see what changed)
UPDATE employees
SET    salary = salary + 5000
WHERE  dept_id = 2
RETURNING emp_id, first_name, salary;

-- Update based on another table (correlated update)
UPDATE employees AS e
SET    dept_id = d.dept_id
FROM   departments AS d
WHERE  d.dept_name = 'Engineering'
  AND  e.last_name = 'Sharma';
```

---

## 9. DELETE

### Basic Syntax

```sql
DELETE FROM table_name
WHERE  condition;
```

> ⚠️ **Always include a WHERE clause** unless you want to delete every row.  
> Use `TRUNCATE` for fast full-table wipes.

### Examples

```sql
-- Delete a specific row
DELETE FROM employees
WHERE  emp_id = 10;

-- Delete with a condition
DELETE FROM employees
WHERE  is_active = FALSE
  AND  hire_date < '2020-01-01';

-- Delete and return deleted rows
DELETE FROM employees
WHERE  dept_id = 4
RETURNING emp_id, first_name;

-- Delete using another table (similar to a JOIN)
DELETE FROM employees AS e
USING  departments AS d
WHERE  e.dept_id = d.dept_id
  AND  d.dept_name = 'Marketing';

-- TRUNCATE: instantly remove all rows (much faster than DELETE for large tables)
TRUNCATE TABLE employees;

-- TRUNCATE and reset sequences (auto-increment counters)
TRUNCATE TABLE employees RESTART IDENTITY;
```

---

## 10. JOINs

JOINs combine rows from two or more tables based on a related column.

```
departments          employees
-----------          ---------
dept_id  <---------  dept_id (FK)
dept_name            emp_id
                     first_name
                     salary
```

---

### INNER JOIN

Returns only rows where there is a match in **both** tables.

```sql
SELECT
    e.first_name,
    e.last_name,
    d.dept_name,
    e.salary
FROM  employees  AS e
INNER JOIN departments AS d ON e.dept_id = d.dept_id;
```

---

### LEFT JOIN (LEFT OUTER JOIN)

Returns **all rows from the left table** and matched rows from the right. Unmatched right rows are NULL.

```sql
-- Shows ALL employees, even those with no department assigned
SELECT
    e.first_name,
    d.dept_name   -- NULL if employee has no dept_id
FROM  employees  AS e
LEFT  JOIN departments AS d ON e.dept_id = d.dept_id;
```

---

### RIGHT JOIN (RIGHT OUTER JOIN)

Returns **all rows from the right table** and matched rows from the left. Unmatched left rows are NULL.

```sql
-- Shows ALL departments, even those with no employees
SELECT
    d.dept_name,
    e.first_name  -- NULL if dept has no employees
FROM  employees  AS e
RIGHT JOIN departments AS d ON e.dept_id = d.dept_id;
```

---

### FULL OUTER JOIN

Returns all rows from **both** tables. NULLs appear where there is no match on either side.

```sql
SELECT
    e.first_name,
    d.dept_name
FROM  employees  AS e
FULL  OUTER JOIN departments AS d ON e.dept_id = d.dept_id;
```

---

### CROSS JOIN

Returns the **Cartesian product** — every combination of rows from both tables. Use with caution.

```sql
-- Every employee paired with every department (rarely useful as-is)
SELECT e.first_name, d.dept_name
FROM   employees  AS e
CROSS  JOIN departments AS d;
```

---

### SELF JOIN

Joining a table to **itself**. Useful for hierarchical or comparative data.

```sql
-- Example: find employees who share the same department
SELECT
    a.first_name AS emp1,
    b.first_name AS emp2,
    a.dept_id
FROM  employees AS a
JOIN  employees AS b ON a.dept_id = b.dept_id
                    AND a.emp_id  < b.emp_id;
```

---

### Multi-table JOIN

```sql
-- Employees → Department → Projects
SELECT
    e.first_name,
    e.last_name,
    d.dept_name,
    p.project_name,
    p.budget
FROM       employees   AS e
INNER JOIN departments AS d ON e.dept_id    = d.dept_id
INNER JOIN projects    AS p ON p.dept_id    = d.dept_id
WHERE      e.salary > 70000
ORDER BY   p.budget DESC;
```

---

### JOIN with Aggregation

```sql
-- Department headcount and average salary
SELECT
    d.dept_name,
    COUNT(e.emp_id)  AS headcount,
    ROUND(AVG(e.salary), 2) AS avg_salary
FROM       departments AS d
LEFT  JOIN employees   AS e ON e.dept_id = d.dept_id
GROUP BY   d.dept_name
ORDER BY   headcount DESC;
```

---

## 11. Quick Reference Cheat Sheet

```sql
-- SELECT
SELECT col1, col2 FROM tbl WHERE cond ORDER BY col LIMIT n;

-- INSERT
INSERT INTO tbl (col1, col2) VALUES (v1, v2) RETURNING id;

-- UPSERT
INSERT INTO tbl (col) VALUES (v) ON CONFLICT (col) DO UPDATE SET col = EXCLUDED.col;

-- UPDATE
UPDATE tbl SET col = val WHERE cond RETURNING *;

-- DELETE
DELETE FROM tbl WHERE cond RETURNING *;

-- INNER JOIN
SELECT * FROM a JOIN b ON a.id = b.a_id;

-- LEFT JOIN
SELECT * FROM a LEFT JOIN b ON a.id = b.a_id;

-- FULL OUTER JOIN
SELECT * FROM a FULL OUTER JOIN b ON a.id = b.a_id;
```

---

## 12. Helpful PostgreSQL-Specific Features

```sql
-- Array columns
SELECT ARRAY[1, 2, 3];
SELECT '{apple, banana}'::TEXT[];

-- JSON / JSONB
SELECT '{"name": "Alice"}'::jsonb ->> 'name';  -- returns "Alice"

-- Generate series (great for testing)
SELECT generate_series(1, 10) AS num;

-- Current timestamp
SELECT NOW();
SELECT CURRENT_DATE;

-- String functions
SELECT UPPER('hello'), LOWER('WORLD'), LENGTH('postgres');
SELECT TRIM('  spaces  '), REPLACE('foo bar', 'bar', 'baz');

-- Type casting
SELECT '42'::INTEGER;
SELECT 3.14::TEXT;
SELECT '2024-01-15'::DATE;

-- EXPLAIN for query planning
EXPLAIN ANALYZE SELECT * FROM employees WHERE dept_id = 1;
```

---

_Notes generated for PostgreSQL 15/16 — syntax is compatible with PG 12+._