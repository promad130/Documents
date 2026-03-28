
## **PART 1: WHAT IS CHECK?**

### **Simple Definition:**

A **CHECK** is a rule that says: *"This column can only have certain values."*

Think of it like a **bouncer at a club**:
- **Rule:** "You must be 21 or older to enter"
- **Your age:** 25
- **Result:** ✓ You enter

vs

- **Your age:** 19
- **Result:** ❌ Bouncer rejects you

That's what CHECK does—it **rejects bad data before it enters your database**.

---

### **Real-World Examples:**

```
✓ "Age must be positive" (no -5 year old people!)
✓ "Price must be greater than 0" (no free movies with negative price!)
✓ "Email must contain @" (must look like an email)
✓ "Percentage must be between 0 and 100"
✓ "Birthday must be before today"
```

---

## **PART 2: WHERE TO PUT CHECK**

### **CHECK goes in CREATE TABLE:**

```sql
CREATE TABLE movies (
    movie_id INT PRIMARY KEY,
    title VARCHAR(100),
    rental_price DECIMAL(5,2) CHECK (rental_price > 0)
                              ^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                              This is the CHECK constraint
);
```

**Breaking it down:**

```
CREATE TABLE movies (
    movie_id INT PRIMARY KEY,
    title VARCHAR(100),
    rental_price DECIMAL(5,2) CHECK (rental_price > 0)
                                           ↑
                                    The condition
                                    
    The CHECK says: "rental_price must be > 0"
);
```

---

## **PART 3: BASIC CHECK EXAMPLES**

### **Example 1: Simple Price Validation**

```sql
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    price DECIMAL(10,2) CHECK (price > 0)
);
```

**What this means:**
- `price DECIMAL(10,2)` = Price column that can store up to $9,999,999.99
- `CHECK (price > 0)` = Price must be greater than 0

**Insert valid data (works):**
```sql
INSERT INTO products VALUES (1, 'Laptop', 999.99);
-- ✓ SUCCESS: 999.99 > 0, so it's accepted
```

**Insert invalid data (fails):**
```sql
INSERT INTO products VALUES (2, 'Mouse', -50);
-- ❌ ERROR 3819: Check constraint 'products_chk_1' is violated
```

**Why does it fail?**
- `-50` is NOT greater than 0
- The CHECK rule rejects it
- The data is **never inserted**

---

### **Example 2: Age Validation**

```sql
CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT CHECK (age >= 18)
);
```

**What this means:**
- `age INT` = Age column (whole numbers)
- `CHECK (age >= 18)` = Age must be 18 or more

**Valid inserts:**
```sql
INSERT INTO customers VALUES (1, 'Alice', 25);   -- ✓ 25 >= 18
INSERT INTO customers VALUES (2, 'Bob', 18);     -- ✓ 18 >= 18
```

**Invalid inserts:**
```sql
INSERT INTO customers VALUES (3, 'Carol', 17);   -- ❌ 17 < 18, REJECTED
INSERT INTO customers VALUES (4, 'David', -5);   -- ❌ -5 < 18, REJECTED
INSERT INTO customers VALUES (5, 'Eve', 0);      -- ❌ 0 < 18, REJECTED
```

---

### **Example 3: Percentage Range**

```sql
CREATE TABLE grades (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    percentage DECIMAL(5,2) CHECK (percentage >= 0 AND percentage <= 100)
);
```

**What this means:**
- `CHECK (percentage >= 0 AND percentage <= 100)` = Percentage between 0 and 100
- The `AND` means **both conditions must be true**

**Valid inserts:**
```sql
INSERT INTO grades VALUES (1, 'Alice', 85.50);   -- ✓ Between 0 and 100
INSERT INTO grades VALUES (2, 'Bob', 0);         -- ✓ Exactly 0
INSERT INTO grades VALUES (3, 'Carol', 100);     -- ✓ Exactly 100
```

**Invalid inserts:**
```sql
INSERT INTO grades VALUES (4, 'David', -5);      -- ❌ Less than 0
INSERT INTO grades VALUES (5, 'Eve', 105);       -- ❌ More than 100
```

---

## **PART 4: CHECK WITH NAMED CONSTRAINT**

