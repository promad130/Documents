
## 1. WHAT IS GROUP BY?

**GROUP BY** groups rows that have the same values in specified columns into **summary rows**. It's used with **aggregate functions** to perform calculations on each group.

### Think of it like this:
Imagine you have a pile of colored balls. GROUP BY sorts them into separate piles by color, then you can count, average, or sum within each pile.

---

## 2. AGGREGATE FUNCTIONS (Used with GROUP BY)

These functions perform calculations on groups of rows:

| Function | Purpose | Example |
|----------|---------|---------|
| `COUNT()` | Count rows | How many employees per department? |
| `SUM()` | Add values | Total sales per region? |
| `AVG()` | Calculate average | Average salary per department? |
| `MAX()` | Find maximum | Highest salary per department? |
| `MIN()` | Find minimum | Lowest salary per department? |

---

## 3. BASIC GROUP BY EXAMPLES

### Example Table:
```sql
-- employees
+----+--------+---------+--------+------------+
| id | name   | dept    | salary | hire_date  |
+----+--------+---------+--------+------------+
| 1  | Alice  | IT      | 75000  | 2020-01-15 |
| 2  | Bob    | Sales   | 60000  | 2019-06-20 |
| 3  | Carol  | IT      | 80000  | 2021-03-10 |
| 4  | David  | HR      | 55000  | 2018-11-05 |
| 5  | Eve    | Sales   | 62000  | 2022-07-18 |
| 6  | Frank  | IT      | 70000  | 2020-09-12 |
| 7  | Grace  | HR      | 58000  | 2021-05-22 |
+----+--------+---------+--------+------------+
```

### Example 1: Count Employees Per Department

```sql
SELECT dept, COUNT(*) AS employee_count
FROM employees
GROUP BY dept;
```

**How it works:**

**Step 1:** GROUP BY separates rows by department
```
IT Group:
  1  | Alice  | IT | 75000
  3  | Carol  | IT | 80000
  6  | Frank  | IT | 70000

Sales Group:
  2  | Bob    | Sales | 60000
  5  | Eve    | Sales | 62000

HR Group:
  4  | David  | HR | 55000
  7  | Grace  | HR | 58000
```

**Step 2:** COUNT(*) counts rows in each group
- IT: 3 rows
- Sales: 2 rows
- HR: 2 rows

**Result:**
```
+---------+----------------+
| dept    | employee_count |
+---------+----------------+
| IT      | 3              |
| Sales   | 2              |
| HR      | 2              |
+---------+----------------+
```

### Example 2: Average Salary Per Department

```sql
SELECT dept, AVG(salary) AS avg_salary
FROM employees
GROUP BY dept;
```

**Execution:**

**IT Group:** (75000 + 80000 + 70000) / 3 = 75,000
**Sales Group:** (60000 + 62000) / 2 = 61,000
**HR Group:** (55000 + 58000) / 2 = 56,500

**Result:**
```
+---------+------------+
| dept    | avg_salary |
+---------+------------+
| IT      | 75000.00   |
| Sales   | 61000.00   |
| HR      | 56500.00   |
+---------+------------+
```

### Example 3: Multiple Aggregate Functions

```sql
SELECT 
    dept,
    COUNT(*) AS employee_count,
    AVG(salary) AS avg_salary,
    MIN(salary) AS min_salary,
    MAX(salary) AS max_salary,
    SUM(salary) AS total_salary
FROM employees
GROUP BY dept;
```

**Result:**
```
+---------+----------------+------------+------------+------------+--------------+
| dept    | employee_count | avg_salary | min_salary | max_salary | total_salary |
+---------+----------------+------------+------------+------------+--------------+
| IT      | 3              | 75000.00   | 70000      | 80000      | 225000       |
| Sales   | 2              | 61000.00   | 60000      | 62000      | 122000       |
| HR      | 2              | 56500.00   | 55000      | 58000      | 113000       |
+---------+----------------+------------+------------+------------+--------------+
```

---

## 4. THE GOLDEN RULE OF GROUP BY

### ⚠️ CRITICAL RULE:

**Every column in SELECT must be either:**
1. **In the GROUP BY clause**, OR
2. **Inside an aggregate function**

### ❌ WRONG Examples:

```sql
-- ERROR: 'name' is neither in GROUP BY nor in aggregate function
SELECT dept, name, AVG(salary)
FROM employees
GROUP BY dept;
-- ERROR 1055: 'employees.name' isn't in GROUP BY
```

**Why?** When you group by dept:
- IT has 3 employees (Alice, Carol, Frank)
- Which name should MySQL show? It doesn't know!

```sql
-- ERROR: 'salary' is neither in GROUP BY nor in aggregate function
SELECT dept, salary, COUNT(*)
FROM employees
GROUP BY dept;
-- ERROR 1055: 'employees.salary' isn't in GROUP BY
```

### ✅ CORRECT Examples:

```sql
-- Option 1: Put all non-aggregated columns in GROUP BY
SELECT dept, name, salary
FROM employees
GROUP BY dept, name, salary;
-- Returns individual rows (no real grouping benefit)

-- Option 2: Use aggregate functions for calculations
SELECT dept, COUNT(*) AS count, AVG(salary) AS avg_sal
FROM employees
GROUP BY dept;
-- Returns summary per department

-- Option 3: Get specific values with aggregates
SELECT dept, MAX(salary) AS highest_salary
FROM employees
GROUP BY dept;
-- Shows highest salary per department
```

---

## 5. GROUP BY WITH MULTIPLE COLUMNS

### Example: Group by Department AND Year

```sql
SELECT 
    dept,
    YEAR(hire_date) AS hire_year,
    COUNT(*) AS employee_count,
    AVG(salary) AS avg_salary
FROM employees
GROUP BY dept, YEAR(hire_date)
ORDER BY dept, hire_year;
```

**How it works:**

**Step 1:** Create groups by BOTH dept AND hire_year
```
IT + 2020:    Alice, Frank
IT + 2021:    Carol
Sales + 2019: Bob
Sales + 2022: Eve
HR + 2018:    David
HR + 2021:    Grace
```

**Step 2:** Calculate aggregates for each group

**Result:**
```
+---------+-----------+----------------+------------+
| dept    | hire_year | employee_count | avg_salary |
+---------+-----------+----------------+------------+
| HR      | 2018      | 1              | 55000.00   |
| HR      | 2021      | 1              | 58000.00   |
| IT      | 2020      | 2              | 72500.00   |
| IT      | 2021      | 1              | 80000.00   |
| Sales   | 2019      | 1              | 60000.00   |
| Sales   | 2022      | 1              | 62000.00   |
+---------+-----------+----------------+------------+
```

---

## 6. HAVING CLAUSE (Filter Groups)

### WHERE vs HAVING:

| Aspect | WHERE | HAVING |
|--------|-------|--------|
| **Filters** | Individual rows | Grouped results |
| **Applied** | BEFORE grouping | AFTER grouping |
| **Can use aggregates?** | ❌ NO | ✅ YES |
| **Execution order** | 3rd (after FROM, JOIN) | 5th (after GROUP BY) |

### Visual Execution Order:

```
1. FROM       - Get tables
2. JOIN       - Combine tables
3. WHERE      - Filter individual rows ← Filters ROWS
4. GROUP BY   - Group remaining rows
5. HAVING     - Filter groups ← Filters GROUPS
6. SELECT     - Choose columns
7. ORDER BY   - Sort results
8. LIMIT      - Limit output
```

---

## 7. HAVING EXAMPLES

### Example 1: Departments with More Than 2 Employees

```sql
SELECT dept, COUNT(*) AS employee_count
FROM employees
GROUP BY dept
HAVING COUNT(*) > 2;
```

**Execution:**

**Step 1:** GROUP BY dept
```
IT:    3 employees
Sales: 2 employees
HR:    2 employees
```

**Step 2:** HAVING filters groups where count > 2
```
IT:    3 > 2 ✓ (included)
Sales: 2 > 2 ✗ (excluded)
HR:    2 > 2 ✗ (excluded)
```

**Result:**
```
+------+----------------+
| dept | employee_count |
+------+----------------+
| IT   | 3              |
+------+----------------+
```

### Example 2: Departments with Average Salary > 60000

```sql
SELECT 
    dept,
    AVG(salary) AS avg_salary,
    COUNT(*) AS employee_count
FROM employees
GROUP BY dept
HAVING AVG(salary) > 60000;
```

**Execution:**

