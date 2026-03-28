
## What is a Trigger?

A **trigger** is a special type of stored procedure in MySQL that automatically executes (or "fires") in response to specific events on a particular table in the database. Think of it as an automated action that happens when something occurs.

**Real-world analogy:** If you set a trigger on your alarm clock to go off at 6 AM, it automatically rings without you having to manually ring it. Similarly, a database trigger automatically runs code when a specific database event happens.

---

## Key Concepts

### 1. **Trigger Event** (What causes the trigger to fire?)
A trigger fires in response to one of these events:
- **INSERT** - When a new row is added to a table
- **UPDATE** - When an existing row is modified
- **DELETE** - When a row is removed from a table

### 2. **Trigger Timing** (When does it fire?)
- **BEFORE** - The trigger fires before the event happens
- **AFTER** - The trigger fires after the event happens

### 3. **Trigger Scope** (Which rows?)
- **FOR EACH ROW** - The trigger fires once for each affected row
- **FOR EACH STATEMENT** - The trigger fires once for the entire statement (Note: MySQL currently only supports FOR EACH ROW)

### 4. **Trigger Body** (What does it do?)
The SQL statements that execute when the trigger fires. This is enclosed in `BEGIN ... END`

---

## Basic Syntax

```sql
CREATE TRIGGER trigger_name
{BEFORE | AFTER} {INSERT | UPDATE | DELETE}
ON table_name
FOR EACH ROW
BEGIN
    -- SQL statements to execute
END;
```

**Let's break this down:**
- `CREATE TRIGGER trigger_name` - Creates a trigger with a specific name
- `{BEFORE | AFTER}` - Pick one: execute before or after the event
- `{INSERT | UPDATE | DELETE}` - Pick one: which database event triggers this
- `ON table_name` - Which table to monitor
- `FOR EACH ROW` - Execute once per affected row
- `BEGIN ... END` - Contains the trigger logic

---

## Practical Examples

### Example 1: Simple AFTER INSERT Trigger

Let's say you have an `orders` table and you want to log every new order to a separate `order_audit` table.

**First, create the tables:**

```sql
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    order_amount DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    action VARCHAR(50),
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Now, create the trigger:**

```sql
CREATE TRIGGER after_order_insert
AFTER INSERT
ON orders
FOR EACH ROW
BEGIN
    INSERT INTO order_audit (order_id, action)
    VALUES (NEW.order_id, 'Order created');
END;
```

**What this means:**
- `after_order_insert` - Name of the trigger
- `AFTER INSERT` - Fire after a new order is inserted
- `ON orders` - Monitor the orders table
- `FOR EACH ROW` - Execute once per new row
- `NEW.order_id` - Refers to the new row's order_id (explained below)

**Test it:**

```sql
INSERT INTO orders (customer_name, order_amount) 
VALUES ('John Doe', 99.99);

-- Check the audit table
SELECT * FROM order_audit;
-- You'll see a record showing that an order was created
```

---

### Example 2: BEFORE INSERT Trigger (Validation)

You want to ensure that order amounts are never negative. You can use a BEFORE INSERT trigger to validate:

```sql
CREATE TRIGGER before_order_insert
BEFORE INSERT
ON orders
FOR EACH ROW
BEGIN
    IF NEW.order_amount <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Order amount must be positive';
    END IF;
END;
```

**What this means:**
- `BEFORE INSERT` - Fire before inserting the row
- `NEW.order_amount` - The amount value being inserted
- `IF` - Conditional check
- `SIGNAL SQLSTATE '45000'` - Throw an error and prevent the insert

**Test it:**

```sql
-- This will FAIL with an error message
INSERT INTO orders (customer_name, order_amount) 
VALUES ('Jane Doe', -50);

-- This will SUCCEED
INSERT INTO orders (customer_name, order_amount) 
VALUES ('Jane Doe', 50);
```

---

### Example 3: BEFORE UPDATE Trigger (Auto-update a field)

You want to automatically update a `last_modified` timestamp whenever an order is modified:

```sql
ALTER TABLE orders ADD COLUMN last_modified TIMESTAMP;

CREATE TRIGGER before_order_update
BEFORE UPDATE
ON orders
FOR EACH ROW
BEGIN
    SET NEW.last_modified = CURRENT_TIMESTAMP;
END;
```

**What this means:**
- `BEFORE UPDATE` - Fire before the update happens
- `SET NEW.last_modified` - Modify the new value before it's saved
- `CURRENT_TIMESTAMP` - MySQL function that returns the current date and time

**Test it:**

```sql
UPDATE orders SET order_amount = 150 WHERE order_id = 1;

