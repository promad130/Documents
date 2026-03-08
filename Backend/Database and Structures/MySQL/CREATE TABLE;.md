
Syntax to create a table:

```mysql
create table <TableName> (column defination1, column defination2, ...); 
```

Example:

```mysql
mysql> CREATE TABLE students ( id CHARACTER (12),
	name VARCHAR(30),
	hostel INTEGER NOT NULL,
	percentage DECIMAL(5,2) DEFAULT 0.0,
	phone INT,
	bdate DATE,
	gender ENUM('F','M'),
	CONSTRAINT uk UNIQUE(name)
);
```

Here we have used [[Introduction to MySQL#Data types in mySQL]]
