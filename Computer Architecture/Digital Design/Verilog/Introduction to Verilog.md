Verilog is a [[Hardware Description Language (HDL)]] used to describe digital circuits and systems. It is not like a traditional programming language such as C or Java; instead, it is a language specifically designed to model hardware behavior and structure.

## What is Verilog?
- Verilog is a language used to describe how digital hardware works at different levels of abstraction, from very high-level behavior to low-level gate connections.
- It enables engineers to write code that models circuits such as microprocessors, communication devices, or memory before physically building them.
- This code can then be simulated, tested, and synthesized to produce actual hardware.

## How is Verilog used?
- Verilog is primarily used to design and verify Field-Programmable Gate Arrays (FPGAs) and Application-Specific Integrated Circuits (ASICs).
- It can describe the function of a circuit (behavioral level), the data flow between registers (register-transfer level), or the gate-level implementation.
- The Verilog code defines modules with inputs and outputs, internal logic, and how signals flow and change over time.

## What does Verilog do?
- Verilog allows digital circuit designers to write, simulate, and verify the functionality of hardware designs in code form.
- It supports simulation, which helps verify the behavior of the design logically before hardware fabrication.
- It facilitates synthesis, turning the code into a netlist that can be used to manufacture real silicon chips or program FPGA devices.

## Is Verilog a programming language?
- Verilog is a hardware description language (HDL), which is different from general-purpose programming languages.
- While programming languages describe algorithms executed sequentially, Verilog describes parallel hardware elements and their interactions.
- It includes constructs for describing combinational and sequential logic, timing, and concurrency.


# Getting Started
Verilog is a **hardware description language (HDL)** used to model digital circuits. Its syntax is similar to C, but it’s designed for describing hardware, not software. A basic Verilog file consists of the following:

## **[[Lexical Tokens in Verilog]]**
- **Keywords:** Reserved words like `module`, `input`, `output`, `wire`, `reg`, `assign`, `always`, `endmodule`
- **Identifiers:** Names for modules, signals, etc. (e.g., `my_module`, `clk`). Must start with a letter or underscore, not a number or `$`.
- **Operators:** Arithmetic (`+`, `-`, `*`), logical (`&&`, `||`, `!`), bitwise (`&`, `|`, `^`, `~`), and more.
- **Numbers:** Written as `<size>'<base><number>`, e.g., `4'b1100` (4-bit binary), `8'hFF` (8-bit hex).
- **Comments:**
    - Single-line: `// comment here`
    - Multi-line: `/* comment here */`
    
- **White space:** Spaces, tabs, and newlines are ignored except inside strings or comments.
- Using the `;` after ending of each statement

## **[[Module Declaration in Verilog]]**
A module is the basic building block:
```verilog
module my_module(input a, input b, output y);   
	assign y = a & b; 
endmodule
```
Module is a basic element of any Verilog program. Think of it like an hardware component of your system, everything happens in them!
- `module` starts the definition, `endmodule` ends it.
- Ports (inputs/outputs) are declared in the header.

## **[[Data Types in Verilog]]**
Now that we know where to work, and what input output ports are, lets look at where we can store data, i.e., values. 
Values are stored as binary numbers, although we might use or work with values of any number system, their storage values are given in terms of binary itself.
- **wire:** Represents a connection Data Types(net) between hardware elements. Used for combinational logic.
- **reg:** Represents a storage element (not a hardware register!). Used inside procedural blocks like `always` or `initial`.
- **integer**: 32-bit signed integer variable, used in loop counters, indices, temporary variables.
- **time**: 64-bit variable representing simulation time, used in timing measurements and delays.
- **real**: Floating-point number(**Non-synthesizable code**), it is used for analog modeling, testbench, calculations.

## **[[Operators and Gates in Verilog]]**
Now its time to write some logic. Once we are familiar with some basic concepts like what are the different tokens in Verilog, where to write the logic, and how to store values, we are almost at that point where we can start writing logic and implement some really cool stuff.
The last part of it all are the Operators or Gates that work on the values to give a certain desired result or to do certain required work/calculation.
- **Arithmetic:** `+`, `-`, `*`, `/`, `%`
- **Bitwise:** `&`, `|`, `^`, `~`, `<<`, `>>`
- **Logical:** `&&`, `||`, `!`
- **Relational:** `==`, `!=`, `>`, `<`, `>=`, `<=`
- **Conditional:** `?:` (ternary operator)
- **Reduction:** `&a`, `|a`, `^a` (AND/OR/XOR all bits of `a`)

