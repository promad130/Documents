
---

## **PART 1: INSTALLATION**

### **Step 1: Install MySQL Server**

Open your terminal and run:

```bash
sudo apt update
sudo apt install mysql-server
```

This installs:
- **MySQL Server** - The database engine (runs in background)
- **MySQL Client** - Command-line tool to connect and manage databases

**What's happening:** You're downloading MySQL from Ubuntu's package repository and installing it on your system.

---

### **Step 2: Verify Installation**

Check if MySQL installed correctly:

```bash
mysql --version
```

**Expected output:**
```
mysql  Ver 8.0.35-0ubuntu0.22.04.1 for Linux on x86_64
```

(Version number might differ—doesn't matter)

---

### **Step 3: Start MySQL Service**

MySQL runs as a **service** (background process). Start it:

```bash
sudo systemctl start mysql
```

**What this does:** Launches the MySQL server. Without this, you can't connect to any databases.

**Check if it's running:**

```bash
sudo systemctl status mysql
```

**Expected output:**
```
● mysql.service - MySQL Community Server
     Loaded: loaded (/lib/systemd/mysql.service; enabled; vendor preset: enabled)
     Active: active (running) since...
```

Green "active (running)" = MySQL is working ✓

---

### **Step 4: Enable MySQL on Startup**

Make MySQL start automatically when your computer boots:

```bash
sudo systemctl enable mysql
```

Now MySQL starts automatically—you don't need to manually start it every time.

---

## **PART 2: INITIAL SECURITY SETUP**

### **Step 5: Run Security Script**

MySQL comes with a setup script for security. Run it:

```bash
sudo mysql_secure_installation
```

You'll be asked several questions:

| Question | Answer | Why |
|----------|--------|-----|
| **Switch to unix_socket authentication?** | Y | Safer login method |
| **Change root password?** | Y | Set a strong password you'll remember |
| **Remove anonymous users?** | Y | Nobody can login without a password |
| **Disable remote root login?** | Y | Safer (only local connections) |
| **Remove test database?** | Y | Not needed |
| **Reload privilege tables?** | Y | Apply all changes |

**Example:**
```
Securing the MySQL server deployment.
Connecting to MySQL using a password-less method (unix_socket).

Switch to unix_socket authentication [Y/n] Y
Change the root password? [Y/n] Y
New password: your_strong_password_here
Re-enter new password: your_strong_password_here
Remove anonymous users? [Y/n] Y
Disable root login remotely? [Y/n] Y
Remove test database and access to it? [Y/n] Y
Reload privilege tables now? [Y/n] Y
```

**After this:** Your MySQL is secure.

---

## **PART 3: CONNECT TO MYSQL**

### **Step 6: Login to MySQL**

Now connect to the MySQL server using the command-line client:

```bash
sudo mysql -u root -p
```

**Breaking this down:**
- `sudo` = Run as administrator (needed for root user)
- `mysql` = The MySQL client program
- `-u root` = Connect as user "root"
- `-p` = Prompt for password (the one you set in security script)

**Press Enter, then enter your password.**

**Expected output:**
```
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 8
Server version: 8.0.35-0ubuntu0.22.04.1 (Ubuntu)

Copyright (c) 2000, 2023, Oracle and/or its affiliates.

mysql>
```

**The `mysql>` prompt = You're inside MySQL** ✓

Now you can type SQL commands.

---

## **PART 4: CREATE YOUR FIRST DATABASE**

### **Step 7: Create a Database**

You're now in the MySQL CLI. Create your first database:

```sql
CREATE DATABASE student_db;
```

**What this does:**
- Creates a new database named `student_db`
- This is like creating a new folder for organizing data

**Verify it was created:**

```sql
SHOW DATABASES;
```

**Expected output:**
```
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| student_db         |
| sys                |
+--------------------+
```

Your `student_db` is there! ✓

---

### **Step 8: Select the Database**

Before creating tables, tell MySQL which database to use:

```sql
USE student_db;
```

**Expected output:**
```
Database changed
```

Now any table you create will go inside `student_db`.

---

### **Step 9: Create Your First Table**

Create a simple table to store student information:

```sql
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    age INT
);
```

**Breaking this down:**
- `student_id INT` = Integer column for ID
- `PRIMARY KEY` = This column uniquely identifies each row
- `AUTO_INCREMENT` = Automatically assign next ID (1, 2, 3...)
- `name VARCHAR(100)` = Text field, up to 100 characters
- `email VARCHAR(100)` = Email field
- `age INT` = Integer for age

**Verify the table was created:**

```sql
SHOW TABLES;
```

**Expected output:**
```
+---------------------+
| Tables_in_student_db |
+---------------------+
| students            |
+---------------------+
```

Great! ✓

---

### **Step 10: Insert Data**

Add student records:

```sql
INSERT INTO students (name, email, age) VALUES ('Alice Johnson', 'alice@email.com', 20);
INSERT INTO students (name, email, age) VALUES ('Bob Smith', 'bob@email.com', 21);
INSERT INTO students (name, email, age) VALUES ('Carol White', 'carol@email.com', 19);
```

**What's happening:**
- Each `INSERT` adds one row to the table
- `student_id` is auto-generated (you don't specify it)

---

### **Step 11: Query Your Data**

Retrieve all students:

```sql
SELECT * FROM students;
```

**Expected output:**
```
+------------+----------------+--------------------+-----+
| student_id | name           | email              | age |
+------------+----------------+--------------------+-----+
|          1 | Alice Johnson  | alice@email.com    |  20 |
|          2 | Bob Smith      | bob@email.com      |  21 |
|          3 | Carol White    | carol@email.com    |  19 |
+------------+----------------+--------------------+-----+
```

Your data is stored! ✓

---

## **PART 5: EXIT MYSQL CLI**

### **Step 12: Exit MySQL**

When you're done, exit the MySQL prompt:

```sql
EXIT;
```

Or:

```sql
QUIT;
```

**Expected output:**
```
Bye
```

You're back to your regular terminal.

---

## **PART 6: RECONNECT LATER**

### **Step 13: Login Again**

Next time you want to use MySQL:

```bash
sudo mysql -u root -p
```

Then select your database:

```sql
USE student_db;
SELECT * FROM students;
```

**The data persists!** Databases save everything to disk.

---

## **PART 7: WORKING WITH MULTIPLE DATABASES**

### **Create Another Database**

```bash
sudo mysql -u root -p
```

```sql
CREATE DATABASE company_db;
USE company_db;

CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    department VARCHAR(50)
);

INSERT INTO employees (name, salary, department) VALUES 
('Alice', 75000, 'IT'),
('Bob', 65000, 'HR'),
('Carol', 85000, 'Sales');

SELECT * FROM employees;
```

**Switch between databases:**

```sql
USE student_db;
SELECT * FROM students;

USE company_db;
SELECT * FROM employees;
```

---

## **PART 8: BACKUP & RESTORE**

### **Backup Your Database**

Exit MySQL first (`EXIT;`), then in your terminal:

```bash
mysqldump -u root -p student_db > student_db_backup.sql
```

**What this does:**
- Exports entire `student_db` to a file named `student_db_backup.sql`
- This file contains all your tables and data
- You'll be prompted for password

**Check the backup file:**

```bash
ls -lh student_db_backup.sql
```

You'll see the file with its size.

---

### **Restore from Backup**

If your database gets damaged, restore from backup:

```bash
sudo mysql -u root -p < student_db_backup.sql
```

This recreates the entire database from the backup file.

---

## **PART 9: BETTER WAY: GUI TOOL (EASIER)**

Instead of command-line, use a graphical tool. Install **MySQL Workbench**:

```bash
sudo apt install mysql-workbench
```

Or use **DBeaver** (more user-friendly):
[Download Site](https://dbeaver.io/download/)

```bash
sudo add-apt-repository ppa:serge-rider/dbeaver-ce
sudo apt-get update
sudo apt-get install dbeaver-ce
```

### **Why use GUI?**
- ✓ Visual interface (easier to see data)
- ✓ Syntax highlighting for SQL
- ✓ Point-and-click database creation
- ✓ Better for learning

### **Using DBeaver:**

1. Open DBeaver (search in applications)
2. Click "New Database Connection"
3. Select "MySQL"
4. Enter:
   - **Server Host:** localhost
   - **Port:** 3306 (default)
   - **Username:** root
   - **Password:** (your password from setup)
5. Click "Test Connection"
6. Click "Finish"

Now you can see all your databases in a visual tree and click to create/edit tables.

---

## **PART 10: IMPORTANT CONCEPTS**

### **What is localhost/127.0.0.1?**

When you see `localhost` or `127.0.0.1`, it means:
- Your **local computer**
- MySQL running on your machine
- Port **3306** is MySQL's default port

### **What is a Connection?**

```
Your Computer → (Connection) → MySQL Server → Databases
```

A connection is like picking up a phone and calling the database server to ask for data.

### **Username vs Database**

- **Username (root)** = Your login to MySQL server
- **Database (student_db)** = The container holding your tables
- You can have multiple databases under one username

### **Table vs Database**

- **Database** = Container of tables (like a folder)
- **Table** = Container of rows (like a spreadsheet)

**Hierarchy:**
```
MySQL Server
├── student_db (Database)
│   ├── students (Table)
│   └── courses (Table)
└── company_db (Database)
    ├── employees (Table)
    └── departments (Table)
```

---

## **PART 11: COMMON COMMANDS REFERENCE**

### **Terminal Commands (Before MySQL login)**

```bash
# Start MySQL service
sudo systemctl start mysql

# Check if MySQL is running
sudo systemctl status mysql

# Stop MySQL
sudo systemctl stop mysql

# Restart MySQL
sudo systemctl restart mysql

# Login to MySQL
sudo mysql -u root -p

# Backup database
mysqldump -u root -p database_name > backup.sql

# Restore database
sudo mysql -u root -p < backup.sql
```

---

### **MySQL Commands (After `mysql>` prompt)**

```sql
-- Show all databases
SHOW DATABASES;

-- Create database
CREATE DATABASE mydb;

-- Delete database
DROP DATABASE mydb;

-- Select database to use
USE mydb;

-- Show tables in current database
SHOW TABLES;

-- Create table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100)
);

-- Insert data
INSERT INTO users (name) VALUES ('Alice');

-- View data
SELECT * FROM users;

-- Update data
UPDATE users SET name = 'Bob' WHERE id = 1;

-- Delete data
DELETE FROM users WHERE id = 1;

-- Drop table
DROP TABLE users;

-- Exit MySQL
EXIT;
```

---

## **PART 12: REAL-WORLD WORKFLOW**

### **Day 1: Setup**

```bash
# Install
sudo apt install mysql-server

# Start
sudo systemctl start mysql

# Secure
sudo mysql_secure_installation

# Login
sudo mysql -u root -p
```

### **Day 2: Create Your Project Database**

```bash
sudo mysql -u root -p
```

```sql
CREATE DATABASE my_project;
USE my_project;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    title VARCHAR(200),
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (username, email) VALUES 
('alice', 'alice@email.com'),
('bob', 'bob@email.com');

INSERT INTO posts (user_id, title, content) VALUES 
(1, 'First Post', 'Hello World'),
(1, 'Second Post', 'MySQL is fun'),
(2, 'Bob\'s Post', 'Great tutorial');

SELECT * FROM users;
SELECT * FROM posts;

EXIT;
```

### **Day 3: Backup Your Work**

```bash
mysqldump -u root -p my_project > my_project_backup.sql
```

---

## **PART 13: TROUBLESHOOTING**

### **Problem: "ERROR: Access denied for user 'root'@'localhost'"**

**Cause:** Wrong password or MySQL not running

**Solution:**
```bash
# Check if MySQL is running
sudo systemctl status mysql

# If not running, start it
sudo systemctl start mysql

# Try login again with correct password
sudo mysql -u root -p
```

---

### **Problem: "ERROR: Database 'mydb' doesn't exist"**

**Cause:** You're trying to use a database that doesn't exist

**Solution:**
```sql
-- Show what databases exist
SHOW DATABASES;

-- Create the database first
CREATE DATABASE mydb;

-- Then use it
USE mydb;
```

---

### **Problem: "ERROR: Unknown command 'myql'"**

**Cause:** Typo in command

**Solution:** Check spelling
```bash
# Wrong: myql
# Right: mysql

sudo mysql -u root -p
```

---

### **Problem: "Can't connect to local MySQL server"**

**Cause:** MySQL service isn't running

**Solution:**
```bash
# Start MySQL
sudo systemctl start mysql

# Check status
sudo systemctl status mysql

# If it fails, reinstall
sudo apt remove mysql-server
sudo apt install mysql-server
```

---

## **PART 14: PRACTICE EXERCISES**

### **Exercise 1: Create a Library Database**

```bash
sudo mysql -u root -p
```

```sql
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200),
    author VARCHAR(100),
    isbn VARCHAR(20) UNIQUE,
    publication_year INT,
    available_copies INT
);

CREATE TABLE members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    join_date DATE
);

CREATE TABLE borrowings (
    borrowing_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT,
    book_id INT,
    borrow_date DATE,
    return_date DATE,
    FOREIGN KEY (member_id) REFERENCES members(member_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

-- Add sample data
INSERT INTO books (title, author, isbn, publication_year, available_copies) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0-7432-7356-5', 1925, 3),
('To Kill a Mockingbird', 'Harper Lee', '978-0-06-112008-4', 1960, 2),
('1984', 'George Orwell', '978-0-452-26423-9', 1949, 1);

INSERT INTO members (name, email, phone, join_date) VALUES
('Alice Johnson', 'alice@email.com', '5551234567', '2024-01-15'),
('Bob Smith', 'bob@email.com', '5559876543', '2024-02-20');

INSERT INTO borrowings (member_id, book_id, borrow_date, return_date) VALUES
(1, 1, '2024-03-01', '2024-03-15'),
(2, 2, '2024-03-05', NULL),
(1, 3, '2024-03-10', '2024-03-20');

-- Query the data
SELECT * FROM books;
SELECT * FROM members;
SELECT * FROM borrowings;

-- Advanced query: Show which member has which book
SELECT 
    m.name AS member_name,
    b.title AS book_title,
    br.borrow_date,
    br.return_date
FROM borrowings br
JOIN members m ON br.member_id = m.member_id
JOIN books b ON br.book_id = b.book_id;

EXIT;
```

---

### **Exercise 2: Create an E-commerce Database**

```bash
sudo mysql -u root -p
```

```sql
CREATE DATABASE ecommerce_db;
USE ecommerce_db;

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    city VARCHAR(50),
    country VARCHAR(50)
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(200),
    category VARCHAR(100),
    price DECIMAL(10,2),
    stock INT
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date DATE,
    total_amount DECIMAL(12,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_items (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Add data
INSERT INTO customers (name, email, city, country) VALUES
('Alice Johnson', 'alice@email.com', 'New York', 'USA'),
('Bob Smith', 'bob@email.com', 'London', 'UK'),
('Carol White', 'carol@email.com', 'Toronto', 'Canada');

INSERT INTO products (product_name, category, price, stock) VALUES
('Laptop', 'Electronics', 999.99, 10),
('Mouse', 'Electronics', 29.99, 50),
('Desk Chair', 'Furniture', 199.99, 15);

INSERT INTO orders (customer_id, order_date, total_amount) VALUES
(1, '2024-03-01', 1029.98),
(2, '2024-03-05', 199.99),
(3, '2024-03-10', 29.99);

INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 999.99),
(1, 2, 1, 29.99),
(2, 3, 1, 199.99),
(3, 2, 1, 29.99);

-- See all orders with customer and product info
SELECT 
    o.order_id,
    c.name AS customer_name,
    o.order_date,
    p.product_name,
    oi.quantity,
    oi.price,
    (oi.quantity * oi.price) AS item_total
FROM orders o
JOIN customers c ON o.customer_id = c.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id;

EXIT;
```

---

## **PART 15: NEXT STEPS**

Now that MySQL is running on your system, you can:

1. **Practice SQL** using the exercises above
2. **Install a GUI tool** (DBeaver/Workbench) for easier management
3. **Learn Python + MySQL** to build applications
4. **Deploy to cloud** (AWS RDS, DigitalOcean, etc.)

---

## **QUICK REFERENCE CHEAT SHEET**

```bash
# ===== SYSTEM COMMANDS =====

# Start MySQL
sudo systemctl start mysql

# Stop MySQL
sudo systemctl stop mysql

# Check if running
sudo systemctl status mysql

# Login
sudo mysql -u root -p

# Backup
mysqldump -u root -p dbname > backup.sql

# Restore
sudo mysql -u root -p < backup.sql


# ===== MYSQL COMMANDS =====

-- Show databases
SHOW DATABASES;

-- Create database
CREATE DATABASE mydb;

-- Select database
USE mydb;

-- Show tables
SHOW TABLES;

-- Create table
CREATE TABLE users (id INT, name VARCHAR(100));

-- Insert
INSERT INTO users VALUES (1, 'Alice');

-- Query
SELECT * FROM users;

-- Update
UPDATE users SET name = 'Bob' WHERE id = 1;

-- Delete
DELETE FROM users WHERE id = 1;

-- Exit
EXIT;
```

---

**You now have MySQL fully setup and ready to use!** Start with the practice exercises and build from there. Let me know if you hit any issues.