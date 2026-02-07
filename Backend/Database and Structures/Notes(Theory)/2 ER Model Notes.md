## Entity-Relationship (ER) Model

The ER model is a high-level data model that represents the real world as entities (objects) and relationships between them. It's used to design databases before they're actually built.

## Entities and Entity Sets

**Entity**: A real-world object that exists and can be distinguished from other objects. Examples: a specific student, a car, a course.[^1]

**Entity Set**: A collection of entities of the same type that share the same properties. Examples: all students, all courses, all employees.[^1]

Think of an entity set as a table name, and entities as rows in that table.[^1]

Example:

- Person(uid, name, address, phone_no)
- Classroom(lecture_hall_no, capacity, no_of_benches, no_of_boards)


## Attributes

Attributes are properties that describe an entity. They become columns when you create database tables.[^1]

### Types of Attributes

**Simple Attributes**: Cannot be divided further.[^1]

- Example: age, gender, id

**Composite Attributes**: Can be broken into smaller parts.[^1]

- Example: name → (first_name, middle_name, last_name)
- Example: address → (street_num, street_name, city, state, postal_code)
- **Mapping Rule**: Only the leaf-level sub-attributes become columns in tables[^1]

**Single-valued Attributes**: Have only one value per entity.[^1]

- Example: student_id, birth_date

**Multivalued Attributes**: Can have multiple values.[^1]

- Example: phone_numbers (landline, mobile, office), hobbies, email_ids
- **Mapping Rule**: Create a separate table with the entity's primary key plus the multivalued attribute columns[^1]

**Derived Attributes**: Calculated from other attributes, not stored.[^1]

- Example: age (calculated from birth_date), total_marks (sum of all subject marks)
- **Notation**: Dashed ellipse in ER diagrams

**Domain/Value Set**: The allowed values for an attribute.[^1]

- Example: gender must be 'M' or 'F', shirt_size must be {L, XL, XXL}


## Relationships and Relationship Sets

**Relationship**: An association between entities.[^1]

- Example: "Student registers for Course", "Employee works in Department"

**Relationship Set**: A collection of similar relationships.[^1]

### Degree of Relationships

**Unary (Recursive)**: Relationship within the same entity set.[^1]

- Example: Employee "manages" Employee (manager-worker relationship)
- Uses role labels like "manager" and "worker" to clarify the relationship

**Binary**: Relationship between two entity sets.

- Example: Student registers for Course

**Ternary**: Simultaneous relationship among three entity sets.

- Example: Instructor teaches Student in Course


### Attributes on Relationships

Relationships can have their own descriptive attributes.

- Example: Student registers for Course with attributes (semester, year, grade)


## Cardinality Constraints

Cardinality defines how many instances of one entity can be associated with instances of another entity.

### Types of Binary Cardinality

**One-to-One (1:1)**: Each entity in A relates to at most one entity in B, and vice versa.[^1]

- Example: One student allocated to one hostel room

**One-to-Many (1:M)**: Each entity in A relates to multiple entities in B, but each entity in B relates to at most one in A.

- Example: One instructor teaches many courses

**Many-to-One (M:1)**: Multiple entities in A relate to one entity in B.[^1]

- Example: Many students do one major project

**Many-to-Many (M:M)**: Entities in both sets can relate to multiple entities in the other set.[^3][^1]

- Example: Students register for many courses, and courses have many students


### Min-Max Notation

Specifies minimum and maximum number of relationships.

- (0, 2): Entity must participate in 0 to 2 relationships
- (1, N): Entity must participate in at least 1, up to N relationships

Example: Customer-Loan (0, 2) means a customer can have 0 to 2 loans.[^1]

## Participation Constraints

Defines whether all entities must participate in a relationship.

**Total Participation**: Every entity must participate in at least one relationship.

- Notation: Double line in ER diagram
- Example: Every loan must have a customer

**Partial Participation**: Some entities may not participate in any relationship.[^4][^1]

- Notation: Single line in ER diagram
- Example: Not all customers have loans


## Keys

### 1. Super Key

To answer your specific question: **No, a Super Key is not necessarily the "maximum" set.**

A **Super Key** is simply **any** set of attributes (columns) that can uniquely identify a row in a table. It doesn't have to be large or maximum; it just has to be unique.

- **The Logic:** If you take a unique identifier (like an ID) and add random other columns to it (like Name or Age), that combination is _still unique_. Therefore, it is still a Super Key.
    
- **Example:** Imagine a student table with `Student_ID` (unique) and `Name` (not unique).
    
    - `{Student_ID}` is a Super Key (it identifies the student).
        
    - `{Student_ID, Name}` is **also** a Super Key (it still identifies the student).
        
    - `{Student_ID, Name, Phone_Number}` is **also** a Super Key.
        

