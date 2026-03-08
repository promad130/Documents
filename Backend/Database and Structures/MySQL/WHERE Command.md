***(Check out [[Data Types in MySQL]])***

The `WHERE` clause is a **DML (Data Manipulation Language)** component used to **filter rows** based on specified conditions. It works on **individual rows** before any grouping or aggregation happens.

### Basic Syntax:

```mysql
SELECT column1, column2, ...
FROM table_name
WHERE condition;
```

### Example:

```mysql
-- employees table
+----+--------+---------+--------+------------+
| id | name   | dept    | salary | hire_date  |
+----+--------+---------+--------+------------+
| 1  | Alice  | IT      | 75000  | 2020-01-15 |
| 2  | Bob    | Sales   | 60000  | 2019-06-20 |
| 3  | Carol  | IT      | 80000  | 2021-03-10 |
| 4  | David  | HR      | 55000  | 2018-11-05 |
| 5  | Eve    | Sales   | 62000  | 2022-07-18 |
+----+--------+---------+--------+------------+

SELECT name, salary, dept
FROM employees
WHERE salary > 60000;
```

**Result:**

```mysql
+-------+--------+------+
| name  | salary | dept |
+-------+--------+------+
| Alice | 75000  | IT   |
| Carol | 80000  | IT   |
| Eve   | 62000  | Sales|
+-------+--------+------+
```


## 2. WHERE CLAUSE OPERATORS

### A. Comparison Operators

|Operator|Meaning|Example|
|---|---|---|
|`=`|Equal to|`WHERE dept = 'IT'`|
|`!=` or `<>`|Not equal|`WHERE dept != 'Sales'`|
|`>`|Greater than|`WHERE salary > 70000`|
|`<`|Less than|`WHERE salary < 60000`|
|`>=`|Greater or equal|`WHERE salary >= 75000`|
|`<=`|Less or equal|`WHERE salary <= 65000`|


```mysql
-- Find employees hired before 2020
SELECT name, hire_date
FROM employees
WHERE hire_date < '2020-01-01';
```

**Result:**


```mysql
+-------+------------+
| name  | hire_date  |
+-------+------------+
| Bob   | 2019-06-20 |
| David | 2018-11-05 |
+-------+------------+
```

### B. Logical Operators

#### `AND` - All conditions must be TRUE

```mysql
SELECT name, dept, salary
FROM employees
WHERE dept = 'IT' AND salary > 70000;
```

**Result:**

```mysql
+-------+------+--------+
| name  | dept | salary |
+-------+------+--------+
| Alice | IT   | 75000  |
| Carol | IT   | 80000  |
+-------+------+--------+
```

#### `OR` - At least one condition must be TRUE

```mysql
SELECT name, dept, salary
FROM employees
WHERE dept = 'IT' OR salary > 70000;
```

**Result:**

```mysql
+-------+-------+--------+
| name  | dept  | salary |
+-------+-------+--------+
| Alice | IT    | 75000  |
| Carol | IT    | 80000  |
+-------+-------+--------+
```

#### `NOT` - Negates a condition
```mysql
SELECT name, dept
FROM employees
WHERE NOT dept = 'Sales';
-- Same as: WHERE dept != 'Sales'
```

**Result:**


```Code

+-------+------+
| name  | dept |
+-------+------+
| Alice | IT   |
| Carol | IT   |
| David | HR   |
+-------+------+
```

#### Combining Multiple Conditions
```SQL
SELECT name, dept, salary
FROM employees
WHERE (dept = 'IT' OR dept = 'Sales') 
  AND salary > 60000;
```

**Execution:**

1. Check if `dept = 'IT' OR dept = 'Sales'` is TRUE
2. Then check if `salary > 60000` is TRUE
3. Return row only if **both** are TRUE

**Result:**

```Code
+-------+-------+--------+
| name  | dept  | salary |
+-------+-------+--------+
| Alice | IT    | 75000  |
| Carol | IT    | 80000  |
| Eve   | Sales | 62000  |
+-------+-------+--------+
```

### C. Range Operators

#### `BETWEEN` - Within a range (inclusive)

```mySQL
SELECT name, salary
FROM employees
WHERE salary BETWEEN 60000 AND 75000;
-- Same as: WHERE salary >= 60000 AND salary <= 75000
```

**Result:**

```Code
+-------+--------+
| name  | salary |
+-------+--------+
| Alice | 75000  |
| Bob   | 60000  |
| Eve   | 62000  |
+-------+--------+
```

**Important:** `BETWEEN` is **inclusive** (includes both boundaries).

#### Date Ranges

```SQL
SELECT name, hire_date
FROM employees
WHERE hire_date BETWEEN '2019-01-01' AND '2021-12-31';
```

**Result:**

Code

