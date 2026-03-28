
## **PART 1: WHAT IS A UDF?**

A **User-Defined Function (UDF)** is a custom function you create that works like built-in functions (`UPPER()`, `LENGTH()`, `SUM()`, etc.).

**Simple analogy:**
- Built-in: `SELECT UPPER('hello');` → Returns 'HELLO'
- UDF: `SELECT my_custom_function(value);` → Returns whatever you programmed

---

## **PART 2: TYPE OF STATEMENT**

**UDFs are DDL (Data Definition Language) statements.**

- ✓ `CREATE FUNCTION` = DDL (creates database objects)
- ✓ Once created, used in DQL queries (`SELECT`)
- ✓ Can accept parameters (optional)
- ✓ Must return a value
- ✗ NOT a view
- ✗ NOT a constraint
- ✗ NOT a trigger

**Usage pattern:** `CREATE FUNCTION (DDL) → Stored in database → USE in SELECT (DQL)`

---

## **PART 3: THE DELIMITER CONCEPT (CRITICAL)**

### **Why DELIMITER Exists:**

In MySQL, the default statement terminator is `;` (semicolon).

**Problem:** UDFs have multiple statements inside, each ending with `;`

```
CREATE FUNCTION add_numbers(a INT, b INT)
RETURNS INT
BEGIN
    DECLARE result INT;  ← ends with ;
    SET result = a + b;  ← ends with ;
    RETURN result;       ← ends with ;
END;                     ← ends with ;
```

When MySQL reads the first `;`, it thinks the entire statement is done and throws an error.

### **Solution: Change Delimiter Temporarily**

Change from `;` to something else (usually `$$` or `//`) for the function body, then change back.

```sql
DELIMITER $$           -- Change delimiter to $$

CREATE FUNCTION add_numbers(a INT, b INT)
RETURNS INT
BEGIN
    DECLARE result INT;
    SET result = a + b;
    RETURN result;
END$$                  -- Use $$ to mark end (not ;)

DELIMITER ;            -- Change delimiter back to ;
```

**How it works:**
1. `DELIMITER $$` = "From now on, use `$$` as statement terminator"
2. All `;` inside are treated as normal characters (not terminators)
3. `END$$` = "This is the real end of the statement"
4. `DELIMITER ;` = "Switch back to `;` as normal terminator"

---

## **PART 4: BASIC SYNTAX WITH DELIMITER**

```sql
DELIMITER $$

CREATE FUNCTION function_name (param1 datatype, param2 datatype, ...)
RETURNS return_datatype
BEGIN
    -- Function body goes here
    RETURN value;
END$$

DELIMITER ;
```

**Components breakdown:**
- `DELIMITER $$` = Change statement terminator (REQUIRED)
- `CREATE FUNCTION` = DDL keyword
- `function_name` = Name you give the function
- `(param1 datatype, ...)` = Input parameters (optional)
- `RETURNS datatype` = Return type (REQUIRED)
- `BEGIN ... END` = Function body (REQUIRED)
- `RETURN value` = Send back result (REQUIRED)
- `DELIMITER ;` = Switch terminator back (REQUIRED)

---

## **PART 5: VARIABLES - DECLARE, SET, DEFAULT**

### **What are Variables?**

Variables are containers that store values temporarily while the function runs. They exist only during function execution.

### **Declaring Variables:**

```sql
DECLARE variable_name datatype [DEFAULT default_value];
```

**Syntax breakdown:**
- `DECLARE` = Create a variable
- `variable_name` = Name of variable (use lowercase with underscores)
- `datatype` = INT, DECIMAL, VARCHAR, DATE, BOOLEAN, etc.
- `DEFAULT` = Optional initial value

### **Examples of Variable Declaration:**

```sql
DECLARE result INT;                           -- No default
DECLARE salary DECIMAL(10,2);                 -- No default
DECLARE employee_name VARCHAR(100);           -- No default
DECLARE discount DECIMAL(5,2) DEFAULT 0;      -- Default is 0
DECLARE is_active BOOLEAN DEFAULT TRUE;       -- Default is TRUE
DECLARE hire_date DATE DEFAULT CURDATE();     -- Default is today's date
DECLARE counter INT DEFAULT 0;                -- Default is 0
```

### **Variables MUST be declared at the beginning of BEGIN block:**