**Step 1:** Calculate averages
```
IT:    75000 (average)
Sales: 61000 (average)
HR:    56500 (average)
```

**Step 2:** HAVING filters groups where avg > 60000
```
IT:    75000 > 60000 ✓
Sales: 61000 > 60000 ✓
HR:    56500 > 60000 ✗
```

**Result:**
```
+---------+------------+----------------+
| dept    | avg_salary | employee_count |
+---------+------------+----------------+
| IT      | 75000.00   | 3              |
| Sales   | 61000.00   | 2  [2000, 2500]            |
+---------+------------+----------------+
```

---

## 8. WHERE vs HAVING (Critical Difference)

### Example Scenario:

**Goal 1:** Find departments where employees earning >60000 have an average salary >70000

**Goal 2:** Find departments with average salary >70000

### Example Data:
```sql
-- sales table
+----+--------+--------+--------+
| id | emp_id | region | amount |
+----+--------+--------+--------+
| 1  | 1      | East   | 1000   |
| 2  | 1      | East   | 1500   |
| 3  | 1      | East   | 500    |
| 4  | 2      | West   | 2000   |
| 5  | 2      | West   | 2500   |
| 6  | 3      | East   | 800    |
+----+--------+--------+--------+
```

### Using WHERE (Filters Rows First)

```sql
-- Total sales per region, only counting sales > 1000
SELECT 
    region,
    SUM(amount) AS total_sales,
    COUNT(*) AS sale_count
FROM sales
WHERE amount > 1000  -- Filter individual rows BEFORE grouping
GROUP BY region;
```

**Execution:**

**Step 1:** WHERE filters rows
```
Keep only: amount > 1000
+----+--------+--------+--------+
| id | emp_id | region | amount |
+----+--------+--------+--------+
| 2  | 1      | East   | 1500   | ✓
| 4  | 2      | West   | 2000   | ✓
| 5  | 2      | West   | 2500   | ✓
+----+--------+--------+--------+
```

**Step 2:** GROUP BY region
```
East: [1500]
West: [2000, 2500]
```
[2000, 2500]
**Step 3:** Calculate aggregates
```
East: SUM = 1500, COUNT = 1
West: SUM = 4500, COUNT = 2
```

**Result:**
```
+--------+-------------+------------+
| region | total_sales | sale_count |
+--------+-------------+------------+
| East   | 1500        | 1          |
| West   | 4500        | 2          |
+--------+-------------+------------+
```

### Using HAVING (Filters Groups After)

```sql
-- Regions with total sales > 2000
SELECT 
    region,
    SUM(amount) AS total_sales,
    COUNT(*) AS sale_count
FROM sales
GROUP BY region
HAVING SUM(amount) > 2000;  -- Filter groups AFTER aggregation
```

**Execution:**

**Step 1:** GROUP BY region (use ALL rows)
```
East: [1000, 1500, 500, 800]
West: [2000, 2500]
```

**Step 2:** Calculate aggregates
```
East: SUM = 3800, COUNT = 4
West: SUM = 4500, COUNT = 2
```

**Step 3:** HAVING filters groups
```
East: 3800 > 2000 ✓
West: 4500 > 2000 ✓
```

**Result:**
```
+--------+-------------+------------+
| region | total_sales | sale_count |
+--------+-------------+------------+
| East   | 3800        | 4          |
| West   | 4500        | 2          |
+--------+-------------+------------+
```

### Combining WHERE + HAVING

```sql
-- Regions with total sales > 2000, counting only sales > 1000
SELECT 
    region,
    SUM(amount) AS total_sales,
    COUNT(*) AS sale_count
FROM sales
WHERE amount > 1000         -- Filter rows first
GROUP BY region
HAVING SUM(amount) > 2000;  -- Then filter groups
```

**Execution:**

**Step 1:** WHERE filters → Keep `[1500, 2000, 2500]`

**Step 2:** GROUP BY → East: `[1500],` West: `[2000, 2500]`

**Step 3:** Aggregates → East: 1500, West: 4500

**Step 4:** HAVING filters → East: 1500 > 2000 ✗, West: 4500 > 2000 ✓

**Result:**
```
+--------+-------------+------------+
| region | total_sales | sale_count |
+--------+-------------+------------+
| West   | 4500        | 2          |
+--------+-------------+------------+
```

---

