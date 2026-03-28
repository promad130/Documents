
## 1. The "Powers of 2" Quick Reference

To find how many internal address lines ($A_0$ to $A_{n-1}$) a memory chip needs, look at its capacity in Bytes/Kilobytes and find the matching power of 2.

- $2^{10} = 1\text{ KB}$ $\rightarrow$ 10 Address Lines ($A_0 - A_9$)
    
- $2^{11} = 2\text{ KB}$ $\rightarrow$ 11 Address Lines ($A_0 - A_{10}$)
    
- $2^{12} = 4\text{ KB}$ $\rightarrow$ 12 Address Lines ($A_0 - A_{11}$)
    
- $2^{13} = 8\text{ KB}$ $\rightarrow$ 13 Address Lines ($A_0 - A_{12}$)
    
- $2^{14} = 16\text{ KB}$ $\rightarrow$ 14 Address Lines ($A_0 - A_{13}$)
    
- $2^{15} = 32\text{ KB}$ $\rightarrow$ 15 Address Lines ($A_0 - A_{14}$)
    
- $2^{16} = 64\text{ KB}$ $\rightarrow$ 16 Address Lines ($A_0 - A_{15}$)
    
- $2^{20} = 1\text{ MB}$ $\rightarrow$ 20 Address Lines (The full 8086 memory space)
    

## 2. The 74LS138 (3-to-8 Decoder) Truth Table

This chip takes a 3-bit binary input (C, B, A) and pulls exactly ONE of its eight outputs LOW (0).

**Enable Conditions (MUST be met to work):**

- $G_1$ must be HIGH (1)
    
- $\overline{G_{2A}}$ must be LOW (0)
    
- $\overline{G_{2B}}$ must be LOW (0)
    

|   |   |   |   |   |   |   |   |   |   |   |
|---|---|---|---|---|---|---|---|---|---|---|
|**C (MSB)**|**B**|**A (LSB)**|**Y0​​**|**Y1​​**|**Y2​​**|**Y3​​**|**Y4​​**|**Y5​​**|**Y6​​**|**Y7​​**|
|0|0|0|**0**|1|1|1|1|1|1|1|
|0|0|1|1|**0**|1|1|1|1|1|1|
|0|1|0|1|1|**0**|1|1|1|1|1|
|0|1|1|1|1|1|**0**|1|1|1|1|
|1|0|0|1|1|1|1|**0**|1|1|1|
|1|0|1|1|1|1|1|1|**0**|1|1|
|1|1|0|1|1|1|1|1|1|**0**|1|
|1|1|1|1|1|1|1|1|1|1|**0**|

_(Note: If the Enable conditions are NOT met, all outputs remain HIGH (1), and no memory chip wakes up)._

## 3. The 3-Step Address Range Calculation Method

Follow these exact steps to find the starting and ending addresses for any memory chip.

**Step 1: Separate the Address Bus**

- Determine the number of internal pins $n$ based on the chip size (Use the table in Section 1).
    
- These are lines $A_0$ through $A_{n-1}$. These lines will **toggle**.
    
- The remaining upper lines ($A_n$ through $A_{19}$) go to the Decoder. These lines are **frozen**.
    

**Step 2: Find the Base (Start) Address**

- Write out all 20 bits ($A_{19}$ down to $A_0$).
    
- Set the **frozen** decoder lines to whatever binary pattern wakes up the chip's $\overline{CS}$ pin.
    
- Set all the **toggling** internal lines ($A_0 - A_{n-1}$) to `0`.
    
- Convert the 20-bit binary number to a 5-digit Hexadecimal number.
    

**Step 3: Find the End Address**

- Keep the **frozen** decoder lines exactly the same.
    
- Set all the **toggling** internal lines ($A_0 - A_{n-1}$) to `1`.
    
- Convert the new 20-bit binary number to Hexadecimal.
    

## 4. Example: 32K RAM at Base Address `80000H`

**Step 1:** 32K = $2^{15}$. The chip needs 15 internal lines ($A_0$ to $A_{14}$).

The remaining 5 lines ($A_{15}$ to $A_{19}$) are for decoding.

**Step 2 (Start Address):** The problem states the base is `80000H` (`1000 0000 0000 0000 0000` in binary).

- Upper 5 bits (Decoder): `10000`
    
- Lower 15 bits (Internal): `000 0000 0000 0000`
    

**Step 3 (End Address):**

- Upper 5 bits (Decoder): `10000` (Remains frozen)
    
- Lower 15 bits (Internal): `111 1111 1111 1111` (Changed all to 1s)
    

**Final Hex Conversion for End Address:**

`1000 0111 1111 1111 1111`

Grouped by 4: `1000` `0111` `1111` `1111` `1111` = **`87FFFH`**

**Result:** This memory block resides from `80000H` to `87FFFH`.