## **[[Blocks, Assignments, Conditionals and Loops in Verilog]]**
**The Big Question: How Do We Assign Values in Verilog?**
In software (C, Python, etc.):

```C
x = a + b;  // Simple assignment
```

In Verilog (hardware description):
- **Who drives this signal?**
- **When does it update?**
- **Is it a wire (continuous) or a register (stored)?**

**Answer:** Verilog has **two fundamentally different ways** to assign values:
- **Continuous Assignment (`assign`)**
	What it is:
	- Drives a **`wire`** continuously
	- Models *combinational logic* (no memory, no clock)
	- Changes *immediately* when inputs change
### Syntax:
``` Verilog
assign <wire_name> = <expression>;
```
	
**Example:**
``` Verilog
module adder(
    input [3:0] a, b,
    output [3:0] sum
);
    assign sum = a + b;  // sum updates instantly when a or b changes
endmodule
```
	
**Key Rules:**
- *Only drives `wire`* types
- *Outside any blocks* (top-level in module body)
- Represents *physical connections* (like wires on a circuit board)
- *Cannot use with `reg`*
	
**Mental Model:**
	Think of `assign` as *welding a wire* — the connection is always active.
	
- **Procedural Assignments (Inside Blocks)**
	- **What are Blocks?**
		Blocks are containers for *sequential statements* that execute step-by-step (like software). Two Types of Blocks:
		
		**A. `initial` Block**	
		- Executes *once* at time 0
		- *Not synthesizable* (simulation only)
		- Used for *testbenches* and initialization		
		
		**B. `always` Block** (Most Important!)
		- Executes *repeatedly* based on a **sensitivity list**
		- *Synthesizable* (becomes real hardware)
		- Used for *combinational and sequential logic*
		
	- **Procedural Assignment Operators**
		Inside `always` or `initial` blocks, you can *only assign to `reg` types* using:
		
		**A. Blocking Assignment (`=`)**
		- Executes *sequentially* (like software)
		- Next statement waits for current one to complete
		- Used for *combinational logic*
		
		**B. Non-Blocking Assignment (`<=`)**
		- Executes *concurrently* (all at "same time")
		- Assignments happen *at the end* of the time step
		- Used for *sequential logic* (registers, flip-flops)


## **[[Modeling Styles and Statements in Verilog]]**:
Now lets look at different ways in which one can write a Verilog Program. In Verilog, we have models, the term "models" generally refers to different ways you can describe digital systems and logic circuits—these are called **modeling styles**. 
Each style provides a different level of abstraction, and understanding them is critical for mastering digital design with Verilog.

### 1. Gate-Level or Structural Modeling
- Describes circuits by listing primitive gates (like AND, OR, NOT) or by **instantiating lower-level modules** and interconnecting them with wires.    
- Directly mimics a circuit’s schematic layout.
- Used for detailed hardware mapping or when you need very explicit control of logic structure.

**Example:**
```verilog
module and_or_example(output y, input a, b, c);     
	wire w1;     
	and (w1, a, b);   // w1 = a AND b     
	or  (y, w1, c);   // y = w1 OR c 
endmodule
```
- Can also involve **instantiating custom modules** (hierarchical design).

### 2. Dataflow Modeling
- Describes hardware using logical or arithmetic equations—focuses on **how data flows through the circuit**.
- Uses continuous assignments with the `assign` keyword and Verilog operators.

**Example:**
```verilog
assign y = (a & b) | c;
```
- The code above tells the tool to implement whatever logic (using gates, LUTs, or whatever resource) is needed to satisfy the equation.

---
### 3. Behavioral Modeling
- Describes **how the circuit should behave** using procedural statements (e.g., `always`, `initial`, `if`, `case`, loops).
- Used for describing **algorithms, control flow**, and complex sequential logic (flip-flops, FSMs).
- Closer to a software programming style, but still concurrent at the module level.

**Example:**
```verilog
always @(posedge clk) begin     
	if (reset)         
		q <= 0;     
	else         
		q <= d; 
end
```
---

### Special Model Types in Verilog
- **Testbenches**: Written in behavioral style; stimulate your designs for testing and validation. Usually do not synthesize to hardware.
- **Finite State Machine (FSM) models**: Implement sequential logic using `always` blocks, `case`/`if` statements.
	There are also two distinct approaches for FSMs:
	- **Moore model**: Output depends only on the state.
	- **Mealy model**: Output depends on state **and** input.





1. **Behavioral Statements**:  
    Describe hardware operation through procedural code. The main behavioral constructs are:
    - `initial` blocks: Execute once at simulation start (used in testbenches and initialization).
    - `always` blocks: Execute continuously or on events (core for describing sequential logic like flip-flops).
    - `assign` statements: Continuous assignments for combinational logic.
    
