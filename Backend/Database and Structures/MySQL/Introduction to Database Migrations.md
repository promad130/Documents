## 1. What is a Database Migration?

A **database migration** is a version-controlled, ordered script that modifies your database schema (or data) in a controlled, repeatable way.

Think of it like `git` for your database structure:

- Your code evolves → you commit changes.
- Your database evolves → you write a migration.

Without migrations, schema changes are applied manually, inconsistently across environments (dev, staging, prod), and are impossible to reproduce or roll back safely.

### What migrations solve

|Problem (without migrations)|Solution (with migrations)|
|---|---|
|"What schema is on prod right now?"|Every state is tracked with a version number|
|Dev's DB differs from staging's DB|Everyone runs the same ordered migration files|
|"Who changed that column last week?"|Migration files live in git — full history|
|Bad deploy broke the schema|Roll back with a single command|
|Onboarding a new dev|`migrate up` brings their local DB to the current state|

**The core idea:** your database schema changes over time (new tables, renamed columns, added indexes). Migrations are version-controlled, ordered SQL scripts that _track and apply_ those changes — the same way `git` tracks code changes.
![[db_migration_lifecycle.svg|975]]

---

## 2. Core Concepts

### Up & Down

Every migration has two directions:

```
UP   → Apply the change   (CREATE TABLE, ADD COLUMN, CREATE INDEX…)
DOWN → Undo the change    (DROP TABLE, DROP COLUMN, DROP INDEX…)
```

The `down` function is what makes rollbacks possible. Some tools call it `revert` or `rollback`.

### The tracking table

Migration tools automatically create a special table in your database (often called `schema_migrations` or `flyway_schema_history`) that records which migrations have been applied. When you run `migrate up`, the tool:

1. Lists all migration files on disk.
2. Checks which ones are already in the tracking table.
3. Applies only the **pending** ones, in order.
4. Records each one in the tracking table.

You never edit this table manually.

### Naming conventions

Migration files are always prefixed with a sortable identifier so they run in order:

```
# Timestamp-based (most common)
20240110_094500_create_users.sql
20240203_142100_add_email_to_users.sql
20240310_080000_create_posts.sql

# Sequential number-based
001_create_users.sql
002_add_email_to_users.sql
003_create_posts.sql

# Flyway style
V1__create_users.sql
V2__add_email_to_users.sql
V3__create_posts.sql
```

> **Rule of thumb:** timestamp-based names avoid conflicts when multiple developers write migrations on the same day.

---

## 3. Writing Migrations Manually (Raw SQL)

Before using a framework, it helps to understand what migrations look like as plain SQL.

### Project structure

```
project/
├── migrations/
│   ├── 001_create_departments.sql
│   ├── 002_create_employees.sql
│   ├── 003_add_email_index.sql
│   └── 004_add_salary_audit_log.sql
└── schema.sql   ← optional "current state" snapshot
```

### Migration 001 — Create departments table

```sql
-- migrations/001_create_departments.sql

-- ===== UP =====
CREATE TABLE departments (
    dept_id   SERIAL PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ===== DOWN =====
-- DROP TABLE departments;
```

### Migration 002 — Create employees table

```sql
-- migrations/002_create_employees.sql

-- ===== UP =====
CREATE TABLE employees (
    emp_id     SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    salary     NUMERIC(10,2),
    dept_id    INT REFERENCES departments(dept_id) ON DELETE SET NULL,
    hire_date  DATE DEFAULT CURRENT_DATE,
    is_active  BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ===== DOWN =====
-- DROP TABLE employees;
```

### Migration 003 — Add an index

```sql
-- migrations/003_add_email_index.sql

-- ===== UP =====
ALTER TABLE employees ADD COLUMN email VARCHAR(120) UNIQUE;
CREATE INDEX idx_employees_dept_id ON employees(dept_id);
CREATE INDEX idx_employees_hire_date ON employees(hire_date);

-- ===== DOWN =====
-- DROP INDEX idx_employees_hire_date;
-- DROP INDEX idx_employees_dept_id;
-- ALTER TABLE employees DROP COLUMN email;
```

### Migration 004 — Rename a column (safe pattern)

