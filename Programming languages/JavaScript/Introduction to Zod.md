## **What is Zod?**

**Zod is a TypeScript-first schema validation library.** It lets you define rules for what data should look like, then validates data against those rules. If the data matches, you get a typed object. If it doesn't, you get clear error messages.

**Why use Zod?**
- Define validation rules once
- Get TypeScript types automatically
- Validate API requests, forms, environment variables
- Type-safe error handling
- Works with both TypeScript and JavaScript

---

## **Installation**

```bash
npm install zod
```

---

## **1. Basic Concept: What is a Schema?**

A **schema** is a blueprint that describes what data should look like.

### **Simple Analogy:**

```
Real World:
  Passport = Schema
  Your personal info (name, age, nationality) = Data
  Passport verification = Validation
  
Zod:
  z.object({name: z.string(), age: z.number()}) = Schema
  {name: "John", age: 25} = Data
  schema.parse(data) = Validation
```

### **Visual Diagram:**

```
┌─────────────────────────────────────────────────────┐
│                  ZODS FLOW                          │
├─────────────────────────────────────────────────────┤
│                                                     │
│  Define Schema          Raw Data      Validation    │
│       │                    │             │          │
│  z.object({         → {name: "John" → schema.parse()│
│    name: z.string()   age: 25      │         │      │
│    age: z.number()   }             │    ┌─────────┐ │
│  })                                │    │ Success?│ │
│       │                            │    └─────────┘ │
│                                    │      ↓    ↓    │
│                              Valid Data / Errors    │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## **2. Your First Schema**

```javascript
const { z } = require('zod');

// Define a schema
const UserSchema = z.object({
    name: z.string(),
    age: z.number(),
    email: z.string()
});

// Valid data
const validUser = {
    name: "John",
    age: 30,
    email: "john@example.com"
};

// Validate it
const result = UserSchema.safeParse(validUser);

console.log(result);
// Output:
// {
//   success: true,
//   data: { name: "John", age: 30, email: "john@example.com" }
// }
```

**That's it.** You define what the data should look like, then validate it.

---

## **3. Two Ways to Validate: `parse()` vs `safeParse()`**

### **`parse()` - Throws Errors**

```javascript
const UserSchema = z.object({
    name: z.string(),
    age: z.number()
});

// Valid data - works fine
const user = UserSchema.parse({ name: "John", age: 30 });
console.log(user); // { name: "John", age: 30 }

// Invalid data - THROWS AN ERROR
try {
    UserSchema.parse({ name: "John", age: "thirty" });
} catch (error) {
    console.error('Validation failed!', error);
}
```

### **`safeParse()` - Returns Result Object (Better for APIs)**

```javascript
const UserSchema = z.object({
    name: z.string(),
    age: z.number()
});

// Valid data
const result1 = UserSchema.safeParse({ name: "John", age: 30 });
console.log(result1);
// { success: true, data: { name: "John", age: 30 } }

// Invalid data - doesn't throw, returns error
const result2 = UserSchema.safeParse({ name: "John", age: "thirty" });
console.log(result2);
// {
//   success: false,
//   error: ZodError { ... }
// }

// Check result
if (result2.success) {
    console.log(result2.data);
} else {
    console.log(result2.error.errors);
}
```

**When to use which?**
- **`parse()`** → Internal code where throwing is acceptable
- **`safeParse()`** → API endpoints, form validation (don't want to crash)

---

## **4. Basic Types (Primitives)**

Zod has schemas for all basic types:

```javascript
const { z } = require('zod');

// String
z.string().parse("hello"); // ✓
z.string().parse(123);     // ✗ Error

// Number
z.number().parse(42);      // ✓
z.number().parse("42");    // ✗ Error

// Boolean
z.boolean().parse(true);   // ✓
z.boolean().parse(1);      // ✗ Error

// Date
z.date().parse(new Date()); // ✓
z.date().parse("2024-01-01"); // ✗ Error

