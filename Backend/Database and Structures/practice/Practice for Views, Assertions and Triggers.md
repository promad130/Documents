I'll generate comprehensive practice questions for Views, Assertions, and Triggers, progressing from basic to advanced. These will help you master all three concepts.

---

# **PRACTICE QUESTIONS: Views, Assertions, and Triggers**

## **PART 1: VIEWS (Basic to Advanced)**

### **BASIC LEVEL (Start Here)**

#### **Question 1.1: Simple View Creation**
Create a database for a movie rental store with these tables:

```sql
CREATE TABLE movies (
    movie_id INT PRIMARY KEY,
    title VARCHAR(100),
    genre VARCHAR(50),
    release_year INT,
    rental_price DECIMAL(5,2)
);

CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    city VARCHAR(50)
);

CREATE TABLE rentals (
    rental_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    movie_id INT,
    rental_date DATE,
    return_date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

-- Insert sample data
INSERT INTO movies VALUES 
(1, 'Inception', 'Sci-Fi', 2010, 5.99),
(2, 'Titanic', 'Drama', 1997, 3.99),
(3, 'Avatar', 'Sci-Fi', 2009, 6.99);

INSERT INTO customers VALUES 
(101, 'Alice Johnson', 'alice@email.com', 'New York'),
(102, 'Bob Smith', 'bob@email.com', 'Los Angeles'),
(103, 'Carol White', 'carol@email.com', 'Chicago');

INSERT INTO rentals VALUES 
(NULL, 101, 1, '2026-01-01', '2026-01-05'),
(NULL, 102, 2, '2026-01-02', '2026-01-06'),
(NULL, 101, 3, '2026-01-03', '2026-01-07');

```

**Your Task:**
1. Create a view called `MOVIE_DETAILS` that shows movie title, genre, and rental price
2. Query this view to display all movies
3. Create another view called `CUSTOMER_PUBLIC` that shows only customer name and email (hide city)
4. Query the view to verify it works

**What you're learning:** Basic view syntax, data hiding with views

---

#### **Question 1.2: View with WHERE Clause**
Using the movie rental database:

**Your Task:**
1. Create a view called `EXPENSIVE_MOVIES` that shows only movies with rental_price > 5.00
2. Include columns: title, genre, rental_price
3. Query the view
4. Try to insert a movie with rental_price = 4.50 through this view—what happens?

**What you're learning:** Views with filtering, limitations of updatable views

---

#### **Question 1.3: View with JOIN**
Using the movie rental database:

**Your Task:**
1. Create a view called `RENTAL_HISTORY` that joins customers and rentals
2. Include: customer name, rental_date, return_date
3. Query this view to see all rental history
4. Create a second view called `ACTIVE_RENTALS` that shows only rentals where return_date IS NULL (still being rented)

**What you're learning:** JOINs in views, filtering with NULL

---

### **INTERMEDIATE LEVEL**

#### **Question 1.4: View with Aggregation and GROUP BY**
Using the movie rental database:

**Your Task:**
1. Create a view called `RENTAL_STATS` that shows:
   - Customer name
   - Number of rentals per customer
   - Total amount spent on rentals (SUM of rental_price)
   
2. Group by customer
3. Query this view
4. Try to UPDATE a value in this view—what error do you get?

**What you're learning:** Aggregate functions in views, why aggregated views are non-updatable

---

#### **Question 1.5: View with CASE Statement**
Using the movie rental database:

**Your Task:**
1. Create a view called `MOVIE_CATEGORY_ANALYSIS` that shows:
   - Movie title
   - Genre
   - Rental price
   - Price category: 
     - "Budget" if price < 4.00
     - "Standard" if price 4.00-5.99
     - "Premium" if price >= 6.00

2. Query this view
3. Try to insert a movie through this view

**What you're learning:** CASE statements in views, computed columns

---

#### **Question 1.6: View Updatability Challenge**
Using the movie rental database:

**Your Task:**

Create THREE views with different properties:

**View 1: UPDATABLE**
```sql
CREATE VIEW updatable_view AS
SELECT movie_id, title, rental_price
FROM movies;
```

**View 2: NON-UPDATABLE (Has WHERE)**
```sql
CREATE VIEW non_updatable_where AS
SELECT movie_id, title, rental_price
FROM movies
WHERE rental_price > 5.00;
```

**View 3: NON-UPDATABLE (Has DISTINCT)**
```sql
CREATE VIEW non_updatable_distinct AS
SELECT DISTINCT genre
FROM movies;
```

**Your Tasks:**
1. Try this INSERT on View 1:
   ```sql
   INSERT INTO updatable_view VALUES (4, 'Interstellar', 7.99);
   ```
   Should succeed or fail? Why?

2. Try this INSERT on View 2:
   ```sql
   INSERT INTO non_updatable_where VALUES (5, 'Parasite', 3.99);
   ```
   Should succeed or fail? Why?

3. Try this INSERT on View 3:
   ```sql
   INSERT INTO non_updatable_distinct (genre) VALUES ('Horror');
   ```
   Should succeed or fail? Why?

4. Try this UPDATE on View 1:
   ```sql
   UPDATE updatable_view SET rental_price = 9.99 WHERE movie_id = 1;
   ```
   Does it work? Check the base table.

**What you're learning:** When views can be updated, limitations, view constraints

---

#### **Question 1.7: Nested Views**
Using the movie rental database:

**Your Task:**
1. Create first-level view: `GENRE_STATS` showing genre and count of movies per genre
2. Create second-level view: `POPULAR_GENRES` that filters GENRE_STATS to only show genres with more than 1 movie
3. Query the second-level view
4. Drop the GENRE_STATS view—what happens to POPULAR_GENRES?

**What you're learning:** View dependencies, cascading drops

---

### **ADVANCED LEVEL**

#### **Question 1.8: View with WITH CHECK OPTION**
Using the movie rental database:

**Your Task:**
1. Create view without WITH CHECK OPTION:
   ```sql
   CREATE VIEW sci_fi_movies AS
   SELECT movie_id, title, genre, rental_price
   FROM movies
   WHERE genre = 'Sci-Fi';
   ```

2. Try this UPDATE:
   ```sql
   UPDATE sci_fi_movies SET genre = 'Drama' WHERE movie_id = 1;
   ```
   What happens? The row disappears from the view. Is this good or bad?

3. Now create the same view WITH CHECK OPTION:
   ```sql
   CREATE OR REPLACE VIEW sci_fi_movies AS
   SELECT movie_id, title, genre, rental_price
   FROM movies
   WHERE genre = 'Sci-Fi'
   WITH CHECK OPTION;
   ```

4. Try the same UPDATE again—what happens?

**What you're learning:** WITH CHECK OPTION enforces view definition

---

#### **Question 1.9: Complex View with Multiple JOINs and Aggregation**
Using the movie rental database (add more sample data):

```sql
CREATE TABLE reviews (
    review_id INT PRIMARY KEY,
    movie_id INT,
    customer_id INT,
    rating INT, -- 1 to 5
    review_text TEXT,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

INSERT INTO reviews VALUES 
(1, 1, 101, 5, 'Amazing movie!'),
(2, 1, 102, 4, 'Very good'),
(3, 2, 103, 3, 'Decent'),
(4, 1, 103, 5, 'Best ever');
```

**Your Task:**
1. Create a view called `MOVIE_PERFORMANCE_REPORT` that shows:
   - Movie title
   - Genre
   - Number of rentals
   - Number of reviews
   - Average rating
   - Total revenue from this movie

2. Query this view
3. Order by average rating descending
4. Filter to show only movies with at least 2 reviews

**What you're learning:** Complex real-world view design

---

#### **Question 1.10: View Security & Information Hiding**
Create an HR database:

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    department VARCHAR(50),
    hire_date DATE,
    ssn VARCHAR(11)
);