```sql
-- migrations/004_rename_salary_column.sql

-- ===== UP =====
-- Step 1: Add the new column
ALTER TABLE employees ADD COLUMN compensation NUMERIC(10,2);

-- Step 2: Copy data
UPDATE employees SET compensation = salary;

-- Step 3: Add NOT NULL once data is populated
ALTER TABLE employees ALTER COLUMN compensation SET NOT NULL;

-- Step 4: Drop the old column (do this in a LATER migration if zero-downtime needed)
ALTER TABLE employees DROP COLUMN salary;

-- ===== DOWN =====
-- ALTER TABLE employees ADD COLUMN salary NUMERIC(10,2);
-- UPDATE employees SET salary = compensation;
-- ALTER TABLE employees ALTER COLUMN salary SET NOT NULL;
-- ALTER TABLE employees DROP COLUMN compensation;
```

> ⚠️ **Never rename a column in a single ALTER statement** if your app is live. The app still references the old column name and will crash. Use the expand-migrate-contract pattern (see Section 7).

---

# Tools for Database Migrations

## Prisma Migrate

In Prisma, **Database Migration** is the process of safely updating your database schema (the actual tables, columns, and relationships inside PostgreSQL, MySQL, SQLite, etc.) to match the changes you make in your TypeScript code.

Think of it as **Version Control (like Git) for your database structure**.

Instead of manually jumping into a database GUI or writing risky, raw SQL alter scripts by hand, Prisma handles the transition seamlessly through its CLI tool: **Prisma Migrate**.

### How It Works: The Workflow

Prisma Migrate works by treating your `schema.prisma` file as the absolute single source of truth. The development lifecycle follows a simple, repeatable loop:

#### Step 1: Modify your Schema

Say you have an existing database, and you decide you want to track when a user profile was created. You open your `schema.prisma` file and add a `createdAt` field to your `User` model:

```Code snippet
model User {
  id        Int      @id @default(autoincrement())
  name      String
  email     String   @unique
  createdAt DateTime @default(now()) // 👈 You just added this line
}
```

#### Step 2: Run the Migrate Command

Open your terminal and run the following command:

```Bash
npx prisma migrate dev --name add_created_at_to_user
```

#### Step 3: The Magic Behind the Scenes

When you run that command, Prisma automatically performs three crucial steps for you:

1. **Generates a Migration File:** It compares your new `schema.prisma` file against your current database structure, figures out exactly what changed, and generates a human-readable `.sql` file inside a `prisma/migrations` folder.
2. **Executes the SQL:** It runs that SQL script against your local database immediately, altering your actual database tables safely.
3. **Regenerates Prisma Client:** It automatically updates your auto-generated TypeScript types so your IDE instantly knows `user.createdAt` exists and is a valid Date object.

### Anatomoy of a Migration File

Prisma doesn't hide what it’s doing. If you look inside the newly generated migration folder, you'll find a raw SQL file that looks something like this:

```SQL
-- prisma/migrations/20260527120000_add_created_at_to_user/migration.sql

ALTER TABLE "User" ADD COLUMN "createdAt" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
```

Because these are plain text SQL files, **you can commit them directly to Git.** This means your entire team can stay perfectly in sync. When a teammate pulls your latest Git branch, they just run `npx prisma migrate dev`, and their local database updates instantly to match yours.

### Local Development vs. Production

Prisma cleanly splits migrations into two distinct environments to prevent catastrophic data loss:

#### 1. In Development (`prisma migrate dev`)

- Designed for your local machine.
- It tracks changes, creates new SQL migration steps, and applies them seamlessly.
- _Warning:_ If you make a drastic change that conflicts with existing test data (like making a column mandatory when it already has empty rows), Prisma will warn you and ask if it’s okay to reset the database.

#### 2. In Production (`prisma migrate deploy`)

- Designed for your CI/CD pipeline or live server deployment.
- **It never creates new migrations.** It simply looks at the existing folder of `.sql` files you committed to Git, checks which ones haven't been run on the production database yet, and executes them exactly as written.
- It ensures zero database resets or unexpected behavior in production.

## Why is this better than traditional methods?

- **No Dual-Sychronization:** In older frameworks, you had to update your database, update your ORM models, and update your TypeScript types separately. With Prisma, you edit **one** file, and all three layers update simultaneously.
- **Human-Inspectable:** Some ORMs execute schema changes completely hidden in the background. Prisma forces a physical `.sql` file artifact, letting you review exactly what will happen to your data before it goes live.