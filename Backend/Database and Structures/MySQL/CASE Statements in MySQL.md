# **CASE Statements in MySQL - Complete Guide**

## **PART 1: WHAT IS CASE?**

A **CASE statement** is a **conditional logic tool** that lets you evaluate conditions and return different values based on those conditions.

**Think of it like:** IF-ELSE in programming, but for SQL queries.

**It's NOT a constraint, NOT a view directive—it's a SELECT expression.**

---

## **PART 2: TYPE OF STATEMENT**

**CASE is a DQL (Data Query Language) component**, specifically an **expression** used in SELECT queries.

**Classification:**
- ✓ DQL (SELECT queries)
- ✓ Expression (returns a value)
- ✓ Can be used anywhere you'd use a normal column
- ✗ NOT a constraint
- ✗ NOT a DDL statement
- ✗ NOT a view directive

**It's similar to:**
- Functions: `UPPER()`, `LENGTH()`, `DATE()`
- Operators: `+`, `-`, `*`
- Comparisons: `=`, `>`, `<`

---

## **PART 3: BASIC SYNTAX**

### **Simple CASE (Pattern Matching):**

```sql
SELECT 
    CASE column_name
        WHEN value1 THEN result1
        WHEN value2 THEN result2
        ELSE default_result
    END AS alias_name
FROM table_name;
```

### **Searched CASE (Condition Evaluation):**

```sql
SELECT 
    CASE
        WHEN condition1 THEN result1
        WHEN condition2 THEN result2
        ELSE default_result
    END AS alias_name
FROM table_name;
```

---

## **PART 4: SIMPLE CASE EXAMPLES**

### **Example 1: Status Mapping**

```sql
CREATE TABLE employees (
    emp_id INT,
    name VARCHAR(100),
    status VARCHAR(20)  -- 'Active', 'Inactive', 'On Leave'
);

INSERT INTO employees VALUES
(1, 'Alice', 'Active'),
(2, 'Bob', 'Inactive'),
(3, 'Carol', 'On Leave');

-- CASE statement: Convert status to description
SELECT 
    name,
    status,
    CASE status
        WHEN 'Active' THEN 'Currently Working'
        WHEN 'Inactive' THEN 'Not Working'
        WHEN 'On Leave' THEN 'On Vacation'
        ELSE 'Unknown Status'
    END AS status_description
FROM employees;

-- Result:
-- name  | status    | status_description
-- ------|-----------|-------------------
-- Alice | Active    | Currently Working
-- Bob   | Inactive  | Not Working
-- Carol | On Leave  | On Vacation
```

**What happened:**
- For each row, CASE checks the `status` column
- If status = 'Active', returns 'Currently Working'
- If status = 'Inactive', returns 'Not Working'
- Etc.

---

### **Example 2: Grade Mapping**

```sql
CREATE TABLE students (
    student_id INT,
    name VARCHAR(100),
    grade CHAR(1)  -- 'A', 'B', 'C', 'D', 'F'
);

INSERT INTO students VALUES
(101, 'Alice', 'A'),
(102, 'Bob', 'C'),
(103, 'Carol', 'F'),
(104, 'David', 'B');

-- Map grades to performance levels
SELECT 
    name,
    grade,
    CASE grade
        WHEN 'A' THEN 'Excellent'
        WHEN 'B' THEN 'Good'
        WHEN 'C' THEN 'Average'
        WHEN 'D' THEN 'Poor'
        WHEN 'F' THEN 'Failed'
        ELSE 'No Grade'
    END AS performance
FROM students;

-- Result:
-- name  | grade | performance
-- ------|-------|------------
-- Alice | A     | Excellent
-- Bob   | C     | Average
-- Carol | F     | Failed
-- David | B     | Good
```

---

## **PART 5: SEARCHED CASE EXAMPLES**

### **Difference from Simple CASE:**

**Simple CASE:** Checks if column equals specific values
```sql
CASE status
    WHEN 'Active' THEN ...
```

**Searched CASE:** Evaluates conditions (>, <, AND, OR, etc.)
```sql
CASE
    WHEN age > 65 THEN ...
    WHEN salary < 30000 THEN ...
```

---

### **Example 1: Age Group Classification**

```sql
CREATE TABLE customers (
    customer_id INT,
    name VARCHAR(100),
    age INT
);

INSERT INTO customers VALUES
(1, 'Alice', 25),
(2, 'Bob', 45),
(3, 'Carol', 70),
(4, 'David', 18);

-- Classify by age ranges
SELECT 
    name,
    age,
    CASE
        WHEN age < 18 THEN 'Minor'
        WHEN age >= 18 AND age < 65 THEN 'Adult'
        WHEN age >= 65 THEN 'Senior'
        ELSE 'Unknown'
    END AS age_group
FROM customers;

-- Result:
-- name  | age | age_group
-- ------|-----|----------
-- Alice | 25  | Adult
-- Bob   | 45  | Adult
-- Carol | 70  | Senior
-- David | 18  | Adult
```

