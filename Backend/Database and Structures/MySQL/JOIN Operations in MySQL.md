Joins are used to combine rows from two or more tables based on a **related column** between them. Let me explain each type with detailed examples.

---

## Understanding the Foundation

### Example Tables:

```sql
-- customers table
+----+--------+-----------+
| id | name   | city      |
+----+--------+-----------+
| 1  | Alice  | New York  |
| 2  | Bob    | London    |
| 3  | Carol  | Paris     |
| 4  | David  | Tokyo     |
+----+--------+-----------+

-- orders table
+----------+-------------+--------+
| order_id | customer_id | amount |
+----------+-------------+--------+
| 101      | 1           | 250    |
| 102      | 1           | 400    |
| 103      | 2           | 150    |
| 104      | 5           | 300    |
| 105      | 5           | 500    |
+----------+-------------+--------+
```

**Key observations:**
- Alice (id=1) has 2 orders
- Bob (id=2) has 1 order
- Carol (id=3) has NO orders
- David (id=4) has NO orders
- Customer id=5 doesn't exist in customers table (orphaned orders)

![[Pasted image 20260302185453.png]]

---

## CROSS JOIN (Cartesian Product)

### Definition:
Combines **every row** from the first table with **every row** from the second table, regardless of any relationship.

### Visualization:
![[Pasted image 20260302190423.png]]

### Formula:
```
Result rows = (rows in table1) × (rows in table2)
```

### Syntax:
```mysql
SELECT customers.name, orders.order_id, orders.amount
FROM customers
CROSS JOIN orders;
```

**OR (implicit syntax):**
```sql
SELECT customers.name, orders.order_id, orders.amount
FROM customers, orders;
```

### Result:
4 customers × 5 orders = **20 rows**

```
+--------+----------+--------+
| name   | order_id | amount |
+--------+----------+--------+
| Alice  | 101      | 250    |
| Alice  | 102      | 400    |
| Alice  | 103      | 150    |
| Alice  | 104      | 300    |
| Alice  | 105      | 500    |
| Bob    | 101      | 250    |
| Bob    | 102      | 400    |
| Bob    | 103      | 150    |
| Bob    | 104      | 300    |
| Bob    | 105      | 500    |
| Carol  | 101      | 250    |
| Carol  | 102      | 400    |
| Carol  | 103      | 150    |
| Carol  | 104      | 300    |
| Carol  | 105      | 500    |
| David  | 101      | 250    |
| David  | 102      | 400    |
| David  | 103      | 150    |
| David  | 104      | 300    |
| David  | 105      | 500    |
+--------+----------+--------+
```

### Real-World Use Case:
```sql
-- Generate all possible combinations of shirt sizes and colors
SELECT sizes.size, colors.color
FROM sizes
CROSS JOIN colors;
```
GROUP BY and HAVING Clause
**When NOT to use:** Almost never for actual data analysis—results are usually meaningless without a join condition.

---

## INNER JOIN (Default JOIN)

### Definition:
Returns **only** the rows where there's a **matching value** in both tables based on the join condition. (data that appears in both tables)

### Syntax:
```sql
SELECT c.name, c.city, o.order_id, o.amount
FROM customers c
INNER JOIN orders o ON c.id = o.customer_id;
```

### Step-by-Step Execution:

1. **Alice (id=1)** → Check orders where customer_id=1 → Found: 101, 102 ✓
2. **Bob (id=2)** → Check orders where customer_id=2 → Found: 103 ✓
3. **Carol (id=3)** → Check orders where customer_id=3 → **NOT FOUND** ✗
4. **David (id=4)** → Check orders where customer_id=4 → **NOT FOUND** ✗

Orders 104 & 105 (customer_id=5) → Customer 5 doesn't exist → **EXCLUDED** ✗

### Result:
```
+--------+----------+----------+--------+
| name   | city     | order_id | amount |
+--------+----------+----------+--------+
| Alice  | New York | 101      | 250    |
| Alice  | New York | 102      | 400    |
| Bob    | London   | 103      | 150    |
+--------+----------+----------+--------+
```

