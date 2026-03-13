
## What is a Database?

A **Database** is a collection of related data. A **Database Management System (DBMS)** is a software package designed to store and manage databases. As Ramakrishnan & Gehrke emphasize, a DBMS is crucial because it provides:

- **Data Independence:** Applications are insulated from how data is structured and stored.
- **Efficient Data Access:** Uses specialized techniques (like indexing) to retrieve data quickly.
- **Data Integrity and Security:** Enforces constraints (e.g., "salary cannot be negative").
- **Concurrent Access & Crash Recovery:** Allows multiple users to access data simultaneously while protecting against system failures.

### The Evolution: File Systems vs. DBMS

Before DBMS, data was stored in operating system files. This led to:

- **Redundancy:** Same information stored in multiple places.
- **Inconsistency:** Updating one file but forgetting another.
- **Difficulty in Access:** Writing a new C++ or Java program for every new query.

## 2. Types of Databases

Modern computing utilizes various database models based on the use case:

- **Relational Databases (RDBMS):** Data is organized into tables (relations). Examples: MySQL, PostgreSQL, Oracle. This is our primary focus.    
- **NoSQL Databases:** Non-tabular, used for big data and real-time web apps. Includes Document (MongoDB), Key-Value (Redis), and Graph databases (Neo4j).
- **Object-Oriented Databases:** Stores data as objects, similar to OOP languages.
- **Distributed Databases:** Data is stored across multiple physical locations.

### Relational Databases (RDBMS) vs. Object-Oriented (OODBMS)

> The Questions:
> What is the difference between relational database and Object-Oriented databas,e the table defined can be thought of as a class, and all the rows as columns?

To answer your question: **Yes, the analogy is very close!** In a RDBMS, we use tables, while in OOP, we use classes.

| Feature           | Relational (RDBMS)                                     | Object-Oriented (OODBMS)                   |
| ----------------- | ------------------------------------------------------ | ------------------------------------------ |
| **Basic Unit**    | **Table (Relation)**                                   | **Class / Object**                         |
| **Structure**     | Rows (Records) and Columns (Attributes).               | Objects with attributes and methods.       |
| **Relationships** | Established via Foreign Keys.                          | Established via references/pointers.       |
| **Logic**         | Logic is in the application code (SQL).                | Logic (Methods) is stored _with_ the data. |
| **Analogy**       | **Table** $\approx$ Class; **Row** $\approx$ Instance. | **Object** is stored directly.             |

**The "Impedance Mismatch":** In your Java/C++ code, you think in objects, but to save them in MySQL, you have to "flatten" them into rows. OODBMS (like ObjectDB) attempts to solve this by storing the object directly, but RDBMS remains more popular due to its rigorous mathematical foundation (Relational Algebra).

### NoSQL Databases (Deep Dive)

NoSQL (Not Only SQL) databases are "Schema-less," meaning they don't require a fixed table structure. This makes them ideal for rapid development and massive scale.