// Null & Undefined
z.null().parse(null);      // ✓
z.undefined().parse(undefined); // ✓

// Literal (exact values)
z.literal("admin").parse("admin"); // ✓
z.literal("admin").parse("user");  // ✗ Error

// Enum (one of several values)
z.enum(["red", "green", "blue"]).parse("red"); // ✓
z.enum(["red", "green", "blue"]).parse("yellow"); // ✗ Error
```

---

## **5. Strings with Validation Rules**

Strings can have additional constraints:

```javascript
const { z } = require('zod');

// Minimum length
z.string().min(3).parse("ab");  // ✗ Error: too short
z.string().min(3).parse("abc"); // ✓

// Maximum length
z.string().max(5).parse("abcdef"); // ✗ Error: too long
z.string().max(5).parse("abcde");  // ✓

// Exact length
z.string().length(5).parse("hello"); // ✓
z.string().length(5).parse("hi");    // ✗ Error

// Email validation
z.string().email().parse("john@example.com"); // ✓
z.string().email().parse("invalid-email");    // ✗ Error

// URL validation
z.string().url().parse("https://example.com"); // ✓

// Regex pattern
z.string().regex(/^[A-Z]+$/).parse("HELLO"); // ✓
z.string().regex(/^[A-Z]+$/).parse("Hello"); // ✗ Error

// Uppercase/lowercase
z.string().toLowerCase().parse("HELLO");   // "hello"
z.string().toUpperCase().parse("hello");   // "HELLO"

// Combinations (chaining)
z.string()
  .min(8)
  .email()
  .toLowerCase()
  .parse("JOHN@EXAMPLE.COM"); // ✓ "john@example.com"
```

---

## **6. Numbers with Validation Rules**

```javascript
const { z } = require('zod');

// Minimum value
z.number().min(0).parse(-5);  // ✗ Error
z.number().min(0).parse(10);  // ✓

// Maximum value
z.number().max(100).parse(150); // ✗ Error
z.number().max(100).parse(50);  // ✓

// Integer only
z.number().int().parse(5);    // ✓
z.number().int().parse(5.5);  // ✗ Error

// Positive/Negative
z.number().positive().parse(5);    // ✓
z.number().positive().parse(-5);   // ✗ Error
z.number().negative().parse(-5);   // ✓

// Multiple of
z.number().multipleOf(5).parse(15); // ✓
z.number().multipleOf(5).parse(12); // ✗ Error

// Combinations
z.number().min(0).max(100).int().parse(50); // ✓
```

---

## **7. Arrays**

```javascript
const { z } = require('zod');

// Array of strings
z.array(z.string()).parse(["a", "b", "c"]); // ✓
z.array(z.string()).parse([1, 2, 3]);       // ✗ Error

// Array with length constraints
z.array(z.number()).min(1).parse([]);       // ✗ Error: empty
z.array(z.number()).max(3).parse([1,2,3,4]); // ✗ Error: too long

// Exact length
z.array(z.string()).length(2).parse(["a", "b"]); // ✓

// Array of objects
const UserSchema = z.array(
    z.object({
        name: z.string(),
        age: z.number()
    })
);

UserSchema.parse([
    { name: "John", age: 30 },
    { name: "Jane", age: 25 }
]); // ✓
```

---

## **8. Objects (Most Important)**

Objects are the most common schema type:

```javascript
const { z } = require('zod');

const UserSchema = z.object({
    name: z.string(),
    age: z.number(),
    email: z.string().email()
});

// Valid
UserSchema.parse({
    name: "John",
    age: 30,
    email: "john@example.com"
}); // ✓

// Invalid - extra properties are rejected by default
UserSchema.parse({
    name: "John",
    age: 30,
    email: "john@example.com",
    phone: "123-456-7890"  // Extra property
}); // ✗ Error: unexpected key "phone"

// Allow extra properties
const FlexibleSchema = z.object({
    name: z.string(),
    age: z.number()
}).passthrough(); // Allow extra properties