## 9. COMMON MISTAKES & FIXES

### Mistake 1: Using Aggregate in WHERE

```sql
-- ❌ WRONG - Can't use aggregate in WHERE
SELECT dept, AVG(salary)
FROM employees
WHERE AVG(salary) > 60000  -- ERROR 1111: Invalid use of group function
GROUP BY dept;
```

**Why it fails:** WHERE executes BEFORE grouping, so AVG() doesn't exist yet.

```sql
-- ✅ CORRECT - Use HAVING for aggregates
SELECT dept, AVG(salary) AS avg_salary
FROM employees
GROUP BY dept
HAVING AVG(salary) > 60000;
```

### Mistake 2: Column Not in GROUP BY

```sql
-- ❌ WRONG
SELECT dept, name, COUNT(*)
FROM employees
GROUP BY dept;
-- ERROR 1055: 'employees.name' isn't in GROUP BY
```

```sql
-- ✅ CORRECT Option 1: Add to GROUP BY
SELECT dept, name, COUNT(*)
FROM employees
GROUP BY dept, name;

-- ✅ CORRECT Option 2: Use aggregate
SELECT dept, COUNT(*) AS count, MAX(name) AS sample_name
FROM employees
GROUP BY dept;
```

### Mistake 3: Using Column Alias in WHERE/HAVING (Sometimes)

```sql
-- ❌ WRONG - Alias not available in WHERE
SELECT dept, AVG(salary) AS avg_sal
FROM employees
WHERE avg_sal > 60000  -- ERROR: Unknown column 'avg_sal'
GROUP BY dept;

-- ✅ CORRECT - Repeat the expression
SELECT dept, AVG(salary) AS avg_sal
FROM employees
GROUP BY dept
HAVING AVG(salary) > 60000;

-- ✅ ALSO CORRECT - Some MySQL versions allow alias in HAVING
SELECT dept, AVG(salary) AS avg_sal
FROM employees
GROUP BY dept
HAVING avg_sal > 60000;  -- Works in many MySQL versions
```

---

## 10. COMPLEX EXAMPLES

### Example 1: Employees Hired Per Year, By Department

```sql
SELECT 
    dept,
    YEAR(hire_date) AS hire_year,
    COUNT(*) AS hired_count,
    GROUP_CONCAT(name ORDER BY name) AS employee_names
FROM employees
GROUP BY dept, YEAR(hire_date)
ORDER BY dept, hire_year;
```

**Result:**
```
+---------+-----------+--------------+------------------+
| dept    | hire_year | hired_count  | employee_names   |
+---------+-----------+--------------+------------------+
| HR      | 2018      | 1            | David            |
| HR      | 2021      | 1            | Grace            |
| IT      | 2020      | 2            | Alice,Frank      |
| IT      | 2021      | 1            | Carol            |
| Sales   | 2019      | 1            | Bob              |
| Sales   | 2022      | 1            | Eve              |
+---------+-----------+--------------+------------------+
```

### Example 2: Departments with Salary Range > 20000

```sql
SELECT 
    dept,
    MAX(salary) - MIN(salary) AS salary_range,
    COUNT(*) AS employee_count,
    AVG(salary) AS avg_salary
FROM employees
GROUP BY dept
HAVING MAX(salary) - MIN(salary) > 20000;
```

**Calculation:**
```
IT:    80000 - 70000 = 10000 (excluded)
Sales: 62000 - 60000 = 2000  (excluded)
HR:    58000 - 55000 = 3000  (excluded)
```

**Result:** Empty (no department has range > 20000)

Let's modify data to see a result:

```sql
-- Add high earner to IT
INSERT INTO employees VALUES (8, 'Henry', 'IT', 95000, '2023-01-10');

-- Now IT range: 95000 - 70000 = 25000

SELECT 
    dept,
    MAX(salary) - MIN(salary) AS salary_range,
    COUNT(*) AS employee_count
FROM employees
GROUP BY dept
HAVING MAX(salary) - MIN(salary) > 20000;
```

**Result:**
```
+------+--------------+----------------+
| dept | salary_range | employee_count |
+------+--------------+----------------+
| IT   | 25000        | 4              |
+------+--------------+----------------+
```

### Example 3: Find Departments with All Employees Earning >55000