### 2. Candidate Key

You asked if a Candidate Key can be a Super Key. The answer is **Yes. In fact, all Candidate Keys are Super Keys.**

A **Candidate Key** is a special type of Super Key known as a **Minimal Super Key**. It is a Super Key with no redundant attributes.

- **The "Minimal" Rule:** If you remove any attribute from the set and it loses its uniqueness, it was a Candidate Key. If you remove an attribute and it _stays_ unique, it was just a regular Super Key.
    
- **Example:**
    
    - `{Student_ID, Name}` is a Super Key. If I remove `Name`, is `{Student_ID}` still unique? Yes. Therefore, `{Student_ID, Name}` was **not** a Candidate Key (it had extra baggage).
        
    - `{Student_ID}` is a Super Key. If I remove `Student_ID`, I have nothing. It is minimal. Therefore, it is a Candidate Key.
        

### 3. MySQL Constraints

You asked if we have defined constraints for Super or Candidate keys in MySQL akin to `PRIMARY KEY`.

Here is how they map to actual SQL commands:

|**Concept**|**MySQL Constraint**|**Details**|
|---|---|---|
|**Primary Key**|`PRIMARY KEY`|You can only have **one** per table. It enforces Uniqueness and `NOT NULL`.|
|**Candidate Key**|`UNIQUE`|You can have **multiple** per table. It enforces Uniqueness. (Note: In MySQL, `UNIQUE` allows `NULL` values unless you also add `NOT NULL`).|
|**Super Key**|_(None)_|There is no specific command for "Super Key" because it is a theoretical concept. If you enforce `PRIMARY KEY` or `UNIQUE`, you have effectively created a Super Key.|

#### **Example Query**

Here is how you would define these constraints in a MySQL table creation script. In this example, `ID` is the Primary Key, but `Email` is a Candidate Key (because it's also unique).

SQL

```
CREATE TABLE Users (
    ID int NOT NULL,
    Email varchar(255) NOT NULL,
    Name varchar(255),
    
    -- This defines the Primary Key
    CONSTRAINT pk_users PRIMARY KEY (ID),
    
    -- This defines a Candidate Key
    CONSTRAINT uc_email UNIQUE (Email) 
);
```

### **Primary Key**: 

The candidate key selected as the main identifier.[^1]

- Must be unique and cannot be NULL
- Only one per table
- Can be composite (multiple attributes)

### **Composite Key**: 
A primary key consisting of multiple attributes.[^1]

### **Foreign Key**: 
An attribute that references the primary key of another table.

### Keys for Relationships

The combination of primary keys from participating entities forms the super key of a relationship.

## Weak Entity Sets

**Strong Entity**: Has its own primary key and exists independently.[^6][^7][^1]

- Notation: Single rectangle

**Weak Entity**: Doesn't have a primary key and depends on a strong entity for existence.[^7][^6][^1]

- Notation: Double rectangle
- Has a **discriminator (partial key)** that distinguishes entities within the weak set
- Notation for discriminator: Dashed underline

**Identifying Relationship**: The relationship connecting weak and strong entities.[^7][^1]

- Notation: Double diamond
- Must be total participation from weak entity side
- Must be one-to-many from strong to weak entity

Example: Loan (strong) → Payment (weak)[^1]

- Payment has discriminator: payment_number
- Primary key of Payment: (loan_number, payment_number)

Another Example: Employee (strong) → Dependent (weak)[^1]

- Dependent identified by (employee_id, dependent_name)


## Aggregation

Used when you need to model a relationship involving another relationship.[^1]

- Treats a relationship set as an entity set
- Allows relationships between relationships and entities
- Useful for ternary relationships that need additional associations


## ER Diagram Notation Summary

| Element | Notation |
| :-- | :-- |
| Entity Set | Rectangle [^1] |
| Weak Entity | Double Rectangle [^1] |
| Attribute | Ellipse [^1] |
| Derived Attribute | Dashed Ellipse [^1] |
| Multivalued Attribute | Double Ellipse [^1] |
| Relationship | Diamond [^1] |
| Identifying Relationship | Double Diamond [^1] |
| Total Participation | Double Line [^1] |
| Partial Participation | Single Line [^1] |

## Mapping ER to Tables

**Entity Sets**: Each becomes a table; entity name = table name.[^1]

**Simple Attributes**: Each becomes a column.[^1]

**Composite Attributes**: Only leaf-level sub-attributes become columns.[^1]

**Multivalued Attributes**: Create a separate table with the entity's primary key.[^1]

**Binary Relationships**: Create a table with primary keys from both entities.[^1]

**Unary Relationships**: Add a foreign key column in the same table referencing its own primary key.