FlexibleSchema.parse({
    name: "John",
    age: 30,
    phone: "123-456-7890"
}); // ✓
```

---

## **9. Optional and Default Values**

```javascript
const { z } = require('zod');

const UserSchema = z.object({
    name: z.string(),
    nickname: z.string().optional(),        // Can be undefined
    age: z.number().default(18),            // Default value
    phone: z.string().nullable().optional() // Can be null or undefined
});

// Missing optional field - OK
UserSchema.parse({
    name: "John",
    age: 30
}); // ✓ { name: "John", nickname: undefined, age: 30, phone: undefined }

// Missing default field - filled in automatically
UserSchema.parse({
    name: "John"
}); // ✓ { name: "John", nickname: undefined, age: 18, phone: undefined }
```

### **Understanding Optional vs Default:**

```javascript
z.string().optional()  // undefined if missing
z.string().default("") // empty string if missing
z.string().nullable()  // can be null
```

---

## **10. Nested Objects (Objects Inside Objects)**

```javascript
const { z } = require('zod');

// Define address schema
const AddressSchema = z.object({
    street: z.string(),
    city: z.string(),
    zipCode: z.string().length(5)
});

// Use it in another schema
const UserSchema = z.object({
    name: z.string(),
    email: z.string().email(),
    address: AddressSchema  // Nested!
});

// Validate
UserSchema.parse({
    name: "John",
    email: "john@example.com",
    address: {
        street: "123 Main St",
        city: "New York",
        zipCode: "10001"
    }
}); // ✓

// Invalid nested data
UserSchema.parse({
    name: "John",
    email: "john@example.com",
    address: {
        street: "123 Main St",
        city: "New York",
        zipCode: "100" // Invalid - too short
    }
}); // ✗ Error in nested field
```

---

## **11. Union Types (Either/Or)**

Sometimes data can be one of several types:

```javascript
const { z } = require('zod');

// String or number
const IdSchema = z.union([
    z.string(),
    z.number()
]);

IdSchema.parse("abc123");  // ✓
IdSchema.parse(123);       // ✓
IdSchema.parse(true);      // ✗ Error

// Shorthand with |
const IdSchema2 = z.string().or(z.number());

// More complex union
const NotificationSchema = z.union([
    z.object({
        type: z.literal("email"),
        email: z.string().email()
    }),
    z.object({
        type: z.literal("sms"),
        phone: z.string()
    })
]);

NotificationSchema.parse({
    type: "email",
    email: "john@example.com"
}); // ✓

NotificationSchema.parse({
    type: "sms",
    phone: "123-456-7890"
}); // ✓

NotificationSchema.parse({
    type: "email",
    phone: "123-456-7890"  // Wrong field for email type
}); // ✗ Error
```

---

## **12. Custom Validation with `refine()`**

For validation rules that can't be expressed with built-in validators:

```javascript
const { z } = require('zod');