```sql
DELIMITER $$

CREATE FUNCTION example_func(param INT)
RETURNS INT
BEGIN
    -- ALL DECLARE statements must come FIRST
    DECLARE var1 INT;
    DECLARE var2 VARCHAR(100);
    DECLARE var3 DECIMAL(10,2) DEFAULT 0;
    
    -- THEN comes your logic
    SET var1 = param;
    
    RETURN var1;
END$$

DELIMITER ;
```

**❌ WRONG - Logic before DECLARE:**
```sql
SET var1 = 5;
DECLARE var1 INT;  -- ❌ ERROR: DECLARE must come first
```

---

## **PART 6: OPERATORS IN UDFs**

### **Arithmetic Operators:**

```sql
DELIMITER $$

CREATE FUNCTION arithmetic_example(a INT, b INT)
RETURNS INT
BEGIN
    DECLARE result INT;
    
    -- Addition
    SET result = a + b;        -- Returns sum
    
    -- Subtraction
    SET result = a - b;        -- Returns difference
    
    -- Multiplication
    SET result = a * b;        -- Returns product
    
    -- Division (integer division)
    SET result = a / b;        -- Returns quotient (loses decimals)
    
    -- Modulo (remainder)
    SET result = a % b;        -- Returns remainder
    
    RETURN result;
END$$

DELIMITER ;
```

**Real example:**

```sql
DELIMITER $$

CREATE FUNCTION calculate_total_price(unit_price DECIMAL(10,2), quantity INT, tax_percent DECIMAL(5,2))
RETURNS DECIMAL(10,2)
BEGIN
    DECLARE subtotal DECIMAL(10,2);
    DECLARE tax_amount DECIMAL(10,2);
    DECLARE total DECIMAL(10,2);
    
    SET subtotal = unit_price * quantity;           -- Multiplication
    SET tax_amount = subtotal * (tax_percent / 100); -- Division & Multiplication
    SET total = subtotal + tax_amount;               -- Addition
    
    RETURN total;
END$$

DELIMITER ;

-- Use it
SELECT calculate_total_price(100, 5, 10);  -- 100*5 = 500, tax = 50, total = 550
```

---

### **Comparison Operators:**

Used in IF/CASE conditions, return TRUE or FALSE.

```sql
= 	Equal to              a = b
!=	Not equal to          a != b
<>	Not equal to (SQL)    a <> b
<	Less than             a < b
>	Greater than          a > b
<=	Less than or equal    a <= b
>=	Greater than or equal a >= b
```

**Example:**

```sql
DELIMITER $$

CREATE FUNCTION check_pass_fail(score INT)
RETURNS VARCHAR(20)
BEGIN
    DECLARE result VARCHAR(20);
    
    IF score >= 60 THEN           -- Greater than or equal
        SET result = 'Pass';
    ELSEIF score < 60 THEN        -- Less than
        SET result = 'Fail';
    END IF;
    
    RETURN result;
END$$

DELIMITER ;
```

---

### **Logical Operators:**

Combine multiple conditions.

```sql
AND    Both conditions must be TRUE
OR     At least one condition must be TRUE
NOT    Reverses the condition
```

**Example:**

```sql
DELIMITER $$

CREATE FUNCTION check_eligibility(age INT, income DECIMAL(10,2))
RETURNS VARCHAR(30)
BEGIN
    DECLARE result VARCHAR(30);
    
    IF age >= 21 AND income > 30000 THEN           -- AND operator
        SET result = 'Eligible';
    ELSEIF age >= 18 OR income > 50000 THEN        -- OR operator
        SET result = 'Maybe Eligible';
    ELSEIF NOT (age < 18) THEN                     -- NOT operator
        SET result = 'Check Details';
    ELSE
        SET result = 'Not Eligible';
    END IF;
    
    RETURN result;
END$$

DELIMITER ;

-- Use it
SELECT check_eligibility(25, 40000);   -- Returns 'Eligible' (25>=21 AND 40000>30000)
SELECT check_eligibility(20, 60000);   -- Returns 'Maybe Eligible' (20>=18 OR 60000>50000)
```

---

### **String Operators:**

```sql
CONCAT()       Join strings
LIKE           Pattern matching
LENGTH()       String length
SUBSTRING()    Extract part of string
UPPER()        Convert to uppercase
LOWER()        Convert to lowercase
```