**Only 3 rows** (Carol, David, and orphaned orders are excluded)

### Multiple Matching Rows:
Alice appears **twice** because she has 2 orders. This is correct behavior—INNER JOIN creates one result row for each matching pair.GROUP BY and HAVING Clause

### Real-World Example:
```sql
-- Find all students enrolled in courses with their grades
SELECT s.student_name, c.course_name, e.grade
FROM students s
INNER JOIN enrollments e ON s.student_id = e.student_id
INNER JOIN courses c ON e.course_id = c.course_id;
```

---

## LEFT (OUTER) JOIN

### Definition:
Returns **ALL rows from the LEFT table**, plus matching rows from the right table. If no match exists, right table columns are filled with **NULL**.

### Visualization:
```
customers (LEFT)    orders (RIGHT)
    ●―――――――――――――――――●
    ●―――――――――――――●   ●
    ●                  ●
    ●              ●   ●
                       ●
                       
LEFT JOIN returns all left rows + matching right rows
Unmatched left rows get NULL for right columns
```

### Syntax:
```sql
SELECT c.name, c.city, o.order_id, o.amount
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id;
```

### Step-by-Step Execution:

1. **Alice (id=1)** → Match with orders 101, 102 ✓
2. **Bob (id=2)** → Match with order 103 ✓
3. **Carol (id=3)** → No match → Include Carol with NULL for order columns ✓
4. **David (id=4)** → No match → Include David with NULL for order columns ✓

Orders 104 & 105 (customer_id=5) → Customer 5 not in LEFT table → **EXCLUDED** ✗

### Result:
GROUP BY and HAVING ClauseGROUP BY and HAVING Clause```
+--------+----------+----------+--------+
| name   | city     | order_id | amount |
+--------+----------+----------+--------+
| Alice  | New York | 101      | 250    |
| Alice  | New York | 102      | 400    |
| Bob    | London   | 103      | 150    |
| Carol  | Paris    | NULL     | NULL   |
| David  | Tokyo    | NULL     | NULL   |
+--------+----------+----------+--------+
```

**5 rows total** (all 4 customers included, orphaned orders excluded)

### Finding Unmatched Rows:
```sql
-- Find customers who have NEVER placed an order
SELECT c.name, c.city
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.order_id IS NULL;
```

**Result:**
```
+--------+-------+
| name   | city  |
+--------+-------+
| Carol  | Paris |
| David  | Tokyo |
+--------+-------+
```

### Real-World Example:
```sql
-- List all employees and their assigned projects (include employees without projects)
SELECT e.employee_name, e.department, p.project_name
FROM employees e
LEFT JOIN projects p ON e.employee_id = p.assigned_to;
```

---

## RIGHT (OUTER) JOIN

### Definition:
Returns **ALL rows from the RIGHT table**, plus matching rows from the left table. If no match exists, left table columns are filled with **NULL**.

### Visualization:
```
customers (LEFT)    orders (RIGHT)
    ●―――――――――――――――――●
    ●―――――――――――――●   ●
    ●                  ●
    ●              ●   ●
                       ●
                       
RIGHT JOIN returns all right rows + matching left rows
Unmatched right rows get NULL for left columns
```

### Syntax:
```sql
SELECT c.name, c.city, o.order_id, o.amount
FROM customers c
RIGHT JOIN orders o ON c.id = o.customer_id;
```

### Step-by-Step Execution:

1. **Order 101** (customer_id=1) → Match with Alice ✓
2. **Order 102** (customer_id=1) → Match with Alice ✓
3. **Order 103** (customer_id=2) → Match with Bob ✓
4. **Order 104** (customer_id=5) → No customer → Include with NULL for customer columns ✓
5. **Order 105** (customer_id=5) → No customer → Include with NULL for customer columns ✓

Carol & David → Not in orders table → **EXCLUDED** ✗