// Password must be strong
const PasswordSchema = z.string()
    .min(8)
    .refine(
        (val) => /[A-Z]/.test(val),
        { message: "Must contain uppercase letter" }
    )
    .refine(
        (val) => /[0-9]/.test(val),
        { message: "Must contain number" }
    )
    .refine(
        (val) => /[!@#$%^&*]/.test(val),
        { message: "Must contain special character" }
    );

PasswordSchema.parse("Weak");        // ✗ Error
PasswordSchema.parse("Weak123!");    // ✓

// Check if password and confirm match
const RegistrationSchema = z.object({
    password: z.string().min(8),
    confirmPassword: z.string().min(8)
}).refine(
    (data) => data.password === data.confirmPassword,
    {
        message: "Passwords don't match",
        path: ["confirmPassword"]  // Which field to mark as error
    }
);

RegistrationSchema.parse({
    password: "Secret123!",
    confirmPassword: "Secret123!"
}); // ✓

RegistrationSchema.parse({
    password: "Secret123!",
    confirmPassword: "Different"
}); // ✗ Error: confirmPassword field
```

---

## **13. Inferring TypeScript Types from Schemas**

This is the magic of Zod - **automatic type inference**:

```typescript
import { z } from 'zod';

// Define schema
const UserSchema = z.object({
    id: z.number(),
    name: z.string(),
    email: z.string().email(),
    age: z.number().min(0).max(120).optional(),
    isAdmin: z.boolean().default(false)
});

// Infer TypeScript type from schema
type User = z.infer<typeof UserSchema>;

// Equivalent to:
// type User = {
//   id: number;
//   name: string;
//   email: string;
//   age?: number;
//   isAdmin: boolean;
// };

// Now use it
const user: User = UserSchema.parse(someData);
// TypeScript knows `user.id` is a number, `user.name` is a string, etc.
```

**This is powerful** - You define validation logic once, and TypeScript automatically creates the correct type.

---

## **14. Real-World Example: API Request Validation**

```javascript
const express = require('express');
const { z } = require('zod');

const app = express();
app.use(express.json());

// Define schema for user creation
const CreateUserSchema = z.object({
    name: z.string().min(1).max(100),
    email: z.string().email(),
    age: z.number().int().min(18).max(120),
    role: z.enum(["user", "admin"]).default("user")
});

// API endpoint
app.post('/users', (req, res) => {
    // Validate request body
    const result = CreateUserSchema.safeParse(req.body);
    
    if (!result.success) {
        // Send validation errors
        return res.status(400).json({
            error: "Validation failed",
            issues: result.error.errors
        });
    }
    
    // Use validated data
    const user = result.data;
    console.log(`Creating user: ${user.name}`);
    
    res.status(201).json({
        success: true,
        user
    });
});

app.listen(3000);

/*
Example requests:

POST /users
{
  "name": "John",
  "email": "john@example.com",
  "age": 25
}
Response: 201 { success: true, user: {...} }

POST /users
{
  "name": "Jane",
  "email": "invalid-email",
  "age": 25
}
Response: 400 { error: "Validation failed", issues: [...] }

POST /users
{
  "name": "Bob",
  "email": "bob@example.com",
  "age": 16
}
Response: 400 { error: "Validation failed", issues: [...] }
(age is less than 18)
*/
```

---

## **15. Error Handling - Understanding Errors**

```javascript
const { z } = require('zod');

const Schema = z.object({
    name: z.string().min(3),
    age: z.number().int(),
    email: z.string().email()
});

const result = Schema.safeParse({
    name: "Jo",           // Too short
    age: 25.5,            // Not integer
    email: "not-email"    // Invalid email
});

if (!result.success) {
    console.log(result.error.errors);
    // Output:
    // [
    //   {
    //     code: "too_small",
    //     minimum: 3,
    //     type: "string",
    //     path: ["name"],
    //     message: "String must contain at least 3 character(s)"
    //   },
    //   {
    //     code: "not_a_type",
    //     expected: "integer",
    //     received: "float",
    //     path: ["age"],
    //     message: "Expected integer, received float"
    //   },
    //   {
    //     code: "invalid_string",
    //     validation: "email",
    //     path: ["email"],
    //     message: "Invalid email"
    //   }
    // ]
    
    // Format errors nicely
    const formatted = result.error.format();
    console.log(formatted);
    // {
    //   name: { _errors: ["String must contain at least 3..."] },
    //   age: { _errors: ["Expected integer, received float"] },
    //   email: { _errors: ["Invalid email"] }
    // }
}
```

---

## **16. Quick Reference Cheat Sheet**

```javascript
// Types
z.string()
z.number()
z.boolean()
z.date()
z.array(z.string())
z.object({ key: z.string() })
z.enum(["a", "b"])
z.union([z.string(), z.number()])

// String methods
.min(n), .max(n), .length(n)
.email(), .url()
.toLowerCase(), .toUpperCase()
.regex(/pattern/)

// Number methods
.min(n), .max(n)
.int(), .positive(), .negative()
.multipleOf(n)

// Object methods
.optional()         // Can be undefined
.default(value)     // Default value
.nullable()         // Can be null
.passthrough()      // Allow extra properties

// Validation
.parse(data)        // Throws on error
.safeParse(data)    // Returns { success, data | error }

// Type inference
z.infer<typeof schema>

// Custom validation
.refine((val) => condition, { message: "..." })
```

---

## **17. Visual Flow Diagram**

```
┌─────────────────────────────────────────────────────────┐
│              HOW ZOD WORKS VISUALLY                     │
└─────────────────────────────────────────────────────────┘

1. DEFINE SCHEMA
   ┌──────────────────────────┐
   │ z.object({               │
   │   name: z.string(),      │
   │   age: z.number().min(0) │
   │ })                       │
   └──────────────────────────┘

2. GET DATA (from API, form, etc)
   ┌──────────────────────────┐
   │ {                        │
   │   name: "John",          │
   │   age: 30,               │
   │   extra: "field"         │
   │ }                        │
   └──────────────────────────┘

3. VALIDATE
   ┌──────────────────────────┐
   │ schema.safeParse(data)   │
   │                          │
   │ Zod checks:              │
   │ ✓ name is string         │
   │ ✓ age is number          │
   │ ✗ age >= 0               │
   │ ✗ extra field not in spec│
   └──────────────────────────┘

4. RESULT
   ┌──────────────────────────┐
   │ {                        │
   │   success: false,        │
   │   error: {               │
   │     errors: [            │
   │       { path: ["extra"]  │
   │       { path: ["age"]    │
   │     ]                    │
   │   }                      │
   │ }                        │
   └──────────────────────────┘
```

---

# Nested Objects in Zod
## **What Are Nested Objects?**

A **nested object** is an object inside another object. In Zod, it means defining a schema that contains other schemas.

**Real-world analogy:**
```
Person has a Name object
  └─ firstName: string
  └─ lastName: string

Person has an Address object
  └─ street: string
  └─ city: string
  └─ zipCode: string
```

In code, the Person object **nests** the Name and Address objects inside it.

---

## **Simple Nested Object Example**

### **Without Nesting (Flat)**

```javascript
const { z } = require('zod');

// All fields at the same level
const UserSchema = z.object({
    firstName: z.string(),
    lastName: z.string(),
    street: z.string(),
    city: z.string(),
    zipCode: z.string()
});

UserSchema.parse({
    firstName: "John",
    lastName: "Doe",
    street: "123 Main St",
    city: "New York",
    zipCode: "10001"
}); // ✓
```

### **With Nesting (Structured)**

```javascript
const { z } = require('zod');

// Create a Name schema
const NameSchema = z.object({
    firstName: z.string(),
    lastName: z.string()
});

// Create an Address schema
const AddressSchema = z.object({
    street: z.string(),
    city: z.string(),
    zipCode: z.string()
});

// Nest them in the User schema
const UserSchema = z.object({
    name: NameSchema,        // Nested!
    address: AddressSchema   // Nested!
});

// Validate
UserSchema.parse({
    name: {
        firstName: "John",
        lastName: "Doe"
    },
    address: {
        street: "123 Main St",
        city: "New York",
        zipCode: "10001"
    }
}); // ✓
```

**Benefits of nesting:**
- Organized structure
- Reusable schemas
- Clearer code
- Matches real data structure

---

## **Visual Diagram: Nested Object Structure**

```
┌─────────────────────────────────────────────────────────────────┐
│                    USER SCHEMA (Nested)                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────────────────────────┐                       │
│  │ name: NAME SCHEMA                   │                       │
│  │ ├─ firstName: string                │                       │
│  │ └─ lastName: string                 │                       │
│  └───────────────���─────────────────────┘                       │
│                                                                 │
│  ┌─────────────────────────────────────┐                       │
│  │ address: ADDRESS SCHEMA             │                       │
│  │ ├─ street: string                   │                       │
│  │ ├─ city: string                     │                       │
│  │ └─ zipCode: string                  │                       │
│  └─────────────────────────────────────┘                       │
│                                                                 │
│  ┌─────────────────────────────────────┐                       │
│  │ contact: CONTACT SCHEMA             │                       │
│  │ ├─ email: string (email format)    │                       │
│  │ └─ phone: string                    │                       │
│  └─────────────────────────────────────┘                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## **Example 1: Simple Two-Level Nesting**

```javascript
const { z } = require('zod');

// Level 1: Define inner schema
const AddressSchema = z.object({
    street: z.string(),
    city: z.string(),
    zipCode: z.string().length(5)
});

// Level 2: Define outer schema with nested schema
const UserSchema = z.object({
    id: z.number(),
    name: z.string(),
    address: AddressSchema  // Nested here!
});

// Valid data
const validUser = {
    id: 1,
    name: "John",
    address: {
        street: "123 Main St",
        city: "New York",
        zipCode: "10001"
    }
};

UserSchema.parse(validUser); // ✓

// Invalid nested data - error in address
const invalidUser = {
    id: 1,
    name: "John",
    address: {
        street: "123 Main St",
        city: "New York",
        zipCode: "100"  // Invalid - too short
    }
};

const result = UserSchema.safeParse(invalidUser);
console.log(result.error.errors);
// Error points to path: ["address", "zipCode"]
// Message: "String must contain exactly 5 character(s)"
```

---

## **Example 2: Multiple Nested Objects**

```javascript
const { z } = require('zod');

// Multiple schemas to nest
const CompanySchema = z.object({
    name: z.string(),
    industry: z.string()
});

const LocationSchema = z.object({
    city: z.string(),
    country: z.string()
});

const ContactSchema = z.object({
    email: z.string().email(),
    phone: z.string()
});

// Main schema with multiple nested objects
const EmployeeSchema = z.object({
    name: z.string(),
    company: CompanySchema,      // Nested 1
    location: LocationSchema,    // Nested 2
    contact: ContactSchema       // Nested 3
});

// Validate
EmployeeSchema.parse({
    name: "Alice",
    company: {
        name: "Tech Corp",
        industry: "Software"
    },
    location: {
        city: "San Francisco",
        country: "USA"
    },
    contact: {
        email: "alice@techcorp.com",
        phone: "+1-555-0123"
    }
}); // ✓
```

---

## **Example 3: Deep Nesting (3+ Levels)**

Objects can be nested multiple levels deep:

```javascript
const { z } = require('zod');

// Level 1
const StreetSchema = z.object({
    number: z.number(),
    name: z.string()
});

// Level 2
const AddressSchema = z.object({
    street: StreetSchema,  // Nested Level 1 schema
    city: z.string(),
    zipCode: z.string()
});

// Level 3
const PersonSchema = z.object({
    name: z.string(),
    address: AddressSchema  // Nested Level 2 schema
});

// Valid data (3 levels deep)
PersonSchema.parse({
    name: "John",
    address: {
        street: {
            number: 123,
            name: "Main St"
        },
        city: "New York",
        zipCode: "10001"
    }
}); // ✓

// Error occurs deep in the structure
const result = PersonSchema.safeParse({
    name: "John",
    address: {
        street: {
            number: "123",  // Should be number, not string
            name: "Main St"
        },
        city: "New York",
        zipCode: "10001"
    }
});

console.log(result.error.errors[0].path);
// ["address", "street", "number"]
// Error path shows exactly where the problem is!
```

---

## **Example 4: Nested Objects with Validation Rules**

Validation rules work at any level of nesting:

```javascript
const { z } = require('zod');

const ContactSchema = z.object({
    email: z.string().email(),        // Email validation
    phone: z.string().regex(/^\d{10}$/), // 10 digits only
    website: z.string().url().optional()
});

const CompanySchema = z.object({
    name: z.string().min(1).max(100),
    employees: z.number().min(1),
    contact: ContactSchema
});

// Invalid - phone doesn't match regex
const result = CompanySchema.safeParse({
    name: "Tech Corp",
    employees: 50,
    contact: {
        email: "info@techcorp.com",
        phone: "123",  // ✗ Should be 10 digits
        website: "https://techcorp.com"
    }
});

if (!result.success) {
    console.log(result.error.errors[0]);
    // {
    //   code: 'invalid_string',
    //   validation: 'regex',
    //   path: ['contact', 'phone'],
    //   message: 'Invalid'
    // }
}
```

---

## **Example 5: Optional Nested Objects**

Nested objects can be optional:

```javascript
const { z } = require('zod');

const ProfileSchema = z.object({
    bio: z.string(),
    website: z.string().url()
});

const UserSchema = z.object({
    name: z.string(),
    profile: ProfileSchema.optional()  // Nested object is optional
});

// Valid without profile
UserSchema.parse({
    name: "John"
}); // ✓

// Valid with profile
UserSchema.parse({
    name: "John",
    profile: {
        bio: "Developer",
        website: "https://johndoe.com"
    }
}); // ✓

// Invalid - profile is provided but incomplete
UserSchema.safeParse({
    name: "John",
    profile: {
        bio: "Developer"
        // Missing website
    }
}); // ✗ Error in nested object
```

---

## **Example 6: Default Values in Nested Objects**

```javascript
const { z } = require('zod');

const PreferencesSchema = z.object({
    theme: z.enum(["light", "dark"]).default("light"),
    notifications: z.boolean().default(true)
});

const UserSchema = z.object({
    name: z.string(),
    preferences: PreferencesSchema.default({})  // Default empty preferences
});

// Missing preferences - uses defaults
const user1 = UserSchema.parse({
    name: "John"
});
console.log(user1);
// {
//   name: "John",
//   preferences: {
//     theme: "light",
//     notifications: true
//   }
// }

// Partial preferences - missing fields use defaults
const user2 = UserSchema.parse({
    name: "Jane",
    preferences: {
        theme: "dark"
    }
});
console.log(user2);
// {
//   name: "Jane",
//   preferences: {
//     theme: "dark",
//     notifications: true  // Default filled in
//   }
// }
```

---

## **Example 7: Nested Arrays**

Combining nested objects with arrays:

```javascript
const { z } = require('zod');

// Nested schema
const PhoneSchema = z.object({
    type: z.enum(["mobile", "home", "work"]),
    number: z.string()
});

// Array of nested objects
const ContactSchema = z.object({
    email: z.string().email(),
    phones: z.array(PhoneSchema)  // Array of nested objects
});

const PersonSchema = z.object({
    name: z.string(),
    contact: ContactSchema
});

// Validate - array of nested objects
PersonSchema.parse({
    name: "John",
    contact: {
        email: "john@example.com",
        phones: [
            {
                type: "mobile",
                number: "555-0123"
            },
            {
                type: "work",
                number: "555-0456"
            }
        ]
    }
}); // ✓

// Invalid - nested object in array has wrong structure
const result = PersonSchema.safeParse({
    name: "John",
    contact: {
        email: "john@example.com",
        phones: [
            {
                type: "mobile",
                number: "555-0123"
            },
            {
                type: "invalid",  // ✗ Not in enum
                number: "555-0456"
            }
        ]
    }
});

if (!result.success) {
    console.log(result.error.errors[0].path);
    // ["contact", "phones", 1, "type"]
    // Error is in the 2nd phone's type field
}
```

---

## **Example 8: Reusing Nested Schemas**

The power of nested objects is **reusability**:

```javascript
const { z } = require('zod');

// Define once
const AddressSchema = z.object({
    street: z.string(),
    city: z.string(),
    zipCode: z.string()
});

// Use in multiple places
const PersonSchema = z.object({
    name: z.string(),
    homeAddress: AddressSchema,
    workAddress: AddressSchema.optional()
});

const CompanySchema = z.object({
    name: z.string(),
    headquarters: AddressSchema
});

const DeliverySchema = z.object({
    from: AddressSchema,
    to: AddressSchema
});

// All use the same AddressSchema!
// If you update AddressSchema, it updates everywhere
```

---

## **Example 9: Type Inference with Nested Objects**

Get TypeScript types automatically from nested schemas:

```typescript
import { z } from 'zod';

const AddressSchema = z.object({
    street: z.string(),
    city: z.string(),
    zipCode: z.string()
});

const UserSchema = z.object({
    id: z.number(),
    name: z.string(),
    address: AddressSchema
});

// Automatically infer types
type Address = z.infer<typeof AddressSchema>;
type User = z.infer<typeof UserSchema>;

// Equivalent to:
// type Address = {
//   street: string;
//   city: string;
//   zipCode: string;
// };
//
// type User = {
//   id: number;
//   name: string;
//   address: Address;
// };

// Now use them
const user: User = UserSchema.parse(data);
// TypeScript knows user.address.city is a string!
```

---

## **Example 10: Real-World API Request with Nested Objects**

```javascript
const { z } = require('zod');
const express = require('express');

const app = express();
app.use(express.json());

// Nested schemas
const BillingAddressSchema = z.object({
    street: z.string(),
    city: z.string(),
    zipCode: z.string().length(5),
    country: z.string().default("USA")
});

const ItemSchema = z.object({
    productId: z.number(),
    quantity: z.number().int().min(1),
    price: z.number().min(0)
});

const OrderSchema = z.object({
    customerId: z.number(),
    items: z.array(ItemSchema),          // Array of nested objects
    billingAddress: BillingAddressSchema, // Nested object
    shippingAddress: BillingAddressSchema.optional(),
    notes: z.string().optional()
});

// API endpoint
app.post('/orders', (req, res) => {
    const result = OrderSchema.safeParse(req.body);
    
    if (!result.success) {
        return res.status(400).json({
            error: "Validation failed",
            issues: result.error.flatten()
        });
    }
    
    const order = result.data;
    console.log(`Order created for customer ${order.customerId}`);
    console.log(`Items: ${order.items.length}`);
    console.log(`Billing address: ${order.billingAddress.city}`);
    
    res.status(201).json({
        success: true,
        orderId: 12345,
        order
    });
});

app.listen(3000);

/*
Example request body:
{
  "customerId": 1,
  "items": [
    {
      "productId": 101,
      "quantity": 2,
      "price": 29.99
    },
    {
      "productId": 102,
      "quantity": 1,
      "price": 49.99
    }
  ],
  "billingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "zipCode": "10001",
    "country": "USA"
  },
  "shippingAddress": {
    "street": "456 Oak Ave",
    "city": "Los Angeles",
    "zipCode": "90001"
  },
  "notes": "Please deliver ASAP"
}
*/
```

---

## **Error Handling in Nested Objects**

When validation fails in a nested object, the error includes the **path** to the field:

```javascript
const { z } = require('zod');

const AddressSchema = z.object({
    city: z.string(),
    zipCode: z.string().length(5)
});

const UserSchema = z.object({
    name: z.string(),
    address: AddressSchema
});

const result = UserSchema.safeParse({
    name: "John",
    address: {
        city: "NYC",
        zipCode: "100"  // Invalid
    }
});

console.log(result.error.errors);
// [
//   {
//     code: "too_small",
//     minimum: 5,
//     type: "string",
//     path: ["address", "zipCode"],  // Path shows nesting!
//     message: "String must contain exactly 5 character(s)"
//   }
// ]

// Format errors for display
const formatted = result.error.format();
console.log(formatted);
// {
//   address: {
//     zipCode: {
//       _errors: ["String must contain exactly 5 character(s)"]
//     }
//   }
// }
```
