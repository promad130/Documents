
`DELETE` removes **rows** from a table.

**Syntax:**

```sql
DELETE FROM table_name 
WHERE condition;
```

**Always use `WHERE` to specify which rows to delete.** Without `WHERE`, **all rows** will be deleted (table structure remains, but data is gone).[^5]

#### Examples

**Delete specific rows:**

```sql
DELETE FROM students 
WHERE id = 'PS99305017';
```

Removes the student with `id = 'PS99305017'`.[^5]

**Delete multiple rows matching condition:**

```sql
DELETE FROM students 
WHERE percentage < 50.0;
```

Deletes all students with percentage below 50.[^5]

**Delete all rows (dangerous!):**

```sql
DELETE FROM students;
```

**Warning:** Deletes **every row** in the table, but the table structure still exists.[^5]

**Delete based on NULL:**

```sql
DELETE FROM students 
WHERE phone IS NULL;
```

Removes all students with no phone number.
