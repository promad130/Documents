
`INSERT INTO` is a **DML (Data Manipulation Language)** command that adds new rows (records) to a table.

#### Basic Syntax

**Two ways to insert:**

**1. Specify column names (recommended):**

```sql
INSERT INTO table_name (column1, column2, column3) 
VALUES (value1, value2, value3);
```

**2. Omit column names (insert all columns in order):**

```sql
INSERT INTO table_name 
VALUES (value1, value2, value3);
```

- You must provide values for **all columns** in the exact order they were defined in the table.
- **You MUST provide a value for EVERY column** - even columns with DEFAULT values or NULL constraints
- If you don't wish to give or provide default values, then have to mention `DEFAULT` keyword.

#### Examples

**Valid insert with all columns:**

```sql
INSERT INTO students VALUES ('PS99305017', 'Mohan Sharma', 13, 76.23, '9800000002', '2001-03-15', 'M');
```

Order matches: `id, name, hostel, percentage, phone, bdate, gender`.

**Valid insert with column names (order doesn't matter):**

```sql
INSERT INTO students (id, name, hostel, percentage, phone, bdate, gender) 
VALUES ('PS99305017', 'Sai Sundar', 11, 77.23, '9800000001', '2001-01-25', 'M');
```


#### Common Errors and Why They Happen

##### 1. Wrong Column Order

```sql
-- WRONG ORDER
INSERT INTO students VALUES ('Sai Sundar', 'PS99305017', 11, 77.23, '2001-01-25', '9800000001', 'M');
```

**Error:** Values don't match column types—`name` (VARCHAR) is in the `id` (CHAR) position.  Always match the table's column order or specify column names explicitly.[^1]

##### 2. Duplicate UNIQUE Value

```sql
-- Violates UNIQUE constraint on phone
INSERT INTO students (name, id, hostel, percentage, bdate, phone, gender) 
VALUES ('Jay Singh', 'PS99305012', 11, 83.73, '2000-07-04', '9900000002', 'M');
```

**Error if `9900000002` already exists:** UNIQUE constraint prevents duplicate phone numbers.[^2]

##### 3. Empty/NULL in PRIMARY KEY

```sql
-- Empty string in PRIMARY KEY
INSERT INTO students VALUES ('', 'Shyam Sundar', 11, 90.23, '9800000004', '2001-01-25', 'M');
```

**Error:** PRIMARY KEY cannot be empty or NULL.[^2]

##### 4. NULL vs Empty String in UNIQUE Column

```sql
-- NULL in name (UNIQUE column)
INSERT INTO students VALUES ('PS99305018', NULL, 11, 90.23, '9800000009', '2001-01-25', 'M');

-- Empty string in name
INSERT INTO students VALUES ('PS99305018', '', 11, 90.23, '9800000009', '2001-01-25', 'M');
```

**Both work once, but:** You can insert **multiple NULLs** (NULLs aren't considered duplicates), but only **one empty string** (empty strings are duplicates of each other).

##### 5. NULL in NOT NULL Column

```sql
-- Violates NOT NULL constraint on hostel
INSERT INTO students VALUES ('PS99305020', 'Sundaram', NULL, 90.23, '9800000005', '2001-01-25', 'M');
```

**Error:** `hostel` is defined as `NOT NULL`, so it must have a value.[^2]

##### 6. Missing Quotes for String/Date Types

```sql
-- Missing quotes around CHAR/VARCHAR
INSERT INTO students VALUES (PS99305020, 'Sundaram', 11, 90.23, 9800000006, '2001-01-25', 'M');
```

**Error:** `PS99305020` and `9800000006` are treated as column names, not values. Strings/dates need quotes: `'PS99305020'` and `'9800000006'`.[^2]

##### 7. Exceeding Column Size

```sql
-- name is VARCHAR(30), but value is 35 characters
INSERT INTO students VALUES ('PS99305021', 'Ram Prabhu Sundaran', 11, 90.23, '9800000006', '2001-01-25', 'M');
```

**Error:** Value exceeds the defined length of `VARCHAR(30)`. MySQL truncates or rejects it depending on strict mode.[^2]

##### 8. Invalid Date

```sql
-- February doesn't have 30 days
INSERT INTO students VALUES ('PS99305023', 'Ramnarayan Sundaran', 11, 90.23, '9800000006', '2001-02-30', 'M');
```

**Error:** `2001-02-30` is not a valid date—February has max 28/29 days.[^2]

##### 9. Invalid ENUM Value

```sql
-- gender is ENUM('F','M'), but 'K' is not allowed
INSERT INTO students VALUES ('PS99305025', 'Narayan Sundar', 11, 90.23, '9800000007', '2001-02-16', 'K');
```

**Error:** `'K'` is not in the allowed ENUM values `('F','M')`.

### Multiple Rows Insert

```sql
INSERT INTO students (id, name, hostel) 
VALUES 
  ('PS001', 'John', 10),
  ('PS002', 'Jane', 12),
  ('PS003', 'Mike', 14);
```

Inserts three rows in a single statement—more efficient than three separate INSERTs.
