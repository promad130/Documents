
--	QUESTION 1.1


--  Creating the view requied in the first question
CREATE VIEW MOVIE_DETAILS AS
SELECT m.title, m.genre, m.rental_price
FROM movies m;

-- querirng the view
SELECT * FROM MOVIE_DETAILS;

-- Creating the Customer View
CREATE VIEW CUSTOMER_PUBLIC AS
SELECT c.name, c.email
FROM customers c;

-- Quering that view
SELECT * FROM CUSTOMER_PUBLIC;


--	QUESTION 1.2


-- Creating the Expensive_Movies view
CREATE VIEW EXPENSIVE_MOVIES AS
SELECT m.title, m.genre, m.rental_price
FROM movies m
WHERE m.rental_price > 5.00;

-- Quering the view
SELECT * FROM EXPENSIVE_MOVIES;

-- trying to insert an invalid value
INSERT INTO EXPENSIVE_MOVIES (title, genre, rental_price) 
VALUES ("Long Live Axis Power", "Political", 4.50);

/*
This will give an error as the main table didn't have an auto increment, hence we add the autoincrement:
*/

ALTER TABLE movies MODIFY movie_id INT AUTO_INCREMENT;

/*
But this gives this error:
ERROR 1833 (HY000): Cannot change column 'movie_id': used in a foreign key constraint 'rentals_ibfk_2' of table 'testDB1.rentals'

Hence, we first remove the foregin key, and then we add that column again
*/

-- Step 1: Drop the foreign key
ALTER TABLE rentals
DROP FOREIGN KEY rentals_ibfk_2;

-- Step 2: Now modify movie_id to AUTO_INCREMENT
ALTER TABLE movies
MODIFY movie_id INT AUTO_INCREMENT;

-- Step 3: Re-add the foreign key
ALTER TABLE rentals
ADD CONSTRAINT rentals_ibfk_2
FOREIGN KEY (movie_id) REFERENCES movies(movie_id);

/*
But won't this affect the values?

mysql> SELECT * FROM rentals;
+-----------+-------------+----------+-------------+-------------+
| rental_id | customer_id | movie_id | rental_date | return_date |
+-----------+-------------+----------+-------------+-------------+
|         1 |         101 |        1 | 2026-01-01  | 2026-01-05  |
|         2 |         102 |        2 | 2026-01-02  | 2026-01-06  |
|         3 |         101 |        3 | 2026-01-03  | 2026-01-07  |
+-----------+-------------+----------+-------------+-------------+
3 rows in set (0.00 sec)

mysql> 

no it doesn't as we just droped the realtion that connects it with the movies table, not the column itself!
i.e., after droping the realtion, if we did anything to the movies, it wont have the effect on the rentals table as the relation was not there

now we try that add query via the view again
*/

INSERT INTO EXPENSIVE_MOVIES (title, genre, rental_price) 
VALUES ("Long Live Axis Power", "Political", 4.50);

/*
And here are the results:

mysql> INSERT INTO EXPENSIVE_MOVIES (title, genre, rental_price) 
    -> VALUES ("Long Live Axis Power", "Political", 4.50);
Query OK, 1 row affected (0.01 sec)

mysql> 
mysql> SELECT * FROM EXPENSIVE_MOVIES;
+-----------+--------+--------------+
| title     | genre  | rental_price |
+-----------+--------+--------------+
| Inception | Sci-Fi |         5.99 |
| Avatar    | Sci-Fi |         6.99 |
+-----------+--------+--------------+
2 rows in set (0.00 sec)

mysql> SELECT * FROM movies;
+----------+----------------------+-----------+--------------+--------------+
| movie_id | title                | genre     | release_year | rental_price |
+----------+----------------------+-----------+--------------+--------------+
|        1 | Inception            | Sci-Fi    |         2010 |         5.99 |
|        2 | Titanic              | Drama     |         1997 |         3.99 |
|        3 | Avatar               | Sci-Fi    |         2009 |         6.99 |
|        4 | Long Live Axis Power | Political |         NULL |         4.50 |
+----------+----------------------+-----------+--------------+--------------+
4 rows in set (0.00 sec)

mysql> 

*/


--	QUESTION 1.3

-- 1) Creating the View Required
CREATE VIEW RENTAL_MOVIES AS
SELECT c.name, r.rental_date, r.return_date
FROM customers c INNER JOIN rentals r
ON c.customer_id = r.customer_id
WHERE NOT r.return_date IS NULL;

-- 2) Querying the Rental Movies
SELECT * FROM RENTAL_MOVIES;

-- 3) Creating the new View with Active Rentals 
CREATE VIEW ACTIVE_RENTALS AS 
SELECT c.name, r.rental_date, r.return_date
FROM customers c INNER JOIN rentals r
ON c.customer_id = r.customer_id
WHERE r.return_date IS NULL;

--	QUESTION 1.4

-- RENTAL_STATS view
CREATE VIEW RENTAL_STATS AS
SELECT 
	c.name, 
	COUNT(r.customer_id), 
	SUM(m.rental_price)
FROM 
	customers c INNER JOIN 
	rentals r INNER JOIN 
	movies m 
ON 
	c.customer_id = r.customer_id 
	AND 
	r.movie_id = m.movie_id
GROUP BY r.customer_id;

-- Trying to update a value in the View
UPDATE RENTAL_STATS 
SET name = "Mathew Browns"; 

/*
mysql> UPDATE RENTAL_STATS 
    -> SET name = "Mathew Browns";
ERROR 1288 (HY000): The target table RENTAL_STATS of the UPDATE is not updatable
mysql> 
*/


--	QUESTION 1.5

CREATE VIEW MOVIE_CATEGORY_ANALYSIS AS
SELECT 
	m.title, 
	m.genre, 
	m.rental_price,
	CASE
		WHEN m.rental_price < 4.00 THEN "Budget"
		WHEN m.rental_price < 5.99 THEN "Standard"
		WHEN m.rental_price >= 6.00 THEN "Premium"
		ELSE "no valid category"
	END AS category
FROM movies m;

SELECT * FROM MOVIE_CATEGORY_ANALYSIS;

INSERT INTO MOVIE_CATEGORY_ANALYSIS VALUES ("New Movie, lets go", "new movie", 79.09);


--	QUESTION 1.7

CREATE VIEW GENRE_STATS AS
SELECT m.genre, COUNT(m.movie_id) AS movie_count
FROM movies m
GROUP BY m.genre;

CREATE VIEW POPULAR_GENRES AS 
SELECT m.genre, movie_count
FROM GENRE_STATS m
WHERE movie_count > 1
GROUP BY m.genre;

SELECT * FROM GENRE_STATS;
SELECT * FROM POPULAR_GENRES;

DROP VIEW IF EXISTS GENRE_STATS;

/*
	mysql> SELECT * FROM POPULAR_GENRES;
	ERROR 1356 (HY000): View 'testDB1.POPULAR_GENRES' references invalid table(s) or column(s) or function(s) or definer/invoker of view lack rights to use them
*/