### **Why Name Your Constraint?**

When CHECK fails, it's helpful to know **why**. You can give your CHECK a name:

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    CONSTRAINT salary_positive CHECK (salary > 0)
                ^^^^^^^^^^^^^^^^
                This is the constraint name
);
```

**When you insert invalid data:**

```sql
INSERT INTO employees VALUES (1, 'Alice', -5000);
-- ❌ ERROR: Check constraint 'salary_positive' is violated
--           ^^^^^^^^^^^^^^^^
--           Now you know exactly which constraint failed!
```

**vs without a name:**

```sql
-- ❌ ERROR: Check constraint 'employees_chk_1' is violated
--           ^^^^^^^^^^^^
--           Not very helpful—is this about salary? age? hours?
```

---

## **PART 5: MULTIPLE CHECK CONSTRAINTS**

You can have **many CHECK rules** on the same table:

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    age INT,
    hours_per_week INT,
    CONSTRAINT salary_positive CHECK (salary > 0),
    CONSTRAINT age_valid CHECK (age >= 18),
    CONSTRAINT hours_valid CHECK (hours_per_week <= 40)
);
```

**What each CHECK does:**
1. `salary > 0` → Salary must be positive
2. `age >= 18` → Must be adult
3. `hours_per_week <= 40` → Can't work more than 40 hours

**Valid insert:**
```sql
INSERT INTO employees VALUES (1, 'Alice', 50000, 25, 40);
-- ✓ All checks pass:
--   50000 > 0 ✓
--   25 >= 18 ✓
--   40 <= 40 ✓
```

**Invalid insert (fails one check):**
```sql
INSERT INTO employees VALUES (2, 'Bob', 50000, 25, 50);
-- ❌ ERROR: Check constraint 'hours_valid' is violated
--           (50 is not <= 40)
```

---

## **PART 6: COMMON CHECK CONDITIONS**

### **Comparison Operators:**

```sql
-- Greater than
CHECK (age > 18)

-- Greater than or equal
CHECK (age >= 18)

-- Less than
CHECK (quantity < 1000)

-- Less than or equal
CHECK (temperature <= 100)

-- Equals
CHECK (status = 'Active')

-- Not equals
CHECK (status != 'Deleted')
```

---

### **Range Checks (BETWEEN):**

```sql
-- Age between 18 and 65
CHECK (age BETWEEN 18 AND 65)

-- Same as:
CHECK (age >= 18 AND age <= 65)

-- Both work the same!
```

---

### **Multiple Conditions (AND/OR):**

```sql
-- Must be adult AND employed
CHECK (age >= 18 AND status = 'Employed')

-- OR condition: Student or Retired
CHECK (status = 'Student' OR status = 'Retired')

-- Complex: (Adult OR Parent) AND Employed
CHECK ((age >= 18 OR has_children = 1) AND status = 'Employed')
```

---

### **String Checks (LIKE pattern):**

```sql
-- Email must contain @
CHECK (email LIKE '%@%')

-- Username must start with letter
CHECK (username LIKE '[A-Z]%')

-- Product code must be 5 characters
CHECK (LENGTH(product_code) = 5)
```

---

### **Date Checks:**

```sql
-- Birth date must be in the past
CHECK (birth_date < CURDATE())

-- End date must be after start date
CHECK (end_date > start_date)

-- Event must be within a year
CHECK (DATEDIFF(end_date, start_date) <= 365)
```

---

## **PART 7: PRACTICAL EXAMPLES**

### **Example 1: Complete Movie Database**

```sql
CREATE TABLE movies (
    movie_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    genre VARCHAR(50),
    release_year INT,
    rental_price DECIMAL(5,2),
    duration_minutes INT,
    rating INT,
    
    -- CHECK constraints
    CONSTRAINT price_positive CHECK (rental_price > 0),
    CONSTRAINT year_valid CHECK (release_year >= 1800 AND release_year <= YEAR(CURDATE())),
    CONSTRAINT duration_positive CHECK (duration_minutes > 0),
    CONSTRAINT rating_valid CHECK (rating BETWEEN 1 AND 5)
);
```

