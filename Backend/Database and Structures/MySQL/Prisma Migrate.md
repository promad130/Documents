![[Introduction to Database Migrations#Prisma Migrate]]

Prisma is a Node.js ORM where the **source of truth is `schema.prisma`**, not raw SQL files. You describe your models in Prisma schema language, and Prisma generates the SQL migrations for you.

### How Prisma's migration flow works

```
1. You edit schema.prisma          (add a model, field, relation)
2. prisma migrate dev              (Prisma diffs the schema, generates SQL, applies it)
3. A new migration folder appears  (prisma/migrations/<timestamp>_name/migration.sql)
4. You commit both files to git    (schema.prisma + the migration folder)
5. On other environments:
   prisma migrate deploy           (applies pending migrations — no prompts, CI-safe)
```

### Install
```bash
npm install prisma @prisma/client
npx prisma init
```

`prisma init` creates:

```
prisma/
└── schema.prisma     ← your models live here
.env                  ← DATABASE_URL goes here
```

### Configure the datasource

```prisma
// prisma/schema.prisma

generator client {
  provider = "prisma-client-js"
}

datasource db {
  provider = "postgresql"
  url      = env("DATABASE_URL")
}
```

```bash
# .env
DATABASE_URL="postgresql://myuser:strongpassword@localhost:5432/mydb"
```

---

## 5. Defining Models in schema.prisma

Everything starts with your Prisma schema. Prisma maps its types to PostgreSQL column types automatically.

### Type mapping (Prisma → PostgreSQL)

|Prisma type|PostgreSQL type|
|---|---|
|`String`|`TEXT`|
|`String @db.VarChar(n)`|`VARCHAR(n)`|
|`Int`|`INTEGER`|
|`BigInt`|`BIGINT`|
|`Float`|`DOUBLE PRECISION`|
|`Decimal`|`DECIMAL(p,s)`|
|`Boolean`|`BOOLEAN`|
|`DateTime`|`TIMESTAMP WITH TIME ZONE`|
|`Json`|`JSONB`|
|`Bytes`|`BYTEA`|

### Full example schema

prisma

```prisma
// prisma/schema.prisma

model Department {
  deptId    Int        @id @default(autoincrement()) @map("dept_id")
  deptName  String     @unique @db.VarChar(100)     @map("dept_name")
  createdAt DateTime   @default(now())               @map("created_at")
  employees Employee[]

  @@map("departments")
}

model Employee {
  empId     Int        @id @default(autoincrement()) @map("emp_id")
  firstName String     @db.VarChar(50)               @map("first_name")
  lastName  String     @db.VarChar(50)               @map("last_name")
  email     String?    @unique @db.VarChar(120)
  salary    Decimal?   @db.Decimal(10, 2)
  isActive  Boolean    @default(true)                @map("is_active")
  hireDate  DateTime   @default(now())               @map("hire_date")
  createdAt DateTime   @default(now())               @map("created_at")

  deptId    Int?                                      @map("dept_id")
  dept      Department? @relation(fields: [deptId], references: [deptId])

  @@index([deptId])
  @@map("employees")
}

model Project {
  projectId   Int        @id @default(autoincrement()) @map("project_id")
  projectName String     @db.VarChar(100)              @map("project_name")
  budget      Decimal?   @db.Decimal(12, 2)

  @@map("projects")
}
```

### Key Prisma schema attributes

|Attribute|Purpose|
|---|---|
|`@id`|Primary key|
|`@default(autoincrement())`|Auto-increment (SERIAL)|
|`@default(now())`|Default to current timestamp|
|`@default(uuid())`|Default to a UUID|
|`@unique`|UNIQUE constraint|
|`@map("col_name")`|Map Prisma field to a different DB column name|
|`@@map("table_name")`|Map Prisma model to a different DB table name|
|`@@index([field])`|Create a database index|
|`@@unique([a, b])`|Composite unique constraint|
|`@db.VarChar(n)`|Use VARCHAR instead of TEXT|
|`@db.Decimal(p, s)`|Precise decimal type|
|`@relation(...)`|Define a foreign key relation|
|`?` after type|Nullable column|

---

## 6. Core Prisma Migration Commands

### `prisma migrate dev` — development workflow

The command you use **locally** while building features. It:

1. Detects changes between your schema and the database.
2. Generates a new SQL migration file.
3. Applies it to your local database.
4. Re-generates `@prisma/client`.

bash

```bash
# After editing schema.prisma:
npx prisma migrate dev --name add_phone_to_employees
```

This creates:

```
prisma/migrations/
  20240310120000_add_phone_to_employees/
    migration.sql     ← the generated SQL (review this!)
```

> Always commit both `schema.prisma` and the new `migrations/` folder to git.

---

### `prisma migrate deploy` — production / CI

The command you use in **staging, production, and CI pipelines**. It:

- Applies only pending migrations (never generates new ones).
- Never prompts for confirmation — safe for automation.
- Fails if the schema drift is detected.

bash

```bash
npx prisma migrate deploy
```

---

### `prisma migrate reset` — wipe and rebuild (dev only)

Drops the database, re-creates it, and applies all migrations from scratch. Then runs seed if configured. Use only in development.

bash

```bash
npx prisma migrate reset
```

---

### `prisma migrate status` — inspect state

Shows which migrations have been applied and which are pending.

bash

```bash
npx prisma migrate status
```

Example output:

```
3 migrations found in prisma/migrations

✔  20240110094500_create_departments
✔  20240203142100_add_email_to_employees
✗  20240310080000_create_posts         ← pending
```

---

### `prisma db pull` — introspect an existing database

If you have an existing database with no Prisma schema, this reverse-engineers it into `schema.prisma`.

bash

```bash
npx prisma db pull
```

Useful when migrating a legacy project to Prisma. After running, review the generated schema, then run `prisma migrate dev` to start tracking changes.

---

### `prisma db push` — quick sync without migration files

Syncs the schema to the database **without creating migration files**. Good for quick prototyping. Not for production — it can destroy data.

bash

```bash
npx prisma db push
```

> Use `prisma migrate dev` once you want to start tracking migrations properly.

---

### `prisma generate` — regenerate the client

After any schema change, regenerate the type-safe Prisma client:

bash

```bash
npx prisma generate
```

This is run automatically by `migrate dev` but you need it explicitly after manual schema edits.

---

## 7. What Prisma Generates — Reading the SQL

Always open and read `migration.sql` after running `migrate dev`. Prisma's output is correct for simple changes but you should understand what it's doing.

### Adding a field

Schema change:

prisma

```prisma
model Employee {
  // ...existing fields...
  phone String? @db.VarChar(20)
}
```

Generated `migration.sql`:

sql

```sql
ALTER TABLE "employees" ADD COLUMN "phone" VARCHAR(20);
```

---

### Adding a relation (foreign key)

Schema change — add a `managerId` self-relation:

prisma

```prisma
model Employee {
  // ...
  managerId Int?      @map("manager_id")
  manager   Employee? @relation("Reports", fields: [managerId], references: [empId])
  reports   Employee[] @relation("Reports")
}
```

Generated SQL:

sql

```sql
ALTER TABLE "employees" ADD COLUMN "manager_id" INTEGER;
ALTER TABLE "employees"
    ADD CONSTRAINT "employees_manager_id_fkey"
    FOREIGN KEY ("manager_id")
    REFERENCES "employees"("emp_id")
    ON DELETE SET NULL ON UPDATE CASCADE;
```

---

### Adding a unique constraint

Schema change:

prisma

```prisma
model Employee {
  // ...
  @@unique([firstName, lastName])
}
```

Generated SQL:

sql

```sql
CREATE UNIQUE INDEX "employees_first_name_last_name_key"
    ON "employees"("first_name", "last_name");
```

---

### Adding an index

Schema change:

prisma

```prisma
model Employee {
  // ...
  @@index([hireDate])
}
```

Generated SQL:

sql

```sql
CREATE INDEX "employees_hire_date_idx" ON "employees"("hire_date");
```

---

### Removing a field

Schema change — remove the `phone` field from the model.

Generated SQL:

sql

```sql
ALTER TABLE "employees" DROP COLUMN "phone";
```

> Prisma will warn you before running a migration that **drops data**. Always review. Never run `migrate dev` without reading the generated SQL when removing fields.

---

## 8. Advanced Patterns with Prisma

### Expand-Migrate-Contract (zero-downtime column rename)

Prisma does not have native rename support — it treats a rename as a drop + add, which loses data. Use three separate migrations instead.

**Phase 1 — Expand:** Add the new column alongside the old one.

Edit `schema.prisma`:

prisma

```prisma
model Employee {
  salary        Decimal? @db.Decimal(10, 2)      // keep for now
  compensation  Decimal? @db.Decimal(10, 2)      // add new
}
```

bash

```bash
npx prisma migrate dev --name expand_add_compensation
```

Generated SQL:

sql

```sql
ALTER TABLE "employees" ADD COLUMN "compensation" DECIMAL(10,2);
```

**Phase 2 — Backfill:** Write a custom migration to copy data.

bash

```bash
npx prisma migrate dev --name backfill_compensation
```

Then manually edit the generated `migration.sql` before applying:

sql

```sql
UPDATE "employees" SET "compensation" = "salary";
```

Update your app to write to both columns during the transition.

**Phase 3 — Contract:** Remove the old column once all app instances use the new field.

Edit `schema.prisma` — remove `salary`.

bash

```bash
npx prisma migrate dev --name contract_drop_salary
```

Generated SQL:

sql

```sql
ALTER TABLE "employees" DROP COLUMN "salary";
```

---

### Custom SQL in a Prisma migration

Sometimes you need SQL that Prisma can't auto-generate: `CREATE INDEX CONCURRENTLY`, triggers, views, stored procedures. Add it manually to the migration file.

```bash
# Create a blank migration
npx prisma migrate dev --name add_concurrent_index --create-only
# --create-only generates the file but does NOT apply it yet
```

Open the generated `migration.sql` and write your custom SQL:

```sql
-- prisma/migrations/20240315_add_concurrent_index/migration.sql

-- Prisma-generated part (if any schema changes exist):
ALTER TABLE "employees" ADD COLUMN "phone" VARCHAR(20);

-- Custom part added manually:
CREATE INDEX CONCURRENTLY "employees_email_idx" ON "employees"("email");
```

Then apply it:

```bash
npx prisma migrate dev
```

> `CREATE INDEX CONCURRENTLY` cannot run inside a transaction. In Prisma's `migrate deploy`, each migration runs in a transaction by default. To opt out, add this at the top of the migration file:
> 
> sql
> 
> ```sql
> -- This migration does not run in a transaction
> -- prisma-migrate-disable-foreign-keys
> ```
> 
> Or use `@db.raw` in a seed script for truly non-transactional operations.

---

### Data migrations in Prisma

Prisma doesn't have a built-in data migration API — use a seed script or a standalone Node script.

**Option 1 — Custom SQL in the migration file:**

sql

```sql
-- migration.sql
-- Promote all Engineering employees salary by 5%
UPDATE "employees" AS e
SET "salary" = e."salary" * 1.05
FROM "departments" AS d
WHERE e."dept_id" = d."dept_id"
  AND d."dept_name" = 'Engineering';
```

**Option 2 — A standalone Node.js script (safer for complex logic):**

typescript

```typescript
// scripts/migrate-data.ts
import { PrismaClient } from '@prisma/client'

const prisma = new PrismaClient()

async function main() {
  const result = await prisma.$executeRaw`
    UPDATE employees AS e
    SET salary = e.salary * 1.05
    FROM departments AS d
    WHERE e.dept_id = d.dept_id
      AND d.dept_name = 'Engineering'
  `
  console.log(`Updated ${result} rows`)
}

main()
  .catch(console.error)
  .finally(() => prisma.$disconnect())
```

bash

```bash
npx ts-node scripts/migrate-data.ts
```

---

### Seeding the database

typescript

```typescript
// prisma/seed.ts
import { PrismaClient } from '@prisma/client'

const prisma = new PrismaClient()

async function main() {
  const engineering = await prisma.department.upsert({
    where:  { deptName: 'Engineering' },
    update: {},
    create: { deptName: 'Engineering' },
  })

  await prisma.employee.createMany({
    data: [
      { firstName: 'Alice', lastName: 'Kumar',  email: 'alice@example.com', salary: 90000, deptId: engineering.deptId },
      { firstName: 'Bob',   lastName: 'Sharma', email: 'bob@example.com',   salary: 85000, deptId: engineering.deptId },
    ],
    skipDuplicates: true,
  })

  console.log('Seed complete')
}

main()
  .catch(console.error)
  .finally(() => prisma.$disconnect())
```

Configure the seed script in `package.json`:

json

```json
{
  "prisma": {
    "seed": "ts-node prisma/seed.ts"
  }
}
```

Run the seed:

bash

```bash
npx prisma db seed

# Or automatically after migrate reset:
npx prisma migrate reset   # resets DB + runs seed automatically
```

---

### Adding constraints safely on large tables

Prisma generates standard `ALTER TABLE ... ADD CONSTRAINT` which locks the table. For production with large datasets, edit the migration SQL manually:

sql

```sql
-- Instead of Prisma's generated:
-- ALTER TABLE "employees" ADD CONSTRAINT "chk_salary" CHECK (salary > 0);

-- Use the non-blocking two-step approach:
ALTER TABLE "employees"
    ADD CONSTRAINT "chk_salary_positive" CHECK (salary > 0) NOT VALID;

ALTER TABLE "employees" VALIDATE CONSTRAINT "chk_salary_positive";
```

---

## 9. Squashing Migrations

Over time you accumulate many migration files. Squashing collapses them into a single baseline. Do this carefully — all environments must be in sync first.

bash

```bash
# Step 1: make sure all environments have run all existing migrations
npx prisma migrate status   # all should show ✔

# Step 2: dump the current schema as a single SQL file
pg_dump --schema-only -d mydb > prisma/migrations/0_init/migration.sql

# Step 3: delete all old migration folders EXCEPT the new 0_init one

# Step 4: mark the baseline as already applied (so Prisma doesn't try to re-run it)
npx prisma migrate resolve --applied 0_init
```

Everyone on the team then needs to run `prisma migrate reset` locally to realign their database with the new baseline.

---

## 10. Migration Best Practices

### Do

- **Always read the generated `migration.sql`** before committing. Prisma's auto-diff is good but not perfect — especially for renames and complex changes.
- **Commit schema + migration in the same PR.** Never separate them.
- **Use `--create-only` for risky changes.** Review and edit the SQL before applying.
- **Use `migrate deploy` in CI/production.** Never `migrate dev` outside of local development.
- **Write seed data** for your test environments — makes `migrate reset` reliable.
- **Use `prisma migrate status`** in your deployment pre-check to catch drift early.
- **Back up production before running migrations** on large tables.

### Don't

- **Never delete a migration folder** that has been applied to any environment.
- **Never manually edit a migration that has already been applied.** Prisma validates checksums and will throw an error.
- **Never rename a model field** directly if the table is live — Prisma treats it as drop + add (data loss). Use the Expand-Migrate-Contract pattern.
- **Never use `prisma db push` in production.** It bypasses migration tracking.
- **Don't squash without coordinating** with your team — everyone must reset their local state.

---

## 11. Migrations in CI/CD

### GitHub Actions example

yaml

```yaml
# .github/workflows/deploy.yml
name: Deploy

on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Install dependencies
        run: npm ci

      - name: Generate Prisma client
        run: npx prisma generate

      - name: Run migrations
        run: npx prisma migrate deploy
        env:
          DATABASE_URL: ${{ secrets.DATABASE_URL }}

      - name: Deploy application
        run: ./deploy.sh
```

### Docker entrypoint

bash

```bash
#!/bin/bash
# docker-entrypoint.sh

echo "Generating Prisma client..."
npx prisma generate

echo "Running migrations..."
npx prisma migrate deploy

echo "Starting app..."
exec node dist/index.js
```

### Pre-deploy check

bash

```bash
# Check for pending migrations before deploying
STATUS=$(npx prisma migrate status 2>&1)
if echo "$STATUS" | grep -q "following migration"; then
  echo "Pending migrations found — run prisma migrate deploy"
  exit 1
fi
```

---

## 12. Quick Reference

bash

```bash
# --- Development ---
npx prisma migrate dev --name <description>    # generate + apply migration
npx prisma migrate dev --create-only           # generate only, don't apply yet
npx prisma migrate reset                        # wipe DB, re-apply all, run seed
npx prisma db push                             # sync schema without migration file (prototype only)
npx prisma db pull                             # reverse-engineer existing DB into schema.prisma
npx prisma generate                            # regenerate Prisma client

# --- Inspection ---
npx prisma migrate status                      # show applied / pending migrations
npx prisma studio                              # open browser-based DB GUI

# --- Production / CI ---
npx prisma migrate deploy                      # apply pending migrations (no prompts)
npx prisma migrate resolve --applied <name>    # mark a migration as applied (baseline)
npx prisma migrate resolve --rolled-back <name> # mark a migration as rolled back

# --- Seed ---
npx prisma db seed                             # run seed script
```

---

## 13. Prisma Schema Cheat Sheet

prisma

```prisma
// Scalar field types
field  String                        // TEXT
field  String   @db.VarChar(100)     // VARCHAR(100)
field  Int                           // INTEGER
field  BigInt                        // BIGINT
field  Float                         // DOUBLE PRECISION
field  Decimal  @db.Decimal(10, 2)   // DECIMAL(10,2)
field  Boolean                       // BOOLEAN
field  DateTime                      // TIMESTAMPTZ
field  Json                          // JSONB
field  String?                       // nullable

// Common attributes
@id
@default(autoincrement())
@default(uuid())
@default(now())
@default(true)
@unique
@map("db_column_name")
@@map("db_table_name")
@@index([field1, field2])
@@unique([field1, field2])

// Relations
@relation(fields: [foreignKeyField], references: [primaryKeyField])
@relation(onDelete: Cascade)
@relation(onDelete: SetNull)
@relation(onDelete: Restrict)
```