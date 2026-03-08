
`UPDATE` modifies **existing rows** in a table.

**Syntax:**

```sql
UPDATE <table_name> 
SET (column1 = value1, column2 = value2, ...) 
WHERE <condition>;
```

**Always use `WHERE` to specify which rows to update.** Without `WHERE`, **all rows** in the table will be updated.[^5]

#### Examples

**Update a single column for specific rows:**

```sql
UPDATE students 
SET percentage = 85.50 
WHERE id = 'PS99305017';
```

Changes the `percentage` to `85.50` only for student with `id = 'PS99305017'`.[^5]

**Update multiple columns:**

```sql
UPDATE students 
SET percentage = 90.00, hostel = 15 
WHERE name = 'Mohan Sharma';
```

Updates both `percentage` and `hostel` for student named `Mohan Sharma`.[^5]

**Update all rows (dangerous!):**

```sql
UPDATE students 
SET percentage = 0.0;
```

**Warning:** Sets `percentage = 0.0` for **every student** because there's no `WHERE` clause.[^5]

**Update based on calculation:**

```sql
UPDATE students 
SET percentage = percentage + 5.0 
WHERE hostel = 11;
```

Adds 5 points to `percentage` for all students in hostel 11.[^5]

