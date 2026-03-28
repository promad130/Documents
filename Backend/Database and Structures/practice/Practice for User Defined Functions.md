# **USER-DEFINED FUNCTIONS - PRACTICE QUESTIONS**

**Start from basic to advanced. Do them in order.**

---

## **LEVEL 1: BASIC (Variables & Simple Math)**

### **Question 1.1: Simple Addition**

Create a UDF that takes two numbers and returns their sum.

```sql
-- Create function
-- Name: add_two_numbers
-- Parameters: num1 INT, num2 INT
-- Returns: INT (sum of both)

-- Test it
SELECT add_two_numbers(10, 20);  -- Should return 30
SELECT add_two_numbers(5, -3);   -- Should return 2
```

**Expected output:**
```
add_two_numbers(10, 20) = 30
add_two_numbers(5, -3) = 2
```

---

### **Question 1.2: Multiply Two Numbers**

Create a UDF that multiplies two decimal numbers.

```sql
-- Create function
-- Name: multiply_numbers
-- Parameters: num1 DECIMAL(10,2), num2 DECIMAL(10,2)
-- Returns: DECIMAL(10,2)

-- Test it
SELECT multiply_numbers(5.50, 4);      -- Should return 22.00
SELECT multiply_numbers(10.25, 3.5);   -- Should return 35.88
```

---

### **Question 1.3: Calculate Rectangle Area**

Create a UDF that calculates area of a rectangle (length × width).

```sql
-- Create function
-- Name: rectangle_area
-- Parameters: length DECIMAL(10,2), width DECIMAL(10,2)
-- Returns: DECIMAL(10,2)

-- Test it
SELECT rectangle_area(10, 5);     -- Should return 50.00
SELECT rectangle_area(7.5, 4.2);  -- Should return 31.50
```

---

### **Question 1.4: Calculate Circle Area**

Create a UDF that calculates area of a circle (π × r²).

```sql
-- Create function
-- Name: circle_area
-- Parameters: radius DECIMAL(10,2)
-- Returns: DECIMAL(10,2)
-- Formula: 3.14159 * radius * radius

-- Test it
SELECT circle_area(5);    -- Should return ~78.54
SELECT circle_area(10);   -- Should return ~314.16
```

---

### **Question 1.5: Temperature Conversion (F to C)**

Create a UDF that converts Fahrenheit to Celsius.

```sql
-- Create function
-- Name: fahrenheit_to_celsius
-- Parameters: fahrenheit DECIMAL(5,2)
-- Returns: DECIMAL(5,2)
-- Formula: (F - 32) × 5/9

-- Test it
SELECT fahrenheit_to_celsius(32);   -- Should return 0
SELECT fahrenheit_to_celsius(212);  -- Should return 100
SELECT fahrenheit_to_celsius(98.6); -- Should return ~37
```

---

## **LEVEL 2: SIMPLE IF/ELSE**

### **Question 2.1: Check if Positive or Negative**

Create a UDF that returns if a number is positive, negative, or zero.

```sql
-- Create function
-- Name: check_number_sign
-- Parameters: num INT
-- Returns: VARCHAR(20)
-- Logic: 
--   If num > 0 → "Positive"
--   If num < 0 → "Negative"
--   If num = 0 → "Zero"

-- Test it
SELECT check_number_sign(5);    -- Should return "Positive"
SELECT check_number_sign(-10);  -- Should return "Negative"
SELECT check_number_sign(0);    -- Should return "Zero"
```

---

### **Question 2.2: Adult or Minor**

Create a UDF that checks if someone is an adult (18+).

```sql
-- Create function
-- Name: check_age_category
-- Parameters: age INT
-- Returns: VARCHAR(20)
-- Logic:
--   If age >= 18 → "Adult"
--   If age < 18 → "Minor"

-- Test it
SELECT check_age_category(25);  -- Should return "Adult"
SELECT check_age_category(15);  -- Should return "Minor"
SELECT check_age_category(18);  -- Should return "Adult"
```

---

### **Question 2.3: Grade Assignment**

Create a UDF that assigns grades based on percentage.

```sql
-- Create function
-- Name: assign_grade
-- Parameters: percentage DECIMAL(5,2)
-- Returns: CHAR(1)
-- Logic:
--   >= 90 → 'A'
--   >= 80 → 'B'
--   >= 70 → 'C'
--   >= 60 → 'D'
--   < 60  → 'F'

-- Test it
SELECT assign_grade(95);   -- Should return 'A'
SELECT assign_grade(85);   -- Should return 'B'
SELECT assign_grade(75);   -- Should return 'C'
SELECT assign_grade(65);   -- Should return 'D'
SELECT assign_grade(50);   -- Should return 'F'
```

---

### **Question 2.4: Eligibility Check**

Create a UDF that checks if someone is eligible for a loan.
- Age must be >= 21
- Income must be > 30000

```sql
-- Create function
-- Name: check_loan_eligibility
-- Parameters: age INT, income DECIMAL(10,2)
-- Returns: VARCHAR(30)
-- Logic:
--   If age >= 21 AND income > 30000 → "Eligible"
--   Else → "Not Eligible"

-- Test it
SELECT check_loan_eligibility(25, 50000);  -- Should return "Eligible"
SELECT check_loan_eligibility(20, 50000);  -- Should return "Not Eligible"
SELECT check_loan_eligibility(25, 25000);  -- Should return "Not Eligible"
```

---

### **Question 2.5: Discount Calculator**

Create a UDF that calculates discount based on purchase amount.

```sql
-- Create function
-- Name: calculate_discount_percentage
-- Parameters: amount DECIMAL(10,2)
-- Returns: DECIMAL(5,2)
-- Logic:
--   If amount >= 10000 → 20% discount
--   If amount >= 5000  → 15% discount
--   If amount >= 1000  → 10% discount
--   If amount >= 500   → 5% discount
--   Else               → 0% discount

-- Test it
SELECT calculate_discount_percentage(15000);  -- Should return 20.00
SELECT calculate_discount_percentage(7000);   -- Should return 15.00
SELECT calculate_discount_percentage(2000);   -- Should return 10.00
SELECT calculate_discount_percentage(300);    -- Should return 0.00
```

---

## **LEVEL 3: CASE STATEMENTS**

### **Question 3.1: Day of Week**

Create a UDF that returns the day name from a number.

```sql
-- Create function
-- Name: get_day_name
-- Parameters: day_num INT (1-7)
-- Returns: VARCHAR(20)
-- Logic: Use CASE
--   1 → "Monday"
--   2 → "Tuesday"
--   3 → "Wednesday"
--   4 → "Thursday"
--   5 → "Friday"
--   6 → "Saturday"
--   7 → "Sunday"
--   Else → "Invalid Day"

-- Test it
SELECT get_day_name(1);  -- Should return "Monday"
SELECT get_day_name(5);  -- Should return "Friday"
SELECT get_day_name(10); -- Should return "Invalid Day"
```

---

### **Question 3.2: Month Name**

Create a UDF that returns month name from a number.

```sql
-- Create function
-- Name: get_month_name
-- Parameters: month_num INT (1-12)
-- Returns: VARCHAR(20)
-- Logic: Use CASE for all 12 months
--   1 → "January"
--   2 → "February"
--   ... etc
--   12 → "December"
--   Else → "Invalid Month"

-- Test it
SELECT get_month_name(1);   -- Should return "January"
SELECT get_month_name(12);  -- Should return "December"
SELECT get_month_name(13);  -- Should return "Invalid Month"
```

---

### **Question 3.3: Employee Level**

Create a UDF that assigns employee level based on salary.

```sql
-- Create function
-- Name: get_employee_level
-- Parameters: salary DECIMAL(10,2)
-- Returns: VARCHAR(30)
-- Logic: Use CASE
--   >= 100000 → "Executive"
--   >= 70000  → "Senior"
--   >= 40000  → "Mid-Level"
--   >= 20000  → "Entry-Level"
--   < 20000   → "Intern"

-- Test it
SELECT get_employee_level(120000);  -- Should return "Executive"
SELECT get_employee_level(75000);   -- Should return "Senior"
SELECT get_employee_level(50000);   -- Should return "Mid-Level"
```

---

### **Question 3.4: Season from Month**

Create a UDF that returns season name from month number.

```sql
-- Create function
-- Name: get_season
-- Parameters: month_num INT
-- Returns: VARCHAR(20)
-- Logic: Use CASE
--   1, 2, 12 → "Winter"
--   3, 4, 5  → "Spring"
--   6, 7, 8  → "Summer"
--   9, 10, 11 → "Fall"
--   Else → "Invalid Month"

-- Test it
SELECT get_season(1);   -- Should return "Winter"
SELECT get_season(6);   -- Should return "Summer"
SELECT get_season(10);  -- Should return "Fall"
```

---

## **LEVEL 4: LOOPS**

### **Question 4.1: Sum of Numbers**

Create a UDF that sums numbers from 1 to N using WHILE loop.

```sql
-- Create function
-- Name: sum_to_n
-- Parameters: n INT
-- Returns: INT
-- Logic: Use WHILE loop to add 1+2+3+...+n

-- Test it
SELECT sum_to_n(5);   -- Should return 15 (1+2+3+4+5)
SELECT sum_to_n(10);  -- Should return 55 (1+2+...+10)
SELECT sum_to_n(1);   -- Should return 1
```

---

### **Question 4.2: Factorial**

Create a UDF that calculates factorial using WHILE loop.

```sql
-- Create function
-- Name: calculate_factorial
-- Parameters: n INT
-- Returns: INT
-- Logic: Use WHILE loop to calculate n!
--   5! = 5 * 4 * 3 * 2 * 1 = 120

-- Test it
SELECT calculate_factorial(5);  -- Should return 120
SELECT calculate_factorial(3);  -- Should return 6
SELECT calculate_factorial(1);  -- Should return 1
```

---

### **Question 4.3: Count Digits**

Create a UDF that counts digits in a number using WHILE loop.

```sql
-- Create function
-- Name: count_digits
-- Parameters: num INT
-- Returns: INT
-- Logic: Use WHILE loop to count digits
--   123 has 3 digits
--   5000 has 4 digits
--   Hint: Use MOD and division

-- Test it
SELECT count_digits(12345);  -- Should return 5
SELECT count_digits(99);     -- Should return 2
SELECT count_digits(1);      -- Should return 1
```

---

### **Question 4.4: Power Calculation**

Create a UDF that calculates power (base^exponent) using loop.

```sql
-- Create function
-- Name: calculate_power
-- Parameters: base INT, exponent INT
-- Returns: INT
-- Logic: Use WHILE loop
--   2^3 = 2 * 2 * 2 = 8
--   5^2 = 5 * 5 = 25

-- Test it
SELECT calculate_power(2, 3);   -- Should return 8
SELECT calculate_power(5, 2);   -- Should return 25
SELECT calculate_power(10, 1);  -- Should return 10
```

---

### **Question 4.5: Fibonacci Series**

Create a UDF that returns the Nth Fibonacci number.

```sql
-- Create function
-- Name: get_fibonacci
-- Parameters: n INT
-- Returns: INT
-- Logic: Use WHILE loop
--   Fibonacci: 0, 1, 1, 2, 3, 5, 8, 13...
--   fib(1) = 0, fib(2) = 1, fib(3) = 1, fib(4) = 2, fib(5) = 3

-- Test it
SELECT get_fibonacci(5);   -- Should return 3
SELECT get_fibonacci(6);   -- Should return 5
SELECT get_fibonacci(8);   -- Should return 13
```

---

## **LEVEL 5: SELECT INTO (Query Inside Function)**

### **Question 5.1: Get Customer Total Spent**

**Setup:**
```sql
CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    total_spent DECIMAL(10,2)
);

INSERT INTO customers VALUES
(1, 'Alice', 50000),
(2, 'Bob', 25000),
(3, 'Carol', 75000);
```

Create a UDF that retrieves total_spent for a customer.

```sql
-- Create function
-- Name: get_customer_total_spent
-- Parameters: customer_id INT
-- Returns: DECIMAL(10,2)
-- Logic: Use SELECT INTO to fetch total_spent from customers table

-- Test it
SELECT get_customer_total_spent(1);  -- Should return 50000
SELECT get_customer_total_spent(3);  -- Should return 75000
```

---

### **Question 5.2: Count Customer Orders**

**Setup:**
```sql
CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    order_amount DECIMAL(10,2)
);

INSERT INTO orders VALUES
(1, 1, 1000),
(2, 1, 2000),
(3, 1, 1500),
(4, 2, 5000),
(5, 3, 3000);
```

Create a UDF that counts how many orders a customer has.

```sql
-- Create function
-- Name: get_customer_order_count
-- Parameters: customer_id INT
-- Returns: INT
-- Logic: SELECT COUNT(*) INTO variable

-- Test it
SELECT get_customer_order_count(1);  -- Should return 3
SELECT get_customer_order_count(2);  -- Should return 1
SELECT get_customer_order_count(3);  -- Should return 1
```

---

### **Question 5.3: Calculate Total Sales**

Create a UDF that calculates total sales amount for a customer.

```sql
-- Create function
-- Name: get_customer_total_sales
-- Parameters: customer_id INT
-- Returns: DECIMAL(10,2)
-- Logic: SELECT SUM(order_amount) INTO variable FROM orders

-- Test it
SELECT get_customer_total_sales(1);  -- Should return 4500 (1000+2000+1500)
SELECT get_customer_total_sales(2);  -- Should return 5000
SELECT get_customer_total_sales(3);  -- Should return 3000
```

---

### **Question 5.4: Check if VIP Customer**

Create a UDF that checks if customer is VIP (total_spent > 50000).

```sql
-- Create function
-- Name: is_vip_customer
-- Parameters: customer_id INT
-- Returns: VARCHAR(20)
-- Logic: 
--   SELECT total_spent INTO variable
--   IF total_spent > 50000 → "VIP"
--   ELSE → "Regular"

-- Test it
SELECT is_vip_customer(1);  -- Should return "VIP"
SELECT is_vip_customer(2);  -- Should return "Regular"
SELECT is_vip_customer(3);  -- Should return "VIP"
```

---

## **LEVEL 6: INTERMEDIATE (Complex Logic)**

### **Question 6.1: Calculate Employee Bonus**

**Setup:**
```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    years_employed INT
);

INSERT INTO employees VALUES
(1, 'Alice', 50000, 8),
(2, 'Bob', 70000, 3),
(3, 'Carol', 100000, 12);
```

Create a UDF that calculates bonus based on years employed.

```sql
-- Create function
-- Name: calculate_employee_bonus
-- Parameters: emp_id INT
-- Returns: DECIMAL(10,2)
-- Logic:
--   GET salary and years_employed from employees table
--   IF years_employed >= 10 → 20% bonus
--   ELSE IF years_employed >= 5 → 15% bonus
--   ELSE IF years_employed >= 2 → 10% bonus
--   ELSE → 5% bonus
--   RETURN salary * bonus_percentage

-- Test it
SELECT calculate_employee_bonus(1);  -- Alice: 50000 * 0.20 = 10000
SELECT calculate_employee_bonus(3);  -- Carol: 100000 * 0.20 = 20000
```

---

### **Question 6.2: Grade with Comments**

Create a UDF that returns grade AND comment.

```sql
-- Create function
-- Name: get_grade_with_comment
-- Parameters: percentage DECIMAL(5,2)
-- Returns: VARCHAR(100)
-- Logic:
--   >= 90 → "A - Excellent"
--   >= 80 → "B - Good"
--   >= 70 → "C - Average"
--   >= 60 → "D - Poor"
--   < 60  → "F - Failed"

-- Test it
SELECT get_grade_with_comment(95);  -- Should return "A - Excellent"
SELECT get_grade_with_comment(45);  -- Should return "F - Failed"
```

---

### **Question 6.3: Discount Amount Calculator**

Create a UDF that calculates actual discount amount (not just percentage).

```sql
-- Create function
-- Name: calculate_discount_amount
-- Parameters: amount DECIMAL(10,2)
-- Returns: DECIMAL(10,2)
-- Logic:
--   IF amount >= 10000 → discount = amount * 0.20
--   ELSE IF amount >= 5000 → discount = amount * 0.15
--   ELSE IF amount >= 1000 → discount = amount * 0.10
--   ELSE → discount = 0
--   RETURN discount

-- Test it
SELECT calculate_discount_amount(15000);  -- Should return 3000
SELECT calculate_discount_amount(7000);   -- Should return 1050
SELECT calculate_discount_amount(500);    -- Should return 0
```

---

## **LEVEL 7: ADVANCED (Everything Combined)**

### **Question 7.1: Complete Order Processing**

**Setup:**
```sql
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    price DECIMAL(10,2)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    product_id INT,
    quantity INT,
    order_date DATE
);

INSERT INTO products VALUES
(1, 'Laptop', 1000),
(2, 'Mouse', 50),
(3, 'Keyboard', 100);
```

Create a UDF that:
1. Gets product price
2. Multiplies by quantity
3. Applies discount based on amount
4. Returns final price

```sql
-- Create function
-- Name: calculate_final_order_price
-- Parameters: product_id INT, quantity INT
-- Returns: DECIMAL(10,2)
-- Logic:
--   SELECT price INTO var FROM products
--   total = price * quantity
--   IF total >= 5000 → total = total * 0.85 (15% off)
--   ELSE IF total >= 2000 → total = total * 0.90 (10% off)
--   ELSE IF total >= 500 → total = total * 0.95 (5% off)
--   RETURN total

-- Test it
SELECT calculate_final_order_price(1, 10);  -- 1000*10 = 10000, -15% = 8500
SELECT calculate_final_order_price(3, 2);   -- 100*2 = 200, no discount
```

---

### **Question 7.2: Student Grade Report**

Create a UDF that generates a complete report string.

```sql
-- Create function
-- Name: generate_student_report
-- Parameters: percentage DECIMAL(5,2)
-- Returns: VARCHAR(200)
-- Logic:
--   grade = assign_grade(percentage)
--   IF grade = 'A' → status = "Excellent - Passed with Honor"
--   ELSE IF grade = 'B' → status = "Good - Passed"
--   ELSE IF grade = 'C' → status = "Average - Passed"
--   ELSE IF grade = 'D' → status = "Poor - Just Passed"
--   ELSE → status = "Failed - Retake Required"
--   RETURN CONCAT(grade, " - ", percentage, "% - ", status)

-- Test it
SELECT generate_student_report(95);   -- "A - 95% - Excellent - Passed with Honor"
SELECT generate_student_report(45);   -- "F - 45% - Failed - Retake Required"
```

---

### **Question 7.3: Salary Review Function**

**Setup:**
```sql
CREATE TABLE emp_salary (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    performance_rating INT  -- 1-5
);

INSERT INTO emp_salary VALUES
(1, 'Alice', 50000, 5),
(2, 'Bob', 60000, 3),
(3, 'Carol', 75000, 4);
```

Create a UDF that calculates new salary after raise.

```sql
-- Create function
-- Name: calculate_new_salary
-- Parameters: emp_id INT
-- Returns: DECIMAL(10,2)
-- Logic:
--   SELECT salary, performance_rating INTO vars
--   IF performance_rating = 5 → raise = 15%
--   ELSE IF performance_rating = 4 → raise = 12%
--   ELSE IF performance_rating = 3 → raise = 8%
--   ELSE IF performance_rating = 2 → raise = 5%
--   ELSE → raise = 0
--   new_salary = salary + (salary * raise_percentage)
--   RETURN new_salary

-- Test it
SELECT calculate_new_salary(1);  -- Alice: 50000 + (50000*0.15) = 57500
SELECT calculate_new_salary(2);  -- Bob: 60000 + (60000*0.08) = 64800
```

---

### **Question 7.4: Birthday Discount**

Create a UDF that gives extra discount for customers on birthday.

```sql
-- Create function
-- Name: calculate_birthday_discount
-- Parameters: customer_id INT, purchase_amount DECIMAL(10,2)
-- Returns: DECIMAL(10,2)
-- Logic:
--   Check if today is customer's birthday (use MONTH and DAY)
--   IF today is birthday → extra 10% discount
--   ELSE → normal discount
--   Apply standard discount rules + birthday bonus if applicable

-- Test it (will vary based on current date)
SELECT calculate_birthday_discount(1, 5000);
```

---

## **LEVEL 8: EXTREME ADVANCED**

### **Question 8.1: Compound Interest Calculator**

Create a UDF that calculates compound interest over years.

```sql
-- Create function
-- Name: calculate_compound_interest
-- Parameters: principal DECIMAL(10,2), annual_rate DECIMAL(5,2), years INT
-- Returns: DECIMAL(10,2)
-- Logic:
--   Use WHILE loop
--   amount = principal
--   FOR each year:
--     amount = amount * (1 + rate/100)
--   RETURN amount

-- Test it
SELECT calculate_compound_interest(1000, 5, 10);  -- $1000 at 5% for 10 years
```

---

### **Question 8.2: Loan EMI Calculator**

Create a UDF that calculates monthly EMI (Equated Monthly Installment).

```sql
-- Create function
-- Name: calculate_emi
-- Parameters: principal DECIMAL(10,2), annual_rate DECIMAL(5,2), months INT
-- Returns: DECIMAL(10,2)
-- Formula: EMI = P * r * (1+r)^n / ((1+r)^n - 1)
--   where P = principal, r = monthly rate, n = number of months

-- Test it
SELECT calculate_emi(100000, 8, 60);  -- $100k at 8% for 60 months
```

---

### **Question 8.3: Perfect Number Checker**

Create a UDF that checks if a number is perfect (sum of divisors = number).

```sql
-- Create function
-- Name: is_perfect_number
-- Parameters: num INT
-- Returns: BOOLEAN (or VARCHAR(20))
-- Logic: 
--   Find all divisors of num (using loop)
--   Sum them up
--   IF sum = num → "Perfect"
--   ELSE → "Not Perfect"
--   Example: 6 is perfect (1+2+3 = 6)

-- Test it
SELECT is_perfect_number(6);    -- Should return "Perfect"
SELECT is_perfect_number(28);   -- Should return "Perfect"
SELECT is_perfect_number(10);   -- Should return "Not Perfect"
```

---

## **ANSWERS**