```sql
SELECT 
    dept,
    MIN(salary) AS lowest_salary,
    COUNT(*) AS employee_count
FROM employees
GROUP BY dept
HAVING MIN(salary) > 55000;
```

**Logic:** If the MINIMUM salary > 55000, then ALL employees earn more than 55000.

**Calculation:**
```
IT:    MIN = 70000 > 55000 ✓
Sales: MIN = 60000 > 55000 ✓
HR:    MIN = 55000 > 55000 ✗ (not greater, equal)
```

**Result:**
```
+---------+---------------+----------------+
| dept    | lowest_salary | employee_count |
+---------+---------------+----------------+
| IT      | 70000         | 3              |
| Sales   | 60000         | 2              |
+---------+---------------+----------------+
```

---

## 11. GROUP BY WITH JOINS

### Example Tables:
```sql
-- employees
+----+--------+---------+--------+
| id | name   | dept_id | salary |
+----+--------+---------+--------+
| 1  | Alice  | 10      | 75000  |
| 2  | Bob    | 20      | 60000  |
| 3  | Carol  | 10      | 80000  |
| 4  | David  | 30      | 55000  |
| 5  | Eve    | 20      | 62000  |
+----+--------+---------+--------+

-- departments
+----+--------+----------+
| id | name   | location |
+----+--------+----------+
| 10 | IT     | NY       |
| 20 | Sales  | LA       |
| 30 | HR     | Chicago  |
+----+--------+----------+
```

### Query: Employee Count and Avg Salary by Location

```sql
SELECT 
    d.location,
    COUNT(e.id) AS employee_count,
    AVG(e.salary) AS avg_salary,
    SUM(e.salary) AS total_payroll
FROM departments d
LEFT JOIN employees e ON d.id = e.dept_id
GROUP BY d.location
ORDER BY employee_count DESC;
```

**Execution:**

**Step 1:** JOIN tables
```
NY      | Alice | 75000
NY      | Carol | 80000
LA      | Bob   | 60000
LA      | Eve   | 62000
Chicago | David | 55000
```

**Step 2:** GROUP BY location
```
NY:      [Alice, Carol]
LA:      [Bob, Eve]
Chicago: [David]
```

**Step 3:** Calculate aggregates

**Result:**
```
+----------+----------------+------------+---------------+
Venue: B Dome
| location | employee_count | avg_salary | total_payroll |
+----------+----------------+------------+---------------+
| NY       | 2              | 77500.00   | 155000        |
| LA       | 2              | 61000.00   | 122000        |
| Chicago  | 1              | 55000.00   | 55000         |
+----------+----------------+------------+---------------+
```

---

## 12. SPECIAL CASES & EDGE CASES

### Case 1: GROUP BY with No Aggregates

```sql
-- This works but is essentially DISTINCT
SELECT dept
FROM employees
GROUP BY dept;
```

**Result:**
Venue: B Dome
```
+---------+
| dept    |
+---------+
| IT      |
| Sales   |
| HR      |
+---------+
```

**Better to use:**
```sql
SELECT DISTINCT dept
FROM employees;
```

### Case 2: COUNT(*) vs COUNT(column)

```sql
-- employees with nullable bonus
+----+--------+-------+
| id | name   | bonus |
+----+--------+-------+
| 1  | Alice  | 5000  |
| 2  | Bob    | NULL  |
| 3  | Carol  | NULL  |
| 4  | David  | 3000  |
+----+--------+-------+

SELECT 
    COUNT(*) AS total_rows,
    COUNT(bonus) AS employees_with_bonus,
    COUNT(name) AS non_null_names
FROM employees;
```

**Result:**
```
+------------+-----------------------+-----------------+
| total_rows | employees_with_bonus  | non_null_names  |
+------------+-----------------------+-----------------+
| 4          | 2                     | 4               |
+------------+-----------------------+-----------------+
```

**Key:**
- `COUNT(*)` counts ALL rows (including NULL)
- `COUNT(column)` counts only NON-NULL values

### Case 3: GROUP BY with NULL Values

```sql
-- employees with nullable dept
+----+--------+------+--------+
| id | name   | dept | salary |
+----+--------+------+--------+
| 1  | Alice  | IT   | 75000  |
| 2  | Bob    | NULL | 60000  |
| 3  | Carol  | IT   | 80000  |
| 4  | David  | NULL | 55000  |
+----+--------+------+--------+Venue: B Dome


SELECT dept, COUNT(*) AS count, AVG(salary) AS avg_sal
FROM employees
GROUP BY dept;
```

