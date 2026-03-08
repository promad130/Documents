
MySQL groups data types into five main categories: numeric, string, date/time, spatial, and JSON.

## Numeric Data Types

Used for numbers, IDs, prices, counts.

- **INT** – standard integer, range −2,147,483,648 to 2,147,483,647 (4 bytes). Use for IDs, counters.
- **TINYINT** – tiny integer, range −128 to 127 (or 0 to 255 unsigned), 1 byte.[^1][^2]
- **BIGINT** – huge integers, range up to ±9 quintillion, 8 bytes.[^1][^2]
- **DECIMAL(p, s)** – exact fixed-point number (e.g., `DECIMAL(10,2)` for money with 2 decimal places.
	- **p (precision)**: Total number of digits (1 to 65). Default is 10.
	- **s (scale)**: Number of digits after the decimal point (0 to 30, must be ≤ M). Default is 0.
	- Example: `DECIMAL(10,2)` stores up to 10 digits with 2 after the decimal (e.g., 99999999.99).
- **FLOAT / DOUBLE** – approximate floating-point for scientific calculations.
- **BOOLEAN** – implemented as `TINYINT(1)`, stores 0 (false) or 1 (true).


## String Data Types

Store text, descriptions, names.

- **VARCHAR(n)** – variable-length string up to *n* characters (max 65,535). Use for names, emails.
- **CHAR(n) or CHARACTER(n)** – fixed-length string exactly *n* characters (padded with spaces). Good for fixed codes like country codes.
- **TEXT** – long text up to 65,535 characters. Variants: `TINYTEXT`, `MEDIUMTEXT`, `LONGTEXT`.
- **BLOB** – binary large object for images, files. Variants: `TINYBLOB`, `MEDIUMBLOB`, `LONGBLOB`.
- **ENUM** – column can hold one of a predefined list (e.g., `ENUM('small','medium','large')`).
	- MySQL **ENUM values are case-insensitive by default**, so `'f'` and `'F'` are treated as the **same value**. 
	- When you try to create an ENUM with duplicate values, MySQL throws an error in strict SQL mode.


## Date and Time Data Types

Store dates, timestamps, durations.

- **DATE** – date only, format `YYYY-MM-DD`, range 1000-01-01 to 9999-12-31.[^1][^2]
- **DATETIME** – date + time, format `YYYY-MM-DD HH:MM:SS`, range 1000-01-01 to 9999-12-31.[^1][^2]
- **TIMESTAMP** – auto-updates to current time on row insert/update; range 1970 to 2038 (UTC).[^2][^1]
- **TIME** – time only, format `HH:MM:SS`.[^1][^2]
- **YEAR** – 4-digit year, 1901 to 2155.[^1]


## JSON Data Type

Stores JSON documents natively; supports efficient querying and indexing of JSON keys.[^2]

## Spatial Data Types

Store geographic and geometric data like points, lines, polygons (GEOMETRY, POINT, LINESTRING, POLYGON).

**Example table using different data types:**

```sql
CREATE TABLE users (
  id INT PRIMARY KEY,
  name VARCHAR(100),
  age TINYINT,
  balance DECIMAL(10,2),
  created_at TIMESTAMP,
  is_active BOOLEAN
);
```

# Parsing and Comparison logic in-built for all Data Types in MySQL :

MySQL comparisons aren't one-size-fits-all. The logic changes based on the "Data Type Group" of the columns involved. If you compare two different types (like a String and an Integer), MySQL performs **Implicit Type Conversion** (Coercion), which can sometimes lead to surprising results.

Here is the breakdown of how parsing and logic work for each category:

---

### 1. Numeric Types (`INT`, `DECIMAL`, `FLOAT`, `DOUBLE`)

- **The Logic:** Direct **Mathematical/Numerical Comparison**.
    
- **Parsing:** If you compare a number to a string (e.g., `WHERE price = '100'`), MySQL parses the string into a number.
    
- **The Warning:** If the string starts with a number but contains text (e.g., `'100 units'`), MySQL parses the `100` and ignores the rest. If it contains no numbers (e.g., `'abc'`), it parses as `0`.
    
- **Floating Point Pitfall:** `FLOAT` and `DOUBLE` are approximate. `0.1 + 0.2` might not exactly equal `0.3`. For money, always use `DECIMAL` because it uses **Exact Fixed-Point** logic.
    

---

### 2. String Types (`VARCHAR`, `CHAR`, `TEXT`)

- **The Logic:** **Lexicographical (Dictionary) Comparison** based on the column's **Collation**.
    
- **Parsing:** It compares character by character from left to right using the character set (usually UTF-8).
    
- **Collation Matters:**
    
    - `utf8mb4_bin`: Comparison is case-sensitive (compares raw binary/ASCII values).
        
    - `utf8mb4_0900_ai_ci`: Case-insensitive (treats 'A' and 'a' as equal).
        
- **Trailing Spaces:** `CHAR` ignores trailing spaces during comparison; `VARCHAR` behavior depends on the MySQL version and collation (usually ignores them in comparisons).
    

---

### 3. Date and Time Types (`DATE`, `DATETIME`, `TIMESTAMP`)

- **The Logic:** **Temporal (Chronological) Comparison**. Internally, MySQL often treats these as packed integers (YYYYMMDDHHMMSS).
    
- **Parsing:** As we discussed, MySQL expects `YYYY-MM-DD`.
    
    - If you provide a string, it tries to cast it to a date.
        
    - **Timestamp Logic:** `TIMESTAMP` is unique because it is converted from your current **Time Zone** to UTC for storage and back again for comparison. If two users in different time zones query the same `TIMESTAMP` using a string, they might get different results if their session time zones aren't aligned.
        

---

### 4. JSON Data Type

- **The Logic:** **Structural and Value Comparison**.
    
- **Parsing:** You don't usually compare the whole JSON blob. You use the `->` or `->>` operators to extract a key.
    
- **The Rule:** JSON values have their own internal types (Object, Array, Number, String).
    
    - When comparing two JSON values, MySQL follows a specific hierarchy: **BLOB > OBJECT > ARRAY > STRING > NUMBER > BOOLEAN > NULL**.
        
    - An empty Object `{}` is considered "greater than" an Array `[]`.
        

---

### 5. Spatial Data Types (`GEOMETRY`, `POINT`)

- **The Logic:** **Coordinate/Geometric Comparison**.
    
- **Parsing:** You cannot use `=` or `BETWEEN` effectively here.
    
- **How it works:** You use specific spatial functions like `ST_Contains()`, `ST_Distance()`, or `ST_Intersects()`. The logic is based on Cartesian coordinates or Great Circle distance (for Earth geography).
    

---

### 6. The "Golden Rule" of Mixed Comparisons

When you mix categories (e.g., comparing a String to a Number), MySQL follows a "Type Precedence" list to decide who gets converted:

1. **Hexadecimal values** are treated as binary strings.
    
2. **If one argument is a Decimal**, the other is converted to Decimal.
    
3. **If one argument is an Integer**, the other is converted to an Integer.
    
4. **If one argument is a Date/Time**, the other is converted to a Date/Time (usually).
    
5. **Otherwise**, they are compared as Strings.
    

|**Comparison**|**Resulting Logic**|**Why?**|
|---|---|---|
|`10 = '10'`|**True**|String `'10'` is cast to Integer `10`.|
|`'10' = '2'`|**False**|String comparison: `'1'` comes before `'2'`.|
|`10 = '2'`|**True**|String `'2'` is cast to Integer `2`.|
|`'2023-01-01' = 20230101`|**True**|The date is treated as its numeric equivalent.|

---

### Summary Table for Quick Reference

|**Category**|**Primary Comparison Logic**|**Primary Pitfall**|
|---|---|---|
|**Numeric**|Magnitude (Size)|Floating point rounding errors.|
|**String**|Collation (Sort Order)|Case sensitivity vs Insensitivity.|
|**Date/Time**|Chronology (Timeline)|`DATETIME` includes time; `DATE` does not.|
|**JSON**|Hierarchical / Structural|Comparing different types within JSON.|
|**Spatial**|Geometric Relationships|Standard operators (`=`) are rarely useful.|
