--	Q1)

DELIMITER $$

CREATE FUNCTION add_two_numbers(num1 INT, num2, INT)
RETURNS INT
BEGIN 

	DECLARE final INT;
	SET final = num1 + num2;
	RETURN final;
END $$

DELIMITER ;


