# Assembly Language Data Types: Complete Deep Dive

Excellent question! Let's explore **data types and memory sizes** in x86 assembly in complete depth. Think of data types as different **container sizes** for your data, just like you have different sized boxes for different items.

## 1. Understanding Data Storage Fundamentals

Unlike high-level languages where `int` automatically means "32-bit integer," in assembly **you explicitly choose the storage size** for every piece of data. The CPU doesn't know if your bytes represent text, numbers, or addresses—**you decide how to interpret them**.[eng.libretexts+1](https://eng.libretexts.org/Bookshelves/Computer_Science/Programming_Languages/x86-64_Assembly_Language_Programming_with_Ubuntu_\(Jorgensen\)/02:_Architecture_Overview/2.02:_Data_Storage_Sizes)

## 2. Complete Data Size Reference

## Basic Storage Sizes (All Powers of 2)

|Size Name|Directive|Bits|Bytes|Range (Unsigned)|Range (Signed)|C Equivalent|
|---|---|---|---|---|---|---|
|**Byte**|`DB`|8|1|0 to 255|-128 to 127|`char`|
|**Word**|`DW`|16|2|0 to 65,535|-32,768 to 32,767|`short`|
|**Double Word**|`DD`|32|4|0 to 4,294,967,295|-2,147,483,648 to 2,147,483,647|`int`|
|**Quad Word**|`DQ`|64|8|0 to 18,446,744,073,709,551,615|-9,223,372,036,854,775,808 to 9,223,372,036,854,775,807|`long long`|
|**Ten Byte**|`DT`|80|10|Used for extended precision floats|Used for extended precision floats|N/A|

## Advanced Data Types

|Type|Directive|Purpose|Size|Example|
|---|---|---|---|---|
|**REAL4**|`DD`|32-bit floating point|4 bytes|`pi REAL4 3.14159`|
|**REAL8**|`DQ`|64-bit floating point|8 bytes|`bigNum REAL8 1.23E+100`|
|**REAL10**|`DT`|80-bit extended float|10 bytes|Used internally by x87 FPU|

## 3. Practical Examples: Declaring Variables

## Integer Data Types

text

`section .data ; 8-bit integers age         db  25          ; unsigned: 0-255 temperature sb  -10         ; signed: -128 to 127  ; 16-bit integers   year        dw  2025        ; unsigned: 0-65535 balance     sword -1500     ; signed: -32768 to 32767  ; 32-bit integers population  dd  50000000    ; unsigned: 0-4.2 billion salary      sdword -50000   ; signed: ±2.1 billion  ; 64-bit integers bigNumber   dq  9999999999999999999   ; massive numbers`

## Character and String Data

text

`section .data ; Single characters initial     db  'A'         ; stores ASCII value 65 newline     db  10          ; ASCII for \n  ; Strings (character arrays) name        db  'Hello',0   ; null-terminated string message     db  'Welcome to Assembly!',13,10,0 buffer      db  100 dup(0)  ; 100-byte buffer filled with zeros  ; Fixed-length strings username    db  'admin    ' ; exactly 9 characters (padded)`

## Floating Point Data Types[ftp.utcluj+1](https://ftp.utcluj.ro/pub/users/cemil/asm/CH05.PDF)

text

`section .data ; Single precision (32-bit) price       REAL4  19.95    ; ~7 decimal digits precision ratio       REAL4  0.25     ; 1.18×10⁻³⁸ to 3.40×10³⁸  ; Double precision (64-bit)   pi          REAL8  3.141592653589793  ; ~16 decimal digits scientific  REAL8  1.23E-45           ; 2.23×10⁻³⁰⁸ to 1.79×10³⁰⁸  ; Extended precision (80-bit) - x87 FPU only precise     REAL10 1.23456789012345678901`

## 4. Arrays and Multiple Values

## Creating Arrays

text

`section .data ; Integer arrays scores      dd  85, 92, 78, 95, 88    ; 5 integers grades      db  'A','B','C','D','F'  ; 5 characters matrix      dw  100 dup(0)            ; 100 words, all zero  ; Mixed data student     db  'John Doe',0          ; name string               dd  12345                 ; student ID             dw  85, 92, 78            ; three grades`

## Using TIMES for Repetition[tutorialspoint](https://www.tutorialspoint.com/assembly_programming/assembly_variables.htm)

text

`section .data buffer      times 1024 db 0    ; 1KB buffer of zeros   stars       times 50 db '*'    ; 50 asterisk characters`

## 5. Memory Layout and Endianness

