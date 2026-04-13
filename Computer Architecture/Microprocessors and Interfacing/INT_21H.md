# INT 21H Complete Reference Guide for 8086 Assembly

## Table of Contents
1. [Introduction](#introduction)
2. [Complete INT 21H Function Reference](#complete-int-21h-function-reference)
3. [Input/Output Functions](#inputoutput-functions)
4. [File Operations (FCB-based)](#file-operations-fcb-based)
5. [File Operations (Handle-based)](#file-operations-handle-based)
6. [Directory and Path Functions](#directory-and-path-functions)
7. [System Information Functions](#system-information-functions)
8. [Memory Management Functions](#memory-management-functions)
9. [Process Control Functions](#process-control-functions)
10. [Detailed Examples with Explanations](#detailed-examples-with-explanations)

---

## Introduction

**INT 21H** is the primary DOS interrupt for interacting with the operating system. It provides services for:
- Input/Output (keyboard, screen, printer, serial port)
- File operations (open, close, read, write, delete)
- Directory management
- Memory allocation
- Process control
- System information retrieval

Before calling `INT 21H`, set the **AH register** to the function number. Other registers may hold additional parameters depending on the function.

---

## Complete INT 21H Function Reference

### Quick Lookup Table

| AH Value | Function Name | Category |
|----------|---------------|----------|
| 00h | Terminate Program | Process Control |
| 01h | Read Character (with echo) | I/O |
| 02h | Write Character | I/O |
| 03h | Read from Auxiliary | I/O |
| 04h | Write to Auxiliary | I/O |
| 05h | Write to Printer | I/O |
| 06h | Direct Console I/O | I/O |
| 07h | Read Character (no echo) | I/O |
| 08h | Read Character (raw, no Ctrl-C) | I/O |
| 09h | Write String | I/O |
| 0Ah | Buffered Keyboard Input | I/O |
| 0Bh | Check Keyboard Status | I/O |
| 0Ch | Clear Buffer + Read Input | I/O |
| 0Dh | Disk Reset | Disk |
| 0Eh | Select Default Drive | Disk |
| 0Fh | Open File (FCB) | File Operations |
| 10h | Close File (FCB) | File Operations |
| 11h | Find First File (FCB) | File Operations |
| 12h | Find Next File (FCB) | File Operations |
| 13h | Delete File (FCB) | File Operations |
| 14h | Sequential Read (FCB) | File Operations |
| 15h | Sequential Write (FCB) | File Operations |
| 16h | Create File (FCB) | File Operations |
| 17h | Rename File (FCB) | File Operations |
| 19h | Get Default Drive | Disk |
| 1Ah | Set DTA Address | Disk |
| 1Bh | Get Default Drive Info | Disk |
| 1Ch | Get Drive Info | Disk |
| 21h | Random Read (FCB) | File Operations |
| 22h | Random Write (FCB) | File Operations |
| 23h | Get File Size (FCB) | File Operations |
| 24h | Set Random Record (FCB) | File Operations |
| 25h | Set Interrupt Vector | System |
| 26h | Create New PSP | Process Control |
| 27h | Random Block Read (FCB) | File Operations |
| 28h | Random Block Write (FCB) | File Operations |
| 29h | Parse Filename | File Operations |
| 2Ah | Get System Date | System |
| 2Bh | Set System Date | System |
| 2Ch | Get System Time | System |
| 2Dh | Set System Time | System |
| 2Eh | Set Verify Switch | Disk |
| 2Fh | Get DTA Address | Disk |
| 30h | Get DOS Version | System |
| 31h | Terminate and Stay Resident | Process Control |
| 33h | Get/Set Ctrl-Break Flag | System |
| 35h | Get Interrupt Vector | System |
| 36h | Get Free Disk Space | Disk |
| 37h | Get/Set Switch Character | System |
| 38h | Get Country Info | System |
| 39h | Create Directory | Directory |
| 3Ah | Remove Directory | Directory |
| 3Bh | Change Directory | Directory |
| 3Ch | Create File (handle) | File Operations |
| 3Dh | Open File (handle) | File Operations |
| 3Eh | Close File (handle) | File Operations |
| 3Fh | Read File (handle) | File Operations |
| 40h | Write File (handle) | File Operations |
| 41h | Delete File | File Operations |
| 42h | Move File Pointer (handle) | File Operations |
| 43h | Get/Set File Attributes | File Operations |
| 44h | I/O Control (IOCTL) | Device |
| 45h | Duplicate File Handle | File Operations |
| 46h | Redirect File Handle | File Operations |
| 47h | Get Current Directory | Directory |
| 48h | Allocate Memory | Memory |
| 49h | Free Memory | Memory |
| 4Ah | Modify Allocated Block | Memory |
| 4Bh | Execute Program | Process Control |
| 4Ch | Terminate Process | Process Control |
| 4Dh | Get Return Code | Process Control |
| 4Eh | Find First File | File Operations |
| 4Fh | Find Next File | File Operations |
| 50h | Set Current PSP | Process Control |
| 51h | Get Current PSP | Process Control |
| 54h | Get Verify Status | Disk |
| 56h | Rename File | File Operations |
| 57h | Get/Set File Date & Time | File Operations |
| 59h | Get Extended Error Info | Error |
| 5Ah | Create Temporary File | File Operations |
| 5Bh | Create New File | File Operations |
| 5Ch | Lock/Unlock File | File Operations |

---

## Input/Output Functions

### AH = 01h: Read Character from Standard Input (with echo)

**Purpose:** Read a single character from keyboard and display it on screen.

**Registers:**
- **Input:** AH = 01h
- **Output:** AL = ASCII code of character read
- **Flags:** ZF set if Ctrl-Z (EOF) detected

**Details:**
- Echoes the character to the screen
- Recognizes Ctrl-C for user interrupt
- Waits until a key is pressed
- Recognizes backspace (08h) and allows editing

**Example Code:**
```asm
mov ah, 01h        ; Function 01h - Read character
int 21h            ; Call DOS
                   ; AL now contains the ASCII code
cmp al, 27         ; Compare with ESC key (ASCII 27)
je exit_program    ; If ESC, exit
mov bl, al         ; Save character for later use
```

---

### AH = 02h: Write Character to Standard Output

**Purpose:** Write a single character to the screen.

**Registers:**
- **Input:** AH = 02h, DL = ASCII code of character to write
- **Output:** Character displayed on screen
- **Flags:** None modified

**Details:**
- Writes directly to screen (typically at current cursor position)
- Does not recognize special characters except newline (0Ah)
- For newline, must call twice: once with 0Dh (CR), once with 0Ah (LF)
- Fastest character output for direct screen writing

**Example Code:**
```asm
mov ah, 02h        ; Function 02h - Write character
mov dl, 'A'        ; DL = ASCII code of 'A' (65)
int 21h            ; Display 'A' on screen

; Write newline
mov dl, 0Dh        ; CR (carriage return)
int 21h
mov dl, 0Ah        ; LF (line feed)
int 21h
```

---

### AH = 06h: Direct Console I/O

**Purpose:** Read or write character without DOS processing.

**Registers:**
- **Input:** AH = 06h, DL = FFh (for input) or character (for output)
- **Output:** AL = character (if input), ZF set if no input

**Details:**
- For output: DL contains character to write
- For input: Set DL = FFh; returns character in AL if available
- Non-blocking input (returns immediately if no key pressed)
- Does not echo input or process Ctrl-C

**Example Code:**
```asm
; Check if key is pressed without waiting
mov ah, 06h        ; Direct console I/O
mov dl, 0FFh       ; FFh means read input
int 21h
jz no_key          ; If ZF set, no key was pressed
; AL contains the character
mov bl, al
no_key:
```

---

### AH = 07h: Read Character from Standard Input (no echo)

**Purpose:** Read a single character from keyboard without displaying it.

**Registers:**
- **Input:** AH = 07h
- **Output:** AL = ASCII code of character read
- **Flags:** None significant

**Details:**
- Character is NOT echoed to screen
- Recognizes Ctrl-C for user interrupt
- Waits until a key is pressed
- Useful for password input or silent commands

**Example Code:**
```asm
mov ah, 07h        ; Function 07h - Read without echo
int 21h
cmp al, 0Dh        ; Compare with ENTER (ASCII 13)
je confirm         ; If ENTER pressed, continue
mov ah, 02h        ; Otherwise, display feedback
mov dl, '?'        ; Show that input was received
int 21h
```

---

### AH = 08h: Read Character (raw, no Ctrl-C check)

**Purpose:** Read a character without Ctrl-C checking or echo.

**Registers:**
- **Input:** AH = 08h
- **Output:** AL = ASCII code of character read
- **Flags:** ZF set if Ctrl-Z detected

**Details:**
- Does not echo character to screen
- Ctrl-C is not recognized (safe for raw input)
- Useful for reading control characters
- Waits for input (blocking call)

**Example Code:**
```asm
mov ah, 08h        ; Function 08h - Raw character input
int 21h            ; Read without interrupt checking
; AL contains character even if it's a control code
```

---

### AH = 09h: Write String to Standard Output

**Purpose:** Display a string on the screen.

**Registers:**
- **Input:** AH = 09h, DS:DX = address of string
- **Output:** String displayed on screen
- **Flags:** None modified

**Details:**
- String must be terminated with '$' (24h)
- The '$' is NOT displayed
- Recognizes newline characters (0Dh, 0Ah)
- Most efficient for writing strings (no loop needed)
- Cannot write binary strings containing '$'

**Example Code:**
```asm
.data
message db "Hello, World!", 0Dh, 0Ah, "$"

.code
mov ah, 09h        ; Function 09h - Write string
lea dx, [message]  ; DX = address of string
int 21h            ; Display string
```

**Algorithm:**
```
1. Load string address into DX
2. Each character pointed to by DX is processed
3. Continue until '$' (24h) is encountered
4. Stop processing; do NOT display '$'
```

---

### AH = 0Ah: Buffered Keyboard Input

**Purpose:** Read a string from keyboard into a buffer with automatic line editing.

**Registers:**
- **Input:** AH = 0Ah, DS:DX = address of input buffer
- **Output:** Buffer filled with input string
- **Flags:** None

**Details:**
- Buffer must be set up with max length in first byte
- Actual characters read stored starting at byte 2
- Supports backspace, delete, and other editing keys
- Recognizes Ctrl-C for interrupt
- Terminates on ENTER key

**Buffer Structure:**
```
Offset  Length  Purpose
0       1 byte  Maximum number of characters to read
1       1 byte  Actual number of characters read (set by DOS)
2+      N bytes Input string (starts here)
```

**Example Code:**
```asm
.data
input_buffer db 20, 0, 20 dup(0)  ; Max 20 chars, 0 read initially

.code
mov ah, 0Ah        ; Function 0Ah - Buffered input
lea dx, [input_buffer]
int 21h            ; Read string with editing

; Now input_buffer[1] contains actual chars read
mov cl, input_buffer[1]  ; CL = number of chars
mov ch, 0          ; Prepare for loop
```

**Algorithm:**
```
1. Set up buffer with maximum length at offset 0
2. Call INT 21H with buffer address in DX
3. User types characters (supports editing keys)
4. User presses ENTER
5. DOS stores actual count at buffer offset 1
6. Process string starting at buffer offset 2
```

---

### AH = 0Bh: Check Keyboard Status

**Purpose:** Check if a key has been pressed without waiting.

**Registers:**
- **Input:** AH = 0Bh
- **Output:** AL = FFh (key available) or 00h (no key)
- **Flags:** ZF set if no key available

**Details:**
- Non-blocking check
- Returns immediately
- Does not consume the keystroke (key still in buffer)
- Useful for polling in loops

**Example Code:**
```asm
check_input:
mov ah, 0Bh        ; Function 0Bh - Check keyboard status
int 21h
cmp al, 0FFh       ; AL = FFh if key pressed
jne no_input       ; If no key, continue
; Key is available, read it
mov ah, 01h
int 21h
no_input:
```

---

### AH = 0Ch: Clear Input Buffer and Read Character

**Purpose:** Clear any pending input, then read a new character.

**Registers:**
- **Input:** AH = 0Ch, AL = function code (01h, 07h, or 0Ah)
- **Output:** Depends on AL function code
- **Flags:** Varies

**Details:**
- AL determines what kind of read operation follows
- 01h = read with echo
- 07h = read without echo
- 0Ah = buffered input
- Useful to clear leftover keystrokes before fresh input

**Example Code:**
```asm
mov ah, 0Ch        ; Function 0Ch - Clear and read
mov al, 01h        ; Use function 01h (read with echo)
int 21h
; Buffer is cleared; character read is in AL
```

---

### AH = 05h: Write Character to Printer

**Purpose:** Send a character to the printer device.

**Registers:**
- **Input:** AH = 05h, DL = ASCII code of character
- **Output:** Character sent to printer
- **Flags:** None

**Details:**
- Sends character to parallel printer port
- Does not check printer status
- Character is queued for printing
- Useful in batch operations for printing multiple characters

**Example Code:**
```asm
mov ah, 05h        ; Function 05h - Write to printer
mov dl, 'P'        ; Send 'P' to printer
int 21h
```

---

### AH = 03h / 04h: Auxiliary I/O (Serial Port)

**Purpose:** Read from (03h) or write to (04h) serial port (COM1/COM2).

**Registers (03h - Read):**
- **Input:** AH = 03h
- **Output:** AL = byte read from serial port
- **Flags:** None

**Registers (04h - Write):**
- **Input:** AH = 04h, DL = byte to send
- **Output:** Byte sent to serial port
- **Flags:** None

**Details:**
- Legacy function; BIOS serial I/O more common today
- Blocking operations (waits for data)
- No error checking
- Timing depends on baud rate (set externally)

**Example Code:**
```asm
; Read from serial port
mov ah, 03h
int 21h            ; AL now contains received byte

; Write to serial port
mov ah, 04h
mov dl, 41h        ; ASCII 'A'
int 21h
```

---

## File Operations (FCB-based)

> **Note:** FCB (File Control Block) operations are DEPRECATED. Use handle-based operations (3Ch-5Ch) for modern code. FCB functions are included for historical completeness.

### AH = 0Fh: Open File (FCB)

**Purpose:** Open an existing file using File Control Block.

**Registers:**
- **Input:** AH = 0Fh, DS:DX = address of FCB
- **Output:** AL = 00h (success) or FFh (failure)

**FCB Structure:**
```
Offset  Size  Purpose
0       1     Drive number (0=default, 1=A:, 2=B:, etc.)
1       8     Filename (left-padded with spaces)
9       3     Extension (left-padded with spaces)
12      2     Current block
14      2     Record size
16      4     File size
20      2     Date created
22      2     Time created
24      8     Reserved
```

**Example Code:**
```asm
.data
fcb db 0           ; Drive: 0 = default
    db "TEST    "  ; Filename padded to 8 bytes
    db "TXT"       ; Extension
    db 0, 0        ; Current block = 0
    db 0, 0        ; Record size = 0 (use default 128)
    db 0, 0, 0, 0  ; File size
    db 0, 0        ; Date
    db 0, 0        ; Time
    db 0, 0, 0, 0, 0, 0, 0, 0  ; Reserved

.code
mov ah, 0Fh        ; Function 0Fh - Open file
lea dx, [fcb]
int 21h
cmp al, 00h        ; AL = 0 for success
je file_open_ok
; Handle error
file_open_ok:
```

---

### AH = 3Ch: Create or Truncate File (handle-based)

**Purpose:** Create a new file or truncate an existing file.

**Registers:**
- **Input:** AH = 3Ch, DS:DX = filename, CX = file attributes
- **Output:** AX = file handle (if success) or error code (if CF set)
- **Flags:** CF set on error

**File Attributes (CX):**
- Bit 0: Read-only
- Bit 1: Hidden
- Bit 2: System
- Bit 3: Volume label
- Bit 4: Directory
- Bit 5: Archive
- Other bits: Reserved (0)

**Example Code:**
```asm
.data
filename db "output.txt", 0

.code
mov ah, 3Ch        ; Function 3Ch - Create file
lea dx, [filename]
xor cx, cx         ; CX = 0 (normal file, archive)
int 21h
jc create_error    ; If CF set, creation failed
mov file_handle, ax ; Save file handle
create_error:
```

**Algorithm:**
```
1. Set AH = 3Ch
2. Load filename address into DX
3. Set CX to desired file attributes
4. Call INT 21H
5. If CF = 0: AX contains valid file handle (0-255)
6. If CF = 1: AX contains error code
   - 3 = Path not found
   - 4 = Too many open files
   - 5 = Access denied
```

---

### AH = 3Dh: Open File (handle-based)

**Purpose:** Open an existing file and return a file handle.

**Registers:**
- **Input:** AH = 3Dh, DS:DX = filename, AL = access mode
- **Output:** AX = file handle (if success) or error code (if CF set)
- **Flags:** CF set on error

**Access Modes (AL):**
- 0 = Read-only
- 1 = Write-only
- 2 = Read/Write

**Example Code:**
```asm
.data
filename db "input.txt", 0

.code
mov ah, 3Dh        ; Function 3Dh - Open file
lea dx, [filename]
mov al, 0          ; AL = 0 (read-only)
int 21h
jc open_error      ; If CF set, open failed
mov input_handle, ax ; Save file handle
open_error:
```

---

### AH = 3Eh: Close File (handle-based)

**Purpose:** Close an open file.

**Registers:**
- **Input:** AH = 3Eh, BX = file handle
- **Output:** AX = error code (if CF set)
- **Flags:** CF set on error

**Details:**
- Must be called for each open file before program termination
- Flushes any pending writes
- Releases the file handle
- Failure indicates invalid handle

**Example Code:**
```asm
mov ah, 3Eh        ; Function 3Eh - Close file
mov bx, input_handle
int 21h
jc close_error     ; If CF set, close failed
close_error:
```

---

### AH = 3Fh: Read from File (handle-based)

**Purpose:** Read data from an open file.

**Registers:**
- **Input:** AH = 3Fh, BX = file handle, CX = bytes to read, DS:DX = buffer address
- **Output:** AX = bytes actually read (0 if EOF)
- **Flags:** CF set on error

**Details:**
- Reads up to CX bytes into buffer
- AX = actual bytes read (may be less than CX at EOF)
- Advances file pointer automatically
- Buffer must be large enough

**Example Code:**
```asm
.data
buffer db 256 dup(0)
bytes_read dw 0

.code
mov ah, 3Fh        ; Function 3Fh - Read file
mov bx, input_handle
mov cx, 256        ; Read 256 bytes
lea dx, [buffer]
int 21h
jc read_error      ; If CF set, read error
mov bytes_read, ax ; AX = actual bytes read
read_error:
```

**Algorithm:**
```
1. Set up buffer in memory with sufficient size
2. Set AH = 3Fh
3. Set BX = valid file handle
4. Set CX = maximum bytes to read
5. Set DX = buffer address
6. Call INT 21H
7. If CF = 0:
   - AX = bytes actually read
   - If AX = 0, EOF reached
   - If AX < CX, partial read (likely EOF)
8. If CF = 1:
   - AX = error code
```

---

### AH = 40h: Write to File (handle-based)

**Purpose:** Write data to an open file.

**Registers:**
- **Input:** AH = 40h, BX = file handle, CX = bytes to write, DS:DX = buffer address
- **Output:** AX = bytes actually written
- **Flags:** CF set on error

**Details:**
- Writes CX bytes from buffer to file
- AX = actual bytes written (should equal CX if successful)
- Advances file pointer automatically
- Disk full error possible if insufficient space

**Example Code:**
```asm
.data
message db "Hello, File!", 0Dh, 0Ah
msg_length equ $ - message

.code
mov ah, 40h        ; Function 40h - Write file
mov bx, output_handle
mov cx, msg_length
lea dx, [message]
int 21h
jc write_error     ; If CF set, write error
cmp ax, cx         ; AX = bytes written
jne partial_write  ; If AX != CX, partial write
write_error:
```

**Algorithm:**
```
1. Prepare data buffer to write
2. Set AH = 40h
3. Set BX = valid file handle
4. Set CX = number of bytes to write
5. Set DX = buffer address
6. Call INT 21H
7. If CF = 0:
   - AX = bytes successfully written
   - If AX = CX, all bytes written
   - If AX < CX, partial write (disk may be full)
8. If CF = 1:
   - AX = error code (5 = access denied, etc.)
```

---

### AH = 42h: Move File Pointer (handle-based)

**Purpose:** Change position in file for next read/write.

**Registers:**
- **Input:** AH = 42h, BX = file handle, CX:DX = offset, AL = method
- **Output:** DX:AX = new file position
- **Flags:** CF set on error

**Seek Methods (AL):**
- 0 = Absolute (from start of file)
- 1 = Relative (from current position)
- 2 = Relative to end of file

**Details:**
- CX:DX is a 32-bit offset (CX = high word, DX = low word)
- Method 2 allows negative offsets (seek backwards from EOF)
- Returns new file position for verification

**Example Code:**
```asm
; Seek to byte 100 from start of file
mov ah, 42h        ; Function 42h - Seek
mov bx, file_handle
mov al, 0          ; Method 0 = absolute
mov cx, 0          ; CX:DX = 100
mov dx, 100
int 21h
jc seek_error      ; If CF set, error
; DX:AX = new position

; Seek back 10 bytes from current position
mov ah, 42h
mov bx, file_handle
mov al, 1          ; Method 1 = relative
mov cx, 0FFFFh     ; CX:DX = -10
mov dx, 0FFF6h
int 21h
seek_error:
```

**Algorithm:**
```
1. Set AH = 42h
2. Set BX = file handle
3. Set AL = seek method (0, 1, or 2)
4. Set CX:DX = offset value
5. Call INT 21H
6. If CF = 0:
   - DX:AX = new file position (DX=high, AX=low)
7. If CF = 1:
   - Error occurred (invalid handle, invalid method, etc.)
```

---

### AH = 41h: Delete File

**Purpose:** Delete a file from disk.

**Registers:**
- **Input:** AH = 41h, DS:DX = filename
- **Output:** AX = error code (if CF set)
- **Flags:** CF set on error

**Details:**
- File must not be open
- Cannot delete read-only files without changing attributes first
- Path must exist

**Example Code:**
```asm
.data
filename db "oldfile.txt", 0

.code
mov ah, 41h        ; Function 41h - Delete file
lea dx, [filename]
int 21h
jc delete_error    ; If CF set, deletion failed
delete_error:
```

---

### AH = 43h: Get/Set File Attributes

**Purpose:** Read or modify file attribute flags (read-only, hidden, system, etc.).

**Registers:**
- **Input:** AH = 43h, DS:DX = filename, AL = function (0=read, 1=set), CX = attributes (if setting)
- **Output:** CX = attributes (if reading)
- **Flags:** CF set on error

**Attributes (CX):**
- Bit 0: Read-only (1)
- Bit 1: Hidden (2)
- Bit 2: System (4)
- Bit 3: Volume label (8)
- Bit 4: Directory (16)
- Bit 5: Archive (32)

**Example Code:**
```asm
.data
filename db "readme.txt", 0

.code
; Read attributes
mov ah, 43h        ; Function 43h
lea dx, [filename]
mov al, 0          ; AL = 0 (read)
int 21h
jc error           ; If CF set, error
; CX now contains attributes

; Set read-only
mov ah, 43h
lea dx, [filename]
mov al, 1          ; AL = 1 (set)
mov cx, 1          ; CX = 1 (read-only)
int 21h
error:
```

---

### AH = 56h: Rename File

**Purpose:** Change a file's name.

**Registers:**
- **Input:** AH = 56h, DS:DX = old filename, ES:DI = new filename
- **Output:** AX = error code (if CF set)
- **Flags:** CF set on error

**Details:**
- Both filenames must be fully qualified paths if needed
- File must not be open
- Destination must not already exist
- Can move between directories on same drive

**Example Code:**
```asm
.data
oldname db "oldname.txt", 0
newname db "newname.txt", 0

.code
mov ah, 56h        ; Function 56h - Rename
lea dx, [oldname]
lea di, [newname]
int 21h
jc rename_error    ; If CF set, rename failed
rename_error:
```

---

### AH = 57h: Get/Set File Date and Time

**Purpose:** Read or modify a file's creation/modification date and time.

**Registers (Get - AL=0):**
- **Input:** AH = 57h, AL = 0, BX = file handle
- **Output:** CX = time, DX = date

**Registers (Set - AL=1):**
- **Input:** AH = 57h, AL = 1, BX = file handle, CX = time, DX = date
- **Output:** None significant

**Time Format (CX):**
- Bits 0-4: Seconds (0-29, divide by 2)
- Bits 5-10: Minutes (0-59)
- Bits 11-15: Hours (0-23)

**Date Format (DX):**
- Bits 0-4: Day (1-31)
- Bits 5-8: Month (1-12)
- Bits 9-15: Year (years since 1980)

**Example Code:**
```asm
.code
; Get file time/date
mov ah, 57h        ; Function 57h
mov al, 0          ; AL = 0 (get)
mov bx, file_handle
int 21h
; CX = time, DX = date

; Extract values
mov al, cl         ; AL = low byte of time
and al, 0x1F       ; Bits 0-4 = seconds
shl al, 1          ; Multiply by 2

mov al, ch         ; AL = high byte of time
shr al, 3          ; Shift to get hours
```

---

## File Operations (Handle-based) - Examples Summary

| AH | Function | Purpose |
|----|----------|---------|
| 3Ch | Create File | Create new or truncate existing |
| 3Dh | Open File | Open existing file |
| 3Eh | Close File | Close open file |
| 3Fh | Read File | Read data |
| 40h | Write File | Write data |
| 42h | Seek | Move file pointer |
| 41h | Delete | Delete file |
| 43h | Attributes | Get/set file attributes |
| 56h | Rename | Rename file |
| 57h | DateTime | Get/set date and time |
| 4Eh | Find First | Find first matching file |
| 4Fh | Find Next | Find next matching file |
| 5Ah | Create Temp | Create temporary file |
| 5Bh | Create New | Create new file (fail if exists) |
| 5Ch | Lock/Unlock | Lock file region |

---

## Directory and Path Functions

### AH = 39h: Create Directory

**Purpose:** Create a new subdirectory.

**Registers:**
- **Input:** AH = 39h, DS:DX = directory path
- **Output:** AX = error code (if CF set)
- **Flags:** CF set on error

**Details:**
- Parent directory must exist
- Directory name must be unique in parent
- Can include full path (drives and parent directories)

**Example Code:**
```asm
.data
newdir db "C:\NEWDIR", 0

.code
mov ah, 39h        ; Function 39h - Create directory
lea dx, [newdir]
int 21h
jc mkdir_error     ; If CF set, creation failed
mkdir_error:
```

---

### AH = 3Ah: Remove Directory

**Purpose:** Delete an empty subdirectory.

**Registers:**
- **Input:** AH = 3Ah, DS:DX = directory path
- **Output:** AX = error code (if CF set)
- **Flags:** CF set on error

**Details:**
- Directory must be empty (no files or subdirectories)
- Cannot delete current directory
- Cannot delete root directory

**Example Code:**
```asm
.data
deldir db "OLDDIR", 0

.code
mov ah, 3Ah        ; Function 3Ah - Remove directory
lea dx, [deldir]
int 21h
jc rmdir_error     ; If CF set, deletion failed
rmdir_error:
```

---

### AH = 3Bh: Change Current Directory

**Purpose:** Change the default working directory.

**Registers:**
- **Input:** AH = 3Bh, DS:DX = directory path
- **Output:** AX = error code (if CF set)
- **Flags:** CF set on error

**Details:**
- Can include drive letter (C:\DOCS)
- Can use relative paths (..\PARENT)
- Changes directory for current process only
- Does not affect other running programs

**Example Code:**
```asm
.data
newpath db "C:\DOCS", 0

.code
mov ah, 3Bh        ; Function 3Bh - Change directory
lea dx, [newpath]
int 21h
jc chdir_error     ; If CF set, change failed
chdir_error:
```

---

### AH = 47h: Get Current Directory

**Purpose:** Retrieve the current working directory path.

**Registers:**
- **Input:** AH = 47h, DL = drive (0=current, 1=A:, 2=B:, etc.), DS:SI = buffer
- **Output:** Buffer filled with path, AX = 00h (always succeeds)

**Details:**
- Buffer must be at least 64 bytes
- Returns path without drive letter
- Path does not include leading backslash

**Example Code:**
```asm
.data
path_buffer db 64 dup(0)

.code
mov ah, 47h        ; Function 47h - Get current directory
mov dl, 0          ; DL = 0 (current drive)
lea si, [path_buffer]
int 21h
; path_buffer now contains current directory path
```

---

### AH = 4Eh: Find First File (with Attributes)

**Purpose:** Search for first file matching name pattern and attributes.

**Registers:**
- **Input:** AH = 4Eh, DS:DX = filename/pattern, CX = attributes to search
- **Output:** AX = error code (if CF set), DTA filled with data
- **Flags:** CF set on error

**Search Attributes (CX):**
- Bit 0: Read-only (1)
- Bit 1: Hidden (2)
- Bit 2: System (4)
- Bit 3: Volume label (8)
- Bit 4: Directory (16)
- Bit 5: Archive (32)

**DTA Format (Returned in buffer set by 1Ah):**
```
Offset  Size  Purpose
0       21    Reserved (for Find Next)
21      1     File attribute
22      2     File time
24      2     File date
26      4     File size
30      13    Filename (null-terminated)
```

**Example Code:**
```asm
.data
pattern db "*.TXT", 0
dta_buffer db 43 dup(0)

.code
; Set DTA address
mov ah, 1Ah        ; Function 1Ah - Set DTA
lea dx, [dta_buffer]
int 21h

; Find first .TXT file
mov ah, 4Eh        ; Function 4Eh - Find first
lea dx, [pattern]
mov cx, 0          ; CX = 0 (normal files only)
int 21h
jc no_files        ; If CF set, no files found
; DTA now contains first matching file info
no_files:
```

**Algorithm:**
```
1. Set up DTA (Disk Transfer Area) with function 1Ah
2. Set AH = 4Eh
3. Set DX = pointer to filename pattern (with wildcards: * or ?)
4. Set CX = file attributes to match
5. Call INT 21H
6. If CF = 0: DTA contains file information
   - Offset 21: Attribute byte
   - Offset 22-23: Time
   - Offset 24-25: Date
   - Offset 26-29: File size (little-endian)
   - Offset 30-42: Filename (ASCIIZ)
7. If CF = 1: No files found
```

---

### AH = 4Fh: Find Next File

**Purpose:** Find next file matching previous Find First search.

**Registers:**
- **Input:** AH = 4Fh
- **Output:** AX = error code (if CF set), DTA updated with next match
- **Flags:** CF set on error (usually no more files)

**Details:**
- Must call Find First (4Eh) before using Find Next
- DTA must not be modified between calls
- Uses search context saved in DTA by Find First

**Example Code:**
```asm
; Assuming Find First was successful...
find_next_loop:
mov ah, 4Fh        ; Function 4Fh - Find next
int 21h
jc end_search      ; If CF set, no more files
; DTA contains next matching file
; Process it...
jmp find_next_loop ; Continue searching

end_search:
```

---

## System Information Functions

### AH = 2Ah: Get System Date

**Purpose:** Retrieve current system date.

**Registers:**
- **Input:** AH = 2Ah
- **Output:** CX = year, DH = month, DL = day, AL = day of week
- **Flags:** None

**Details:**
- CX = year (1980-2099)
- DH = month (1-12)
- DL = day (1-31)
- AL = day of week (0=Sunday, 1=Monday, ..., 6=Saturday)

**Example Code:**
```asm
.data
year dw 0
month db 0
day db 0
weekday db 0

.code
mov ah, 2Ah        ; Function 2Ah - Get date
int 21h
mov year, cx       ; CX = year
mov month, dh      ; DH = month
mov day, dl        ; DL = day
mov weekday, al    ; AL = day of week
```

---

### AH = 2Bh: Set System Date

**Purpose:** Change the system date.

**Registers:**
- **Input:** AH = 2Bh, CX = year, DH = month, DL = day
- **Output:** AL = 0 (success) or FFh (invalid date)
- **Flags:** None

**Details:**
- Requires valid date values
- CX = year (1980-2099)
- DH = month (1-12)
- DL = day (1-31)
- Changing date affects file timestamps

**Example Code:**
```asm
mov ah, 2Bh        ; Function 2Bh - Set date
mov cx, 2025       ; Year = 2025
mov dh, 3          ; Month = March
mov dl, 15         ; Day = 15
int 21h
cmp al, 0          ; AL = 0 for success
jne invalid_date
invalid_date:
```

---

### AH = 2Ch: Get System Time

**Purpose:** Retrieve current system time.

**Registers:**
- **Input:** AH = 2Ch
- **Output:** CH = hours, CL = minutes, DH = seconds, DL = hundredths
- **Flags:** None

**Details:**
- CH = hours (0-23)
- CL = minutes (0-59)
- DH = seconds (0-59)
- DL = hundredths of second (0-99)

**Example Code:**
```asm
.data
hours db 0
minutes db 0
seconds db 0

.code
mov ah, 2Ch        ; Function 2Ch - Get time
int 21h
mov hours, ch      ; CH = hours
mov minutes, cl    ; CL = minutes
mov seconds, dh    ; DH = seconds
```

---

### AH = 2Dh: Set System Time

**Purpose:** Change the system time.

**Registers:**
- **Input:** AH = 2Dh, CH = hours, CL = minutes, DH = seconds, DL = hundredths
- **Output:** AL = 0 (success) or FFh (invalid time)
- **Flags:** None

**Details:**
- Validates time values before setting
- CH = hours (0-23)
- CL = minutes (0-59)
- DH = seconds (0-59)
- DL = hundredths (0-99)

**Example Code:**
```asm
mov ah, 2Dh        ; Function 2Dh - Set time
mov ch, 14         ; Hours = 14 (2 PM)
mov cl, 30         ; Minutes = 30
mov dh, 45         ; Seconds = 45
mov dl, 0          ; Hundredths = 0
int 21h
```

---

### AH = 30h: Get DOS Version

**Purpose:** Retrieve DOS version information.

**Registers:**
- **Input:** AH = 30h
- **Output:** AL = major version, AH = minor version
- **Flags:** None

**Details:**
- Returns version in decimal (e.g., 3.30 = AL=3, AH=30)
- Useful for compatibility checks
- Different DOS versions have different function support

**Example Code:**
```asm
.data
dos_version db 0

.code
mov ah, 30h        ; Function 30h - Get DOS version
int 21h
; AL = major version, AH = minor version
mov dos_version, al
```

---

### AH = 36h: Get Free Disk Space

**Purpose:** Retrieve available disk space on a drive.

**Registers:**
- **Input:** AH = 36h, DL = drive (0=current, 1=A:, 2=B:, etc.)
- **Output:** AX = sectors per cluster, BX = free clusters, CX = bytes per sector, DX = total clusters
- **Flags:** AX = FFFFh on error

**Details:**
- Calculation: Free space = (BX * AX * CX) bytes
- Useful for checking if enough space before writing

**Example Code:**
```asm
.data
free_space dd 0

.code
mov ah, 36h        ; Function 36h - Get free disk space
mov dl, 0          ; DL = 0 (current drive)
int 21h
cmp ax, 0FFFFh     ; Check for error
je disk_error      ; If ax=FFFFh, error
; AX = sectors/cluster, BX = free clusters, CX = bytes/sector
mov eax, ebx       ; EAX = free clusters
mov ecx, eax       ; ECX = sectors per cluster
imul eax, ecx      ; EAX = free clusters * sectors
mov ecx, cax       ; ECX = bytes per sector
imul eax, ecx      ; EAX = total free bytes
mov free_space, eax
disk_error:
```

---

### AH = 33h: Get/Set Ctrl-Break Flag

**Purpose:** Enable or disable Ctrl-C/Ctrl-Break interrupt checking.

**Registers (Get - AL=0):**
- **Input:** AH = 33h, AL = 0
- **Output:** DL = current state (0=off, 1=on)

**Registers (Set - AL=1):**
- **Input:** AH = 33h, AL = 1, DL = new state
- **Output:** None significant

**Details:**
- When enabled (DL=1), Ctrl-C is checked regularly
- When disabled (DL=0), program runs without interruption
- Useful for critical sections of code

**Example Code:**
```asm
; Get current Ctrl-Break state
mov ah, 33h
mov al, 0
int 21h
; DL = current state

; Disable Ctrl-Break
mov ah, 33h
mov al, 1
mov dl, 0          ; DL = 0 (disable)
int 21h

; Re-enable Ctrl-Break
mov ah, 33h
mov al, 1
mov dl, 1          ; DL = 1 (enable)
int 21h
```

---

### AH = 35h: Get Interrupt Vector

**Purpose:** Retrieve the address of an interrupt service routine.

**Registers:**
- **Input:** AH = 35h, AL = interrupt number
- **Output:** ES:BX = address of ISR
- **Flags:** None

**Details:**
- Allows reading existing interrupt handlers
- Useful for chaining interrupts
- ES = segment, BX = offset

**Example Code:**
```asm
.data
old_int08_seg dw 0
old_int08_off dw 0

.code
mov ah, 35h        ; Function 35h - Get interrupt vector
mov al, 08h        ; AL = interrupt number (timer)
int 21h
mov old_int08_seg, es  ; ES = segment
mov old_int08_off, bx  ; BX = offset
```

---

### AH = 25h: Set Interrupt Vector

**Purpose:** Change the address of an interrupt service routine.

**Registers:**
- **Input:** AH = 25h, AL = interrupt number, DS:DX = new ISR address
- **Output:** None significant
- **Flags:** None

**Details:**
- Allows hooking interrupts with custom handlers
- Must preserve original address to chain or restore
- Used for TSR (Terminate and Stay Resident) programs

**Example Code:**
```asm
.data
old_timer_seg dw 0
old_timer_off dw 0
new_isr_offset equ my_timer_handler

.code
; Save old handler
mov ah, 35h
mov al, 08h
int 21h
mov old_timer_seg, es
mov old_timer_off, bx

; Install new handler
mov ah, 25h
mov al, 08h
mov dx, offset new_isr_offset
int 21h

my_timer_handler:
    ; Custom timer code here
    jmp [old_timer_seg:old_timer_off]
```

---

## Memory Management Functions

### AH = 48h: Allocate Memory Block

**Purpose:** Request a block of memory from DOS.

**Registers:**
- **Input:** AH = 48h, BX = size in paragraphs (16-byte blocks)
- **Output:** AX = segment address (if CF=0) or max available (if CF=1)
- **Flags:** CF set on error

**Details:**
- BX = number of paragraphs (1 paragraph = 16 bytes)
- Memory returned at segment address in AX
- Offset is always 0 (paragraph-aligned)
- If allocation fails, AX contains largest available block

**Example Code:**
```asm
.data
allocated_segment dw 0

.code
mov ah, 48h        ; Function 48h - Allocate memory
mov bx, 1024       ; BX = 1024 paragraphs (16 KB)
int 21h
jc alloc_failed    ; If CF set, allocation failed
mov allocated_segment, ax  ; AX = segment address
alloc_failed:
```

**Algorithm:**
```
1. Calculate size in paragraphs: (bytes + 15) / 16
2. Set AH = 48h
3. Set BX = size in paragraphs
4. Call INT 21H
5. If CF = 0: AX = segment of allocated memory
   - Access memory at AX:0 (segment:offset)
6. If CF = 1: Allocation failed
   - AX = size of largest available block (in paragraphs)
```

---

### AH = 49h: Free Memory Block

**Purpose:** Release a previously allocated memory block.

**Registers:**
- **Input:** AH = 49h, ES = segment address of block to free
- **Output:** AX = error code (if CF set)
- **Flags:** CF set on error

**Details:**
- Must provide exact segment address from allocation
- Cannot partially free a block
- Frees memory for other programs to use

**Example Code:**
```asm
mov ah, 49h        ; Function 49h - Free memory
mov es, allocated_segment
int 21h
jc free_failed     ; If CF set, free failed
free_failed:
```

---

### AH = 4Ah: Modify Memory Block Size

**Purpose:** Resize an allocated memory block (grow or shrink).

**Registers:**
- **Input:** AH = 4Ah, ES = segment address, BX = new size in paragraphs
- **Output:** AX = error code (if CF set), BX = max available (if CF=1)
- **Flags:** CF set on error

**Details:**
- Can increase or decrease block size
- Cannot shrink below current usage
- If grow fails, AX contains largest available

**Example Code:**
```asm
mov ah, 4Ah        ; Function 4Ah - Modify memory
mov es, allocated_segment
mov bx, 2048       ; New size = 2048 paragraphs
int 21h
jc resize_failed   ; If CF set, resize failed
resize_failed:
```

---

## Process Control Functions

### AH = 4Ch: Terminate Process (Return to DOS)

**Purpose:** End the current program and return to DOS.

**Registers:**
- **Input:** AH = 4Ch, AL = exit code (0-255)
- **Output:** Program terminates
- **Flags:** N/A

**Details:**
- Most common way to exit a program
- Exit code can be checked by parent process or batch file
- All open files are closed automatically
- Allocated memory is freed

**Example Code:**
```asm
mov ah, 4Ch        ; Function 4Ch - Terminate
mov al, 0          ; AL = 0 (success exit code)
int 21h            ; Does not return
```

---

### AH = 4Bh: Execute Program (Load and Run)

**Purpose:** Load and execute another program, returning when it finishes.

**Registers:**
- **Input:** AH = 4Bh, AL = function (0=load and execute, 1=load only, 3=load overlay), DS:DX = filename, ES:BX = parameters
- **Output:** AX = error code (if CF set)
- **Flags:** CF set on error

**Parameter Block Structure (for AL=0):**
```
Offset  Size  Purpose
0       2     Environment segment (0=inherit from parent)
2       4     Command line pointer (DS:SI format)
6       4     FCB1 pointer (DS:SI format)
10      4     FCB2 pointer (DS:SI format)
```

**Details:**
- AL=0: Execute and wait for return
- AL=1: Load only, don't execute
- AL=3: Load overlay (for overlaid programs)
- Parent process waits for child to complete
- Child's exit code available via function 4Dh

**Example Code:**
```asm
.data
param_block db 0, 0  ; Environment segment = inherit
            dd 0     ; Command line pointer
            dd 0     ; FCB1
            dd 0     ; FCB2
program db "TEST.COM", 0

.code
mov ah, 4Bh        ; Function 4Bh - Execute
mov al, 0          ; AL = 0 (load and execute)
lea dx, [program]
lea bx, [param_block]
int 21h
jc exec_failed     ; If CF set, execution failed
exec_failed:
```

---

### AH = 4Dh: Get Return Code from Child Process

**Purpose:** Retrieve exit code from a completed child process.

**Registers:**
- **Input:** AH = 4Dh
- **Output:** AX = return code (AH=termination type, AL=exit code)
- **Flags:** None

**Termination Types (AH):**
- 0 = Normal termination
- 1 = Terminated by Ctrl-C/Break
- 2 = Terminated by critical error
- 3 = Terminated and stayed resident (TSR)

**Details:**
- Must call immediately after child process returns
- Only valid immediately after process execution
- AL = actual exit code set by program

**Example Code:**
```asm
mov ah, 4Dh        ; Function 4Dh - Get return code
int 21h
mov child_exit_code, al  ; AL = exit code
mov termination_type, ah ; AH = termination type
```

---

### AH = 31h: Terminate and Stay Resident (TSR)

**Purpose:** End current program but keep it in memory for future use.

**Registers:**
- **Input:** AH = 31h, AL = exit code, DX = memory size in paragraphs to keep resident
- **Output:** Program terminates
- **Flags:** N/A

**Details:**
- Program remains loaded in memory
- Typically used with interrupt hooking
- DX = paragraphs to reserve (including PSP)
- Rest of memory becomes available

**Example Code:**
```asm
; Keep 4 KB resident (256 paragraphs)
mov ah, 31h        ; Function 31h - TSR
mov al, 0          ; Exit code = 0
mov dx, 256        ; Keep 256 paragraphs resident
int 21h            ; Does not return (TSR installed)
```

---

## Detailed Examples with Explanations

### Example 1: Read String from Keyboard and Display

**Purpose:** Read a line of text from user and display it back.

```asm
.data
input_buffer db 20, 0, 20 dup(0)  ; Max 20 chars
output_msg db "You typed: ", "$"

.code
start:
    ; Display prompt
    mov ah, 09h        ; Function 09h - Write string
    lea dx, [output_msg]
    int 21h
    
    ; Read input
    mov ah, 0Ah        ; Function 0Ah - Buffered input
    lea dx, [input_buffer]
    int 21h            ; User enters text, presses ENTER
    
    ; Display newline
    mov ah, 02h        ; Function 02h - Write character
    mov dl, 0Dh        ; CR
    int 21h
    mov dl, 0Ah        ; LF
    int 21h
    
    ; Display the input buffer content
    mov ah, 09h        ; Function 09h
    lea dx, [input_buffer + 2]  ; Skip max and actual length bytes
    int 21h
    
    ; Exit program
    mov ah, 4Ch        ; Function 4Ch - Terminate
    mov al, 0          ; Exit code = 0
    int 21h

; Algorithm Explanation:
; 1. Prompt user for input using function 09h (write string)
; 2. Read string using function 0Ah (buffered input)
;    - Buffer structure: [max_length][actual_length][string...]
;    - Function 0Ah handles backspace and line editing
;    - User presses ENTER to confirm
; 3. Output newline (CR + LF) using function 02h twice
; 4. Display input using function 09h (but must add '$' terminator)
; 5. Exit with function 4Ch
```

**What Each Line Does:**
- `mov ah, 09h`: Set function to "write string"
- `lea dx, [output_msg]`: Load address of prompt message
- `int 21h`: Call DOS to display prompt
- `mov ah, 0Ah`: Set function to "buffered input"
- `lea dx, [input_buffer]`: Load address of input buffer
- `int 21h`: Call DOS to read keyboard input
- The user types text and presses ENTER
- Function 0Ah stores character count at buffer[1]
- `mov dl, 0Dh`: Carriage return ASCII code
- `mov dl, 0Ah`: Line feed ASCII code
- `lea dx, [input_buffer + 2]`: Point to actual string data (skip 2-byte header)
- `mov ah, 4Ch`: Set function to terminate
- `mov al, 0`: Exit code 0 (success)

---

### Example 2: Create, Write, and Read a File

**Purpose:** Create a file, write data to it, close it, then reopen and read it.

```asm
.data
filename db "output.txt", 0
write_data db "Hello, File World!", 0Dh, 0Ah
write_len equ $ - write_data
read_buffer db 256 dup(0)
file_handle dw 0
bytes_read dw 0

.code
start:
    ; Create new file
    mov ah, 3Ch        ; Function 3Ch - Create file
    lea dx, [filename]
    xor cx, cx         ; CX = 0 (normal file attributes)
    int 21h
    jc create_error    ; If CF set, error occurred
    mov file_handle, ax ; Save file handle
    
    ; Write data to file
    mov ah, 40h        ; Function 40h - Write file
    mov bx, file_handle
    mov cx, write_len
    lea dx, [write_data]
    int 21h
    jc write_error     ; If CF set, write error
    
    ; Close file
    mov ah, 3Eh        ; Function 3Eh - Close file
    mov bx, file_handle
    int 21h
    jc close_error
    
    ; Open file for reading
    mov ah, 3Dh        ; Function 3Dh - Open file
    lea dx, [filename]
    mov al, 0          ; AL = 0 (read-only mode)
    int 21h
    jc open_error      ; If CF set, error
    mov file_handle, ax ; Save new file handle
    
    ; Read data from file
    mov ah, 3Fh        ; Function 3Fh - Read file
    mov bx, file_handle
    mov cx, 256        ; Read up to 256 bytes
    lea dx, [read_buffer]
    int 21h
    jc read_error      ; If CF set, read error
    mov bytes_read, ax ; AX = bytes actually read
    
    ; Close file
    mov ah, 3Eh        ; Function 3Eh - Close file
    mov bx, file_handle
    int 21h
    jc close_error
    
    ; Exit program
    mov ah, 4Ch
    mov al, 0
    int 21h

create_error:
open_error:
write_error:
read_error:
close_error:
    ; All errors jump here
    mov ah, 4Ch
    mov al, 1          ; Exit code = 1 (error)
    int 21h

; Algorithm Explanation:
; 1. Create file using function 3Ch
;    - DX = filename, CX = attributes
;    - Returns file handle in AX on success
; 2. Write data using function 40h
;    - BX = file handle, CX = bytes to write, DX = data address
;    - Returns actual bytes written in AX
; 3. Close file using function 3Eh
;    - BX = file handle
; 4. Open existing file using function 3Dh
;    - DX = filename, AL = access mode (0=read, 1=write, 2=read/write)
;    - Returns new file handle in AX
; 5. Read file using function 3Fh
;    - BX = file handle, CX = max bytes to read, DX = buffer address
;    - Returns actual bytes read in AX (0 if EOF)
; 6. Close file again
; 7. Exit program
```

**What Each Line Does:**
- `mov ah, 3Ch`: Create file function
- `lea dx, [filename]`: Address of filename string
- `xor cx, cx`: Clear CX (no special attributes)
- `int 21h`: Call DOS
- `jc create_error`: Jump if error (carry flag set)
- `mov file_handle, ax`: Save returned file handle
- `mov ah, 40h`: Write file function
- `mov bx, file_handle`: Use saved file handle
- `mov cx, write_len`: Number of bytes to write
- `lea dx, [write_data]`: Address of data
- `jc write_error`: Jump if write error
- `mov ah, 3Eh`: Close file function
- `mov bx, file_handle`: File handle to close
- Similar pattern repeats for open and read operations

---

### Example 3: Display System Date and Time

**Purpose:** Retrieve and display current system date and time.

```asm
.data
date_msg db "Date: ", "$"
time_msg db "Time: ", "$"
dash db "-", "$"
colon db ":", "$"

.code
start:
    ; Display "Date: " prompt
    mov ah, 09h        ; Function 09h - Write string
    lea dx, [date_msg]
    int 21h
    
    ; Get system date
    mov ah, 2Ah        ; Function 2Ah - Get date
    int 21h
    ; CX = year, DH = month, DL = day, AL = day of week
    
    ; Display year (in CX)
    mov ax, cx         ; AX = year
    call display_word  ; Custom procedure to convert and display
    
    ; Display '-'
    mov ah, 09h
    lea dx, [dash]
    int 21h
    
    ; Display month (in DH)
    mov al, dh         ; AL = month
    call display_byte  ; Custom procedure for byte
    
    ; Display '-'
    mov ah, 09h
    lea dx, [dash]
    int 21h
    
    ; Display day (in DL)
    mov al,