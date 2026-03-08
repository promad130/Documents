
**DROP TABLE** completely **removes the table** from the database—both structure and data are gone permanently.[^6][^4]

**Syntax:**

```sql
DROP TABLE <table_name>;
```

**Example:**

```sql
-- Delete the entire gradstudents table
DROP TABLE gradstudents;

-- Verify table is gone
SHOW TABLES;
```

**What happens:**

- Table structure deleted (columns, constraints, indexes)
- All data deleted
- Table no longer exists—trying to query it causes an error
- Frees all space used by the table
- Cannot undo without a backup

**When to use:** Permanently removing obsolete tables, cleanup during database restructuring.