**Result:**
```
+------+-------+----------+
| dept | count | avg_sal  |
+------+-------+----------+
| IT   | 2     | 77500.00 |
| NULL | 2     | 57500.00 |
+------+-------+----------+
```

**Key:** NULL values are grouped together as one group.

---

## 13. PERFORMANCE TIPS

### 1. Index Columns Used in GROUP BY

```sql
-- Create index for faster grouping
CREATE INDEX idx_dept ON employees(dept);

-- Now this is faster:
SELECT dept, COUNT(*)
FROM employees
GROUP BY dept;
```

### 2. Filter with WHERE Before GROUP BY

```sql
-- ✅ BETTER - Filter first (fewer rows to group)
SELECT dept, AVG(salary)
FROM employees
WHERE salary > 50000  -- Reduces rows before grouping
GROUP BY dept;

-- ❌ SLOWER - Group all rows, then filter
SELECT dept, AVG(salary)
FROM employees
GROUP BY dept
HAVING AVG(salary) > 50000;
```

**Use WHERE when possible** to reduce the dataset before grouping.

### 3. Avoid Unnecessary Columns

```sql
-- ❌ SLOWER - Unnecessary columns
SELECT dept, name, salary, COUNT(*)
FROM employees
GROUP BY dept, name, salary;

-- ✅ FASTER - Only needed columns
SELECT dept, COUNT(*)
FROM employees
GROUP BY dept;
```

---

## 14. COMPLETE SYNTAX ORDER

### Full Query Structure:

```sql
SELECT 
    column1,
    aggregate_function(column2) AS alias
FROM table1
JOIN table2 ON condition
WHERE row_condition              -- Filter ROWS
GROUP BY column1                 -- Group rows
HAVING aggregate_condition       -- Filter GROUPS
ORDER BY column1 DESC            -- Sort results
LIMIT 10;                        -- Limit output
```

### Execution Order:

```
1. FROM/JOIN    → Get and combine tables
2. WHERE        → Filter individual rows
3. GROUP BY     → Group remaining rows
4. Aggregate    → Calculate COUNT, SUM, AVG, etc.
5. HAVING       → Filter groups
6. SELECT       → Choose columns
7. DISTINCT     → Remove duplicates
8. ORDER BY     → Sort results
9. LIMIT/OFFSET → Limit output
```

---

## 15. PRACTICE PROBLEMS

### Problem 1: Basic Grouping
**Find total salary per department, ordered by total descending**

```sql
SELECT dept, SUM(salary) AS total_salary
FROM employees
GROUP BY dept
ORDER BY total_salary DESC;
```

### Problem 2: Multiple Conditions
**Find departments with >2 employees AND average salary >60000**

```sql
SELECT 
    dept,
    COUNT(*) AS emp_count,
    AVG(salary) AS avg_sal
FROM employees
GROUP BY dept
HAVING COUNT(*) > 2 AND AVG(salary) > 60000;
```

### Problem 3: WHERE + HAVING
**Find departments where employees earning >60000 have average salary >70000**

```sql
SELECT 
    dept,
    AVG(salary) AS avg_high_earners
FROM employees
WHERE salary > 60000
GROUP BY dept
HAVING AVG(salary) > 70000;
```

---

## FINAL SUMMARY

| Concept | Purpose | Example |
|---------|---------|---------|
| **GROUP BY** | Group rows by column values | `GROUP BY dept` |
| **Aggregate Functions** | Calculate on groups | `COUNT(*)`, `AVG(salary)` |
| **HAVING** | Filter groups (after aggregation) | `HAVING COUNT(*) > 2` |
| **WHERE** | Filter rows (before grouping) | `WHERE salary > 50000` |
| **Golden Rule** | SELECT columns must be in GROUP BY or aggregate | Required! |

**Key Takeaways:**
1. **GROUP BY** organizes data into groups
2. **Aggregates** calculate values for each group
3. **WHERE** filters rows BEFORE grouping
4. **HAVING** filters groups AFTER aggregation
5. Can't use aggregates in WHERE (use HAVING instead)
6. Every SELECT column must be grouped or aggregated