**Example:**

```sql
DELIMITER $$

CREATE FUNCTION generate_email(first_name VARCHAR(50), last_name VARCHAR(50))
RETURNS VARCHAR(100)
BEGIN
    DECLARE email VARCHAR(100);
    
    -- CONCAT joins strings together
    SET email = CONCAT(
        LOWER(first_name),      -- Convert to lowercase
        '.',
        LOWER(last_name),
        '@company.com'
    );
    
    RETURN email;
END$$

DELIMITER ;

-- Use it
SELECT generate_email('John', 'Doe');  -- Returns 'john.doe@company.com'
```

---

## **PART 7: ASSIGNMENTS - SET STATEMENT**

### **What is SET?**

`SET` assigns a value to a variable.

**Syntax:**
```sql
SET variable_name = value;
SET variable_name = expression;
SET variable_name = (SELECT column FROM table);
```

### **Basic Assignment:**

```sql
DELIMITER $$

CREATE FUNCTION simple_assignment(num INT)
RETURNS INT
BEGIN
    DECLARE result INT;
    
    SET result = num * 2;      -- Assign num*2 to result
    
    RETURN result;
END$$

DELIMITER ;

-- Use it
SELECT simple_assignment(5);   -- Returns 10
```

---

### **Assignment with Expressions:**

```sql
DELIMITER $$

CREATE FUNCTION complex_assignment(salary DECIMAL(10,2))
RETURNS DECIMAL(10,2)
BEGIN
    DECLARE bonus DECIMAL(10,2);
    DECLARE tax DECIMAL(10,2);
    DECLARE net_salary DECIMAL(10,2);
    
    SET bonus = salary * 0.15;              -- 15% bonus
    SET tax = salary * 0.20;                -- 20% tax
    SET net_salary = salary + bonus - tax;  -- net = salary + bonus - tax
    
    RETURN net_salary;
END$$

DELIMITER ;

-- Use it
SELECT complex_assignment(50000);  -- Returns 50000 + 7500 - 10000 = 47500
```

---

### **Assignment from Query (SELECT INTO):**

```sql
SET variable_name = (SELECT column FROM table WHERE condition);
-- OR
SELECT column INTO variable_name FROM table WHERE condition;
```

**Example:**

```sql
DELIMITER $$

CREATE FUNCTION get_employee_salary(emp_id INT)
RETURNS DECIMAL(10,2)
BEGIN
    DECLARE salary DECIMAL(10,2);
    
    -- SELECT INTO syntax
    SELECT emp_salary INTO salary
    FROM employees
    WHERE employees.emp_id = emp_id;
    
    RETURN salary;
END$$

DELIMITER ;

-- Use it
SELECT get_employee_salary(1);  -- Returns employee's salary from table
```

---

## **PART 8: CONTROL FLOW - IF/ELSE/ELSEIF**

### **Basic IF Statement:**

```sql
IF condition THEN
    -- Statements to execute if condition is TRUE
END IF;
```

**Example:**

```sql
DELIMITER $$

CREATE FUNCTION check_age(age INT)
RETURNS VARCHAR(20)
BEGIN
    DECLARE result VARCHAR(20);
    
    IF age >= 18 THEN
        SET result = 'Adult';
    END IF;
    
    RETURN result;
END$$

DELIMITER ;
```

---

### **IF/ELSE Statement:**

```sql
IF condition THEN
    -- Statements if TRUE
ELSE
    -- Statements if FALSE
END IF;
```

**Example:**

```sql
DELIMITER $$

CREATE FUNCTION check_pass_fail(score INT)
RETURNS VARCHAR(20)
BEGIN
    DECLARE result VARCHAR(20);
    
    IF score >= 60 THEN
        SET result = 'Pass';
    ELSE
        SET result = 'Fail';
    END IF;
    
    RETURN result;
END$$

DELIMITER ;

-- Use it
SELECT check_pass_fail(75);   -- Returns 'Pass'
SELECT check_pass_fail(50);   -- Returns 'Fail'
```

---

### **IF/ELSEIF/ELSE Statement:**

```sql
IF condition1 THEN
    -- Statements if condition1 is TRUE
ELSEIF condition2 THEN
    -- Statements if condition2 is TRUE
ELSEIF condition3 THEN
    -- Statements if condition3 is TRUE
ELSE
    -- Statements if all are FALSE
END IF;
```

