Set Operations in MySQL
Set operations UNION, INTERSECT, and EXCEPT:
(*Each of the above operations automatically eliminates duplicates*)
- **UNION**
	- Used to combine the result of two or more sql SELECT queries.
	- In this operation, all the number of datatypes and columns must be the same in both the tables on which the UNION operation is being applied.
	- The union operation eliminates the duplicate rows from its resultset.
- **INTERSECT**
	- Used to combine two SELECT statements. The Intersect operation returns the common rows from both the SELECT statements.
	- In this operation, the number of datatype and columns must be the same.
	- It has no duplicates, and it arranges the data in ascending order by default.
- **EXCEPT**
	- Used to combine two result sets and returns the data from the first result set, which is not present in the second result set.

**Examples:**
1. Find courses that ran in Fall 2017 or in Spring 2018.
```MySQL
(select course_id from section where sem = 'Fall' and year = 2017)
	union
(select course_id from section where sem = 'Spring' and year = 2018)
```
2. Find courses that ran in Fall 2017 and in Spring 2018
```MySQL
(select course_id from section where sem = 'Fall' and year = 2017)
	intersect
(select course_id from section where sem = 'Spring' and year = 2018)
```
3. Find courses that ran in Fall 2017 but not in Spring 2018
```MySQL
(select course_id from section where sem = 'Fall' and year = 2017)
	except
(select course_id from section where sem = 'Spring' and year = 2018)
```

To retain all duplicates use the
- `UNION ALL`
- `INTERSECT ALL`
- `EXCEPT ALL`
