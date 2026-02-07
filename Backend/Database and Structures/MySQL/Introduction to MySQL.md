A **database** is just an organized place in a computer where related data is stored so it can be found, changed, and used efficiently later.[^5]

## What is data?

- Data is raw facts: numbers, text, dates, etc. (e.g., a user’s name, email, order amount).[^9]
- When you organize data in a useful structure (like a table with rows and columns), it becomes easy to search, filter, and update.[^7]


## What is a database?

- A **database** is an organized collection of related data, usually stored electronically on a computer.[^5]
- Most modern databases store data in tables (rows = records, columns = fields), similar to an Excel sheet but much more powerful.[^7]


## What is a DBMS?

- A **Database Management System (DBMS)** is software that lets you create, store, modify, and retrieve data from a database. Examples: **MySQL**, PostgreSQL, Oracle.
- A DBMS handles storage, security, backup, concurrent access, and performance, so applications and users don’t touch raw files directly.


## What is a database system?

- A **database system** = database (data) + DBMS (software) + applications that use it (e.g., your web app, analytics scripts).
- When people casually say “database,” they often mean this whole system together.


## What is SQL and MySQL?

- **SQL** (Structured Query Language) is a language used to talk to relational databases: create tables, insert data, query, update, delete.
- **MySQL** is a specific relational DBMS that uses SQL; it’s one of the most widely used open‑source database systems in web apps.


# The Syntax
### Conventions used:
- Keywords in syntax are shown in bold letters.
- Words in syntax between `‘<’` and `‘>’` signify the name of the table or column given by the user.
- Words in syntax between `‘[’` and `‘]’` are optional.

### NOTE: 
1) Every SQL statement ends with a semicolon.
2) SQL keywords are not case sensitive, but the data in the table and the name of the table are case sensitive.
3) It is a good practice to write the query with line separator to make error detection more
convenient.

# Types of MySQL Commands

There are three types of MySQL Commands:

## Data Definition Language (DDL) Commands:

This is working, changing and managing the defination of the data in the database.

### CREATE DATABASE;

Syntax to create a database:

```MySQL
CREATE DATABASE <database_name>
```

Examples;
```mysql
mysql> create database mydb;
```


### SHOW DATABASE;

lists all the available database in the severer you connected to:

```mysql
show databases;
```


### USE;

This command is used to use a database, i.e, we mention which database to make changes to:

```mysql
use <database_name>;
```

example:

```mysql
mysql> USE mydab;
```


### SHOW TABLES;

Lists all the tables that are there in the current database that is being used:

```mysql
SHOW TABLES;
```


### CREATE TABLE;

Syntax to create a table:

```mysql
create table <TableName> (column defination1, column defination2, ...); 
```

Example:

```mysql
mysql> CREATE TABLE students ( id CHARACTER (12),
	name VARCHAR(30),
	hostel INTEGER NOT NULL,
	percentage DECIMAL(5,2) DEFAULT 0.0,
	phone INT,
	bdate DATE,
	gender ENUM('F','M'),
	CONSTRAINT uk UNIQUE(name)
);
```

