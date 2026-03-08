
**DROP DATABASE** deletes an **entire database** including all its tables, views, stored procedures, and data.

**Syntax:**

```mysql
DROP DATABASE <database_name>;
```

**Example:**

```mysql
-- Delete the entire mydb database
DROP DATABASE mydb;

-- Check remaining databases
SHOW DATABASES;
```

**What happens:**

- Entire database and everything inside it is permanently deleted
- All tables, views, procedures, functions removed
- Database no longer exists
- Cannot undo without backup

**When to use:** Removing test/development databases, cleaning up after project completion.
