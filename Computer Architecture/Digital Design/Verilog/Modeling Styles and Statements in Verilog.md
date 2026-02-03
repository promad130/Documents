![[Introduction to Verilog#** Modeling Styles and Statements in Verilog **]]

# Structural Modeling in Verilog
Structural modeling in digital design with Verilog is a method of describing a digital circuit by explicitly specifying its components (such as logic gates or modules) and the detailed interconnections between them.

## Key Concepts in Structural Modeling
- **Component Instantiation**: You instantiate primitive gates (AND, OR, NOT, etc.) or user-defined modules inside a module.
- **Wiring**: Components are connected with wires, which represent signal paths.
- **Hierarchy**: Structural modeling supports hierarchical designs, where modules contain instances of other modules.
- **Explicit Details**: Every part and connection is explicitly declared.

Now lets have a look at its implementation in Verilog, aka Structural Statements:
# Structural Statements in Verilog
<blockquote><b>Structural statements in Verilog allow you to build digital designs by explicitly instantiating logic gates and connecting them using wires, closely reflecting the actual hardware schematic.</b></blockquote>
Structural Modeling is the practice of describing the digital system in terms of its constituent components and the way these components are interconnected.
Here, we work with logic gates and there is minimum level of abstraction.

## Components of Structural Statements:
### Gate Initialization
Verilog supports standard logic gate primitives like AND, OR, NOT, XOR, XNOR, etc,., which can be used to initialize the gates.

**Syntax:**
```verilog
<gate_type> <instance_name> (<output>, <input1>, <input2>, ...);
```

### Wire Declaration 
`wire` is used to declare internal connections between gates or modules.
Example:
```verilog
wire w1, w2;
```

### Module Declaration
A complex design requires a module declaration, so that we can have multiple hardware parts that we can then assemble into the whole system.
Any previously defined module can be instantiated within another module.
**Syntax:**
Named Port Mapping
```verilog
module_name instance_name (.port1(signal1), .port2(signal2), ...);
```
Positional Post Mapping
```verilog
module_name instance_name (signal1, signal2, ...);
```

For using another module which is in another file, we use `include` keyword:
## include keyword:
include keyword literally inserts the content of one file into another, making the modules across files available to use. It is a preprocessor directive, works like `#include` in C.

Syntax:
```verilog
`include "filename.v"
```

Some important points:
- The filename is given in double quotes `"..."`.
- The included file contents are copied into the location of the `include` line by the compiler.
- File paths can be relative or absolute.
- The `include` directive is **just a text inclusion**. It doesn't create hierarchy or dependency.
- **Including the same file multiple times can cause redefinition errors**; use include guards if needed (not built-in in Verilog; managed by design).
I.e., for example we have:
**first.v**
```verilog
module myModule(
	//Module Inputs and outputs
);
	//module logic
endmodule
```

**second.v**
```verilog
`include "./first.v"

module mySecondModule(
	//Module Inputs and Outputs
);
	//Module Logic
endmodule
```

**errorPlace.v**
```verilog
`include "./first.v"
`include "./second.v"

//Remaining Logic
```

Now, here `errorPlace` will generate an error, as first.v has already been imported in second.v, so basically what we are doing here is:
**errorPlace.v**
```verilog
`include "./first.v"
`include "./first.v"

module mySecondModule(
	//Module Inputs and Outputs
);
	//Module Logic
endmodule

//Remaining Logic
```
Hence, the first.v is being imported again!
So keep these things in mind.