**Example: Grade Assignment**

```sql
DELIMITER $$

CREATE FUNCTION assign_grade(percentage DECIMAL(5,2))
RETURNS CHAR(1)
BEGIN
    DECLARE grade CHAR(1);
    
    IF percentage >= 90 THEN
        SET grade = 'A';
    ELSEIF percentage >= 80 THEN
        SET grade = 'B';
    ELSEIF percentage >= 70 THEN
        SET grade = 'C';
    ELSEIF percentage >= 60 THEN
        SET grade = 'D';
    ELSE
        SET grade = 'F';
    END IF;
    
    RETURN grade;
END$$

DELIMITER ;

-- Use it
SELECT assign_grade(95);   -- Returns 'A'
SELECT assign_grade(85);   -- Returns 'B'
SELECT assign_grade(75);   -- Returns 'C'
SELECT assign_grade(65);   -- Returns 'D'
SELECT assign_grade(50);   -- Returns 'F'
```

---

### **Real Example: Discount Calculation**

```sql
DELIMITER $$

CREATE FUNCTION get_discount(amount DECIMAL(10,2))
RETURNS DECIMAL(5,2)
BEGIN
    DECLARE discount DECIMAL(5,2);
    
    IF amount >= 10000 THEN
        SET discount = 0.20;      -- 20%
    ELSEIF amount >= 5000 THEN
        SET discount = 0.15;      -- 15%
    ELSEIF amount >= 1000 THEN
        SET discount = 0.10;      -- 10%
    ELSEIF amount >= 500 THEN
        SET discount = 0.05;      -- 5%
    ELSE
        SET discount = 0;         -- No discount
    END IF;
    
    RETURN discount;
END$$

DELIMITER ;

-- Use it
SELECT get_discount(15000);  -- Returns 0.20
SELECT get_discount(7000);   -- Returns 0.15
SELECT get_discount(300);    -- Returns 0
```

---

## **PART 9: CONTROL FLOW - CASE STATEMENT**

### **Simple CASE (Pattern Matching):**

```sql
CASE value
    WHEN value1 THEN statement1;
    WHEN value2 THEN statement2;
    ...
    ELSE default_statement;
END CASE;
```

**Example: Day of Week**

```sql
DELIMITER $$

CREATE FUNCTION get_day_name(day_num INT)
RETURNS VARCHAR(20)
BEGIN
    DECLARE day_name VARCHAR(20);
    
    CASE day_num
        WHEN 1 THEN SET day_name = 'Monday';
        WHEN 2 THEN SET day_name = 'Tuesday';
        WHEN 3 THEN SET day_name = 'Wednesday';
        WHEN 4 THEN SET day_name = 'Thursday';
        WHEN 5 THEN SET day_name = 'Friday';
        WHEN 6 THEN SET day_name = 'Saturday';
        WHEN 7 THEN SET day_name = 'Sunday';
        ELSE SET day_name = 'Invalid Day';
    END CASE;
    
    RETURN day_name;
END$$

DELIMITER ;

-- Use it
SELECT get_day_name(1);   -- Returns 'Monday'
SELECT get_day_name(5);   -- Returns 'Friday'
SELECT get_day_name(10);  -- Returns 'Invalid Day'
```

---

### **Searched CASE (Conditions):**

```sql
CASE
    WHEN condition1 THEN statement1;
    WHEN condition2 THEN statement2;
    ...
    ELSE default_statement;
END CASE;
```

**Example: Performance Rating**

```sql
DELIMITER $$

CREATE FUNCTION assign_rating(score INT)
RETURNS VARCHAR(20)
BEGIN
    DECLARE rating VARCHAR(20);
    
    CASE
        WHEN score >= 90 THEN SET rating = 'Excellent';
        WHEN score >= 80 THEN SET rating = 'Good';
        WHEN score >= 70 THEN SET rating = 'Average';
        WHEN score >= 60 THEN SET rating = 'Pass';
        ELSE SET rating = 'Fail';
    END CASE;
    
    RETURN rating;
END$$

DELIMITER ;

-- Use it
SELECT assign_rating(95);   -- Returns 'Excellent'
SELECT assign_rating(75);   -- Returns 'Average'
SELECT assign_rating(50);   -- Returns 'Fail'
```

---

## **PART 10: LOOPS - WHILE**

### **WHILE Loop Syntax:**

```sql
WHILE condition DO
    -- Statements to repeat
END WHILE;
```

### **How it works:**

1. Check condition
2. If TRUE, execute statements
3. Go back to step 1
4. If FALSE, exit loop

### **Example 1: Sum Numbers**

```sql
DELIMITER $$

CREATE FUNCTION sum_numbers(n INT)
RETURNS INT
BEGIN
    DECLARE total INT;
    DECLARE counter INT;
    
    SET total = 0;
    SET counter = 1;
    
    WHILE counter <= n DO
        SET total = total + counter;
        SET counter = counter + 1;
    END WHILE;
    
    RETURN total;
END$$

DELIMITER ;

-- Use it
SELECT sum_numbers(5);   -- Returns 15 (1+2+3+4+5)
SELECT sum_numbers(10);  -- Returns 55 (1+2+...+10)
```

**Step-by-step execution (n=5):**
```
Iteration 1: counter=1, total=0, total = 0+1 = 1, counter = 2
Iteration 2: counter=2, total=1, total = 1+2 = 3, counter = 3
Iteration 3: counter=3, total=3, total = 3+3 = 6, counter = 4
Iteration 4: counter=4, total=6, total = 6+4 = 10, counter = 5
Iteration 5: counter=5, total=10, total = 10+5 = 15, counter = 6
Loop exit: counter=6, 6 <= 5 is FALSE
Return: 15
```

---

### **Example 2: Factorial**

```sql
DELIMITER $$

CREATE FUNCTION factorial(n INT)
RETURNS INT
BEGIN
    DECLARE result INT;
    DECLARE counter INT;
    
    SET result = 1;
    SET counter = 1;
    
    WHILE counter <= n DO
        SET result = result * counter;
        SET counter = counter + 1;
    END WHILE;
    
    RETURN result;
END$$

DELIMITER ;

-- Use it
SELECT factorial(5);   -- Returns 120 (5! = 5*4*3*2*1)
SELECT factorial(3);   -- Returns 6 (3! = 3*2*1)
```

---

### **Example 3: Generate String**

```sql
DELIMITER $$

CREATE FUNCTION repeat_string(str VARCHAR(50), times INT)
RETURNS VARCHAR(500)
BEGIN
    DECLARE result VARCHAR(500);
    DECLARE counter INT;
    
    SET result = '';
    SET counter = 0;
    
    WHILE counter < times DO
        SET result = CONCAT(result, str);
        SET counter = counter + 1;
    END WHILE;
    
    RETURN result;
END$$

DELIMITER ;

-- Use it
SELECT repeat_string('Ha', 5);  -- Returns 'HaHaHaHaHa'
SELECT repeat_string('*', 10);  -- Returns '**********'
```

---

## **PART 11: LOOPS - REPEAT**

### **REPEAT Loop Syntax:**

```sql
REPEAT
    -- Statements to repeat
UNTIL condition
END REPEAT;
```

### **How it works:**

1. Execute statements
2. Check condition
3. If FALSE, go back to step 1
4. If TRUE, exit loop

**Difference from WHILE:**
- **WHILE:** Check condition FIRST (may not execute at all)
- **REPEAT:** Execute FIRST, check condition AFTER (always executes at least once)

### **Example:**

```sql
DELIMITER $$

CREATE FUNCTION multiply_until(start INT, multiplier INT, limit INT)
RETURNS INT
BEGIN
    DECLARE result INT;
    
    SET result = start;
    
    REPEAT
        SET result = result * multiplier;
    UNTIL result > limit
    END REPEAT;
    
    RETURN result;
END$$

DELIMITER ;

-- Use it
SELECT multiply_until(2, 2, 100);  -- Returns 128 (2*2*2*2*2*2*2 = 128)
```

**Step-by-step execution (start=2, multiplier=2, limit=100):**
```
Iteration 1: result = 2, result = 2*2 = 4, 4 > 100? NO
Iteration 2: result = 4, result = 4*2 = 8, 8 > 100? NO
Iteration 3: result = 8, result = 8*2 = 16, 16 > 100? NO
Iteration 4: result = 16, result = 16*2 = 32, 32 > 100? NO
Iteration 5: result = 32, result = 32*2 = 64, 64 > 100? NO
Iteration 6: result = 64, result = 64*2 = 128, 128 > 100? YES
Loop exit, return 128
```

