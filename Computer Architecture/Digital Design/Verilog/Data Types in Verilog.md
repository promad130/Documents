**Expected to know:** [[Introduction to Verilog#3. ** Data Types in Verilog **]]
**Topics Covered:**

Verilog data types fall mainly into two categories: **nets** and **variables**.
# 1. Nets
- Represent **connections** or physical wires between components.
- Do **not** store values by themselves; they continuously reflect the driven value.
- Declared using keywords like `wire`, `wand`, `wor`, etc.
- Most common net type is `wire`.
- Used on the left side of **continuous assignments**.
Example:
```Verilog
wire a;         // Single bit wire
wire [7:0] bus; // 8-bit wide wire (vector)
```

### wire
- Represents a **physical connection or a net** between hardware elements, like a wire in a real circuit.
- **Cannot store values**; it only reflects the signal being driven by some source.
- Used for **combinational logic** where signals continuously flow.
- Assigned values only through **continuous assignments** (`assign` statements) or by connecting to outputs of modules or primitives.
- Declared as:
```verilog
wire signalName;
```


# 2. Variables (Registers)
- Used to **store values** and hold them until explicitly changed.    
- Declared using `reg`, `integer`, etc.
- Used inside procedural blocks (`always`, `initial`).
Example:
```Verilog
reg flag;         // Single bit register
reg [7:0] data;   // 8-bit register (vector)
```

### reg
- Represents a **variable that can hold or store a value** until explicitly changed.
- Conceptually modeled like a **register or memory** element but does not necessarily imply hardware registers; it depends on usage.
- Used mainly in **sequential logic** (inside `always` blocks) where values are assigned procedurally.
- Can be assigned values inside **procedural blocks** (`always` or `initial`).
- Declared as:
```verilog
reg signal_name;
```

# 3. Special Type
Numeric or special-purpose variables, mostly used inside procedural blocks; not always synthesizable as hardware directly.
Let's explore these three important special data types in Verilog with detailed explanations, syntax, and examples:

---
## 1. `integer` Data Type
- **What it is:**  
    A Verilog `integer` is a **signed 32-bit variable** used for storing integer values.
- **Common role:**  
    Used mostly as **loop counters**, indices, or any temporary integer variable inside **procedural blocks** (`always`, `initial`).
- **Not hardware directly:**  
    It is not synthesized as a specific hardware register by itself; synthesis tools infer hardware as needed.

### Syntax:
```verilog
integer i;  // declaration
```
### Example — Loop counter using integer:
```verilog
integer i; 
initial begin     
	for (i = 0; i < 8; i = i + 1) begin         
		$display("Counter value: %d", i);     
	end 
end
```
- The `integer i` is used to control the loop.    

---
## 2. `time` Data Type
- **What it is:**  
    A special **64-bit unsigned integer** that stores simulation time.
- **Purpose:**  
    Used to **measure or capture elapsed time** or event timestamps during simulation.
- **Non-synthesizable:**  
    Does **not correspond to any hardware** and cannot be synthesized.

### Syntax:
```verilog
time t1;  // declare a time variable
```
### Example — Measure elapsed simulation time:
```verilog
time start_time, elapsed;

initial begin
	start_time = $time;
	#100
	elapsed = $time - start_time;
	$display("Elapsed time: %0t", elapsed);
```
- `$time` is a system function returning current simulation time.
- Used mainly for testbenches, not hardware.
- The `#` symbol followed by a number in Verilog represents a **time delay**.
	```Verilog
	#<time_value> <statement>;
	```
**Output**:
```text
Elapsed simulation time: 100
```

---
## 3. `real` Data Type
- **What it is:**  
    A 64-bit floating-point number.
- **Purpose:**  
    Used for **analog modeling**, precise calculation, or testbench computations.
- **Non-synthesizable:**  
    Cannot be implemented in hardware.

### Syntax:
```verilog
real r;  // declare a real variable
```
### Example — Perform floating-point calculation:
```verilog
real voltage, current, power;

initial begin
	voltage = 5.0;
	current = 0.1;
	power = voltage * current;
	$display("Power = %f Watts", power);
end
```

---
## Summary Table

| Data Type | Bit Width  | Signs    | Synthesizable? | Typical Usage                                |
| --------- | ---------- | -------- | -------------- | -------------------------------------------- |
| `integer` | 32 bits    | Signed   | Yes            | Loop counters, procedural vars               |
| `time`    | 64 bits    | Unsigned | No             | Simulation time measurement                  |
| `real`    | 64 bits FP | N/A      | No             | Analog/testbench floating-point calculations |
