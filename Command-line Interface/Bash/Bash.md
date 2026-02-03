# An Introduction to Bash
Bash, short for **Bourne Again Shell**, is a [command-line](obsidian://open?vault=Documents&file=Command-line%20Interface%2FWhat%20is%20a%20Command-Line%20Interface%3F) shell and powerful scripting language widely used on [[Unix]]-like operating systems such as Linux and macOS.

It is an enhanced, free version of the original Bourne shell (`sh`), created to improve on its features and provide additional functionality. *Bash serves as a **command interpreter** that reads and executes commands typed by the user or contained in shell scripts*

[But, what is Shell?](Shell.md)

If you already know C, you'll find some programming concepts familiar, but Bash is designed for interacting with the operating system and automating tasks rather than building standalone programs.

## Again the Million Dollar Question: What is Bash?
- **Shell & Scripting Language:** Bash acts as a command-line interface (CLI) where you type commands to interact with the operating system. It also lets you write scripts—plain text files containing a series of commands—to automate repetitive tasks.
- **Default on Most Linux Systems:** Bash is the default shell on most Linux distributions and is also available on macOS and Windows (via WSL or other tools)
So putting it all together, Kernel is like an executor, shell is like an compiler, terminal is the environment where you type in the command and get an output.[Refer this to get a more accurate relation](Bash,%20Relating%20Shell,%20Kernal%20and%20Terminal) 
## Why Learn Bash?
- **Automation:** Automate repetitive tasks and system maintenance.
- **Efficiency:** Manage files, processes, and software quickly.
- **Foundation for DevOps & Cloud:** Bash is essential for working with servers, cloud environments, and CI/CD pipelines.
- - **Integration:** Bash scripts can call programs written in other languages, making it a glue language for system tasks.

## Why not bash:
- Lacks a few features common to other languages.
- Does not support Object Oriented Programming.
- Difficult syntax compared to Python.
- Newer tools are available in place of Bash like Ansible.

---
# Lets get started!
Bash is also a shell, hence can be executed via a terminal.
A terminal in linux distro looks a bit like this:
![[Pasted image 20250522142511.png]]
The tilde(~) means that we are working in the current logged in user's home directory(pwd is a command that tells  us where we are in computer), and the dollar sign($) means that the terminal is ready to take in commands.  
![[Pasted image 20250522142604.png]]
We write our commands here.
The structure of commands is:
![[Pasted image 20250522142809.png]]

[[More on command structre to be known as we make things and practice stuff]] 
Now lets have a look at the basic shell commands:
## **File and Directory Operations**
- **ls**  
    Lists files and directories in the current directory.
    - `ls -l` : Detailed listing
![[Pasted image 20250521150915.png]]
    - `ls -a` : Includes hidden files(In the Linux world, hidden files and directories start with a dot (`.`))
    - `ls -lh` : Human-readable sizes (The `-h` option stands for "human-readable". It converts the file sizes into a format that's easier for humans to understand)
```BASH

rubber_duck@pop-os:~/Desktop/Coding$ ls -l
total 56
drwxrwxr-x  2 rubber_duck rubber_duck 12288 May 20 14:56  Attachments
-rw-rw-r--  1 rubber_duck rubber_duck  1857 May 18 19:26  Coding.sln
lrwxrwxrwx  1 rubber_duck rubber_duck    61 Feb 15 02:25 'CP F111 (Prac + Theory)' -> '/home/rubber_duck/Desktop/Acads/Sem2/CP F111 (Prac + Theory)/'
drwxrwxr-x  2 rubber_duck rubber_duck  4096 May 21 00:19  DevOPS
drwxrwxr-x  4 rubber_duck rubber_duck  4096 Mar 23 19:39  GameDev
drwxrwxr-x  6 rubber_duck rubber_duck  4096 Mar  6 17:15 'Intro to Programming using The C Programming Language'
drwxrwxr-x  3 rubber_duck rubber_duck  4096 Mar 26 23:45 'Lab08 - Pointers_Structures-20250326'
drwxrwxr-x  3 rubber_duck rubber_duck  4096 Apr 11 20:28  Math
drwxr-xr-x  8 rubber_duck rubber_duck  4096 Mar 23 19:32 'Object Oriented Programming'
drwxrwxr-x  2 rubber_duck rubber_duck  4096 Mar 26 23:44  PracticeQ1_Q1
drwxrwxr-x 10 rubber_duck rubber_duck  4096 May 21 13:33 'Programming languages'
drwxrwxr-x  3 rubber_duck rubber_duck  4096 May 17 18:51  Templates

rubber_duck@pop-os:~/Desktop/Coding$ ls -a
 .    Attachments  'CP F111 (Prac + Theory)'   GameDev                                                 'Lab08 - Pointers_Structures-20250326'  'Object Oriented Programming'   PracticeQ1_Q1            Templates
 ..   Coding.sln    DevOPS                    'Intro to Programming using The C Programming Language'   Math                                    .obsidian                     'Programming languages'   .vscode

rubber_duck@pop-os:~/Desktop/Coding$ ls -lh
total 56K
drwxrwxr-x  2 rubber_duck rubber_duck  12K May 20 14:56  Attachments
-rw-rw-r--  1 rubber_duck rubber_duck 1.9K May 18 19:26  Coding.sln
lrwxrwxrwx  1 rubber_duck rubber_duck   61 Feb 15 02:25 'CP F111 (Prac + Theory)' -> '/home/rubber_duck/Desktop/Acads/Sem2/CP F111 (Prac + Theory)/'
drwxrwxr-x  2 rubber_duck rubber_duck 4.0K May 21 00:19  DevOPS
drwxrwxr-x  4 rubber_duck rubber_duck 4.0K Mar 23 19:39  GameDev
drwxrwxr-x  6 rubber_duck rubber_duck 4.0K Mar  6 17:15 'Intro to Programming using The C Programming Language'
drwxrwxr-x  3 rubber_duck rubber_duck 4.0K Mar 26 23:45 'Lab08 - Pointers_Structures-20250326'
drwxrwxr-x  3 rubber_duck rubber_duck 4.0K Apr 11 20:28  Math
drwxr-xr-x  8 rubber_duck rubber_duck 4.0K Mar 23 19:32 'Object Oriented Programming'
drwxrwxr-x  2 rubber_duck rubber_duck 4.0K Mar 26 23:44  PracticeQ1_Q1
drwxrwxr-x 10 rubber_duck rubber_duck 4.0K May 21 13:33 'Programming languages'
drwxrwxr-x  3 rubber_duck rubber_duck 4.0K May 17 18:51  Templates
```

We can also combine ls commands like this: `ls -alh`(Order doesn't matter, `-lah` would also work)

Also, ls has something interesting:
![[Pasted image 20250521151525.png]]
	
- **cd**  
    Changes the current working directory.
    
    - `cd /path/to/directory` : Go to specified directory
    - `cd ~` : Go to home directory
    - `cd -` : Go to previous directory
    
- **pwd**  
    Prints the current working directory (shows where you are in the filesystem)
    
- **mkdir**  
    Creates a new directory.
    - `mkdir new_folder`
    
- **rm**  
    Removes files or directories.
    - `rm file.txt` : Remove a file
    - `rm -r folder` : Remove a directory and its contents
    - `rm -f file.txt` : Force remove without prompt
    
- **cp**  
    Copies files or directories.
    - `cp source.txt dest.txt` : Copy file
    - `cp -r dir1 dir2` : Copy directories recursively
- **mv**  
    Moves or renames files and directories.
    - `mv oldname.txt newname.txt` : Rename file
    - `mv file.txt /path/to/dir/` : Move file
- **touch**  
    Creates an empty file or updates the timestamp of an existing file.
    - `touch file.txt`

## **Viewing and Searching Files**

- **cat**  
    Displays the contents of a file.
    - `cat file.txt`
    
- **less**  
    Views the contents of a file one screen at a time (safer for large files).
    
    - `less file.txt`[2](https://www.educative.io/blog/bash-shell-command-cheat-sheet)[5](https://builtin.com/data-science/bash-commands)
        
- **grep**  
    Searches for patterns within files.
    
    - `grep "pattern" file.txt` : Find lines matching "pattern"[2](https://www.educative.io/blog/bash-shell-command-cheat-sheet)[5](https://builtin.com/data-science/bash-commands)
        

## **Important Useful Commands**

- **echo**  
    Prints text or variables to the terminal. It is a print function of Bash.
    - `echo "Hello, World!"`
- **man**  
    Displays the manual page for a command (help/documentation).
    - `man ls`
- **history**  
    Shows the history of commands you have entered.
- **clear**  
    Clears the terminal screen.
- **chmod**  
    Changes file permissions.
![[Pasted image 20250522142829.png]]![[Pasted image 20250522142839.png]]

- **chown**  
    Changes file owner and group.

## **Process and System Management**

- **top**  
    Displays running processes and system resource usage.[5](https://builtin.com/data-science/bash-commands)
    
- **kill**  
    Terminates a process by its process ID (PID).[2](https://www.educative.io/blog/bash-shell-command-cheat-sheet)
    

## **Navigation and Help**

- **which**  
    Shows the path of a command (e.g., `which ls`).[5](https://builtin.com/data-science/bash-commands)
    
- **locate**  
    Quickly finds files by name.
    
- **compgen**  
    Lists all available commands, aliases, and functions.[2](https://www.educative.io/blog/bash-shell-command-cheat-sheet)
    

These commands form the foundation of Bash usage and will allow you to navigate, manipulate files, and manage your system effectively. As you become more comfortable, you can explore more advanced features and scripting capabilities.

---
# Syntax of Bash
Now even though what we saw earlier were shell commands in Bash, it is a programming language in itself as well. 
Bash was a revolutionary idea which has done a lot in the field of Computer science.

It is both a shell and a programming language.
	- **Shell:** Bash (Bourne Again Shell) is a command-line interface (CLI) that lets users interact directly with the operating system. As a shell, it allows you to execute commands, navigate the file system, and manage system processes interactively in a terminal window
	- **Programming Language:** 
		- Bash is also a scripting language. 
		- You can write scripts—plain text files containing a series of commands, logic (like loops and conditionals), and functions—to automate tasks. 
		- Bash scripts are interpreted, not compiled, and are widely used for system administration, automation, and repetitive tasks.

Now lets have a look at the [[Syntax of Bash]] .

But how do I [create a bash file](Text%20Editors%20) and [execute it]()?

Now knowing the syntax of bash, lets have a look at the elements of this Scripting Language one by one.
- [[Variables and Types in Bash]] 
- [[Operators and Conditionals in Bash]] 
- [[Input and Output in Bash]]
- [[Loops in Bash]]
- [[Functions in Bash]]
- [[Arrays in Bash]]
Cover AWK and SED