Here we have used [Data Types offered in MySQL](#The%20Data%20Types).


### DESC / DESCRIBE;
`DESC` displays information about a table's columns **without showing the actual data** stored in rows. It's like viewing the blueprint of your table.

Syntax:

```mysql
DESC table_name;
-- or
DESCRIBE table_name;
```

Example:

```mysql
DESC students;
```
**Output:**
```mysql
+------------+--------------+------+-----+---------+-------+
| Field      | Type         | Null | Key | Default | Extra |
+------------+--------------+------+-----+---------+-------+
| id         | char(12)     | NO   | PRI | NULL    |       |
| name       | varchar(30)  | YES  |     | NULL    |       |
| hostel     | int          | NO   |     | NULL    |       |
| percentage | decimal(5,2) | YES  |     | 0.00    |       |
| phone      | int          | YES  | UNI | NULL    |       |
+------------+--------------+------+-----+---------+-------+
```


### ALTER TABLE Commands

#### 1. MODIFY – Change Column Data Type or Attributes

Changes the data type or properties of an existing column **without renaming it**.

```mysql
ALTER TABLE students MODIFY percentage DECIMAL(10,2);
```

Changes `percentage` from `DECIMAL(5,2)` to `DECIMAL(10,2)`.

#### 2. RENAME – Rename the Table

Gives the table a new name.

```mysql
ALTER TABLE students RENAME gradstudents;
```

Renames the `students` table to `gradstudents`.

#### 3. ADD COLUMN – Add a New Column

Adds a new column to the table.

```mysql
ALTER TABLE students ADD COLUMN email VARCHAR(100);
```

Adds an `email` column to the `students` table.

#### 4. CHANGE – Rename a Column

Renames a column; you must specify the old name, new name, and full column definition.

```mysql
ALTER TABLE students CHANGE phone mobile_number INT;
```

Renames `phone` to `mobile_number` while keeping it as `INT`.

#### 5. DROP COLUMN – Delete a Column

Permanently removes a column and all its data.

```mysql
ALTER TABLE students DROP COLUMN bdate;
```

Deletes the `bdate` column from the table.

#### 6. ADD CONSTRAINT – Add a New Constraint

Adds a constraint like UNIQUE, CHECK, or FOREIGN KEY to an existing table.[^2][^3]

```mysql
ALTER TABLE students ADD CONSTRAINT email_unique UNIQUE(email);
```

Makes the `email` column unique.

#### 7. DROP CONSTRAINT – Remove a Constraint

Removes a named constraint from the table.

```mysql
ALTER TABLE students DROP CONSTRAINT email_unique;
```

Drops the UNIQUE constraint on `email`.


### TRUNCATE TABLE

**TRUNCATE** removes **all rows** from a table but keeps the table structure (columns, data types, constraints) intact.  It's much faster than `DELETE` because it doesn't log each row deletion—it just deallocates the entire data pages.[^1][^2][^3]

**Syntax:**

```sql
TRUNCATE TABLE <table_name>;
```

**Example:**

```sql
-- Remove all rows from gradstudents table
TRUNCATE TABLE gradstudents;

-- Check if table still exists (structure remains)
SELECT * FROM gradstudents;
```

**What happens:**

- All data rows are deleted instantly[^1]
- Table structure (columns, constraints, indexes) remains[^4][^3]
- Auto-increment counters reset to starting value[^2]
- Much faster than `DELETE FROM gradstudents;`[^2][^1]
- **Cannot undo** (no rollback in most cases)[^1][^2]
- Cannot use `WHERE` clause—deletes everything (unlike in [[#UPDATE Command]])

**When to use:** Clearing test data, resetting tables for fresh data load, emptying large tables quickly.


### DROP TABLE

**DROP TABLE** completely **removes the table** from the database—both structure and data are gone permanently.[^6][^4]

**Syntax:**

```sql
DROP TABLE <table_name>;
```

**Example:**

```sql
-- Delete the entire gradstudents table
DROP TABLE gradstudents;

-- Verify table is gone
SHOW TABLES;
```

**What happens:**

- Table structure deleted (columns, constraints, indexes)
- All data deleted
- Table no longer exists—trying to query it causes an error
- Frees all space used by the table
- Cannot undo without a backup

**When to use:** Permanently removing obsolete tables, cleanup during database restructuring.


### DROP DATABASE

**DROP DATABASE** deletes an **entire database** including all its tables, views, stored procedures, and data.

**Syntax:**

```sql
DROP DATABASE <database_name>;
```

**Example:**

```sql
-- Delete the entire mydb database
DROP DATABASE mydb;

-- Check remaining databases
SHOW DATABASES;
```

**What happens:**

- Entire database and everything inside it is permanently deleted[^6]
- All tables, views, procedures, functions removed[^6]
- Database no longer exists[^6]
- Cannot undo without backup[^6]

**When to use:** Removing test/development databases, cleaning up after project completion.[^6]


CREATE INDEX
CREATE VIEW


- ## Data Manipulation Language (DML) Commands:

This is Manipulation the data that is there in the tables in the active database.

### INSERT INTO;

`INSERT INTO` is a **DML (Data Manipulation Language)** command that adds new rows (records) to a table.

#### Basic Syntax

**Two ways to insert:**

**1. Specify column names (recommended):**

```sql
INSERT INTO table_name (column1, column2, column3) 
VALUES (value1, value2, value3);
```

**2. Omit column names (insert all columns in order):**

```sql
INSERT INTO table_name 
VALUES (value1, value2, value3);
```

- You must provide values for **all columns** in the exact order they were defined in the table.
- **You MUST provide a value for EVERY column** - even columns with DEFAULT values or NULL constraints
- If you don't wish to give or provide default values, then have to mention `DEFAULT` keyword.

#### Examples

**Valid insert with all columns:**

```sql
INSERT INTO students VALUES ('PS99305017', 'Mohan Sharma', 13, 76.23, '9800000002', '2001-03-15', 'M');
```

Order matches: `id, name, hostel, percentage, phone, bdate, gender`.

**Valid insert with column names (order doesn't matter):**

```sql
INSERT INTO students (id, name, hostel, percentage, phone, bdate, gender) 
VALUES ('PS99305017', 'Sai Sundar', 11, 77.23, '9800000001', '2001-01-25', 'M');
```


#### Common Errors and Why They Happen

##### 1. Wrong Column Order

```sql
-- WRONG ORDER
INSERT INTO students VALUES ('Sai Sundar', 'PS99305017', 11, 77.23, '2001-01-25', '9800000001', 'M');
```

**Error:** Values don't match column types—`name` (VARCHAR) is in the `id` (CHAR) position.  Always match the table's column order or specify column names explicitly.[^1]

##### 2. Duplicate UNIQUE Value

```sql
-- Violates UNIQUE constraint on phone
INSERT INTO students (name, id, hostel, percentage, bdate, phone, gender) 
VALUES ('Jay Singh', 'PS99305012', 11, 83.73, '2000-07-04', '9900000002', 'M');
```

**Error if `9900000002` already exists:** UNIQUE constraint prevents duplicate phone numbers.[^2]

##### 3. Empty/NULL in PRIMARY KEY

```sql
-- Empty string in PRIMARY KEY
INSERT INTO students VALUES ('', 'Shyam Sundar', 11, 90.23, '9800000004', '2001-01-25', 'M');
```

**Error:** PRIMARY KEY cannot be empty or NULL.[^2]

##### 4. NULL vs Empty String in UNIQUE Column

```sql
-- NULL in name (UNIQUE column)
INSERT INTO students VALUES ('PS99305018', NULL, 11, 90.23, '9800000009', '2001-01-25', 'M');

-- Empty string in name
INSERT INTO students VALUES ('PS99305018', '', 11, 90.23, '9800000009', '2001-01-25', 'M');
```

**Both work once, but:** You can insert **multiple NULLs** (NULLs aren't considered duplicates), but only **one empty string** (empty strings are duplicates of each other).

##### 5. NULL in NOT NULL Column

```sql
-- Violates NOT NULL constraint on hostel
INSERT INTO students VALUES ('PS99305020', 'Sundaram', NULL, 90.23, '9800000005', '2001-01-25', 'M');
```

**Error:** `hostel` is defined as `NOT NULL`, so it must have a value.[^2]

##### 6. Missing Quotes for String/Date Types

```sql
-- Missing quotes around CHAR/VARCHAR
INSERT INTO students VALUES (PS99305020, 'Sundaram', 11, 90.23, 9800000006, '2001-01-25', 'M');
```

**Error:** `PS99305020` and `9800000006` are treated as column names, not values. Strings/dates need quotes: `'PS99305020'` and `'9800000006'`.[^2]

##### 7. Exceeding Column Size

```sql
-- name is VARCHAR(30), but value is 35 characters
INSERT INTO students VALUES ('PS99305021', 'Ram Prabhu Sundaran', 11, 90.23, '9800000006', '2001-01-25', 'M');
```

**Error:** Value exceeds the defined length of `VARCHAR(30)`. MySQL truncates or rejects it depending on strict mode.[^2]

##### 8. Invalid Date

```sql
-- February doesn't have 30 days
INSERT INTO students VALUES ('PS99305023', 'Ramnarayan Sundaran', 11, 90.23, '9800000006', '2001-02-30', 'M');
```

**Error:** `2001-02-30` is not a valid date—February has max 28/29 days.[^2]

##### 9. Invalid ENUM Value

```sql
-- gender is ENUM('F','M'), but 'K' is not allowed
INSERT INTO students VALUES ('PS99305025', 'Narayan Sundar', 11, 90.23, '9800000007', '2001-02-16', 'K');
```

**Error:** `'K'` is not in the allowed ENUM values `('F','M')`.

### Multiple Rows Insert

```sql
INSERT INTO students (id, name, hostel) 
VALUES 
  ('PS001', 'John', 10),
  ('PS002', 'Jane', 12),
  ('PS003', 'Mike', 14);
```

Inserts three rows in a single statement—more efficient than three separate INSERTs.



### UPDATE Command

`UPDATE` modifies **existing rows** in a table.

**Syntax:**

```sql
UPDATE <table_name> 
SET (column1 = value1, column2 = value2, ...) 
WHERE <condition>;
```

**Always use `WHERE` to specify which rows to update.** Without `WHERE`, **all rows** in the table will be updated.[^5]

#### Examples

**Update a single column for specific rows:**

```sql
UPDATE students 
SET percentage = 85.50 
WHERE id = 'PS99305017';
```

Changes the `percentage` to `85.50` only for student with `id = 'PS99305017'`.[^5]

**Update multiple columns:**

```sql
UPDATE students 
SET percentage = 90.00, hostel = 15 
WHERE name = 'Mohan Sharma';
```

Updates both `percentage` and `hostel` for student named `Mohan Sharma`.[^5]

**Update all rows (dangerous!):**

```sql
UPDATE students 
SET percentage = 0.0;
```

**Warning:** Sets `percentage = 0.0` for **every student** because there's no `WHERE` clause.[^5]

**Update based on calculation:**

```sql
UPDATE students 
SET percentage = percentage + 5.0 
WHERE hostel = 11;
```

Adds 5 points to `percentage` for all students in hostel 11.[^5]


### DELETE Command

`DELETE` removes **rows** from a table.

**Syntax:**

```sql
DELETE FROM table_name 
WHERE condition;
```

**Always use `WHERE` to specify which rows to delete.** Without `WHERE`, **all rows** will be deleted (table structure remains, but data is gone).[^5]

#### Examples

**Delete specific rows:**

```sql
DELETE FROM students 
WHERE id = 'PS99305017';
```

Removes the student with `id = 'PS99305017'`.[^5]

**Delete multiple rows matching condition:**

```sql
DELETE FROM students 
WHERE percentage < 50.0;
```

Deletes all students with percentage below 50.[^5]

**Delete all rows (dangerous!):**

```sql
DELETE FROM students;
```

**Warning:** Deletes **every row** in the table, but the table structure still exists.[^5]

**Delete based on NULL:**

```sql
DELETE FROM students 
WHERE phone IS NULL;
```

Removes all students with no phone number.


## Data Query Language (DQL) Commands:

### SELECT

Command used to retrieve data from a table.  It doesn't modify data—it just reads and displays it.

#### Basic Syntax

```sql
SELECT column, another_column, … 
FROM mytable 
WHERE _condition_ 
	AND/OR _another_condition_ 
	AND/OR …
;
```


#### 1. Select All Rows and All Columns

Use `*` (asterisk) to retrieve **every column** from the table.

```sql
SELECT * FROM students;
```

**What this shows:**

- All columns: `id, name, hostel, percentage, phone, bdate, gender`
- All rows in the table

**Observations from your data:**

- Student with `id = PS99305023` has `bdate` as the default value (likely `NULL` or `'0000-00-00'`)[^3]
- Student with `id = PS99305019` has `percentage = 0.0` (the user-defined default)[^3]
- Student with `id = PS99305025` has blank `gender` (empty string `''` or `NULL`)[^3]


#### 2. Select Specific Columns

List only the column names you want to see, separated by commas.

**Single column:**

```sql
SELECT id FROM students;
```

Returns only the `id` column for all rows.

**Multiple columns:**

```sql
SELECT id, name FROM students;
```

Returns only `id` and `name` columns in that order.

#### 3. Select Distinct Rows (Remove Duplicates)

Use `DISTINCT` to return only **unique values** in a column—duplicates are removed.[^2][^3]

```sql
SELECT DISTINCT name FROM students;
```

**What this does:**

- If multiple students have the same name, it shows that name only **once**.[^2]
- Without `DISTINCT`, duplicate names would appear multiple times.[^2]

**Example output without DISTINCT:**

```
name
-----------
Mohan
Mohan
Sai
```

**Example output with DISTINCT:**

```
name
-----------
Mohan
Sai
```


### ORDER BY clause in SELECT:

The `ORDER BY` clause sorts the result set in **ascending** or **descending** order.

**Syntax:**

```sql
SELECT column1, column2, ...
FROM table_name
ORDER BY column1 [ASC|DESC], column2 [ASC|DESC];
```

- **ASC** = ascending order (default, optional)
- **DESC** = descending order


#### Sorting in Ascending Order

By default, `ORDER BY` sorts in **ascending order**, so `ASC` is optional.

```sql
-- Sort students by name (A to Z)
SELECT * FROM students
ORDER BY name;

-- Same as above (ASC is implicit)
SELECT * FROM students
ORDER BY name ASC;
```

```sql
-- Sort by percentage (lowest to highest)
SELECT id, name, percentage
FROM students
ORDER BY percentage;
```


#### Sorting in Descending Order

Use `DESC` keyword for descending order.

```sql
-- Sort students by percentage (highest to lowest)
SELECT * FROM students
ORDER BY percentage DESC;
```

```sql
-- Sort by birth date (newest to oldest)
SELECT name, bdate
FROM students
ORDER BY bdate DESC;
```


#### Sorting by Multiple Columns

You can sort by multiple columns—MySQL sorts by the first column, then breaks ties using the second column, and so on.

```sql
-- Sort by hostel (ascending), then by name (ascending)
SELECT name, hostel, percentage
FROM students
ORDER BY hostel, name;
```

```sql
-- Sort by hostel (ascending), then by percentage (descending)
SELECT name, hostel, percentage
FROM students
ORDER BY hostel ASC, percentage DESC;
```



# Data types in mySQL

MySQL groups data types into five main categories: numeric, string, date/time, spatial, and JSON.

## Numeric Data Types

Used for numbers, IDs, prices, counts.

- **INT** – standard integer, range −2,147,483,648 to 2,147,483,647 (4 bytes). Use for IDs, counters.
- **TINYINT** – tiny integer, range −128 to 127 (or 0 to 255 unsigned), 1 byte.[^1][^2]
- **BIGINT** – huge integers, range up to ±9 quintillion, 8 bytes.[^1][^2]
- **DECIMAL(p, s)** – exact fixed-point number (e.g., `DECIMAL(10,2)` for money with 2 decimal places).[^2][^1]
- **FLOAT / DOUBLE** – approximate floating-point for scientific calculations.
- **BOOLEAN** – implemented as `TINYINT(1)`, stores 0 (false) or 1 (true).


## String Data Types

Store text, descriptions, names.

- **VARCHAR(n)** – variable-length string up to *n* characters (max 65,535). Use for names, emails.
- **CHAR(n) or CHARACTER(n)** – fixed-length string exactly *n* characters (padded with spaces). Good for fixed codes like country codes.
- **TEXT** – long text up to 65,535 characters. Variants: `TINYTEXT`, `MEDIUMTEXT`, `LONGTEXT`.
- **BLOB** – binary large object for images, files. Variants: `TINYBLOB`, `MEDIUMBLOB`, `LONGBLOB`.
- **ENUM** – column can hold one of a predefined list (e.g., `ENUM('small','medium','large')`).
	- MySQL **ENUM values are case-insensitive by default**, so `'f'` and `'F'` are treated as the **same value**. 
	- When you try to create an ENUM with duplicate values, MySQL throws an error in strict SQL mode.


## Date and Time Data Types

Store dates, timestamps, durations.

- **DATE** – date only, format `YYYY-MM-DD`, range 1000-01-01 to 9999-12-31.[^1][^2]
- **DATETIME** – date + time, format `YYYY-MM-DD HH:MM:SS`, range 1000-01-01 to 9999-12-31.[^1][^2]
- **TIMESTAMP** – auto-updates to current time on row insert/update; range 1970 to 2038 (UTC).[^2][^1]
- **TIME** – time only, format `HH:MM:SS`.[^1][^2]
- **YEAR** – 4-digit year, 1901 to 2155.[^1]


## JSON Data Type

Stores JSON documents natively; supports efficient querying and indexing of JSON keys.[^2]

## Spatial Data Types

Store geographic and geometric data like points, lines, polygons (GEOMETRY, POINT, LINESTRING, POLYGON).

**Example table using different data types:**

```sql
CREATE TABLE users (
  id INT PRIMARY KEY,
  name VARCHAR(100),
  age TINYINT,
  balance DECIMAL(10,2),
  created_at TIMESTAMP,
  is_active BOOLEAN
);
```


# Operators in MySQL:

## MySQL Operators

MySQL operators are symbols used to perform operations on data in queries. They help filter, compare, calculate, and manipulate data when retrieving or modifying database records.[^1][^2]

## Types of Operators

### Comparison Operators

Used to compare values and return TRUE or FALSE:[^1]

- `=` Equal to
- `>` Greater than
- `<` Less than
- `>=` Greater than or equal to
- `<=` Less than or equal to
- `<>` or `!=` Not equal to

**Example:**

```sql
SELECT name, percentage FROM students WHERE percentage > 90.6;
SELECT id, name FROM students WHERE hostel != 10;
```


### Arithmetic Operators

Used for mathematical calculations:[^2]

- `+` Addition
- `-` Subtraction
- `*` Multiplication
- `/` Division
- `%` Modulus (remainder)

**Example:**

```sql
SELECT percentage + 10.0 FROM students WHERE hostel = 10;
```


### Logical Operators

Used to combine multiple conditions:[^2][^1]

- `AND` - TRUE if all conditions are true
- `OR` - TRUE if any condition is true
- `NOT` - Reverses the result

**Example:**

```sql
SELECT id, name FROM students WHERE percentage > 90.6 AND hostel = 10;
SELECT id, name FROM students WHERE percentage = 60.0 OR percentage = 90.6;
SELECT id, name FROM students WHERE percentage > 90.6 AND NOT hostel = 10;
```


### Special Comparison Operators

**BETWEEN** - Checks if value is within a range:[^3]

```sql
SELECT id, name FROM students WHERE percentage BETWEEN 60.0 AND 90.6;
```

This is equivalent to `percentage >= 60.0 AND percentage <= 90.6`.[^3][^4]

**IN** - Checks if value matches any in a list:[^3]

```sql
SELECT * FROM students WHERE hostel IN (10, 11, 13);
```

**LIKE** - Pattern matching with wildcards:[^4][^3]

- `%` matches any sequence of characters (including zero)
- `_` matches exactly one character

**Examples:**

```sql
SELECT * FROM students WHERE name LIKE 'b%';  -- names starting with 'b'
SELECT * FROM students WHERE name LIKE '%fy';  -- names ending with 'fy'
SELECT * FROM students WHERE name LIKE '%w%';  -- names containing 'w'
SELECT * FROM students WHERE name LIKE '_____';  -- names with exactly 5 characters
SELECT * FROM students WHERE percentage LIKE '%!%%';
```

**IS NULL / IS NOT NULL** - Checks for NULL values:

```sql
SELECT * FROM students WHERE phone IS NULL;
SELECT * FROM students WHERE phone IS NOT NULL;
```


## AS Keyword (Aliases)

The `AS` keyword creates temporary names (aliases) for columns or tables in query results. It makes queries more readable, especially when using calculations or functions.[^5]

### Column Aliases

**Basic syntax:**

```sql
SELECT column_name AS alias_name FROM table_name;
```

**Examples:**

```sql
-- Rename a column
SELECT name AS student_name, percentage AS marks FROM students;

-- With calculations
SELECT percentage + 10.0 AS adjusted_percentage FROM students;

-- With functions
SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM employees;
```

**Without AS keyword** (optional):

```sql
SELECT percentage + 10.0 product FROM students WHERE hostel = 10;
```

**With spaces in alias** (use quotes):

```sql
SELECT bookPrice + 500 AS 'New Price' FROM books;
```


### Table Aliases

Useful when joining multiple tables:

```sql
SELECT s.name, s.percentage 
FROM students AS s 
WHERE s.hostel = 10;
```


## MySQL LIMIT Clause

The `LIMIT` clause restricts the number of rows returned by a `SELECT` query. It's useful for handling large tables, pagination, and improving query performance by fetching only what you need.[^1][^7][^8][^9]

## Basic Syntax

```sql
SELECT column1, column2 FROM table_name LIMIT number;
```

**Example:**

```sql
SELECT name FROM students LIMIT 3;
```

This returns only the first 3 student names.[^7][^1]

## LIMIT with OFFSET

The `OFFSET` lets you skip rows before starting to return results.[^7][^8]

**Two syntax styles:**

```sql
-- Style 1: LIMIT with OFFSET keyword
SELECT * FROM students LIMIT 5 OFFSET 3;

-- Style 2: LIMIT offset, count (MySQL specific)
SELECT * FROM students LIMIT 3, 5;
```

Both skip the first 3 rows and return the next 5 rows.[^9][^8]

**Important:** The offset is zero-based, so `LIMIT 0, 5` returns the first 5 rows.[^8]

## LIMIT with ORDER BY

Combine with `ORDER BY` to get specific ranked results.[^1][^7]

**Example - Get top 3 students by percentage:**

```sql
SELECT name, percentage FROM students ORDER BY percentage DESC LIMIT 3;
```

**Example - Get 2nd lowest salary (pagination):**

```sql
SELECT * FROM employees ORDER BY salary ASC LIMIT 1, 1;
```

This skips the lowest salary (offset 1) and returns the next single row.[^1]

## LIMIT with WHERE

Filter data first, then limit results.[^9]

```sql
SELECT * FROM students WHERE hostel = 10 LIMIT 5;
```

Returns the first 5 students from hostel 10.[^7][^9]

**With offset:**

```sql
SELECT * FROM students WHERE percentage > 90.6 LIMIT 2, 4;
```

Skips 2 rows and returns the next 4 where percentage exceeds 90.6.[^9][^1]

## Common Use Cases

**Pagination** - Display results across multiple pages:

```sql
-- Page 1 (first 10 records)
SELECT * FROM products LIMIT 10 OFFSET 0;

-- Page 2 (records 11-20)
SELECT * FROM products LIMIT 10 OFFSET 10;

-- Page 3 (records 21-30)
SELECT * FROM products LIMIT 10 OFFSET 20;
```

**Top N results:**

```sql
SELECT * FROM sales ORDER BY amount DESC LIMIT 10;
```

**Testing queries** - View sample data without overwhelming output:

```sql
SELECT * FROM large_table LIMIT 5;
```

### Key Points

- `AS` is optional but recommended for clarity[^6][^5]
- Aliases only exist during query execution, they don't change the actual table[^4]
- Use meaningful, concise names[^5]
- Avoid SQL reserved words as aliases[^5]
- Use quotes when alias contains spaces or special characters


# CONSTRAINTS in MySQL:

## What are Constraints?

Constraints are **rules** you apply to columns in a table to control what kind of data can be stored.  They prevent invalid or unwanted data from entering your database and keep data accurate and consistent.

## Types of Constraints

### NOT NULL

Forces a column to **always have a value**—it cannot be empty (NULL).[^1]

```sql
CREATE TABLE students (
  id INT NOT NULL,
  name VARCHAR(30) NOT NULL
);
```

Here, every student **must** have an `id` and `name`; you can't insert a row without them.[^10]

### UNIQUE

Ensures **all values in a column are different**—no duplicates allowed.[^3][^1]

```sql
CREATE TABLE students (
  phone INT UNIQUE
);
```

No two students can have the same phone number.  You can have **multiple** UNIQUE constraints in a table.

Multiple `NULL` are allowed.

### PRIMARY KEY

A **combination of NOT NULL + UNIQUE**—uniquely identifies each row.  A table can have **only one** primary key.

```sql
CREATE TABLE students (
  id CHAR(12) PRIMARY KEY,
  name VARCHAR(30)
);
```

The `id` column cannot be NULL and must be unique for every student.  Other tables can reference this primary key using foreign keys.[^5][^9]

**Two ways to define PRIMARY KEY:**

```sql
-- Inline (at column level)
CREATE TABLE students (id CHAR(12) PRIMARY KEY, name VARCHAR(30));

-- At table level
CREATE TABLE students (
  id CHAR(12),
  name VARCHAR(30),
  PRIMARY KEY (id)
);
```

Both are identical.[^6]

### DEFAULT

Sets a **default value** if no value is provided during INSERT.

```sql
CREATE TABLE students (
  percentage DECIMAL(5,2) DEFAULT 0.0
);
```

If you don't specify `percentage` when inserting a row, it automatically gets `0.0`.

### CHECK

Enforces a **condition** on column values—only values passing the condition are allowed.

```sql
CREATE TABLE students (
  age INT CHECK (age >= 18)
);
```

Only students aged 18 or older can be inserted.

**Using named CHECK constraint:**

```sql
CREATE TABLE students (
  age INT,
  CONSTRAINT age_check CHECK (age >= 18)
);
```


## Using CONSTRAINT Keyword

You can **name** constraints with `CONSTRAINT` for easier management.

```sql
CREATE TABLE gradstudents (
  id CHAR(12),
  phone INT,
  CONSTRAINT ue UNIQUE (phone),
  PRIMARY KEY (id)
);
```

Here, `ue` is the name of the UNIQUE constraint on `phone`.  Named constraints make it easier to drop or modify them later.[^8]

## Complete Example
```sql
CREATE TABLE gradstudents (
  id CHARACTER(12),
  name VARCHAR(30),
  hostel INTEGER NOT NULL,
  percentage DECIMAL(5,2) DEFAULT 0.0,
  phone INT,
  bdate DATE,
  gender ENUM('F','M'),
  CONSTRAINT ue UNIQUE(phone),
  PRIMARY KEY(id)
);
```

**What each constraint does:**

- `hostel INTEGER NOT NULL` – hostel number is mandatory.[^10]
- `percentage DECIMAL(5,2) DEFAULT 0.0` – if no percentage given, defaults to 0.0.[^8]
- `CONSTRAINT ue UNIQUE(phone)` – phone numbers must be unique across all students.[^8]
- `PRIMARY KEY(id)` – `id` uniquely identifies each student, cannot be NULL or duplicate.

## Another Example:

TRY THIS (justify if you get errors):

```mysql
mysql> CREATE TABLE temp (id char(10), name varchar(30) unique, hostel int, percentage
decimal(5,2) default 0.0, phone VARCHAR(11), bdate date, gender enum('F','M'), primary
key(id), primary key(name) );
```

Correct method to create a composite primary key:

```mysql
mysql> CREATE TABLE temp (id char(10), name varchar(30) unique, hostel int, percentage
decimal(5,2) default 0.0, phone varchar(11), bdate date, gender enum('F','M'), primary
key(id,name) );
```


## Types of Constraints

Constraints enforce rules that columns in a table must follow, ensuring data integrity. There are three main categories:[^1]

### Domain Integrity Constraints

These set acceptable ranges and prevent operations that violate those rules.[^1]

- **NOT NULL**: Ensures a column cannot contain NULL values (by default, columns allow NULL)[^1]
- **CHECK**: Defines a specific range or condition that values must satisfy[^1]


### Entity Integrity Constraints

These ensure each record is uniquely identifiable.[^1]

- **UNIQUE**: Allows only unique values in a column or group of columns, preventing duplicate records, but permits NULL values[^1]
- **PRIMARY KEY**: Combines UNIQUE and NOT NULL—it prevents duplicates and doesn't allow NULL values. Cannot be used on LONG data type columns[^1]


### Referential Integrity Constraints

These enforce relationships between tables using foreign keys.[^1]

- **FOREIGN KEY**: Designates a column (or combination) as a foreign key that references a primary or unique key in another table. The table with the foreign key is the child table; the table with the referenced key is the parent table[^1]


# Foreign Keys in MySQL
# Complete Guide to Foreign Keys in MySQL

## Prerequisites Review

Since you know DQL, DML, and DDL commands, let's quickly establish the foundation:

- **DDL (Data Definition Language)**: CREATE, ALTER, DROP - defines database structure
- **DML (Data Manipulation Language)**: INSERT, UPDATE, DELETE - manipulates data
- **DQL (Data Query Language)**: SELECT - retrieves data

---

## 1. Understanding Constraints

**What are Constraints?**
Constraints are **rules** that columns in a table must follow. They enforce data integrity and prevent invalid data from entering your database.

### Types of Constraints:

#### A. **Domain Integrity Constraints** (Control what values are allowed)
1. **NOT NULL**: Column must have a value (no empty/null values)
2. **CHECK**: Column must meet specific conditions

#### B. **Entity Integrity Constraints** (Control uniqueness)
1. **UNIQUE**: No duplicate values allowed
2. **PRIMARY KEY**: No duplicates + No nulls (identifies each row uniquely)

#### C. **Referential Integrity Constraints** (Control relationships between tables)
1. **FOREIGN KEY**: Links tables together, maintains relationships

---

## What is FOREIGN KEY?

### **Definition:**
A **FOREIGN KEY** is a column (or group of columns) in one table that **references the PRIMARY KEY or UNIQUE KEY** of another table.

### **Purpose:**
- Maintains **referential integrity** (keeps relationships valid)
- Prevents orphaned records (child records without a parent)
- Enforces relationships between tables

## 2. What is INDEX? (Important Foundation)

### **What is an INDEX?**
An INDEX is like a **book's index** - it helps MySQL find data quickly without reading every row. Think of an **INDEX** in MySQL not as a "rule" (like a Key), but as a **physical data structure** created to speed up data retrieval.

"In value," an Index is a **sorted copy** of specific columns from your table, stored separately, with a "pointer" (address) that tells MySQL exactly where to find the full row.

### **Why is INDEX Required?**

**Without Index:**
```
To find student with ID=500:
MySQL scans Row 1, Row 2, Row 3... Row 500 ✓ (Slow!)
```

**With Index:**
```
MySQL directly jumps to Row 500 (Fast!)
```

### **Why INDEX is Required for Foreign Keys?**
When you use a FOREIGN KEY, MySQL needs to:
1. Check if the referenced value exists in the parent table (fast lookup needed)
2. Verify relationships during INSERT/UPDATE/DELETE operations

**InnoDB automatically creates an index on foreign key columns** to make these checks fast.

The relationship between **Foreign Keys** and **Indexes** is one of "Logical Rule" vs. "Physical Performance."

In MySQL (specifically the InnoDB engine), they are tightly connected because **MySQL requires an Index on every Foreign Key column.** If you don't create one manually, MySQL will secretly create one for you.

Here is why they are connected and how the index is actually used.

### 1. The "Integrity Check" (Why the database needs it)

The most critical use of an index on a Foreign Key is to prevent "locking" issues when you modify data.

Imagine two tables:

- **Parent:** `Users` (Primary Key: `UserID`)
    
- **Child:** `Orders` (Foreign Key: `UserID`)
    

**The Scenario:** You try to **DELETE** a user from the `Users` table.

- **Without an Index on `Orders.UserID`:** The database must verify that this user has no orders (Referential Integrity). Without an index, it has to scan the **entire** `Orders` table row-by-row to ensure `UserID 5` doesn't exist there. This locks the table and kills performance.
    
- **With an Index:** The database looks at the Index for `Orders`, jumps straight to "5", sees if any records exist, and instantly approves or rejects your DELETE command.
    

### 2. The "Join" Speed (Why you need it)

Foreign Keys are almost always used to `JOIN` tables together.

- **Query:** `SELECT * FROM Orders WHERE UserID = 5`
    
- This is the standard "Foreign Key lookup." The index acts exactly as described in the previous answer: it serves as a sorted lookup list so the database can find the connections instantly without scanning the whole table.

### **Example:**
```sql
CREATE TABLE parent (
    id INT NOT NULL,
    PRIMARY KEY (id)  -- Automatically creates an index on 'id'
) ENGINE=INNODB;

CREATE TABLE child (
    id INT,
    parent_id INT,
    INDEX par_ind (parent_id),  -- Index for fast lookups
    FOREIGN KEY (parent_id) REFERENCES parent(id)
) ENGINE=INNODB;
```

### **"If I manually create an index, and then I add a Foreign Key on the same column, is MySQL smart enough to link them, or will it blindly create a second, duplicate index?"**

The short answer is: **Yes, MySQL is smart enough.** It will detect your manual index and use it, provided it meets specific criteria.

Here is exactly how the "decision logic" works inside the MySQL engine (specifically InnoDB) when you add a Foreign Key.

### 1. The "Leftmost Prefix" Rule

When you try to create a Foreign Key on a column (let's say `UserID`), MySQL scans the table's existing indexes before creating a new one.

It doesn't just look for an exact match. It looks for any index where the **Foreign Key column is the first column listed (the "Leftmost Prefix").**

- **Scenario A (Exact Match):**
    
    - You create `INDEX my_custom_idx (UserID)`.
        
    - You add a Foreign Key on `UserID`.
        
    - **Result:** MySQL sees `my_custom_idx`, says "Perfect," and uses it. It creates **0** new indexes.
        
- **Scenario B (Composite Index Match):**
    
    - You create `INDEX complex_idx (UserID, Status, Date)`.
        
    - You add a Foreign Key on `UserID`.
        
    - **Result:** MySQL sees that `UserID` is the **first** column in `complex_idx`. It says "Good enough," and uses this index. It creates **0** new indexes.
        
- **Scenario C (No Match / Wrong Order):**
    
    - You create `INDEX backwards_idx (Status, UserID)`.
        
    - You add a Foreign Key on `UserID`.
        
    - **Result:** MySQL checks the index. It sees `Status` comes first, not `UserID`. This index cannot be used for efficient lookups on `UserID` alone. MySQL says "I can't use this," and **automatically creates a new, internal index** just for the Foreign Key.


---

## 4. Parent and Child Table Concept

### **What are Parent and Child Tables?**

- **Parent Table**: Contains the **original/master data** (has PRIMARY KEY)
- **Child Table**: Contains **dependent data** that references the parent (has FOREIGN KEY)

### **Relationship:**
```
Parent Table (PRIMARY KEY) ← Referenced by ← Child Table (FOREIGN KEY)
```

### **Example:**

```sql
-- PARENT TABLE (Customers)
CREATE TABLE customer (
    id INT NOT NULL,
    name VARCHAR(100),
    PRIMARY KEY (id)  -- This is the referenced key
) ENGINE=INNODB;

-- CHILD TABLE (Orders)
CREATE TABLE orders (
    order_no INT NOT NULL AUTO_INCREMENT,
    customer_id INT,  -- This is the FOREIGN KEY
    order_date DATE,
    PRIMARY KEY(order_no),
    INDEX (customer_id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
) ENGINE=INNODB;
```

**Visualization:**
```
CUSTOMER (Parent)          ORDERS (Child)
+----+---------+           +----------+-------------+
| id | name    |           | order_no | customer_id |
+----+---------+           +----------+-------------+
| 1  | Alice   | ←───────── | 101      | 1           |
| 2  | Bob     | ←───────── | 102      | 2           |
+----+---------+   │       | 103      | 1           |
                   └─────── +----------+-------------+
                  Foreign Key Relationship
```

---

## 5. Relationship Between FOREIGN KEY and PRIMARY KEY

### **The Golden Rule:**
A FOREIGN KEY in the child table **must match** a PRIMARY KEY (or UNIQUE KEY) in the parent table.

### **Key Points:**

1. **Data Types Must Match:**
   ```sql
   -- ✓ Correct
   Parent: id INT
   Child:  parent_id INT
   
   -- ✗ Wrong
   Parent: id INT
   Child:  parent_id VARCHAR(10)  -- Type mismatch!
   ```

2. **Values Must Exist in Parent:**
   ```sql
   -- If customer id=5 doesn't exist in customer table
   INSERT INTO orders (customer_id) VALUES (5);  -- ✗ REJECTED!
   ```

3. **Parent Table Must Exist First:**
   ```sql
   -- ✓ Correct Order
   CREATE TABLE parent (...);
   CREATE TABLE child (... FOREIGN KEY ...);
   
   -- ✗ Wrong Order
   CREATE TABLE child (... FOREIGN KEY ...);  -- Parent doesn't exist yet!
   ```

---

## 6. Why Don't We Add NOT NULL Constraint to Foreign Keys?

### **The Answer: It Depends on Your Business Logic**

### **Case 1: Foreign Key WITHOUT NOT NULL (Nullable)**

**When to Use:** When the relationship is **optional**

```sql
CREATE TABLE employee (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    manager_id INT,  -- Can be NULL (CEO has no manager)
    FOREIGN KEY (manager_id) REFERENCES employee(emp_id)
) ENGINE=INNODB;
```

**Why?**
- Not every employee has a manager (e.g., CEO)
- Allows flexibility: `manager_id = NULL` means "no manager"

```sql
INSERT INTO employee VALUES (1, 'CEO John', NULL);        -- ✓ Valid
INSERT INTO employee VALUES (2, 'Manager Sarah', 1);      -- ✓ Valid
INSERT INTO employee VALUES (3, 'Employee Tom', 2);       -- ✓ Valid
```

### **Case 2: Foreign Key WITH NOT NULL (Mandatory)**

**When to Use:** When the relationship is **mandatory**

```sql
CREATE TABLE orders (
    order_no INT PRIMARY KEY,
    customer_id INT NOT NULL,  -- Every order MUST have a customer
    FOREIGN KEY (customer_id) REFERENCES customer(id)
) ENGINE=INNODB;
```

**Why?**
- Business rule: "Every order must belong to a customer"
- Prevents incomplete data

```sql
INSERT INTO orders VALUES (101, NULL);  -- ✗ REJECTED! (NOT NULL constraint)
INSERT INTO orders VALUES (101, 1);     -- ✓ Valid (if customer id=1 exists)
```

### **Summary:**
- **NOT NULL on Foreign Key** = "This relationship is mandatory"
- **No NOT NULL** = "This relationship is optional"

---

## 7. FOREIGN KEY Syntax

### **Basic Syntax:**

```sql
CREATE TABLE child_table (
    column1 datatype,
    column2 datatype,
    foreign_key_column datatype,
    
    [CONSTRAINT constraint_name] 
    FOREIGN KEY (foreign_key_column)
    REFERENCES parent_table(parent_key_column)
    [ON DELETE action]
    [ON UPDATE action]
) ENGINE=INNODB;
```

### **Components Explained:**

1. **CONSTRAINT constraint_name** (Optional):
   - Give your foreign key a name for easy reference
   - If omitted, InnoDB generates a name like `0_38775`

2. **FOREIGN KEY (column_name)**:
   - Specify which column(s) in child table are foreign keys

3. **REFERENCES parent_table(column_name)**:
   - Specify which table and column to reference

4. **ON DELETE / ON UPDATE actions**:
   - Define what happens when parent data changes

---

## 8. Referential Actions (The Most Important Part!)

### **What Happens When Parent Data Changes?**

When you UPDATE or DELETE a row in the **parent table**, what should happen to related rows in the **child table**?

### **5 Referential Actions:**

#### **1. RESTRICT (Default)**

**What:** Prevents deletion/update if child records exist

```sql
CREATE TABLE parent (id INT PRIMARY KEY) ENGINE=INNODB;
CREATE TABLE child (
    id INT,
    parent_id INT,
    FOREIGN KEY (parent_id) REFERENCES parent(id)
    ON DELETE RESTRICT  -- Default behavior
) ENGINE=INNODB;

INSERT INTO parent VALUES (1);
INSERT INTO child VALUES (1, 1);

DELETE FROM parent WHERE id=1;  -- ✗ REJECTED! Child record exists
```

**Use Case:** Prevent accidental data loss

---

#### **2. CASCADE**

**What:** Automatically delete/update child records when parent changes

```sql
CREATE TABLE child (
    id INT,
    parent_id INT,
    FOREIGN KEY (parent_id) REFERENCES parent(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=INNODB;

INSERT INTO parent VALUES (1);
INSERT INTO child VALUES (1, 1);
INSERT INTO child VALUES (2, 1);

DELETE FROM parent WHERE id=1;  -- ✓ Parent deleted
                                 -- ✓ Both child records also deleted!

-- OR

UPDATE parent SET id=10 WHERE id=1;  -- ✓ Parent updated
                                       -- ✓ Child parent_id changed to 10!
```

**Use Case:** When child records are meaningless without parent
- Example: Delete customer → Delete all their orders

---

#### **3. SET NULL**

**What:** Set foreign key to NULL when parent is deleted/updated

```sql
CREATE TABLE child (
    id INT,
    parent_id INT,  -- Must allow NULL!
    FOREIGN KEY (parent_id) REFERENCES parent(id)
    ON DELETE SET NULL
) ENGINE=INNODB;

INSERT INTO parent VALUES (1);
INSERT INTO child VALUES (1, 1);

DELETE FROM parent WHERE id=1;  -- ✓ Parent deleted
                                 -- ✓ child.parent_id = NULL
```

**Important:** You **cannot** use SET NULL if the foreign key column has NOT NULL constraint!

```sql
-- ✗ WRONG:
parent_id INT NOT NULL,
FOREIGN KEY (parent_id) ... ON DELETE SET NULL  -- Contradiction!
```

**Use Case:** Keep child records but break the relationship
- Example: Delete category → Set product category to NULL (product remains)

---

#### **4. NO ACTION**

**What:** Same as RESTRICT in MySQL (immediate check)

```sql
FOREIGN KEY (parent_id) REFERENCES parent(id)
ON DELETE NO ACTION
```

---

#### **5. SET DEFAULT**

**What:** Set foreign key to default value (NOT supported by InnoDB)

```sql
-- ✗ Parser accepts but InnoDB rejects:
FOREIGN KEY (parent_id) ... ON DELETE SET DEFAULT
```

---

### **Comparison Table:**

| Action | Parent Deleted | Child Records | Use Case |
|--------|---------------|---------------|----------|
| **RESTRICT** | ✗ Rejected | Unchanged | Protect data |
| **CASCADE** | ✓ Deleted | Also deleted | Dependent data |
| **SET NULL** | ✓ Deleted | FK = NULL | Optional relationship |
| **NO ACTION** | ✗ Rejected | Unchanged | Same as RESTRICT |

---

## 9. Complete Examples

### **Example 1: Simple Parent-Child**

```sql
-- Parent: Departments
CREATE TABLE department (
    dept_id INT NOT NULL,
    dept_name VARCHAR(100),
    PRIMARY KEY (dept_id)
) ENGINE=INNODB;

-- Child: Employees
CREATE TABLE employee (
    emp_id INT NOT NULL,
    emp_name VARCHAR(100),
    dept_id INT NOT NULL,  -- NOT NULL: Every employee must have department
    PRIMARY KEY (emp_id),
    INDEX (dept_id),
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
        ON DELETE RESTRICT    -- Can't delete department if employees exist
        ON UPDATE CASCADE     -- Update dept_id in both tables
) ENGINE=INNODB;

-- Test Data
INSERT INTO department VALUES (10, 'IT'), (20, 'HR');
INSERT INTO employee VALUES (1, 'Alice', 10), (2, 'Bob', 10);

-- Try to delete department
DELETE FROM department WHERE dept_id=10;  
-- ✗ ERROR: Cannot delete - employees exist!

-- Update department
UPDATE department SET dept_id=15 WHERE dept_id=10;
-- ✓ Success! Employee dept_id also updated to 15 (CASCADE)
```

---

### **Example 2: Composite Foreign Key**

```sql
-- Parent: Products (Composite Primary Key)
CREATE TABLE product (
    category INT NOT NULL,
    id INT NOT NULL,
    price DECIMAL,
    PRIMARY KEY(category, id)  -- Composite key
) ENGINE=INNODB;

-- Child: Product Orders
CREATE TABLE product_order (
    no INT NOT NULL AUTO_INCREMENT,
    product_category INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY(no),
    INDEX (product_category, product_id),  -- Index on both columns
    FOREIGN KEY (product_category, product_id)  -- Both columns together
        REFERENCES product(category, id)
        ON UPDATE CASCADE 
        ON DELETE RESTRICT
) ENGINE=INNODB;

-- Test Data
INSERT INTO product VALUES (1, 100, 50.00);  -- Category 1, Product 100
INSERT INTO product_order VALUES (NULL, 1, 100);  -- References (1,100)

-- Try invalid reference
INSERT INTO product_order VALUES (NULL, 1, 999);  
-- ✗ ERROR: Product (1,999) doesn't exist!
```

---

### **Example 3: Self-Referential Foreign Key**

```sql
-- Employee table referencing itself
CREATE TABLE employee (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    manager_id INT,  -- References another employee in same table
    FOREIGN KEY (manager_id) REFERENCES employee(emp_id)
        ON DELETE SET NULL  -- If manager deleted, set to NULL
) ENGINE=INNODB;

-- Test Data
INSERT INTO employee VALUES (1, 'CEO', NULL);           -- No manager
INSERT INTO employee VALUES (2, 'Manager', 1);          -- Reports to CEO
INSERT INTO employee VALUES (3, 'Employee', 2);         -- Reports to Manager

-- Delete manager
DELETE FROM employee WHERE emp_id=2;
-- ✓ Success! Employee 3's manager_id becomes NULL
```

---

## 10. Adding/Dropping Foreign Keys with ALTER TABLE

### **Adding Foreign Key:**

```sql
-- Create tables without foreign key
CREATE TABLE parent (id INT PRIMARY KEY) ENGINE=INNODB;
CREATE TABLE child (id INT, parent_id INT, INDEX(parent_id)) ENGINE=INNODB;

-- Add foreign key later
ALTER TABLE child
ADD CONSTRAINT fk_child_parent
FOREIGN KEY (parent_id) REFERENCES parent(id)
ON DELETE CASCADE;
```

**Important:** Create the index FIRST before adding foreign key!

---

### **Dropping Foreign Key:**

#### **Method 1: If you know the constraint name**

```sql
ALTER TABLE child DROP FOREIGN KEY fk_child_parent;
```

#### **Method 2: Find the auto-generated name**

```sql
-- Show table structure
SHOW CREATE TABLE child\G

-- Output:
-- CONSTRAINT `0_38775` FOREIGN KEY (`parent_id`) 
-- REFERENCES `parent` (`id`)

-- Drop using that name
ALTER TABLE child DROP FOREIGN KEY `0_38775`;
```

---

### **Important Rules:**

1. **Cannot add and drop foreign key in same statement:**
   ```sql
   -- ✗ WRONG:
   ALTER TABLE child 
       DROP FOREIGN KEY fk1,
       ADD FOREIGN KEY (col) REFERENCES parent(id);
   
   -- ✓ CORRECT: Use separate statements
   ALTER TABLE child DROP FOREIGN KEY fk1;
   ALTER TABLE child ADD FOREIGN KEY (col) REFERENCES parent(id);
   ```

2. **Remember to create index first:**
   ```sql
   -- Add index if not exists
   ALTER TABLE child ADD INDEX (parent_id);
   
   -- Then add foreign key
   ALTER TABLE child ADD FOREIGN KEY (parent_id) REFERENCES parent(id);
   ```

---

## 11. Common Mistakes and Troubleshooting

### **Mistake 1: Type Mismatch**
```sql
-- ✗ Parent: INT, Child: VARCHAR
CREATE TABLE parent (id INT PRIMARY KEY);
CREATE TABLE child (
    parent_id VARCHAR(10),  -- Wrong type!
    FOREIGN KEY (parent_id) REFERENCES parent(id)  -- ERROR
);
```

**Fix:** Match data types exactly

---

### **Mistake 2: Missing Index**
```sql
-- ✗ No index on foreign key column
CREATE TABLE child (
    parent_id INT,
    FOREIGN KEY (parent_id) REFERENCES parent(id)  -- InnoDB creates it automatically
);
```

**Note:** InnoDB creates index automatically, but it's good practice to create it explicitly

---

### **Mistake 3: SET NULL with NOT NULL**
```sql
-- ✗ Contradiction
CREATE TABLE child (
    parent_id INT NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES parent(id)
        ON DELETE SET NULL  -- Can't set NULL on NOT NULL column!
);
```

**Fix:** Remove NOT NULL or use different action

---

### **Mistake 4: Wrong Order of Table Creation**
```sql
-- ✗ Child created before parent
CREATE TABLE child (
    FOREIGN KEY (parent_id) REFERENCES parent(id)  -- parent doesn't exist!
);
CREATE TABLE parent (id INT PRIMARY KEY);
```

**Fix:** Always create parent table first

---

## 12. Why Use InnoDB Engine?

### **What is InnoDB?**
InnoDB is MySQL's default storage engine (since MySQL 5.5)

### **Why InnoDB for Foreign Keys?**

1. **Only InnoDB supports FOREIGN KEY constraints**
   - MyISAM engine accepts syntax but doesn't enforce it!

2. **ACID Compliance:**
   - **A**tomicity: All or nothing transactions
   - **C**onsistency: Data integrity maintained
   - **I**solation: Transactions don't interfere
   - **D**urability: Data persists after commit

3. **Row-level locking:**
   - Better performance for concurrent users

4. **Crash recovery:**
   - Automatic recovery after crashes

### **Checking Engine:**
```sql
SHOW ENGINES;

-- Output shows InnoDB as DEFAULT
```

---

## 13. Best Practices

1. **Always Use InnoDB for Tables with Foreign Keys**
   ```sql
   CREATE TABLE mytable (...) ENGINE=INNODB;
   ```

2. **Name Your Constraints**
   ```sql
   CONSTRAINT fk_orders_customer  -- Clear, descriptive name
   FOREIGN KEY (customer_id) REFERENCES customer(id)
   ```

3. **Create Indexes Explicitly**
   ```sql
   INDEX (foreign_key_column),
   FOREIGN KEY (foreign_key_column) ...
   ```

4. **Choose Appropriate Referential Actions**
   - Use CASCADE carefully (can delete lots of data!)
   - RESTRICT is safest default

5. **Document Relationships**
   ```sql
   -- Orders depend on customers
   -- CASCADE: Deleting customer deletes their orders
   FOREIGN KEY (customer_id) REFERENCES customer(id)
       ON DELETE CASCADE
   ```

---

## 14. Quick Reference Summary

| Concept | Key Points |
|---------|-----------|
| **Foreign Key** | Links child table to parent table |
| **Parent Table** | Has PRIMARY KEY being referenced |
| **Child Table** | Has FOREIGN KEY referencing parent |
| **INDEX** | Required for fast FK lookups (auto-created) |
| **NOT NULL on FK** | Use when relationship is mandatory |
| **RESTRICT** | Prevents parent deletion if children exist |
| **CASCADE** | Automatically deletes/updates children |
| **SET NULL** | Sets FK to NULL (requires nullable column) |
| **InnoDB** | Only engine that enforces FK constraints |