2. **Procedural Blocks and Statements**:  
    Blocks defined by `initial` or `always` keywords, containing sequential statements grouped with `begin ... end`. These are used to describe the behavior over time or in response to signals.
    
3. **Continuous Assignments**:  
    Use the `assign` keyword to describe combinational logic that drives nets continuously.

# How to avoid making a latch?
When designing circuits, you _must_ think first in terms of circuits:

- I want this logic gate
- I want a _combinational_ blob of logic that has these inputs and produces these outputs
- I want a combinational blob of logic followed by a set of flip-flops

What you _must not_ do is write the code first, then hope it generates a proper circuit.

- If (cpu_overheated) then shut_off_computer = 1;
- If (~arrived) then keep_driving = ~gas_tank_empty;

Syntactically-correct code does not necessarily result in a reasonable circuit (combinational logic + flip-flops). The usual reason is: "What happens in the cases other than those you specified?". Verilog's answer is: Keep the outputs unchanged.

This behaviour of "keep outputs unchanged" means the current state needs to be _remembered_, and thus produces a _latch_. Combinational logic (e.g., logic gates) cannot remember any state. Watch out for Warning (10240): ... inferring latch(es)" messages. Unless the latch was intentional, it almost always indicates a bug. Combinational circuits must have a value assigned to all outputs under all conditions. This usually means you always need else clauses or a default value assigned to the outputs.

Example:
Case statements are more convenient than if statements if there are a large number of cases. So, in this exercise, create a 6-to-1 multiplexer. When sel is between 0 and 5, choose the corresponding data input. Otherwise, output 0. The data inputs and outputs are all 4 bits wide.

```verilog
// synthesis verilog_input_version verilog_2001
module top_module ( 
    input [2:0] sel, 
    input [3:0] data0,
    input [3:0] data1,
    input [3:0] data2,
    input [3:0] data3,
    input [3:0] data4,
    input [3:0] data5,
    output reg [3:0] out   );//

    always@(*) begin  // This is a combinational circuit
        case (sel)
        	3'b000: out = data0;
            3'b001: out = data1;
            3'b010: out = data2;
            3'b011: out = data3;
            3'b100: out = data4;
            3'b101: out = data5;
            default: out = 3'b000;
        endcase
    end

endmodule
```

Here, values 110 and 111 of sel are dont care, so leaving them not mentioned would have created a latch, aka this would have created a latch:
```verilog
// synthesis verilog_input_version verilog_2001
module top_module ( 
    input [2:0] sel, 
    input [3:0] data0,
    input [3:0] data1,
    input [3:0] data2,
    input [3:0] data3,
    input [3:0] data4,
    input [3:0] data5,
    output reg [3:0] out   );//

    always@(*) begin  // This is a combinational circuit
        case (sel)
        	3'b000: out = data0;
            3'b001: out = data1;
            3'b010: out = data2;
            3'b011: out = data3;
            3'b100: out = data4;
            3'b101: out = data5;
        endcase
    end

endmodule
```

# Vectors in Verilog
- A **vector** is a multi-bit signal represented as a group of bits (similar to a bus).
- Can be declared for both nets (`wire`) and variables (`reg`).
- Syntax: `[MSB:LSB]` defines the width and bit order.
Example:
```Verilog
wire [3:0] nibble;  // 4-bit vector wire
reg [7:0] byte;     // 8-bit vector reg
```
Vectors are used to connect or store multiple bits together and work efficiently on multi-bit data.

# Four Logic Values Supported
- `0` = logic zero
- `1` = logic one
- `x` = unknown
- `z` = high impedance (floating)

### Revision for High Impedance
In Verilog, the logic value `z` stands for **high impedance**, also called **tri-state** or **floating**. This means the signal is not being actively driven to a logical high (`1`) or low (`0`) by any output circuit.

### Why Use High Impedance?
- When a signal is in the `z` state, it's like the wire is **disconnected** or "open circuit." No current flows from the driver, and the wire can be pulled to any value by another connected driver or by external circuitry.
- This is essential for **bus systems** where multiple devices share the same wire. Only one device should drive the bus at a time; others set their outputs to `z` so they don't interfere

# Vectors
- A **vector** in Verilog is a single signal made up of multiple bits
- Vectors can be declared for both `wire` (net) and `reg` (variable) types.
Data buses, addresses, or any multi-bit value in digital circuits.