**Valid insert:**
```sql
INSERT INTO movies 
(title, genre, release_year, rental_price, duration_minutes, rating)
VALUES 
('Inception', 'Sci-Fi', 2010, 5.99, 148, 5);

-- ✓ All checks pass:
--   5.99 > 0 ✓
--   2010 >= 1800 AND 2010 <= 2026 ✓
--   148 > 0 ✓
--   5 BETWEEN 1 AND 5 ✓
```

**Invalid inserts:**
```sql
INSERT INTO movies 
(title, genre, release_year, rental_price, duration_minutes, rating)
VALUES 
('Bad Movie', 'Drama', 2030, 5.99, 90, 5);
-- ❌ ERROR: release_year 2030 is in the future (> 2026)

INSERT INTO movies 
(title, genre, release_year, rental_price, duration_minutes, rating)
VALUES 
('Bad Movie', 'Drama', 2020, -5.99, 90, 5);
-- ❌ ERROR: price -5.99 is not > 0

INSERT INTO movies 
(title, genre, release_year, rental_price, duration_minutes, rating)
VALUES 
('Bad Movie', 'Drama', 2020, 5.99, -90, 5);
-- ❌ ERROR: duration -90 is not > 0

INSERT INTO movies 
(title, genre, release_year, rental_price, duration_minutes, rating)
VALUES 
('Bad Movie', 'Drama', 2020, 5.99, 90, 10);
-- ❌ ERROR: rating 10 is not BETWEEN 1 AND 5
```

---

### **Example 2: Bank Account Database**

```sql
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    account_holder VARCHAR(100) NOT NULL,
    account_type VARCHAR(20),
    balance DECIMAL(12,2),
    interest_rate DECIMAL(5,2),
    min_balance DECIMAL(10,2),
    
    CONSTRAINT balance_positive CHECK (balance >= 0),
    CONSTRAINT interest_positive CHECK (interest_rate >= 0),
    CONSTRAINT interest_realistic CHECK (interest_rate <= 20),
    CONSTRAINT min_balance_positive CHECK (min_balance >= 0)
);
```

**Valid inserts:**
```sql
INSERT INTO accounts 
(account_holder, account_type, balance, interest_rate, min_balance)
VALUES 
('Alice Johnson', 'Savings', 5000, 2.5, 100);
-- ✓ All checks pass

INSERT INTO accounts 
(account_holder, account_type, balance, interest_rate, min_balance)
VALUES 
('Bob Smith', 'Checking', 2500, 0, 500);
-- ✓ All checks pass (0 interest is valid)
```

**Invalid inserts:**
```sql
INSERT INTO accounts 
(account_holder, account_type, balance, interest_rate, min_balance)
VALUES 
('Carol', 'Savings', -1000, 2.5, 100);
-- ❌ ERROR: balance -1000 is not >= 0

INSERT INTO accounts 
(account_holder, account_type, balance, interest_rate, min_balance)
VALUES 
('David', 'Savings', 5000, 50, 100);
-- ❌ ERROR: interest_rate 50 is not <= 20

INSERT INTO accounts 
(account_holder, account_type, balance, interest_rate, min_balance)
VALUES 
('Eve', 'Savings', 5000, 2.5, -100);
-- ❌ ERROR: min_balance -100 is not >= 0
```

---

### **Example 3: Student Grades Database**

```sql
CREATE TABLE student_grades (
    grade_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    subject VARCHAR(50),
    marks DECIMAL(5,2),
    attendance INT,
    status VARCHAR(20),
    
    CONSTRAINT marks_valid CHECK (marks >= 0 AND marks <= 100),
    CONSTRAINT attendance_valid CHECK (attendance >= 0 AND attendance <= 100),
    CONSTRAINT status_valid CHECK (status IN ('Pass', 'Fail', 'Incomplete'))
);
```

**What these checks do:**
1. `marks BETWEEN 0 and 100` → Marks must be 0-100
2. `attendance BETWEEN 0 and 100` → Attendance must be 0-100%
3. `status IN (...)` → Status must be one of three values

**Valid inserts:**
```sql
INSERT INTO student_grades 
(student_id, subject, marks, attendance, status)
VALUES 
(101, 'Math', 85.5, 95, 'Pass');
-- ✓ All checks pass

INSERT INTO student_grades 
(student_id, subject, marks, attendance, status)
VALUES 
(102, 'Science', 45, 60, 'Fail');
-- ✓ All checks pass
```

**Invalid inserts:**
```sql
INSERT INTO student_grades 
(student_id, subject, marks, attendance, status)
VALUES 
(103, 'English', 105, 80, 'Pass');
-- ❌ ERROR: marks 105 is not between 0 and 100

INSERT INTO student_grades 
(student_id, subject, marks, attendance, status)
VALUES 
(104, 'History', 75, 150, 'Pass');
-- ❌ ERROR: attendance 150 is not between 0 and 100

INSERT INTO student_grades 
(student_id, subject, marks, attendance, status)
VALUES 
(105, 'Geography', 65, 75, 'Missing');
-- ❌ ERROR: 'Missing' is not in ('Pass', 'Fail', 'Incomplete')
```

---

## **PART 8: CHECKING WITH UPDATE**

CHECK also protects you when **updating** data:

```sql
-- Start with a movie
INSERT INTO movies 
(title, genre, release_year, rental_price, duration_minutes, rating)
VALUES 
('Inception', 'Sci-Fi', 2010, 5.99, 148, 5);

-- Try to update price to negative
UPDATE movies 
SET rental_price = -10 
WHERE movie_id = 1;
-- ❌ ERROR: Check constraint 'price_positive' is violated

-- Valid update
UPDATE movies 
SET rental_price = 7.99 
WHERE movie_id = 1;
-- ✓ SUCCESS: New price is positive
```

---

## **PART 9: VIEW ALL CONSTRAINTS**

### **Check your table's constraints:**

```sql
-- Method 1: Show table structure
DESCRIBE movies;

-- Shows all columns and their properties
-- (doesn't show CHECK details clearly)

-- Method 2: Show CREATE TABLE statement
SHOW CREATE TABLE movies\G

-- Shows the full CREATE TABLE including all CHECKs
```

**Example output:**
```
CREATE TABLE `movies` (
  `movie_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `rental_price` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`movie_id`),
  CONSTRAINT `price_positive` CHECK ((`rental_price` > 0))
) ENGINE=InnoDB
```

---

## **PART 10: REMOVE A CHECK CONSTRAINT**

If you don't want a CHECK anymore, you can drop it:

```sql
-- Drop a named constraint
ALTER TABLE movies DROP CONSTRAINT price_positive;

-- Now you can insert negative prices
INSERT INTO movies 
(title, genre, release_year, rental_price, duration_minutes, rating)
VALUES 
('Free Movie', 'Comedy', 2020, -1, 90, 3);
-- ✓ Now it works! (The CHECK is gone)
```

---

## **PART 11: PRACTICE EXERCISES**

### **Exercise 1: Create a Product Table**

Create a table with these requirements:
- Product ID (auto-increment primary key)
- Product name (required)
- Price (must be > 0)
- Stock (must be >= 0)
- Discount percentage (must be between 0 and 50)

**Your code:**
```sql
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2),
    stock INT,
    discount_percentage DECIMAL(5,2),
    
    -- Add CHECK constraints here
    CONSTRAINT ???
);
```

**Solution:**
```sql
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2),
    stock INT,
    discount_percentage DECIMAL(5,2),
    
    CONSTRAINT price_positive CHECK (price > 0),
    CONSTRAINT stock_positive CHECK (stock >= 0),
    CONSTRAINT discount_valid CHECK (discount_percentage >= 0 AND discount_percentage <= 50)
);
```

---

### **Exercise 2: Create a Movie Ratings Table**

Create a table with:
- Rating ID (primary key, auto-increment)
- Movie ID (integer)
- Reviewer name (required)
- Rating (must be 1-5)
- Review text
- Review date (must be before today)

**Your code:**
```sql
CREATE TABLE movie_ratings (
    -- Your code here
);
```

**Solution:**
```sql
CREATE TABLE movie_ratings (
    rating_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT NOT NULL,
    reviewer_name VARCHAR(100) NOT NULL,
    rating INT,
    review_text TEXT,
    review_date DATE,
    
    CONSTRAINT rating_valid CHECK (rating >= 1 AND rating <= 5),
    CONSTRAINT review_date_valid CHECK (review_date <= CURDATE())
);
```

---

### **Exercise 3: Test Your Table**

Using the products table from Exercise 1:

**Try these inserts:**

```sql
-- 1. Valid insert
INSERT INTO products (product_name, price, stock, discount_percentage) 
VALUES ('Laptop', 999.99, 10, 10);
-- Should work ✓

-- 2. Invalid price
INSERT INTO products (product_name, price, stock, discount_percentage) 
VALUES ('Mouse', -50, 100, 5);
-- Should fail ❌ Why?

-- 3. Invalid stock
INSERT INTO products (product_name, price, stock, discount_percentage) 
VALUES ('Keyboard', 50, -5, 0);
-- Should fail ❌ Why?

-- 4. Invalid discount
INSERT INTO products (product_name, price, stock, discount_percentage) 
VALUES ('Monitor', 300, 20, 100);
-- Should fail ❌ Why?

-- 5. Another valid insert
INSERT INTO products (product_name, price, stock, discount_percentage) 
VALUES ('Chair', 200, 15, 25);
-- Should work ✓
```

---

## **PART 12: COMMON MISTAKES**

### **Mistake 1: Forgetting Parentheses**

```sql
-- ❌ WRONG
CHECK price > 0

-- ✓ CORRECT
CHECK (price > 0)
```

Always use parentheses around the condition!

---

### **Mistake 2: Using Wrong Operators**

```sql
-- ❌ WRONG: Single = is assignment, not comparison
CHECK (status = "Active")  -- Might work, but risky

-- ✓ CORRECT: Use == or just =
CHECK (status = 'Active')  -- Use single quotes for strings
```

---

### **Mistake 3: Forgetting Quotes on Strings**

```sql
-- ❌ WRONG
CHECK (status = Active)
-- ERROR: Active is treated as a column name, not a string

-- ✓ CORRECT
CHECK (status = 'Active')
-- 'Active' is a string value
```

---

### **Mistake 4: Using AND/OR Incorrectly**

```sql
-- ❌ WRONG: Multiple conditions without AND/OR
CHECK (age >= 18, salary > 0)

-- ✓ CORRECT: Use AND or OR
CHECK (age >= 18 AND salary > 0)
```

---

### **Mistake 5: Checking Impossible Conditions**

```sql
-- ❌ WRONG: No number can be both > 100 and < 50
CHECK (age > 100 AND age < 50)
-- This will always be false!

-- ✓ CORRECT
CHECK (age > 18 AND age < 100)
```

---

## **PART 13: QUICK REFERENCE**

### **Common CHECK Examples:**

```sql
-- Age (must be adult)
CHECK (age >= 18)

-- Price (must be positive)
CHECK (price > 0)

-- Percentage (0-100)
CHECK (percentage BETWEEN 0 AND 100)

-- Status (must be one of three values)
CHECK (status IN ('Active', 'Inactive', 'Pending'))

-- Date (must be in past)
CHECK (created_date <= CURDATE())

-- Range (between two values)
CHECK (quantity >= 10 AND quantity <= 1000)

-- String length (minimum 5 characters)
CHECK (LENGTH(username) >= 5)

-- Email format (contains @)
CHECK (email LIKE '%@%')

-- Two dates (end after start)
CHECK (end_date > start_date)

-- Not equal
CHECK (status != 'Deleted')
```

---

## **PART 14: SUMMARY**

**CHECK is:**
- ✓ A simple rule that prevents bad data
- ✓ Applied when inserting/updating
- ✓ Fast and efficient
- ✓ Applied automatically
- ✗ Can only check current row
- ✗ Can't reference other tables

**Use CHECK for:**
- Domain validation (price > 0, age >= 18)
- Range validation (0-100)
- Format validation (email contains @)
- Simple mathematical relationships (end_date > start_date)

**Don't use CHECK for:**
- Complex business rules (need ASSERTION/TRIGGER)
- Rules involving other tables
- Operations that need side effects
