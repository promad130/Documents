
The `ORDER BY` clause sorts the result set in **ascending** or **descending** order.

**Syntax:**

```sql
SELECT column1, column2, ...
FROM table_name
ORDER BY column1 [ASC|DESC], column2 [ASC|DESC];
```

- **ASC** = ascending order (default, optional)
- **DESC** = descending order


#### Sorting in Ascending Order

By default, `ORDER BY` sorts in **ascending order**Step 5: Mapping of Binary M:N Relationship Types., so `ASC` is optional.

```sql
-- Sort students by name (A to Z)
SELECT * FROM students
ORDER BY name;

-- Same as above (ASC is implicit)
SELECT * FROM students
ORDER BY name ASC;
```

```sql
-- Sort by percentage (lowest to highest)
SELECT id, name, percentage
FROM students
ORDER BY percentage;
```


#### Sorting in Descending Order

Use `DESC` keyword for descending order.

```sql
-- Sort students by percentage (highest to lowest)
SELECT * FROM students
ORDER BY percentage DESC;
```

```sql
-- Sort by birth date (newest to oldest)
SELECT name, bdate
FROM students
ORDER BY bdate DESC;
```


#### Sorting by Multiple Columns

You can sort by multiple columns—MySQL sorts by the first column, then breaks ties using the second column, and so on.

```sql
-- Sort by hostel (ascending), then by name (ascending)
SELECT name, hostel, percentage
FROM students
ORDER BY hostel, name;
```

```sql
-- Sort by hostel (ascending), then by percentage (descending)
SELECT name, hostel, percentage
FROM students
ORDER BY hostel ASC, percentage DESC;
```
