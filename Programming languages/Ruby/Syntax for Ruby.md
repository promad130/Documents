**Expected to know:** [[Syntax]],[[The C Programming Language]]
**Topics Covered:**
**Tags:** [[Syntax]]

# Ruby Syntax Overview

Ruby's syntax is designed for readability and simplicity, making it accessible for beginners and efficient for experienced developers. Here are the core elements of Ruby syntax:

---
## **File Extension and Execution**
- Ruby files use the `.rb` extension.
- To execute, use: `ruby filename.rb.

---
## **Comments**

- Single-line comments start with `#`:
```ruby
# This is a single-line comment
```
- Multi-line comments use `=begin` and `=end`:
```ruby
=begin
This is a multi-line comment 
=end
```    

---
## **Data Types**

- Numbers: `num = 42` 
- Strings: `str = "Hello"` 
- Arrays: `arr = [1,2]` 
- Hashes: `hash = {"key" => "value"}`.

---
## **Variables**
```ruby
x = 10           # Integer
name = "Ruby"    # String
pi = 3.14        # Float
flag = true      # Boolean
```
- No type declarations.
- Variable scope is determined by naming convention and context.
	- Local variables: start with a lowercase letter or underscore (`local_var`) 
	- Instance variables: start with `@` (`@instance_var`)
	- Class variables: start with `@@` (`@@class_var`) 
	- Global variables: start with `$` (`$global_var`).

---
## **Whitespace and Line Endings**
- Whitespace (spaces, tabs) is generally ignored except within strings.
- Statements end with a newline or semicolon (`;`).

---
## **Printing Output**
- Use `puts` or `print`:
```ruby
puts "Hello, Ruby!"
```

---
## **Control Structures**
No parentheses needed for conditions.
`end` closes blocks (not braces `{}` as in C).

### **If/Else:**
```ruby
if x > 5   
	puts "x is greater than 5" 
elsif x == 5   
	puts "x is equal to 5" 
else   
	puts "x is less than 5" 
end
```

### **While Loop:**
```ruby
while x > 0
	puts x  
	x -= 1 
end
```

### **For Loop:**
```ruby
for i in 1..5   
	puts i 
end
```

---
## **Methods**
- Defined with `def` and closed with `end`:
```ruby
def say_hello(name)   
	puts "Hello, #{name}!" 
end 

say_hello("Ruby")`
```

**Blocks**
- Use `do...end` or `{...}` for blocks:
```ruby
3.times do |i|   
	puts i 
end
```

---
## **Special Statements**

- `BEGIN { ... }`: Runs code before the program starts.
- `END { ... }`: Runs code after the program ends.

---
## **String Interpolation**
- Double quotes allow interpolation: `"Hello, #{name}"`
- Single quotes do not: `'Hello, #{name}'`.
---

# **Major Differences from C**

- **No Compilation Step**
    - Ruby is interpreted: just run `ruby myfile.rb`—no compiling or linking[1](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/to-ruby-from-c-and-cpp/)[3](https://www.mytaskpanel.com/programming-language-ruby/)[5](https://stackoverflow.com/questions/3314837/key-differences-between-ruby-and-c).

- **Dynamic Typing**
    
    - Variables do not have fixed types. Types are checked at runtime, not compile time[1](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/to-ruby-from-c-and-cpp/)[5](https://stackoverflow.com/questions/3314837/key-differences-between-ruby-and-c).
        
- **Everything is an Object**
    
    - Even integers, strings, and booleans are objects. You can call methods on literals: `5.abs`, `"hello".upcase`[3](https://www.mytaskpanel.com/programming-language-ruby/)[4](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/)[7](https://en.wikipedia.org/wiki/Ruby_syntax).
        
- **No Pointers, No Manual Memory Management**
    
    - No `*`, `&`, or pointer arithmetic. Garbage collection is automatic[1](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/to-ruby-from-c-and-cpp/)[5](https://stackoverflow.com/questions/3314837/key-differences-between-ruby-and-c).
        
- **No Macros, Preprocessor, or Header Files**
    
    - No `#define`, `#include`, or preprocessor directives. Use constants (names starting with a capital letter) instead of macros[1](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/to-ruby-from-c-and-cpp/).
        
- **No Explicit Variable Declarations**
    
    - Just assign: `x = 42`. Variable names themselves have no type[1](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/to-ruby-from-c-and-cpp/)[5](https://stackoverflow.com/questions/3314837/key-differences-between-ruby-and-c).
        
- **No Main Function**
    
    - Code at the top level just runs. No need for `int main()`[1](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/to-ruby-from-c-and-cpp/).

# **Similarities to C (with a Ruby Twist)**

- **Operators**
    
    - Most operators (`+`, `-`, `*`, `/`, `%`, `&`, `|`, `^`, etc.) work as expected, but `++` and `--` do not exist[1](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/to-ruby-from-c-and-cpp/).
        
- **Constants**
    
    - Ruby has constants, but they are by naming convention: names starting with a capital letter are treated as constants[1](https://www.ruby-lang.org/en/documentation/ruby-from-other-languages/to-ruby-from-c-and-cpp/).
        
- **Procedural Style Possible**
    
    - You can write procedural code, but it’s always object-oriented under the hood