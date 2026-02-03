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

**Binary**: Relationship between two entity sets.[^1]

- Example: Student registers for Course

**Ternary**: Simultaneous relationship among three entity sets.[^1]

- Example: Instructor teaches Student in Course


### Attributes on Relationships

Relationships can have their own descriptive attributes.[^1]

- Example: Student registers for Course with attributes (semester, year, grade)


## Cardinality Constraints

Cardinality defines how many instances of one entity can be associated with instances of another entity.[^2][^1]

### Types of Binary Cardinality

**One-to-One (1:1)**: Each entity in A relates to at most one entity in B, and vice versa.[^1]

- Example: One student allocated to one hostel room

**One-to-Many (1:M)**: Each entity in A relates to multiple entities in B, but each entity in B relates to at most one in A.[^1]

- Example: One instructor teaches many courses

**Many-to-One (M:1)**: Multiple entities in A relate to one entity in B.[^1]

- Example: Many students do one major project

**Many-to-Many (M:M)**: Entities in both sets can relate to multiple entities in the other set.[^3][^1]

- Example: Students register for many courses, and courses have many students


### Min-Max Notation

Specifies minimum and maximum number of relationships.[^1]

- (0, 2): Entity must participate in 0 to 2 relationships
- (1, N): Entity must participate in at least 1, up to N relationships

Example: Customer-Loan (0, 2) means a customer can have 0 to 2 loans.[^1]

## Participation Constraints

Defines whether all entities must participate in a relationship.[^4][^5][^1]

**Total Participation**: Every entity must participate in at least one relationship.[^5][^1]

- Notation: Double line in ER diagram
- Example: Every loan must have a customer

**Partial Participation**: Some entities may not participate in any relationship.[^4][^1]

- Notation: Single line in ER diagram
- Example: Not all customers have loans


## Keys

**Super Key**: A set of one or more attributes that uniquely identifies each entity.[^1]

**Candidate Key**: A minimal super key (no unnecessary attributes).[^1]

- Example: For Student(id, name, mobile_no), candidate keys could be {id}, {email_id}, or {name, mobile_no}

**Primary Key**: The candidate key selected as the main identifier.[^1]

- Must be unique and cannot be NULL
- Only one per table
- Can be composite (multiple attributes)

**Composite Key**: A primary key consisting of multiple attributes.[^1]

**Foreign Key**: An attribute that references the primary key of another table.[^1]

### Keys for Relationships

The combination of primary keys from participating entities forms the super key of a relationship.[^1]

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