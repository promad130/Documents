Here is the no-bullshit breakdown of Relational Algebra. Since your mid-sem is on March 13th and this is explicitly heavily weighted in the Raghu Ramakrishnan textbook, you absolutely need to master this.

While SQL is _declarative_ (you tell the database _what_ you want), Relational Algebra is _procedural_ (you tell the database exactly _how_ to get it, step-by-step). Think of it as the mathematical engine running under the hood of your SQL queries.

Here are the fundamental operators you need to know, broken down by how they manipulate tables (relations).

### 1. The Slicers (Unary Operators)

These operate on a single table to filter data.

- **Selection ($\sigma$):** The Row Filter. This is the equivalent of the SQL `WHERE` clause. It horizontally slices your table, returning only the rows that meet a specific condition.
    
    - _Syntax:_ $\sigma_{rating > 8}(Sailors)$
        
    - _Meaning:_ "Give me all the rows from the Sailors table where the rating is greater than 8."
        
- **Projection ($\pi$):** The Column Filter. This is the equivalent of the SQL `SELECT` clause. It vertically slices your table, returning only the specific columns you ask for and automatically stripping out any duplicate rows.
    
    - _Syntax:_ $\pi_{sname, rating}(Sailors)$
        
    - _Meaning:_ "Give me only the name and rating columns for all sailors."
        
- **Rename ($\rho$):** Sometimes you need to rename a table or its columns temporarily to avoid conflicts (especially when joining a table to itself).
    
    - _Syntax:_ $\rho_{S1}(Sailors)$ renames the table to S1.
        

### 2. The Combiners (Set Operations)

These operate on two tables. To use Union, Intersection, or Difference, the two tables **must be union-compatible** (they must have the exact same number of columns, and the corresponding columns must have matching data types).

- **Union ($\cup$):** Combines rows from Table A and Table B. If a row exists in either, it makes the cut. Duplicates are removed.
    
- **Intersection ($\cap$):** Returns only the rows that exist in _both_ Table A and Table B.
    
- **Set Difference ($-$):** Returns rows that are in Table A, but _not_ in Table B. (e.g., "Find all sailors who have reserved a red boat but NOT a green boat").
    
- **Cross-Product / Cartesian Product ($\times$):** This combines _every_ row in Table A with _every_ row in Table B. If Table A has 5 rows and Table B has 10 rows, the output is 50 rows. On its own, it's mostly useless and highly inefficient, but it's the building block for Joins.
    

### 3. The Power Moves (Derived Operators)

These are combinations of the basic operators, and they are what you will actually use to solve complex exam questions.

- **Join ($\bowtie$):** A Join is simply a Cross-Product ($\times$) followed immediately by a Selection ($\sigma$). It connects tables based on a relationship.
    
    - _Equi-Join:_ Joins tables where specific columns are exactly equal (e.g., `Sailors.sid = Reserves.sid`).
        
    - _Natural Join:_ A special, cleaner type of Equi-Join. It automatically finds columns with the exact same name in both tables, joins on them, and drops the duplicate column in the final output.
        
- **Division ($/$):** **Pay close attention to this one.** Division is the hardest operator and is a classic BITS exam question. It is used to answer "for all" or "every" queries.
    
    - _Example Question:_ "Find the names of sailors who have reserved _every_ boat."
        
    - _How it works:_ If Table A contains (Sailor, Boat) and Table B contains a list of _all_ (Boats), $A / B$ will return only the Sailors who have a matching row for every single Boat listed in Table B.
        

---

### How to approach a question in Lab 5 or the Mid-Sem:

1. Read the English requirement (e.g., "Find names of sailors who reserved boat 103").
    
2. Filter the specific condition first: $\sigma_{bid=103}(Reserves)$
    
3. Join it to the table with the names: $\bowtie Sailors$
    
4. Extract the column you need: $\pi_{sname}$
    
5. _Final Equation:_ $\pi_{sname}(\sigma_{bid=103}(Reserves) \bowtie Sailors)$
    

Would you like me to give you a complex English query and see if you can translate it into a Relational Algebra equation, or do you want to move on to SQL Triggers?