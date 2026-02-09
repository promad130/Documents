# 07 - Build System: Compiling and Linking the OS

## Table of Contents

1. [Overview](#overview)
2. [Cross-Compiler Setup](#cross-compiler-setup)
3. [Makefile Structure](#makefile-structure)
4. [Compilation Process](#compilation-process)
5. [Linker Scripts](#linker-scripts)
6. [Creating Bootable ISO](#creating-bootable-iso)

---

## Overview

Building an OS requires specialized tools:

1. **Cross-Compiler** - Compiles for bare-metal x86_64 (no OS dependencies)
2. **Assembler** - Converts assembly to machine code (NASM)
3. **Linker** - Combines object files into kernel binary
4. **GRUB** - Bootloader
5. **ISO Creator** - Makes bootable CD image

### Why Cross-Compiler?

Your host compiler (gcc) targets your OS (Linux/Windows/macOS):

- Includes standard library (libc)
- Expects OS features (system calls, dynamic linking)
- Wrong target architecture

A **cross-compiler** targets bare-metal:

- No standard library
- No OS assumptions
- Correct target (x86_64-elf)

---

## Cross-Compiler Setup

### Building x86_64-elf-gcc

#### Prerequisites

```bash
# Ubuntu/Debian
sudo apt install build-essential bison flex libgmp3-dev \
                 libmpc-dev libmpfr-dev texinfo

# Arch Linux
sudo pacman -S base-devel gmp libmpc mpfr
```

#### Download Binutils and GCC

```bash
export PREFIX="$HOME/opt/cross"
export TARGET=x86_64-elf
export PATH="$PREFIX/bin:$PATH"

mkdir ~/cross-compiler
cd ~/cross-compiler

# Download
wget https://ftp.gnu.org/gnu/binutils/binutils-2.39.tar.xz
wget https://ftp.gnu.org/gnu/gcc/gcc-12.2.0/gcc-12.2.0.tar.xz

tar xf binutils-2.39.tar.xz
tar xf gcc-12.2.0.tar.xz
```

#### Build Binutils

```bash
mkdir build-binutils
cd build-binutils

../binutils-2.39/configure --target=$TARGET --prefix="$PREFIX" \
    --with-sysroot --disable-nls --disable-werror

make -j$(nproc)
make install

cd ..
```

#### Build GCC

```bash
mkdir build-gcc
cd build-gcc

../gcc-12.2.0/configure --target=$TARGET --prefix="$PREFIX" \
    --disable-nls --enable-languages=c,c++ --without-headers

make all-gcc -j$(nproc)
make all-target-libgcc -j$(nproc)
make install-gcc
make install-target-libgcc
```

#### Verify Installation

```bash
$TARGET-gcc --version
$TARGET-ld --version
```

### Alternative: Pre-built Cross-Compiler

```bash
# Ubuntu
sudo apt install gcc-x86-64-linux-gnu

# Or use Docker
docker pull randomdude/gcc-cross-x86_64-elf
```

### Other Required Tools

```bash
# NASM (assembler)
sudo apt install nasm

# QEMU (emulator)
sudo apt install qemu-system-x86

# GRUB tools
sudo apt install grub-pc-bin xorriso
```

---

## Makefile Structure

Our Makefile automates the build process.

### File Collection

```makefile
# Find all C source files (excluding build/dist/targets directories)
all_c_sources := $(shell find . -name '*.c' \
                  -not -path './build/*' \
                  -not -path './dist/*' \
                  -not -path './targets/*' \
                  -printf '%P\n')

# Convert to object file paths
all_object_files := $(patsubst %.c, build/%.o, $(all_c_sources))

# Find all assembly source files
x86_64_asm_source_files := $(shell find . -name '*.asm' \
                             -not -path './build/*' \
                             -not -path './dist/*' \
                             -not -path './targets/*' \
                             -printf '%P\n')

# Convert to object file paths
x86_64_asm_object_files := $(patsubst %.asm, build/x86_64/%.o, \
                             $(x86_64_asm_source_files))
```

📝 **Note**: `find` searches recursively, `%P` gives relative path.

### Compilation Rules

#### C Files

```makefile
# Pattern rule: build/path/to/file.o from path/to/file.c
build/%.o: %.c
	mkdir -p $(dir $@) && \
	x86_64-elf-gcc -c -I includes -ffreestanding $< -o $@
```

**Flags explained**:

- `-c` - Compile only (don't link)
- `-I includes` - Add includes directory to search path
- `-ffreestanding` - Bare-metal target (no standard library)
- `$<` - Input file (%.c)
- `$@` - Output file (build/%.o)

#### Assembly Files

```makefile
$(x86_64_asm_object_files): build/x86_64/%.o : %.asm
	mkdir -p $(dir $@) && \
	nasm -f elf64 $< -o $@
```

**Flags explained**:

- `-f elf64` - Output format (64-bit ELF)
- `$<` - Input file (%.asm)
- `$@` - Output file (build/x86_64/%.o)

### Linking

```makefile
.PHONY: build-x86_64
build-x86_64: clean-x86_64 $(all_object_files) $(x86_64_asm_object_files)
	mkdir -p dist/x86_64 && \
	x86_64-elf-ld -n -o dist/x86_64/kernel.bin \
		-T targets/x86_64/linker.ld \
		$(all_object_files) $(x86_64_asm_object_files) && \
	cp dist/x86_64/kernel.bin targets/x86_64/iso/boot/kernel.bin && \
	grub-mkrescue -o dist/x86_64/kernel.iso targets/x86_64/iso
```

**Linker flags**:

- `-n` - Disable page alignment
- `-o` - Output file
- `-T` - Use custom linker script

### Clean Target

```makefile
clean-x86_64:
	$(SUDO) rm -rf build/*
	$(SUDO) rm -rf dist/x86_64
	$(SUDO) rm -rf targets/x86_64/iso/boot/kernel.bin
```

### Running in QEMU

```makefile
QEMU_MEM ?= 1G

.PHONY: run
run: build-x86_64
	qemu-system-x86_64 -cdrom dist/x86_64/kernel.iso -m $(QEMU_MEM)
```

### Disk Image Creation

```makefile
DISK_IMAGE := disk.img
DISK_SIZE_MB := 100

.PHONY: disk-create
disk-create:
	@echo "Creating $(DISK_SIZE_MB)MB disk image..."
	dd if=/dev/zero of=$(DISK_IMAGE) bs=1M count=$(DISK_SIZE_MB)
	@echo "Creating partition table..."
	$(SUDO) parted $(DISK_IMAGE) mklabel msdos
	$(SUDO) parted $(DISK_IMAGE) mkpart primary fat32 1MiB 100%
	$(SUDO) parted $(DISK_IMAGE) set 1 boot on
```

---

## Compilation Process

### Step-by-Step Build

#### 1. Preprocessing

```bash
x86_64-elf-gcc -E -I includes source.c -o source.i
```

- Expands `#include`, `#define`
- Removes comments
- Outputs `.i` file

#### 2. Compilation

```bash
x86_64-elf-gcc -S -ffreestanding source.i -o source.s
```

- Converts C to assembly
- Outputs `.s` file

#### 3. Assembly

```bash
x86_64-elf-gcc -c source.s -o source.o
# OR for .asm files:
nasm -f elf64 source.asm -o source.o
```

- Assembles to machine code
- Outputs `.o` (ELF object file)

#### 4. Linking

```bash
x86_64-elf-ld -T linker.ld -o kernel.bin file1.o file2.o file3.o
```

- Combines all object files
- Resolves symbols (`extern` references)
- Outputs final binary

### Compiler Flags Deep Dive

```bash
x86_64-elf-gcc \
    -c \                      # Compile only, don't link
    -ffreestanding \          # Bare-metal (no OS)
    -fno-builtin \            # Don't use GCC built-in functions
    -nostdlib \               # Don't link standard library
    -nostdinc \               # Don't include standard headers
    -Wall \                   # Enable all warnings
    -Wextra \                 # Extra warnings
    -Werror \                 # Treat warnings as errors
    -O2 \                     # Optimization level 2
    -I includes \             # Include directory
    -mno-red-zone \           # Disable red zone (important for kernel!)
    -mno-sse \                # Disable SSE instructions
    -mno-sse2 \               # Disable SSE2 instructions
    -mcmodel=kernel \         # Kernel code model
    source.c -o source.o
```

#### Important Flags Explained

**`-ffreestanding`**:

- No hosted environment (OS)
- Only language features, no libraries
- `main` is not special

**`-mno-red-zone`**:

- Red zone = 128 bytes below RSP that function can use without adjusting RSP
- Interrupts can clobber red zone!
- Must disable for kernel code

**`-mno-sse`**:

- SSE uses XMM registers
- Kernel must save/restore XMM on context switch
- Easier to disable initially

**`-mcmodel=kernel`**:

- Code/data in high 2GB of address space
- For higher-half kernels

### Object File Inspection

```bash
# View symbols
x86_64-elf-nm kernel.bin

# View sections
x86_64-elf-objdump -h kernel.bin

# Disassemble
x86_64-elf-objdump -d kernel.bin

# View relocations
x86_64-elf-readelf -r file.o
```

---

## Linker Scripts

The **linker script** controls memory layout.

### Our Linker Script

**File: `targets/x86_64/linker.ld`**

```ld
ENTRY(start)                    /* Entry point symbol */

SECTIONS
{
  . = 1M;                       /* Start at 1MB */

  .boot :
  {
    KEEP(*(.multiboot_header))  /* Keep multiboot header */
  }

  .text ALIGN(4K) :             /* Code section, 4KB aligned */
  {
    *(.text)                    /* All .text sections */
  }

  .rodata ALIGN(4K) :           /* Read-only data */
  {
    *(.rodata)
  }

  .data ALIGN(4K) :             /* Initialized data */
  {
    *(.data)
  }

  .bss ALIGN(4K) :              /* Uninitialized data */
  {
    *(COMMON)
    *(.bss)
  }

  /* End marker */
  _kernel_end = .;
}
```

### Linker Script Syntax

#### Location Counter (`.`)

```ld
. = 1M;                 /* Set location to 1MB */
. = ALIGN(4K);          /* Align to 4KB boundary */
. += 0x1000;            /* Advance by 4KB */
```

#### Section Commands

```ld
.section_name :         /* Output section name */
{
  *(.input_section)     /* All input sections named .input_section */
  file.o(.text)         /* .text from specific file */
}
```

#### Symbols

```ld
_start_of_section = .;  /* Create symbol at current location */

/* Use in C: */
extern char _start_of_section;
uint64_t addr = (uint64_t)&_start_of_section;
```

### Advanced Linker Script

```ld
OUTPUT_FORMAT(elf64-x86-64)
OUTPUT_ARCH(i386:x86-64)
ENTRY(start)

SECTIONS
{
  /* Higher-half kernel at -2GB */
  . = 0xFFFFFFFF80000000;

  /* Save physical address */
  _kernel_phys_start = . - 0xFFFFFFFF80000000;

  .boot ALIGN(4K) :
  {
    KEEP(*(.multiboot_header))
  }

  .text ALIGN(4K) :
  {
    *(.text*)
  }

  .rodata ALIGN(4K) :
  {
    *(.rodata*)
  }

  .data ALIGN(4K) :
  {
    *(.data*)
  }

  .bss ALIGN(4K) :
  {
    _bss_start = .;
    *(COMMON)
    *(.bss*)
    _bss_end = .;
  }

  _kernel_end = .;
  _kernel_phys_end = . - 0xFFFFFFFF80000000;

  /* Discard sections */
  /DISCARD/ :
  {
    *(.comment)
    *(.note*)
  }
}
```

### Zeroing BSS in C

```c
extern char _bss_start, _bss_end;

void clear_bss() {
    char* bss = &_bss_start;
    char* bss_end = &_bss_end;

    while (bss < bss_end) {
        *bss++ = 0;
    }
}
```

---

## Creating Bootable ISO

### ISO Directory Structure

```
targets/x86_64/iso/
├── boot/
│   ├── grub/
│   │   └── grub.cfg
│   └── kernel.bin
└── (other files)
```

### GRUB Configuration

**File: `targets/x86_64/grub.cfg`**

```
set timeout=0
set default=0

menuentry "My OS" {
    multiboot2 /boot/kernel.bin
    boot
}
```

**Options**:

- `timeout=0` - Boot immediately
- `menuentry` - Menu item
- `multiboot2` - Use Multiboot2 protocol
- `boot` - Start kernel

### Creating ISO

```bash
grub-mkrescue -o kernel.iso targets/x86_64/iso
```

This:

1. Creates ISO filesystem
2. Installs GRUB bootloader
3. Includes kernel and GRUB config
4. Makes it bootable

### Testing ISO

```bash
qemu-system-x86_64 -cdrom kernel.iso
```

### Debugging with QEMU

```bash
# Enable serial output
qemu-system-x86_64 -cdrom kernel.iso -serial stdio

# Enable debugging
qemu-system-x86_64 -cdrom kernel.iso -s -S
# -s: GDB server on port 1234
# -S: Pause at startup

# In another terminal:
gdb kernel.bin
(gdb) target remote localhost:1234
(gdb) break kernel_main
(gdb) continue
```

---

## Summary

### Build Process

```
Source Files (.c, .asm)
    ↓
Preprocessing (#include, #define)
    ↓
Compilation (C → Assembly)
    ↓
Assembly (Assembly → Object Code)
    ↓
Linking (Object Files → Binary)
    ↓
ISO Creation (Binary → Bootable Image)
```

### Key Tools

| Tool               | Purpose                  |
| ------------------ | ------------------------ |
| x86_64-elf-gcc     | Cross-compiler (C)       |
| x86_64-elf-ld      | Linker                   |
| nasm               | Assembler (x86 assembly) |
| grub-mkrescue      | ISO creation             |
| qemu-system-x86_64 | Emulator/testing         |

### Build Commands

```bash
# Build kernel
make build-x86_64

# Run in QEMU
make run

# Clean build
make clean-x86_64

# Create disk image
make disk-create
```

### Essential Compiler Flags

```
-ffreestanding    # Bare-metal target
-I includes       # Include directory
-mno-red-zone     # Disable red zone
-Wall -Wextra     # Enable warnings
-O2               # Optimize
```

---

**Next**: [08-Kernel-Architecture.md](08-Kernel-Architecture.md) - Overall kernel design

---

_"A robust build system is the foundation of efficient OS development. Automate early, debug less."_
