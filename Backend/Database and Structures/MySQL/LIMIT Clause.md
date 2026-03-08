
The `LIMIT` clause restricts the number of rows returned by a `SELECT` query. It's useful for handling large tables, pagination, and improving query performance by fetching only what you need.[^1][^7][^8][^9]

## Basic Syntax

```sql
SELECT column1, column2 FROM table_name LIMIT number;
```

**Example:**

```sql
SELECT name FROM students LIMIT 3;
```

This returns only the first 3 student names.[^7][^1]

## LIMIT with OFFSET

The `OFFSET` lets you skip rows before starting to return results.[^7][^8]

**Two syntax styles:**

```sql
-- Style 1: LIMIT with OFFSET keyword
SELECT * FROM students LIMIT 5 OFFSET 3;

-- Style 2: LIMIT offset, count (MySQL specific)
SELECT * FROM students LIMIT 3, 5;
```

Both skip the first 3 rows and return the next 5 rows.[^9][^8]

**Important:** The offset is zero-based, so `LIMIT 0, 5` returns the first 5 rows.[^8]

## LIMIT with ORDER BY

Combine with `ORDER BY` to get specific ranked results.[^1][^7]

**Example - Get top 3 students by percentage:**

```sql
SELECT name, percentage FROM students ORDER BY percentage DESC LIMIT 3;
```

**Example - Get 2nd lowest salary (pagination):**

```sql
SELECT * FROM employees ORDER BY salary ASC LIMIT 1, 1;
```

This skips the lowest salary (offset 1) and returns the next single row.[^1]

## LIMIT with WHERE

Filter data first, then limit results.[^9]

```sql
SELECT * FROM students WHERE hostel = 10 LIMIT 5;
```

Returns the first 5 students from hostel 10.[^7][^9]

**With offset:**

```sql
SELECT * FROM students WHERE percentage > 90.6 LIMIT 2, 4;
```

Skips 2 rows and returns the next 4 where percentage exceeds 90.6.[^9][^1]

## Common Use Cases

**Pagination** - Display results across multiple pages:

```sql
-- Page 1 (first 10 records)
SELECT * FROM products LIMIT 10 OFFSET 0;

-- Page 2 (records 11-20)
SELECT * FROM products LIMIT 10 OFFSET 10;

-- Page 3 (records 21-30)
SELECT * FROM products LIMIT 10 OFFSET 20;
```

**Top N results:**

```sql
SELECT * FROM sales ORDER BY amount DESC LIMIT 10;
```

**Testing queries** - View sample data without overwhelming output:

```sql
SELECT * FROM large_table LIMIT 5;
```

### Key Points

- `AS` is optional but recommended for clarity[^6][^5]
- Aliases only exist during query execution, they don't change the actual table[^4]
- Use meaningful, concise names[^5]
- Avoid SQL reserved words as aliases[^5]
- Use quotes when alias contains spaces or special characters
