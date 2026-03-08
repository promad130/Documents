
Command used to retrieve data from a table.  It doesn't modify data—it just reads and displays it.

#### Basic Syntax

```sql
SELECT column, another_column, … 
FROM mytable 
WHERE _condition_ 
	AND/OR _another_condition_ 
	AND/OR …
;
```


#### 1. Select All Rows and All Columns

Use `*` (asterisk) to retrieve **every column** from the table.

```sql
SELECT * FROM students;
```

**What this shows:**

- All columns: `id, name, hostel, percentage, phone, bdate, gender`
- All rows in the table

**Observations from your data:**

- Student with `id = PS99305023` has `bdate` as the default value (likely `NULL` or `'0000-00-00'`)[^3]
- Student with `id = PS99305019` has `percentage = 0.0` (the user-defined default)[^3]
- Student with `id = PS99305025` has blank `gender` (empty string `''` or `NULL`)[^3]


#### 2. Select Specific Columns

List only the column names you want to see, separated by commas.

**Single column:**

```sql
SELECT id FROM students;
```

Returns only the `id` column for all rows.

**Multiple columns:**

```sql
SELECT id, name FROM students;
```

Returns only `id` and `name` columns in that order.

#### 3. Select Distinct Rows (Remove Duplicates)

Use `DISTINCT` to return only **unique values** in a column—duplicates are removed.[^2][^3]

```sql
SELECT DISTINCT name FROM students;
```

**What this does:**

- If multiple students have the same name, it shows that name only **once**.[^2]
- Without `DISTINCT`, duplicate names would appear multiple times.[^2]

**Example output without DISTINCT:**

```
name
-----------
Mohan
Mohan
Sai
```

**Example output with DISTINCT:**

```
name
-----------
Mohan
Sai
```
