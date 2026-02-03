**Expected to know:** [[Input&Output and placeholders]] 
**Topics Covered:**
**Tags:**

![[Syntax of Bash#**5. Reading User Input**]]

If you know C, you'll find Bash input/output (I/O) concepts familiar, though the syntax and mechanisms are different. Here’s a concise overview of Bash I/O basics, highlighting parallels and contrasts with C.

## Input in Bash

**1. Reading User Input (Interactive)**

- Use the `read` command to capture user input from the terminal.
- Syntax:
```bash
echo "Enter your name:" 
read name 
echo "Hello, $name!"`
```
- This is similar to using `scanf()` in C, but simpler—no format specifiers, and variables are automatically strings.

**2. Command-Line Arguments**

- Arguments passed to a script are accessed as `$1`, `$2`, ..., and `$@` (all arguments).
- Example:
```bash
#!/bin/bash 
echo "First argument: $1" 
echo "Second argument: $2"`
```
- In C, this is analogous to using `argc` and `argv[]` in `main()`.

---
Only till here for now, later on we do all this as we go in depth
**3. Reading from Files**

- You can loop through lines in a file using `while read`:
```bash
while read line; do   
	echo $line 
done < input.txt
```
    
- This is similar to `fgets()` in C, but Bash handles line-by-line reading automatically[1](https://www.freecodecamp.org/news/bash-scripting-tutorial-linux-shell-script-and-command-line-for-beginners/)[5](https://learnxinyminutes.com/bash/).
    

## Output in Bash

**1. Printing to Terminal**

- Use `echo` to print text or variable values:
    
    bash
    
    `echo "Hello, World!"`
    
- This is like `printf()` or `puts()` in C[1](https://www.freecodecamp.org/news/bash-scripting-tutorial-linux-shell-script-and-command-line-for-beginners/)[3](https://www.hostinger.my/tutorials/bash-scripting-tutorial)[5](https://learnxinyminutes.com/bash/).
    

**2. Writing to Files**

- Redirect output using `>` (overwrite) or `>>` (append):
    
    bash
    
    `echo "Some text" > output.txt   # Overwrites output.txt echo "More text" >> output.txt  # Appends to output.txt`
    
- This is similar to opening a file in C with `fopen()` and writing with `fprintf()`[1](https://www.freecodecamp.org/news/bash-scripting-tutorial-linux-shell-script-and-command-line-for-beginners/)[5](https://learnxinyminutes.com/bash/).
    

**3. Redirecting Command Output**

- Redirect standard output or standard error:
    
    bash
    
    `ls > files.txt        # Output to file ls not_a_file 2> err.txt  # Errors to file ls > files.txt 2>&1   # Output and errors to same file`
    
- This resembles redirecting `stdout` and `stderr` in C, but is done via shell syntax[5](https://learnxinyminutes.com/bash/)[9](https://www.tecmint.com/linux-io-input-output-redirection-operators/).
    

## Standard Input, Output, and Error

- Bash scripts and commands use file descriptors:
    
    - `0` – Standard Input (stdin)
        
    - `1` – Standard Output (stdout)
        
    - `2` – Standard Error (stderr)
        
- You can redirect these using `<`, `>`, `2>`, etc.[4](https://mywiki.wooledge.org/BashGuide/InputAndOutput)[5](https://learnxinyminutes.com/bash/)[9](https://www.tecmint.com/linux-io-input-output-redirection-operators/).
    