```
+-------+------------+
| name  | hire_date  |
+-------+------------+
| Alice | 2020-01-15 |
| Bob   | 2019-06-20 |
| Carol | 2021-03-10 |
+-------+------------+
```

### D. Pattern Matching - `LIKE`

Used with **wildcards** for partial string matching:

- `%` = Zero or more characters
- `_` = Exactly one character

SQL

```
-- Names starting with 'A'
SELECT name FROM employees WHERE name LIKE 'A%';
-- Result: Alice

-- Names ending with 'e'
SELECT name FROM employees WHERE name LIKE '%e';
-- Result: Alice, Eve

-- Names containing 'ar'
SELECT name FROM employees WHERE name LIKE '%ar%';
-- Result: Carol

-- Names with exactly 3 characters
SELECT name FROM employees WHERE name LIKE '___';
-- Result: Bob, Eve

-- Names with 'a' as second letter
SELECT name FROM employees WHERE name LIKE '_a%';
-- Result: Carol, David
```

#### Case Sensitivity:

- **MySQL (default):** Case-**insensitive** (depends on collation)
- To force case-sensitive: Use `BINARY`


```mySql
-- Case-sensitive search
SELECT name FROM employees WHERE BINARY name LIKE 'alice';
-- Result: Empty (no match because 'Alice' has capital A)

SELECT name FROM employees WHERE BINARY name LIKE 'Alice';
-- Result: Alice
```

### E. List Membership - `IN`

Check if value exists in a list.

```mySQL
SELECT name, dept
FROM employees
WHERE dept IN ('IT', 'HR');
-- Same as: WHERE dept = 'IT' OR dept = 'HR'
```

**Result:**

```Code

+-------+------+
| name  | dept |
+-------+------+
| Alice | IT   |
| Carol | IT   |
| David | HR   |
+-------+------+
```

#### With Subquery:

```mySQL

-- Find employees in departments with budget > 100000
SELECT name, dept
FROM employees
WHERE dept IN (
    SELECT dept_name 
    FROM departments 
    WHERE budget > 100000
);
```

#### `NOT IN`:

```mySQL

SELECT name, dept
FROM employees
WHERE dept NOT IN ('Sales', 'HR');
```

**Result:**

Code

```
+-------+------+
| name  | dept |
+-------+------+
| Alice | IT   |
| Carol | IT   |
+-------+------+
```

### F. NULL Handling

**Critical:** `NULL` means "unknown" or "missing value"

#### `IS NULL` - Check for NULL values

SQL

```
-- employees with bonus column
+----+-------+-------+
| id | name  | bonus |
+----+-------+-------+
| 1  | Alice | 5000  |
| 2  | Bob   | NULL  |
| 3  | Carol | NULL  |
+----+-------+-------+

SELECT name, bonus
FROM employees
WHERE bonus IS NULL;
```

**Result:**

Code

```
+-------+-------+
| name  | bonus |
+-------+-------+
| Bob   | NULL  |
| Carol | NULL  |
+-------+-------+
```

#### `IS NOT NULL` - Check for non-NULL values

SQL

```
SELECT name, bonus
FROM employees
WHERE bonus IS NOT NULL;
```

**Result:**

Code

```
+-------+-------+
| name  | bonus |
+-------+-------+
| Alice | 5000  |
+-------+-------+
```

#### **WRONG WAY (Common Mistake):**

SQL

```
-- THIS DOES NOT WORK!
SELECT name FROM employees WHERE bonus = NULL;
-- Result: Empty (0 rows)

SELECT name FROM employees WHERE bonus != NULL;
-- Result: Empty (0 rows)
```

**Why?** `NULL = NULL` is **FALSE** (unknown ≠ unknown). `NULL != NULL` is also **FALSE**.

**Always use `IS NULL` or `IS NOT NULL`.**

---

## WHERE CLAUSE WITH DIFFERENT SQL STATEMENTS

### A. With SELECT (most common)

SQL

```
SELECT name, salary 
FROM employees 
WHERE salary > 70000;
```

### B. With UPDATE

SQL

```
-- Give 10% raise to IT department
UPDATE employees
SET salary = salary * 1.10
WHERE dept = 'IT';
```

**Before:**

Code

```
Alice | IT | 75000
Carol | IT | 80000
```

**After:**

Code

```
Alice | IT | 82500
Carol | IT | 88000
```

### C. With DELETE

SQL

```
-- Remove employees hired before 2019
DELETE FROM employees
WHERE hire_date < '2019-01-01';
```

**Warning:** Without WHERE, **all rows** are deleted!

```mySQL
DELETE FROM employees;  -- Deletes EVERYTHING!
```

### D. Cannot Use with INSERT

```SQL
-- INVALID - WHERE doesn't work with INSERT
INSERT INTO employees (name, dept, salary)
VALUES ('Frank', 'IT', 70000)
WHERE dept = 'IT';  -- Syntax Error!
```