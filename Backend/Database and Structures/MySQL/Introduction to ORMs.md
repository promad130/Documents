An **ORM**, which stands for **Object-Relational Mapping**, is a programming technique (and tool) that acts as a translator between two completely different worlds in software development:

1. **The Object-Oriented World (Your Code):** Where you think in terms of programming languages like C++, Python, Java, or TypeScript, using classes and objects.
2. **The Relational World (Your Database):** Where data is stored in tables with rows and columns (like PostgreSQL, MySQL, or SQLite), using SQL queries to fetch or change data.

Without an ORM, you have to manually write raw SQL strings inside your programming code to talk to the database. An ORM allows you to interact with your database using the native programming language you are already writing in.

## How It Works (The Core Concept)

An ORM maps your database structure directly to your code's object structure:

- A **Table** in your database becomes a **Class** in your code.
- A **Column** in that table becomes an **Attribute/Property** of that class.
- A specific **Row** in that table becomes an **Instance (Object)** of that class.

### Code Comparison: With vs. Without an ORM

Imagine you want to find a user named "Nikunj" in your database and update their email.

#### 1. The Traditional Way (Without ORM / Raw SQL)

You have to write a raw SQL query string, open a database connection, execute it, parse the rows manually, and handle types carefully.

```TypeScript
// Raw SQL approach
const userId = 42;
const newEmail = "nikunj@example.com";

// You have to write string-based SQL queries
await db.query("UPDATE users SET email = $1 WHERE id = $2", [newEmail, userId]);
```

#### 2. The ORM Way

The ORM gives you a clean, type-safe programmatic interface. You don't write a single line of SQL string; the ORM generates it for you behind the scenes.

```TypeScript
// ORM approach (Example using an ORM style syntax)
const user = await User.findById(42);
user.email = "nikunj@example.com";
await user.save();
```

## Why Use an ORM? (The Pros)

- **Write Less Code, Move Faster:** You don't have to spend time writing tedious `SELECT`, `INSERT`, or `UPDATE` queries for every single entity.
- **Database Agnostic:** This is a massive benefit. If you write your app using an ORM and start with SQLite for local development, you can easily switch the underlying database to PostgreSQL for production. You usually just change a configuration line; the ORM handles rewriting the SQL syntax for the new database.
- **Security out of the box:** Good ORMs automatically mitigate **SQL Injection** vulnerabilities by parameterizing queries by default.
- **Maintainability & Type Safety:** If you change a column name in your database, your compiler or IDE can immediately catch where your code breaks if you're using a type-safe ORM.

## The Trade-offs (The Cons)

While ORMs are incredible for rapid development, they aren't a silver bullet:

- **The "Black Box" Problem:** If you don't understand underlying SQL, an ORM can hide what's actually happening. It might run 100 small database queries to fetch a list (known as the $N+1$ query problem) when a single optimized SQL `JOIN` would have done the job instantly.
- **Performance Overhead:** Because the ORM has to dynamically translate your code into SQL strings and then translate the returned database rows back into language objects, it adds a small layer of CPU and memory overhead.
- **Complex Queries are Hard:** For basic CRUD (Create, Read, Update, Delete) operations, ORMs are flawless. But if you need to run highly complex analytical queries with heavy aggregations, window functions, and complex joins, writing it through an ORM syntax can become an absolute nightmare compared to just writing raw SQL.

## Popular ORM Examples

Depending on the ecosystem you work in, you'll run into different standard ORMs:

- **Node.js / TypeScript:** Prisma, TypeORM, Sequelize
- **Python:** SQLAlchemy, Django ORM
- **Java:** Hibernate
- **C# / .NET:** Entity Framework

So, lets begin with [[Prisma ORM]].