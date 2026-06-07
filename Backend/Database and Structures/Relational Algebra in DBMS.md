
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
