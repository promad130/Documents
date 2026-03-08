NESTED QUERIES (Subqueries)

A query inside another query. The inner query executes first.

### A. Independent Nested Queries

Inner query runs once; outer query uses its result.

#### Using `=` (expects single value)

SQL

```
-- Find employees earning more than Alice
SELECT name, salary 
FROM employees
WHERE salary > (SELECT salary FROM employees WHERE name = 'Alice');
```

If the subquery returns >1 row: **ERROR 1242: Subquery returns more than 1 row**

#### Using `IN` (handles multiple values)

SQL

```
-- Find employees in departments with budget > 100000
SELECT name 
FROM employees
WHERE dept_id IN (SELECT id FROM departments WHERE budget > 100000);
```

#### Using `ALL`

Compares against **all** values.

SQL

```
-- Find employees earning more than ALL employees in dept 3
SELECT name, salary
FROM employees
WHERE salary > ALL (SELECT salary FROM employees WHERE dept_id = 3);
```

If dept 3 salaries are [30000, 40000, 45000], this finds employees earning > 45000.

#### Using `ANY`

Compares against **any** value.

SQL

```
-- Find employees earning more than ANY employee in dept 3
SELECT name, salary
FROM employees
WHERE salary > ANY (SELECT salary FROM employees WHERE dept_id = 3);
```

If dept 3 salaries are [30000, 40000, 45000], this finds employees earning > 30000.

### B. Correlated Nested Queries

Inner query depends on outer query. Runs **once per row** → SLOW.

SQL

```
-- Find employees earning above their department's average
SELECT e1.name, e1.salary, e1.dept_id
FROM employees e1
WHERE e1.salary > (
    SELECT AVG(e2.salary) 
    FROM employees e2 
    WHERE e2.dept_id = e1.dept_id  -- depends on outer query
);
```

**Execution:**

1. Outer query processes employee 1 (dept_id = 2)
2. Inner query calculates AVG salary for dept 2
3. Compare employee 1's salary to this average
4. Repeat for **each** employee

### C. EXISTS Operator

Checks if subquery returns **any rows** (doesn't care about values).

SQL

```
-- Find customers who placed at least one order
SELECT c.name
FROM customers c
WHERE EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);
```

**Key insight:** `SELECT 1`, `SELECT *`, or `SELECT NULL` all work the same. EXISTS only checks if **rows exist**, not their values.

**Doubt: What if subquery returns NULL?**

SQL

```
WHERE EXISTS (SELECT NULL FROM orders WHERE customer_id = 1);
```

If `customer_id = 1` has orders, this returns TRUE (rows exist). If no orders, returns FALSE (no rows).
