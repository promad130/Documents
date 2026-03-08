`DESC` displays information about a table's columns **without showing the actual data** stored in rows. It's like viewing the blueprint of your table.

Syntax:

```mysql
DESC table_name;
-- or
DESCRIBE table_name;
```

Example:

```mysql
DESC students;
```
**Output:**
```mysql
+------------+--------------+------+-----+---------+-------+
| Field      | Type         | Null | Key | Default | Extra |
+------------+--------------+------+-----+---------+-------+
| id         | char(12)     | NO   | PRI | NULL    |       |
| name       | varchar(30)  | YES  |     | NULL    |       |
| hostel     | int          | NO   |     | NULL    |       |
| percentage | decimal(5,2) | YES  |     | 0.00    |       |
| phone      | int          | YES  | UNI | NULL    |       |
+------------+--------------+------+-----+---------+-------+
```
