**Expected to know:** [[Introduction to Verilog#** Blocks, Assignments, Conditionals and Loops in Verilog **]]
**Topics Covered:**

![[Introduction to Verilog#** Blocks and Assignments in Verilog **]]

Verilog uses **assignment statements** to connect signals and describe how data flows in hardware. There are two main types:

## 1. Continuous Assignment (`assign`)
- **Purpose:** Used to drive values onto `wire`-type signals, modeling how real hardware wires work.

- **Syntax:**
```verilog
assign output_signal = expression;
```
- The output updates _immediately_ whenever any input in the expression changes.
- Only works with `wire` (or net) types, not `reg`.

- **Examples:**
```verilog
assign and_out = a & b;      // AND gate 
assign upper_4bits = data[7:4]; // Extract upper 4 bits 
assign out = sel ? d1 : d2; // Conditional (multiplexer)
```

- **Delay:** You can add a simulation delay:
```verilog
assign #5 out = a & b; // out updates 5 time units after a or b changes[1][6]
```    

- **Implicit Assignment:** You can assign a value when declaring a wire:
```verilog
wire valid = a | b; // Implicit continuous assignment[2][8]
```
Where `|` is an OR operator

## 2. Procedural Assignment
- Used inside `always` or `initial` blocks to update `reg`-type signals.
- Syntax:
```verilog
always @(posedge clk) begin   
	data <= a + b; 
end
```
- Not covered in detail here, but important for sequential logic.    

Now what are these `always` or `initial` blocks?

Procedural assignments are **how you update variables (like `reg`, `integer`, or memory types) inside procedural blocks**—these are blocks that describe how your circuit behaves over time, not just instantaneously. Procedural assignments are only allowed inside blocks like `always`, `initial`, `task`, or `function`.[](https://verificationmaster.com/procedural-assignment/)

## Why Is It Written This Way?
- **Hardware needs memory:** Real circuits have storage elements (flip-flops, registers) that only update at specific times (like on a clock edge). Procedural assignments let you model this behavior.
- **Verilog separates wires and storage:** `wire` is for connections (updated instantly with `assign`), but `reg` is for storage (updated only when you say so, inside a procedural block).
- **Procedural blocks = time-based behavior:** You use `always` or `initial` to say "do this when something happens" (like a clock tick, reset, or simulation start).

## How Is It Written?
Procedural assignments come in two main flavors:
## 1. **Blocking Assignment (`=`)**
- **Syntax:** `a = b;`
- **Behavior:** Executes statements **in order**. The next statement waits until the current one is done.
- **Use:** Good for combinational logic inside an `always` block, or for simple testbench code.
- **Example:**
    ```    verilog
    always @(posedge clk) begin   a = b;   b = c;   // 'a' gets 'b', then 'b' gets 'c', in order end
    ```
- **Why?** This models hardware where one thing happens after another, like a chain of logic gates.

## 2. **Non-blocking Assignment (`<=`)**
- **Syntax:** `a <= b;`
- **Behavior:** All assignments in the block are **scheduled** to update at the end of the time step. Statements don't wait for each other—they all "happen together".
- **Use:** Essential for modeling flip-flops and registers that update in parallel on a clock edge.
- **Example:**
    ```    verilog
    always @(posedge clk) begin   
	    a <= b;   
	    b <= c;   // Both assignments are scheduled; 'a' gets old 'b', 'b' gets old 'c' 
	end
    ```
- **Why?** This matches real hardware, where all flip-flops update at the same time on a clock edge.

In a **combinational** always(i.e., for example `always @(*)`) block, use **blocking** assignments. In a **clocked** always(i.e., for example `always @(posedge clk)`) block, use **non-blocking** assignments. 
A full understanding of why is not particularly useful for hardware design and requires a good understanding of how Verilog simulators keep track of events. 
Not following this rule results in extremely hard to find errors that are both non-deterministic and differ between simulation and synthesized hardware.

# Blocks, Conditionals and Loops
![[Blocks, Conditionals and Loops in Verilog]]