---

## **PART 12: LOOPS - LOOP with LEAVE**

### **LOOP Syntax:**

```sql
LOOP_LABEL: LOOP
    -- Statements
    IF condition THEN
        LEAVE LOOP_LABEL;  -- Exit loop
    END IF;
END LOOP;
```

### **How it works:**

1. Execute statements
2. Check IF condition
3. If TRUE, LEAVE (exit) loop
4. If FALSE, go back to step 1

### **Example: Count Down**

```sql
DELIMITER $$

CREATE FUNCTION count_down(start INT)
RETURNS VARCHAR(100)
BEGIN
    DECLARE result VARCHAR(100);
    DECLARE counter INT;
    
    SET result = '';
    SET counter = start;
    
    LOOP_LABEL: LOOP
        IF counter < 0 THEN
            LEAVE LOOP_LABEL;  -- Exit loop
        END IF;
        
        SET result = CONCAT(result, counter, ' ');
        SET counter = counter - 1;
    END LOOP;
    
    RETURN result;
END$$

DELIMITER ;

-- Use it
SELECT count_down(5);  -- Returns '5 4 3 2 1 0 '
```

---

## **PART 13: USING FUNCTIONS WITH QUERIES - SELECT INTO**

### **SELECT INTO Syntax:**

```sql
SELECT column1, column2, ... INTO variable1, variable2, ...
FROM table
WHERE condition;
```

### **Single Value from Query:**

```sql
DELIMITER $$

CREATE FUNCTION get_employee_salary(emp_id INT)
RETURNS DECIMAL(10,2)
BEGIN
    DECLARE salary DECIMAL(10,2);
    
    SELECT emp_salary INTO salary
    FROM employees
    WHERE employees.emp_id = emp_id;
    
    RETURN salary;
END$$

DELIMITER ;

-- Use it
SELECT get_employee_salary(1);  -- Returns specific employee's salary
```

---

### **Aggregate from Query:**

```sql
DELIMITER $$

CREATE FUNCTION total_sales(salesman_id INT)
RETURNS DECIMAL(10,2)
BEGIN
    DECLARE total DECIMAL(10,2);
    
    SELECT SUM(amount) INTO total
    FROM sales
    WHERE sales.salesman_id = salesman_id;
    
    RETURN total;
END$$

DELIMITER ;

-- Setup
CREATE TABLE sales (
    sale_id INT,
    salesman_id INT,
    amount DECIMAL(10,2)
);

INSERT INTO sales VALUES
(1, 101, 5000),
(2, 101, 3000),
(3, 102, 7000),
(4, 102, 2000);

-- Use it
SELECT total_sales(101);  -- Returns 8000 (5000 + 3000)
SELECT total_sales(102);  -- Returns 9000 (7000 + 2000)
```

---

### **Count from Query:**

```sql
DELIMITER $$

CREATE FUNCTION count_customer_orders(customer_id INT)
RETURNS INT
BEGIN
    DECLARE order_count INT;
    
    SELECT COUNT(*) INTO order_count
    FROM orders
    WHERE orders.customer_id = customer_id;
    
    RETURN order_count;
END$$

DELIMITER ;
```

---

### **Multiple Values from Query:**

```sql
DELIMITER $$

CREATE FUNCTION get_employee_details(emp_id INT)
RETURNS VARCHAR(200)
BEGIN
    DECLARE emp_name VARCHAR(100);
    DECLARE emp_salary DECIMAL(10,2);
    DECLARE result VARCHAR(200);
    
    SELECT name, salary INTO emp_name, emp_salary
    FROM employees
    WHERE employees.emp_id = emp_id;
    
    SET result = CONCAT('Name: ', emp_name, ', Salary: ', emp_salary);
    
    RETURN result;
END$$

DELIMITER ;
```

---

## **PART 14: ADVANCED EXAMPLE - EVERYTHING COMBINED**

