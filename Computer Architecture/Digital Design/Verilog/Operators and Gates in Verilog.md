![[Introduction to Verilog#** Operators and Gates in Verilog **]]

In Verilog, gates are basic building blocks that represent the fundamental digital logic operations like AND, OR, NOT, NAND, NOR, XOR, and XNOR. These gates can be used to model combinational logic circuits at the gate level.

## Basics of Gates in Verilog
Basic gates in Verilog are the fundamental building blocks for digital circuits and include AND, OR, NOT, NAND, NOR, XOR, and XNOR gates. Each gate operates on one or more inputs to produce a specific logical output.

- **NOT gate**: It inverts the input; output is the complement of input.
```verilog
module not_gate(input a, output c);   
	assign c = ~a; 
endmodule
```
- **AND gate**: Output is high (1) only if all inputs are high.
```verilog
module and_gate(input a, input b, output c);   
	assign c = a & b; 
endmodule
```
- **OR gate**: Output is high if at least one input is high.
```verilog
module or_gate(input a, input b, output c);   
	assign c = a | b; 
endmodule
```
- **NAND gate**: Output is the complement of AND gate output.
```verilog
module nand_gate(input a, input b, output c);   
	assign c = ~(a & b); 
endmodule
```
- **NOR gate**: Output is the complement of OR gate output.
```verilog
module nor_gate(input a, input b, output c);   
	assign c = ~(a | b); 
endmodule
```
- **XOR gate**: Output is high if inputs are different.    
```verilog
module xor_gate(input a, input b, output c);   
	assign c = a ^ b; 
endmodule
```
- **XNOR gate**: Output is high if inputs are the same.    
```verilog
module xnor_gate(input a, input b, output c);   
	assign c = ~(a ^ b); 
endmodule
```

These gates can be defined using the `assign` statement in Verilog for simple combinational logic. 
They can also be instantiated as primitives or within behavioral blocks. This modularity helps you build complex digital circuits from these basic gates easily. 

## Gate Primitives
Verilog provides built-in **gate primitives** that correspond to common logic gates. The main gate primitives are:

- `and` — AND gate
- `or` — OR gate
- `not` — NOT gate (inverter)
- `nand` — NAND gate (AND followed by NOT)
- `nor` — NOR gate (OR followed by NOT)
- `xor` — XOR gate (exclusive OR)
- `xnor` — XNOR gate (exclusive NOR)

## Syntax for Gate Instantiation

Gates in Verilog are instantiated similarly to modules but they are predefined, so you don't need to write their internal logic.

Basic syntax:
```verilog
gate_type instance_name (output, input1, input2, ...);
```
- The first port is always the **output**.
- The subsequent ports are **inputs** to the gate.

Example of a 2-input AND gate:
```verilog
and and1 (y, a, b);
```
This means the output `y` is the AND of inputs `a` and `b`.

## Example Codes for Basic Gates
1. **AND Gate (2-input)**    
```verilog
module and_gate(input a, input b, output y);   
	and g1(y, a, b); 
endmodule
```
2. **OR Gate (2-input)**
    
```verilog
module or_gate(input a, input b, output y);   
	or g1(y, a, b); 
endmodule
```
3. **NOT Gate**
    
```verilog
module not_gate(input a, output y);   
	not g1(y, a); 
endmodule
```
4. **NAND Gate**
    
```verilog
module nand_gate(input a, input b, output y);   
	nand g1(y, a, b); 
endmodule
```
5. **NOR Gate**
    
```verilog
module nor_gate(input a, input b, output y);   
	nor g1(y, a, b); 
endmodule
```
6. **XOR Gate**
    
```verilog
module xor_gate(input a, input b, output y);   
	xor g1(y, a, b); 
endmodule
```
7. **XNOR Gate**
    
```verilog
module xnor_gate(input a, input b, output y);   
	xnor g1(y, a, b); 
endmodule
```
## Multi-input Gates

You can specify more than two inputs for gates with multiple inputs:
```verilog
and g_and(y, a, b, c, d);  // y = a & b & c & d
```
## Continuous Assignment Alternative
Instead of gate primitives, combinational logic can also be described with **continuous assignments (`assign`)** using operators like `&`, `|`, `~`, `^` for flexibility.
These gates provide a direct and low-level way to model digital logic using Verilog's gate-level modeling style. They map closely to actual hardware implementations.

# Verilog Operators

## **Arithmetic Operators**

|Operator|Description|Example|
|---|---|---|
|`+`|Addition|`a + b`|
|`-`|Subtraction|`a - b`|
|`*`|Multiplication|`a * b`|
|`/`|Division|`a / b`|
|`%`|Modulus|`a % b`|
|`**`|Power|`a ** b`|

## **Bitwise Operators**

| Operator | Description | Example |
| -------- | ----------- | ------- |
| &        | AND         | a & b   |
| \|       | OR          | a \| b  |
| ^        | XOR         | a ^ b   |
| ~        | NOT         | ~a      |
| ~&       | NAND        | ~(b&a)  |
| ~\|      | NOR         | ~(b\|a) |
| ~^       | XNOR        | ~(b^a)  |

## **Logical Operators**

| Operator | Description | Example |
| -------- | ----------- | ------- |
| &&       | Logical AND | a && b  |
| \|\|     | Logical OR  | \|\|    |
| !        | Logical NOT | !a      |

## **Relational Operators**

| Operator | Description                  | Example |
| -------- | ---------------------------- | ------- |
| ==       | Equal                        | a == b  |
| !=       | Not equal                    | a != b  |
| <        | Less than                    | a < b   |
| <=       | Less than or equal           | a <= b  |
| >        | Greater than                 | a > b   |
| >=       | Greater than or equal        | a >= b  |
| ===      | Case equality (compares X,Z) | a === b |
| !==      | Case inequality              | a !== b |
### === and !== compared to == and !=

For `==` and `!=`:
- Returns **X (unknown)** if either operand contains `x` or `z`
- Used in synthesizable RTL code
- Example:
``` Verilog
1'b0 == 1'b1   // Returns 0 (false)
1'b0 == 1'bx   // Returns x (unknown)
1'bz == 1'bz   // Returns x (unknown)
```

For `===` and `!==`: 
- Compares bit-by-bit including `x` and `z` values
- Returns **0 or 1** (never returns x)
- **Not synthesizable** - only used in testbenches and simulation
- Example:
``` Verilog
1'b0 === 1'b1   // Returns 0 (false)
1'b0 === 1'bx   // Returns 0 (false, they're different)
1'bx === 1'bx   // Returns 1 (true, both are x)
1'bz === 1'bz   // Returns 1 (true, both are z)
1'bx === 1'bz   // Returns 0 (false, x is not z)
```
## **Shift Operators**

|Operator|Description|Example|
|---|---|---|
|`<<`|Left shift|`a << 2`|
|`>>`|Right shift|`a >> 2`|
|`<<<`|Arithmetic left shift|`a <<< 2`|
|`>>>`|Arithmetic right shift|`a >>> 2`|

## **Assignment Operators**

|Operator|Description|Example|
|---|---|---|
|`=`|Blocking assignment|`a = b`|
|`<=`|Non-blocking assignment|`a <= b`|
|`+=`|Add and assign|`a += b`|
|`-=`|Subtract and assign|`a -= b`|

## **Other Operators**
- **Conditional**: `condition ? true_val : false_val`
- **Replication**: `{3{a}}` (repeats `a` 3 times)
- **Reduction**: `&a` (AND all bits), `|a` (OR all bits), `^a` (XOR all bits)
	You're already familiar with bitwise operations between two values, e.g., `a & b` or `a ^ b`. Sometimes, you want to create a wide gate that operates on all of the bits of _one_ vector, like (`a[0] & a[1] & a[2] & a[3] ... `), which gets tedious if the vector is long.
	The _reduction_ operators can do AND, OR, and XOR of the bits of a vector, producing one bit of output:
	```verilog
	& a[3:0]     // AND: a[3]&a[2]&a[1]&a[0]. Equivalent to (a[3:0] == 4'hf)
	| b[3:0]     // OR:  b[3]|b[2]|b[1]|b[0]. Equivalent to (b[3:0] != 4'h0)
	^ c[2:0]     // XOR: c[2]^c[1]^c[0]
	```
	These are _unary_ operators that have only one operand (similar to the NOT operators ! and ~). You can also invert the outputs of these to create NAND, NOR, and XNOR gates, e.g., (`~& d[7:0]`).

### **Concatenation**: `{a, b, c}`
The concatenation operator can be used on both the left and right sides of assignments.
```verilog
input [15:0] in;
output [23:0] out;
assign {out[7:0], out[15:8]} = in;         // Swap two bytes. Right side and left side are both 16-bit vectors.
assign out[15:0] = {in[7:0], in[15:8]};    // This is the same thing.
assign out = {in[7:0], in[15:8]};       
/*
This is different. The 16-bit vector on the right is extended to match the 24-bit vector on the left, so out[23:16] are zero. 
In the first two examples, out[23:16] are not assigned.
*/
```

#### Some examples:
![[Pasted image 20251118234931.png]]
This is what we want to do, then the Verilog module of it would be:
```verilog
module top_module (
    input [4:0] a, b, c, d, e, f,
    output [7:0] w, x, y, z );//

    // assign { ... } = { ... };
    assign w = {a, b[4:2]};
    assign x = {b[1:0], c, d[4]};
    assign y = {d[3:0], e[4:1]};
    assign z = {e[0], f, 2'b11};

endmodule
```

``` text
Given an 8-bit input vector [7:0], reverse its bit ordering.
```
```verilog
module top_module( 
    input [7:0] in,
    output [7:0] out
);

    assign {
            out[7], out[6], 
        	out[5], out[4], 
        	out[3], out[2], 
        	out[1], out[0]
    		}
        =
		    {
		        in[0], in[1],
		        in[2], in[3],
		        in[4], in[5],
		        in[6], in[7],
		    };
endmodule

```

#### Replication in Concatenation
The [concatenation operator](https://hdlbits.01xz.net/wiki/Vector3 "Vector3") allowed concatenating together vectors to form a larger vector. But sometimes you want the same thing concatenated together many times, and it is still tedious to do something like assign a = {b,b,b,b,b,b};. The replication operator allows repeating a vector and concatenating them together:

```verilog
{5{1'b1}}           // 5'b11111 (or 5'd31 or 5'h1f)
{2{a,b,c}}          // The same as {a,b,c,a,b,c}
{3'd5, {2{3'd6}}}   
// 9'b101_110_110. It's a concatenation of 101 with the second vector, which is two copies of 3'b110.
```

The **replication operator** syntax is:
``` Verilog
{count{item}}
     ↑    ↑
     └────┴─── Both braces are REQUIRED
```

## **Key Differences**
- `&` vs `&&`: Bitwise vs Logical
- `==` vs `===`: Regular vs Case equality (X,Z handling)
- `=` vs `<=`: Blocking vs Non-blocking assignment

# Example
```verilog
// Basic gates
and g1(y, a, b);           // y = a & b
or g2(y, a, b);            // y = a | b  
not g3(y, a);              // y = ~a
nand g4(y, a, b);          // y = ~(a & b)
xor g5(y, a, b);           // y = a ^ b

// Multi-input gates
and g6(y, a, b, c, d);     // y = a & b & c & d
or g7(y, a, b, c);         // y = a | b | c

// Tri-state buffers
bufif1 g8(out, in, enable); // out = in when enable=1, else Z
bufif0 g9(out, in, enable); // out = in when enable=0, else Z

```

- Full Adder Example
```verilog
module full_adder(a, b, cin, sum, cout);
  input a, b, cin;
  output sum, cout;
  wire w1, w2, w3;
  
  xor (sum, a, b, cin);      // Sum output
  and (w1, a, b);            // Partial carry
  and (w2, a, cin);          // Partial carry  
  and (w3, b, cin);          // Partial carry
  or (cout, w1, w2, w3);     // Final carry
endmodule

```

