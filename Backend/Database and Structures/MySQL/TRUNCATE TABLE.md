
**TRUNCATE** removes **all rows** from a table but keeps the table structure (columns, data types, constraints) intact.  It's much faster than `DELETE` because it doesn't log each row deletion—it just deallocates the entire data pages.[^1][^2][^3]

**Syntax:**

```sql
TRUNCATE TABLE <table_name>;
```

**Example:**

```sql
-- Remove all rows from gradstudents table
TRUNCATE TABLE gradstudents;

-- Check if table still exists (structure remains)
SELECT * FROM gradstudents;
```

**What happens:**

- All data rows are deleted instantly[^1]
- Table structure (columns, constraints, indexes) remains[^4][^3]
- Auto-increment counters reset to starting value[^2]
- Much faster than `DELETE FROM gradstudents;`[^2][^1]
- **Cannot undo** (no rollback in most cases)[^1][^2]
- Cannot use `WHERE` clause—deletes everything (unlike in [[Introduction to MySQL#UPDATE Command]])

**When to use:** Clearing test data, resetting tables for fresh data load, emptying large tables quickly.