**Key difference:**
- Uses conditions: `age < 18`, `age >= 18 AND age < 65`, etc.
- NOT comparing to specific values
- More flexible than Simple CASE

---

### **Example 2: Salary Grade Assignment**

```sql
CREATE TABLE employees (
    emp_id INT,
    name VARCHAR(100),
    salary DECIMAL(10,2)
);

INSERT INTO employees VALUES
(1, 'Alice', 30000),
(2, 'Bob', 55000),
(3, 'Carol', 80000),
(4, 'David', 100000);

-- Assign salary grades
SELECT 
    name,
    salary,
    CASE
        WHEN salary < 40000 THEN 'Entry Level'
        WHEN salary >= 40000 AND salary < 70000 THEN 'Mid Level'
        WHEN salary >= 70000 AND salary < 100000 THEN 'Senior'
        WHEN salary >= 100000 THEN 'Executive'
    END AS salary_grade
FROM employees;

-- Result:
-- name  | salary | salary_grade
-- ------|--------|---------------
-- Alice | 30000  | Entry Level
-- Bob   | 55000  | Mid Level
-- Carol | 80000  | Senior
-- David | 100000 | Executive
```

---

### **Example 3: Multiple Conditions (Complex)**

```sql
CREATE TABLE products (
    product_id INT,
    product_name VARCHAR(100),
    price DECIMAL(10,2),
    stock INT
);

INSERT INTO products VALUES
(1, 'Laptop', 999.99, 5),
(2, 'Mouse', 29.99, 100),
(3, 'Keyboard', 79.99, 0),
(4, 'Monitor', 299.99, 3);

-- Assign action based on price AND stock
SELECT 
    product_name,
    price,
    stock,
    CASE
        WHEN stock = 0 THEN 'OUT OF STOCK - Order Now'
        WHEN stock < 10 AND price > 500 THEN 'LOW STOCK - Expensive Item'
        WHEN stock < 10 AND price <= 500 THEN 'LOW STOCK - Regular Item'
        WHEN stock >= 10 THEN 'IN STOCK'
    END AS inventory_status
FROM products;

-- Result:
-- product_name | price  | stock | inventory_status
-- --------------|--------|-------|--------------------------------
-- Laptop       | 999.99 | 5     | LOW STOCK - Expensive Item
-- Mouse        | 29.99  | 100   | IN STOCK
-- Keyboard     | 79.99  | 0     | OUT OF STOCK - Order Now
-- Monitor      | 299.99 | 3     | LOW STOCK - Regular Item
```

---

## **PART 6: CASE WITH AGGREGATES**

### **Example 1: Count Different Types**

```sql
CREATE TABLE orders (
    order_id INT,
    status VARCHAR(20),
    amount DECIMAL(10,2)
);

INSERT INTO orders VALUES
(1, 'Completed', 100),
(2, 'Pending', 200),
(3, 'Completed', 150),
(4, 'Cancelled', 50),
(5, 'Pending', 300);

-- Count orders by status
SELECT 
    COUNT(CASE WHEN status = 'Completed' THEN 1 END) AS completed_orders,
    COUNT(CASE WHEN status = 'Pending' THEN 1 END) AS pending_orders,
    COUNT(CASE WHEN status = 'Cancelled' THEN 1 END) AS cancelled_orders,
    COUNT(*) AS total_orders
FROM orders;

-- Result:
-- completed_orders | pending_orders | cancelled_orders | total_orders
-- ------------------|------------------|------------------|---------------
-- 2                 | 2                | 1                | 5
```

**How it works:**
- `CASE WHEN status = 'Completed' THEN 1 END` returns 1 if status is 'Completed', NULL otherwise
- `COUNT(...)` counts only the 1s (ignores NULL)
- Result: count of completed orders

---

### **Example 2: Sum Different Categories**

```sql
-- Sum amounts by status
SELECT 
    SUM(CASE WHEN status = 'Completed' THEN amount ELSE 0 END) AS completed_revenue,
    SUM(CASE WHEN status = 'Pending' THEN amount ELSE 0 END) AS pending_revenue,
    SUM(CASE WHEN status = 'Cancelled' THEN amount ELSE 0 END) AS cancelled_revenue,
    SUM(amount) AS total_revenue
FROM orders;

-- Result:
-- completed_revenue | pending_revenue | cancelled_revenue | total_revenue
-- -------------------|-------------------|-------------------|----------------
-- 250                | 500               | 50                | 800
```

---

### **Example 3: Average with Conditions**

```sql
CREATE TABLE employees (
    emp_id INT,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    department VARCHAR(50)
);

INSERT INTO employees VALUES
(1, 'Alice', 50000, 'IT'),
(2, 'Bob', 60000, 'IT'),
(3, 'Carol', 45000, 'HR'),
(4, 'David', 55000, 'HR');

-- Average salary by department
SELECT 
    AVG(CASE WHEN department = 'IT' THEN salary END) AS it_avg_salary,
    AVG(CASE WHEN department = 'HR' THEN salary END) AS hr_avg_salary,
    AVG(salary) AS overall_avg_salary
FROM employees;

-- Result:
-- it_avg_salary | hr_avg_salary | overall_avg_salary
-- --------------|---------------|-------------------
-- 55000         | 50000         | 52500
```

