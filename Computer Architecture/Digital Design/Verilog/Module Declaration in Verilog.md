**Expected to know:** [[Introduction to Verilog#2. ** Module Declaration in Verilog **]]
**Topics Covered:**

<blockquote>In Verilog, a <b>module</b> is the fundamental building block for describing hardware. It represents a self-contained block of hardware with a well-defined interface of inputs and outputs, much like a function or a class in software programming. Modules can be instantiated and connected to build complex digital systems.</blockquote>
- Begins with the keyword `module` 
- Followed by the module name 
	- and an optional list of ports (inputs/outputs). 
- The module ends with the keyword `endmodule`. The ports serve as the interface to the outside world.

Basic syntax:
```verilog
module module_name (port1, port2, ..., portN);   // Port declarations  
	input port1;  
	output port2f;  // Internal signals and logic 
endmodule
```

# Ports: Input, Output, Inout
- **Input ports** are signals that bring data into the module.
- **Output ports** send data out of the module.
- **Inout ports** are bidirectional ports, used for buses that can both transmit and receive.

### Two Styles of Port Declaration:
1. **Verilog 1995** style (Ports declared separately):
```verilog
module abc (in1, in2, out1, out2);   
	input in1, in2;  
	output out1, out2;  
	wire out1;  
	reg out2;  
	
	// Module body 
	
endmodule
```
2. **Verilog 2001** style (Ports declared with direction and data type):
```verilog
module abc (   
	input in1,  
	input in2,  
	output wire out1,  
	output reg out2 
	);  
	 
		// Module body 
endmodule
```
The Verilog 2001 style is more concise and clear, merging port declaration and definition.

---
## Input, Output and Wires
In Verilog, understanding the concepts of inputs, outputs, and wires is fundamental to describing how signals flow and are connected in digital circuits. Here's a detailed explanation:

### Inputs
- **Input** ports are signals coming _into_ a module from the external environment or other modules.
- They are used to receive data or control signals.
- In Verilog, inputs are always of **net type**, typically declared as `wire` internally, meaning they represent physical connections rather than storage.
- Inputs cannot be assigned values inside the module because they are driven externally.
```Verilog
input a; // 'a' receives signal from outside the module
```

### Output
- **Output** ports send signals _out_ of a module to other modules or external pins.
- An output can be of type `wire` or `reg`:
    - If the output is driven by continuous assignment (`assign`), it's `wire`.
    - If assigned inside procedural blocks (like `always`), it must be declared as `reg`.
- Outputs represent the result or response of the module logic.

Example:
```verilog
output y;          // default wire 
output reg y_reg;  // can store value inside procedural block
```

# Parameters
Parameters in Verilog are the named constants that can be used again and again. They provide a way to make your modules more flexible and reusable by setting certain values during module declaration or override them during instantiation.
- Parameters are constant values defined inside a module using the keyword `parameter`.
- They act like named constants.
- They do not change during simulation or synthesis.
- They allow customizing modules without modifying the source code.

## Syntax of Parameter Declaration in Module
```verilog
module example_module #(parameter WIDTH=8, parameter DEPTH = 16)
( 
	input wire [WIDTH-1:0] data_in,
	output wire [DEPTH-1:0] data_out,
);
	//Module Implementation
endmodule 
```
This syntax uses Verilog-2001 style parameter declaration with the `#(...)` before the port list.

Alternatively, parameters can be declared inside the module body:
```verilog
module example_module
(
	input wire [WIDTH-1:0] data_in,
	output wire [DEPTH-1:0] data_out
);
	parameter WIDTH = 8;
	parameter DEPTH = 16;
	
	//Module Implementation
	
endmodule
```

# Dangling wires
Note that we can not provide connection to the wire if the value is not of our use, for example in here:

You are given a module `add16` that performs a 16-bit addition. Instantiate two of them to create a 32-bit adder. 
One add16 module computes the lower 16 bits of the addition result, while the second add16 module computes the upper 16 bits of the result, after receiving the carry-out from the first adder. 
Your 32-bit adder does not need to handle carry-in (assume 0) or carry-out (ignored), but the internal modules need to in order to function correctly. (In other words, the `add16` module performs 16-bit a + b + cin, while your module performs 32-bit a + b).

Connect the modules together as shown in the diagram below. The provided module `add16` has the following declaration:

`module add16 ( input[15:0] a, input[15:0] b, input cin, output[15:0] sum, output cout );`

![[Pasted image 20251119023316.png]]

```verilog
module top_module(
    input [31:0] a,
    input [31:0] b,
    output [31:0] sum
);
	wire carry_out;
    add16 a1(a[15:0], b[15:0], 1'b0, sum[15:0], carry_out);
    add16 a2(a[31:16], b[31:16], carry_out, sum[31:16], );
endmodule
```