INSERT INTO employees VALUES 
(1, 'Alice', 75000, 'IT', '2020-01-15', '123-45-6789'),
(2, 'Bob', 65000, 'IT', '2020-03-20', '234-56-7890'),
(3, 'Carol', 85000, 'Sales', '2019-06-10', '345-67-8901');
```

**Your Task:**
1. Create view `EMPLOYEE_DIRECTORY` (for general staff):
   - Show: name, department, hire_date
   - Hide: salary, SSN

2. Create view `PAYROLL_VIEW` (for HR only):
   - Show: emp_id, name, salary
   - Hide: SSN, hire_date

3. Create view `MANAGER_VIEW` (for managers):
   - Show: name, salary, department
   - Hide: SSN, hire_date

4. Query all three views and verify data is properly hidden

**What you're learning:** Role-based security using views

---

---

## **PART 2: ASSERTIONS (Basic to Advanced)**

### **BASIC LEVEL**

#### **Question 2.1: Simple Domain Assertion**
Using the movie rental database:

**Your Task:**
Create an assertion (using triggers) to ensure:
- Rental price cannot be negative
- Release year cannot be in the future

```sql
-- Your implementation:
DELIMITER $$

CREATE TRIGGER check_movie_price
BEFORE INSERT ON movies
FOR EACH ROW
BEGIN
    IF NEW.rental_price < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Rental price cannot be negative';
    END IF;
END$$

DELIMITER ;
```

**Test:**
1. Try to insert a movie with negative price—should fail
2. Try to insert a valid movie—should succeed
3. Try to update a movie to negative price—should fail

**What you're learning:** Basic trigger-based assertions, SIGNAL SQLSTATE

---

#### **Question 2.2: Business Rule Assertion**
Using the movie rental database:

**Your Task:**
Create an assertion: "Return date must be after rental date"

```sql
-- Write your trigger here
```

**Test:**
1. Insert a rental with return_date BEFORE rental_date—should fail
2. Insert a valid rental—should succeed

**What you're learning:** Date comparison in assertions

---

### **INTERMEDIATE LEVEL**

#### **Question 2.3: Cross-Table Assertion**
Using the banking database:

```sql
CREATE TABLE branch (
    branch_id INT PRIMARY KEY,
    branch_name VARCHAR(100),
    location VARCHAR(100)
);

CREATE TABLE account (
    account_no VARCHAR(20) PRIMARY KEY,
    branch_id INT,
    balance DECIMAL(12,2),
    FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
);

CREATE TABLE loan (
    loan_no VARCHAR(20) PRIMARY KEY,
    branch_id INT,
    amount DECIMAL(12,2),
    FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
);

INSERT INTO branch VALUES (1, 'Downtown', 'Main Street'), (2, 'Uptown', 'North Ave');
INSERT INTO account VALUES ('A-101', 1, 5000), ('A-102', 1, 8000), ('A-103', 2, 1200);
INSERT INTO loan VALUES ('L-11', 1, 900), ('L-14', 1, 1500), ('L-15', 2, 500);
```

**Your Task:**
Create an assertion: "For each branch, total loans cannot exceed total deposits"

```sql
-- Write your trigger here
-- Hint: Use a CHECK trigger on INSERT/UPDATE for both LOAN and ACCOUNT tables
```

**Test:**
1. Try to insert a loan that would violate the constraint—should fail
2. Try to reduce account balance to violate constraint—should fail
3. Add a new branch with small deposits and large loans—should fail

**What you're learning:** Complex assertions spanning multiple tables

---

#### **Question 2.4: Temporal Assertion**
Using the rental database:

**Your Task:**
Create an assertion: "Each customer can rent maximum 5 movies at a time (unreturned)"

```sql
-- Write your trigger here
-- Hint: COUNT active rentals before allowing new ones
```

**Test:**
1. Insert rentals until you hit 5—should work
2. Try to insert 6th rental—should fail
3. Return a movie, then insert 6th—should work

**What you're learning:** Aggregate-based assertions, COUNT in triggers

---

#### **Question 2.5: Referential Integrity Assertion**
Using the rental database:

**Your Task:**
Create an assertion: "Cannot rent a movie that doesn't exist"

(Note: This is usually handled by FOREIGN KEY, but let's do it with a trigger assertion)

```sql
-- Write your trigger here
-- Hint: Check if movie_id exists in movies table
```

**Test:**
1. Try to rent movie_id = 999 (doesn't exist)—should fail
2. Rent a valid movie—should succeed

**What you're learning:** Manual referential integrity with triggers

---

### **ADVANCED LEVEL**

#### **Question 2.6: Multi-Condition Assertion**
Using employee database:

```sql
CREATE TABLE employee (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    manager_id INT,
    hire_date DATE,
    department VARCHAR(50),
    FOREIGN KEY (manager_id) REFERENCES employee(emp_id)
);

INSERT INTO employee VALUES 
(1, 'CEO John', 200000, NULL, '2015-01-01', 'Executive'),
(2, 'Manager Sarah', 120000, 1, '2017-03-15', 'IT'),
(3, 'Employee Bob', 75000, 2, '2020-06-10', 'IT');
```

**Your Task:**
Create assertions:
1. **Salary must match department standards:**
   - Executive: 150000+
   - Manager: 100000-150000
   - Employee: 50000-100000

2. **Employee salary cannot exceed their manager's salary**

```sql
-- Write your triggers here
```

**Test:**
1. Insert CEO with salary 100000 (too low)—should fail
2. Insert manager with salary 180000 (too high)—should fail
3. Insert employee earning more than manager—should fail
4. Update manager's salary below an employee's—should fail
5. Insert valid data—should succeed

**What you're learning:** Complex business rule enforcement with CASE statements

---

#### **Question 2.7: Assertion with Cascading Effects**
Using movie rental database:

**Your Task:**
Create assertions:
1. "When a movie is rented, reduce available_stock by 1"
2. "Stock cannot go below 0"
3. "When rental is returned, increase stock by 1"

First, add a stock column:
```sql
ALTER TABLE movies ADD COLUMN available_stock INT DEFAULT 10;
```

```sql
-- Write your triggers here
-- Hint: Need triggers on INSERT/UPDATE for rentals table
-- Also need a trigger when return_date is set
```

**Test:**
1. Insert a rental—check that stock decreased
2. Try to rent when stock = 0—should fail
3. Return a rental—check that stock increased

**What you're learning:** Cascading updates with triggers, maintaining data consistency

---

#### **Question 2.8: Performance Consideration Assertion**
Using the banking database:

**Your Task:**
Create an assertion with performance implications:
"When loan amount is set, ensure borrower has account at same branch with sufficient balance"

```sql
-- Write your trigger here
-- Time how long it takes to insert 1000 loans
```

**Test:**
1. Create scenario and insert 1000 loans while assertion validates each one
2. Measure execution time
3. Add an INDEX on branch_id in the subquery
4. Measure again—should be faster

**What you're learning:** Assertion performance bottlenecks, importance of indexes

---

#### **Question 2.9: Assertion with State Transitions**
Add status column to rentals:

```sql
ALTER TABLE rentals ADD COLUMN status ENUM('Active', 'Returned', 'Overdue') DEFAULT 'Active';
```

**Your Task:**
Create assertions for valid state transitions:
- Can only go from "Active" → "Returned" or "Overdue"
- Cannot skip states
- Once "Returned", cannot go back to "Active"

```sql
-- Write your triggers here
```

**Test:**
1. Create rental with status "Active"—should work
2. Try to change directly to "Returned"—should work
3. Try to change "Returned" back to "Active"—should fail
4. Try to create "Overdue" directly—should fail

**What you're learning:** State machine enforcement in triggers

---

#### **Question 2.10: Trigger-Based Audit Trail**
Create audit table:

```sql
CREATE TABLE employee_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT,
    column_name VARCHAR(50),
    old_value VARCHAR(255),
    new_value VARCHAR(255),
    changed_by VARCHAR(100),
    changed_at TIMESTAMP
);
```

**Your Task:**
Create triggers on employee table to log all changes to salary:

```sql
-- Write your triggers here
-- Must capture old value and new value
-- Must trigger on UPDATE only
```

**Test:**
1. Insert an employee
2. Update salary—check audit table for new entry
3. Update again—check audit has both values
4. Query audit to see change history

**What you're learning:** Audit logging with triggers, capturing OLD/NEW values

---

---

## **PART 3: TRIGGERS (Basic to Advanced)**

### **BASIC LEVEL**

#### **Question 3.1: Simple BEFORE INSERT Trigger**
Using movies table:

**Your Task:**
Create a trigger that automatically sets `rental_price = 5.99` if not specified:

```sql
-- Write your trigger here
-- Hint: Check if NEW.rental_price is NULL
```

**Test:**
1. Insert movie without specifying price—check it's set to 5.99
2. Insert movie with specific price—check it uses that price

**What you're learning:** BEFORE INSERT trigger, setting default values

---

#### **Question 3.2: AFTER INSERT Trigger**
Add a statistics table:

```sql
CREATE TABLE movie_stats (
    stat_id INT AUTO_INCREMENT PRIMARY KEY,
    total_movies INT,
    total_rentals INT,
    last_updated TIMESTAMP
);

INSERT INTO movie_stats VALUES (1, 0, 0, NOW());
```

**Your Task:**
Create a trigger that updates movie_stats when a new movie is inserted:

```sql
-- Write your trigger here
```

**Test:**
1. Insert 3 new movies
2. Check movie_stats.total_movies—should be 3
3. Insert 2 rentals
4. Check movie_stats.total_rentals—should be 2

**What you're learning:** AFTER INSERT trigger, maintaining aggregated data

---

#### **Question 3.3: BEFORE UPDATE Trigger**
Using movies table:

**Your Task:**
Create a trigger that prevents rental_price from being decreased by more than 10%:

```sql
-- Write your trigger here
-- Hint: Calculate (OLD.price - NEW.price) / OLD.price * 100
```

**Test:**
1. Movie with price 10.00, try to set to 8.00 (20% decrease)—should fail
2. Try to set to 9.00 (10% decrease)—should work
3. Increase price—should always work

**What you're learning:** BEFORE UPDATE trigger, data validation

---

#### **Question 3.4: AFTER DELETE Trigger**
Add an archive table:

```sql
CREATE TABLE deleted_movies_archive (
    archive_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT,
    title VARCHAR(100),
    genre VARCHAR(50),
    deleted_at TIMESTAMP
);
```

**Your Task:**
Create a trigger that archives deleted movies:

```sql
-- Write your trigger here
-- Hint: Use OLD values to get deleted movie info
```

**Test:**
1. Delete a movie
2. Check deleted_movies_archive—should have the deleted movie
3. Delete another movie
4. Verify archive has both

**What you're learning:** AFTER DELETE trigger, soft deletion/archiving

---

### **INTERMEDIATE LEVEL**

#### **Question 3.5: Trigger with Complex Logic**
Using rentals table:

**Your Task:**
Create a trigger that calculates late fees:

```sql
ALTER TABLE rentals ADD COLUMN late_fee DECIMAL(10,2) DEFAULT 0;
```

Rule: $2.00 per day late (any partial day counts)

```sql
-- Write your trigger here
-- Hint: DATEDIFF(return_date, rental_date)
-- Calculate days allowed (3 days), then days overdue
```

**Test:**
1. Rental with rental_date='2026-01-01', return_date='2026-01-05' (4 days, 1 day late)—late_fee = 2.00
2. Rental with rental_date='2026-01-01', return_date='2026-01-09' (8 days, 5 days late)—late_fee = 10.00
3. On-time rental—late_fee = 0

**What you're learning:** Complex calculations in triggers, DATEDIFF function

---

#### **Question 3.6: Trigger Preventing Circular Reference**
Using employee table with manager_id:

**Your Task:**
Create a trigger that prevents an employee from being their own manager (direct or indirect):

```sql
-- Write your trigger here
-- Hint: Check if NEW.manager_id = NEW.emp_id
-- Also check if manager's manager is this employee
```

**Test:**
1. Try to set emp_id = 2, manager_id = 2 (self)—should fail
2. Employee 2 reports to 1, try to set employee 1's manager to 2—should fail (indirect cycle)
3. Valid hierarchy—should work

**What you're learning:** Complex constraint logic, preventing data anomalies

---

#### **Question 3.7: Trigger with Multiple Conditions**
Using orders and inventory:

```sql
CREATE TABLE product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    stock INT,
    min_stock INT DEFAULT 10
);

CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    quantity INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

INSERT INTO product VALUES (1, 'Laptop', 15, 10), (2, 'Mouse', 5, 10);
```

**Your Task:**
Create a trigger that:
1. Prevents ordering more than available stock
2. Updates stock when order is placed
3. Generates alert if stock drops below min_stock

```sql
-- Write your triggers here
-- Hint: Will need trigger + alert table
```

**Test:**
1. Order 10 laptops (stock=15)—should work, stock becomes 5
2. Order 10 mice (stock=5)—should fail
3. Order 3 mice (stock=5)—should work, stock becomes 2, alert generated

**What you're learning:** Multiple validations in single trigger, side effects (generating alerts)

---

### **ADVANCED LEVEL**

#### **Question 3.8: Recursive Trigger (Cascading Updates)**
Using organizational hierarchy:

```sql
CREATE TABLE organization (
    org_id INT PRIMARY KEY,
    org_name VARCHAR(100),
    parent_org_id INT,
    total_employees INT DEFAULT 0,
    FOREIGN KEY (parent_org_id) REFERENCES organization(org_id)
);

CREATE TABLE org_employees (
    emp_id INT,
    org_id INT,
    PRIMARY KEY (emp_id, org_id),
    FOREIGN KEY (org_id) REFERENCES organization(org_id)
);

INSERT INTO organization VALUES (1, 'Company', NULL, 0);
INSERT INTO organization VALUES (2, 'IT Dept', 1, 0);
INSERT INTO organization VALUES (3, 'Sales Dept', 1, 0);
INSERT INTO organization VALUES (4, 'IT Support', 2, 0);
```

**Your Task:**
Create triggers that automatically update total_employees in:
- The department when employee is added
- The parent department
- The parent's parent department
- All the way up

```sql
-- Write your recursive trigger here
-- Hint: Will need to UPDATE parent organization
-- Which might trigger the same UPDATE trigger on parent
```

**Test:**
1. Add 5 employees to IT Support (org_id=4)
2. Check org_id=4: total_employees = 5
3. Check org_id=2: total_employees = 5 (cascaded)
4. Check org_id=1: total_employees = 5 (cascaded)

**What you're learning:** Recursive triggers, cascading updates, hierarchy management

---

#### **Question 3.9: Trigger for Denormalization**
Create denormalized reporting table:

```sql
CREATE TABLE rental_summary (
    summary_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    customer_name VARCHAR(100),
    rental_count INT,
    total_spent DECIMAL(10,2),
    last_rental_date DATE,
    last_updated TIMESTAMP
);
```

**Your Task:**
Create triggers that:
1. Create summary row when customer first rents
2. Update summary when rental is added
3. Update summary when rental is returned

```sql
-- Write your triggers here
-- Multiple triggers needed on rentals table
```

**Test:**
1. Customer rentals 3 movies
2. Check rental_summary: rental_count=3, total_spent=sum of prices
3. Return 1 movie
4. Check rental_summary: rental_count still 3 (total, not active)

**What you're learning:** Maintaining denormalized data with triggers, reporting optimization

---

#### **Question 3.10: Trigger with Error Logging**
Create error log table:

```sql
CREATE TABLE trigger_errors (
    error_id INT AUTO_INCREMENT PRIMARY KEY,
    trigger_name VARCHAR(100),
    table_name VARCHAR(100),
    error_message TEXT,
    attempted_action VARCHAR(50), -- INSERT, UPDATE, DELETE
    error_time TIMESTAMP
);
```

**Your Task:**
Create triggers with comprehensive error logging:

```sql
-- Write your triggers here
-- Log EVERY error that occurs
-- Include context (what was attempted, why it failed)
```

**Test:**
1. Cause 5 different trigger violations
2. Check trigger_errors table
3. Verify all errors logged with details

**What you're learning:** Error handling in triggers, debugging and monitoring

---

---

## **PART 4: INTEGRATION CHALLENGES**

#### **Challenge 4.1: Complete Hospital Management System**

Build views, assertions, and triggers for:

```sql
CREATE TABLE doctors (
    doctor_id INT PRIMARY KEY,
    name VARCHAR(100),
    specialty VARCHAR(100),
    license_number VARCHAR(50),
    availability_hours INT DEFAULT 40
);

CREATE TABLE patients (
    patient_id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    medical_history TEXT
);

CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT,
    patient_id INT,
    appointment_date DATE,
    appointment_time TIME,
    duration_minutes INT,
    status ENUM('Scheduled', 'Completed', 'Cancelled'),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

CREATE TABLE prescriptions (
    prescription_id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT,
    medication VARCHAR(100),
    dosage VARCHAR(50),
    frequency VARCHAR(50),
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);
```

**Your Tasks:**

**VIEWS:**
1. `DOCTOR_AVAILABILITY_VIEW`: Shows doctor, specialty, appointments per month
2. `PATIENT_MEDICAL_SUMMARY`: Shows patient name, age, number of visits, prescribed medications
3. `APPOINTMENT_SCHEDULE`: Shows upcoming appointments with doctor and patient info

**ASSERTIONS (Triggers):**
1. Doctor cannot have overlapping appointments (accounting for duration)
2. Prescription can only be added to completed appointments
3. Doctor's availability hours cannot exceed 40 per week
4. Patient age must be positive

**TRIGGERS:**
1. Mark appointment as "Completed" when prescription is added
2. Log appointment cancellations to audit table
3. Update doctor's total appointments count

**Questions:**
- Can you update the PATIENT_MEDICAL_SUMMARY view? Why or why not?
- What happens if you try to delete a doctor with scheduled appointments?
- Create a scenario where a trigger prevents an invalid operation

---

#### **Challenge 4.2: E-Commerce with Complex Business Rules**

Build complete system with:

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    email VARCHAR(100) UNIQUE,
    account_status ENUM('Active', 'Suspended', 'Deleted'),
    credit_score INT
);

CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    price DECIMAL(10,2),
    stock INT,
    category VARCHAR(50)
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date DATE,
    total_amount DECIMAL(10,2),
    status ENUM('Pending', 'Processing', 'Shipped', 'Delivered', 'Cancelled'),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    unit_price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

**Your Tasks:**

**VIEWS:**
1. `CUSTOMER_ORDER_HISTORY`: Shows customer and their order details
2. `TOP_CUSTOMERS`: Shows customers with highest spending (last 90 days)
3. `LOW_STOCK_PRODUCTS`: Shows products with stock < 20

**ASSERTIONS:**
1. Order total must match SUM of order_items
2. Cannot order product if stock is 0
3. Users with credit_score < 400 can only order max $500 total
4. Order status can only progress forward (Pending → Processing → Shipped → Delivered)

**TRIGGERS:**
1. Update product stock when order is placed
2. Update user's credit_score when order is delivered (add points)
3. Generate invoice PDF path when status changes to "Shipped"
4. Prevent orders when user account is "Suspended"

**Questions:**
- What view would help customer service representatives quickly identify high-value customers?
- What assertion prevents most common errors in orders?
- If you delete a product, what should happen to order_items? Implement with trigger.

---

---

## **ANSWER GUIDE STRUCTURE**

For each question, I'll provide:
1. **Expected SQL Code**
2. **Test Cases with Expected Output**
3. **Common Mistakes to Avoid**
4. **Key Learning Concepts**

---

**Would you like me to provide detailed answers for all these questions, or would you prefer to attempt them first and then request solutions for specific ones?**

I can also create additional follow-up scenarios if you find these helpful!