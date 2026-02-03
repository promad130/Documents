**Expected to know:** [[Introduction to Verilog#1. ** Lexical Tokens in Verilog **]]
**Topics Covered:**

Let's break down a few of the most basic and commonly used lexical tokens in Verilog, with simple examples:
## 1. **Keywords**
- **Definition:** Reserved words with special meaning in Verilog. You can't use them as names for your own variables or modules.
- **Examples:** `module`, `input`, `output`, `wire`, `reg`, `assign`, `always`, `endmodule`.
```verilog
module my_module(input a, output b);
assign b = ~a;
endmodule
```

## 2. **Identifiers**
- **Definition:** Names you create for modules, signals, variables, etc.
- **Rules:** Must start with a letter or underscore, not a number or `$`. Case-sensitive.
- **Examples:** `clk`, `reset`, `data_in`, `count`.
```verilog
wire clk;
reg [7:0] data_in;
```

## 3. **Operators**
- **Definition:** Symbols that perform operations on signals or variables.
- **Common Examples:** `+` (add), `-` (subtract), `*` (multiply), `&` (bitwise AND), `|` (bitwise OR), `!` (logical NOT), `==` (equality).
```verilog
assign sum = a + b;
assign and_out = a & b;
assign is_equal = (a == b);
```

## 4. **Numbers**
- **Definition:** Used to specify constant values, often with size and base.
- **Syntax:** `<size>'<base><number>`
- Bases: `b` (binary), `d` (decimal), `h` (hex), `o` (octal)
- **Examples:** `4'b1100` (4-bit binary), `8'hFF` (8-bit hex), `16'd255` (16-bit decimal).
	- Note how the size of all values are wrt binary number system. This is a crucial observation that tells us that the values are always stored in binary format.
```verilog
wire [3:0] nibble = 4'b1010;
reg [7:0] byte = 8'hFF;
```

## 5. **Comments**
- **Definition:** Notes for humans, ignored by the compiler.
- **Single-line:** `// comment here`
- **Multi-line:** `/* comment here */`.
```verilog
// This is a single-line comment
/*
  This is a
  multi-line comment
*/
```

## 6. **Whitespace**
- **Definition:** Spaces, tabs, and newlines. Used to separate tokens and make code readable. Ignored by the compiler except inside strings or comments.
```verilog
module my_module(input a ,output b );
```

## 7. **Using `;`**
In Verilog, **ending each statement with a semicolon (;) is generally compulsory**, but there are some nuances to understand.

### When Semicolon is Compulsory
1. **Most executable or assignment statements require a semicolon at the end.**

Examples:
```verilog
assign y = a & b;  // Semicolon required 
wire a, b, y;      // Semicolon required in declarations 
reg q; 
q = d;             // Inside procedural block, semicolon needed
```
2. **Module Declarations and endmodule Statement**
	- The `module` keyword does not need a semicolon.
	- The module's internal statements and declarations inside it must end with semicolons.
	- The `endmodule` keyword marks the end of the module but does not require a semicolon.
```verilog
module my_module(input a, output y);
	assign y = a;  // Semicolon here 
endmodule          // No semicolon needed here
```

### When Semicolon Is Not Used
- Before keywords like `begin`, `end`, `if`, `else`, `case`, `endcase`, `always`, **no semicolon** is used.
- Some constructs are **blocks of statements** and do not end with a semicolon themselves but the statements inside them do.

Example:
```verilog
always @(posedge clk) begin     
	q <= d;    // Semicolon required 
end            // No semicolon after 'end'
```