## Declaring Vectors
- **Syntax:**
```verilog
wire [MSB:LSB] signal_name; 
reg [MSB:LSB] signal_name;
```
- `[MSB:LSB]` defines the width and bit order. 
- For example, `[7:0]` is 8 bits, with bit 7 as the most significant and bit 0 as the least significant.

So we could do something like `wire [5,2] myWire;`, and it would mean:
- We have 4 bit wide vector
- Bits are:
	- `myWire[2];`
	- `myWire[3];`
	- `myWire[4];`
	- `myWire[5];`
- And we can assign it values:
	- `myWire = 4'b1110`
	- `myWire = 4'hE`
Using something like this makes the vectors in Verilog really powerful.

### Vector Initialization
- To initialize all bits to 1:
```verilog
wire [127:0] all_ones = {128{1'b1}};
// Or: assign mywire = 128'b1; (sets only the least significant bit to 1, not all bits)[1]

```

## Assigning Values to Vectors
- You can assign values using binary, hex, octal, or decimal representations:
```verilog
reg [3:0] a; 
a = 4'b1010; // binary 
a = 4'hA;    // hex 
a = 4'd10;   // decimal 
a = 4'o12;   // octal
```
- The format is `<size>'<base><value>`, e.g., `8'hFF` for 8 bits, hex FF.

## Vector Slicing (Part Select)
- You can select a subset of bits from a vector:
```verilog
wire [7:0] data; 
wire [3:0] lower_nibble = data[3:0]; // bits 3 to 0 
wire [3:0] upper_nibble = data[7:4]; // bits 7 to 4
```
- **SystemVerilog** adds flexible slicing:
    - `data[2 +: 3]` selects bits 2, 3, 4
    - `data[5 -: 2]` selects bits 5, 4

- **SystemVerilog** adds flexible slicing:
    
	- `data[2 +: 3]` selects bits 2, 3, 4
    - `data[5 -: 2]` selects bits 5, 4
	
	- Syntax and Meaning
		- `vector[start +: width]` — Selects `width` bits, starting at `start` and counting **up**.
		- `vector[start -: width]` — Selects `width` bits, starting at `start` and counting **down**.



# Arrays in Verilog

# Compiling and Simulating

## Testbench
### What is a Testbench in Verilog?

A **testbench** is a special Verilog module used to **simulate and verify** the behavior of a design module (called the Device Under Test or DUT). It does not represent hardware itself but is a piece of code written purely for testing purposes. The testbench applies inputs (stimuli) to the DUT, observes outputs, and checks if the circuit behaves as expected.

---

## Key Components of a Verilog Testbench

## 1. Module Declaration

A testbench is defined as a module but **does not have any input or output ports** because it is a top-level simulation entity.

Example:

verilog

`module my_module_tb();   // testbench code here endmodule`

## 2. Signal Declaration

- Inputs to the DUT are declared as **reg** type variables in the testbench since the testbench needs to drive them.
    
- Outputs from the DUT are declared as **wire** type signals in the testbench to monitor the values driven by the DUT.
    

Example:

verilog

`reg a, b;       // inputs to DUT driven by testbench wire y;         // output from DUT monitored in testbench`

## 3. DUT Instantiation

The design module is instantiated inside the testbench, connecting the testbench signals to DUT ports.

Example:

verilog

`my_module uut (   .input1(a),  .input2(b),  .output1(y) );`

Here, `uut` is the instance name of the DUT.

## 4. Stimulus Generation

Inside an `initial` block, test inputs are applied to the DUT. The initial block runs once at simulation start.

Example:

verilog

`initial begin   a = 0; b = 0;  #10;    // Wait for 10 time units  a = 1; b = 0;  #10;  a = 0; b = 1;  #10;  a = 1; b = 1;  #10;  $finish;  // End simulation end`

## 5. Monitoring Outputs

You can use system tasks like `$monitor` or `$display` to print outputs and verify behavior during simulation.

Example:

verilog

`initial begin   $monitor("At time %0t: a=%b b=%b => y=%b", $time, a, b, y); end`

---

## Summary

- A testbench is a self-contained module with no ports.
    
- It instantiates the DUT to provide inputs and observe outputs.
    
- Inputs driven by **reg** and outputs monitored by **wire**.
    
- Uses `initial` blocks for stimulus generation and simulation control.
    
- Essential for verifying digital designs before hardware implementation.
    

---
[Follow this to learn more](https://www.chipverify.com/verilog/verilog-tutorial).


# Arrays in Verilog
- Array of Instances
```verilog
// Instead of 8 separate instantiations
and gate[7:0](out, in1, in2);  // Creates 8 AND gates

```
