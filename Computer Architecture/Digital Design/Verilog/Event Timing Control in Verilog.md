# Edge and Event Timing Controls in Verilog

You already know about `posedge` (positive edge)—it triggers when a signal (usually a clock) transitions from low (0) to high (1). There are other timing/event controls in Verilog that let you synchronize your logic to different signal changes or conditions:

## 1. **negedge**

- **Meaning:** "Negative edge"—triggers when a signal transitions from high (1) to low (0).
    
- **Usage:**
    
    verilog
    
    `always @(negedge clk) begin   // Runs when clk goes from 1 to 0 end`
    
- Used for circuits that update on the falling edge of a clock or signal.
    

## 2. **Level-sensitive and Multiple Event Controls**

- You can trigger on multiple edges or levels:
    
    verilog
    
    `always @(posedge clk or negedge rst_n) begin   // Runs on clk rising edge OR rst_n falling edge end`
    
- You can also use commas:
    
    verilog
    
    `always @(posedge clk, negedge rst_n) begin   // Same as above, triggers on either event end`
    
- For level-sensitive (not edge), just use the signal name:
    
    verilog
    
    `always @(clk or rst_n) begin   // Runs whenever clk or rst_n changes value end`
    

## 3. **wait Keyword (Level-sensitive Timing Control)**

- Waits until a condition is true before executing the next statement.
    
- Example:
    
    verilog
    
    `wait(count == 5); $display("Count reached 5!");`
    
- Used in simulation/testbenches to block execution until a condition is met.[](https://vlsiverify.com/verilog/procedural-timing-control/)
    

## 4. **Delay-based Timing Control (#)**

- Adds a time delay before executing a statement.
    
- Example:
    
    verilog
    
    `#10 a = b; // Wait 10 time units, then assign`
    
- Used mostly in testbenches and simulation, not in synthesizable hardware.[](https://vlsi.pro/verilog-timing-controls/)
    

## 5. **Named Event Control**

- You can declare and trigger custom events using the `event` keyword and `->` operator.
    
- Example:
    
    verilog
    
    `event my_event; always @(my_event) begin   // Runs when my_event is triggered end // Somewhere else: -> my_event;`
    
- Useful for advanced simulation control.