**Important:** x86 uses **little-endian** byte order—the least significant byte comes first in memory.[csie.ntu+1](https://www.csie.ntu.edu.tw/~cyy/courses/assembly/12fall/lectures/handouts/lec13_x86Asm.pdf)

text

`section .data number      dd  0x12345678    ; stored as: 78 56 34 12  section .text mov eax, [number]             ; EAX = 0x12345678 (correct value) mov al, [number]              ; AL = 0x78 (lowest byte) mov ah, [number+1]            ; AH = 0x56 (second byte)`

## 6. Signed vs Unsigned: Same Storage, Different Interpretation[ibm+1](https://www.ibm.com/docs/en/aix/7.2?topic=types-signed-unsigned-integers)

The **same bit pattern** can represent different values depending on how you interpret it:

text

`section .data myByte      db  200           ; could be 200 (unsigned) or -56 (signed)  section .text ; Unsigned comparison cmp al, 150 ja  greater                   ; jump if ABOVE (unsigned)  ; Signed comparison   cmp al, -50 jg  greater                   ; jump if GREATER (signed)`

**Ranges for Common Sizes:**

- **8-bit:** 0-255 (unsigned) or -128 to 127 (signed)
    
- **16-bit:** 0-65,535 (unsigned) or -32,768 to 32,767 (signed)
    
- **32-bit:** 0-4,294,967,295 (unsigned) or -2,147,483,648 to 2,147,483,647 (signed)
    

## 7. Working with Different Data Sizes

## Loading Different Sized Data

text

`section .data byteVal     db  42 wordVal     dw  1234   dwordVal    dd  567890  section .text mov al, [byteVal]      ; load 8-bit value mov ax, [wordVal]      ; load 16-bit value   mov eax, [dwordVal]    ; load 32-bit value  ; Size specification when needed mov byte [buffer], 65     ; store single byte mov word [buffer], 1234   ; store 16-bit value mov dword [buffer], 56789 ; store 32-bit value`

## 8. Your Practice Exercise

**Given this data declaration:**

text

`section .data studentAge    db   19 studentGrade  dw   85   studentID     dd   12345 studentGPA    REAL4 3.75`

**Questions:**

1. How many total bytes does this data occupy in memory?
    
2. If `studentAge` is stored at memory address 1000, what addresses would the other variables occupy?
    
3. What would happen if you tried: `mov eax, [studentAge]`?
    

**Answers:**

1. **Total bytes:** 1 + 2 + 4 + 4 = **11 bytes**
    
2. **Addresses:** studentAge=1000, studentGrade=1001, studentID=1003, studentGPA=1007
    
3. **Loading 32-bit from 8-bit:** EAX would load the byte value plus 3 bytes of following data (likely part of studentGrade)
    

This is why **size matters** in assembly—you must match your instruction size to your data size, or explicitly specify the size you want to access.

Ready to move on to arithmetic operations and manipulating this data?

1. [https://eng.libretexts.org/Bookshelves/Computer_Science/Programming_Languages/x86-64_Assembly_Language_Programming_with_Ubuntu_(Jorgensen)/02:_Architecture_Overview/2.02:_Data_Storage_Sizes](https://eng.libretexts.org/Bookshelves/Computer_Science/Programming_Languages/x86-64_Assembly_Language_Programming_with_Ubuntu_\(Jorgensen\)/02:_Architecture_Overview/2.02:_Data_Storage_Sizes)
2. [https://www.tutorialspoint.com/assembly_programming/assembly_variables.htm](https://www.tutorialspoint.com/assembly_programming/assembly_variables.htm)
3. [https://ftp.utcluj.ro/pub/users/cemil/asm/CH05.PDF](https://ftp.utcluj.ro/pub/users/cemil/asm/CH05.PDF)
4. [https://www.csee.umbc.edu/courses/undergraduate/313/fall07/burt/CMSC313_lectures/Floating%20Point/floatingPoint.html](https://www.csee.umbc.edu/courses/undergraduate/313/fall07/burt/CMSC313_lectures/Floating%20Point/floatingPoint.html)
5. [https://www.csie.ntu.edu.tw/~cyy/courses/assembly/12fall/lectures/handouts/lec13_x86Asm.pdf](https://www.csie.ntu.edu.tw/~cyy/courses/assembly/12fall/lectures/handouts/lec13_x86Asm.pdf)
6. [https://www.ibm.com/docs/en/aix/7.2?topic=types-signed-unsigned-integers](https://www.ibm.com/docs/en/aix/7.2?topic=types-signed-unsigned-integers)
7. [https://stackoverflow.com/questions/11205522/identifying-signed-and-unsigned-values-in-assembly](https://stackoverflow.com/questions/11205522/identifying-signed-and-unsigned-values-in-assembly)
8. [https://cuitutorial.com/courses/microprocessor-and-assembly-language/lessons/data-types-in-assembly-byte-word-and-dword/](https://cuitutorial.com/courses/microprocessor-and-assembly-language/lessons/data-types-in-assembly-byte-word-and-dword/)
9. [https://stackoverflow.com/questions/32301650/assembly-data-types-limits-and-examples](https://stackoverflow.com/questions/32301650/assembly-data-types-limits-and-examples)
10. [https://stackoverflow.com/questions/10168743/which-variable-size-to-use-db-dw-dd-with-x86-assembly](https://stackoverflow.com/questions/10168743/which-variable-size-to-use-db-dw-dd-with-x86-assembly)
11. [https://duetorun.com/blog/20211010/x86-word/](https://duetorun.com/blog/20211010/x86-word/)
12. [https://www.youtube.com/watch?v=Ac6yIMz94aA](https://www.youtube.com/watch?v=Ac6yIMz94aA)
13. [https://electronicsdesk.com/assembler-directives.html](https://electronicsdesk.com/assembler-directives.html)
14. [https://www.gamedev.net/forums/topic/479177-need-understanding-of-dword/4139542/](https://www.gamedev.net/forums/topic/479177-need-understanding-of-dword/4139542/)
15. [https://en.wikibooks.org/wiki/X86_Assembly/Intrinsic_Data_Types](https://en.wikibooks.org/wiki/X86_Assembly/Intrinsic_Data_Types)
16. [http://www.cs.emory.edu/~cheung/Courses/255/Syllabus/9-Intel/cs255/asm-directives.html](http://www.cs.emory.edu/~cheung/Courses/255/Syllabus/9-Intel/cs255/asm-directives.html)
17. [https://stackoverflow.com/questions/55430725/whats-the-size-of-a-qword-on-a-64-bit-machine](https://stackoverflow.com/questions/55430725/whats-the-size-of-a-qword-on-a-64-bit-machine)
18. [https://en.wikipedia.org/wiki/X86](https://en.wikipedia.org/wiki/X86)
19. [https://courses.cs.umbc.edu/undergraduate/313/fall04/burt_katz/lectures/Lect03/datatypes.html](https://courses.cs.umbc.edu/undergraduate/313/fall04/burt_katz/lectures/Lect03/datatypes.html)
20. [https://en.wikipedia.org/wiki/Word_(computer_architecture)](https://en.wikipedia.org/wiki/Word_\(computer_architecture\))
21. [https://www.cs.virginia.edu/~evans/cs216/guides/x86.html](https://www.cs.virginia.edu/~evans/cs216/guides/x86.html)
22. [https://www.cs.uaf.edu/2010/fall/cs301/lecture/10_01_memory.html](https://www.cs.uaf.edu/2010/fall/cs301/lecture/10_01_memory.html)
23. [https://www.csie.ntu.edu.tw/~cyy/courses/assembly/12fall/lectures/handouts/lec13_x86Asm_4up.pdf](https://www.csie.ntu.edu.tw/~cyy/courses/assembly/12fall/lectures/handouts/lec13_x86Asm_4up.pdf)
24. [https://www.cse.iitd.ac.in/~srsarangi/archbook/chapters/x86.pdf](https://www.cse.iitd.ac.in/~srsarangi/archbook/chapters/x86.pdf)
25. [https://www.scribd.com/document/300586650/Assembler-Directives](https://www.scribd.com/document/300586650/Assembler-Directives)
26. [https://www.tutorialspoint.com/assembly_programming/assembly_strings.htm](https://www.tutorialspoint.com/assembly_programming/assembly_strings.htm)
27. [https://azeria-labs.com/arm-data-types-and-registers-part-2/](https://azeria-labs.com/arm-data-types-and-registers-part-2/)
28. [https://www.plantation-productions.com/Webster/www.artofasm.com/DOS/ch05/CH05-1.html](https://www.plantation-productions.com/Webster/www.artofasm.com/DOS/ch05/CH05-1.html)
29. [https://en.wikipedia.org/wiki/X86_assembly_language](https://en.wikipedia.org/wiki/X86_assembly_language)
30. [https://learn.microsoft.com/en-us/cpp/cpp/data-type-ranges?view=msvc-170](https://learn.microsoft.com/en-us/cpp/cpp/data-type-ranges?view=msvc-170)
31. [https://groups.google.com/g/comp.lang.clipper.visual-objects/c/tpXtfVRXQ4M](https://groups.google.com/g/comp.lang.clipper.visual-objects/c/tpXtfVRXQ4M)
32. [https://stackoverflow.com/questions/47957551/masm-assembly-real4-float-instructions](https://stackoverflow.com/questions/47957551/masm-assembly-real4-float-instructions)
33. [https://en.wikipedia.org/wiki/String_(computer_science)](https://en.wikipedia.org/wiki/String_\(computer_science\))
34. [https://www.sciencedirect.com/topics/computer-science/unsigned-integer](https://www.sciencedirect.com/topics/computer-science/unsigned-integer)
35. [https://stackoverflow.com/questions/11663745/compare-arrays-of-characters-in-x86-assembly](https://stackoverflow.com/questions/11663745/compare-arrays-of-characters-in-x86-assembly)
36. [https://board.flatassembler.net/topic.php?t=20573](https://board.flatassembler.net/topic.php?t=20573)
37. [https://en.wikibooks.org/wiki/X86_Assembly/Floating_Point](https://en.wikibooks.org/wiki/X86_Assembly/Floating_Point)
38. [https://www.youtube.com/watch?v=9A2B2lpBzbg](https://www.youtube.com/watch?v=9A2B2lpBzbg)