## What is an Index?

Think of a database table like a massive textbook. If you want to find a specific topic, you don't read every page from the start (that's a **Full Table Scan**). Instead, you flip to the **Index** at the back, find the page number, and jump straight there.

### 1. Creating an Index

You usually create an index on columns that you use frequently in `WHERE` clauses or `JOIN` statements.

**Syntax:**

SQL

```
CREATE INDEX index_name
ON table_name (column1, column2, ...);
```

**Example:**

If you frequently search for students by their `last_name`:

SQL

```
CREATE INDEX idx_lastname
ON Students (last_name);
```

### 2. Unique Indexes

If you want to ensure no two rows have the same value in a column (while also speeding up searches), use a `UNIQUE INDEX`.

SQL

```
CREATE UNIQUE INDEX idx_email
ON Users (email);
```

### 3. Dropping an Index

If an index is no longer needed (or slowing down your `INSERT` operations too much), you can remove it:

SQL

```
ALTER TABLE Students
DROP INDEX idx_lastname;
```

---

## The "Trade-Off" (Why not index everything?)

While indexes make **`SELECT`** queries lightning fast, they have a downside:

- **Slower Writes:** Every time you `INSERT`, `UPDATE`, or `DELETE` data, MySQL has to update the index too.
    
- **Storage Space:** Indexes are stored separately and take up extra disk space.
    

### Summary Table

|**Feature**|**Without Index**|**With Index**|
|---|---|---|
|**Search Speed (`SELECT`)**|Slow (Sequential Scan)|Fast (Binary Search/B-Tree)|
|**Write Speed (`INSERT/UPDATE`)**|Fast|Slower (Index must be updated)|
|**Storage**|Minimal|Higher (Index files)|