1. **Document Databases (e.g., MongoDB):**
    
    - **Concept:** Stores data in JSON-like documents.
        
    - **Example:** A "User" document might have varying fields (some have phone numbers, some don't) without needing a column for every possibility.
        
2. **Key-Value Stores (e.g., Redis):**
    
    - **Concept:** Like a giant Dictionary or Hash Map. You provide a Key and get a Value.
        
    - **Example:** Storing session tokens or shopping carts for a website where speed is the only priority.
        
3. **Graph Databases (e.g., Neo4j):**
    
    - **Concept:** Focuses on relationships (Edges) between entities (Nodes).
        
    - **Example:** A Social Network. "User A" _is friends with_ "User B." Querying "friends of friends" is much faster here than in SQL.
        
4. **Wide-Column Stores (e.g., Cassandra):**
    
    - **Concept:** Designed for massive writes and large datasets across many servers.
        
    - **Example:** Storing sensor logs from millions of IoT devices.

## Introduction to SQL

**SQL (Structured Query Language)** is the standard language for interacting with Relational Databases. It is declarative, meaning you tell the system _what_ you want, not _how_ to get it.

SQL is divided into sub-languages:

1. **DDL (Data Definition Language):** Defines the structure (schema).
    
    - `CREATE`, `ALTER`, `DROP`.
        
2. **DML (Data Manipulation Language):** Manages the data within the schema.
    
    - `INSERT`, `UPDATE`, `DELETE`.
    
3. **DQL (Data Query Language):** Used to retrieve data.
    
    - `SELECT`.
    
4. **DCL (Data Control Language):** Manages permissions.
    
    - `GRANT`, `REVOKE`.

## Getting Started with MySQL

MySQL is one of the most popular open-source RDBMS. It uses a Client-Server architecture.

### Basic Syntax & Commands

To begin, you typically interact with the MySQL server via a terminal or a GUI (like MySQL Workbench).

**Step 1: Create and Use a Database**

```
CREATE DATABASE University;
USE University;
```

**Step 2: Create a Table** Following the schema from the "Student" example in Ramakrishnan:

```
CREATE TABLE Students (
    sid INT PRIMARY KEY,
    name VARCHAR(50),
    login VARCHAR(20),
    age INT,
    gpa REAL
);
```

**Step 3: Insert Data**

```
INSERT INTO Students VALUES (53666, 'Jones', 'jones@cs', 18, 3.4);
```

**Step 4: Query Data**

```
SELECT name, gpa 
FROM Students 
WHERE age > 19;
```

(***Now follow along [[Introduction to MySQL]] to get into depth about MySQL!***)

# What is a Database Management System (DBMS)?

A **Database Management System (DBMS)** is a collection of interrelated data plus a set of programs to access that data. It provides an environment that is both convenient and efficient to store, retrieve, and manage information about a particular organization or enterprise.

## Why Do We Need DBMS?

Before DBMS, organizations used **file systems** to store data, which created serious problems:

- **Data redundancy**: Same information stored multiple times in different files. For example, if you store student records, you might repeat department name "Computer Science" and department head "Steve Rogers" for every student in that department.
- **Data inconsistency**: When the same data exists in multiple places, updating one copy but not others creates conflicts.
- **Difficulty accessing data**: You need to write a new program every time you want to retrieve data differently.
- **No integrity constraints**: Rules like "account balance must be greater than 0" had to be manually coded into every program.
- **Atomicity problems**: If a transaction fails midway (like transferring \$100 from account X to Y), the database could end up in an inconsistent state. Atomicity means a transaction either completes fully or doesn't happen at all.
- **Concurrency issues**: Multiple users accessing and updating the same data simultaneously without control leads to errors.[^1]
- **Security problems**: No systematic way to control who can access what data.[^1]

DBMS solves all these problems.[^1]

## Real-World DBMS Applications

Databases are everywhere:[^1]

- **Banking**: All transactions, account information
- **Airlines**: Flight reservations, schedules
- **Universities**: Student registration, grades
- **Sales**: Customer data, products, purchases
- **Manufacturing**: Production tracking, inventory, supply chain


## Three Levels of Data Abstraction

DBMS organizes data into three levels to separate how data is stored from how users see it:[^1]

- **Physical level**: How data is actually stored on disk (storage format, file structure)
- **Logical level**: What data is stored and relationships between data (like defining a customer has name, street, city)
- **View level**: What individual users or applications see (can hide sensitive info like salaries)


## Schema vs Instance

Think of a database like a variable in programming:[^1]

- **Schema**: The structure/design of the database (like the data type of a variable). It defines tables, columns, data types, and relationships. Schema rarely changes.[^6][^7][^1]
- **Instance**: The actual data stored at a specific moment (like the value of a variable). Instance changes frequently as data is inserted, updated, or deleted.[^7][^6][^1]

Example: The schema defines "Student table has ID, Name, Age columns." The instance is the actual student records like "1, Tony Stark, 18" at 9 AM today.[^7]

## Data Independence

- **Physical Data Independence**: You can change how data is stored (physical level) without changing the logical structure or applications.
- **Logical Data Independence**: You can modify the logical schema without affecting user views or applications.

This separation allows flexibility—you can optimize storage or add new tables without breaking existing programs.

## Database Users

Different types of users interact with DBMS:

- **Naive users**: Regular users like bank customers using ATMs or web apps
- **Application programmers**: Developers who write programs that interact with the database
- **Sophisticated users**: Data analysts who write complex queries
- **Database Administrator (DBA)**: Person responsible for schema design, security, backups, performance monitoring, and recovery


## System Architecture

Modern DBMS typically uses:

- **Two-tier**: Client programs directly communicate with database server
- **Three-tier**: Web applications with middleware between user interface and database

# Entity-Relationship (ER) Model

## Introduction to ER Model

The **Entity-Relationship (ER) Model** is a conceptual data model used to design databases. It provides a high-level, visual representation of data structure before implementation.

**Purpose:**
- Design databases systematically
- Communicate database structure to stakeholders
- Serve as a blueprint for physical database implementation

## Entities and Entity Sets

### Entity
A **real-world object** that exists independently and can be distinguished from other objects.

**Examples:**
- A specific student: "John Smith, ID: 12345"
- A particular car: "Toyota Camry, License: ABC123"
- An individual course: "DBMS101"

### Entity Set
A **collection of similar entities** sharing the same properties.

**Examples:**
- `STUDENT` (all students)
- `COURSE` (all courses)
- `EMPLOYEE` (all employees)

**Analogy:** 
- Entity Set = Table Name
- Entity = Row in that table

### ER Diagram Notation

| Element           | Symbol                                | Description           |
| ----------------- | ------------------------------------- | --------------------- |
| **Strong Entity** | ![Rectangle](Rectangle)               | Single-line rectangle |
| **Weak Entity**   | ![Double Rectangle](Double Rectangle) | Double-line rectangle |

```
┌─────────────┐
│   STUDENT   │  ← Strong Entity
└─────────────┘

╔═════════════╗
║  DEPENDENT  ║  ← Weak Entity
╚═════════════╝
```

### MySQL Implementation

```sql
-- Entity Set becomes a table
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(200),
    phone_no VARCHAR(15)
);

CREATE TABLE CLASSROOM (
    lecture_hall_no VARCHAR(10) PRIMARY KEY,
    capacity INT,
    no_of_benches INT,
    no_of_boards INT
);
```


## Attributes

**Attributes** are properties that describe entities. They become columns in database tables.

### Simple vs Composite Attributes

#### Simple Attribute
**Cannot be divided** into smaller meaningful parts.

**ER Notation:** 
```
    ○
    │
   age
```

**Examples:**
- `age`
- `gender`
- `student_id`
- `salary`

```sql
CREATE TABLE EMPLOYEE (
    emp_id INT,      -- Simple
    age INT,         -- Simple
    gender CHAR(1)   -- Simple
);
```

#### Composite Attribute
**Can be broken down** into smaller sub-attributes.

**ER Notation:**
```
        ○ name
       /│\
      / │ \
     ○  ○  ○
  first middle last
```

**Examples:**

**Name:**
- `first_name`
- `middle_name`
- `last_name`

**Address:**
- `street_number`
- `street_name`
- `city`
- `state`
- `postal_code`

**Mapping Rule:** Only **leaf-level** sub-attributes become columns.

```sql
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY,
    -- Don't create a 'name' column
    first_name VARCHAR(50),   -- Leaf attribute
    middle_name VARCHAR(50),  -- Leaf attribute
    last_name VARCHAR(50),    -- Leaf attribute
    -- Don't create an 'address' column
    street_number VARCHAR(10),
    street_name VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(10)
);
```

### 3.2 Single-valued vs Multivalued Attributes

#### Single-valued Attribute
Has **only one value** per entity.

**ER Notation:**
```
    ○
    │
 birth_date
```

**Examples:**
- `birth_date` (one per person)
- `student_id` (one per student)
- `passport_number` (one per person)

```sql
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,  -- Single-valued
    birth_date DATE              -- Single-valued
);
```

#### Multivalued Attribute
Can have **multiple values** for a single entity.

**ER Notation:**
```
   ◎◎
    │
phone_numbers
```
(Double ellipse/circle)

**Examples:**
- `phone_numbers` (mobile, home, office)
- `email_addresses`
- `hobbies`
- `degrees_earned`

**Mapping Rule:** Create a **separate table** with:
1. The entity's primary key (foreign key)
2. The multivalued attribute column(s)
3. Composite primary key of both

```sql
-- Main entity
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

-- Separate table for multivalued attribute
CREATE TABLE STUDENT_PHONE (
    student_id INT,
    phone_number VARCHAR(15),
    phone_type VARCHAR(20),  -- Optional: 'mobile', 'home', 'office'
    
    PRIMARY KEY (student_id, phone_number),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
        ON DELETE CASCADE
);

-- Example: One student with multiple phones
INSERT INTO STUDENT VALUES (101, 'John Doe');
INSERT INTO STUDENT_PHONE VALUES (101, '555-1234', 'mobile');
INSERT INTO STUDENT_PHONE VALUES (101, '555-5678', 'home');
INSERT INTO STUDENT_PHONE VALUES (101, '555-9012', 'office');
```

**Another Example: Hobbies**

```sql
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE EMPLOYEE_HOBBY (
    emp_id INT,
    hobby VARCHAR(50),
    
    PRIMARY KEY (emp_id, hobby),
    FOREIGN KEY (emp_id) REFERENCES EMPLOYEE(emp_id)
        ON DELETE CASCADE
);
```

### 3.3 Stored vs Derived Attributes

#### Stored Attribute
**Physically stored** in the database.

```sql
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    birth_date DATE  -- Stored
);
```

#### Derived Attribute
**Calculated** from other attributes, **not stored**.

**ER Notation:**
```
   (○)  ← Dashed ellipse
    │
   age
```

**Examples:**
- `age` (derived from `birth_date`)
- `total_marks` (sum of subject marks)
- `years_of_service` (derived from `hire_date`)
- `GPA` (calculated from grades)

```sql
-- Don't create a column for derived attributes
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    birth_date DATE  -- Store this
    -- age INT  ← DON'T DO THIS
);

-- Calculate age when needed
SELECT 
    student_id,
    birth_date,
    TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) AS age  -- Derived
FROM STUDENT;

-- Another example: Total salary
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    basic_salary DECIMAL(10,2),
    allowance DECIMAL(10,2),
    bonus DECIMAL(10,2)
    -- total_salary DECIMAL(10,2)  ← DON'T store this
);

SELECT 
    emp_id,
    basic_salary,
    allowance,
    bonus,
    (basic_salary + allowance + bonus) AS total_salary  -- Derived
FROM EMPLOYEE;
```

**Using Virtual Columns (MySQL 5.7+):**

```sql
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    hire_date DATE,
    years_of_service INT AS (TIMESTAMPDIFF(YEAR, hire_date, CURDATE())) VIRTUAL
);
```

### 3.4 Domain / Value Set

The **set of allowed values** for an attribute.

**Examples:**

| Attribute     | Domain                                            |
| ------------- | ------------------------------------------------- |
| `gender`      | {'M', 'F', 'Other'}                               |
| `shirt_size`  | {'S', 'M', 'L', 'XL', 'XXL'}                      |
| `grade`       | {'A', 'B', 'C', 'D', 'F'}                         |
| `age`         | {0, 1, 2, ..., 150}                               |
| `day_of_week` | {'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'} |

```sql
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    gender ENUM('M', 'F', 'Other'),  -- Domain constraint
    shirt_size ENUM('S', 'M', 'L', 'XL', 'XXL'),
    age INT CHECK (age >= 0 AND age <= 150),
    
    CONSTRAINT chk_gender CHECK (gender IN ('M', 'F', 'Other'))
);

CREATE TABLE ENROLLMENT (
    student_id INT,
    course_id INT,
    grade CHAR(1) CHECK (grade IN ('A', 'B', 'C', 'D', 'F')),
    PRIMARY KEY (student_id, course_id)
);
```

### Attribute Notation Summary

| Attribute Type | ER Notation | Example |
|----------------|-------------|---------|
| **Simple** | `○` (Single ellipse) | age, id |
| **Composite** | `○` with branches | name (first, middle, last) |
| **Single-valued** | `○` (Single ellipse) | birth_date |
| **Multivalued** | `◎◎` (Double ellipse) | phone_numbers |
| **Derived** | `(○)` (Dashed ellipse) | age from birth_date |
| **Key Attribute** | `○` with underline | student_id |

---

## Relationships and Relationship Sets 

### Relationship
An **association** between two or more entities.

**Examples:**
- Student **enrolls in** Course
- Employee **works for** Department
- Author **writes** Book

### Relationship Set
A **collection of similar relationships** between entity sets.

**ER Notation:**
```
┌─────────┐         ◇         ┌─────────┐
│ STUDENT │─────enrolls_in────│ COURSE  │
└─────────┘                   └─────────┘
```
(Diamond shape for relationship)

### 4.1 Degree of Relationships

#### Unary (Recursive) Relationship
Relationship **within the same entity set**.

**ER Diagram:**
```
        ┌─────────────┐
    ┌───│  EMPLOYEE   │◄──┐
    │   └─────────────┘   │
    │          │          │
    │       manages       │
    │          │          │
    └──────────◇──────────┘
          (manager)  (worker)
          ↑ Role indicators ↑
```

**Examples:**
1. **Employee manages Employee**
   - manager → worker relationship
   
2. **Person is_married_to Person**
   - spouse1 → spouse2 relationship

3. **Course is_prerequisite_for Course**
   - prerequisite → dependent course

```sql
-- Example 1: Employee manages Employee
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    manager_id INT,  -- Foreign key to same table
    
    FOREIGN KEY (manager_id) REFERENCES EMPLOYEE(emp_id)
);

-- Data example
INSERT INTO EMPLOYEE VALUES (1, 'CEO', NULL);        -- Top manager
INSERT INTO EMPLOYEE VALUES (2, 'Manager A', 1);     -- Reports to CEO
INSERT INTO EMPLOYEE VALUES (3, 'Employee B', 2);    -- Reports to Manager A

-- Find manager of an employee
SELECT 
    e.name AS employee,
    m.name AS manager
FROM EMPLOYEE e
LEFT JOIN EMPLOYEE m ON e.manager_id = m.emp_id;

-- Example 2: Course prerequisites
CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100)
);

CREATE TABLE PREREQUISITE (
    course_id VARCHAR(10),
    prerequisite_course_id VARCHAR(10),
    
    PRIMARY KEY (course_id, prerequisite_course_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id),
    FOREIGN KEY (prerequisite_course_id) REFERENCES COURSE(course_id)
);

-- Data: DBMS requires Data Structures
INSERT INTO COURSE VALUES ('CS101', 'Data Structures');
INSERT INTO COURSE VALUES ('CS201', 'DBMS');
INSERT INTO PREREQUISITE VALUES ('CS201', 'CS101');
```

#### Binary Relationship
Relationship between **two different entity sets**.

**ER Diagram:**
```
┌─────────┐         ◇         ┌─────────┐
│ STUDENT │────enrolls_in─────│ COURSE  │
└─────────┘                   └─────────┘
```

**Examples:**
- Student enrolls in Course
- Employee works in Department
- Customer borrows Loan

```sql
-- Example: Student enrolls in Course
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100)
);

CREATE TABLE ENROLLS_IN (
    student_id INT,
    course_id VARCHAR(10),
    enrollment_date DATE,
    
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);
```

#### Ternary Relationship
**Simultaneous relationship** among three entity sets.

**ER Diagram:**
```
┌────────────┐
│ INSTRUCTOR │──┐
└────────────┘  │
                │    ◇
                ├──teaches──┐
                │           │
┌─────────┐    │           │    ┌─────────┐
│ STUDENT │────┘           └────│ COURSE  │
└─────────┘                     └─────────┘
```

**Example:** Instructor teaches Student in Course (which instructor teaches which students in which course)

**Real-world scenario:** 
- Dr. Smith teaches John and Mary in DBMS
- Dr. Johnson teaches John and Peter in Algorithms

```sql
CREATE TABLE INSTRUCTOR (
    instructor_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100)
);

-- Ternary relationship
CREATE TABLE TEACHES (
    instructor_id INT,
    student_id INT,
    course_id VARCHAR(10),
    semester VARCHAR(20),
    
    PRIMARY KEY (instructor_id, student_id, course_id, semester),
    FOREIGN KEY (instructor_id) REFERENCES INSTRUCTOR(instructor_id),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);

-- Data example
INSERT INTO TEACHES VALUES (101, 201, 'CS301', 'Fall 2026');
-- Instructor 101 teaches Student 201 in Course CS301 in Fall 2026
```

**Another Example:** Supplier supplies Part to Project

```sql
CREATE TABLE SUPPLIER (
    supplier_id INT PRIMARY KEY,
    supplier_name VARCHAR(100)
);

CREATE TABLE PART (
    part_id INT PRIMARY KEY,
    part_name VARCHAR(100)
);

CREATE TABLE PROJECT (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100)
);

CREATE TABLE SUPPLIES (
    supplier_id INT,
    part_id INT,
    project_id INT,
    quantity INT,
    supply_date DATE,
    
    PRIMARY KEY (supplier_id, part_id, project_id),
    FOREIGN KEY (supplier_id) REFERENCES SUPPLIER(supplier_id),
    FOREIGN KEY (part_id) REFERENCES PART(part_id),
    FOREIGN KEY (project_id) REFERENCES PROJECT(project_id)
);
```

### 4.2 Attributes on Relationships

Relationships can have **descriptive attributes**.

**Example:** Student enrolls in Course

**Relationship Attributes:**
- `enrollment_date`
- `semester`
- `grade`

```sql
CREATE TABLE ENROLLS_IN (
    student_id INT,
    course_id VARCHAR(10),
    
    -- Relationship attributes
    enrollment_date DATE,
    semester VARCHAR(20),
    year INT,
    grade CHAR(1),
    
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);
```

**Another Example:** Employee works in Department

```sql
CREATE TABLE WORKS_IN (
    emp_id INT,
    dept_id INT,
    
    -- Relationship attributes
    start_date DATE,
    role VARCHAR(50),
    
    PRIMARY KEY (emp_id, dept_id),
    FOREIGN KEY (emp_id) REFERENCES EMPLOYEE(emp_id),
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id)
);
```

---

## 5. Cardinality and Participation Constraints 

### 5.1 Cardinality Constraints

Defines **how many instances** of one entity can be associated with instances of another entity.

#### One-to-One (1:1)

Each entity in A relates to **at most one** entity in B, and vice versa.

**ER Notation:**
```
┌─────────┐     1      ◇      1     ┌──────────────┐
│ STUDENT │────────allocates────────│ HOSTEL_ROOM  │
└─────────┘                         └──────────────┘
```

**Real-world Examples:**
- One student allocated to one hostel room
- One person has one passport
- One employee has one company ID card

```sql
-- Method 1: Foreign key in either table
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE HOSTEL_ROOM (
    room_id INT PRIMARY KEY,
    room_number VARCHAR(10),
    student_id INT UNIQUE,  -- UNIQUE ensures 1:1
    
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
);

-- Method 2: Share the same primary key
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE PASSPORT (
    person_id INT PRIMARY KEY,  -- Same as PERSON's PK
    passport_number VARCHAR(20),
    issue_date DATE,
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);
```

#### One-to-Many (1:M)

Each entity in A relates to **multiple** entities in B, but each entity in B relates to **at most one** in A.

**ER Notation:**
```
┌────────────┐    1      ◇      M    ┌─────────┐
│ INSTRUCTOR │─────────teaches───────│ COURSE  │
└────────────┘                        └─────────┘
```

**Real-world Examples:**
- One instructor teaches many courses
- One department has many employees
- One customer places many orders

```sql
-- Foreign key goes in the "Many" side
CREATE TABLE INSTRUCTOR (
    instructor_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100),
    instructor_id INT,  -- Foreign key on "Many" side
    
    FOREIGN KEY (instructor_id) REFERENCES INSTRUCTOR(instructor_id)
);

-- Data example
INSERT INTO INSTRUCTOR VALUES (101, 'Dr. Smith');
INSERT INTO COURSE VALUES ('CS101', 'DBMS', 101);
INSERT INTO COURSE VALUES ('CS102', 'Algorithms', 101);
INSERT INTO COURSE VALUES ('CS103', 'Networks', 101);
```

#### Many-to-One (M:1)

**Multiple** entities in A relate to **one** entity in B.

**ER Notation:**
```
┌─────────┐     M      ◇      1     ┌──────────┐
│ STUDENT │──────────does───────────│ PROJECT  │
└─────────┘                         └──────────┘
```

**Real-world Examples:**
- Many students work on one major project
- Many employees belong to one department
- Many books written by one author

```sql
CREATE TABLE PROJECT (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100)
);

CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    project_id INT,  -- Foreign key on "Many" side
    
    FOREIGN KEY (project_id) REFERENCES PROJECT(project_id)
);

-- Data example: Multiple students on same project
INSERT INTO PROJECT VALUES (1, 'Library Management System');
INSERT INTO STUDENT VALUES (101, 'John', 1);
INSERT INTO STUDENT VALUES (102, 'Mary', 1);
INSERT INTO STUDENT VALUES (103, 'Peter', 1);
```

#### Many-to-Many (M:M)

Entities in both sets can relate to **multiple** entities in the other set.

**ER Notation:**
```
┌──────���──┐     M      ◇      M     ┌─────────┐
│ STUDENT │─────────enrolls─────────│ COURSE  │
└─────────┘                         └─────────┘
```

**Real-world Examples:**
- Students enroll in many courses; courses have many students
- Actors appear in many movies; movies have many actors
- Authors write many books; books have many authors

```sql
-- Requires a separate "junction" table
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100)
);

-- Junction table
CREATE TABLE ENROLLS_IN (
    student_id INT,
    course_id VARCHAR(10),
    enrollment_date DATE,
    
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);

-- Data example
INSERT INTO STUDENT VALUES (101, 'John'), (102, 'Mary');
INSERT INTO COURSE VALUES ('CS101', 'DBMS'), ('CS102', 'Algorithms');

-- John enrolls in both courses
INSERT INTO ENROLLS_IN VALUES (101, 'CS101', '2026-01-15');
INSERT INTO ENROLLS_IN VALUES (101, 'CS102', '2026-01-15');

-- Mary enrolls in CS101
INSERT INTO ENROLLS_IN VALUES (102, 'CS101', '2026-01-16');
```

### 5.2 Min-Max Notation

Specifies **minimum and maximum** number of relationship instances.

**Notation:** `(min, max)`

**ER Diagram:**
```
┌──────────┐  (0,2)     ◇     (1,M)   ┌──────┐
│ CUSTOMER │───────────borrows────────│ LOAN │
└──────────┘                          └──────┘
```

**Interpretation:**
- `(0, 2)`: Customer can have **0 to 2** loans
- `(1, M)`: Each loan must have **at least 1** customer, up to many

```sql
-- Enforce min-max using triggers (simplified example)
CREATE TABLE CUSTOMER (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE LOAN (
    loan_id INT PRIMARY KEY,
    amount DECIMAL(10,2)
);

CREATE TABLE BORROWS (
    customer_id INT,
    loan_id INT,
    
    PRIMARY KEY (customer_id, loan_id),
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id),
    FOREIGN KEY (loan_id) REFERENCES LOAN(loan_id)
);

-- Trigger to enforce max 2 loans per customer
DELIMITER $$
CREATE TRIGGER check_max_loans
BEFORE INSERT ON BORROWS
FOR EACH ROW
BEGIN
    DECLARE loan_count INT;
    
    SELECT COUNT(*) INTO loan_count
    FROM BORROWS
    WHERE customer_id = NEW.customer_id;
    
    IF loan_count >= 2 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Customer cannot have more than 2 loans';
    END IF;
END$$
DELIMITER ;
```

**Common Min-Max Patterns:**

| Pattern  | Meaning                 |
| -------- | ----------------------- |
| `(0, 1)` | Optional, at most one   |
| `(1, 1)` | Exactly one (mandatory) |
| `(0, M)` | Zero or more (optional) |
| `(1, M)` | One or more (mandatory) |
| `(0, N)` | Up to N instances       |

### 5.3 Participation Constraints

Defines whether **all entities must participate** in a relationship.

#### Total Participation

**Every entity must participate** in at least one relationship.

**ER Notation:** Double line `═══`

**ER Diagram:**
```
┌──────┐            ◇           ┌──────────┐
│ LOAN ╞══════must_have════════│ CUSTOMER │
└──────┘                        └──────────┘
  (Total participation)
```

**Meaning:** Every loan **must** have a customer (no loan exists without a customer).

```sql
-- Enforce with NOT NULL foreign key
CREATE TABLE CUSTOMER (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE LOAN (
    loan_id INT PRIMARY KEY,
    amount DECIMAL(10,2),
    customer_id INT NOT NULL,  -- NOT NULL enforces total participation
    
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id)
);

-- This will FAIL (total participation violated)
-- INSERT INTO LOAN VALUES (1, 10000, NULL);  ← Error!
```

#### Partial Participation

**Some entities may not participate** in any relationship.

**ER Notation:** Single line `───`

**ER Diagram:**
```
┌──────────┐          ◇          ┌──────┐
│ CUSTOMER │────may_borrow───────│ LOAN │
└──────────┘                     └──────┘
  (Partial participation)
```

**Meaning:** Not all customers have loans (some customers may have zero loans).

```sql
-- Foreign key allows NULL
CREATE TABLE CUSTOMER (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE LOAN (
    loan_id INT PRIMARY KEY,
    amount DECIMAL(10,2)
);

CREATE TABLE BORROWS (
    customer_id INT,
    loan_id INT,
    
    PRIMARY KEY (customer_id, loan_id),
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id),
    FOREIGN KEY (loan_id) REFERENCES LOAN(loan_id)
);

-- Valid: Customer with no loans
INSERT INTO CUSTOMER VALUES (101, 'John');
-- No entry in BORROWS for customer 101 → Partial participation
```

### Complete Constraint Example

```
┌─────────┐  (1,1)     ◇     (1,M)   ┌──────────┐
│ STUDENT ╞═══════enrolled_in═══════│ COLLEGE  │
└─────────┘  Total              Partial └──────────┘
```

**Interpretation:**
- Each student must be enrolled in exactly one college (total participation, 1:1)
- Each college can have one or many students (partial participation, 1:M)

```sql
CREATE TABLE COLLEGE (
    college_id INT PRIMARY KEY,
    college_name VARCHAR(100)
);

CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    college_id INT NOT NULL,  -- Total participation
    
    FOREIGN KEY (college_id) REFERENCES COLLEGE(college_id)
);
```

---

## Keys

Keys are attributes that **uniquely identify** entities in an entity set.

### 6.1 Super Key

**Any set of attributes** that can uniquely identify a row.

**Definition:** A super key is any combination of attributes whose values uniquely identify a tuple (row).

**Examples:**

Given `STUDENT(student_id, email, name, phone)`:

**Super Keys:**
- `{student_id}` ✓
- `{email}` ✓
- `{student_id, name}` ✓ (still unique)
- `{student_id, email, phone}` ✓ (still unique)
- `{email, name}` ✓ (if email is unique)

**Not a Super Key:**
- `{name}` ✗ (not unique - many students can have same name)
- `{phone}` ✗ (not unique if multiple students share a phone)

### 6.2 Candidate Key

A **minimal super key** - no redundant attributes.

**Definition:** A super key with no unnecessary attributes. If you remove any attribute, it loses uniqueness.

**Test for Candidate Key:**
1. Start with a super key
2. Try removing each attribute one by one
3. If it stays unique after removal → original was NOT a candidate key
4. If it loses uniqueness → original WAS a candidate key

**Examples:**

Given `STUDENT(student_id, email, name, phone)`:

| Set                   | Super Key? | Candidate Key? | Why?                               |
| --------------------- | ---------- | -------------- | ---------------------------------- |
| `{student_id}`        | ✓          | ✓              | Minimal - can't remove anything    |
| `{email}`             | ✓          | ✓              | Minimal - can't remove anything    |
| `{student_id, name}`  | ✓          | ✗              | Can remove `name` and still unique |
| `{student_id, email}` | ✓          | ✗              | Can remove either and still unique |

**Another Example:**

`EMPLOYEE(emp_id, ssn, email, name, dept)`

**Candidate Keys:**
- `{emp_id}` - unique employee ID
- `{ssn}` - unique social security number  
- `{email}` - unique email address

**Not Candidate Keys:**
- `{emp_id, name}` - redundant `name`
- `{ssn, email}` - redundant (either alone is enough)

### 6.3 Primary Key

The **chosen candidate key** to be the main identifier.

**Rules:**
- Must be **unique**
- Must be **NOT NULL**
- Only **one per table**
- Should be **stable** (doesn't change often)
- Preferably **simple** (fewer attributes)

```sql
-- Single attribute primary key
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,  -- Primary key
    email VARCHAR(100) UNIQUE,   -- Candidate key (not chosen as primary)
    name VARCHAR(100)
);

-- Named constraint
CREATE TABLE EMPLOYEE (
    emp_id INT,
    ssn VARCHAR(11),
    email VARCHAR(100),
    name VARCHAR(100),
    
    CONSTRAINT pk_employee PRIMARY KEY (emp_id)
);
```

### 6.4 Composite Primary Key

A primary key consisting of **multiple attributes**.

**When to use:**
- Natural combination uniquely identifies entity
- Junction tables (M:M relationships)
- Weak entity sets

```sql
-- Example 1: Student enrollment (M:M relationship)
CREATE TABLE ENROLLMENT (
    student_id INT,
    course_id VARCHAR(10),
    semester VARCHAR(20),
    grade CHAR(1),
    
    PRIMARY KEY (student_id, course_id),  -- Composite key
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);

-- Example 2: Flight seat reservation
CREATE TABLE SEAT_RESERVATION (
    flight_number VARCHAR(10),
    seat_number VARCHAR(5),
    passenger_name VARCHAR(100),
    
    PRIMARY KEY (flight_number, seat_number)  -- Composite key
);

-- Example 3: Time slots
CREATE TABLE CLASS_SCHEDULE (
    room_id VARCHAR(10),
    day VARCHAR(10),
    time_slot VARCHAR(20),
    course_id VARCHAR(10),
    
    PRIMARY KEY (room_id, day, time_slot)  -- Three-attribute composite key
);
```

### 6.5 Alternate Key

**Candidate keys that were NOT chosen** as the primary key.

**Implementation:** Use `UNIQUE` constraint

```sql
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,      -- Primary key
    ssn VARCHAR(11) UNIQUE,      -- Alternate key
    email VARCHAR(100) UNIQUE,   -- Alternate key
    name VARCHAR(100)
);

-- Both SSN and email are candidate keys, but emp_id was chosen as primary
```

### 6.6 Foreign Key

An attribute that **references the primary key** of another table.

**Purpose:**
- Establish relationships between tables
- Maintain referential integrity
- Link parent-child tables

```sql
-- Parent table
CREATE TABLE DEPARTMENT (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100)
);

-- Child table
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    dept_id INT,  -- Foreign key
    
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id)
);

-- Referential integrity enforced
INSERT INTO DEPARTMENT VALUES (1, 'IT');
INSERT INTO EMPLOYEE VALUES (101, 'John', 1);  -- ✓ Valid

-- This will FAIL (dept_id 99 doesn't exist)
-- INSERT INTO EMPLOYEE VALUES (102, 'Mary', 99);  ← Error!
```

**Foreign Key Actions:**

```sql
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    dept_id INT,
    
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id)
        ON DELETE CASCADE      -- Delete employees when department is deleted
        ON UPDATE CASCADE      -- Update emp.dept_id when dept.dept_id changes
);

-- Other options:
-- ON DELETE SET NULL     → Set foreign key to NULL
-- ON DELETE RESTRICT     → Prevent deletion if referenced
-- ON DELETE NO ACTION    → Same as RESTRICT
-- ON UPDATE SET NULL
-- ON UPDATE RESTRICT
```

### Keys in Relationships

For a relationship set, the **combination of primary keys** from participating entities forms the super key.

**Example:** `ENROLLS_IN(student_id, course_id, semester, grade)`

**Super Key:** `{student_id, course_id}`

```sql
CREATE TABLE ENROLLS_IN (
    student_id INT,
    course_id VARCHAR(10),
    semester VARCHAR(20),
    grade CHAR(1),
    
    PRIMARY KEY (student_id, course_id),  -- Super key of relationship
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);
```

### MySQL Key Constraint Summary

| Concept | MySQL Constraint | Count per Table | Allows NULL? |
|---------|------------------|-----------------|--------------|
| **Primary Key** | `PRIMARY KEY` | 1 | ✗ No |
| **Candidate Key** | `UNIQUE` | Multiple | ✓ Yes (unless `NOT NULL`) |
| **Alternate Key** | `UNIQUE` | Multiple | ✓ Yes |
| **Foreign Key** | `FOREIGN KEY` | Multiple | ✓ Yes (unless `NOT NULL`) |
| **Super Key** | _(Conceptual)_ | N/A | N/A |

**Complete Example:**

```sql
CREATE TABLE STUDENT (
    -- Primary key
    student_id INT PRIMARY KEY,
    
    -- Alternate keys (candidate keys not chosen as primary)
    email VARCHAR(100) UNIQUE NOT NULL,
    ssn VARCHAR(11) UNIQUE,
    
    -- Regular attributes
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    birth_date DATE,
    
    -- Foreign key
    major_dept_id INT,
    
    FOREIGN KEY (major_dept_id) REFERENCES DEPARTMENT(dept_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    
    -- Composite unique constraint (another candidate key)
    CONSTRAINT uc_name_dob UNIQUE (first_name, last_name, birth_date)
);
```


## Weak Entities

### Definition

A **weak entity** is an entity that:
1. **Cannot be uniquely identified** by its own attributes alone
2. **Depends on a strong entity** (owner) for its existence
3. Has a **partial key (discriminator)** that identifies entities within the owner

### Strong vs Weak Entity

| Aspect | Strong Entity | Weak Entity |
|--------|---------------|-------------|
| **Primary Key** | Has its own | Borrows from owner |
| **Existence** | Independent | Dependent |
| **ER Notation** | Single rectangle `┌───┐` | Double rectangle `╔═══╗` |
| **Example** | `LOAN` | `PAYMENT` |

### ER Diagram Notation

```
┌──────────┐                     ╔═══════════╗
│   LOAN   │                     ║  PAYMENT  ║
│ (Strong) │                     ║  (Weak)   ║
└──────────┘                     ╚═══════════╝
     │                                 │
     │           ◇◇                    │
     └──────has_payment────────────────┘
           (Identifying                 
            Relationship)               
                                  payment_number
                                  (Discriminator)
```

**Notation Summary:**

| Element | Symbol | Description |
|---------|--------|-------------|
| **Weak Entity** | `╔═══╗` | Double rectangle |
| **Identifying Relationship** | `◇◇` | Double diamond |
| **Discriminator (Partial Key)** | Underline with dashes | `payment_number` |
| **Total Participation** | `═══` | Double line (weak entity side) |

### Example 1: Loan and Payment

**Scenario:** 
- A `LOAN` is a strong entity
- A `PAYMENT` is a weak entity (payments exist only for a loan)
- `payment_number` alone doesn't uniquely identify a payment across all loans
- Combination `(loan_number, payment_number)` uniquely identifies a payment

**ER Diagram:**
```
┌────────────────┐                    ╔══════════════════╗
│     LOAN       │                    ║    PAYMENT       ║
├────────────────┤                    ╠══════════════════╣
│ loan_number PK │◇◇──has_payment──◇◇║ payment_number   ║ ← Discriminator
│ amount         │                    ║ payment_date     ║
│ interest_rate  │                    ║ payment_amount   ║
└────────────────┘                    ╚══════════════════╝
      (Strong)                              (Weak)
```

```sql
-- Strong entity
CREATE TABLE LOAN (
    loan_number INT PRIMARY KEY,
    amount DECIMAL(10,2),
    interest_rate DECIMAL(5,2)
);

-- Weak entity
CREATE TABLE PAYMENT (
    payment_number INT NOT NULL,       -- Discriminator (partial key)
    loan_number INT NOT NULL,          -- Foreign key to owner
    payment_date DATE NOT NULL,
    payment_amount DECIMAL(10,2) NOT NULL,
    
    -- Composite primary key: owner's key + discriminator
    PRIMARY KEY (loan_number, payment_number),
    
    -- Foreign key with CASCADE (weak entity depends on strong entity)
    FOREIGN KEY (loan_number) REFERENCES LOAN(loan_number)
        ON DELETE CASCADE  -- If loan deleted, all payments deleted
        ON UPDATE CASCADE
);

-- Data example
INSERT INTO LOAN VALUES (1001, 100000, 8.5);

INSERT INTO PAYMENT VALUES (1, 1001, '2026-01-15', 5000);  -- First payment
INSERT INTO PAYMENT VALUES (2, 1001, '2026-02-15', 5000);  -- Second payment
INSERT INTO PAYMENT VALUES (3, 1001, '2026-03-15', 5000);  -- Third payment

-- payment_number 1, 2, 3 are only unique within loan 1001
-- Another loan can also have payment_number 1, 2, 3
INSERT INTO LOAN VALUES (1002, 50000, 7.5);
INSERT INTO PAYMENT VALUES (1, 1002, '2026-01-20', 2500);  -- payment_number 1 again!
```

### Example 2: Employee and Dependent

**Scenario:**
- `EMPLOYEE` is a strong entity
- `DEPENDENT` is a weak entity (dependents exist only for an employee)
- `dependent_name` alone may not be unique (two employees might have dependents named "John")

**ER Diagram:**
```
┌────────────────┐                    ╔══════════════════╗
│   EMPLOYEE     │                    ║    DEPENDENT     ║
├────────────────┤                    ╠══════════════════╣
│ emp_id PK      │◇◇───has_dependent──◇◇║ dependent_name   ║ ← Discriminator
│ name           │                    ║ birth_date       ║
│ salary         │                    ║ relationship     ║
└────────────────┘                    ╚══════════════════╝
      (Strong)                              (Weak)
```

```sql
-- Strong entity
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2)
);

-- Weak entity
CREATE TABLE DEPENDENT (
    emp_id INT NOT NULL,               -- Foreign key to owner
    dependent_name VARCHAR(100) NOT NULL,  -- Discriminator
    birth_date DATE,
    relationship VARCHAR(20),
    
    -- Composite primary key
    PRIMARY KEY (emp_id, dependent_name),
    
    FOREIGN KEY (emp_id) REFERENCES EMPLOYEE(emp_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Data example
INSERT INTO EMPLOYEE VALUES (101, 'John Smith', 75000);
INSERT INTO EMPLOYEE VALUES (102, 'Mary Johnson', 80000);

-- John's dependents
INSERT INTO DEPENDENT VALUES (101, 'Alice Smith', '2010-05-15', 'Daughter');
INSERT INTO DEPENDENT VALUES (101, 'Bob Smith', '2012-08-20', 'Son');

-- Mary's dependent (also named 'Alice' - no conflict!)
INSERT INTO DEPENDENT VALUES (102, 'Alice Johnson', '2015-03-10', 'Daughter');
```

### Example 3: Building and Apartment

```sql
-- Strong entity
CREATE TABLE BUILDING (
    building_id INT PRIMARY KEY,
    building_name VARCHAR(100),
    address VARCHAR(200)
);

-- Weak entity
CREATE TABLE APARTMENT (
    building_id INT NOT NULL,
    apartment_number VARCHAR(10) NOT NULL,  -- Discriminator (e.g., '101', '102')
    bedrooms INT,
    rent DECIMAL(10,2),
    
    PRIMARY KEY (building_id, apartment_number),
    
    FOREIGN KEY (building_id) REFERENCES BUILDING(building_id)
        ON DELETE CASCADE
);

-- Data
INSERT INTO BUILDING VALUES (1, 'Sunset Apartments', '123 Main St');
INSERT INTO BUILDING VALUES (2, 'Ocean View', '456 Beach Rd');

-- Building 1 apartments
INSERT INTO APARTMENT VALUES (1, '101', 2, 1200);
INSERT INTO APARTMENT VALUES (1, '102', 2, 1200);
INSERT INTO APARTMENT VALUES (1, '201', 3, 1500);

-- Building 2 apartments (apartment_number '101' again - no conflict!)
INSERT INTO APARTMENT VALUES (2, '101', 1, 1000);
```

### Example 4: University - Department - Course

**Multi-level weak entity hierarchy**

```sql
-- Strong entity
CREATE TABLE UNIVERSITY (
    university_id INT PRIMARY KEY,
    university_name VARCHAR(100)
);

-- Weak entity (depends on UNIVERSITY)
CREATE TABLE DEPARTMENT (
    university_id INT NOT NULL,
    dept_code VARCHAR(10) NOT NULL,  -- Discriminator
    dept_name VARCHAR(100),
    
    PRIMARY KEY (university_id, dept_code),
    FOREIGN KEY (university_id) REFERENCES UNIVERSITY(university_id)
        ON DELETE CASCADE
);

-- Weak entity (depends on DEPARTMENT, which depends on UNIVERSITY)
CREATE TABLE COURSE (
    university_id INT NOT NULL,
    dept_code VARCHAR(10) NOT NULL,
    course_number VARCHAR(10) NOT NULL,  -- Discriminator
    course_name VARCHAR(100),
    credits INT,
    
    PRIMARY KEY (university_id, dept_code, course_number),
    FOREIGN KEY (university_id, dept_code) 
        REFERENCES DEPARTMENT(university_id, dept_code)
        ON DELETE CASCADE
);
```

### Why Use Weak Entities?

**1. Natural Modeling:**
- Some entities naturally depend on others
- Payments only exist for loans
- Apartment numbers only make sense within a building

**2. Prevent Orphaned Records:**
- CASCADE delete ensures consistency
- When loan deleted, all its payments deleted automatically

**3. Simplify Keys:**
- No need for globally unique payment IDs
- Use simple sequential numbers within each loan

**4. Enforce Business Rules:**
- Payment cannot exist without a loan
- Dependent cannot exist without an employee

### Weak Entity Rules

1. **Must have total participation** in identifying relationship
2. **Must have one-to-many** relationship with owner (one loan → many payments)
3. **Primary key = owner's key + discriminator**
4. **Use CASCADE for referential integrity**
5. **Discriminator must be underlined** with dashes in ER diagram

---

## Advanced Concepts 

### Aggregation

**Aggregation** treats a **relationship as an entity** to participate in another relationship.

**When to use:**
- When a relationship itself needs to relate to another entity
- Modeling ternary relationships that need additional associations

**ER Diagram:**
```
         ┌─────────────────────────────────┐
         │        Aggregation              │
         │                                 │
         │  ┌─────────┐      ┌─────────┐  │
         │  │ EMPLOYEE│──◇───│ PROJECT │  │      ┌──────────┐
         │  └─────────┘ works └─────────┘  │───◇──│ MANAGER  │
         │             on                   │ manages └──────────┘
         └─────────────────────────────────┘
```

**Example Scenario:** 
- Employees work on Projects
- Managers manage which employees work on which projects

```sql
-- Entities
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE PROJECT (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100)
);

CREATE TABLE MANAGER (
    manager_id INT PRIMARY KEY,
    name VARCHAR(100)
);

-- Base relationship: Employee works on Project
CREATE TABLE WORKS_ON (
    emp_id INT,
    project_id INT,
    hours_per_week INT,
    
    PRIMARY KEY (emp_id, project_id),
    FOREIGN KEY (emp_id) REFERENCES EMPLOYEE(emp_id),
    FOREIGN KEY (project_id) REFERENCES PROJECT(project_id)
);

-- Aggregation: Manager manages the "works_on" relationship
CREATE TABLE MANAGES_WORK (
    manager_id INT,
    emp_id INT,
    project_id INT,
    assignment_date DATE,
    
    PRIMARY KEY (manager_id, emp_id, project_id),
    FOREIGN KEY (manager_id) REFERENCES MANAGER(manager_id),
    -- This foreign key references the aggregated relationship
    FOREIGN KEY (emp_id, project_id) REFERENCES WORKS_ON(emp_id, project_id)
);
```

### 8.2 Generalization and Specialization

**Generalization:** Combining multiple entity types into a higher-level entity (bottom-up)

**Specialization:** Dividing an entity type into sub-entities (top-down)

**Example: Person → Student, Employee**

```
      ┌─────────┐
      │  PERSON │  ← Superclass/Generalization
      └────┬────┘
           │
      ╱────┴────╲
     ╱           ╲
┌─────────┐   ┌──────────┐
│ STUDENT │   │ EMPLOYEE │  ← Subclasses/Specializations
└─────────┘   └──────────┘
```

**Implementation Strategies:**

#### Strategy 1: Single Table (Table per Hierarchy)

```sql
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY,
    name VARCHAR(100),
    birth_date DATE,
    person_type VARCHAR(20),  -- 'Student' or 'Employee'
    
    -- Student-specific attributes (NULL for employees)
    student_id VARCHAR(20),
    major VARCHAR(50),
    gpa DECIMAL(3,2),
    
    -- Employee-specific attributes (NULL for students)
    emp_id VARCHAR(20),
    salary DECIMAL(10,2),
    hire_date DATE
);
```

**Pros:** Simple queries, no joins needed
**Cons:** Wasted space (many NULL values)

#### Strategy 2: Table per Type (Class Table Inheritance)

```sql
-- Superclass table
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY,
    name VARCHAR(100),
    birth_date DATE
);

-- Subclass tables
CREATE TABLE STUDENT (
    person_id INT PRIMARY KEY,
    student_id VARCHAR(20),
    major VARCHAR(50),
    gpa DECIMAL(3,2),
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

CREATE TABLE EMPLOYEE (
    person_id INT PRIMARY KEY,
    emp_id VARCHAR(20),
    salary DECIMAL(10,2),
    hire_date DATE,
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);
```

**Pros:** No wasted space, clear structure
**Cons:** Requires joins to get full information

#### Strategy 3: Table per Concrete Class

```sql
-- No PERSON table, each subclass is independent
CREATE TABLE STUDENT (
    person_id INT PRIMARY KEY,
    name VARCHAR(100),        -- Repeated from Person
    birth_date DATE,          -- Repeated from Person
    student_id VARCHAR(20),
    major VARCHAR(50),
    gpa DECIMAL(3,2)
);

CREATE TABLE EMPLOYEE (
    person_id INT PRIMARY KEY,
    name VARCHAR(100),        -- Repeated from Person
    birth_date DATE,          -- Repeated from Person
    emp_id VARCHAR(20),
    salary DECIMAL(10,2),
    hire_date DATE
);
```

**Pros:** No joins needed, fast queries
**Cons:** Data duplication, hard to query all people

### 8.3 Disjoint vs Overlapping Constraints

**Disjoint:** An entity can belong to **at most one** subclass

```
      PERSON
      /    \
   (d)      \   ← (d) = disjoint
STUDENT  EMPLOYEE
```

A person is either a student OR an employee, not both.

**Overlapping:** An entity can belong to **multiple** subclasses

```
      PERSON
      /    \
   (o)      \   ← (o) = overlapping
STUDENT  EMPLOYEE
```

A person can be both a student AND an employee.

```sql
-- Overlapping implementation (allowing both)
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE STUDENT (
    person_id INT PRIMARY KEY,
    student_id VARCHAR(20),
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

CREATE TABLE EMPLOYEE (
    person_id INT PRIMARY KEY,
    emp_id VARCHAR(20),
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

-- Person 101 can exist in both STUDENT and EMPLOYEE tables
INSERT INTO PERSON VALUES (101, 'John');
INSERT INTO STUDENT VALUES (101, 'S12345');
INSERT INTO EMPLOYEE VALUES (101, 'E9876');  -- Same person, also an employee
```

### 8.4 Total vs Partial Specialization

**Total:** Every superclass entity **must belong** to at least one subclass

```
      VEHICLE
      /    \
   [total]  \
  CAR     TRUCK
```

Every vehicle must be either a car or a truck.

**Partial:** A superclass entity **may not belong** to any subclass

```
      PERSON
      /    \
  [partial] \
STUDENT  EMPLOYEE
```

Some people may be neither students nor employees.

---

## 9. ER to Relational Mapping

### Step-by-Step Mapping Process

#### Step 1: Map Strong Entity Sets

**Rule:** Each strong entity becomes a table with its simple attributes as columns.

```sql
-- ER: STUDENT(student_id, name, birth_date)
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    birth_date DATE
);
```

#### Step 2: Map Weak Entity Sets

**Rule:** Create table with owner's primary key + discriminator as composite primary key.

```sql
-- ER: LOAN → PAYMENT (weak entity)
CREATE TABLE LOAN (
    loan_number INT PRIMARY KEY,
    amount DECIMAL(10,2)
);

CREATE TABLE PAYMENT (
    loan_number INT,
    payment_number INT,
    payment_date DATE,
    
    PRIMARY KEY (loan_number, payment_number),
    FOREIGN KEY (loan_number) REFERENCES LOAN(loan_number)
);
```

#### Step 3: Map Binary 1:1 Relationships

**Option A:** Foreign key in either table (choose the one with total participation)

```sql
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE HOSTEL_ROOM (
    room_id INT PRIMARY KEY,
    room_number VARCHAR(10),
    student_id INT UNIQUE,  -- Foreign key in HOSTEL_ROOM
    
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
);
```

**Option B:** Merge both entities into one table (if both have total participation)

#### Step 4: Map Binary 1:M Relationships

**Rule:** Foreign key goes on the "many" side.

```sql
-- 1 Department : M Employees
CREATE TABLE DEPARTMENT (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100)
);

CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    dept_id INT,  -- Foreign key on "many" side
    
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id)
);
```

#### Step 5: Map Binary M:M Relationships

**Rule:** Create a new junction table with foreign keys to both entities.

```sql
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100)
);

-- Junction table for M:M relationship
CREATE TABLE ENROLLS_IN (
    student_id INT,
    course_id VARCHAR(10),
    enrollment_date DATE,
    grade CHAR(1),
    
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);
```

#### Step 6: Map Multivalued Attributes

**Rule:** Create a separate table with entity's primary key + attribute.

```sql
-- ER: STUDENT(student_id, name, phone_numbers)
--     phone_numbers is multivalued

CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE STUDENT_PHONE (
    student_id INT,
    phone_number VARCHAR(15),
    
    PRIMARY KEY (student_id, phone_number),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
);
```

#### Step 7: Map Composite Attributes

**Rule:** Create columns only for leaf-level attributes.

```sql
-- ER: PERSON(person_id, name{first, middle, last}, address{street, city, state, zip})

CREATE TABLE PERSON (
    person_id INT PRIMARY KEY,
    -- Don't create 'name' column
    first_name VARCHAR(50),
    middle_name VARCHAR(50),
    last_name VARCHAR(50),
    -- Don't create 'address' column
    street VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    zip VARCHAR(10)
);
```

#### Step 8: Map Ternary Relationships

**Rule:** Create table with foreign keys to all three participating entities.

```sql
-- Instructor teaches Student in Course
CREATE TABLE INSTRUCTOR (
    instructor_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100)
);

CREATE TABLE TEACHES (
    instructor_id INT,
    student_id INT,
    course_id VARCHAR(10),
    semester VARCHAR(20),
    
    PRIMARY KEY (instructor_id, student_id, course_id, semester),
    FOREIGN KEY (instructor_id) REFERENCES INSTRUCTOR(instructor_id),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);
```

---

## 10. Complete Examples {#examples}

### Example 1: University Database

**Requirements:**
- Students enroll in courses
- Instructors teach courses
- Departments offer courses
- Students have multiple phone numbers
- Track enrollment grades

**ER Diagram:**
```
┌────────────┐     M        ◇        M    ┌─────────┐
│  STUDENT   │────────enrolls_in─────────│ COURSE  │
└────────────┘    (grade, semester)       └─────────┘
     │ ◎◎                                      │
  phone_nos                                    │ M
                                               │
                                          ┌────▼─────┐
                                          │INSTRUCTOR│
                                          └──────────┘
                                               │ M
                                               │
                                          ┌────▼──────┐
                                          │DEPARTMENT │
                                          └───────────┘
```

**Implementation:**

```sql
-- Strong entities
CREATE TABLE DEPARTMENT (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100) UNIQUE NOT NULL,
    building VARCHAR(50),
    budget DECIMAL(12,2)
);

CREATE TABLE INSTRUCTOR (
    instructor_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    office VARCHAR(20),
    salary DECIMAL(10,2),
    dept_id INT,
    
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id)
);

CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    birth_date DATE,
    major_dept_id INT,
    
    FOREIGN KEY (major_dept_id) REFERENCES DEPARTMENT(dept_id)
);

CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    credits INT,
    dept_id INT,
    instructor_id INT,
    
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id),
    FOREIGN KEY (instructor_id) REFERENCES INSTRUCTOR(instructor_id)
);

-- Multivalued attribute
CREATE TABLE STUDENT_PHONE (
    student_id INT,
    phone_number VARCHAR(15),
    phone_type ENUM('mobile', 'home', 'emergency'),
    
    PRIMARY KEY (student_id, phone_number),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
        ON DELETE CASCADE
);

-- M:M relationship
CREATE TABLE ENROLLMENT (
    student_id INT,
    course_id VARCHAR(10),
    semester VARCHAR(20),
    year INT,
    grade CHAR(1),
    
    PRIMARY KEY (student_id, course_id, semester, year),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id),
    
    CHECK (grade IN ('A', 'B', 'C', 'D', 'F'))
);

-- Sample data
INSERT INTO DEPARTMENT VALUES (1, 'Computer Science', 'Tech Building', 500000);
INSERT INTO DEPARTMENT VALUES (2, 'Mathematics', 'Science Hall', 300000);

INSERT INTO INSTRUCTOR VALUES (101, 'Dr. Smith', 'smith@univ.edu', 'TB-201', 85000, 1);
INSERT INTO INSTRUCTOR VALUES (102, 'Dr. Johnson', 'johnson@univ.edu', 'SH-101', 78000, 2);

INSERT INTO STUDENT VALUES (1001, 'John', 'Doe', 'john@student.edu', '2004-05-15', 1);
INSERT INTO STUDENT VALUES (1002, 'Mary', 'Smith', 'mary@student.edu', '2003-08-22', 1);

INSERT INTO STUDENT_PHONE VALUES (1001, '555-1234', 'mobile');
INSERT INTO STUDENT_PHONE VALUES (1001, '555-5678', 'home');

INSERT INTO COURSE VALUES ('CS101', 'Introduction to Programming', 3, 1, 101);
INSERT INTO COURSE VALUES ('CS201', 'Data Structures', 4, 1, 101);
INSERT INTO COURSE VALUES ('MATH101', 'Calculus I', 4, 2, 102);

INSERT INTO ENROLLMENT VALUES (1001, 'CS101', 'Fall', 2026, 'A');
INSERT INTO ENROLLMENT VALUES (1001, 'MATH101', 'Fall', 2026, 'B');
INSERT INTO ENROLLMENT VALUES (1002, 'CS101', 'Fall', 2026, 'A');
```

### Example 2: Hospital Management System

**Requirements:**
- Doctors treat patients
- Patients are admitted to rooms
- Doctors work in departments
- Track appointment history

```sql
-- Strong entities
CREATE TABLE DEPARTMENT (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100)
);

CREATE TABLE DOCTOR (
    doctor_id INT PRIMARY KEY,
    name VARCHAR(100),
    specialization VARCHAR(50),
    phone VARCHAR(15),
    dept_id INT,
    
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id)
);

CREATE TABLE PATIENT (
    patient_id INT PRIMARY KEY,
    name VARCHAR(100),
    birth_date DATE,
    gender CHAR(1),
    address VARCHAR(200),
    phone VARCHAR(15),
    blood_group VARCHAR(5)
);

CREATE TABLE ROOM (
    room_id INT PRIMARY KEY,
    room_number VARCHAR(10),
    room_type VARCHAR(20),
    charges_per_day DECIMAL(8,2)
);

-- Relationships
CREATE TABLE APPOINTMENT (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id INT,
    patient_id INT,
    appointment_date DATE,
    appointment_time TIME,
    diagnosis VARCHAR(500),
    prescription TEXT,
    
    FOREIGN KEY (doctor_id) REFERENCES DOCTOR(doctor_id),
    FOREIGN KEY (patient_id) REFERENCES PATIENT(patient_id)
);

-- 1:1 relationship (patient admitted to room)
CREATE TABLE ADMISSION (
    admission_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    room_id INT UNIQUE,  -- Each room has at most one patient
    admission_date DATE,
    discharge_date DATE,
    
    FOREIGN KEY (patient_id) REFERENCES PATIENT(patient_id),
    FOREIGN KEY (room_id) REFERENCES ROOM(room_id)
);
```

### Example 3: E-Commerce System

```sql
-- Customer
CREATE TABLE CUSTOMER (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    registration_date DATE
);

-- Customer addresses (multivalued attribute)
CREATE TABLE CUSTOMER_ADDRESS (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    address_type ENUM('billing', 'shipping'),
    street VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    zip VARCHAR(10),
    country VARCHAR(50),
    
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id)
        ON DELETE CASCADE
);

-- Product
CREATE TABLE PRODUCT (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(200),
    description TEXT,
    price DECIMAL(10,2),
    stock_quantity INT,
    category VARCHAR(50)
);

-- Order (strong entity)
CREATE TABLE `ORDER` (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date DATETIME,
    total_amount DECIMAL(12,2),
    status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled'),
    
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id)
);

-- Order items (M:M between Order and Product)
CREATE TABLE ORDER_ITEM (
    order_id INT,
    product_id INT,
    quantity INT,
    unit_price DECIMAL(10,2),
    subtotal DECIMAL(12,2),
    
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES `ORDER`(order_id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
);

-- Payment (weak entity depending on ORDER)
CREATE TABLE PAYMENT (
    order_id INT,
    payment_number INT,
    payment_date DATETIME,
    amount DECIMAL(10,2),
    payment_method ENUM('credit_card', 'debit_card', 'paypal', 'cash'),
    
    PRIMARY KEY (order_id, payment_number),
    FOREIGN KEY (order_id) REFERENCES `ORDER`(order_id)
        ON DELETE CASCADE
);
```

---

## Complete ER Notation Reference

| Element                      | Symbol                                                                    | MySQL Implementation          |
| ---------------------------- | ------------------------------------------------------------------------- | ----------------------------- |
| **Strong Entity**            | `┌───┐` Rectangle                                                         | `CREATE TABLE`                |
| **Weak Entity**              | `╔═══╗` Double Rectangle                                                  | Composite PK with FK          |
| **Attribute**                | `○` Ellipse                                                               | Column                        |
| **Key Attribute**            | <u>○</u> Underlined                                                       | `PRIMARY KEY`                 |
| **Composite Attribute**      | `○` with branches                                                         | Multiple columns (leaf level) |
| **Multivalued Attribute**    | `◎◎` Double Ellipse                                                       | Separate table                |
| **Derived Attribute**        | `(○)` Dashed Ellipse                                                      | Calculated, not stored        |
| **Relationship**             | `◇` Diamond                                                               | Junction table or FK          |
| **Identifying Relationship** | `◇◇` Double Diamond                                                       | FK with CASCADE               |
| **Total Participation**      | `═══` Double Line                                                         | `NOT NULL` FK                 |
| **Partial Participation**    | `───` Single Line                                                         | Nullable FK                   |
| **Cardinality 1:1**          | `1───◇───1`                                                               | UNIQUE FK                     |
| **Cardinality 1:M**          | `1───◇───M`                                                               | FK on "many" side             |
| **Cardinality M:M**          | `M───◇───M`                                                               | Junction table                |
| **Discriminator**            | Dashed underline, while the primary keys on the other hand are underlined | Part of composite PK          |
| **Generalization**           | Triangle `△`                                                              | Inheritance tables            |

---

## Best Practices

### 1. Naming Conventions
- Use **singular** nouns for entity names: `STUDENT` not `STUDENTS`
- Use **descriptive** relationship names: `enrolls_in`, `works_for`
- Use **lowercase_underscore** for MySQL: `student_id`, `course_name`

### 2. Key Selection
- Choose **stable** attributes for primary keys (won't change)
- Prefer **simple** keys over composite when possible
- Use **surrogate keys** (auto-increment) for complex scenarios

### 3. Normalization
- Avoid **redundant** data
- Use **separate tables** for multivalued attributes
- Don't store **derived** attributes

### 4. Referential Integrity
- Always define **foreign key constraints**
- Use appropriate **CASCADE** actions
- Consider **NULL** vs `NOT NULL` carefully

### 5. Documentation
- Comment complex relationships
- Document business rules
- Maintain ER diagrams alongside code

---

## Common Mistakes to Avoid

❌ **Storing derived attributes**
```sql
-- DON'T
CREATE TABLE STUDENT (
    birth_date DATE,
    age INT  -- Derived - will become outdated
);
```

✅ **Calculate derived attributes**
```sql
-- DO
SELECT TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) AS age
FROM STUDENT;
```

❌ **Not using foreign keys**
```sql
-- DON'T
CREATE TABLE EMPLOYEE (
    dept_id INT  -- No foreign key constraint
);
```

✅ **Always use foreign keys**
```sql
-- DO
CREATE TABLE EMPLOYEE (
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id)
);
```

❌ **Storing multivalued attributes in one column**
```sql
-- DON'T
CREATE TABLE STUDENT (
    phone_numbers VARCHAR(200)  -- '555-1234, 555-5678, 555-9012'
);
```

✅ **Separate table for multivalued attributes**
```sql
-- DO
CREATE TABLE STUDENT_PHONE (
    student_id INT,
    phone_number VARCHAR(15),
    PRIMARY KEY (student_id, phone_number)
);
```

---

# Enhanced Entity-Relationship (EER) Model

## Introduction to EER Model 

### What is the EER Model?

The **Enhanced Entity-Relationship (EER) Model** extends the basic ER model by adding:
- **Subclasses and Superclasses** (specialization/generalization)
- **Attribute Inheritance**
- **Constraints** on relationships between superclasses and subclasses
- **Union Types (Categories)**

### Why Do We Need EER?

The standard ER model couldn't represent:
- **Hierarchical classifications** (e.g., Employees → Managers, Engineers, Secretaries)
- **Shared properties** across entity types
- **Complex real-world scenarios** requiring object-oriented concepts

**Real-World Example:**

In a company database:
- All employees share common attributes (name, SSN, birth_date)
- But **Managers** have unique attributes (bonus, dept_managed)
- **Engineers** have unique attributes (engineering_type, certifications)
- **Secretaries** have unique attributes (typing_speed, office_hours)

The EER model allows us to model this hierarchy efficiently without duplicating the common attributes.

---

## Superclasses and Subclasses {#superclass-subclass}

### The IS-A Relationship

A **subclass** IS-A specialized version of a **superclass**.

**Definition:**
- **Superclass (Supertype):** A general entity type
- **Subclass (Subtype):** A specialized entity type that inherits from the superclass

**Examples:**
- `SECRETARY` IS-A `EMPLOYEE`
- `ENGINEER` IS-A `EMPLOYEE`
- `MANAGER` IS-A `EMPLOYEE`
- `CAR` IS-A `VEHICLE`
- `CHECKING_ACCOUNT` IS-A `ACCOUNT`

### EER Diagram Notation

```
                ┌─────────────┐
                │  EMPLOYEE   │  ← Superclass
                │ (SSN, Name) │
                └──────┬──────┘
                       │
                       ○  ← Circle (specialization symbol)
                      ╱│╲
                     ╱ │ ╲
                    ╱  │  ╲
        ┌──────────┐ ┌┴────────┐ ┌──────────┐
        │SECRETARY │ │ENGINEER │ │TECHNICIAN│  ← Subclasses
        └──────────┘ └─────────┘ └──────────┘
```

**Symbol Breakdown:**

| Symbol               | Meaning                                 |
| -------------------- | --------------------------------------- |
| **Rectangle**        | Entity (superclass or subclass)         |
| **Circle ○**         | Specialization/Generalization connector |
| **Line from circle** | Connects superclass to subclasses       |
| **`d` in circle**    | Disjoint constraint                     |
| **`o` in circle**    | Overlapping constraint                  |
| **Double line**      | Total participation (completeness)      |
| **Single line**      | Partial participation                   |

### Existence Dependency

**Critical Rule:** An entity **cannot exist only in a subclass**; it must also exist in the superclass.

**Example:**
- You cannot be a `SECRETARY` without first being an `EMPLOYEE`
- Every secretary has all employee attributes (SSN, name, birth_date) PLUS secretary-specific attributes (typing_speed)

---

## 3. Specialization (Top-Down)

### Definition

**Specialization** is the process of defining **subclasses** from a **superclass** based on distinguishing characteristics.

**Direction:** Top → Down (from general to specific)

![[Pasted image 20260313005740.png]]

### Process
1. Start with a general superclass
2. Identify distinguishing characteristics
3. Define subclasses based on these characteristics
4. Add subclass-specific attributes

### Example 1: Company Employee Database

**Superclass:** `EMPLOYEE`
- Common attributes: `SSN`, `Name`, `Birth_date`, `Address`, `Salary`

**Specialization based on job type:**

```
                    ┌─────────────────────────┐
                    │       EMPLOYEE          │
                    │ SSN, Name, Birth_date   │
                    │ Address, Salary         │
                    └───────────┬─────────────┘
                                │
                                ○ d  ← Disjoint
                               ╱│╲
                              ╱ │ ╲
                             ╱  │  ╲
              ┌────────────┐  ┌┴──────────┐  ┌───────────┐
              │ SECRETARY  │  │ ENGINEER  │  │ TECHNICIAN│
              │Typing_speed│  │Eng_type   │  │TGrade     │
              │Office_hours│  │Certs      │  │           │
              └────────────┘  └───────────┘  └───────────┘
```

**MySQL Implementation:**

```sql
-- Superclass
CREATE TABLE EMPLOYEE (
    ssn VARCHAR(11) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE,
    address VARCHAR(200),
    salary DECIMAL(10,2),
    job_type VARCHAR(20)  -- Attribute to determine subclass
);

-- Subclass: Secretary
CREATE TABLE SECRETARY (
    ssn VARCHAR(11) PRIMARY KEY,
    typing_speed INT,  -- Words per minute
    office_hours VARCHAR(50),
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Subclass: Engineer
CREATE TABLE ENGINEER (
    ssn VARCHAR(11) PRIMARY KEY,
    engineering_type VARCHAR(50),  -- 'Software', 'Mechanical', 'Electrical'
    certifications TEXT,
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
        ON DELETE CASCADE
);

-- Subclass: Technician
CREATE TABLE TECHNICIAN (
    ssn VARCHAR(11) PRIMARY KEY,
    tgrade INT,  -- Technical grade level
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
        ON DELETE CASCADE
);

-- Sample data
INSERT INTO EMPLOYEE VALUES 
    ('123-45-6789', 'Alice Johnson', '1990-05-15', '123 Main St', 45000, 'Secretary'),
    ('234-56-7890', 'Bob Smith', '1985-08-22', '456 Oak Ave', 75000, 'Engineer'),
    ('345-67-8901', 'Carol White', '1992-11-30', '789 Elm St', 55000, 'Technician');

INSERT INTO SECRETARY VALUES ('123-45-6789', 80, '9AM-5PM');
INSERT INTO ENGINEER VALUES ('234-56-7890', 'Software', 'AWS Certified, Oracle DBA');
INSERT INTO TECHNICIAN VALUES ('345-67-8901', 3);
```

### Example 2: University Student Specialization

```
                    ┌─────────────────────────┐
                    │       STUDENT           │
                    │ Student_ID, Name        │
                    │ Email, DOB              │
                    └───────────┬─────────────┘
                                │
                                ○ d  ← Disjoint (can't be both)
                               ╱│╲
                              ╱ │ ╲
                             ╱  │  ╲
              ┌─────────────┐  ┌┴──────────────┐
              │UNDERGRADUATE│  │   GRADUATE    │
              │Major        │  │Thesis_topic   │
              │Class_year   │  │Advisor_name   │
              └─────────────┘  └───────────────┘
```

```sql
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    dob DATE,
    student_type VARCHAR(20)  -- 'Undergraduate' or 'Graduate'
);

CREATE TABLE UNDERGRADUATE (
    student_id INT PRIMARY KEY,
    major VARCHAR(50),
    class_year INT,  -- 1, 2, 3, 4
    
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
        ON DELETE CASCADE
);

CREATE TABLE GRADUATE (
    student_id INT PRIMARY KEY,
    thesis_topic VARCHAR(200),
    advisor_name VARCHAR(100),
    degree_type ENUM('Masters', 'PhD'),
    
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
        ON DELETE CASCADE
);
```

### Example 3: Bank Account Specialization

```
                    ┌─────────────────────────┐
                    │       ACCOUNT           │
                    │ Account_no, Balance     │
                    │ Date_opened             │
                    └───────────┬─────────────┘
                                │
                                ○ o  ← Overlapping (can have both)
                               ╱│╲
                              ╱ │ ╲
                             ╱  │  ╲
              ┌─────────────┐  ┌┴──────────────┐
              │  SAVINGS    │  │   CHECKING    │
              │Interest_rate│  │Overdraft_limit│
              │Min_balance  │  │Fee_per_check  │
              └─────────────┘  └───────────────┘
```

```sql
CREATE TABLE ACCOUNT (
    account_no VARCHAR(20) PRIMARY KEY,
    balance DECIMAL(12,2),
    date_opened DATE,
    customer_id INT
);

CREATE TABLE SAVINGS (
    account_no VARCHAR(20) PRIMARY KEY,
    interest_rate DECIMAL(5,2),  -- e.g., 3.5 for 3.5%
    min_balance DECIMAL(10,2),
    
    FOREIGN KEY (account_no) REFERENCES ACCOUNT(account_no)
);

CREATE TABLE CHECKING (
    account_no VARCHAR(20) PRIMARY KEY,
    overdraft_limit DECIMAL(10,2),
    fee_per_check DECIMAL(5,2),
    
    FOREIGN KEY (account_no) REFERENCES ACCOUNT(account_no)
);

-- Example: Account that is BOTH savings and checking
INSERT INTO ACCOUNT VALUES ('ACC001', 5000.00, '2025-01-15', 101);
INSERT INTO SAVINGS VALUES ('ACC001', 3.5, 1000.00);
INSERT INTO CHECKING VALUES ('ACC001', 500.00, 0.25);
```

---

## 4. Generalization (Bottom-Up) 

### Definition

**Generalization** is the **reverse** of specialization. It's the process of combining multiple entity types with common features into a single **superclass**.

**Direction:** Bottom → Up (from specific to general)

### Process

1. Identify multiple entity types with common attributes
2. Extract common attributes
3. Create a superclass with these common attributes
4. Make original entities subclasses

![[Pasted image 20260313005817.png]]

### Example 1: Vehicle Generalization

**Before Generalization:**

```
┌─────────┐       ┌─────────┐       ┌─────────┐
│   CAR   │       │  TRUCK  │       │   BUS   │
│ VIN     │       │ VIN     │       │ VIN     │
│ Make    │       │ Make    │       │ Make    │
│ Model   │       │ Model   │       │ Model   │
│ NumDoors│       │ Tonnage │       │ Capacity│
└─────────┘       └─────────┘       └─────────┘
```

Common attributes: `VIN`, `Make`, `Model`, `Year`, `Color`

**After Generalization:**

```
                    ┌─────────────────────────┐
                    │       VEHICLE           │  ← Generalized superclass
                    │ VIN, Make, Model        │
                    │ Year, Color             │
                    └───────────┬─────────────┘
                                │
                                ○ d
                               ╱│╲
                              ╱ │ ╲
                             ╱  │  ╲
              ┌───────────┐  ┌┴────────┐  ┌─────────┐
              │    CAR    │  │  TRUCK  │  │   BUS   │
              │ NumDoors  │  │ Tonnage │  │Capacity │
              │ BodyStyle │  │BedLength│  │RouteNum │
              └───────────┘  └─────────┘  └─────────┘
```

**MySQL Implementation:**

```sql
-- Generalized superclass
CREATE TABLE VEHICLE (
    vin VARCHAR(17) PRIMARY KEY,
    make VARCHAR(50),
    model VARCHAR(50),
    year INT,
    color VARCHAR(30),
    vehicle_type VARCHAR(20)  -- 'Car', 'Truck', 'Bus'
);

-- Subclass: Car
CREATE TABLE CAR (
    vin VARCHAR(17) PRIMARY KEY,
    num_doors INT,
    body_style VARCHAR(30),  -- 'Sedan', 'Coupe', 'Hatchback', 'SUV'
    
    FOREIGN KEY (vin) REFERENCES VEHICLE(vin)
        ON DELETE CASCADE
);

-- Subclass: Truck
CREATE TABLE TRUCK (
    vin VARCHAR(17) PRIMARY KEY,
    tonnage DECIMAL(8,2),  -- Cargo capacity in tons
    bed_length DECIMAL(5,2),  -- In feet
    four_wheel_drive BOOLEAN,
    
    FOREIGN KEY (vin) REFERENCES VEHICLE(vin)
        ON DELETE CASCADE
);

-- Subclass: Bus
CREATE TABLE BUS (
    vin VARCHAR(17) PRIMARY KEY,
    passenger_capacity INT,
    route_number VARCHAR(20),
    wheelchair_accessible BOOLEAN,
    
    FOREIGN KEY (vin) REFERENCES VEHICLE(vin)
        ON DELETE CASCADE
);

-- Sample data
INSERT INTO VEHICLE VALUES 
    ('1HGBH41JXMN109186', 'Honda', 'Accord', 2022, 'Silver', 'Car'),
    ('1FTFW1ET5DFC10312', 'Ford', 'F-150', 2021, 'Blue', 'Truck'),
    ('5TDKK3DC2ES123456', 'Toyota', 'School Bus', 2020, 'Yellow', 'Bus');

INSERT INTO CAR VALUES ('1HGBH41JXMN109186', 4, 'Sedan');
INSERT INTO TRUCK VALUES ('1FTFW1ET5DFC10312', 2.5, 6.5, TRUE);
INSERT INTO BUS VALUES ('5TDKK3DC2ES123456', 45, 'Route 12', TRUE);
```

### Example 2: Person Generalization

**Scenario:** University database has separate entities for `FACULTY`, `STAFF`, and `STUDENT`. All have common attributes.

**After Generalization:**

```
                    ┌─────────────────────────┐
                    │       PERSON            │
                    │ Person_ID, Name         │
                    │ DOB, Address, Phone     │
                    └───────────┬─────────────┘
                                │
                                ○ o  ← Overlapping
                               ╱│╲
                              ╱ │ ╲
                             ╱  │  ╲
              ┌──────────┐  ┌┴────────┐  ┌─────────┐
              │ FACULTY  │  │  STAFF  │  │ STUDENT │
              │ Rank     │  │Position │  │Major    │
              │ Office   │  │Dept     │  │GPA      │
              └──────────┘  └─────────┘  └─────────┘
```

```sql
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    dob DATE,
    address VARCHAR(200),
    phone VARCHAR(15),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE FACULTY (
    person_id INT PRIMARY KEY,
    rank VARCHAR(50),  -- 'Assistant Professor', 'Associate Professor', 'Professor'
    office VARCHAR(20),
    research_area VARCHAR(100),
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

CREATE TABLE STAFF (
    person_id INT PRIMARY KEY,
    position VARCHAR(50),
    department VARCHAR(50),
    hire_date DATE,
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

CREATE TABLE STUDENT (
    person_id INT PRIMARY KEY,
    major VARCHAR(50),
    gpa DECIMAL(3,2),
    enrollment_date DATE,
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

-- Example: Person who is both Faculty and Student (PhD student teaching)
INSERT INTO PERSON VALUES (NULL, 'Dr. John Smith', '1985-03-15', '123 College Ave', '555-1234', 'john@univ.edu');
SET @person_id = LAST_INSERT_ID();

INSERT INTO FACULTY VALUES (@person_id, 'Assistant Professor', 'SB-301', 'Database Systems');
INSERT INTO STUDENT VALUES (@person_id, 'Computer Science PhD', 3.95, '2020-09-01');
```

---

## Constraints in EER 

EER models use **three types of constraints** to define the rules governing superclass/subclass relationships.

### Disjointness Constraint

**Question:** Can an entity belong to **multiple subclasses** at the same time?

#### Disjoint (`d`)

An entity can be a member of **at most ONE** subclass.

**Symbol:** `d` inside the circle

**Real-World Examples:**

1. **Student Classification:**
   ```
   STUDENT → {UNDERGRADUATE, GRADUATE}  [Disjoint]
   ```
   A student cannot be both undergraduate AND graduate simultaneously.

2. **Employee Job Type:**
   ```
   EMPLOYEE → {SECRETARY, ENGINEER, TECHNICIAN}  [Disjoint]
   ```
   An employee has ONE job classification.

**MySQL Enforcement:**

```mysql
-- Method 1: Use CHECK constraint (MySQL 8.0+)
CREATE TABLE EMPLOYEE (
    ssn VARCHAR(11) PRIMARY KEY,
    name VARCHAR(100),
    job_type VARCHAR(20),
    
    CHECK (job_type IN ('Secretary', 'Engineer', 'Technician'))
);

-- Method 2: Use triggers to enforce disjointness
DELIMITER $$

CREATE TRIGGER check_employee_disjoint_insert
BEFORE INSERT ON SECRETARY
FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM ENGINEER WHERE ssn = NEW.ssn) 
       OR EXISTS (SELECT 1 FROM TECHNICIAN WHERE ssn = NEW.ssn) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Employee cannot be in multiple job categories';
    END IF;
END$$

DELIMITER ;
```

#### Overlapping (`o`)

An entity can belong to **multiple subclasses** simultaneously.

**Symbol:** `o` inside the circle

**Real-World Examples:**

1. **Person Roles:**
   ```
   PERSON → {EMPLOYEE, ALUMNUS, STUDENT}  [Overlapping]
   ```
   A person can be an employee (professor) AND an alumnus (graduated from there) simultaneously.

2. **Parts Classification:**
   ```
   PART → {MANUFACTURED_PART, PURCHASED_PART}  [Overlapping]
   ```
   A company might manufacture a part in-house AND purchase it from suppliers as backup.

3. **Bank Account:**
   ```
   ACCOUNT → {SAVINGS, CHECKING}  [Overlapping]
   ```
   An account can be BOTH savings (earns interest) and checking (has checks).

**MySQL Implementation:**

```mysql
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE EMPLOYEE (
    person_id INT PRIMARY KEY,
    employee_id VARCHAR(20),
    hire_date DATE,
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

CREATE TABLE ALUMNUS (
    person_id INT PRIMARY KEY,
    graduation_year INT,
    degree VARCHAR(50),
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

CREATE TABLE STUDENT (
    person_id INT PRIMARY KEY,
    student_id VARCHAR(20),
    major VARCHAR(50),
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

-- Example: Person who is BOTH employee and alumnus
INSERT INTO PERSON VALUES (101, 'Dr. Jane Smith');
INSERT INTO EMPLOYEE VALUES (101, 'E12345', '2015-08-15');
INSERT INTO ALUMNUS VALUES (101, 2010, 'PhD Computer Science');
-- Same person (101) exists in both EMPLOYEE and ALUMNUS subclasses
```

#### But then what would be difference between Overlapping Specialization and Generalization EER?
NO. Overlapping is a **constraint type**, not a direction indicator.

**The direction (specialization vs generalization) is about the DESIGN PROCESS, not the constraint.**

The overlapping constraint describes **membership rules**, not the **design process**. Whether you designed it top-down (specialization) or bottom-up (generalization), you can end up with the same overlapping constraint. The final EER diagram and SQL implementation are identical regardless of which approach you used.

### 5.2 Completeness Constraint (Participation)

**Question:** Must **every entity** in the superclass belong to **at least one** subclass?

#### Total Participation (Mandatory)

**Every** entity in the superclass **must** belong to at least one subclass.

**Symbol:** Double line from superclass to circle `═══`

**Real-World Examples:**

1. **Hospital Patients:**
   ```
   PATIENT → {OUTPATIENT, RESIDENT_PATIENT}  [Total]
   ```
   Every patient MUST be classified as either outpatient OR resident.

2. **Vehicle Registration:**
   ```
   VEHICLE → {CAR, TRUCK, MOTORCYCLE, BUS}  [Total]
   ```
   Every registered vehicle must fall into one of these categories.

**EER Diagram:**

```
                    ┌─────────────────────┐
                    │      PATIENT        │
                    └══════════┬══════════┘  ← Double line
                               │
                               ○ d  ← Total, Disjoint
                              ╱│╲
                             ╱ │ ╲
              ┌────────────┐  ┌┴──────────────┐
              │ OUTPATIENT │  │RESIDENT_PATIENT│
              └────────────┘  └────────────────┘
```

**MySQL Enforcement:**

```sql
CREATE TABLE PATIENT (
    patient_id INT PRIMARY KEY,
    name VARCHAR(100),
    patient_type VARCHAR(20) NOT NULL,  -- Force classification
    
    CHECK (patient_type IN ('Outpatient', 'Resident'))
);

-- Trigger to ensure total participation
DELIMITER $$

CREATE TRIGGER enforce_total_participation
AFTER INSERT ON PATIENT
FOR EACH ROW
BEGIN
    DECLARE subclass_count INT;
    
    SELECT (
        (SELECT COUNT(*) FROM OUTPATIENT WHERE patient_id = NEW.patient_id) +
        (SELECT COUNT(*) FROM RESIDENT_PATIENT WHERE patient_id = NEW.patient_id)
    ) INTO subclass_count;
    
    IF subclass_count = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Patient must belong to at least one subclass';
    END IF;
END$$

DELIMITER ;
```

#### Partial Participation (Optional)

Some entities in the superclass **may NOT** belong to any subclass.

**Symbol:** Single line from superclass to circle `───`

**Real-World Examples:**

1. **Company Employees:**
   ```
   EMPLOYEE → {MANAGER, ENGINEER}  [Partial]
   ```
   Not all employees are managers or engineers (janitors, HR, security exist as just employees).

2. **University Courses:**
   ```
   COURSE → {ONLINE_COURSE, LAB_COURSE}  [Partial]
   ```
   Some courses are regular lecture courses (neither online nor lab).

**EER Diagram:**

```
                    ┌─────────────────────┐
                    │      EMPLOYEE       │
                    └───────────┬─────────┘  ← Single line
                                │
                                ○ d  ← Partial, Disjoint
                               ╱│╲
                              ╱ │ ╲
              ┌────────────┐  ┌┴────────┐
              │  MANAGER   │  │ ENGINEER│
              └────────────┘  └─────────┘
```

**MySQL Implementation:**

```sql
CREATE TABLE EMPLOYEE (
    ssn VARCHAR(11) PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2)
    -- No mandatory job_type field
);

CREATE TABLE MANAGER (
    ssn VARCHAR(11) PRIMARY KEY,
    bonus DECIMAL(10,2),
    dept_managed VARCHAR(50),
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
);

CREATE TABLE ENGINEER (
    ssn VARCHAR(11) PRIMARY KEY,
    engineering_type VARCHAR(50),
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
);

-- Valid: Employee who is neither manager nor engineer
INSERT INTO EMPLOYEE VALUES ('111-22-3333', 'Mike Johnson', 40000);
-- No corresponding entry in MANAGER or ENGINEER tables
```

![[Pasted image 20260313010515.png]]
![[Pasted image 20260313011426.png]]

### [[So, is Generalization always have to be total participation and can never be partial participation?]]

### 5.3 Membership Constraint (Defining Predicates)

**Question:** **How** is membership in a subclass determined?

#### Attribute-Defined (Condition-Defined)

Membership is **automatically determined** by the value of a specific attribute.

**Notation:** Attribute name shown next to the circle

**Real-World Examples:**

1. **Bank Accounts by Type:**
   ```
   ACCOUNT → {SAVINGS, CHECKING}  [Based on Account_Type]
   ```
   If `Account_Type = 'Savings'` → goes to SAVINGS subclass
   If `Account_Type = 'Checking'` → goes to CHECKING subclass

2. **Students by Level:**
   ```
   STUDENT → {FRESHMAN, SOPHOMORE, JUNIOR, SENIOR}  [Based on Class_Year]
   ```

**EER Diagram:**

```
                    ┌─────────────────────┐
                    │      ACCOUNT        │
                    │ Account_Type        │  ← Defining attribute
                    └───────────┬─────────┘
                                │
                        Account_Type  ← Label on line
                                ○ d
                               ╱│╲
              ┌──────────┐    ┌┴──────────┐
              │ SAVINGS  │    │ CHECKING  │
              └──────────┘    └───────────┘
```

**MySQL Implementation:**

```sql
CREATE TABLE ACCOUNT (
    account_no VARCHAR(20) PRIMARY KEY,
    balance DECIMAL(12,2),
    account_type VARCHAR(20) NOT NULL,  -- Defining attribute
    
    CHECK (account_type IN ('Savings', 'Checking'))
);

-- Use views to represent subclasses
CREATE VIEW SAVINGS AS
SELECT * FROM ACCOUNT WHERE account_type = 'Savings';

CREATE VIEW CHECKING AS
SELECT * FROM ACCOUNT WHERE account_type = 'Checking';

-- Or use tables with automatic insertion
CREATE TABLE SAVINGS (
    account_no VARCHAR(20) PRIMARY KEY,
    interest_rate DECIMAL(5,2),
    
    FOREIGN KEY (account_no) REFERENCES ACCOUNT(account_no)
);

-- Trigger to auto-insert based on account_type
DELIMITER $$

CREATE TRIGGER auto_classify_account
AFTER INSERT ON ACCOUNT
FOR EACH ROW
BEGIN
    IF NEW.account_type = 'Savings' THEN
        INSERT INTO SAVINGS (account_no, interest_rate) 
        VALUES (NEW.account_no, 2.5);  -- Default interest rate
    END IF;
END$$

DELIMITER ;
```

#### User-Defined

Membership is **manually specified** by a user or application; no automatic rule exists.

**Real-World Examples:**

1. **VIP Customers:**
   ```
   CUSTOMER → {VIP_CUSTOMER}  [User-Defined]
   ```
   Store manager manually designates certain customers as VIP based on loyalty, spending, or personal relationship.

2. **Featured Products:**
   ```
   PRODUCT → {FEATURED_PRODUCT}  [User-Defined]
   ```
   Marketing team manually selects which products to feature.

**MySQL Implementation:**

```sql
CREATE TABLE CUSTOMER (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE VIP_CUSTOMER (
    customer_id INT PRIMARY KEY,
    vip_since DATE,
    assigned_by VARCHAR(100),  -- Who made them VIP
    notes TEXT,
    
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id)
);

-- Manager manually adds VIP status
INSERT INTO CUSTOMER VALUES (101, 'Alice Brown', 'alice@email.com');
-- Later, manager decides to make her VIP
INSERT INTO VIP_CUSTOMER VALUES (101, '2026-03-11', 'Manager John', 'Long-time loyal customer');
```

### Constraint Combination Examples

#### Example 1: Disjoint + Total

**Scenario:** Every vehicle must be exactly one type.

```
                    VEHICLE
                   (VIN, Make)
                       ═══  ← Total (double line)
                        ○ d  ← Disjoint
                       ╱│╲
                 CAR  TRUCK  MOTORCYCLE
```

**Interpretation:** Every vehicle MUST be classified, and can be ONLY one type.

```sql
CREATE TABLE VEHICLE (
    vin VARCHAR(17) PRIMARY KEY,
    make VARCHAR(50),
    vehicle_type VARCHAR(20) NOT NULL,  -- Total: NOT NULL
    
    CHECK (vehicle_type IN ('Car', 'Truck', 'Motorcycle'))  -- Disjoint
);
```

#### Example 2: Overlapping + Partial

**Scenario:** Employees may have multiple roles, but not required.

```
                    EMPLOYEE
                   (SSN, Name)
                       ───  ← Partial (single line)
                        ○ o  ← Overlapping
                       ╱│╲
              MANAGER  ENGINEER  SALESPERSON
```

**Interpretation:** An employee may be none, one, or multiple roles.

```sql
CREATE TABLE EMPLOYEE (
    ssn VARCHAR(11) PRIMARY KEY,
    name VARCHAR(100)
);

-- Can exist in multiple subclasses or none
CREATE TABLE MANAGER (
    ssn VARCHAR(11) PRIMARY KEY,
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
);

CREATE TABLE ENGINEER (
    ssn VARCHAR(11) PRIMARY KEY,
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
);

CREATE TABLE SALESPERSON (
    ssn VARCHAR(11) PRIMARY KEY,
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
);

-- Valid scenarios:
-- 1. Employee in no subclass
-- 2. Employee only in MANAGER
-- 3. Employee in both MANAGER and ENGINEER
```

#### Example 3: Disjoint + Partial + Attribute-Defined

**Scenario:** Some courses have special types, determined by course format.

```
                    COURSE
                (Course_ID, Format)
                       ───  ← Partial
                     Format ← Defining attribute
                        ○ d  ← Disjoint
                       ╱│╲
              ONLINE  LAB  HYBRID
```

```sql
CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100),
    format VARCHAR(20),  -- Can be NULL (partial)
    
    CHECK (format IS NULL OR format IN ('Online', 'Lab', 'Hybrid'))
);

-- Regular course with no special format
INSERT INTO COURSE VALUES ('CS101', 'Intro to CS', NULL);

-- Special format courses
INSERT INTO COURSE VALUES ('CS102', 'Programming Lab', 'Lab');
INSERT INTO COURSE VALUES ('CS103', 'Web Development', 'Online');
```

---

## 6. Attribute Inheritance

### Definition

**Attribute Inheritance:** An entity in a subclass **automatically inherits ALL attributes and relationships** from its superclass.

### Rules

1. **Inherited attributes** don't need to be repeated in the subclass
2. **Subclass-specific attributes** are added to the inherited ones
3. **Primary key** of the superclass becomes the primary key of the subclass
4. Changes to superclass attributes affect all subclasses

### Example: Employee Hierarchy

**Superclass:** `EMPLOYEE (SSN, Name, Birth_date, Address, Salary)`

**Subclass:** `SECRETARY`
- **Inherited:** SSN, Name, Birth_date, Address, Salary
- **Own attributes:** Typing_speed, Office_hours

**Complete attribute set for SECRETARY:**
```
{SSN, Name, Birth_date, Address, Salary, Typing_speed, Office_hours}
```

**MySQL Query to Get Complete Information:**

```sql
-- Get all attributes for a secretary (inherited + own)
SELECT 
    e.ssn,
    e.name,
    e.birth_date,
    e.address,
    e.salary,          -- All inherited from EMPLOYEE
    s.typing_speed,
    s.office_hours     -- Own attributes
FROM EMPLOYEE e
JOIN SECRETARY s ON e.ssn = s.ssn
WHERE e.ssn = '123-45-6789';
```

### Inheritance with Relationships

Subclasses also inherit **relationships** from the superclass.

**Example:**

```
EMPLOYEE ──works_for→ DEPARTMENT

SECRETARY (subclass of EMPLOYEE) automatically has works_for relationship
```

```sql
-- Employee works for Department
CREATE TABLE DEPARTMENT (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100)
);

CREATE TABLE EMPLOYEE (
    ssn VARCHAR(11) PRIMARY KEY,
    name VARCHAR(100),
    dept_id INT,  -- Relationship: works_for
    
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id)
);

CREATE TABLE SECRETARY (
    ssn VARCHAR(11) PRIMARY KEY,
    typing_speed INT,
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
);

-- Secretary automatically inherits the works_for relationship
SELECT 
    s.ssn,
    e.name,
    d.dept_name  -- Inherited relationship
FROM SECRETARY s
JOIN EMPLOYEE e ON s.ssn = e.ssn
JOIN DEPARTMENT d ON e.dept_id = d.dept_id;
```

---

## 7. Multiple Inheritance and Lattices

### Hierarchy vs Lattice

**Hierarchy (Single Inheritance):**
- Each subclass has **exactly ONE** direct superclass
- Forms a tree structure

**Lattice (Multiple Inheritance):**
- A subclass has **MORE THAN ONE** direct superclass
- Forms a graph structure

### Multiple Inheritance Example

**Scenario:** University has people who are both students and employees.

```
         ┌──────────┐          ┌──────────┐
         │ EMPLOYEE │          │ STUDENT  │
         │ Salary   │          │ Major    │
         └─────┬────┘          └────┬─────┘
               │                    │
               └────────┬───────────┘
                        │
                  ┌─────▼──────┐
                  │ TEACHING_  │
                  │ ASSISTANT  │  ← Shared subclass
                  │ Hours_week │
                  └────────────┘
```

**TEACHING_ASSISTANT** inherits from **both** EMPLOYEE and STUDENT.

**Inherited attributes:**
- From EMPLOYEE: Salary, Hire_date
- From STUDENT: Major, GPA
- Own attributes: Hours_week, Course_assigned

**MySQL Implementation:**

```sql
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY,
    name VARCHAR(100),
    dob DATE,
    address VARCHAR(200)
);

CREATE TABLE EMPLOYEE (
    person_id INT PRIMARY KEY,
    employee_id VARCHAR(20),
    salary DECIMAL(10,2),
    hire_date DATE,
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

CREATE TABLE STUDENT (
    person_id INT PRIMARY KEY,
    student_id VARCHAR(20),
    major VARCHAR(50),
    gpa DECIMAL(3,2),
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

-- Shared subclass: Multiple inheritance
CREATE TABLE TEACHING_ASSISTANT (
    person_id INT PRIMARY KEY,
    hours_per_week INT,
    course_assigned VARCHAR(50),
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
    -- Must also exist in both EMPLOYEE and STUDENT
);

-- Trigger to enforce multiple inheritance
DELIMITER $$

CREATE TRIGGER check_ta_inheritance
BEFORE INSERT ON TEACHING_ASSISTANT
FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT 1 FROM EMPLOYEE WHERE person_id = NEW.person_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'TA must be an employee';
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM STUDENT WHERE person_id = NEW.person_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'TA must be a student';
    END IF;
END$$

DELIMITER ;

-- Sample data
INSERT INTO PERSON VALUES (1, 'John Smith', '1995-05-15', '123 Campus Dr');
INSERT INTO EMPLOYEE VALUES (1, 'E1001', 15000, '2024-08-20');
INSERT INTO STUDENT VALUES (1, 'S5001', 'Computer Science', 3.8);
INSERT INTO TEACHING_ASSISTANT VALUES (1, 20, 'CS101');

-- Query to get all inherited attributes
SELECT 
    p.name,
    p.dob,
    e.employee_id,
    e.salary,
    s.student_id,
    s.major,
    s.gpa,
    ta.hours_per_week,
    ta.course_assigned
FROM TEACHING_ASSISTANT ta
JOIN PERSON p ON ta.person_id = p.person_id
JOIN EMPLOYEE e ON ta.person_id = e.person_id
JOIN STUDENT s ON ta.person_id = s.person_id;
```

### Complex Lattice Example

```
    ┌──────────┐        ┌──────────┐
    │ STUDENT  ��        │ EMPLOYEE │
    └────┬─────┘        └────┬─────┘
         │                   │
         │  ┌────────────┐   │
         └──│GRAD_STUDENT│───┘
            │            │
            └──────┬─────┘
                   │
            ┌──────▼──────┐
            │ RESEARCH_   │
            │ ASSISTANT   │
            └─────────────┘
```

**RESEARCH_ASSISTANT** inherits from GRAD_STUDENT, which inherits from both STUDENT and EMPLOYEE.

```sql
CREATE TABLE GRAD_STUDENT (
    person_id INT PRIMARY KEY,
    thesis_topic VARCHAR(200),
    advisor_id INT,
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

CREATE TABLE RESEARCH_ASSISTANT (
    person_id INT PRIMARY KEY,
    research_area VARCHAR(100),
    grant_number VARCHAR(50),
    
    FOREIGN KEY (person_id) REFERENCES GRAD_STUDENT(person_id)
);

-- Must exist in PERSON, EMPLOYEE, STUDENT, and GRAD_STUDENT
```

![[Pasted image 20260313011949.png]]

---

## 8. Union Types (Categories) 
### Definition

A **Category (Union Type)** is a subclass that represents a **subset of the UNION** of multiple distinct superclasses.

**Key difference from shared subclass:**
- **Shared subclass:** Entity must exist in ALL superclasses
- **Category:** Entity exists in **ONE OR MORE** (but not necessarily all) superclasses

### Notation

**Symbol:** `U` in a circle, with lines to multiple superclasses

```
┌─────────┐    ┌──────┐    ┌─────────┐
│ PERSON  │    │ BANK │    │ COMPANY │
└────┬────┘    └───┬──┘    └────┬────┘
     │             │             │
     └─────────────┴─────────────┘
                   U  ← Union symbol
                   │
             ┌─────▼──────┐
             │   OWNER    │
             └────────────┘
```

![[Pasted image 20260313012212.png]]

### Example 1: Vehicle Owner

**Scenario:** A vehicle owner can be a PERSON, a BANK (leasing), or a COMPANY (fleet vehicle).

**Interpretation:** An OWNER is either:
- A person who owns a vehicle, OR
- A bank that owns a vehicle, OR
- A company that owns a vehicle

```
┌─────────┐    ┌──────┐    ┌─────────┐
│ PERSON  │    │ BANK │    │ COMPANY │
│ SSN     │    │ Code │    │ Tax_ID  │
└────┬────┘    └───┬──┘    └────┬────┘
     │             │             │
     └─────────────┴─────────────┘
                   U
                   │
             ┌─────▼──────┐
             │   OWNER    │
             │ Owner_ID   │
             └─────┬──────┘
                   │
             ┌─────▼──────┐
             │  VEHICLE   │
             │    VIN     │
             └────────────┘
```

**MySQL Implementation:**

```sql
CREATE TABLE PERSON (
    ssn VARCHAR(11) PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(200)
);

CREATE TABLE BANK (
    bank_code VARCHAR(20) PRIMARY KEY,
    bank_name VARCHAR(100),
    branch_address VARCHAR(200)
);

CREATE TABLE COMPANY (
    tax_id VARCHAR(20) PRIMARY KEY,
    company_name VARCHAR(100),
    industry VARCHAR(50)
);

-- Category: OWNER
CREATE TABLE OWNER (
    owner_id INT PRIMARY KEY AUTO_INCREMENT,
    owner_type VARCHAR(20) NOT NULL,  -- 'Person', 'Bank', or 'Company'
    person_ssn VARCHAR(11),
    bank_code VARCHAR(20),
    company_tax_id VARCHAR(20),
    
    -- Exactly ONE foreign key must be NOT NULL
    CHECK (
        (owner_type = 'Person' AND person_ssn IS NOT NULL AND bank_code IS NULL AND company_tax_id IS NULL) OR
        (owner_type = 'Bank' AND bank_code IS NOT NULL AND person_ssn IS NULL AND company_tax_id IS NULL) OR
        (owner_type = 'Company' AND company_tax_id IS NOT NULL AND person_ssn IS NULL AND bank_code IS NULL)
    ),
    
    FOREIGN KEY (person_ssn) REFERENCES PERSON(ssn),
    FOREIGN KEY (bank_code) REFERENCES BANK(bank_code),
    FOREIGN KEY (company_tax_id) REFERENCES COMPANY(tax_id)
);

CREATE TABLE VEHICLE (
    vin VARCHAR(17) PRIMARY KEY,
    make VARCHAR(50),
    model VARCHAR(50),
    year INT,
    owner_id INT,
    
    FOREIGN KEY (owner_id) REFERENCES OWNER(owner_id)
);

-- Sample data
INSERT INTO PERSON VALUES ('123-45-6789', 'John Doe', '123 Main St');
INSERT INTO BANK VALUES ('BNK001', 'First National Bank', '456 Bank Ave');
INSERT INTO COMPANY VALUES ('TX123456', 'ABC Corporation', 'Technology');

-- Owner who is a person
INSERT INTO OWNER (owner_type, person_ssn) 
VALUES ('Person', '123-45-6789');

-- Owner who is a bank
INSERT INTO OWNER (owner_type, bank_code) 
VALUES ('Bank', 'BNK001');

-- Owner who is a company
INSERT INTO OWNER (owner_type, company_tax_id) 
VALUES ('Company', 'TX123456');

-- Query to get owner details
SELECT 
    o.owner_id,
    o.owner_type,
    COALESCE(p.name, b.bank_name, c.company_name) AS owner_name,
    v.vin,
    v.make,
    v.model
FROM OWNER o
LEFT JOIN PERSON p ON o.person_ssn = p.ssn
LEFT JOIN BANK b ON o.bank_code = b.bank_code
LEFT JOIN COMPANY c ON o.company_tax_id = c.tax_id
JOIN VEHICLE v ON o.owner_id = v.owner_id;
```

### Example 2: Account Holder

**Scenario:** Bank account holders can be individuals or businesses.

```
┌─────────────┐          ┌──────────┐
│ INDIVIDUAL  │          │ BUSINESS │
│ SSN, Name   │          │ Tax_ID   │
└──────┬──────┘          └─────┬────┘
       │                       │
       └───────────┬───────────┘
                   U
                   │
          ┌────────▼─────────┐
          │ ACCOUNT_HOLDER   │
          │ Holder_ID        │
          └────────┬─────────┘
                   │
          ┌────────▼─────────┐
          │ BANK_ACCOUNT     │
          │ Account_No       │
          └──────────────────┘
```

```sql
CREATE TABLE INDIVIDUAL (
    ssn VARCHAR(11) PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    dob DATE
);

CREATE TABLE BUSINESS (
    tax_id VARCHAR(20) PRIMARY KEY,
    business_name VARCHAR(100),
    business_type VARCHAR(50)
);

CREATE TABLE ACCOUNT_HOLDER (
    holder_id INT PRIMARY KEY AUTO_INCREMENT,
    holder_type ENUM('Individual', 'Business') NOT NULL,
    individual_ssn VARCHAR(11),
    business_tax_id VARCHAR(20),
    
    CHECK (
        (holder_type = 'Individual' AND individual_ssn IS NOT NULL AND business_tax_id IS NULL) OR
        (holder_type = 'Business' AND business_tax_id IS NOT NULL AND individual_ssn IS NULL)
    ),
    
    FOREIGN KEY (individual_ssn) REFERENCES INDIVIDUAL(ssn),
    FOREIGN KEY (business_tax_id) REFERENCES BUSINESS(tax_id)
);

CREATE TABLE BANK_ACCOUNT (
    account_no VARCHAR(20) PRIMARY KEY,
    balance DECIMAL(12,2),
    account_type VARCHAR(20),
    holder_id INT NOT NULL,
    
    FOREIGN KEY (holder_id) REFERENCES ACCOUNT_HOLDER(holder_id)
);
```

### Example 3: Registered User (Multiple Partial Participation)

**Scenario:** A registered user can be a customer, vendor, or both.

```
┌──────────┐          ┌────────┐
│ CUSTOMER │          │ VENDOR │
└─────┬────┘          └────┬───┘
      │                    │
      └────────┬───────────┘
               U
               │
        ┌──────▼────────┐
        │ REGISTERED_   │
        │ USER          │
        └───────────────┘
```

```sql
CREATE TABLE CUSTOMER (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    loyalty_points INT
);

CREATE TABLE VENDOR (
    vendor_id INT PRIMARY KEY,
    vendor_name VARCHAR(100),
    commission_rate DECIMAL(5,2)
);

CREATE TABLE REGISTERED_USER (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(255),
    customer_id INT,
    vendor_id INT,
    
    -- Must be at least customer OR vendor (or both)
    CHECK (customer_id IS NOT NULL OR vendor_id IS NOT NULL),
    
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id),
    FOREIGN KEY (vendor_id) REFERENCES VENDOR(vendor_id)
);

-- User who is only a customer
INSERT INTO CUSTOMER VALUES (1, 'John Doe', 500);
INSERT INTO REGISTERED_USER (username, email, customer_id, vendor_id)
VALUES ('johndoe', 'john@email.com', 1, NULL);

-- User who is only a vendor
INSERT INTO VENDOR VALUES (1, 'ABC Supplies', 10.5);
INSERT INTO REGISTERED_USER (username, email, customer_id, vendor_id)
VALUES ('abcsupplies', 'abc@supplies.com', NULL, 1);

-- User who is BOTH customer and vendor
INSERT INTO CUSTOMER VALUES (2, 'Jane Smith', 1000);
INSERT INTO VENDOR VALUES (2, 'Janes Shop', 8.0);
INSERT INTO REGISTERED_USER (username, email, customer_id, vendor_id)
VALUES ('janesmith', 'jane@email.com', 2, 2);
```

### [[Ok, but can it be said that Category is just like a total participation overlapping Generalization?]]

---

## 9. EER to Relational Mapping

### Strategy 1: Single Table (Table per Hierarchy)

**Create ONE table** for the superclass with all attributes from all subclasses.

**Pros:**
- Simple queries (no joins)
- Fast access

**Cons:**
- Many NULL values (wasted space)
- Can violate normalization

**When to use:** 
- Few subclasses
- Subclasses have few unique attributes
- Queries mostly access superclass attributes

```sql
-- Single table for entire hierarchy
CREATE TABLE EMPLOYEE (
    ssn VARCHAR(11) PRIMARY KEY,
    name VARCHAR(100),
    birth_date DATE,
    salary DECIMAL(10,2),
    
    -- Discriminator
    employee_type VARCHAR(20),
    
    -- Secretary attributes (NULL for non-secretaries)
    typing_speed INT,
    office_hours VARCHAR(50),
    
    -- Engineer attributes (NULL for non-engineers)
    engineering_type VARCHAR(50),
    certifications TEXT,
    
    -- Technician attributes (NULL for non-technicians)
    tgrade INT
);
```

### Strategy 2: Table per Type (Class Table Inheritance)

**Create separate tables** for superclass and each subclass, linked by foreign keys.

**Pros:**
- No wasted space
- Clear structure
- Easy to add new subclasses

**Cons:**
- Requires joins for complete information
- More complex queries

**When to use:**
- Many subclasses
- Subclasses have many unique attributes
- Need to query subclasses independently

```sql
-- Superclass table
CREATE TABLE EMPLOYEE (
    ssn VARCHAR(11) PRIMARY KEY,
    name VARCHAR(100),
    birth_date DATE,
    salary DECIMAL(10,2)
);

-- Subclass tables
CREATE TABLE SECRETARY (
    ssn VARCHAR(11) PRIMARY KEY,
    typing_speed INT,
    office_hours VARCHAR(50),
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
        ON DELETE CASCADE
);

CREATE TABLE ENGINEER (
    ssn VARCHAR(11) PRIMARY KEY,
    engineering_type VARCHAR(50),
    certifications TEXT,
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
        ON DELETE CASCADE
);

CREATE TABLE TECHNICIAN (
    ssn VARCHAR(11) PRIMARY KEY,
    tgrade INT,
    
    FOREIGN KEY (ssn) REFERENCES EMPLOYEE(ssn)
        ON DELETE CASCADE
);
```

### Strategy 3: Table per Concrete Class

**Create separate tables** for each subclass ONLY (no superclass table).

**Pros:**
- No joins needed
- Fast queries on individual subclasses

**Cons:**
- Duplicate attributes
- Hard to query all employees
- Difficult to maintain

**When to use:**
- Rarely need to query across all subclasses
- Subclasses are very different

```sql
-- No EMPLOYEE table

CREATE TABLE SECRETARY (
    ssn VARCHAR(11) PRIMARY KEY,
    -- Repeated superclass attributes
    name VARCHAR(100),
    birth_date DATE,
    salary DECIMAL(10,2),
    -- Subclass attributes
    typing_speed INT,
    office_hours VARCHAR(50)
);

CREATE TABLE ENGINEER (
    ssn VARCHAR(11) PRIMARY KEY,
    -- Repeated superclass attributes
    name VARCHAR(100),
    birth_date DATE,
    salary DECIMAL(10,2),
    -- Subclass attributes
    engineering_type VARCHAR(50),
    certifications TEXT
);

CREATE TABLE TECHNICIAN (
    ssn VARCHAR(11) PRIMARY KEY,
    -- Repeated superclass attributes
    name VARCHAR(100),
    birth_date DATE,
    salary DECIMAL(10,2),
    -- Subclass attributes
    tgrade INT
);

-- Query all employees requires UNION
SELECT ssn, name, 'Secretary' AS type FROM SECRETARY
UNION
SELECT ssn, name, 'Engineer' AS type FROM ENGINEER
UNION
SELECT ssn, name, 'Technician' AS type FROM TECHNICIAN;
```

### Mapping Decision Tree

```
Is there a superclass with many shared attributes?
│
├─ YES → Use Strategy 2 (Table per Type)
│        Are queries mostly on individual subclasses?
│        │
│        ├─ YES → Strategy 2 is good
│        └─ NO → Consider Strategy 1 if few subclasses
│
└─ NO → Use Strategy 3 (Table per Concrete Class)
```

---

## 10. Complete Examples 
### Example 1: University Database with EER

**Requirements:**
- University has people: students, faculty, staff
- Students can be undergraduate or graduate
- Faculty can teach courses
- Some graduate students are teaching assistants (both student and employee)

**EER Diagram:**

```
                    ┌─────────────────────────┐
                    │       PERSON            │
                    │ Person_ID, Name, DOB    │
                    └───────────┬─────────────┘
                                │
                                ○ o,∂  ← Overlapping, Partial
                               ╱│╲
                              ╱ │ ╲
             ┌──────────┐    ┌┴────────┐    ┌──────────┐
             │ STUDENT  │    │ FACULTY │    │  STAFF   │
             └─────┬────┘    └─────────┘    └──────────┘
                   │
                   ○ d,T  ← Disjoint, Total
                  ╱│╲
        ┌────────┐ ┌┴────────────┐
        │UNDERGRAD│ │  GRADUATE   │
        └─────────┘ └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │   TEACHING  │
                    │  ASSISTANT  │
                    └─────────────┘
```

**Complete MySQL Implementation:**

```sql
-- Superclass
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dob DATE,
    address VARCHAR(200),
    phone VARCHAR(15),
    email VARCHAR(100) UNIQUE
);

-- Subclass: Student (overlapping with others)
CREATE TABLE STUDENT (
    person_id INT PRIMARY KEY,
    student_id VARCHAR(20) UNIQUE NOT NULL,
    major VARCHAR(50),
    enrollment_date DATE,
    student_level VARCHAR(20) NOT NULL,  -- 'Undergraduate' or 'Graduate'
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
        ON DELETE CASCADE,
    
    CHECK (student_level IN ('Undergraduate', 'Graduate'))
);

-- Subclass: Faculty
CREATE TABLE FACULTY (
    person_id INT PRIMARY KEY,
    faculty_id VARCHAR(20) UNIQUE NOT NULL,
    rank VARCHAR(50),  -- 'Assistant Prof', 'Associate Prof', 'Professor'
    department VARCHAR(50),
    office VARCHAR(20),
    research_area VARCHAR(100),
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
        ON DELETE CASCADE
);

-- Subclass: Staff
CREATE TABLE STAFF (
    person_id INT PRIMARY KEY,
    staff_id VARCHAR(20) UNIQUE NOT NULL,
    position VARCHAR(50),
    department VARCHAR(50),
    hire_date DATE,
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
        ON DELETE CASCADE
);

-- Specialization of Student: Undergraduate (Disjoint, Total)
CREATE TABLE UNDERGRADUATE (
    person_id INT PRIMARY KEY,
    class_year INT,  -- 1, 2, 3, 4
    advisor_id INT,
    gpa DECIMAL(3,2),
    
    FOREIGN KEY (person_id) REFERENCES STUDENT(person_id)
        ON DELETE CASCADE,
    FOREIGN KEY (advisor_id) REFERENCES FACULTY(person_id)
);

-- Specialization of Student: Graduate (Disjoint, Total)
CREATE TABLE GRADUATE (
    person_id INT PRIMARY KEY,
    thesis_topic VARCHAR(200),
    advisor_id INT,
    degree_type ENUM('Masters', 'PhD'),
    qualifying_exam_passed BOOLEAN,
    
    FOREIGN KEY (person_id) REFERENCES STUDENT(person_id)
        ON DELETE CASCADE,
    FOREIGN KEY (advisor_id) REFERENCES FACULTY(person_id)
);

-- Multiple Inheritance: Teaching Assistant (both Graduate Student and Employee)
CREATE TABLE TEACHING_ASSISTANT (
    person_id INT PRIMARY KEY,
    ta_id VARCHAR(20) UNIQUE,
    course_assigned VARCHAR(50),
    hours_per_week INT,
    hourly_rate DECIMAL(8,2),
    
    FOREIGN KEY (person_id) REFERENCES GRADUATE(person_id)
        ON DELETE CASCADE
);

-- Sample Data
INSERT INTO PERSON VALUES 
    (NULL, 'Alice Johnson', '2003-05-15', '123 Dorm A', '555-0001', 'alice@univ.edu'),
    (NULL, 'Bob Smith', '1985-08-22', '456 Faculty Row', '555-0002', 'bob@univ.edu'),
    (NULL, 'Carol White', '1992-11-30', '789 Admin Bldg', '555-0003', 'carol@univ.edu'),
    (NULL, 'David Lee', '2000-03-10', '321 Grad Housing', '555-0004', 'david@univ.edu');

SET @alice_id = 1;
SET @bob_id = 2;
SET @carol_id = 3;
SET @david_id = 4;

-- Alice is an undergraduate student
INSERT INTO STUDENT VALUES (@alice_id, 'S2023001', 'Computer Science', '2023-08-20', 'Undergraduate');
INSERT INTO UNDERGRADUATE VALUES (@alice_id, 2, @bob_id, 3.7);

-- Bob is faculty
INSERT INTO FACULTY VALUES (@bob_id, 'F001', 'Associate Professor', 'Computer Science', 'TB-301', 'Database Systems');

-- Carol is staff
INSERT INTO STAFF VALUES (@carol_id, 'ST001', 'Administrative Assistant', 'Registrar', '2015-03-01');

-- David is a graduate student AND teaching assistant (multiple roles)
INSERT INTO STUDENT VALUES (@david_id, 'S2023002', 'Computer Science', '2023-08-20', 'Graduate');
INSERT INTO GRADUATE VALUES (@david_id, 'Query Optimization in Distributed Databases', @bob_id, 'PhD', TRUE);
INSERT INTO TEACHING_ASSISTANT VALUES (@david_id, 'TA001', 'CS101', 20, 25.00);

-- Queries

-- 1. Get all information about a teaching assistant (multiple inheritance)
SELECT 
    p.name,
    p.email,
    s.student_id,
    s.major,
    g.thesis_topic,
    g.degree_type,
    f.name AS advisor_name,
    ta.ta_id,
    ta.course_assigned,
    ta.hours_per_week,
    (ta.hours_per_week * ta.hourly_rate * 4) AS monthly_salary
FROM TEACHING_ASSISTANT ta
JOIN GRADUATE g ON ta.person_id = g.person_id
JOIN STUDENT s ON ta.person_id = s.person_id
JOIN PERSON p ON ta.person_id = p.person_id
LEFT JOIN FACULTY f ON g.advisor_id = f.person_id
LEFT JOIN PERSON fp ON f.person_id = fp.person_id;

-- 2. Get all students (both undergrad and grad)
SELECT 
    p.name,
    s.student_id,
    s.student_level,
    s.major,
    CASE 
        WHEN u.person_id IS NOT NULL THEN CONCAT('Year ', u.class_year)
        WHEN g.person_id IS NOT NULL THEN g.degree_type
    END AS details
FROM STUDENT s
JOIN PERSON p ON s.person_id = p.person_id
LEFT JOIN UNDERGRADUATE u ON s.person_id = u.person_id
LEFT JOIN GRADUATE g ON s.person_id = g.person_id;

-- 3. Find people with multiple roles
SELECT 
    p.person_id,
    p.name,
    COUNT(DISTINCT role) AS num_roles,
    GROUP_CONCAT(DISTINCT role) AS roles
FROM (
    SELECT person_id, 'Student' AS role FROM STUDENT
    UNION ALL
    SELECT person_id, 'Faculty' AS role FROM FACULTY
    UNION ALL
    SELECT person_id, 'Staff' AS role FROM STAFF
    UNION ALL
    SELECT person_id, 'Teaching Assistant' AS role FROM TEACHING_ASSISTANT
) AS roles
JOIN PERSON p ON roles.person_id = p.person_id
GROUP BY p.person_id, p.name
HAVING num_roles > 1;
```

### Example 2: E-Commerce with Union Types

**Scenario:** Product suppliers can be manufacturers, distributors, or individual sellers.

**EER Diagram:**

```
┌────────────┐  ┌────────────┐  ┌──────────────┐
│MANUFACTURER│  │DISTRIBUTOR │  │INDIVIDUAL_   │
│            │  │            │  │SELLER        │
└──────┬─────┘  └──────┬─────┘  └──────┬───────┘
       │               │               │
       └───────────────┴───────────────┘
                       U
                       │
                ┌──────▼───────┐
                │   SUPPLIER   │
                └──────┬───────┘
                       │
                       │ supplies
                       │
                ┌──────▼───────┐
                │   PRODUCT    │
                └──────────────┘
```

**MySQL Implementation:**

```sql
CREATE TABLE MANUFACTURER (
    manufacturer_id INT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(100),
    factory_location VARCHAR(100),
    production_capacity INT,
    certification VARCHAR(100)
);

CREATE TABLE DISTRIBUTOR (
    distributor_id INT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(100),
    warehouse_locations TEXT,
    distribution_network VARCHAR(50)
);

CREATE TABLE INDIVIDUAL_SELLER (
    seller_id INT PRIMARY KEY AUTO_INCREMENT,
    seller_name VARCHAR(100),
    contact_phone VARCHAR(15),
    seller_rating DECIMAL(3,2)
);

-- Union type: Supplier
CREATE TABLE SUPPLIER (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_type ENUM('Manufacturer', 'Distributor', 'Individual') NOT NULL,
    manufacturer_id INT,
    distributor_id INT,
    seller_id INT,
    registration_date DATE,
    
    -- Enforce union constraint
    CHECK (
        (supplier_type = 'Manufacturer' AND manufacturer_id IS NOT NULL 
         AND distributor_id IS NULL AND seller_id IS NULL) OR
        (supplier_type = 'Distributor' AND distributor_id IS NOT NULL 
         AND manufacturer_id IS NULL AND seller_id IS NULL) OR
        (supplier_type = 'Individual' AND seller_id IS NOT NULL 
         AND manufacturer_id IS NULL AND distributor_id IS NULL)
    ),
    
    FOREIGN KEY (manufacturer_id) REFERENCES MANUFACTURER(manufacturer_id),
    FOREIGN KEY (distributor_id) REFERENCES DISTRIBUTOR(distributor_id),
    FOREIGN KEY (seller_id) REFERENCES INDIVIDUAL_SELLER(seller_id)
);

CREATE TABLE PRODUCT (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(200),
    category VARCHAR(50),
    price DECIMAL(10,2),
    supplier_id INT NOT NULL,
    stock_quantity INT,
    
    FOREIGN KEY (supplier_id) REFERENCES SUPPLIER(supplier_id)
);

-- Sample data
INSERT INTO MANUFACTURER VALUES 
    (NULL, 'TechCorp Manufacturing', 'China', 10000, 'ISO 9001');
    
INSERT INTO DISTRIBUTOR VALUES 
    (NULL, 'Global Distributors Inc', 'USA, Canada, Mexico', 'North America');
    
INSERT INTO INDIVIDUAL_SELLER VALUES 
    (NULL, 'John Doe', '555-1234', 4.5);

INSERT INTO SUPPLIER (supplier_type, manufacturer_id) 
VALUES ('Manufacturer', 1);

INSERT INTO SUPPLIER (supplier_type, distributor_id) 
VALUES ('Distributor', 1);

INSERT INTO SUPPLIER (supplier_type, seller_id) 
VALUES ('Individual', 1);

-- Query to get supplier details
SELECT 
    s.supplier_id,
    s.supplier_type,
    COALESCE(m.company_name, d.company_name, i.seller_name) AS name,
    COUNT(p.product_id) AS num_products
FROM SUPPLIER s
LEFT JOIN MANUFACTURER m ON s.manufacturer_id = m.manufacturer_id
LEFT JOIN DISTRIBUTOR d ON s.distributor_id = d.distributor_id
LEFT JOIN INDIVIDUAL_SELLER i ON s.seller_id = i.seller_id
LEFT JOIN PRODUCT p ON s.supplier_id = p.supplier_id
GROUP BY s.supplier_id, s.supplier_type, name;
```

### Example 3: Hospital Management with Complex Hierarchy

```sql
-- Superclass: Person
CREATE TABLE PERSON (
    person_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    dob DATE,
    gender CHAR(1),
    address VARCHAR(200),
    phone VARCHAR(15)
);

-- Subclass: Patient
CREATE TABLE PATIENT (
    person_id INT PRIMARY KEY,
    patient_id VARCHAR(20) UNIQUE,
    blood_group VARCHAR(5),
    allergies TEXT,
    insurance_id VARCHAR(50),
    patient_type VARCHAR(20) NOT NULL,  -- 'Outpatient' or 'Inpatient'
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id),
    CHECK (patient_type IN ('Outpatient', 'Inpatient'))
);

-- Specialization: Outpatient (Total, Disjoint)
CREATE TABLE OUTPATIENT (
    person_id INT PRIMARY KEY,
    appointment_date DATE,
    referring_doctor INT,
    
    FOREIGN KEY (person_id) REFERENCES PATIENT(person_id)
);

-- Specialization: Inpatient (Total, Disjoint)
CREATE TABLE INPATIENT (
    person_id INT PRIMARY KEY,
    admission_date DATE,
    room_number VARCHAR(10),
    expected_discharge DATE,
    
    FOREIGN KEY (person_id) REFERENCES PATIENT(person_id)
);

-- Subclass: Medical Staff
CREATE TABLE MEDICAL_STAFF (
    person_id INT PRIMARY KEY,
    staff_id VARCHAR(20) UNIQUE,
    hire_date DATE,
    department VARCHAR(50),
    shift VARCHAR(20),
    
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
);

-- Specialization: Doctor
CREATE TABLE DOCTOR (
    person_id INT PRIMARY KEY,
    specialization VARCHAR(50),
    license_number VARCHAR(50) UNIQUE,
    years_experience INT,
    
    FOREIGN KEY (person_id) REFERENCES MEDICAL_STAFF(person_id)
);

-- Specialization: Nurse
CREATE TABLE NURSE (
    person_id INT PRIMARY KEY,
    nurse_level VARCHAR(20),  -- 'RN', 'LPN', 'Nurse Practitioner'
    certification VARCHAR(100),
    
    FOREIGN KEY (person_id) REFERENCES MEDICAL_STAFF(person_id)
);

-- Relationship: Treatment
CREATE TABLE TREATMENT (
    treatment_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    doctor_id INT,
    treatment_date DATE,
    diagnosis VARCHAR(500),
    prescription TEXT,
    
    FOREIGN KEY (patient_id) REFERENCES PATIENT(person_id),
    FOREIGN KEY (doctor_id) REFERENCES DOCTOR(person_id)
);
```

---

## Summary Tables

### EER Constraint Summary

| Constraint Type | Question | Values | Symbol | MySQL Enforcement |
|-----------------|----------|--------|--------|-------------------|
| **Disjointness** | Multiple subclasses? | Disjoint `d` / Overlapping `o` | `d` or `o` in circle | CHECK constraint, Triggers |
| **Completeness** | Must be in subclass? | Total (double line) / Partial (single line) | `═══` or `───` | NOT NULL, Triggers |
| **Membership** | How determined? | Attribute-defined / User-defined | Attribute label | Automatic or Manual |

### Mapping Strategies Comparison

| Strategy | Tables | Space | Query Speed | Use When |
|----------|--------|-------|-------------|----------|
| **Single Table** | 1 | Wasted (NULLs) | Fast | Few subclasses, simple attributes |
| **Table per Type** | N+1 | Efficient | Slow (joins) | Many subclasses, complex attributes |
| **Table per Concrete** | N | Duplicated | Fast | Rarely query all together |

### EER vs ER Comparison

| Feature | ER Model | EER Model |
|---------|----------|-----------|
| **Subclasses** | ✗ Not supported | ✓ Supported |
| **Inheritance** | ✗ Not supported | ✓ Supported |
| **Specialization** | ✗ Not supported | ✓ Supported |
| **Generalization** | ✗ Not supported | ✓ Supported |
| **Union Types** | ✗ Not supported | ✓ Supported |
| **Complexity** | Simple | Complex |
| **Use Case** | Simple databases | Complex, hierarchical databases |

---

## Best Practices

### 1. Choose Appropriate Constraints

✅ **Do:**
- Use **Disjoint** when categories are mutually exclusive
- Use **Overlapping** when entities can have multiple roles
- Use **Total** when every entity must be classified
- Use **Partial** when classification is optional

❌ **Don't:**
- Overuse overlapping (can cause complexity)
- Force total participation when not needed

### 2. Select Right Mapping Strategy

✅ **Do:**
- Use **Table per Type** for most scenarios (balance of flexibility and efficiency)
- Use **Single Table** for simple hierarchies with few attributes
- Document your choice

❌ **Don't:**
- Use **Table per Concrete Class** unless absolutely necessary
- Mix strategies in the same hierarchy

### 3. Enforce Constraints

✅ **Do:**
- Use CHECK constraints for disjointness
- Use triggers for complex constraints
- Use NOT NULL for total participation

❌ **Don't:**
- Rely solely on application logic
- Forget to test constraint enforcement

### 4. Design for Querying

✅ **Do:**
- Create views for common queries
- Index foreign keys
- Consider query patterns when choosing mapping strategy

❌ **Don't:**
- Over-normalize at the expense of performance
- Create unnecessary joins

---

## Common Mistakes to Avoid

❌ **Mistake 1:** Confusing shared subclass with union type
```
Shared subclass: Must exist in ALL superclasses
Union type: Exists in ONE OR MORE superclasses
```

❌ **Mistake 2:** Not enforcing disjointness in database
```sql
-- BAD: No constraint
CREATE TABLE SECRETARY (ssn VARCHAR(11) PRIMARY KEY);
CREATE TABLE ENGINEER (ssn VARCHAR(11) PRIMARY KEY);
-- Nothing prevents same SSN in both!

-- GOOD: Use trigger to enforce
```

❌ **Mistake 3:** Using wrong mapping strategy
```
Don't use single table if subclasses have 20+ unique attributes each
```

❌ **Mistake 4:** Forgetting inheritance in queries
```sql
-- BAD: Only query subclass
SELECT * FROM SECRETARY WHERE ssn = '123-45-6789';
-- Missing inherited EMPLOYEE attributes!

-- GOOD: Join with superclass
SELECT * FROM SECRETARY s 
JOIN EMPLOYEE e ON s.ssn = e.ssn 
WHERE s.ssn = '123-45-6789';
```

---
# Complete Guide to Relational Algebra in DBMS

## Table of Contents
1. [Introduction to Relational Algebra](#introduction)
2. [Sample Database Schema](#schema)
3. [Unary Operations](#unary-operations)
4. [Set Operations](#set-operations)
5. [Binary Operations](#binary-operations)
6. [Join Operations](#join-operations)
7. [Division Operation](#division)
8. [Extended Operations](#extended-operations)
9. [Database Modification Operations](#modification)
10. [Complex Query Examples](#complex-examples)
11. [Translation to SQL](#sql-translation)

---

## 1. Introduction to Relational Algebra {#introduction}

### What is Relational Algebra?

**Relational Algebra** is a **procedural query language** that tells the database **HOW** to retrieve data step-by-step.

**Comparison:**

| Aspect | Relational Algebra | SQL |
|--------|-------------------|-----|
| **Type** | Procedural | Declarative |
| **Approach** | Specify **how** to get data | Specify **what** data you want |
| **Usage** | Theoretical foundation | Practical implementation |
| **Example** | $\pi_{name}(\sigma_{age>25}(Person))$ | `SELECT name FROM Person WHERE age > 25` |

### Why Learn Relational Algebra?

1. **Foundation of SQL:** SQL queries are translated to relational algebra internally
2. **Query Optimization:** Understanding how operations work helps write efficient queries
3. **Database Theory:** Essential for understanding database design and optimization
4. **Academic Importance:** Heavily tested in DBMS courses and exams

### Properties

- **Closure Property:** Output of any operation is also a relation
- **Composable:** Operations can be nested and combined
- **Set-based:** Operates on sets (no duplicates by default)

---

## 2. Sample Database Schema {#schema}

We'll use a **University Database** for all examples:

### Schema Diagrams

```
┌─────────────────────────────┐
│         SAILORS             │
├─────────────────────────────┤
��� sid (PK)  INTEGER           │
│ sname     VARCHAR(50)       │
│ rating    INTEGER           │
│ age       DECIMAL(4,1)      │
└─────────────────────────────┘

┌─────────────────────────────┐
│         BOATS               │
├─────────────────────────────┤
│ bid (PK)  INTEGER           │
│ bname     VARCHAR(50)       │
│ color     VARCHAR(20)       │
└─────────────────────────────┘

┌─────────────────────────────┐
│        RESERVES             │
├─────────────────────────────┤
│ sid (FK)  INTEGER           │
│ bid (FK)  INTEGER           │
│ day       DATE              │
└─────────────────────────────┘
```

### MySQL Implementation

```sql
CREATE TABLE SAILORS (
    sid INT PRIMARY KEY,
    sname VARCHAR(50),
    rating INT,
    age DECIMAL(4,1)
);

CREATE TABLE BOATS (
    bid INT PRIMARY KEY,
    bname VARCHAR(50),
    color VARCHAR(20)
);

CREATE TABLE RESERVES (
    sid INT,
    bid INT,
    day DATE,
    PRIMARY KEY (sid, bid, day),
    FOREIGN KEY (sid) REFERENCES SAILORS(sid),
    FOREIGN KEY (bid) REFERENCES BOATS(bid)
);

-- Sample Data
INSERT INTO SAILORS VALUES
    (22, 'Dustin', 7, 45.0),
    (29, 'Brutus', 1, 33.0),
    (31, 'Lubber', 8, 55.5),
    (32, 'Andy', 8, 25.5),
    (58, 'Rusty', 10, 35.0),
    (64, 'Horatio', 7, 35.0),
    (71, 'Zorba', 10, 16.0),
    (74, 'Horatio', 9, 35.0),
    (85, 'Art', 3, 25.5),
    (95, 'Bob', 3, 63.5);

INSERT INTO BOATS VALUES
    (101, 'Interlake', 'blue'),
    (102, 'Interlake', 'red'),
    (103, 'Clipper', 'green'),
    (104, 'Marine', 'red');

INSERT INTO RESERVES VALUES
    (22, 101, '2026-10-10'),
    (22, 102, '2026-10-10'),
    (22, 103, '2026-10-08'),
    (22, 104, '2026-10-07'),
    (31, 102, '2026-11-10'),
    (31, 103, '2026-11-06'),
    (31, 104, '2026-11-12'),
    (64, 101, '2026-09-05'),
    (64, 102, '2026-09-08'),
    (74, 103, '2026-09-08');
```

### Additional Banking Schema (for specific examples)

```sql
CREATE TABLE ACCOUNT (
    account_number VARCHAR(20) PRIMARY KEY,
    branch_name VARCHAR(50),
    balance DECIMAL(12,2)
);

CREATE TABLE LOAN (
    loan_number VARCHAR(20) PRIMARY KEY,
    branch_name VARCHAR(50),
    amount DECIMAL(12,2)
);

CREATE TABLE BORROWER (
    customer_name VARCHAR(50),
    loan_number VARCHAR(20),
    PRIMARY KEY (customer_name, loan_number),
    FOREIGN KEY (loan_number) REFERENCES LOAN(loan_number)
);

CREATE TABLE DEPOSITOR (
    customer_name VARCHAR(50),
    account_number VARCHAR(20),
    PRIMARY KEY (customer_name, account_number),
    FOREIGN KEY (account_number) REFERENCES ACCOUNT(account_number)
);

-- Sample data
INSERT INTO ACCOUNT VALUES
    ('A-101', 'Downtown', 500),
    ('A-102', 'Perryridge', 400),
    ('A-201', 'Brighton', 900),
    ('A-215', 'Mianus', 700),
    ('A-217', 'Brighton', 750);

INSERT INTO LOAN VALUES
    ('L-11', 'Round Hill', 900),
    ('L-14', 'Downtown', 1500),
    ('L-15', 'Perryridge', 1500),
    ('L-16', 'Perryridge', 1300),
    ('L-17', 'Downtown', 1000),
    ('L-23', 'Redwood', 2000),
    ('L-93', 'Mianus', 500);

INSERT INTO BORROWER VALUES
    ('Jones', 'L-17'),
    ('Smith', 'L-11'),
    ('Smith', 'L-23'),
    ('Hayes', 'L-15');

INSERT INTO DEPOSITOR VALUES
    ('Johnson', 'A-101'),
    ('Smith', 'A-215'),
    ('Hayes', 'A-102'),
    ('Turner', 'A-201');
```

---

## 3. Unary Operations {#unary-operations}

**Unary operations** work on a **single relation**.

### 3.1 Selection (σ) - The Row Filter

**Symbol:** σ (sigma)

**Purpose:** Select rows (tuples) that satisfy a given **condition** (predicate).

**Equivalent:** SQL `WHERE` clause

**Syntax:**
```
σ_condition(Relation)
```

**Properties:**
- Returns a subset of rows
- Same number of columns as input
- Commutative: σ_c1(σ_c2(R)) = σ_c2(σ_c1(R))
- Can combine conditions: σ_c1 AND c2(R) = σ_c1(σ_c2(R))

#### Example 1: Simple Selection

**Query:** Find all sailors with rating greater than 8

**Relational Algebra:**

$$σ_{rating > 8}(SAILORS)$$


**Result:**
```
┌─────┬─────────┬────────┬──────┐
│ sid │ sname   │ rating │ age  │
├─────┼─────────┼────────┼──────┤
│ 31  │ Lubber  │ 8      │ 55.5 │
│ 58  │ Rusty   │ 10     │ 35.0 │
│ 71  │ Zorba   │ 10     │ 16.0 │
│ 74  │ Horatio │ 9      │ 35.0 │
└─────┴─────────┴────────┴──────┘
```

**MySQL:**
```sql
SELECT * FROM SAILORS WHERE rating > 8;
```

#### Example 2: Multiple Conditions

**Query:** Find sailors with rating > 7 AND age < 40

**Relational Algebra:**
$$σ_{rating > 7 \; ∧ \;age < 40}(SAILORS)$$

**MySQL:**
```sql
SELECT * FROM SAILORS WHERE rating > 7 AND age < 40;
```

**Result:**
```
┌─────┬─────────┬────────┬──────┐
│ sid │ sname   │ rating │ age  │
├─────┼─────────┼────────┼──────┤
│ 32  │ Andy    │ 8      │ 25.5 │
│ 58  │ Rusty   │ 10     │ 35.0 │
│ 71  │ Zorba   │ 10     │ 16.0 │
│ 74  │ Horatio │ 9      │ 35.0 │
└─────┴─────────┴────────┴──────┘
```

#### Comparison Operators in Selection

| Operator          | Symbol | Example                |
| ----------------- | ------ | ---------------------- |
| **Equal**         | `=`    | σ_rating = 10(SAILORS) |
| **Not Equal**     | `≠`    | σ_rating ≠ 10(SAILORS) |
| **Greater**       | `>`    | σ_age > 30(SAILORS)    |
| **Less**          | `<`    | σ_age < 30(SAILORS)    |
| **Greater/Equal** | `≥`    | σ_rating ≥ 8(SAILORS)  |
| **Less/Equal**    | `≤`    | σ_rating ≤ 5(SAILORS)  |

#### Logical Operators

| Operator | Symbol | Example |
|----------|--------|---------|
| **AND** | `∧` | σ_rating > 7 ∧ age < 40(SAILORS) |
| **OR** | `∨` | σ_rating > 9 ∨ age < 20(SAILORS) |
| **NOT** | `¬` | σ_¬(rating = 10)(SAILORS) |

---

### 3.2 Projection (π) - The Column Filter

**Symbol:** π (pi)

**Purpose:** Select specific **columns** (attributes) from a relation.

**Equivalent:** SQL `SELECT` clause

**Syntax:**

$$π_{attribute1,\;attribute2,\;...\;}(Relation)$$

**Properties:**
- Returns a subset of columns
- **Automatically removes duplicate rows**
- Number of rows ≤ input rows
- Not commutative in general

#### Example 1: Single Column Projection

**Query:** Get all sailor names

**Relational Algebra:**

$$π_{sname}(SAILORS)$$


**Result:**
```
┌─────────┐
│ sname   │
├─────────┤
│ Dustin  │
│ Brutus  │
│ Lubber  │
│ Andy    │
│ Rusty   │
│ Horatio │
│ Zorba   │
│ Art     │
│ Bob     │
└─────────┘
```

**MySQL:**
```sql
SELECT DISTINCT sname FROM SAILORS;
```

**Note:** SQL requires `DISTINCT` to match relational algebra behavior (automatic duplicate removal).

#### Example 2: Multiple Column Projection

**Query:** Get sailor names and ratings

**Relational Algebra:**
```
π_sname, rating(SAILORS)
```

**Result:**
```
┌─────────┬────────┐
│ sname   │ rating │
├─────────┼────────┤
│ Dustin  │ 7      │
│ Brutus  │ 1      │
│ Lubber  │ 8      │
│ Andy    │ 8      │
│ Rusty   │ 10     │
│ Horatio │ 7      │
│ Zorba   │ 10     │
│ Horatio │ 9      │
│ Art     │ 3      │
│ Bob     │ 3      │
└─────────┴────────┘
```

**MySQL:**
```sql
SELECT DISTINCT sname, rating FROM SAILORS;
```

#### Example 3: Combining Selection and Projection

**Query:** Get names of sailors with rating > 8

**Relational Algebra:**
```
π_sname(σ_rating > 8(SAILORS))
```

**Step-by-step execution:**
1. **σ_rating > 8(SAILORS)** - Filter rows where rating > 8
2. **π_sname(...)** - Project only sname column

**Result:**
```
┌─────────┐
│ sname   │
├─────────┤
│ Rusty   │
│ Zorba   │
│ Horatio │
└─────────┘
```

**MySQL:**
```sql
SELECT DISTINCT sname 
FROM SAILORS 
WHERE rating > 8;
```

---

### 3.3 Rename (ρ) - The Alias Operator

**Symbol:** ρ (rho)

**Purpose:** Rename relations and/or their attributes.

**Equivalent:** SQL `AS` keyword

**Syntax:**

$$ρ_{NewName}(Relation)$$
	-- Rename relation

$$ρ_{NewName\;(A1, A2, ...)}(Relation)$$       
	-- Rename relation and attributes

$$ρ_{(A1, A2, ...)}(Relation)$$
	-- Rename only attributes


**Why use Rename?**
1. **Self-joins:** Join a table to itself
2. **Clarity:** Make complex queries more readable
3. **Avoid conflicts:** When same attribute names appear in different tables

#### Example 1: Rename Relation

**Query:** Rename SAILORS to S

**Relational Algebra:**

$$ρ_S(SAILORS)$$


**MySQL:**
```sql
SELECT * FROM SAILORS AS S;
```

#### Example 2: Rename Relation and Attributes

**Query:** Rename SAILORS to S with columns (id, name, r, a)

**Relational Algebra:**

$$ρ_{S\;(id, name, r, a)}(SAILORS)$$


**MySQL:**
```sql
SELECT 
    sid AS id, 
    sname AS name, 
    rating AS r, 
    age AS a
FROM SAILORS;
```

#### Example 3: Self-Join Using Rename

**Query:** Find pairs of sailors with the same rating

**Relational Algebra:**
```
π_{S1.sname, S2.sname}(
    σ_{S1.rating = S2.rating ∧ S1.sid < S2.sid}(
        ρ_{S1}(SAILORS) × ρ_{S2}(SAILORS)
    )
)
```

$$π_{S1.sname, S2.sname}(
    σ_{S1.rating = S2.rating ∧ S1.sid < S2.sid}(
        ρ_{S1}(SAILORS) × ρ_{S2}(SAILORS)
    )
)$$

**MySQL:**
```sql
SELECT DISTINCT S1.sname, S2.sname
FROM SAILORS S1, SAILORS S2
WHERE S1.rating = S2.rating 
  AND S1.sid < S2.sid;
```

**Result:**
```
┌─────────┬─────────┐
│ S1.sname│ S2.sname│
├─────────┼─────────┤
│ Dustin  │ Horatio │
│ Lubber  │ Andy    │
│ Rusty   │ Zorba   │
│ Brutus  │ Art     │
│ Brutus  │ Bob     │
│ Art     │ Bob     │
└─────────┴─────────┘
```

---

## 4. Set Operations

**Set operations** work on **two relations** that are **union-compatible**.

### Union Compatibility

**Definition:** Two relations R and S are union-compatible if:
1. They have the **same number of attributes** (same arity)
2. Corresponding attributes have **compatible data types**
3. Attribute names don't need to match

**Example:**

**Union-Compatible:**
```
R(id INT, name VARCHAR)
S(sid INT, sname VARCHAR)  ✓ Compatible (same types)
```

**NOT Union-Compatible:**
```
R(id INT, name VARCHAR)
S(id INT, age INT)  ✗ Not compatible (VARCHAR ≠ INT)
```

---

### 4.1 Union (∪)

**Symbol:** $∪$

**Purpose:** Combine rows from two relations. Returns rows that appear in **either** relation.

**Properties:**
- Automatically removes duplicates
- Commutative: $R ∪ S = S ∪ R$
- Associative: $(R ∪ S) ∪ T = R ∪ (S ∪ T)$

**Syntax:**
```
R ∪ S
```

#### Example 1: Union of Sailors

**Setup:** Create two separate sailor groups

```sql
CREATE TABLE SAILORS_GROUP1 (
    sid INT, sname VARCHAR(50), rating INT
);

CREATE TABLE SAILORS_GROUP2 (
    sid INT, sname VARCHAR(50), rating INT
);

INSERT INTO SAILORS_GROUP1 VALUES
    (22, 'Dustin', 7),
    (31, 'Lubber', 8),
    (58, 'Rusty', 10);

INSERT INTO SAILORS_GROUP2 VALUES
    (31, 'Lubber', 8),   -- Duplicate
    (64, 'Horatio', 7),
    (71, 'Zorba', 10);
```

**Query:** Find all sailors from either group

**Relational Algebra:**
```
SAILORS_GROUP1 ∪ SAILORS_GROUP2
```

**Result:**
```
┌─────┬─────────┬────────┐
│ sid │ sname   │ rating │
├─────┼─────────┼────────┤
│ 22  │ Dustin  │ 7      │
│ 31  │ Lubber  │ 8      │  ← Duplicate removed
│ 58  │ Rusty   │ 10     │
│ 64  │ Horatio │ 7      │
│ 71  │ Zorba   │ 10     │
└─────┴─────────┴────────┘
```

**MySQL:**
```sql
SELECT * FROM SAILORS_GROUP1
UNION
SELECT * FROM SAILORS_GROUP2;
```

**Note:** `UNION` automatically removes duplicates. Use `UNION ALL` to keep duplicates.

#### Example 2: Find customers who have account OR loan

**Query:** Find all customer names who are either depositors or borrowers

**Relational Algebra:**

$$π_{customer_name}(DEPOSITOR) ∪ π_{customer_name}(BORROWER)$$

**MySQL:**
```sql
SELECT customer_name FROM DEPOSITOR
UNION
SELECT customer_name FROM BORROWER;
```

**Result:**
```
┌───────────────┐
│ customer_name │
├───────────────┤
│ Johnson       │
│ Smith         │
│ Hayes         │
│ Turner        │
│ Jones         │
└───────────────┘
```

---

### 4.2 Intersection (∩)

**Symbol:** ∩

**Purpose:** Find rows that appear in **both** relations.

**Properties:**
- Commutative: R ∩ S = S ∩ R
- Associative: (R ∩ S) ∩ T = R ∩ (S ∩ T)
- Can be expressed using set difference: R ∩ S = R - (R - S)

**Syntax:**
```
R ∩ S
```

#### Example 1: Intersection of Sailor Groups

**Query:** Find sailors in both groups

**Relational Algebra:**
```
SAILORS_GROUP1 ∩ SAILORS_GROUP2
```

**Result:**
```
┌─────┬────────┬────────┐
│ sid │ sname  │ rating │
├─────┼────────┼────────┤
│ 31  │ Lubber │ 8      │
└─────┴────────┴────────┘
```

**MySQL:**
```sql
-- Method 1: Using INTERSECT (MySQL 8.0.31+)
SELECT * FROM SAILORS_GROUP1
INTERSECT
SELECT * FROM SAILORS_GROUP2;

-- Method 2: Using JOIN (works in all versions)
SELECT DISTINCT s1.*
FROM SAILORS_GROUP1 s1
JOIN SAILORS_GROUP2 s2 
  ON s1.sid = s2.sid 
  AND s1.sname = s2.sname 
  AND s1.rating = s2.rating;

-- Method 3: Using IN
SELECT * FROM SAILORS_GROUP1
WHERE (sid, sname, rating) IN (SELECT sid, sname, rating FROM SAILORS_GROUP2);
```

#### Example 2: Find customers with BOTH account AND loan

**Query:** Find customers who are both depositors and borrowers

**Relational Algebra:**

$$π_{customer_name}(DEPOSITOR) ∩ π_{customer_name}(BORROWER)$$


**MySQL:**
```sql
SELECT customer_name FROM DEPOSITOR
INTERSECT
SELECT customer_name FROM BORROWER;

-- Alternative
SELECT DISTINCT d.customer_name
FROM DEPOSITOR d
WHERE d.customer_name IN (SELECT customer_name FROM BORROWER);
```

**Result:**
```
┌───────────────┐
│ customer_name │
├───────────────┤
│ Smith         │
│ Hayes         │
└───────────────┘
```

---

### 4.3 Set Difference (−)

**Symbol:** − or \

**Purpose:** Find rows in **first relation** but **NOT** in second relation.

**Properties:**
- **NOT commutative:** R − S ≠ S − R
- **NOT associative**

**Syntax:**
```
R − S
```

#### Example 1: Difference of Sailor Groups

**Query:** Find sailors in GROUP1 but not in GROUP2

**Relational Algebra:**
```
SAILORS_GROUP1 − SAILORS_GROUP2
```

**Result:**
```
┌─────┬────────┬────────┐
│ sid │ sname  │ rating │
├─────┼────────┼────────┤
│ 22  │ Dustin │ 7      │
│ 58  │ Rusty  │ 10     │
└─────┴────────┴────────┘
```

**MySQL:**
```sql
-- Method 1: Using EXCEPT (MySQL 8.0.31+)
SELECT * FROM SAILORS_GROUP1
EXCEPT
SELECT * FROM SAILORS_GROUP2;

-- Method 2: Using NOT IN (works in all versions)
SELECT * FROM SAILORS_GROUP1
WHERE (sid, sname, rating) NOT IN (
    SELECT sid, sname, rating FROM SAILORS_GROUP2
);

-- Method 3: Using LEFT JOIN
SELECT s1.*
FROM SAILORS_GROUP1 s1
LEFT JOIN SAILORS_GROUP2 s2 
  ON s1.sid = s2.sid 
  AND s1.sname = s2.sname 
  AND s1.rating = s2.rating
WHERE s2.sid IS NULL;
```

#### Example 2: Find customers with account but NO loan

**Query:** Find customers who have deposited but not borrowed

**Relational Algebra:**
```
π_customer_name(DEPOSITOR) − π_customer_name(BORROWER)
```

**MySQL:**
```sql
SELECT customer_name FROM DEPOSITOR
EXCEPT
SELECT customer_name FROM BORROWER;

-- Alternative
SELECT customer_name 
FROM DEPOSITOR
WHERE customer_name NOT IN (SELECT customer_name FROM BORROWER);
```

**Result:**
```
┌───────────────┐
│ customer_name │
├───────────────┤
│ Johnson       │
│ Turner        │
└───────────────┘
```

#### Example 3: Asymmetric Nature of Difference

**Demonstrate:** R − S ≠ S − R

```sql
-- SAILORS_GROUP1 − SAILORS_GROUP2
-- Result: (22, Dustin), (58, Rusty)

-- SAILORS_GROUP2 − SAILORS_GROUP1
-- Result: (64, Horatio), (71, Zorba)
```

---

## 5. Binary Operations 

### 5.1 Cartesian Product (×)

**Symbol:** × (cross)

**Purpose:** Combine **every row** from first relation with **every row** from second relation.

**Properties:**
- If R has m rows and S has n rows, R × S has m × n rows
- If R has p columns and S has q columns, R × S has p + q columns
- Usually inefficient on its own (produces huge result)
- Building block for joins

**Syntax:**
```
R × S
```

#### Example 1: Simple Cartesian Product

**Setup:**
```sql
CREATE TABLE R (A INT, B VARCHAR(10));
CREATE TABLE S (C INT, D VARCHAR(10));

INSERT INTO R VALUES (1, 'a'), (2, 'b');
INSERT INTO S VALUES (10, 'x'), (20, 'y'), (30, 'z');
```

**Query:** Cartesian product of R and S

**Relational Algebra:**
```
R × S
```

**Result:** 2 rows × 3 rows = 6 rows

```
┌───┬───┬────┬────┐
│ A │ B │ C  │ D  │
├───┼───┼────┼────┤
│ 1 │ a │ 10 │ x  │
│ 1 │ a │ 20 │ y  │
│ 1 │ a │ 30 │ z  │
│ 2 │ b │ 10 │ x  │
│ 2 │ b │ 20 │ y  │
│ 2 │ b │ 30 │ z  │
└───┴───┴────┴────┘
```

**MySQL:**
```sql
SELECT * FROM R, S;
-- or
SELECT * FROM R CROSS JOIN S;
```

#### Example 2: Cartesian Product with Real Data

**Query:** Combine all sailors with all boats

**Relational Algebra:**
```
SAILORS × BOATS
```

**Result:** 10 sailors × 4 boats = 40 rows

```
┌─────┬─────────┬────────┬──────┬─────┬───────────┬────────┐
│ sid │ sname   │ rating │ age  │ bid │ bname     │ color  │
├─────┼─────────┼────────┼──────┼─────┼───────────┼────────┤
│ 22  │ Dustin  │ 7      │ 45.0 │ 101 │ Interlake │ blue   │
│ 22  │ Dustin  │ 7      │ 45.0 │ 102 │ Interlake │ red    │
│ 22  │ Dustin  │ 7      │ 45.0 │ 103 │ Clipper   │ green  │
│ 22  │ Dustin  │ 7      │ 45.0 │ 104 │ Marine    │ red    │
│ 29  │ Brutus  │ 1      │ 33.0 │ 101 │ Interlake │ blue   │
│ ... (36 more rows) ...                                    │
└─────┴─────────┴────────┴──────┴─────┴───────────┴────────┘
```

**MySQL:**
```sql
SELECT * FROM SAILORS CROSS JOIN BOATS;
```

**Practical Use:** Cartesian product alone is rarely useful, but it's the foundation for **joins**.

---

## 6. Join Operations

**Join** = Cartesian Product + Selection

Joins connect related tables based on common attributes.

### 6.1 Theta Join ($⋈_θ$)

**Symbol:** $⋈_{θ}$ (bowtie with theta)

**Purpose:** Cartesian product followed by selection with condition $θ$.

**Formula:**
$$R ⋈_θ S = σ_θ(R × S)$$

**Syntax:**
$$R ⋈_{condition} S$$

#### Example: Theta Join

**Query:** Combine sailors and boats where sailor's age is greater than boat's bid

**Relational Algebra:**
```
SAILORS ⋈_age > bid BOATS
```

**Equivalent:**
```
σ_age > bid(SAILORS × BOATS)
```

**MySQL:**
```sql
SELECT *
FROM SAILORS, BOATS
WHERE age > bid;
```

---

### 6.2 Equi-Join

**Purpose:** Theta join where condition is **equality** (=).

**Most common type of join.**

**Syntax:**
$$R ⋈_{R.A = S.B} S$$

#### Example 1: Join Sailors and Reserves

**Query:** Find sailor information for all reservations

**Relational Algebra:**
```
SAILORS ⋈_SAILORS.sid = RESERVES.sid RESERVES
```

**MySQL:**
```sql
SELECT *
FROM SAILORS S, RESERVES R
WHERE S.sid = R.sid;

-- Or using explicit JOIN syntax
SELECT *
FROM SAILORS S
JOIN RESERVES R ON S.sid = R.sid;
```

**Result:**
```
┌─────┬─────────┬────────┬──────┬─────┬─────┬────────────┐
│ sid │ sname   │ rating │ age  │ sid │ bid │ day        │
├─────┼─────────┼────────┼──────┼─────┼─────┼────────────┤
│ 22  │ Dustin  │ 7      │ 45.0 │ 22  │ 101 │ 2026-10-10 │
│ 22  │ Dustin  │ 7      │ 45.0 │ 22  │ 102 │ 2026-10-10 │
│ 22  │ Dustin  │ 7      │ 45.0 │ 22  │ 103 │ 2026-10-08 │
│ 22  │ Dustin  │ 7      │ 45.0 │ 22  │ 104 │ 2026-10-07 │
│ 31  │ Lubber  │ 8      │ 55.5 │ 31  │ 102 │ 2026-11-10 │
│ ... (more rows) ...                                      │
└─────┴─────────┴────────┴──────┴─────┴─────┴────────────┘
```

**Note:** The `sid` column appears twice (once from SAILORS, once from RESERVES).

---

### 6.3 Natural Join (⋈)

**Symbol:** ⋈ (bowtie)

**Purpose:** Equi-join on **all common attributes**, then **remove duplicate columns**.

**Automatic behavior:**
1. Find all attributes with the same name in both relations
2. Join on equality of these attributes
3. Keep only one copy of common attributes in result

**Syntax:**
```
R ⋈ S
```

**Advantages:**
- Cleaner output (no duplicate columns)
- More concise notation
- Matches foreign key relationships naturally

#### Example 1: Natural Join Sailors and Reserves

**Query:** Join SAILORS and RESERVES

**Relational Algebra:**
```
SAILORS ⋈ RESERVES
```

**Steps:**
1. Common attribute: `sid`
2. Join condition: SAILORS.sid = RESERVES.sid
3. Result keeps only one `sid` column

**Result:**
```
┌─────┬─────────┬────────┬──────┬─────┬────────────┐
│ sid │ sname   │ rating │ age  │ bid │ day        │  ← Only ONE sid column
├─────┼─────────┼────────┼──────┼─────┼────────────┤
│ 22  │ Dustin  │ 7      │ 45.0 │ 101 │ 2026-10-10 │
│ 22  │ Dustin  │ 7      │ 45.0 │ 102 │ 2026-10-10 │
│ 22  │ Dustin  │ 7      │ 45.0 │ 103 │ 2026-10-08 │
│ 22  │ Dustin  │ 7      │ 45.0 │ 104 │ 2026-10-07 │
│ 31  │ Lubber  │ 8      │ 55.5 │ 102 │ 2026-11-10 │
│ 31  │ Lubber  │ 8      │ 55.5 │ 103 │ 2026-11-06 │
│ 31  │ Lubber  │ 8      │ 55.5 │ 104 │ 2026-11-12 │
│ 64  │ Horatio │ 7      │ 35.0 │ 101 │ 2026-09-05 │
│ 64  │ Horatio │ 7      │ 35.0 │ 102 │ 2026-09-08 │
│ 74  │ Horatio │ 9      │ 35.0 │ 103 │ 2026-09-08 │
└─────┴─────────┴────────┴──────┴─────┴────────────┘
```

**MySQL:**
```sql
SELECT *
FROM SAILORS
NATURAL JOIN RESERVES;
```

#### Example 2: Three-Way Natural Join

**Query:** Find sailor names and boat names for all reservations

**Relational Algebra:**
```
π_sname, bname(SAILORS ⋈ RESERVES ⋈ BOATS)
```

**Step-by-step:**
1. **SAILORS ⋈ RESERVES** - Join on `sid`
2. **Result ⋈ BOATS** - Join on `bid`
3. **π_sname, bname(...)** - Project sailor and boat names

**MySQL:**
```sql
SELECT DISTINCT S.sname, B.bname
FROM SAILORS S
NATURAL JOIN RESERVES R
NATURAL JOIN BOATS B;
```

**Result:**
```
┌─────────┬───────────┐
│ sname   │ bname     │
├─────────┼───────────┤
│ Dustin  │ Interlake │
│ Dustin  │ Clipper   │
│ Dustin  │ Marine    │
│ Lubber  │ Interlake │
│ Lubber  │ Clipper   │
│ Lubber  │ Marine    │
│ Horatio │ Interlake │
│ Horatio │ Clipper   │
└─────────┴───────────┘
```

#### Example 3: Natural Join with Banking Data

**Query:** Find customer names with their loan amounts

**Relational Algebra:**
```
π_customer_name, amount(BORROWER ⋈ LOAN)
```

**MySQL:**
```sql
SELECT B.customer_name, L.amount
FROM BORROWER B
NATURAL JOIN LOAN L;
```

**Result:**
```
┌───────────────┬────────┐
│ customer_name │ amount │
├───────────────┼────────┤
│ Jones         │ 1000   │
│ Smith         │ 900    │
│ Smith         │ 2000   │
│ Hayes         │ 1500   │
└───────────────┴────────┘
```

---

### 6.4 Outer Joins

**Purpose:** Preserve rows that don't have matching rows in the other relation by filling with **NULL** values.

![[Pasted image 20260313020635.png]]

#### Left Outer Join ($⟕$)

**Symbol:** $⟕$

**Purpose:** Keep **all rows from left relation**, match with right where possible, fill with NULL otherwise.

**Example:** Find all sailors and their reservations (include sailors with no reservations)

**Relational Algebra:**
```
SAILORS ⟕ RESERVES
```

**Result:**
```
┌─────┬─────────┬────────┬──────┬──────┬──────┬────────────┐
│ sid │ sname   │ rating │ age  │ bid  │ day  │            │
├─────┼─────────┼────────┼──────┼──────┼──────┼────────────┤
│ 22  │ Dustin  │ 7      │ 45.0 │ 101  │ ...  │            │
│ 29  │ Brutus  │ 1      │ 33.0 │ NULL │ NULL │            │  ← No reservations
│ 31  │ Lubber  │ 8      │ 55.5 │ 102  │ ...  │            │
│ 32  │ Andy    │ 8      │ 25.5 │ NULL │ NULL │            │  ← No reservations
│ ... (more rows) ...                                       │
└─────┴─────────┴────────┴──────┴──────┴──────┴────────────┘
```

**MySQL:**
```sql
SELECT *
FROM SAILORS S
LEFT OUTER JOIN RESERVES R ON S.sid = R.sid;
```

#### Right Outer Join ($⟖$)

**Symbol:** $⟖$

**Purpose:** Keep **all rows from right relation**, match with left where possible.

**MySQL:**
```sql
SELECT *
FROM RESERVES R
RIGHT OUTER JOIN SAILORS S ON R.sid = S.sid;
```

#### Full Outer Join ($⟗$)

**Symbol:** ⟗

**Purpose:** Keep **all rows from both relations**, match where possible.

**MySQL:**
```sql
-- MySQL doesn't have FULL OUTER JOIN directly
-- Simulate with UNION of LEFT and RIGHT joins

SELECT *
FROM SAILORS S
LEFT OUTER JOIN RESERVES R ON S.sid = R.sid

UNION

SELECT *
FROM SAILORS S
RIGHT OUTER JOIN RESERVES R ON S.sid = R.sid;
```

![[Pasted image 20260313020756.png]]

---

## 7. Division Operation (÷) 

**Symbol:** ÷

**Purpose:** Find entities related to **ALL** of a specific set (answers "for every" queries).

**This is the HARDEST and MOST TESTED operation.**

### Understanding Division

**Conceptual Definition:**

Given relations R(A, B) and S(B), the division R ÷ S produces relation T(A) containing all values of A that are associated with **EVERY** value of B in S.

**Formula:**
```
R ÷ S = {a | for all b in S, (a, b) in R}
```

**In SQL terms:** "Find A such that for every B in S, the pair (A, B) exists in R."

### Visual Example

**Relation R (Student-Course):**
```
┌─────────┬─────────┐
│ Student │ Course  │
├─────────┼─────────┤
│ Alice   │ DBMS    │
│ Alice   │ OS      │
│ Alice   │ Networks│
│ Bob     │ DBMS    │
│ Bob     │ OS      │
│ Carol   │ DBMS    │
└─────────┴─────────┘
```

**Relation S (Required Courses):**
```
┌─────────┐
│ Course  │
├─────────┤
│ DBMS    │
│ OS      │
└─────────┘
```

**R ÷ S (Students who took ALL required courses):**
```
┌─────────┐
│ Student │
├─────────┤
│ Alice   │  ← Has DBMS AND OS
│ Bob     │  ← Has DBMS AND OS
└─────────┘
```

Carol is NOT included because she only has DBMS, not OS.

### Division Using Basic Operations

**Formula 1 (Using Set Difference):**
```
R ÷ S = π_A(R) − π_A((π_A(R) × S) − R)
```

**Step-by-step explanation:**
1. `π_A(R)` - Get all unique values of A from R (all students)
2. `π_A(R) × S` - All possible (Student, Course) combinations
3. `(π_A(R) × S) − R` - Combinations that DON'T exist (missing courses)
4. `π_A(...)` - Students who have missing courses
5. `π_A(R) − ...` - Subtract bad students from all students

**Formula 2 (Using Double Negation):**
```
R ÷ S = {a | ¬∃b ∈ S such that (a, b) ∉ R}
```

"Find A such that there does NOT exist any B in S for which (A, B) is NOT in R."

---

### Division Examples

#### Example 1: Sailors Who Reserved ALL Boats

**Setup:**
```sql
-- Create view of all boats
CREATE VIEW ALL_BOATS AS
SELECT bid FROM BOATS;
```

**Query:** Find sailors who have reserved every boat

**Relational Algebra:**
```
π_sid, bid(RESERVES) ÷ π_bid(BOATS)
```

**Step-by-step calculation:**

**Step 1:** Get (sid, bid) pairs from RESERVES
```
┌─────┬─────┐
│ sid │ bid │
├─────┼─────┤
│ 22  │ 101 │
│ 22  │ 102 │
│ 22  │ 103 │
│ 22  │ 104 │
│ 31  │ 102 │
│ 31  │ 103 │
│ 31  │ 104 │
│ 64  │ 101 │
│ 64  │ 102 │
│ 74  │ 103 │
└─────┴─────┘
```

**Step 2:** Get all boat IDs
```
┌─────┐
│ bid │
├─────┤
│ 101 │
│ 102 │
│ 103 │
│ 104 │
└─────┘
```

**Step 3:** Find sids associated with ALL bids

Checking each sailor:
- **Sailor 22:** Has bids {101, 102, 103, 104} = ALL boats ✓
- **Sailor 31:** Has bids {102, 103, 104} = Missing 101 ✗
- **Sailor 64:** Has bids {101, 102} = Missing 103, 104 ✗
- **Sailor 74:** Has bids {103} = Missing 101, 102, 104 ✗

**Result:**
```
┌─────┐
│ sid │
├─────┤
│ 22  │
└─────┘
```

**MySQL Implementation:**

```sql
-- Method 1: Using NOT EXISTS (most efficient)
SELECT DISTINCT S.sid, S.sname
FROM SAILORS S
WHERE NOT EXISTS (
    -- Find a boat that this sailor has NOT reserved
    SELECT B.bid
    FROM BOATS B
    WHERE NOT EXISTS (
        -- Check if sailor S reserved boat B
        SELECT R.sid
        FROM RESERVES R
        WHERE R.sid = S.sid AND R.bid = B.bid
    )
);

-- Method 2: Using COUNT and GROUP BY
SELECT R.sid, S.sname
FROM RESERVES R
JOIN SAILORS S ON R.sid = S.sid
GROUP BY R.sid, S.sname
HAVING COUNT(DISTINCT R.bid) = (SELECT COUNT(*) FROM BOATS);

-- Method 3: Using set difference logic
SELECT DISTINCT S.sid, S.sname
FROM SAILORS S
WHERE S.sid NOT IN (
    -- Find sailors who are missing at least one boat
    SELECT S2.sid
    FROM SAILORS S2, BOATS B
    WHERE NOT EXISTS (
        SELECT *
        FROM RESERVES R
        WHERE R.sid = S2.sid AND R.bid = B.bid
    )
);
```

#### Example 2: Sailors Who Reserved ALL Red Boats

**Query:** Find sailors who have reserved every red boat

**Relational Algebra:**
```
π_sid, bid(RESERVES) ÷ π_bid(σ_color='red'(BOATS))
```

**Step-by-step:**

**Step 1:** Find all red boats
```sql
SELECT bid FROM BOATS WHERE color = 'red';
```
Result: {102, 104}

**Step 2:** Find sailors who reserved both 102 AND 104

**MySQL:**
```sql
SELECT DISTINCT S.sid, S.sname
FROM SAILORS S
WHERE NOT EXISTS (
    SELECT B.bid
    FROM BOATS B
    WHERE B.color = 'red'
      AND NOT EXISTS (
          SELECT R.sid
          FROM RESERVES R
          WHERE R.sid = S.sid AND R.bid = B.bid
      )
);

-- Alternative using COUNT
SELECT R.sid, S.sname
FROM RESERVES R
JOIN SAILORS S ON R.sid = S.sid
JOIN BOATS B ON R.bid = B.bid
WHERE B.color = 'red'
GROUP BY R.sid, S.sname
HAVING COUNT(DISTINCT R.bid) = (
    SELECT COUNT(*) FROM BOATS WHERE color = 'red'
);
```

**Result:**
```
┌─────┬────────┐
│ sid │ sname  │
├─────┼────────┤
│ 22  │ Dustin │
│ 31  │ Lubber │
└─────┴────────┘
```

#### Example 3: Banking - Customers with Accounts at ALL Branches

**Setup:**
```sql
-- Branches
CREATE VIEW BRANCH_LIST AS
SELECT DISTINCT branch_name FROM ACCOUNT;
```

**Query:** Find customers who have accounts at every branch

**Relational Algebra:**
```
π_customer_name, branch_name(DEPOSITOR ⋈ ACCOUNT) ÷ π_branch_name(BRANCH_LIST)
```

**MySQL:**
```sql
SELECT D.customer_name
FROM DEPOSITOR D
JOIN ACCOUNT A ON D.account_number = A.account_number
GROUP BY D.customer_name
HAVING COUNT(DISTINCT A.branch_name) = (
    SELECT COUNT(DISTINCT branch_name) FROM ACCOUNT
);
```

---

## 8. Extended Operations

### 8.1 Assignment (←)

**Symbol:** ← (left arrow)

**Purpose:** Break complex queries into **steps**, assign intermediate results to temporary variables.

**Advantages:**
- Improves readability
- Enables reuse of intermediate results
- Makes debugging easier

**Syntax:**
```
TempRelation ← Expression
```

#### Example: Multi-Step Query

**Query:** Find names of sailors who reserved a red boat

**Without Assignment (Complex):**
```
π_sname(σ_color='red'(BOATS) ⋈ RESERVES ⋈ SAILORS)
```

**With Assignment (Clear):**
```
RedBoats ← σ_color='red'(BOATS)
RedReserves ← RedBoats ⋈ RESERVES
Result ← π_sname(RedReserves ⋈ SAILORS)
```

**MySQL (Using CTEs - Common Table Expressions):**
```sql
WITH RedBoats AS (
    SELECT * FROM BOATS WHERE color = 'red'
),
RedReserves AS (
    SELECT * FROM RedBoats NATURAL JOIN RESERVES
)
SELECT DISTINCT S.sname
FROM RedReserves RR
JOIN SAILORS S ON RR.sid = S.sid;
```

---

### 8.2 Generalized Projection (π̂)

**Symbol:** π̂ (pi-hat)

**Purpose:** Project columns AND perform **arithmetic operations** or **computations**.

**Syntax:**
```
π̂_expression1, expression2, ...(Relation)
```

#### Example 1: Arithmetic Operations

**Query:** Calculate annual salary for each employee

**Relational Algebra:**
```
π̂_name, salary*12 AS annual_salary(EMPLOYEE)
```

**MySQL:**
```sql
SELECT name, salary * 12 AS annual_salary
FROM EMPLOYEE;
```

#### Example 2: String Operations

**Query:** Create full name and age in months

**Relational Algebra:**
```
π̂_first_name || ' ' || last_name AS full_name, age*12 AS age_months(PERSON)
```

**MySQL:**
```sql
SELECT 
    CONCAT(first_name, ' ', last_name) AS full_name,
    age * 12 AS age_months
FROM PERSON;
```

#### Example 3: Conditional Expressions

**Query:** Categorize sailors by rating

**Relational Algebra:**
```
π̂_sname, rating, 
   CASE 
       WHEN rating >= 8 THEN 'Excellent'
       WHEN rating >= 5 THEN 'Good'
       ELSE 'Average'
   END AS category
(SAILORS)
```

**MySQL:**
```sql
SELECT 
    sname,
    rating,
    CASE 
        WHEN rating >= 8 THEN 'Excellent'
        WHEN rating >= 5 THEN 'Good'
        ELSE 'Average'
    END AS category
FROM SAILORS;
```

---

### 8.3 Aggregate Functions (ℱ)

**Symbol:** ℱ (script F)

**Purpose:** Perform calculations on groups of tuples (sum, count, avg, min, max).

**Syntax:**
```
ℱ_function(attribute)(Relation)
ℱ_grouping_attributes; function(attribute)(Relation)
```

#### Basic Aggregate Functions

| Function | Symbol | Purpose | MySQL |
|----------|--------|---------|-------|
| **Count** | COUNT | Number of tuples | `COUNT(*)` |
| **Sum** | SUM | Sum of values | `SUM(column)` |
| **Average** | AVG | Average of values | `AVG(column)` |
| **Maximum** | MAX | Maximum value | `MAX(column)` |
| **Minimum** | MIN | Minimum value | `MIN(column)` |

#### Example 1: Simple Aggregates

**Query 1:** Count total number of sailors

**Relational Algebra:**
```
ℱ_COUNT(*)(SAILORS)
```

**MySQL:**
```sql
SELECT COUNT(*) AS total_sailors FROM SAILORS;
```

**Result:** `10`

**Query 2:** Average rating of all sailors

**Relational Algebra:**
```
ℱ_AVG(rating)(SAILORS)
```

**MySQL:**
```sql
SELECT AVG(rating) AS avg_rating FROM SAILORS;
```

**Result:** `6.3`

**Query 3:** Maximum age

**Relational Algebra:**
```
ℱ_MAX(age)(SAILORS)
```

**MySQL:**
```sql
SELECT MAX(age) AS max_age FROM SAILORS;
```

**Result:** `63.5`

#### Example 2: Grouping (GROUP BY)

**Query:** Count number of reservations per sailor

**Relational Algebra:**
```
ℱ_sid; COUNT(*) AS num_reservations(RESERVES)
```

**Explanation:**
- `sid` = grouping attribute (GROUP BY sid)
- `COUNT(*)` = aggregate function
- Result: (sid, num_reservations) pairs

**MySQL:**
```sql
SELECT sid, COUNT(*) AS num_reservations
FROM RESERVES
GROUP BY sid;
```

**Result:**
```
┌─────┬──────────────────┐
│ sid │num_reservations  │
├─────┼──────────────────┤
│ 22  │ 4                │
│ 31  │ 3                │
│ 64  │ 2                │
│ 74  │ 1                │
└─────┴──────────────────┘
```

#### Example 3: Multiple Aggregates with Grouping

**Query:** For each rating, find count of sailors and average age

**Relational Algebra:**
```
ℱ_rating; COUNT(*) AS num_sailors, AVG(age) AS avg_age(SAILORS)
```

**MySQL:**
```sql
SELECT 
    rating,
    COUNT(*) AS num_sailors,
    AVG(age) AS avg_age
FROM SAILORS
GROUP BY rating
ORDER BY rating;
```

**Result:**
```
┌────────┬─────────────┬─────────┐
│ rating │ num_sailors │ avg_age │
├────────┼─────────────┼─────────┤
│ 1      │ 1           │ 33.0    │
│ 3      │ 2           │ 44.5    │
│ 7      │ 2           │ 40.0    │
│ 8      │ 2           │ 40.5    │
│ 9      │ 1           │ 35.0    │
│ 10     │ 2           │ 25.5    │
└────────┴─────────────┴─────────┘
```

#### Example 4: HAVING Clause

**Query:** Find ratings with more than one sailor

**Relational Algebra:**
```
σ_{num_sailors > 1}(ℱ_rating; COUNT(*) AS num_sailors(SAILORS))
```

**MySQL:**
```sql
SELECT rating, COUNT(*) AS num_sailors
FROM SAILORS
GROUP BY rating
HAVING COUNT(*) > 1;
```

**Result:**
```
┌────────┬─────────────┐
│ rating │ num_sailors │
├────────┼─────────────┤
│ 3      │ 2           │
│ 7      │ 2           │
│ 8      │ 2           │
│ 10     │ 2           │
└────────┴─────────────┘
```

#### Example 5: Aggregate with Join

**Query:** For each boat, find total number of reservations

**Relational Algebra:**
```
ℱ_bname, color; COUNT(*) AS num_reservations(BOATS ⋈ RESERVES)
```

**MySQL:**
```sql
SELECT 
    B.bname,
    B.color,
    COUNT(*) AS num_reservations
FROM BOATS B
JOIN RESERVES R ON B.bid = R.bid
GROUP BY B.bid, B.bname, B.color;
```

**Result:**
```
┌───────────┬────────┬──────────────────┐
│ bname     │ color  │num_reservations  │
├───────────┼────────┼──────────────────┤
│ Interlake │ blue   │ 2                │
│ Interlake │ red    │ 4                │
│ Clipper   │ green  │ 3                │
│ Marine    │ red    │ 1                │
└───────────┴────────┴──────────────────┘
```

---

## 9. Database Modification Operations {#modification}

### 9.1 Deletion

**Purpose:** Remove tuples from a relation.

**Syntax:**
```
Relation ← Relation − Expression
```

#### Example 1: Delete All Sailors

**Relational Algebra:**
```
SAILORS ← ∅
```

**MySQL:**
```sql
DELETE FROM SAILORS;
```

#### Example 2: Delete Sailors with Rating < 5

**Relational Algebra:**
```
SAILORS ← SAILORS − σ_rating < 5(SAILORS)
```

**Equivalent:**
```
SAILORS ← σ_rating ≥ 5(SAILORS)
```

**MySQL:**
```sql
DELETE FROM SAILORS
WHERE rating < 5;
```

#### Example 3: Delete All Reservations for Boat 103

**Relational Algebra:**
```
RESERVES ← RESERVES − σ_bid=103(RESERVES)
```

**MySQL:**
```sql
DELETE FROM RESERVES
WHERE bid = 103;
```

#### Example 4: Delete Sailors Who Never Reserved

**Relational Algebra:**
```
SAILORS ← SAILORS − (SAILORS − π_sid(RESERVES))
```

**Simplified:**
```
SAILORS ← π_sid(RESERVES) ⋈ SAILORS
```

**MySQL:**
```sql
DELETE FROM SAILORS
WHERE sid NOT IN (SELECT DISTINCT sid FROM RESERVES);
```

---

### 9.2 Insertion

**Purpose:** Add new tuples to a relation.

**Syntax:**
```
Relation ← Relation ∪ Expression
```

#### Example 1: Insert Single Tuple

**Relational Algebra:**
```
SAILORS ← SAILORS ∪ {(99, 'Charlie', 5, 28.0)}
```

**MySQL:**
```sql
INSERT INTO SAILORS VALUES (99, 'Charlie', 5, 28.0);
```

#### Example 2: Insert Multiple Tuples

**Relational Algebra:**
```
SAILORS ← SAILORS ∪ {
    (100, 'David', 7, 30.0),
    (101, 'Emma', 9, 27.5)
}
```

**MySQL:**
```sql
INSERT INTO SAILORS VALUES
    (100, 'David', 7, 30.0),
    (101, 'Emma', 9, 27.5);
```

#### Example 3: Insert Result of Query

**Query:** Create a new table of high-rated sailors (rating ≥ 8)

**Relational Algebra:**
```
HIGH_RATED_SAILORS ← σ_rating ≥ 8(SAILORS)
```

**MySQL:**
```sql
CREATE TABLE HIGH_RATED_SAILORS AS
SELECT * FROM SAILORS WHERE rating >= 8;

-- Or insert into existing table
INSERT INTO HIGH_RATED_SAILORS
SELECT * FROM SAILORS WHERE rating >= 8;
```

#### Example 4: Insert with Computation

**Query:** Give 10% raise to all sailors and insert as bonus

**MySQL:**
```sql
INSERT INTO SALARY_BONUS (sid, bonus_amount)
SELECT sid, salary * 0.10
FROM SAILORS;
```

---

### 9.3 Update

**Purpose:** Modify attribute values of existing tuples.

**Syntax:**
```
Relation ← π̂_attr1, attr2, ..., modified_attr(Relation)
```

#### Example 1: Update Single Attribute

**Query:** Increase rating of sailor 22 by 1

**Relational Algebra:**
```
SAILORS ← π̂_sid, sname, rating+1, age(σ_sid=22(SAILORS)) 
          ∪ σ_sid≠22(SAILORS)
```

**MySQL:**
```sql
UPDATE SAILORS
SET rating = rating + 1
WHERE sid = 22;
```

#### Example 2: Update Multiple Attributes

**Query:** Update sailor 31: new rating 9, new age 56.0

**MySQL:**
```sql
UPDATE SAILORS
SET rating = 9, age = 56.0
WHERE sid = 31;
```

#### Example 3: Conditional Update

**Query:** Give 5% raise to employees in Downtown branch

**MySQL:**
```sql
UPDATE EMPLOYEE E
SET salary = salary * 1.05
WHERE branch_name = 'Downtown';
```

#### Example 4: Update Using Subquery

**Query:** Set rating to average rating for sailors older than 50

**MySQL:**
```sql
UPDATE SAILORS
SET rating = (SELECT AVG(rating) FROM SAILORS)
WHERE age > 50;
```

#### Example 5: Update with Join

**Query:** Increase balance by 6% for accounts in Perryridge branch

**MySQL:**
```sql
UPDATE ACCOUNT A
JOIN (SELECT account_number FROM ACCOUNT WHERE branch_name = 'Perryridge') AS P
  ON A.account_number = P.account_number
SET A.balance = A.balance * 1.06;
```

---

## 10. Complex Query Examples {#complex-examples}

### Example 1: Find Sailors Who Reserved Red OR Green Boats

**English:** Find names of sailors who reserved a red boat or a green boat.

**Relational Algebra:**
```
RedOrGreenBoats ← σ_color='red' ∨ color='green'(BOATS)
ReservedRedOrGreen ← π_sid(RedOrGreenBoats ⋈ RESERVES)
Result ← π_sname(ReservedRedOrGreen ⋈ SAILORS)
```

**One-liner:**
```
π_sname((σ_color='red' ∨ color='green'(BOATS)) ⋈ RESERVES ⋈ SAILORS)
```

**MySQL:**
```sql
SELECT DISTINCT S.sname
FROM SAILORS S
JOIN RESERVES R ON S.sid = R.sid
JOIN BOATS B ON R.bid = B.bid
WHERE B.color IN ('red', 'green');
```

---

### Example 2: Find Sailors Who Reserved Red AND Green Boats

**English:** Find names of sailors who reserved both a red boat AND a green boat.

**Relational Algebra (Using Intersection):**
```
RedBoatSailors ← π_sid(σ_color='red'(BOATS) ⋈ RESERVES)
GreenBoatSailors ← π_sid(σ_color='green'(BOATS) ⋈ RESERVES)
BothColorSailors ← RedBoatSailors ∩ GreenBoatSailors
Result ← π_sname(BothColorSailors ⋈ SAILORS)
```

**MySQL:**
```sql
SELECT DISTINCT S.sname
FROM SAILORS S
WHERE S.sid IN (
    SELECT R.sid FROM RESERVES R
    JOIN BOATS B ON R.bid = B.bid
    WHERE B.color = 'red'
)
AND S.sid IN (
    SELECT R.sid FROM RESERVES R
    JOIN BOATS B ON R.bid = B.bid
    WHERE B.color = 'green'
);

-- Alternative using COUNT
SELECT S.sname
FROM SAILORS S
JOIN RESERVES R ON S.sid = R.sid
JOIN BOATS B ON R.bid = B.bid
WHERE B.color IN ('red', 'green')
GROUP BY S.sid, S.sname
HAVING COUNT(DISTINCT B.color) = 2;
```

---

### Example 3: Find Sailors Who Reserved Red BUT NOT Green Boats

**English:** Find sailors who reserved at least one red boat but no green boats.

**Relational Algebra:**
```
RedBoatSailors ← π_sid(σ_color='red'(BOATS) ⋈ RESERVES)
GreenBoatSailors ← π_sid(σ_color='green'(BOATS) ⋈ RESERVES)
RedOnlySailors ← RedBoatSailors − GreenBoatSailors
Result ← π_sname(RedOnlySailors ⋈ SAILORS)
```

**MySQL:**
```sql
SELECT DISTINCT S.sname
FROM SAILORS S
JOIN RESERVES R ON S.sid = R.sid
JOIN BOATS B ON R.bid = B.bid
WHERE B.color = 'red'
  AND S.sid NOT IN (
      SELECT R2.sid
      FROM RESERVES R2
      JOIN BOATS B2 ON R2.bid = B2.bid
      WHERE B2.color = 'green'
  );
```

---

### Example 4: Find Oldest Sailor

**English:** Find the name of the oldest sailor.

**Relational Algebra:**
```
MaxAge ← ℱ_MAX(age)(SAILORS)
Result ← π_sname(σ_age=MaxAge.max_age(SAILORS × MaxAge))
```

**MySQL:**
```sql
SELECT sname
FROM SAILORS
WHERE age = (SELECT MAX(age) FROM SAILORS);
```

---

### Example 5: Find Sailors with Rating Higher Than All Sailors Named 'Horatio'

**English:** Find sailors whose rating is greater than every sailor named Horatio.

**Relational Algebra:**
```
HoratioMaxRating ← ℱ_MAX(rating)(σ_sname='Horatio'(SAILORS))
Result ← π_sname(σ_rating > HoratioMaxRating.max_rating(SAILORS))
```

**MySQL:**
```sql
SELECT sname
FROM SAILORS
WHERE rating > ALL (
    SELECT rating 
    FROM SAILORS 
    WHERE sname = 'Horatio'
);

-- Alternative
SELECT sname
FROM SAILORS
WHERE rating > (
    SELECT MAX(rating) 
    FROM SAILORS 
    WHERE sname = 'Horatio'
);
```

---

### Example 6: Find Average Age Per Rating, Only for Ratings with 2+ Sailors

**English:** For each rating level that has at least 2 sailors, find the average age.

**Relational Algebra:**
```
GroupedData ← ℱ_rating; COUNT(*) AS cnt, AVG(age) AS avg_age(SAILORS)
Result ← σ_cnt ≥ 2(GroupedData)
```

**MySQL:**
```sql
SELECT rating, AVG(age) AS avg_age
FROM SAILORS
GROUP BY rating
HAVING COUNT(*) >= 2;
```

---

### Example 7: Find Boats Reserved by All Sailors with Rating 10

**English:** Find boats that have been reserved by every sailor with rating 10.

**Relational Algebra:**
```
Rating10Sailors ← π_sid(σ_rating=10(SAILORS))
BoatSailorPairs ← π_bid, sid(RESERVES)
Result ← π_bid(BoatSailorPairs ÷ Rating10Sailors)
```

**MySQL:**
```sql
SELECT R.bid
FROM RESERVES R
GROUP BY R.bid
HAVING COUNT(DISTINCT CASE WHEN S.rating = 10 THEN R.sid END) = (
    SELECT COUNT(*) FROM SAILORS WHERE rating = 10
)
AND COUNT(DISTINCT CASE WHEN S.rating = 10 THEN R.sid END) > 0;

-- Clearer version
SELECT B.bid, B.bname
FROM BOATS B
WHERE NOT EXISTS (
    SELECT S.sid
    FROM SAILORS S
    WHERE S.rating = 10
      AND NOT EXISTS (
          SELECT R.sid
          FROM RESERVES R
          WHERE R.sid = S.sid AND R.bid = B.bid
      )
);
```

---

### Example 8: Find Pairs of Sailors with Same Rating

**English:** Find all pairs of different sailors who have the same rating.

**Relational Algebra:**
```
S1 ← ρ_S1(sid1, sname1, rating1, age1)(SAILORS)
S2 ← ρ_S2(sid2, sname2, rating2, age2)(SAILORS)
SameRating ← σ_rating1 = rating2 ∧ sid1 < sid2(S1 × S2)
Result ← π_sname1, sname2, rating1(SameRating)
```

**MySQL:**
```sql
SELECT 
    S1.sname AS sailor1,
    S2.sname AS sailor2,
    S1.rating
FROM SAILORS S1
JOIN SAILORS S2 ON S1.rating = S2.rating AND S1.sid < S2.sid
ORDER BY S1.rating, S1.sname;
```

---

## 11. Translation Guide: Relational Algebra ↔ SQL {#sql-translation}

### Quick Reference Table

| Relational Algebra | SQL Equivalent | Example |
|-------------------|----------------|---------|
| **σ_condition(R)** | `SELECT * FROM R WHERE condition` | σ_age>25(Person) → `WHERE age > 25` |
| **π_A,B(R)** | `SELECT DISTINCT A, B FROM R` | π_name,age(Person) |
| **R ∪ S** | `SELECT * FROM R UNION SELECT * FROM S` | R ∪ S |
| **R ∩ S** | `SELECT * FROM R INTERSECT SELECT * FROM S` | R ∩ S |
| **R − S** | `SELECT * FROM R EXCEPT SELECT * FROM S` | R − S |
| **R × S** | `SELECT * FROM R, S` or `R CROSS JOIN S` | R × S |
| **R ⋈ S** | `SELECT * FROM R NATURAL JOIN S` | R ⋈ S |
| **R ⋈_R.A=S.B S** | `SELECT * FROM R JOIN S ON R.A = S.B` | Equi-join |
| **ρ_NewName(R)** | `FROM R AS NewName` | Rename |
| **ℱ_COUNT(*)(R)** | `SELECT COUNT(*) FROM R` | Count |
| **ℱ_A;SUM(B)(R)** | `SELECT A, SUM(B) FROM R GROUP BY A` | Aggregate |

---

### Step-by-Step Translation Examples

#### Example 1: Simple Selection + Projection

**English:** Find names of sailors older than 40

**Relational Algebra:**
```
π_sname(σ_age > 40(SAILORS))
```

**Translation Steps:**
1. Start inside-out: σ_age > 40(SAILORS)
2. SQL: `SELECT * FROM SAILORS WHERE age > 40`
3. Add projection: π_sname
4. SQL: `SELECT sname FROM SAILORS WHERE age > 40`
5. Add DISTINCT (relational algebra default)
6. Final: `SELECT DISTINCT sname FROM SAILORS WHERE age > 40`

**Final SQL:**
```sql
SELECT DISTINCT sname
FROM SAILORS
WHERE age > 40;
```

---

#### Example 2: Join with Selection

**English:** Find names of sailors who reserved boat 103

**Relational Algebra:**
```
π_sname(σ_bid=103(RESERVES) ⋈ SAILORS)
```

**Translation Steps:**
1. σ_bid=103(RESERVES) → `WHERE bid = 103`
2. Join with SAILORS: `JOIN SAILORS`
3. Common attribute: sid
4. Project sname: `SELECT sname`

**Final SQL:**
```sql
SELECT DISTINCT S.sname
FROM RESERVES R
JOIN SAILORS S ON R.sid = S.sid
WHERE R.bid = 103;
```

---

#### Example 3: Set Difference

**English:** Find sailors who never made a reservation

**Relational Algebra:**
```
π_sid(SAILORS) − π_sid(RESERVES)
```

**Translation:**
```sql
SELECT sid FROM SAILORS
EXCEPT
SELECT sid FROM RESERVES;

-- Alternative
SELECT S.sid
FROM SAILORS S
WHERE S.sid NOT IN (SELECT sid FROM RESERVES);
```

---

#### Example 4: Division

**English:** Find sailors who reserved all boats

**Relational Algebra:**
```
π_sid,bid(RESERVES) ÷ π_bid(BOATS)
```

**Translation:**
```sql
SELECT DISTINCT S.sid
FROM SAILORS S
WHERE NOT EXISTS (
    SELECT B.bid
    FROM BOATS B
    WHERE NOT EXISTS (
        SELECT R.sid
        FROM RESERVES R
        WHERE R.sid = S.sid AND R.bid = B.bid
    )
);
```

---

## Summary Tables

### Operator Precedence

1. **σ, π, ρ** (Unary operators - highest precedence)
2. **×, ⋈** (Cartesian product, Join)
3. **∩** (Intersection)
4. **∪, −** (Union, Difference - lowest precedence)

### Operator Properties

| Operator | Commutative | Associative | Idempotent |
|----------|-------------|-------------|------------|
| **σ** | ✓ Yes | ✓ Yes | ✓ Yes |
| **π** | ✗ No | ✗ No | ✓ Yes |
| **∪** | ✓ Yes | ✓ Yes | ✓ Yes |
| **∩** | ✓ Yes | ✓ Yes | ✓ Yes |
| **−** | ✗ No | ✗ No | ✗ No |
| **×** | ✓ Yes | ✓ Yes | ✗ No |
| **⋈** | ✓ Yes | ✓ Yes | ✗ No |

---

## Practice Problems

### Problem 1
**Find names of sailors who reserved a red boat AND have rating > 7**

<details>
<summary>Click to see solution</summary>

**Relational Algebra:**
```
π_sname((σ_color='red'(BOATS) ⋈ RESERVES) ⋈ σ_rating>7(SAILORS))
```

**MySQL:**
```sql
SELECT DISTINCT S.sname
FROM SAILORS S
JOIN RESERVES R ON S.sid = R.sid
JOIN BOATS B ON R.bid = B.bid
WHERE B.color = 'red' AND S.rating > 7;
```
</details>

### Problem 2
**Find average age of sailors for each rating level**

<details>
<summary>Click to see solution</summary>

**Relational Algebra:**
```
ℱ_rating; AVG(age) AS avg_age(SAILORS)
```

**MySQL:**
```sql
SELECT rating, AVG(age) AS avg_age
FROM SAILORS
GROUP BY rating;
```
</details>

### Problem 3
**Find boats that have never been reserved**

<details>
<summary>Click to see solution</summary>

**Relational Algebra:**
```
π_bid(BOATS) − π_bid(RESERVES)
```

**MySQL:**
```sql
SELECT bid FROM BOATS
EXCEPT
SELECT bid FROM RESERVES;

-- Alternative
SELECT B.bid
FROM BOATS B
WHERE B.bid NOT IN (SELECT bid FROM RESERVES);
```
</details>

---

# Complete Guide to Views, Assertions, and Bitmap Indexing in DBMS

## Table of Contents
1. [Views](#views)
2. [Assertions](#assertions)
3. [Bitmap Indexing](#bitmap-indexing)
4. [Materialized Views](#materialized-views)
5. [Complete Examples](#examples)

---

## 1. Views

### What is a View?

A **view** is a **virtual table** based on the result of a SQL query. It doesn't store data physically; instead, it stores the query definition.

**Key Characteristics:**
- **Virtual relation** - exists logically, not physically
- **Derived data** - computed from base tables when queried
- **Dynamic** - automatically reflects changes in underlying tables
- **Security layer** - hides sensitive data
- **Simplification** - provides simplified interface to complex queries

### Why Use Views?

| Purpose | Example |
|---------|---------|
| **Security** | Hide salary information from certain users |
| **Simplification** | Present complex joins as simple tables |
| **Data Independence** | Change underlying schema without affecting applications |
| **Customization** | Different users see different data representations |
| **Reusability** | Encapsulate frequently used queries |

---

### Creating Views

**Syntax:**
```sql
CREATE VIEW view_name AS
SELECT column1, column2, ...
FROM table_name
WHERE condition;
```

### Sample Database Schema

```sql
-- Base Tables
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2),
    dept_id INT,
    manager_id INT,
    hire_date DATE
);

CREATE TABLE DEPARTMENT (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50),
    location VARCHAR(100)
);

CREATE TABLE CUSTOMER (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    city VARCHAR(50),
    credit_score INT
);

CREATE TABLE LOAN (
    loan_number VARCHAR(20) PRIMARY KEY,
    branch_name VARCHAR(50),
    amount DECIMAL(12,2),
    loan_type VARCHAR(20)
);

CREATE TABLE BORROWER (
    customer_id INT,
    loan_number VARCHAR(20),
    PRIMARY KEY (customer_id, loan_number),
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id),
    FOREIGN KEY (loan_number) REFERENCES LOAN(loan_number)
);

CREATE TABLE ACCOUNT (
    account_number VARCHAR(20) PRIMARY KEY,
    branch_name VARCHAR(50),
    balance DECIMAL(12,2)
);

CREATE TABLE DEPOSITOR (
    customer_id INT,
    account_number VARCHAR(20),
    PRIMARY KEY (customer_id, account_number),
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id),
    FOREIGN KEY (account_number) REFERENCES ACCOUNT(account_number)
);

-- Sample Data
INSERT INTO EMPLOYEE VALUES
    (101, 'John Smith', 75000, 1, NULL, '2020-01-15'),
    (102, 'Jane Doe', 65000, 1, 101, '2020-03-20'),
    (103, 'Bob Wilson', 85000, 2, NULL, '2019-06-10'),
    (104, 'Alice Brown', 55000, 2, 103, '2021-02-01'),
    (105, 'Charlie Davis', 95000, 1, 101, '2018-11-05');

INSERT INTO DEPARTMENT VALUES
    (1, 'Engineering', 'Building A'),
    (2, 'Sales', 'Building B'),
    (3, 'HR', 'Building C');

INSERT INTO CUSTOMER VALUES
    (201, 'Michael Johnson', 'New York', 720),
    (202, 'Sarah Williams', 'Boston', 680),
    (203, 'David Miller', 'Chicago', 750);

INSERT INTO LOAN VALUES
    ('L-101', 'Downtown', 50000, 'Personal'),
    ('L-102', 'Uptown', 100000, 'Business'),
    ('L-103', 'Downtown', 75000, 'Home');

INSERT INTO BORROWER VALUES
    (201, 'L-101'),
    (202, 'L-102'),
    (203, 'L-103');

INSERT INTO ACCOUNT VALUES
    ('A-101', 'Downtown', 5000),
    ('A-102', 'Uptown', 8000),
    ('A-103', 'Downtown', 1200);

INSERT INTO DEPOSITOR VALUES
    (201, 'A-101'),
    (202, 'A-102'),
    (203, 'A-103');
```

---

### Example 1: Simple View (Hide Sensitive Data)

**Requirement:** Show employee information without salary details

```sql
-- Create view without salary
CREATE VIEW EMPLOYEE_PUBLIC AS
SELECT emp_id, name, dept_id, hire_date
FROM EMPLOYEE;

-- Query the view
SELECT * FROM EMPLOYEE_PUBLIC;
```

**Result:**
```
┌────────┬───────────────┬─────────┬────────────┐
│ emp_id │ name          │ dept_id │ hire_date  │
├────────┼───────────────┼─────────┼────────────┤
│ 101    │ John Smith    │ 1       │ 2020-01-15 │
│ 102    │ Jane Doe      │ 1       │ 2020-03-20 │
│ 103    │ Bob Wilson    │ 2       │ 2019-06-10 │
│ 104    │ Alice Brown   │ 2       │ 2021-02-01 │
│ 105    │ Charlie Davis │ 1       │ 2018-11-05 │
└────────┴───────────────┴─────────┴────────────┘
```

**Benefit:** Users querying this view cannot see salary information.

---

### Example 2: View with JOIN (Simplification)

**Requirement:** Simplify employee-department query

```sql
-- Create view joining tables
CREATE VIEW EMPLOYEE_DEPT_INFO AS
SELECT 
    e.emp_id,
    e.name AS employee_name,
    e.salary,
    d.dept_name,
    d.location
FROM EMPLOYEE e
JOIN DEPARTMENT d ON e.dept_id = d.dept_id;

-- Query the view
SELECT * FROM EMPLOYEE_DEPT_INFO WHERE dept_name = 'Engineering';
```

**Result:**
```
┌────────┬───────────────┬─────────┬─────────────┬───────────┐
│ emp_id │ employee_name │ salary  │ dept_name   │ location  │
├────────┼───────────────┼─────────┼─────────────┼───────────┤
│ 101    │ John Smith    │ 75000   │ Engineering │ Building A│
│ 102    │ Jane Doe      │ 65000   │ Engineering │ Building A│
│ 105    │ Charlie Davis │ 95000   │ Engineering │ Building A│
└────────┴───────────────┴─────────┴─────────────┴───────────┘
```

**Benefit:** Users don't need to know the JOIN syntax; they query a simple view.

---

### Example 3: View with Aggregation

**Requirement:** Show department-wise average salary

```sql
-- Create aggregate view
CREATE VIEW DEPT_SALARY_STATS AS
SELECT 
    d.dept_name,
    COUNT(e.emp_id) AS num_employees,
    AVG(e.salary) AS avg_salary,
    MIN(e.salary) AS min_salary,
    MAX(e.salary) AS max_salary
FROM DEPARTMENT d
LEFT JOIN EMPLOYEE e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name;

-- Query the view
SELECT * FROM DEPT_SALARY_STATS ORDER BY avg_salary DESC;
```

**Result:**
```
┌─────────────┬───────────────┬────────────┬────────────┬────────────┐
│ dept_name   │ num_employees │ avg_salary │ min_salary │ max_salary │
├─────────────┼───────────────┼────────────┼────────────┼────────────┤
│ Engineering │ 3             │ 78333.33   │ 65000      │ 95000      │
│ Sales       │ 2             │ 70000.00   │ 55000      │ 85000      │
│ HR          │ 0             │ NULL       │ NULL       │ NULL       │
└─────────────┴───────────────┴────────────┴────────────┴────────────┘
```

---

### Example 4: View with Computed Columns

**Requirement:** Show employee tenure

```sql
-- Create view with calculated column
CREATE VIEW EMPLOYEE_TENURE AS
SELECT 
    emp_id,
    name,
    hire_date,
    TIMESTAMPDIFF(YEAR, hire_date, CURDATE()) AS years_of_service,
    salary,
    CASE 
        WHEN TIMESTAMPDIFF(YEAR, hire_date, CURDATE()) >= 5 THEN 'Senior'
        WHEN TIMESTAMPDIFF(YEAR, hire_date, CURDATE()) >= 2 THEN 'Mid-Level'
        ELSE 'Junior'
    END AS seniority_level
FROM EMPLOYEE;

-- Query the view
SELECT * FROM EMPLOYEE_TENURE ORDER BY years_of_service DESC;
```

**Result:**
```
┌────────┬───────────────┬────────────┬──────────────────┬────────┬─────────────────┐
│ emp_id │ name          │ hire_date  │ years_of_service │ salary │ seniority_level │
├────────┼───────────────┼────────────┼──────────────────┼────────┼─────────────────┤
│ 105    │ Charlie Davis │ 2018-11-05 │ 7                │ 95000  │ Senior          │
│ 103    │ Bob Wilson    │ 2019-06-10 │ 6                │ 85000  │ Senior          │
│ 101    │ John Smith    │ 2020-01-15 │ 6                │ 75000  │ Senior          │
│ 102    │ Jane Doe      │ 2020-03-20 │ 5                │ 65000  │ Senior          │
│ 104    │ Alice Brown   │ 2021-02-01 │ 4                │ 55000  │ Mid-Level       │
└────────┴───────────────┴────────────┴──────────────────┴────────┴─────────────────┘
```

---

### Example 5: Parameterized View (Using WHERE)

**Requirement:** View for high-performing customers

```sql
-- Create view with condition
CREATE VIEW HIGH_CREDIT_CUSTOMERS AS
SELECT 
    customer_id,
    customer_name,
    city,
    credit_score
FROM CUSTOMER
WHERE credit_score >= 700;

-- Query the view
SELECT * FROM HIGH_CREDIT_CUSTOMERS;
```

**Result:**
```
┌─────────────┬─────────────────┬─────────┬──────────────┐
│ customer_id │ customer_name   │ city    │ credit_score │
├─────────────┼─────────────────┼─────────┼──────────────┤
│ 201         │ Michael Johnson │ New York│ 720          │
│ 203         │ David Miller    │ Chicago │ 750          │
└─────────────┴─────────────────┴─────────┴──────────────┘
```

---

### View Operations

#### 1. Querying Views

Views can be queried like regular tables:

```sql
-- Simple SELECT
SELECT * FROM EMPLOYEE_PUBLIC;

-- With WHERE clause
SELECT * FROM EMPLOYEE_PUBLIC WHERE dept_id = 1;

-- With JOIN (view joining with table)
SELECT 
    ep.name,
    d.dept_name
FROM EMPLOYEE_PUBLIC ep
JOIN DEPARTMENT d ON ep.dept_id = d.dept_id;

-- Nested view query
SELECT dept_name, avg_salary
FROM DEPT_SALARY_STATS
WHERE avg_salary > 70000;
```

---

#### 2. Updating Views

**View updates** are allowed under certain conditions but have restrictions.

##### Updatable View Requirements:

A view is **updatable** if:
1. ✓ Defined on a **single base table**
2. ✓ Contains **no aggregates** (SUM, AVG, COUNT, etc.)
3. ✓ Contains **no GROUP BY** or **HAVING**
4. ✓ Contains **no DISTINCT**
5. ✓ Contains **no subqueries** in SELECT clause
6. ✓ Contains **no UNION** operations

##### Example: Updatable View

```sql
-- Create updatable view
CREATE VIEW ENGINEERING_EMPLOYEES AS
SELECT emp_id, name, salary, dept_id
FROM EMPLOYEE
WHERE dept_id = 1;

-- UPDATE through view (✓ Allowed)
UPDATE ENGINEERING_EMPLOYEES
SET salary = salary * 1.10
WHERE emp_id = 102;

-- Verify update
SELECT * FROM EMPLOYEE WHERE emp_id = 102;
```

**Result:** Jane Doe's salary updated to 71,500 (65,000 * 1.10)

##### Example: INSERT through View

```sql
-- INSERT through view (✓ Allowed)
INSERT INTO ENGINEERING_EMPLOYEES (emp_id, name, salary, dept_id)
VALUES (106, 'Tom Anderson', 70000, 1);

-- Verify insertion
SELECT * FROM EMPLOYEE WHERE emp_id = 106;
```

**Result:** New employee added to EMPLOYEE table

##### Example: DELETE through View

```sql
-- DELETE through view (✓ Allowed)
DELETE FROM ENGINEERING_EMPLOYEES
WHERE emp_id = 106;

-- Verify deletion
SELECT * FROM EMPLOYEE WHERE emp_id = 106;
```

**Result:** Employee removed from EMPLOYEE table

---

##### Example: Non-Updatable View (Aggregate)

```sql
-- This view is NOT updatable (has aggregation)
CREATE VIEW DEPT_AVG_SALARY AS
SELECT dept_id, AVG(salary) AS avg_salary
FROM EMPLOYEE
GROUP BY dept_id;

-- Try to UPDATE (❌ Will FAIL)
UPDATE DEPT_AVG_SALARY
SET avg_salary = 80000
WHERE dept_id = 1;
```

**Error:** Cannot update aggregate view

**Why?** How would the system translate "set average to 80000" into updates on individual employee salaries?

---

##### Example: View with NULL Challenge

```sql
-- View missing some columns
CREATE VIEW EMPLOYEE_BASIC AS
SELECT emp_id, name, dept_id
FROM EMPLOYEE;

-- Try to INSERT (⚠️ Problem: missing salary)
INSERT INTO EMPLOYEE_BASIC (emp_id, name, dept_id)
VALUES (107, 'Lisa Green', 2);

-- What happens in base table?
SELECT * FROM EMPLOYEE WHERE emp_id = 107;
```

**Result:**
```
┌────────┬────────────┬─────────┬─────────┬────────────┬───────────┐
│ emp_id │ name       │ salary  │ dept_id │ manager_id │ hire_date │
├────────┼────────────┼─────────┼─────────┼────────────┼───────────┤
│ 107    │ Lisa Green │ NULL    │ 2       │ NULL       │ NULL      │
└────────┴────────────┴─────────┴─────────┴────────────┴───────────┘
```

**Issue:** Missing columns get NULL values, which may violate constraints.

---

##### WITH CHECK OPTION

Prevents updates/inserts that would make rows disappear from the view.

```sql
-- Create view with check option
CREATE VIEW ENGINEERING_EMPLOYEES_SAFE AS
SELECT emp_id, name, salary, dept_id
FROM EMPLOYEE
WHERE dept_id = 1
WITH CHECK OPTION;

-- Try to change dept_id (❌ Will FAIL)
UPDATE ENGINEERING_EMPLOYEES_SAFE
SET dept_id = 2
WHERE emp_id = 102;
```

**Error:** CHECK OPTION violated

**Reason:** Changing dept_id to 2 would remove the row from the view (which shows only dept_id = 1), so it's blocked.

---

#### 3. Dropping Views

```sql
-- Drop a view
DROP VIEW IF EXISTS EMPLOYEE_PUBLIC;

-- Drop multiple views
DROP VIEW IF EXISTS EMPLOYEE_PUBLIC, DEPT_SALARY_STATS;
```

---

#### 4. Altering Views

MySQL doesn't have `ALTER VIEW`, so you must `CREATE OR REPLACE`:

```sql
-- Replace existing view
CREATE OR REPLACE VIEW EMPLOYEE_PUBLIC AS
SELECT emp_id, name, dept_id, hire_date, salary  -- Added salary
FROM EMPLOYEE;
```

---

### View Performance Considerations

#### How Views Work Internally

**When you query a view:**

```sql
SELECT * FROM EMPLOYEE_DEPT_INFO WHERE dept_name = 'Engineering';
```

**MySQL internally rewrites it as:**

```sql
SELECT 
    e.emp_id,
    e.name AS employee_name,
    e.salary,
    d.dept_name,
    d.location
FROM EMPLOYEE e
JOIN DEPARTMENT d ON e.dept_id = d.dept_id
WHERE d.dept_name = 'Engineering';  -- Condition pushed down
```

**Key Points:**
- View definition is **substituted** into the query
- No intermediate result stored (unless materialized)
- Conditions are **pushed down** to base tables for efficiency

---

### View Materialization

Some complex views benefit from **materialization** (storing results).

**Standard View (Virtual):**
```sql
CREATE VIEW COMPLEX_REPORT AS
SELECT ...
FROM table1 t1
JOIN table2 t2 ON ...
JOIN table3 t3 ON ...
WHERE ...;
```

**Problem:** Query runs every time you access the view (slow for complex queries)

**Solution:** Materialized View (covered later in this guide)

---

### Practical View Examples

#### Example 6: Customer Loan Summary

```sql
-- Create comprehensive customer view
CREATE VIEW CUSTOMER_LOAN_SUMMARY AS
SELECT 
    c.customer_id,
    c.customer_name,
    c.city,
    c.credit_score,
    COUNT(l.loan_number) AS num_loans,
    COALESCE(SUM(l.amount), 0) AS total_loan_amount
FROM CUSTOMER c
LEFT JOIN BORROWER b ON c.customer_id = b.customer_id
LEFT JOIN LOAN l ON b.loan_number = l.loan_number
GROUP BY c.customer_id, c.customer_name, c.city, c.credit_score;

-- Query the view
SELECT * FROM CUSTOMER_LOAN_SUMMARY
WHERE total_loan_amount > 50000;
```

**Result:**
```
┌─────────────┬──────────��──────┬─────────┬──────────────┬───────────┬───────────────────┐
│ customer_id │ customer_name   │ city    │ credit_score │ num_loans │ total_loan_amount │
├─────────────┼─────────────────┼─────────┼──────────────┼───────────┼───────────────────┤
│ 202         │ Sarah Williams  │ Boston  │ 680          │ 1         │ 100000            │
│ 203         │ David Miller    │ Chicago │ 750          │ 1         │ 75000             │
└─────────────┴─────────────────┴─────────┴──────────────┴───────────┴───────────────────┘
```

---

#### Example 7: View on View (Nested Views)

```sql
-- First-level view
CREATE VIEW HIGH_SALARY_EMPLOYEES AS
SELECT emp_id, name, salary, dept_id
FROM EMPLOYEE
WHERE salary > 70000;

-- Second-level view (based on first view)
CREATE VIEW HIGH_SALARY_ENGINEERING AS
SELECT hse.emp_id, hse.name, hse.salary, d.dept_name
FROM HIGH_SALARY_EMPLOYEES hse
JOIN DEPARTMENT d ON hse.dept_id = d.dept_id
WHERE d.dept_name = 'Engineering';

-- Query nested view
SELECT * FROM HIGH_SALARY_ENGINEERING;
```

**Result:**
```
┌────────┬──────────���────┬────────┬─────────────┐
│ emp_id │ name          │ salary │ dept_name   │
├────────┼───────────────┼────────┼─────────────┤
│ 101    │ John Smith    │ 75000  │ Engineering │
│ 105    │ Charlie Davis │ 95000  │ Engineering │
└────────┴───────────────┴────────┴─────────────┘
```

---

### View Metadata

Check existing views:

```sql
-- Show all views in database
SHOW FULL TABLES WHERE Table_type = 'VIEW';

-- Show view definition
SHOW CREATE VIEW EMPLOYEE_PUBLIC;

-- Query information schema
SELECT 
    TABLE_NAME,
    VIEW_DEFINITION
FROM INFORMATION_SCHEMA.VIEWS
WHERE TABLE_SCHEMA = 'your_database_name';
```

---

## 2. Assertions {#assertions}

### What is an Assertion?

An **assertion** is a **database constraint** that expresses a condition the database must **always** satisfy.

**Key Characteristics:**
- **Predicate** - a boolean expression
- **Global constraint** - can span multiple tables
- **Always enforced** - checked on every relevant update
- **Declarative** - you specify WHAT, not HOW

### Syntax

**Standard SQL:**
```sql
CREATE ASSERTION assertion_name
CHECK (predicate);

DROP ASSERTION assertion_name;
```

**⚠️ IMPORTANT:** MySQL **does NOT support assertions** natively. However, we can achieve similar functionality using:
1. **CHECK constraints** (MySQL 8.0.16+)
2. **Triggers**
3. **Stored procedures**

---

### Why Use Assertions?

| Purpose | Example |
|---------|---------|
| **Business Rules** | Total branch loans < total branch deposits |
| **Data Integrity** | Every loan must have a qualified borrower |
| **Complex Constraints** | Manager salary > all subordinates |
| **Cross-Table Rules** | Inventory sum = sales sum + stock |

---

### Assertion Logic Pattern: NOT EXISTS

**Common Pattern:** "There should NOT exist any violation"

```sql
CREATE ASSERTION assertion_name
CHECK (NOT EXISTS (
    SELECT *
    FROM tables
    WHERE violation_condition
));
```

**Translation:** "Assert that no rows exist where the violation occurs"

---

### Example 1: Branch Loan Limit

**Business Rule:** The sum of all loan amounts at a branch must be less than the sum of all account balances at that branch.

**Logical Statement:**
```
For every branch:
  SUM(loan.amount) < SUM(account.balance)
```

**Assertion (Standard SQL):**
```sql
CREATE ASSERTION BRANCH_LOAN_LIMIT
CHECK (NOT EXISTS (
    SELECT branch_name
    FROM (
        SELECT branch_name, SUM(amount) AS total_loans
        FROM LOAN
        GROUP BY branch_name
    ) AS loan_totals
    JOIN (
        SELECT branch_name, SUM(balance) AS total_deposits
        FROM ACCOUNT
        GROUP BY branch_name
    ) AS deposit_totals USING (branch_name)
    WHERE total_loans >= total_deposits
));
```

**MySQL Implementation (Using Trigger):**

```mysql
DELIMITER $$

-- Trigger for INSERT on LOAN
CREATE TRIGGER check_branch_loan_limit_insert
BEFORE INSERT ON LOAN
FOR EACH ROW
BEGIN
    DECLARE total_loans DECIMAL(12,2);
    DECLARE total_deposits DECIMAL(12,2);
    
    -- Calculate total loans for the branch (including new loan)
    SELECT COALESCE(SUM(amount), 0) + NEW.amount INTO total_loans
    FROM LOAN
    WHERE branch_name = NEW.branch_name;
    
    -- Calculate total deposits for the branch
    SELECT COALESCE(SUM(balance), 0) INTO total_deposits
    FROM ACCOUNT
    WHERE branch_name = NEW.branch_name;
    
    -- Check assertion
    IF total_loans >= total_deposits THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Branch loan limit exceeded: total loans must be less than total deposits';
    END IF;
END$$

-- Trigger for UPDATE on LOAN
CREATE TRIGGER check_branch_loan_limit_update
BEFORE UPDATE ON LOAN
FOR EACH ROW
BEGIN
    DECLARE total_loans DECIMAL(12,2);
    DECLARE total_deposits DECIMAL(12,2);
    
    SELECT COALESCE(SUM(amount), 0) - OLD.amount + NEW.amount INTO total_loans
    FROM LOAN
    WHERE branch_name = NEW.branch_name;
    
    SELECT COALESCE(SUM(balance), 0) INTO total_deposits
    FROM ACCOUNT
    WHERE branch_name = NEW.branch_name;
    
    IF total_loans >= total_deposits THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Branch loan limit exceeded';
    END IF;
END$$

-- Trigger for UPDATE on ACCOUNT (deposits decrease)
CREATE TRIGGER check_branch_loan_limit_account_update
BEFORE UPDATE ON ACCOUNT
FOR EACH ROW
BEGIN
    DECLARE total_loans DECIMAL(12,2);
    DECLARE total_deposits DECIMAL(12,2);
    
    SELECT COALESCE(SUM(amount), 0) INTO total_loans
    FROM LOAN
    WHERE branch_name = NEW.branch_name;
    
    SELECT COALESCE(SUM(balance), 0) - OLD.balance + NEW.balance INTO total_deposits
    FROM ACCOUNT
    WHERE branch_name = NEW.branch_name;
    
    IF total_loans >= total_deposits THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot reduce deposits: would violate branch loan limit';
    END IF;
END$$

DELIMITER ;
```

**Test the Assertion:**

```sql
-- Current state
SELECT 
    branch_name,
    (SELECT SUM(amount) FROM LOAN WHERE branch_name = 'Downtown') AS total_loans,
    (SELECT SUM(balance) FROM ACCOUNT WHERE branch_name = 'Downtown') AS total_deposits;
```

**Result:**
```
┌─────────────┬─────────────┬────────────────┐
│ branch_name │ total_loans │ total_deposits │
├─────────────┼─────────────┼────────────────┤
│ Downtown    │ 125000      │ 6200           │
└─────────────┴─────────────┴────────────────┘
```

**Try to violate:**
```sql
-- This should FAIL (125000 >= 6200 already!)
INSERT INTO LOAN VALUES ('L-104', 'Downtown', 10000, 'Personal');
```

**Error:** Branch loan limit exceeded

---

### Example 2: Minimum Borrower Balance

**Business Rule:** Every loan must have at least one borrower with an account balance of at least $1000.

**Logical Statement:**
```
For every loan:
  EXISTS at least one borrower with balance >= 1000
```

**Assertion (Standard SQL):**
```sql
CREATE ASSERTION MIN_BORROWER_BALANCE
CHECK (NOT EXISTS (
    SELECT loan_number
    FROM LOAN L
    WHERE NOT EXISTS (
        SELECT *
        FROM BORROWER B
        JOIN DEPOSITOR D ON B.customer_id = D.customer_id
        JOIN ACCOUNT A ON D.account_number = A.account_number
        WHERE B.loan_number = L.loan_number
          AND A.balance >= 1000
    )
));
```

**Translation:** "There should NOT exist any loan for which there does NOT exist a borrower with balance >= 1000"

**MySQL Implementation (Using Trigger):**

```sql
DELIMITER $$

-- Trigger for INSERT on LOAN
CREATE TRIGGER check_min_borrower_balance_loan
AFTER INSERT ON LOAN
FOR EACH ROW
BEGIN
    DECLARE qualified_borrowers INT;
    
    -- Count borrowers with sufficient balance
    SELECT COUNT(*) INTO qualified_borrowers
    FROM BORROWER B
    JOIN DEPOSITOR D ON B.customer_id = D.customer_id
    JOIN ACCOUNT A ON D.account_number = A.account_number
    WHERE B.loan_number = NEW.loan_number
      AND A.balance >= 1000;
    
    IF qualified_borrowers = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Loan must have at least one borrower with account balance >= 1000';
    END IF;
END$$

-- Trigger for INSERT on BORROWER
CREATE TRIGGER check_min_borrower_balance_borrower
AFTER INSERT ON BORROWER
FOR EACH ROW
BEGIN
    DECLARE qualified_borrowers INT;
    
    SELECT COUNT(*) INTO qualified_borrowers
    FROM BORROWER B
    JOIN DEPOSITOR D ON B.customer_id = D.customer_id
    JOIN ACCOUNT A ON D.account_number = A.account_number
    WHERE B.loan_number = NEW.loan_number
      AND A.balance >= 1000;
    
    IF qualified_borrowers = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Loan must have at least one borrower with account balance >= 1000';
    END IF;
END$$

-- Trigger for UPDATE/DELETE on ACCOUNT (balance changes)
CREATE TRIGGER check_min_borrower_balance_account
BEFORE UPDATE ON ACCOUNT
FOR EACH ROW
BEGIN
    -- Check if this account is associated with any loan borrowers
    IF EXISTS (
        SELECT 1
        FROM DEPOSITOR D
        JOIN BORROWER B ON D.customer_id = B.customer_id
        WHERE D.account_number = NEW.account_number
          AND OLD.balance >= 1000
          AND NEW.balance < 1000
    ) THEN
        -- Check if removing this qualified borrower would violate the constraint
        DECLARE affected_loans CURSOR FOR
            SELECT DISTINCT B.loan_number
            FROM DEPOSITOR D
            JOIN BORROWER B ON D.customer_id = B.customer_id
            WHERE D.account_number = NEW.account_number;
        
        -- Would need to check each affected loan
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot reduce balance below 1000: would violate loan borrower requirement';
    END IF;
END$$

DELIMITER ;
```

**Test:**

```sql
-- Add a loan
INSERT INTO LOAN VALUES ('L-105', 'Downtown', 20000, 'Personal');

-- Try to add borrower with insufficient balance
INSERT INTO BORROWER VALUES (202, 'L-105');  -- Sarah has balance 8000 (>= 1000) ✓

-- But if we try with a customer with < 1000 balance and no other borrowers
-- It would fail ❌
```

---

### Example 3: Manager Salary Constraint

**Business Rule:** An employee's salary cannot exceed their manager's salary.

**Logical Statement:**
```
For every employee with a manager:
  employee.salary <= manager.salary
```

**Assertion (Standard SQL):**
```sql
CREATE ASSERTION MANAGER_SALARY_CONSTRAINT
CHECK (NOT EXISTS (
    SELECT *
    FROM EMPLOYEE E
    JOIN EMPLOYEE M ON E.manager_id = M.emp_id
    WHERE E.salary > M.salary
));
```

**MySQL Implementation (Using Trigger):**

```sql
DELIMITER $$

-- Trigger for INSERT on EMPLOYEE
CREATE TRIGGER check_manager_salary_insert
BEFORE INSERT ON EMPLOYEE
FOR EACH ROW
BEGIN
    DECLARE manager_salary DECIMAL(10,2);
    
    IF NEW.manager_id IS NOT NULL THEN
        SELECT salary INTO manager_salary
        FROM EMPLOYEE
        WHERE emp_id = NEW.manager_id;
        
        IF NEW.salary > manager_salary THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Employee salary cannot exceed manager salary';
        END IF;
    END IF;
END$$

-- Trigger for UPDATE on EMPLOYEE (salary increase)
CREATE TRIGGER check_manager_salary_update
BEFORE UPDATE ON EMPLOYEE
FOR EACH ROW
BEGIN
    DECLARE manager_salary DECIMAL(10,2);
    
    -- Check if employee's new salary exceeds their manager's
    IF NEW.manager_id IS NOT NULL THEN
        SELECT salary INTO manager_salary
        FROM EMPLOYEE
        WHERE emp_id = NEW.manager_id;
        
        IF NEW.salary > manager_salary THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Employee salary cannot exceed manager salary';
        END IF;
    END IF;
    
    -- Check if this employee is a manager and new salary < subordinate salary
    IF EXISTS (
        SELECT 1
        FROM EMPLOYEE
        WHERE manager_id = NEW.emp_id
          AND salary > NEW.salary
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Manager salary cannot be less than subordinate salary';
    END IF;
END$$

DELIMITER ;
```

**Test:**

```sql
-- Current: John (manager, 75000), Jane (subordinate, 65000)

-- Try to give Jane higher salary than John (❌ Should FAIL)
UPDATE EMPLOYEE SET salary = 80000 WHERE emp_id = 102;

-- Error: Employee salary cannot exceed manager salary

-- Try to reduce John's salary below Jane's (❌ Should FAIL)
UPDATE EMPLOYEE SET salary = 60000 WHERE emp_id = 101;

-- Error: Manager salary cannot be less than subordinate salary
```

---

### Example 4: Referential Integrity (Alternative to FK)

**Business Rule:** Every borrower must reference an existing customer.

**Assertion (Standard SQL):**
```sql
CREATE ASSERTION BORROWER_CUSTOMER_INTEGRITY
CHECK (NOT EXISTS (
    SELECT *
    FROM BORROWER B
    WHERE B.customer_id NOT IN (SELECT customer_id FROM CUSTOMER)
));
```

**Note:** This is already handled by FOREIGN KEY constraints, but shows assertion capability.

---

### Example 5: Account Balance Non-Negative

**Business Rule:** No account can have negative balance.

**CHECK Constraint (MySQL 8.0.16+):**
```sql
ALTER TABLE ACCOUNT
ADD CONSTRAINT chk_balance_positive
CHECK (balance >= 0);
```

**Test:**
```sql
-- This should FAIL
UPDATE ACCOUNT SET balance = -100 WHERE account_number = 'A-101';

-- Error: Check constraint violated
```

---

### Assertion Performance Considerations

**Problem:** Assertions are checked on **every relevant update**

**Example:** Branch loan limit assertion requires:
- Query all loans for branch
- Query all accounts for branch
- Compute sums
- Compare

**Performance Impact:**
- ⚠️ Slow updates if many rows affected
- ⚠️ Complex predicates = expensive checks
- ⚠️ Can cause bottlenecks in high-transaction systems

**Best Practices:**
1. ✓ Use assertions for **critical business rules** only
2. ✓ Prefer **simpler constraints** (CHECK, FK) when possible
3. ✓ Consider **periodic validation** instead of real-time
4. ✓ Optimize assertion predicates with indexes
5. ✓ Document performance impact

---

### Alternative: Periodic Validation

Instead of enforcing on every transaction:

```sql
-- Validation stored procedure (run periodically)
DELIMITER $$

CREATE PROCEDURE VALIDATE_BRANCH_LOAN_LIMITS()
BEGIN
    SELECT 
        branch_name,
        total_loans,
        total_deposits,
        'VIOLATION' AS status
    FROM (
        SELECT 
            L.branch_name,
            COALESCE(SUM(L.amount), 0) AS total_loans,
            COALESCE(SUM(A.balance), 0) AS total_deposits
        FROM LOAN L
        LEFT JOIN ACCOUNT A ON L.branch_name = A.branch_name
        GROUP BY L.branch_name
    ) AS branch_totals
    WHERE total_loans >= total_deposits;
END$$

DELIMITER ;

-- Run validation
CALL VALIDATE_BRANCH_LOAN_LIMITS();
```

---

## 3. Bitmap Indexing {#bitmap-indexing}

### What is Bitmap Indexing?

A **bitmap index** is a specialized indexing technique using **bit arrays (bitmaps)** to represent data.

**Key Characteristics:**
- **Compact** - uses bits (0 or 1) instead of traditional pointers
- **Efficient** - excellent for low-cardinality columns (few distinct values)
- **Fast queries** - uses bitwise operations (AND, OR, NOT)
- **Space-saving** - especially for sparse data

---

### When to Use Bitmap Indexes?

| Use Case | Example |
|----------|---------|
| **Low cardinality** | Gender (M/F), Status (Active/Inactive) |
| **Read-heavy workloads** | Data warehouses, reporting |
| **Multiple column queries** | WHERE gender='F' AND status='Active' |
| **Large tables** | Millions of rows with few distinct values |

**❌ Not suitable for:**
- High-cardinality columns (email, SSN, etc.)
- Frequent updates (expensive to maintain)
- OLTP systems (better suited for OLAP)

---

### Bitmap Index Structure

**Example Table:**
```sql
CREATE TABLE EMPLOYEE_STATUS (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    gender CHAR(1),
    status VARCHAR(20),
    department VARCHAR(50)
);

INSERT INTO EMPLOYEE_STATUS VALUES
    (1, 'Alice', 'F', 'Active', 'Sales'),
    (2, 'Bob', 'M', 'Active', 'IT'),
    (3, 'Carol', 'F', 'Inactive', 'HR'),
    (4, 'David', 'M', 'Active', 'Sales'),
    (5, 'Emma', 'F', 'Active', 'IT'),
    (6, 'Frank', 'M', 'Inactive', 'Sales'),
    (7, 'Grace', 'F', 'Active', 'HR'),
    (8, 'Henry', 'M', 'Active', 'IT');
```

---

### Bitmap Index for Gender Column

**Distinct values:** M, F

**Bitmap Representation:**

| ROWID | Name  | Gender | Bitmap 'M' | Bitmap 'F' |
|-------|-------|--------|------------|------------|
| 1     | Alice | F      | 0          | 1          |
| 2     | Bob   | M      | 1          | 0          |
| 3     | Carol | F      | 0          | 1          |
| 4     | David | M      | 1          | 0          |
| 5     | Emma  | F      | 0          | 1          |
| 6     | Frank | M      | 1          | 0          |
| 7     | Grace | F      | 0          | 1          |
| 8     | Henry | M      | 1          | 0          |

**Bitmap for 'M':** `01010101` (binary)
**Bitmap for 'F':** `10101010` (binary)

---

### Bitmap Index Storage

**Typical structure:**

| Column Value | Starting ROWID | Ending ROWID | Bitmap     |
|--------------|----------------|--------------|------------|
| M            | 1              | 8            | 01010101   |
| F            | 1              | 8            | 10101010   |

---

### Querying with Bitmap Indexes

#### Query 1: Simple Equality

**Query:** Find all female employees

**SQL:**
```sql
SELECT * FROM EMPLOYEE_STATUS WHERE gender = 'F';
```

**Bitmap Operation:**
```
Bitmap for 'F': 10101010
ROWIDs with bit=1: 1, 3, 5, 7
```

**Result:** Retrieve rows 1, 3, 5, 7

---

#### Query 2: Multiple Conditions with AND

**Query:** Find female employees who are active

**SQL:**
```sql
SELECT * FROM EMPLOYEE_STATUS 
WHERE gender = 'F' AND status = 'Active';
```

**Bitmap Representation:**

**Gender Bitmaps:**
- 'M': `01010101`
- 'F': `10101010`

**Status Bitmaps:**
- 'Active': `11011011`
- 'Inactive': `00100100`

**Bitwise AND Operation:**
```
Bitmap for 'F':      10101010
Bitmap for 'Active': 11011011
                     --------
Result (AND):        10001010
```

**ROWIDs with bit=1:** 1, 5, 7

**Result:** Alice, Emma, Grace

---

#### Query 3: OR Operation

**Query:** Find employees who are female OR in Sales

**SQL:**
```sql
SELECT * FROM EMPLOYEE_STATUS 
WHERE gender = 'F' OR department = 'Sales';
```

**Bitmaps:**

**Gender 'F':** `10101010`
**Department 'Sales':** `10010100`

**Bitwise OR:**
```
Gender 'F':    10101010
Dept 'Sales':  10010100
               --------
Result (OR):   10111110
```

**ROWIDs:** 1, 2, 4, 5, 6, 7

---

#### Query 4: NOT Operation

**Query:** Find employees who are NOT in IT

**SQL:**
```sql
SELECT * FROM EMPLOYEE_STATUS 
WHERE department != 'IT';
```

**Bitmaps:**

**Department 'IT':** `01001001`

**Bitwise NOT:**
```
Dept 'IT':   01001001
             --------
NOT IT:      10110110
```

**ROWIDs:** 1, 3, 4, 6, 7

---

#### Query 5: Complex Combination

**Query:** Find active male employees NOT in HR

**SQL:**
```sql
SELECT * FROM EMPLOYEE_STATUS 
WHERE gender = 'M' AND status = 'Active' AND department != 'HR';
```

**Bitmaps:**
- Gender 'M': `01010101`
- Status 'Active': `11011011`
- Department 'HR': `00100010`

**Operations:**
```
Step 1: M AND Active
01010101
11011011
--------
01010001

Step 2: Result AND (NOT HR)
NOT HR:  11011101
Result:  01010001
         --------
Final:   01010001
```

**ROWIDs:** 2, 8 (Bob, Henry)

---

### Bitmap Index Creation (MySQL Simulation)

**Note:** MySQL doesn't support native bitmap indexes, but we can simulate the concept.

```sql
-- Create bitmap table for gender
CREATE TABLE BITMAP_GENDER (
    gender_value CHAR(1),
    bitmap_data BINARY(1000)  -- Supports up to 8000 rows (1000 bytes * 8 bits)
);

-- Manually populate bitmap (conceptual example)
-- In practice, this would be done by specialized database systems
```

**Oracle Example (has native bitmap indexes):**
```sql
-- Oracle syntax
CREATE BITMAP INDEX idx_gender ON EMPLOYEE_STATUS(gender);
CREATE BITMAP INDEX idx_status ON EMPLOYEE_STATUS(status);
CREATE BITMAP INDEX idx_department ON EMPLOYEE_STATUS(department);
```

---

### Advantages of Bitmap Indexes

| Advantage | Explanation |
|-----------|-------------|
| **Space efficient** | Bits use minimal storage vs traditional B-tree |
| **Fast queries** | Bitwise operations are extremely fast |
| **Multiple conditions** | AND/OR/NOT operations trivial with bitmaps |
| **Good for DW** | Perfect for read-heavy analytical queries |
| **Compression** | Bitmaps compress well (run-length encoding) |

---

### Disadvantages of Bitmap Indexes

| Disadvantage | Explanation |
|--------------|-------------|
| **Update overhead** | Updating bitmap requires recomputing bits |
| **Lock contention** | Updates lock entire bitmap (not row-level) |
| **Not for OLTP** | Frequent updates make them impractical |
| **High cardinality** | Inefficient for unique/near-unique values |
| **Space waste** | Large bitmaps if cardinality is high |

---

### Bitmap vs B-tree Index

| Aspect | B-tree Index | Bitmap Index |
|--------|--------------|--------------|
| **Cardinality** | High (unique values) | Low (few values) |
| **Workload** | OLTP (read/write) | OLAP (read-heavy) |
| **Multiple conditions** | Slower (merge indexes) | Fast (bitwise ops) |
| **Updates** | Fast (row-level) | Slow (bitmap rebuild) |
| **Space** | More space | Less space |
| **Example** | Email, SSN | Gender, Status |

---

### Bitmap Compression (Run-Length Encoding)

**Sparse bitmap:**
```
00000000 00000000 11111111 00000000
```

**Compressed (RLE):**
```
[0: 16 times] [1: 8 times] [0: 8 times]
```

**Benefit:** Saves significant space for sparse data

---

### Practical Example: Data Warehouse Query

**Scenario:** Sales data warehouse

```sql
CREATE TABLE SALES (
    sale_id INT,
    product_category VARCHAR(50),
    customer_gender CHAR(1),
    sale_region VARCHAR(50),
    sale_status VARCHAR(20),
    amount DECIMAL(10,2)
);
```

**Query:** Find total sales for active female customers in Electronics in West region

**SQL:**
```sql
SELECT SUM(amount)
FROM SALES
WHERE customer_gender = 'F'
  AND product_category = 'Electronics'
  AND sale_region = 'West'
  AND sale_status = 'Active';
```

**With Bitmap Indexes:**
1. Get bitmap for Gender='F'
2. Get bitmap for Category='Electronics'
3. Get bitmap for Region='West'
4. Get bitmap for Status='Active'
5. AND all bitmaps
6. Retrieve matching rows
7. Sum amounts

**Performance:** Extremely fast (bitwise operations on compressed bitmaps)

---

## 4. Materialized Views {#materialized-views}

### What is a Materialized View?

A **materialized view** is a view whose results are **physically stored** (like a table).

**Difference from Regular View:**

| Regular View | Materialized View |
|--------------|-------------------|
| Virtual (no storage) | Physical (stored) |
| Computed on query | Pre-computed |
| Always current | Needs refresh |
| Slower (recomputes) | Faster (precomputed) |

---

### MySQL Simulation of Materialized View

MySQL doesn't have native materialized views, but we can simulate:

```sql
-- Create table to store materialized view
CREATE TABLE DEPT_SALARY_STATS_MV (
    dept_name VARCHAR(50),
    num_employees INT,
    avg_salary DECIMAL(10,2),
    total_salary DECIMAL(12,2),
    last_refreshed TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Populate materialized view
INSERT INTO DEPT_SALARY_STATS_MV (dept_name, num_employees, avg_salary, total_salary)
SELECT 
    d.dept_name,
    COUNT(e.emp_id),
    AVG(e.salary),
    SUM(e.salary)
FROM DEPARTMENT d
LEFT JOIN EMPLOYEE e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name;

-- Refresh materialized view (manual)
TRUNCATE TABLE DEPT_SALARY_STATS_MV;
INSERT INTO DEPT_SALARY_STATS_MV (dept_name, num_employees, avg_salary, total_salary)
SELECT 
    d.dept_name,
    COUNT(e.emp_id),
    AVG(e.salary),
    SUM(e.salary)
FROM DEPARTMENT d
LEFT JOIN EMPLOYEE e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name;
```

---

### Automatic Refresh with Event Scheduler

```sql
-- Enable event scheduler
SET GLOBAL event_scheduler = ON;

-- Create refresh event
DELIMITER $$

CREATE EVENT refresh_dept_salary_stats
ON SCHEDULE EVERY 1 HOUR
DO
BEGIN
    TRUNCATE TABLE DEPT_SALARY_STATS_MV;
    INSERT INTO DEPT_SALARY_STATS_MV (dept_name, num_employees, avg_salary, total_salary)
    SELECT 
        d.dept_name,
        COUNT(e.emp_id),
        AVG(e.salary),
        SUM(e.salary)
    FROM DEPARTMENT d
    LEFT JOIN EMPLOYEE e ON d.dept_id = e.dept_id
    GROUP BY d.dept_id, d.dept_name;
END$$

DELIMITER ;
```

---

## 5. Complete Examples {#examples}

### Example: Complete Database with Views and Assertions

```sql
-- ========================================
-- Complete University Database Example
-- ========================================

-- Base Tables
CREATE TABLE STUDENT (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    gpa DECIMAL(3,2),
    credits_completed INT
);

CREATE TABLE COURSE (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100),
    credits INT,
    max_enrollment INT
);

CREATE TABLE ENROLLMENT (
    student_id INT,
    course_id VARCHAR(10),
    semester VARCHAR(20),
    grade CHAR(1),
    PRIMARY KEY (student_id, course_id, semester),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE(course_id)
);

-- Sample Data
INSERT INTO STUDENT VALUES
    (1001, 'Alice Johnson', 'alice@univ.edu', 3.8, 90),
    (1002, 'Bob Smith', 'bob@univ.edu', 3.5, 75),
    (1003, 'Carol White', 'carol@univ.edu', 3.9, 105);

INSERT INTO COURSE VALUES
    ('CS101', 'Intro to CS', 3, 50),
    ('CS201', 'Data Structures', 4, 40),
    ('MATH101', 'Calculus', 4, 60);

INSERT INTO ENROLLMENT VALUES
    (1001, 'CS101', 'Fall 2025', 'A'),
    (1001, 'MATH101', 'Fall 2025', 'B'),
    (1002, 'CS101', 'Fall 2025', 'A'),
    (1003, 'CS201', 'Fall 2025', 'A');

-- ========================================
-- VIEWS
-- ========================================

-- View 1: Honor Students (GPA >= 3.5)
CREATE VIEW HONOR_STUDENTS AS
SELECT student_id, name, email, gpa
FROM STUDENT
WHERE gpa >= 3.5;

-- View 2: Course Enrollment Summary
CREATE VIEW COURSE_ENROLLMENT_SUMMARY AS
SELECT 
    c.course_id,
    c.course_name,
    c.max_enrollment,
    COUNT(e.student_id) AS current_enrollment,
    c.max_enrollment - COUNT(e.student_id) AS available_seats
FROM COURSE c
LEFT JOIN ENROLLMENT e ON c.course_id = e.course_id
GROUP BY c.course_id, c.course_name, c.max_enrollment;

-- View 3: Student Transcript
CREATE VIEW STUDENT_TRANSCRIPT AS
SELECT 
    s.student_id,
    s.name,
    s.email,
    e.course_id,
    c.course_name,
    e.semester,
    e.grade,
    c.credits
FROM STUDENT s
JOIN ENROLLMENT e ON s.student_id = e.student_id
JOIN COURSE c ON e.course_id = c.course_id;

-- ========================================
-- ASSERTIONS (via Triggers)
-- ========================================

-- Assertion 1: Course cannot exceed max enrollment
DELIMITER $$

CREATE TRIGGER check_max_enrollment
BEFORE INSERT ON ENROLLMENT
FOR EACH ROW
BEGIN
    DECLARE current_count INT;
    DECLARE max_count INT;
    
    SELECT COUNT(*) INTO current_count
    FROM ENROLLMENT
    WHERE course_id = NEW.course_id;
    
    SELECT max_enrollment INTO max_count
    FROM COURSE
    WHERE course_id = NEW.course_id;
    
    IF current_count >= max_count THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Course enrollment limit reached';
    END IF;
END$$

DELIMITER ;

-- Assertion 2: GPA must be between 0.0 and 4.0
ALTER TABLE STUDENT
ADD CONSTRAINT chk_gpa_range
CHECK (gpa >= 0.0 AND gpa <= 4.0);

-- Query Examples
SELECT * FROM HONOR_STUDENTS;
SELECT * FROM COURSE_ENROLLMENT_SUMMARY;
SELECT * FROM STUDENT_TRANSCRIPT WHERE student_id = 1001;
```

---

## Summary Comparison Table

| Feature | Views | Assertions | Bitmap Indexes |
|---------|-------|------------|----------------|
| **Purpose** | Virtual tables | Enforce constraints | Fast querying |
| **Storage** | None (virtual) | None (logic) | Bitmaps stored |
| **Performance** | Depends on query | Overhead on updates | Fast reads |
| **MySQL Support** | ✓ Native | ⚠️ Via triggers | ✗ Not native |
| **Use Case** | Simplify, secure | Business rules | Low-cardinality queries |
| **Best For** | OLTP/OLAP | Critical rules | OLAP/Data warehouses |

---

## Best Practices

### Views
1. ✓ Use for security (hide sensitive columns)
2. ✓ Use for simplification (complex joins)
3. ✓ Document view dependencies
4. ✓ Use WITH CHECK OPTION for updatable views
5. ⚠️ Avoid deep nesting (performance)

### Assertions
1. ✓ Use for critical business rules
2. ✓ Optimize predicates (add indexes)
3. ✓ Consider periodic validation for non-critical rules
4. ⚠️ Be aware of performance overhead
5. ✓ Document all assertion logic

### Bitmap Indexes
1. ✓ Use for low-cardinality columns
2. ✓ Use in read-heavy workloads (OLAP)
3. ✗ Avoid in OLTP systems
4. ✓ Combine with compression
5. ✓ Monitor update performance

---

**End of Complete Guide**