### Result:
```
+--------+----------+----------+--------+
| name   | city     | order_id | amount |
+--------+----------+----------+--------+
| Alice  | New York | 101      | 250    |
| Alice  | New York | 102      | 400    |
| Bob    | London   | 103      | 150    |
| NULL   | NULL     | 104      | 300    |
| NULL   | NULL     | 105      | 500    |
+--------+----------+----------+--------+
```

**5 rows total** (all 5 orders included, Carol & David excluded)

### Finding Orphaned Records:
```sql
-- Find orders without a valid customer (data integrity issue)
SELECT o.order_id, o.amount, o.customer_id
FROM customers c
RIGHT JOIN orders o ON c.id = o.customer_id
WHERE c.id IS NULL;
```

**Result:**
```
+----------+--------+-------------+
| order_id | amount | customer_id |
+----------+--------+-------------+
| 104      | 300    | 5           |
| 105      | 500    | 5           |
+----------+--------+-------------+
```

### Real-World Example:
```sql
-- List all orders and customer details (include orders even if customer was deleted)
SELECT o.order_id, o.order_date, c.customer_name, c.email
FROM customers c
RIGHT JOIN orders o ON c.customer_id = o.customer_id;
```

---

## 5. FULL (OUTER) JOIN

### Definition:
Returns **ALL rows from BOTH tables**. Fills NULL where there's no match on either side.

### Visualization:

![[Pasted image 20260302190359.png]]

```
customers (LEFT)    orders (RIGHT)
    ●―――――――――――――――――●
    ●―――――――――――――●   ●
    ●                  ●
    ●              ●   ●
                       ●
                       
FULL JOIN returns ALL rows from both tables
Unmatched rows get NULL for the opposite table's columns
```

### Problem: MySQL doesn't support FULL JOIN

**Workaround:** Combine LEFT JOIN and RIGHT JOIN using UNION

### Syntax:
```sql
SELECT c.name, c.city, o.order_id, o.amount
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id

UNION

SELECT c.name, c.city, o.order_id, o.amount
FROM customers c
RIGHT JOIN orders o ON c.id = o.customer_id;
```

### How It Works:

**Part 1 (LEFT JOIN):** Gets all customers + matching orders
```
Alice  | New York | 101  | 250
Alice  | New York | 102  | 400
Bob    | London   | 103  | 150
Carol  | Paris    | NULL | NULL
David  | Tokyo    | NULL | NULL
```

**Part 2 (RIGHT JOIN):** Gets all orders + matching customers
```
Alice  | New York | 101  | 250
Alice  | New York | 102  | 400
Bob    | London   | 103  | 150
NULL   | NULL     | 104  | 300
NULL   | NULL     | 105  | 500
```

**UNION removes duplicates** (Alice and Bob rows appear in both, kept once)

### Final Result:
```
+--------+----------+----------+--------+
| name   | city     | order_id | amount |
+--------+----------+----------+--------+
| Alice  | New York | 101      | 250    |
| Alice  | New York | 102      | 400    |
| Bob    | London   | 103      | 150    |
| Carol  | Paris    | NULL     | NULL   |
| David  | Tokyo    | NULL     | NULL   |
| NULL   | NULL     | 104      | 300    |
| NULL   | NULL     | 105      | 500    |
+--------+----------+----------+--------+
```

**7 rows total** (4 customers + 5 orders - 2 duplicate matches = 7)

### Real-World Example:
```sql
-- Audit: Find all employees and all departments, including unassigned employees and empty departments
SELECT e.employee_name, d.department_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.dept_id

UNION

SELECT e.employee_name, d.department_name
FROM employees e
RIGHT JOIN departments d ON e.dept_id = d.dept_id;
```

---

## SELF JOIN

### Definition:
Joining a table with **itself**. Used to compare rows within the same table.

### Example Table:
```sql
-- employees
+----+--------+------------+--------+
| id | name   | manager_id | salary |
+----+--------+------------+--------+
| 1  | Alice  | NULL       | 90000  |  -- CEO (no manager)
| 2  | Bob    | 1          | 70000  |  -- Reports to Alice
| 3  | Carol  | 1          | 75000  |  -- Reports to Alice
| 4  | David  | 2          | 60000  |  -- Reports to Bob
| 5  | Eve    | 2          | 65000  |  -- Reports to Bob
+----+--------+------------+--------+
```

### Use Case 1: Find each employee and their manager
```sql
SELECT 
    e.name AS employee,
    m.name AS manager,
    e.salary AS employee_salary
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;
```

**Explanation:**
- `e` (employee table): Main employee data
- `m` (manager table): Same table, but matched where `e.manager_id = m.id`

**Result:**
```
+----------+---------+-----------------+
| employee | manager | employee_salary |
+----------+---------+-----------------+
| Alice    | NULL    | 90000           |  -- No manager (CEO)
| Bob      | Alice   | 70000           |
| Carol    | Alice   | 75000           |
| David    | Bob     | 60000           |
| Eve      | Bob     | 65000           |
+----------+---------+-----------------+
```

### Use Case 2: Find employees earning more than their manager
```sql
SELECT 
    e.name AS employee,
    e.salary AS employee_salary,
    m.name AS manager,
    m.salary AS manager_salary
FROM employees e
INNER JOIN employees m ON e.manager_id = m.id
WHERE e.salary > m.salary;
```

**Result:**
```
+----------+-----------------+---------+----------------+
| employee | employee_salary | manager | manager_salary |
+----------+-----------------+---------+----------------+
| Carol    | 75000           | Alice   | 90000          |
+----------+-----------------+---------+----------------+
```
Wait, that's wrong! Let me recalculate...

Actually with the data above, **no one earns more than their manager**:
- Bob (70000) < Alice (90000)
- Carol (75000) < Alice (90000)
- David (60000) < Bob (70000)
- Eve (65000) < Bob (70000)

**Result would be empty** (no rows).

### Use Case 3: Find pairs of employees in the same salary range
```sql
SELECT 
    e1.name AS employee1,
    e2.name AS employee2,
    e1.salary
FROM employees e1
INNER JOIN employees e2 
    ON e1.salary = e2.salary 
    AND e1.id < e2.id;  -- Avoid duplicate pairs and self-matching
```

**Why `e1.id < e2.id`?** 
Without it, you'd get:
- Alice-Alice (self-match)
- Alice-Bob AND Bob-Alice (duplicate pair)

---
## What is the difference between ON and WHERE then?
### ON vs WHERE:

|Aspect|ON|WHERE|
|---|---|---|
|**Purpose**|Defines **how tables are joined**|Filters **final result**|
|**When executed**|During JOIN operation|After JOIN is complete|
|**Matters for**|OUTER JOINs (LEFT/RIGHT/FULL)|All query types|
|**For INNER JOIN**|Same result as WHERE|Same result as ON|
|**For OUTER JOIN**|**DIFFERENT behavior!**|**DIFFERENT behavior!**|

**Key Insight:** For **INNER JOIN**, ON and WHERE produce the **same result**. For **OUTER JOIN** (LEFT/RIGHT/FULL), they produce **completely different results**!

Example Tables:
```Text
-- customers
+----+--------+-----------+
| id | name   | city      |
+----+--------+-----------+
| 1  | Alice  | New York  |
| 2  | Bob    | London    |
| 3  | Carol  | Paris     |
+----+--------+-----------+

-- orders
+----------+-------------+--------+
| order_id | customer_id | amount |
+----------+-------------+--------+
| 101      | 1           | 250    |
| 102      | 1           | 100    |
| 103      | 2           | 400    |
| 104      | 2           | 50     |
+----------+-------------+--------+
```

### Query Setup:

```mysql
-- Condition: amount > 200
```

#### LEFT JOIN with ON:
```mysql
FROM customers c
LEFT JOIN orders o 
    ON c.id = o.customer_id 
    AND o.amount > 200;
```

**Result:**

```Code

+-------+----------+--------+
| name  | order_id | amount |
+-------+----------+--------+
| Alice | 101      | 250    |
| Bob   | 103      | 400    |
| Carol | NULL     | NULL   |  ← PRESENT
+-------+----------+--------+
```

