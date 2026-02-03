In Verilog, **blocks** are critical concepts—these are sections of code grouped together for procedural execution, logic description, or hardware modeling. 
Understanding the different types of blocks helps structure your code, control when statements execute, and determine how signals update.
We have two types of blocks at the top level:
- Statement Blocks
- Procedural blocks
Both have to work together in order to form a valid and executable logic in Verilog.
```Verilog
<Procedural_Block> <Statement_Block>
```

---
# 1. Statement Blocks
These are the ways to group statements together that can then be executed according to the design. These are essential for writing readable and logically correct code when using constructs like `if`, `else`, `case`, or within [procedural blocks](#1.%20Procedural%20Blocks) such as `always` or `initial`.
## begin ... end (sequential block)
<blockquote><b>Sequential blocks</b> in Verilog are blocks that describe logic and behavior which depend on the order of operations (sequence), usually reflecting the behavior of memory elements like flip-flops, registers, and state machines.</blockquote>
- Groups multiple statements into a single block, executed sequentially (top-to-bottom order as written).
- Needed when more than one statement follows `if`, `else`, `always`, or `initial`.

**Example:**
```verilog
always @(posedge clk) begin   
	count <= count + 1;   
	flag  <= (count == 10);
end
```
## fork ... join (parallel block)
<blockquote>In Verilog, <b>parallel blocks</b> refer specifically to the `fork ... join` construct. This type of block allows multiple statements or processes to execute concurrently during simulation—meaning all enclosed statements start together and may finish independently.​</blockquote>
- **Syntax:**
```verilog
fork   
	statement1;   
	statement2;   
	... 
join
```
- All statements inside a `fork ... join` block are **launched at the same time** by the simulator.
- The simulation waits at `join` until **all** enclosed processes finish.
- Useful for testbench stimulus, verification, or simulating concurrent actions/events.    
- Cannot be used with always block as it is not synthesizable.

**Example of Parallel Block**
```verilog
initial fork   
	#10 a = 1;   
	#20 b = 2;   
	#30 c = 3; 
join
```
- Here, setting `a`, `b`, and `c` starts together.
- Each assignment happens after its delay, but all three run in parallel.(Don't worry if you don't understand the examples yet, keep reading, you'll get it)

# 2. Procedural Blocks
## *a. initial block*
- Statements inside an `initial` block run **once at the very start of simulation**.
- Commonly used in **testbenches** for stimulus generation or initializing signals.
- **Non-synthesizable**—cannot be turned into hardware.
### Syntax:
```verilog
initial <StatementBlock>
```

### What do we mean when we say "*once at the very start of simulation*"?
When we say "at the start of simulation" in Verilog, it means the moment the simulator begins running your entire design (`vvp`, ModelSim, Vivado, etc.). 
This is **not** the same as "calling a function" in software or "instantiating a module" in code.

#### How the `initial` Block Executes in a Simulation
- **Every time the simulator is started**, all `initial` blocks in **every instance** of every module in your whole design will execute—**once per instance**—at simulation time `t=0`.
- If you instantiate a module multiple times in your design, **each instance's `initial` block will run exactly once, at the very beginning of simulation**.

**It does NOT re-run if you re-instantiate the module inside another module, or "call" the module later**—because hardware is not "called" like functions. All instantiations (hardware blocks) are created at compile time and there is no dynamic invocation in simulation.

**Example:**
```verilog
initial begin   
	clk = 0;   
	reset = 1;   
	#10 
	reset = 0; 
end
```
Note that here #10 is an [[Event Timing Control in Verilog]].

## *b. always block*
<blockquote>An <b>`always` block</b> is a fundamental procedural block in Verilog used to describe hardware that reacts to changes in signals, commonly used for modeling sequential logic like flip-flops, latches, state machines, as well as complex combinational logic.</blockquote>

- Executes instructions **repeatedly** for the entire simulation duration.
- The most important procedural block for describing **sequential logic** (registers, flip-flops, counters, state machines).
- Can be **level-sensitive** or **edge-sensitive**, as defined by the block's "sensitivity list".
### Syntax of an `always` Block
```verilog
always @(sensitivity_list) begin     
	// procedural statements (assignments, if, case, loops, etc.) 
end
```
- `always`: Verilog keyword to declare the block.
- `@` followed by **sensitivity list**: Signals or events that trigger the block execution.
- `begin ... end`: Groups multiple statements inside the block.
- Inside the block, write **procedural statements** in sequential order.
**Example:**
```verilog
always @(posedge clk, negedge reset) begin   
	if (!reset)     
		q <= 0;   
	else     
		q <= d; 
	end
```
---

# 3. Special Modeling Blocks
## **a. continuous assignment (assign)**
- Not a procedural block, but describes **combinational logic** outside of procedural contexts.
- Always “active” and automatically updates outputs when inputs change.

**Example:**
```verilog
assign y = a & b;
```
## **b. user-defined primitives (UDP)**
- Allows you to define your own "gate" with a table of behaviors.
- Encapsulated in `primitive ... endprimitive` blocks.

#  Block Statements in FSMs and Testbenches
Finite State Machines (FSMs) and testbenches use a combination of the above blocks:
- **Multiple always blocks running in parallel** (common in FSMs for updating state and outputs).
- **initial and always blocks for stimulus generation and checking** (in testbenches).

---
# Summary:

|Block Type|Key Use|Syntax Example|
|---|---|---|
|**initial**|One-time setup/stimulus|`initial begin ... end`|
|**always**|Repeated hardware behavior|`always @(...) begin ... end`|
|**begin ... end**|Sequential statement grouping|`{if (x) begin ... end}`|
|**fork ... join**|Parallel execution (testbench)|`fork ... join`|
|**assign**|Continuous combinational logic|`assign y = a & b;`|
|**primitive ... endprimitive**|User-defined primitive models|`primitive name ... endprimitive`|

## Key Notes
- **Combinational logic**: Use `assign` and level-sensitive `always` blocks.
- **Sequential logic**: Use edge-sensitive `always @(posedge clk)` or `@(negedge clk)`.
- **Testbenches are full of initial, fork...join, and parallel always blocks.**
- **You can nest begin...end blocks, but fork...join is only for simulation, not synthesis.**    
- **initial runs once, always runs repeatedly.**

---
# Conditionals and Loops in Verilog
These work with Statement Blocks or a single statement to execute a certain set of commands or command according to the condition that we require.
These are used inside the procedural blocks, as they describe **procedural behavior**, and hence can be used only inside procedural blocks, used in procedural modelling.
## `if`, `else`, and `else if` conditionals
**Rules**:
- Use `if` to execute code when a condition is true. 
- `else` executes when the `if` condition is false.
- `else if` is like chaining multiple conditions (in Verilog, `else if` is written as separate `else` followed by `if`).

Example:
```verilog
always @(some_signals) begin
    if (a == 1) begin
        out = 1;
    end else if (b == 1) begin
        out = 0;
    end else begin
        out = 1'bx; // unknown
    end
end
```
- Use `begin ... end` to group multiple statements for a condition.
- Omitting `begin...end` is allowed if a condition has only single statements.

## `case` statements:
Case statements in Verilog are a control flow construct used primarily within behavioral modeling to select one of many code blocks to execute based on the value of an expression, similar to switch-case statements in programming languages like C.

**Syntax:**
```Verilog
case (expression)
  value1: begin
    // statements for value1
  end
  value2: begin
    // statements for value2
  end
  ...
  default: begin
    // statements if no case matches
  end
endcase
```
- **expression**: The variable or signal being compared.
- **value1, value2, ...**: Constants or literals to match against the expression.    
- **default**: Optional clause executed if none of the values match, acts like "else".

**Types of Case Statements in Verilog**
- **case**: Compares expression exactly with listed values (bit-wise equality).
- **casez**: Like case, but treats `z` and `?` as don't-cares in comparisons.
	It treats bits that have the value z as don't-care in the comparison.
	Example:
	```verilog
	always @(*) begin
	    casez (in[3:0])
	        4'bzzz1: out = 0;   // in[3:1] can be anything
	        4'bzz1z: out = 1;
	        4'bz1zz: out = 2;
	        4'b1zzz: out = 3;
	        default: out = 0;
	    endcase
	end
	```
- **casex**: Treats both `z` and `x` (unknown) as don't-cares, useful but can be risky due to masking unknowns.

## `for` loop
The `for` loop in Verilog is a procedural control statement used always used inside the procedural blocks and used in procedural statements.

### Syntax:
```verilog
for (initialization; condition; update) begin
    // statements to repeat
end
```
- **initialization**: Sets the starting value of the loop variable.
- **condition**: Loop runs as long as this is true.
- **update**: Updates the loop variable each iteration (typically increment or decrement).
- The code inside `begin ... end` executes each iteration.

### Some points
- The loop variable is *usually* an `integer` declared outside the loop.
- `for` loops are **unrolled by synthesis tools** at compile time, i.e., hardware is replicated for each iteration.
- Use loops only where the number of iterations is **known at compile time**.
- Do NOT use loops for indefinite or runtime iteration.

Example:
```Verilog
integer i;
reg [7:0] reg_array [0:15];  // 16 registers, 8-bit each

initial begin
    for (i = 0; i < 16; i = i + 1) begin
        reg_array[i] = 0;    // Initialize all registers to 0
    end
end
```

## `while` loop
A `while` loop in Verilog is used inside procedural blocks (`initial` or `always`) to **repeatedly execute a set of statements as long as a condition remains true**. 
It's similar to `while` loops in C or Python, but it's important to remember that in hardware description, all loops must have conditions known at compile time and eventually terminate (otherwise simulation can hang).

### Syntax:
```verilog
while (<condition>) begin
    // statements to execute repeatedly
end
```
- `<condition>` is a boolean expression that is checked before each loop iteration.
- Use `begin ... end` to group multiple statements (or just a single statement without them).

**Example**:
```verilog
integer count;
initial being
	while(count < 5) begin
		$display("Value: %d", count);
		count = count + 1;
	end
end
```

**Expected Output**:
```text
Value: 0
Value: 1
Value: 2
Value: 3
Value: 4
```

### Main point:
- Use `while` loops **only inside procedural blocks** (e.g., `initial`, `always`).
- The loop must eventually terminate—otherwise the simulation will hang!
- Useful in testbenches for stimulus, initialization, or checking conditions.
- Loops generally **unroll** at synthesis time, so use static, predictable conditions in synthesizable code.

## `repeat` loop:
