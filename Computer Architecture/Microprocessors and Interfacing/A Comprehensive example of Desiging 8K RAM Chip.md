### **The Problem**

Design an address decoder for an **8K RAM** chip starting at address **$1\text{E}000\text{H}$** in an 8086 system.

---

### **Step 1: Calculate Internal vs. External Lines**

First, determine how many address pins the RAM chip actually has.

- **Size**: $8\text{K} = 8,192 \text{ bytes}$.
    
- **Math**: $2^{13} = 8,192$.
    
- **Internal Lines ($A_0 - A_{12}$)**: These 13 pins connect directly from the CPU to the RAM chip. They are used to pick a specific byte _inside_ the chip.
    
- **Decoding Lines ($A_{13} - A_{19}$)**: The remaining 7 lines are used for the decoder to decide _when_ to wake the chip up.
    

---

### **Step 2: Map the Hex Range to Binary**

We need to see what the address lines look like when the CPU is talking to this range.

- **Start Address ($1\text{E}000\text{H}$)**: `0001 1110 0000 0000 0000`.
    
- **End Address**: Since the chip is 8K ($1\text{FFFH}$ wide), the range ends at **$1\text{FFFFH}$** ($1\text{E}000 + 1\text{FFF}$).
    
- **End Binary**: `0001 1111 1111 1111 1111`.
    

|**Address Lines**|**A19​**|**A18​**|**A17​**|**A16​**|**A15​**|**A14​**|**A13​**|**A12​…A0​**|
|---|---|---|---|---|---|---|---|---|
|**Binary**|**0**|**0**|**0**|**1**|**1**|**1**|**1**|(Variable)|

---

### **Step 3: Design the Decoding Logic**

**The Point**: A NAND gate only outputs a **0** (to trigger the active-low $\overline{CS}$ pin) when **all** its inputs are **1**.

Looking at our table above, we have a problem: $A_{19}, A_{18},$ and $A_{17}$ are **0s**. If we plug them into a NAND gate as-is, the gate will never output a 0, and the chip will never wake up.

**The Fix (The Inverter)**:

We pass every "0" line through a **NOT gate** before it hits the NAND gate. This flips the 0 to a 1.

1. **NOT** $A_{19} \rightarrow 1$
    
2. **NOT** $A_{18} \rightarrow 1$
    
3. **NOT** $A_{17} \rightarrow 1$
    
4. $A_{16}, A_{15}, A_{14}, A_{13}$ are already 1s.
    

---

### **Step 4: Final Circuit Connections**

To build this, you wire the components as follows:

- **CPU Pins $A_0 - A_{12}$**: Wired directly to **RAM Pins $A_0 - A_{12}$**.
    
- **CPU Pins $A_{13}, A_{14}, A_{15}, A_{16}$**: Wired directly to the **NAND Gate inputs**.
    
- **CPU Pins $A_{17}, A_{18}, A_{19}$**: Wired to **Inverters**, then to the **NAND Gate inputs**.
    
- **NAND Output**: Wired to the RAM's **$\overline{CS}$ (Chip Select)** pin.
    
- **Control Signals**: CPU $\overline{RD}$ goes to RAM $\overline{OE}$; CPU $\overline{WR}$ goes to RAM $\overline{WE}$ .
    

---

### **Quick Terminology Check**

- **NOT Gate (Inverter)**: Used to "flip" a bit. It’s mandatory if your target address range has a 0 in the decoding bits.
    
- **Fixed Bits**: The high-order bits ($A_{19}-A_{13}$) that don't change throughout the entire 8K range. These define the "neighborhood" of the chip.
    
- **Variable Bits**: The low-order bits ($A_{12}-A_0$) that change as the CPU reads different bytes _inside_ the chip.
    
- **Memory Map**: The final spreadsheet showing exactly which Hex values trigger which physical chips.
    