```sql
DELIMITER $$

CREATE FUNCTION calculate_employee_bonus(emp_id INT)
RETURNS DECIMAL(10,2)
BEGIN
    -- Declare all variables
    DECLARE salary DECIMAL(10,2);
    DECLARE years_employed INT;
    DECLARE hire_year INT;
    DECLARE bonus_percentage DECIMAL(5,2);
    DECLARE bonus_amount DECIMAL(10,2);
    DECLARE employee_exists INT;
    
    -- Check if employee exists
    SELECT COUNT(*) INTO employee_exists
    FROM employees
    WHERE employees.emp_id = emp_id;
    
    -- IF/ELSE: Handle if employee doesn't exist
    IF employee_exists = 0 THEN
        RETURN 0;
    END IF;
    
    -- SELECT INTO: Get salary and hire year
    SELECT emp_salary, YEAR(hire_date) INTO salary, hire_year
    FROM employees
    WHERE employees.emp_id = emp_id;
    
    -- Calculate years employed
    SET years_employed = YEAR(CURDATE()) - hire_year;
    
    -- IF/ELSEIF: Determine bonus percentage based on years
    IF years_employed >= 10 THEN
        SET bonus_percentage = 0.20;      -- 20%
    ELSEIF years_employed >= 5 THEN
        SET bonus_percentage = 0.15;      -- 15%
    ELSEIF years_employed >= 2 THEN
        SET bonus_percentage = 0.10;      -- 10%
    ELSE
        SET bonus_percentage = 0.05;      -- 5%
    END IF;
    
    -- Calculate bonus amount
    SET bonus_amount = salary * bonus_percentage;
    
    RETURN bonus_amount;
END$$

DELIMITER ;

-- Use it
SELECT calculate_employee_bonus(1);  -- Returns calculated bonus
```

---

## **PART 15: PRACTICAL EXAMPLES**

### **Example 1: VIP Customer Check**

```sql
CREATE TABLE customers (
    customer_id INT,
    name VARCHAR(100),
    total_spent DECIMAL(10,2)
);

INSERT INTO customers VALUES
(1, 'Alice', 50000),
(2, 'Bob', 5000),
(3, 'Carol', 100000);

DELIMITER $$

CREATE FUNCTION is_vip_customer(customer_id INT)
RETURNS VARCHAR(20)
BEGIN
    DECLARE spent DECIMAL(10,2);
    DECLARE result VARCHAR(20);
    
    SELECT total_spent INTO spent
    FROM customers
    WHERE customers.customer_id = customer_id;
    
    IF spent > 50000 THEN
        SET result = 'VIP';
    ELSE
        SET result = 'Regular';
    END IF;
    
    RETURN result;
END$$

DELIMITER ;

-- Use it
SELECT is_vip_customer(1);  -- Returns 'VIP'
SELECT is_vip_customer(2);  -- Returns 'Regular'
SELECT is_vip_customer(3);  -- Returns 'VIP'
```

---

### **Example 2: Calculate Final Price with Discount**

```sql
DELIMITER $$

CREATE FUNCTION calculate_final_price(base_price DECIMAL(10,2), quantity INT, tax_rate DECIMAL(5,2))
RETURNS DECIMAL(10,2)
BEGIN
    DECLARE subtotal DECIMAL(10,2);
    DECLARE discount DECIMAL(10,2);
    DECLARE tax_amount DECIMAL(10,2);
    DECLARE final_price DECIMAL(10,2);
    
    -- Calculate subtotal
    SET subtotal = base_price * quantity;
    
    -- Determine discount based on quantity
    IF quantity >= 100 THEN
        SET discount = subtotal * 0.20;    -- 20% discount
    ELSEIF quantity >= 50 THEN
        SET discount = subtotal * 0.15;    -- 15% discount
    ELSEIF quantity >= 10 THEN
        SET discount = subtotal * 0.10;    -- 10% discount
    ELSE
        SET discount = 0;                  -- No discount
    END IF;
    
    -- Calculate tax on discounted amount
    SET tax_amount = (subtotal - discount) * (tax_rate / 100);
    
    -- Calculate final price
    SET final_price = subtotal - discount + tax_amount;
    
    RETURN final_price;
END$$

DELIMITER ;

-- Use it
SELECT calculate_final_price(100, 150, 8);  -- Subtotal: 15000, Discount: 3000, Tax: 960, Final: 12960
```

---

### **Example 3: Employee Grade with Loop**