**Carol is included** because LEFT JOIN preserves all left-side rows.

#### LEFT JOIN with WHERE:
```MySQL
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.amount > 200;
```

**Result:**

```Code

+-------+----------+--------+
| name  | order_id | amount |
+-------+----------+--------+
| Alice | 101      | 250    |
| Bob   | 103      | 400    |
+-------+----------+--------+
```

**Carol is excluded** because WHERE filters the final result (NULL doesn't pass the filter).

---

### VISUAL EXPLANATION

#### ON Condition (Applied During Join):

```Code
LEFT JOIN with ON condition:

Step 1: Match tables with condition
Customers         Orders (amount > 200)
  Alice  ────────→  101 (250) ✓
         ╳────────→  102 (100) ✗ Filtered during join
  Bob    ────────→  103 (400) ✓
         ╳────────→  104 (50)  ✗ Filtered during join
  Carol  ─────────   (no match)

Step 2: LEFT JOIN preserves all left rows
  Alice  ────────→  101 (250)
  Bob    ────────→  103 (400)
  Carol  ────────→  NULL       ← Still included!
```

#### WHERE Condition (Applied After Join):

```Code

LEFT JOIN then WHERE filter:

Step 1: Join ALL matching rows
Customers         Orders (ALL)
  Alice  ────────→  101 (250)
         ────────→  102 (100)
  Bob    ────────→  103 (400)
         ────────→  104 (50)
  Carol  ────────→  NULL

Step 2: WHERE filters final result
  Alice + 101 (250) → 250 > 200 ✓ Keep
  Alice + 102 (100) → 100 > 200 ✗ Remove
  Bob   + 103 (400) → 400 > 200 ✓ Keep
  Bob   + 104 (50)  → 50  > 200 ✗ Remove
  Carol + NULL      → NULL > 200 ✗ Remove ← Lost!
```


## MULTIPLE TABLE JOINS

### Example Tables:
```sql
-- students
+----+--------+
| id | name   |
+----+--------+
| 1  | Alice  |
| 2  | Bob    |
| 3  | Carol  |
+----+--------+

-- enrollments
+----+------------+-----------+-------+
| id | student_id | course_id | grade |
+----+------------+-----------+-------+
| 1  | 1          | 101       | A     |
| 2  | 1          | 102       | B     |
| 3  | 2          | 101       | A     |
| 4  | 3          | 103       | C     |
+----+------------+-----------+-------+

-- courses
+-----+-------------+----------+
| id  | course_name | credits  |
+-----+-------------+----------+
| 101 | Math        | 3        |
| 102 | Physics     | 4        |
| 103 | Chemistry   | 3        |
| 104 | Biology     | 4        |
+-----+-------------+----------+
```

### Query: List all students with their enrolled courses and grades
```sql
SELECT 
    s.name AS student,
    c.course_name,
    e.grade,
    c.credits
FROM students s
INNER JOIN enrollments e ON s.id = e.student_id
INNER JOIN courses c ON e.course_id = c.id
ORDER BY s.name, c.course_name;
```

**Execution flow:**
1. Join students + enrollments (on student_id)
2. Take that result + join with courses (on course_id)

**Result:**
```
+---------+-------------+-------+---------+
| student | course_name | grade | credits |
+---------+-------------+-------+---------+
| Alice   | Math        | A     | 3       |
| Alice   | Physics     | B     | 4       |
| Bob     | Math        | A     | 3       |
| Carol   | Chemistry   | C     | 3       |
+---------+-------------+-------+---------+
```

### Query: Find courses with no enrolled students
```sql
SELECT c.course_name, c.credits
FROM courses c
LEFT JOIN enrollments e ON c.id = e.course_id
WHERE e.id IS NULL;
```

**Result:**
```
+-------------+---------+
| course_name | credits |
+-------------+---------+
| Biology     | 4       |
+-------------+---------+
```

---

## COMMON DOUBTS & EDGE CASES

### 1. **What if join columns have NULL values?**

```sql
-- customers
+----+--------+-------------+
| id | name   | referrer_id |
+----+--------+-------------+
| 1  | Alice  | NULL        |
| 2  | Bob    | 1           |
| 3  | Carol  | 1           |
| 4  | David  | NULL        |
+----+--------+-------------+

SELECT 
    c1.name AS customer,
    c2.name AS referrer
FROM customers c1
LEFT JOIN customers c2 ON c1.referrer_id = c2.id;
```

**Result:**
```
+----------+----------+
| customer | referrer |
+----------+----------+
| Alice    | NULL     |  -- referrer_id is NULL, no match
| Bob      | Alice    |  -- referrer_id=1 matches Alice
| Carol    | Alice    |  -- referrer_id=1 matches Alice
| David    | NULL     |  -- referrer_id is NULL, no match
+----------+----------+
```

**Key point:** `NULL = NULL` is **FALSE** in SQL. NULL values in join columns won't match with each other.

### 2. **One-to-Many vs Many-to-Many**

**One-to-Many:** One customer → many orders (shown in earlier examples)
- Result rows = sum of all matching pairs

**Many-to-Many:** Students ↔ Courses (requires junction table)
```sql
-- One student can take many courses
-- One course can have many students
-- enrollments table bridges them
```

### 3. **JOIN with Additional Conditions**

```sql
-- Find orders placed by customers in New York with amount > 200
SELECT c.name, o.order_id, o.amount
FROM customers c
INNER JOIN orders o 
    ON c.id = o.customer_id 
    AND c.city = 'New York'  -- Additional condition in JOIN
WHERE o.amount > 200;
```

**Alternative (same result):**
```sql
SELECT c.name, o.order_id, o.amount
FROM customers c
INNER JOIN orders o ON c.id = o.customer_id
WHERE c.city = 'New York' 
    AND o.amount > 200;
```

**Performance difference:** For INNER JOIN, both are equivalent. For OUTER JOINs, conditions in ON vs WHERE produce different results.

### 4. **OUTER JOIN: ON vs WHERE**

```sql
-- Version 1: Condition in ON
SELECT c.name, o.order_id, o.amount
FROM customers c
LEFT JOIN orders o 
    ON c.id = o.customer_id 
    AND o.amount > 200;
```
**Result:** All customers + only orders with amount > 200
```
Alice | 101  | 250
Alice | 102  | 400
Bob   | NULL | NULL  -- Bob's order (150) filtered out by ON, but Bob still included
Carol | NULL | NULL
David | NULL | NULL
```

```sql
-- Version 2: Condition in WHERE
SELECT c.name, o.order_id, o.amount
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.amount > 200;
```
**Result:** Only customers with orders > 200
```
Alice | 101 | 250
Alice | 102 | 400
```
**Bob excluded** because his order doesn't meet WHERE condition, and WHERE filters the final result (converts LEFT JOIN to INNER JOIN behavior).

### 5. **Duplicate Column Names**

```sql
-- Both tables have 'id' column
SELECT id, name, order_id  -- ERROR: Column 'id' is ambiguous
FROM customers
INNER JOIN orders ON customers.id = orders.customer_id;
```

**Solution: Use table aliases**
```sql
SELECT c.id, c.name, o.order_id, o.id AS order_table_id
FROM customers c
INNER JOIN orders o ON c.id = o.customer_id;
```

---

## PERFORMANCE TIPS

1. **Index join columns** for faster lookups:
```sql
CREATE INDEX idx_customer_id ON orders(customer_id);
```

2. **Filter early:** Use WHERE before JOIN when possible:
```sql
-- Better: Filter orders first
SELECT c.name, o.order_id
FROM customers c
INNER JOIN (SELECT * FROM orders WHERE amount > 1000) o
    ON c.id = o.customer_id;
```

3. **Avoid CROSS JOIN** unless absolutely necessary (exponential row growth).

4. **Use EXISTS instead of JOIN** when you only need to check existence:
```sql
-- Faster for large tables
SELECT c.name
FROM customers c
WHERE EXISTS (SELECT 1 FROM orders o WHERE o.customer_id = c.id);
```