-- Check the result
SELECT order_id, order_amount, last_modified FROM orders;
-- You'll see last_modified is automatically updated
```

---

### Example 4: AFTER DELETE Trigger (Archive deleted data)

You want to save deleted orders to an archive table before they're removed:

```sql
CREATE TABLE orders_archive (
    archive_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    customer_name VARCHAR(100),
    order_amount DECIMAL(10, 2),
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER after_order_delete
AFTER DELETE
ON orders
FOR EACH ROW
BEGIN
    INSERT INTO orders_archive (order_id, customer_name, order_amount)
    VALUES (OLD.order_id, OLD.customer_name, OLD.order_amount);
END;
```

**What this means:**
- `AFTER DELETE` - Fire after a row is deleted
- `OLD.order_id` - Refers to the deleted row's values (explained below)

**Test it:**

```sql
DELETE FROM orders WHERE order_id = 1;

-- Check the archive
SELECT * FROM orders_archive;
-- The deleted order is now saved here
```

---

## Important Keywords Explained

### `NEW` Keyword
- Used in **INSERT** and **UPDATE** triggers
- Refers to the NEW values being inserted or updated
- Example: `NEW.order_amount` = the new amount value

### `OLD` Keyword
- Used in **UPDATE** and **DELETE** triggers
- Refers to the OLD values before modification or deletion
- Example: `OLD.order_amount` = the previous amount value

### Important: `OLD` vs `NEW` availability
| Trigger Type | OLD | NEW |
|---|---|---|
| BEFORE INSERT | ❌ | ✅ |
| AFTER INSERT | ❌ | ✅ |
| BEFORE UPDATE | ✅ | ✅ |
| AFTER UPDATE | ✅ | ✅ |
| BEFORE DELETE | ✅ | ❌ |
| AFTER DELETE | ✅ | ❌ |

---

## Viewing and Managing Triggers

### View all triggers in your database:

```sql
SHOW TRIGGERS;
```

### View triggers for a specific table:

```sql
SHOW TRIGGERS FROM your_database LIKE 'orders%';
```

### View the trigger definition:

```sql
SHOW CREATE TRIGGER trigger_name;
```

### Delete a trigger:

```sql
DROP TRIGGER trigger_name;
```

---

## Real-World Use Cases

1. **Audit Logging** - Track who changed what and when
2. **Data Validation** - Prevent invalid data from being inserted
3. **Maintaining Denormalized Data** - Keep summary/cache tables updated
4. **Auto-updating Timestamps** - Automatically set `created_at` and `updated_at`
5. **Enforcing Business Rules** - Ensure stock doesn't go negative, totals are correct, etc.
6. **Notifications** - Trigger background jobs when important data changes

---

## Important Warnings

**Triggers can be tricky:**
- They execute silently in the background, making debugging harder
- They can impact performance if not optimized
- Complex trigger logic makes code harder to maintain
- Performance can be poor with multiple triggers on the same table
- Triggers can fire other triggers (cascading effects)

---

## Complete Working Example

Here's a complete, copy-paste ready example:

```sql
-- Create tables
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100),
    stock_quantity INT,
    price DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE stock_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    old_stock INT,
    new_stock INT,
    change_reason VARCHAR(100),
    logged_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create BEFORE UPDATE trigger to validate stock
CREATE TRIGGER before_stock_update
BEFORE UPDATE
ON products
FOR EACH ROW
BEGIN
    IF NEW.stock_quantity < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Stock cannot be negative';
    END IF;
END;

-- Create AFTER UPDATE trigger to log stock changes
CREATE TRIGGER after_stock_update
AFTER UPDATE
ON products
FOR EACH ROW
BEGIN
    IF OLD.stock_quantity != NEW.stock_quantity THEN
        INSERT INTO stock_log (product_id, old_stock, new_stock, change_reason)
        VALUES (NEW.product_id, OLD.stock_quantity, NEW.stock_quantity, 'Stock adjusted');
    END IF;
END;

-- Insert a product
INSERT INTO products (product_name, stock_quantity, price)
VALUES ('Laptop', 50, 999.99);

-- Update stock (triggers will fire)
UPDATE products SET stock_quantity = 45 WHERE product_id = 1;

-- Check the log
SELECT * FROM stock_log;

-- Try to set negative stock (will fail due to validation trigger)
UPDATE products SET stock_quantity = -5 WHERE product_id = 1;
```

