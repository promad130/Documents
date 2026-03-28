### **What is WITH CHECK OPTION?**

`WITH CHECK OPTION` is a **directive** that says:

_"Only allow inserts/updates that satisfy the view's WHERE condition. Reject anything that would violate it."_

**It prevents the weird disappearing data problem.**

### **Syntax:**

SQL

```
CREATE VIEW view_name AS
SELECT columns
FROM table
WHERE condition
WITH CHECK OPTION;
        ^^^^^^^^^^^^^^^^
        Add this at the end!
```

### **Example: With vs Without**

#### **WITHOUT WITH CHECK OPTION (Confusing):**

```mySQL
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT movie_id, title, rental_price
FROM movies
WHERE rental_price > 5.00;

-- Try to insert cheap movie
INSERT INTO EXPENSIVE_MOVIES (title, rental_price) 
VALUES ('Budget Film', 3.99);
-- ✓ Succeeds! But you won't see it in the view
```

---

#### **WITH WITH CHECK OPTION (Safe):**

```mySQL
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT movie_id, title, rental_price
FROM movies
WHERE rental_price > 5.00
WITH CHECK OPTION;
       ^^^^^^^^^^^^^^^^

-- Try to insert cheap movie
INSERT INTO EXPENSIVE_MOVIES (title, rental_price) 
VALUES ('Budget Film', 3.99);
-- ❌ ERROR 3527: Check option failed 'EXPENSIVE_MOVIES'
-- MySQL rejects it immediately!
```

**Much better!** Now you get an immediate error instead of silent confusion.

---

### **Is WITH CHECK OPTION Part of CREATE VIEW or SELECT?**

**Answer: It's part of CREATE VIEW syntax.**

```MySQL
CREATE VIEW view_name AS
    SELECT ...           ← SELECT query part
    FROM ...
    WHERE ...
WITH CHECK OPTION;      ← CREATE VIEW directive (not SELECT)
```

It comes **after the entire SELECT statement**.

**You CANNOT use it like WHERE:**

```MySQL
-- ❌ WRONG: Can't use WHERE to filter
SELECT * FROM EXPENSIVE_MOVIES
WITH CHECK OPTION;
-- This makes no sense!

-- ✓ CORRECT: Only when defining the view
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT ...
WHERE ...
WITH CHECK OPTION;
```

---

## PART 3: VALUES OF WITH CHECK OPTION

### There are TWO types:

#### Type 1: CASCADED (Default)

```MySQL
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT movie_id, title, rental_price
FROM movies
WHERE rental_price > 5.00
WITH CHECK OPTION;
-- Same as: WITH CASCADED CHECK OPTION
```

**What it does:**

- Checks the view's WHERE condition
- Also checks ALL underlying view's conditions (if this view is based on another view)

**Example:**

```MySQL
-- Base view: Only active movies
CREATE VIEW ACTIVE_MOVIES AS
SELECT movie_id, title, rental_price, is_active
FROM movies
WHERE is_active = 1
WITH CASCADED CHECK OPTION;

-- Derived view (based on ACTIVE_MOVIES)
CREATE VIEW EXPENSIVE_ACTIVE_MOVIES AS
SELECT movie_id, title, rental_price
FROM ACTIVE_MOVIES
WHERE rental_price > 5.00
WITH CASCADED CHECK OPTION;

-- Try to insert
INSERT INTO EXPENSIVE_ACTIVE_MOVIES (title, rental_price, is_active) 
VALUES ('Movie', 3.99, 1);
-- ❌ FAILS: rental_price (3.99) ≤ 5.00 (EXPENSIVE_ACTIVE_MOVIES condition)

INSERT INTO EXPENSIVE_ACTIVE_MOVIES (title, rental_price, is_active) 
VALUES ('Movie', 5.99, 0);
-- ❌ FAILS: is_active (0) ≠ 1 (ACTIVE_MOVIES condition)
-- CASCADED checks BOTH conditions!

INSERT INTO EXPENSIVE_ACTIVE_MOVIES (title, rental_price, is_active) 
VALUES ('Movie', 5.99, 1);
-- ✓ SUCCEEDS: Both conditions satisfied
```

---

#### Type 2: LOCAL
```MySQL
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT movie_id, title, rental_price
FROM movies
WHERE rental_price > 5.00
WITH LOCAL CHECK OPTION;
```

**What it does:**

- Checks ONLY the view's own WHERE condition
- Ignores underlying view conditions

**Example:**

```MySQL

-- Base view
CREATE VIEW ACTIVE_MOVIES AS
SELECT movie_id, title, rental_price, is_active
FROM movies
WHERE is_active = 1
WITH LOCAL CHECK OPTION;

-- Derived view (based on ACTIVE_MOVIES)
CREATE VIEW EXPENSIVE_ACTIVE_MOVIES AS
SELECT movie_id, title, rental_price
FROM ACTIVE_MOVIES
WHERE rental_price > 5.00
WITH LOCAL CHECK OPTION;

-- Try to insert
INSERT INTO EXPENSIVE_ACTIVE_MOVIES (title, rental_price, is_active) 
VALUES ('Movie', 5.99, 0);
-- ✓ SUCCEEDS! 
-- LOCAL only checks rental_price > 5.00 (✓ 5.99 > 5.00)
-- Ignores is_active = 1 condition from ACTIVE_MOVIES
```

---

### **CASCADED vs LOCAL Comparison:**

|Aspect|CASCADED|LOCAL|
|---|---|---|
|**Checks own view**|✓ YES|✓ YES|
|**Checks underlying views**|✓ YES|✗ NO|
|**Default**|✓ YES|✗ NO|
|**Use case**|Strict validation|Flexible validation|

**Rule of thumb:**

- Use **CASCADED** (default) = Safe, strict
- Use **LOCAL** = Only when you know what you're doing

---

## **PART 4: UPDATABLE VIEWS WITH CHECK OPTION**

### **Inserting with CHECK OPTION:**

```MySQL
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT movie_id, title, rental_price
FROM movies
WHERE rental_price > 5.00
WITH CHECK OPTION;

-- Valid insert
INSERT INTO EXPENSIVE_MOVIES (title, rental_price) 
VALUES ('Inception', 5.99);
-- ✓ SUCCESS: 5.99 > 5.00

-- Invalid insert
INSERT INTO EXPENSIVE_MOVIES (title, rental_price) 
VALUES ('Budget Movie', 3.99);
-- ❌ ERROR: Check option failed
```

---

### **Updating with CHECK OPTION:**

```mySQL
-- Valid update (stays > 5.00)
UPDATE EXPENSIVE_MOVIES 
SET rental_price = 6.99 
WHERE movie_id = 1;
-- ✓ SUCCESS

-- Invalid update (would violate WHERE)
UPDATE EXPENSIVE_MOVIES 
SET rental_price = 3.99 
WHERE movie_id = 1;
-- ❌ ERROR: Check option failed
-- Can't update to 3.99 because then it wouldn't appear in view!
```