```sql
DELIMITER $$

CREATE FUNCTION get_average_grade(emp_id INT)
RETURNS VARCHAR(50)
BEGIN
    DECLARE avg_score DECIMAL(5,2);
    DECLARE grade VARCHAR(50);
    
    -- Get average score
    SELECT AVG(score) INTO avg_score
    FROM employee_scores
    WHERE employee_scores.emp_id = emp_id;
    
    -- Assign grade using CASE
    CASE
        WHEN avg_score >= 90 THEN SET grade = 'A - Excellent';
        WHEN avg_score >= 80 THEN SET grade = 'B - Good';
        WHEN avg_score >= 70 THEN SET grade = 'C - Average';
        WHEN avg_score >= 60 THEN SET grade = 'D - Poor';
        ELSE SET grade = 'F - Failed';
    END CASE;
    
    RETURN grade;
END$$

DELIMITER ;
```

---

## **PART 16: USING UDFs IN QUERIES**

### **In SELECT:**

```sql
SELECT 
    name,
    salary,
    calculate_employee_bonus(emp_id) AS bonus
FROM employees;
```

---

### **In WHERE:**

```sql
SELECT 
    name,
    total_spent,
    is_vip_customer(customer_id) AS vip_status
FROM customers
WHERE is_vip_customer(customer_id) = 'VIP';
```

---

### **In ORDER BY:**

```sql
SELECT 
    name,
    salary
FROM employees
ORDER BY calculate_employee_bonus(emp_id) DESC;
```

---

## **PART 17: DROP, SHOW, AND MODIFY FUNCTIONS**

### **View all functions:**

```sql
SHOW FUNCTION STATUS;
```

### **View specific function:**

```sql
SHOW CREATE FUNCTION function_name\G
```

### **Drop a function:**

```sql
DROP FUNCTION function_name;
DROP FUNCTION IF EXISTS function_name;
```

### **Modify a function (Drop and Recreate):**

```sql
DROP FUNCTION add_numbers;

DELIMITER $$

CREATE FUNCTION add_numbers(a INT, b INT)
RETURNS INT
BEGIN
    DECLARE result INT;
    SET result = a + b;
    RETURN result;
END$$

DELIMITER ;
```

---

## **PART 18: QUICK REFERENCE**

| Concept | Syntax | Example |
|---------|--------|---------|
| **DELIMITER** | `DELIMITER $$` ... `END$$` ... `DELIMITER ;` | Required wrapper |
| **Variable** | `DECLARE var datatype [DEFAULT value];` | `DECLARE total INT DEFAULT 0;` |
| **Assign** | `SET var = value;` | `SET total = 100;` |
| **Arithmetic** | `+ - * / %` | `SET result = a + b;` |
| **Comparison** | `= != < > <= >=` | `IF age >= 18 THEN ...` |
| **Logical** | `AND OR NOT` | `IF age >= 21 AND income > 30000 THEN ...` |
| **String** | `CONCAT() UPPER() LOWER()` | `CONCAT(first, ' ', last)` |
| **IF** | `IF condition THEN ... END IF;` | See Part 8 |
| **CASE** | `CASE value WHEN ... THEN ... END CASE;` | See Part 9 |
| **WHILE** | `WHILE condition DO ... END WHILE;` | See Part 10 |
| **REPEAT** | `REPEAT ... UNTIL condition END REPEAT;` | See Part 11 |
| **LOOP** | `LOOP ... LEAVE label; END LOOP;` | See Part 12 |
| **SELECT INTO** | `SELECT col INTO var FROM table;` | See Part 13 |
| **RETURN** | `RETURN value;` | `RETURN total;` |

---

## **SUMMARY**

**UDFs are:**
- ✓ DDL statements (`CREATE FUNCTION`)
- ✓ Custom reusable functions
- ✓ Can accept parameters
- ✓ Must return a value
- ✓ Stored in database
- ✓ Used in SELECT queries

**Learning order (in this guide):**
1. ✓ **DELIMITER** - How to create functions
2. ✓ **VARIABLES** - Declare and DEFAULT
3. ✓ **OPERATORS** - Arithmetic, Comparison, Logical, String
4. ✓ **ASSIGNMENTS** - SET and SELECT INTO
5. ✓ **CONTROL FLOW** - IF/ELSE and CASE
6. ✓ **LOOPS** - WHILE, REPEAT, LOOP
7. ✓ **QUERIES** - SELECT INTO
8. ✓ **PRACTICAL EXAMPLES** - Real use cases
