
#### 1. MODIFY – Change Column Data Type or Attributes

Changes the data type or properties of an existing column **without renaming it**.

```mysql
ALTER TABLE students MODIFY percentage DECIMAL(10,2);
```

Changes `percentage` from `DECIMAL(5,2)` to `DECIMAL(10,2)`.

```mysql
ALTER TABLE table_name
MODIFY column_name INT AUTO_INCREMENT;
```

**Breaking it down:**

- `ALTER TABLE` = Modify existing table
- `table_name` = Which table to modify
- `MODIFY` = Change a column's properties
- `column_name INT AUTO_INCREMENT` = Make this column auto-increment

#### 2. RENAME – Rename the Table

Gives the table a new name.

```mysql
ALTER TABLE students RENAME gradstudents;
```

Renames the `students` table to `gradstudents`.

#### 3. ADD COLUMN – Add a New Column

Adds a new column to the table.

```mysql
ALTER TABLE students ADD COLUMN email VARCHAR(100);
```

Adds an `email` column to the `students` table.

#### 4. CHANGE – Rename a Column

Renames a column; you must specify the old name, new name, and full column definition.

```mysql
ALTER TABLE students CHANGE phone mobile_number INT;
```

Renames `phone` to `mobile_number` while keeping it as `INT`.

#### 5. DROP COLUMN – Delete a Column

Permanently removes a column and all its data.

```mysql
ALTER TABLE students DROP COLUMN bdate;
```

Deletes the `bdate` column from the table.

#### 6. ADD CONSTRAINT – Add a New Constraint

Adds a constraint like UNIQUE, CHECK, or FOREIGN KEY to an existing table.[^2][^3]

```mysql
ALTER TABLE students ADD CONSTRAINT email_unique UNIQUE(email);
```

Makes the `email` column unique.

#### 7. DROP CONSTRAINT – Remove a Constraint

Removes a named constraint from the table.

```mysql
ALTER TABLE students DROP CONSTRAINT email_unique;
```

Drops the UNIQUE constraint on `email`.