---

## **PART 7: CASE WITH GROUP BY**

```sql
CREATE TABLE sales (
    sale_id INT,
    salesperson VARCHAR(100),
    amount DECIMAL(10,2),
    region VARCHAR(50)
);

INSERT INTO sales VALUES
(1, 'Alice', 5000, 'North'),
(2, 'Alice', 6000, 'North'),
(3, 'Bob', 3000, 'South'),
(4, 'Bob', 4000, 'South'),
(5, 'Carol', 7000, 'East');

-- Sales summary with CASE in GROUP BY query
SELECT 
    salesperson,
    region,
    SUM(amount) AS total_sales,
    COUNT(*) AS num_sales,
    AVG(amount) AS avg_sale,
    CASE
        WHEN SUM(amount) > 10000 THEN 'High Performer'
        WHEN SUM(amount) > 5000 THEN 'Good Performer'
        ELSE 'Average Performer'
    END AS performance
FROM sales
GROUP BY salesperson, region
ORDER BY total_sales DESC;

-- Result:
-- salesperson | region | total_sales | num_sales | avg_sale | performance
-- ------------|--------|-------------|-----------|----------|------------------
-- Alice      | North  | 11000       | 2         | 5500     | High Performer
-- Carol      | East   | 7000        | 1         | 7000     | Good Performer
-- Bob        | South  | 7000        | 2         | 3500     | Good Performer
```

**Key:** CASE evaluates the aggregate result, then assigns performance level.

---

## **PART 8: CASE IN WHERE CLAUSE (Less Common)**

```sql
-- Find high performers
SELECT 
    salesperson,
    region,
    SUM(amount) AS total_sales
FROM sales
GROUP BY salesperson, region
HAVING CASE
    WHEN SUM(amount) > 8000 THEN 1
    ELSE 0
END = 1;

-- Better way (use HAVING directly):
HAVING SUM(amount) > 8000;
```

---

## **PART 9: CASE IN UPDATE/INSERT (Different Context)**

```sql
-- Update salary based on CASE
UPDATE employees
SET salary = CASE
    WHEN department = 'IT' THEN salary * 1.10
    WHEN department = 'HR' THEN salary * 1.05
    ELSE salary
END;

-- Insert with CASE (rare)
INSERT INTO log_table (message, severity)
VALUES ('Error occurred', CASE WHEN error_code = 500 THEN 'Critical' ELSE 'Warning' END);
```

---

## **PART 10: CAN CASE BE USED IN VIEWS?**

**YES! CASE is a SELECT expression, so it works in views.**

```sql
CREATE VIEW employee_performance AS
SELECT 
    name,
    salary,
    CASE
        WHEN salary < 40000 THEN 'Entry Level'
        WHEN salary >= 40000 AND salary < 70000 THEN 'Mid Level'
        WHEN salary >= 70000 THEN 'Senior'
    END AS level
FROM employees;

-- Query the view
SELECT * FROM employee_performance;
-- Shows calculated CASE column
```

**CASE is NOT a view directive (like WITH CHECK OPTION)—it's just a column expression inside the view.**

---

## **PART 11: NESTED CASE**

```sql
SELECT 
    name,
    salary,
    CASE
        WHEN salary < 40000 THEN 
            CASE
                WHEN age < 25 THEN 'Young Junior'
                ELSE 'Experienced Junior'
            END
        ELSE 'Senior'
    END AS employee_category
FROM employees;
```

Works but gets confusing—avoid if possible.

---

## **PART 12: QUICK REFERENCE**

| Type | Syntax | Use When |
|------|--------|----------|
| **Simple CASE** | `CASE column WHEN val THEN result END` | Checking if column equals specific values |
| **Searched CASE** | `CASE WHEN condition THEN result END` | Checking complex conditions (>, <, AND, OR) |
| **With COUNT** | `COUNT(CASE WHEN ... THEN 1 END)` | Count rows matching condition |
| **With SUM** | `SUM(CASE WHEN ... THEN amount END)` | Sum values matching condition |
| **With AVG** | `AVG(CASE WHEN ... THEN salary END)` | Average of values matching condition |
| **With GROUP BY** | `CASE ... END` in SELECT after GROUP BY | Categorize groups |
| **In Views** | Create VIEW with CASE in SELECT | Store calculated column in view |

---
# **CASE is:**
- ✓ A SELECT expression (DQL)
- ✓ Conditional logic (IF-ELSE in SQL)
- ✓ Can be used anywhere you'd use a normal column
- ✓ Works with aggregates, GROUP BY, WHERE, UPDATE, INSERT
- ✓ Can be used in views
- ✗ NOT a constraint
- ✗ NOT a view directive
- ✗ NOT a data type
